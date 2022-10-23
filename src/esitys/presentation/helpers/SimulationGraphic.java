package esitys.presentation.helpers;

import java.awt.*;

/**
 * Class to abstract all the common methods of objects interacting in the visualization
 */
public abstract class SimulationGraphic {
    public int x = 0, y = 0, width = 0, height = 0;
    public Color fillColor = Color.white, borderColor = Color.black, previousColor;

    /**
     * Constructor of the SimulationGraphic class
     * @param x : Initial X coordinates for the object to be rendered
     * @param y : Initial X coordinates for the object to be rendered
     * @param width : Width of the object to be rendered
     * @param height : Height of the object to be rendered
     * @param fillColor : Initial fill color for the object to be rendered
     * @param borderColor : Initial border color for the object to be rendered
     */
    public SimulationGraphic(int x, int y, int width, int height, Color fillColor, Color borderColor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    /**
     * Method used to preserve the initial fll and border color of the graphics object
     * @param g : Graphics object used for visually representing the objects of this class in the canvas
     */
    private void paintInCanvasStart(Graphics g) {
        this.previousColor = g.getColor();
        g.setColor(fillColor);
    }

    /**
     * Method used to restore the initial fill and border color of the graphics object
     * @param g : Graphics object used for visually representing the objects of this class in the canvas
     */
    private void paintInCanvasEnd(Graphics g) {
        g.setColor(previousColor);
    }

    /**
     * Method used to be written by the classes extending this class with their own graphics logic
     * @param g : Graphics object used for visually representing the objects of this class in the canvas
     */
    public abstract void paintInCanvasEditable(Graphics g);

    /**
     * Method used to call paintInCanvasStart, paintInCanvasEditable, paintInCanvasEnd in the right order
     * @param g : Graphics object used for visually representing the objects of this class in the canvas
     */
    public void paintInCanvas(Graphics g) {
        paintInCanvasStart(g);
        paintInCanvasEditable(g);
        paintInCanvasEnd(g);
    }
}
