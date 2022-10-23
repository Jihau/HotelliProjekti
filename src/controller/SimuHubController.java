package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import view.SimulaattorinGUI;

/**
* Luokka toteuttaa fxml tieostosta haetun SimuHub näkymän toiminnot
*
* @author Miika Auvinen
*/
public class SimuHubController {
	
	/**
	* mainApp muuttujaan tallennetaan viittaus main luokkaan, josta kutsuttiin tätä luokkaa.
	*/
	private SimulaattorinGUI mainApp;
	
	/**
	* Alla olevat muuttujat antavat viittauksen simuHub näkymässä oleviin teksteihin.
	*/
	@FXML
	private Text tarkasteleText;
	@FXML
	private Text simuloiText;

	/**
	* viittaus simuHub näkymän tarkasteluButtoniin, käytetään sen toimintojen toteutukseen.
	*/
	@FXML
	private Button tarkasteleButton;
	/**
	* viittaus simuHub näkymän simuloiButtoniin, käytetään sen toimintojen toteutukseen
	*/
	@FXML
	private Button simuloiButton;
	
	/**
	* Tyhjä konstruktori
	*/
	public SimuHubController() {
		
	}
	
	/**
	* Asetetaan kuvaus-tekstit ja kuuntelijat näkymän nappeihin
	*/
	@FXML
	public void initialize() {
		tarkasteleText.setText("Alla olevasta napista pääset tarkastelemaan aikaisempia "
				+ "simulaatioita ja niiden tarkkoja suorituskykysuureita");
		
		simuloiText.setText("Alla olevasta pääset ajamaan simulaation hotellista"
				+ " antamillasi parametreillä:");
		
		tarkasteleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainApp.naytaTulosNaytto();
            }
        });
		
		simuloiButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainApp.NaytaSimuIkkuna();
            }
        });
		
	}
	
	/**
	* Kutsutaan SimulaattorinGUI luokasta, jotta saadaan tälle luokalle viittaus käytettävään main luokkaan
	*
	* @param mainApp viittauksen main luokkaan muuttujana mainApp
	*/
	public void setMainApp(SimulaattorinGUI mainApp) {
		this.mainApp = mainApp;
	}
}

