import javax.swing.*;
import java.awt.*;

/**
 * Created by julienmoniot on 02/06/2016.
 *
 * @author Julien Moniot
 */
public class FenetreErreur extends JFrame {

    /**
     * Fenetre ouverte lors de la capturation d'exceptions de deplacement ou d'envoi
     *
     * @param message Message initie
     */
    public FenetreErreur(String message) {
        this.setTitle("Erreur");
        this.setLocationRelativeTo(null);
        this.setSize(300,100);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(new JLabel(message), BorderLayout.CENTER);
        this.setVisible(true);
    }
}
