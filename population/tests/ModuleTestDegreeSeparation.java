import utility.*;
import population.*;
import population.graph.*;
import java.util.*;

/**
 * This is the module test suite for Degree Separation--a function to
 * determine the number of hops between any pairs of nodes. This function 
 * consist of modes to compute average and maximum degree of separation, 
 * both will be verified in this test suite.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.8
 */
public class ModuleTestDegreeSeparation {
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
    public ModuleTestDegreeSeparation() {
        Debugger.printErr("Don't instantiate a test class: " + 
                            "ModuleTestDegreeSeparation");
    }
    
    /**
     * The main method for this class. 
     */
    public static void main(String[] args) {
        // pipe results to file
        Debugger.pipeResult("DegreeSeparationNormal.txt",
                            "DegreeSeparationError.txt");
        Debugger.enableTrace(true);                             

        Debugger.debug("\n====================================\n"+
                         "filename: DegreeSeparationNormal.txt\n" +
                         "====================================");
        Debugger.debug("Module Test for population.DegreeSeparation:\n");
        
        Debugger.printErr("\n===================================\n"+
                            "filename: DegreeSeparationError.txt\n" +
                            "===================================");
        Debugger.printErr("Module Test for population.DegreeSeparation:\n");

        
        DegreeSeparation[] ds = new DegreeSeparation[3];
        ds[0] = testDefaultConstructor();
        Debugger.debug("");
        
        boolean doAvg = false;
        ds[1] = testStandardConstructor(doAvg);
        Debugger.debug("");
        
        doAvg = true;
        ds[2] = testStandardConstructor(doAvg);
        Debugger.debug("");
        
        testConvertMatrix(ds);
        Debugger.debug("");
        
        testApplication(ds);
        Debugger.debug("");
        
        Debugger.debug("==========================================");
        Debugger.debug("Module Test for DegreeSeparation Completed");
        Debugger.debug("==========================================");
        Debugger.printErr("==========================================");
        Debugger.printErr("Module Test for DegreeSeparation Completed");
        Debugger.printErr("==========================================");
    }

    
    private static DegreeSeparation testDefaultConstructor() {
        testName = "Testing to create default instance DegreeSeparation";
        Debugger.debug(testName);
        Debugger.debug("===================================================");

        DegreeSeparation ds = new DegreeSeparation();
        if(ds != null)
            Debugger.debug("    A default instance has been created");
        else
            Debugger.printErr(testName + "\n    !!!ERROR occured " +
                "when instantiating a default instance of DegreeSeparation");
        
        return ds;
    }


    private static DegreeSeparation testStandardConstructor(boolean doAvg) {
        testName = "Testing to create standard instance DegreeSeparation";
        Debugger.debug(testName);
        Debugger.debug("====================================================");
        Debugger.debug("doAvg = " + doAvg);
        DegreeSeparation ds = new DegreeSeparation(doAvg);
        if(ds != null)
            Debugger.debug("    A standard instance has been created");
        else
            Debugger.printErr(testName + "\n    !!!ERROR occured " +
                "when instantiating a standard instance of DegreeSeparation");
        
        return ds;
    }

    private static void testConvertMatrix(DegreeSeparation[] ds)  {
        Debugger.debug(testName = "Testing adj2linkMatrix method");
        Debugger.debug("=============================");        
        
        double[][] result;
        
        // create matrices for test
        double[][][] m = new double[5][][];;
        m[0] = new double[][] { {0.0,  oo, 0.0},  { 0.0, 0.0, 123} };
        m[1] = new double[][] { {0.0, 5.0}, {0.5, 0.0}, {-70, 0.0} };
        m[2] = new double[][] { {0.0, 0.0, 1.0}, {0.0, 2.0}, {3.0} };
        m[3] = new double[][] { {0.0, 6.0, 3.0,  oo,  oo},
                                {6.0, 0.0, 2.0, 4.0, 1.0},
                                {3.0, 2.0, 0.0, 1.0, 6.0},
                                { oo, 4.0, 1.0, 0.0, 5.0},
                                { oo, 1.0, 6.0, 5.0, 0.0} };
        m[4] = new double[][] { {0.0, 1.0, 1.0,  oo,  oo},
                                {1.0, 0.0, 1.0, 1.0, 1.0},
                                {1.0, 1.0, 0.0, 1.0, 1.0},
                                { oo, 1.0, 1.0, 0.0, 1.0},
                                { oo, 1.0, 1.0, 1.0, 0.0} };
        boolean[] shouldCatch = {true, true, true, false, false};
        
        String[] subTest = { "with a 2 x 3 matrix", 
                             "with a 3 x 2 matrix",
                             "with an array of 3 arrays,\nhaving 3, 2, 1 " +
                                "elements respectively",
                             "with a 5 x 5 matrix", 
                             "with a 5 x 5 matrix" };
        
        for (int i = 0; i < m.length; i++) {
            Debugger.debug(testName + " " + subTest[i]);
            
            result = ds[0].adj2linkMatrix(m[i]);
            printResult(m[i], result);
        }
        
        Debugger.debug(testName + " Completed");
        Debugger.debug("=======================================");                    
    }




    private static void testApplication(DegreeSeparation[] ds) {
        Debugger.debug(testName = "Testing function application");
        Debugger.debug("============================\n");
        
        Debugger.debug("Creating a population with 1 known chromosome and " +
                        "8 random ones...\n");
        SetOfChromosomes popA = createPop(9);
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
        for (int j = 0; j < ds.length; j++) {
            Debugger.enableTrace(true);
            Debugger.debug("Using the " + fName[j] + "...");
            Debugger.debug("Apply the function to each of the chromosomes " +
                            "in the first population...\n");
            Debugger.enableTrace(false);
        
            double[] fitnessArr1 = new double[popA.getPopSize()];
            for (int i = 0; i < popA.getPopSize(); i++) {
                ds[j].apply( popA.getChromo(i), arrIndex);
                fitnessArr1[i] = (popA.getChromo(i)).getFitArrElem(arrIndex);
            }

            Debugger.enableTrace(true);
            Debugger.debug("Map the function onto the second "+
                            "population...\n");
            Debugger.enableTrace(false);
        
            // start testing the function
            ds[j].map(popB, arrIndex);

            double[] fitnessArr2 = new double[popB.getPopSize()];
            for (int i = 0; i < popB.getPopSize(); i++) 
                fitnessArr2[i] = (popB.getChromo(i)).getFitArrElem(arrIndex);
        
            Debugger.enableTrace(true);

            Debugger.debug("Comparing the results of Apply and Map...\n    " +
                           "[Note: Result should be equal for corressponding " +
                           "pairs of chromosomes.]" );
            compareArrays(fitnessArr1, fitnessArr2);
        }
    
        Debugger.debug(testName + " Completed");
        Debugger.debug("======================================");        
    }

    private static void compareArrays(double[] a, double[] b) {
        String line = "\n";
        for (int i = 0; i < a.length; i++) {
            line += "!!! " + i + "\t" + a[i];
            if (a[i] != b[i]) line += "\t=X=\t";
            else              line += "\t = \t";
            line += "" + b[i] + "\n";
        }
        Debugger.debug(line +"!!! =============\n");
    }

    private static SetOfChromosomes createPop(int numChromo) {
        SetOfChromosomes soc = new SetOfChromosomes();
        
        Debugger.debug("Constructing Chromosome with 11 Nodes and 20 Edges");
        
        Debugger.enableTrace(false);
        
        ArrayList nodeList = new ArrayList();
        ArrayList cells    = new ArrayList();
        
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
      
        /**
         * for every node that exist in the network, create a list to store
         * another list of destination nodes.
         */
        for(int i = 0; i < nodeList.size(); i++) cells.add(new ArrayList());
        // add adjacent nodes        
        
        int[][] edges = { { 2, 3}, { 2, 4}, { 4, 2}, { 5, 3}, { 3, 2}, 
                          { 0, 3}, { 2, 0}, { 0, 7}, { 1, 0}, { 0, 1},
                          { 8, 3}, { 9, 3}, {10, 3}, { 4, 3}, { 6, 7},
                          { 1, 7}, { 2, 6}, { 7, 2}, { 5, 2}, { 2, 5} };
        ArrayList cell;
        for(int i = 0; i < edges.length; i++) {
            cell = (ArrayList) cells.get(edges[i][0]);
            cell.add(nodeList.get(edges[i][1]));
        }
        
        // create cost matrix
        double[][] cost = new double[nodeList.size()][nodeList.size()];
        for (int i = 0; i < cost.length; i++) {
            for(int j = 0; j < cost.length; j++) {
                if  (i == j) continue;
                cost[i][j] = distBetween( (Node) nodeList.get(i), 
                                          (Node) nodeList.get(j) );
            }
        }
        int src, dest;
        for (int i = 0; i < edges.length; i++) {
            src = edges[i][0];
            dest = edges[i][1];
            cost[src][dest] = distBetween( (Node) nodeList.get(src), 
                                           (Node) nodeList.get(dest) );
        }
        EdgeCostMatrix.setMatrix(cost);
                
        soc.addChromo(new Chromosome(cells, nodeList, 3, 8));
        
        int nodesToGen = nodeList.size(), edgesToGen, serversToGen, x, y;
        
        for (int j = 1; j < numChromo; j++) {
            edgesToGen = nodesToGen + rand.nextInt(nodesToGen*nodesToGen/2);
            Debugger.enableTrace(true);
            Debugger.debug("Constructing Chromosome with "+ nodesToGen+
                    " Nodes and " + edgesToGen + " Edges");
            Debugger.enableTrace(false);

            cells    = new ArrayList();        
            
            
            /**
             * for every node that exist in the network, create a list to store
             * another list of destination nodes.
             */
            for(int i = 0; i < nodesToGen; i++) cells.add(new ArrayList());
            
            for (int i = 0; i < edgesToGen; i++) {
                // obtain source node
                x = rand.nextInt(nodesToGen);
                cell = (ArrayList) cells.get(x);
                /**
                 * try to find a destination node, find another if the
                 * source is the same as the destination node, or if the
                 * source already connects to the destination node, and there
                 * are still nodes that the source hasn't been connected to.
                 */
                do {
                    y = rand.nextInt(nodesToGen); 
                } while (x == y || 
                        ( cell.contains(nodeList.get(y)) && 
                            cell.size() < (nodesToGen-1) ) );
                
                cell.add(nodeList.get(y));              
            }
            
            soc.addChromo(new Chromosome(cells, nodeList, 3, 8));
        }
        Debugger.enableTrace(true);
        return soc;
    }

    /**
     * Calculates the cartesian distance between 2 nodes using Pythagoras
     * theorem. (Implemented by Andy 01/04/05)
     *
     * @param   a The source node
     * @param   b The destination node
     * @return  the cartesian distance between 2 nodes
     */
    private static double distBetween(Node a, Node b) {
        int[] aCoord = a.getCoordinates();
        int[] bCoord = b.getCoordinates();
        int x = aCoord[0] - bCoord[0];
        int y = aCoord[1] - bCoord[1];
        return Math.sqrt( (x*x) + (y*y) );
    }

    /**
     * Prints out the result of applying a function for a chromosome. This
     * should only be used in the testsuite that extends this class. The
     * original matrix of the graph should be printed with the new matrix for
     * easier comparison.<p>
     *
     * We assume that the matrix is a square matrix and the width and height
     * of the matrix are equal to the length of the chromosome (the number of
     * nodes in the network). 
     *
     * @param   matrixA the original matrix
     * @param   matrixB the new matrix with the minimal graph
     */
	private static void printResult(double[][] matrixA, double[][] matrixB) {
	    Debugger.debug("Before:");
	    printMatrix(matrixA);
	    Debugger.debug("After:");
        printMatrix(matrixB);
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