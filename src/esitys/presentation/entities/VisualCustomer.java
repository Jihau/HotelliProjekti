package esitys.presentation.entities;

import esitys.presentation.helpers.SimulationGraphic;

import simu.model.Asiakas;

import java.awt.*;

/**
 * Class for visually representing the Customers in the simulation
 */
public class VisualCustomer extends SimulationGraphic {

    /**
     * Data of Customer to be represented in the visualization
     */
	Asiakas customer;


    /**
     * Constructor of the VisualCustomer class
     * @param x : Placing X coordinates for the visual representation of the Customer
     * @param y : Placing Y coordinates for the visual representation of the Customer
     * @param width: Width of the shape representing the Customer
     * @param height: Height of the shape representing the Customer
     * @param customer: Customer object containing the data to be bound/represented into the object
     */
    public VisualCustomer(int x, int y, int width, int height, Asiakas customer) {
        super(x, y, width, height, Color.pink, Color.black);
        this.customer = customer;
    }

    /**
     * Method from superclass to draw graphics of the class
     * @param g : Graphics object used for visually representing the objects of this class in the canvas
     */
    @Override
    public void paintInCanvasEditable(Graphics g) {
        g.fillOval(this.x, this.y, this.width, this.height);
        g.setColor(this.borderColor);
        g.drawOval(this.x, this.y, this.width, this.height);
        g.drawString(String.valueOf(customer.getId()), this.x + this.width / 4, this.y + this.height / 2);
    }
}
