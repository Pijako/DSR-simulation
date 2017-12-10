import java.util.Random;

/**
 * Created by julienmoniot on 19/05/2016.
 *
 * @author Julien Moniot et Anthony Rohr
 */
public class RandomWaypoint extends RandomWalk {


	private int compteurAttente;

	/** Constructeur Random Waypoint
	 */
	public RandomWaypoint(int largeur, int hauteur) {
		super(largeur,hauteur);
		Random alea = new Random();
		this.compteurAttente = 0;
	}

	/** Permet au point d'avancer pour un mode deplacement Random Waypoint
	 * @param duree duree de deplacement du noeud
	 * @param vitesse la vitesse de deplacement du noeud
	 * @param pointDepart le point de depart du noeud
	 * @param destinationFinale le point de destination du noeud
	 * @return le point qui se sera deplacer
	 */
	@Override
	public Point avancer(int duree, int vitesse,Point pointDepart, Point destinationFinale) {
		if (this.compteurAttente == 3) {
			this.compteurAttente = 0;
			return super.avancer(duree, vitesse, pointDepart, destinationFinale);
		}
		if ((pointDepart.getAbscisse() == destinationFinale.getAbscisse()) && (pointDepart.getOrdonnee() == destinationFinale.getOrdonnee())) {
			this.compteurAttente++;
			return new Point(destinationFinale.getAbscisse(), destinationFinale.getOrdonnee());
		} else {
			return super.avancer(duree, vitesse, pointDepart, destinationFinale);
		}
	}
}
