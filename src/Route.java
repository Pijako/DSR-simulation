import java.util.ArrayList;

/**
 * Created by thibaultpichel on 06/06/2016.
 *
 * @author Thibault Pichel
 */

public class Route {

	private ArrayList<Noeud> noeudsRoute;

	//Construire une route vierge
	public Route() {
		this.noeudsRoute = new ArrayList();
	}


	/**Construire une route a partir d'une autre
	 * @param routePrecedente
	 */
	public Route(Route routePrecedente) {
		this.noeudsRoute = new ArrayList(routePrecedente.getNoeuds());
	}

	/**
	 * @return la taille d'une route
	 */
	public int getSize() {
		return this.noeudsRoute.size();
	}

	/**
	 * @param index
	 * @return le noeud en question
	 */
	public Noeud getNoeud(int index) {
		return this.noeudsRoute.get(index);
	}

	/**
	 * @return une route
	 */
	public ArrayList<Noeud> getNoeuds() {
		return this.noeudsRoute;
	}

	/**
	 * @param noeudsRoute
	 */
	public void setNoeudsRoute(ArrayList<Noeud> noeudsRoute) {
		this.noeudsRoute = noeudsRoute;
	}

	/**Ajouter un noeud a la route
	 * @param nouveauNoeud
	 */
	public void ajouterNoeud(Noeud nouveauNoeud) {
		this.noeudsRoute.add(nouveauNoeud);
	}

	/**Retirer un noeud de la route
	 * @param noeud
	 */
	public void supprimerNoeud(Noeud noeud) {
		this.noeudsRoute.remove(noeud);
	}

}
