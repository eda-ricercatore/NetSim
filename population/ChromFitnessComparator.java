/*
 * population package determines the bahaviour of chromosomes in the
 * population of the network. That is, each chromosome in the population
 * represents a network.
 * The behaviour of chromosmes is determined by modifying the data pertaining
 * to each chromosome
 */
package population;

import java.util.Comparator;

/**
 * This class is the comparison function used to impose a total ordering on
 * chromosome objects. This will be used for sorting a population of
 * chromosomes, stored in an array, in descending order of fitness.
 *
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.2-andy
 * @since           0.3
 */
public class ChromFitnessComparator implements Comparator {
	
	// Indicator for the use of normalised or non-normalised values
	//boolean normalise = false;
	
	// ----------------------------------------------------------------------
	
	// Default constructor
	public ChromFitnessComparator() {
		// Do nothing...
	}
	
	// ----------------------------------------------------------------------

    /**
     * This is the only method that is needed to be implmented. This will
     * compare the fitness values for two chromosomes. Subject to the
     * interface constraint, the input parameters are superclass objects
     * rather than chromosomes, so it is required to convert the objects to
     * chromosomes for the comparison. This only works if the objects are
     * really chromosomes, and this will be checke by preconditions.<p>
     * The comparison produces 3 results (-1,0,1). If -1 is returned, it
     * implies that the first object should be placed before the second. 0 is
     * returned if equality holds.
     * @param   o1 The first object to be compared
     * @param   o2 The second object to be compared
     * @return  "-1" if <code>o1</code> preceeds <code>o2</code>, "0" if
     *          <code>o1</code> has equivalent placement <code>o2</code>, and
     *          "1" if <code>o1</code> follows <code>o2</code>
     * @throws  ClassCastException If the objects are not chromosomes
     */ 
    public int compare(Object o1, Object o2) {
        // Obtain fitness values from chromosome objects
/*
		if(normalise) {
			// Use tempFitness only prior to evolution
			double value1 = ((Chromosome) o1).getTempFitness();
			double value2 = ((Chromosome) o2).getTempFitness();
			normalise = false;
		}else{
			// Use fitness otherwise
*/
			double value1 = ((Chromosome) o1).getFitness();
			double value2 = ((Chromosome) o2).getFitness();
//}

        // for sorting in descending order
		// # Modified by Zhiyang Ong - 13 April 2005
		// Allow the values of fitness to be sorted in ascending order
		/*
        if (value1 < value2) return 1;          // lower object follows
        else if (value1 > value2) return -1;    // higher object preceeds
        else return 0;
		*/
		if (value1 < value2) return -1;          // higher object follows
        else if (value1 > value2) return 1;    // lower object preceeds
        else return 0;
    }
	
	/**
	 * Method to indicate if the normalised or non-normalised values should
	 * be used
	 * @param normalised indicates if the normalised values of fitness shall be used
	 * @return nothing
	 */
	/*
	public static void setFitness(boolean normalised) {
		normalise = normalised;
	}
	*/
}