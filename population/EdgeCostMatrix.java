/*
 * population package determines the bahaviour of chromosomes in the
 * population of the network. That is, each chromosome in the population
 * represents a network.
 * The behaviour of chromosmes is determined by modifying the data pertaining
 * to each chromosome
 */
package population;

// importing packages
import utility.*;
import java.util.Random;

/**
 * This class is responsible for holding a matrix that contains all the 
 * values that should be set for the cost of edges between nodes. Every
 * element (i,j) represents the cost of traversing the edge that starts from
 * node "i" and ends at node "j"
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.8
 */

public class EdgeCostMatrix {

    private static Random rand = new Random();
    
    // the matrix containing cost between all pairs of nodes in a network
    private static double[][] costMatrix;
    // controls the matrix creation, so that it is populated ONCE ONLY
    private static boolean matrixExists = false;

    /**
     * Thou shall not instantiate this class.
     */
    public EdgeCostMatrix() {
        Debugger.printErr("It is not required to instantiate this class.\n" +
                          "Call methods by: EdgeCostMatrix.method(params)");
    }

    /**
     * Creates and populates a sqaure matrix with the number of nodes in the
     * network as the width and height. The maximum cost is specified so that
     * the cost of the edge from one node to another is uniformly distributed
     * from 0 to maxCost.
     *
     * @param   numNodes    the number of nodes in the network
     * @param   maxCost     the maximum cost to travel between any arbitrary
     *                      pairs of nodes
     * @throws  PreconditionException if numNodes is not greater than 0
     * @throws  PreconditionException if maxCost is not greater than 0
     */
    public static void populateMatrix(int numNodes, double maxCost)
    throws PreconditionException {
        if (matrixExists) Debugger.printErr("Matrix already exists");
        else {
            Assertion.pre( numNodes > 0,
                "Creating matrix for " + numNodes + " nodes",
                "ERROR!!! Number of nodes must be GREATER THAN ZERO");
            Assertion.pre( maxCost > 0.0,
                "Maximum cost for edges is " + maxCost,
                "ERROR!!! (Maximum) cost must be GREATER THAN ZERO");

            // instantiate matrix
            costMatrix = new double[numNodes][numNodes];

            // for every row
            for (int i = 0; i < numNodes; i++)
                // for every element in that row
                for (int j = 0; j < numNodes; j++) {
                    if (i == j) continue;   // every cost(i,i) = 0
                    costMatrix[i][j] = rand.nextDouble() * maxCost;
                }
            
            matrixExists = true;    // store matrix
        }
    }

    /**
     * Sets a predetermined matrix as the matrix of edge costs stored in this
     * class.
     *
     * @param   m   A square matrix whose height and width equals to the
     *              number nodes in the network.
     * @throws  PreconditionException If the input matrix is not a SQUARE 
     *          matrix
     */
    public static void setMatrix(double[][] m) throws PreconditionException {
        if (matrixExists) Debugger.printErr("Matrix already exists");
        else {
            // Checking Precondition
            for (int i = 0; i < m.length; i++) {
                if( m[i].length != m.length ) {
                    throw new PreconditionException(
                        "    ERROR!!! Row " + i + " of the matrix has " +
                        m[i].length + " elements.\n    Each row " +
                        "should have same number of elements!");
                }            
            }
            costMatrix = m;         // store matrix            
            matrixExists = true;    // update status
        }
    }

    /**
     * Retrieve the cost of using the edge that originates from node "i" and
     * terminates at node "j". This corresponds to element (i,j) in the cost
     * matrix. In general, cost(i,j) does not equal to cost(j,i).
     *
     * @param   i   the index of the source node of the edge
     * @param   j   the index of the destination node of the edge
     * @throws  AssertionException If cost matrix has not been populated.
     * @throws  PreconditionException If the index (i) of the source node is
     *          out of range
     * @throws  PreconditionException If the index (j) of the destination
     *          node is out of range
     * @return  the cost of the edge connecting (i,j)
     */
    public static double getCost(int i, int j)
    throws AssertionException, PreconditionException {
        Assertion.asrt( costMatrix != null,
            "Obtaining cost information from element at",
            "ERROR!!! costMatrix does not exist. Use setMatrix to set one");
        Assertion.pre( i >= 0 && i < costMatrix.length, "Row " + i,
            "ERROR!!! row index OUT of BOUNDS. Range is [0," +
            (costMatrix.length-1) + "]");
        Assertion.pre( j >= 0 && j < costMatrix[i].length, "Column " + j,
            "ERROR!!! column index OUT of BOUNDS. Range is [0," +
            (costMatrix[i].length-1) + "] ("+j+")");
        return costMatrix[i][j];
    }

    /**
     * Returns a value which is equal to the height or width of the matrix
     * 
     * @throws  AssertionException If cost matrix has not been populated.
     * @return  the height or width of the square cost matrix
     */
    public static int dimension () {
        Assertion.asrt( costMatrix != null,
            "Obtaining cost information from element at",
            "ERROR!!! costMatrix does not exist. Use setMatrix to set one");
        int dim = costMatrix.length;
        
        Assertion.post( dim > 0,
            "The matrix is a " + dim + " by " + dim + " matrix.",
            "ERROR!!! Dimension cannot be negative: (" + dim + ")" );

        return dim;
    }


    /**
     * Reset the <b>Status</b> of the costMatrix, so that it can be set or
     * populated (at random) again.
     */
    public static void reset() {
        matrixExists = false;
        costMatrix = null;
    }
}
