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
import java.util.ArrayList;
import java.util.List;
import java.lang.ClassCastException;

/**
 * This class is a basic implementation for an node to be used in the software
 * <b>NetSim</b>. Since the <code>Node</code> interface inherits properties of
 * <code>Usable</code> objects, this Node is an <i>usable repairable</i>
 * node.<p>
 *
 * A Node may be <i>usable</i> depending on its type. A node that can be used
 * are typically agents/servers for handling requests, or it simple passes on
 * requests to a particular agent/server. This node alse models the fact that
 * there exists an upper bound for possible usage, since an node (modelling a
 * agent or server) may not be optimised, or there maybe a need to reserve a
 * portion of serving capacity to limit utilisation or load. Apart from being
 * used by others, this node may produce some traffic loading the network.
 * This is not only the case with a node producing requests, this also applies
 * when after a requests is serviced, some feedback is to be retured.<p>
 *
 * Since the node is <i>repairable</i>, it can be used to simulate the
 * likelihood of a node malfunctioning. The node has a threshold for maximum
 * allowable fail rate. Subject to this threshold, a node should be replaced,
 * if the node has a very high chance of failing and it is cheap to replace, 
 * or if the number of generations required for repair is too large and its 
 * absense is causing the Network to halt its operations.<p>
 *
 * The type of the node is stored inside its label, and typically reflects 
 * what purpose it serves for all the other nodes which connects to it. At 
 * this stage, the label should be either  "SERVER" or "CLIENT".This node
 * stores two separate lists for edges which ends at this node and for edges 
 * which originates from this node. This means each node "knows" its
 * neighbours that can be reached by traversing throught the list of edges.<p>
 *
 * Due to a need to display graphs, coordinates of location of nodes will also
 * be stored. However, this position has no implication on the geographical
 * location; they are only used for plotting points for nodes and drawing
 * drawing lines for edges.<p>
 *
 * <b>Updated</b> by Andy 08/04/05: The term efficiency used prior to v0.3.7
 * of NetSim is intended to be used as a regulator to cap the available
 * capacity, or to reserve some portion of the capacity. The name of the
 * variable is misleading, and the variable is has no purpose in the software.
 * Decision was also made to vary usage in terms of percentage. Thus from
 * v0.3.7 onwards, efficieny and utilisation are equivalent terms.<p>
 *
 * Methods getEfficiency, setEfficiency, getUsage and getAvailCap has been
 * removed as they will no longer be used. The methods increUsage, decreUsage
 * and getLoad have been modified to reflect the use of usage in relative
 * terms. getUtilisation has been renamed to getEfficiency, taking on the new
 * conventional meaning.<p>
 *
 * <b>Obsolete</b>: <i>Note: In this version of <b>NetSim</b>, every node is
 * <i>usable</i> regardless of its type. It is intentional not to
 * differentiate between the server nodes and client nodes.</i>
 *
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.7
 * @since           0.2
 * @acknowledgement Matthew Berryman, Wei-li Khoo and Hiep Nguyen
 */ 
public class NodeImp extends Repairable implements Node {
    // label indicating the type of the node
    private String label;
    // the maximum capcity of the node
    private double capacity;
    // the operating efficiency of the node ranged from 0.00 to 1.00
    private double efficiency;
    // the amount of the node's capacity currently in use
    private double usage = 0.0;
    private double traffic = 0.0;
    /**
     * the coordinates of this node for display purposes only. The first
     * element is the X-coordinate and the second is the Y-coordinate. This is
     * initilised to (0,0)
     */
    private int[] coord = new int[2];
    /**
     * an array to store the number of connections to different types of
     * nodes. The structure of the array are as follows:
     * {Outgoing connection to Servers, Incoming connection from Servers, 
     *  Outgoing connection to Clients, Incoming connection from clients}
     */ 
    private int[] stats = new int[4];       

    // the list of incoming and outgoing edges stored for efficient traversal
    ArrayList incoming = new ArrayList();
    ArrayList outgoing = new ArrayList();;

    // Default Constructor
    /**
     * Creates a default instance of NodeImp. A Node created using this
     * default constructor is non-functional and cannot be used immediately.
     * This is because it would have the properties of non-repairable and
     * non-usable objects.<p>
     *
     * That is, extremely high repair generation requirement, probablity of 
     * failure of 1.00 and  maximum allowable probablity of failure
     * (threshold) of 0%. Also, this node's capacity and operating efficieny
     * are both 0.<p>
     *
     * Thus the above mentioned properties must be altered before using this
     * node. In addition, the coordinates of this node must be change.
     * @see Repairable
     */
    public NodeImp() {
        super();            // call constructor of super class
        label      = "";    // empty label
        capacity   = 0.00;  // 0 capacity
        efficiency = 0.00;  // operating at 0% efficiency
    }
    
    // Standard Constructors
    /**
     * Creates an instance of NodeImp without a label. This constructor is
     * used for creating a node template which can be reused, as many time as
     * desired, to create nodes with similar properties.<p>
     *
     * In order to construct this node, a model Repairable object is needed.
     * All the Repairable properties are obtained from the model. The usable
     * object properties are taken in via an array. The contents of the array
     * includes:
     * <ul><li>capacity of the node
     *     <li>initial operating efficiency of the node</ul>
     *
     * Note: Doesn't matter if "r" is a Repairable object, and EdgeImp or
     * NodeImp. As we are only copying values from repairable. Ideally, we
     * should create a reapriable for creating edges and a separate one for
     * node.
     *
     * @param   param The array of parameters as outlined above.
     * @param   r The repairable object template for this node
     * @throws  PreconditionException If capacity of this node is negative.
     * @throws  PreconditionException If efficiency is not between 0 and 1.
     * @see     Repairable
     */
    public NodeImp (double[] param, Repairable r)
    throws PreconditionException {
        // call super class constructor with appropriate parameters
        super(r.getAvgRepGen(), r.getFailureProb(), r.getThreshold(),
            r.activated());
        // check preconditions
        // # Modified by Zhiyang Ong
        Assertion.pre(param.length >= 2,
                       "param is of the right size; size of " + param.length,
                       "There exists insufficient properties in param");
        Assertion.pre( param[0] >= 0, "Capacity is " + param[0],
                                      "Capacity must not be negative");
        Assertion.pre((param[1] >= 0.00) && (param[1] <= 1.00),
                       "Efficiency to be set is " + param[1],
                       "Efficiency to be set must be between 0.0 and 1.0");
        label      = "";        // label is an empty string (undetermined)
        capacity   = param[0];  // store parameters
        efficiency = param[1];
        // array for coordinates, this is initilised to (0,0)
        coord      = new int[2]; 
    }
    
    /**
     * Creates an instance of NodeImp from a template node. This template node
     * must be an instance of <code>Repairable</code> so that the properties 
     * for this node, such as the probability of failure and the threshold, 
     * and the required generations for repair, can be copied. There are some 
     * properties such as capacity and efficiency (similar to properties of 
     * <code>Usable</code> object) that are exclusive to NodeImp. Thus it is
     * assumed that the parameter node "n" must be NodeImp.<p>
     * 
     * Having all relevant properties copied, a complete node is created by
     * assigning a label <code>l</code> to it.<p>
     *
     * @param   l The label indicating type of this node
     * @param   x The X-coordinate of this node
     * @param   y The Y-coordinate of this node
     * @param   n The repairable node template for this node. That is
     *          <code>n</code> inherits from <code>Repairable</code>.
     * @throws  ClassCastException If <code>n</code> is not an instance of
     *          <code>Repairable</code>.
     * @throws  PreconditionException If <code>l</code> is Null.
     * @throws  PreconditionException If <code>x</code> or <code>y</code> are
     *          negative.
     * @see     Repairable
     */          
    public NodeImp (String l, int x, int y, Node n)
    throws PreconditionException, ClassCastException {
        /**
         * call super class constructor with appropriate parameters obtained
         * from the template node
         */
        super( ((Repairable) n).getAvgRepGen(),
               ((Repairable) n).getFailureProb(),
               ((Repairable) n).getThreshold(),
               ((Repairable) n).activated() );
        // check precondition
        Assertion.pre( l != null, "Label to be set is " + l,
                                  "Label to be set must not be null");
        Assertion.pre( (x >= 0) && (y >= 0),
                       "The new coordinates are ("+ x +","+ y +")",
                       "The new coordinates must not be negative");
        label      = new String(l);       // store the label
        /**
         * # Modified by Andy 08/07/05:
         *      Since Node interface no longer extends the Usable interface,
         *      it has not inherited the methods getMaxCapacity and
         *      getEfficiency (please note Efficiency and Utilisation are now
         *      interchangeable terms). Thus "n' must be casted to NodeImp to
         *      use the respective methods.
         */
        capacity   = ((NodeImp)n).getMaxCapacity();  // obtain and store values from "n"
        efficiency = ((NodeImp)n).getEfficiency();
        coord[0] = x;                     // store the coordinates
        coord[1] = y;
    }
        
    /**
     * Creates an instance of NodeImp. This will construct a node with all the
     * necessary properties from the elementary parameters. These parameters
     * includes its label, the number of generations required for repair, an
     * array of paramaters of decimal numbers, and a boolean determining
     * whether the node  should be actiavated. <p>
     * 
     * The contents of the array metioned above includes:
     * # Modified by Zhiyang Ong 
     * <ul><li>capacity of the node
     *     <li>initial operating efficiency of the node
     *     <li>probability of failure
     *     <li>threshold for probability of failure</ul>
     *
     * @param   l The label indicating type of this node
     * @param   x The X-coordinate of this node
     * @param   y The Y-coordinate of this node
     * @param   repGens The average number of generations required for repair
     * @param   param The array of parameters as outlined above.
     * @param   act <code>true</code> if this node is activated initially
     * @throws  PreconditionException If <code>l</code> is Null.
     * @throws  PreconditionException If <code>x</code> or <code>y</code> are
     *          negative.
     * @throws  PreconditionException If capacity of this node is negative.
     * @throws  PreconditionException If efficiency is not between 0 and 1.
     * @see Repairable
     */
    public NodeImp (String l, int x, int y, int repGens, double[] param,
    boolean act) throws PreconditionException {
        // call super class constructor with appropriate parameters
        // # Modified by Zhiyang Ong
        super(repGens, param[2], param[3], act);
        // check preconditions
        // # Modified by Zhiyang Ong
        Assertion.pre(param.length >= 4,
                       "param is of the right size; size of " + l,
                       "There exists insufficient properties in param");
        Assertion.pre( l != null,
                       "Label to be set is " + l,
                       "Label to be set must not be null");
        // # Modeified by Andy Lo to obtain values from correct array indices
        Assertion.pre( param[0] >= 0, "Capacity of node is " + param[0],
                                    "Capacity of node must not be negative");
        Assertion.pre( (param[1] >= 0.00) && (param[1] <= 1.00),
                        "Efficiency to be set is " + param[1],
                        "Efficiency to be set must be between 0.0 and 1.0");
        Assertion.pre( (x >= 0) && (y >= 0),
                       "The new coordinates are ("+ x +","+ y +")",
                       "The new coordinates must not be negative");
        label      = new String(l); // create a copy of the String and store
        // # Modified by Zhiyang Ong
        capacity   = param[0];      // and the Usable variables
        efficiency = param[1];
        coord[0] = x;               // store the coordinates
        coord[1] = y;
    }        

    /**
     * implmentation of Node Interface
     */

    /**
     * Returns the label or type of this node. This is implemented as a
     * String, but in later versions Objects maybe used for more flexibility.
     * @return  the label which sufficiently describes the Node
     * @throws  PostconditionException If Null is returned.
     */
    public String getLabel() throws PostconditionException {
        Assertion.post( label != null,
                        "Label of this node is " + label,
                        "Label of this node must not be null");
        return label;
    }

    /**
     * Sets the label or type for the node. This is implemented as a String,
     * but in later versions Objects maybe used for more flexibility.
     * @param   l A String that sufficiently describes the Node
     * @throws  PreconditionException If <code>l</code> is a Null String.
     */
    public void setLabel(String l) throws PreconditionException {
        Assertion.pre( l != null,
                       "Label to be set is " + l,
                       "Label to be set must not be null");
        label = new String(l);  // create a copy of the String and store
    }

    /**
     * Returns the location of this node in terms of cartesian coordinates.
     * These coordinates are only used for graphical display purposes. There
     * is no implication of the node's spatial locations.
     * @return  the coordinates of this node as an array of length 2
     * @throws  PostconditionException If the returned array is not length 2
     * @throws  PostconditionException If an entry in the returned array is 
     *          negative.
     */
    public int[] getCoordinates() throws PostconditionException {
        Assertion.post( coord.length == 2, "The coordinates are of length 2",
                                       "The coordinates must be of length 2");
        Assertion.post( (coord[0] >= 0) && (coord[1] >= 0),
                        "The coordinates are ("+coord[0]+","+coord[1]+")",
                        "The coordinates must not be negative");
        return coord;
    }

    /**
     * Sets the location of this node in terms of cartesian coordinates. These
     * coordinates are only used for graphical display purposes. There is no
     * implication of the node's spatial locations.
     * @param   x The X-coordinate of this node
     * @param   y The Y-coordinate of this node
     * @throws  PreconditionException If <code>x</code> or <code>y</code> are
     *          negative.
     */
    public void setCoordinates(int x, int y) throws PreconditionException {
        Assertion.pre( (x >= 0) && (y >= 0),
                       "The new coordinates are ("+ x +","+ y +")",
                       "The new coordinates must not be negative");
        coord[0] = x;   // store the coordinates
        coord[1] = y;
    }    
    
    /**
     * Returns the number of incoming edges of this node. These incoming edges
     * terminate at this node, so they have the toNode being this node.
     * @return  the in-degree of this node
     * @throws  PostconditionException If the returned value is negative.
     */
    public int inDegree() throws PostconditionException {
        int deg = incoming.size();  // obtain list size
        Assertion.post( (deg >= 0),
                        "The inDegree is " + deg,
                        "The inDegree must not be negative");
        return deg;
    }
    
    /**
     * Returns the number of outgoing edges of this node. These outgoing edges
     * starts from this node, so they have the fromNode being this node.
     * @return  the out-degree of this node
     * @throws  PostconditionException If the returned value is negative.
     */    
    public int outDegree() throws PostconditionException {
        int deg = outgoing.size();  // obtain list size
        Assertion.post( (deg >= 0),
                        "The outDegree is " + deg,
                        "The outDegree must not be negative");
        return deg;
    }
    
    /**
     * Returns a list of incoming edges. These edges are those arriving at
     * this node. If there are no incoming edges, the returned list should be
     * empty, not null.
     * @return  A list of incoming edges
     * @throws  PostconditionException If the returned list is a Null list.
     */
    public List inEdgeList() throws PostconditionException {
        Assertion.post( incoming != null,
                        "The incoming edge list is not null and is returned",
                        "The incoming edge list must not be null");
        return incoming;
    }
    
    /**
     * Returns a list of outcoming edges. These edges are those departing from
     * this node. If there are no outgoing edges, the returned list should be
     * empty, not null.
     * @return  A list of outgoing edges
     * @throws  PostconditionException If the returned list is a Null list.
     */
    public List outEdgeList() throws PostconditionException {
        Assertion.post( outgoing != null,
                        "The outgoing edge list is not null and is returned",
                        "The outgoing edge list must not be null");
        return outgoing;
    }
    
    /**
     * Adds a new incoming edge to this Node. This added edge should have a
     * terminating node as this Node, and this edge should be in the graph
     * where this node resides.
     * @param   e The edge to be added to the incoming edge list
     * @throws  PreconditionException If <code>e</code> is not Null, but 
     *          either its toNode is not this node, or its fromNode is this
     *          node.
     */ 
    public void addInEdge(Edge e) throws PreconditionException {
        // ### We are checking for references/pointers for the node
        Assertion.pre( e.getToNode() == this,
                       "The new incoming edge ends at this node",
                       "The new incoming edge must end at this node");
        String label = (e.getFromNode()).getLabel();
        if ( (label.toUpperCase()).startsWith("SERVER") ) stats[1]++;
        else if ( (label.toUpperCase()).startsWith("CLIENT") ) stats[3]++;
        incoming.add(e);
    }
    
    /**
     * Adds a new outgoing edge to this Node. This added edge should have a
     * originating node as this Node, and this edge should be in the graph
     * where this node resides.
     * @param   e The edge to be added to the outgoing edge list
     * @throws  PreconditionException If <code>e</code> is not Null, but 
     *          either its fromNode is not this node, or its toNode is this
     *          node.
     */ 
    public void addOutEdge(Edge e) throws PreconditionException {
        // ### We are checking for references/pointers for the node
        Assertion.pre( e.getFromNode() == this,
                       "The new outgoing edge leaves from this node",
                       "The new outgoing edge must leave from this node");
        String label = (e.getToNode()).getLabel();
        if ( (label.toUpperCase()).startsWith("SERVER") ) stats[0]++;
        else if ( (label.toUpperCase()).startsWith("CLIENT") ) stats[2]++;

        outgoing.add(e);
    }
    
    /**
     * Returns the average date traffic generated from this node. This data
     * rate is typically in the order of Mbps.
     * @return  the rate of data transfer from this node
     * @throws  PostconditionException If the returned data rate is negative
     */
    public double getTraffic() throws PostconditionException {
        Assertion.post( traffic >= 0,
                        "The traffic from this node is " + traffic,
                        "The traffic from this node must be non-negative");
        return traffic;
    }
    
    /**
     * Assign the average date traffic generated from this node. This data
     * rate is typically in the order of Mbps.
     * @param   traf The rate of data transfer from this node
     * @throws  PreconditionException If <code>rate</code> is negative
     */
    public void setTraffic(double traf) throws PreconditionException {
        Assertion.pre( traf >= 0,
                       "The new traffic from this node is " + traffic,
                       "The new traffic from this node must be non-negative");
        traffic = traf;
    }

    /**
     * Return the connection statistics of this node. These statistics are
     * stored in an array of 4 integers, and the information stored are as
     * follows: 
     * <ul><li>Outgoing connection to Servers,
     *     <li>Incoming connection from Servers, 
     *     <li>Outgoing connection to Clients, and
     *     <li>Incoming connection from clients</ul>
     * These informations are useful in terms of calculating the amount of
     * pleiotropy and redundancy, and the clustering factor of this node.
     * @return  an array of 4 integers with contents indicated above.
     * @throws  PostconditionException If returned array is not of length 4.
     * @throws  PostconditionException If any elements of array are negative.
     */    
    public int[] getStats() throws PostconditionException {
        Assertion.post( stats.length == 4,
                        "The statistics array is of length 4",
                        "The statistics array must be of length 4");
        Assertion.post( (stats[0] >= 0) && (stats[1] >= 0) && (stats[2] >= 0)
                        && (stats[3] >= 0) ,
                        "The statistic values are ("+stats[0]+","+stats[1]+
                        ","+stats[2]+","+stats[3]+")",
                        "The statistic values must not be negative");
        return stats;
    }

    /**
     * implmentation of Usable Interface
     * Modified by Andy 08/04/05: No longer need to implement Usable inteface
     */

    /**
     * Returns the maximum capacity of this node. The units to measure
     * capacity is application dependent. For example of data traffic,
     * capicity may be in units of Mbps. 
     * @return  the capacity of this node operating at 100% efficiency
     * @throws  PostconditionException If the returned capacity is negative.
     */
    public double getMaxCapacity() throws PostconditionException {
        return capacity;
    }
    
    /**
     * Sets the maximum capacity of this node. The units to measure capacity
     * is application dependent. For example of data traffic, capicity may be
     * in units of Mbps. <p>
     *
     * The capacity limits the usage of this node, so the new capacity, 
     * subject to efficiency, must not be less than the current usage. If a
     * lesser value is to be set, usage must be reduced. This automatically
     * implies if input parameter is negative, change of capacity will also
     * be unsucessful.
     *
     * @param   cap The capacity of this node operating at 100% efficiency
     * @return  <code>true</code> If the maximum capacity was changed
     *          successfully.
     */
    public boolean setMaxCapacity(double cap) {
        if ( (cap*efficiency) >= usage ) {
            capacity = cap;
            return true;
        } else return false;
    }

    /**
     * Removed by Andy 08/04/05:
     *      The term efficiency used prior to v0.3.7 of NetSim is intended to
     * be used as a regulator to cap the available capacity, or to reserve
     * some portion of the capacity. The name of the variable is misleading, 
     * and the variable is has no purpose in the software. Decision was also
     * made to vary usage in terms of percentage. Thus from v0.3.7 onwards, 
     * efficieny and utilisation are equivalent terms.
     */
//    /**
//     * Returns the operating efficiency this node is running. This is 
//     * measured as a percentage, this is used in conjunction with the maximum
//     * capacity to specify the total capacity available for use.
//     * @return  the efficiency of this node as a percentage
//     * @throws  PostconditionException If the returned capacity is not
//     *          between 0.00 and 1.00
//     */
//    public double getEfficiency() throws PostconditionException {
//        Assertion.post( (efficiency >= 0.00) && (efficiency <= 1.00),
//                        "Efficiency factor is " + efficiency,
//                        "Efficiency factor must be between 0.0 and 1.0");
//        return efficiency;
//    }
//    
    /**
     * Removed by Andy 08/04/05:
     *      Removed for the same reason of misconceptiong of Efficiencey.
     */
//    /**
//     * Sets the operating efficiency for this node. This is measured as a 
//     * percentage, this is used in conjunction with the maximum capacity to
//     * specify the total capacity available for use.<p>
//     *
//     * The efficiency limits the usage of this node, so if the new value set
//     * result in a total available capacity less than the cuurent usage, the
//     * change can not be made. 
//     *
//     * @param   eff The level of efficiency as a number between 0.00 and 1.00
//     *          inclusive.
//     * @return  <code>true</code> If the efficiency was changed successfully.
//     * @throws  PreconditionException If <code>eff</code> is not between 0.00
//     *          and 1.00.
//     */
//    public boolean setEfficiency(double eff) throws PreconditionException {
//        Assertion.pre( (eff >= 0.00) && (eff <= 1.00),
//                        "Efficiency to be set is " + eff,
//                        "Efficiency to be set must be between 0.0 and 1.0");
//        if ( (capacity*eff) >= usage ) {
//            efficiency = eff;
//            return true;
//        } else return false;    
//    }
//
    /**
     * Removed by Andy 08/04/05:
     *      Removed as usage is now refer to as percentage. Thus getUsage is
     *      redundant to getEfficiency.
     */
//    /**
//     * Returns the usage on this node. The units to quantify the usage should
//     * be consistent with that of the capacity.
//     * @return  the current usage of this node
//     * @throws  PostconditionException If the returned usage is negative.
//     */
//    public double getUsage() throws PostconditionException {
//        Assertion.post( usage >= 0, "Usage is " + usage,
//                                    "Usage must be non-negative");
//        return usage;
//    }
    
    /**
     * Increases the current usage by an arbitrary amount as a percentage of
     * the overall capacity. This is typically used when a request is made to
     * use this object. Note the increase or request may not always be 
     * successful due to availability.
     * @param   amount The amount of usage to increase
     * @return  <code>true</code> If the usage was increased successfully.
     * @throws  PreconditionException If <code>amount</code> is not between
     *          0.0 and 1.0.
     */
    public boolean increUsage(double amount) throws PreconditionException {
        /**
         * Modified by Andy 08/04/05:
         *      Since usage is now percentage, the value must be in the range
         *      [0.0,1.0];
         */
        Assertion.pre( amount >= 0.0 && amount <= 1.0,
                   "The amount to increment is " + amount,
                   "The amount to increment must be between 0.0 and 1.0");
        // Modified by Andy 08/04/05: to compare with and vary efficiency
        if( amount <= 1.0 - efficiency ) {
            efficiency += amount;
            return true;
        } else return false;
    }  

    /**
     * Decreases the current usage by an arbitrary amount as a percentage of
     * the overall capcity. This is typically sed when an user finishes using
     * use this object. Note when the amount requested to be reduced is more
     * than that actually used, just reset the usage.
     * @param   amount The amount of usage to decrease
     * @throws  PreconditionException If <code>amount</code> is negative.
     */
    /**
     * Modified by Andy 08/04/05: Method no longer need to return a result
     * to comfirm the success of decreasing usage
     */
    public void decreUsage(double amount) throws PreconditionException {
        /**
         * Modified by Andy 08/04/05:
         *      Since usage is now percentage, the value must be in the range
         *      [0.0,1.0];
         */        Assertion.pre( amount >= 0.0 && amount <= 1.0,
                   "The amount to decrement is " + amount,
                   "The amount to decrement must be between 0.0 and 1.0");
        /**
         * Modified by Andy 08/04/05:
         *      1. to compare with and vary efficiency
         *      2. if the amount to be reduced is more than that is used
         *          reset the usage to 0.0;
         */
        if( (efficiency - amount) >= 0 ) efficiency -= amount;
        else resetUsage();
    }
    /**
     * If required, later versions should register users of this object, in a
     * hash set for quick access. This way, only users of this node can remove
     * their own usage within this object. Thus this method may later on be
     * changed to decreUsage(Node n, double amount).
     * Likewise for increUsage, it becomes increUsage(Node n, double amount).
     */
          
    /**
     * Sets the current usage to 0. This is typically used when the node is
     * disabled and re-enabled.
     */
    public void resetUsage() {
        efficiency = 0.0;
    }
    
    /**
     * Removed by Andy 08/04/05:
     *      Removed as getAvailCap is now refer to as percentage. Thus 
     *      getUsage is redundant to getEfficiency.
     */
//    /**
//     * Returns the amount of capacity which is available for use. An user can
//     * determine whether this node can meet the user's demand.
//     * @return  the available capacity of this node
//     * @throws  PostconditionException If the returned capacity is negative.
//     */
//    public double getAvailCap() throws PostconditionException {
//        double cap = (capacity * efficiency) - usage;
//        Assertion.post( cap >= 0, "Available capacity is " + cap,
//                                  "Available capacity must be non-negative");
//        return cap;        
//    }
    
    /**
     * Returns the value of utilisation for this node. This is calculated as
     * the ratio between usage and maximum capacity. This value can be used 
     * when choosing amongst node with similar amount of available usage.
     * @return  the utilisation of this node as a decimal
     * @throws  PostconditionException If the returned utilisation is not
     *          between 0.00 and 1.00.
     */    
    public double getEfficiency() throws PostconditionException {
        // Modified by Andy 08/04/05: to check if effciency is in range
        Assertion.post( (efficiency >= 0.00) && (efficiency <= 1.00),
                    "Efficiency/Utilisation is " + efficiency,
                    "Efficiency/Utilisation must be between 0.0 and 1.0");
        return efficiency;        
    }        
            
    /**
     * Returns the load factor of this node. This is calculated as the ratio
     * between the usage, and the maximum capacity subtracted by the usage.
     * This value can be used when choosing amongst nodes with similar amount
     * of available usage.
     * @return  the load factor of this node
     * @throws  PostconditionException If the returned load is negative.
     */
    public double getLoad() throws PostconditionException {
        /**
         * # Modified by Andy 08/04/05: 
         *  to reflect on the use of efficiency instead of name utilisation
         */

        double load = efficiency / (1.0 - efficiency);
        Assertion.post( load >= 0.00,
                        "Load factor is " + load,
                        "Load factor must be between 0.0 and 1.0");
        return load;          
    }
}
