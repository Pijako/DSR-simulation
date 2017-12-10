import java.util.Random;

/**
 * Created by julienmoniot on 19/05/2016.
 *
 * @author Julien Moniot et Anthony Rohr
 */
public class Point {
	
	protected int abscisse;
	protected int ordonnee;

	/** Constructeur d'un point
	 * @param x abscisse du point
	 * @param y ordonnee du point
	 */
	public Point(int x, int y){
		this.abscisse = x;
		this.ordonnee = y;
	}

	/** Obtenir l'abscisse d'un point
	 * @return l'abscisse du point
	 */
	public int getAbscisse(){
		return this.abscisse;
	}

	/** Modifier l'abscisse d'un point
	 * @param x abscisse du point a changer
	 */
	public void setAbscisse(int x){
		this.abscisse = x;
	}

	/**
	 * Obtenir l'ordonnee d'un point
	 *
	 * @return l'ordonnee d'un point
	 */
	public int getOrdonnee() {
		return this.ordonnee;
	}

	/** Modifier l'ordonnee d'un point
	 * @param y ordonnee du point a changer
	 */
	public void setOrdonnee(int y){
		this.ordonnee = y;
	}

	/** Permet de faire avancer un point en connaissant sa position initiale
	 *  et sa position finale et en ajoutant une variation aleatoire a ce
	 * 	deplacement.
	 * @param distance la distance a parcourir de la distance totale
	 * @param destinationFinale le point finale
	 * @return le nouveau de point qui aura progresser
	 */
	public Point progression(int distance,Point destinationFinale){
		if((this.getAbscisse() == destinationFinale.getAbscisse()) && (this.getOrdonnee() == destinationFinale.getOrdonnee())){
			return destinationFinale;
		} else {
			Random alea = new Random();
			int x_A = this.getAbscisse();
			int y_A = this.getOrdonnee();
			int x_D = destinationFinale.getAbscisse();
			int y_D = destinationFinale.getOrdonnee();
			int C = x_D-x_A;
			int H = y_D-y_A;
			int distX = distance * C;
			int distY = distance * H;
			if((distX > 0) && (distY > 0)){
				return new Point(x_A + distX + alea.nextInt(Math.abs(distX)), y_A + distY + alea.nextInt(Math.abs(distY)));
			} else if ((distX > 0) && (distY < 0)) {
				return new Point(x_A + distX + alea.nextInt(Math.abs(distX)), y_A + distY - alea.nextInt(Math.abs(distY)));
			} else if ((distX < 0 ) && (distY > 0)){
				return new Point(x_A + distX - alea.nextInt(Math.abs(distX)), y_A + distY + alea.nextInt(Math.abs(distY)));
			} else if ((distX < 0) && (distY < 0)) {
				return new Point(x_A + distX - alea.nextInt(Math.abs(distX)), y_A + distY - alea.nextInt(Math.abs(distY)));
			} else {
				return destinationFinale;
			}
		}	
	}

	/** Montre la chaine de caractere d'un point
	 * @return la chaine de caractere d'un point
	 */
	public String toString(){
		return "( " + this.getAbscisse() + " , " + this.getOrdonnee() + " )";
	}
}
