package esitys.presentation.entities;

import esitys.presentation.constants.PresentationConstants;
import esitys.presentation.helpers.SimulationGraphic;
import simu.model.Asiakas;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * This class representing the queue were the customers wait for the simulation
 */
public class CommonWaitingQueue extends SimulationGraphic {
    /**
     * List of visual Customers that the queue will render
     */
    List<VisualCustomer> customerList;
    /**
     * Helper variable for keeping track of the last object drawn in the queue
     */
    int current_x_pos;

    /**
     * Constructor of the CommonWaitingQueue class
     * @param x : Placing X coordinates for placing the rectangle representing the queue
     * @param y : Placing Y coordinates for placing the rectangle representing the queue
     * @param width : Width of the rectangle representing the queue
     * @param height: Height of the rectangle representing the queue
     */
    public CommonWaitingQueue(int x, int y, int width, int height) {
        super(x, y, width, height, Color.gray, Color.blue);
        customerList = new LinkedList<>();
        current_x_pos = x;
    }

    /**
     * Method for syncing the list of customers to the rendering logic
     * @param listCustomer : List of Customers that will be visualized in the queue
     */
    public void syncCustomerToUI(List<Asiakas> listCustomer) {
        if (listCustomer != null && listCustomer.size() > 0) {
            customerList = new LinkedList<>();
            current_x_pos = x;
            for (Asiakas customer : listCustomer) {
                addCustomerToQueue(customer);
            }
        }
    }

    /**
     * Method for adding Customer to the visual representation of the queue
     * @param customer : Customer that will be added to the visualization
     */
    private void addCustomerToQueue(Asiakas customer) {
        VisualCustomer visualCustomer = new VisualCustomer(getNextXPosition(), y, PresentationConstants.CUSTOMER_WIDTH, PresentationConstants.CUSTOMER_HEIGHT, customer);
        customerList.add(visualCustomer);
    }

    /**
     * Method for getting the x placing for the new customer
     * @return Integer representing the next position in the X axis
     */
    public int getNextXPosition() {
        return x + customerList.size() * PresentationConstants.CUSTOMER_WIDTH;
    }

    /**
     * Method from superclass to draw graphics of the class
     * @param g : Graphics object used for visually representing the objects of this class in the canvas
     */
    @Override
    public void paintInCanvasEditable(Graphics g) {
        g.setColor(this.fillColor);
        g.fillRect(this.x, this.y, this.width, this.height);
        g.setColor(this.borderColor);
        g.drawRect(this.x, this.y, this.width, this.height);
        if (customerList != null) {
            for (VisualCustomer customer : customerList) {
                customer.paintInCanvas(g);
            }
        }
    }
}
