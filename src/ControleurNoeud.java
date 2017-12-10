import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by julienmoniot on 31/05/2016.
 *
 * @author Julien Moniot
 */
public class ControleurNoeud extends JPanel {

    private int numeroNoeud;
    private Noeud noeudModele;
    private ControleurReseau controleurReseau;
    private Reseau reseauModele;
    private JButton boutonDeplacement;
    private JTextField texteDeplacement;
    private JButton boutonActivite;
    private JButton boutonSupprimer;
    private JButton boutonEnvoyer;
    private JTextField texteEnvoyer;

    /**
     * Constructeur d'un controleur de noeud. Ce controleur permet de créer le modele d'un noeud et d'interagir avec lui.
     *
     * @param numeroNoeud      Numéro du noeud dans le réseau
     * @param initial          Position initiale du noeud
     * @param portee           Portee du noeud
     * @param debit            Debit du noeud
     * @param deplacement      Mode de déplacement du noeud
     * @param controleurReseau Controleur du reseau
     * @param reseauModele     Modèle du reseau
     */
    public ControleurNoeud(int numeroNoeud, String initial, String portee, String debit,
                           String deplacement, ControleurReseau controleurReseau, Reseau reseauModele) {
        //Apparence du contrôleur du Noeud
        this.controleurReseau = controleurReseau;
        this.numeroNoeud = numeroNoeud;
        this.setMaximumSize(new Dimension(250,70));

        this.setLayout(new GridLayout(4,2));
        this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

        boutonActivite = new JButton("Désactiver");
        boutonActivite.addActionListener(new BoutonDesactiverListener());

        boutonSupprimer = new JButton("Supprimer");
        boutonSupprimer.addActionListener(new BoutonSupprimerListener());

        texteDeplacement = new JTextField(deplacement);
        boutonDeplacement = new JButton("Changer");
        boutonDeplacement.addActionListener(new BoutonDeplacementListener());

        texteEnvoyer = new JTextField("");
        boutonEnvoyer = new JButton("Envoyer");
        boutonEnvoyer.addActionListener(new BoutonEnvoyerListener());

        this.add(new JLabel("NOEUD " + numeroNoeud));
        this.add(new JLabel("P:" + portee + " D:" + debit));
        this.add(boutonActivite);
        this.add(boutonSupprimer);
        this.add(texteDeplacement);
        this.add(boutonDeplacement);
        this.add(texteEnvoyer);
        this.add(boutonEnvoyer);

        //Change le modèle du noeud et du réseau
        Noeud noeudCible = null;
        Point depart = null;
        Point destination = null;
        int vitesse = 0;
        try {
            depart = new Point(Integer.parseInt(initial.split("@")[0]), Integer.parseInt(initial.split("@")[1]));
        } catch (ArrayIndexOutOfBoundsException e) {
            FenetreErreur fenetre = new FenetreErreur("Paramètres invalides pour le point de départ");
        } catch (NumberFormatException e) {
            FenetreErreur fenetre = new FenetreErreur("Le point de départ à des coordonnées entières");
        }
        if (deplacement.startsWith("det")) {
            try {
                destination = new Point(Integer.parseInt(deplacement.split("@")[1]), Integer.parseInt(deplacement.split("@")[2]));
                vitesse = Integer.parseInt(deplacement.split("@")[3]);
                deplacement = "Deterministe";
            } catch (ArrayIndexOutOfBoundsException e) {
                FenetreErreur fenetre = new FenetreErreur("Paramètres invalides pour le mode déterministe");
            } catch (NumberFormatException e) {
                FenetreErreur fenetre = new FenetreErreur("Le point de destination à des coordonnées entières");
            }
        } else if (deplacement.startsWith("pursue")) {
            try {
                noeudCible = reseauModele.getNoeud(Integer.parseInt(deplacement.split("@")[1]));
                deplacement = "Pursue";
            } catch (ArrayIndexOutOfBoundsException e) {
                FenetreErreur fenetre = new FenetreErreur("Paramètres invalides pour le mode pursue");
            } catch (NumberFormatException e) {
                FenetreErreur fenetre = new FenetreErreur("Le noeud à poursuivre est un entier");
            }
        }
        try {
            this.noeudModele = new Noeud(Integer.parseInt(portee), Integer.parseInt(debit), numeroNoeud, depart,
                    deplacement.toLowerCase(), destination, vitesse, 10, noeudCible, reseauModele);
        } catch (NumberFormatException e) {
            FenetreErreur fenetre = new FenetreErreur("Le débit et la portée doivent être des entiers");
        }

        this.reseauModele = reseauModele;
        reseauModele.ajouterNoeud(this.noeudModele);
    }

    /**
     * Recuperer le modèle du noeud
     *
     * @return Le noeud modele
     */
    public Noeud getNoeudModele() {
        return noeudModele;
    }

    /**
     * Recuperer l'adresse du noeud
     * @return adresse du noeud
     */
    public int getNumeroNoeud() {
        return numeroNoeud;
    }

    private void supprimer() {
        controleurReseau.suppressionNoeud(this);
        reseauModele.supprimerNoeud(this.noeudModele);
    }

    /**
     * Le controleur demande au modele du noeud d'avancer pendant un certain temps
     * @param temps temps pendant lequel le noeud doit avancer
     */
    public void avancer(int temps) {
        noeudModele.caracteristiquePhysique.faireAvancer(temps);
    }

    private class BoutonSupprimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            supprimer();
        }
    }

    private class BoutonDesactiverListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (boutonActivite.getText().compareTo("Désactiver") == 0) {
                //Change l'aspect du controleur
                boutonActivite.setText("Activer");

                //Change le modèle du noeud
                noeudModele.setActif(false);
            } else {
                //Change l'aspect du controleur
                boutonActivite.setText("Désactiver");

                //Change le modèle du noeud
                noeudModele.setActif(false);
            }

        }
    }

    private class BoutonDeplacementListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Noeud noeudCible = null;
            Point depart = null;
            Point destination = null;
            int vitesse = 0;
            if (texteDeplacement.getText().startsWith("det")) {
                try {
                    destination = new Point(Integer.parseInt(texteDeplacement.getText().split("@")[1]), Integer.parseInt(texteDeplacement.getText().split("@")[2]));
                    vitesse = Integer.parseInt(texteDeplacement.getText().split("@")[3]);
                    noeudModele.caracteristiquePhysique.setModeDeplacement("deterministe", 900, 755, destination,
                            vitesse, 10, noeudCible);
                } catch (ArrayIndexOutOfBoundsException er) {
                    FenetreErreur fenetre = new FenetreErreur("Paramètres invalides pour le mode déterministe");
                } catch (NumberFormatException err) {
                    FenetreErreur fenetre = new FenetreErreur("Le point de destination à des coordonnées entières");
                }
            } else if (texteDeplacement.getText().endsWith("walk")) {
                noeudModele.caracteristiquePhysique.setModeDeplacement("random walk", 900, 755, destination,
                        vitesse, 10, noeudCible);
            } else if (texteDeplacement.getText().endsWith("waypoint")) {
                noeudModele.caracteristiquePhysique.setModeDeplacement("random walk", 900, 755, destination,
                        vitesse, 10, noeudCible);
            } else if (texteDeplacement.getText().startsWith("pursue")) {
                try {
                    noeudCible = reseauModele.getNoeud(Integer.parseInt(texteDeplacement.getText().split("@")[1]));
                } catch (ArrayIndexOutOfBoundsException er) {
                    FenetreErreur fenetre = new FenetreErreur("Paramètres invalides pour le mode pursue");
                } catch (NumberFormatException err) {
                    FenetreErreur fenetre = new FenetreErreur("Le noeud à poursuivre est un entier");
                }
                noeudModele.caracteristiquePhysique.setModeDeplacement("pursue", 900, 755, destination,
                        vitesse, 10, noeudCible);
            }
        }
    }

    private class BoutonEnvoyerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int numeroDestinataire = Integer.parseInt(texteEnvoyer.getText());
                Noeud destinataire = reseauModele.getNoeud(numeroDestinataire);
                Route entete = new Route();
                PaquetDonnee paquetEnvoi = new PaquetDonnee(reseauModele.nouveauPaquet(), noeudModele, destinataire, entete, new Route());

                reseauModele.getSimulateur().ajouterEvenement(new EvenementEnvoyer("Noeud n° " +
                        noeudModele.getAdresse() + " Envoi Paquet de données n° " + paquetEnvoi.identifiant + " a Noeud n° "
                        + destinataire.getAdresse(), noeudModele.getDebit(), noeudModele, destinataire, paquetEnvoi));
            } catch (EnvoyerException err) {
                FenetreErreur fenetre = new FenetreErreur(err.getMessage());
            } catch (NumberFormatException err) {
                FenetreErreur fenetre = new FenetreErreur("L'adresse d'un noeud doit être un entier");
            }
        }
    }

}
