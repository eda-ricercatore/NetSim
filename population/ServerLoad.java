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
import population.graph.*;

/**
 * This function calculates the total of load factor on each server within a 
 * network represented by a chromosome.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.8
 * @since   0.3.7
 * @see     Chromosome
 * @see     SetOfChromosomes
 * @acknowledgement Matthew Berryman, Wei-li Khoo and Hiep Nguyen
 */
public class ServerLoad implements Function {
    
    /**
     * Constructs a default instance of the function
     */
    public ServerLoad() {
        
    }
    
    /**
     * Applies the function to each of the chromosome in the population
     * @param   pop     The chromosome population.
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
     * Sums the load factor of the server nodes in the network.
     * @param   c       the chromosome to which the function is applied
	 * @param   index   the index of this cost function
     */
    public void apply(Chromosome c, int index) {        
        // obtain graph from this chromosome
        Graph g = c.getGraph();
        // retrieve the number of nodes in the graph
        int numNodes = g.numNodes();
        
        Node n;
        double load = 0.0;

        // for each node, check if it is a server; if so, add the load factor
        for (int i = 0; i < numNodes; i++) {
            n = g.getNode(i);
            if ( (n.getLabel()).startsWith("SERVER") )
                load += ((NodeImp) n).getLoad();
        }
        
        //c.setFitness(load);
		c.insertIntoFitArr(index, load);              
    }
}
