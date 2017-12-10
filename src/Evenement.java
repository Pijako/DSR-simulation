/**
 * Created by alexandrepidutti on 04/06/2016.
 *
 * @author Alexandre Pidutti
 */

public interface Evenement {

	/**
	 * Permet de recuperer le nom d'un evenement
	 *
	 * @return Nom
	 */
	String getNom();

	/**
	 * Permet de recuperer la duree d'un evenement
	 * @return Duree
	 */
	int getDuree();

	/**
	 * Permet de recuperer la date d'un evenement
	 * @return Date
	 */
	int getDate();

	/**
	 * Permet de definir la date d'execution d'un evenement
	 * @param date Date definie
	 */
	void setDate(int date);

	/**
	 * Permet de declencher un evenement
	 */
	void seProduire();
}
