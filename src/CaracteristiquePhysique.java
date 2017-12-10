import java.util.Random;

/**
 * Created by julienmoniot on 25/05/2016.
 *
 * @author Julien Moniot et Anthony Rohr
 */
public class CaracteristiquePhysique {

    private ModeDeplacement modeDeplacement;

    private Point positionCourante;

    private Point destinationFinale;

    private int vitesseCourante;

    private int limiteVitesse;

	/** Initialiser toute les caracteristiques physique d'un noeud
	 * @param positionCourante la position actuelle d'un point
	 * @param modeDeplacement le mode de deplacement d'un noeud
	 * @param largeur la largeur de la fenetre de l'interface graphique 
	 * @param hauteur la hauteur de la fenetre de l'interface graphique
	 * @param destinationFinale le point de destination
	 * @param vitesse la vitesse désiré du point
	 * @param limiteVitesse la vitesse limite d'un noeud
	 * @param noeudCible le noeud à atteindre
	 */
    public CaracteristiquePhysique(Point positionCourante, String modeDeplacement, int largeur, int hauteur,
                                   Point destinationFinale, int vitesse, int limiteVitesse, Noeud noeudCible) {
        Random alea = new Random();
        this.positionCourante = positionCourante;
    	switch(modeDeplacement){
    		case "deterministe":
                this.vitesseCourante = vitesse;
    			this.destinationFinale = destinationFinale;
    			this.modeDeplacement = new Deterministe(largeur,hauteur);
    			this.limiteVitesse = limiteVitesse;
    			break;
    		case "random walk" :
    			this.limiteVitesse = limiteVitesse; 
    			this.modeDeplacement = new RandomWalk(largeur, hauteur);
                this.vitesseCourante = alea.nextInt(limiteVitesse);
                int abscisse = alea.nextInt(largeur);
    			int ordonnee = alea.nextInt(hauteur);
    			this.destinationFinale = new Point(abscisse, ordonnee);
    			break;
    		case "random waypoint" :
    			this.limiteVitesse = limiteVitesse;
                this.modeDeplacement = new RandomWaypoint(largeur, hauteur);
                this.vitesseCourante = alea.nextInt(limiteVitesse);
                int abscisse1 = alea.nextInt(largeur);
    			int ordonnee1 = alea.nextInt(hauteur);
    			this.destinationFinale = new Point(abscisse1, ordonnee1);
    			break;
    		case "pursue" :
    			this.limiteVitesse = limiteVitesse;
                this.modeDeplacement = new Pursue(noeudCible, 1);
                this.destinationFinale = new Point(noeudCible.caracteristiquePhysique.getPositionCourante().getAbscisse(),
                        noeudCible.caracteristiquePhysique.getPositionCourante().getOrdonnee());
                break;
        }

    }
    
    /** Faire avancer un point
     */
    public void faireAvancer(int duree){
    	setPositionCourante(this.modeDeplacement.avancer(duree, this.vitesseCourante, this.positionCourante, this.destinationFinale));
        if (this.modeDeplacement instanceof Pursue) {
            setDestinationFinale(new Point(((Pursue) this.modeDeplacement).getNoeudCible().caracteristiquePhysique.getPositionCourante().getAbscisse(),
                    ((Pursue) this.modeDeplacement).getNoeudCible().caracteristiquePhysique.getPositionCourante().getOrdonnee()));
        }
    }
	
	/** Obtenir la position courant d'un noeud
	 * @return la position courante
	 */
    public Point getPositionCourante() {
        return positionCourante;
    }

	/** Modifier la position courante
	 * @param positionCourante modifier la position courante
	 */
    public void setPositionCourante(Point positionCourante) {
        this.positionCourante = positionCourante;
    }
	
	/** Obtenir la destination finale
	 * @return la destination finale
	 */
    public Point getDestinationFinale() {
        return destinationFinale;
    }
	
	/** Modifier la destination Finale
	 * @param destinationFinale La nouvelle destinationFinale
	 */
    public void setDestinationFinale(Point destinationFinale) {
        this.destinationFinale = destinationFinale;
    }

	/** Modifier le mode de déplacement d'un noeud
	 * @param modeDeplacement le mode de deplacement d'un noeud
	 * @param largeur la largeur de la fenetre de l'interface graphique 
	 * @param hauteur la hauteur de la fenetre de l'interface graphique
	 * @param destinationFinale le point de destination
	 * @param vitesse la vitesse désiré du point
	 * @param limiteVitesse la vitesse limite d'un noeud
	 * @param noeudCible le noeud à atteindre
	 */
    public void setModeDeplacement(String modeDeplacement, int largeur, int hauteur,
                                   Point destinationFinale, int vitesse, int limiteVitesse, Noeud noeudCible) {
        Random alea = new Random();
        switch (modeDeplacement) {
            case "deterministe":
                this.vitesseCourante = vitesse;
                this.destinationFinale = destinationFinale;
                this.modeDeplacement = new Deterministe(largeur, hauteur);
                this.limiteVitesse = limiteVitesse;
                break;
            case "random walk":
                this.limiteVitesse = limiteVitesse;
                this.modeDeplacement = new RandomWalk(largeur, hauteur);
                this.vitesseCourante = alea.nextInt(limiteVitesse);
                int abscisse = alea.nextInt(largeur);
                int ordonnee = alea.nextInt(hauteur);
                this.destinationFinale = new Point(abscisse, ordonnee);
                break;
            case "random waypoint":
                this.limiteVitesse = limiteVitesse;
                this.modeDeplacement = new RandomWaypoint(largeur, hauteur);
                this.vitesseCourante = alea.nextInt(limiteVitesse);
                int abscisse1 = alea.nextInt(largeur);
                int ordonnee1 = alea.nextInt(hauteur);
                this.destinationFinale = new Point(abscisse1, ordonnee1);
                break;
            case "pursue":
                this.limiteVitesse = limiteVitesse;
                this.modeDeplacement = new Pursue(noeudCible, 1);
                this.destinationFinale = new Point(noeudCible.caracteristiquePhysique.getPositionCourante().getAbscisse(),
                        noeudCible.caracteristiquePhysique.getPositionCourante().getOrdonnee());
                break;
        }
    }
}
