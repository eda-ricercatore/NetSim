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

// Importing packages...
import java.awt.event.*;
import utility.*;
import java.awt.geom.*;

/**
 * Class to represent a generic listener for window components
 * in the "NetSim" software
 * @author Zhiyang Ong and Andy Lo
 * @version 0.3.1-zhiyang
 * @acknowledgement J Lewis & W Loftus, Java Software Solutions:
 *  Foundations of Program Design, 2nd Edition, Addison-Wesley,
 *  Reading, Massachusetts
 */
public class NSWindowListener extends WindowAdapter{
    
    // Default constructor...
    public NSWindowListener() {
    }
    
    // -------------------------------------------------------------------
    
    /**
     * Method to terminate the program when the Window is closed
     * @param WindowEvent "event" is a low-level event to indicate that
     *  the Window has changed its status
     * @returns nothing
     */
    public void windowClosing(WindowEvent event) {
        System.exit(0);
    }
}