/*
 * population package determines the bahaviour of chromosomes in the
 * population of the network. That is, each chromosome in the population
 * represents a network.
 * The behaviour of chromosmes is determined by modifying the data pertaining
 * to each chromosome
 */
package population;

/**
 * This internal interface is used to provide the function to be optimised
 * by this evolution process.
 * 
 * @author  Zhiyang Ong and Andy Lo
 * @version 0.3.7
 * @since   0.3
 */
public interface Function {
	/**
	 * This method takes in a population of chromosomes, and maps a function
     * to all its chromosomes
	 * @param   pop     the chromosome population
	 * @param   index   the index of the cost function being used
	 */
	public void map(SetOfChromosomes pop, int index);

	/**
	 * This method takes in a chromosome, and applies the function, which
     * provides the fitness result for this chromosome.<p>
     *
     * <b>Updated</b> by Andy 15/04/05.
     *
     * @param   c       the chromosome to which the function is applied
	 * @param   index   the index of the cost function being used
	 */
	public void apply(Chromosome c, int index);
}
