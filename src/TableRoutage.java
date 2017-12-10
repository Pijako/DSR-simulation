import java.util.ArrayList;
import java.util.List;

/**
 * Created by julienmoniot on 19/05/2016.
 *
 * @author Thibault Pichel
 */
public class TableRoutage {

	private List<Route> routesConnues;

	public TableRoutage(List<Route> routes) {
		this.routesConnues = routes;
	}

	/**
	 * @param noeudDestination
	 * @return true si contient la route, false sinon
	 */
	public boolean contientRoute(Noeud noeudDestination) {
		boolean contient = false;
		for (Route r : this.routesConnues) {
			if (r.getNoeuds().contains(noeudDestination)) {
				contient = true;
			}
		}
		return contient;
	}

    /**
     * @param noeudDestination
     * @return une route vers la destination
     */
    public Route getRoute(Noeud noeudDestination) {
		int compteur = 0;
		Route r = routesConnues.get(0);
        while(r.getNoeud(r.getSize()) != noeudDestination && compteur <= routesConnues.size()){
        	compteur++;
			if(compteur == routesConnues.size()+1){
				r = null;
			}
        }
		return r;
    }

    /**
     * @param noeudDestination
     * @param cheminParticulier
	 * @return une route empruntant un chemin specifique
	 */
    public Route getRouteParticulier(Noeud noeudDestination, ArrayList<Noeud> cheminParticulier) {
		int compteur = 0;
		Route r = routesConnues.get(0);
		boolean identique = true;
        while(r.getNoeud(r.getSize()) != noeudDestination){
			if(r.getSize() != cheminParticulier.size()){
        		compteur++;
			}
			else{
				for(int i = 0;i<=cheminParticulier.size();i++){
					if(r.getNoeud(i) != cheminParticulier.get(i)){
						identique = false;
					}
					break;
				}
				if(identique == true){
					return routesConnues.get(compteur);
				}
				else{
					compteur++;
					if(compteur == routesConnues.size()){
						return null;
					}
				}
			}
			
        }
		return r;
    }

    /**Supprimer une route de la table
     * @param route
     */
    public void supprimerRoute(Route route){
		int compteur = 0;
		Route r = routesConnues.get(compteur);
		while(route != r){
				compteur++;
		}
		this.routesConnues.remove(compteur);
    }

    /**Ajouter une route de la table
     * @param route
     */
    public void ajouterRoute(Route route) {
		this.routesConnues.add(route);
    }

}
