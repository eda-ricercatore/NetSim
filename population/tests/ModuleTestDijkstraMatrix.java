import utility.*;
import population.*;
import population.graph.*;
import java.util.*;

/**
 * This is the module test suite for Dijkstra Matrix. The test is in two
 * parts. Firstly it shall test for the output results of Dijsktra algorithm
 * with familiar input with known output. The calculation of maximum, 
 * average and sum will be checked against predetermined array of arrays. 
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.7
 */
public class ModuleTestDijkstraMatrix {
    // to store info regarding the current test
    private static String testName;
    //private static Random rand = new Random();
    private static final double oo = Double.POSITIVE_INFINITY;
    private static DijkstraMatrix dm;
    
    // Default constructor
    /**
     * One should not instantate a test class
     */
    public ModuleTestDijkstraMatrix() {
        Debugger.printErr("Don't instantiate a test class: " + 
                            "ModuleTestDijkstraMatrix");
    }
    
    /**
     * The main method for this class. 
     */
    public static void main(String[] args) {
        // pipe results to file
        Debugger.pipeResult("DijkstraMatrixNormal.txt",
                            "DijkstraMatrixError.txt");
        Debugger.enableTrace(true);                             
        /**
         * Modified by Andy 09/04/05: to add header for output files for
         * test results.
         */
        Debugger.debug("\n==================================\n"+
                         "filename: DijkstraMatrixNormal.txt\n" +
                         "==================================");
        Debugger.debug("Module Test for population.DijkstraMatrix:\n");
        
        Debugger.printErr("\n==================================\n"+
                            "filename: DijkstraMatrixError.txt\n" +
                            "==================================");
        Debugger.printErr("Module Test for population.DijkstraMatrix:\n");
        
        dm = new DijkstraMatrix();
        
        testDijkstra();
        Debugger.debug("");
        
        testCalculation();
        Debugger.debug("");
        
        //dm.testApplication();
        //Debugger.debug("");
        
        //Debugger.enableTrace(true); // enable trace printing
        
        // Modified by Andy 09/04/05: to emphasise end of test
        Debugger.debug("========================================");
        Debugger.debug("Module Test for DijkstraMatrix Completed");
        Debugger.debug("========================================");
        Debugger.printErr("========================================");
        Debugger.printErr("Module Test for DijkstraMatrix Completed");
        Debugger.printErr("========================================");
    }

    /**
     * Tests the dijkstra algorithm with different input conditions
     */
    private static void testDijkstra()  {
        Debugger.debug(testName = "Testing dijkstra method");
        Debugger.debug("=======================");        
        
        double[][] result;
        
        // create matrices for test
        double[][][] m = new double[5][][];;
        m[0] = new double[][] { {0.0, 0.0, 0.0},  { 0.0, 0.0, 0.0} };
        m[1] = new double[][] { {0.0, 0.0}, {0.0, 0.0}, {0.0, 0.0} };
        m[2] = new double[][] { {0.0, 0.0, 0.0}, {0.0, 0.0}, {0.0} };
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
        String[] msg = { "the matrix is NOT a SQUARE matrix",
                         "the matrix is NOT a SQUARE matrix",
                         "the matrix is NOT a SQUARE matrix",
                         "the matrix IS a square matrix",
                         "the matrix IS a square matrix"      };
        
        for (int i = 0; i < m.length; i++) {
            Debugger.debug(testName + " " + subTest[i]);
            try {
                result = dm.dijkstra(m[i]);
                //Debugger.enableTrace(true);
                if(!shouldCatch[i]) {
                    Debugger.debug("    Exception not caught as expected, " +
                                    "since\n    " + msg[i]);
                } else {
                    Debugger.printErr(testName + ":\n    Exception SHOULD " +
                                    " have been caught, since " + msg[i]);
                }
                printResult(m[i], result);
            } catch (PreconditionException pe) {
                //Debugger.enableTrace(true);
                if(shouldCatch[i]) {
                    Debugger.debug("    Exception caught as expected, " +
                                    "since\n    " + msg[i]);
                } else {
                    Debugger.printErr(testName + ":\n    Exception SHOULD " +
                                    "NOT have been caught, since " + msg[i]);
                }
            }
            Debugger.debug("");
        }
        //Debugger.enableTrace(true);
        Debugger.debug(testName + " Completed");
        Debugger.debug("=================================");                    
    }



    private static void testCalculation(){
        Debugger.debug(testName = "Testing calculation methods");
        Debugger.debug("===========================");        
 	    double[][] m = { {11.987,   49.7, -52.8, -1.089},
 	                     {1.342},
	                     {132.21,    0.0,  -998,     oo},
	                     {   -oo,    405,   0.0, 123456},
	                     {  3589, 457.09, 38.44} };
        Debugger.debug("Matrix used: ");
        printMatrix(m);
        
        //Debugger.enableTrace(true);
        Debugger.debug("Sum     = " + dm.getSum(m));
        Debugger.debug("Average = " + dm.getAverage(m));
        Debugger.debug("Maximum = " + dm.getMax(m));
        
        Debugger.debug("");
        Debugger.debug(testName + " Completed");
        Debugger.debug("=====================================");        
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
	
