import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by julienmoniot on 31/05/2016.
 *
 * @author Julien Moniot
 */
public class ControleurReseau extends JPanel {

    private int numeroNoeud;
    private List<ControleurNoeud> noeudsPresents;
    private Reseau reseauModele;
    private ReseauSwing reseauVue;
    private JTextField textePosition;
    private JTextField textePortee;
    private JTextField texteDebit;
    private JTextField texteDeplacement;
    private int temps;

    /**
     * Constructeur d'un controleur de reseau. Permet de controler le modele du reseau et d'initialiser des controleurs de noeuds.
     *
     * @param reseauVue      Vue du reseau
     * @param noeudsPresents Controleurs de reseau presents dans le controleur de reseau
     */
    public ControleurReseau(ReseauSwing reseauVue, List<ControleurNoeud> noeudsPresents) {
        this.temps = 1;
        numeroNoeud = 0;
        this.noeudsPresents = noeudsPresents;
        this.reseauVue = reseauVue;
        this.reseauModele = new Reseau();

        JPanel valider = new JPanel();
        valider.setLayout(new BoxLayout(valider, BoxLayout.LINE_AXIS));
        JButton boutonAjouter = new JButton("+");
        valider.add(new JLabel(" Ajouter un noeud   "));
        valider.add(boutonAjouter);
        JButton boutonEvenement = new JButton("T+" + temps);
        valider.add(boutonEvenement);
        boutonAjouter.addActionListener(new BoutonAjouterListener());
        boutonEvenement.addActionListener(new BoutonEvenementListener());

        JPanel position = new JPanel();
        position.setMaximumSize(new Dimension(250,20));
        position.setLayout(new GridLayout(1, 1));
        textePosition = new JTextField("100@100");
        position.add(new JLabel("Position Initiale"));
        position.add(textePosition);

        JPanel deplacement = new JPanel();
        deplacement.setMaximumSize(new Dimension(250,20));
        deplacement.setLayout(new GridLayout(1, 1));
        texteDeplacement = new JTextField("random walk");
        deplacement.add(new JLabel("Déplacement"));
        deplacement.add(texteDeplacement);

        JPanel portee = new JPanel();
        portee.setMaximumSize(new Dimension(250,20));
        portee.setLayout(new GridLayout(1, 1));
        this.textePortee = new JTextField("100");
        portee.add(new JLabel("Portée"));
        portee.add(textePortee);

        JPanel debit = new JPanel();
        debit.setMaximumSize(new Dimension(250,20));
        debit.setLayout(new GridLayout(1, 1));
        this.texteDebit = new JTextField("10");
        debit.add(new JLabel("Débit (Mb/s)"));
        debit.add(texteDebit);

        //On positionne maintenant ces panels en colonne
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.ajoutPanel(valider);
        this.ajoutPanel(position);
        this.ajoutPanel(deplacement);
        this.ajoutPanel(portee);
        this.ajoutPanel(debit);
    }

    private ControleurNoeud creerNoeud() {

        //Ajoute le controleur Noeud au controleur Reseau
        numeroNoeud = numeroNoeud + 1;
        ControleurNoeud noeud = new ControleurNoeud(numeroNoeud, textePosition.getText(), textePortee.getText(),
                texteDebit.getText(), texteDeplacement.getText(), this, reseauModele);
        noeudsPresents.add(noeud);
        this.add(noeud);
        this.revalidate();

        //Ajoute la vue Noeud à la vue réseau
        reseauVue.repaint();

        return noeud;
    }

    private void ajoutPanel(JPanel panel) {
        this.add(panel);
        this.revalidate();
    }

    /**
     * Permet de supprimer un controleur de noeud
     *
     * @param noeud Controleur de noeud a supprimer
     */
    public void suppressionNoeud(ControleurNoeud noeud) {
        //Change l'aspect du controleur
        noeudsPresents.remove(noeudsPresents.indexOf(noeud));
        this.remove(noeud);
        this.revalidate();
        this.repaint();

        //Change la vue du réseau
        reseauVue.repaint();
    }

    private class BoutonAjouterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Change l'aspect du controleur
            ControleurNoeud panelNoeud = creerNoeud();

            //Change le modèle de réseau
            //reseauModele.ajouterNoeud();

        }
    }

    private class BoutonEvenementListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            //Les noeuds avancent
            for (ControleurNoeud noeud : noeudsPresents) {
                noeud.avancer(temps);
            }
            reseauVue.repaint();

            //On exécute les évenements en attente dans le simulateur
            reseauModele.getSimulateur().executerEvenements(temps);
        }
    }

}
