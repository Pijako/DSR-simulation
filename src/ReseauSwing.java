import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by julienmoniot on 04/06/2016.
 *
 * @author Julien Moniot
 */
public class ReseauSwing extends JPanel {

    private List<ControleurNoeud> noeudsPresents;

    /** Constructeur de la vue du reseau
     * @param noeudsPresents Controleurs de noeuds
     */
    public ReseauSwing(List<ControleurNoeud> noeudsPresents) {
        this.noeudsPresents = noeudsPresents;
    }


    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (ControleurNoeud noeud : noeudsPresents) {
            int x = noeud.getNoeudModele().caracteristiquePhysique.getPositionCourante().getAbscisse();
            int y = noeud.getNoeudModele().caracteristiquePhysique.getPositionCourante().getOrdonnee();
            int portee = noeud.getNoeudModele().getPortee();
            String texteNoeud = Integer.toString(noeud.getNumeroNoeud());
            g.setColor(Color.BLACK);
            g.fillOval(x, y, 20, 20);
            g.drawString(texteNoeud, x, y);
            g.setColor(Color.GREEN);
            g.drawOval(x - portee / 2 + 10, y - portee / 2 + 10, portee, portee);
        }
    }

}
