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
 * This is an engine that runs a <b>Boreadth-First-Search</b> algorithm in
 * order to perform the calculation of resistance in the network.<p> 
 *
 * There are 2 modes for the calculation. The default mode will use the edge 
 * cost for calculating the resistance, whereas the second mode will use unit 
 * cost for the calculation.<p>
 *
 * The algorithm performs in O(n^3) complexity. [At worst case, each of the 
 * "n" nodes can be connected via "n" paths of "n" hops.]
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.4.1
 * @see BFSNode
 */
 public class BFSResistance {
    // determines if unit resistance will be used
    private boolean     useUnitResistance;    

    // the adjacency matrix and nodelist associated with current chromosome
    private double[][]  adjMatrix;
    private ArrayList   nodeList;
    // the list of destination nodes for each node in the node list
    private ArrayList   destNodes;

    // stores the resistance between any pairs of nodes
    private double[][]  resistMatrix;
    
    /**
     * the list of BFSNodes to be expanded. This will be cleared when 
     * expanding the graph from a different source node.
     */
    private ArrayList   nodesToExpand;
    // the inverse of resistance of a node to any other node
    private double[]    resistArray;

    /**
     * Constructs a default instance of BFSResistance, which will use the
     * actual edge cost in calculating resistance.
     */
    public BFSResistance() {
        useUnitResistance = false;   
    }

    /**
     * Constructs an instance of BFSResistance, specifying whether unit 
     * resistance will be used.
     * @param   use     set to <code>true</code>, if using unit resistance is
     *                  desired.
     */
    public BFSResistance(boolean use) {
        useUnitResistance = use; 
    }

    /**
     * Calculates the resistance of the network. The graph represented by the
     * chromosome will be expanded many times, each with a different starting
     * node. Each iteration will determine the resistance from a single 
     * source node. Thus, these iterations will approximate the resistance 
     * between any two nodes in the network.<p>
     *
     * @param   c   the chromosome which the resistance is to be determined
     */   
    public double[][] getResistance (Chromosome c) {
        // setup class for graph traversal
        this.initialise(c);
        BFSNode root;
        for (int i = 0; i < numNodes(); i++) {
            //Debugger.enableTrace(true);
            //Debugger.debug("Expanding for node#"+i);
            //Debugger.enableTrace(false);
            root = newSource(i);        // prepare node for expansion
            expand(root);               // start expansion
            invertResistanceArray(i);   // take inverse to obtain resistance
            setResults(i, resistArray); // store the result for node "i"
        }
        return getMatrix();             // return final result
    }

    /**
     * Initialises the process of finding resistance by retriving appropriate
     * nodeList, destination nodes and the adjacency matrix, etc. If 
     * instructed to use unit resistance instead of the acutal cost, the 
     * non-zero and non-infinity numbers in the adjacency matirx will be 
     * converted to one.
     *
     * @param   c   the chromosome which the resistance is to be determined
     */    
    private void initialise(Chromosome c) {
        nodeList      = c.getNodeList();
        destNodes     = c.getDataArray();
        adjMatrix     = c.getAdjacencyMatrix();
        if(useUnitResistance) adjMatrix = adj2linkMatrix(adjMatrix);
        resistMatrix  = new double[numNodes()][numNodes()];
        nodesToExpand = new ArrayList();        
    }

    /**
     * Prepares for expansion of a source node with index "i". This will 
     * reset the resistance array for node "i"'s exclusive use. A new BFSNode
     * will be constructed and is ready for expansion.
     * 
     * Note: there should be no need to reset the nodeQueue as it should be 
     * emptied at the end of expansion
     *
     * @param   i   the index of the source node
     * @return  the BFSNode representation of the source node
     */
    private BFSNode newSource(int i) {
        // reset array
        resistArray   = new double[numNodes()];
        // each node should have traversed itself
        ArrayList self = new ArrayList();
        self.add(nodeList.get(i));

        return new BFSNode(this, 0.0, self, (ArrayList) destNodes.get(i) );
    }

    /**
     * Iteratively expand BFSNodes to calculate the resistance. Each 
     * expansion of a node should update the resistance array;
     * @param   bNode   the first node to expand
     */       
    private void expand(BFSNode bNode) {
        // expand root node;
        bNode.expand();
        // iteratively deque and expand nodes in the queue
        while(moreToExpand()) {
            headNode().expand();
        }
    }

    /**
     * Inverts the values in the resistance array, since the values are the
     * sum of the inverse of the resistance. Note that if the value is 0 and
     * the array index does not correspond to the source node, the resistance
     * should be infinity (+oo);
     * @param   index   the index of the source node
     */
    private void invertResistanceArray(int index) {
//        String line = "";
//        for (int i = 0; i < resistArray.length; i++) {
//            if (i==0) line += "Before [";
//            else      line += ", ";
//            line += "" + resistArray[i];
//            if (i == (resistArray.length-1)) line += "]";         
//        }
//        Debugger.printErr(line);
        
        
        // for each element
        for (int i = 0; i < resistArray.length; i++) {
            // check if the value is 0
            if ( resistArray[i] == 0 ) {
                /**
                 * set element to +oo, if the element does not corresspond
                 * to the source node. 
                 */
                if (i != index) resistArray[i] = Double.POSITIVE_INFINITY;
            } // otherwise take the inverse
            else resistArray[i] = 1.0 / resistArray[i];
        }
        
//        line = "";
//        for (int i = 0; i < resistArray.length; i++) {
//            if (i==0) line += "After [";
//            else      line += ", ";
//            line += "" + resistArray[i];
//            if (i == (resistArray.length-1)) line += "]";         
//        }
//        Debugger.printErr(line);
    }

    /**
     * Sets the level of resistance of traversing from node "nodeIndex" to
     * all other nodes in the network.
     * @param   nodeIndex   the index of the source node
     * @param   resistance  an array of resistance to each destination node
     * @throws  PreconditionException if nodeIndex is out of bounds
     */
    private void setResults(int nodeIndex, double[] resistance) 
    throws PreconditionException {
//        Assertion.pre( nodeIndex >= 0 && nodeIndex < numNodes(), "",
//                       "ERROR!!! setResults: nodeIndex is out of bounds");
        double[][] matrix = getMatrix();
        for (int i = 0; i < matrix[nodeIndex].length; i++) 
            matrix[nodeIndex][i] = resistArray[i];
    }
 
    /**    
     * Obtains the matrix of resistance in the network.
     * @return  the resistance matrix
     */
    private double[][] getMatrix() {
        return resistMatrix;
    }
    
    /**
     * Returns the number of nodes in the network
     */
    private int numNodes() {
        return nodeList.size();
    }    
    
    /**
     * Converts an adjacency matrix to a link matrix. That is replace all 
     * values, which are not <code>Double.POSITIVE_INFINITY</code> or 0, with
     * <b>1</b>. This indicats a connection between two nodes.
     *
     * @param   m   an adjacency matrix with dimension n x n, where n is the
     *              number of nodes in the network
     * @return  a link matrix with only values of 1 indicating a connection 
     *          between nodes
     */
    private double[][] adj2linkMatrix(double[][] m) {
        // create storage for result
        double[][] result = new double[m.length][m[0].length];
        // for every row of the matrix
        for(int i = 0; i < m.length; i++) {
            // for every element in that row
            for(int j = 0; j < m[i].length; j++) {                
                if (m[i][j] != Double.POSITIVE_INFINITY && m[i][j] != 0)
                    result[i][j] = 1;
                else 
                    result[i][j] = m[i][j];
            }
        }
        return result;
    }
 
    /**
     * Checks if there are more nodes to expand in the search
     */
    private boolean moreToExpand() {
        return !nodesToExpand.isEmpty();
    }
    
    /**
     * Dequeues the node to expand.
     */
    private BFSNode headNode() {
        BFSNode bNode = null;
        if ( !nodesToExpand.isEmpty() ) 
            bNode = (BFSNode) nodesToExpand.remove(0);
        return bNode;
    }
 
    /**
     * Adds a node to be expanded to the queue, and updates the resistance 
     * measure for the corresponding node.
     *
     * @param   bNode   the BFSNode to be inserted into the 
     *                  queue of nodes to be expanded
     * @throws  PreconditionException If the corresponding graph node in 
     *          bNode does not exists in the nodeList.
     */
    public void addToQueue(BFSNode bNode) throws PreconditionException {
        // obtain graph node and obtain its index
        Node graphNode = bNode.getGraphNode();
        int index = nodeList.indexOf(graphNode);
        // check if its index is valid
        if (index < 0) {
            String msg = "ERROR!!! Corresponding graph node in bNode does " +
                         "not exist\n    in the nodeList.";
            throw new PreconditionException(msg);
        }
        // update with the inverse of the resistance
        updateResistanceMatrix(index, 1.0/bNode.getAccumCost());
        // add node to queue
        nodesToExpand.add(bNode);
    }

    /**
     * Adds the inverse of resistance to a corresponding array position.
     *
     * @param   index   the index of the correspoding node
     * @param   amount  the amount to increment the sum by
     */    
    private void updateResistanceMatrix(int index, double amount) {
        resistArray[index] += amount;   
    }
    
    /**
     * Obtains the cost between two nodes.
     * @param   from    the source node
     * @param   to      the destination node
     * @throws  PreconditionException if from and to nodes does not exist in
     *          the node list
     */
    public double getCost(Node from, Node to) throws PreconditionException {
        int indexFrom = nodeList.indexOf(from);
        int indexTo   = nodeList.indexOf(to); 
        if (indexFrom == -1) {
            String msg = "ERROR!!! From node does not exists in the graph.";
            throw new PreconditionException(msg);
        } else if(indexTo == -1) {
            String msg = "ERROR!!! To node does not exists in the graph.";
            throw new PreconditionException(msg);
        }
        
        return adjMatrix[indexFrom][indexTo];
    }
    
    /**
     * Retrieves the list of destination node of the source node "n"
     * @param   n   the source node of the destination nodes
     * @return  a list of destination nodes from node "n", or null if the
     *          node "n" does not exist in the graph
     */
    public ArrayList getReachable(Node n) {
        ArrayList reachable = null;
        int i = nodeList.indexOf(n);   
        if (i >= 0) reachable = (ArrayList) destNodes.get(i);
        else        Debugger.printErr("Node does not exists in the graph.");
        return reachable;
    }
}
    