import utility.*;
import population.*;
import population.graph.*;
import java.util.*;

/**
 * Verifies the functionality of the TotalEdgeCost class, which is a
 * "Function" that computes the total cost of edges in the graph.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.8
 */
public class ModuleTestTotalEdgeCost {
    // to store info regarding the current test
    private static String testName;
    
    // Default constructor
    /**
     * One should not instantate a test class
     */
    public ModuleTestTotalEdgeCost() {
        Debugger.printErr("Don't instantiate a test class: " + 
                            "ModuleTestTotalEdgeCost");
    }
    
    /**
     * The main method for this class. Firstly, 2 sets of chromosomes are 
     * generated. The first is used to test that apply and map produces the
     * same results. The second set is used to test that the summing of the 
     * edges via a matrix is consistent with the graph method.
     */
    public static void main(String[] args) {
        // pipe results to file
        Debugger.pipeResult("TotalEdgeCostNormal.txt",
                            "TotalEdgeCostError.txt");
        Debugger.enableTrace(true);                             

        Debugger.debug("\n=================================\n"+
                         "filename: TotalEdgeCostNormal.txt\n" +
                         "=================================");
        Debugger.debug("Module Test for population.TotalEdgeCost:\n");
        
        Debugger.printErr("\n================================\n"+
                            "filename: TotalEdgeCostError.txt\n" +
                            "================================");
        Debugger.printErr("Module Test for population.TotalEdgeCost:\n");

        SetOfChromosomes[] sets = createPop();
        Debugger.debug("");
        
        testApplication(sets[0]);
        Debugger.debug("");
        
        checkAndTime(sets[1]);
        Debugger.debug("");
        
        Debugger.debug("=======================================");
        Debugger.debug("Module Test for TotalEdgeCost Completed");
        Debugger.debug("=======================================");
        Debugger.printErr("=======================================");
        Debugger.printErr("Module Test for TotalEdgeCost Completed");
        Debugger.printErr("=======================================");
    }

    /**
     * Checks that if the method map and apply produces the same result
     */
    private static void testApplication(SetOfChromosomes popA) {
        Debugger.debug(testName = "Testing function application");
        Debugger.debug("============================\n");

        Function tec = new TotalEdgeCost();
        
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
        
        Debugger.enableTrace(true);
        Debugger.debug("Apply the function to each of the chromosomes " +
                            "in the first population...\n");
        
        double[] fitnessArr1 = new double[popA.getPopSize()];
        Debugger.enableTrace(false);
         
        for (int i = 0; i < popA.getPopSize(); i++) {
            tec.apply( popA.getChromo(i), arrIndex);
            fitnessArr1[i] = (popA.getChromo(i)).getFitArrElem(arrIndex);
        }

        Debugger.enableTrace(true);
        Debugger.debug("Map the function onto the second population...\n");
        Debugger.enableTrace(false);
        
        // start mapping the function
        tec.map(popB, arrIndex);

        double[] fitnessArr2 = new double[popB.getPopSize()];
        for (int i = 0; i < popB.getPopSize(); i++) 
            fitnessArr2[i] = (popB.getChromo(i)).getFitArrElem(arrIndex);
        
        Debugger.enableTrace(true);

        Debugger.debug("Comparing the results of Apply and Map...\n    " +
                       "[Note: Result should be equal for corressponding " +
                       "pairs of chromosomes.]" );
        compareArrays(fitnessArr1, fitnessArr2);
        
    
        Debugger.debug(testName + " Completed");
        Debugger.debug("======================================");        
    }

    /**
     * Checks that if the matrix method and the graph method produce the same
     * result, and compare their time of execution.
     */
    private static void checkAndTime(SetOfChromosomes popA) {
        Debugger.debug(testName = "Testing function application");
        Debugger.debug("============================\n");

        long startTime, finishTime, timeMatrix, timeGraph;

        Function tec = new TotalEdgeCost();
        
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
        
        Debugger.enableTrace(true);
        Debugger.debug("Map the function to each of the chromosomes " +
                            "in the first population...\n");
        
        double[] fitnessArr1 = new double[popA.getPopSize()];
        Debugger.enableTrace(false);
        
        startTime = System.currentTimeMillis();
        tec.map(popA, arrIndex);
        finishTime = System.currentTimeMillis();

        timeMatrix = finishTime - startTime;
       
        for (int i = 0; i < popA.getPopSize(); i++)
            fitnessArr1[i] = (popA.getChromo(i)).getFitArrElem(arrIndex);
        
        Debugger.enableTrace(true);
        Debugger.debug("Calculate the cost of the second population...\n");
        Debugger.enableTrace(false);
        
        // obtain graph can check the result of the function are consistent
        Chromosome tempChromo;
        Graph tempGraph;
        double sum;
      
        startTime = System.currentTimeMillis();
        for (int i = 0; i < popB.getPopSize(); i++) {
            tempChromo = popB.getChromo(i);
            tempGraph  = tempChromo.getGraph();
            sum = 0;
            // Debugger.enableTrace(true);
            // Debugger.printErr("number of edges in graph = " + tempGraph.numEdges());
            // Debugger.enableTrace(false);            
            for (int j = 0; j < tempGraph.numEdges(); j++)
                sum += tempGraph.getEdge(j).getCost();                
            tempChromo.insertIntoFitArr(arrIndex, sum);        
        }
        finishTime = System.currentTimeMillis();

        timeGraph = finishTime - startTime;
        
        double[] fitnessArr2 = new double[popB.getPopSize()];
        for (int i = 0; i < popB.getPopSize(); i++) 
            fitnessArr2[i] = (popB.getChromo(i)).getFitArrElem(arrIndex);
        
        Debugger.enableTrace(true);

        Debugger.debug("Cross-checking the results of Map...\n    " +
                       "[Note: Result should be equal for corressponding " +
                       "pairs of chromosomes.]" );
        compareArrays(fitnessArr1, fitnessArr2);
        
        Debugger.debug("Matrix method completed in " + timeMatrix + "ms");
        Debugger.debug("Graph method completed in  " + timeGraph + "ms");
    
        Debugger.debug(testName + " Completed");
        Debugger.debug("======================================");        
    }

    /**
     * Create predetermined chromosomes for testing functions
     */
    private static SetOfChromosomes[] createPop() {

        Debugger.debug("Constructing Chromosomes for test Population");          
        ArrayList nodeList = genNodeList();
        Debugger.enableTrace(false); 
        // create cost matrix
        EdgeCostMatrix.populateMatrix(nodeList.size(), 1000);
      
        // edges for different test cases
        int[][] chrom1Edges = { { 2, 3}, { 2, 4}, { 4, 2}, { 5, 3}, { 3, 2}, 
                                { 0, 3}, { 2, 0}, { 0, 7}, { 1, 0}, { 0, 1},
                                { 8, 3}, { 9, 3}, {10, 3}, { 4, 3}, { 6, 7},
                                { 1, 7}, { 2, 6}, { 7, 2}, { 5, 2}, { 2, 5} };
        int[][] chrom2Edges = { { 3, 0}, { 3, 4}, { 3, 7} };
        int[][] chrom3Edges = { { 3, 0}, { 3, 4}, { 3, 7}, 
                                { 2, 4}, { 2, 7}, {2, 9} };
        int[][] chrom4Edges = { { 1, 0}, { 2, 0} };
        int[][] chrom5Edges = { { 1, 0}, { 2, 0}, { 3, 0}, 
                                { 1, 4}, { 2, 4}, {3, 4} };
        int[][] chrom6Edges = { };

        int[][] chrom7Edges = { {0,2}, {0,3}, {1,7}, {2,1}, {2,5}, 
                         {2,7}, {3,2}, {3,10}, {4,2}, {4,3}, {6,2}, {6,5},
                         {7,6}, {8,3}, {9,0}, {9,3}, {9,1}, {1,0} };
        int[][] chrom8Edges = { {7,3}, {1,7}, {2,6}, {8,1}, {1,9}, 
                         {1,0}, {3,8}, {4,2}, {7,2}, {3,10}, {3,0}, {3,9},
                         {3,4}, {2,5}, {3,2} };
        int[][] chrom9Edges = { {0,3}, {1,2}, {2,1}, {2,3}, {3,2},
                         {4,2}, {5,2}, {6,2}, {6,7}, {7,1}, {8,3}, {9,3},
                         {10,3}, {3,0} };
        int[][] chrom10Edges = { {0,1}, {1,2}, {2,3}, {2,5}, {3,4},
                         {4,5}, {5,6}, {6,7}, {7,1}, {7,8}, {8,9}, {9,0},
                         {9,1}, {9,10}, {10,3}, {10,4} };        
        int[][] chrom11Edges = { {0,1}, {0,3}, {1,7}, {2,3}, {2,5}, 
                         {2,6}, {3,0}, {3,2}, {3,9}, {4,5}, {4,10}, {5,4},
                         {6,2}, {6,7}, {7,1}, {7,6}, {8,9}, {8,10}, {9,3},
                         {9,8}, {10,4}, {10, 8}, {1,0}, {5,2} };
        int[][] chrom12Edges = { {0,9}, {1,9}, {2,3}, {2,7}, {3,0}, 
                         {3,2}, {4,5}, {5,2}, {5,6}, {6,1}, {7,3}, {8,10},
                         {9,8}, {10,4} };    

        // Array of edgelists
        int[][][] allEdges = new int[][][]{ chrom1Edges, chrom2Edges,
                  chrom3Edges, chrom4Edges, chrom5Edges, chrom6Edges,
                  chrom7Edges, chrom8Edges, chrom9Edges, chrom10Edges,
                  chrom11Edges, chrom12Edges };

        SetOfChromosomes[] sets = new SetOfChromosomes[2];
        sets[0] = new SetOfChromosomes();
        sets[1] = new SetOfChromosomes();

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
                        
            setNumber = (int) ( (double)i/(double)6 );
            
//            Debugger.printErr("Creating chromosome " + i + " and add " +
//                "it to set number " + setNumber);
            
            temp = new Chromosome(cells, nodeList, numServers, numClients); 
            sets[setNumber].addChromo(temp);
        }
        
        Debugger.enableTrace(true);
// This can be uncommented to print information regarding the matrices
//        for (int i = 0; i < dim; i++) 
//            for (int j = 0; j < dim; j++) 
//                reference[i][j] = EdgeCostMatrix.getCost(i,j);
//        for (int i = 0; i < sets[0].length; i++) printChromo(sets[0][i]);        
//        for (int i = 0; i < sets[1].length; i++) printChromo(sets[1][i]);                

        return sets;
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
    
    private static void compareArrays(double[] a, double[] b) {
        String line = "\n";
        for (int i = 0; i < a.length; i++) {
//          line += "!!! " + i + "\t" + a[i];
            line += "!!! " + i + "\t" + double2String(a[i]);
            if ( !closeEnough(a[i],b[i]) ) line += "\t=X=\t";
            else              line += "\t = \t";
//            line += "" + b[i] + "\n";
            line += "" + double2String(b[i]) + "\n";
        }
        Debugger.debug(line +"!!! =============\n");
    }

    private static boolean closeEnough(double a, double b) {
        double diff = Math.abs(b - a);
        if (diff * 1000000 < 1.0) return true;
        else return false;
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

}
