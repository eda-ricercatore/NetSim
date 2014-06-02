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
import java.awt.event.*;
import utility.*;

/**
 * Class to query user for standard deviation of the Gaussian distribution
 * that is used for determining the probabilities of fitness, reliability,
 * redundancy, and pleiotropy
 * Java Swing components are primarily used since they are more versatile
 * than the AWT package.
 * @author Zhiyang Ong and Andy Lo
 * @version 0.3.1-zhiyang
 * @acknowledgement J Lewis & W Loftus, Java Software Solutions:
 *  Foundations of Program Design, 2nd Edition, Addison-Wesley,
 *  Reading, Massachusetts;
 *  Craig Eales for assistance in the development of the GUI;
 *	 Rogers Cadenhead, "SAMS Teach Yourself Java 2 in 24 Hours," 2nd Edition,
 *	 Techmedia, New Delhi, 2001
 */
public class UserInput extends JFrame implements KeyListener, ActionListener {
    // Declaration of instance variables...
	 
	 // Label of the textbox
	 JLabel label;
	 // Text field for the user input
	 JTextField keyText;
	 // Buffer for user input
	 StringBuffer buffer = new StringBuffer();
	 // Indicates the label of the button to run the simulation
	 private static final String OK = "OK";
	 
	 // Standard deviation of the Gaussian probability density function
	 double stddev = 1.0;		// default value of 1.0
	 
	 // Container for this JFrame
	 Container pane;
	 // Label to indicate the default value for the standard deviation
	 JLabel defaultStdDev;
	 // Label to indicate the current user input
	 JLabel submit;
	 // Label to inform user to click "OK" to submit input
	 JLabel currentInput;
    
    // -------------------------------------------------------------------
    
    // Default constructor...
    public UserInput() {
        // Indicate the title of the "NetSim" software
        super("NetSim v1.0 - Query for user input");
// Size of pop-up window
		  setSize(140,100);
		  setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  keyText = new JTextField(8);
		  keyText.addKeyListener(this);
                
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
//			pane.setLayout(new FlowLayout());
pane.setLayout(new GridLayout(5,1));
			
			// Prompt user for input
			label = new JLabel("Enter standard deviation for the"
				+" Gaussian distribution",SwingConstants.LEFT);
			// align the pop-up window
			label.setHorizontalTextPosition(SwingConstants.CENTER);
			label.setVerticalTextPosition(SwingConstants.TOP);
			
			// Information for users
			defaultStdDev = new JLabel("Default value is 1.0",SwingConstants.LEFT);
			defaultStdDev.setHorizontalTextPosition(SwingConstants.CENTER);
			defaultStdDev.setVerticalTextPosition(SwingConstants.BOTTOM);
			submit = new JLabel("Click OK to enter input",SwingConstants.LEFT);
			submit.setHorizontalTextPosition(SwingConstants.CENTER);
			submit.setVerticalTextPosition(SwingConstants.BOTTOM);
/*
			currentInput = new JLabel("Your input is: "+buffer.toString(),
				SwingConstants.LEFT);
			currentInput.setHorizontalTextPosition(SwingConstants.CENTER);
			currentInput.setVerticalTextPosition(SwingConstants.TOP);
*/
			
			// Create the OK button for the user to enter input
			// Button for user to submit input to NetSim
			JButton button = new JButton(OK);
			button.setActionCommand(OK);
			button.addActionListener(this);
			
			// Add labels to a GUI Container
			pane.add(label);
			pane.add(defaultStdDev);
			pane.add(submit);
//			pane.add(currentInput);
			pane.add(keyText);
			pane.add(button);
			
			// Add the label to this Window
//			this.getContentPane().add(label);
//			this.getCOntentPane().add();
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
	  * Method to return the standard deviation selected by the user
	  * @return the standard deviation
	  */
	 public double getInput() {
	 	return stddev;
	 }
	 
	 /**
	  * Method to parse the typed keys into double values.
	  * @param input is the typed character
	  * @return nothing
	  */
	 public void keyTyped(KeyEvent input) {
	 	char key = input.getKeyChar();
Debugger.debug("Char is "+key);
		buffer.append(key);
Debugger.debug("String is: "+buffer.toString());
/*
		if((buffer.length() > 8) && (key == '\n')) {
			String userInput = buffer.toString();
			try{
				stddev = Double.parseDouble(userInput);
			}catch(NumberFormatException nfe) {
				label.setText("Please enter a valid double value for"
					+" standard deviation");
				keyText = new JTextField(8);
				this.setSize(400,80);
			}
		}
*/
	 }
	 
	 /**
	  * Method to parse the pressed keys into useful information
	  * @param txt is the input text from the user
	  * @return nothing
	  */
	 public void keyPressed(KeyEvent txt) {
	 	// do nothing
	 }
	 
	 /**
	  * Method to parse the released keys into useful information
	  * @param txt is the input text from the user
	  * @return nothing
	  */
	 public void keyReleased(KeyEvent txt) {
	 	// do nothing
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
	 
	/**
	 * Method to perform some action when the user clicks the "OK" button
	 * @param ae is the ActionEvent resulting from the user's interaction
	 *  with the GUI - clicking the OK button
	 * @return nothing
	 */
	public void actionPerformed(ActionEvent ae) {
		/**
		 * Name of the Action that this user desires, or rather the name
		 * of the button that this user clicked on
		 */
		String cmd = ae.getActionCommand();
		
		// Text field to be refreshed...
		JTextField textField;
		
		// Handle the event when the user clicks the OK button
		if(OK.equals(cmd)) {
			// OK button clicked...
			Debugger.debug("The "+cmd+" button is pressed.");
			// Process user input
			String userInput = buffer.toString();
			try{
				stddev = Double.parseDouble(userInput);
				if(stddev < 0.0) {
					label.setText("Value for standard deviation must not be"
						+" negative. Please try again.");
//					keyText = new JTextField(8);
//System.out.println("Class is: "
//	+this.getContentPane().getComponent(3).getClass().toString());
textField = (JTextField)this.getContentPane().getComponent(3);
textField.setText("");
//textField = new JTextField(8);
// Size of pop-up window
					this.setSize(450,120);
					buffer = new StringBuffer();
				}else{
Debugger.debug("The standard deviation of the PDF is: "+stddev);
this.dispose();	// make the pop-up query window disappear
				}
			}catch(NumberFormatException nfe) {
				label.setText("Please enter a valid double value for"
					+" standard deviation");
//				keyText = new JTextField(8);
textField = (JTextField)this.getContentPane().getComponent(3);
textField.setText("");
				this.setSize(400,120);
				buffer = new StringBuffer();
			}
			
		}	// Else, do nothing...
	}
}