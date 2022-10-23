package simu.model;
import java.util.LinkedList;
import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
/**
 * @author juvon
 * Luokan tarkoituksena ylläpitää palvelupisteiden jonoja, luoda eroavaisuus palvelupisteiden valille ja 
 * laskea eri palvelupisteiden suureita simuloinnin perusteella. 
 */

public class Palvelupiste  {
	
	/**
	 * Asiakas-tyyppinen <code>jono</code> pitaa sisallaan eri palvelupisteiden jonot
	 */
	public LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus
	
	/**
	 *  <code>generator</code> on ContinuousGenerator oliomuuttujan esittely
	 */
	private ContinuousGenerator generator;
	
	/**
	 *  <code>tapahtumalista</code> on Tapahtumalistan oliomuuttujan esittely
	 */
	private Tapahtumalista tapahtumalista;
	/**
	 *  <code>skeduloitavanTapahtumanTyyppi</code> on TapahtumanTyyppi oliomuuttujan esittely
	 */
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;

	//JonoStartegia strategia; //optio: asiakkaiden järjestys
	/**
	 * Muuttuja <code>varattu</code> sisaltaa tiedon, onko palvelupiste varattu
	 */
	private boolean varattu = false;
	
	/**
	 * Luo <code>Palvelupiste</code>-olion, jolle annetaan haluttu jakaumasatunnaislukukugeneraattori, lista tapahtumista
	 * seka tapahtuman tyyppi, ts. palvelupiste
	 * @param generator ilamisee kaytetyn satunnaislukugeneraattorin
	 * @param tapahtumalista sisaltaa Tapahtumalista-olion
	 * @param tyyppi sisaltaa TapahtumanTyyppi -olion
	 */
	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
	}

	/**
	 * Luo tyhjan  <code>Palvelupiste</code>-olion
	 */
	public Palvelupiste () {}
	
	
	/**
	 * Lisaa jonoon asiakas-olion
	 * @param a on asiakas
	 */
	public void lisaaJonoon(Asiakas a){   // Jonon 1. asiakas aina palvelussa	
		jono.add(a);
	}
	
	/**
	 * Ottaa jonosta ensimmaisen asiakkaan, eli palvelussa olleen asiakkaan
	 * @return jonon ensimmaisen asiakkaan
	 */
	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		return jono.poll();
	}
	
	/**
	 * Nayttaa ja palauttaa jonon koon
	 * @return jonon koko
	 */
	public int jononKokoko () {
		return jono.size();
	}

	/**
	 * Asiakkaalle aloitetaan palvelu ja tapahtumantyypin(palvelupisteen) mukaisesti maaritetaan palveluaika.
	 * Tapahtumalistaan lisataan uusi tapahtuma jolle annetaan tietona tapahtuman/palvelupisteen tyyppi ja kauanko
	 * asiakas on palvelussa. 
	 */
	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());
		varattu = true;

		if (skeduloitavanTapahtumanTyyppi == TapahtumanTyyppi.PALVELUTISKI1POISTUMINEN) {
			double palveluaika = generator.sample()+5;
			tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika));
			} else {
				double palveluaika = generator.sample();
				tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika));
			}
	}

	/**
	 * Palauttaa jonon varauksen tilan
	 * @return jonon varauksen tila
	 */
	public boolean onVarattu(){
		return varattu;
	}
	
	/**
	 * Palauttaa vastauksen onko jonossa asiakkaita, mikali jonossa on asiakas, metodi palauttaa arvon true
	 * @return jonon pituuden tila 
	 */
	public boolean onJonossa(){
		return jono.size() != 0;
	}
	
	//--------------------------------------------Kahvila tulokset------------------------
	/**
	 * Laskee kahvilan keskimaaraisen lapimenoajan kutsumalla yleista metodia lapimenoaikojen laskemiseen antaen sille
	 * parametriksi asiakkaiden kahvilaan tulojen ja sieltä lähtemisten ajat sekä kahvila-asiakkaiden lukumaarat. Asiakastiedot haetaan 
	 * Asiakas-luokasta.
	 * Muuntaa vastauksen kahden desimaalin tarkkuuteen
	 * @return kahvilan keskimaarainen lapimenoaika 
	 */
	public String getKahvilaLapimenoaika () {
		//return String.format("%.2f", getLapimenoaika(Asiakas.getKahvilaTulo(), Asiakas.getKahvilaLahto(), Asiakas.getKahvilaAsiakkaat()));
		return String.format("%.2f", getLapimenoaika(Asiakas.getKahvilaTulo(), Asiakas.getKahvilaLahto(), Asiakas.getKahvilaAsiakkaat()));
	}
	
	/**
	 * Laskee kahvilan keskimaaraisen jononpituuden kutsumalla yleista metodia jononpituuden laskemiseen antaen sille
	 * parametriksi asiakkaiden kahvilaan tulojen ja sieltä lähtemisten ajat. Asiakastiedot haetaan 
	 * Asiakas-luokasta.
	 * @return kahvilan keskimaarainen jononpituus
	 */
	public String getKahvilanKeskimJononPituus() {
		//String.format("%.2f",getKeskimJononPituus(Asiakas.getKahvilaTulo(), Asiakas.getKahvilaLahto()));
		return String.format("%.2f", getKeskimJononPituus(Asiakas.getKahvilaTulo(), Asiakas.getKahvilaLahto()));
	}
	
	//----------------------------------palvelutiski1 tulostenlasku ---------------------------------------------
	
	/**
	 * Laskee palvelutiski 1 keskimaaraisen lapimenoajan kutsumalla yleista metodia lapimenoaikojen laskemiseen antaen sille
	 * parametriksi asiakkaiden palvelutiski 1:lle tulojen ja sieltä lähtemisten ajat sekä palvelutiski 1 -asiakkaiden lukumaarat.
	 * Asiakastiedot haetaan Asiakas-luokasta.
	 * Muuntaa vastauksen kahden desimaalin tarkkuuteen
	 * @return palvelutiski1 keskimaarainen lapimenoaika 
	 */
	public String getPlvltiski1Lapimenoaika () {
		return String.format("%.2f", getLapimenoaika(Asiakas.getPlvltiski1tulo(), Asiakas.getPlvltiski1Lahto(), Asiakas.getP1Asiakkaat()));
	}
	
	/**
	 * Laskee palvelutiski 1 keskimaaraisen jononpituuden kutsumalla yleista metodia jononpituuden laskemiseen antaen sille
	 * parametriksi asiakkaiden palvelutiski 1 tulojen ja sieltä lähtemisten ajat. Asiakastiedot haetaan 
	 * Asiakas-luokasta.
	 * @return palvelutiski 1 keskimaarainen jononpituus
	 */
	public String getPlvltiski1KeskimJononPituus() {
		return String.format("%.2f", getKeskimJononPituus(Asiakas.getPlvltiski1tulo(), Asiakas.getPlvltiski1Lahto()));
	}
	
	//---------------------------------palvelutiski2 tulosten lasku--------------------------
	/**
	 * Laskee palvelutiski 2 keskimaaraisen lapimenoajan kutsumalla yleista metodia lapimenoaikojen laskemiseen antaen sille
	 * parametriksi asiakkaiden palvelutiski 2:lle tulojen ja sieltä lähtemisten ajat sekä palvelutiski 2 -asiakkaiden lukumaarat.
	 * Asiakastiedot haetaan Asiakas-luokasta. Muuntaa vastauksen kahden desimaalin tarkkuuteen
	 * @return palvelutiski 2 keskimaarainen lapimenoaika 
	 */
	public String getPlvltiski2Lapimenoaika () {
		
	  return String.format("%.2f", getLapimenoaika(Asiakas.getPlvltiski2tulo(), Asiakas.getPlvltiski2Lahto(), Asiakas.getP2Asiakkaat()));
		//return String.format("%.2f",getLapimenoaika(Asiakas.getPlvltiski2tulo(), Asiakas.getPlvltiski2Lahto(), Asiakas.getP2Asiakkaat()));
	}
	
	/**
	 * Laskee palvelutiski 2 keskimaaraisen jononpituuden kutsumalla yleista metodia jononpituuden laskemiseen antaen sille
	 * parametriksi asiakkaiden palvelutiski 2 tulojen ja sieltä lähtemisten ajat. Asiakastiedot haetaan 
	 * Asiakas-luokasta.
	 * @return palvelutiski 2 keskimaarainen jononpituus
	 */
	public String getPlvltiski2KeskimJononPituus() {
		return String.format("%.2f", getKeskimJononPituus(Asiakas.getPlvltiski2tulo(), Asiakas.getPlvltiski2Lahto()));
	}
	
	//---------------------------------huoneen tulosten lasku----------------------------------

	/**
	 * Laskee huoneen keskimaaraisen lapimenoajan kutsumalla yleista metodia lapimenoaikojen laskemiseen antaen sille
	 * parametriksi asiakkaiden huoneeseen tulojen - ja sieltä lähtemisten ajat sekä huoneen -asiakkaiden lukumaarat.
	 * Asiakastiedot haetaan Asiakas-luokasta. Muuntaa vastauksen kahden desimaalin tarkkuuteen
	 * @return huoneen keskimaarainen lapimenoaika 
	 */
	public String getHuoneLapimenoaika () {
		return String.format("%.2f", getLapimenoaika(Asiakas.getHuoneTulo(), Asiakas.getHuoneLahto(), Asiakas.getHuoneAsiakkaat()));
	}
	
	/**
	 * Laskee huoneen keskimaaraisen jononpituuden kutsumalla yleista metodia jononpituuden laskemiseen antaen sille
	 * parametriksi asiakkaiden huoneeseen tulojen ja sieltä lähtemisten ajat. Asiakastiedot haetaan 
	 * Asiakas-luokasta.
	 * @return huoneen keskimaarainen jononpituus
	 */
	public String getHuoneKeskimJononPituus() {
		return String.format("%.2f", getKeskimJononPituus(Asiakas.getHuoneTulo(), Asiakas.getHuoneLahto()));
	}
	
	//--------------------------------ravintolan tulosten lasku ------------------------------------
	/**
	 * Laskee ravintolan keskimaaraisen lapimenoajan kutsumalla yleista metodia lapimenoaikojen laskemiseen antaen sille
	 * parametriksi asiakkaiden ravintolaan tulojen - ja sieltä lähtemisten ajat sekä ravintolan -asiakkaiden lukumaarat.
	 * Asiakastiedot haetaan Asiakas-luokasta. Muuntaa vastauksen kahden desimaalin tarkkuuteen
	 * @return ravintolan keskimaarainen lapimenoaika 
	 */
	public String getRavintolaLapimenoaika() {
		return String.format("%.2f", getLapimenoaika(Asiakas.getRavintolaTulo(), Asiakas.getRavintolaLahto(), Asiakas.getRavintolaAsiakkaat()));
	}
	
	/**
	 * Laskee ravintolan keskimaaraisen jononpituuden kutsumalla yleista metodia jononpituuden laskemiseen antaen sille
	 * parametriksi asiakkaiden ravintolaan tulojen ja sieltä lähtemisten ajat. Asiakastiedot haetaan 
	 * Asiakas-luokasta.
	 * @return ravintolan keskimaarainen jononpituus
	 */
	public String getRavintolaKeskimJononPituus() {
		return String.format("%.2f", getKeskimJononPituus(Asiakas.getRavintolaTulo(), Asiakas.getRavintolaLahto()));
	}
	
	 //------------------------yleiset metodit tulosten laskemiseen -------------------------------------
	
	/**
	 * Yleinen metodi keskimaaristen lapimenoaikojen laskemiselle. 
	 *  <code>aikojenErotus</code>  on @param lahtoaika vahennettyna @param tuloaika 
	 *  <code>lapimenoaika</code> on <code>aikojenErotus</code> jaettuna @param asiakaslkm
	 * @param tuloaika asiakkaiden yhteenlasketut tuloajat palvelupisteeseen
	 * @param lahtoaika asiakkaiden yhteenlasketut lahtoajat palvelupisteesta
	 * @param asiakaslkm palvelupisteen yhteenlasketut palvellut asiakkaat
	 * @return palvelupisteen keskimaaraisen lapimenoajan 
	 */
	public double getLapimenoaika(double tuloaika, double lahtoaika, int asiakaslkm) {
		double aikojenErotus = (lahtoaika - tuloaika);
		double lapimenoaika = (aikojenErotus / asiakaslkm);
		return lapimenoaika;
	}
	
	/**
	 * Yleinen metodi keskimaaraisen jononpituuden laskemiselle. 
	 *  <code>aikojenErotus</code>  on @param lahtoaika vahennettyna @param tuloaika 
	 *  <code>jononPituus</code> on <code>aikojenErotus</code> jaettuna simulointiajalla
	 * @param tuloAika asiakkaiden yhteenlasketut tuloajat palvelupisteeseen
	 * @param lahtoAika asiakkaiden yhteenlasketut lahtoajat palvelupisteesta
	 * @return palvelupisteen keskimaaraisen jononpituuden 
	 */
	public double getKeskimJononPituus (double tuloAika, double lahtoAika) {
		double aikojenErotus = lahtoAika - tuloAika;
		
		double jononPituus = (aikojenErotus / Kello.getInstance().getAika()); 
		return jononPituus;
	}
	
}
