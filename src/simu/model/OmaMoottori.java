package simu.model;

import controller.IKontrolleriMtoV;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;


/**
 * @author juvon
 *  Luokka <code>OmaMoottori</code> sisaltaa simulaation toiminnan ja logiikan, simulaatiotulosten luomisen 
 */

public class OmaMoottori extends Moottori {
	
	/**
	 * Muuttuja <code>saapumisprosessi</code> viite Saapumisprosessiluokkaan
	 */
	private Saapumisprosessi saapumisprosessi;
	/**
	 * Muuttuja <code>kahvilaTodNak</code> ilmaisee asiakkaan todennakoisyyden kahvilaanmenoon
	 */
	private int kahvilaTodNak;
	
	/**
	 * Muuttuja <code>varausTodNak</code> ilmaisee asiakkaan todennakoisyyden huonevaraukseen
	 */
	private int varausTodNak;
	
	/**
	 * Muuttujat <code>rvTulo</code>, <code>kTulo</code>, <code>hTulo</code> , <code>p1Tulo</code> , <code>p2Tulo</code> 
	 *  ilmaisevat asiakkaan tuloajat tiettyihin palvelupisteisiin
	 */
	private double rvTulo, kTulo, hTulo, p1Tulo, p2Tulo;
	
	/**
	 * raportti on Raporttiluokan olio
	 */
	
	static private Raportti raportti = new Raportti();
	/**
	 * Muuttuja <code>tulokset</code> on Raportti-luokka-tyyppinen taulukko, 
	 */
	static private Raportti[] tulokset;
	

	/**
	 * Luo <code>OmaMottori</code> -olion
	 * Luodaan halutut alustukset OmaMoottorin toiminnalle, lisataan palvelupistetaulukkoon uudet viisi palvelupistetta,
	 * joita kaytetaan simuloinnissa
	 * @param kontrolleri
	 * 			periytynyt Moottori-yliluokan parametrillinen konstruktorista
	 */
	public OmaMoottori(IKontrolleriMtoV kontrolleri){ // UUSI

		super(kontrolleri); //UUSI
		
		palvelupisteet = new Palvelupiste[5];
		palvelupisteet[0] = new Palvelupiste(new Normal(17, 7), tapahtumalista, TapahtumanTyyppi.KAHVILASTAPOISTUMINEN);
		palvelupisteet[1] = new Palvelupiste(new Normal(15, 7), tapahtumalista, TapahtumanTyyppi.PALVELUTISKI1POISTUMINEN);
		palvelupisteet[2] = new Palvelupiste(new Normal(10, 5), tapahtumalista, TapahtumanTyyppi.PALVELUTISKI2POISTUMINEN);
		palvelupisteet[3] = new Palvelupiste(new Normal(15, 5), tapahtumalista, TapahtumanTyyppi.HUONEESTAPOISTUMINEN);
		palvelupisteet[4] = new Palvelupiste(new Normal(18, 5), tapahtumalista, TapahtumanTyyppi.RAVINTOLASTAPOISTUMINEN);

		saapumisprosessi = new Saapumisprosessi(new Negexp(15,5), tapahtumalista, TapahtumanTyyppi.ARR1);
	}
	
	/**
	 * Luo ensimmaisen asiakkaan jarjestelmaan 
	 */
	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}
	
	/**
	 * Asettaa kahvilaanmenon todennakoisyyden saadun <code>kahvilanTodennakoisyys</code> -parametrin mukaisesti
	 *@param kahvilanTodennakoisyys
	 *				Asiakkaan todennakoisyys kahvilaanmenoon
	 */
	@Override
	public void setKahvilaTodennakoisyys(int kahvilanTodennakoisyys) {
		// TODO Auto-generated method stub
		kahvilaTodNak = kahvilanTodennakoisyys;
	}

	/**
	 * Asettaa huonevarauksen todennakoisyyden saadun <code>varauksenTodennakoisyys</code> -parametrin mukaisesti
	 *@param varauksenTodennakoisyys
	 *				Asiakkaan todennakoisyys huonevaraukselle
	 */
	@Override
	public void setVarausTodennakoisyys(int varauksenTodennakoisyys) {
		// TODO Auto-generated method stub
		varausTodNak = varauksenTodennakoisyys;
	}
	
	
	/**
	 * Palauttaa asiakkaan kahvilaanmenon todennakoisyyden 
	 * @return asiakkaan kahvilaan menon todennakoisyys
	 */
	public int getKahvilaTodNak() {
		return kahvilaTodNak;
	}

	/**
	 * Palauttaa asiakkaan huonevarauksen todennakoisyyden 
	 * @return asiakkaan huonevarauksen todennakoisyys
	 */
	public int getVarausTodNak() {
		return varausTodNak;
	}

	
	/**
	 * Metodi suoritaTapahtuma on simulaation toiminnan logiikka ja moottori
	 * Luo uusia asiakkaita jarjestelmaan. 
	 * Kuljettaa asiakkaita jarjestelmassa tiettyjen asetettujen ehtojen mukaisesti.
	 * Ehtoihin tarvittavat tiedot asiakkaan ennakkohuonevarauksesta ja siita meneeko asiakas ennen ilmoittautumistiskia, 
	 * kahvilaan metodi saa kutsumalla Asiakas-luokan get-metodeja. 
	 * Asettaa asiakkaille palvelupistekohtaisia tulo- ja lahtoaikoja. 
	 */
	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat
		Asiakas a;
			switch (t.getTyyppi()){
				case ARR1: a = new Asiakas();
				a.setKahvilatodennakoisyys(kahvilaTodNak);
				a.setVaraustodennakoisyys(varausTodNak);
					if (a.getKahvilaanMeno()) {
						kTulo = Kello.getInstance().getAika();
						System.out.println("Kahvilajonon pituus: " + palvelupisteet[0].jononKokoko());
						palvelupisteet[0].lisaaJonoon(a);		
					}else {
						if (!a.getHuoneVaraus()) {
							p1Tulo = Kello.getInstance().getAika();
							palvelupisteet[1].lisaaJonoon(a);
							System.out.println("palvelutiski 1 jono: " + palvelupisteet[1].jononKokoko());
						} else {
							p2Tulo = Kello.getInstance().getAika();
							palvelupisteet[2].lisaaJonoon(a);
							System.out.println("palvelutiski 2 jono: " + palvelupisteet[2].jononKokoko());
						}	
					}
					saapumisprosessi.generoiSeuraava();
					kontrolleri.visualisoiAsiakas(); // UUSI
					break;
				case KAHVILASTAPOISTUMINEN:
					a = palvelupisteet[0].otaJonosta();
					a.setKahvilaanTuloAika(kTulo);
					a.setKahvilastaPoistumisaika(Kello.getInstance().getAika());
					if (!a.getHuoneVaraus()) {
						p1Tulo = Kello.getInstance().getAika();
						palvelupisteet[1].lisaaJonoon(a);
						System.out.println("palvelutiski1 jono: " + palvelupisteet[1].jononKokoko());
					}else{	
						p2Tulo = Kello.getInstance().getAika();
						palvelupisteet[2].lisaaJonoon(a);
						System.out.println("palvelutiski2 jono: " + palvelupisteet[2].jononKokoko());
					}
					break;
				case PALVELUTISKI1POISTUMINEN: a = palvelupisteet[1].otaJonosta();
				a.setPlvlTiski1TuloAika(p1Tulo);
				a.setPlvltiski1Poistumisaika(Kello.getInstance().getAika());
					hTulo = Kello.getInstance().getAika();
					palvelupisteet[3].lisaaJonoon(a);
					System.out.println("Lisätään huoneeseen: huoneen jono: " + palvelupisteet[3].jononKokoko());
					break;
				case PALVELUTISKI2POISTUMINEN: a = palvelupisteet[2].otaJonosta();
				a.setPlvlTiski2TuloAika(p2Tulo);
				a.setPlvltiski2Poistumisaika(Kello.getInstance().getAika());
					palvelupisteet[3].lisaaJonoon(a);
					System.out.println("Lisätään huoneeseen, huoneen jono: " + palvelupisteet[3].jononKokoko());
					hTulo = Kello.getInstance().getAika();
					break;
				case HUONEESTAPOISTUMINEN: a = palvelupisteet[3].otaJonosta();
				a.setHuoneeseenTuloAika(hTulo);
				a.setHuoneestaPoistumisaika(Kello.getInstance().getAika());
					palvelupisteet[4].lisaaJonoon(a);
					System.out.println("ravintolan jono: " + palvelupisteet[4].jononKokoko());
					rvTulo = Kello.getInstance().getAika();
					break;
				case RAVINTOLASTAPOISTUMINEN:
					a = palvelupisteet[4].otaJonosta();
					a.setRavintolaanTuloAika(rvTulo);
					a.setRavintolastaPoistumisAika(Kello.getInstance().getAika());
					a.setPoistumisaika(Kello.getInstance().getAika());
					
					a.raportti();		
			}
		}
		
	
	/**
	 * Metodi tulokset() kutsuu Raportti-luokan metodeja, jotka luovat tietokantaan simuloinnin tuloksia
	 */
	@Override
	protected void tulokset() {
		
	     if(raportti.createAsiakasMaarat()) {
             System.out.println("Asiakasmäärät lisätty tietokantaan");
         } else {
             System.out.println("Asiakasmäärien lisääminen ei onnistunut");
         }		
		
		
        if(raportti.createTulokset()) {
              System.out.println("Tulokset lisätty tietokantaan");
          } else {
              System.out.println("Tulosten lisääminen ei onnistunut");
          }		
		
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		// UUTTA graafista
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());	
		
		listaaTulokset();
	}
	
	
	
	/**
	 * Kutsuu Raportti-luokan metodia getKaikkiTuloksetSamastaSimulaatiokerrasta (),
	 * joka lukee simuloinnin tulokset tietokannasta.
	 * Seuraavaksi kay lapi saamansa taulukon tuloksista ja 
	 * tulostaa konsoliin simulointituokset
	 */
	public static void listaaTulokset() {
		tulokset = raportti.getKaikkiTuloksetSamastaSimulaatiokerrasta ();
	 	for (Raportti r : tulokset) {
			System.out.println("Databasesta luetut tiedot : " + "asiakasmäärätulokset simulointikerrasta : " + r.getAsiakasMaaraSimulointi() + ", tulokset simulointikerrasta: " + r.getTuloksetSimulointi()  + "\n " + 
					"k asiakas " +  r.getKahvilaAsiakkaat() + ",k lapimeno " + r.getKahvilaKeskimLapimenoaika() + ", k jono " + r.getKahvilanKeskimJononPituus() + "\n p2 asiakas" +
					r.getPlvlpiste2Asiakkaat() + ", p2  lapimeno "+ r.getPlvltiski2Lapimenoaika() + ", p2 jono " + r.getKPlvltiski2KeskimJononPituus() + "\n p1 asiakas" +
					r.getPlvlpiste1Asiakkaat() + ", p1 lapimeno " + r.getPlvltiski1KeskimLapimenoaika() + ", p1 jono  " + r.getKPlvltiski1KeskimJononPituus() + "\n h asiakas" +
					r.getHuoneAsiakkaat() + ", h lapimeno " + r.getHuoneKeskimLapimenoaika() + ",h jono " + r.getHuoneenKeskimJononPituus() + "\n r asiakas" +
					r.getRavintolaAsiakkaat() + ", r lapimeno " + r.getRavintolaKeskimLapimenoaika() + ", r jono " + r.getRavintolanKeskimJononPituus()
			);
		}
	}

}
