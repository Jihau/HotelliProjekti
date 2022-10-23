package esitys.presentation.helpers;


import esitys.presentation.constants.PresentationConstants;
import esitys.presentation.entities.CommonWaitingQueue;
import simu.model.Asiakas;
import simu.model.OmaMoottori;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class for containing objects to be represented into the simulation
 */
public class Canvas extends JPanel {

    /**
     * SerialVersionUID of the JPanel object
     */
    private static final long serialVersionUID = 1L;

    /**
     * Array of objects representing the Queues with the Customers of the simulation
     */
    CommonWaitingQueue[] commonWaitingQueue;
    /**
     * List of Customer containing the data of the objects to be represented in the visualization
     */
    List<Asiakas>[] customerList;

    /**
     * Constructor of the Canvas class
     * @param omaMoottori : Object of type OmaMoottori class to be bound into the simulation
     */
    public Canvas(OmaMoottori omaMoottori) {
        commonWaitingQueue = new CommonWaitingQueue[omaMoottori.palvelupisteet.length];
        for (int i = 0; i < commonWaitingQueue.length; i++) {
        	commonWaitingQueue[i] = new CommonWaitingQueue(PresentationConstants.COMMON_WAITING_QUEUE_INITIAL_X,
                    PresentationConstants.COMMON_WAITING_QUEUE_INITIAL_Y + (PresentationConstants.COMMON_WAITING_QUEUE_SPACE * i), PresentationConstants.COMMON_WAITING_QUEUE_WIDTH, PresentationConstants.COMMON_WAITING_QUEUE_HEIGHT);
		}
        customerList = new List[omaMoottori.palvelupisteet.length];
        for (int i = 0; i < omaMoottori.palvelupisteet.length; i++) {
        	customerList[i] = omaMoottori.palvelupisteet[i].jono;
		}
        this.repaint();
        startSimulation();
    }

    /**
     * Method for syncing all the objects that will be visually represented into the JPanel
     * @param g : Graphics object used for visually representing the objects of this class in the canvas
     */
    public void refreshAllComponents(Graphics g) {
        for (int i = 0; i < commonWaitingQueue.length; i++) {
        	commonWaitingQueue[i].paintInCanvas(g);
		}
    }

    /**
     * Overriding method for setting size of the JPanel
     * @return Dimensions of the JPanel
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PresentationConstants.WINDOW_WIDTH, PresentationConstants.WINDOW_HEIGHT);
    }

    /**
     * Method needed to draw components into the canvas of the JPanel
     * @param g : Graphics object used for visually representing the objects of this class in the canvas
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        refreshAllComponents(g);
    }

    /**
     * Method for starting the syncing of the visual representations of the simulation
     */
    public void startSimulation() {
        Runnable helloRunnable = () -> {
            for (int i = 0; i < commonWaitingQueue.length; i++) {
                commonWaitingQueue[i].syncCustomerToUI(customerList[i]);
            }
            repaint();
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, PresentationConstants.REFRESH_INTERVAL, TimeUnit.MILLISECONDS);
    }
}