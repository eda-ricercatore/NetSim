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
 * Calculates the node to node resistance within a network.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.4.1
 * @see BFSResistance
 */
public class Resistance implements Function {
    
    /**
     * if set to true, the calculations for the resistance will use unit cost
     * for the edges, or else, the actual each cost will be used.
     */
    private boolean useUnitResistance;
    
    DijkstraMatrix dm;
    BFSResistance bfs;
    
    /**
     * The default instance of Resistance will use the actual edge cost for 
     * calculating the resistance.
     */
    public Resistance () {
        dm  = new DijkstraMatrix();
        useUnitResistance = false;
        bfs = new BFSResistance(useUnitResistance);        
    }
    
    /**
     * This constructs an instance of Resistance specifying whether the 
     * algorithm will use unit edge cost or the actual edge cost for
     * calculating the resistance.
     *
     * @param   use <code>true</code>, if unit edge cost is to be used.
     */
    public Resistance (boolean use) {
        dm  = new DijkstraMatrix();
        useUnitResistance = use;
        bfs = new BFSResistance(useUnitResistance);        
    }
    
    /**
     * Map the function that calculates average resistance on to a population
     * of chromosomes.
     *
     * @param   pop     The chromosome population.
	 * @param   index   the index of this cost function
     */
    public void map(SetOfChromosomes pop, int index) {
        int size = pop.getPopSize();
        Chromosome c;
        // for every chromosome in the population
        for (int i = 0; i < size; i++) {
//            Debugger.enableTrace(true);
//            Debugger.debug("Applying to chromosome #"+i);
//            Debugger.enableTrace(false);
            c = pop.getChromo(i);   // store the refrence of the chromosome
            apply(c, index);
        }
    }

    /**
     * Calculates the average resistance within the network, represented by
     * the chromosome. This is an approximation by treating each path as
     * independent regardless of branches and merges at vertices.
     *
     * @param   c       the chromosome to which the function is applied
	 * @param   index   the index of this cost function
     */
    public void apply(Chromosome c, int index) {
        // obtain the resistance matrix
        double[][] matrix = bfs.getResistance(c);
        // if doAvg is set, get the average, otherwise get the maximum
        double result = getAverage(matrix);
        // store the result as fitness
        c.insertIntoFitArr(index, result);
    }
     
    /**
     * Calculates the average degree of separation in the network
     * @param   m   the matrix from which the average will be calculated
     * @return  the average degree of separation of nodes in the network
     */
    private double getAverage(double[][] m) {
        return dm.getAverage(m);
    }    
    
}
