import utility.*;
import population.*;
import population.graph.*;
import java.util.*;

/**
 * This class tests the simple operation of the node use for breadth first
 * search.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.4.1
 */
public class ModuleTestBFSNode {
    // to store info regarding the current test
    private static String testName;

    private static BFSResistance bfsr;
    
    // Default constructor
    /**
     * One should not instantate a test class
     */
    public ModuleTestBFSNode() {
        Debugger.printErr("Don't instantiate a test class: " + 
                            "ModuleTestBFSNode");
    }
    
    /**
     * The main method for this class. Firstly, a set of chromosomes is
     * generated. Then it is used to test that apply and map produces the
     * same results. The correctness was checked manually.
     */
    public static void main(String[] args) {
        // pipe results to file
        Debugger.pipeResult("BFSNodeNormal.txt",
                            "BFSNodeError.txt");
        Debugger.enableTrace(true);                             

        Debugger.debug("\n===========================\n"+
                         "filename: BFSNodeNormal.txt\n" +
                         "===========================");
        Debugger.debug("Module Test for population.BFSNode:\n");
        
        Debugger.printErr("\n==========================\n"+
                            "filename: BFSNodeError.txt\n" +
                            "==========================");
        Debugger.printErr("Module Test for population.BFSNode:\n");

        bfsr = new BFSResistance();

        testDefault();
        Debugger.debug("");

        BFSNode bNode = testStandard();
        Debugger.debug("");

        Debugger.debug("=================================");
        Debugger.debug("Module Test for BFSNode Completed");
        Debugger.debug("=================================");
        Debugger.printErr("=================================");
        Debugger.printErr("Module Test for BFSNode Completed");
        Debugger.printErr("=================================");
    }


    /**
     * Tests the default constructor
     */
    private static void testDefault() {
        Debugger.debug("Create a BFSNode using the default constructor, " +
            "this will be\n    shown not usable in later stage.");
        BFSNode bNode = new BFSNode();

        Debugger.debug( "Testing " + (testName = "getGraphNode()") + ":");
        try {
            bNode.getGraphNode();
            Debugger.printErr(testName + ": ERROR!!! Exception SHOULD " +
                "have been caught,\n    since this node should be null!!!");
        } catch (NullPointerException npe) {
            Debugger.debug("Exception not caught as expected. since this " +
                    "node correspond to \"Null\". and cannot be used for " +
                    " traversal.");
        }        
                
        Debugger.debug( "Testing " + (testName = "getAccumCost()") + ":");
        if(bNode.getAccumCost() == Double.POSITIVE_INFINITY)
            Debugger.debug("The cost/resistance at this point is " +
                 "Infinity (+oo). Thus the path cannot be continued");
        else
            Debugger.printErr(testName + ": ERROR!!! This cost/resistance " +
                "at this point should be infinite.");
    }

    /**
     * tests the standard constructor
     */
    private static BFSNode testStandard() {
        Debugger.debug("Create a BFSNode using the standard constructor.");
        
        // set up the BFSResistance for this test
        Chromosome tempChromo = createChromo();
        
        // prepare parameters for creating the node
        double cost = EdgeCostMatrix.getCost(9,3) + 
                        EdgeCostMatrix.getCost(3,2);
        // assume this node is on the path starting from node "9"
        ArrayList visited = new ArrayList();
        visited.add(tempChromo.getNodeList().get(9));
        visited.add(tempChromo.getNodeList().get(3));
        visited.add(tempChromo.getNodeList().get(2));
        // obtain the list of reachable nodes
        ArrayList reachable = (ArrayList) tempChromo.getDataArray().get(2);
        
        BFSNode bNode = new BFSNode(bfsr, cost, visited, reachable);

        
        Debugger.debug( "Testing " + (testName = "getGraphNode()") + ":");
        if(bNode.getGraphNode() != null)
            Debugger.debug("This node does not correspond to \"Null\". " +
                "Thus it can be used for traversal.");
        else
            Debugger.printErr(testName + ": ERROR!!! This node should NOT " +
                "be null!!!");
        
        Debugger.debug( "Testing " + (testName = "getAccumCost()") + ":");
        if(bNode.getAccumCost() != Double.POSITIVE_INFINITY)
            Debugger.debug("The cost/resistance at this point is not " +
                "Infinity (+oo). Thus the path can be continued");
        else
            Debugger.printErr(testName + ": ERROR!!! This cost/resistance " +
                "at this point should not be infinite.");
        
        return bNode;
    }        

    /**
     * Create predetermined chromosomes for testing
     */
    private static Chromosome createChromo() {

        Debugger.debug("Constructing Chromosome for test");          
        ArrayList nodeList = genNodeList();
        Debugger.enableTrace(false); 
        // create cost matrix
        EdgeCostMatrix.populateMatrix(nodeList.size(), 1000);
      
        // edges for different test cases
        int[][] chromEdges = { { 2, 3}, { 2, 4}, { 4, 2}, { 5, 3}, { 3, 2}, 
                               { 0, 3}, { 2, 0}, { 0, 7}, { 1, 0}, { 0, 1},
                               { 8, 3}, { 9, 3}, {10, 3}, { 4, 3}, { 6, 7},
                               { 1, 7}, { 2, 6}, { 7, 2}, { 5, 2}, { 2, 5} };

        int numServers = 3, numClients = 8, setNumber;
        
        ArrayList cells = new ArrayList();
        /**
         * for every node in the network, create a list to store   
         * destination nodes.
         */
        for(int j = 0; j < nodeList.size(); j++) cells.add(new ArrayList());
        
        ArrayList cell;
        Chromosome temp;
        
        // add adjacent nodes  
        for(int j = 0; j < chromEdges.length; j++) {
            cell = (ArrayList) cells.get(chromEdges[j][0]);
            cell.add(nodeList.get(chromEdges[j][1]));
        }
                    
        temp = new Chromosome(cells, nodeList, numServers, numClients); 

        Debugger.enableTrace(true);

        return temp;
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
        double[] params = {1000000000, 0.0}; 
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


}
