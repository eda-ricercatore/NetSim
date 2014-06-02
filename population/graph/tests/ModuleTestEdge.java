// importing packages
import utility.*;
import population.graph.*; // the class we are testing

/**
 * This test suite will test for the functionality of the Edge class under 
 * the assumption that Repairable is a fully functional superclass of Edge. 
 * This test suite will first check if all the assertion made are enforced by 
 * providing invalid input parameters. Then it should be checked that the 
 * general behaviour of the class is consistent with that in the class 
 * descriptions.
 *
 * The results of the tests will be output to 2 separate files:
 *     -EdgeNormal.txt will contain the message if tests are passed
 *     -EdgeError.txt will contain the message for erroneous behaviour
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.5
 * @since   0.3.5
 */
public class ModuleTestEdge {
    // to store info regarding the current test
    private static String testName;

    // Default constructor
    /**
     * Should not instantate a test class.
     */
    public ModuleTestEdge() {
    	throw new AssertionException("Don't instantiate a test class.");
    }

    /**
     * The main method for this class. The test suite is divided into 3 main
     * components. The first test will check the functionality of the default
     * and standard constructors. The second will validate the behaviour
     * concerned with basic methods of the edge. The last test shall
     * determine the correct behaviour of usage of an Edge object.
     */
    public static void main(String[] args) {
        // pipe results to file
        Debugger.pipeResult("EdgeImpNormal.txt", "EdgeImpError.txt");
        Debugger.enableTrace(true); // enable trace printing
        Debugger.debug("");

        /**
         * Modified by Andy 07/04/05: to add header for output files for
         * test results.
         */
        Debugger.debug("===========================\n"+
                       "filename: EdgeImpNormal.txt\n" +
                       "===========================");
        Debugger.debug("Module Test for population.graph.EdgeImp:\n");
        
        Debugger.printErr("==========================\n"+
                          "filename: EdgeImpError.txt\n" +
                          "==========================");
        Debugger.printErr("Module Test for population.graph.EdgeImp:\n");

        testConstructors();
        Debugger.debug("");        
        
        testBasics();
        Debugger.debug("");

        testUsage();
        Debugger.debug("");

        // Modified by Andy 07/04/05: to emphisise end of test
        Debugger.debug("=================================");
        Debugger.debug("Module Test for EdgeImp Completed");
        Debugger.debug("=================================");
        Debugger.printErr("=================================");
        Debugger.printErr("Module Test for EdgeImp Completed");
        Debugger.printErr("=================================");
    }

    /**
     * Tests the constructors of Edge object. This includes one default
     * constructor and three standard constructors. The standard constructors
     * will be tested with a variety of input parameters to ensure all the
     * precondition are checked for.
     */
    private static void testConstructors() {
        Debugger.debug("Testing Constructors\n====================");
        testName = "Testing default constructor";
        
        Edge test = new EdgeImp();
        checkBasicProperties(test);
        Debugger.debug("");

        testName = "Testing standard constructor1";
        double[] params = {-1.0, 1.0, 0.0};
        // test with illegal value for initial capacity of the Edge
        Debugger.debug(testName + " with capacity = -1.0");
        try {
            test = new EdgeImp( params, new Repairable());
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
            params = new double[] {1.0, 1.5, 0.0};
            test = new EdgeImp( params, new Repairable());
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
            params = new double[] {1.0, -0.5, 0.0};
            test = new EdgeImp( params, new Repairable());
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught,since efficiency = -0.5\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "efficiency = -0.5 violates the\n    " +
                           "precondition");
        }
        Debugger.debug("");

        // test with illegal value for cost
        Debugger.debug(testName + " with capacity = 1.0, efficiency = 0.5," +
                            " \n    cost = -1.0");
        try {
            params = new double[] {1.0, 0.5, -1.0};
            test = new EdgeImp( params, new Repairable());
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since cost = -1.0\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "cost = -1.0 violates the\n    " +
                           "precondition");
        }
        Debugger.debug("");
            
        // test constructor with legal values
        Debugger.debug(testName + " with capacity = 1.0, efficiency = 0.5," +
                            " \n    cost = 1.0");
        params = new double[] {1.0, 0.5, 1.0};
        test = new EdgeImp( params, new Repairable());
            checkBasicProperties(test);
            
        
        Edge tempEdge = test;
        Node tempNode1 = new NodeImp();
        Node tempNode2 = new NodeImp();
        testName = "Testing standard constructor2";
        // test with illegal fromNode
        Debugger.debug(testName + " with fromNode = Null");
        try {
            test = new EdgeImp( null, tempNode2, "NewEdge1", tempEdge);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since fromNode = Null\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "fromNode = Null violates the precondition");
        }
        Debugger.debug("");
    
        // test with illegal toNode
        Debugger.debug(testName + " with toNode = Null");
        try {
            test = new EdgeImp(tempNode1, null, "NewEdge2", tempEdge);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since toNode = Null\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "toNode = Null violates the precondition");
        }
        Debugger.debug("");    
    
        // test with illegal label
        Debugger.debug(testName + " with label = Null");
        try {
            test = new EdgeImp(tempNode1, tempNode2, null, tempEdge);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since label = Null\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "label = Null violates the precondition");
        }
        Debugger.debug("");        
    
        // test with illegal edge (i.e. non-Repairable)
        Debugger.debug(testName + " with non-Repairable edges");
        try {
            test = new EdgeImp(tempNode1, tempNode2, "blah", new EdgeTmp());
            Debugger.printErr(testName + ":\n    ClassCastException should" +
                                " have been caught, since the template edge"+
                                " \n    for edge creation does not have a"  +
                                " superclass being Repairable");
        } catch (ClassCastException ce) {
            Debugger.debug("    ClassCastException caught as expected since"+
                           " the template edge for edge creation does not"+
                           "\n    have a superclass being Repairable");
        }
        Debugger.debug("");         
    
        // test with legal parameters
        Debugger.debug(testName + " with legal params");
        test = new EdgeImp(tempNode1, tempNode2, "Working", tempEdge);
        checkBasicProperties(test);        
        Debugger.debug("");  


        testName = "Testing standard constructor3";
        Node[] nodes = {tempNode1, tempNode2};
        params = new double[] {1.0, 1.0, 1.0, 0.0, 0.0};
        // test with illegal label
        Debugger.debug(testName + " with label = Null");
        try {
            test = new EdgeImp(nodes, null, 1, params, true);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since label = Null\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "label = Null violates the precondition");
        }
        Debugger.debug("");
        
        // test with illegal value for initial capacity of the Edge
        Debugger.debug(testName + " with capacity = -1.0");
        params = new double[] {-1.0, 1.0, 1.0, 0.0, 0.0};
        try {
            test = new EdgeImp(nodes, "newEdge", 1, params, true);
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
        params = new double[] {1.0, 1.5, 1.0, 0.0, 0.0};
        try {
            test = new EdgeImp(nodes, "newEdge", 1, params, true);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught,since efficiency = 1.5\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "efficiency = 1.5 violates the\n    " +
                           "precondition");
        }
        Debugger.debug("");

        // test with illegal value for cost
        Debugger.debug(testName + " with capacity = 1.0, efficiency = 0.5," +
                            " \n    cost = -1.0");
        params = new double[] {1.0, 0.5, -1.0, 0.0, 0.0};
        try {
            test = new EdgeImp(nodes, "newEdge", 1, params, true);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since cost = -1.0\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "cost = -1.0 violates the\n    precondition");
        }
        Debugger.debug("");
            
        // test with illegal value for cost
        Debugger.debug(testName + " with capacity = 1.0, efficiency = 0.5," +
                            " \n    cost = 1.0");
        params = new double[] {1.0, 0.5, 1.0, 0.0, 0.0};
        test = new EdgeImp(nodes, "Working2", 1, params, true);
        checkBasicProperties(test);        
        
        Debugger.debug("");                        

        Debugger.debug("=============================");
        Debugger.debug("Testing Constructors Complete");
        Debugger.debug("=============================");
    }

    /**
     * Queries an edge object for its basic properties.
     * @param e The edge to be checked
     */
    private static void checkBasicProperties(Edge e) {
        Debugger.debug("    Checking basic properties:");
        try {
            String label = e.getLabel();
            double capacity = e.getMaxCapacity();
            double efficiency = e.getEfficiency();
            double getCost = e.getCost();
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + ":\n    This instance of Edge "+
                                                "has invalid properties.");
        }
    }

    /**
     * Tests the basic function of the edge. Firstly, the accessing and
     * modifying the connected nodes are checked. Here, we assume that the
     * node implementation is functional up to the methods we call. Then the
     * labelling and the cost are also verified.
     */
    private static void testBasics() {
        Debugger.debug("Testing Basic Functions\n=======================");
        
        //prepare 4 nodes to be used
        Debugger.debug("Preparing some nodes...\n");
        Debugger.enableTrace(false);
    
        Node tmpNode = new NodeImp();
        Node fromNode1 = new NodeImp("fromNode1", 3, 3, tmpNode);
        Node fromNode2 = new NodeImp("fromNode2", 4, 4, tmpNode);
        Node toNode1 = new NodeImp("toNode1", 1, 1, tmpNode);
        Node toNode2 = new NodeImp("toNode2", 2, 2, tmpNode);
        
        Debugger.enableTrace(true);
        Debugger.debug("Preparing test edge...");
        //prepare test edge
        Edge tmpEdge = new EdgeImp();
        Edge testEdge = new EdgeImp(fromNode1, toNode1, "testEdge", tmpEdge);

        // testing getFromNode() and getToNode()        
        Debugger.debug("\nRetrieving fromNode variable:");
        Node tempFrom = testEdge.getFromNode();
        tempFrom.getLabel();
        Debugger.debug("Retrieving toNode variable:");
        Node tempTo = testEdge.getToNode();
        tempTo.getLabel();
        
        // testing setFromNode() and setToNode()        
        Debugger.debug("\nSetting fromNode variable:");
        testEdge.setFromNode(fromNode2);
        Debugger.debug("Retrieving fromNode variable:");
        tempFrom = testEdge.getFromNode();
        tempFrom.getLabel();
        
        Debugger.debug("\nSetting toNode variable:");
        testEdge.setToNode(toNode2);
        Debugger.debug("Retrieving toNode variable:");
        tempTo = testEdge.getToNode();
        tempTo.getLabel();        
        
        // testing getLabel() and setLabel()
        Debugger.debug("\nRetrieving label variable:");
        testEdge.getLabel();
        
        testName = "Setting label variable to null ";
        Debugger.debug(testName);
        try {   // try setting a null label
            testEdge.setLabel(null);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since label = null\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "label = null violates the\n    precondition");
        }      
          
        Debugger.debug("Setting label variable to something not null:");
        testEdge.setLabel("newTestEdge");
        Debugger.debug("Retrieving label variable:");
        testEdge.getLabel();
        
        
        // testing getCost() and setCost()
        Debugger.debug("\nRetrieving Cost variable:");
        testEdge.getCost();
        
        testName = "Setting Cost variable to -1.0 ";
        Debugger.debug(testName);
        try {   // try setting a negative cost
            testEdge.setCost(-1.0);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since cost = -1.0\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "cost = -1.0 violates the\n    precondition");
        }      
          
        Debugger.debug("Setting Cost variable to something not negative:");
        testEdge.setCost(1.0);
        Debugger.debug("Retrieving Cost variable:");
        testEdge.getCost();
        
        Debugger.debug("");                        

        Debugger.debug("================================");
        Debugger.debug("Testing Basic Functions Complete");
        Debugger.debug("================================");
    }

    /**
     * Tests the implementation of the usable interface contained in EdgeImp.
     * The methods tested include increasing and decreasing usage, maximum
     * capacity and efficiency. Also, the test also confirms the correct 
     * accessing the mentioned variables.
     */
    private static void testUsage() {
        Debugger.debug("Testing Usage Functions\n=======================");
        
        //prepare 4 nodes to be used
        Debugger.debug("Preparing some nodes...\n");
        Debugger.enableTrace(false);
    
        Node tmpNode = new NodeImp();
        Node fromNode = new NodeImp("fromNode", 3, 3, tmpNode);
        Node toNode = new NodeImp("toNode", 1, 1, tmpNode);
        
        Debugger.enableTrace(true);
        Debugger.debug("Preparing test edge...");
        //prepare test edge
        double[] params = {1000000, 1.0, 1.0, 0.3, 0.5 };
        Edge testEdge = new EdgeImp( new Node[] {fromNode, toNode},
                                     "testEdge", 5, params, true);
        checkStat(testEdge);
        Debugger.debug("");

        Debugger.debug("Attempt to increase usage");
        Debugger.debug("    Try increasing by a negative amount");
        try {
            testEdge.increUsage(-1000);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since the negative amount\n" +
                                "    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                    "the negative amount violates the\n    precondition");
        }
        
        if(testEdge.increUsage(1000)) Debugger.debug("Success");
        else Debugger.debug("Fail");
        if(testEdge.getUsage() != 1000)
            Debugger.printErr("Error: increUsage");
        checkStat(testEdge);
        Debugger.debug("");
  
        if(testEdge.increUsage(990000)) Debugger.debug("Success");
        else Debugger.debug("Fail");
        if(testEdge.getUsage() != 991000)
            Debugger.printErr("Error: increUsage");
        checkStat(testEdge);
        Debugger.debug("");
        
        if(testEdge.increUsage(9000)) Debugger.debug("Success");
        else Debugger.debug("Fail");
        if(testEdge.getUsage() != 1000000)
            Debugger.printErr("Error: increUsage");
        checkStat(testEdge);
        Debugger.debug("");
            
        if(testEdge.increUsage(1000)) Debugger.debug("Success");
        else Debugger.debug("Fail");
        if(testEdge.getUsage() != 1000000)
            Debugger.printErr("Error: increUsage");
        Debugger.debug("");
        
        

        Debugger.debug("Attempt to decrease usage");
        Debugger.debug("    Try decreasing by a negative amount");
        try {
            testEdge.decreUsage(-1000);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught, since the negative amount\n" +
                                "    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                    "the negative amount violates the\n    precondition");
        }
        
        if(testEdge.decreUsage(1000)) Debugger.debug("Success");
        else Debugger.debug("Fail");
        if(testEdge.getUsage() != 999000) 
            Debugger.printErr("Error: decreUsage");
        checkStat(testEdge);
        Debugger.debug("");
          
        if(testEdge.decreUsage(990000)) Debugger.debug("Success");
        else Debugger.debug("Fail");
        if(testEdge.getUsage() != 9000) 
            Debugger.printErr("Error: decreUsage");
        checkStat(testEdge);
        Debugger.debug("");
        
        Debugger.debug("Resetting usage");
        testEdge.resetUsage();
        if(testEdge.getUsage() != 0) Debugger.printErr("Error: resetUsage");
        checkStat(testEdge);
        Debugger.debug("");
            
        if(testEdge.decreUsage(1000)) Debugger.debug("Success");
        else Debugger.debug("Fail");
        if(testEdge.getUsage() != 0) Debugger.printErr("Error: decreUsage");
        Debugger.debug("");

        
        Debugger.debug("Attempt to vary maximum capacity");
        testEdge.increUsage(990000);
        testEdge.getUsage();        
        Debugger.debug("");

        testEdge.getMaxCapacity();
        checkStat(testEdge);
        if(testEdge.setMaxCapacity(900000)) Debugger.debug("Success");
        else Debugger.debug("Fail");
        if(testEdge.getMaxCapacity()==900000)
            Debugger.printErr("Error: setMaxCapacity reduced available " +
                                "capacity lower than current usage");
        
        Debugger.debug("Try decreasing usage");
        testEdge.decreUsage(100000);
        testEdge.getUsage();
        
        if(testEdge.setMaxCapacity(900000)) Debugger.debug("Success");
        else Debugger.debug("Fail");
        if(testEdge.getMaxCapacity()!=900000)
            Debugger.printErr("Error: setMaxCapacity failed to set the " +
                                "maximum capacity");
        checkStat(testEdge);
        Debugger.debug("");
        

        Debugger.debug("Attempt to vary efficiency");
        Debugger.debug("");

        testEdge.getEfficiency();
        checkStat(testEdge);
        if(testEdge.setEfficiency(0.70)) Debugger.debug("Success");
        else Debugger.debug("Fail");
        if(testEdge.getEfficiency()==0.70)
            Debugger.printErr("Error: setEfficiency reduced available " +
                                "capacity lower than current usage");
        
        Debugger.debug("Try decreasing usage");
        testEdge.decreUsage(270000);
        testEdge.getUsage();
        
        if(testEdge.setEfficiency(0.70)) Debugger.debug("Success");
        else Debugger.debug("Fail");
        if(testEdge.getEfficiency()!=0.70)
            Debugger.printErr("Error: setEfficiency failed to set the " +
                                "efficiency");
        checkStat(testEdge);
        
        Debugger.debug("================================");
        Debugger.debug("Testing Usage Functions Complete");
        Debugger.debug("================================");        
    }


    /**
     * Queries an edge object for its basic usage properties.
     * @param u The edge in the form of an usable object to be checked
     */
     private static void checkStat(Usable u) {
        Debugger.debug("Checking edge statistics...");
        u.getAvailCap();
        u.getUtilisation();
        u.getLoad();
    }

    /**
     * A copy of the original edgeImp which does not extend Repairable.
     */
    private static class EdgeTmp implements Edge {
        // nodes which the edge originates and ends
        private Node fromNode, toNode;
        // label indicating the type of the edge
        private String label;
        // the maximum capcity of the edge
        private double capacity;
        // the operating efficiency of an edge ranged from 0.00 to 1.00
        private double efficiency;
        // the cost of using this edge
        private double edgeCost;
        // the amount of the edge's capacity currently in use
        private double usage = 0.0;
    
        // Default Constructor
        /**
         * Creates a default instance of EdgeImp. An Edge created using this
         * default constructor cannot be used immediately, since it would have
         * the properties of non-repairable and non-usable objects.<p>
         * That is, extremely high repair generation requirement, probablity
         * of failure of 1.00 and  maximum allowable probablity of failure
         * (threshold) of 0%. Also, this edge's capacity and operating
         * efficiency are both 0, and its cost is <code>POSITIVE_INFINITY
         * </code> (+oo).<p>
         * Thus the above mentioned properties must be altered before using
         * this edge.
         * @see Repairable
         */
        public EdgeTmp() {
            super();            // call constructor of super class
            capacity = 0.00;    // 0 capacity
            efficiency = 0.00;  // operating at 0% efficiency
            label = "";         // empty label
            edgeCost = Double.POSITIVE_INFINITY;    // extremely high cost
        }  
        
        /**
         * implmentation of Edge Interface
         */
    
        /**
         * Returns the departure node of this edge
         * @return  the node which this edge starts from
         */
        public Node getFromNode() {
            return fromNode;
        }
        
        /**
         * Returns the destination node of this edge
         * @return  the node which this edge ends
         */
        public Node getToNode() {
            return toNode;
        }
    
        /**
         * Sets the departure node of this edge
         * @param   n The node which this edge starts from
         */
        public void setFromNode(Node n) {
            fromNode = n;
        }
        
        /**
         * Sets the destination node of this edge
         * @param   n The node which this edge ends
         */
        public void setToNode(Node n) {
            toNode = n;
        }
        
        /**
         * Returns the label or type of this edge. This is implemented as a
         * String, but in later versions Objects maybe used for more
         * flexibility.
         * @return  the label which sufficiently describes the Edge
         * @throws  PostconditionException If Null is returned.
         */
        public String getLabel() throws PostconditionException {
            Assertion.post( label != null,
                            "Label of this edge is " + label,
                            "Label of this edge must not be null");
            return label;
        }
    
        /**
         * Sets the label or type for the edge. This is implemented as a
         * String, but in later versions Objects maybe used for more
         * flexibility.
         * @param   l A String that sufficiently describes the Edge
         * @throws  PreconditionException If <code>l</code> is a Null String.
         */
        public void setLabel(String l) throws PreconditionException {
            Assertion.pre( l != null,
                           "Label to be set is " + l,
                           "Label to be set must not be null");
            label = new String(l);
        }
        
        /**
         * Returns the cost of using this edge to transfer data.
         * @throws  PostconditionException If the returned cost is negative.
         * @return  the cost of transfering data along this Edge
         */
        public double getCost() throws PostconditionException {
            Assertion.post( edgeCost >= 0,
                            "Cost of edge is " + edgeCost,
                            "Cost of edge must not be negative");
            return edgeCost;    
        }
        
        /**
         * Sets the cost of using this edge to transfer data.
         * @param   cost The new cost of this edge
         * @throws  PreconditionException If <code>cost</code> is negative.
         */
        public void setCost(double cost) throws PreconditionException {
            Assertion.pre( cost >= 0,
                            "New cost of edge is " + cost,
                            "New cost of edge must not be negative");
            edgeCost = cost;
        }
        
        /**
         * implmentation of Usable Interface
         */
    
        /**
         * Returns the maximum capacity of this edge. The units to measure
         * capacity is application dependent. For example of data traffic,
         * capicity may be in units of Mbps. 
         * @return  the capacity of this edge operating at 100% efficiency
         * @throws  PostconditionException If the returned capacity is 
         *          negative.
         */
        public double getMaxCapacity() throws PostconditionException {
            return capacity;
        }
        
        /**
         * Sets the maximum capacity of this edge. The units to measure 
         * capacity is application dependent. For example of data traffic,
         * capicity may be in units of Mbps. <p>
         *
         * The capacity limits the usage of this edge, so the new capacity, 
         * subject to efficiency, must not be less than the current usage. If
         * a lesser value is to be set, usage must be reduced. This
         * automatically implies if input parameter is negative, change of
         * capacity will also be unsucessful.
         *
         * @param   cap The capacity of the edge operating at 100% efficiency
         * @return  <code>true</code> If the maximum capacity was changed
         *          successfully.
         */
        public boolean setMaxCapacity(double cap) {
            if ( (cap*efficiency) >= usage ) {
                capacity = cap;
                return true;
            } else return false;
        }
    
        /**
         * Returns the operating efficiency this edge is running. This is 
         * measured as a percentage, this is used in conjunction with the
         * maximum capacity to specify the total capacity available for use.
         * @return  the efficiency of this edge as a percentage
         * @throws  PostconditionException If the returned capacity is not
         *          between 0.00 and 1.00
         */
        public double getEfficiency() throws PostconditionException {
            Assertion.post( (efficiency >= 0.00) && (efficiency <= 1.00),
                            "Efficiency factor is " + efficiency,
                            "Efficiency factor must be between 0.0 and 1.0");
            return efficiency;
        }
        
        /**
         * Sets the operating efficiency for this edge. This is measured as a 
         * percentage, this is used in conjunction with the maximum capacity
         * to specify the total capacity available for use.<p>
         *
         * The efficiency limits the usage of this edge, so if the new value
         * set result in a total available capacity less than the cuurent
         * usage, the change can not be made. 
         *
         * @param   eff The level of efficiency as a number between 0.00 and 
         *          1.00 inclusive.
         * @return  <code>true</code> If the efficiency was changed
         *          successfully.
         * @throws  PreconditionException If <code>eff</code> is not between 
         *          0.00 and 1.00.
         */
        public boolean setEfficiency(double eff)
            throws PreconditionException {
            Assertion.pre( (eff >= 0.00) && (eff <= 1.00),
                        "Efficiency to be set is " + eff,
                        "Efficiency to be set must be between 0.0 and 1.0");
            if ( (capacity*eff) >= usage ) {
                efficiency = eff;
                return true;
            } else return false;    
        }
    
        /**
         * Returns the usage on this edge. The units to quantify the usage
         * should be consistent with that of the capacity.
         * @return  the current usage of this edge
         * @throws  PostconditionException If the returned usage is negative.
         */
        public double getUsage() throws PostconditionException {
            Assertion.post( usage >= 0, "Usage is " + usage,
                                        "Usage must be non-negative");
            return usage;
        }
        
        /**
         * Increases the current usage by an arbitrary amount. This is
         * typically used when a request is made to use this object. Note the
         * increase or request may not always be successful due t
         * availability.
         * @param   amount The amount of usage to increase
         * @return  <code>true</code> If the usage was increased successfully.
         * @throws  PreconditionException If <code>amount</code> is negative.
         */
        public boolean increUsage(double amount)
            throws PreconditionException {
            Assertion.pre( amount >= 0,
                           "The amount to increment is " + amount,
                           "The amount to increment must be non-negative");
            if( amount <= getAvailCap() ) {
                usage += amount;
                return true;
            } else return false;
        }  
    
        /**
         * Decreases the current usage by an arbitrary amount. This is
         * typically used when an user finishes using use this object.
         * @param   amount The amount of usage to decrease
         * @return  <code>true</code> If the usage was decreased successfully.
         * @throws  PreconditionException If <code>amount</code> is negative.
         */
        public boolean decreUsage(double amount)
            throws PreconditionException {
            Assertion.pre( amount >= 0,
                           "The amount to decrement is " + amount,
                           "The amount to decrement must be non-negative");
            if( (usage - amount) >= 0 ) {
                usage -= amount;
                return true;
            } else return false;
        }
        /**
         * If required, later versions should register users of this object,
         * in a hash set for quick access. This way, only users of this node
         * can remove their own usage within this object. Thus this method
         * may later on be changed to decreUsage(Node n, double amount).
         * Likewise for increUsage, it becomes increUsage(Node n, double
         * amount).
         */
              
        /**
         * Sets the current usage to 0. This is typically used when the edge
         * is disabled and re-enabled.
         */
        public void resetUsage() {
            usage = 0;
        }
        
        /**
         * Returns the amount of capacity which is available for use. An user
         * can determine whether this edge can meet the user's demand.
         * @return  the available capacity of this edge
         * @throws  PostconditionException If the returned capacity is
         *          negative.
         */
        public double getAvailCap() throws PostconditionException {
            double cap = (capacity * efficiency) - usage;
            Assertion.post( cap >= 0, "Available capacity is " + cap,
                                  "Available capacity must be non-negative");
            return cap;        
        }
        
        /**
         * Returns the value of utilisation for this edge. This is calculated
         * as the ratio between usage and maximum capacity. This value can be
         * used when choosing amongst node with similar amount of available
         * usage.
         * @return  the utilisation of this edge as a decimal
         * @throws  PostconditionException If the returned utilisation is not
         *          between 0.00 and 1.00.
         */    
        public double getUtilisation() throws PostconditionException {
            double util = usage / capacity;
            Assertion.post( (util >= 0.00) && (util <= 1.00),
                            "Utilisation is " + util,
                            "Utilisation must be between 0.0 and 1.0");
            return util;        
        }        
                
        /**
         * Returns the load factor of this edge. This is calculated as the
         * ratio between the usage, and the maximum capacity subtracted by
         * the usage. This value can be used when choosing amongst nodes with
         * similar amount of available usage.
         * @return  the load factor of this edge
         * @throws  PostconditionException If the returned load is negative.
         */
        public double getLoad() throws PostconditionException {
            double load = usage / (capacity - usage);
            Assertion.post( load >= 0.00, "Load factor is " + load,
                            "Load factor must be between 0.0 and 1.0");
            return load;          
        }
    }
}
