package simu.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import simu.framework.Kello;
import simu.framework.Moottori;

/**
 * @author juvon
 * Raportti-luokka toteuttaa IRaporttiDao-rajapinnan
 * Luokan ideana lahettaa - ja hakea tietokannasta tuloksia
 */

public class Raportti implements IRaporttiDao {
	
    int kahvilaAsiakkaat, plvlpiste1Asiakkaat,plvlpiste2Asiakkaat,huoneAsiakkaat,ravintolaAsiakkaat;

    //Asiakkaan aika plvlpisteen jonoon saapumisesta palvelun päättymiseen (Asiakkaan kokema läpimenoaika)
	/**
	 * Muuttuja <code>plvltiski1keskimlapimenoaika</code> ilmaisee palvelutiski1 keskimaaraisen lapimenoajan
	 */
    private String plvltiski1keskimlapimenoaika;
    /**
	 * Muuttuja <code>plvltiski2keskimlapimenoaika</code> ilmaisee palvelutiski2 keskimaaraisen lapimenoajan
	 */
    private String plvltiski2keskimlapimenoaika;
    /**
  	 * Muuttuja <code>kahvilaKeskimLapimenoaika</code> ilmaisee kahvilan keskimaaraisen lapimenoajan
  	 */
    private String kahvilaKeskimLapimenoaika;
    /**
  	 * Muuttuja <code>huoneKeskimLapimenoaika</code> ilmaisee huoneen keskimaaraisen lapimenoajan
  	 */
    private String huoneKeskimLapimenoaika;
    /**
  	 * Muuttuja <code>ravintolaKeskimLapimenoaika</code> ilmaisee ravintolan keskimaaraisen lapimenoajan
  	 */
    private String ravintolaKeskimLapimenoaika;
    //palvelupisteen keskimääräinen jononpituus
    //(palveltava mukana) N = W/T (kokonaisoleskeluaika palvelupisteessä / simulointiajalla)
    /**
  	 * Muuttujat <code>kahvilaKeskimJononPituus</code>, <code>plvltiski1KeskimJononPituus</code>,
  	 * <code>plvltiski2KeskimJononPituus</code>, <code>ravintolanKeskimJononPituus</code> ja 
  	 *  <code>huoneenKeskimJononPituus</code>  ilmaisevat palvelupisteiden keskimaaraiset jonon pituudet
  	 *
  	 */
    private String kahvilaKeskimJononPituus, plvltiski1KeskimJononPituus,
            plvltiski2KeskimJononPituus, ravintolanKeskimJononPituus,huoneenKeskimJononPituus;
    
    /**
  	 * Muuttuja <code>asiakasMaaraSimulointi</code> ilmaisee simulointikerran, josta tietyt asiakasmaaratulosteet
  	 * on saatu
  	 */
    private int asiakasMaaraSimulointi;
    /**
  	 * Muuttuja <code>tuloksetSimulointi</code> ilmaisee simulointikerran, josta tietyt asiakasmaaratulosteet
  	 * on saatu
  	 */
    private int tuloksetSimulointi;
    
    /**
  	 * Staattinen muuttuja <code>conn</code> pitaa sisallaan tietokantayhteyden tilan, jonka 
  	 * tiedon hakee MariaDbConnector -luokasta.
  	 * 
  	 */
	private static Connection conn = datasource.MariaDbConnector.getInstance();

	
	//public Raportti() {}

    //--------------------------------------------------------------------------------------------
    //Asiakkaan aika plvlpisteen jonoon saapumisesta palvelun päättymiseen (Asiakkaan kokema läpimenoaika)
    //Keskimääräinen läpimenoaika.
	/**
	 * Palauttaa poistumsiajan järjestelmästä
	 * @return Asiakkaan poistumisaika
	 */
	
	
    /**
     * Asettaa kahvilan keskimaaraisen lapimenoajan
     * @param kahvilaLapimeno
     * 			kahvilan lapimenoaika
     */
    public void setKahvilaLapimenoaika(String kahvilaLapimeno){
        this.kahvilaKeskimLapimenoaika = kahvilaLapimeno;
    }
 
	/**
	 * Palauttaa kahvilan keskimaaraisen lapimenoajan 
	 * @return Kahvilam lapimenoaika
	 */   
    public String getKahvilaKeskimLapimenoaika() {
        return kahvilaKeskimLapimenoaika;
    }
    
    /**
     * Asettaa plvltiski 1 keskimaaraisen lapimenoajan
     * @param plvltiski1Lapimeno
     * 			ravintolan lapimenoaika
     */
    public void  setPlvltiski1KeskimLapimenoaika(String plvltiski1Lapimeno){
        this.plvltiski1keskimlapimenoaika = plvltiski1Lapimeno;
    }
    
	/**
	 * Palauttaa palvelutiski 1 keskimaaraisen lapimenoajan 
	 * @return Palvelutiski 1 keskimaarainen lapimenoaika
	 */
    public String getPlvltiski1KeskimLapimenoaika() {
        return plvltiski1keskimlapimenoaika;
    }
    
    /**
     * Asettaa plvltiski 2 keskimaaraisen lapimenoajan
     * @param plvltiski2Lapimeno
     * 			plvltiski2 lapimenoaika
     */
    public void setPlvltiski2Lapimenoaika(String plvltiski2Lapimeno){
        this.plvltiski2keskimlapimenoaika = plvltiski2Lapimeno;
    }
    
    /**
	 * Palauttaa palvelutiski 2 keskimaaraisen lapimenoajan 
	 * @return Palvelutiski 2 keskimaarainen lapimenoaika
	 */
    public String getPlvltiski2Lapimenoaika() {
        return plvltiski2keskimlapimenoaika;
    }
    /**
     * Asettaa huoneen keskimaaraisen lapimenoajan
     * @param huoneLapimeno
     * 			huoneen lapimenoaika
     */
    public void setHuoneLapimenoaika(String huoneLapimeno) {
        this.huoneKeskimLapimenoaika = huoneLapimeno;
    }
    
    /**
	 * Palauttaa huoneen keskimaaraisen lapimenoajan 
	 * @return Huoneen keskimaarainen lapimenoaika
	 */
    public String getHuoneKeskimLapimenoaika () {
        return huoneKeskimLapimenoaika;
    }
    
    /**
     * Asettaa ravintolan keskimaaraisen lapimenoajan
     * @param ravintolaLapimeno
     * 			ravintolan lapimenoaika
     */
    public void setRavintolaLapimenoaika(String ravintolaLapimeno){
      this.ravintolaKeskimLapimenoaika = ravintolaLapimeno;
    }
    
    /**
	 * Palauttaa ravintolan keskimaaraisen lapimenoajan 
	 * @return Ravintolan keskimaarainen lapimenoaika
	 */
    public String getRavintolaKeskimLapimenoaika() {
        return ravintolaKeskimLapimenoaika;
    }


    //-----------------------------------------------------------
    //palvelupisteen keskimääräinen jononpituus
    //(palveltava mukana) N = W/T (kokonaisoleskeluaika palvelupisteessä / simulointiajalla)
    
    /**
     * Asettaa kahvilan keskimaaraisen jononpituuden
     * @param kahvilaKeskimJono
     * 			kahvilan keskimaarainen jononpituus
     */
    public void setKahvilanKeskimJononPituus (String kahvilaKeskimJono) {
        this.kahvilaKeskimJononPituus = kahvilaKeskimJono;
    }
    
    /**
  	 * Palauttaa ravintolan keskimaaraisen jononpituuden 
  	 * @return Ravintolan keskimaarainen jononpituus
  	 */
    public String getKahvilanKeskimJononPituus() {
        return this.kahvilaKeskimJononPituus;
    }
    
    /**
     * Asettaa  palvelupiste 1 keskimaaraisen jononpituuden
     * @param plvl1KeskimJono
     * 			palvelupiste 1 keskimaarainen jononpituus
     */
    public void setPlvltiski1KeskimJononPituus (String plvl1KeskimJono) {
        this.plvltiski1KeskimJononPituus = plvl1KeskimJono;
    }
    
    /**
  	 * Palauttaa palvelutiski 1 keskimaaraisen jononpituuden 
  	 * @return palvelutiski 1 keskimaarainen jononpituus
  	 */
    public String getKPlvltiski1KeskimJononPituus() {
        return this.plvltiski1KeskimJononPituus;
    }
    
    /**
     * Asettaa palvelupiste 2 keskimaaraisen jononpituuden
     * @param plvl2KeskimJono
     * 			palvelupiste 2 keskimaarainen jononpituus
     */
    public void setPlvltiski2KeskimJononPituus (String plvl2KeskimJono) {
        this.plvltiski2KeskimJononPituus = plvl2KeskimJono;
    }
    
    /**
  	 * Palauttaa palvelutiski 2 keskimaaraisen jononpituuden 
  	 * @return palvelutiski 2 keskimaarainen jononpituus
  	 */
    public String getKPlvltiski2KeskimJononPituus() {
        return this.plvltiski2KeskimJononPituus;
    }
    
    /**
     * Asettaa huoneen keskimaaraisen jononpituuden
     * @param huoneKeskimJono
     * 			huoneen keskimaarainen jononpituus
     */
    public void setHuoneenKeskimJononPituus (String huoneKeskimJono) {
        this.huoneenKeskimJononPituus = huoneKeskimJono;
    }
    
    /**
  	 * Palauttaa huoneen keskimaaraisen jononpituuden 
  	 * @return huoneen keskimaarainen jononpituus
  	 */
    public String getHuoneenKeskimJononPituus() {
        return this.huoneenKeskimJononPituus;
    }
    
    /**
     * Asettaa ravintolan keskimaaraisen jononpituuden
     * @param ravintolanKeskimJononPituus
     * 			ravintolan keskimaarainen jononpituus
     */
    public void setRavintolaKeskimJononPituus (String ravintolanKeskimJononPituus) {
        this.ravintolanKeskimJononPituus = ravintolanKeskimJononPituus;
    }
    
    /**
  	 * Palauttaa ravintolan keskimaaraisen jononpituuden 
  	 * @return ravintolan keskimaarainen jononpituus
  	 */
    public String getRavintolanKeskimJononPituus() {
        return this.ravintolanKeskimJononPituus;
    }

    //-----------------------------------------------------------------------------------------------
    
    /**
     * Asettaa kahvilan asiakkaiden lukumaaran
     * @param kAsiakkaat
     * 			kahvilan palvelluiden asiakkaiden lukumaara
     */
    public void setKahvilaAsiakkaat (int kAsiakkaat) {
        this.kahvilaAsiakkaat = kAsiakkaat;
    }
    
    /**
     * Asettaa palvelupiste 1 asiakkaiden lukumaaran
     * @param p1Asiakkaat
     * 			palvelupiste 1 palvelluiden asiakkaiden lukumaara
     */
    public void setPlvlpiste1Asiakkaat (int p1Asiakkaat) {
        this.plvlpiste1Asiakkaat = p1Asiakkaat;
    }
    
    /**
     * Asettaa palvelupiste 2 asiakkaiden lukumaaran
     * @param p2Asiakkaat
     * 			palvelupiste 2 palvelluiden asiakkaiden lukumaara
     */
    public void setPlvlpiste2Asiakkaat (int p2Asiakkaat) {
        this.plvlpiste2Asiakkaat = p2Asiakkaat;
    }
    
    /**
     * Asettaa huoneen asiakkaiden lukumaaran
     * @param hAsiakkaat
     * 			huoneen palvelluiden asiakkaiden lukumaara
     */
    public void setHuoneAsiakkaat (int hAsiakkaat) {
        this.huoneAsiakkaat = hAsiakkaat;
    } 
    
    /**
     * Asettaa ravintolan asiakkaiden lukumaaran
     * @param rAsiakkaat
     * 			ravintolan palvelluiden asiakkaiden lukumaara
     */
    public void setRavintolaAsiakkaat (int rAsiakkaat) {
        this.ravintolaAsiakkaat = rAsiakkaat;
    }
    
    /**
  	 * Palauttaa kahvilan palveltujen asiakkaiden lukumaaran 
  	 * @return kahvilan palveltujen asiakkaiden lukumaara
  	 */
    public int getKahvilaAsiakkaat() {
		return kahvilaAsiakkaat;
	}
    
    /**
  	 * Palauttaa palvelupiste 1 palveltujen asiakkaiden lukumaaran 
  	 * @return palvelupiste1 palveltujen asiakkaiden lukumaara
  	 */
	public int getPlvlpiste1Asiakkaat() {
		return plvlpiste1Asiakkaat;
	}
	/**  	 
	 * Palauttaa  palvelupiste 2 palveltujen asiakkaiden lukumaaran 
	 * @return palvelupiste2 palveltujen asiakkaiden lukumaara
	 */
	public int getPlvlpiste2Asiakkaat() {
		return plvlpiste2Asiakkaat;
	}
	/**  	 
	 * Palauttaa  huoneen palveltujen asiakkaiden lukumaaran 
	 * @return huoneen palveltujen asiakkaiden lukumaara
	 */
	public int getHuoneAsiakkaat() {
		return huoneAsiakkaat;
	}
	/**  	 
	 * Palauttaa  ravintolan palveltujen asiakkaiden lukumaaran 
	 * @return ravintolan palveltujen asiakkaiden lukumaara
	 */
	public int getRavintolaAsiakkaat() {
		return ravintolaAsiakkaat;
	}
	
	//-----------------------Simulointikerrat ----------------------------------------------
	
	  /**
     * Asettaa simulointinumeron, josta palvelupisteiden asikaslukumaarat on saatu 
     * @param asiakasMaaraSimulointi
     * 			simulointinumero
     */
	public void setAsiakasMaaraSimulointi(int asiakasMaaraSimulointi) {
		this.asiakasMaaraSimulointi = asiakasMaaraSimulointi;
	}
	
	  /**
     * Asettaa simulointinumeron, josta palvelupisteiden lasketut suureet on tehty 
     * @param tuloksetSimulointi
     * 			simulointinumero
     */
	public void setTuloksetSimulointi(int tuloksetSimulointi) {
		this.tuloksetSimulointi = tuloksetSimulointi;
	}
	
	/**  	 
	 * Palauttaa simulointinumeron, jolloin on saatu palvelupisteiden asiakaslukumaarat
	 * @return asiakasmaarien simulointinumero
	 */
	public int getAsiakasMaaraSimulointi() {
		return asiakasMaaraSimulointi;
	}
	
	/**  	 
	 * Palauttaa simulointinumeron, jonka simulointikerrasta palvelupistriden suureiden laskenta on tehty
	 * @return tulosten simulointinumero
	 */
	public int getTuloksetSimulointi() {
		return tuloksetSimulointi;
	}

	//----------------------Tietokanta-------------------------------------------------------------------------
    
	
	/**
	 * Luo kyselyn tietokantaan. Asettaa tietokannan asiakasmaarat-taulukkoon simuloinnista saatujen 
	 * pavelupistieden palveltujen asiakkaiden lukumaarat. 
	 * Jos kysely ei onnistu syysta tai toisesta, metodi palauttaa false-arvon ja tulostaa tekstin, joka ilmaisee, 
	 * ettei kysely onnistunut
	 */
	public boolean createAsiakasMaarat () {	
		try (PreparedStatement stat = conn.prepareStatement("INSERT INTO asiakasmaarat values(?,?,?,?,?,NULL)")) {
			//Asiakas a;
		//	stat.setInt(1, getKaikkiAsiakkaat());
			//stat.setInt(2, getPalvellutAsiakkaat());
			stat.setInt(1, Asiakas.getP2Asiakkaat());
			stat.setInt(2, Asiakas.getP1Asiakkaat());
			stat.setInt(3, Asiakas.getKahvilaAsiakkaat());
			stat.setInt(4, Asiakas.getHuoneAsiakkaat());
			stat.setInt(5, Asiakas.getRavintolaAsiakkaat());

			stat.executeQuery();
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLExceptions("Create asiakasMaarat ei onnistunut", e);
			return false;
		} 
	}
	
	/**
	 * Luo kyselyn tietokantaan. Asettaa tietokannan tulosteet-taulukkoon simuloinnista saatujen
	 * palvelupistekohtaisten suureiden laskemisten tulokset.
	 * Jos kysely ei onnistu syysta tai toisesta, metodi palauttaa false-arvon ja tulostaa tekstin, joka ilmaisee, 
	 * ettei kysely onnistunut
	 */
	public boolean createTulokset() {
		
		try (PreparedStatement stat = conn.prepareStatement("INSERT INTO tulosteet values(NULL,?,?,?,?,?,?,?,?,?,?)")) {
			
			Palvelupiste p = new Palvelupiste();
			stat.setString(1, p.getKahvilaLapimenoaika());
			stat.setString(2, p.getPlvltiski1Lapimenoaika());
			stat.setString(3, p.getPlvltiski2Lapimenoaika());
			stat.setString(4, p.getHuoneLapimenoaika());
			stat.setString(5, p.getRavintolaLapimenoaika());
			
			stat.setString(6, p.getKahvilanKeskimJononPituus());
			stat.setString(7, p.getPlvltiski1KeskimJononPituus());
			stat.setString(8, p.getPlvltiski2KeskimJononPituus());
			stat.setString(9, p.getHuoneKeskimJononPituus());
			stat.setString(10, p.getRavintolaKeskimJononPituus());
			stat.executeUpdate();
			//stat.executeQuery();
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLExceptions("Create tulokset ei onnistunut", e);
			return false;
		} 
	}	
	
	
    /**
     * Luo tietokantakyselyn, jolla halutaan hakea tietokannasta valittuja tietoja
     * ja asettaa tiedot luotuun taulukkoon.
     * Taulukko tietoineen palautetaan sita kysyvalle luokalle.
     * Jos kysely ei onnistu, catch-lohko nappaa virheen ja tulostaa virheilmoituksen
     */
    public Tulokset[] getTulokset() {
    	Tulokset[] palautettava;
    	try {
			String sql = "SELECT * FROM asiakasmaarat";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			palautettava = new Tulokset[rs.getRow()];
			rs.beforeFirst();
			//palautettava[rs.getRow() - 1] = new Tulokset(rs.getInt(8), rs.getInt(3), rs.getInt(4), 
			while(rs.next()) {
				palautettava[rs.getRow() - 1] = new Tulokset(rs.getInt(6), rs.getInt(1), rs.getInt(2), 
						rs.getInt(3), rs.getInt(5), rs.getInt(4));
			}
			System.out.println(palautettava);
			return palautettava;
			
		} catch (SQLException e) {
			printSQLExceptions("getTulokset ei onnistunut", e);
			//Jos ei toimi, caihda alemmaks
			return null;
		}
    	
    }
    
    /**
     * Luo tietokantakyselyn. Kyselyssa yhdistetaan kaksi taulua: tulosteet-taulu ja asiakasMaarat-taulu ja
     * haetaan molempien taulujen kaikki tiedot siitä simulointikerrasta, jossa simulointinumero on molemmissa sama.
     * Lisataan tulokset listaan,
     * lista muutetaan taulukoksi ja lopuksi taulukko tietoineen palautetaan sita kysyvalle luokalle.
     * Jos kysely ei onnistu, catch-lohko nappaa virheen ja tulostaa virheilmoituksen
     */
    public Raportti[] getKaikkiTuloksetSamastaSimulaatiokerrasta () {
    	ArrayList<Raportti> list = new ArrayList();

    	String sql2 = "SELECT * FROM tulosteet\r\n"
    			+ "INNER JOIN asiakasMaarat ON tulosteet.tulosteSimulointinumero = asiakasMaarat.asiakasSimulointinumero;\r\n";
    	try (PreparedStatement statement = conn.prepareStatement(sql2)) {
			
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {	
				Raportti rap = new Raportti ();
				rap.setKahvilaAsiakkaat(rs.getInt(14));
				rap.setKahvilaLapimenoaika(rs.getString(2));
				rap.setKahvilanKeskimJononPituus(rs.getString(7));
				
				rap.setPlvlpiste2Asiakkaat(rs.getInt(12));
				rap.setPlvltiski2Lapimenoaika(rs.getString(4));
				rap.setPlvltiski2KeskimJononPituus(rs.getString(9));
				
				rap.setPlvlpiste1Asiakkaat(rs.getInt(13));
				rap.setPlvltiski1KeskimLapimenoaika(rs.getString(3));
				rap.setPlvltiski1KeskimJononPituus(rs.getString(8));
				
				rap.setHuoneAsiakkaat(rs.getInt(15));
				rap.setHuoneLapimenoaika(rs.getString(5));
				rap.setHuoneenKeskimJononPituus(rs.getString(10));
				
				rap.setRavintolaAsiakkaat(rs.getInt(16));
				rap.setRavintolaLapimenoaika(rs.getString(6));
				rap.setRavintolaKeskimJononPituus(rs.getString(11));
				
				rap.setTuloksetSimulointi(rs.getInt(1));
				rap.setAsiakasMaaraSimulointi(rs.getInt(17));		
						
				list.add(rap);
			}			
		} catch (SQLException e) {
			printSQLExceptions("getKaikkiTuloksetSamastaSimulaatiokerrasta ei onnistunut", e);
			System.exit(-1);
		}
		Raportti[] returnArray = new Raportti[list.size()];
		return (Raportti[])list.toArray(returnArray);
	}

    
	/**
	 * printSQLExceptions metodia kutsutaan, jos tietokantakyselymetodeissa suoritus etenee catch-lohkoon. 
	 * Metodi tulostaa kaikki virheilmoitukset mita loytaa 
	 * @param methodName metodin nimi
	 * 
	 * @param e sql poikkeus
	 */
	private static void printSQLExceptions(String methodName, SQLException e) {
		 System.err.println("Metodi: " + methodName);
		 do {
			 System.err.println("Viesti: " + e.getMessage());
			 System.err.println("Virhekoodi: " + e.getErrorCode());
			 System.err.println("SQL-tilakoodi: " + e.getSQLState());
		 	} while (e.getNextException() != null);
		}

}
