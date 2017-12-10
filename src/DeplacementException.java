/**
 * Created by julienmoniot on 02/06/2016.
 *
 * @author Julien Moniot
 */
public class DeplacementException extends RuntimeException {

    /**
     * Constructeur de l'exception levee en cas de mauvaise saisie de deplacement
     *
     * @param message Message initie
     */
    public DeplacementException(String message) {
        super(message);
    }
}
