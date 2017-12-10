/**
 * Created by thibaultpichel on 24/05/2016.
 *
 * @author Thibault Pichel
 */
public class PaquetReply extends Paquet{

    private Route routeExploration;
    private Route reponseRoute;

    public PaquetReply(int identifiant, Noeud emetteur, Noeud destinataire, Route reponseRoute) {
        super(identifiant, emetteur, destinataire);
        this.reponseRoute = reponseRoute;
    }

    public void actionPaquet(Noeud noeudActuel) {
        this.routeExploration.ajouterNoeud(noeudActuel);
    }

    public Route getRouteExploration() {
        return routeExploration;
    }

    public void setRoute(Route route) {
        this.routeExploration = route;
    }

    public Route getReponseRoute() {
        return reponseRoute;
    }

    public void setReponseRoute(Route route) {
        this.routeExploration = route;
    }

    public Noeud getDestinataire() {
        return this.destinataire;
    }

}