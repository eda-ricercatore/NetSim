// importing packages
import utility.*;
import population.*;
import population.graph.*;
import ecomp.*;
import java.util.*;

/**
 * This is the first part of the module tests for NetworkGAImp. This will
 * test the default constructor, basic accessor and mutator methods, and
 * methods for population generation, such as initNode, init Chromo and 
 * initPop.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.7
 */
public class ModuleTestNetworkGA01 {
    // instance variable to store info regarding current test
    private static String testName;
	
    
    // ---------------------------------------------------------------------
	 
    // Default constructor
    /**
     * Should not instantate a test class.
     */
    public ModuleTestNetworkGA01() {
        Debugger.printErr("Don't instantiate a test class: " + 
                            "ModuleTestNetworkGA01");
    }
	
	// --------------------------------------------------------------------
    
    /**
     * Initiate tests for the default constructor, basic accessor and mutator
     * methods, and methods for  population generation, such as initNode, 
     * init Chromo and initPop.
     */
	public static void main(String[] args) {
        Debugger.pipeResult("NetworkGAImpNormal01.txt",
                            "NetworkGAImpError01.txt");
        Debugger.enableTrace(true);

        Debugger.debug("==================================\n"+
                       "filename: NetworkGAImpNormal01.txt\n" +
                       "==================================");
        Debugger.debug("Module Test 01 for ecomp.NetworkGAImp:\n");
        
        Debugger.printErr("=================================\n"+
                          "filename: NetworkGAImpError01.txt\n" +
                          "=================================");
        Debugger.printErr("Module Test 01 for ecomp.NetworkGAImp:\n");

        // test default constructor
        NetworkGAImp defaultImp = testDefaultConstructor();
        Debugger.debug("");
        // test basic with default instance
        testBasic(defaultImp);
        Debugger.debug("");
 
        // test initNode with default instance    
        testInitNode(defaultImp);
        Debugger.debug("");
        // test initPop with default instance
        testInitChromo(defaultImp);
        Debugger.debug("");
        // test initPop with default instance
        testInitPop(defaultImp);
        Debugger.debug("");

        Debugger.debug("========================================");
        Debugger.debug("Module Test for NetworkGAImp01 Completed");
        Debugger.debug("========================================");
        Debugger.printErr("========================================");
        Debugger.printErr("Module Test for NetworkGAImp01 Completed");
        Debugger.printErr("========================================");
    }
    
// ==========================================================================
    
    /**
     * Attempt to instantiate a default instance of NetworkGAImp
     */
    private static NetworkGAImp testDefaultConstructor() {
        Debugger.debug(testName = "Testing to instantiate default " +
                                    "instance of NetworkGAImp");

        Debugger.debug("===========================" +
                           "============================\n");
        NetworkGAImp nga = new NetworkGAImp();
        if(nga != null) 
            Debugger.debug("    A default instance has been created");
        else
            Debugger.printErr(testName + "\n    !!!ERROR occured " +
                "when instantiating a default instance of NetworkGAImp");
        return nga;
    }

// ==========================================================================

    /**
     * Tests the basic accessor and mutator methods of "NetworkGAImp"
     */
    private static void testBasic(NetworkGAImp nga) {
        Debugger.debug("Testing the basic accessor and mutator methods of " +
                            "NetworkGAImp");
        Debugger.debug("==============================="+
                            "==============================\n");

        double prob;
        
        // ========================================================
        // Testing setting and retrieving probability Crossover
        // ========================================================
        nga.getPrCrossover();

        testName = "Setting probability of crossover";
        
        // test setting probability of crossover with illegal value
        prob = -1.5;
        Debugger.debug(testName + " to " + prob);
        try {
            nga.setPrCrossover(prob);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                        "SHOULD have been caught, since prCrossover to be" +
                        "\n    set is " + prob + ", violating the " +
                        "precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                        "prCrossover to be set is " + prob + ",\n    " +
                        "violating the precondition");
        }
        Debugger.debug("");

        // test setting probability of crossover with illegal value
        prob = 1.5;
        Debugger.debug(testName + " to " + prob);
        try {
            nga.setPrCrossover(prob);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                        "SHOULD have been caught, since prCrossover to be" +
                        "\n    set is " + prob + ", violating the " +
                        "precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                        "prCrossover to be set is " + prob + ",\n    " +
                        "violating the precondition");
        }
        Debugger.debug("");

        // test setting probability of crossover with legal value
        prob = 0.3;
        Debugger.debug(testName + " to " + prob);
        try {
            nga.setPrCrossover(prob);
            Debugger.debug(testName + ":\n    Exception not caught as " +
                        "expected, since prCrossover to be\n    set is " + 
                        prob +", which is legal");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                        "SHOULD NOT have been caught since prCrossover to " +
                        "be set is " + prob + ",\n    which is legal");
        }

        nga.getPrCrossover();
        
        Debugger.debug("");

        // =======================================================
        // Testing setting and retrieving probability Mutation
        // =======================================================
        nga.getPrMutation();

        testName = "Setting probability of mutation";
        
        // test setting probability of mutation with illegal value
        prob = -1.5;
        Debugger.debug(testName + " to " + prob);
        try {
            nga.setPrMutation(prob);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                        "SHOULD have been caught, since prMutation to be" +
                        "\n    set is " + prob + ", violating the " +
                        "precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                        "prMutation to be set is " + prob + ",\n    " +
                        "violating the precondition");
        }
        Debugger.debug("");

        // test setting probability of mutation with illegal value
        prob = 1.5;
        Debugger.debug(testName + " to " + prob);
        try {
            nga.setPrMutation(prob);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                        "SHOULD have been caught, since prMutation to be" +
                        "\n    set is " + prob + ", violating the " +
                        "precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                        "prMutation to be set is " + prob + ",\n    " +
                        "violating the precondition");
        }
        Debugger.debug("");

        // test setting probability of mutation with legal value
        prob = 0.05;
        Debugger.debug(testName + " to " + prob);
        try {
            nga.setPrMutation(prob);
            Debugger.debug("    Exception not caught as " +
                        "expected, since prMutation to be\n    set is " + 
                        prob +", which is legal");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                        "SHOULD NOT have been caught since prMutation to " +
                        "be set is " + prob + ",\n    which is legal");
        }

        nga.getPrMutation();

        // =================================================================
        // Testing incrementing and retrieving number of generations evolved
        // =================================================================
        Debugger.debug(testName = "Testing incrementing and retrieving " +
                                    "number of generations evolved");
        int gens;
        Debugger.debug("Generations evolved is " + (gens = nga.getNumGen()) +
                            " generations");
        Debugger.debug("Increase the number of generations evolved by 1 " +
                            "by calling increGens()");
        nga.increGen();
        if( nga.getNumGen() == (gens+1)) {
            Debugger.debug("Generations evolved is now " + nga.getNumGen() +   
                            " generations");
        } else {
            Debugger.printErr(testName + ":\n    ERROR!!! Generations " +
                            "evolved WAS NOT incremented by 1 generation, " +
                            "but by " + (nga.getNumGen()-gens) );    
        }

        Debugger.debug("");
        Debugger.debug("Testing to basic accessor and mutator methods of " +
                            "NetworkGAImp Completed");
        Debugger.debug("===================================="+
                            "===================================\n");
    }

// ==========================================================================
    
    /**
     * Tests initNodes with various input labels
     */
    private static void testInitNode(NetworkGAImp nga) {
        Debugger.debug(testName = "Testing initNode");
        Debugger.debug("================");

        String label = null;
        Node temp;

        // try to initialise a node with label being Null;
        Debugger.debug(testName + " with label = " + label);
        try {
            nga.initNode(label);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                                "SHOULD have been caught, since label = " +
                                label + "\n    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "label = "+label+" violates the precondition");
        }
        Debugger.debug("");

        // try to initialise a node with label Router
        label = "Router";
        Debugger.debug(testName + " with label = " + label);
        try {
            nga.initNode(label);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                                "SHOULD have been caught, since label = " +
                                label + "\n    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "label = "+label+" violates the precondition");
        }
        Debugger.debug("");
        
        // try to initialise a node with label SERVER
        label = "SERVER";
        try {
            temp = nga.initNode(label);
            Debugger.debug("    Exception not caught as expected since " +
                           "label = "+ label +" satisfies the precondition");
            checkBasicProperties(temp);
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                                "SHOULD NOT have been caught, since " +
                                "label = " + label + "\n    violates the " +
                                "precondition");
        }
        Debugger.debug("");

        // try to initialise a node with label SeRvEr
        label = "SeRvEr";
        Debugger.debug(testName + " with label = " + label);
        try {
            temp = nga.initNode(label);
            Debugger.debug("    Exception not caught as expected since " +
                           "label = "+ label +" satisfies the precondition");
            checkBasicProperties(temp);
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                                "SHOULD NOT have been caught, since " +
                                "label = " + label + "\n    violates the " +
                                "precondition");
        }
        Debugger.debug("");

        // try to initialise a node with label CLIENT
        label = "CLIENT";
        Debugger.debug(testName + " with label = " + label);
        try {
            temp = nga.initNode(label);
            Debugger.debug("    Exception not caught as expected since " +
                           "label = "+ label +" satisfies the precondition");
            checkBasicProperties(temp);
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                                "SHOULD NOT have been caught, since " +
                                "label = " + label + "\n    violates the " +
                                "precondition");
        }
        Debugger.debug("");

        // try to initialise a node with label cLiEnT
        label = "cLiEnT";
        Debugger.debug(testName + " with label = " + label);
        try {
            temp = nga.initNode(label);
            Debugger.debug("    Exception not caught as expected since " +
                           "label = "+ label +" satisfies the precondition");
            checkBasicProperties(temp);
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                                "SHOULD NOT have been caught, since " +
                                "label = " + label + "\n    violates the " +
                                "precondition");
        }
        Debugger.debug("");

        // try to initialise a node with label SERVER-Itanium
        label = "SERVER-Itanium";
        Debugger.debug(testName + " with label = " + label);
        try {
            temp = nga.initNode(label);
            Debugger.debug("    Exception not caught as expected since " +
                           "label = "+ label +" satisfies the precondition");
            checkBasicProperties(temp);
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                                "SHOULD NOT have been caught, since " +
                                "label = " + label + "\n    violates the " +
                                "precondition");
        }
        Debugger.debug("");

        // try to initialise a node with label CLIENT-Sunray
        label = "CLIENT-Sunray";
        Debugger.debug(testName + " with label = " + label);
        try {
            temp = nga.initNode(label);
            Debugger.debug("    Exception not caught as expected since " +
                           "label = "+label+" satisfies the precondition");
            checkBasicProperties(temp);
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                                "SHOULD NOT have been caught, since " +
                                "label = " + label + "\n    violates the " +
                                "precondition");
        }
        Debugger.debug("");

        Debugger.debug("Testing initNode Completed");
        Debugger.debug("==========================");
    }

    /**
     * Queries a node object for its basic properties.
     * @param n The node to be checked
     */
    private static void checkBasicProperties(Node n) {
        Debugger.debug("    Checking basic properties:");
        try {
            String Label    = n.getLabel();
            int[]  coord    = n.getCoordinates();
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + ":\n    This instance of node "+
                                                "has invalid properties.");
        }
    }
// ==========================================================================

    /**
     * Tests initChromo with different nodeList and combination of number
     * of servers and clients
     */
    private static void testInitChromo(NetworkGAImp nga) {
        testName = "Testing initChromo";
        Debugger.debug(testName);
        Debugger.debug("==================");

        ArrayList nodeList = new ArrayList();
        int numServers, numClients;   

        /**
         * trying initChrom with empty nodeList, numServers = 3 and
         * numClients = 8,
         */
        numServers = 3;
        numClients = 8;

        Debugger.debug(testName + "with nodeList.size() = " +
                    nodeList.size() +", with numServers = " + numServers + 
                    ",\n    with numClients = " + numClients);
        try {
            nga.initChromo(nodeList, numServers, numClients);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD have been caught, since nodeList.size() = " +
                    nodeList.size() + "\n    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                    "nodeList.size() = " + nodeList.size() + "\n    " + 
                    "violates the precondition");
        }
        Debugger.debug("");

        nodeList = genNodeList();

        /**
         * trying initChrom with poppulated nodeList, numServers = -3 and
         * numClients = -8,
         */
        numServers = -3;
        numClients = -8;
        Debugger.debug(testName + "with nodeList.size() = " +
                    nodeList.size() +", with numServers = " + numServers + 
                    ",\n    with numClients = " + numClients);
        try {
            nga.initChromo(nodeList, numServers, numClients);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD have been caught, since numServers = " + 
                    numServers + " and numClients = " + numClients + 
                    "\n    violate the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                    "numServers = " + numServers + " and numClients = " +
                    numClients + "\n    violate the precondition");
        }
        Debugger.debug("");

        /**
         * trying initChrom with poppulated nodeList, numServers = -3 and
         * numClients = 8,
         */
        numServers = -3;
        numClients = 8;
        Debugger.debug(testName + "with nodeList.size() = " +
                    nodeList.size() +", with numServers = " + numServers + 
                    ",\n    with numClients = " + numClients);
        try {
            nga.initChromo(nodeList, numServers, numClients);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                "SHOULD have been caught, since numServers = " + 
                numServers + " violates the\n    precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                "numServers = " + numServers + " violates the precondition");
        }
        Debugger.debug("");

        /**
         * trying initChrom with poppulated nodeList, numServers = 3 and
         * numClients = -8,
         */
        numServers = 3;
        numClients = -8;
        Debugger.debug(testName + "with nodeList.size() = " +
                    nodeList.size() +", with numServers = " + numServers + 
                    ",\n    with numClients = " + numClients);
        try {
            nga.initChromo(nodeList, numServers, numClients);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                "SHOULD have been caught, since numClients = " + 
                numClients + " violates the\n    precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                "numClients = " + numClients + " violates the precondition");
        }
        Debugger.debug("");

        /**
         * trying initChrom with poppulated nodeList, numServers = 3 and
         * numClients = 9,
         */
        numServers = 3;
        numClients = 9;
        Debugger.debug(testName + "with nodeList.size() = " +
                    nodeList.size() +", with numServers = " + numServers + 
                    ",\n    with numClients = " + numClients);
        try {
            nga.initChromo(nodeList, numServers, numClients);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD have been caught, since nodeList.size() = " +
                    nodeList.size() + " but (numServers + numClients) = " + 
                    (numServers + numClients) );
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                "nodeList.size() = " + nodeList.size() + "\n    but " +
                "(numServers + numClients) = " + (numServers + numClients) );
        }
        Debugger.debug("");

        /**
         * trying initChrom with poppulated nodeList, numServers = 3 and
         * numClients = 8,
         */
        Chromosome result = null;
        numServers = 3;
        numClients = 8;
        Debugger.debug(testName + "with nodeList.size() = " +
                    nodeList.size() +", with numServers = " + numServers + 
                    ",\n    with numClients = " + numClients);
        try {
            result = nga.initChromo(nodeList, numServers, numClients);
            Debugger.debug("    Exception not caught as expected since " +
                "nodeList.size() = " + nodeList.size() + "\n    and " +
                "(numServers + numClients) = " + (numServers + numClients) );

        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD NOT have been caught, since nodeList.size() = " +
                    nodeList.size() + " and (numServers + numClients) = " + 
                    (numServers + numClients) );
        }
        Debugger.debug("");

        printChromo(result);        

        /**
         * trying initChrom with poppulated nodeList, numServers = 3 and
         * numClients = 8,
         */
        Debugger.debug("");
        Debugger.debug(testName + "with nodeList.size() = " +
                    nodeList.size() +", with numServers = " + numServers + 
                    ",\n    with numClients = " + numClients);
        try {
            result = nga.initChromo(nodeList, numServers, numClients);
            Debugger.debug("    Exception not caught as expected since " +
                "nodeList.size() = " + nodeList.size() + "\n    and " +
                "(numServers + numClients) = " + (numServers + numClients) );

        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD NOT have been caught, since nodeList.size() = " +
                    nodeList.size() + " and (numServers + numClients) = " + 
                    (numServers + numClients) );
        }
        Debugger.debug("");

        printChromo(result);        


        
        Debugger.debug(testName + " Completed");
        Debugger.debug("============================");

    }

    /**
     * Generate the nodeList which resemble nodes in our "Mock-Up" graph
     */
    private static ArrayList genNodeList() {

        ArrayList nodeList = new ArrayList();

        //Populating Node List
        Debugger.debug("Populate the nodeList with 11 nodes");

        Debugger.enableTrace(false);
        // assume the worst case repair generation is 20 generations
        int repgen = (int)(Math.random() * 20.0);
        /**
         * assume the probability of failure is 30% and the threshold
         * (the maximum allowable probability of failure) is 70%.
         */
        Repairable r = new Repairable(repgen, 0.30, 0.70, true);
        // node is capable of 10 Gbit traffic
        double[] params = {1000000000, 1.0}; 
        Node tmpNode = new NodeImp (params, r);
        
        nodeList.add(new NodeImp("CLIENT", 130, 160, tmpNode));
        nodeList.add(new NodeImp("SERVER", 210, 210, tmpNode));
        nodeList.add(new NodeImp("SERVER", 130,  70, tmpNode));
        nodeList.add(new NodeImp("SERVER",  70, 120, tmpNode));
        nodeList.add(new NodeImp("CLIENT",  60,  30, tmpNode));
        nodeList.add(new NodeImp("CLIENT", 130,  10, tmpNode));
        nodeList.add(new NodeImp("CLIENT", 210,  60, tmpNode));
        nodeList.add(new NodeImp("CLIENT", 200, 120, tmpNode));
        nodeList.add(new NodeImp("CLIENT",  10, 170, tmpNode));
        nodeList.add(new NodeImp("CLIENT",  90, 200, tmpNode));
        nodeList.add(new NodeImp("CLIENT",  10,  90, tmpNode));

        Debugger.enableTrace(true);
        return nodeList;
    }

    /**
     * Prints a simple adjacency list of a chromosome
     */
    private static void printChromo(Chromosome c) {
        String line;

        Debugger.enableTrace(false);

        ArrayList nodeList = c.getNodeList();
        ArrayList cells = c.getDataArray();
        ArrayList destinations;

        for (int i = 0; i < cells.size(); i++) {
            line = "Node " + i + " -> ";
            
            destinations = (ArrayList) cells.get(i);
            for (int j = 0; j < destinations.size(); j++) {
                if (j > 0) line += ", ";
                line += nodeList.indexOf(destinations.get(j));
            }
            Debugger.enableTrace(true);
            Debugger.debug(line);
            Debugger.enableTrace(false); // mute
        }
        Debugger.enableTrace(true);
        Debugger.debug("");
    }

// ==========================================================================

    /**
     * Tests initPop under different asserted conditions, and with different
     * input parameter. There is also a cross check to see if two population
     * created using the same method calls will generate different populations
     * as the process is random.
     */
    private static void testInitPop(NetworkGAImp nga) {

        Debugger.debug("Testing methods related to population");
        Debugger.debug("=====================================");

        int size;

        testName = "Testing setPopSize";
        // trying setPopSize with size = -2
        size = -2;
        Debugger.debug(testName + " with size = " + size);
        try {
            nga.setPopSize(size);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                                "SHOULD have been caught, since size = " +
                                size + "\n    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "size = " + size + " violates the precondition");
        }
        Debugger.debug("");

        // trying setPopSize with size = 0
        size = 0;
        Debugger.debug(testName + " with size = " + size);
        try {
            nga.setPopSize(size);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                                "SHOULD have been caught, since size = " +
                                size + "\n    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "size = " + size + " violates the precondition");
        }
        Debugger.debug("");

        // trying setPopSize with size = 2
        size = 2;
        Debugger.debug(testName + " with size = " + size);
        try {
            nga.setPopSize(size);
            Debugger.debug("    Exception not caught as expected since " +
                           "size = " + size + " satisfies the precondition");

        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                            "SHOULD NOT have been caught, since size = " +
                            size + "\n    satisfies the precondition");
        }
        Debugger.debug("");

        // ------------------------------------------------------------------

        Debugger.debug(testName = "Testing setCurPop");
        Debugger.debug("    Creating empty population...");
        SetOfChromosomes tempPop = new SetOfChromosomes();
        ArrayList nodeList = genNodeList();

        int numChromo = 4;

        Debugger.debug("    Creating " + numChromo + " chromosomes with " + 
                                "the nodeList and add to the population");
        for(int i = 0; i < numChromo; i++) 
            tempPop.addChromo(nga.initChromo(nodeList, 3, 8));


        Debugger.debug(testName + " with created population");
        try {
            nga.setCurPop(tempPop);
            Debugger.debug("    Exception not caught as expected since " +
                           "each chromosome shares the same nodeList");

        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                            "SHOULD NOT have been caught since each " +
                            "chromosome shares the same nodeList");
        }
        Debugger.debug("");
        
        // print current population size
        if (nga.getPopSize() != numChromo) 
            Debugger.printErr(testName + ":\n    ERROR!!! Population in " +
                            "NetworkGAImp should have size = " + numChromo);
        Debugger.debug("");

        if (nga.getNodeList() != nodeList)
            Debugger.printErr(testName + ":\n    ERROR!!! nodeList in " +
                            "NetworkGAImp was NOT updated");
        else
            Debugger.debug("nodeList in NetworkGAImp is updated");
        Debugger.debug("");

        // add a new member to the population
        tempPop.addChromo(new Chromosome());
                                
        Debugger.debug(testName + " with updated population");
        try {
            nga.setCurPop(tempPop);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                        "SHOULD have been caught since the last " +
                        "chromosome does not share the same nodeList");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since the " +
                        "last chromosome does not share the same nodeList");
        }
        Debugger.debug("");



        // set cur pop with new empty Set
        Debugger.debug(testName + " with an empty population");
        try {
            nga.setCurPop(new SetOfChromosomes());
            Debugger.debug("    Exception not caught as expected since " +
                           "the population is empty");

        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                            "SHOULD NOT have been caught since the " +
                            "population is empty");
        }

        // print current population size
        if (nga.getPopSize() != 0) 
            Debugger.printErr("Testing setCurPop:\n    ERROR!!! Population " +
                "SHOULD be empty, as a new empty SetOfChromosomes was set");

        // ------------------------------------------------------------------

        int numServers, numClients;
        testName = "Testing initPop";
        // trying initPop with numServers = -1, numClients = -1
        numServers = -1;
        numClients = -1;
        Debugger.debug(testName + " with numServers = " + numServers + ", " +
                            "with numClients = " + numClients);
        try {
            nga.initPop(numServers, numClients);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD have been caught, since numServers = " + 
                    numServers + " and numClients = " + numClients + 
                    "\n    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                    "numServers = " + numServers + " and numClients = " +
                    numClients + "\n    violates the precondition");
        }
        Debugger.debug("");

        // trying initPop with numServers = -1, numClients = 10
        numClients = 10;        
        Debugger.debug(testName + " with numServers = " + numServers + ", " +
                            "with numClients = " + numClients);
        try {
            nga.initPop(numServers, numClients);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD have been caught, since numServers = " + 
                    numServers + "\n    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                    "numServers = " + numServers + " violates the " +
                    "precondition");
        }
        Debugger.debug("");

        // trying initPop with numServers =  5, numClients = -1
        numServers = 5;
        numClients = -1;
        Debugger.debug(testName + " with numServers = " + numServers + ", " +
                            "with numClients = " + numClients);
        try {
            nga.initPop(numServers, numClients);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD have been caught, since numClients = " + 
                    numClients + "\n    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                    "numClients = " + numClients + " violates the " +
                    "precondition");
        }
        Debugger.debug("");

        // print current population size
        if (nga.getPopSize() != 0) 
            Debugger.printErr("Testing methods related to population:\n" +
                "    ERROR!!! Population in NetworkGAImp should be empty");

        // ------------------------------------------------------------------
        
        // trying initPop with numServers =  5, numClients = 10
        numServers = 5;
        numClients = 10;
        Debugger.debug(testName + " with numServers = " + numServers + ", " +
                            "with numClients = " + numClients);
        try {
            Debugger.enableTrace(false);
            nga.setCurPop(nga.initPop(numServers, numClients));
            Debugger.enableTrace(true);
            Debugger.debug("    Initialisation completed successfully. " +
                    "(Exception not caught as expected,\n    since " + 
                    "numServers = " + numServers + " and numClients = " + 
                    numClients + " satisfying \n    the precondition)");
        } catch (PreconditionException pe) {
            Debugger.enableTrace(true);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD NOT have been caught, since numServers = " +
                    numServers + " and numClients = " + numClients + 
                    "\n    satisfies the precondition");
        }
        Debugger.debug("");        
        // print current population size
        if (nga.getPopSize() != size) 
            Debugger.printErr(testName + ":\n    ERROR!!! Population in " +
                                "NetworkGAImp should have size = " + size);

        SetOfChromosomes popA = nga.getCurPop();

        Debugger.debug(testName + " with numServers = " + numServers + ", " +
                            "with numClients = " + numClients);
        try {
            Debugger.enableTrace(false);
            nga.setCurPop(nga.initPop(numServers, numClients));
            Debugger.enableTrace(true);
            Debugger.debug("    Initialisation completed successfully. " +
                    "(Exception not caught as expected,\n    since " + 
                    "numServers = " + numServers + " and numClients = " + 
                    numClients + " satisfying \n    the precondition)");
        } catch (PreconditionException pe) {
            Debugger.enableTrace(true);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD NOT have been caught, since numServers = " +
                    numServers + " and numClients = " + numClients + 
                    "\n    satisfies the precondition");
        }
        Debugger.debug("");        
        // print current population size
        if (nga.getPopSize() != size) 
            Debugger.printErr(testName + ":\n    ERROR!!! Population in " +
                                "NetworkGAImp should have size = " + size);

        SetOfChromosomes popB = nga.getCurPop();

        Assertion.asrt( popA.getPopSize() == popB.getPopSize(),
            "Population size are equal", "Population size should be equal");
        for (int i = 0; i < popA.getPopSize(); i++) {
            Debugger.debug("Printing chromosome #" + i + " from popA");
            printChromo(popA.getChromo(i));
            Debugger.debug("Printing chromosome #" + i + " from popB");
            printChromo(popB.getChromo(i));
            Debugger.debug("");
        }        

        Debugger.debug("Testing methods related to population Completed");
        Debugger.debug("===============================================");
    }

}
