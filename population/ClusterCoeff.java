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
import java.util.Arrays;

/**
 * This class calculates the fitness of a chromosome as the average of the
 * clustering coeffient of all nodes with degree 2 or above.<p>
 *
 * It is calculated as:<center>
 *      C<sub>avg</sub> = [Sum of clustering coefficients] / N<sub>2</sub>
 * </center>
 *
 * And the clustering coefficient is:<center>
 *      C<sub>i</sub> = 2 x E<sub>i</sub> / [k<sub>i</sub>(k<sub>i</sub>-1)]
 * </center>
 * where:<ul><li>N<sub>2</sub> is the number of nodes of degree 2 or above
             <li>E<sub>i</sub> is the number of connection node i makes
 *           <li>k<sub>i</sub> is the number of nodes in the neighborhood
 * </ul><p>
 *
 * Reference: <i>Boykin, P. Oscar and Roychowdhury, Vwani P.</i>, 
 * IEEE Computer Society, April 2005, Leveraging Social Networks to Fight 
 * Spam, 0018-9162/05, viewed 17 Apr 2005
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.4.1
 * @since   0.3.7
 * @see     Chromosome
 * @see     SetOfChromosomes
 */
public class ClusterCoeff extends DegreeSeparation {

    /**
     * Instantiates this function.
     */
    public ClusterCoeff() {
        super();
    }

    /**
     * Applies the function that calculates the average clustering 
     * coefficient in a network. 
     *
     * @param   c       the chromosome to which the function is applied
	 * @param   index   the index of this cost function
     */
    public void apply(Chromosome c, int index) {
        // obtain the adjacency matrix
        double[][] adjMatrix = c.getAdjacencyMatrix();
        // convert to a link matrix with only 0, 1, and infinity (+oo)
        adjMatrix = adj2linkMatrix(adjMatrix);
        // perform dijsktra on the matrix
        double[][] resMatrix = dm.dijkstra(adjMatrix);
        // if doAvg is set, get the average, otherwise get the maximum
        double result = assessConnectivity(adjMatrix, resMatrix);

        // store the result as fitness
        c.insertIntoFitArr(index, result);
    }

    /**
     * Produces the result of average clustering coefficient. This is done 
     * by comparing the original link matrix and the result of dijkstra's
     * algorithm to identify the number of direct and indirect links.
     * 
     * @param   a   the original link matrix representing the network
     * @param   b   the resultign matrix from performing dijkstra on "a"
     * @return  the connectivity
     */
    protected double assessConnectivity(double[][] a, double b[][]) {
        // the number of nodes of degree 2 or greater
        int nodeCount = 0;
        // the number of connection for a node (direct links)
        int connections;
        // the number of neighbors indirectly or directly connected
        int neighbors;
        // the accumulated sum of cluster coefficient
        double clusterSum = 0;

        // for each node in the network
        for (int i = 0; i < a.length; i++) {
            // find the number of direct connection from the original matrix
            connections = 0;
            for (int j = 0; j < a[i].length; j++) {
                if (i == j) continue;
                if (a[i][j] != Double.POSITIVE_INFINITY) connections++;
            }
            /**
             * skip, since cluster coefficient for nodes with degree 1 is
             * undefined
             */
            if (connections < 2) continue;
            // else, increment the number of nodes with degree 2 or greater
            nodeCount++;
            /**
             * find the number of connected nodes from the matrix result
             * from dijsktra
             */
            neighbors = 0;
            for (int j = 0; j < b[i].length; j++) {
                if (i == j) continue;
                if (b[i][j] != Double.POSITIVE_INFINITY) neighbors++;
            }
            // update sum
            
            
//            Debugger.enableTrace(true);
//            Debugger.debug("node " + i);
//            Debugger.debug("connections = " + connections);
//            Debugger.debug("neighbors   = " + neighbors);           
//            Debugger.debug("clusterSum = " + ((double) connections /
//                          ((double) neighbors * ((double)neighbors - 1.0))) );            

            clusterSum += (double) connections /
                          ((double) neighbors * ((double)neighbors - 1.0) );

//            Debugger.enableTrace(false);
        }
        /**
         * calculate the average. Note that the x2 operation is performed
         * here instead of in the loop.
         */
        double averageCoeff;
        if( clusterSum == 0 && nodeCount == 0) // 0/0 gives NaN in java
            averageCoeff = 0;
        else
            averageCoeff = 2.0*( clusterSum / (double) nodeCount);
        return averageCoeff;
    }
}
