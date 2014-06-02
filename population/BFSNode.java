/*
 * population package determines the bahaviour of chromosomes in the
 * population of the network. That is, each chromosome in the population
 * represents a network.
 * The behaviour of chromosmes is determined by modifying the data pertaining
 * to each chromosome
 */
package population;

// Importing packages
import java.util.ArrayList;
import population.graph.*;
import utility.*;

/**
 * The simple data structure used for breadth-first search algorithm. The
 * main purpose for the node is to keep track of the accumulated cost, and 
 * the lists of nodes that have been traverse on the path, and those that 
 * need to be expanded. <p>
 *
 * Note that the nodes are not explicitly linked. This is because only the
 * accumulated cost of the path (at each node) is required, and there is no
 * need to do a tree traversal after all possible path has been explored.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.4.1
 */
 public class BFSNode {
    // cost the path travelled so far
    private double accumCost;           
    // list of visited nodes in order of traversal
    private ArrayList nodesVisited;
    // list of nodes to be visisted
    private ArrayList nodesToVisit;
    // the object that handles the breadth-first search algorithm
    BFSResistance bfs;
    
    /**
     * <font color="red">THIS DEFAULT CONSTRUCTOR SHOULD NOT BE USED.</font>
     */
    public BFSNode() {
        Debugger.printErr("WARNING!!! " +
                            "Please use the standard constructor!!!");
        bfs = null;
        accumCost = Double.POSITIVE_INFINITY;
        nodesVisited = null;
        nodesToVisit = null;
    }
    
    /**
     * Constructs an instance of BFSNode ready for expansion, when used to
     * calculate resistance. Note that the list of nodes to be visited will
     * be determined by forming a list with every destination in reachable
     * minus those in the visited list. [reachable - (reachable & visited)];
     *
     * @param   host    the object that handles the BFS algorithm
     * @param   cost    the accumulated cost of the path, upto this node
     * @param   visited the list of visited nodes along the path
     * @param   reachable the list of nodes that are adjacent to this node
     */
    public BFSNode(BFSResistance host, double cost, 
    ArrayList visited, ArrayList reachable) {
        bfs          = host;
        accumCost    = cost;
        nodesVisited = visited;
        nodesToVisit = findUnvisited(visited, reachable);
    }
    
    /**
     * Finds a list of unvisited nodes by comparing the list of visited and
     * reachable nodes. 
     *
     * @param   visited     list of visited nodes
     * @param   reachable   list of adjacent nodes
     */
    private ArrayList findUnvisited(ArrayList visited, ArrayList reachable) {
        // copy the list of reachable nodes
        ArrayList newList = new ArrayList (reachable);
        // remove all visited nodes from the new list
        newList.removeAll(visited);
        // return the result
        return newList;
    }
       
    /**
     * Expands this node, and explore the nodes that haven't been visited 
     * along this path. In order to do so, new BFSNodes with new accumulated
     * costs will be created and added to the queue of nodes to be visited.
     */
    public void expand() {
        // obtain the node that this BFSNode refers to;
        Node fromNode = getGraphNode();
        // the node that this BFSNode will connect to;
        Node toNode;
        // the accumalated cost for the new BFSNode
        double tmpCost;
        // lists to store the visited and reachable nodes for the new BFSNode
        ArrayList tmpVisited, tmpReachable;
        // temp storage for the BFSNode to be added to the queue
        BFSNode newBNode;
        
        // for each node to be visted
        for (int i = 0; i < nodesToVisit.size(); i++) {       
            // obtain the toNode
            toNode = (Node) nodesToVisit.get(i);
            // calculate the accumulated cost at "toNode"
            tmpCost  = getAccumCost() + bfs.getCost( fromNode, toNode );
            /**
             * copy and add the toNode to a list, which will be used as the
             * list of visted node for the toNode
             */
            tmpVisited = new ArrayList(nodesVisited);
            tmpVisited.add(toNode);
            // obtain the list of destination nodes for "toNode"
            tmpReachable = bfs.getReachable(toNode);
            // create a new BFSNode and add to queue
            newBNode = new BFSNode(bfs, tmpCost, tmpVisited, tmpReachable);
            bfs.addToQueue(newBNode);
        }
    }
    
    /**
     * Obtains the graph node corresponding to this node. This should be the
     * last added node in the visited node list
     * @return  the graph node corresponding to thie BFSNode
     */
    public Node getGraphNode() {
        return (Node) nodesVisited.get(nodesVisited.size()-1);
    }
    
    /**
     * Retrieves the cost of the path traversed so far.
     * @return  the accumulated cost of the path
     */
    public double getAccumCost() {
        return accumCost;
    }
}