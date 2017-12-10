/**
 * Created by thibaultpichel on 24/05/2016.
 *
 * @author Thibault Pichel
 */
public class Paquet {

	protected int identifiant;
	protected Noeud destinataire;
	private Noeud emetteur;

	public Paquet(int identifiant, Noeud emetteur, Noeud destinataire) {

		this.identifiant = identifiant;
		this.emetteur = emetteur;
		this.destinataire = destinataire;

	}

	public Noeud getEmetteur() {
		return this.emetteur;
	}

	public Noeud getDestinataire() {
		return this.destinataire;
	}
}


