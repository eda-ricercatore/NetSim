// importing packages
import utility.*;
import population.*;
import population.graph.*;
import java.util.ArrayList;

/**
 * This test suite will test for the functionality of SetOfChromosomes class.
 * A SetOfChromosomes contain properties about the population of
 * telecommunications networks that will be evolved for their optimisation
 *
 * @author  Zhiyang Ong and Andy Lo
 * @version 0.3.7
 * @since   0.3.7
 */
public class TestSetOfChromosomes {
    // instance variable to store info regarding current test
    private static String testName;
	/**
	 * SetOfChromosomes that is instantiated with some chromosomes in the
	 * population once its constructors has been tested
	 */
	private static SetOfChromosomes setOfChromos;
    
	// ---------------------------------------------------------------
	
    // Default constructor
    /**
     * Should not instantate a test class.
     */
    public TestSetOfChromosomes() {
		Debugger.printErr("Do NOT instantiate an instance of the test suite");
    	throw new AssertionException("Don't instantiate a test class.");
    }
	
	// ---------------------------------------------------------------
    
    // main method...
    public static void main(String[] args) {
        Debugger.pipeResult("SetOfChromosomesNormal.txt", "SetOfChromosomesError.txt");
        Debugger.enableTrace(true);
		Debugger.debug("\n==================================\n"+
                         "filename: SetOfChromosomesNormal.txt\n" +
                         "==================================");
        Debugger.debug("Module Test for population.SetOfChromosomes:\n");
        
        Debugger.printErr("\n==================================\n"+
                            "filename: SetOfChromosomesError.txt\n" +
                            "==================================");
        Debugger.printErr("Module Test for population.SetOfChromosomes:\n");
        setOfChromos = testConstructor();
        Debugger.debug("");
		testAddChromo();
		Debugger.debug("");
		testCheckIfTwins();
		Debugger.debug("");
		testAppendPop();
		Debugger.debug("");
		testSort();
		Debugger.debug("");
        Debugger.debug("\nModule Test Completed");
    }
    
    /**
     * Tests the constructor of <code>SetOfChromosomes</code>.
     * @param   none
     * @return  a Chromosome
     */
    public static SetOfChromosomes testConstructor() {
        Debugger.debug("Testing default constructor of SetOfChromosomes");
		SetOfChromosomes populationChromos = new SetOfChromosomes();
		Assertion.asrt(populationChromos.getCurPop() != null, 
			"Default constructor has initialised a set of populations",
			"Default constructor has NOT INITIALISED a set of populations");
		Debugger.debug("");
		
		Debugger.debug("Testing standard constructor of SetOfChromosomes");
		Debugger.debug("The constructor SetOfChromosomes(ArrayList list)"
			+" is tested");
		// Size of the population of chromosomes
		int numChromos = 5;
		// Set/population of chromosomes
		ArrayList set = new ArrayList(numChromos);
		
		// number of nodes in each population
		int numNodes = 100;
		// List of nodes to be shared by the population
		ArrayList nodeList = new ArrayList(numNodes);

		/**
		 * Create the parameters for a Node with 1000 Gbps capacity, 70% efficiency,
		 * 15% probability of failure, and 30% threshold for the probability of
		 * failure
		 */
		double[] param = {1000.0,0.7,0.15,0.20};
		// Number of generations for the node to be repaired
		int numGen = 20;
		// labels of Server and Client Nodes
		String label;
		String server = "SERVER";
		String client = "CLIENT";
		// x coordinate of the node
		int x=0;
		// y coordinate of the node
		int y=0;
		
		// number of clients in a chromosome
		int numClients = numNodes / 5;
		// number of servers in a chromosome
		int numServers = numNodes - numClients;
		// number of servers and clients (= number of nodes) in a network
		int numberNodes = numServers + numClients;
		Assertion.asrt(numNodes == numberNodes, 
			"Each chromosome shall have the same number of nodes",
			"Each chromosome may NOT have the same number of nodes");
		
		// Holder for a Node that can be a server/client
		Node tempNode;
		// Create Nodes and add them to the ArrayList of Nodes
		for(int i=0;i<numNodes;i++) {
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
			nodeList.add(tempNode);
		}
		
		// create a chromosome - Chromosome A
		Chromosome chromoA = new Chromosome(nodeList,numServers,numClients);
		/**
		 * Since the number of clients and servers in each Chromosome/network
		 * is the same, new Chromosomes can be instantiated with the same
		 * parameters as Chromosome A
		 *
		 * Care must be taken to ensure that all their corresponding nodes do
		 * not have the same list of destination nodes
		 */
		Chromosome chromoC = new Chromosome(nodeList,numServers,numClients);
		Chromosome chromoE = new Chromosome(nodeList,numServers,numClients);
		// Number of edges added to current graph
		int numNodesAdded=0;
		// List of lists containing destination nodes
		ArrayList cellList = (ArrayList)chromoA.getDataArray();
//Debugger.debug("cellList.size() is: "+cellList.size());

// NOT REQUIRED SINCE THIS IS SHARED BY ALL CHROMOSOMES
//		ArrayList nodesList = (ArrayList)chromosome.getNodeList();

//Debugger.debug("nodesList.size() is: "+nodesList.size());
		/**
		 * add edges to the graph as follows: 3,5,2,0,3,5,2,0,...
		 * for consecutive cells in the dataArray
		 */
		for(int j=0; j<chromoA.getLength();j++) {
			// add three edges to the (i)^{th} node
			for(int k3=0; k3<3; k3++) {
				((ArrayList)cellList.get(j)).add(
					nodeList.get(numNodesAdded%100));
				numNodesAdded++;
			}
			j++;
			if(j>=chromoA.getLength()) {
				break;
			}
			// add five edges to the (i+1)^{th} node
			for(int k5=0; k5<5; k5++) {
//Debugger.debug("J is: "+j);
				((ArrayList)cellList.get(j)).add(
					nodeList.get(numNodesAdded%100));
				numNodesAdded++;
			}
			j++;
			if(j>=chromoA.getLength()) {
				break;
			}
			// add two edges to the (i+2)^{th} node
			for(int k2=0; k2<2; k2++) {
				((ArrayList)cellList.get(j)).add(
					nodeList.get(numNodesAdded%100));
				numNodesAdded++;
			}
			// add zero edges to the (i+3)^{th} node
			j++;
		}
		// Add Chromosome A to the population of Chromosomes
		set.add(chromoA);
		
		// Clone Chromosome A, and call it Chromosome B
		Chromosome chromoB = (Chromosome)chromoA.clone();
		// Add Chromosome B to the population of Chromosomes
		set.add(chromoB);
		
		Assertion.asrt(chromoB.getNumEdges() == numNodesAdded,
			"Number of edges in Chromosome B is: "+chromoB.getNumEdges(),
			"HELP!!! Number of edges in Chromosome B is NOT determined correctly; "
				+"This implies that Chromosome A does not have the correct "
				+"number of edges as well");
				
		// Number of edges added to current graph - Chromosome C
		numNodesAdded=0;
		// List of lists containing destination nodes
		cellList = (ArrayList)chromoC.getDataArray();
		/**
		 * add edges to the graph as follows: 5,2,8,5,2,8,...
		 * for consecutive cells in the dataArray
		 */
		for(int j=0; j<chromoC.getLength();j++) {
			// add five edges to the (i)^{th} node
			for(int k5=0; k5<5; k5++) {
				((ArrayList)cellList.get(j)).add(
					nodeList.get(numNodesAdded%100));
				numNodesAdded++;
			}
			j++;
			if(j>=chromoC.getLength()) {
				break;
			}
			// add two edges to the (i+1)^{th} node
			for(int k2=0; k2<2; k2++) {
//Debugger.debug("J is: "+j);
				((ArrayList)cellList.get(j)).add(
					nodeList.get(numNodesAdded%100));
				numNodesAdded++;
			}
			j++;
			if(j>=chromoC.getLength()) {
				break;
			}
			// add eight edges to the (i+2)^{th} node
			for(int k8=0; k8<8; k8++) {
				((ArrayList)cellList.get(j)).add(
					nodeList.get(numNodesAdded%100));
				numNodesAdded++;
			}
		}
		
		// Add Chromosome C to the population of Chromosomes
		set.add(chromoC);
		
		// Clone Chromosome C, and call it Chromosome D
		Chromosome chromoD = (Chromosome)chromoC.clone();
		// Add Chromosome D to the population of Chromosomes
		set.add(chromoD);
		Assertion.asrt(chromoD.getNumEdges() == numNodesAdded,
			"Number of edges in Chromosome D is: "+chromoD.getNumEdges(),
			"HELP!!! Number of edges in Chromosome D is NOT determined correctly; "
				+"This implies that Chromosome C does not have the correct "
				+"number of edges as well");
		
		// Number of edges added to current graph - Chromosome E
		numNodesAdded=0;
		// List of lists containing destination nodes
		cellList = (ArrayList)chromoE.getDataArray();
		/**
		 * add edges to the graph as follows: 9,3,7,1,2,9,3,7,1,2,...
		 * for consecutive cells in the dataArray
		 */
		for(int j=0; j<chromoE.getLength();j++) {
			// add nine edges to the (i)^{th} node
			for(int k9=0; k9<9; k9++) {
				((ArrayList)cellList.get(j)).add(
					nodeList.get(numNodesAdded%100));
				numNodesAdded++;
			}
			j++;
			if(j>=chromoE.getLength()) {
				break;
			}
			// add three edges to the (i+1)^{th} node
			for(int k3=0; k3<3; k3++) {
//Debugger.debug("J is: "+j);
				((ArrayList)cellList.get(j)).add(
					nodeList.get(numNodesAdded%100));
				numNodesAdded++;
			}
			j++;
			if(j>=chromoE.getLength()) {
				break;
			}
			// add seven edges to the (i+1)^{th} node
			for(int k7=0; k7<7; k7++) {
//Debugger.debug("J is: "+j);
				((ArrayList)cellList.get(j)).add(
					nodeList.get(numNodesAdded%100));
				numNodesAdded++;
			}
			j++;
			if(j>=chromoE.getLength()) {
				break;
			}
			// add one edges to the (i+1)^{th} node
			for(int k1=0; k1<1; k1++) {
//Debugger.debug("J is: "+j);
				((ArrayList)cellList.get(j)).add(
					nodeList.get(numNodesAdded%100));
				numNodesAdded++;
			}
			j++;
			if(j>=chromoE.getLength()) {
				break;
			}
			// add two edges to the (i+2)^{th} node
			for(int k2=0; k2<2; k2++) {
				((ArrayList)cellList.get(j)).add(
					nodeList.get(numNodesAdded%100));
				numNodesAdded++;
			}
		}
		
		// Add Chromosome E to the population of Chromosomes
		set.add(chromoE);
		Assertion.asrt(chromoE.getNumEdges() == numNodesAdded,
			"Number of edges in Chromosome E is: "+chromoE.getNumEdges(),
			"HELP!!! Number of edges in Chromosome E is NOT determined correctly");

		SetOfChromosomes popOfChromos = new SetOfChromosomes(set);
		// Test the method getPopSize()...
		Assertion.asrt(popOfChromos.getPopSize() == numChromos,
			"Number of chromosomes in the population is: "+popOfChromos.getPopSize(),
			"OOPS!!! The number of chromosomes in the population is INCORRECT!");
		// Test the method getCurPop()...
		Assertion.asrt(popOfChromos.getCurPop() == set,
			"The population of chromosomes is assigned correctly",
			"OH OH!!! The population of chromosomes is assigned INCORRECTLY");
		// Test the method getChromo(int i)...
		Assertion.asrt(popOfChromos.getChromo(4) == chromoE,
			"The 5th chromosome in the population is assigned correctly",
			"OH OH!!! The 5th chromosome in the population is assigned INCORRECTLY");
		Assertion.asrt(popOfChromos.getChromo(1) == chromoB,
			"The 2nd chromosome in the population is assigned correctly",
			"OH OH!!! The 2nd chromosome in the population is assigned INCORRECTLY");
		Debugger.debug("");

		return popOfChromos;
    }
    
        
    /**
	 * Method to test if this pair of Chromosomes are twins
	 * @pre none
	 * @return nothing
	 */
	public static void testCheckIfTwins() {
		// A clone of something has 100% correlation with that object
		// A set of Chromosomes to be determined if they are twins
		Chromosome[] twins;
		
		//Chromosome[] arrayChromos = ((ArrayList)setOfChromos.getCurPop())
		//	.toArray(setOfChromos.getPopSize());
		
		// Chromosome B is a clone of Chromosome A
		Chromosome a = (Chromosome)setOfChromos.getChromo(0);
		Chromosome b = (Chromosome)setOfChromos.getChromo(1);
		// Put them into an array and determine if they are twins
		twins = new Chromosome[2];
		twins[0] = a;
		twins[1] = b;
		Assertion.asrt(setOfChromos.checkIfTwins(twins),
			"Chromosome B is a clone of Chromosome A",
			"Chromosome B IS NOT a clone of Chromosome A");
		/*
		Assertion.asrt(((Chromosome)setOfChromos.getChromo(0))
			== ((Chromosome)setOfChromos.getChromo(1)),
			"Chromosome B is a clone of Chromosome A",
			"Chromosome B IS NOT a clone of Chromosome A");
		*/
		
		// Similarly, Chromosome D is a clone of Chromosome C
		Chromosome c = (Chromosome)setOfChromos.getChromo(2);
		Chromosome d = (Chromosome)setOfChromos.getChromo(3);
		// Put them into an array and determine if they are twins
		twins[0] = c;
		twins[1] = d;
		Assertion.asrt(setOfChromos.checkIfTwins(twins),
			"Chromosome D is a clone of Chromosome C",
			"Chromosome D IS NOT a clone of Chromosome C");
		// There exist no relationship (zero correlation) between other networks
		Chromosome e = (Chromosome)setOfChromos.getChromo(4);
		Chromosome f = (Chromosome)setOfChromos.getChromo(5);
		/**
		 * Put Chromosomes that aren't twins into an array and
		 * determine if they are twins
		 */
		twins[0] = e;
		twins[1] = f;
		Assertion.asrt(!setOfChromos.checkIfTwins(twins),
			"Chromosome F is not a clone of Chromosome E",
			"HELP!!! Chromosome F IS a clone of Chromosome E");
		twins[0] = a;
		twins[1] = c;
		Assertion.asrt(!setOfChromos.checkIfTwins(twins),
			"Chromosome A is not a clone of Chromosome C",
			"HELP!!! Chromosome A IS a clone of Chromosome C");
		twins[0] = a;
		twins[1] = e;
		Assertion.asrt(!setOfChromos.checkIfTwins(twins),
			"Chromosome A is not a clone of Chromosome E",
			"HELP!!! Chromosome A IS a clone of Chromosome E");
		twins[0] = a;
		twins[1] = f;
		Assertion.asrt(!setOfChromos.checkIfTwins(twins),
			"Chromosome A is not a clone of Chromosome F",
			"HELP!!! Chromosome A IS a clone of Chromosome F");
		twins[0] = e;
		twins[1] = c;
		Assertion.asrt(!setOfChromos.checkIfTwins(twins),
			"Chromosome E is not a clone of Chromosome C",
			"HELP!!! Chromosome E IS a clone of Chromosome C");
		twins[0] = f;
		twins[1] = c;
		Assertion.asrt(!setOfChromos.checkIfTwins(twins),
			"Chromosome F is not a clone of Chromosome C",
			"HELP!!! Chromosome F IS a clone of Chromosome C");
		twins = new Chromosome[3];
		twins[0] = a;
		twins[1] = c;
		twins[2] = e;
		Assertion.asrt(!setOfChromos.checkIfTwins(twins),
			"Chromosomes A, C, & E are not clones of each other",
			"HELP!!! Chromosomes A, C, & E ARE CLONES of each other");
		twins[0] = a;
		twins[1] = c;
		twins[2] = f;
		Assertion.asrt(!setOfChromos.checkIfTwins(twins),
			"Chromosomes A, C, & F are not clones of each other",
			"HELP!!! Chromosomes A, C, & F ARE CLONES of each other");
		twins[0] = a;
		twins[1] = f;
		twins[2] = e;
		Assertion.asrt(!setOfChromos.checkIfTwins(twins),
			"Chromosomes A, F, & E are not clones of each other",
			"HELP!!! Chromosomes A, F, & E ARE CLONES of each other");
		twins[0] = f;
		twins[1] = c;
		twins[2] = e;
		Assertion.asrt(!setOfChromos.checkIfTwins(twins),
			"Chromosomes F, C, & E are not clones of each other",
			"HELP!!! Chromosomes F, C, & E ARE CLONES of each other");
		twins[0] = a;
		twins[1] = b;
		twins[2] = b;
		Assertion.asrt(setOfChromos.checkIfTwins(twins),
			"Chromosomes A, B, & B are clones of each other",
			"HELP!!! Chromosomes A, B, & B ARE NOT CLONES of each other");
		twins[0] = c;
		twins[1] = d;
		twins[2] = c;
		Assertion.asrt(setOfChromos.checkIfTwins(twins),
			"Chromosomes C, D, & C are clones of each other",
			"HELP!!! Chromosomes C, D, & C ARE NOT CLONES of each other");
		twins[0] = a;
		twins[1] = null;
		twins[2] = b;
		Assertion.asrt(!setOfChromos.checkIfTwins(twins),
			"Chromosomes A, null, & B are not clones of each other",
			"HELP!!! Chromosomes A, null, & B ARE CLONES of each other");
		twins[0] = null;
		twins[1] = a;
		twins[2] = b;
		Assertion.asrt(!setOfChromos.checkIfTwins(twins),
			"Chromosomes null, A, & B are not clones of each other",
			"HELP!!! Chromosomes null, A, & B ARE CLONES of each other");
		twins = new Chromosome[5];
		twins[0] = a;
		twins[1] = b;
		twins[2] = a;
		twins[3] = b;
		twins[4] = b;
		Assertion.asrt(setOfChromos.checkIfTwins(twins),
			"Chromosomes A, B, A, B, & B are clones of each other",
			"HELP!!! Chromosomes A, B, A, B, & B ARE NOT CLONES of each other");
		twins = new Chromosome[6];
		twins[0] = a;
		twins[1] = b;
		twins[2] = c;
		twins[3] = d;
		twins[4] = e;
		twins[5] = f;
		Assertion.asrt(!setOfChromos.checkIfTwins(twins),
			"Chromosomes A, B, C, D, E, & F are not clones of each other",
			"HELP!!! Chromosomes A, B, C, D, E, & F ARE CLONES of each other");
	}
	
	/**
	 * Method to test if a Chromosome can be added correctly
	 * @pre none
	 * @return nothing
	 */
	public static void testAddChromo() {

		// ASSUME THAT THE SIZE OF THE POPULATION IS > 0
		// number of clients in the network
		int numClients = ((Chromosome)setOfChromos.getChromo(0)).getNumClients();
		// number of servers in the network
		int numServers = ((Chromosome)setOfChromos.getChromo(0)).getNumServers();
		// List of nodes in the network
		ArrayList nodesList = ((Chromosome)setOfChromos.getChromo(0)).getNodeList();
		// Create a new Chromosome that will be added to the population
		Chromosome chromoF = new Chromosome(nodesList,numServers,numClients);
		// Check if Chromosome F has been instantiated with the correct list of Nodes
		Assertion.asrt(chromoF.getNumServers() == numServers,
			"Chromosome F has been assigned with the correct number of servers",
			"Chromosome F has NOT been assigned with the correct number of servers");
		Assertion.asrt(chromoF.getNumClients() == numClients,
			"Chromosome F has been assigned with the correct number of clients",
			"Chromosome F has NOT been assigned with the correct number of clients");
		Assertion.asrt(chromoF.getNodeList().equals(nodesList),
			"Chromosome F has been assigned with the correct list of Nodes",
			"Chromosome F has NOT been assigned with the correct list of Nodes");
		// Number of edges added to current graph - Chromosome F
		int numNodesAdded=0;
		// List of lists containing destination nodes
		ArrayList cellList = (ArrayList)chromoF.getDataArray();
		/**
		 * add edges to the graph as follows: 3,4,6,8,3,4,6,8,...
		 * for consecutive cells in the dataArray
		 */
		for(int j=0; j<chromoF.getLength();j++) {
			// add three edges to the (i)^{th} node
			for(int k3=0; k3<3; k3++) {
				((ArrayList)cellList.get(j)).add(
					nodesList.get(numNodesAdded%100));
				numNodesAdded++;
			}
			j++;
			if(j>=chromoF.getLength()) {
				break;
			}
			// add four edges to the (i+1)^{th} node
			for(int k4=0; k4<4; k4++) {
//Debugger.debug("J is: "+j);
				((ArrayList)cellList.get(j)).add(
					nodesList.get(numNodesAdded%100));
				numNodesAdded++;
			}
			j++;
			if(j>=chromoF.getLength()) {
				break;
			}
			// add six edges to the (i+1)^{th} node
			for(int k6=0; k6<6; k6++) {
//Debugger.debug("J is: "+j);
				((ArrayList)cellList.get(j)).add(
					nodesList.get(numNodesAdded%100));
				numNodesAdded++;
			}
			j++;
			if(j>=chromoF.getLength()) {
				break;
			}
			// add eight edges to the (i+1)^{th} node
			for(int k8=0; k8<8; k8++) {
//Debugger.debug("J is: "+j);
				((ArrayList)cellList.get(j)).add(
					nodesList.get(numNodesAdded%100));
				numNodesAdded++;
			}
		}
		
		// Size of the population before the addition of a chromosome
		int preAddSize = setOfChromos.getPopSize();
		// Add Chromosome F to the population of Chromosomes
		setOfChromos.addChromo(chromoF);
		Assertion.asrt(chromoF.getNumEdges() == numNodesAdded,
			"Number of edges in Chromosome F is: "+chromoF.getNumEdges(),
			"HELP!!! Number of edges in Chromosome F is NOT determined correctly");
		Assertion.asrt(setOfChromos.getChromo(preAddSize) == chromoF,
			"The sixth chromosome has been correctly added to the population",
			"The sixth chromosome has been INCORRECTLY added to the population");
		preAddSize++;
		Assertion.asrt(setOfChromos.getPopSize() == preAddSize,
			"Size of the population after the addition of a 6th chromosome is: "
			+setOfChromos.getPopSize(),
			"HELP!!! The size of the population is NOT determined correctly");
	}
	
	/**
	 * Method to test if the method appendPop(SetOfChromosomes set) works
	 * @param none
	 * @return none
	 */
	public static void testAppendPop() {
		// Retrieve each chromosome from the list of chromosomes
		Chromosome a = (Chromosome)setOfChromos.getChromo(0);
		Chromosome b = (Chromosome)setOfChromos.getChromo(1);
		Chromosome c = (Chromosome)setOfChromos.getChromo(2);
		Chromosome d = (Chromosome)setOfChromos.getChromo(3);
		Chromosome e = (Chromosome)setOfChromos.getChromo(4);
		Chromosome f = (Chromosome)setOfChromos.getChromo(5);
		ArrayList listA = new ArrayList();
		listA.add(a);
		listA.add(b);
		SetOfChromosomes setA = new SetOfChromosomes(listA);
		ArrayList listB = new ArrayList();
		listB.add(c);
		listB.add(d);
		listB.add(e);
		listB.add(f);
		SetOfChromosomes setB = new SetOfChromosomes(listB);
		setA.appendPop(setB);
		int size = 6;
		Assertion.asrt(setA.getPopSize() == size,
			"The populations setA and setB have been appended to"
			+" each other correctly",
			"The populations setA and setB HAVE NOT been appended to"
			+" each other correctly");
		setA.appendPop(null);
		Assertion.asrt(setA.getPopSize() == size,
			"The populations setA and null have been appended to"
			+" each other correctly",
			"The populations setA and null HAVE NOT been appended to"
			+" each other correctly");
		setB.appendPop(setA);
		Assertion.asrt(setB.getPopSize() == (size+4),
			"The populations (setA and setB) and setB have been appended to"
			+" each other correctly",
			"The populations (setA and setB) and setB HAVE NOT been appended to"
			+" each other correctly");
		// Create an empty set
		SetOfChromosomes setC = new SetOfChromosomes();
		// Append empty set to setB
		setB.appendPop(setC);
		// Set B has not changed...
		Assertion.asrt(setB.getPopSize() == (size+4),
			"The population (setA and setB) remains unchanged with the "
			+"addition of an empty set",
			"The population (setA and setB) WAS CHANGED with the "
			+"addition of an empty set");
		// Append setB to empty set
		setC.appendPop(setB);
		// Set B is equal to Set C...
		Assertion.asrt(setC.getPopSize() == (size+4),
			"Set B and Set C are now the same",
			"UH OH! Set B and Set C are NOT the same");
	}
	
	/**
	 * Method to test the method sort(Comparator comp, ArrayList pop)
	 * @param none
	 * @return none
	 */
	public static void testSort() {
		// Assign fitness values to each Chromosome
		int[] fitness = {73,41,97,17,53,87};
		for(int i=0;i<setOfChromos.getPopSize(); i++) {
			setOfChromos.getChromo(i).setFitness(fitness[i]);
		}
		// Sort the population of Chromosomes
		setOfChromos.sort();
		/**
		 * Check if the population of Chromosomes have been properly
		 * sorted in ascending order
		 */
		for(int j=0;j<(setOfChromos.getPopSize()-1);j++) {
			Assertion.asrt(setOfChromos.getChromo(j).getFitness() <
				setOfChromos.getChromo((j+1)).getFitness(),
				"Population of Chromosomes is sorted correctly",
				"Population of Chromosomes HAS YET TO BE sorted correctly");
		}
	}
}