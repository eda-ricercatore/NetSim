import utility.*;
import population.*;
import population.graph.*;
import java.util.*;

/**
 * Tests the function which calculates the average costs of node-to-node 
 * minimal paths or the cost of the minimal spanning tree. This test suite
 * is to make sure that the correct result is produced for whichever mode.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.8
 */
public class ModuleTestMinimalPaths {
    // to store info regarding the current test
    private static String testName;
    private static Random rand = new Random();
    private static final double oo = Double.POSITIVE_INFINITY;
    private static String[] fName = { "Default instance",
                                      "Standard instance with doAvg = false",
                                      "Standard instance with doAvg = true" };
    // Default constructor
    /**
     * One should not instantate a test class
     */
    public ModuleTestMinimalPaths() {
        Debugger.printErr("Don't instantiate a test class: " + 
                            "ModuleTestMinimalPaths");
    }
    
    /**
     * The main method for this class. 
     */
    public static void main(String[] args) {
        // pipe results to file
        Debugger.pipeResult("MinimalPathsNormal.txt",
                            "MinimalPathsError.txt");
        Debugger.enableTrace(true);                             

        Debugger.debug("\n================================\n"+
                         "filename: MinimalPathsNormal.txt\n" +
                         "================================");
        Debugger.debug("Module Test for population.MinimalPaths:\n");
        
        Debugger.printErr("\n===============================\n"+
                            "filename: MinimalPathsError.txt\n" +
                            "===============================");
        Debugger.printErr("Module Test for population.MinimalPaths:\n");

        
        MinimalPaths[] mps = new MinimalPaths[3];
        mps[0] = testDefaultConstructor();
        Debugger.debug("");
        
        boolean doAvg = false;
        mps[1] = testStandardConstructor(doAvg);
        Debugger.debug("");
        
        doAvg = true;
        mps[2] = testStandardConstructor(doAvg);
        Debugger.debug("");
        
        testApplication(mps);
        Debugger.debug("");
        
        Debugger.debug("======================================");
        Debugger.debug("Module Test for MinimalPaths Completed");
        Debugger.debug("======================================");
        Debugger.printErr("======================================");
        Debugger.printErr("Module Test for MinimalPaths Completed");
        Debugger.printErr("======================================");
    }

    private static MinimalPaths testDefaultConstructor() {
        testName = "Testing to create default instance MinimalPaths";
        Debugger.debug(testName);
        Debugger.debug("===================================================");

        MinimalPaths mp = new MinimalPaths();
        if(mp != null)
            Debugger.debug("    A default instance has been created");
        else
            Debugger.printErr(testName + "\n    !!!ERROR occured " +
                "when instantiating a default instance of MinimalPaths");
        
        return mp;
    }

    private static MinimalPaths testStandardConstructor(boolean doAvg) {
        testName = "Testing to create standard instance MinimalPaths";
        Debugger.debug(testName);
        Debugger.debug("====================================================");
        Debugger.debug("doAvg = " + doAvg);
        MinimalPaths mp = new MinimalPaths(doAvg);
        if(mp != null)
            Debugger.debug("    A standard instance has been created");
        else
            Debugger.printErr(testName + "\n    !!!ERROR occured " +
                "when instantiating a standard instance of MinimalPaths");
        
        return mp;
    }

    /**
     * Checks for the consistency between map and apply methods. The result
     * should be the same. 
     */
    private static void testApplication(MinimalPaths[] mps) {
        Debugger.debug(testName = "Testing function application");
        Debugger.debug("============================\n");
        
        Debugger.debug("Creating a population with 7 chromosome...\n");
        SetOfChromosomes popA = createPop();
        
        // make a copy of the population
        Debugger.debug("\nCopying the population for later use...\n");
        Debugger.enableTrace(false);
        SetOfChromosomes popB = new SetOfChromosomes();
        for (int i = 0; i < popA.getPopSize(); i++) {
            popA.getChromo(i).createFitnessArr(1);
            popB.addChromo( (Chromosome) (popA.getChromo(i)).clone() );
            popB.getChromo(i).createFitnessArr(1);
        }
        
        int arrIndex = 0;
        
        for (int j = 0; j < mps.length; j++) {
            Debugger.enableTrace(true);
            Debugger.debug("Using the " + fName[j] + "...");
            Debugger.debug("Apply the function to each of the chromosomes " +
                            "in the first population...\n");
            Debugger.enableTrace(false);
        
            double[] fitnessArr1 = new double[popA.getPopSize()];
            for (int i = 0; i < popA.getPopSize(); i++) {
                mps[j].apply( popA.getChromo(i), arrIndex);
                fitnessArr1[i] = (popA.getChromo(i)).getFitArrElem(arrIndex);
            }

            Debugger.enableTrace(true);
            Debugger.debug("Map the function onto the second "+
                            "population...\n");
            Debugger.enableTrace(false);
        
            // start testing the function
            mps[j].map(popB, arrIndex);

            double[] fitnessArr2 = new double[popB.getPopSize()];
            for (int i = 0; i < popB.getPopSize(); i++) 
                fitnessArr2[i] = (popB.getChromo(i)).getFitArrElem(arrIndex);
        
            Debugger.enableTrace(true);

            Debugger.debug("Comparing the results of Apply and Map..." +
                       "\n    [Note: Result should be equal for " +
                       "corressponding pairs of chromosomes.]" );
            compareArrays(fitnessArr1, fitnessArr2);
        }
    
        Debugger.debug(testName + " Completed");
        Debugger.debug("======================================");        
    }

    private static void compareArrays(double[] a, double[] b) {
        String line = "\n";
        for (int i = 0; i < a.length; i++) {
            line += "!!! " + i + "\t" + double2String(a[i]);
            if (a[i] != b[i]) line += "\t=X=\t";
            else              line += "\t = \t";
            line += "" + double2String(b[i]) + "\n";
        }
        Debugger.debug(line +"!!! =============\n");
    }

	/*
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
	}*/
	
	private static String double2String(double d) {
        if (d == Double.POSITIVE_INFINITY) return "-";        
        String num = Double.toString(d);        
        if (num.length() > 6) {
            num = num.substring(0,6);
            if (num.endsWith(".")) num = num.substring(0,5);
        }        
        return num;
    }

    /**
     * Create predetermined chromosomes for testing functions
     */
    private static SetOfChromosomes createPop() {

        Debugger.debug("Constructing Chromosomes for test Population");          
        ArrayList nodeList = genNodeList();
        Debugger.enableTrace(false); 
        // create cost matrix
        EdgeCostMatrix.populateMatrix(nodeList.size(), 1000);
      
        // edges for different test cases
        int[][] chrom1Edges = { {2,3}, {2,4}, { 4,2}, {5,3}, {3,2}, 
                                {0,3}, {2,0}, { 0,7}, {1,0}, {0,1},
                                {8,3}, {9,3}, {10,3}, {4,3}, {6,7},
                                {1,7}, {2,6}, { 7,2}, {5,2}, {2,5} };
        int[][] chrom2Edges = { {0,2}, {0,3}, {1,7}, {2,1}, {2,5}, 
                         {2,7}, {3,2}, {3,10}, {4,2}, {4,3}, {6,2}, {6,5},
                         {7,6}, {8,3}, {9,0}, {9,3}, {9,1}, {1,0} };
        int[][] chrom3Edges = { {7,3}, {1,7}, {2,6}, {8,1}, {1,9}, 
                         {1,0}, {3,8}, {4,2}, {7,2}, {3,10}, {3,0}, {3,9},
                         {3,4}, {2,5}, {3,2} };
        int[][] chrom4Edges = { {0,3}, {1,2}, {2,1}, {2,3}, {3,2},
                         {4,2}, {5,2}, {6,2}, {6,7}, {7,1}, {8,3}, {9,3},
                         {10,3}, {3,0} };
        int[][] chrom5Edges = { {0,1}, {1,2}, {2,3}, {2,5}, {3,4},
                         {4,5}, {5,6}, {6,7}, {7,1}, {7,8}, {8,9}, {9,0},
                         {9,1}, {9,10}, {10,3}, {10,4} };        
        int[][] chrom6Edges = { {0,1}, {0,3}, {1,7}, {2,3}, {2,5}, 
                         {2,6}, {3,0}, {3,2}, {3,9}, {4,5}, {4,10}, {5,4},
                         {6,2}, {6,7}, {7,1}, {7,6}, {8,9}, {8,10}, {9,3},
                         {9,8}, {10,4}, {10,8}, {1,0}, {5,2} };
        int[][] chrom7Edges = { {0,9}, {1,9}, {2,3}, {2,7}, {3,0}, 
                         {3,2}, {4,5}, {5,2}, {5,6}, {6,1}, {7,3}, {8,10},
                         {9,8}, {10,4} };    

        // Array of edgelists
        int[][][] allEdges = new int[][][]{ chrom1Edges, chrom2Edges,
                               chrom3Edges, chrom4Edges, chrom5Edges, 
                               chrom6Edges, chrom7Edges };

        SetOfChromosomes set = new SetOfChromosomes();

        int numServers = 3, numClients = 8, setNumber;
        ArrayList cells;
        ArrayList cell;
        Chromosome temp;
        
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
// This can be uncommented to print information regarding the matrices
//        for (int i = 0; i < dim; i++) 
//            for (int j = 0; j < dim; j++) 
//                reference[i][j] = EdgeCostMatrix.getCost(i,j);
//        for (int i = 0; i < sets[0].length; i++) printChromo(sets[0][i]);

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
}
