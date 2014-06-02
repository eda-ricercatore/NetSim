import utility.*;
import population.*;
import java.util.*;

/**
 * This class tests the functionality of the EdgeCostMatrix. All the 
 * variables and methods of this class are static. Consequently, contents of 
 * the matrix within this class will persist through out the test suite. This 
 * test suite will make use of the method reset to check the states of the 
 * class.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.8
 */
public class ModuleTestEdgeCostMatrix {
    // to store info regarding the current test
    private static String testName;
    private static final double oo = Double.POSITIVE_INFINITY;
    // temporary storage for the reference matrix
    private static double[][] reference = null;
    
    // Default constructor
    /**
     * One should not instantate a test class
     */
    public ModuleTestEdgeCostMatrix() {
        Debugger.printErr("Don't instantiate a test class: " + 
                            "ModuleTestEdgeCostMatrix");
    }
    
    /**
     * The main method for this class. This will initiate tests to set a
     * predetermined matrix, get cost from the matrix, and populate a matrix
     * with a set of "random" number (from a uniform distribution)
     */
    public static void main(String[] args) {
        // pipe results to file
        Debugger.pipeResult("EdgeCostMatrixNormal.txt",
                            "EdgeCostMatrixError.txt");
        Debugger.enableTrace(true);                             

        Debugger.debug("\n==================================\n"+
                         "filename: EdgeCostMatrixNormal.txt\n" +
                         "==================================");
        Debugger.debug("Module Test for population.EdgeCostMatrix:\n");
        
        Debugger.printErr("\n==================================\n"+
                            "filename: EdgeCostMatrixError.txt\n" +
                            "==================================");
        Debugger.printErr("Module Test for population.EdgeCostMatrix:\n");
        
        testSetMatrix();
        Debugger.debug("");        

        testGetCost();
        Debugger.debug("");        

        testPopulateMatrix();
        Debugger.debug("");        

        Debugger.debug("========================================");
        Debugger.debug("Module Test for EdgeCostMatrix Completed");
        Debugger.debug("========================================");
        Debugger.printErr("========================================");
        Debugger.printErr("Module Test for EdgeCostMatrix Completed");
        Debugger.printErr("========================================");
    }

    /**
     * Tests mainly the static methods setMatrix. Various arrays of arrays
     * will be used to setMatrix. Only square matrices can be passed.
     */
    private static void testSetMatrix()  {
        Debugger.debug("Testing setMatrix");
        Debugger.debug("=================\n");
    
        Debugger.debug(testName = "Testing dimension before populating or " +
                            "setting the matrix");
        try {
            EdgeCostMatrix.dimension();
            Debugger.printErr(testName + ":\n    ERROR!!! Exception SHOULD "+
                            "have been caught, since matrix does no exist.");
        } catch (AssertionException ae) {
            Debugger.debug("    Exception caught as expected, since matrix "+
                            "does not exist");
        }

        Debugger.debug(testName = "Testing getCost before populating or " +
                            "setting the matrix");
        try {
            EdgeCostMatrix.getCost(1,1);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception SHOULD "+
                            "have been caught, since matrix does no exist.");
        } catch (AssertionException ae) {
            Debugger.debug("    Exception caught as expected, since matrix "+
                            "does not exist");
        }        
        
    
        Debugger.debug(testName = "Testing setMatrix method");
        Debugger.debug("========================");        
        
        // create 4 matrices for test
        double[][][] m = new double[4][][];
        m[0] = new double[][] { {0.0, 0.0, 0.0},  { 0.0, 0.0, 0.0} };
        m[1] = new double[][] { {0.0, 0.0}, {0.0, 0.0}, {0.0, 0.0} };
        m[2] = new double[][] { {0.0, 0.0, 0.0}, {0.0, 0.0}, {0.0} };
        m[3] = new double[][] { {0.0, 6.0, 3.0,  oo,  oo},
                                {6.0, 0.0, 2.0, 4.0, 1.0},
                                {3.0, 2.0, 0.0, 1.0, 6.0},
                                { oo, 4.0, 1.0, 0.0, 5.0},
                                { oo, 1.0, 6.0, 5.0, 0.0} };
        boolean[] shouldCatch = {true, true, true, false};
        
        String[] subTest = { "with a 2 x 3 matrix", 
                             "with a 3 x 2 matrix",
                             "with an array of 3 arrays,\nhaving 3, 2, 1 " +
                                "elements respectively",
                             "with a 5 x 5 matrix" };
        String[] msg = { "the matrix is NOT a SQUARE matrix",
                         "the matrix is NOT a SQUARE matrix",
                         "the matrix is NOT a SQUARE matrix",
                         "the matrix IS a square matrix"      };
        
        // run sub-tests
        for (int i = 0; i < m.length; i++) {
            Debugger.debug(testName + " " + subTest[i]);
            try {
                EdgeCostMatrix.setMatrix(m[i]);
                //Debugger.enableTrace(true);
                if(!shouldCatch[i]) {
                    Debugger.debug("    Exception not caught as expected, " +
                                    "since\n    " + msg[i]);
                } else {
                    Debugger.printErr(testName + ":\n    Exception SHOULD " +
                                    " have been caught, since " + msg[i]);
                }
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
        Debugger.debug("Storing the last input matrix as reference");
        reference = m[3];
        
        Debugger.debug("Testing setMatrix Completed");
        Debugger.debug("===========================\n");
    }

    /**
     * tests the getCost static method with different illegal and legal 
     * parameters. A matrix is also reconstructed from EdgeCostMatrix, and it
     * is checked to see if it is equal to the one that was set before.
     */
    private static void testGetCost(){
        Debugger.debug("Testing getCost");
        Debugger.debug("===============\n");
                
        int dim = -1;

        Debugger.debug(testName = "Testing dimension");
        try {
            dim = EdgeCostMatrix.dimension();
            Debugger.debug("    Exception not caught as expected, since " +
                    "matrix does exist");
        } catch (AssertionException ae) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD not have been caught, since matrix does exist.");
        }        
        Debugger.debug("");

        //Debugger.enableTrace(true);
        Debugger.debug(testName = "Testing getCost(int, int)");
        Debugger.debug("=========================");
        
        int[][] input = { {   -1, -1 }, {  -1,   1 }, {   1,  -1 },
                          { dim, dim }, { dim,   1 }, {   1, dim } };
        
        
        String[] msg = { "i = " + input[0][0] + ", j = " + input[0][1],
                         "i = " + input[1][0],    "j = " + input[2][1],
                         "i = " + input[3][0] + ", j = " + input[3][1],
                         "i = " + input[4][0],    "j = " + input[5][1] };
        
        for (int i = 0; i < input.length; i++) {
            Debugger.debug(testName + " with i = " + input[i][0] + ", j = " +
                           input[i][1]);
            try {
                EdgeCostMatrix.getCost(input[i][0],input[i][1]);
                
                Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD have been caught, since " + msg[i] + " " +
                    "violating the precondition.");
            } catch (PreconditionException pe) {
                Debugger.debug("    Exception caught as expected, since " +
                     msg[i] + "\n    violating the precondition.");
            }
            Debugger.debug("");
        }
        
        double[][] checkMatrix = new double[dim][dim];
        
        int i = -1, j = -1;

        Debugger.debug("Copying the matrix store within EdgeCostMatrix...");
        Debugger.debug("");
        Debugger.enableTrace(false);         
    
        for (i = 0; i < dim; i++) 
            for (j = 0; j < dim; j++) 
                checkMatrix[i][j] = EdgeCostMatrix.getCost(i,j);

        Debugger.enableTrace(true);        
        Debugger.debug("The matrix copied is the same as the matrix m[3]: " +
                    sameMatrix(checkMatrix, reference) );

        Debugger.debug("Resetting the matrix stored within...");
        EdgeCostMatrix.reset();
        Debugger.debug("");
        
        Debugger.debug(testName = "Testing dimension after resetting");
        try {
            EdgeCostMatrix.dimension();
            Debugger.printErr(testName + ":\n    ERROR!!! Exception SHOULD "+
                        "have been caught, since matrix no longer exists.");
        } catch (AssertionException ae) {
            Debugger.debug("    Exception caught as expected, since matrix "+
                        "no longer exists");
        }
        Debugger.debug("");

        Debugger.debug(testName = "Testing getCost after resetting");
        try {
            EdgeCostMatrix.getCost(1,1);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception SHOULD "+
                        "have been caught, since matrix no longer exists.");
        } catch (AssertionException ae) {
            Debugger.debug("    Exception caught as expected, since matrix "+
                        "no longer exists");
        }
        Debugger.debug("");
        
        Debugger.debug("Testing getCost completed");
        Debugger.debug("=========================\n");
    }
	
	/**
	 * tests the populateMatrix method. Various input parameter will be 
	 * tested. It is also checked that two matrices created with this method
	 * bare minimum resemblance.
	 */
    private static void testPopulateMatrix () {
        Debugger.debug("Testing populateMatrix");
        Debugger.debug("======================\n");
        
        // input parameters for test    
        double[][] input = { { 0, -1.0 }, { 0, 1.0 }, { 1, -1.0 } };
        // test information
        String[] msg = { "numNodes = " + ((int)input[0][0]) + 
                                        ", maxCost = " + input[0][1],
                         "numNodes = " + ((int)input[1][0]),
                         "maxCost = " + input[2][1] };
        
        // run tests
        for (int i = 0; i < input.length; i++) {
            Debugger.debug(testName + " with numNodes = " + input[i][0] + 
                            ", maxCost = " + input[i][1]);
            try {
                EdgeCostMatrix.populateMatrix((int)input[i][0],input[i][1]);
                Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD have been caught, since\n    " + msg[i] + " " +
                    "violating the precondition.");
            } catch (PreconditionException pe) {
                Debugger.debug("    Exception caught as expected, since " +
                     msg[i] + " violating\n    the precondition.");
            }
            Debugger.debug("");
        }
        
        int dim = 8;
        double maxCost = 1000;

        Debugger.debug(testName + " with numNodes = " + dim +
                            ", maxCost = " + maxCost);
        try {
            EdgeCostMatrix.populateMatrix( dim, maxCost);
            Debugger.debug("    Exception not caught as expected, since " +
                     "numNodes = " + dim +", maxCost = " + maxCost + 
                     "\n    satisfying    the precondition.");
            
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD not have been caught, since\n    numNodes = " + 
                    dim +", maxCost = " + maxCost + " satisfying the " +
                    "precondition.");
        }
        Debugger.debug("");

        Debugger.debug("Copying the matrix to reference matrix...");
        reference = new double[dim][dim];
        Debugger.enableTrace(false);         
    
        for (int i = 0; i < dim; i++) 
            for (int j = 0; j < dim; j++) 
                reference[i][j] = EdgeCostMatrix.getCost(i,j);
        Debugger.enableTrace(true);      
    
        Debugger.debug("");
        Debugger.debug("Print the matrix...");
        printMatrix(reference);
        
        int iterations = 4;        
        double[][] check;
        
        // create population to see if different ones are created
        for (int k = 0; k < iterations; k++) {
            Debugger.debug("Resetting and populating a matrix");
            EdgeCostMatrix.reset();
            EdgeCostMatrix.populateMatrix(dim, maxCost);
            check = new double[dim][dim];
            Debugger.debug("Copying the matrix to check matrix...");
            Debugger.enableTrace(false);  
            for (int i = 0; i < dim; i++) 
                for (int j = 0; j < dim; j++) 
                    check[i][j] = EdgeCostMatrix.getCost(i,j);
            Debugger.enableTrace(true);
            
            Debugger.debug("The matrix just created is the same as the " +
                "one created previously: " + sameMatrix(check, reference) );
            Debugger.debug("\nSet reference matrix as current matrix");
            reference = check;
            Debugger.debug("");
        }
        
        Debugger.debug("Testing populateMatrix completed");
        Debugger.debug("================================");
    }
    
    /**
     * Checks if the two matrices are the same. This means, if the matrices
     * are the same, they have same dimensions and contents
     * @param   m1  the first matrix for comparison
     * @param   m2  the second matrix for comparison
     */
    private static boolean sameMatrix(double[][] m1, double[][] m2) {
        // check dimensions
        if (m1.length != m2.length) return false;
        for (int i = 0; i < m1.length; i++) 
            if (m1[i].length != m2[i].length) return false;
        // check contents
        for (int i = 0; i < m1.length; i++) 
            for (int j = 0; j < m1[i].length; j++)
                if (m1[i][j] != m2[i][j]) return false;
        
        return true;
    }

    /**
     * Prints a matrix.
     * @param   matrix  an array of arrays of double numbers
     */
	private static void printMatrix(double[][] matrix) {
	    String line = "";
	    
	    for(int col = 0; col < matrix.length; col++ ) line += "\t" + col;
        Debugger.debug(line);
        
        for (int i = 0; i < matrix.length; i++) {
            line = Integer.toString(i);
            for (int j = 0; j < matrix[i].length; j++) 
                line += "\t" + double2String(matrix[i][j]);
            Debugger.debug(line);
        }
        Debugger.debug("");
	}
	
	/**
	 * converts a number into a string. It is not intended to work for
	 *  numbers larger than 999999 (more than 6 digits).
	 * @param   d   the number to be converted
	 */
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
	
