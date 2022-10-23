package simu.model;

/**
 * @author juvon
 * Interface IRaporttiDao maarittaa, mitka metodit totetuttavan luokan on toteutettava
 */
public interface IRaporttiDao {

	/**
	 * Vie tietokantaan tietoja
	 * @return toteuttava luokka palauttaa true tai false riippuen onnistuuko tulosten tietokantaan vieminen
	 */
	public boolean createAsiakasMaarat();
	/**
	 * Vie tietokantaan tietoja 
	 * @return toteuttava luokka palauttaa true tai false riippuen onnistuuko tulosten tietokantaan vieminen
	 */
	public boolean createTulokset();
	/**
	 * Hakee tietokannasta tietoja
	 * @return toteuttava luokka palauttaa Tulokset-tyyppisen taulukon 
	 */
	public Tulokset [] getTulokset();
	/**
	 *  Hakee tietokannasta tietoja
	 * @return toteuttava luokka palauttaa Tulokset-tyyppisen taulukon 
	 */
	public Raportti[] getKaikkiTuloksetSamastaSimulaatiokerrasta ();

}
