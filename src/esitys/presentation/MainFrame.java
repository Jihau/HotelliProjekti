package esitys.presentation;

import esitys.presentation.constants.PresentationConstants;
import esitys.presentation.helpers.Canvas;
import simu.framework.IMoottori;
import simu.model.OmaMoottori;

import javax.swing.*;
import java.awt.*;

/**
 * Class Used to create a JFrame object with a JPanel of type Canvas where we can render the objects involved in the simulation
 */
public class MainFrame {
    /**
     * Constructor of the MainFrame class that:<br>
     * 1) Creates a JFrame object with a JPanel of Canvas type<br>
     * 2) Links and parses the IMoottori type object to the Canvas object<br>
     * 3) Sets the size, title, default close operation and shows the JFrame object in the middle of the screen<br>
     * @param moottori : Object of type OmaMoottori class to be bound into the simulation
     */
	public MainFrame(IMoottori moottori) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
                JFrame frame = new JFrame("Hotellin jonot");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new Canvas((OmaMoottori)moottori));
                frame.pack();
                frame.setSize(PresentationConstants.WINDOW_WIDTH, PresentationConstants.WINDOW_HEIGHT);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}