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
 * Computes the degree of separation of nodes in the network, represented by
 * a chromosome. This is an Adaptor for the Dijkstra Matrix, which contains
 * the fundamental algorithms for the Dijkstra min-plus operations.<p>
 *
 * In order to calculate the degree of separation, all edges will have unit
 * cost, meaning that if there is a connection the cost is 1. The result of
 * applying Dijkstra will result with a matrix indicating the smallest number
 * of degrees of separation (hops) between any 2 nodes.<p>
 *
 * The value of cost that will be set is either the maximum or the average of
 * degree of separation between nodes in the network. Which result will be
 * determined is dependent a switch that can be set when constructing this
 * object, or can be toggled at run time.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.8
 * @since   0.3.7
 * @see     Chromosome
 * @see     SetOfChromosomes
 * @acknowledgement Matthew Berryman, Wei-li Khoo and Hiep Nguyen
 */
public class DegreeSeparation implements Function {
    
    /**
     * if set to true, the average degree of separation will be calculated, 
     * or else, tha maximum degree of separation will be determined .
     */
    private boolean doAvg;

    DijkstraMatrix dm = new DijkstraMatrix();

    /**
     * Constructs this function, which is defaulted to find the maximum
     * degree of separation
     */
    public DegreeSeparation() {
        doAvg = false;
    }

    /**
     * Constructs this function to calculate the average of degree of
     * separation, or determine the maximum separation.
     * @param   doAverage   set <code>true</code> to calculated the average
     */    
    public DegreeSeparation(boolean doAverage) {
        this.doAvg = doAverage;
    }

    /**
     * Instructs whether the function will calculate the average of degree of
     * separation, or determine the maximum.
     * @param   doAverage   set <code>true</code> to calculated the average
     */
    public void setDoAvg(boolean doAverage) {
        this.doAvg = doAverage;
    }

    /**
     * This method takes in a population of chromosomes, and measures the 
     * degree of separation for each chromosome within.
     * @param   pop     the chromosome population.
	 * @param   index   the index of this cost function
     */
    public void map(SetOfChromosomes pop, int index) {
        int size = pop.getPopSize();
        Chromosome c;
        // for every chromosome in the population
        for (int i = 0; i < size; i++) {
            c = pop.getChromo(i);   // store the refrence of the chromosome
            apply(c, index);
        }
    }

    /**
     * Measures the degree of separation in the network represented by the
     * chromosome. A link matrix (with only 0, 1, and infinity, where 1 means
     * a connection) instead of a adjacency matrix (where values indicates
     * the cost of the edge).<p>
     *
     * Whether the functions uses the average or the maximum degree of 
     * separation is dependent on the variable <code>doAvg</code> which can
     * be toggled with <code>setDoAvg(boolean)</code>.
     *
     * @param   c       the chromosome to which the function is applied
	 * @param   index   the index of this cost function
     */
    public void apply(Chromosome c, int index) {
        double result;
        
        // obtain the adjacency matrix
        double[][] matrix = c.getAdjacencyMatrix();
        // convert to a link matrix with only 0, 1, and infinity (+oo)
        matrix = adj2linkMatrix(matrix);
        // perform dijsktra on the matrix
        matrix = dm.dijkstra(matrix);
        // if doAvg is set, get the average, otherwise get the maximum
        result = (doAvg) ? getAverage(matrix) : getMax(matrix);

        // store the result as fitness
        c.insertIntoFitArr(index, result);
    }
    
    /**
     * Converts an adjacency matrix to a link matrix. That is replace all 
     * values, which are not <code>Double.POSITIVE_INFINITY</code> or 0, with
     * <b>1</b>. This indicats a connection between two nodes.
     *
     * @param   m   an adjacency matrix with dimension n x n, where n is the
     *              number of nodes in the network
     * @return  a link matrix with only values of 1 indicating a connection 
     *          between nodes
     */
    public double[][] adj2linkMatrix(double[][] m) {
        // create storage for result
        double[][] result = new double[m.length][m[0].length];
        // for every row of the matrix
        for(int i = 0; i < m.length; i++) {
            // for every element in that row
            for(int j = 0; j < m[i].length; j++) {                
                if (m[i][j] != Double.POSITIVE_INFINITY && 
                    m[i][j] != Double.NEGATIVE_INFINITY &&
                    m[i][j] != 0)
                    result[i][j] = 1;
                else 
                    result[i][j] = m[i][j];
            }
        }
        return result;
    }

    /**
     * Calculates the average degree of separation in the network
     * @param   m   the matrix from which the average will be calculated
     * @return  the average degree of separation of nodes in the network
     */
    private double getAverage(double[][] m) {
        return dm.getAverage(m);
    }    

    /**
     * Determines the maximum degree of separation in the network
     * @param   m   the matrix from which the maximum will be found
     * @return  the maximum degree of separation of nodes in the network
     */
    private double getMax(double[][] m) {
        return dm.getMax(m);
    }
}
