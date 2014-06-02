import utility.*;
import population.*;
import population.graph.*;
import java.util.*;

/**
 * This class verifies the functionality of the algorithm of calculating 
 * resistance in the graph by breadth first search, and the associated 
 * methods.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.4.1
 */
public class ModuleTestBFSResistance {
    // to store info regarding the current test
    private static String testName;
    private static final double oo = Double.POSITIVE_INFINITY;
    
    // Default constructor
    /**
     * One should not instantate a test class
     */
    public ModuleTestBFSResistance() {
        Debugger.printErr("Don't instantiate a test class: " + 
                            "ModuleTestBFSResistance");
    }
    
    /**
     * Initiates the tests for the class BFSResistance. A population will be
     * created at first. Then the accessor will be tested, and the resistance
     * calculation will verified manually.
     */
    public static void main(String[] args) {
        // pipe results to file
        Debugger.pipeResult("BFSResistanceNormal.txt",
                            "BFSResistanceError.txt");
        Debugger.enableTrace(true);                             

        Debugger.debug("\n===================================\n"+
                         "filename: BFSResistanceNormal.txt\n" +
                         "===================================");
        Debugger.debug("Module Test for population.BFSResistance:\n");
        
        Debugger.printErr("\n===================================\n"+
                            "filename: BFSResistanceError.txt\n" +
                            "===================================");
        Debugger.printErr("Module Test for population.BFSResistance:\n");

        SetOfChromosomes soc = createPop();
        Debugger.debug(""); 
        
        testAccessor(soc);
        Debugger.debug("");        

        testGetResistance(soc);
        Debugger.debug("");        

        Debugger.debug("=======================================");
        Debugger.debug("Module Test for BFSResistance Completed");
        Debugger.debug("=======================================");
        Debugger.printErr("=======================================");
        Debugger.printErr("Module Test for BFSResistance Completed");
        Debugger.printErr("=======================================");
    }



    /**
     * Create predetermined chromosomes for testing functions
     */
    private static SetOfChromosomes createPop() {
        ArrayList nodeList = genNodeList();
        Debugger.debug("Constructing Chromosomes for test Population:\n");
        
        Debugger.debug("Preparing edge cost matrix...");
        Debugger.debug("[cost between 2 nodes is the average of their ids.");          
        // create cost matrix
        int numNodes = nodeList.size();
        //Debugger.printErr("numNodes = "+numNodes);
        double[][] costMatrix = new double[numNodes][numNodes];
        for(int i = 0; i < numNodes; i++)
            for(int j = 0; j < numNodes; j++) 
                if (i != j) costMatrix[i][j] = ((double)i+(double)j)/2.0;

        // set cost matrix
        EdgeCostMatrix.setMatrix(costMatrix);
        // edges for different test cases
        int[][] chrom1Edges = { {0,4}, {4,5}, {5,2},  {2,6},  {6,7}, {7,8},
                                {8,3}, {3,9}, {9,10}, {10,1}, {1,0} };
        int[][] chrom2Edges = { {0,4}, {4,1}, {1,6}, {6,2}, {2,8}, {8,10},
                                {0,5}, {5,3}, {3,7}, {7,9}, {9,10} };
        int[][] chrom3Edges = { {0,4}, {4,1}, {1,7}, {7,10},
                                {0,5}, {5,2}, {2,8}, {8,10},
                                {0,6}, {6,3}, {3,9}, {9,10} };
        int[][] chrom4Edges = { {1,0}, {0,5}, {5,2}, {2,7}, {7,9},  {9,3},
                                {1,4}, {4,6}, {6,2}, {2,8}, {8,10}, {10,3} };

        // Array of edgelists
        int[][][] allEdges = new int[][][]{ chrom1Edges, chrom2Edges,
                                            chrom3Edges, chrom4Edges };

        SetOfChromosomes set = new SetOfChromosomes();

        int numServers = 3, numClients = 8;
        ArrayList cells, cell;
        Chromosome temp;
        
        Debugger.enableTrace(false);
        
        for (int i = 0; i < allEdges.length; i++) {
            cells = new ArrayList();
            /**
             * for every node in the network, create a list to store   
             * destination nodes.
             */
            for(int j = 0; j < nodeList.size(); j++) 
                cells.add(new ArrayList());

            if(allEdges[i] != null) {
                // add adjacent nodes  
                for(int j = 0; j < allEdges[i].length; j++) {
                    cell = (ArrayList) cells.get(allEdges[i][j][0]);
                    cell.add(nodeList.get(allEdges[i][j][1]));
                }
            }                        
            temp = new Chromosome(cells, nodeList, numServers, numClients); 
            set.addChromo(temp);
        }
        Debugger.enableTrace(true);
        return set;
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


    /**
     * Tests the three basic accessor methods in BFSResistance. 
     */
    private static void testAccessor(SetOfChromosomes pop) {
        Debugger.debug("Testing accessor methods");
        Debugger.debug("========================\n");

        // setup BFSResistance        
        Debugger.enableTrace(false);
        BFSResistance bfsEngine = new BFSResistance();
        Chromosome chrom = pop.getChromo(3);
        bfsEngine.getResistance(chrom);
        Debugger.enableTrace(true);

        //-------------------------------------------------------------------
        ArrayList visited = new ArrayList();
        visited.add(new NodeImp());

        BFSNode bNode = new BFSNode(bfsEngine, 0, visited, new ArrayList());

        Debugger.debug(testName = "Testing addToQueue");
        try {
            bfsEngine.addToQueue(bNode);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " + 
                "SHOULD have been caught, since the \n    corresponding " + 
                "graph node does not exist in the graph");
        } catch ( PreconditionException pe ) {
            Debugger.debug("Exception caught as expected, since the " +
                "coresponding graph node\n    does not exist in the graph");
        }

        // prepare parameters for creating the node
        double cost = EdgeCostMatrix.getCost(0,5) + 
                        EdgeCostMatrix.getCost(5,2);
        visited = new ArrayList();
        visited.add(chrom.getNodeList().get(0));
        visited.add(chrom.getNodeList().get(5));
        visited.add(chrom.getNodeList().get(2));
        // obtain the list of reachable nodes
        ArrayList reachable = (ArrayList) chrom.getDataArray().get(2);
        bNode = new BFSNode(bfsEngine, cost, visited, reachable);

        Debugger.debug(testName = "Testing addToQueue");
        try {
            bfsEngine.addToQueue(bNode);
            Debugger.debug("Exception not caught as expected, since the " +
                "coresponding graph node exist in\n    the graph");
        } catch ( PreconditionException pe ) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " + 
                "SHOULD NOT have been caught, since\n    the " +
                "corresponding graph node does exist in the graph");
        }

        //-------------------------------------------------------------------
        ArrayList nodes = chrom.getNodeList();
        Debugger.debug(testName = "Testing getCost");
        try {
            bfsEngine.getCost(new NodeImp(), (Node) nodes.get(0));
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " + 
                "SHOULD have been caught, since the \n    corresponding " + 
                "from node does not exist in the graph");
        } catch ( PreconditionException pe ) {
            Debugger.debug("Exception caught as expected, since the " +
                "coresponding from node\n    does not exist in the graph");
        }

        Debugger.debug(testName = "Testing getCost");
        try {
            bfsEngine.getCost((Node) nodes.get(0), new NodeImp());
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " + 
                "SHOULD have been caught, since the \n    corresponding " + 
                "to node does not exist in the graph");
        } catch ( PreconditionException pe ) {
            Debugger.debug("Exception caught as expected, since the " +
                "coresponding to node\n    does not exist in the graph");
        }

        Debugger.debug(testName = "Testing getCost");
        try {
            cost = bfsEngine.getCost((Node) nodes.get(0), 
                                            (Node) nodes.get(5) );
            Debugger.debug("Exception not caught as expected, since the " +
                "coresponding nodes\n    do exist in the graph");
            if (cost != 2.5) Debugger.printErr(testName + ":\n    ERROR!!! " +
                "The returned cost is incorrect");
            
        } catch ( PreconditionException pe ) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " + 
                "SHOULD NOT have been caught, since the\n    " + 
                "corresponding nodes do exist in the graph");
        }
        
        //-------------------------------------------------------------------
        Debugger.debug(testName = "Testing getReachable");
        
        reachable = bfsEngine.getReachable(new NodeImp());
        if (reachable != null) 
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " + 
                "SHOULD have been caught, since the \n    corresponding " + 
                "node does not exist in the graph");
        else 
            Debugger.debug("Exception caught as expected, since the " +
                "coresponding node\n    does not exist in the graph");


        Debugger.debug(testName = "Testing getReachable");
        reachable = bfsEngine.getReachable((Node) nodes.get(0) );
        if (reachable != null) 
            Debugger.debug("Exception not caught as expected, since the " +
                "coresponding node\n    does exist in the graph");
        else
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " + 
                "SHOULD NOT have been caught, since the\n    " + 
                "corresponding node does exist in the graph");
        
        Debugger.debug("Testing accessor methods Completed");
        Debugger.debug("==================================");
    }

    /**
     * Verifies the result of calculating the resistance
     */
    private static void testGetResistance(SetOfChromosomes pop) {
        Debugger.debug(testName = "Testing getResistance");
        Debugger.debug("=====================\n");        
        /**
         * create two instances-- one to use unit edge cost and one to use
         * the actual edge cost
         */
        BFSResistance[] bfsEngines = new BFSResistance[2];
        bfsEngines[0] = new BFSResistance(true);
        bfsEngines[1] = new BFSResistance(false);
        
        Chromosome chrom;
        // Run the population through each variant of BFSResistance 
        for(int i = 0; i < bfsEngines.length; i++) {
            for(int j = 0; j < pop.getPopSize(); j++) {
                chrom = pop.getChromo(j);
                printMatrix(chrom.getAdjacencyMatrix());
                printMatrix(bfsEngines[i].getResistance(chrom));
            }
        }
        
        Debugger.debug(testName + " Completed");
        Debugger.debug("===============================");        
    }

    private static void printMatrix(double[][] matrix) {
        String line = "";
	    
        for(int col = 0; col < matrix.length; col++ ) line += "\t" + col;
        Debugger.debug(line);
        
        for (int i = 0; i < matrix.length; i++) {
            line = Integer.toString(i);
            for(int j = 0; j < matrix[i].length; j++) 
                                line += "\t" + double2String(matrix[i][j]);
            Debugger.debug(line);
        }
        Debugger.debug("");
    }

    private static String double2String(double d) {
        if (d == Double.POSITIVE_INFINITY) return "-";        
        String num = Double.toString(d);        
        if (num.length() > 6) {
            num = num.substring(0,6);
            if (num.endsWith(".")) num = num.substring(0,5);
        }        
        return num;
    }
}
	
