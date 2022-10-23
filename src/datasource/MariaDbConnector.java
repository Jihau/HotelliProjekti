package datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author juvon
 * Luokka luo ja pitää yllä tietokantayhteyden
 */
public class MariaDbConnector {
	
	/**
	 * <code>conn</code> on Connection oliomuuttujan esittely
	 * 			conn arvo asetetaan null.
	 */
	private static Connection conn = null;
	
	/**
	 * Jos conn on null, eli ei ole vielä luotu tietokantayhteyttä,
	 *  metodi luo tietokantayhteyden try-lohkossa ja asettaa sen conn-muuttujaan. Mikäli yhteyden luominen ei onnistu, suoritus siirtyy
	 *  catch-lohkoon joka tulostaa käyttäjälle tiedon muodostamisen epäonnistumisesta. 
	 *  Palauttaa conn-muuttujan, joka sisältää tietokantayhteyden.
	 * @return tietokantayhteyden, joka säilötty muuttujaan conn. 
	 */
	public static Connection getInstance() {	
		if (conn==null) {
			// luo tietokantayhteys		
			try {
				conn = DriverManager.getConnection("jdbc:mariadb://localhost/Hotelli?user=olso&password=olso");
				System.out.println("Yhteys luotu.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Tietokantayhteyden muodostus epäonnistui." );
				 e.printStackTrace();
				System.exit(-1);
			}
			return conn;
		}
		
		else {
			return conn;
		}
	}
}
