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
 * This function calculates the total cost of edges within a network 
 * represented by a chromosome.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.8
 * @since   0.3.7
 * @see     Chromosome
 * @see     SetOfChromosomes
 * @acknowledgement Matthew Berryman, Wei-li Khoo and Hiep Nguyen
 */
public class TotalEdgeCost implements Function {
    
    DijkstraMatrix dm = new DijkstraMatrix();
    
    /**
     * Calculates the sum of costs of edges for each chromosome.
     * @param   pop     The chromosome population.
	 * @param   index   the index of this cost function
     */
	// pass in an index (int) representing the cost function
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
     * Computes the sum of all edges in the network using the adjacency 
     * matrix associated with a Chromosome
     * @param   c       the chromosome to which the function is applied
	 * @param   index   the index of this cost function
     */
	// pass in index representing cost function
    public void apply(Chromosome c, int index) {        
        // obtain the adjacency matrix
        double[][] matrix = c.getAdjacencyMatrix();
        // calculate the result
        double result = getSum(matrix);
        // store the result as fitness
        //c.setFitness(result);
		c.insertIntoFitArr(index, result);
    }
    
    /**
     * Perform summation on an adjacency matrix
     * @param   m   the matrix from which the sum will be determined
     * @return  the total cost of edges in the network
     */
    private double getSum(double[][] m) {
        return dm.getSum(m);
    }    
}
