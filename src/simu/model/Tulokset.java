package simu.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
* Luokan tarkoitus kerätä käyttäjää kiinnostavat surituskykysuureet yhteen
*
* @author Miika Auvinen
*/
public class Tulokset {
	/**
	* Taulukossa näkyvä StringProperty muotoinen id
	*/
	private final StringProperty idNaytettava;
	
	/**
	* Tuloksen id
	*/
	private int id;
	
	/**
	* Tallennettavat suureet myöhempää näyttöä varten
	*/
	private int pp1As, pp2As, kahvAs, ravAs, huoAs;
	
	/**
	* konstruktori asettaa saadut parametrit instanssi muuttujien arvoiksi
	*
	* @param simuId simulointi kerran id
	* @param pp2Asiakkaat Palvelupisteen 2 läpi kulkeneiden asiakkaiden määrä
	* @param pp1Asiakkaat Palvelupisteen 1 läpi kulkeneiden asiakkaiden määrä
	* @param kahvAsiakkaat Kahvilan palvelemat asiakkaat
	* @param ravAsiakkaat Ravintolan palvelemat asiakkaat
	* @param huoneAsiakkaat Huoneissa vierailevien asiakkaiden määrä
	*/
	public Tulokset(int simuId, int pp2Asiakkaat, int pp1Asiakkaat, int kahvAsiakkaat, int ravAsiakkaat
			, int huoneAsiakkaat) {
		idNaytettava = new SimpleStringProperty(Integer.toString(simuId));
		this.id = simuId;
		this.pp1As = pp1Asiakkaat;
		this.pp2As = pp2Asiakkaat;
		this.kahvAs = kahvAsiakkaat;
		this.ravAs = ravAsiakkaat;
		this.huoAs = huoneAsiakkaat;
	}

	/**
	* Getteri muuttujalle idNaytettava
	*
	* @return StringProperty muotoinen id
	*/
	public StringProperty getIdNaytettava() {
		return idNaytettava;
	}

	/**
	* Getteri muuttujalle id
	*
	* @return Simulointikerran id
	*/
	public int getId() {
		return id;
	}

	/**
	* Getteri muuttujalle pp1As
	*
	* @return Palvelupisteen 1 läpi kulkeneiden asiakkaiden määrä
	*/
	public int getPp1As() {
		return pp1As;
	}

	/**
	* Getteri muuttujalle pp2As
	*
	* @return Palvelupisteen 2 läpi kulkeneiden asiakkaiden määrä
	*/
	public int getPp2As() {
		return pp2As;
	}

	/**
	* Getteri muuttujalle KahvAs
	*
	* @return kahvAsiakkaat Kahvilan palvelemat asiakkaat
	*/
	public int getKahvAs() {
		return kahvAs;
	}
	
	/**
	* Getteri muuttujalle ravAs
	*
	* @return ravAsiakkaat Ravintolan palvelemat asiakkaat
	*/
	public int getRavAs() {
		return ravAs;
	}

	/**
	* Getteri muuttujalle HuoAs
	*
	* @return Huoneissa vierailevien asiakkaiden määrä
	*/
	public int getHuoAs() {
		return huoAs;
	}

}
