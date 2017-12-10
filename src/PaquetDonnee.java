/**
 * Created by thibaultpichel on 24/05/2016.
 *
 * @author Thibault Pichel
 */

public class PaquetDonnee extends Paquet {

	boolean finRoutage = false;
	boolean finReply = false;
	private Route route;
	private Route retourRoute;

	public PaquetDonnee(int identifiant, Noeud emetteur, Noeud destinataire, Route route, Route reponseRoute) {
		super(identifiant, emetteur, destinataire);
		this.route = route;
		this.retourRoute = reponseRoute;
	}

	public void actionPaquet(Noeud noeud){
		this.route.supprimerNoeud(noeud);
	}

	public void lireDonnee() {

	}

	public Route getRoute() {
		return this.route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Route getRetourRoute() {
		return retourRoute;
	}

	public void setRetourRoute(Route retourRoute) {
		this.retourRoute = retourRoute;
	}

	public boolean getFinRoutage() {
		return finRoutage;
	}

	public void setFinRoutage(boolean finRoutage) {
		this.finRoutage = finRoutage;
	}

	public boolean getFinReply() {
		return finReply;
	}

	public void setFinReply(boolean finReply) {
		this.finReply = finReply;
	}

	public Noeud getDestinataireSuivant() {
		return this.route.getNoeud(0);
	}

}