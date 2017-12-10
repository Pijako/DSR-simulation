/**
 * Created by julienmoniot on 09/06/2016.
 *
 * @author Alexandre Pidutti
 */
public class EvenementTraiter implements Evenement {
    private String nom;
    private Noeud noeud;
    private Paquet paquet;
    private int date;
    private int duree;


    public EvenementTraiter(String nom, int debit, Noeud noeud, Paquet paquet) {
        this.nom = nom;
        this.noeud = noeud;
        this.paquet = paquet;
        this.duree = Math.round(1 / debit);
    }

    public int getDuree() {
        return this.duree;
    }

    public String getNom() {
        return this.nom;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void seProduire() {
        noeud.traiterPaquet(paquet);
    }
}
