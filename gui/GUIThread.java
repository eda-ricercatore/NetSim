/*
 * Package containing the necessary tools to provide a
 * Graphical User Interface.
 * #It allows users to modify restricted input parameters for the network
 *  to be optimised.
 * #It graphically indicates the evolution of the network as the "NetSim"
 *  software optimises the network, seeking the optimal trade-off between
 *  pleiotropy and redundancy
 * #It allows users to graphically view the optimised network at the end
 *  of the simulation
 */
package gui;

import java.awt.geom.*;

/**
 * Class to create the Thread to set up and manage the GUI for "NetSim"
 * Java Swing components are primarily used since they are more versatile
 * than the AWT package.
 * @author Zhiyang Ong and Andy Lo
 * @version 0.3.1-zhiyang
 * @acknowledgement J Lewis & W Loftus, Java Software Solutions:
 *  Foundations of Program Design, 2nd Edition, Addison-Wesley,
 *  Reading, Massachusetts
 *  and Craig Eales for assistance in the development of the GUI
 */
public class GUIThread extends Thread {
    // Declaration of instance variables
    GuiManager frame;       // Frame for the GUI of NetSim
    
    // -----------------------------------------------------------------
    
    // Default constructor
    public GUIThread() {
        // Create a new Frame for the GUI of "NetSim"
//System.out.println("GUIThread is about to be instantiated");
        frame = new GuiManager();
//System.out.println("GUIThread is instantiated");
    }
    
    // ------------------------------------------------------------------
    
    /**
     * Method to run this GUIThread so that a GUI Window can be created
     * and the user can execute the software.
     * @return nothing
     */
    public void run() {
        /**
         * Allow the GUI Window Frame to respond to user events, such as
         * open, close, activate or deactivate, iconify or deiconify
         * the Window Frame.
         */
        frame.addWindowListener(new NSWindowListener());
//        frame.show();           // Make the GUI Window visible
        frame.setVisible(true);
    }
}
