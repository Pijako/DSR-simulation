/**
 * Created by julienmoniot on 19/05/2016.
 *
 * @author Julien Moniot
 */
public interface ModeDeplacement {


    /**
     * Permet au noeud d'avancer
     *
     * @param duree             duree de deplacement du noeud
     * @param vitesse           la vitesse de deplacement du noeud
     * @param pointDepart       le point de depart du noeud
     * @param destinationFinale le point de destination du noeud
     * @return le point qui se sera deplacer
     */
    Point avancer(int duree, int vitesse,Point pointDepart, Point destinationFinale);

}
