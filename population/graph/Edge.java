/**
 * This is the underlying data structure for NetSim, abstracting a collection
 * of pairwise connections/relations between pairs of objects. These
 * connection/relations are abstracted by edges, and these objected are
 * represented by nodes.
 * 
 * The Graph to be implemented shall be directed and weighted, such that all
 * edges/relations are non-symmetric and are assigned values, which maybe be
 * costs in monetary or in distance sense.
 */
package population.graph;

// importing packages
import utility.*;

/**
 * This interface defines the minimum set of necessary functionality for an
 * edge in a Graph. An edge maintains information of the interconnection or
 * relations between nodes in the Graph ADT. This representation can be seen 
 * as a vector from one node to another.
 *
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.2-andy
 * @since           0.2
 * @acknowledgement Matthew Berryman, Wei-li Khoo and Hiep Nguyen, and code
 *                  obtained from Henry Detmold for Programming Techniques,
 *                  Assignment 1, available <a href="http://www.cs.adelaide.edu.au/users/third/pt/assignments/assignment1.tgz">here</a>.
 */
public interface Edge extends Usable {
    
    /**
     * Returns the departure node of this edge
     * @return  the node which this edge starts from
     */
    public Node getFromNode();
    
    /**
     * Returns the destination node of this edge
     * @return  the node which this edge ends
     */
    public Node getToNode();

    /**
     * Sets the departure node of this edge
     * @param   n The node which this edge starts from
     */
    public void setFromNode(Node n);
    
    /**
     * Sets the destination node of this edge
     * @param   n The node which this edge ends
     */
    public void setToNode(Node n);
    
    /**
     * Returns the descriptive label of this edge. Current version implements
     * this as a String, but later Objects maybe used for more flexibility.
     * @return  a String that sufficiently describes the Edge
     * @throws  PostconditionException If Null is returned.
     */
    public String getLabel() throws PostconditionException;

    /**
     * Sets a descriptive label for the edge. Current version implemetns as a
     * String, but later Objects maybe used for more flexibility.
     * @param   l A String that sufficiently describes the Edge
     * @throws  PreconditionException If <code>l</code> is a Null String.
     */
    public void setLabel(String l)  throws PreconditionException;
    
    /**
     * Returns the cost of using this edge to transfer data.
     * @throws PostconditionException If the returned cost is negative.
     * @return the cost of transfering data along this Edge
     */
    public double getCost() throws PostconditionException;
    
    /**
     * Assigns (or reassigns) a cost value to this edge.
     * @param cost The new cost of this edge
     * @throws PreconditionException If <code>cost</code> is negative.
     */
    public void setCost(double cost) throws PreconditionException;
}
