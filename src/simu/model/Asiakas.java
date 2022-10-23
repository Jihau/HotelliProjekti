package simu.model;

import eduni.distributions.Bernoulli;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Trace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)

/**
 * @author juvon
 * Luokan tarkoituksena ylläpitää ja lähettää asiakaskohtaista ja asiakkaisiin liittyvää dataa
 */
public class Asiakas {
	/**
	 * Muuttuja <code>saapumisaika</code> ilmaisee asiakkaan saapumisajan
	 */
	private double saapumisaika;
	/**
	 * Muuttuja <code>poistumisaika</code>ilmaisee asiakkaan poistumisajan
	 */
	private double poistumisaika;
	/**
	 * Muuttuja <code>id</code> ilmaisee asiakaskohtaisen asiakasmumeron
	 */
	private int id;
	
	/**
	 * Muuttuja <code>i</code> pitää huolta asiakasnumeron säilömisestä
	 */
	
	private static int i = 1;
	/**
	 * Muuttujat <code>huonevaraus</code> ja <code>kahvilavaraus</code> ilmaisevat asiakkaan huone-ja kahvilavarauksen tilan
	 */
	public boolean huonevaraus, kahvilavaraus = true;
	
	/**
	 * Muuttuja <code>sumKaikki</code> pitää kirjaa asiakkaiden läpimenoajoista
	 */
	private static long sumKaikki = 0;

//	private int status;
	/**
	 * Muuttujan <code>kahvilatodennakoisyys</code> arvo ilmaisee asiakkaan todennäköisyysprosentin kahvilavaraukselle
	 */
	private int kahvilatodennakoisyys;
	/**
	 * Muuttujan <code>varaustodennakoisyys</code> arvo ilmaisee asiakkaan todennäköisyysprosentin huonevaraukselle
	 */
	private int varaustodennakoisyys;
	
	/**
	 * Muuttujat <code>kahvilatulo</code>, <code>plvltiski1tulo</code>,  <code>plvltiski2tulo</code>,  <code>huoneTulo</code> ja
	 * <code>ravintolaTulo</code> säilövät ja ilmaisevat asiakkaiden saapumisajat kyseiseen palvelupisteeseen
	 */
	static private double kahvilaTulo, plvltiski1tulo, plvltiski2tulo, huoneTulo, ravintolaTulo; 
	/**
	 * Muuttujat <code>kahvilaLahto</code>, <code>plvltiski1Lahto</code>,  <code>plvltiski2Lahto</code>,  <code>huoneLahto</code> ja
	 * <code>ravintolaLahto</code> säilövät ja ilmaisevat asiakkaiden poistumisajat kyseiseen palvelupisteeseen
	 */
	static private double plvltiski1Lahto, plvltiski2Lahto, huoneLahto, ravintolaLahto, kahvilaLahto;
	/**
	 * Muuttujat <code>kahvilassaPalvellutAsiakkaat</code>, <code>plvltiski1llaPalvellutAsiakkaat</code>,  <code>plvltiski2llaPalvellutAsiakkaat</code>,
	 *   <code>huoneenPalvellutAsiakkaat</code> ja <code>ravintolassaPalvellutAsiakkaat</code> 
	 *   ilmaisevat palvelupisteiden täysinpalvellut asiakkaat
	 */
	static int kahvilassaPalvellutAsiakkaat, ravintolassaPalvellutAsiakkaat, plvltiski1llaPalvellutAsiakkaat, 
	plvltiski2llaPalvellutAsiakkaat, huoneenPalvellutAsiakkaat;
	
	

	/**
	 * Luo <code>Asiakas</code> -olion, jossa annetaan asiakkaalle oma id(asiakasnumero) ja saapumisaika, sekä 
	 * tiedot siitä, onko asiakkaalla kahvilavaraus tai/ja huonevaraus
	 */

	public Asiakas(){
		id = i++;
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo "+saapumisaika);
		getKahvilaanMeno();
		getHuoneVaraus();
	}
	
	/**
	 * Palauttaa <code>Asiakas</code> -olion asiakasnumeron(id)
	 * @return Asiakasnumero
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Palauttaa <code>Asiakas</code> -olion poistumsiajan järjestelmästä
	 * @return Asiakkaan poistumisaika
	 */
	public double getPoistumisaika() {
		return poistumisaika;
	}
	
	/**
	 * Asettaa <code>Asiakas</code> -olion poistumisajan
	 * @param poistumisaika 
	 *               Asiakkaan poistumisaika
	 */
	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}
	
	/**
	 * Palauttaa <code>Asiakas</code> -olion saapumisajan järjestelmään
	 * @return Asiakkaan saapumisaika
	 */	
	public double getSaapumisaika() {
		return saapumisaika;
	}
	
	/**
	 * Asettaa <code>Asiakas</code> -olion saapumisajan
	 * @param saapumisaika 
	 *               Asiakkaan saapumisaika
	 */
	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}

	/**
	 * Asettaa <code>Asiakas</code> -olion kahvilaanmenon todennakoisyyden
	 * @param kahvilatodennakoisyys 
	 *               Asiakkaan todennakoisyys kahvilaan menoon
	 */
	public void setKahvilatodennakoisyys(int kahvilatodennakoisyys) {
		this.kahvilatodennakoisyys = kahvilatodennakoisyys;
	}
	
	/**
	 * Palauttaa <code>Asiakas</code> -olion kahvilavarauksen tilan, eli meneekö asiakas kahvilaan vai ei
	 * Vertaa saamaansa kahvilatodennakoisyytta saamaansa satunnaislukuun. Mikali satunnaisluku on isompi, kuin annettu todennakoisyys,
	 * asiakas ei mene kahvilaan
	 * @return Asiakkaan meno kahvilaan
	 */
	public boolean getKahvilaanMeno () {

		Random rnd = new Random();
		int arpa = rnd.nextInt(100-1) + 1;
		if (arpa > kahvilatodennakoisyys) {
			kahvilavaraus = false;
		} else
			kahvilavaraus = true;

		return kahvilavaraus;
	}
	
	
	/**
	 * Asettaa <code>Asiakas</code> -olion huonevarauksen todennakoisyyden
	 * @param varausTodennakoisyys2
	 *                    Asiakkaan todennakoisyys huonevaraukselle
	 */
	public void setVaraustodennakoisyys(int varausTodennakoisyys2) {
		varaustodennakoisyys = varausTodennakoisyys2;
	}
	
	/**
	 * Palauttaa <code>Asiakas</code> -olion huonevarauksen tilan, eli onko asiakkaalla huonevaraus vai ei.
	 * Vertaa saamaansa huonevarauksen todennakoisyytta saamaansa satunnaislukuun. Mikali satunnaisluku on isompi,
	 * kuin annettu todennakoisyys, asiakas ei mene kahvilaan
	 * @return Asiakkaan huonevaraus	
	 */
	public boolean getHuoneVaraus() {
		  //int huonevarauksenTodennakoisyys = 50;
			Random rnd = new Random();
			int chance = rnd.nextInt(100-1) + 1;
			if (chance > varaustodennakoisyys) {
				huonevaraus = false;
			} else
				huonevaraus = true;

		return huonevaraus; 
	}
	
	
	/**
	 * Tulostaa yksittaisen asiakkaan tulokset sekä
	 * tulostaa valiaikatietoja kaikkien asiakkaiden lapimenoajoista 
	 * Summaa kaikkien poistumisaikojen ja kaikkien saapumisaikojen erotuksen ja lopulta jakaa summan asiakkaiden lukumaaralla
	 * 
	 */
	public void raportti() {
		Trace.out(Trace.Level.INFO, "\nAsiakas "+id+ " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui: " +saapumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " poistui: " +poistumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " varaus: " + huonevaraus);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi: " +(poistumisaika-saapumisaika));
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " kahvila: " + kahvilavaraus);
		
		sumKaikki += (poistumisaika-saapumisaika);
		double kokKeskiarvo = sumKaikki/id;
		System.out.println("Kaikkien asiakkaiden läpimenoaikojen keskiarvo tähän asti "+ kokKeskiarvo);
		
	}

	//------------------------Tuloaikojen saanti------------------------------------------------
	
	/**
	 * Palauttaa kaikkien <code>Asiakas</code> -olioiden tähän mennessä yhteenlasketut tuloajat kahvilaan 
	 * @return Asiakkaiden yhteenlasketut tuloajat kahvilaan	
	 */
	public static double getKahvilaTulo() {
		return kahvilaTulo;
	}

	/**
	 * Palauttaa kaikkien <code>Asiakas</code> -olioiden tähän mennessä yhteenlasketut tuloajat palvelutiski 1:lle 
	 * @return Asiakkaiden yhteenlasketut tuloajat palvelutiski 1:lle	
	 */
	public static double getPlvltiski1tulo() {
		return plvltiski1tulo;
	}

	/**
	 * Palauttaa kaikkien <code>Asiakas</code> -olioiden tähän mennessä yhteenlasketut tuloajat palvelutiski 2:lle 
	 * @return Asiakkaiden yhteenlasketut tuloajat palvelutiski 2:lle	
	 */
	public static double getPlvltiski2tulo() {
		return plvltiski2tulo;
	}

	/**
	 * Palauttaa kaikkien <code>Asiakas</code> -olioiden tähän mennessä yhteenlasketut tuloajat huoneeseen
	 * @return Asiakkaiden yhteenlasketut tuloajat huoneeseen	
	 */
	public static double getHuoneTulo() {
		return huoneTulo;
	}
	
	/**
	 * Palauttaa kaikkien <code>Asiakas</code> -olioiden tähän mennessä yhteenlasketut tuloajat ravintolaan
	 * @return Asiakkaiden yhteenlasketut tuloajat ravintolaan	
	 */
	public static double getRavintolaTulo() {
		return ravintolaTulo;
	}

//---------------------Lähtöaikojen saanti----------------------------------------
	
	/**
	 * Palauttaa kaikkien <code>Asiakas</code> -olioiden tähän mennessä yhteenlasketut lahtoajat kahvilasta
	 * @return Asiakkaiden yhteenlasketut lahtoajat kahvilasta	
	 */
	public static double getKahvilaLahto() {
		return kahvilaLahto;
	}

	/**
	 * Palauttaa kaikkien <code>Asiakas</code> -olioiden tähän mennessä yhteenlasketut lahtoajat palvelutiski 1:lta
	 * @return Asiakkaiden yhteenlasketut lahtoajat palvelutiski 1:lta
	 */
	public static double getPlvltiski1Lahto() {
		return plvltiski1Lahto;
	}

	/**
	 * Palauttaa kaikkien <code>Asiakas</code> -olioiden tähän mennessä yhteenlasketut lahtoajat palvelutiski 2:lta
	 * @return Asiakkaiden yhteenlasketut lahtoajat palvelutiski 2:lta
	 */
	public static double getPlvltiski2Lahto() {
		return plvltiski2Lahto;
	}
	
	/**
	 * Palauttaa kaikkien <code>Asiakas</code> -olioiden tähän mennessä yhteenlasketut lahtoajat huoneesta
	 * @return Asiakkaiden yhteenlasketut lahtoajat huoneesta
	 */
	public static double getHuoneLahto() {
		return huoneLahto;
	}
	
	/**
	 * Palauttaa kaikkien <code>Asiakas</code> -olioiden tähän mennessä yhteenlasketut lahtoajat ravintolaasta
	 * @return Asiakkaiden yhteenlasketut lahtoajat ravintolasta
	 */
	public static double getRavintolaLahto() {
		return ravintolaLahto;
	}	

	//------------------------------Asiakkaat--------------------------------------
	

	/**
	 * Palauttaa kaikki kahvilassa kokonaan palvellut <code>Asiakas</code> -oliot
	 * @return Kahvilan palvellut asiakkaat
	 */
	public static int getKahvilaAsiakkaat(){
		return kahvilassaPalvellutAsiakkaat;
	}
	
	/**
	 * Palauttaa kaikki palvelutiski 1:lla kokonaan palvellut <code>Asiakas</code> -oliot
	 * @return Palvelutiski 1 palvellut asiakkaat
	 */
	public static int getP1Asiakkaat(){
		return plvltiski1llaPalvellutAsiakkaat;
	}
	
	/**
	 * Palauttaa kaikki palvelutiski 2:lla kokonaan palvellut <code>Asiakas</code> -oliot
	 * @return Palvelutiski 2 palvellut asiakkaat
	 */
	public static int getP2Asiakkaat(){
		return plvltiski2llaPalvellutAsiakkaat;
	}
	
	/**
	 * Palauttaa kaikki huoneesta kokonaan palvellut <code>Asiakas</code> -oliot
	 * @return Huoneen palvellut asiakkaat
	 */
	public static int getHuoneAsiakkaat(){
		return huoneenPalvellutAsiakkaat;
	}
	
	/**
	 * Palauttaa kaikki ravintolassa kokonaan palvellut <code>Asiakas</code> -oliot
	 * @return Ravintolan palvellut asiakkaat
	 */
	public static int getRavintolaAsiakkaat () {
		return ravintolassaPalvellutAsiakkaat;
	}
	
	//----------------------Tuloaikojen asettaminen----------------------------------------
	
	/**
	 * Lisaa <code>Asiakas</code> -olion <code>kahvilaanTuloAika</code> kahvilatuloaikojen summaan 
	 * @param kahvilaanTuloAika
	 *                    Asiakkaan saapumisaika kahvilaan
	 */
	public void setKahvilaanTuloAika (double kahvilaanTuloAika) {
		this.kahvilaTulo = (kahvilaTulo+kahvilaanTuloAika);
	}
	
	/**
	 * Lisaa saadun  <code>Asiakas</code> -olion <code>plvlTiski1TuloAika</code>  palvelupiste1 tuloaikojen summaan 
	 * @param plvlTiski1TuloAika
	 * 					Asiakkaan saapumisaika palvelutiski1:lle
	 */
	public void setPlvlTiski1TuloAika (double plvlTiski1TuloAika) {
		this.plvltiski1tulo = (plvltiski1tulo + plvlTiski1TuloAika);
	}
	
	/**
	 * Lisaa saadun  <code>Asiakas</code> -olion <code>plvlTiski2TuloAika</code> palvelupiste 2 tuloaikojen summaan 
	 * @param plvlTiski2TuloAika
	 * 					Asiakkaan saapumisaika palvelutiski2:lle
	 */
	public void setPlvlTiski2TuloAika (double plvlTiski2TuloAika) {
		 this.plvltiski2tulo = (plvltiski2tulo + plvlTiski2TuloAika);
	}
	
	
	/**
	 * Lisaa saadun  <code>Asiakas</code> -olion <code>huoneeseenTuloAika</code> huonetuloaikojen summaan 
	 * @param huoneeseenTuloAika
	 * 					Asiakkaan saapumisaika huoneeseen
	 */
	public void setHuoneeseenTuloAika (double huoneeseenTuloAika) {
		 huoneTulo = (huoneTulo + huoneeseenTuloAika);
	}
	
	/**
	 * Lisaa saadun  <code>Asiakas</code> -olion <code>ravintolaanTuloAika</code> ravintolatuloaikojen summaan 
	 * @param ravintolaanTuloAika
	 * 					Asiakkaan tuloaika ravintolaan
	 */
	public void setRavintolaanTuloAika (double ravintolaanTuloAika) {
		this.ravintolaTulo = (ravintolaTulo + ravintolaanTuloAika);
	}
	
	//---------------------Poistumisaikojen asettaminen --------------------------------------
	
	/**
	 * Lisaa <code>Asiakas</code> -olion <code>kahvilastaPoistumisaika</code> kahvilastapoistumisaikojen summaan 
	 * @param kahvilastaPoistumisaika
	 *                    Asiakkaan poistumisaika kahvilasta
	 */
	public void setKahvilastaPoistumisaika(double kahvilastaPoistumisaika) {
		this.kahvilaLahto = (kahvilaLahto + kahvilastaPoistumisaika);
		this.kahvilassaPalvellutAsiakkaat++;
	}
	
	/**
	 * Lisaa <code>Asiakas</code> -olion <code>plvltiski1LahtoAika</code> plvltiski1 lahtoaikojen summaan 
	 * @param plvltiski1LahtoAika
	 *                    Asiakkaan poistumisaika palvelupiste 1:lta
	 */
	public void setPlvltiski1Poistumisaika (double plvltiski1LahtoAika) {
		this.plvltiski1Lahto = (plvltiski1Lahto + plvltiski1LahtoAika);
		this.plvltiski1llaPalvellutAsiakkaat++;
	}
	
	/**
	 * Lisaa <code>Asiakas</code> -olion <code>plvltiski2LahtoAika</code> plvltiski2 lahtoaikojen summaan 
	 * @param plvltiski2LahtoAika
	 *                    Asiakkaan poistumisaika palvelupiste 2:lta
	 */
	public void setPlvltiski2Poistumisaika (double plvltiski2LahtoAika) {
		this.plvltiski2Lahto = (plvltiski2Lahto + plvltiski2LahtoAika);
		this.plvltiski2llaPalvellutAsiakkaat++;
	}

	/**
	 * Lisaa <code>Asiakas</code> -olion <code>huonePoistumisaika</code> huoneen lahtoaikojen summaan 
	 * @param huonePoistumisaika
	 *                    Asiakkaan poistumisaika huoneesta
	 */
	public void setHuoneestaPoistumisaika(double huonePoistumisaika) {
		this.huoneLahto = (huoneLahto + huonePoistumisaika);
		huoneenPalvellutAsiakkaat++;
	}

	/**
	 * Lisaa <code>Asiakas</code> -olion <code>ravintolastaLahtoaika</code> ravintolan lahtoaikojen summaan 
	 * @param ravintolastaLahtoaika
	 *                    Asiakkaan poistumisaika ravintolasta
	 */
	public void setRavintolastaPoistumisAika (double ravintolastaLahtoaika) {
		this.ravintolaLahto = (ravintolaLahto + ravintolastaLahtoaika);
		ravintolassaPalvellutAsiakkaat++;
	}
}