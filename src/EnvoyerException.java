/**
 * Created by julienmoniot on 02/06/2016.
 *
 * @author Julien Moniot
 */
public class EnvoyerException extends RuntimeException {

    /**
     * Exception levee en cas de mauvaise saisie de noeud destinataire
     *
     * @param message Message initie
     */
    public EnvoyerException(String message) {
        super(message);
    }
}
