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
import java.awt.event.*;
import javax.swing.*;
import utility.*;

/**
 * Class to create the toolbar for the GUI
 * Java Swing components are primarily used since they are more versatile
 * than the AWT package.
 * @author Zhiyang Ong and Andy Lo
 * @version 0.3.1-zhiyang
 * @acknowledgement J Lewis & W Loftus, Java Software Solutions:
 *  Foundations of Program Design, 2nd Edition, Addison-Wesley,
 *  Reading, Massachusetts; Sun Microsystems, Inc's Java Tutorial located at
 *  <http://developer.java.sun.com/developer/techDocs/hi/repository/>
 *  and Craig Eales for assistance in the development of the GUI
 */
public class ToolBar implements ActionListener {
    // Declare and initialise instance variables
    
    // Toolbar for the GUI makes use of this Menu Bar
    private JMenuBar menuBar;
    /**
     * Indicates the label of the button on the toolbar to execute
     * the simulator; run/pause/stop the simulation
     */
    private static final String COMMAND = "Command";
    /**
     * Indicates the label of the button on the toolbar to request
     * for user manual
     */
    private static final String HELP = "Help";
    /**
     * Indicates the label of the button on the toolbar to request
     * for information about the development of the NetSim software
     */
    private static final String ABOUT = "About NetSim";
    /**
     * Indicates the label of the button on the COMMAND submenu to
     */
    public static final String RUN = "Run simulation";
    /**
     * Indicates the label of the button on the COMMAND submenu to
     */
    public static final String PAUSE = "Pause simulation";
    /**
     * Indicates the label of the button on the COMMAND submenu to
     */
    public static final String STOP = "Stop simulation";
    /**
     * Indicates the label of the button on the COMMAND submenu to
     */
    public static final String EXIT = "Exit NetSim";
    /**
     * Indicates the label of the button on the HELP submenu to
     */
    public static final String GET_MANUAL = "Display user manual";
    /**
     * Indicates the label of the button on the ABOUT submenu to
     */
    public static final String GET_DETAILS = "Get developers' details";
	 
	 // Container for the Control Panel of the GUI
    ControlPanel ctrl;
	 
	 // Thread to run the simulation
	 SimulationThread simu;
	 
	 /**
	  * GuiManager allows commands to be activated from the toolbar
	  * and control panel
	  */
	 GuiManager gui;
	 
    // --------------------------------------------------------------
    
    // Default constructor for the ToolBar
    public ToolBar() {
        //Create the toolbar
        menuBar = new JMenuBar();
    }
    
    // -------------------------------------------------------------
    
    // Methods...

    /**
     * Method to add the MenuBar as the toolbar of the GUI
     * @return the toolbar
     */
    protected JMenuBar createMenuBar() {
        // Menu to contain the Buttons on the toolbar
        JMenu menu = null;

        // Add the "COMMAND" button
        menu = new JMenu(COMMAND);
        // Keyboard shortcut to access this COMMAND menu (button) is "Alt C"
        menu.setMnemonic(KeyEvent.VK_C);
        // Description of this COMMAND menu
        menu.getAccessibleContext().setAccessibleDescription(
            "Access commands to run the simulator");
        // Name the event associated with clicking this COMMAND button
        menu.setActionCommand(COMMAND);
        // "Help" tip displayed when the cursor lingers over the COMMAND button
        menu.setToolTipText("Access commands to run the simulator");
        menuBar.add(menu);      // "COMMAND" button added
        // Add options and submenus to the "COMMAND" button
        menu = fillCommandMenu(menu);

        

        // Add the "HELP" button
        menu = new JMenu(HELP);
        // Keyboard shortcut to access this HELP menu (button) is "Alt H"
        menu.setMnemonic(KeyEvent.VK_H);
        // Description of the Help menu
        menu.getAccessibleContext().setAccessibleDescription(
            "Look at the user manual");
        // Name the event associated with clicking this HELP button
        menu.setActionCommand(HELP);
        // "Help" tip displayed when the cursor lingers over the HELP button
        menu.setToolTipText("Look at the user manual");
        menu.addActionListener(this);       // Allow user to click on HELP
        // Add options and submenus to the "HELP" button
        menu = fillHelpMenu(menu);
        menuBar.add(menu);                  // "HELP" button added

        return menuBar;
    }
    
    /**
     * Method to add items to the "COMMAND" Menu
     * @param menu is to be added with options (buttons) or submenus
     * @return the submenu for the COMMAND button
     */
    public JMenu fillCommandMenu(JMenu menu) {
// Menu to contain the options of each button on the toolbar
//JMenu submenu = null;

        // Create Button for run option with the keyboard shortcut "Alt R"
        JMenuItem menuItem = new JMenuItem(RUN, KeyEvent.VK_R);
        // Name the event associated with clicking this RUN button
        menuItem.setActionCommand("Run");
        // "Help" tip displayed when the cursor lingers over the RUN button
        String tip = "Click and run the software";
        menuItem.setToolTipText(tip);   // Add help tip for the RUN button
        // Set the description of the RUN button
        menuItem.getAccessibleContext().setAccessibleDescription(tip);
        // Allow this RUN button to be clicked
        menuItem.addActionListener(this);
        menu.add(menuItem);             // RUN button added
        
        // Create Button for pause option with the keyboard shortcut "Alt P"
        menuItem = new JMenuItem(PAUSE, KeyEvent.VK_P);
        // Name the event associated with clicking this PAUSE button
        menuItem.setActionCommand("Pause");
        // "Help" tip displayed when the cursor lingers over the PAUSE button
        tip="Pause the simulation";
        menuItem.setToolTipText(tip);   // Add help tip for the PAUSE button
        // Set the description for the PAUSE button
        menuItem.getAccessibleContext().setAccessibleDescription(tip);
        // Allow this PAUSE button to be clicked
        menuItem.addActionListener(this);
        menu.add(menuItem);             // PAUSE button added
        
        // Create button for stop option with the keyboard shortcut "Alt S"
        menuItem = new JMenuItem(STOP, KeyEvent.VK_S);
        // Name the event associated with clicking this STOP button
        menuItem.setActionCommand("Stop");
        // "Help" tip displayed when the cursor lingers over the STOP button
        tip="Stop the simulation";
        menuItem.setToolTipText(tip);   // Add tip for the STOP button
        // Set the description for the STOP button
        menuItem.getAccessibleContext().setAccessibleDescription(tip);
        // Allow this STOP button to be clicked
        menuItem.addActionListener(this);
        menu.add(menuItem);             // STOP button added
        
        // Create button for exit option with the keyboard shortcut "Alt Q"
        menuItem = new JMenuItem(EXIT);
        // Name the event associated with clicking this EXIT button
        menuItem.setActionCommand(EXIT);
        // "Help" tip displayed when the cursor lingers over the EXIT button
        tip="Quit the NetSim program";
        menuItem.setToolTipText(tip);   // Add tip for the EXIT button
        // Set the fast access keyboard shortcut for the EXIT button
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        // Set the description for the EXIT button
        menuItem.getAccessibleContext().setAccessibleDescription(tip);
        // Allow this EXIT button to be clicked
        menuItem.addActionListener(this);
        menu.add(menuItem);             // EXIT button added
                
        return menu;
    }

    /**
     * Method to add items to the "HELP" Menu
     * @param menu is to be added with options (buttons) or submenus
     * @return submenu for HELP button
     */
    public JMenu fillHelpMenu(JMenu menu) {
// Menu to contain the options of each button on the toolbar
//JMenu submenu = null;

        // Create Button for GET_MANUAL option
        JMenuItem menuItem = new JMenuItem(GET_MANUAL);
        // Name the event associated with clicking this GET_MANUAL button
        menuItem.setActionCommand(GET_MANUAL);
        // "Help" tip displayed when cursor lingers over GET_MANUAL button
        String tip = "Get the user manual of NetSim";
        menuItem.setToolTipText(tip);   // Add help tip for GET_MANUAL button
        // Set the description of the GET_MANUAL button
        menuItem.getAccessibleContext().setAccessibleDescription(tip);
        // Allow this GET_MANUAL button to be clicked
        menuItem.addActionListener(this);
        menu.add(menuItem);             // GET_MANUAL button added
        
        // Create Button for GET_DETAILS option
        menuItem = new JMenuItem(GET_DETAILS);
        // Name the event associated with clicking this GET_DETAILS button
        menuItem.setActionCommand(GET_DETAILS);
        // "Help" tip displayed when cursor lingers over GET_DETAILS button
        tip = "Get the development history of NetSim";
        menuItem.setToolTipText(tip);   // Add help tip for GET_DETAILS button
        // Set the description of the GET_DETAILS button
        menuItem.getAccessibleContext().setAccessibleDescription(tip);
        // Allow this GET_DETAILS button to be clicked
        menuItem.addActionListener(this);
        menu.add(menuItem);             // GET_DETAILS button added
        
        return menu;
    }
  
    /**
     * Method to perform some action when the user clicks any button
     * on the toolbar
     * @param e is the ActionEvent resulting from the user's interaction
     *  with the GUI
     * @return nothing
     */

//    public void actionPerformed(ActionEvent e) {
        /**
         * Name of the Action that this user desires, or rather the name
         * of the button that this user clicked on
         */
//        String cmd = e.getActionCommand();
/*
        // For each button, handle the event when user clicks it
        if (COMMAND.equals(cmd)) {
            // COMMAND menu clicked
Debugger.debug("Button pressed is: "+COMMAND);
// Don't worry about this, it works... 9/10/04 1225 hrs
        } else if (HELP.equals(cmd)) {
            // HELP menu clicked
Debugger.debug("Button pressed is: "+HELP);
// Don't worry about this, it works... 9/10/04 1225 hrs
        } else if (RUN.equals(cmd)) {
            // RUN button clicked
Debugger.debug("Button pressed is: "+RUN);
        } else if (PAUSE.equals(cmd)) {
            // PAUSE button clicked
Debugger.debug("Button pressed is: "+PAUSE);
        } else if (STOP.equals(cmd)) {
            // STOP button clicked
Debugger.debug("Button pressed is: "+STOP);
        } else if (EXIT.equals(cmd)) {
            // EXIT button clicked
Debugger.debug("Button pressed i1s: "
+EXIT+"; current thread running is: "+Thread.currentThread().toString());
            System.exit(0);                     // END THE PROGRAM
        }else if (GET_MANUAL.equals(cmd)) {
            // STOP button clicked
Debugger.debug("Button pressed is: "+GET_MANUAL);
        }else if (GET_DETAILS.equals(cmd)) {
            // STOP button clicked
Debugger.debug("Button pressed is: "+GET_DETAILS);
        }else{
            Debugger.printErr("There is no button on the toolbar"
                +"named "+cmd);
        }
    }
*/
	 
	 /**
     * Method to perform some action when the user clicks any button
     * on the toolbar
     * @param e is the ActionEvent resulting from the user's interaction
     *  with the GUI
     * @return nothing
    */
    public void actionPerformed(ActionEvent e) {
        /**
         * Name of the Action that this user desires, or rather the name
         * of the button that this user clicked on
         */
        String cmd = e.getActionCommand();

        // For each button, handle the event when user clicks it
        if (COMMAND.equals(cmd)) {
            // COMMAND menu clicked
Debugger.debug("Button pressed is: "+COMMAND);
// Don't worry about this, it works... 9/10/04 1225 hrs
        } else if (HELP.equals(cmd)) {
            // HELP menu clicked
Debugger.debug("Button pressed is: "+HELP);
// Don't worry about this, it works... 9/10/04 1225 hrs
		  } else if (RUN.equals(cmd)) {
            // RUN button clicked
Debugger.debug("Button pressed is: "+RUN);
// START THE SIMULATION THREAD
if(simu == null) {
	simu = new SimulationThread(gui);
	simu.setPlayButton("Pause");
	simu.setStopButton("Stop");
	simu.start();
}else{
	simu.setPlayButton("Pause");
	simu.setStopButton("Stop");
}
ctrl.pauseSimulator(false);

        } else if (EXIT.equals(cmd)) {
            // EXIT button clicked
Debugger.debug("Button pressed i1s: "
+EXIT+"; current thread running is: "+Thread.currentThread().toString());
            System.exit(0);                     // END THE PROGRAM
        }else if (GET_MANUAL.equals(cmd)) {
            // GET_MANUAL button clicked
Debugger.debug("Button pressed is: "+GET_MANUAL);
        }else if (GET_DETAILS.equals(cmd)) {
            // GET_DETAILS button clicked
Debugger.debug("Button pressed is: "+GET_DETAILS);
        }else{
ctrl.actionPerformed(e);
        }

		 
//System.out.println("Command ENTERED is: "+e.getActionCommand());
	 }
	 
	 /**
	  * Method to assign the Control Panel of the GUI to the toolbar's
	  * environment so that it can be used to handle user interactions
	  * @param control is an instance of the ControlPanel of the GUI
	  */
	 public void setCtrlPanel(ControlPanel control) {
		 ctrl = control;
	 }
	 
	 /**
	  * Method to pass a copy of the GuiManager to the toolbar so that
	  * it can start/run the simulation
	  * @param g is the GuiManager
	  * @return nothing
	  */
	 public void passGuiManager(GuiManager g) {
	 	gui = g;
	 }
}
