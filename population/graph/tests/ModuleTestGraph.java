// importing packages
import utility.*;
import population.graph.*; // the class we are testing
import java.util.*;

/**
 * This test suite will test for the functionality of the GraphImp class under
 * the assumption that Node and Edge implementations are fully functional.
 * This test suite will first check if all the assertion made are enforced by
 * providing invalid input parameters. Then it should be checked that the
 * general behaviour of the class is consistent with that in the class
 * descriptions.
 *
 * The results of the tests will be output to 2 separate files:
 *     -GraphNormal.txt will contain the message if tests are passed
 *     -GraphError.txt will contain the message for erroneous behaviour
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.6
 * @since   0.3.6
 */
public class ModuleTestGraph {
    // to store info regarding the current test
    private static String testName;
    private static Random rand = new Random();

    // Default constructor
    /**
     * Should not instantate a test class.
     */
    public ModuleTestGraph() {
    	throw new AssertionException("Don't instantiate a test class.");
    }

    /**
     * The main method for this class. The test suite is divided into 3 main
     * components. The first test will check the functionality of the default
     * and standard constructors. The second will validate the behaviour
     * concerned with basic methods of the edge. The last test shall
     * determine the correct behaviour of GraphStat of an GraphImp object.
     */
    public static void main(String[] args) {
        // pipe results to file
        Debugger.pipeResult("GraphImpNormal.txt", "GraphImpError.txt");
        Debugger.enableTrace(true); // enable trace printing

        /**
         * Modified by Andy 07/04/05: to add header for output files for
         * test results.
         */
        Debugger.debug("\n============================\n"+
                       "filename: GraphImpNormal.txt\n" +
                       "============================");
        Debugger.debug("Module Test for population.graph.GraphImp:\n");
        
        Debugger.printErr("\n===========================\n"+
                          "filename: GraphImpError.txt\n" +
                          "===========================");
        Debugger.printErr("Module Test for population.graph.GraphImp:\n");

        testConstructors();
        Debugger.debug("");

        testBasics();
        Debugger.debug("");

        testGraphStat();
        Debugger.debug("");
        
        Debugger.enableTrace(true);
        // Modified by Andy 07/04/05: to emphisise end of test
        Debugger.debug("==================================");
        Debugger.debug("Module Test for GraphImp Completed");
        Debugger.debug("==================================");
        Debugger.printErr("==================================");
        Debugger.printErr("Module Test for GraphImp Completed");
        Debugger.printErr("==================================");
    }

    /**
     * Tests the constructors of Node object. This includes one default
     * constructor and one standard constructors. The standard constructor
     * will be tested with a variety of input parameters to ensure all the
     * precondition are checked for.
     */
    private static void testConstructors() {
        Debugger.debug("Testing Constructors\n====================");

        testName = "Testing default constructor";
        Graph test = new GraphImp();
        checkBasicProperties(test);
        Debugger.debug("");

        testName = "Testing standard constructor";
        // test with illegal value for maximum X size of the graph
        Debugger.debug(testName + " with maxX = -512");
        try {
            test = new GraphImp( -512, 512);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since maxX = -512\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "maxX = -512 violates\n    the precondition");
        }
        Debugger.debug("");

        // test with illegal value for maximum Y size of the graph
        Debugger.debug(testName + " with maxY = -512");
        try {
            test = new GraphImp( 512, -512);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since maxY = -512\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "maxY = -512 violates\n    the precondition");
        }
        Debugger.debug("");

        // test with legal value for maximum X & Y size of the graph
        Debugger.debug(testName + " with maxX = maxY = 512");
        try {
            test = new GraphImp( 512, 512);
            Debugger.debug("    Exception not caught as expected, since " +
                                "maxX = 512, maxY = 512\n    does not " +
                                "violate the precondition");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    Exception should have not " +
                                "been caught as maxX = 512, maxY = 512 " +
                                "\n    does not violate the precondition");
        }
        Debugger.debug("");
        checkBasicProperties(test);

        Debugger.debug("=============================");
        Debugger.debug("Testing Constructors Complete");
        Debugger.debug("=============================");
    }


    /**
     * Queries a graph object for its basic properties.
     * @param g The Graph to be checked
     */
    private static void checkBasicProperties(Graph g) {
        Debugger.debug("    Checking basic properties:");
        try {
            int numberNodes = g.numNodes();
            Debugger.debug("The number of servers in the graph is " +
                                ((GraphStat)g).numServers());
            Debugger.debug("The number of clients in the graph is " +
                                ((GraphStat)g).numClients());
            int numberEdges = g.numEdges();
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + ":\n    This instance of graph "+
                                                "has invalid properties.");
        }
    }

    /**
     * Tests the basic function of the Graph. Firstly, the accessing or the
     * existence of nodes and edges connecting nodes are checked. Here, we 
     * assume that the node and edge implementations are functional up to the
     * methods we call. The most important part of this part is to be able to
     * iterate through the edge lists.
     */
    private static void testBasics() {
        Debugger.debug("Testing Basic Functions\n=======================");

        Debugger.debug("Creating 7 nodes (3 server and 4 clients)...");
        Debugger.enableTrace(false);
        //prepare test nodes
        Node tmpNode = new NodeImp();
        Node node01 = new NodeImp("CLIENT", 130, 160, tmpNode);
        Node node02 = new NodeImp("SERVER", 210, 210, tmpNode);
        Node node03 = new NodeImp("SERVER", 130,  70, tmpNode);
        Node node04 = new NodeImp("SERVER",  70, 120, tmpNode);
        Node node05 = new NodeImp("CLIENT",  60,  30, tmpNode);
        Node node06 = new NodeImp("CLIENT", 130,  10, tmpNode);
        Node node07 = new NodeImp("CLIENT", 130,  10, tmpNode);
        Debugger.enableTrace(true);

        Debugger.debug("Preparing test 5 edges (2SS+1SC+2CS)...");
        Debugger.enableTrace(false);
        //prepare test edges
        Edge tmpEdge = new EdgeImp();
        Edge edge01 = new EdgeImp(node03, node04, "SERVER-SERVER", tmpEdge);
        Edge edge02 = new EdgeImp(node03, node05, "SERVER-CLIENT", tmpEdge);
        Edge edge03 = new EdgeImp(node05, node03, "CLIENT-SERVER", tmpEdge);
        Edge edge04 = new EdgeImp(node06, node04, "CLIENT-SERVER", tmpEdge);
        Edge edge05 = new EdgeImp(node04, node03, "SERVER-SERVER", tmpEdge);
        Edge edge06 = new EdgeImp(node03, node01, "SERVER-CLIENT", tmpEdge);
        Edge edge07 = new EdgeImp(node01, node03, "CLIENT-SERVER", tmpEdge);
        Debugger.enableTrace(true);
        Debugger.debug("These 7 nodes and 7 edges will be added as the " +
                            "the test suite progresses...");

        Graph g = new GraphImp( 256, 256);
        checkBasicProperties(g);

        Debugger.debug("Adding \"node01\":");
        g.addNode(node01);
        Debugger.debug("Adding \"node02\":");
        g.addNode(node02);
        Debugger.debug("Adding \"node03\":");
        g.addNode(node03);
        Debugger.debug("Checking stats for \"node03\":");
        checkStat((NodeImp)node03);
        Debugger.debug("Adding \"node04\":");
        g.addNode(node04);
        Debugger.debug("Adding \"node05\":");
        g.addNode(node05);
        if (g.hasNode(node01)) Debugger.debug("\"node01\" is in the graph");
        else Debugger.printErr("addNode didn't add \"node01\" to the graph");
        if (g.hasNode(node02)) Debugger.debug("\"node02\" is in the graph");
        else Debugger.printErr("addNode didn't add \"node03\" to the graph");
        if (g.hasNode(node03)) Debugger.debug("\"node03\" is in the graph");
        else Debugger.printErr("addNode didn't add \"node03\" to the graph");
        if (g.hasNode(node04)) Debugger.debug("\"node04\" is in the graph");
        else Debugger.printErr("addNode didn't add \"node04\" to the graph");
        if (g.hasNode(node05)) Debugger.debug("\"node05\" is in the graph");
        else Debugger.printErr("addNode didn't add \"node05\" to the graph");


        Debugger.debug("Adding \"edge01\":");
        g.addEdge(edge01);
        Debugger.debug("Adding \"edge02\":");
        g.addEdge(edge02);
        Debugger.debug("Adding \"edge03\" which terminates at node03:");
        g.addEdge(edge03);
        Debugger.debug("Checking stats for \"node03\":");
        checkStat((NodeImp)node03);
        
        if (g.hasEdge(edge01)) Debugger.debug("\"edge01\" is in the graph");
        else Debugger.printErr("addNode didn't add \"edge01\" to the graph");
        if (g.hasEdge(edge02)) Debugger.debug("\"edge02\" is in the graph");
        else Debugger.printErr("addNode didn't add \"edge03\" to the graph");
        if (g.hasEdge(edge03)) Debugger.debug("\"edge03\" is in the graph");
        else Debugger.printErr("addNode didn't add \"edge03\" to the graph");

        checkBasicProperties(g);

        Debugger.debug(testName = "Testing hasEdge(Node from, Node to):");
        try {
            Debugger.debug("There is an edge from \"node06\" to \"node03\""+
                            ": " + g.hasEdge(node06, node03));
            Debugger.printErr(testName + ":\n    Exception should have " +
                            "been caught, since from node \"node06\" is " +
                            "not in the graph violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since from " +
                            "node node06 is not \n    in the graph " +
                            "violating the precondition");
        }
        Debugger.debug("");

        try {
            Debugger.debug("There is an edge from \"node01\" to \"node07\""+
                            ": " + g.hasEdge(node01, node07));
            Debugger.printErr(testName + ":\n    Exception should have " +
                            "been caught, since to node \"node07\" is " +
                            "not in the graph violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since to " +
                            "node07 is not \n    in the graph " +
                            "violating the precondition");
        }
        Debugger.debug("");

        try {
            Debugger.debug("There is an edge from \"node01\" to \"node04\""+
                            ": " + g.hasEdge(node01, node04));
            Debugger.debug("    Exception not caught as expected, since " +
                        "\"node01\" and \"node04\" are in\n    the graph " +
                        "satisfying the precondition");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    Exception should not be " +
                        "thrown, since \"node01\" and \"node04\" are\n    " +
                        "in the graph satisfying the precondition");
        }
        Debugger.debug("");

        try {
            Debugger.debug("There is an edge from \"node03\" to \"node05\"" +
                            ": " + g.hasEdge(node03, node05));
            Debugger.debug("    Exception not caught as expected, since " +
                        "\"node03\" and \"node05\" are in\n    the graph " +
                        "satisfying the precondition");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    Exception should not be " +
                        "thrown, since \"node03\" and \"node05\" are\n    " +
                        "in the graph satisfying the precondition");
        }
        Debugger.debug("");

        Debugger.debug("Testing nodeId(Node n):");
        Debugger.debug("   Obtaining nodeId for \"node01\"");
        try {
            g.nodeId(node01);
            Debugger.debug("    Exception not caught as expected, since " +
                        "\"node01\" is in the graph\n    satisfying the" +
                        "precondition");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    Exception should not be " +
                        "thrown, since \"node01\" is in the graph " +
                        "\n   satisfying the precondition");
        }
        Debugger.debug("");

        Debugger.debug("   Obtaining nodeId for \"node06\"");
        try {
            g.nodeId(node06);
            Debugger.printErr(testName + ":\n    Exception should have " +
                        " been caught, since \"node06\" is not in the" +
                        "graph\n    violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected, since " +
                        "\"node06\" is not in the graph\n    violating " +
                        " the precondition");
        }
        Debugger.debug("");

        Debugger.debug("Testing edgeId(Edge e):");
        Debugger.debug("    Obtaining nodeId for \"edge01\"");
        try {
            g.edgeId(edge01);
            Debugger.debug("    Exception not caught as expected, since " +
                        "\"edge01\" is in the graph\n    satisfying the" +
                        "precondition");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    Exception should not be " +
                        "thrown, since \"edge01\" is in the graph " +
                        "\n   satisfying the precondition");
        }
        Debugger.debug("");

        Debugger.debug("    Obtaining edgeId for \"edge06\"");
        try {
            g.edgeId(edge06);
            Debugger.printErr(testName + ":\n    Exception should have " +
                        "been caught, since \"edge06\" is not in the" +
                        "graph\n    violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected, since " +
                        "\"edge06\" is not in the graph\n    violating " +
                        " the precondition");
        }
        Debugger.debug("");

        Debugger.debug("Testing getNode(int id):");
        Debugger.debug(testName = "Attempting to getNode with id = -1");
        if(g.getNode(-1) != null)
            Debugger.printErr(testName + ": There should be no Node with " +
                                "the id of -1");
        Debugger.debug(testName = "Attempting to getNode with id = 1");
        if(g.getNode(1) == null)
            Debugger.printErr(testName + ": There should be a Node with " +
                                "the id of 1, since there are " + 
                                g.numNodes() + " of nodes");
        Debugger.debug(testName = "Attempting to getNode with id = 10");
        if(g.getNode(10) != null)
            Debugger.printErr(testName + ": There should be no Node with " +
                                "the id of 10, since there are only " +
                                g.numNodes() + " of nodes");

        Debugger.debug("Testing inDegree(Node n)");
        Debugger.debug(testName = "Obtaining the inDegree of \"node06\"");
        try {
            g.inDegree(node06);
            Debugger.printErr(testName + ":\n    Exception should have " +
                        "been caught, since \"node06\" is not in the" +
                        "graph\n    violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected, since " +
                        "\"node06\" is not in the graph\n    violating " +
                        " the precondition");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Obtaining the inDegree of \"node04\"");
        try {
            g.inDegree(node04);
            Debugger.debug("    Exception not caught as expected, since " +
                        "\"node04\" is in the graph\n    satisfying the" +
                        "precondition");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    Exception should not be " +
                        "thrown, since \"node04\" is in the graph " +
                        "\n   satisfying the precondition");
        }
        Debugger.debug("");

        Debugger.debug("Adding \"node06\":");
        g.addNode(node06);
        Debugger.debug("Adding \"edge04\", which terminates at \"node04\":");
        g.addEdge(edge04);

        Debugger.debug(testName = "Obtaining the inDegree of \"node04\"");
        try {
            g.inDegree(node04);
            Debugger.debug("    Exception not caught as expected, since " +
                        "\"node04\" is in the graph\n    satisfying the" +
                        "precondition");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    Exception should not be " +
                        "thrown, since \"node04\" is in the graph " +
                        "\n   satisfying the precondition");
        }
        Debugger.debug("");

        Debugger.debug("Testing outDegree(Node n)");
        Debugger.debug(testName = "Obtaining the inDegree of \"node07\"");
        try {
            g.outDegree(node07);
            Debugger.printErr(testName + ":\n    Exception should have " +
                        "been caught, since \"node07\" is not in the" +
                        "graph\n    violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected, since " +
                        "\"node07\" is not in the graph\n    violating " +
                        " the precondition");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Obtaining the outDegree of \"node04\"");
        try {
            g.outDegree(node04);
            Debugger.debug("    Exception not caught as expected, since " +
                        "\"node04\" is in the graph\n    satisfying the" +
                        "precondition");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    Exception should not be " +
                        "thrown, since \"node04\" is in the graph " +
                        "\n   satisfying the precondition");
        }
        Debugger.debug("");

        Debugger.debug("Adding \"edge05\", which originates from " +
                        "\"node04\" and terminates at \"node03\":");
        g.addEdge(edge05);
        Debugger.debug("Checking stats for \"node03\":");
        checkStat((NodeImp)node03);

        Debugger.debug(testName = "Obtaining the outDegree of \"node04\"");
        try {
            g.outDegree(node04);
            Debugger.debug("    Exception not caught as expected, since " +
                        "\"node04\" is in the graph\n    satisfying the" +
                        "precondition");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    Exception should not be " +
                        "thrown, since \"node04\" is in the graph " +
                        "\n   satisfying the precondition");
        }
        Debugger.debug("");

        Debugger.debug("Testing getEdge(int id):");
        Debugger.debug(testName = "Attempting to getEdge with id = -1");
        if(g.getEdge(-1) != null)
            Debugger.printErr(testName + ": There should be no Edge with " +
                                "the id of -1");
        Debugger.debug(testName = "Attempting to getEdge with id = 1");
        if(g.getEdge(1) == null)
            Debugger.printErr(testName + ": There should be a Edge with " +
                                "the id of -1, since there are " + 
                                g.numEdges() + " of edges");
        Debugger.debug(testName = "Attempting to getEdge with id = 10");
        if(g.getEdge(10) != null)
            Debugger.printErr(testName + ": There should be no Edge with " +
                                "the id of 10, since there are only " +
                                g.numEdges() + " of edges");

        Debugger.debug(testName = "Testing getEdge(node07, node03):");
        try {
            g.getEdge(node07, node03);
            Debugger.printErr(testName + ":\n    Exception should have " +
                            "been caught, since from node \"node07\" is " +
                            "not in the graph violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since from " +
                            "node node07 is not \n    in the graph " +
                            "violating the precondition");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing getEdge(node01, node07):");
        try {
            g.getEdge(node01, node07);
            Debugger.printErr(testName + ":\n    Exception should have " +
                            "been caught, since to node \"node07\" is " +
                            "not in the graph violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since to " +
                            "node node07 is not \n    in the graph " +
                            "violating the precondition");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing getEdge(node01, node04):");
        try {
            Edge temp = g.getEdge(node01, node04);
            Debugger.debug("    PreconditionException not caught as " +
                        "expected, since both \"node01\" and \"node04\"" +
                        "\n    are in the graph.");
            if (temp != null) Debugger.printErr(testName + ": Null should " +
                            "be returned, since there is no edges between " +
                            "\"node01\" and \"node04\".");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    PreconditionException " +
                        "should not be thrown, since both \"node01\"" +
                        "and \"node04\" \n    are in the graph satisfying " +
                        "the precondition");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing getEdge(node03, node05):");
        try {
            Edge temp = g.getEdge(node03, node05);
            Debugger.debug("    PreconditionException not caught as " +
                        "expected, since both \"node03\" and \"node05\"" +
                        "\n    is in the graph.");
            if (temp == null) Debugger.printErr(testName + " Null should " +
                            "not be returned, since there is an edge " +
                            "between \"node01\" and \"node04\".");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + "\n    PreconditionException " +
                        "should not be thrown, since both \"node03\"" +
                        "and \"node05\" \n    are in the graph satisfying " +
                        "the precondition");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing firstNode():");
        Node tempNode = null;
        try {
            tempNode = g.firstNode();
            if ( (tempNode) == null && g.numNodes() != 0)
                Debugger.printErr(testName + ":\n    Null is returned, but " +
                            " the graph is not empty");
        } catch (PostconditionException pe) {
            Debugger.printErr("    Either the returned node is not in the " +
                                "graph, or Null is returned but the graph" +
                                "is not empty");
        }
        Debugger.debug("");

        Debugger.debug(testName = ("Testing nextNode(node07) [node07 not " +
                                    " in graph:"));
        try {
            g.nextNode(node07);
            Debugger.printErr(testName + "\n    Exception should have " +
                            "been caught, since node \"node07\" is not in " +
                            "the graph violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                            "\"node07\" is not \n    in the graph " +
                            "violating the precondition");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing nextNode(node02):");
        try {
            tempNode = g.nextNode(node02);
            if (tempNode != node03)
                Debugger.printErr(testName + "\n    Returned node is not " +
                            "the next node of \"node02\"");
        } catch (PostconditionException pe) {
            Debugger.printErr("    Either the returned node is not in the " +
                                "graph, or Null is returned but the " +
                                "parameter node is not the last node");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing nextNode(node06):");
        try {
            tempNode = g.nextNode(node06);
            if (tempNode != null)
                Debugger.printErr(testName + "\n    Returned node is not " +
                            "null, but \"node06\" is the last node");
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + "\n    A node is returned but " +
                                "\"node06\" is the last node");
        }
        Debugger.debug("");

        Edge tempEdge = null;
        Debugger.debug(testName = "Testing firstEdge():");
        try {
            tempEdge = g.firstEdge();
            if ( (tempEdge) == null && g.numEdges() != 0)
                Debugger.printErr(testName + ":\n    Null is returned, but " +
                            " the graph has edges");
        } catch (PostconditionException pe) {
            Debugger.printErr("    Either the returned edge is not in the " +
                                "graph, or Null is returned but the graph" +
                                "has edges");
        }
        Debugger.debug("");

        Debugger.debug(testName = ("Testing nextEdge(edge06) [edge06 not " +
                                    " in graph:"));
        try {
            g.nextEdge(edge06);
            Debugger.printErr(testName + "\n    Exception should have " +
                            "been caught, since \"edge06\" is not in the " +
                            "graph violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                            "\"edge06\" is not in the \n    graph " +
                            "violating the precondition");
        }
        Debugger.debug("");


        Debugger.debug(testName = "Testing nextEdge(edge02):");
        try {
            tempEdge = g.nextEdge(edge02);
            if (tempEdge != edge03)
                Debugger.printErr(testName + "\n    Returned edge is not " +
                            "the next edge of \"edge02\"");
        } catch (PostconditionException pe) {
            Debugger.printErr("    Either the returned edge is not in the " +
                                "graph, or Null is returned but the " +
                                "parameter edge is not the last edge");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing nextEdge(edge05):");
        try {
            tempEdge = g.nextEdge(edge05);
            if (tempEdge != null)
                Debugger.printErr(testName + "\n    Returned edge is not " +
                            "null, but \"edge05\" is the last edge");
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + "\n    An edge is returned but " +
                                "\"edge05\" is the last edge");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing firstEdgeTo(node07):");
        try {
            g.firstEdgeTo(node07);
            Debugger.printErr(testName + "\n    Exception should have " +
                            "been caught, since \"node07\" is not in the " +
                            "\n    graph violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                            "\"node07\" is not in the \n    graph " +
                            "violating the precondition");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing firstEdgeTo(node01):");
        try {
            tempEdge = g.firstEdgeTo(node01);
            if (tempEdge != null)
                Debugger.printErr(testName + "\n    Returned edge is not " +
                            "null, but \"node01\" has no incoming edge yet");
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + "\n    Either the returned edge " +
                                "is not in the graph, or the returned " +
                                "edge does not terminate at \"node01\"");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing firstEdgeTo(node03):");
        try {
            tempEdge = g.firstEdgeTo(node03);
            Edge check = g.firstEdge();
            while(check.getToNode() != node03) check = g.nextEdge(check);
            if (tempEdge != check)
                Debugger.printErr(testName + "\n    Returned edge is not " +
                            "the first edge to node03");
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + "\n    Either the returned edge " +
                                "is not in the graph, or the returned " +
                                "edge does not terminate at \"node03\"");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing lastEdgeTo(node07):");
        try {
            g.lastEdgeTo(node07);
            Debugger.printErr(testName + "\n    Exception should have " +
                            "been caught, since \"node07\" is not in the " +
                            "graph violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                            "\"node07\" is not in the \n    graph " +
                            "violating the precondition");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing lastEdgeTo(node01):");
        try {
            tempEdge = g.lastEdgeTo(node01);
            if (tempEdge != null)
                Debugger.printErr(testName + "\n    Returned edge is not " +
                            "null, but \"node01\" has no incoming edge yet");
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + "\n    Either the returned edge " +
                                "is not in the graph, or the returned " +
                                "edge does not terminate at \"node01\"");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing lastEdgeTo(node03):");
        try {
            tempEdge = g.lastEdgeTo(node03);
            Edge check = g.nextEdge(tempEdge);
            while((check != null) && (check.getToNode() != node03) )
                check = g.nextEdge(check);
            if (check != null)
                Debugger.printErr(testName + "\n    Returned edge is not " +
                            "the last edge to node03");
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + "\n    Either the returned edge " +
                                "is not in the graph, or the returned " +
                                "edge does not terminate at \"node03\"");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing firstEdgeFrom(node07):");
        try {
            g.firstEdgeFrom(node07);
            Debugger.printErr(testName + "\n    Exception should have " +
                            "been caught, since \"node07\" is not in the " +
                            "graph violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                            "\"node07\" is not in the \n    graph " +
                            "violating the precondition");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing firstEdgeFrom(node01):");
        try {
            tempEdge = g.firstEdgeFrom(node01);
            if (tempEdge != null)
                Debugger.printErr(testName + "\n    Returned edge is not " +
                            "null, but \"node01\" has no outcoming edge yet");
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + "\n    Either the returned edge " +
                                "is not in the graph, or the returned " +
                                "edge does not originate from \"node01\"");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing firstEdgeFrom(node03):");
        try {
            tempEdge = g.firstEdgeFrom(node03);
            Edge check = g.firstEdge();
            while(check.getFromNode() != node03) check = g.nextEdge(check);
            if (tempEdge != check)
                Debugger.printErr(testName + "\n    Returned edge is not " +
                            "the first edge from node03");
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + "\n    Either the returned edge " +
                                "is not in the graph, or the returned " +
                                "edge does not originate from \"node03\"");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing lastEdgeFrom(node07):");
        try {
            g.lastEdgeFrom(node07);
            Debugger.printErr(testName + "\n    Exception should have " +
                            "been caught, since \"node07\" is not in the " +
                            "graph violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                            "\"node07\" is not in the \n    graph " +
                            "violating the precondition");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing lastEdgeFrom(node01):");
        try {
            tempEdge = g.lastEdgeFrom(node01);
            if (tempEdge != null)
                Debugger.printErr(testName + "\n    Returned edge is not " +
                            "null, but \"node01\" has no outgoing edge yet");
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + "\n    Either the returned edge " +
                                "is not in the graph, or the returned " +
                                "edge does not originate from \"node01\"");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing lastEdgeFrom(node03):");
        try {
            tempEdge = g.lastEdgeFrom(node03);
            Edge check = g.nextEdge(tempEdge);
            while((check != null) && (check.getFromNode() != node03) )
                check = g.nextEdge(check);
            if (check != null)
                Debugger.printErr(testName + "\n    Returned edge is not " +
                            "the last edge from node03");
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + "\n    Either the returned edge " +
                                "is not in the graph, or the returned " +
                                "edge does not originate from \"node03\"");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing nextEdgeTo(node07, edge03):");
        try {
            g.nextEdgeTo(node07, edge03);
            Debugger.printErr(testName + "\n    Exception should have " +
                            "been caught, since \"node07\" is not in the " +
                            "graph violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                            "\"node07\" is not in the\n    graph " +
                            "violating the precondition");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing nextEdgeTo(node03, edge06):");
        try {
            g.nextEdgeTo(node03, edge06);
            Debugger.printErr(testName + "\n    Exception should have " +
                            "been caught, since \"edge06\" is not in the " +
                            "graph violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                            "\"edge06\" is not in the \n    graph " +
                            "violating the precondition");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing nextEdgeTo(node03, edge02):");
        try {
            g.nextEdgeTo(node03, edge02);
            Debugger.printErr(testName + "\n    Exception should have been" +
                            "caught, since \"edge02\" does not terminate " +
                            "at node03 violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since edge02 " +
                            "does not terminate\n    at node03 violating " +
                            "the precondition");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing nextEdgeTo(node03, edge03):");
        try {
            tempEdge = g.nextEdgeTo(node03, edge03);
            Edge check = g.nextEdge(edge03);
            while(check.getToNode() != node03) check = g.nextEdge(check);
            if (tempEdge != check)
                Debugger.printErr(testName + "\n    Returned edge is not " +
                            "the next edge from node03 after edge03");
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + "\n    Either the returned edge " +
                                "is not in the graph, or the returned " +
                                "edge does not originate from \"node03\"");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing nextEdgeTo(node03, edge05):");
        try {
            tempEdge = g.nextEdgeTo(node03, g.lastEdgeTo(node03));
            if (tempEdge != null)
                Debugger.printErr(testName + "\n    Null should have been " +
                            "returned, since edge05 is the last edge from " +
                            "node03");
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + "\n    Either the returned edge " +
                                "is not in the graph, or the returned " +
                                "edge does not originate from \"node03\"");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing nextEdgeFrom(node07, edge03):");
        try {
            g.nextEdgeFrom(node07, edge03);
            Debugger.printErr(testName + "\n    Exception should have " +
                            "been caught, since node \"node07\" is " +
                            "not in the graph violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                            "\"node07\" is not in the \n    graph " +
                            "violating the precondition");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing nextEdgeFrom(node03, edge06):");
        try {
            g.nextEdgeFrom(node03, edge06);
            Debugger.printErr(testName + "\n    Exception should have " +
                            "been caught, since edge \"edge06\" is " +
                            "not in the graph violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since edge06 " +
                            "is not \n    in the graph violating the " +
                            "precondition");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing nextEdgeFrom(node03, edge03):");
        try {
            g.nextEdgeFrom(node03, edge03);
            Debugger.printErr(testName + "\n    Exception should have been" +
                        "caught, since edge \"edge03\" does not\n    " +
                        "originate from node03 violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since edge03 " +
                            "does not originate\n    from node03 " +
                            "violating the precondition");
        }
        Debugger.debug("");


        Debugger.debug(testName = "Testing nextEdgeFrom(node03, edge01):");
        try {
            tempEdge = g.nextEdgeFrom(node03, edge01);
            Edge check = g.nextEdge(edge01);
            while(check.getFromNode() != node03) check = g.nextEdge(check);
            if (tempEdge != check)
                Debugger.printErr(testName + "\n    Returned edge is not " +
                            "the next edge from node03 after edge01");
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + "\n    Either the returned edge " +
                                "is not in the graph, or the returned " +
                                "edge does not originate from \"node03\"");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing nextEdgeFrom(node03, edge02):");
        try {
            tempEdge = g.nextEdgeFrom(node03, g.lastEdgeFrom(node03));
            if (tempEdge != null)
                Debugger.printErr(testName + "\n    Null should have been " +
                            "returned, since edge02 is the last edge from " +
                            "node03");
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + "\n    Either the returned edge " +
                                "is not in the graph, or the returned " +
                                "edge does not originate from \"node03\"");
        }
        Debugger.debug("");

        Debugger.debug(testName = ("Testing addNode(node07) whose " +
                                    "coordinate is already taken"));
        g.addNode(node07);
        if (g.hasNode(node07)) Debugger.debug("\"node07\" is in the graph");
        else Debugger.printErr("addNode didn't add \"node07\" to the graph");

        Debugger.debug("Adding \"edge07\", which terminates at \"node03\":");
        g.addEdge(edge07);
        Debugger.debug("Checking stats for \"node03\":");
        checkStat((NodeImp)node03);

        Debugger.debug("");

        Debugger.debug("================================");
        Debugger.debug("Testing Basic Functions Complete");
        Debugger.debug("================================");
    }

    /**
     * Tests the graph statistics regarding pleiotropy and redundancy of each
     * node.
     */
    private static void testGraphStat() {
        Debugger.debug("Testing GraphStat Functions\n=====================");
        
        Debugger.debug("Creating 11 nodes (3 server and 8 clients)...");
        Debugger.enableTrace(false);
        //prepare test nodes
        Node tmpNode = new NodeImp();
        Node node01 = new NodeImp("CLIENT", 130, 160, tmpNode);
        Node node02 = new NodeImp("SERVER", 210, 210, tmpNode);
        Node node03 = new NodeImp("SERVER", 130,  70, tmpNode);
        Node node04 = new NodeImp("SERVER",  70, 120, tmpNode);
        Node node05 = new NodeImp("CLIENT",  60,  30, tmpNode);
        Node node06 = new NodeImp("CLIENT", 130,  10, tmpNode);
        Node node07 = new NodeImp("CLIENT", 210,  60, tmpNode);
        Node node08 = new NodeImp("CLIENT", 200, 120, tmpNode);
        Node node09 = new NodeImp("CLIENT",  10, 170, tmpNode);
        Node node10 = new NodeImp("CLIENT",  90, 200, tmpNode);
        Node node11 = new NodeImp("CLIENT",  10,  90, tmpNode);
        Debugger.enableTrace(true);

        Debugger.debug("Preparing test 20 edges (2SS+6SC+10CS+2CC)...");
        Debugger.enableTrace(false);
        //prepare test edges
        Edge tmpEdge = new EdgeImp();
        Edge edge01 = new EdgeImp(node03, node04, "SERVER-SERVER", tmpEdge);
        Edge edge02 = new EdgeImp(node03, node05, "SERVER-CLIENT", tmpEdge);
        Edge edge03 = new EdgeImp(node05, node03, "CLIENT-SERVER", tmpEdge);
        Edge edge04 = new EdgeImp(node06, node04, "CLIENT-SERVER", tmpEdge);
        Edge edge05 = new EdgeImp(node04, node03, "SERVER-SERVER", tmpEdge);
        Edge edge06 = new EdgeImp(node01, node04, "CLIENT-SERVER", tmpEdge);
        Edge edge07 = new EdgeImp(node03, node01, "SERVER-CLIENT", tmpEdge);
        Edge edge08 = new EdgeImp(node01, node08, "CLIENT-CLIENT", tmpEdge);
        Edge edge09 = new EdgeImp(node02, node01, "SERVER-CLIENT", tmpEdge);
        Edge edge10 = new EdgeImp(node01, node02, "CLIENT-SERVER", tmpEdge);
        Edge edge11 = new EdgeImp(node09, node04, "CLIENT-SERVER", tmpEdge);
        Edge edge12 = new EdgeImp(node10, node04, "CLIENT-SERVER", tmpEdge);
        Edge edge13 = new EdgeImp(node11, node04, "CLIENT-SERVER", tmpEdge);
        Edge edge14 = new EdgeImp(node05, node04, "CLIENT-SERVER", tmpEdge);
        Edge edge15 = new EdgeImp(node07, node08, "CLIENT-CLIENT", tmpEdge);
        Edge edge16 = new EdgeImp(node02, node08, "SERVER-CLIENT", tmpEdge);
        Edge edge17 = new EdgeImp(node03, node07, "SERVER-CLIENT", tmpEdge);
        Edge edge18 = new EdgeImp(node08, node03, "CLIENT-SERVER", tmpEdge);
        Edge edge19 = new EdgeImp(node06, node03, "CLIENT-SERVER", tmpEdge);
        Edge edge20 = new EdgeImp(node03, node06, "SERVER-CLIENT", tmpEdge);
        Debugger.enableTrace(true);

        Graph g = new GraphImp( 256, 256);
        
        Debugger.debug("Adding nodes...");
        Debugger.enableTrace(false);
        g.addNode(node01); g.addNode(node02); g.addNode(node03); 
        g.addNode(node04); g.addNode(node05); g.addNode(node06); 
        g.addNode(node07); g.addNode(node08); g.addNode(node09); 
        g.addNode(node10); g.addNode(node11); 
        Debugger.enableTrace(true);
        
        Debugger.debug("Adding edges...");
        Debugger.enableTrace(false);
        g.addEdge(edge01); g.addEdge(edge02); g.addEdge(edge03);
        g.addEdge(edge04); g.addEdge(edge05); g.addEdge(edge06);
        g.addEdge(edge07); g.addEdge(edge08); g.addEdge(edge09);
        g.addEdge(edge10); g.addEdge(edge11); g.addEdge(edge12);
        g.addEdge(edge13); g.addEdge(edge14); g.addEdge(edge15);
        g.addEdge(edge16); g.addEdge(edge17); g.addEdge(edge18);
        g.addEdge(edge19); g.addEdge(edge20);
        Debugger.enableTrace(true);
        
        ((GraphStat)g).numServers();        
        ((GraphStat)g).numClients();
        
        Debugger.enableTrace(false);
        Node n;
        for(int i = 0; i < g.numNodes(); i++) {
            n = g.getNode(i);
            Debugger.enableTrace(true);    

            Debugger.debug("Node " + i + ":");
            Debugger.debug("Pleotropy to servers = "
                                        + ((GraphStat)g).pleioToServer(n));
            Debugger.debug("Pleotropy to clients = "   
                                        + ((GraphStat)g).pleioToClient(n));
            Debugger.debug("Pleotropy to all nodes = " 
                                        + ((GraphStat)g).pleioToAll(n));
            Debugger.debug("Redundancy from servers = "
                                        + ((GraphStat)g).redunFromServer(n));
            Debugger.debug("Redundancy from clients = "
                                        + ((GraphStat)g).redunFromClient(n));
            Debugger.debug("Redundancy from all nodes = "
                                        + ((GraphStat)g).redunFromAll(n));
            Debugger.debug("Cluster factor = "
                                        + ((GraphStat)g).clusterFactor(n));
            
            Debugger.debug("");
            Debugger.enableTrace(false);  
        }
        
        Debugger.debug("====================================");
        Debugger.debug("Testing GraphStat Functions Complete");
        Debugger.debug("====================================");
    }
    
    /**
     * Queries an edge object for its basic usage properties.
     * @param u The edge in the form of an usable object to be checked
     */
     private static void checkStat(NodeImp u) {
        Debugger.debug("Checking node statistics...");
        u.getEfficiency();
        u.getLoad();
    }

}
