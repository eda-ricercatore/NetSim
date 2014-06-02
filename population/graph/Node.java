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
import java.util.List;

/**
 * This interface defines the minimum set of necessary functionality for a
 * node in a Graph. A node in the Graph ADT typically abstracts a real-life
 * entity/object. This node should have knowledge of local connections, which
 * in turn allows for calculating scores for clustering, pleiotropy and/or
 * redundancy.<p>
 *
 * <b>Modified</b> by Andy 08/04/05: This interface previously extends from
 * the Usable interface. However it is more practical to work in terms of 
 * relative terms of utilisation. Thus operation will be carried out using
 * percentages. Since EdgeImp has not reflected upon these concepts. It is
 * more simple to dissociate from Super class Usable.
 *
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.3-andy
 * @since           0.2
 * @acknowledgement Matthew Berryman, Wei-li Khoo and Hiep Nguyen, and code
 *                  obtained from Henry Detmold for Programming Techniques,
 *                  Assignment 1, available <a href="http://www.cs.adelaide.edu.au/users/third/pt/assignments/assignment1.tgz">here</a>.
 */
public interface Node {
    
    /**
     * Returns the descriptive label of this node. Current version implements
     * this as a String, but later Objects maybe used for more flexibility.
     * @return  a String that sufficiently describes the node
     * @throws  PostconditionException If Null is returned.
     */
    public String getLabel() throws PostconditionException;

    /**
     * Sets a descriptive label for the node. Current version implemetns as a
     * String, but later Objects maybe used for more flexibility.
     * @param   l A String that sufficiently describes the node
     * @throws  PreconditionException If <code>l</code> is a Null String.
     */
    public void setLabel(String l) throws PreconditionException;

    /**
     * Returns the coordinates of this node. These coordinates are only used
     * for graphical display purposes. Therefore there are no geographical
     * implication of the nodes locations.
     * @return  the coordinates of this node as an array of length 2
     * @throws  PostconditionException If the returned array is not length 2
     * @throws  PostconditionException If an entry in the returned array is 
     *          negative.
     */
    public int[] getCoordinates() throws PostconditionException;

    /**
     * Sets the coordinates of this node. These coordinates are only used
     * for graphical display purposes. Therefore there are no geographical
     * implication of the nodes locations.
     * @param   x The X-coordinate of this node
     * @param   y The Y-coordinate of this node
     * @throws  PreconditionException If <code>x</code> or <code>y</code> are
     *          negative.
     */
    public void setCoordinates(int x, int y) throws PreconditionException;
    
    /**
     * Returns the number of edges that terminates at this node. These 
     * incoming edges will have the toNode being this node.
     * @return  the in-degree of this node
     * @throws  PostconditionException If the returned value is negative.
     */
    public int inDegree() throws PostconditionException;
    
    /**
     * Returns the number of edges that originates from this node. These 
     * outgoing edges will have the fromNode being this node.
     * @return  the out-degree of this node
     * @throws  PostconditionException If the returned value is negative.
     */    
    public int outDegree() throws PostconditionException;

    /**
     * Returns a local list of incoming edges arriving at this node. If there
     * are no incoming edges, the returned list should be empty.
     * @return  A list of incoming edges
     * @throws  PostconditionException If the returned list is a Null list.
     */
    public List inEdgeList() throws PostconditionException;

    /**
     * Returns a local list of outcoming edges departing from this node. If
     * there are no outgoing edges, the returned list should be empty.
     * @return  A list of outgoing edges
     * @throws  PostconditionException If the returned list is a Null list.
     */
    public List outEdgeList() throws PostconditionException;
    
    /**
     * Adds a new incoming edge to this Node. This added edge is also in the
     * graph where this node resides.
     * @param   e The edge to be added to the incoming edge list
     * @throws  PreconditionException If <code>e</code> is not Null, but 
     *          either its toNode is not this node, or its fromNode is this
     *          node.
     */ 
    public void addInEdge(Edge e) throws PreconditionException;  
    
    /**
     * Adds a new outgoing edge to this Node. This added edge is also in the
     * graph where this node resides.
     * @param   e The edge to be added to the outgoing edge list
     * @throws  PreconditionException If <code>e</code> is not Null, but 
     *          either its fromNode is not this node, or its toNode is this
     *          node.
     */ 
    public void addOutEdge(Edge e) throws PreconditionException;
    
    /**
     * Returns the average transfer rate for data generated from this node.
     * This data rate is typically in the order of Mbps.
     * @return  the rate of data transfer from this node
     * @throws  PostconditionException If the returned data rate is negative
     */
    public double getTraffic() throws PostconditionException;
    
    /**
     * Assign the average transfer rate for data generated from or to this
     * node. This data rate is typically in the order of Mbps.
     * @param   rate The rate of data transfer from this node
     * @throws  PreconditionException If <code>rate</code> is negative
     */
    public void setTraffic(double rate) throws PreconditionException;
    
    /**
     * Returns statistical information regarding connections at this node.
     * This information should aid the calculation of pleiotropy, redundancy
     * and clustering factors.These statistics are stored in an array of 4
     * integers, and the information stored are as follows: 
     * <ul><li>Outgoing connection to Servers,
     *     <li>Incoming connection from Servers, 
     *     <li>Outgoing connection to Clients, and
     *     <li>Incoming connection from clients</ul>
     * @return  an array of 4 integers with contents indicated above
     * @throws  PostconditionException If returned array is not of length 4.
     * @throws  PostconditionException If any elements of array are negative.
     */    
    public int[] getStats() throws PostconditionException;
}

