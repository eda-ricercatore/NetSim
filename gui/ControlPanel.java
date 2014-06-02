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
import population.graph.*;
import population.*;

/**
 * Class to create the control panel for the GUI
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
public class ControlPanel extends JPanel implements ActionListener {
	// Declare and initialise instance variables
    
	// ControlPanel for the GUI makes use of this Panel
	private JPanel panel;

	// Holder for the play/pause picture
	ImageIcon playpause;
	// Holder for the halt picture
	ImageIcon halt;
	
	/**
	 * Create thread to simulate the optimization of the telecommunications
	 * network
	 */
	SimulationThread simu;
	
	// Indicates if the PAUSE command button is shown 
//	boolean resume = false;
	// Indicates if the STOP command button is shown
//	boolean end = false;

	// Indicates the label of the button to run the simulation
	private static final String RUN = "Run";
	// Indicates the label of the button to resume the simulation
//	private static final String RESUME = "Resume";
	// Indicates the label of the button to stop the simulation
	private static final String STOP = "Stop";
	// Indicates the label of the button to clear the simulation
//	private static final String CLEAR = "Clear";
	// Indicates the label to get help about using NetSim
//	private static final String HELP = "Help";
	// Indicates the label to pause the simulation
	private static final String PAUSE = "Pause";
	// Indicates the label to do nothing
	private static final String DO_NOTHING = "Do nothing";
   
// Current GuiManager that creates, updates, and displays the Control Panel
GuiManager gm;

	// Indicates if the simulation is in play: true (play), false (pause/stop)
	boolean pauseSimulation = false;
	
	// Holder for the graph of the telecommunication network topology
	GraphImp network;
	Chromosome fittestChromo;

	// Dimensions of the control panel
	public final int ctrlWidth = 500;
	public final int ctrlHeigth = 650;
	
	/**
	 * Indicates the label to get the manual for the current version
	 * of NetSim
	 */
//	private static final String GET_MANUAL = "Get manual";
    
	/**
	 * Indicates the label to get details about the current version
	 * of NetSim
	 */
//	private static final String GET_DETAILS = "Get details";
    
	/**
	 * Indicates the label to quit NetSim
	 */
//	private static final String EXIT = "Exit";
    
	// --------------------------------------------------------------

	// Default constructor for the ControlPanel
	public ControlPanel() {

		// Create the Control Panel
//		panel = new JPanel();
      
//		panel.setSize(1000,1300);
      
		/**
	 	 * Arrange the Graphical components of this GUI vertically,
		 * from top to bottom
		 */
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		// Create a border with the title "Control Panel" for this Panel
//		panel.setBorder(BorderFactory.createTitledBorder("Control Panel"));
        
		// Temporary holder for JPanel to allow changes to its X/Y alignments
//		JPanel temp = new JPanel();
        
		// Adding the Graphical components of this GUI
//		temp = createLegend();
//		temp.setAlignmentX(LEFT_ALIGNMENT);
//		panel.add(temp);          // Add the Legend Panel
        
        
		// Add a separator to separate the Legend from the Information panel
//		JSeparator separator = new JSeparator();
//		panel.add(separator);
        
        
//		temp = createInfoPanel();
//		temp.setAlignmentX(LEFT_ALIGNMENT);
//		panel.add(temp);       // Add the Information Panel
      //        panel.add(separator);
gm = new GuiManager();
		fittestChromo = new Chromosome();
		updateControlPanel();
		JPanel temp = createCommandButtons(RUN,DO_NOTHING);
		temp.setAlignmentX(LEFT_ALIGNMENT);
		panel.add(temp);       // Add the Control Buttons
	}
	
	// Standard constructor to update the control panel
	public ControlPanel(String commence, String finish, GuiManager gui) {
// *********************
gm = gui;
		updateControlPanel();
		JPanel temp = createCommandButtons(commence,finish);
		temp.setAlignmentX(LEFT_ALIGNMENT);
		panel.add(temp);       // Add the Control Buttons
	}
    
	// -------------------------------------------------------------
    
	// Methods...

	/**
	 * Method to update the control panel
	 * @return nothing
	 */
	public void updateControlPanel() {
		// Create the Control Panel
		panel = new JPanel();
      
		panel.setSize(ctrlWidth,ctrlHeigth);
      
		/**
	 	 * Arrange the Graphical components of this GUI vertically,
		 * from top to bottom
		 */
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		// Create a border with the title "Control Panel" for this Panel
		panel.setBorder(BorderFactory.createTitledBorder("Control Panel"));
        
		// Temporary holder for JPanel to allow changes to its X/Y alignments
		JPanel temp = new JPanel();
        
		// Adding the Graphical components of this GUI
		temp = createLegend();
		temp.setAlignmentX(LEFT_ALIGNMENT);
		panel.add(temp);          // Add the Legend Panel
        
        
		// Add a separator to separate the Legend from the Information panel
		JSeparator separator = new JSeparator();
		panel.add(separator);
        
        
		temp = createInfoPanel();
		temp.setAlignmentX(LEFT_ALIGNMENT);
		panel.add(temp);       // Add the Information Panel
      //        panel.add(separator);
	}

	/**
	 * Method to obtain the Control Panel of the GUI
	 * @return the Control Panel of the GUI
	 */
	public JPanel getPanel() {
		panel.validate();
      //panel.setEnable(true);
        
		// Make this graphical panel opaque and visible so that it can be seen
		panel.setVisible(true);
		panel.setOpaque(true);
		return panel;
	}
   
    /**
     * Method to add the Legend to the GUI
     * @return the Legend
     */
	public JPanel createLegend() {
		// Create a panel and add components to it.
		JPanel legend = new JPanel();
		// Arrange entries of the Legend vertically
		legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS));
		// Create a border with the title "Legend" for this Legend
		legend.setBorder(BorderFactory.createTitledBorder("Legend"));
		// Label for entries of the Legend Panel
		JLabel pictureLabel;
        
		// Add the Server icon
		ImageIcon picture = new ImageIcon("gui/images/Server.jpg");
		// if the image file was found
		if (picture != null) {
			// Create a label for the "Server Image"
			pictureLabel = new JLabel("Server", picture,
			SwingConstants.LEFT);
		}else{
			pictureLabel = new JLabel("Server");
		}
      //pictureLabel.setAlignmentX(CENTER_ALIGNMENT);
		legend.add(pictureLabel);
        
		// Add the Client icon
		picture = new ImageIcon("gui/images/Client.jpg");
		// if the image file was found
		if (picture != null) {
			// Create a label for the "Client Image"
			pictureLabel = new JLabel("Client", picture,
				SwingConstants.LEFT);
		}else{
			pictureLabel = new JLabel("Client");
		}
      //pictureLabel.setAlignmentX(CENTER_ALIGNMENT);
		legend.add(pictureLabel);
        
		// Add the Server-Client Link icon
		picture = new ImageIcon("gui/images/Server-Client-link.jpg");
		// if the image file was found
		if (picture != null) {
			// Create a label for the "Server-Client link Image"
			pictureLabel = new JLabel("Server-Client link", picture,
				SwingConstants.LEFT);
		}else{
			pictureLabel = new JLabel("Server-Client link");
		}
      //pictureLabel.setAlignmentX(CENTER_ALIGNMENT);
		legend.add(pictureLabel);
        
		// Add the Server-Server Link icon
		picture = new ImageIcon("gui/images/Server-Server-link.jpg");
		// if the image file was found
		if (picture != null) {
		// Create a label for the "Server-Server link Image"
		pictureLabel = new JLabel("Server-Server link", picture,
			SwingConstants.LEFT);
		}else{
			pictureLabel = new JLabel("Server-Server link");
		}
      //pictureLabel.setAlignmentX(CENTER_ALIGNMENT);
		legend.add(pictureLabel);
        
		//Make it the content pane.
		legend.setOpaque(true);
		return legend;
	}
    
    
	/**
	 * Method to add the Information Panel to the GUI
	 * @return the Information Panel
	 */
	public JPanel createInfoPanel() {
		// Create a panel and add components to it.
		JPanel info = new JPanel();
		// Arrange entries of the Information Panel vertically
		info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
		// Create a border with the title "Information Panel" for this Legend
		info.setBorder(BorderFactory.createTitledBorder(
			"Information Panel"));
		// Label for entries of the Information Panel
		JLabel infoLabel;
        
		/**
		 * Diplay the number of Clients in the best Network (top Chromosome)
		 * of this evolution
		 */
		/**
		 * String should read {"Number of Clients: " + 
		 * method getNumClients() for Top Chromosome}
		 */
		infoLabel = new JLabel("Number of Clients: ");
		info.add(infoLabel);
        
		/**
		 * Diplay the number of Servers in the best Network (top Chromosome)
		 * of this evolution
		 */
		/**
		 * String should read {"Number of Servers: " + 
		 * method getNumServers() for Top Chromosome}
		 */
		infoLabel = new JLabel("Number of Servers: ");
		info.add(infoLabel);
        
		/**
		 * Diplay the number of generations evolved in this simulation
		 */
		/**
		 * String should read {"Number of generations evolved: " + 
		 * method getNumGen()}
		 */
		infoLabel = new JLabel("Number of generations");
		info.add(infoLabel);
		infoLabel = new JLabel("evolved: ");
		info.add(infoLabel);
        
		/**
		 * Diplay the number of generations to be evolved in this simulation
		 */
		/**
		 * String should read {"Number of generations to be evolved
		 * in this simulation: " + method getNumGen()}
		 */
		infoLabel = new JLabel("Number of generations");
		info.add(infoLabel);
		infoLabel = new JLabel("to be evolved in this");
		info.add(infoLabel);
		infoLabel = new JLabel("simulation : ");
		info.add(infoLabel);
        
		//Make it the content pane.
		info.setOpaque(true);
		return info;
	}
        
	/**
	 * Method to add the Command Buttons to the GUI
	 * @return the Command Buttons' Panel
	 */
	public JPanel createCommandButtons(String play, String stop) {
		// Create a panel and add components to it.
		JPanel cmd = new JPanel();
		// Arrange entries of the Command Buttons' Panel vertically
		cmd.setLayout(new BoxLayout(cmd, BoxLayout.Y_AXIS));
		// Create a border with the title "Command Buttons" for this Legend
		cmd.setBorder(BorderFactory.createTitledBorder(
			"Command Buttons"));
		// Button for entries of the Command Buttons' Panel
		JButton button;
      
		// Adding the RUN/PAUSE button for the GUI
//		if(play.equals(RUN)) {
//			resume = false;
//		}else{
//			resume = true;
//		}
		ImageIcon picture = setImageIcon(play);
		// if the image file was found
		if (picture != null) {
		// Create a button with an image to run/pause NetSim
			button = new JButton(play, picture);
		}else{
			// Else, create a button to run/pause NetSim
			button = new JButton(play);
		}
		button.setActionCommand(play);
		button.addActionListener(this);
		cmd.add(button);
        
		// Adding the STOP/DO_NOTHING button for the GUI
//		if(stop.equals(STOP)) {
//			end = true;
//		}else{
//			end = false;
//		}
		picture = setImageIcon(stop);
		// if the image file was found
		if (picture != null) {
			/**
			 * Create a button with an image to stop the NetSim
			 * simulation or do nothing
			 */
			button = new JButton(stop, picture);
		}else{
			/**
			 * Else, create a button to stop the NetSim simulation
			 * or do nothing
			 */
			button = new JButton(stop);
		}
		button.setActionCommand(stop);
		button.addActionListener(this);
		cmd.add(button);
        
		//Make it the content pane.
		cmd.setOpaque(true);
		return cmd;
	}
	
	/**
	 * Method to change the Command Buttons of the Control Panel
	 * @param action indicates the next action that the button 
	 *	 should represent
	 * @return the action that the command button represents
	 */
	public ImageIcon setImageIcon(String action) {
		if(action.equals(RUN)) {
			playpause = new ImageIcon("gui/images/Play.jpg");
			return playpause;
		}else if(action.equals(PAUSE)) {
			playpause = new ImageIcon("gui/images/Pause.jpg");
			return playpause;
		}else if(action.equals(STOP)) {
			halt = new ImageIcon("gui/images/Stop.jpg");
			return halt;
		}else{
			// The action must be DO_NOTHING
			halt = new ImageIcon("gui/images/shaded-stop.jpg");
			return halt;
		}
	}
    
	/**
	 * Method to perform some action when the user clicks any command button
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
		if (RUN.equals(cmd)) {
			// RUN button clicked
			Debugger.debug("RUN Button pressed is: "+RUN);
			// Don't worry about this, it works... 9/10/04 1225 hrs
			
//			ControlPanel()
//			createCommandButtons(PAUSE,STOP);
//gm.updateCtrlPanel(PAUSE,STOP);
//gm.displayGUI(PAUSE,STOP);

// START THE SIMULATION THREAD
if(simu == null) {
	simu = new SimulationThread(gm);
	simu.setPlayButton(PAUSE);
	simu.setStopButton(STOP);
	simu.start();
}else{
	simu.setPlayButton(PAUSE);
	simu.setStopButton(STOP);
}
pauseSimulator(false);

		}else if (PAUSE.equals(cmd)) {
			// PAUSE button clicked
			Debugger.debug("Button pressed is: "+PAUSE);
// Remove this
if(simu == null) { simu = new SimulationThread(gm); }
// End of to-be-removed-stuff

gm.displayGUI(RUN,STOP,simu.getFittestChromo());
pauseSimulator(true);
try {
	while(pausedSimulation()) {
		simu.sleep(Long.MAX_VALUE);
	}
}catch(InterruptedException ie) {
	Debugger.debug("resume simulation");
}
			
//			createCommandButtons(RUN,STOP);

		}else if (STOP.equals(cmd)) {
			// STOP button clicked
			Debugger.debug("STOP Button pressed is: "+STOP);

//			createCommandButtons(RUN,DO_NOTHING);
network = new GraphImp();
fittestChromo = new Chromosome();

gm.displayGUI(RUN,DO_NOTHING,fittestChromo);
/*
try this!!!
if(simu != null) {
	simu = null;
}
*/

/*		}else if (EXIT.equals(cmd)) {
			// EXIT button clicked
			Debugger.debug("Button pressed i1s: "
				+EXIT+"; current thread running is: "+Thread.currentThread().toString());
			System.exit(0);                     // END THE PROGRAM
*/
		}else if (DO_NOTHING.equals(cmd)) {
			// STOP button clicked
			Debugger.debug("Button pressed is: "+DO_NOTHING);
/*		}else if (GET_DETAILS.equals(cmd)) {
			// STOP button clicked
			Debugger.debug("Button pressed is: "+GET_DETAILS);
*/
		}else{
			Debugger.printErr("There is no button on the toolbar"
				+"named "+cmd);
		}
	}
	
	/**
	 * Method to set the PLAY/PAUSE state of the Simulator
	 * @param pauseSim indicates if the simulator is paused
	 * @return nothing
	 */
	public void pauseSimulator(boolean pauseSim) {
		pauseSimulation = pauseSim;
	}
	
	/**
	 * Method to determine if the Simulation has paused
	 * @return pauseSimulation to indicate if it has paused
	 *	 return true if paused, else return false
	 */
	public boolean pausedSimulation() {
		return pauseSimulation;
	}
	
	/**
	 * Method to pass the simulation thread to the control panel
	 * Allows toolbar to run/pause the simulation and still allow
	 * the control panel to determine its state of simulation
	 * @return nothing
	 */
	public void passSimulationThread(SimulationThread s) {
		simu = s;
	}
}