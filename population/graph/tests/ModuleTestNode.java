// importing packages
import utility.*;
import population.graph.*; // the class we are testing
import java.util.*;

/**
 * This test suite will test for the functionality of the Node class under 
 * the assumption that Repairable is a fully functional superclass of Node. 
 * This test suite will first check if all the assertion made are enforced by 
 * providing invalid input parameters. Then it should be checked that the 
 * general behaviour of the class is consistent with that in the class 
 * descriptions.
 *
 * The results of the tests will be output to 2 separate files:
 *     -NodeNormal.txt will contain the message if tests are passed
 *     -NodeError.txt will contain the message for erroneous behaviour
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.7
 * @since   0.3.5
 */
public class ModuleTestNode {
    // to store info regarding the current test
    private static String testName;
    private static Random rand = new Random();

    // Default constructor
    /**
     * Should not instantate a test class.
     */
    public ModuleTestNode() {
    	throw new AssertionException("Don't instantiate a test class.");
    }

    /**
     * The main method for this class. The test suite is divided into 3 main
     * components. The first test will check the functionality of the default
     * and standard constructors. The second will validate the behaviour
     * concerned with basic methods of the Node. The last test shall
     * determine the correct behaviour of usage of an Node object.
     */
    public static void main(String[] args) {
        // pipe results to file
        Debugger.pipeResult("NodeImpNormal.txt", "NodeImpError.txt");
        Debugger.enableTrace(true); // enable trace printing

        /**
         * Modified by Andy 07/04/05: to add header for output files for
         * test results.
         */
        Debugger.debug("===========================\n"+
                       "filename: NodeImpNormal.txt\n" +
                       "===========================");
        Debugger.debug("Module Test for population.graph.NodeImp:\n");
        
        Debugger.printErr("==========================\n"+
                          "filename: NodeImpError.txt\n" +
                          "==========================");
        Debugger.printErr("Module Test for population.graph.NodeImp:\n");

        testConstructors();
        Debugger.debug("");        

        testBasics();
        Debugger.debug(""); 
        
        testUsage();
        Debugger.debug("");

        // Modified by Andy 07/04/05: to emphesise end of test
        Debugger.debug("=================================");
        Debugger.debug("Module Test for NodeImp Completed");
        Debugger.debug("=================================");
        Debugger.printErr("=================================");
        Debugger.printErr("Module Test for NodeImp Completed");
        Debugger.printErr("=================================");
    }

    /**
     * Tests the constructors of Node object. This includes one default
     * constructor and three standard constructors. The standard constructors
     * will be tested with a variety of input parameters to ensure all the
     * precondition are checked for.
     */
    private static void testConstructors() {
        Debugger.debug("Testing Constructors\n====================");
        testName = "Testing default constructor";
        
        Node test = new NodeImp();
        checkBasicProperties(test);
        Debugger.debug("");

        testName = "Testing standard constructor1";
        double[] params = {-1.0, 1.0};
        // test with illegal value for initial capacity of the Edge
        Debugger.debug(testName + " with capacity = -1.0");
        try {
            test = new NodeImp( params, new Repairable());
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since capacity = -1.0\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "capacity = -1.0 violates the precondition");
        }
        Debugger.debug("");

        // test with illegal value for efficiency
        Debugger.debug(testName + " with capacity = 1.0, efficiency = 1.5");
        try {
            params = new double[] {1.0, 1.5};
            test = new NodeImp( params, new Repairable());
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught,since efficiency = 1.5\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "efficiency = 1.5 violates the\n    " +
                           "precondition");
        }
        Debugger.debug("");

        // test with illegal value for efficiency
        Debugger.debug(testName + " with capacity = 1.0, efficiency = -0.5");
        try {
            params = new double[] {1.0, -0.5};
            test = new NodeImp( params, new Repairable());
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught,since efficiency = -0.5\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "efficiency = -0.5 violates the\n    " +
                           "precondition");
        }
        Debugger.debug("");
            
        // test constructor with legal values
        Debugger.debug(testName + " with capacity = 1.0, efficiency = 0.5," +
                            " \n    cost = 1.0");
        params = new double[] {1.0, 0.5};
        test = new NodeImp( params, new Repairable());
        checkBasicProperties(test);
            
        Node tempNode = test;
        testName = "Testing standard constructor2";
        // test with illegal label
        Debugger.debug(testName + " with label = Null");
        try {
            test = new NodeImp( null, 10, 10, tempNode);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since label = Null\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "label = Null violates the precondition");
        }
        Debugger.debug("");

        // test with illegal x-coordinates
        Debugger.debug(testName + " with x-coordinate = -10");
        try {
            test = new NodeImp("newNode", -10, 10, tempNode);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since x-coordinate = -10\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "x-coordinate = -10 violates the precondition");
        }
        Debugger.debug("");    
    
        // test with illegal y-coordinates
        Debugger.debug(testName + " with y-coordinate = -10");
        try {
            test = new NodeImp("newNode", 10, -10, tempNode);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since y-coordinate = -10\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "y-coordinate = -10 violates the precondition");
        }
        Debugger.debug("");    

        // test with illegal node (i.e. non-Repairable)
        Debugger.debug(testName + " with non-Repairable nodes");
        try {
            test = new NodeImp("newNode", 10, 10, new NodeTmp());
            Debugger.printErr(testName + ":\n    ClassCastException should" +
                                " have been caught, since the template node"+
                                " \n    for node creation does not have a"  +
                                " superclass being Repairable");
        } catch (ClassCastException ce) {
            Debugger.debug("    ClassCastException caught as expected since"+
                           " the template node for node creation does not"+
                           "\n    have a superclass being Repairable");
        }
        Debugger.debug("");         
    
        // test with legal parameters
        Debugger.debug(testName + " with legal params");
        test = new NodeImp("newNode", 10, 10, tempNode);
        checkBasicProperties(test);        
        Debugger.debug("");


        testName = "Testing standard constructor3";
        params = new double[] {1.0, 1.0, 0.0, 0.0};
        // test with illegal label
        Debugger.debug(testName + " with label = Null");
        try {
            test = new NodeImp(null, 10, 10, 10, params, true);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since label = Null\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "label = Null violates the precondition");
        }
        Debugger.debug("");
        
        // test with illegal x-coordinates
        Debugger.debug(testName + " with x-coordinate = -10");
        try {
            test = new NodeImp("newNode", -10, 10, 10, params, true);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since x-coordinate = -10\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "x-coordinate = -10 violates the precondition");
        }
        Debugger.debug("");    
    
        // test with illegal y-coordinates
        Debugger.debug(testName + " with y-coordinate = -10");
        try {
            test = new NodeImp("newNode", 10, -10, 10, params, true);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since y-coordinate = -10\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "y-coordinate = -10 violates the precondition");
        }
        Debugger.debug("");    
        
        
        // test with illegal repair generations
        Debugger.debug(testName + " with repGens = -10");
        try {
            test = new NodeImp("newNode", 10, 10, -10, params, true);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since repGens = -10\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "repGens = -10 violates the precondition");
        }
        Debugger.debug("");                   
        
        // test with illegal value for initial capacity of the Edge
        Debugger.debug(testName + " with capacity = -1.0");
        params = new double[] {-1.0, 1.0, 0.0, 0.0};
        try {
            test = new NodeImp("newNode", 10, 10, 10, params, true);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since capacity = -1.0\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "capacity = -1.0 violates the precondition");
        }
        Debugger.debug("");
        
        // test with illegal value for efficiency
        Debugger.debug(testName + " with capacity = 1.0, efficiency = 1.5");
        params = new double[] {1.0, 1.5, 0.0, 0.0};
        try {
            test = new NodeImp("newNode", 10, 10, 10, params, true);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught,since efficiency = 1.5\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "efficiency = 1.5 violates the\n    " +
                           "precondition");
        }
        Debugger.debug("");

        // test with legal parameters
        params = new double[] {1.0, 0.5, 0.0, 0.0};
        Debugger.debug(testName + " with legal params");
        test = new NodeImp("newNode", 10, 10, 10, params, true);
        checkBasicProperties(test);        
        Debugger.debug("");

        Debugger.debug("=============================");
        Debugger.debug("Testing Constructors Complete");
        Debugger.debug("=============================");
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

    /**
     * Tests the basic function of the node. Firstly, the accessing and
     * modifying the label and coordinates are checked. Then, assuming the
     * edge implementation is functional up to the methods we call. Then the
     * connectivity of edges to and from the node is verified.
     */
    private static void testBasics() {
        Debugger.debug("Testing Basic Functions\n=======================");
        
        Debugger.debug("Preparing test node...");
        //prepare test node
        Node tmpNode = new NodeImp();
        Node testNode = new NodeImp("testNode", 10, 10, tmpNode);
        
        // testing getLabel() and setLabel()
        Debugger.debug("\nRetrieving label variable:");
        testNode.getLabel();
        
        testName = "Setting label variable to null ";
        Debugger.debug(testName);
        try {   // try setting a null label
            testNode.setLabel(null);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since label = null\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "label = null violates the\n    precondition");
        }      
          
        Debugger.debug("Setting label variable to something not null:");
        testNode.setLabel("newTestNode");
        Debugger.debug("Retrieving label variable:");
        testNode.getLabel();
        
        // testing getCoordinates() and setCoordinates()
        Debugger.debug("\nRetrieving node coordinates");
        testNode.getCoordinates();
        
        testName = "Setting the x-coordinate to a negative value";
        Debugger.debug(testName);
        try {   // try setting a negative x-coordinate
            testNode.setCoordinates(-10, 10);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since x-coord = -10\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "x-coord = -10 violates the\n    precondition");
        }
        
        testName = "Setting the y-coordinate to a negative value";
        Debugger.debug(testName);
        try {   // try setting a negative y-coordinate
            testNode.setCoordinates(10, -10);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since y-coord = -10\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "y-coord = -10 violates the\n    precondition");
        }
        
        testName = "Setting the coordinates to positive values";
        Debugger.debug(testName);
        try {   // try setting a negative y-coordinate
            testNode.setCoordinates(100, 100);
            Debugger.debug("\n    Exception not caught as expected, since " +
                            "coordinates of (100,100) do not \n    violate " +
                            "the precondition");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    Exception should not have" +
                                " been caught as coordinates of (100,100)" +
                                "\n    do not violate the precondition");
        }
        
        // Testing edge connectivity functions 
        assessNode(testNode);
        
        Debugger.debug("Testing Edge connectivity...\n");
        Debugger.debug("Preparing some nodes...\n");
        Debugger.enableTrace(false);        
        Node server1 = new NodeImp("SERVER", Math.abs(rand.nextInt()), 
                                        Math.abs(rand.nextInt()), testNode);
        Node server2 = new NodeImp("SERVER", Math.abs(rand.nextInt()), 
                                        Math.abs(rand.nextInt()), testNode);
        Node server3 = new NodeImp("SERVER", Math.abs(rand.nextInt()), 
                                        Math.abs(rand.nextInt()), testNode);
        Node client1 = new NodeImp("CLIENT", Math.abs(rand.nextInt()), 
                                        Math.abs(rand.nextInt()), testNode);
        Node client2 = new NodeImp("CLIENT", Math.abs(rand.nextInt()), 
                                        Math.abs(rand.nextInt()), testNode);
        Node client3 = new NodeImp("CLIENT", Math.abs(rand.nextInt()), 
                                        Math.abs(rand.nextInt()), testNode);
        Node client4 = new NodeImp("CLIENT", Math.abs(rand.nextInt()), 
                                        Math.abs(rand.nextInt()), testNode);
        Node client5 = new NodeImp("CLIENT", Math.abs(rand.nextInt()), 
                                        Math.abs(rand.nextInt()), testNode);
        Debugger.enableTrace(true);
        Debugger.debug("Preparing some edges...\n");
        Debugger.enableTrace(false);        
                
        // We now simulate how edged would be connected between nodes 
        Edge tmpEdge = new EdgeImp();
        Edge inEdge1 = new EdgeImp(server1, testNode,
                                                "SERVER-CLIENT", tmpEdge);
        Edge inEdge2 = new EdgeImp(client1, testNode,
                                                "CLIENT-CLIENT", tmpEdge);
        Edge inEdge3 = new EdgeImp(client2, testNode,
                                                "CLIENT-CLIENT", tmpEdge);
        Edge inEdgeBad = new EdgeImp(client2, server1,
                                                "CLIENT-SERVER", tmpEdge);
                                                
        Edge outEdge1 = new EdgeImp(testNode, server2,
                                                "CLIENT-SERVER", tmpEdge);
        Edge outEdge2 = new EdgeImp(testNode, client3,
                                                "CLIENT-CLIENT", tmpEdge);
        Edge outEdge3 = new EdgeImp(testNode, client4,
                                                "CLIENT-CLIENT", tmpEdge);
        Edge outEdge4 = new EdgeImp(testNode, server3,
                                                "CLIENT-SERVER", tmpEdge);
        Edge outEdge5 = new EdgeImp(testNode, client5,
                                                "CLIENT-CLIENT", tmpEdge);
        Edge outEdgeBad = new EdgeImp(server1, client5,
                                                "SERVER-CLIENT", tmpEdge);        
        Debugger.enableTrace(true);
        
        // Adding a proper incoming server-client edge to this node
        try {
            testNode.addInEdge(inEdge1);
            Debugger.debug("No exception caught as expected, as the edge " +
                                "terminates at this node");
        } catch (PreconditionException pe) {
            Debugger.printErr("Exception shouldn't be caught, as the edge " +
                                "terminates at this node");   
        } 
           
        assessNode(testNode);
        
        // Adding a bad incoming client-server edge to this node
        try {
            testNode.addInEdge(inEdgeBad);
            Debugger.printErr("Exception should have been caught, as the " +
                                " edge does not terminate at this node");
        } catch (PreconditionException pe) {
            Debugger.debug("Exception caught as expected, as the edge does " +
                                "not terminate at this node");   
        }
        
        assessNode(testNode);
        
        // Adding a proper incoming client-client edge to this node
        try {
            testNode.addInEdge(inEdge2);
            Debugger.debug("No exception caught as expected, as the edge " +
                                "terminates at this node");
        } catch (PreconditionException pe) {
            Debugger.printErr("Exception shouldn't be caught, as the edge " +
                                "terminates at this node");   
        }
        
        // Adding a proper incoming client-client edge to this node
        try{
            testNode.addInEdge(inEdge3);
            Debugger.debug("No exception caught as expected, as the edge " +
                                "terminates at this node");
        } catch (PreconditionException pe) {
            Debugger.printErr("Exception shouldn't be caught, as the edge " +
                                "terminates at this node");   
        } 
        assessNode(testNode);

        // Adding a proper outgoing client-server edge to this node
        try {
            testNode.addOutEdge(outEdge1);
            Debugger.debug("No exception caught as expected, as the edge " +
                                "originates from this node");
        } catch (PreconditionException pe) {
            Debugger.printErr("Exception shouldn't be caught, as the edge " +
                                "originates from this node");   
        }
        
        // Adding a proper outgoing client-client edge to this node
        try {
            testNode.addOutEdge(outEdge2);
            Debugger.debug("No exception caught as expected, as the edge " +
                                "originates from this node");
        } catch (PreconditionException pe) {
            Debugger.printErr("Exception shouldn't be caught, as the edge " +
                                "originates from this node");   
        }
        
        assessNode(testNode);
        
        // Adding a bad outgoing server-client edge to this node
        try {
            testNode.addOutEdge(outEdgeBad);
            Debugger.printErr("Exception should have been caught, as the " +
                                " edge does not originate from this node");
        } catch (PreconditionException pe) {
            Debugger.debug("Exception caught as expected, as the edge does " +
                                "not originate from this node");   
        }
        
        assessNode(testNode);
        
        // Adding a proper outgoing client-client edge to this node
        try {
            testNode.addOutEdge(outEdge3);
            Debugger.debug("No exception caught as expected, as the edge " +
                                "originates from this node");
        } catch (PreconditionException pe) {
            Debugger.printErr("Exception shouldn't be caught, as the edge " +
                                "originates from this node");   
        }
        // Adding a proper outgoing client-server edge to this node
        try {
            testNode.addOutEdge(outEdge4);
            Debugger.debug("No exception caught as expected, as the edge " +
                                "originates from this node");
        } catch (PreconditionException pe) {
            Debugger.printErr("Exception shouldn't be caught, as the edge " +
                                "originates from this node");   
        }
        
        // Adding a proper outgoing client-client edge to this node
        try {
            testNode.addOutEdge(outEdge5);
            Debugger.debug("No exception caught as expected, as the edge " +
                                "originates from this node");
        } catch (PreconditionException pe) {
            Debugger.printErr("Exception shouldn't be caught, as the edge " +
                                "originates from this node");   
        }
        
        assessNode(testNode);

        // testing getTraffic() and setTraffic()
        Debugger.debug("\nRetrieving Traffic variable:");
        testNode.getTraffic();
        
        testName = "Setting Traffic variable to -100 ";
        Debugger.debug(testName);
        try {   // try setting a null label
            testNode.setTraffic(-100);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since traffic = -100\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "traffic = -100 violates the\n    precondition");
        }      
          
        Debugger.debug("Setting Traffic variable to something not negative:");
        testNode.setTraffic(100);
        Debugger.debug("Retrieving Traffic variable:");
        testNode.getTraffic();
        
        Debugger.debug("");                        

        Debugger.debug("================================");
        Debugger.debug("Testing Basic Functions Complete");
        Debugger.debug("================================");
    }


    /**
     * Obtains the in/out degree and link status of the node. A nodes in/out
     * EdgeLists will also be assessed
     * @param   n The node whose status will be assessed.
     */ 
    public static void assessNode(Node n) {
        Debugger.debug("Checking node");      
        n.inDegree();
        n.outDegree();
        List inEdgeList = n.inEdgeList();
        List outEdgeList = n.outEdgeList();
        
        if(inEdgeList.size() != 0)
            for(int i = 0; i < inEdgeList.size(); i++) {
                Debugger.debug("Incoming Node " + i + ": ");
                ((Edge) inEdgeList.get(i)).getLabel();
            }
        else Debugger.debug("The inEdgeList is empty.");

        if(outEdgeList.size() != 0)
            for(int i = 0; i < outEdgeList.size(); i++) {
                Debugger.debug("Outgoing Node " + i + ": ");
                ((Edge) outEdgeList.get(i)).getLabel();
            }
        else Debugger.debug("The outEdgeList is empty.");
        n.getStats();        
    }

    /**
     * Tests the methods which concerns the Usage in NodeImp. The methods
     * tested include increasing and decreasing usage, and accessing some
     * related variables.
     *
     * Modified by Andy 08/04/05:
     *      1. vary usage as a percentage.
     *      2. call methods that were supposed to be in Usable.
     */
    private static void testUsage() {
        Debugger.debug("Testing Usage Functions\n=======================");
        
        //prepare 4 nodes to be used
        Debugger.debug("Preparing test node...\n");
        double[] params = {1000000, 0.0, 0.3, 0.5};
        NodeImp testNode = new NodeImp("SERVER", Math.abs(rand.nextInt()), 
                                Math.abs(rand.nextInt()), 10, params, true);
        
        Debugger.debug("");

        Debugger.debug("Attempt to increase usage");
        Debugger.debug("    Try increasing by a negative amount");
        try {
            testNode.increUsage(-0.001);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since the negative amount\n" +
                                "    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                    "the negative amount violates the\n    precondition");
        }

        Debugger.debug("Attempt to increase usage");
        Debugger.debug("    Try increasing by 1.001");
        try {
            testNode.increUsage(1.001);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since the amount > 1.0\n" +
                                "    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                    "the amount > 1.0 violates the\n    precondition");
        }
       
        if(testNode.increUsage(0.001)) Debugger.debug("Success");
        else Debugger.debug("Fail");
        if(testNode.getEfficiency() != 0.001)
            Debugger.printErr("Error: increUsage");
        checkStat(testNode);
        Debugger.debug("");
  
        if(testNode.increUsage(0.99)) Debugger.debug("Success");
        else Debugger.debug("Fail");
        if(testNode.getEfficiency() != 0.991)
            Debugger.printErr("Error: increUsage");
        checkStat(testNode);
        Debugger.debug("");
        
        checkStat(testNode);
        
        if(testNode.increUsage(0.009)) Debugger.debug("Success");
        else Debugger.debug("Fail");
        if(testNode.getEfficiency() != 1.0)
            Debugger.printErr("Error: increUsage");
        checkStat(testNode);
        Debugger.debug("");
            
        if(testNode.increUsage(0.001)) Debugger.debug("Success");
        else Debugger.debug("Fail");
        if(testNode.getEfficiency() != 1.0)
        Debugger.printErr("Error: increUsage");
        Debugger.debug("");
        
        Debugger.debug("Attempt to decrease usage");
        Debugger.debug("    Try decreasing by a negative amount");
        try {
            testNode.decreUsage(-0.001);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since the negative amount\n" +
                                "    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                    "the negative amount violates the\n    precondition");
        }

        Debugger.debug("Attempt to decrease usage");
        Debugger.debug("    Try decreasing by an amount greater than 1.0");
        try {
            testNode.decreUsage(1.001);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since the amount > 1.0\n" +
                                "    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                    "the amount > 1.0 violates the\n    precondition");
        }
               
        testNode.decreUsage(0.105);
        if(testNode.getEfficiency() != 0.895) 
            Debugger.printErr("Error: decreUsage");
        checkStat(testNode);
        Debugger.debug("");
          
        testNode.decreUsage(0.495);
        if(testNode.getEfficiency() != 0.400) 
            Debugger.printErr("Error: decreUsage");
        checkStat(testNode);
        Debugger.debug("");
        
        
        Debugger.debug("Resetting usage");
        testNode.resetUsage();
        if(testNode.getEfficiency() != 0) Debugger.printErr("Error: resetUsage");
        checkStat(testNode);
        Debugger.debug("");
            
        testNode.decreUsage(0.001);
        if(testNode.getEfficiency() != 0) Debugger.printErr("Error: decreUsage");
        Debugger.debug("");

        checkStat(testNode);
        
        Debugger.debug("================================");
        Debugger.debug("Testing Usage Functions Complete");
        Debugger.debug("================================");        
    }

    /**
     * Queries an edge object for its basic usage properties.
     * @param u The edge in the form of an usable object to be checked
     */
     private static void checkStat(NodeImp u) {
        Debugger.debug("Checking node statistics...");
        u.getLoad();
    }

    /**
     * A copy of the original nodeImp which does not extend Repairable.
     */

    public static class NodeTmp implements Node {
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
         * element is the X-coordinate and the second is the Y-coordinate. 
         * This is initilised to (0,0)
         */
        private int[] coord = new int[2];
        /**
         * an array to store the number of connections to different types of
         * nodes. The structure of the array are as follows:
         * {Outgoing connection to Servers, Incoming connection from Servers, 
         *  Outgoing connection to Clients, Incoming connection from clients}
         */ 
        private int[] stats = new int[4];       
    
        /* the list of incoming and outgoing edges stored for efficient
         * traversal
         */
        ArrayList incoming, outgoing;
    
        // Default Constructor
        /**
         * Creates a default instance of NodeImp. A Node created using this
         * default constructor is non-functional and cannot be used
         * immediately. This is because it would have the properties of 
         * non-repairable and non-usable objects.<p>
         *
         * That is, extremely high repair generation requirement, probablity 
         * of failure of 1.00 and  maximum allowable probablity of failure
         * (threshold) of 0%. Also, this node's capacity and operating
         * efficiency are both 0.<p>
         *
         * Thus the above mentioned properties must be altered before using 
         * this node. In addition, the coordinates of this node must be
         * change.
         * @see Repairable
         */
        public NodeTmp() {
            super();            // call constructor of super class
            label      = "";    // empty label
            capacity   = 0.00;  // 0 capacity
            efficiency = 0.00;  // operating at 0% efficiency
        }
           
        /**
         * implmentation of Node Interface
         */
    
        public String getLabel() throws PostconditionException {
            return "";
        }
    
        public void setLabel(String l) throws PreconditionException {
        }
    
        public int[] getCoordinates() throws PostconditionException {
            return coord;
        }
    
        public void setCoordinates(int x, int y)
        throws PreconditionException {
        }    
        
        public int inDegree() throws PostconditionException {
            return 0;
        }
        
        public int outDegree() throws PostconditionException {
            return 0;
        }
        public List inEdgeList() throws PostconditionException {
            return incoming;
        }
        
        public List outEdgeList() throws PostconditionException {
            return outgoing;
        }
        
        public void addInEdge(Edge e) throws PreconditionException {
        }
        
        public void addOutEdge(Edge e) throws PreconditionException {
        }
        
        public double getTraffic() throws PostconditionException {
            return 0.0;
        }
        public void setTraffic(double traf) throws PreconditionException {
        }
    
        public int[] getStats() throws PostconditionException {
            return stats;
        }
    
        public double getMaxCapacity() throws PostconditionException {
            return 0.0;
        }

        public boolean setMaxCapacity(double cap) {
            return false;
        }
    
        public double getEfficiency() throws PostconditionException {
            return 0.0;
        }
        
        public boolean setEfficiency(double eff)
        throws PreconditionException {
            return false;    
        }
    
        public double getUsage() throws PostconditionException {
            return 0.0;
        }
        
        public boolean increUsage(double amount)
        throws PreconditionException {
            return false;
        }  

        public boolean decreUsage(double amount)
        throws PreconditionException {
            return false;
        }
        
        public void resetUsage() {
        }
        
        public double getAvailCap() throws PostconditionException {
            return 0.0;        
        }
        
        public double getUtilisation() throws PostconditionException {
            return 0.0;        
        }        
                
        public double getLoad() throws PostconditionException {
            return 0.0;          
        }
    }
}
