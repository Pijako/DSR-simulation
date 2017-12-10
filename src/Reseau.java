import java.util.ArrayList;
import java.util.List;

/**
 * Created by thibaultpichel on 24/05/2016.
 *
 * @author Thibault Pichel
 */

public class Reseau {

	private List<Noeud> noeuds;
	private int numeroPaquet = 0;
	private Simulateur simulateur;

	public Reseau() {
		this.noeuds = new ArrayList<>();
		this.simulateur = new Simulateur();
	}

	/**
	 * @return simulateur
	 */
	public Simulateur getSimulateur() {
		return simulateur;
	}

	/**Ajoute un noeud au reseau
	 * @param noeud
	 */
	public void ajouterNoeud(Noeud noeud) {
		this.noeuds.add(noeud);
    }

	/**Supprime un noeud du reseau
	 * @param noeud
	 */
	public void supprimerNoeud(Noeud noeud) {
		this.noeuds.remove(noeud);
	}
	
	/**
	 * @return
	 */
	public int nouveauPaquet(){
		numeroPaquet++;
		return numeroPaquet;
	}

	/**
	 * @param noeud1
	 * @param noeud2
	 * @return la distance entre deux noeuds
	 */
	public double distanceNoeuds(Noeud noeud1, Noeud noeud2) {
		double distance, distanceCarree;

		distanceCarree = Math.pow((noeud2.caracteristiquePhysique.getPositionCourante().getAbscisse() -
				noeud1.caracteristiquePhysique.getPositionCourante().getAbscisse()), 2) +
				Math.pow((noeud1.caracteristiquePhysique.getPositionCourante().getOrdonnee() -
						noeud2.caracteristiquePhysique.getPositionCourante().getOrdonnee()), 2);
		distance = Math.sqrt(distanceCarree);

		return distance;
	}

	/**
	 * @param noeud
	 * @return la liste des noeuds a portee
	 */
	public List<Noeud> noeudsAtteignables (Noeud noeud) {
		List<Noeud> noeudsAtteignables = new ArrayList<>();
		
		for(Noeud n : this.noeuds){
			if ((this.distanceNoeuds(noeud, n) < noeud.getPortee()) && (this.distanceNoeuds(noeud, n) != 0)) {
				noeudsAtteignables.add(n);
			}
		}	
		return noeudsAtteignables;	
		
	}

	/**
	 * @param adresse
	 * @return
	 * @throws DeplacementException
	 */
	public Noeud getNoeud(int adresse) throws DeplacementException {
		boolean present = false;

		for (Noeud n : this.noeuds) {
			if (n.getAdresse() == adresse) {
				return n;
			}
		}
		throw new DeplacementException("Le noeud choisi n'est pas présent");
	}
   
}
