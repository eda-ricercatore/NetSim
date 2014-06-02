/*
 * population package determines the bahaviour of chromosomes in the
 * population of the network. That is, each chromosome in the population
 * represents a network.
 * The behaviour of chromosmes is determined by modifying the data pertaining
 * to each chromosome
 */
package population;

// Importing packages
import utility.*;

/**
 * This class is responsible for manipulating matrices, in particularly the
 * adjacency matrices, using the Dijkstra's Algorithm. This algorithm can be
 * used to determine the minimal spanning tree.<p>
 *
 * The application varies depending on the cost entered in the adjacency
 * matrix. For example, if the matrix contains only unit cost, the resulting 
 * matrix will indicate the degree of separation between any two nodes. 
 * Metrics such as load and clustering coefficient can also used to balance
 * load in the network.<p>
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.4.1
 * @since   0.3.7
 * @acknowledgement Matthew Berryman, Wei-li Khoo and Hiep Nguyen
 */
public class DijkstraMatrix {
    private final double LN2 = Math.log(2.0);
    
    // a counter, which is required when calculatign average
    private int count = 0;

    /**
     * Performs Dijkstra's algorithm on an adjacency matrix.
     * @param   adjMatrix   the adjacency matrix which the algorithm is 
     *                      operated on
     * @return  the resulting matrix
     * @throws  PreconditionException if the number of columns of the
     *          matrix does not equal the number of rows of the matrix
     * @throws  PostconditionException if the dimension of the returned
     *          matrix is not n x n
     */
    public double[][] dijkstra (double[][] adjMatrix) 
        throws PreconditionException, PostconditionException {
        {// Checking Precondition
            for (int i = 0; i < adjMatrix.length; i++) {
                if( adjMatrix[i].length != adjMatrix.length ) {
                    throw new PreconditionException("    ERROR!!! Row " + i + 
                        " of the matrix has only " + adjMatrix[i].length + 
                        " elements.\n    Each row should have " + 
                        adjMatrix.length + " elements!");
                }            
            }
            //Debugger.debug("The input matrix has dimension: " + 
            //    adjMatrix.length + " x " + adjMatrix.length);
        }

        // obtain matrix
        double[][] m = adjMatrix;

        // create matrix for shortest paths
        // calculate A^2, A^4,...,A^(n-1) in floor(log(n-1));
        int iterations = (int) (Math.log((double)(m.length-1))/LN2);
        for (int i = 0; i < iterations ; i++) m = manipulate (m);


        {// Checking Postcondition
            for (int i = 0; i < m.length; i++) {
                if( m[i].length != m.length ) {
                    throw new PostconditionException("    ERROR!!! Row " + 
                        i + " of the matrix has only " + m[i].length + 
                        " elements.\n    Each row should have " + m.length +
                        " elements!");
                }            
            }
            //Debugger.debug("The result matrix has dimension: " + m.length +
            //    " x " + m.length);
        }
        
        /**
         * Print results. Note this will only have effect if the method
         * printResults is overridden elsewhere.
         */
        //printResults(adjMatrix, m);

        return m;
    }

    /**
     * This is a modified version of matrix multiplication. In standard
     * matrix multiplication, one sums the scalar products of the
     * corresponding rows and columns. In this case, we find the minimum of
     * the individual sum of the corresponding elements. This is because in
     * Dijsktra's algorithm, we attempt to find a minimal cost path
     * for routing. <p>
     *
     * A matrix is declared as an array of arrays. It can be initialised as:
     * double array[][] = new double[number of rows][number of cols]. Thus to
     * reference the element (i,j), that is ith row and jth column, we refer
     * to array[i][j].<p>
     *
     * Assumes every row has same number of elements, and also every column
     * have the same number of elements. Note that m=n1=n2=k in this case, as
     * we are manipulating adjancency matrices, which are square matrices.
     *
     * @param   m  an n x n matrix of doubles
//     * @throws  PreconditionException if the number of columns of the
//     *          matrix does not equal the number of rows of the matrix
//     * @throws  PostconditionException if the dimension of the returned
//     *          matrix is not n x n
     * @return  a matrix of doubles as a result from the matrix manipulation
     */
    private double[][] manipulate(double[][] m) {
//        throws PreconditionException, PostconditionException {
//        {// Checking Precondition
//            for (int i = 0; i < m.length; i++) {
//                if( m[i].length != m.length ) {
//                    throw new PreconditionException("    ERROR!!! Row " + i + 
//                        " of the matrix has " + m[i].length + 
//                        " elements.\n    Each row should have same number" +
//                        " of elements!");
//                }            
//            }
//            //Debugger.debug("The input matrix has dimension: " + m.length +
//            //  " x " + m.length);
//        }

        /**
         * The dimension of the result of matrix manipulation is the number
         * of rows in the matrix (m.length) by number of columns of
         * the matrix (m[i].length).
         */
        double result[][] = new double[m.length][m.length];
        double is[] = new double[m.length];

        // selects the row of the first matrix
        for (int i = 0; i < result.length; i++) {
            // selects the column of the second matrix
            for (int j = 0; j < result[0].length; j++) {
                // if it's the (i,i) element, the cost is 0 (short cut 1)
                if (i == j) continue;
                // otherwise workout the appropriate value
                for (int k = 0; k < m[0].length; k++) {
                    /**
                     * if one of the term is positive infinity (+oo) the
                     * result is positive infinity
                     * (short cut 2)
                     */
                    if ( m[i][k] == Double.POSITIVE_INFINITY ||
                         m[k][j] == Double.POSITIVE_INFINITY )
                           is[k] =  Double.POSITIVE_INFINITY;
                    else
                        is[k] = m[i][k] + m[k][j];
                }
                result[i][j] = min(is); // find the minimum sum in array "is"
            }
        }

//        // check if the resulting matrix has the right number of rows
//        {
//            for (int i = 0; i < result.length; i++) {
//                if( result[i].length != m.length ) {
//                    throw new PostconditionException("    ERROR!!! Row " +  
//                        i + " of the matrix has only " + result[i].length + 
//                        " elements.\n    Each row should have " + m.length +
//                        " elements!");
//                }            
//            }
//            //Debugger.debug("The result matrix has dimension: " + 
//            //  result.length + " x " + m.length);
//        }

        return result;
    }

    /**
     * Computes the sum of all non-infinity double number in a matrix.
     * @param   m   the matrix from which the sum will be calculated
     * @return  the sum of all non-infinity double number in the matrix
     */
    public double getSum(double[][] m) {
        return assessMatrix(m, false, false);
    }

    /**
     * Calculates the average of all non-infinity double number in a matrix.
     * @param   m   the matrix from which the average will be calculated
     * @return  the average of all non-infinity double number in the matrix
     */
    public double getAverage(double[][] m) {
        return assessMatrix(m, false, true);
    }    

    /**
     * Determine the maximum of all non-infinity double number in a matrix.
     * @param   m   the matrix from which the maximum will be found
     * @return the maximum of all non-infinity double number in the matrix
     */
    public double getMax(double[][] m) {
        return assessMatrix(m, true, false);
    }

    /**
     * Assesses the matrix and calculate the sum, average or maximum of all
     * the non-infinity double number. The operation depends on the flag.
     * @param   m   the matrix to be assessed
     * @param   max set to true to calculated the maximum
     * @param   avg set to true to calculated the average
     * @returns the result of assessment
     * @throws  AssertionException if both max and avg are set to true
     */
    private double assessMatrix(double[][] m, boolean max, boolean avg) {
        Assertion.asrt( !max | !avg, 
                "Find maximum = " + max + ", Find average = " + avg,
                "ERROR!!! Cannot find maximum and average at the same time");

        // storage for intermediate result
        double[] intermediate = new double[m.length];
        // storage for final result
        double result;

        if (avg) count = 0;        // if want find average, reset count
        
        // for each row do sum or max
        for (int i = 0; i < m.length; i++) {
            // intermediate[i] = (max) ? max(m[i]) : sum(m[i]);
            if (max) intermediate[i] = max(m[i]);
            else     intermediate[i] = sum(m[i]);
        }

        // do sum or max for the intermediate results
        // result = (max) ? max(intermediate) : sum(intermediate);
        if (max) result = max(intermediate);
        else     result = sum(intermediate);
        
        /**
         * If want find average, divide by count. However note that count was
         * also incremetned by the previous step of summing intermediate, so
         * we must subtract the number of count increment then.
         */
        if (avg) result /= count-intermediate.length;

        return result;
    }

    /**
     * Finds the sum of all double precision number, which is not
     * Double.POSITIVE_INFINITY or Double.NEGATIVE_INFINITY, in an array.
     * @param arr an array of double numbers
     * @return the sum of the double values in the array arr
     */
    private double sum(double[] arr) {
        // let the largest number be -oo
        double result = 0;
        // look add each element that is not POSITIVE_INFINITY
        for (int i = 0; i < arr.length; i++) 
            if (arr[i] != Double.POSITIVE_INFINITY && 
            arr[i] != Double.NEGATIVE_INFINITY && arr[i] != 0.0) {
                result += arr[i];
                // increment count, in case we need average
                count++;
            }
        return result;        
    }

    /**
     * Finds the maximum double precision number, which is not
     * Double.POSITIVE_INFINITY, in a given array.
     * @param arr an array of double numbers
     * @return the largest double in the array arr
     */
    private double max (double[] arr) {
        // let the largest number be -oo
        double result = Double.NEGATIVE_INFINITY;
        // look at each element and try to find a larger number
        for (int i = 0; i < arr.length; i++) 
            if (arr[i] > result && arr[i] != Double.POSITIVE_INFINITY)
                result = arr[i];
        return result;
    }

    /**
     * Finds the minimum double precision number, which is not
     * Double.NEGATIVE_INFINITY, in a given array.
     * @param m an array of double numbers
     * @return the smallest double in the array arr
     */
    private double min (double[] arr) {
        // let the smallest number be +oo
        double result = Double.POSITIVE_INFINITY;
        // look at each element and try to find a smaller number
        for (int i = 0; i < arr.length; i++) 
            if (arr[i] < result && arr[i] != Double.NEGATIVE_INFINITY)
                result = arr[i];
        return result;
    }

    /**
     * Intended to print out the result of applying a function for a
     * chromosome. This should only be used in the testsuite that extends
     * this class. The original matrix of the graph should be printed with
     * the new matrix for easier comparison.
     *
     * @param   matrixA the original matrix
     * @param   matrixB the new matrix with the minimal graph
     */
//    public void printResults(double[][] matrixA, double[][] matrixB) {
        //Debugger.printErr("WARNING!!! Method printResults should be " +
        //    " overridden in a test class, which extends DijkstraMatrix.");
//    }
}
