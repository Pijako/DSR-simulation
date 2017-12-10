/**
 * Created by julienmoniot on 19/05/2016.
 *
 * @author Julien Moniot et Anthony Rohr
 */
public class Deterministe implements ModeDeplacement {
	 
	protected int largeur;
	protected int hauteur;

	/** Constructeur Deterministe
	 * @param largeur Largeur de l'ecran
	 * @param hauteur hauteur de l'ecran
	 */
	public Deterministe(int largeur, int hauteur){
		this.largeur = largeur;
		this.hauteur = hauteur;
	}

	/** Permet au point d'avancer pendant un certain temps pour un mode deplacement deterministe
	 * @param duree duree de deplacement du noeud
	 * @param vitesse la vitesse de deplacement du noeud
	 * @param pointDepart le point de depart du noeud
	 * @param destinationFinale le point de destination du noeud
	 * @return le point qui se sera deplacer
	 */
	@Override
	public Point avancer(int duree, int vitesse,Point pointDepart, Point destinationFinale) {
		int distance = vitesse * duree;
		int x_A = pointDepart.getAbscisse();
		int y_A = pointDepart.getOrdonnee();
		int x_D = destinationFinale.getAbscisse();
		int y_D = destinationFinale.getOrdonnee();
		int C = x_D-x_A;
		int H = y_D-y_A;
		int D = (int) Math.floor(Math.sqrt(Math.pow(x_D-x_A,2) + Math.pow(y_D-y_A,2)));
		int x_avance = (int)Math.floor((double)distance*C/D);
		int y_avance = (int)Math.floor((double)distance*H/D);
		if((Math.abs(x_D - x_A)-  Math.abs(x_avance) <= 0) && (Math.abs(y_D - y_A)- Math.abs(y_avance) <= 0)){
			return new Point(x_D,y_D);
		}else if(Math.abs(x_D - x_A)- x_avance <= 0){
			return new Point(x_D,y_A + y_avance);
		}else if(Math.abs(y_D - y_A)- y_avance <= 0){
			return new Point(x_A + x_avance,y_D);
		}else{
			return new Point(x_A + x_avance, y_A + y_avance );
		}
	}

}
