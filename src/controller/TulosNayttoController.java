package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import simu.model.Tulokset;
import view.SimulaattorinGUI;

/**
* Luokka toteuttaa fxml tieostosta haetun TulosNäyttö näkymän toiminnot
*
* @author Miika Auvinen
*/
public class TulosNayttoController {
	
	/**
	* Viittaus TableVieviin: tuloksetTaulu
	*/
	@FXML
	private TableView<Tulokset> tuloksetTaulu;
	
	/**
	* Viittaus TableVievin pysty riviin: idColumn
	*/
	@FXML
	private TableColumn<Tulokset, String> idColumn;
	
	//@FXML
	//private TableColumn<Tulokset, String> pvmColumn;
	
	/**
	* Viittaus GridPanen Labeliin: idLabel
	*/
	@FXML
	private Label idLabel;
	/**
	* Viittaus GridPanen Labeliin: suure1Label
	*/
	@FXML
	private Label suure1Label;
	/**
	* Viittaus GridPanen Labeliin: suure2Label
	*/
	@FXML
	private Label suure2Label;
	/**
	* Viittaus GridPanen Labeliin: ravAsLabel
	*/
	@FXML
	private Label ravAsLabel;
	/**
	* Viittaus GridPanen Labeliin: huoneAsLabel
	*/
	@FXML
	private Label huoneAsLabel;
	/**
	* Viittaus GridPanen Labeliin: kahvAsLabel
	*/
	@FXML
	private Label kahvAsLabel;
	
	/**
	* Viittaus Siirtymis nappiin: simuloiButton
	*/
	@FXML
	private Button simuloiButton;
	/**
	* Viittaus Siirtymis nappiin: hubButton
	*/
	@FXML
	private Button hubButton;
	
	/**
	* mainApp muuttujaan tallennetaan viittaus main luokkaan, josta kutsuttiin tätä luokkaa.
	*/
	private SimulaattorinGUI mainApp;
	
	/**
	* Tyhjä konstruktori
	*/
	public TulosNayttoController() {
		
	}
	
	/**
	* Kutsutaan ladattaessa fxml tiedosto. Asettaa toiminnut sovelluksen siirtymis-napeille
	* ja alustaa gridpanen labelit tyhjiksi, sekä toteuttaa listviewin sisällön ja 
	* asettaa niiden valintoihin kuuntelijan tietojen näyttöä varten
	*/
	@FXML
	private void initialize() {
        // Initialize the person table with the two columns.
		
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdNaytettava());
        //pvmColumn.setCellValueFactory(cellData -> cellData.getValue().pvmProperty());
        
        naytaSimuTiedot(null);
        
        tuloksetTaulu.getSelectionModel().selectedItemProperty().addListener(
        		(observable, oldValue, newValue) -> naytaSimuTiedot(newValue));
        
        simuloiButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainApp.NaytaSimuIkkuna();
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
	* Kutsutaan SimulaattorinGUI luokasta, jotta saadaan tälle luokalle viittaus käytettävään main luokkaan
	*
	* @param mainApp viittauksen main luokkaan muuttujana mainApp
	*/
	public void setMainApp(SimulaattorinGUI mainApp) {
		this.mainApp = mainApp;
		
		tuloksetTaulu.setItems(this.mainApp.getTulokset());
	}
	
	/**
	* Asettaa halutut tiedot gridpanen labeleihin
	* 
	*
	* @param tulos parametrinä käyttäjän listasta valitseman Tulokset olion
	*/
	private void naytaSimuTiedot(Tulokset tulos) {
        if (tulos != null) {
            // Fill the labels with info from the person object.
            idLabel.setText(Integer.toString(tulos.getId()));
            suure1Label.setText(Integer.toString(tulos.getPp1As()));
            suure2Label.setText(Integer.toString(tulos.getPp2As()));
            huoneAsLabel.setText(Integer.toString(tulos.getHuoAs()));
            ravAsLabel.setText(Integer.toString(tulos.getRavAs()));
            kahvAsLabel.setText(Integer.toString(tulos.getKahvAs()));
            
        } else {
            // Person is null, remove all the text.
            idLabel.setText("");
            suure1Label.setText("");
            suure2Label.setText("");
            huoneAsLabel.setText("");
            ravAsLabel.setText("");
            kahvAsLabel.setText("");
            
        }
    }
    
	
}


/*package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import simu.model.Tulokset;
import view.SimulaattorinGUI;

public class TulosNäyttöController {
	@FXML
	private TableView<Tulokset> tuloksetTaulu;
	
	@FXML
	private TableColumn<Tulokset, String> idColumn;
	
	//@FXML
	//private TableColumn<Tulokset, String> pvmColumn;
	
	@FXML
	private Label idLabel;
	
	@FXML
	private Label suure1Label;
	
	@FXML
	private Label suure2Label;
	
	@FXML
	private Label ravAsLabel;
	
	@FXML
	private Label huoneAsLabel;
	
	@FXML
	private Label kahvAsLabel;
	
	@FXML
	private Button simuloiButton;
	@FXML
	private Button hubButton;
	
	private SimulaattorinGUI mainApp;
	
	public TulosNäyttöController() {
		
	}
	
	@FXML
	private void initialize() {
        // Initialize the person table with the two columns.
		
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdNaytettava());
        //pvmColumn.setCellValueFactory(cellData -> cellData.getValue().pvmProperty());
        
        naytaSimuTiedot(null);
        
        tuloksetTaulu.getSelectionModel().selectedItemProperty().addListener(
        		(observable, oldValue, newValue) -> naytaSimuTiedot(newValue));
        
        simuloiButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainApp.NaytaSimuIkkuna();
            }
        });
		
		hubButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainApp.naytaSimuHub();
            }
        });
        		
    }
	
	
	public void setMainApp(SimulaattorinGUI mainApp) {
		this.mainApp = mainApp;
		
		tuloksetTaulu.setItems(this.mainApp.getTulokset());
	}
	
	
	private void naytaSimuTiedot(Tulokset tulos) {
        if (tulos != null) {
            // Fill the labels with info from the person object.
            idLabel.setText(Integer.toString(tulos.getId()));
            suure1Label.setText(Integer.toString(tulos.getPp1As()));
            suure2Label.setText(Integer.toString(tulos.getPp2As()));
            huoneAsLabel.setText(Integer.toString(tulos.getHuoAs()));
            ravAsLabel.setText(Integer.toString(tulos.getRavAs()));
            kahvAsLabel.setText(Integer.toString(tulos.getKahvAs()));
            
        } else {
            // Person is null, remove all the text.
            idLabel.setText("");
            suure1Label.setText("");
            suure2Label.setText("");
            huoneAsLabel.setText("");
            ravAsLabel.setText("");
            kahvAsLabel.setText("");
            
        }
    }
	
}
*/