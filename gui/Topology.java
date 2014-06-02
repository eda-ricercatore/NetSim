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

/**
 * Class to draw the telecommunication network topology for the GUI
 * Java Swing components are primarily used since they are more versatile
 * than the AWT package.
 * @author Zhiyang Ong and Andy Lo
 * @version 0.3.1-zhiyang
 * @acknowledgement J Lewis & W Loftus, Java Software Solutions:
 *  Foundations of Program Design, 2nd Edition, Addison-Wesley,
 *  Reading, Massachusetts; Sun Microsystems, Inc's Java Tutorial located at
 *  <http://java.sun.com/docs/books/tutorial/uiswing/components/example-1dot4/
 *  index.html#ScrollDemo2> - courtesy of John Vella, Rogers Cadenhead,
 * "SAMS Teach Yourself Java 2 in 24 Hours," 2nd Edition, Techmedia, Delhi,
 * pp. 375-377, 2000
 *  and Craig Eales and Andy Lo for assistance in the development of the GUI
 */

// Importing packages...
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import utility.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.event.MouseInputAdapter;
//import java.util.ArrayList;
import population.graph.*;
import population.*;

// Class definition...

// ========================================================================

public class Topology extends JPanel {
	// # Added by Andy 19 Apr 2005
    // Declaration of constants
    
    // the distance to retract from the end point of a line
    private final double ALPHA = 5.0;
    // the half angle (deg) of the point of the arrow (smaller => sharper)
    private final double PHETA = 10.0;
    private final double SINSQR = Math.sin(20*Math.PI/180) *
                                            Math.sin(20*Math.PI/180);
    // the distance of the end point of the arrow from the line
    private final double GAMMA = ALPHA * Math.sqrt(SINSQR / (1-SINSQR));
    
    // Declaration of instance variables...
	
	/**
	 * a list to store all the nodes and edges. Chronological order of
	 * addition are implicitly maintained by the ArrayList.
	 */
	private ArrayList nodeList = new ArrayList();
	/**
	 * a list to store all the nodes and edges. Chronological order of
	 * addition are implicitly maintained by the ArrayList.
	 */
	private ArrayList edgeList = new ArrayList();
	
    // graphics object to draw on
    private Graphics2D comp2D;
    
	/**
	 * Graph data structure to model the topology of the network
	 * Contains information about the nodes and edges
	 */
	GraphImp graph = new GraphImp(RunNetSim.getWidth(),RunNetSim.getHeight());
	
	// Fittest chromosome of the population which topology is to be sketched
	Chromosome fittestChromo;
	
	// Dimensions (in pixels) for the side of the square representing a Node
	private int nodeSide = 10;
	
	// Boolean flag to indicate if a source Node is a server
	boolean fromServer = false;
	// Boolean flag to indicate if a destination Node is a server
	boolean toServer = false;
	// Dimensions of the pop-up window
	private int xDim = 1000;
	private int yDim = 750;

	// --------------------------------------------------------------------

	// Default constructor for the scroll pane/workspace
	public Topology() {
		fittestChromo = new Chromosome();
	}
	
	// Standard constructor for the scroll pane/workspace
	public Topology(Chromosome chromo) {
		super("NetSim v2.0");
		fittestChromo = chromo;
		// Size of pop-up window
		setSize(xDim,yDim);
		// When user clicks on the Window_Close button, close the pop-up window.
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	// --------------------------------------------------------------------
	
	/**
	 * Method to draw the network topology of the GUI
	 * @return nothing
	 */
	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
		// Drawing 2D pictures...
		comp2D = (Graphics2D) g;
		// Set the backgrounud white
		comp2D.setColor(Color.white);
		// Set the size of the panel
		comp2D.fillRect(0,0,RunNetSim.getWidth(),RunNetSim.getHeight());
		
		// Draw network topology
		for(int i=0; i<fittestChromo.getLength(); i++) {
			// Enumerate each node...
			NodeImp curNode = (NodeImp) fittestChromo.getNodeList().get(i);
			// Get its coordinates...
			int[] coordinates = curNode.getCoordinates();
			// Determine if it is a server or a client
			if(curNode.getLabel().equals("SERVER")) {
				// It is a server...
				fromServer = true;
				// Color it black
				comp2D.setColor(Color.black);
			}else{
				// It is a client...
				fromServer = true;
				// Color it red
				comp2D.setColor(Color.red);
			}
			// Draw this Node
			comp2D.fillRect(coordinates[0],coordinates[1],nodeSide,nodeSide);
			// Draw the list of (destination) nodes that this Node is connected to
			ArrayList destinationNodes = fittestChromo.getData(i);
			for(int j=0; j<destinationNodes.size(); j++) {
				// For each destination node, draw a connecting line to it
				NodeImp toNode = (NodeImp)destinationNodes.get(j);
				// Get its coordinates...
				int[] toCoord = toNode.getCoordinates();
				// Determine if it is a server or a client
				if(toNode.getLabel().equals("SERVER")) {
					// It is a server...
					toServer = true;
				}else{
					// It is a client...
					toServer = true;
				}
				if(fromServer && toServer) {
					// Color it green
					// Server-Server link
					comp2D.setColor(Color.green);
				}else if((!fromServer) && (!toServer)) {
					// Color it magenta
					// Client-Client link
					comp2D.setColor(Color.magenta);
				}else{
					// Color it blue
					// Server-Client link or Client-Server link
					comp2D.setColor(Color.blue);
				}
				// Draw the directed edge
				drawArrowLine(coordinates[0],coordinates[1],toCoord[0],toCoord[1]);
			}
		}

        
        // # Added by Andy for test 19 Apr 2005
/*
        drawArrowLine(500,300,500,500);
        drawArrowLine(500,300,700,300);        
        drawArrowLine(500,300,500,100);		
        drawArrowLine(500,300,300,300);
        
		comp2D.setColor(Color.red);
        drawArrowLine(500,300,353,200);        
		comp2D.setColor(Color.green);
        drawArrowLine(500,300,707,410);        
		comp2D.setColor(Color.cyan);
        drawArrowLine(500,300,850,200);
        comp2D.setColor(Color.magenta);
        drawArrowLine(500,300,315,400);
*/
	}

    /**
     * Draws a line with an arrow tip at the end. 
     *
     * # Added by Andy 19 Apr 2005
     *
     * @param   x1  the x coordinate of the starting point
     * @param   y1  the y coordinate of the starting point
     * @param   x2  the x coordinate of the ending point
     * @param   y2  the y coordinate of the ending point
     *
     */
    private void drawArrowLine (int x1, int y1, int x2, int y2) {
        // draw the actual line first
        comp2D.drawLine(x1, y1, x2, y2);

        double x3, y3;

        if(x1 == x2) {
            y3 = (y2 < y1) ? y2+ALPHA : y2-ALPHA;
            
            //comp2D.fillRect( (int)(x2),(int)(y3),10,10);
            
            comp2D.drawLine( (int) (x2+GAMMA), (int) (y3), x2 ,y2);
            comp2D.drawLine( (int) (x2-GAMMA), (int) (y3), x2 ,y2);
//          comp2D.drawLine( (int) (x2+GAMMA), (int) (y3),
//                           (int) (x2-GAMMA), (int) (y3) );            
        
        } else if (y1 == y2) {
            x3 = (x2 < x1) ? x2+ALPHA : x2-ALPHA;
            
            //  comp2D.fillRect( (int)(x3),(int)(y2),10,10);
            
            comp2D.drawLine( (int) (x3), (int) (y2+GAMMA), x2 ,y2);
            comp2D.drawLine( (int) (x3), (int) (y2-GAMMA), x2 ,y2);
           // comp2D.drawLine( (int) (x3), (int) (y2+GAMMA), 
           //                  (int) (x3), (int) (y2-GAMMA) ); 
        } else {
            double slope = ( (double)y2 - (double)y1) / 
                            ( (double)x2 - (double)x1);
                        
            // sqrt(1+(m*m))
            double divisorA = Math.sqrt(1+(slope*slope));
            // sqrt( 1+(1/(m*m)) )
            double divisorB = Math.sqrt(1+(1/(slope*slope)));
        
            // point "alpha" away along the line relative to x2 and y2
            double[] midBase = new double[2];
            // first obtain the offset
            midBase[0] = -ALPHA / divisorA;
            midBase[1] = -ALPHA / divisorB;
        
            if (x2 < x1) midBase[0] *= -1;
            if (y2 < y1) midBase[1] *= -1;
        
            /**
             * define end points of arrows relative to the "midBase"
             * This is in the format of [xa,ya,xb,yb]
             */
            double[] offset = new double[4];
            // for a positive slope, xa should be on the left of "midBase"
            offset[0] = -GAMMA / divisorB;
            // for a positive slope, ya should be above "midBase"
            offset[1] =  GAMMA / divisorA;
            // the second point are in the oppsite direction.
            offset[2] = -offset[0];
            offset[3] = -offset[1];        

            if (slope < 0.0) {
                offset[0] *= -1;
                offset[2] *= -1;
            }
        
            // Draw one side of the arrow
            comp2D.drawLine( (int)(x2 + midBase[0] + offset[0]), 
                             (int)(y2 + midBase[1] + offset[1]), x2, y2);
            // Draw the other side
            comp2D.drawLine( (int)(x2 + midBase[0] + offset[2]),
                             (int)(y2 + midBase[1] + offset[3]), x2, y2);
        }
    }
}
