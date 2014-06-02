/**
 * ecomp package evolves the set (population) of networks (Chromosomes)
 * using Genetic Algorithms to find the optimal balance between pleiotropy
 * and redundancy. This mimics the process of biological evolution using
 * natural selection of the best networks (fittest Chromosomes) and
 * inheritance of network properties (traits)
 */
package ecomp;

//importing packages
import java.util.Random;
import java.util.ArrayList;
import population.*;
import utility.*;

/**
 * This class selects chromosomes for mating and mutation by tournament 
 * selection and replaces chromosomes by the closest fitness algorithm. 
 * Tournament seletion is where for each selection "n" chromosomes from
 * the population will be selected as condidates, and the best of the "n" 
 * chromosomes will be used in evolution. <p>
 *
 * The replacecment algorithm is required to insert the resulting chromosome
 * from crossover and mutation into the population, and to eject one out. The
 * closest fitness will find the chromosome with the least different to 
 * replace. This will ensure constant diversity in the population.
 *
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.7
 * @since           0.3
 * @acknowledgement Professor Zbigniew Michalewicz - for the theory behind
 *                  tournament selection, and replacement algorithm after
 *                  evolving chromosomes
 */

// Class definition...

// ======================================================================

public class SSEASelection {
    // Declare instance variables...
    
    /**
     * This is the current population of Networks (Chromosomes)
     * that we are selecting pairs of Networks from
     */
    private SetOfChromosomes population;
    // size of the population
    int size;
    // The pseudo-random number generator for selection
    private Random rand = new Random();

    // ----------------------------------------------------------------
    
    // Default constructor...
    /**
     * Creates a new instance of Selection. This constructor SHOULD NOT BE
     * USED, since no instance variables of this class will be 
     * initialised, and no pair of Networks can be selected.
     */
    public SSEASelection() {
        population = null;
        size = 0;
    }
    
    // Standard Constructor
    /**
     * Creates a new instance of Selection, with a population. 
     * @param   set The population from which Networks will be selected from
     */
    public SSEASelection(SetOfChromosomes set) {
        population = set;
        size = population.getPopSize();        
    }
    
    // -----------------------------------------------------------------
    
    // Methods...
    
    /**
     * Selects the best chromosomes out of <code>n</code> chromosomes selected
     * from the population. The best chromosome is defined as that with the
     * MINIMUM value from the cost function.<p>
     *
     * Note that candidates can be selected more than once, and the number of 
     * candidates selected can exceed the size of the population.
     *
     * @param   n   the number of candidates to use in tournament selection
     * @throws  PreconditionException If the number of candidates to be used
     *          in the tournament is not positive.
     * @throws  AssertionException If the size of this population is NOT
     *          greater than 0
     * @throws  AssertionException The chromosome to be returned does not have
     *          the fitness, which is the smallest value in the tournament  
     *          fitness array
     * @return  A chromosome as a parent for mutating and mating
     */
    public Chromosome tournamentSelectFrom(int n) throws AssertionException {
        Assertion.asrt( size > 0, "The population is not empty",
                                  "The population must not be empty");
        Assertion.pre ( n > 0 , 
                    "The number of candidates for tournament is " + n,
                    "The number of candidates must be greater than 0 (n>0)");

        // instantiate an array of "n" Chromosomes
        Chromosome[] candidate = new Chromosome[n];
        double[] fitness = new double[n];
        
        // select n chromosomes at random
        for (int i = 0; i < n; i++) { 
            candidate[i] = population.getChromo(rand.nextInt(size));
            fitness[i] = candidate[i].getFitness();    
        }
        
        // find the best of the chromosomes
        Chromosome winner = candidate[0];
        for (int i = 1; i < n; i++) {
            // assume that the fitness value is cost, that will be minimisd
            if(candidate[i].getFitness() < winner.getFitness())
                winner = candidate[i];
        }

/*        
        Assertion.asrt( winner.getFitness() == min(fitness),
            "The winning chromosome has the smallest cost of all candidates",
            "The chromosome to be returned has the cost of value " +
                winner.getFitness() + ",\n    whereas the smallest value " +
                "of cost of all candiates is " + min(fitness) );
*/
        return winner;
    }
    
    /**
     * Finds the minimum double precision number in a given array.
     * @param m an array of double numbers
     * @return the smallest double in the array m
     */
    private double min (double[] m) {
        // let the smallest number be the value of the first element
        double result = m[0];
        // look at each element and try to find a smaller number
        for (int i = 0; i < m.length; i++) if (m[i] < result) result = m[i];

        return result;
    }
    
    /**
     * Replaces a chromosome from the population with <code>c</code> using 
     * the closest fitness replacement algorithm.
     *
     * # Modified by Andy Lo 10 Dec 2005
     *      Changed to store the indices of chromosomes with the higher and 
     *      lower closest fitness. Then the new chromosome will replace the 
     *      just worse chromosome in the population, OR replace the worst of 
     *      the better chromosomes.
     *
     * @param   c The chromosome that will replace another chromosome in the
     *          population
     * @throws  AssertionException if the index of the element to be replaced
     *          is out-of-bounds
     */
    public void insert (Chromosome c) throws AssertionException{
        // obtain the fitness of this chromosome
        double chromFitness = c.getFitArrElem(0);
        // temporary storage for fitness of the chromosome of interest
        double tempFitness;
        // stores fitness differences between "c" and another chromosome
        double diff;
        // stores the smallest difference
        double minDiffFitter = Double.POSITIVE_INFINITY;
        double minDiffLessFit = Double.POSITIVE_INFINITY;
        // indices of the chromosome contributing to the smaller differences
        int indexLowerChrom = -1;
        int indexHigherChrom = -1;
        // the index for the chromosome to be replaced.
        int index = -1;
        
        /**
         * Need to check every chromosome, because it cannot be gauranteed
         * that the population will be sorted
         */
        for (int i = 0; i < size; i++) {
            // obtain fitness of a chromosome within the population
            try {
                tempFitness = (population.getChromo(i)).getFitArrElem(0);
            } catch(NullPointerException e) {
                // this only happens to empty population
                tempFitness = Double.POSITIVE_INFINITY;
            }
            // find the differences between "c" and another chromosome
			diff = Math.abs( tempFitness - chromFitness);
		
            /**
             * Treat chromosomes with higher and lower fitness values 
             * differently. If the current chromosome has higher fitness than
             * the one to be inserted, check if it's the chromsome with the 
             * smallest fitness that is larger. If so, store relevant
             * information. Vice versa, for chromosomes with lower fitness.
             */
		    if (chromFitness < tempFitness) {
		        if (diff < minDiffFitter) {
		            minDiffFitter = diff;    
		            indexHigherChrom = i;
		        }
		    } else {
		        if (diff < minDiffLessFit) {
    		        minDiffLessFit = diff;
		            indexLowerChrom = i;
		        }
		    }
        }
        
        /**
         * Firstly check if a valid index has been registered for the 
         * chromosome closest to, but worse than, the chromosome to be 
         * inserted. If so, this chromosome will be replaced. However, if no
         * worse chromosome are found, we will replace with the worst of the
         * better chromosome (with index as indexHigherChrom) to induce
         * diversity
         */
        if      (indexLowerChrom >= 0)  index = indexLowerChrom;
        else if (indexHigherChrom >= 0) index = indexHigherChrom;
        else return; 
        
        // check if we have a valid index
        Assertion.asrt(index >= 0 && index < size, 
                        "Chromosome to be replaced has index " + index,
                        "Array index of the Chromosome to be replaced is " +
                        "out-of-bounds:" + index);
        
        /**
         * set the new chromosome at the position corresponding to "index" and
         * discard the original chromosome
         */
        (population.getCurPop()).set(index, c);
    } 
}

