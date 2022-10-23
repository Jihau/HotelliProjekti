
package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import view.SimulaattorinGUI;

/**
* Luokka toteuttaa fxml tieostosta haetun SimuIkkuna näkymän toiminnot
*
* @author Miika Auvinen
*/
public class SimuIkkunaController {
	
	/**
	* mainApp muuttujaan tallennetaan viittaus main luokkaan, josta kutsuttiin tätä luokkaa.
	*/
	private SimulaattorinGUI mainApp;
	
	/**
	* Moottorille annettava todennäköisyys kahvila varauksille
	*/
	private int kahvilaTodNak;
	/**
	* Moottorille annettava todennäköisyys huone varauksille
	*/
	private int huoneTodNak;
	
	/**
	* Viite tekstikenttään: viive
	*/
	@FXML
	private TextField viiveField;
	/**
	* Viite tekstikenttään: aika
	*/
	@FXML
	private TextField aikaField;
	/**
	* Viite tekstikenttään: kahvila
	*/
	@FXML
	private TextField kahvilaField;
	/**
	* Viite tekstikenttään: huone
	*/
	@FXML
	private TextField huoneField;
	
	/**
	* Viite nappiin: Hub
	*/
	@FXML
	private Button hubButton;
	/**
	* Viite nappiin: tarkastelu
	*/
	@FXML
	private Button tarkasteluButton;
	/**
	* Viite nappiin: simuloi
	*/
	@FXML
	private Button simuloiButton;
	/**
	* Viite nappiin: hidasta
	*/
	@FXML
	private Button hidastaButton;
	/**
	* Viite nappiin: nopeuta
	*/
	@FXML
	private Button nopeutaButton;
	
	/**
	* Viite näkymän perustana olevaan HBox elementtiin
	*/
	@FXML
	private HBox takaHBox;
	
	/**
	* Viite Labeliin, johon tulostetaan simulaation kokonaisaika
	*/
	@FXML
	private Label tulosLabel;
	
	
	/**
	* Tyhjä konstruktori
	*
	*/
	public SimuIkkunaController() {}
	
	/**
	* Kutsutaan ladattaessa fxml tiedosto. Asettaa toiminnut sovelluksen siirtymis-napeille
	*
	*/
	@FXML
	public void initialize() {
		tarkasteluButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainApp.naytaTulosNaytto();
            }
        });
		
		hubButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainApp.naytaSimuHub();
            }
        });
	}
	
	/**
	* Kutsutaan, kun käyttäjä antaa tarvittavat tiedot ja painaa simuloi nappia.
	* Hakee tiedot teksti kentistä ja aloittaa simulaation.
	*
	*/
	@FXML
	public void handleSimulointi() {
		double simuAika = Double.parseDouble(aikaField.getText());
		long simuViive = Long.parseLong(viiveField.getText());
		kahvilaTodNak = Integer.parseInt(kahvilaField.getText());
		huoneTodNak = Integer.parseInt(huoneField.getText());
		
		mainApp.setAika(simuAika);
		mainApp.setViive(simuViive);
		
		
		simuloiButton.setDisable(true);
		
		mainApp.kaynnistaSimu();
	}
	
	/**
	* Nopeuta nappi kutsuu. Hakee simulaation kontrollerin ja käskee pienentää viivettä
	*
	*/
	@FXML
	public void nopeuta() {
		mainApp.getKontroller().nopeuta();
	}
	
	/**
	* Hidasta nappi kutsuu. Hakee simulaation kontrollerin ja käskee lisätä viivettä
	*
	* 
	*/
	@FXML
	public void hidasta() {
		mainApp.getKontroller().hidasta();
	}
	
	
	/**
	 * Palauttaa asiakaskohtaisen todennakoisyyden kahvilaan menoon
	 * @return todennakoisyys asiakkaan kahvilaanmenoon
	 */
	public int getKahvilaTodNak() {
		return kahvilaTodNak;
	}

	/**
	 * Palauttaa asiakaskohtaisen todennakoisyyden ennakkovaraukselle
	 * @return todennakoisyys asiakkaan ennakkovaraukselle
	 */
	public int getHuoneTodNak() {
		return huoneTodNak;
	}

	/**
	* Hakija tulosLabel muuttujalle
	* 	tulosLabel pitää sisällään simulaation kokonaisajan
	*
	* @return tulosLabel joka sisältää simulaation kokonaisajan
	*/
	public Label getLabel() {
		return tulosLabel;
	}
	
	/**
	* Hakija takaHBox muuttujalle
	*
	* @return näkymän perustana oleva HBox elementti
	*/
	public HBox getHBox() {
		return takaHBox;
	}
	
	/**
	* Kutsutaan SimulaattorinGUI luokasta, jotta saadaan tälle luokalle viittaus käytettävään main luokkaan
	*
	* @param mainApp viittauksen main luokkaan muuttujana mainApp
	* 
	*/
	public void setMain(SimulaattorinGUI mainApp) {
		this.mainApp = mainApp;
	}
}

