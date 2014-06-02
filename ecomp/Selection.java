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
import population.*;
import utility.*;

/**
 * This class selects pairs of Chromosomes for mating and mutation by the 
 * rank selection method. The selection is based on the comparison of pseudo 
 * random generated probabilities of selection with a set of predetermined 
 * probabilities. If the pseudo random value is larger than the predetermined 
 * value, a Network Chromosome is selected.
 *
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.1-andy
 * @since           0.3
 * @acknowledgement Mike Brooks - Formula for assigning the predetermined 
 *                  probabilities for selection whilst applying "Rank Method 
 *                  of Selection". This is obtained from the Artificial
 *                  Intelligence Course, Notes for Lectures 15-17 (2004), 
 *                  available <a href="http://www.cs.adelaide.edu.au/~third/ai/lectures/HTML/GeneticAlgorithms.htm">here</a>.
 */

// Class definition...

// ======================================================================

public class Selection {
    // Declare instance variables...
    
    /**
     * Probability of selection for all the Networks (Chromosomes)
     * in this (current) population of this generation, starting
     * from the fittest Chromosome to the worst Chromosome
     */
    private double[] prSelection;
    /**
     * This is the current population of Networks (Chromosomes)
     * that we are selecting pairs of Networks from
     */
    private SetOfChromosomes population;
    // The size of this population
    private int size;
    // The pseudo-random number generator for selection
    private Random rand = new Random();

    // ----------------------------------------------------------------
    
    // Default constructor...
    /**
     * Creates a new instance of Selection. This constructor SHOULD NOT BE
     * USED, since no instance variables of this class will be 
     * initialised, and no pair of Networks can be selected.
     */
    public Selection() {
        population = null;
        size = 0;
        prSelection = null;
    }
    
    // Standard Constructor
    /**
     * Creates a new instance of Selection, with a population. Each Network
     * from the supplied population <code>set</code> will be assigned a
     * predetermined value for probability of selection.
     * @param   set The population from which Networks will be selected from
     */
    public Selection(SetOfChromosomes set) {
        population = set;
        size = population.getPopSize();
        prSelection = new double[size];
        setPrSelection(getFirstProb(size));
    }
    
    // -----------------------------------------------------------------
    
    // Methods...
    
    /**
     * Assign the predetermined probabilities of selection for each Network
     * Chromosome in this population. The predetermined probability of 
     * selection for the "n"th Network is defined as:
     * <ul>(1-<i>CumulativeProbabilities</i> till [n-1]) 
     *                                      * (<code>prFirstChrom</code>)</ul>
     * where <i>CumulativeProbabilities</i> is the cumulative sum of
     * predetermined probabilities of selection.<p>
     *
     * Note that the predetermined probabilities of selection, beginning with
     * <code>prFirstChrom</code> for the fittest Network Chromosome, are
     * stored cumulatively in the double array <code>prSelection</code>.
     *
     * @param   prFirstChrom The predetermined probability of selection for
     *          the fittest Chromosome
     * @throws  PreconditionException If <code>prFirstChrom</code> is NOT
     *          between 0 and 1
     * @throws  PostconditionException If the array of "Predetermined
     *          probabilities of Selection" for each Network is NOT stored
     *          cumulatively
     * @throws  PostconditionException If the predetermined probability of 
     *          selection for the worst Network is NOT equal to 1
     */
    public void setPrSelection(double prFirstChrom) 
    throws PreconditionException, PostconditionException {
        Assertion.pre( (prFirstChrom >= 0.0) && (prFirstChrom <= 1.0),
                        "Probability is between 0.00 and 1.00 inclusive",
                        "Probability must be between 0.00 and 1.00");
        
        double leftOver = 1.00;     // unassigned probability
        double prob;                // probability to assign
        // the array of predetermined probabilities
        double[] target = getPrSelection();
        
        for (int i = 0; i < size; i++) {
            // for each element of array, calculated probability to assign
            prob = leftOver * prFirstChrom;
            target[i] = prob;       // assign the probability
            leftOver -= prob;       // subtract the assigned amount
        }

        // calcuate cumulative probabilities
        for (int i = 1; i < size; i++) target[i] += target[i-1];
        // ensures the last element has prSelection = 1
        target[size - 1] = 1.0;
        
        Assertion.post( checkCumulative(target),
                "The cumulative probability array is ordered correctly",
                "The cumulative probability array is NOT ordered correctly");
        Assertion.post( target[target.length-1] == 1,
                "Probability for worst Network equals 1",
                "Probability for worst Netwrok does not equal to 1");
    }

    /**
     * Convenience method to check if an array of double precison numbers are
     * sorted.
     * @param   arr The array to be checked
     * @return  true if the values are sorted in ascending order
     */
    private boolean checkCumulative(double[] arr) {
        for(int i=1; i < arr.length; i++) {
            if (arr[i] < arr[i-1]) return false;
        }
        return true;
    }
    
    /**
     * Returns the list of predetermined probabilities of selection for each
     * Network Chromosome in this population.
     * @return The list of predetermined probabilities of selection in
     *  cumulation
     */
    public double[] getPrSelection() {
        return prSelection;
    }
    
    /**
     * Returns the predetermined probability of selection for the fittest
     * Chromosome. This probability is calculated based on the size of the 
     * Population of of Networks.<p>
     *
     * Note that the return value depends on the size of this population:
     * P1=[Pn]/[1-CumulativeSumofProbabilities]
     * Using the rank method of selection results in selecting the middle
     * range of chromosomes
     *
     * ### MUST BROADEN THE SPECTRUM OF CHROMOSOMES' LIKELIHOOD OF SELECTION
     * ### CONSIDER USING NEW METHOD NEXT VERSION
     *
     * @param   popSize The size of the population of Networks
     * @return  The predetermined probability of selection for the fittest
     *          Network
     * @throws  PreconditionException If <code>popSize</code> is less than 0
     * @throws  PostconditionException If the returned predetermined
     *          probability is NOT between 0 and 1
     */
    private double getFirstProb(int popSize)
    throws PreconditionException, PostconditionException {
        Assertion.pre( (popSize >= 0),
                        "Population size is " + popSize,
                        "Population size must be greater than 0");
// # Modified by Zhiyang Ong 31 Mar 2005
//        return 0.02 + (5* Math.exp(-size/25) ) / 10;
return 0.02 + 0.5 * Math.exp(-size/25);
    }
    
    /**
     * Selects a pair of Chromosomes. This implies that when this method is
     * called, a pair of chromosomes will be selected.<p>
     * This selection is based on the comparison of the pseudo-random
     * generated probability of selection with the appropriate value of the
     * predetermined value of probability of selection. That is:
     * <ul>If (pseudo value) < (predetermined value), a Chromosome of the
     * pair is selected.</ul><p>
     * Once two chromosomes have been selected, they will be mated and/or
     * mutated in another method
     *
     * @return  A pair of Chromosomes for mutating and mating
     * @throws  AssertionException If the size of this population is NOT
     *          greater than 0
     * @throws  PostconditionException If the returned array of Chromosomes
     *          is NOT of length 2
     */
    public Chromosome[] selectPairChromo() 
    throws AssertionException, PostconditionException{
        Assertion.asrt( population.getPopSize() > 0,
                        "The population is not empty",
                        "The population must not be empty");
        // instantiat an array of 2 Chromosomes
        Chromosome[] pair = new Chromosome[2];
        
        // generate a pseudo-random number
        double randNum = rand.nextDouble();
        // select the first chromosome, by comparing probability values
        for(int i = 0; i < size; i++) {
            if(randNum < prSelection[i]) {
                 // obtain the chromsome
                 pair[0] = (Chromosome) population.getChromo(i);
// # Modified by Zhiyang Ong 29 Mar 2005
pair[0].setPrSelection(randNum);
Debugger.debug("fitness for 1st Chromosome is "+pair[0].getPrSelection());
                 break;
            }
        }
        
        // generate another pseudo-random number
        randNum = rand.nextDouble();
        // select the second chromosome 
        for(int i = 0; i < size; i++) {
            if(randNum < prSelection[i]) {
                 // obtain the chromsome
                 pair[1] = (Chromosome) population.getChromo(i);
// # Modified by Zhiyang Ong 29 Mar 2005
pair[1].setPrSelection(randNum);
Debugger.debug("fitness for 2nd Chromosome is "+pair[1].getPrSelection());
                 break;
            }
        }
        
        Assertion.post( pair.length == 2,
                        "Returning a pair of chromosomes",
                        "Must be returning a pair of chromosomes");
// # Modified by Zhiyang Ong 28 Feb 2005
/*
Assertion.post( (0 < pair[0]) && (pair[0] <= 1),
	"The first chromosome is selectable",
	"The first chromosome is NOT selectable; Pr(selection) > 1 or < 0");
Assertion.post( (0 < pair[0]) && (pair[0] <= 1),
	"The first chromosome is selectable",
	"The first chromosome is NOT selectable; Pr(selection) > 1 or < 0");
*/
        return pair;
    }    
}
