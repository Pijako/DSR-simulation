/**
 * Created by thibaultpichel on 24/05/2016.
 *
 * @author Thibault Pichel
 */

public class PaquetRoutage extends Paquet {

	private Route routeExploration;

	public PaquetRoutage(int identifiant, Noeud emetteur, Noeud destinataire, Route routeExploration) {
		super(identifiant, emetteur, destinataire);
		this.routeExploration = routeExploration;
	}

	public void actionPaquet(Noeud noeudActuel){
		this.routeExploration.ajouterNoeud(noeudActuel);
	}

	public Route getRouteExploration(){
		return this.routeExploration;
	}


}
