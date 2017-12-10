import java.util.Random;

/**
 * Created by julienmoniot on 19/05/2016.
 *
 * @author Julien Moniot et Anthony Rohr.
 */
public class RandomWalk extends Deterministe {

	protected int largeur;
	protected int hauteur;

	/**
	 * Constructeur RandomWalk
	 */
	public RandomWalk (int largeur, int hauteur){
		super(largeur,hauteur);
		this.largeur = largeur;
		this.hauteur = hauteur;
	}

	/** Permet au point d'avancer pour un mode deplacement Random Walk
	 * @param duree duree de deplacement du noeud
	 * @param vitesse la vitesse de deplacement du noeud
	 * @param pointDepart le point de depart du noeud
	 * @param destinationFinale le point de destination du noeud
	 * @return le point qui se sera deplacer
	 */
	@Override
	public Point avancer(int duree, int vitesse, Point pointDepart, Point destinationFinale) {
		if ((pointDepart.getAbscisse() == destinationFinale.getAbscisse()) && (pointDepart.getOrdonnee() == destinationFinale.getOrdonnee())) {
			Random alea = new Random();
			int abscisse = alea.nextInt(largeur);
			int ordonnee = alea.nextInt(hauteur);
			destinationFinale.setAbscisse(abscisse);
			destinationFinale.setOrdonnee(ordonnee);
		}
		return super.avancer(duree, vitesse, pointDepart, destinationFinale);
	}
}
