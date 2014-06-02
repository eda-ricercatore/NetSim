// importing packages
import utility.*;
import population.*;
import population.graph.*;
import java.util.ArrayList;

/**
 * This test suite will test for the functionality of Chromosome class.
 * A Chromosome contain properties about the telecommunications network,
 * and its nodes (Clients and Servers) and edges (communication).
 *
 * @author  Zhiyang Ong and Andy Lo
 * @version 0.3.7
 * @since 0.3.7
 */
public class TestForChromosome {
    // instance variable to store info regarding current test
    private static String testName;
	/**
	 * Chromosome that is instantiated with some Nodes in the graph once
	 * the constructor has been tested
	 */
	private static Chromosome chromosome;
    
	// ---------------------------------------------------------------
	
    // Default constructor
    /**
     * Should not instantate a test class.
     */
    public TestForChromosome() {
		Debugger.printErr("Do NOT instantiate an instance of the test suite");
    	throw new AssertionException("Don't instantiate a test class.");
    }
	
	// ---------------------------------------------------------------
    
    // main method...
    public static void main(String[] args) {
        Debugger.pipeResult("ChromosomeNormal.txt", "ChromosomeError.txt");
        Debugger.enableTrace(true);
		Debugger.debug("\n==================================\n"+
                         "filename: ChromosomeNormal.txt\n" +
                         "==================================");
        Debugger.debug("Module Test for population.Chromosome:\n");
        
        Debugger.printErr("\n==================================\n"+
                            "filename: ChromosomeError.txt\n" +
                            "==================================");
        Debugger.printErr("Module Test for population.Chromosome:\n");
        chromosome = testConstructor();
        Debugger.debug("");
		testSetNumServersClients();
		Debugger.debug("");
		testClone();
		Debugger.debug("");
		//testDistBetween();
		Debugger.debug("");
        Debugger.debug("\nModule Test Completed");
    }
    
    /**
     * Tests the constructor of <code>Chromosome</code>.
     * @param   none
     * @return  a Chromosome
     */
    public static Chromosome testConstructor() {
        Debugger.debug("Testing default constructor of Chromosome");
		Chromosome chromo = new Chromosome();
		Assertion.asrt(chromo.getDataArray().size() == 0, 
			"Default constructor has initialised data array",
			"Default constructor has UNINITIALISED data array");
		Assertion.asrt(chromo.getNodeList().size() == 0, 
			"Default constructor has initialised data array",
			"Default constructor has UNINITIALISED data array");
		Assertion.asrt(chromo.getNumClients() == 0, 
			"Default constructor has 0 Clients",
			"Default constructor has some Clients it shouldn't have");
		Assertion.asrt(chromo.getNumServers() == 0, 
			"Default constructor has 0 Servers",
			"Default constructor has some Servers it shouldn't have");
		Debugger.debug("");
		
		Debugger.debug("Testing standard constructor of Chromosome");
		Debugger.debug("The constructor Chromosome(ArrayList data, "
			+"ArrayList nodeList, int noServer, int noClient) is tested");
		int numNodes = 100;
		ArrayList nodes = new ArrayList(numNodes);
		NodeImp node;
		for(int i=0; i < numNodes; i++) {
			node = new NodeImp();
			nodes.add(node);
		}
		int numClients = numNodes / 5;
		int numServers = numNodes - numClients;
		int numberNodes = numServers + numClients;
		chromo = new Chromosome(nodes,nodes,numServers,numClients);
		Assertion.asrt(chromo.getDataArray() == nodes, 
			"Standard constructor has initialised list of nodes",
			"Standard constructor has UNINITIALISED list of nodes");
		Assertion.asrt(chromo.getNodeList() == nodes, 
			"Standard constructor has initialised data array",
			"Standard constructor has UNINITIALISED data array");
		Assertion.asrt(chromo.getNodeList().size() == chromo.getDataArray().size(), 
			"Number of elements in list of nodes and data array are equal",
			"Number of elements in list of nodes and data array are NOT equal");
		Assertion.asrt(chromo.getDataArray().size() == numberNodes, 
			"Number of elements in list of nodes, data array, and the sum of"
			+"clients and servers are equal",
			"Number of elements in list of nodes, data array, and the sum of"
			+"clients and servers are NOT equal");
		Assertion.asrt((chromo.getNumClients() == numClients)
			&& (chromo.getNumClients() >= 0), 
			"Number of clients has been assigned appropriately",
			"Number of clients has NOT been assigned appropriately");
		Assertion.asrt((chromo.getNumServers() == numServers)
			&& (chromo.getNumServers() >= 0), 
			"Number of servers has been assigned appropriately",
			"Number of servers has NOT been assigned appropriately");
		Debugger.debug("");
		
		Debugger.debug("Testing the other standard constructor of Chromosome");
		Debugger.debug("The constructor Chromosome(ArrayList nodeList,"
			+" int noServer, int noClient) is tested");
		chromo = new Chromosome(nodes,numServers,numClients);
		Assertion.asrt(chromo.getNodeList() == nodes, 
			"Standard constructor has initialised list of nodes",
			"Standard constructor has UNINITIALISED list of nodes");
		String class1stElem = chromo.getNodeList().get(0).getClass().toString();
		// System.out.println(class1stElem+"CLASSNAME");
		Assertion.asrt(class1stElem.equals("class population.graph.NodeImp"),
			"elements of node list are objects of NodeImp",
			"elements of node list are NOT objects of NodeImp");
			// System.out.println(chromo.getNodeList().size()+"SIZE OF NODE LIST");
			// System.out.println(chromo.getDataArray().size()+"SIZE OF DATA ARRAY");
		Assertion.asrt(chromo.getNodeList().size() == chromo.getDataArray().size(), 
			"Number of elements in list of nodes and data array are equal",
			"Number of elements in list of nodes and data array are NOT equal");
		Assertion.asrt(chromo.getDataArray().size() == numberNodes, 
			"Number of elements in list of nodes, data array, and the sum of"
			+"clients and servers are equal",
			"Number of elements in list of nodes, data array, and the sum of"
			+"clients and servers are NOT equal");
		Assertion.asrt((chromo.getNumClients() == numClients)
			&& (chromo.getNumClients() >= 0), 
			"Number of clients has been assigned appropriately",
			"Number of clients has NOT been assigned appropriately");
		Assertion.asrt((chromo.getNumServers() == numServers)
			&& (chromo.getNumServers() >= 0), 
			"Number of servers has been assigned appropriately",
			"Number of servers has NOT been assigned appropriately");
		Debugger.debug("");
		
		return chromo;
    }
    
    /**
     * Tests the accessor and mutator methods of NumClients and NumServers.
     * @param   none
     * @return  nothing
     */
    public static void testSetNumServersClients() {
		Debugger.debug("Testing the accessor and mutator methods of NumClients "
			+"and NumServers");
		// set number of servers to a negative number & see if an exception occurs
		int numNodes = -37;
		Debugger.debug("Setting the number of servers to: "+numNodes);
		try{
			chromosome.setNumServers(numNodes);
		}catch(PreconditionException p) {
			if(numNodes < 0) {
				Debugger.debug("The number of servers cannot be set to negative");
			}else{
				Debugger.printErr("Unknown PreconditionException has occurred");
				Debugger.printErr("Please report bug to software developers.");
			}
		}
		// set number of clients to a negative number & see if an exception occurs
		numNodes = -48023857;
		Debugger.debug("Setting the number of clients to: "+numNodes);
		try{
			chromosome.setNumServers(numNodes);
		}catch(PreconditionException p) {
			if(numNodes < 0) {
				Debugger.debug("The number of clients cannot be set to negative");
			}else{
				Debugger.printErr("Unknown PreconditionException has occurred");
				Debugger.printErr("Please report bug to software developers.");
			}
		}
		Debugger.debug("");
    }
    
    /**
     * Tests the clone method of Chromosome
     * @param   none
     * @return  nothing
     */
    public static void testClone() {
        Debugger.debug("Testing the clone method of Chromosome");
		Debugger.debug("Creating a list of Nodes to be shared by the Chromosomes");
		/**
		 * Create the parameters for a Node with 1000 Gbps capacity, 70% efficiency,
		 * 15% probability of failure, and 30% threshold for the probability of
		 * failure
		 */
		double[] param = {1000.0,0.7,0.15,0.20};
		// Number of generations for the node to be repaired
		int numGen = 100;
		// labels of Server and Client Nodes
		String label;
		String server = "SERVER";
		String client = "CLIENT";
		// x coordinate of the node
		int x=0;
		// y coordinate of the node
		int y=0;
		// Obtain the ArrayList of Nodes shared by all chromosomes from "chromosome"
		ArrayList nodeList = chromosome.getNodeList();
		// Number of nodes in the chromosome
		int numNodesPerChromo = 100;
		Node tempNode;
		// Create Nodes and add them to the ArrayList of Nodes
		for(int i=0;i<numNodesPerChromo;i++) {
			// vary the location of each node
			x+=10;
			y+=20;
			// let every fourth node be a server
			if(i%4 == 0) {
				label = server;
			}else{
				label = client;
			}
			// create the node
			tempNode = new NodeImp(label,x,y,numGen,param,true);
			// add nodes to nodeList
			nodeList.set(i,tempNode);
		}
		// Number of edges added to the graph
		int numNodesAdded=0;
		ArrayList cellList = (ArrayList)chromosome.getDataArray();
//Debugger.debug("cellList.size() is: "+cellList.size());
		ArrayList nodesList = (ArrayList)chromosome.getNodeList();
//Debugger.debug("nodesList.size() is: "+nodesList.size());
		/**
		 * add edges to the graph as follows: 3,5,2,0,3,5,2,0,...
		 * for consecutive cells in the dataArray
		 */
		for(int j=0; j<chromosome.getLength();j++) {
			// add three edges to the (i)^{th} node
			for(int k3=0; k3<3; k3++) {
				((ArrayList)cellList.get(j)).add(
					nodesList.get(numNodesAdded%100));
				numNodesAdded++;
			}
			j++;
			if(j>=chromosome.getLength()) {
				break;
			}
			// add five edges to the (i+1)^{th} node
			for(int k5=0; k5<5; k5++) {
//Debugger.debug("J is: "+j);
				((ArrayList)cellList.get(j)).add(
					nodesList.get(numNodesAdded%100));
				numNodesAdded++;
			}
			j++;
			if(j>=chromosome.getLength()) {
				break;
			}
			// add two edges to the (i+2)^{th} node
			for(int k2=0; k2<2; k2++) {
				((ArrayList)cellList.get(j)).add(
					nodesList.get(numNodesAdded%100));
				numNodesAdded++;
			}
			// add zero edges to the (i+3)^{th} node
			j++;
		}
		// clone this chromosome
		Chromosome clone = (Chromosome)chromosome.clone();
		// test the method getNumEdges()
		Assertion.asrt(clone.getNumEdges() == numNodesAdded, 
			"Number of edges in the clone is; "+clone.getNumEdges(),
			"There exists a mistake in the clone method.");
		Assertion.asrt(chromosome.getNumEdges() == numNodesAdded,
			"Number of edges in chromosome is: "+chromosome.getNumEdges(),
			"HELP!!! Number of edges in chromosome is NOT determined correctly");
		// list of cells for original chromosome
		ArrayList originalCells = (ArrayList)chromosome.getDataArray();
		// list of cells for cloned chromosome
		ArrayList clonedCells = (ArrayList)clone.getDataArray();
		// destination node lists of destination nodes in original chromosome
		NodeImp originalNode;
		// destination node lists of destination nodes in cloned chromosome
		NodeImp clonedNode;
		// list of destination nodes in original chromosome
		ArrayList originalList;
		// list of destination nodes in cloned chromosome
		ArrayList clonedList;
		
		// for each edge in the cloned chromosome,
		for(int k=0;k<clone.getLength();k++) {
			// check that it exists in the original chromosome
//System.out.println("CLASSNAME IS:: "+originalCells.get(k).getClass().toString());
			originalList = (ArrayList)originalCells.get(k);
			clonedList = (ArrayList)clonedCells.get(k);
			for(int kk=0;kk<clonedList.size();kk++) {
				clonedNode = (NodeImp)clonedList.get(kk);
				originalNode = (NodeImp)originalList.get(kk);
				Assertion.asrt(clonedNode.equals(originalNode), 
					"The "+kk+"th destination node of the "+k+"th "
					+"node in the data array was cloned correctly",
					"Chromosomes are NOT properly cloned.");
			}
		}
    }
    
    /**
     * Tests the method distBetween(Node a, Node b)
     * @param   none
     * @return  nothing
     */
    public static void testDistBetween() {
		Debugger.debug("Testing the method distBetween(Node a, Node b)");
		// obtain two nodes from chromosome's cells/(data array)
		ArrayList tempList = (ArrayList)chromosome.getDataArray();
		/**
		 * index of the i^{th} Node, which list of destination cells
		 * has to be obtained
		 */
		int index = 0;
		tempList = (ArrayList)tempList.get(index);
		int destNode = 0;
		NodeImp a = (NodeImp)tempList.get(destNode);
		destNode++;
		NodeImp b = (NodeImp)tempList.get(destNode);
		int[] aLocation = a.getCoordinates();
		Debugger.debug("Location of Node a: ("
			+aLocation[0]+","+aLocation[1]+")");
		int[] bLocation = b.getCoordinates();
		Debugger.debug("Location of Node b: ("
			+bLocation[0]+","+bLocation[1]+")");
		// double dist = chromosome.distBetween(a,b);
		
		Debugger.debug("");
    }        
}