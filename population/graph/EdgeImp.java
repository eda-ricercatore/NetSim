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
import java.lang.ClassCastException;

/**
 * This class is a basic implementation for an edge to be used in the software
 * <b>NetSim</b>. Since the <code>Edge</code> interface inherits properties of
 * <code>Usable</code> objects, this Edge is an <i>usable repairable</i>
 * edge.<p>
 *
 * An edge is <i>usable</i> because it is the only way for two Network nodes
 * to communicate with each other. The edge models the fact that there exists  
 * an upper bound for possible usage, since an edge (modelling a link) may be
 * subject to noise or signal attenuation. In addition, there maybe a need to
 * reserve bandwidth to limit utilisation or load.<p>
 *
 * Since the edge is <i>repairable</i>, it can be used to simulate the
 * likelihood of an edge failing. The edge has a threshold for maximum
 * allowable fail rate. Subject to this threshold, an edge should be replaced,
 * if the edge has a very high chance of failing and it is cheap to replace, 
 * or if the number of generations required for repair is too large and its 
 * absense is causing the Network to halt its operations.<p>
 *
 * The type of the edge is stored inside its label, and typically reflects on 
 * type of fromNode and toNode the edge connects. A cost is also associated
 * with an edge. This cost is that required for using the edge to communicate.
 * It is assumed that users of the edge are not timed and are charged per use.
 *
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.2-andy
 * @since           0.2
 * @acknowledgement Matthew Berryman, Wei-li Khoo and Hiep Nguyen, and Dr
 *                  Elizabeth Cousins for Load Factor formulae (from course
 *                  Communication Network Design taught in University of
 *                  Adelaide 2004).
 */  
public class EdgeImp extends Repairable implements Edge {
    // nodes which the edge originates and ends
    private Node fromNode, toNode;
    // label indicating the type of the edge
    private String label;
    // the maximum capcity of the edge
    private double capacity;
    // the operating efficiency of an edge ranged from 0.00 to 1.00
    private double efficiency;
    // the cost of using this edge
    private double edgeCost;
    // the amount of the edge's capacity currently in use
    private double usage = 0.0;

    // Default Constructor
    /**
     * Creates a default instance of EdgeImp. An Edge created using this
     * default constructor cannot be used immediately, since it would have the
     * properties of non-repairable and non-usable objects.<p>
     * That is, extremely high repair generation requirement, probablity of 
     * failure of 1.00 and  maximum allowable probablity of failure
     * (threshold) of 0%. Also, this edge's capacity and operating efficiency
     * are both 0, and its cost is <code>POSITIVE_INFINITY</code> (+oo).<p>
     * Thus the above mentioned properties must be altered before using this
     * edge.
     * @see Repairable
     */
    public EdgeImp() {
        super();            // call constructor of super class
        capacity = 0.00;    // 0 capacity
        efficiency = 0.00;  // operating at 0% efficiency
        label = "";         // empty label
        edgeCost = Double.POSITIVE_INFINITY;    // extremely high cost
    }
    
    // Standard Constructors
    /**
     * Creates an instance of EdgeImp without a label, fromNode and toNode. 
     * That is, this creates an edge which is not connected to any nodes. This
     * constructor is used for creating an edge template which can be reused,
     * as many time as desired, to create edges with similar properties.<p>
     * In order to construct this edge, a model Repairable object is needed.
     * All the Repairable properties are obtained from the model. The usable
     * object properties are taken in via an array. The contents of the array
     * includes:
     * <ul><li>capacity of the edge
     *     <li>operating efficiency of the edge
     *     <li>the cost of using the edge</ul>
     *
     * Note: Doesn't matter if "r" is a Repairable object, and EdgeImp or
     * NodeImp. As we are only copying values from repairable. Ideally, we
     * should create a reapriable for creating edges and a separate one for
     * node.
     *
     * @param   param The array of parameters as outlined above.
     * @param   r The repairable object template for this edge
     * @throws  PreconditionException If capacity or cost of edge is negative.
     * @throws  PreconditionException If efficiency is not between 0 and 1.
     * @see     Repairable
     */
    public EdgeImp (double[] param, Repairable r)
    throws PreconditionException {
        // call super class constructor with appropriate parameters
        super(r.getAvgRepGen(), r.getFailureProb(), r.getThreshold(),
            r.activated());
        // check preconditions
        // # Modified by Zhiyang Ong
        Assertion.pre(param.length >= 3,
                       "param is of the right size; size of " + param.length,
                       "There exists insufficient properties in param");
        Assertion.pre( param[0] >= 0, "Capacity is " + param[0],
                                      "Capacity must not be negative");
        Assertion.pre((param[1] >= 0.00) && (param[1] <= 1.00),
                       "Efficiency to be set is " + param[1],
                       "Efficiency to be set must be between 0.0 and 1.0");
        Assertion.pre( param[2] >= 0, "Cost of edge is " + param[2],
                                      "Cost of edge must not be negative");
        label      = "";        // label is an empty string (undetermined)
        capacity   = param[0];  // store parameters
        efficiency = param[1];
        edgeCost   = param[2];
    }
    
    /**
     * Creates an instance of EdgeImp from a template edge. This template edge
     * must be an instance of <code>Repairable</code> so that the properties 
     * for this edge, such as the probability of failure and the threshold, 
     * and the required generations for repair, can be copied. Other 
     * properties such as capacity, efficiency and cost (properties of 
     * <code>Usable</code> object) are also copied.<p>
     * 
     * Having all relevant properties copied, a complete edge is created by
     * connecting it to the <code>from</code> node and <code>to</code> node,
     * and assigning a label <code>l</code> to it.
     *
     * @param   from FromNode of this edge.
     * @param   to ToNode of this edge.
     * @param   l The label indicating type of this edge
     * @param   e The repairable edge template for this edge. That is
     *          <code>e</code> inherits from <code>Repairable</code>.
     * @throws  ClassCastException If <code>e</code> is not an instance of
     *          <code>Repairable</code>.
     * @throws  PreconditionException If <code>from</code> or <code>to</code>
     *          is Null.
     * @throws  PreconditionException If <code>l</code> is Null.
     * @see     Repairable
     */          
    public EdgeImp (Node from, Node to, String l, Edge e)
    throws PreconditionException, ClassCastException {
        /**
         * call super class constructor with appropriate parameters obtained
         * from the template edge
         */
        super( ((Repairable) e).getAvgRepGen(),
               ((Repairable) e).getFailureProb(),
               ((Repairable) e).getThreshold(),
               ((Repairable) e).activated() );
        // check preconditions
        Assertion.pre( from != null, "FromNode is not \"null\"",
                                     "FromNode must not be null");
        Assertion.pre( to != null, "ToNode is not \"null\"",
                                   "ToNode must not be null");        
        Assertion.pre( l != null, "Label to be set is " + l,
                                  "Label to be set must not be null");
        
        fromNode   = from;                // store the origin node
        toNode     = to;                  // store the end node
        label      = new String(l);       // store the label
        capacity   = e.getMaxCapacity();  // obtain and store values from "e"
        efficiency = e.getEfficiency();
        edgeCost   = e.getCost();
    }
        
    /**
     * Creates an instance of EdgeImp. This will construct an edge with all
     * the necessary properties from the elementary parameters. These
     * parameters includes the nodes this edge connects, its label, the 
     * number of generations required for repair, an array of paramaters of
     * decimal numbers, and a boolean determining whether the edge should be
     * actiavated. <p>
     * 
     * The contents of the array metioned above includes:
     * <ul><li>capacity of the edge
     *     <li>operating efficiency of the edge
     *     <li>the cost of using the edge
     *     <li>probability of failure
     *     <li>threshold for probability of failure</ul>
     *
     * @param   node The array storing the toNode and fromNode of this edge
     * @param   l The label indicating type of this edge
     * @param   repGens The average number of generations required for repair
     * @param   param The array of parameters as outlined above.
     * @param   act <code>true</code> if this edge is activated initially
     * @throws  PreconditionException If <code>l</code> is Null.
     * @throws  PreconditionException If capacity or cost of edge is negative.
     * @throws  PreconditionException If efficiency is not between 0 and 1.
     * @see Repairable
     */
    public EdgeImp (Node[] node, String l, int repGens, double[] param,
    boolean act) throws PreconditionException {
        // call super class constructor with appropriate parameters
        super(repGens, param[3], param[4], act);
        // check preconditions
        // # Modified by Zhiyang Ong
        Assertion.pre(param.length >= 5,
                       "param is of the right size; size of " + l,
                       "There exists insufficient properties in param");		  
        Assertion.pre( l != null,
                       "Label to be set is " + l,
                       "Label to be set must not be null");
        Assertion.pre( param[0] >= 0, "Capacity of edge is " + param[0],
                                    "Capacity of edge must not be negative");

        Assertion.pre( (param[1] >= 0.00) && (param[1] <= 1.00),
                        "Efficiency to be set is " + param[1],
                        "Efficiency to be set must be between 0.0 and 1.0"+param[1]);
        Assertion.pre( param[2] >= 0, "Cost of edge is " + param[2],
                                      "Cost of edge must not be negative");
                        
        fromNode   = node[0];       // store the origin node
        toNode     = node[1];       // store the end node
        label      = new String(l); // create a copy of the String and store
        capacity   = param[0];      // and the Usable variables
        efficiency = param[1];
        edgeCost   = param[2];
    }        
    
    /**
     * implmentation of Edge Interface
     */

    /**
     * Returns the departure node of this edge
     * @return  the node which this edge starts from
     */
    public Node getFromNode() {
        return fromNode;
    }
    
    /**
     * Returns the destination node of this edge
     * @return  the node which this edge ends
     */
    public Node getToNode() {
        return toNode;
    }

    /**
     * Sets the departure node of this edge
     * @param   n The node which this edge starts from
     */
    public void setFromNode(Node n) {
        fromNode = n;
    }
    
    /**
     * Sets the destination node of this edge
     * @param   n The node which this edge ends
     */
    public void setToNode(Node n) {
        toNode = n;
    }
    
    /**
     * Returns the label or type of this edge. This is implemented as a
     * String, but in later versions Objects maybe used for more flexibility.
     * @return  the label which sufficiently describes the Edge
     * @throws  PostconditionException If Null is returned.
     */
    public String getLabel() throws PostconditionException {
        Assertion.post( label != null,
                        "Label of this edge is " + label,
                        "Label of this edge must not be null");
        return label;
    }

    /**
     * Sets the label or type for the edge. This is implemented as a String,
     * but in later versions Objects maybe used for more flexibility.
     * @param   l A String that sufficiently describes the Edge
     * @throws  PreconditionException If <code>l</code> is a Null String.
     */
    public void setLabel(String l) throws PreconditionException {
        Assertion.pre( l != null,
                       "Label to be set is " + l,
                       "Label to be set must not be null");
        label = new String(l);
    }
    
    /**
     * Returns the cost of using this edge to transfer data.
     * @throws  PostconditionException If the returned cost is negative.
     * @return  the cost of transfering data along this Edge
     */
    public double getCost() throws PostconditionException {
        Assertion.post( edgeCost >= 0,
                        "Cost of edge is " + edgeCost,
                        "Cost of edge must not be negative");
        return edgeCost;    
    }
    
    /**
     * Sets the cost of using this edge to transfer data.
     * @param   cost The new cost of this edge
     * @throws  PreconditionException If <code>cost</code> is negative.
     */
    public void setCost(double cost) throws PreconditionException {
        Assertion.pre( cost >= 0,
                        "New cost of edge is " + cost,
                        "New cost of edge must not be negative");
        edgeCost = cost;
    }
    
    /**
     * implmentation of Usable Interface
     */

    /**
     * Returns the maximum capacity of this edge. The units to measure
     * capacity is application dependent. For example of data traffic,
     * capicity may be in units of Mbps. 
     * @return  the capacity of this edge operating at 100% efficiency
     * @throws  PostconditionException If the returned capacity is negative.
     */
    public double getMaxCapacity() throws PostconditionException {
        Assertion.post(capacity >= 0, "The maximum capacity is " + capacity,
                                "The maximum capcity must not be negative");
        return capacity;
    }
    
    /**
     * Sets the maximum capacity of this edge. The units to measure capacity
     * is application dependent. For example of data traffic, capicity may be
     * in units of Mbps. <p>
     *
     * The capacity limits the usage of this edge, so the new capacity, 
     * subject to efficiency, must not be less than the current usage. If a
     * lesser value is to be set, usage must be reduced. This automatically
     * implies if input parameter is negative, change of capacity will also
     * be unsucessful.
     *
     * @param   cap The capacity of this edge operating at 100% efficiency
     * @return  <code>true</code> If the maximum capacity was changed
     *          successfully.
     */
    public boolean setMaxCapacity(double cap) {
        Debugger.debug("The maximum capacity to be set is " + cap);
        if ( (cap*efficiency) >= usage ) {
            capacity = cap;
            return true;
        } else return false;
    }

    /**
     * Returns the operating efficiency this edge is running. This is 
     * measured as a percentage, this is used in conjunction with the maximum
     * capacity to specify the total capacity available for use.
     * @return  the efficiency of this edge as a percentage
     * @throws  PostconditionException If the returned capacity is not
     *          between 0.00 and 1.00
     */
    public double getEfficiency() throws PostconditionException {
        Assertion.post( (efficiency >= 0.00) && (efficiency <= 1.00),
                        "Efficiency factor is " + efficiency,
                        "Efficiency factor must be between 0.0 and 1.0");
        return efficiency;
    }
    
    /**
     * Sets the operating efficiency for this edge. This is measured as a 
     * percentage, this is used in conjunction with the maximum capacity to
     * specify the total capacity available for use.<p>
     *
     * The efficiency limits the usage of this edge, so if the new value set
     * result in a total available capacity less than the cuurent usage, the
     * change can not be made. 
     *
     * @param   eff The level of efficiency as a number between 0.00 and 1.00
     *          inclusive.
     * @return  <code>true</code> If the efficiency was changed successfully.
     * @throws  PreconditionException If <code>eff</code> is not between 0.00
     *          and 1.00.
     */
    public boolean setEfficiency(double eff) throws PreconditionException {
        Assertion.pre( (eff >= 0.00) && (eff <= 1.00),
                        "Efficiency to be set is " + eff,
                        "Efficiency to be set must be between 0.0 and 1.0");
        if ( (capacity*eff) >= usage ) {
            efficiency = eff;
            return true;
        } else return false;    
    }

    /**
     * Returns the usage on this edge. The units to quantify the usage should
     * be consistent with that of the capacity.
     * @return  the current usage of this edge
     * @throws  PostconditionException If the returned usage is negative.
     */
    public double getUsage() throws PostconditionException {
        Assertion.post( usage >= 0, "Usage is " + usage,
                                    "Usage must be non-negative");
        return usage;
    }
    
    /**
     * Increases the current usage by an arbitrary amount. This is typically
     * used when a request is made to use this object. Note the increase or
     * request may not always be successful due to availability.
     * @param   amount The amount of usage to increase
     * @return  <code>true</code> If the usage was increased successfully.
     * @throws  PreconditionException If <code>amount</code> is negative.
     */
    public boolean increUsage(double amount) throws PreconditionException {
        Assertion.pre( amount >= 0,
                       "The amount to increment is " + amount,
                       "The amount to increment must be non-negative");
        if( amount <= getAvailCap() ) {
            usage += amount;
            return true;
        } else return false;
    }  

    /**
     * Decreases the current usage by an arbitrary amount. This is typically
     * used when an user finishes using use this object.
     * @param   amount The amount of usage to decrease
     * @return  <code>true</code> If the usage was decreased successfully.
     * @throws  PreconditionException If <code>amount</code> is negative.
     */
    public boolean decreUsage(double amount) throws PreconditionException {
        Assertion.pre( amount >= 0,
                       "The amount to decrement is " + amount,
                       "The amount to decrement must be non-negative");
        if( (usage - amount) >= 0 ) {
            usage -= amount;
            return true;
        } else return false;
    }
    /**
     * If required, later versions should register users of this object, in a
     * hash set for quick access. This way, only users of this node can remove
     * their own usage within this object. Thus this method may later on be
     * changed to decreUsage(Node n, double amount).
     * Likewise for increUsage, it becomes increUsage(Node n, double amount).
     */
          
    /**
     * Sets the current usage to 0. This is typically used when the edge is
     * disabled and re-enabled.
     */
    public void resetUsage() {
        usage = 0;
    }
    
    /**
     * Returns the amount of capacity which is available for use. An user can
     * determine whether this edge can meet the user's demand.
     * @return  the available capacity of this edge
     * @throws  PostconditionException If the returned capacity is negative.
     */
    public double getAvailCap() throws PostconditionException {
        double cap = (capacity * efficiency) - usage;
        Assertion.post( cap >= 0, "Available capacity is " + cap,
                                  "Available capacity must be non-negative");
        return cap;        
    }
    
    /**
     * Returns the value of utilisation for this edge. This is calculated as
     * the ratio between usage and maximum capacity. This value can be used 
     * when choosing amongst node with similar amount of available usage.
     * @return  the utilisation of this edge as a decimal
     * @throws  PostconditionException If the returned utilisation is not
     *          between 0.00 and 1.00.
     */    
    public double getUtilisation() throws PostconditionException {
        double util = usage / capacity;
        Assertion.post( (util >= 0.00) && (util <= 1.00),
                        "Utilisation is " + util,
                        "Utilisation must be between 0.0 and 1.0");
        return util;        
    }        
            
    /**
     * Returns the load factor of this edge. This is calculated as the ratio
     * between the usage, and the maximum capacity subtracted by the usage.
     * This value can be used when choosing amongst nodes with similar amount
     * of available usage.
     * @return  the load factor of this edge
     * @throws  PostconditionException If the returned load is negative.
     */
    public double getLoad() throws PostconditionException {
        double load = usage / (capacity - usage);
        Assertion.post( load >= 0.00,
                        "Load factor is " + load,
                        "Load factor must be between 0.0 and 1.0");
        return load;          
    }
}
