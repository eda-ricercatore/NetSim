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
 * This interface abstracts a usable object. A class implementing the methods
 * specified here in effect becomes an <b>Agent</b> which attends to data or 
 * requests from users, or it may be a <b>Medium</b> via which transmits data
 * or requests to the <b>Agent</b>.<p>
 *
 * In <b>NetSim</b>, all nodes intended to be Servers, and all edges may
 * implement this interface, so that they can be <i>used</i> to serve or
 * transmit data. This provides for methods to query the capacity and usage
 * of an <b>Usable</b> object.
 *
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.2-andy
 * @since           0.3
 */ 
public interface Usable {
    
    /**
     * Returns the maximum capacity of this Usable object. The units to
     * quantify capacity is application dependent. For example of data
     * traffic, capicity may be in units of Mbps. 
     * @return  the capacity of this object operating at 100% efficiency
     * @throws  PostconditionException If the returned capacity is negative.
     */
    public double getMaxCapacity() throws PostconditionException;
    
    /**
     * Sets the maximum capacity of this Usable object. The units measuring
     * the capacity is application dependent. For example of data traffic,
     * capicity may be in units of Mbps.<p>
     *
     * The capacity limits the usage of this object, so the new capacity, 
     * subject to efficiency, must not be less than the current usage. If a
     * lesser value is to be set, usage must be reduced. This automatically
     * implies if input parameter is negative, change of capacity will also
     * be unsucessful.
     *
     * @param   cap The capacity of this object operating at 100% efficiency
     * @return  <code>true</code> If the maximum capacity was changed
     *          successfully.
     */
    public boolean setMaxCapacity(double cap);

    /**
     * Returns the operating efficiency this Usable object is running. This
     * is measured as a percentage, this is used in conjunction with the
     * maximum capacity to specify the total capacity available for use.
     * @return  the efficiency of this object as a percentage
     * @throws  PostconditionException If the returned capacity is not
     *          between 0.00 and 1.00
     */
    public double getEfficiency() throws PostconditionException;
    
    /**
     * Sets the operating efficiency for this Usable object. This is measured
     * as a percentage, this is used in conjunction with the maximum capacity
     * to specify the total capacity available for use.<p>
     *
     * The efficiency limits the usage of this object, so if the new value
     * set result in a total available capacity less than the cuurent usage,
     * the change can not be made. 
     *
     * @param   eff The level of efficiency as a number between 0.00 and 1.00
     *          inclusive.
     * @return  <code>true</code> If the efficiency was changed successfully.
     * @throws  PreconditionException If <code>eff</code> is not between 0.00
     *          and 1.00.
     */
    public boolean setEfficiency(double eff) throws PreconditionException;

    /**
     * Returns the usage on this Usable object. The units to quantify the
     * usage should be consistent with that of the capacity.
     * @return  the current usage of this Usable object
     * @throws  PostconditionException If the returned usage is negative.
     */
    public double getUsage() throws PostconditionException;
    
    /**
     * Increases the current usage by an arbitrary amount. This is typically
     * used when a request is made to use this object. Note the increase or
     * request may not always be successful due to availability.
     * @param   amount The amount of usage to increase
     * @return  <code>true</code> If the usage was increased successfully.
     * @throws  PreconditionException If <code>amount</code> is negative.
     */
    public boolean increUsage(double amount) throws PreconditionException;

    /**
     * Decreases the current usage by an arbitrary amount. This is typically
     * used when an user finishes using use this object. 
     * @param   amount The amount of usage to decrease
     * @return  <code>true</code> If the usage was decreased successfully.
     * @throws  PreconditionException If <code>amount</code> is negative.
     */
    public boolean decreUsage(double amount) throws PreconditionException;
    
    /**
     * Resets the current usage to 0. This is typically used when the object
     * is disabled and re-enabled.
     */
    public void resetUsage();
    
    /**
     * Returns the amount of capacity which is available for use. An user can
     * determine whether this Usable object can meet the user's demand.
     * @return  the available capacity of this object
     * @throws  PostconditionException If the returned capacity is negative.
     */
    public double getAvailCap() throws PostconditionException;

    /**
     * Returns the value of utilisation for this Usable object. This value
     * can be used when choosing amongst objects with similar amount of
     * available usage.
     * @return  the utilisation of this object as a decimal
     * @throws  PostconditionException If the returned utilisation is not
     *          between 0.00 and 1.00.
     */    
    public double getUtilisation() throws PostconditionException;
            
    /**
     * Returns the load factor of this Usable object. This value can be used
     * when choosing amongst objects with similar amount of available usage.
     * @return  the load factor of this object
     * @throws  PostconditionException If the returned load is negative.
     */    
    public double getLoad() throws PostconditionException;
}
