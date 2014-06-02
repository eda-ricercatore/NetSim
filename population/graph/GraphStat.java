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
 * This interfaces provides method for reporting of node statistics. Two of
 * the types of statistical information are pleiotropy and redundancy. These
 * are the main factors considered in NetSim. A secondary interest in the
 * statistics is the clustering factor of each node, which is important in
 * terms of load balancing. These statistic function should work seamlessly
 * with the <code>Function</code> interface to calculate fitness.
 *
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.3-andy
 * @since           0.3
 */
public interface GraphStat {
    
    /**
     * Returns the number of Server nodes in the network.
     * @return  the number of Servers
     * @throws  PostconditionException If the returned number is negative.
     */   
    public int numServers() throws PostconditionException;

    /**
     * Returns the number of Client nodes in the network.
     * @return  the number of Clients
     * @throws  PostconditionException If the returned number is negative.
     */   
    public int numClients() throws PostconditionException;

    /**
     * Returns the degree of pleiotropy of a node provided to other Server
     * nodes only.
     * @return  the degree of pleiotropy due to servers
     */ 
    public double pleioToServer(Node n);

    /**
     * Returns the degree of pleiotropy of a node provided to other Client
     * nodes only.
     * @return  the degree of pleiotropy due to clients
     */ 
    public double pleioToClient(Node n);

    /**
     * Returns the degree of pleiotropy of a node provided to all other
     * nodes.
     * @return  the degree of pleiotropy due to all nodes
     */ 
    public double pleioToAll(Node n);

    /**
     * Returns the degree of redundancy of a node provided by other Server
     * nodes only.
     * @return  the degree of redundancy due to servers
     */ 
    public double redunFromServer(Node n);

    /**
     * Returns the degree of redundancy of a node provided by other Client
     * nodes only.
     * @return  the degree of redundancy due to clients
     */ 
    public double redunFromClient(Node n);

    /**
     * Returns the degree of redundancy of a node provided by all other
     * nodes.
     * @return  the degree of redundancy due to all nodes
     */ 
    public double redunFromAll(Node n);

    /**
     * Return the clustering factor of this node.
     * @return  the clustering factor
     */
    public double clusterFactor(Node n);    
}
