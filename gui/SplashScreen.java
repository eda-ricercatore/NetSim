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
import java.awt.*;
import javax.swing.*;
import utility.*;

/**
 * Class to create the Splash Screen for the "NetSim" software
 * Java Swing components are primarily used since they are more versatile
 * than the AWT package.
 * @author Zhiyang Ong and Andy Lo
 * @version 0.3.1-zhiyang
 * @acknowledgement J Lewis & W Loftus, Java Software Solutions:
 *  Foundations of Program Design, 2nd Edition, Addison-Wesley,
 *  Reading, Massachusetts;
 *  Craig Eales for assistance in the development of the GUI;
 *  and Nick Ray for assistance on using Thread.sleep(int delay)
 *  to create a time delay so that the Splash Screen can be displayed
 *  for that period of time
 */
public class SplashScreen extends JFrame {
    // Declaration of instance variables...

    
    // -------------------------------------------------------------------
    
    // Default constructor...
    public SplashScreen() {
        // Indicate the title of the "NetSim" software
        super("NetSim v1.0");
                
        // Flash the "Splash Image"
         displaySplash();
    }
    
    // ------------------------------------------------------------------
    
    // Methods...
    
    /**
     * Method to flash the "Splash Image" on the screen
     * @return nothing
     */
    public void displaySplash() {
        // Get picture of the "Splash Image"
        ImageIcon splash = getSplashImage();
        
        // if the image file was found
        if (splash != null) {
            // create splash screen and centre it in centre of screen
            
            /**
             * Create a label for the "Splash Image"
             * I am unable to center the text "Starting up NetSim" in 1 line
             * of code; hence, I am taking a few extra lines
             */
            JLabel splashLabel = new JLabel("Starting up...", splash,
                SwingConstants.LEFT);
            // Center the Splash Screen on the centre of the Monitor
            splashLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            splashLabel.setVerticalTextPosition(SwingConstants.TOP);
            // Add the Label to a GUI Container
            Container splashContent = getContentPane();
            splashContent.setLayout(new FlowLayout());
        
            // Get the dimensions of the Splash Screen Image
            int width = splash.getIconWidth();
            int height = splash.getIconHeight();

            // Get the dimensions of the screen (monitor)
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            // Add the Splash label to this Window
            this.getContentPane().add(splashLabel);
            // Set the Bounds of the Splash Screen Window to center the Splash
            this.setBounds( (screenSize.width - width)/2,
                (screenSize.height - height)/2, width, height);
            // Size the Window appropriately on the screen
            this.pack();
            // Display the Splash Screen
            this.show();
            // Replace default cursor with a wait cursor whilst loading screen
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            // Display the Splash Screen for 3 seconds...
            splashDelay(3);
        }
    }
    
    /**
     * Method to access the location of the "Splash Image" picture (420*280);
     * include its package (path) name!
     * @return "Splash Screen Image" as an Image Icon
     */
    public ImageIcon getSplashImage() {
        /**
         * To change the path name of where the picture is located,
         * amend the String path that is a parameter for the ImageIcon
         * constructor
         */
        return new ImageIcon("gui/images/netsim_splash.jpg");
    }
    
    /**
     * Method to display the Splash Screen image on the monitor
     * for "delayTime" number of seconds
     * @param delayTime is the number of seconds to display the
     *  Splash Screen
     * @throws PreconditionException if "delayTime" is negative
     * @return nothing
     */
    public void splashDelay(int delayTime) {
        Assertion.pre(delayTime > 0, "splashDelay(int delayTime) works",
            "Time cannot have negative values");
        try{
            // Pause for "delayTime"*1000 ms
            Thread.sleep(delayTime*1000);
        }catch(InterruptedException e) {
            Debugger.printErr("Delay for Splash Screen is not working");
        }
    }
}