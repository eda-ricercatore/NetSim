/**
 * population package determines the bahaviour of chromosomes in the
 * population of the network. That is, each chromosome in the population
 * represents a network.
 * The behaviour of chromosmes is determined by modifying the data pertaining
 * to each chromosome
 */
package population;

// Importing packages
import java.util.ArrayList;
import java.util.Arrays;
import utility.*;
import population.graph.*;

/**
 * Class to describe the network of Nodes in a network/Graph.
 * Each cell of this Chromosome represents a Node in the network
 * Chromosomes with be mutated and mated with other chromosomes to evolve
 * towards a fitter chromosome
 * The fitness of the chromosome is determined by the pleiotropy and
 * redundancy of the network.
 * Each Node in the network is stored in an ArrayList. For each Node contained
 * in this ArrayList, it contains a ArrayList of Nodes that this Node is
 * connected to. Hence, you have an ArrayList of ArrayLists.
 *
 * @author  Zhiyang Ong
 * @author  Andy Lo
 * @version 0.3.8
 *
 * @acknowledgement Matthew Berryman, Wei-li Khoo and Hiep Nguyen, and 
 *  interface obtained from Anton van den Hengel for Artificial Intelligence,
 *  Assignment 2, available at <http://www.cs.adelaide.edu.au/users/third/
 *  ai/assignments/doc/ai/ga/Chromosome.html>
 */
public class Chromosome {
    // Declaring instance variables...
    
    // Container for the list of destination Nodes for each Node in the Network
    private ArrayList cells;
    /**
     * Added by Andy 01/04/05: nodes is used to store a reference to the
     * list of all nodes for this simulation session.
     */
    private ArrayList nodes;
    // Amount of pleiotropy in the Network
    private double pleiotropy = Double.NEGATIVE_INFINITY;
    // Amount of redundancy in the Network
    private double redundancy = Double.NEGATIVE_INFINITY;
    /**
     * Added by Andy 04/04/05: Instance variable...
     * Value of fitness of this Network
     */
    private double fitness    = Double.NEGATIVE_INFINITY;
	
	// Array of fitness values from different cost functions
	private double[] fitnessArr;
	
    /**
     * Probability for this Chromosome to be selected for mutation of mating
     * in the selection process during evolution
     */
    private double prSelected = 0.0;
    // Number of Servers in the network (Chromosome)
    int numServers;
    // Number of Clients in the network (Chromosome)
    int numClients;
    /**
     * Added by Andy 01/04/05: The adjacency matrix and the graph will be
     * stored once constructed. Another words, they will be null when not
     * constructed, and will be not null otherwise.
     */
    private double[][] adjacencyMatrix = null;
    private Graph graph = null;
    
    // --------------------------------------------------------------------
    
    // Default constructor - empty network shall be created
    public Chromosome() {
        /**
         * If the user instantiates a Chromosome without any parameters,
         * the user shall get an empty Chromosome
         */
        cells = new ArrayList();
        numServers = 0;
        numClients = 0;
		nodes = new ArrayList();
    }
    
    // Standard constructors...
    /**
     * Modified by Andy 02/04/05: The constuctor will take in an extra
     * parameter called nodeList, which contains all the nodes corresponding
     * to each cell.
     */
    public Chromosome(ArrayList data, ArrayList nodeList, int noServer,
    int noClient) {
		Assertion.asrt(data.size() == nodeList.size(),
			"Number of Nodes in this Chromosome is correct",
			"Number of Nodes in this Chromosome is INCORRECT; "
			+"Check that data and nodeList have the same number of nodes");
		/**
		 * Assume that all Objects in data and nodeList are of the Class
		 * NodeImp; no Objects in data and nodeList can belong to another
		 * Class
		 * Checking both of these variables will require O(n^2) operations 
		 */
		int numNodes = noServer + noClient;
		Assertion.asrt(data.size() == numNodes,
			"Number of Nodes in this Chromosome is correct",
			"Number of Nodes in this Chromosome is INCORRECT; "
			+"Check that data and nodeList have the same number of nodes");
        cells = data;
        // Modified by Andy 02/04/05: Store nodeList as "nodes"
        nodes = nodeList;
        Assertion.asrt(noServer>=0 && noClient>=0,
            "noServer and noClient are positive",
            "noServer and noClient must be positive");
        numServers = noServer;
        numClients = noClient;        
    }

    /**
     * Modified by Andy 02/04/05: The constuctor will take in an extra
     * parameter called nodeList, which contains all the nodes corresponding
     * to each cell.
     */    
    public Chromosome(ArrayList nodeList, int noServer, int noClient) {
		/**
		 * Assume that all Objects in nodeList are of the Class NodeImp;
		 * no Objects in nodeList can belong to another Class
		 * Checking this variable will require O(n) operations
		 */
		int numNodes = noServer + noClient;
		Assertion.asrt(nodeList.size() == numNodes,
			"Number of Nodes in this Chromosome is correct",
			"Number of Nodes in this Chromosome is INCORRECT; "
			+"Check that the number of nodes in nodeList is equal"
			+"to the sum of the number of clients and servers");
        /**
         * The number of Nodes in a Network is the number of Clients
         * and Servers in the Network
         */
        cells = new ArrayList(noServer+noClient);
        /**
         * Create an empty list of destination Nodes for each Node
         * in this Network
         */
		ArrayList destinationCells;
        for(int i=0;i<numNodes;i++) {
            destinationCells = new ArrayList();
            cells.add(destinationCells);
        }
        // Modified by Andy 02/04/05: Store nodeList as "nodes"
        nodes = nodeList;
        Assertion.asrt(noServer>=0 && noClient>=0,
            "noServer and noClient are positive",
            "noServer and noClient must be positive");
        numServers = noServer;
        numClients = noClient;
    }
    
    // -----------------------------------------------------------------
    
    // Methods of the Chromosome Class arranged according to their functions
    
	/**
	 * Methods to access the list of Nodes for the Chromosome
	 * @return the list of Nodes in the network/graph/Chromosome
	 */
	public ArrayList getNodeList() {
		return nodes;
	}
	
    /**
     * Method to obtain the number of Clients in the Network (Chromosomes)
     * @throws PostconditionException if number of Clients is negative
     * @return the number of Clients in the Network
     */
    public int getNumClients() throws PostconditionException {
        Assertion.post(numClients>=0,"numClients is "+numClients,
            "numClients MUST be positive");
        return numClients;
    }
    
    /**
     * Method to assign the number of Clients in the Network (Chromosome)
     * @param   noClients   the number of Clients in the Network
     * @throws  PreconditionException if the number of Clients is negative
     */
    public void setNumClients(int noClients) throws PreconditionException {
        Assertion.pre(noClients>=0,"noClients is "+noClients,
            "noClients MUST be positive");
        numClients = noClients;
    }

    /**
     * Method to obtain the number of Servers in the Network (Chromosomes)
     * @throws  PostconditionException if the number of Servers is negative
     * @return the number of Servers in the Network
     */
    public int getNumServers() throws PostconditionException {
        Assertion.post(numServers>=0,"numServers is "+numServers,
            "numServers MUST be positive");
        return numServers;
    }
    
    /**
     * Method to assign the number of Servers in the Network (Chromosome)
     * @throws  PreconditionException if the number of Servers is negative
     * @param   noServers   the number of Servers in the Network
     */
    public void setNumServers(int noServers) throws PreconditionException {
        Assertion.pre(noServers>=0,"noServers is "+noServers,
            "noServers MUST be positive");
        numServers = noServers;
    }
    
    /**
     * Duplicates this Chromosome. The clone must be an exact copy of this 
     * Chromosome. Note that the clone method must return an Object, not a 
     * specific Object such as the Chromosome Object.
     * @return a copy of this Chromosome as an Object
     */    
    public Object clone() {
        // Get a copy of this Chromosome/Network
        ArrayList tempCells = getDataArray();
        
        //Create the storage of cells for the cloned Chromosome/Network
        ArrayList clonedNetwork = new ArrayList(tempCells.size());
        /**
         * Create an empty list of destination Nodes for each Node
         * in this Network
         */

		ArrayList destinationCells;
/*
        for(int i=0;i<tempCells.size();i++) {
            destinationCells = new ArrayList();
            clonedNetwork.set(i, destinationCells);
        }
*/
        // For each Client/Server...
        for(int i=0; i<tempCells.size(); i++) {
			destinationCells = new ArrayList(((ArrayList)tempCells.get(i)).size());
            clonedNetwork.add(i, destinationCells);
            // copy its set of destination Nodes over.
            for(int j=0; j < ((ArrayList)tempCells.get(i)).size();j++) {
                ((ArrayList)clonedNetwork.get(i)).
                    add(j,((ArrayList)tempCells.get(i)).get(j));
            }
        }
        
        // Shallow copying...        
		Chromosome clonedChromo = new Chromosome(clonedNetwork, getNodeList(),
			getNumServers(), getNumClients());
        return clonedChromo;
    }
    
    /**
     * Obtains the destination Nodes of the outgoing Edges from this 
     * correspoding cell/Node (with the index "index" in this Chromosome) in 
     * the network.<p>
     *
     * If the desired Node has no outgoing Edges, it will return an
     * ArrayList with size 0.
     *
     * @param   index   the index of the desired Node in this Chromosome
     * @throws  PreconditionException if "index" is < 0 or >= getLength(),
     *          where getLength() is the length of this Chromosome
     * @return  an ArrayList of destination Nodes for outgoing arcs from this
     *          Node
     */
    public ArrayList getData(int index) throws PreconditionException {
        Assertion.pre(index>=0 && index<getLength(),
            "Access to desired Node is proper",
            "Index of Node in this Chromosome is found to be negative!");
        return (ArrayList)cells.get(index);
    }
    
    /**
     * Method to change the list (ArrayList) of destination Nodes for outgoing
     * Edges of this Node with the index "index" of this Chromosome
     *
     * @param int "index" indicates the index of the Chromosome that will
     *  have new destination Nodes for outgoing Edges set for this Node
     * @param ArrayList "dataCell" indicates the list of destination Nodes
     *  set for this Node
     * @throws PreconditionException if "index" is < 0 or >= getLength(),
     *  where getLength() is the length of this Chromosome
     */
    public void setData(int index, ArrayList listToNodes)
        throws PreconditionException {
            
        Assertion.pre(index>=0 && index<getLength(),
            "Access to desired Node is proper",
            "Index of Node in this Chromosome is found to be negative!");
        cells.add(index, listToNodes);
    }
    
    /**
     * Method to get the list of Nodes in the network
     * @return a list of Nodes in this network; note that each Node in this
     *  network has a list of destination Nodes for outgoing Edges from
     *  this Node
     */
    public ArrayList getDataArray() {
        return cells;
    }
    
    /**
     * Method to get the amount of pleiotropy for this network
     * @post the amount of pleiotropy in a network must be greater or equal
     *  to zero
     * @throws PostconditionException if the network is not empty and its
     *  pleiotropy is negative since pleiotropy values are set to negative
     *  infinity for initialised empty networks
     * @return the amount of pleiotropy for this network
     */
    public double getPleiotropy() throws PreconditionException {
        /**
         * # Modified by Andy Lo 21 Apr 2005: 
         *      changed the postcondition check so that the valid case is
         *      non-negative pleiotropy for non-empty network
         */
        Assertion.post( (pleiotropy >= 0 && getLength()> 0) || 
            (pleiotropy == Double.NEGATIVE_INFINITY && getLength() == 0),
            "Pleiotropy of network is "+pleiotropy,
            "Pleiotropy of non-empty network cannot be negative");
        return pleiotropy;
    }
    
    /**
     * Method to assign the amount of pleiotropy for this network
     * @param double "pleio" is the amount of pleiotropy of this network
     * @pre the amount of pleiotropy in a network must be greater or equal
     *  to zero
     * @throws PreconditionException if the network is not empty and its
     *  pleiotropy is negative since pleiotropy values are set to negative
     *  infinity for initialised empty networks
     */
    public void setPleiotropy(double pleio) throws PreconditionException {
        /**
         * # Modified by Andy Lo 21 Apr 2005: 
         *      changed the precondition check so that the valid case is
         *      non-negative pleiotropy for non-empty network
         */
        Assertion.pre( (pleio >= 0 && getLength()>0) ||
            (pleio == Double.NEGATIVE_INFINITY && getLength() == 0),
            "Pleiotropy of network is "+pleio,
            "Pleiotropy of non-empty network cannot be negative");
        pleiotropy = pleio;
    }
    
    /**
     * Gets the amount of redundancy for this network
     * @throws  PostconditionException if the network's redundancy is negative,
     *          but is not empty, since redundancy values are set to negative
     *          infinity for initialised empty networks
     * @return the amount of redundancy for this network
     */
    public double getRedundancy() throws PostconditionException {
        /**
         * # Modified by Andy Lo 21 Apr 2005: 
         *      changed the postcondition check so that the valid case is
         *      non-negative redundancy for non-empty network
         */
        Assertion.post( (redundancy >= 0 && getLength() > 0) ||
            (redundancy == Double.NEGATIVE_INFINITY && getLength() == 0),
            "Redundancy of network is "+redundancy,
            "Redundancy of non-empty network cannot be negative");
        return redundancy;
    }
    
    /**
     * Assigns the amount of redundancy for this network
     * @param   redun   the amount of redundancy of this network
     * @throws  PreconditionException if the redundancy value to be set is 
     *          negative, but the network is not empty, since redundancy 
     *          values are set to negative infinity for initialised 
     *          empty networks
     */    
    public void setRedundancy(double redun) throws PreconditionException {
        /**
         * # Modified by Andy Lo 17 Apr 2005: 
         *      changed the precondition check so that the valid case is
         *      non-negative redundancy for non-empty network
         */
        Assertion.pre( (redun >= 0 && getLength() > 0) || 
            (redun == Double.NEGATIVE_INFINITY && getLength() == 0),
            "Redundancy of network is "+redun,
            "Redundancy of non-empty network cannot be negative");
        redundancy = redun;
    }
    
    /**
     * Gets the length of the chromosome (number of Nodes in this Network).
     * @throws PostconditionException If the length returned is negative.
     * @return the number of nodes in this network (length of the chromosome)
     */
    public int getLength() throws PreconditionException {
        /**
         * Modified by Andy 02/04/05: Assertion checked for cells.size() < 0.
         * This is not desirable as it will throw exception when length is
         * non-negative. We want the opposite, so it was changed to
         * cells.size() > 0.
         */
        Assertion.post(cells.size() >= 0, 
                        "Size of the network is "+cells.size(),
                        "Size of a Network cannot be negative");
        return cells.size();
    }
	
	/**
	 * Method to determine the number of edges in the graph
	 */
	public int getNumEdges() throws PreconditionException {
		int numberEdges=0;
		for(int i=0;i<getLength();i++) {
			numberEdges = numberEdges + ((ArrayList)cells.get(i)).size();
		}
		Assertion.post(numberEdges >= 0,
			"Number of edges in the graph is: "+numberEdges,
			"The Graph has a NEGATIVE number of EDGES");
		return numberEdges;
	}
    
    /**
     * Method to get the probability of selection [Pr(Selection)] for this 
     * Chromosome during evolution for mating and mutation
     * @throws  PostconditionException if probability of selection, 
     *          Pr(Selection) < 0.0 or > 1.0
     * @return the probability of selection
     */
    public double getPrSelection() {
        Assertion.post(prSelected>=0 && prSelected<=1, 
            "The probability of selection for this Chromosome is "+prSelected,
            "0<=probability of selection for Chromosomes <= 1 - MUST BE TRUE");
        return prSelected;
    }
    
    /**
     * Assigns the probability of selection [Pr(Selection)] for this 
     * Chromosome during evolution for mating and mutation
     * @param double "prSelec" is the probability of Selection for this network
     * @throws  PreconditionException if probability of selection, 
     *          Pr(Selection) < 0.0 or > 1.0
     */
    public void setPrSelection(double prSelec) throws PreconditionException {
        Assertion.pre(prSelec>=0 && prSelec<=1, 
            "The probability of selection for this Chromosome is "+prSelec,
            "0<=probability of selection for Chromosomes <= 1 - MUST HOLD");
        prSelected = prSelec;
    }
    
    
    /**
     * Returns the fitness value of this chromosome. This value should only
     * be set by a fitness function which performs relevant calculation on
     * this chromsome. 
     * @return  the fitness value of this chromosome
     */
    public double getFitness() {
        // Modified by Andy 04/04/05: changed to return actual fitness value
        return fitness;
    }

    /**
     * Sets the fitness value of this chromosome. This method should only
     * be called by a fitness function which performs relevant calculation on
     * this chromsome. <p>
     *
     * Added by Andy 04/04/05.
     *
     * @param f  the fitness value of this chromosome to be set
     */
    public void setFitness(double f) {
        fitness = f;
    }
	
	/**
	 * Method to assign the array of fitness values obtained from various
	 * cost functions
	 * @param f is the array
	 * @return nothing
	 */
	public void setFitnessArr(double[] f) {
        fitnessArr = f;
    }
	
	/**
	 * Method to create the array of fitness values obtained from various
	 * cost functions
	 * @param "size" is the size of the array
	 * @return nothing
	 */
	public void createFitnessArr(int size) {
        fitnessArr = new double[size];
    }
	
	/**
	 * Method to insert a fitness value into the array of fitness values
	 * @param "index" is the index of the array that you want to assign
	 *	fitness to
	 * @param fit is the value of fitness to assign value to.
	 * @return nothing
	 */
	public void insertIntoFitArr(int index, double fit) {

//Debugger.enableTrace(true);
Debugger.debug("");
Debugger.debug("");
Debugger.debug("#####VALUE OF index IS: "+index);
Debugger.debug("fitnessArr IS EQUAL TO NULL: "+(fitnessArr==null));
Debugger.debug("#####VALUE OF fitnessArr.length IS: "+fitnessArr.length);
Debugger.debug("");
Debugger.debug("");
Debugger.enableTrace(false);

// ### Modified by Zhiyang Ong - 31 July 2005
//        fitnessArr[index]=fit;
fitnessArr[0]=fit;
    }
    
	/**
	 * Method to obtain a fitness value from the array of fitness values
	 * @param "index" is the index of the array that you want to obtain
	 *	fitness from
	 * @return the fitness value
	 */
	public double getFitArrElem(int index) {

//        return fitnessArr[index];
return fitnessArr[0];

    }
	/**
	 * Method to return the array of fitness values obtained from various
	 * cost functions
	 * @param none
	 * @return array of fitness values obtained from various cost functions
	 */
	public double[] getFitnessArr() {
        return fitnessArr;
    }
	
    /**
     * Method to return a String representation of each Node (cell) in this
     * Network (Chromosome)
     * @return String representation of each Node (cell) in this Network
     *  (Chromosome)
     */
    public String toString() {
        return cells.toString();
// Work on this... NOT IMPORTANT
/*
// CODE FROM THE CLASS FunctionTestChromosome01
String line;
        
        Debugger.debug("Nodes: " + g.numNodes() + " Edges: " + g.numEdges());

        // Print graph as a list of adjacency lists.
        Debugger.debug("\nAdjacency lists:");
    
        Debugger.enableTrace(false);    // mute
        for ( Node node = g.firstNode(); node != null ;
        node = g.nextNode(node) ) {
            line = "Node " + g.nodeId(node) + "(" + node.getLabel() +
                                                            ") has:\n";
            // Print the node's out edges
            if ( g.outDegree(node) == 0 ) {
            line += "\tNo out edges";
            } else {
                Debugger.enableTrace(false); // mute
                line += "\t" + g.outDegree(node) + " out edge" + 
                                    (g.outDegree(node) > 1 ? "s" : "") + ":";
                for ( Edge ed = g.firstEdgeFrom(node); ed != null ;
                ed = g.nextEdgeFrom(node,ed) ) {
                    line += " (" + g.nodeId(ed.getFromNode()) + "->" +
                                g.nodeId(ed.getToNode()) + ":" + 
                                ed.getLabel() + ")";            
                }
                line += "\n";
            }
                
            //
            // Print the node's in edges
            //
            if ( g.inDegree(node) == 0 ) {
            line += "\tNo in edges";
            } else {
                Debugger.enableTrace(false);    // mute
                line += "\t" + g.inDegree(node) + " in edge"+ 
                                    (g.outDegree(node) > 1 ? "s" : "") +":";
                for ( Edge ed = g.firstEdgeTo(node); ed != null;
                ed = g.nextEdgeTo(node,ed) ) {
                    line += " (" + g.nodeId(ed.getFromNode()) + "->" + 
                                g.nodeId(ed.getToNode()) + ":" + 
                                ed.getLabel() + ")";                
                }
            line += "\n";
            }
*/
    }


    /**
     * Constructs an Adjacency Matrix based on this Network (Chromosome).
     * This will assess each node (cell) of the network (chromosome) and
     * obtain a list of destination (adjacent) nodes to which the current node
     * (cell) connects to. The value to be stored into the matrix is the
     * cartesian distance between the 2 nodes. (### This may be changed
     * later)<p>
     *
     * Note that a matrix is declared as an array of arrays. It can be 
     * initialised as: <br> <center>
     *      double array[][] = new double[number of rows][number of cols],
     * </center><br>
     * Thus to reference the element (i,j), that is ith row and jth column, we
     * refer to array[i][j].<p>
     *   
     * Once the adjacency matrix has been constructed, it will be stored, so
     * if this method is queried again, the stored matrix will be returned.<p>
     *
     * (Implemented by Andy 1 Apr 2005)
     *
     * @throws  AssertionException If the the size of this Network is 0. i.e.
     *          getLength() > 0
     * @throws  PostconditionException If the returned matrix is not a square
     *          matrix, or the dimension is not the square of the number of
     *          Nodes in the Network. i.e. dimensions = (getLength())^2
     * @return  a square Adjacency Matrix indicating the connectivity of
     *          the Nodes in the Network
     */
    public double[][] getAdjacencyMatrix()
    throws AssertionException, PostconditionException {
        
        Assertion.asrt( getLength() > 0,
                        "The chromosome has length = " + getLength(),
                        "The chromosome has length = 0!!??");
        
        // If the adjacencyMatrix hasn't been already constructed...
        if (adjacencyMatrix == null) {
            int dim = getLength(); // Obtain chromosome length
            // initialise matrix
            double[][] adjmatrix = new double[dim][dim];
            double edgeCost;
            
            Node src, dest;     // reference to source and destination nodes
            ArrayList adjNodes; // list of destination nodes
        
            /**
             * Each row of the matrix correspond to a cell in the chromosome
             * or a node in the network. Thus for every row/cell/node
             */
            for (int i = 0; i < dim; i++) {
                /**
                 * Modified by Andy 05/04/05:
                 *      fill this row with positive infinity, indicating that 
                 * there are no edges linking any nodes.
                 */
                Arrays.fill(adjmatrix[i], Double.POSITIVE_INFINITY);

                /**
                 * # Modified by Andy 05/04/05:
                 *      Distance between the node and itself is 0
                 */
                adjmatrix[i][i] = 0;
                /**
                 * # Modified by Andy 21 Apr 2005:
                 *      if the node is deactivated, all outgoing connections
                 *  are barred. Thus the costs to desinations will be 
                 *  infinity
                 */
                src = (Node) nodes.get(i);
                if ( !((Repairable)src).activated() ) continue;
                /**
                 * for this node in the network, obtain the list of adjacent
                 * destination nodes.
                 */
                adjNodes = (ArrayList) cells.get(i);
                // for each adjacent node
                for (int j = 0; j < adjNodes.size(); j++) {
                    dest = (Node) adjNodes.get(j);
                    /**
                     * # Modified by Andy 21 Apr 2005:
                     *      If the destination is deactivated, no one can
                     *  connect to it. Thus the costs remains to be infinity
                     */                    
                    if ( !((Repairable)dest).activated() ) continue;
                    /**
                     * Calculate the cartesian distance between current node, 
                     * and the adjacent destination node, and then set the
                     * distance.
                     * # Modified by Andy Lo 18 Apr 2005:
                     *      Changed to obtain edge cost from EdgeCostMatrix
                     */
                    
                    edgeCost = EdgeCostMatrix.getCost(i,nodes.indexOf(dest));
                    //Debugger.printErr("cost of " + i + " to " + 
                    //    nodes.indexOf(dest) +" is " + edgeCost);
                    adjmatrix[i][nodes.indexOf(dest)] = edgeCost;
                    
                                                    //distBetween(src, dest);
                }
            }
            adjacencyMatrix = adjmatrix;
        }
        // else just return the stored matrix...
                
        Assertion.post( (adjacencyMatrix.length == getLength()) &&
                        (adjacencyMatrix[0].length == getLength()),
                        "The returned matrix is a n x n matrix where n is " +
                        "\n    the number of nodes in the network.",
                        "The returned matrix MUST be a SQUARE matrix" );
                        
        return adjacencyMatrix;
    }

    /**
     * Calculates the cartesian distance between 2 nodes using Pythagoras
     * theorem. (Implemented by Andy 01/04/05)
     *
     * @param   a The source node
     * @param   b The destination node
     * @return  the cartesian distance between 2 nodes
     */
//    private double distBetween(Node a, Node b) {
//        int[] aCoord = a.getCoordinates();
//        int[] bCoord = b.getCoordinates();
//        int x = aCoord[0] - bCoord[0];
//        int y = aCoord[1] - bCoord[1];
//        return Math.sqrt( (x*x) + (y*y) );
//    }
    
    /**
     * Constructs a graph for this Network (Chromosome). Firstly, all the
     * nodes in the node list will be cloned and added to the graph. We need
     * to clone the nodes, because it's not desirableto alter a node's
     * property whilst manipulating the graph. <p>
     *
     * Then each cell (node) of the chromosome (netowrk) is assessed, and the 
     * list, of adjacent nodes to which the current node is connnected, is 
     * obtained. From the list, edges will be added to the graph with cost 
     * of the edge being the cartesian distance between two nodes. (### This 
     * may be changed later.)<p>
     *
     * This method also assumes, for the edge, the worst case repair
     * generation is 20 generations, the probability of failure is 30% and
     * the threshold (the maximum allowable probability of failure) is 70%. 
     * In addition, all edges will be capable of carrying 1000Mbps or
     * 1000 megabit per second. (Note: 1000 megabit = 1 gigabit)<p>
     * 
     * Once the graph has been constructed, it will be stored, so if this
     * method is queried again, the stored graph will be returned. <p>
     *
     * (Implemented by Andy 01/04/05)
     *
     * @throws  AssertionException If the the size of this Network is 0. i.e.
     *          getLength() > 0
     * @throws  PostconditionException If the number of nodes in the graph
     *          does not equal to the number of cells in this chromosome.
     * @return  a Graph representing the Network (Chromosome)
     */
     public Graph getGraph()
     throws AssertionException, PostconditionException {
        int dim = getLength();  // the length of the chromosome
        Assertion.asrt( dim > 0,
                        "The chromosome has length = " + getLength(),
                        "The chromosome has length = 0!!??");
        
        // If the graph hasn't been already constructed...
        if(graph == null) {
            Graph g = new GraphImp(1000, 750);
            Node refNode, clone;
            String refLabel;
            int[] coord;
            
            /**
             * For each node in the node list, make a copy of the node, and 
             * add them into the graph.
             */
            for(int i = 0; i < dim; i++) {
                refNode = (Node) nodes.get(i);
                refLabel = refNode.getLabel();
                coord = refNode.getCoordinates();
                // make the clone
                clone = new NodeImp (refLabel, coord[0], coord[1], refNode);
                g.addNode(clone);   // add the clone
            }
            
            // prepare to add edges
            // assume the worst case repair generation is 20 generations
            int repgen = (int)(Math.random() * 20.0);
            /**
             * assume the probability of failure is 30% and the threshold
             * (the maximum allowable probability of failure) is 70%.
             */
            Repairable r = new Repairable(repgen, 0.30, 0.70, true);
            // edge can carry 1 Gbit
            double[] params = {1000, 0.0, 0.0}; 
            
            Edge edgeModel = new EdgeImp(params, r);
            Edge newEdge;
            
            Node src, dest;         // source and destination nodes of edge
            ArrayList adjNodes;     // list of destination nodes for a node
            
            double edgeCost;
            
            /**
             * for each node in the node list, find its adjacent/destination
             * nodes and create an edge between them.
             */
            for (int i = 0; i < dim; i++) {
                src = g.getNode(i);  // obtain the source
                /**
                 * # Modified by Andy 21 Apr 2005:
                 *      if the node is deactivated, all outgoing connections
                 *  are barred. 
                 */
                if ( !((Repairable)src).activated() ) continue;
                
                adjNodes = (ArrayList) cells.get(i);
                for (int j = 0; j < adjNodes.size(); j++) {
                    // obtain the destination node from the adjacency list
                    dest = (Node) adjNodes.get(j);
                    /**
                     * # Modified by Andy 21 Apr 2005:
                     *      If the destination is deactivated, no one can
                     *  connect to it.
                     */                    
                    if ( ! ((Repairable)dest).activated() ) continue;
                    /**
                     * newEdge.setCost(distBetween(src, dest));
                     * # Modified by Andy Lo 18 Apr 2005:
                     *      Changed to obtain edge cost from EdgeCostMatrix
                     */
                    edgeCost = EdgeCostMatrix.getCost(i,nodes.indexOf(dest));
                    
                    // obtain the clone of "dest" that is in the graph
                    dest = g.getNode(nodes.indexOf(dest));  
                    refLabel = (src.getLabel()).toUpperCase() + "-" +
                                    (dest.getLabel()).toUpperCase();
                    newEdge = new EdgeImp(src, dest, refLabel, edgeModel);
                    newEdge.setCost(edgeCost);            

                    g.addEdge(newEdge);             // add the edge
                }
            }
            graph = g;  // update graph
        }
        // else just return the stored graph
        
        Assertion.post( graph.numNodes() == getLength(),
                    "The number of nodes in the graph (" + graph.numNodes() + 
                    ") equals to the length of the chromosome",
                    "The number of nodes in the graph does not equal to " +
                    "length of the chromosome");
        return graph;
    }
}
