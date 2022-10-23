package view;


import java.io.IOException;
import java.text.DecimalFormat;

import simu.model.Raportti;
import simu.model.Tulokset;
import controller.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.framework.Kello;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class SimulaattorinGUI extends Application implements ISimulaattorinUI{

	//Kontrollerin esittely (tarvitaan käyttöliittymässä)
	private IKontrolleriVtoM kontrolleri;
	// Käyttöliittymäkomponentit:
	/**
	 *Muuttuja <code>aika</code> sisältää tiedon simuloinnin ajanhetkestä
	 */
	private double aika;
	/**
	 *Muuttuja <code>viive</code> sisältää tiedon simuloinnin viiveestä
	 */
	private long viive;
	/**
	 *Muuttuja <code>tulos</code> sisältää tiedon simuloinnin kokonaisajasta
	 */
	private Label tulos;

	/**
	 * <code>naytto</code> on IVisualisointi oliomuuttujan esittely
	 */
	private IVisualisointi naytto;
	/**
	 *Muuttuja <code>primaryStage</code> sisältää tiedon primaryStagesta 
	 */
	private Stage primaryStage;
	
    /**
     * 
     */
    private BorderPane rootLayout;
    
    /**
	 * Muuttujaan <code>hbox</code> asetetaan tieto näkymän perustana olevasta HBox elementista
	 */
    private HBox hbox;
    
    private SimuIkkunaController simuIkkunaController;
    
    private ObservableList<Tulokset> tuloksetData = FXCollections.observableArrayList();

	@Override
	public void init(){
		
		Trace.setTraceLevel(Level.INFO);
		
		kontrolleri = new Kontrolleri(this);
	}

	@Override
	public void start(Stage primaryStage) {
		
		// Käyttöliittymän rakentaminen
		
		this.primaryStage = primaryStage;
		try {
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent t) {
			        Platform.exit();
			        System.exit(0);
			    }
			});
			
			naytto = new Visualisointi2(750,400);
			
			//TODO: Hae näytettävät tiedot db
			
			this.primaryStage.setTitle("HotelSimulator");	

	        initRootLayout();
	        
	        naytaSimuHub();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SimulaattorinGUI.class.getResource("/view/RootLayout.fxml"));
            System.out.println(loader.getLocation());
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void naytaSimuHub() {
		try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SimulaattorinGUI.class.getResource("/view/SimuHub.fxml"));
            AnchorPane simuHub = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(simuHub);
            
            SimuHubController controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void naytaTulosNaytto() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SimulaattorinGUI.class.getResource("/view/TulosNäyttö.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            
            TulosNayttoController controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Avaa visuaaliseksi simulaattorin käynnistämissivun
	 */
	public void NaytaSimuIkkuna() {
		try {
			Kello.getInstance().resetoiAika();
			
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SimulaattorinGUI.class.getResource("/view/SimuIkkuna.fxml"));
            AnchorPane simuIkkuna = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(simuIkkuna);
            
            simuIkkunaController = loader.getController();
            simuIkkunaController.setMain(this);
            
            tulos = simuIkkunaController.getLabel();
            hbox = simuIkkunaController.getHBox();
            System.out.println(hbox.getChildren());
            hbox.getChildren().add((Canvas) naytto);
            
        } catch (IOException e) {
        	
            e.printStackTrace();
        }
	}
	
	/**
	 * Kaynnistaa simulaation
	 */
	public void kaynnistaSimu() {
		kontrolleri.kaynnistaSimulointi();
	}
	
	
	/**
	 * Palauttaa tulosten datan
	 * Hakee raporttiluokasta tulokset ja lisaa ne tulokset taulukkoon.
	 * Kay taulukon lapi ja lisaa tulokset ObservableList -tyyppiseen tuoksetData listaan. 
	 * Palauttaa tuloksetData-listan.
	 * @return ObservableList tyyppinen lista tuloksista
	 */
	public ObservableList<Tulokset> getTulokset(){
		Raportti r = new Raportti();
		//tuloksetData.addAll(r.getTulokset());
		Tulokset[] t = r.getTulokset();
		for(int i = 0;i<t.length;i++) {
			tuloksetData.add(t[i]);
		}
		System.out.println(tuloksetData);
		return this.tuloksetData;
	}


	//Käyttöliittymän rajapintametodit (kutsutaan kontrollerista)

	public void setAika(double aika) {
		this.aika = aika;
	}

	public void setViive(long viive) {
		this.viive = viive;
	}

	/**
	 *Palauttaa ajanhetken, joka on viimeksi asetettu aika-muuttujaan
	 *@return aika
	 */
	@Override
	public double getAika(){
		return aika;
	}

	/**
	 * Palauttaa simuloinnin viiveen
	 * @return simuloinnin viive
	 */
	@Override
	public long getViive(){
		return viive;
	}

	/**
	 * Asettaa loppuajan parametrina saadun ajan mukaisesti
	 */
	@Override
	public void setLoppuaika(double aika){
		 DecimalFormat formatter = new DecimalFormat("#0.00");
		 this.tulos.setText("Lopetusaika: " + formatter.format(aika));
	}


	/**
	 * Palauttaa visualisoinnissa kaytetyn nayton
	 *@return visuaalinen naytto
	 */
	@Override
	public IVisualisointi getVisualisointi() {
		 return naytto;
	}
	
	/**
	 * Palauttaa kontrollerin 
	 * @return asetetun IKontrolleriVtoM tyyppisen kontrollerin
	 */
	public IKontrolleriVtoM getKontroller() {
		return kontrolleri;
	}
	
	// JavaFX-sovelluksen (käyttöliittymän) käynnistäminen

	public static void main(String[] args) {
		launch(args);
	}
	
	
	/**
	 * Palauttaa asiakkaan kahvilaan menemisen todennakoisyyden 
	 * @return kahvilaan menemisen todennakoisyys
	 */
	@Override
	public int getKahvilanTodennakoisyys() {
		
		return this.simuIkkunaController.getKahvilaTodNak();
	}
	
	/**
	 * Palauttaa asiakkaan ennakkohuonevarauksen todennakoisyyden 
	 * @return ennakkohuonevarauksen todennakoisyys
	 */
	@Override
	public int getVarauksenTodennakoisyys() {
		
		return this.simuIkkunaController.getHuoneTodNak();
	}

	
}
