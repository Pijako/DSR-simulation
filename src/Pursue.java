/**
 * Created by julienmoniot on 19/05/2016.
 *
 * @author Julien Moniot et Anthony Rohr
 */
public class Pursue implements ModeDeplacement {

	protected int pourcentage;

	private Noeud noeudCible;

	/**
	 * Constructeur Pursue
	 */
	public Pursue(Noeud noeudCible, int pourcentage) {
		this.noeudCible = noeudCible;
		this.pourcentage = pourcentage;
	}

	/** Permet au point d'avancer pour un mode deplacement Pursue
	 * @param duree duree de deplacement du noeud
	 * @param vitesse la vitesse de deplacement du noeud
	 * @param pointDepart le point de depart du noeud
	 * @param destinationFinale le point de destination du noeud
	 * @return le point qui se sera deplacer
	 */
	@Override
	public Point avancer(int duree, int vitesse, Point pointDepart, Point destinationFinale) {
		Point progression = pointDepart.progression(this.pourcentage, destinationFinale);//Calcul grâce à la fonction progression
		return new Point((progression.getAbscisse()),progression.getOrdonnee());
	}

	public Noeud getNoeudCible() {
		return noeudCible;
	}
}
