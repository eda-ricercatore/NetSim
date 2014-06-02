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
import java.util.*;
import java.awt.geom.*;
import javax.swing.event.MouseInputAdapter;
//import java.util.*;
import population.graph.*;
import population.*;

/**
 * Class to create the workspace for the GUI
 * Java Swing components are primarily used since they are more versatile
 * than the AWT package.
 * @author Zhiyang Ong and Andy Lo
 * @version 0.3.1-zhiyang
 * @acknowledgement J Lewis & W Loftus, Java Software Solutions:
 *  Foundations of Program Design, 2nd Edition, Addison-Wesley,
 *  Reading, Massachusetts; Sun Microsystems, Inc's Java Tutorial located at
 *  <http://java.sun.com/docs/books/tutorial/uiswing/components/example-1dot4/
 *  index.html#ScrollDemo2> - courtesy of John Vella,
 *  and Craig Eales and Andy Lo for assistance in the development of the GUI
 */
public class Workspace extends JFrame {
	// Declare and initialise instance variables
	
	// Area for graphics
	private Dimension area;
	// Panel for insertion of scroll pane
	private JPanel workspace;
	
	/**
	 * Graph data structure to model the topology of the network
	 * Contains information about the nodes and edges
	 */
	GraphImp graph = new GraphImp(RunNetSim.getWidth(),RunNetSim.getHeight());
	
	// Fittest chromosome of the population which topology is to be sketched
	Chromosome fittestChromo;
	
	// --------------------------------------------------------------------
	
	// Default constructor for the scroll pane/workspace
	public Workspace() {
		// Set up the layout for the workspace
		//super(new BorderLayout());
		super();
		graph = new GraphImp();
		fittestChromo = new Chromosome();
/*
		this.setPreferredSize(new Dimension(
			RunNetSim.getWidth(),RunNetSim.getHeight()));
		setMinimumSize(getPreferredSize());
*/
		setSize(new Dimension(
			RunNetSim.getWidth(),RunNetSim.getHeight()));

		setupWorkspace();
	}
	
	// Standard constructor for the scroll pane/workspace
	public Workspace(Chromosome chromo) {
		// Set up the layout for the workspace
		super();
		
		//graph = chromo;
		fittestChromo=chromo;
/*
		this.setPreferredSize(new Dimension(
			RunNetSim.getWidth(),RunNetSim.getHeight()));
		setMinimumSize(getPreferredSize());
*/
		setSize(new Dimension(
			RunNetSim.getWidth(),RunNetSim.getHeight()));
		setupWorkspace();
	}
	
	// --------------------------------------------------------------------
	
	// Methods...
	
	/**
	 * Method to set up the workspace for the GUI
	 * @return the workspace of the GUI
	 */
	public void setupWorkspace() {
		// Create the workspace
//		System.out.println("Preferred size"+getPreferredSize());

		area = new Dimension(0,0);
		
		// Description of the workspace
		JLabel title = new JLabel("Telecommunication Network Topology");
		JPanel workspaceTitle = new JPanel(new GridLayout(0,1));
		workspaceTitle.add(title);
		
		//Set up the scroll pane for displaying the network topology.
		workspace = new NetworkTopology(fittestChromo);
		workspace.setBackground(Color.white);
//workspace.addMouseListener(this);

workspace.revalidate();
workspace.repaint();
//		workspace.addMouseListener(this);

		//Put the drawing area in a scroll pane.
		JScrollPane scroller = new JScrollPane(workspace);
		scroller.setPreferredSize(new Dimension(
			RunNetSim.getWidth(),RunNetSim.getHeight()));
		
		// Lay out the components in this Panel
		this.getContentPane().add(workspaceTitle, BorderLayout.PAGE_START);
		this.getContentPane().add(scroller, BorderLayout.CENTER);
	}
	
	// Handle mouse events
	
	/**
	 * Method to handle releases of mouse buttons
	 * @return nothing
	 */
	public void mouseReleased(MouseEvent e) {
// may change the values of w and h later on
		// width of the scroll pane, which contains the topology, in pixels
		int w = RunNetSim.getWidth();
		// heigth of the scroll pane, which contains the topology, in pixels
		int h = RunNetSim.getHeight();
		// Indicates if the mouse has been clicked
		boolean clicked = false;
		
		if(SwingUtilities.isLeftMouseButton(e)) {
			// Determine the new coordinates of the scroll panel
			int x = e.getX() - w/2;
			int y = e.getY() - h/2;
			
			/**
			 * Assertions... the width and height of the scroll pane cannot
			 * be negative
			 */
			if(x < 0) x = 0;
			if(y < 0) y = 0;
			
			// Set the new coordinates and dimensions of the scroll pane
			Rectangle rect = new Rectangle(x,y,w,h);
			// Allow the scroll panel to be scrollable
			workspace.scrollRectToVisible(rect);
			
			/**
			 * Can the topology be fit into this current dimensions?
			 * If not, expand the topology and make the scroll pane scrollable.
			 */
			int this_width = (x + w + 2);
			if(this_width > area.width) {
				area.width = this_width;
				clicked = true;
			}
			int this_height = (y + h + 2);
			if(this_height > area.height) {
				area.height = this_height;
				clicked = true;
			}
		}
		
		if(clicked) {
			/**
			 * Update client's preferred size because the area taken up by
			 * the graphics has gotten larger or smaller (if cleared)
			 */
			workspace.setPreferredSize(area);
			
			// Update the scroll pane and scrollbars
			workspace.revalidate();
		}
		// Update the workspace
		workspace.repaint();
	}
	
	// Not implemented methods for mouse events
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	
	/**
	 * Method to obtain the Workspace of the GUI
	 * @return the workspace of the GUI
	 */
	public JPanel getWorkspace() {
		// Make the workspace opaque and visible so that it can be seen
		workspace.validate();
		workspace.setVisible(true);
		workspace.setOpaque(true);
		return workspace;
	}
	
	/**
	 * Method to draw the network topology on a scroll pane
	 * @return a JPanel that contains the topology
	 * @deprecated 15 March 2005 - Zhiyang Ong
	 */
	public JPanel createScrollPane() {
		// Description of the workspace
		JLabel title = new JLabel("Telecommunication Network Topology");
		JPanel workspaceTitle = new JPanel(new GridLayout(0,1));
		workspaceTitle.add(title);
		
		//Set up the scroll pane for displaying the network topology.
		workspace = new NetworkTopology(fittestChromo);
		workspace.setBackground(Color.white);

workspace.revalidate();
workspace.repaint();
//		workspace.addMouseListener(this);

		//Put the drawing area in a scroll pane.
		JScrollPane scroller = new JScrollPane(workspace);
		scroller.setPreferredSize(new Dimension(400,800));
		
		// Lay out the components in this Panel
		JPanel temp = new JPanel();
		temp.add(workspaceTitle, BorderLayout.PAGE_START);
		temp.add(scroller, BorderLayout.CENTER);
		return temp;
	}
	
	/**
     * Method to display the Topology of the network on the monitor
     * for "delayTime" number of seconds
     * @param delayTime is the number of seconds to display the
     *  Topology
     * @throws PreconditionException if "delayTime" is negative
     * @return nothing
     */
    public void delayTopology(int delayTime) {
        Assertion.pre(delayTime > 0, "delayTopology(int delayTime) works",
            "Time cannot have negative values");
        try{
            // Pause for "delayTime"*1000 ms
            Thread.sleep(delayTime*1000);
        }catch(InterruptedException e) {
            Debugger.printErr("Delay for Topology is not working");
        }
    }
}