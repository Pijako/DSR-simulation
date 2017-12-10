import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandrepidutti on 04/06/2016.
 *
 * @author Alexandre Pidutti
 */

public class Simulateur {
	
	private List<Evenement> evenements;
	private int temps;

	public Simulateur() {
		this.evenements = new ArrayList<Evenement>();
		this.temps = 0;
	}

	/**Ajoute un evenement
	 * @param evenement
	 */
	public void ajouterEvenement(Evenement evenement) {
		evenement.setDate(evenement.getDuree() + this.temps);
		this.evenements.add(evenement);
	}

	/**Donne le temps
	 * @return
	 */
	public int getTemps() {
		return this.temps;
	}

	/**Execute un evenement
	 * @param tempsPasse
	 */
	public void executerEvenements(int tempsPasse) {
		this.temps = this.temps + tempsPasse;
		System.out.println("Temps Ecoulé : " + temps);
		for (int i = 0; i < this.evenements.size(); i++) {
			if ((this.evenements.get(i).getDate() < this.temps) && (this.evenements.get(i).getDate() >= this.temps - tempsPasse)) {
				System.out.println("J'exécute l'évenement " + this.evenements.get(i).getNom());
				this.evenements.get(i).seProduire();
			}
		}
	}
}

