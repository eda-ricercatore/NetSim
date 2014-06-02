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
import javax.swing.UIManager;
import javax.swing.Timer;
import utility.*;
import java.awt.geom.*;

/**
 * Class to execute the "NetSim" software
 * @author Zhiyang Ong and Andy Lo
 * @version 0.3.1-zhiyang
 * @acknowledgement J Lewis & W Loftus, Java Software Solutions:
 *  Foundations of Program Design, 2nd Edition, Addison-Wesley,
 *  Reading, Massachusetts
 */
public class RunNetSim {
	 // Declaration of instance variables
    public static GuiManager frame;       // Frame for the GUI of NetSim

	 // Dimensions of the width and height of the GUI in Pixels
	 public static final int width = 1000;
	 public static final int height = 750;
    
    // -----------------------------------------------------------------
    
    // Default constructor
    public RunNetSim() {
        Debugger.printErr("The Class RunNetSim shouldn't be "
            +"instantiated with its default constructor");
    }
    
    // ------------------------------------------------------------------
    
    // Main Method...
    public static void main(String[] args) {
        try {
            /**
             * Set the current default look and feel based on the
             * current operating system/environment
             */
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e) {
            // Print a list of where and what Exceptions occurred
            e.printStackTrace();
        }

        // Enable statements to be printed on the console
        Debugger.enableTrace(true);
        
        // Create the Splash Screen for the GUI of "NetSim"
        SplashScreen splashScr = new SplashScreen();
        
        splashScr.show();           // Make the Splash Screen Window visible
        splashScr.dispose();        // Close the Splash Screen
        
        // Display the GUI of the NetSim program
		  // Create a new Frame for the GUI of "NetSim"
		  frame = new GuiManager();
		  /**
         * Allow the GUI Window Frame to respond to user events, such as
         * open, close, activate or deactivate, iconify or deiconify
         * the Window Frame.
         */
        frame.addWindowListener(new NSWindowListener());
//        frame.show();           // Make the GUI Window visible
        frame.setVisible(true);
		  
        // Create a thread to run the GUI
//        GUIThread guiThread = new GUIThread();
        // Start executing the Thread
//        guiThread.start();

			// Create a thread to run the simulation
//			SimulationThread simulate = new SimulationThread();
			// Start the simulation
//			simulate.start();
    }
	 
	 /**
	  * Method to return the width of the GUI
	  * @return the width of the GUI
	  */
	 public static int getWidth() {
	 	return width;
	 }
	 
	 /**
	  * Method to return the height of the GUI
	  * @return the height of the GUI
	  */
	 public static int getHeight() {
	 	return height;
	 }
}
