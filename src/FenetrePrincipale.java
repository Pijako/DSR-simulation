import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by julienmoniot on 25/05/2016.
 *
 * @author Julien Moniot
 */
public class FenetrePrincipale extends JFrame{

    private JPanel conteneur = new JPanel();
    private JScrollPane panneauCommandes;
    private ReseauSwing panneauReseau;


    /**
     * Constructeur de la fenetre principale de l'application. Construit la vue et le controleur du reseau.
     */
    public FenetrePrincipale() {
        this.setTitle("Réseau mobile de communication");
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        List<ControleurNoeud> noeudsPresents = new ArrayList<ControleurNoeud>();
        this.panneauReseau = new ReseauSwing(noeudsPresents);
        this.panneauCommandes = new JScrollPane(new ControleurReseau(panneauReseau, noeudsPresents));

        conteneur.setLayout(new BorderLayout());
        conteneur.add(this.panneauReseau, BorderLayout.CENTER);
        conteneur.add(this.panneauCommandes, BorderLayout.EAST);

        this.setContentPane(this.conteneur);

        this.setVisible(true);
    }
}