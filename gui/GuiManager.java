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
import java.awt.geom.*;
import population.graph.*;
import population.*;

/**
 * Class to create the GUI for the "NetSim" software
 * Java Swing components are primarily used since they are more versatile
 * than the AWT package.
 * @author Zhiyang Ong and Andy Lo
 * @version 0.3.1-zhiyang
 * @acknowledgement J Lewis & W Loftus, Java Software Solutions:
 *  Foundations of Program Design, 2nd Edition, Addison-Wesley,
 *  Reading, Massachusetts
 *  and Craig Eales for assistance in the development of the GUI
 */
public class GuiManager extends JFrame {
    // Declaration and instantiation of instance variables...
    
    // Direction indicator for insertion of Graphical Components
    public static boolean RIGHT_TO_LEFT = false;
    // Layered panel to contain Graphical Components
    public JLayeredPane layeredPane;
// GridBag panel to contain Graphical Components
public GridBagLayout gridbag;
// Dimensional and layout constraints of the GridBag
public GridBagConstraints constraint;

	// Indicates the label of the button to run the simulation
	private static final String RUN = "Run";
	// Indicates the label of the button to stop the simulation
	private static final String STOP = "Stop";
	// Indicates the label to pause the simulation
	private static final String PAUSE = "Pause";
	// Indicates the label to do nothing
	private static final String DO_NOTHING = "Do nothing";

// Container for the Control Panel
JPanel ctrlPanel;

	/**
	 * Graph data structure to model the topology of the network
	 * Contains information about the nodes and edges
	 */
	GraphImp graph = new GraphImp(RunNetSim.getWidth(),RunNetSim.getHeight());
	
	// Fittest chromosome of the population which topology is to be sketched
	Chromosome fittestChromo = new Chromosome();

    // -------------------------------------------------------------------
    
    // Default constructor...
    public GuiManager() {
        // Indicate the title of the "NetSim" software
        super("NetSim v1.0");
        // Set the size of the GUI Window/Screen for the software
// setSize(1000,1000);                  // currently redundant
//System.out.println("Created the GuiManager");        
        // Flash the "Splash Image"
//System.out.println("Display the GuiManager"); 
        displayGUI(RUN,DO_NOTHING,fittestChromo);
    }
    
    // ------------------------------------------------------------------
    
    // Handel window resizing
	
	private class Work extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			e.getComponent().setSize(RunNetSim.getHeight(),RunNetSim.getHeight());
		}
	}
    
    // Methods...
    
    /**
     * Method to display the GUI of "NetSim"
     * @return nothing
     */
    public void displayGUI(String play, String end, Chromosome chromo) {
		// Use Chromosome instead of GraphImp
		fittestChromo = chromo;
        // Set up the contents of the GUI
        layeredPane = new JLayeredPane();
//        layeredPane.setLayout(new GridLayout(1,2));
gridbag = new GridBagLayout();
//layeredPane.setLayout(gridbag);
constraint = new GridBagConstraints();

//constraint.fill = GridBagConstraints.RELATIVE;
//layeredPane.setLayout(gridbag);

        // Create a toolbar to add it to the GUI
        ToolBar toolBar = new ToolBar();
        // Add the toolbar to the GUI
        this.setJMenuBar(toolBar.createMenuBar());

/**
 * don't getContentPane() and set its layout
 * or add components to it - it can't work
 */
//this.setLayout(new GridLayout(2,1));
            
        // Add graphical components to this GUI
        
        // Add the Workspace Panel of the GUI to the Frame
        JPanel currentPane;

constraint.fill = GridBagConstraints.RELATIVE;
layeredPane.setLayout(gridbag);

//ControlPanel ctrl = new ControlPanel();
//currentPane = ctrl.getPanel();
//*******************************************************************
//currentPane = updateCtrlPanel(RUN, DO_NOTHING);
ControlPanel ctrl = new ControlPanel(play, end, this);
/**
 * Pass the control panel to the toolbar so that commands can be run
 * from the toolbar
 */
toolBar.setCtrlPanel(ctrl);
toolBar.passGuiManager(this);
currentPane = ctrl.getPanel();
ctrlPanel = currentPane;
//*******************************************************************

//        currentPane = addComponentsToPane((JPanel)this.getContentPane());
        // Make the Workspace panel opaque and visible so that it can be seen
        currentPane.setOpaque(true);
        currentPane.setVisible(true);
        // Set up the Workspace Panel by adding it to the Layered Pane        
        

        layeredPane.add(currentPane);
//        layeredPane.setPosition(currentPane,0);
//layeredPane.validate();
        
        // Add the Control Panel of the GUI to the Frame

//        ControlPanel ctrl = new ControlPanel();
//        currentPane = (JPanel) ctrl.getPanel();

constraint.fill = GridBagConstraints.REMAINDER;
layeredPane.setLayout(gridbag);

//mod this
//currentPane = addComponentsToPane((JPanel)this.getContentPane());
//MOD THIS 7 MAR 05 - 0522 HRS

// Modified by Zhiyang Ong - 26 May 2005
//currentPane = new Workspace(fittestChromo);


/*
Workspace wkspace = new Workspace();
currentPane = wkspace.getWorkspace();
*/

        // Make the Workspace panel opaque and visible so that it can be seen
        currentPane.setOpaque(true);
        currentPane.setVisible(true);
        // Set up the Workspace Panel by adding it to the Layered Pane
        layeredPane.add(currentPane);
//        layeredPane.setPosition(currentPane,1);
        
        /**
         * Add all the Panels (Control Panel and Workspace) into
         * the Window Frame
         */
        layeredPane.validate();
//        this.setLayeredPane(layeredPane);

        this.setContentPane(layeredPane);
                
        
//  Get the dimensions of the Window
//int width = (int) this.getContentPane().getMinimumSize().getWidth();
//int height = (int) this.getContentPane().getMinimumSize().getHeight();
//  Get the dimensions of the screen (monitor)
//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//  Set the Bounds of the Window to center it
//this.setBounds( (screenSize.width)/7,
//    (screenSize.height)/5, width, height);
        
        // Size the Window appropriately on the screen
        this.pack();
        // Display the window
        this.setVisible(true);
//System.out.println("GUI displayed"); 
    }
    
	/**
	 * Method to update the control panel
	 * By default, the control panel shows the PLAY and DO_NOTHING buttons
	 * @param run indicates if the PLAY or PAUSE button should be displayed
	 * @param run indicates if the STOP or DO_NOTHING button should be displayed
	 * @return the updated control panel
	 * Method is NOT USED.
	 */
/*
	public JPanel updateCtrlPanel(String run, String halt) {
		ControlPanel ctrl = new ControlPanel(run, halt, this);
		ctrlPanel = ctrl.getPanel();
		return ctrlPanel;
	}
*/
	 
    /**
     * Method to add GUI components to this GUI Container
     * @param pane is the Container containing all of the GUI
     *  components in this GUI
     * @return nothing
	  * @deprecated since v0.3.4 - 9 March 2005
     */
//    public JPanel addComponentsToPane(JPanel pane) {
        /**
         * Does this GUI screen use BorderLayout to arrange its components
         * in this GUI container to give it its visual appearance?
         */
 //       if (!(pane.getLayout() instanceof BorderLayout)) {
            /**
             * No, fill this container with an uneditable text message
             * indicating that the wrong type of layout is selected
             */
//            pane.add(new JLabel("Container doesn't use BorderLayout!"));
//           return null;             // exit
//       }
        
        // Yes... Shall the components be arranged from right to left?
/*
        if (RIGHT_TO_LEFT) {
            // Yes, arrange them that way...
            pane.setComponentOrientation(
                java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        }
*/        
        /**
         * ### Instead of inserting proper panels, buttons and
         * ### popup Windows in this iteration, I will just use buttons
         * ### for the time being.
         *
         * ### Will replace the buttons later
         */
        
        // Adding the display scroll pane/workspace for displaying the network
//        JButton button = new JButton("Scroll pane for network");
        // Make the left component big since it is displaying the network
//        button.setPreferredSize(new Dimension(800, 500));

//ImageIcon server = new ImageIcon("gui/images/SPLASH-copy.jpg");
//JLabel serverLabel = new JLabel("Server", server, SwingConstants.LEFT);
//        pane.add(serverLabel, BorderLayout.LINE_START);

//pane.add(new Workspace());

        /**
         * Adding the display panel of the GUI and the control buttons
         * for (play/pause) and stop
         */
//        button = new JButton("Display panel and control buttons");
//        pane.add(button, BorderLayout.LINE_END);
//        pane.add(new ControlPanel(), BorderLayout.LINE_END);

/*
ControlPanel ctrl = new ControlPanel();
JPanel ctrlPanel = (JPanel)ctrl.getPanel();
ctrlPanel.setOpaque(true);
ctrlPanel.setVisible(true);
pane.add(ctrlPanel);
pane.validate();
pane.setEnabled(true);
*/
        
        // Make this graphical panel opaque and visible so that it can be seen
//        pane.setVisible(true);
//        pane.setOpaque(true);
//System.out.println("Added components to the gui"); 
//        return pane;
//    }
}
