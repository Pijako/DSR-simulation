import java.util.ArrayList;
import java.util.List;

/**
 * Created by julienmoniot on 19/05/2016.
 *
 * @author Julien Moniot et Thibault Pichel
 */
public class Noeud {

    protected CaracteristiquePhysique caracteristiquePhysique;
    boolean evenementDemande = false;
    boolean evenementEnvoi = false;
    private Reseau reseau;
    private int debit;
    private int portee;
    private int adresse;
    private boolean actif;
    private TableRoutage tableRoutage;
    private List<Paquet> paquetsAttente;
    private List<Paquet> paquetsAttenteRoutage;
    private Noeud destinataire;

    /**
     * Constructeur de la classe Noeud
     *
     * @param portee
     * @param debit
     * @param adresse
     * @param position
     * @param modeDeplacement
     * @param destinationFinale
     * @param vitesse
     * @param limiteVitesse
     * @param noeudCible
     * @param reseau
     */
    public Noeud(int portee, int debit, int adresse, Point position, String modeDeplacement, Point destinationFinale,
                 int vitesse, int limiteVitesse, Noeud noeudCible, Reseau reseau) {
        this.actif = true;
        this.portee = portee;
        this.debit = debit;
        this.adresse = adresse;
        this.reseau = reseau;
        this.tableRoutage = new TableRoutage(new ArrayList<Route>());
        this.paquetsAttente = new ArrayList<Paquet>();
        this.paquetsAttenteRoutage = new ArrayList<Paquet>();
        this.caracteristiquePhysique = new CaracteristiquePhysique(position, modeDeplacement, 900, 755, destinationFinale,
                vitesse, limiteVitesse, noeudCible);
    }

    public int getAdresse() {
        return adresse;
    }

    public boolean estActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public int getPortee() {
        return portee;
    }

    public CaracteristiquePhysique getCaracteristiquePhysique() {
        return caracteristiquePhysique;
    }

    public int getDebit() {
        return debit;
    }

    public boolean getEvenementDemande(){
        return this.evenementDemande;
    }

    public boolean getEvenementEnvoi(){
        return this.evenementEnvoi;
    }

    public boolean setEvenementDemande(boolean demande){
        return this.evenementDemande;
    }

    public boolean setEvenementEnvoi(boolean envoi){
        return this.evenementEnvoi;
    }

    public TableRoutage getTable() {
        return this.tableRoutage;
    }


    /**
     * Permet de définir si un paquet est en attente ou non
     * @param paquet
     * @return
     */
    public boolean enAttente(Paquet paquet) {
        boolean enAttente = false;
        for (Paquet p : this.paquetsAttenteRoutage) {
            if (p == paquet) {
                enAttente = true;
            }
        }
        return enAttente;
    }

    private void purgerListeAttente() {
        for (Paquet p : this.paquetsAttenteRoutage) {
            reseau.getSimulateur().ajouterEvenement(new EvenementEnvoyer("Noeud n° " + this.getAdresse()
                    + " envoit Paquet n° " + p.identifiant + " a Noeud n° " + p.getEmetteur().getAdresse(), debit, this, p.getEmetteur(), p));
        }

    }

    private Route rassemblerRoute(Route route1, Route route2) {
        Route route3 = new Route();
        ArrayList<Noeud> tmp = new ArrayList<>();
        tmp.addAll(route1.getNoeuds());
        tmp.addAll(route2.getNoeuds());
        route3.setNoeudsRoute(tmp);
        return route3;
    }

    /**
     * Permet d'envoyer un paquet vers un Noeud destinataire
     * @param paquet Paquet à envoyer
     * @param noeudDestinataire Noeud destinataire
     */
    public void envoyerPaquet(Paquet paquet, Noeud noeudDestinataire) {
        //SI LE DESTINATAIRE EST A PORTEE
        if (this.estAtteignable(noeudDestinataire)) {
            reseau.getSimulateur().ajouterEvenement(new EvenementTraiter("Noeud  n° " + noeudDestinataire.getAdresse()
                    + " reçoit Paquet n° " + paquet.identifiant, debit, noeudDestinataire, paquet));
            //noeudDestinataire.traiterPaquet(paquet);
        }
        //SI LE DESTINATAIRE N'EST PAS ATTEIGNABLE, ON ESSAYE DE SUIVRE LA ROUTE
        else {
            //TYPE PAQUET DONNEE
            if (paquet instanceof PaquetDonnee) {
                PaquetDonnee paquetDonnee = (PaquetDonnee) paquet;
                this.evenementEnvoi = true;
                //SI ON ENVOIT LE PAQUET AU DEBUT
                if ((paquetDonnee.getRoute()).getSize() == 0) {
                    //SI LA ROUTE EST INCONNNUE, ON MET EN ATTENTE ET FAIT UNE DEMANDE DE ROUTE
                    if (!tableRoutage.contientRoute(noeudDestinataire)) {
                        if (!enAttente(paquetDonnee)) {
                            this.paquetsAttenteRoutage.add(paquetDonnee);
                        }
                        this.evenementDemande = true;
                        List<Noeud> atteignables = reseau.noeudsAtteignables(this);
                        for (Noeud noeud : atteignables) {
                            Route routeExploration = new Route();
                            routeExploration.ajouterNoeud(this);
                            PaquetRoutage paquetRoutage = new PaquetRoutage(reseau.nouveauPaquet(), this, destinataire, routeExploration);
                            reseau.getSimulateur().ajouterEvenement(new EvenementEnvoyer("Noeud : " + this.getAdresse()
                                    + " envoit Paquet de routage n° " + paquetRoutage.identifiant + " a Noeud n° " + noeud.getAdresse(), debit, this, noeud, paquetRoutage));
                            //envoyerPaquet(paquetRoutage, noeud);
                        }
                    }
                    //SI LA ROUTE EST CONNUE
                    else if (tableRoutage.contientRoute(noeudDestinataire)) {
                        paquetDonnee.setRoute(tableRoutage.getRoute(noeudDestinataire));
                        reseau.getSimulateur().ajouterEvenement(new EvenementTraiter("Noeud : " + paquetDonnee.getDestinataireSuivant().getAdresse()
                                + " reçoit Paquet de données n° " + paquetDonnee.identifiant, debit, paquetDonnee.getDestinataireSuivant(), paquetDonnee));
                        //(paquetDonnee.getDestinataireSuivant()).traiterPaquet(paquetDonnee);
                    }
                    //SI ON TRANSMET JUSTE LE PAQUET ET QU'ON PENSE CONNAITRE LA ROUTE
                    else if (tableRoutage.contientRoute(noeudDestinataire)) {
                        //SI EFFECTIVEMENT LE DESTINATAIRE SUIVANT EST A PORTEE
                        if (this.estAtteignable(paquetDonnee.getDestinataireSuivant())) {
                            reseau.getSimulateur().ajouterEvenement(new EvenementTraiter("Noeud : " + paquetDonnee.getDestinataireSuivant().getAdresse()
                                    + " reçoit Paquet de données n° " + paquetDonnee.identifiant, debit, paquetDonnee.getDestinataireSuivant(), paquetDonnee));
                            //(paquetDonnee.getDestinataireSuivant()).traiterPaquet(paquetDonnee);
                        }
                        //SI EN FAIT IL NE L'EST PLUS COMME ON LE CRAINT
                        else {
                            if (!enAttente(paquetDonnee)) {
                                this.paquetsAttenteRoutage.add(paquetDonnee);
                            }
                            this.evenementDemande = true;
                            List<Noeud> atteignables = reseau.noeudsAtteignables(this);
                            for (Noeud noeud : atteignables) {
                                Route routeExploration = new Route();
                                routeExploration.ajouterNoeud(this);
                                PaquetRoutage paquetRoutage = new PaquetRoutage(reseau.nouveauPaquet(), this, destinataire, routeExploration);
                                reseau.getSimulateur().ajouterEvenement(new EvenementEnvoyer("Noeud : " + this.getAdresse()
                                        + " envoit Paquet de données n° " + paquet.identifiant + " a Noeud n° " + noeud.getAdresse(), debit, this, noeud, paquetRoutage));
                                //envoyerPaquet(paquetRoutage, noeud);
                            }
                        }
                    }
                }
                //TYPE PAQUET ROUTAGE
                else if (paquet instanceof PaquetRoutage) {
                    this.evenementEnvoi = true;
                    PaquetRoutage paquetRoutage = (PaquetRoutage) paquet;
                    //SI C'EST LE DEBUT DE LA DEMANDE, ON FAIT UNE DEMANDE TOUT AUTOUR
                    if (paquetRoutage.getRouteExploration().getSize() == 0) {
                        List<Noeud> atteignables = reseau.noeudsAtteignables(this);
                        for (Noeud noeud : atteignables) {
                            Route routeExploration = new Route();
                            PaquetRoutage paquetDemandeRoute = new PaquetRoutage(reseau.nouveauPaquet(), this, paquet.getDestinataire(), routeExploration);
                            reseau.getSimulateur().ajouterEvenement(new EvenementTraiter("Noeud : " + noeud.getAdresse()
                                    + " reçoit Paquet de routage n° " + paquetDemandeRoute.identifiant, debit, noeud, paquetDemandeRoute));
                            //noeud.traiterPaquet(paquetDemandeRoute);
                        }
                    } else {
                        //SI ON NE FAIT QUE LA TRANSMETTRE
                        Route ancienneRoute = ((PaquetRoutage) paquet).getRouteExploration();
                        //ON INTERROGE LA TABLE DE ROUTAGE POUR POURSUIVRE
                        Route demandeRouteTable = tableRoutage.getRoute(noeudDestinataire);
                        //SI IL N'Y A AUCUNE ROUTE DISPONIBLE, ON POURSUIT LA DEMANDE DE ROUTE TOUT AUTOUR
                        if (!tableRoutage.contientRoute(noeudDestinataire)) {
                            List<Noeud> atteignables = reseau.noeudsAtteignables(this);
                            for (Noeud noeud : atteignables) {
                                Route routeExploration = new Route(paquetRoutage.getRouteExploration());
                                PaquetRoutage paquetDemandeRoute = new PaquetRoutage(reseau.nouveauPaquet(), this, paquetRoutage.getDestinataire(), routeExploration);
                                reseau.getSimulateur().ajouterEvenement(new EvenementTraiter("Noeud : " + noeud.getAdresse()
                                        + " reçoit Paquet de routage n° " + paquetDemandeRoute.identifiant, debit, noeud, paquetDemandeRoute));
                                //noeud.traiterPaquet(paquetDemandeRoute);
                            }
                            //SI LA ROUTE EST DANS LA TABLE DE ROUTAGE
                        } else {
                            Route routeFinale = this.rassemblerRoute(ancienneRoute, demandeRouteTable);
                            PaquetDonnee paquetFinRoutage = new PaquetDonnee(reseau.nouveauPaquet(), paquet.getEmetteur(), paquet.getDestinataire(), demandeRouteTable, routeFinale);
                            paquetFinRoutage.setFinRoutage(true);
                            reseau.getSimulateur().ajouterEvenement(new EvenementTraiter("Noeud : " + (paquetFinRoutage.getDestinataireSuivant()).getAdresse()
                                    + " reçoit Paquet de routage n° " + paquetFinRoutage.identifiant, debit, (paquetFinRoutage.getDestinataireSuivant()), paquetFinRoutage));
                            //(paquetFinRoutage.getDestinataireSuivant()).traiterPaquet(paquet);
                        }
                    }
                }
                //TYPE PAQUET REPLY
                else if (paquet instanceof PaquetReply) {
                    PaquetReply paquetReply = (PaquetReply) paquet;
                    this.evenementEnvoi = true;
                    //SI ON NE CONNAIT PAS LA SUITE DE LA ROUTE
                    if (!tableRoutage.contientRoute(noeudDestinataire)) {
                        List<Noeud> atteignables = reseau.noeudsAtteignables(this);
                        for (Noeud noeud : atteignables) {
                            Route routeExploration = new Route(paquetReply.getRouteExploration());
                            PaquetRoutage paquetReplySuivant = new PaquetRoutage(reseau.nouveauPaquet(), paquetReply.getEmetteur(), paquet.getDestinataire(), routeExploration);
                            reseau.getSimulateur().ajouterEvenement(new EvenementTraiter("Noeud : " + noeud.getAdresse()
                                    + " reçoit Paquet de Reply n° " + paquetReplySuivant.identifiant, debit, noeud, paquetReplySuivant));
                            //noeud.traiterPaquet(paquetReplySuivant);
                        }
                    }
                    //SI ON CONNAIT LA SUITE DE LA ROUTE
                    else {
                        //ON INTERROGE LA TABLE DE ROUTAGE POUR POURSUIVRE
                        Route demandeRouteTable = tableRoutage.getRoute(noeudDestinataire);
                        PaquetDonnee paquetFinReply = new PaquetDonnee(reseau.nouveauPaquet(), paquet.getEmetteur(), paquet.getDestinataire(), demandeRouteTable, paquetReply.getReponseRoute());
                        paquetFinReply.setFinReply(true);
                        reseau.getSimulateur().ajouterEvenement(new EvenementTraiter("Noeud : " + (paquetFinReply.getDestinataireSuivant()).getAdresse()
                                + " reçoit Paquet de Reply n° " + paquetFinReply.identifiant, debit, (paquetFinReply.getDestinataireSuivant()), paquetFinReply));
                        //(paquetFinReply.getDestinataireSuivant()).traiterPaquet(paquetFinReply);
                    }

                }
            }
        }
    }

    /**
     * Permet de traiter un paquet en réception
     * @param paquetRecu Paquet reçu
     */
    public void traiterPaquet(Paquet paquetRecu) {
        if (this.actif) {
            //TYPE PAQUET DONNEE
            if (paquetRecu instanceof PaquetDonnee) {
                PaquetDonnee paquetDonnee = (PaquetDonnee) paquetRecu;
                //SI C'EST POUR MOI
                if (paquetDonnee.getDestinataire() == this) {
                    //SI C'EST UN MESSAGE
                    if (paquetDonnee.getFinRoutage() == false) {
                        paquetDonnee.lireDonnee();
                    }
                    //SI C'EST UNE FIN DE ROUTAGE
                    else {
                        //SI ON CONNAIT LA ROUTE POUR RENVOYER LA REPONSE DE ROUTE
                        if (tableRoutage.contientRoute(paquetDonnee.getDestinataire())) {
                            PaquetDonnee paquetDonneeRetour = new PaquetDonnee(reseau.nouveauPaquet(), this, paquetDonnee.getEmetteur(), this.tableRoutage.getRoute(paquetDonnee.getDestinataire()),
                                    paquetDonnee.getRetourRoute());
                            reseau.getSimulateur().ajouterEvenement(new EvenementEnvoyer("Noeud : " + this.getAdresse()
                                    + " envoit Paquet n° " + paquetDonneeRetour.identifiant, debit, this, paquetDonnee.getEmetteur(), paquetDonneeRetour));
                            //this.envoyerPaquet(paquetDonneeRetour, paquetDonnee.getEmetteur());
                        }
                        //SI ON NE CONNAIT PAS LA ROUTE POUR ENVOYER LA REPONSE DE ROUTE
                        else {
                            PaquetReply paquetReply = new PaquetReply(reseau.nouveauPaquet(), this, paquetDonnee.getEmetteur(), paquetDonnee.getRetourRoute());
                            reseau.getSimulateur().ajouterEvenement(new EvenementEnvoyer("Noeud : " + this.getAdresse()
                                    + " envoit Paquet n° " + paquetReply.identifiant, debit, this, paquetDonnee.getEmetteur(), paquetReply));
                            //this.envoyerPaquet(paquetReply, paquetDonnee.getEmetteur());
                        }
                    }
                }
                //SI C'EST PAS POUR MOI
                else if (paquetDonnee.getDestinataireSuivant() != this) {
                    if (!paquetDonnee.getFinRoutage()) {
                        paquetDonnee.actionPaquet(this);
                    }
                    reseau.getSimulateur().ajouterEvenement(new EvenementEnvoyer("Noeud : " + this.getAdresse()
                            + " envoit Paquet n° " + paquetDonnee.identifiant, debit, this, paquetDonnee.getDestinataireSuivant(), paquetDonnee));
                    //this.envoyerPaquet(paquetDonnee, paquetDonnee.getDestinataireSuivant());
                }
                //TYPE PAQUET ROUTAGE
            } else if (paquetRecu instanceof PaquetRoutage) {
                PaquetRoutage paquetRoutage = (PaquetRoutage) paquetRecu;
                //SI C'EST MOI QU'IL RECHERCHE
                if (paquetRoutage.getDestinataire() == this) {
                    //SI ON CONNAIT LA ROUTE POUR RENVOYER LA REPONSE DE ROUTE
                    if (this.tableRoutage.getRoute(paquetRoutage.getDestinataire()) != null) {
                        PaquetDonnee paquetDonnee = new PaquetDonnee(reseau.nouveauPaquet(), this, paquetRoutage.getEmetteur(), this.tableRoutage.getRoute(paquetRoutage.getDestinataire()),
                                paquetRoutage.getRouteExploration());
                        reseau.getSimulateur().ajouterEvenement(new EvenementEnvoyer("Noeud : " + this.getAdresse()
                                + " envoit Paquet n° " + paquetDonnee.identifiant, debit, this, paquetRoutage.getEmetteur(), paquetDonnee));
                        //this.envoyerPaquet(paquetDonnee, paquetRoutage.getEmetteur());
                    }
                    //SI ON NE CONNAIT PAS LA ROUTE POUR ENVOYER LA REPONSE DE ROUTE
                    else {
                        PaquetReply paquetReply = new PaquetReply(reseau.nouveauPaquet(), this, paquetRoutage.getEmetteur(), paquetRoutage.getRouteExploration());
                        reseau.getSimulateur().ajouterEvenement(new EvenementEnvoyer("Noeud : " + this.getAdresse()
                                + " envoit Paquet n° " + paquetReply.identifiant, debit, this, paquetRoutage.getEmetteur(), paquetReply));
                        //this.envoyerPaquet(paquetReply, paquetRoutage.getEmetteur());
                    }
                }
                //SI C'EST PAS MOI QU'IL RECHERCHE, ON TRANSMET
                else if (paquetRoutage.getDestinataire() != this) {
                    paquetRoutage.actionPaquet(this);
                    reseau.getSimulateur().ajouterEvenement(new EvenementEnvoyer("Noeud : " + this.getAdresse()
                            + " envoit Paquet n° " + paquetRoutage.identifiant, debit, this, paquetRoutage.getDestinataire(), paquetRoutage));
                    //this.envoyerPaquet(paquetRoutage, paquetRoutage.getDestinataire());
                }
                //TYPE PAQUET REPLY
            } else if (paquetRecu instanceof PaquetReply) {
                PaquetReply paquetReply = (PaquetReply) paquetRecu;
                //SI C'EST UNE REPONSE DE ROUTE QUE J'AI DEMANDE
                if (paquetRecu.getDestinataire() == this) {
                    this.tableRoutage.ajouterRoute(paquetReply.getReponseRoute());
                    this.purgerListeAttente();
                }
                //SI C'EST PAS LA MIENNE
                else {
                    paquetReply.actionPaquet(this);
                    reseau.getSimulateur().ajouterEvenement(new EvenementEnvoyer("Noeud : " + this.getAdresse()
                            + " envoit Paquet n° " + paquetReply.identifiant, debit, this, paquetReply.getDestinataire(), paquetReply));
                    //this.envoyerPaquet(paquetReply, paquetReply.getDestinataire());
                }
            }
        }
    }

    public Noeud getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(Noeud destinataire) {
        this.destinataire = destinataire;
    }

    public boolean estAtteignable(Noeud noeudCible) {
        return this.reseau.distanceNoeuds(this, noeudCible) <= this.getPortee();
    }

	public void setReseau(Reseau reseau) {
		this.reseau = reseau;
		
	}

	public void setTableRoutage(TableRoutage tableRoutage) {
		this.tableRoutage = tableRoutage;
		
	}
}


