/**
 * Created by alexandrepidutti on 04/06/2016.
 *
 * @author Alexandre Pidutti
 */
public class EvenementEnvoyer implements Evenement {

    private String nom;
    private Noeud noeudEmetteur;
	private Noeud noeudDestinataire;
	private Paquet paquet;
	private int date;
    private int duree;


    public EvenementEnvoyer(String nom, int debit, Noeud noeudEmetteur, Noeud noeudDestinataire, Paquet paquet) {
        this.nom = nom;
        this.noeudDestinataire = noeudDestinataire;
        this.noeudEmetteur = noeudEmetteur;
        this.paquet = paquet;
        this.duree = Math.round(1 / debit);
    }

	public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getNom() {
        return this.nom;
    }

	public void seProduire() {
        noeudEmetteur.envoyerPaquet(paquet, noeudDestinataire);
    }

    public int getDuree() {
        return duree;
    }
}
