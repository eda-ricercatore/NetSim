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
import javax.swing.event.*;
import javax.swing.text.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import utility.*;

/**
 * Class to query user for the following:
 * #Type and combinations of cost functions to be selected
 * #Number of servers and clients in the telecommunications network
 * #Number of generations to evolve the network using 
 *	steady-state evolutionary, and genetic, algorithms
 * #Size of the set of networks to evolve - population size of chromosomes
 * Java Swing components are primarily used since they are more versatile
 * than the AWT package.
 * @author Zhiyang Ong and Andy Lo
 * @version 0.3.1-zhiyang
 * @acknowledgement J Lewis & W Loftus, Java Software Solutions:
 *  Foundations of Program Design, 2nd Edition, Addison-Wesley,
 *  Reading, Massachusetts;
 *  Craig Eales for assistance in the development of the GUI;
 *	Rogers Cadenhead, "SAMS Teach Yourself Java 2 in 24 Hours," 2nd Edition,
 *	Techmedia, New Delhi, 2001
 *	Reference Documentation: Java 2 Platform Standard Edition (J2SE):
 *	The Java Tutorial: Creating a GUI with JFC/Swing: Using Swing Components:
 *	A Visual Index to the Swing: Buttons (How to Use Buttons, Check Boxes, and
 *	Radio Buttons): How to Use Check Boxes. Sun Microsystems.
 *	Santa Clara, CA [Online]. Available:
 *	http://java.sun.com/docs/books/tutorial/uiswing/components/button.html
 *	Reference Documentation: Java 2 Platform Standard Edition (J2SE):
 *	The Java Tutorial: Creating a GUI with JFC/Swing: Using Swing Components:
 *	Using Text Components: How to Use Formatted Text Fields. Sun Microsystems.
 *	Santa Clara, CA [Online]. Available:
 *	http://java.sun.com/docs/books/tutorial/uiswing/components/
 *	formattedtextfield.html
 *	Reference Documentation: Java 2 Platform Standard Edition (J2SE):
 *	The Java Tutorial: Creating a GUI with JFC/Swing: Using Swing Components:
 *	A Visual Index to the Swing: Buttons (How to Use Buttons, Check Boxes, and
 *	Radio Buttons): Examples that Use Various Kinds of Buttons. Sun Microsystems.
 *	Santa Clara, CA [Online]. Available:
 *	http://java.sun.com/docs/books/tutorial/uiswing/components/example-1dot4/
 *	index.html
 */

// Class definition...

// ========================================================================

public class SelectionChoices extends JFrame
	//implements KeyListener, ActionListener, ItemListener, PropertyChangeListener {
	implements ActionListener, ItemListener, PropertyChangeListener {
    // Declaration of instance variables...

	// Number of servers in the telecommunications network
//	private int numServers = 200;			// default value
private int numServers = 5;
	/**
	 * Ratio of clients to servers for default values for the
	 * number of clients per server
	 */
	private int ratioClientToServer = 30;
	// Number of clients in the telecommunications network
//	private int numClients = numServers*ratioClientToServer;
private int numClients =50;
	/**
	 * Number of generations to evolve the network using 
	 * steady-state evolutionary, and genetic, algorithms
	 */
//	private int numGenEvolve = 1000;		// default value
private int numGenEvolve = 50;
	// Size of the set of networks to evolve - population size of chromosomes
//	private int popSize = 100;				// default value
private int popSize = 30;
	
	// Array of default values
	private int[] defaultVals = {numClients, numServers, numGenEvolve, popSize};
	
	// Formats to format and parse input (numbers)
	// Handle conversion of user input to numbers
	private NumberFormat clientsFormat;		// Number of clients
	private NumberFormat serversFormat;		// Number of servers
	private NumberFormat numGenFormat;		// Number of generations
	private NumberFormat popSizeFormat;		// Size of population
	
	
	// Checkbox entries for cost functions
	private JCheckBox checkboxCF1;
	private JCheckBox checkboxCF2;
	private JCheckBox checkboxCF3;
	private JCheckBox checkboxCF4;
	private JCheckBox checkboxCF5;
	private JCheckBox checkboxCF6;
	private JCheckBox checkboxCF7;
	private JCheckBox checkboxCF8;
	// Query user for input parameters
	private String askUser = "Please enter the following parameters for simulation";
	// Query the user for the number of clients
	private String askNumClients = "Number of clients in the network";
	// Query the user for the number of clients
	private String askNumServers = "Number of servers in the network";
	// Query the user for number of generations to evolve
	private String askNumGens = "Number of generations to evolve";
	// Query the user for the size of the population
	private String askPopSize = "Size of the population of chromosomes/networks";
	
	
	// Type and combinations of cost functions to be selected
	// Text labels of the cost functions...
	private String costFunc1 = "Minimum spanning tree";
	private String cf1Description =
		"Total edge cost of all edges in minimum spanning tree";
	private String costFunc2 = "Average distance travelled";
	private String cf2Description =
		"Average distance from any Node A to any other Node B";
	private String costFunc3 = "Total cost of all edges";
	private String cf3Description =
		"Total cost of all edges in the network";
	/*
	private String costFunc4 = "Maximum degree of separation";
	private String cf4Description =
		"Maximum diameter of the graph";
	private String costFunc5 = "Average degree of separation";
	private String cf5Description =
		"Average diameter of the graph";
	*/
	// ### Modified by Zhiyang Ong - 8 Jul
	// Changed the order of cost functions 4 and 5 appearing in the pop-up box
	private String costFunc5 = "Maximum degree of separation";
	private String cf5Description =
		"Maximum diameter of the graph";
	private String costFunc4 = "Average degree of separation";
	private String cf4Description =
		"Average diameter of the graph";
	/**
	 * ### Determine the load factor for servers only, since the load
	 * ### factor of clients are not of importance to the telecommunication
	 * ### network service providers - profit margin is.
	 * ### If a client uses a terminal or device of "obsolete" technology,
	 * ### and is unable  to access the network, the network providers
	 * ### should not have to concern themselves with the continual
	 * ### provision of service to them.
	 */
	private String costFunc6 = "Total load factor for all servers";
	private String cf6Description =
		"Sum the load factor for each server in the network";
	private String costFunc7 = "Average clustering coefficiency of servers";
	private String cf7Description =
		"Average clustering coefficient of servers in the network";
	private String costFunc8 = "Network resistance";
	private String cf8Description =
		"Model the network as an electrical network and determine its resistance";

	// Label to  users for numerical input
	private JLabel prompt;
	
	
	// Container for this JFrame
	private Container pane;
	
	
	// Label of the textbox
	private JLabel label;
	
	/**
	 * Formatted Text Fields for user input...
	 * ### A separate instance of Formatted Text Field is required for each
	 * ### input so that the processing of the user input is easier,
	 * ### and the parsed number can be assigned to the appropriate variables
	 */
	private JFormattedTextField clientsField;
	private JFormattedTextField serversField;
	private JFormattedTextField numGenField;
	private JFormattedTextField popSizeField;

	// Number of columns assigned to that Formatted Text Field
	private int numColumns = 10; 
	
	// Indicates the label of the button to run the simulation
	private static final String OK = "OK";

	
	// Dimensions of the pop-up window
	private int xDim = 140;
	private int yDim = 300;
	
	
	// Buffer for user input
	private StringBuffer buffer = new StringBuffer();
	
	// Current simulation thread that is running
	private Optimizer simuThread;
	
	// Number of selected cost functions
	private int numSelectedCostFns=0;
    
	private int[] userInput;
	private int[] costsFns;
	private boolean proceed=false;
	
	// -------------------------------------------------------------------

	// Default constructor...
	public SelectionChoices() {
		// Indicate the title of the "NetSim" software
		super("NetSim v1.0 - Query for user input");
		
		//simuThread = thread;
		
		// Size of pop-up window
		setSize(xDim,yDim);
		// When user clicks on the Window_Close button, close the pop-up window.
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set up the formats to handle user inputs
		setUpFormats();

		// Ask the user for the standard deviation
		queryUser();
	}
	
	// Standard constructor...
	public SelectionChoices(Optimizer thread) {
		// Indicate the title of the "NetSim" software
		super("NetSim v1.0 - Query for user input");
		
		simuThread = thread;
		
		// Size of pop-up window
		setSize(xDim,yDim);
		// When user clicks on the Window_Close button, close the pop-up window.
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set up the formats to handle user inputs
		setUpFormats();

		// Ask the user for the standard deviation
		queryUser();
    }
    
    // ------------------------------------------------------------------
    
    // Methods definition...
    
    /**
     * Method to ask the user for the standard deviation
     * @return nothing
     */
	public void queryUser() {
		// Determine layout pattern of container
		pane = getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));


		// Prompt user for input
		label = new JLabel("Select cost functions to optimize the "
			+"telecommunication network",SwingConstants.LEFT);
		// align the pop-up window
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setVerticalTextPosition(SwingConstants.TOP);
		pane.add(label);


		// Insert checkbox for first cost function
		checkboxCF1 = new JCheckBox(costFunc1);
		// Add tip for the first cost function
		checkboxCF1.setToolTipText(cf1Description);
		// Set the description for the first cost function
		checkboxCF1.getAccessibleContext().setAccessibleDescription(cf1Description);
		checkboxCF1.addItemListener(this);
		pane.add(checkboxCF1);

		// Insert checkbox for second cost function
		checkboxCF2 = new JCheckBox(costFunc2);
		// Add tip for the second cost function
		checkboxCF2.setToolTipText(cf2Description);
		// Set the description for the second cost function
		checkboxCF2.getAccessibleContext().setAccessibleDescription(cf2Description);
		checkboxCF2.addItemListener(this);
		pane.add(checkboxCF2);

		// Insert checkbox for third cost function
		checkboxCF3 = new JCheckBox(costFunc3);
		// Add tip for the third cost function
		checkboxCF3.setToolTipText(cf3Description);
		// Set the description for the third cost function
		checkboxCF3.getAccessibleContext().setAccessibleDescription(cf3Description);
		checkboxCF3.addItemListener(this);
		pane.add(checkboxCF3);

		// Insert checkbox for fourth cost function
		checkboxCF4 = new JCheckBox(costFunc4);
		// Add tip for the fourth cost function
		checkboxCF4.setToolTipText(cf4Description);
		// Set the description for the fourth cost function
		checkboxCF4.getAccessibleContext().setAccessibleDescription(cf4Description);
		checkboxCF4.addItemListener(this);
		pane.add(checkboxCF4);

		// Insert checkbox for fifth cost function
		checkboxCF5 = new JCheckBox(costFunc5);
		// Add tip for the fifth cost function
		checkboxCF5.setToolTipText(cf5Description);
		// Set the description for the fifth cost function
		checkboxCF5.getAccessibleContext().setAccessibleDescription(cf5Description);
		checkboxCF5.addItemListener(this);
		pane.add(checkboxCF5);

		// Insert checkbox for sixth cost function
		checkboxCF6 = new JCheckBox(costFunc6);
		// Add tip for the sixth cost function
		checkboxCF6.setToolTipText(cf6Description);
		// Set the description for the sixth cost function
		checkboxCF6.getAccessibleContext().setAccessibleDescription(cf6Description);
		checkboxCF6.addItemListener(this);
		pane.add(checkboxCF6);
		
		// Insert checkbox for seventh cost function
		checkboxCF7 = new JCheckBox(costFunc7);
		// Add tip for the seventh cost function
		checkboxCF7.setToolTipText(cf7Description);
		// Set the description for the seventh cost function
		checkboxCF7.getAccessibleContext().setAccessibleDescription(cf7Description);
		checkboxCF7.addItemListener(this);
		pane.add(checkboxCF7);
		
		// Insert checkbox for eight cost function
		checkboxCF8 = new JCheckBox(costFunc8);
		// Add tip for the eight cost function
		checkboxCF8.setToolTipText(cf8Description);
		// Set the description for the eight cost function
		checkboxCF8.getAccessibleContext().setAccessibleDescription(cf8Description);
		checkboxCF8.addItemListener(this);
		pane.add(checkboxCF8);

		/**
		 * Request use input for cost functions to maximise the
		 * cost functions with.
		 *
		 * ### Note that the text fields only accept input with a
		 * ### non-delimiter first character
		 *
		 * ### It does accept user input with delimiters occuring
		 * ### after the first character; the input number will
		 * ### consist of numbers occurring before the first
		 * ### delimieter.
		 *
		 * ### Input with a limiter as the first character will
		 * ### be rejected; previous valid input/default value
		 * ### (if no previous value exist) will be used instead
		 */
		prompt = new JLabel(askUser,SwingConstants.LEFT);
		prompt.setHorizontalTextPosition(SwingConstants.CENTER);
		prompt.setVerticalTextPosition(SwingConstants.BOTTOM);
		pane.add(prompt);
		// Ask user to enter the number of clients
		prompt = new JLabel(askNumClients,SwingConstants.LEFT);
		prompt.setHorizontalTextPosition(SwingConstants.CENTER);
		prompt.setVerticalTextPosition(SwingConstants.BOTTOM);
		//pane.add(prompt);
		// Formatted text field for users to enter number of clients
		clientsField = new JFormattedTextField(clientsFormat);
		clientsField.setValue(new Integer(numClients));
		clientsField.setColumns(numColumns);
		clientsField.addPropertyChangeListener("Number of CLIENTS",this);
		//txtField.addPropertyChangeListener("CLIENTS",this);
		prompt.setLabelFor(clientsField);//
		pane.add(prompt);//
		pane.add(clientsField);
		// Ask user to enter the number of servers
		prompt = new JLabel(askNumServers,SwingConstants.LEFT);
		prompt.setHorizontalTextPosition(SwingConstants.CENTER);
		prompt.setVerticalTextPosition(SwingConstants.BOTTOM);
		//pane.add(prompt);
		// Formatted text field for users to enter number of servers
		serversField = new JFormattedTextField(serversFormat);
		serversField.setValue(new Integer(numServers));
		serversField.setColumns(numColumns);
		serversField.addPropertyChangeListener("Number of SERVERS",this);
		prompt.setLabelFor(serversField);//
		pane.add(prompt);//
		pane.add(serversField);
		// Ask user to enter the number of generations
		prompt = new JLabel(askNumGens,SwingConstants.LEFT);
		prompt.setHorizontalTextPosition(SwingConstants.CENTER);
		prompt.setVerticalTextPosition(SwingConstants.BOTTOM);
		//pane.add(prompt);
		// Formatted text field for users to enter number of generations
		numGenField = new JFormattedTextField(numGenFormat);
		numGenField.setValue(new Integer(numGenEvolve));
		numGenField.setColumns(numColumns);
		numGenField.addPropertyChangeListener("Number of GENERATIONS",this);
		prompt.setLabelFor(numGenField);//
		pane.add(prompt);
		pane.add(numGenField);
		// Ask user to enter the population size
		prompt = new JLabel(askPopSize,SwingConstants.LEFT);
		prompt.setHorizontalTextPosition(SwingConstants.CENTER);
		prompt.setVerticalTextPosition(SwingConstants.BOTTOM);
		//pane.add(prompt);
		// Formatted text field for users to enter the population size
		popSizeField = new JFormattedTextField(popSizeFormat);
		popSizeField.setValue(new Integer(popSize));
		popSizeField.setColumns(numColumns);
		popSizeField.addPropertyChangeListener("POPULATION SIZE",this);
		prompt.setLabelFor(numGenField);//
		pane.add(prompt);
		pane.add(popSizeField);
		// Inform user to click OK to proceed
		prompt = new JLabel("Click OK to proceed",SwingConstants.LEFT);
		prompt.setHorizontalTextPosition(SwingConstants.CENTER);
		prompt.setVerticalTextPosition(SwingConstants.BOTTOM);
		pane.add(prompt);
		
		// Create the OK button for the user to click and proceed
		// Button for user to click to proceed with the NetSim simulation
		JButton button = new JButton(OK);
		button.setActionCommand(OK);
		button.addActionListener(this);
		// Add the button to the GUI Container
		pane.add(button);
		
		// Add the label to this Window
		this.setContentPane(pane);
		this.setVisible(true);
		// Size the Window appropriately on the screen
		this.pack();
		// Display the window
		this.show();
		// Replace default cursor with a wait cursor whilst loading screen
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}
	 
	/**
	 * Method to create and set up the Number Formats for the user inputs,
	 * which will parse numbers input by user
	 * @param nothing
	 * @return nothing
	 */
	private void setUpFormats() {
		/**
		 * Allow the user input to be parsed into the appropriate
		 * number formatters
		 */
		clientsFormat = NumberFormat.getNumberInstance();
		serversFormat = NumberFormat.getNumberInstance();
		numGenFormat = NumberFormat.getNumberInstance();
		popSizeFormat = NumberFormat.getNumberInstance();
	}
	 
	 /**
	 * Method to perform some action when the user clicks the "OK" button
	 * @param ae is the ActionEvent resulting from the user's interaction
	 *  with the GUI - clicking the OK button
	 * @return nothing
	 */
	public void actionPerformed(ActionEvent ae) {
		// move on
		// PASS RESULTS TO USER
/*
		Debugger.debug("##########		PRINT OUT RESULTS");
		Debugger.debug("numClients: "+numClients);
		Debugger.debug("numServers: "+numServers);
		Debugger.debug("numGenEvolve: "+numGenEvolve);
		Debugger.debug("popSize: "+popSize);

Debugger.debug("&&&&&& numClients: "
	+((Number)clientsField.getValue()).intValue());
Debugger.debug("&&&&&& numServers: "
	+((Number)serversField.getValue()).intValue());
Debugger.debug("&&&&&& numGenEvolve: "
	+((Number)numGenField.getValue()).intValue());
Debugger.debug("&&&&&& popSize: "
	+((Number)popSizeField.getValue()).intValue());
*/
//		int[] userInput = new int[4];
userInput = new int[4];
		userInput[0] = ((Number)clientsField.getValue()).intValue();
		userInput[1] = ((Number)serversField.getValue()).intValue();
		userInput[2] = ((Number)numGenField.getValue()).intValue();
		userInput[3] = ((Number)popSizeField.getValue()).intValue();
		
		// Use default values if user enters negative numbers
		for(int i=0; i<userInput.length; i++) {
			if(userInput[i] <= 0) {
				userInput[i] = defaultVals[i];
			}
		}
		
		Optimizer.setUserInput(userInput);
		
//		int[] costsFns = new int[8];
costsFns = new int[8];
		/**
		 * If-else if-else if-...-else statements are not used since they
		 * only allow one cost function to be "selected" at once.
		 *
		 * Hence, a series of if-statements are used instead
		 */
		if(checkboxCF1.isSelected()) {
			//Debugger.debug("COST FUNCTION IS SELECTED is number ONE");
			costsFns[0] = 1;
			numSelectedCostFns++;
		}
		if(checkboxCF2.isSelected()) {
			//Debugger.debug("COST FUNCTION IS SELECTED is number TWO");
			costsFns[1] = 1;
			numSelectedCostFns++;
		}
		if(checkboxCF3.isSelected()) {
			//Debugger.debug("COST FUNCTION IS SELECTED is number THREE");
			costsFns[2] = 1;
			numSelectedCostFns++;
		}
		if(checkboxCF4.isSelected()) {
			//Debugger.debug("COST FUNCTION IS SELECTED is number FOUR");
			costsFns[3] = 1;
			numSelectedCostFns++;
		}
		if(checkboxCF5.isSelected()) {
			//Debugger.debug("COST FUNCTION IS SELECTED is number FIVE");
			costsFns[4] = 1;
			numSelectedCostFns++;
		}
		if(checkboxCF6.isSelected()) {
			//Debugger.debug("COST FUNCTION IS SELECTED is number SIX");
			costsFns[5] = 1;
			numSelectedCostFns++;
		}
		if(checkboxCF7.isSelected()) {
			//Debugger.debug("COST FUNCTION IS SELECTED is number SEVENTH");
			costsFns[6] = 1;
			numSelectedCostFns++;
		}
		if(checkboxCF8.isSelected()) {
			//Debugger.debug("COST FUNCTION IS SELECTED is number EIGHT");
			costsFns[7] = 1;
			numSelectedCostFns++;
		}
		
		// If no cost functions are selected...
		if((costsFns[0]==0) && (costsFns[1]==0) && (costsFns[2]==0) && (costsFns[3]==0) &&
			(costsFns[4]==0) && (costsFns[5]==0)) {
			
			// ...select the first cost function by default
			costsFns[0] = 1;
			numSelectedCostFns=1;
		}
		Optimizer.setNumSelectedCostFns(numSelectedCostFns);
		Optimizer.setCostFunctions(costsFns);
		//simuThread.setNumSelectedCostFns(numSelectedCostFns);
Debugger.debug("NUMBER OF SELECTED COST FUNCTIONS IS:????: "+numSelectedCostFns);
		Optimizer.setUsrInput(true);
		setCF(costsFns);
		setProceed(true);
Debugger.debug("Simulation can be started NOW????: "+Optimizer.getUsrInput());
		this.dispose();
	}
	
	 
	/**
	 * Method to listen to the check boxes
	 * @param a is the source event that indicates a check box has
	 *	just been selected or deselected
	 * @return nothing
	 */
	public void itemStateChanged(ItemEvent a) {
		/**
		 * Each instance of the check box is used to indicate
		 * the user's choice of cost function(s) to be used
		 *
		 * Hence, it explains why we need a separate instance of the
		 * check box for each desired user choice
		 */
		// Get the source of the change in the check box's state
		Object source = a.getItemSelectable();
		// Determine the user's choice of cost function(s)
		if(source == checkboxCF1) {
			//Debugger.debug("1ST COST FUNCTION IS SELECTED/DESELECTED");
		}else if(source == checkboxCF2) {
			//Debugger.debug("2ND COST FUNCTION IS SELECTED/DESELECTED");
		}else if(source == checkboxCF3) {
			//Debugger.debug("3RD COST FUNCTION IS SELECTED/DESELECTED");
		}else if(source == checkboxCF4) {
			//Debugger.debug("4TH COST FUNCTION IS SELECTED/DESELECTED");
		}else if(source == checkboxCF5) {
			//Debugger.debug("5TH COST FUNCTION IS SELECTED/DESELECTED");
		}else if(source == checkboxCF6) {
			//Debugger.debug("6TH COST FUNCTION IS SELECTED/DESELECTED");
		}else if(source == checkboxCF7) {
			//Debugger.debug("7TH COST FUNCTION IS SELECTED/DESELECTED");
		}else if(source == checkboxCF8) {
			//Debugger.debug("8TH COST FUNCTION IS SELECTED/DESELECTED");
		}
	}
	 
	/**
	 * Method to assign the user input to the field variables when
	 * the user has entered/changed values for the field variables
	 * 
	 * Called automatically when the field's "value" property changes
	 *
	 * @param evt is the description of the event source and the
	 *	property that has changed
	 * @return nothing
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		/**
		 * Each instance of the formatted text field is used to assign
		 * the user input to the appropriate variable
		 *
		 * Hence, it explains why we need a separate instance of the
		 * formatted text field for each desired user input
		 */
		// Get the source of the change in the field's property
		Object source = evt.getSource();
		Debugger.debug("PRE IF STATEMENT"+source+"	"+clientsField);
		/**
		 * Obtain the user input from the source and
		 * assign it to the field variable
		 */
		if(source == clientsField) {
			try{
				Debugger.debug("ENTERED TRY BLOCK");
				clientsField.commitEdit();
			}catch(ParseException p) {
				Debugger.printErr("The number of clients cannot be set by the user");
				Debugger.debug("The default value for the number of clients "
					+"will be used");
			}
			numClients = ((Number)clientsField.getValue()).intValue();
			Debugger.debug("&&&&&& numClients: "+numClients);
		}else if(source == serversField) {
			numServers = ((Number)serversField.getValue()).intValue();
			Debugger.debug("&&&&&& numServers: "+numServers);
		}else if(source == numGenField) {
			numGenEvolve = ((Number)numGenField.getValue()).intValue();
			Debugger.debug("&&&&&& numGenEvolve: "+numGenEvolve);
		}else if(source == popSizeField) {
			popSize = ((Number)popSizeField.getValue()).intValue();
			Debugger.debug("&&&&&& popSize: "+popSize);
		}else{
			Debugger.printErr("HELP!!!");
			Debugger.debug("WHAT IS WRONG HERE!!!");
		}
	}
	
	public int[] getUserInput() {
		return userInput;
	}
	
	public int[] getCostFunctions() {
		return costsFns;
	}
	public void setCF(int[] a){
	costsFns=a;
	}
	
	public boolean getProceed() {
		return proceed;
	}
	public void setProceed(boolean moveOn) {
		proceed=moveOn;
	}
}