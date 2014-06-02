/*
 * population package determines the bahaviour of chromosomes in the
 * population of the network. That is, each chromosome in the population
 * represents a network.
 * The behaviour of chromosmes is determined by modifying the data pertaining
 * to each chromosome
 */
package population;

import java.util.*;
import utility.*;
import population.graph.*;

/**
 * Class "SetOfChromosomes" contains all the Networks (Chromosomes) of this
 * generation of the evolution process
 * It ranks this set of Networks according to their "fitness", which indicates
 * the amount of redundancy and pleiotropy, and selects them for mutation
 * and mating based on their ranking.
 *
 * In versions 2.0 and later of "NetSim", the number of Networks in this
 * population may vary due to the addition or removal of Networks
 * (Chromosomes)
 *
 * @author Zhiyang Ong and Andy Lo
 * @version 0.3.7
 */

// Class definition...

// ======================================================================

public class SetOfChromosomes {
    // Declaring instance variables
    
    // Current set of networks
    private ArrayList population;
	 
	 // Probability for a random chromosome to be selected for mutation
//	 double prSelection; 
    
    /**
     * The maximum percentage of this Population of Networks that
     * can be removed
     * That is, no more than "MAX_RM_PERCENTAGE" * 100% of the
     * Population of Networks can be removed
     */
    public static final double MAX_RM_PERCENTAGE = 0.1;
	
	// Array of cost functions used in optimisation
	Function[] functions;
	
	// Array of Sums of fitness values for cost functions
	private double[] arrayOfSums;
    
    // ---------------------------------------------------------------
    
    // Default constructor...
    public SetOfChromosomes() {
        population = new ArrayList();
//        prSelection = 0.0;
    }
    
    // Standard Constructor 1...
    public SetOfChromosomes(ArrayList list) {
        population = list;
//		  prSelection = new double[list.size()];
    }
    
    /**
     * Modified by Andy 03/04/05:
     *      Chromosome now requires a nodelist as a reference as the bare
     *      minimum. ACTION needs to be discussed...
     */
    // Standard Constructor 2... 
//    public SetOfChromosomes(int size) {
//        population = new ArrayList(size);
//        /*for(int i=0; i<size; i++) {
//            population.add(i,new Chromosome(20,200));
//        }
//        System.out.println("###SIZE OF POPULATION IS:"+population.size());
////		  prSelection = new double[size];*/
//    }
    
    // ---------------------------------------------------------------
    
    /**
     * Method to rank this set of Networks (Chromosomes) [this population]
     * according to their fitness, which is determined by the amount of
     * pleiotropy and redundancy of this network
     * @param Comparator "comp" calculates the fitness of this Network
     *  (Chromosome) and indicates which Network is better (fitter)
     * @param ArrayList "pop" is a container for all the Networks
     *  (Chromosomes) of this population that will be ranked.
     * @post the list of Networks (Chromosomes) must be sorted in
     *  descending order of their fitness
     * @return sorted list of Networks based on their fitness values
     */
	// # Modified by Zhiyang Ong - 14 April 2005
	/**
	 * You do not have to call a 
	 */
    //public ArrayList sort(Comparator comp, ArrayList pop) {
	public void sort() {
        //Debugger.printErr("Before sort: getPopSize() = " +getPopSize());
		/**
		 * Array of Chromosomes to contain the population
		 * in an array so that it can be sorted by the modified
		 * mergesort method in the Class Arrays from the Java API
		 */
		//Chromosome[] arrChromos;
		Object[] arrChromos;
		/**
		 * Convert the ArrayList of Chromosomes into an array of
		 * Chromosomes
		 */
		//arrChromos = (Chromosome[])getCurPop().toArray();
		arrChromos = getCurPop().toArray();
		//Chromosome[] arrChromosomes = (Chromosome[]) arrChromos;
		/**
		 * Instantiate a Comparator to compare the Chromosomes
		 * for sorting purposes
		 */
		ChromFitnessComparator comparator = new ChromFitnessComparator();
		// Sort the population of Chromosomes
		Arrays.sort(arrChromos,comparator);
		// Put the Chromosomes back into the ArrayList population
		for(int i=0;i<arrChromos.length;i++) {
			getCurPop().set(i,arrChromos[i]);
		}
		//Debugger.printErr("After sort: getPopSize() = " +getPopSize());        
    }

    /**
     * Method to determine if these pair of Chromosomes are equivalent
     * Chromosomes/Networks (Clones of Chromosomes/Networks)
     * @param   pair    An array that contains Chromosomes (Networks) to be 
     *                  determined if they are identical
     * @return  <code>true</code>, if the Networks are clones of each other, 
     *          <code>false</code>, if the Chromosomes are different
     */
    public boolean checkIfTwins(Chromosome[] pair) {
	/*
	 * # Modified by Zhiyang Ong - 14 April to handle an array of Chromosomes
	 * # of arbitrary length, array need not be of size two.
     */
		// If there are less than two Chromosomes in "pair", they can't be twins
		if(pair.length < 2) {
//Debugger.printErr("Number of elements in pair is less than 0");
//System.out.println("Number of elements in pair is less than 0");
			return false;
		}
		// Contents of the first Chromosome
		if(pair[0] == null) {
			/**
			 * If a Chromosome is referenced to null,
			 * the Chromosomes can't be twins
			 */
//Debugger.printErr("The first element of pair is referenced to null");
//System.out.println("The first element of pair is referenced to null");
			return false;
		}
		// Get the first Chromosome
		Chromosome firstChromo = pair[0];
		// Get the ArrayList of lists of destination nodes
		ArrayList firstChromoList = firstChromo.getDataArray();
		// List of  destination nodes
		ArrayList cellList;
		// A destination node
		NodeImp cellListEntry;

		// Contents of the other Chromosomes
		// Get the (i)^{th} chromosome
		Chromosome otherChromo;
		// The ArrayList of lists of destination nodes for the (i)^{th} chromosome
		ArrayList otherChromoList;
		// List of destination nodes for the (i)^{th} chromosome
		ArrayList otherCellList;
		// Destination nodes for the (i)^{th} chromosome
		NodeImp otherCellListEntry;
		
		// For each chromosome in the array...
		for(int i=0; i<pair.length; i++) {
			if(pair[i] == null) {
				/**
				 * If a Chromosome is referenced to null,
				 * the Chromosomes can't be twins
				 */
//Debugger.printErr("An element in pair is referenced to null");
//System.out.println("An element in pair is referenced to null");
				return false;
			}
			// Get the (i)^{th} Chromosome of the array
			otherChromo = pair[i];
			// Get its data array
			otherChromoList = otherChromo.getDataArray();
			// Check whether its data array is equal to that of the first chromosome
			for(int j=0;j<firstChromoList.size();j++) {
				// Get the list of  destination nodes for the (j)^{th} Node
				cellList = (ArrayList)firstChromoList.get(j);
				otherCellList = (ArrayList)otherChromoList.get(j);
				
				// Are these adjacency lists of the same size
				if(cellList.size() != otherCellList.size()) {
					// No, the Chromosomes are not twins
//Debugger.printErr("The sizes of the adjacency lists are not equal");
//System.out.println("The sizes of the adjacency lists are not EQUAL");
//System.out.println("j is; "+j+" & i is: "+i);
					return false;
				}
				
				// Check if each destination node is the same in these lists
				for(int k=0;k<cellList.size();k++) {
					// Get the (k)^{th} destination node in the list
					cellListEntry = (NodeImp)cellList.get(k);
					/**
					 * Determine if this Node exists in the adjacency list
					 * of the other Chromosome
					 */
					if(!otherCellList.contains(cellListEntry)) {
						// No, the Chromosomes are not twins
//Debugger.printErr("The adjacency lists of the chromosomes in pair do not match");
//System.out.println("The adjacency lists of the chromosomes in pair do not match");
//Debugger.printErr("k is: "+k+" & j is: "+j+" & i is: "+i);
//System.out.println("k is: "+k+" & j is: "+j+" & i is: "+i);
						return false;
					}
					
					//otherCellListEntry = (NodeImp)otherCellList.get(k);
					// Are these nodes the same
					//if(!cellListEntry.equals(otherCellListEntry)) {
					// if(!cellListEntry.equals(otherCellListEntry)) {
						// No, they are not.
						// Hence, these Chromosomes are not twins
						//return false;
					//}
				}
			}
		}
		
		return true;
    }
    
    /**
     * Method to remove a number of Chromosomes from this Population
     * of Networks (Chromosomes)
     * The number of Networks to be removed is generated pseudo randomly.
     * The pseudo random generated number between 0 and 1 inclusive is
     * multiplied by the size of the population
     * 
     * The precentage of Networks removed from this Population must be
     * no greater than "MAX_RM_PERCENMAX_RM_PERCENTAGETAGE" * 100%
     * If the pseudo random generated number is less than
     * "MAX_RM_PERCENTAGE" * 100%, no Chromosomes are removed
     */
    public void removeChromo() {
		// # Modified by Zhiyang Ong - 13 April 2005
		// To be implemented sometime in the future...
    }
    
    /**
     * Method to add a Chromosome to the Population of Networks
     * (Chromosomes)
     * @param   ch  the Chromosome to be added to the Population
     * //@post the size of the Population must be incremented by 1
     * //@post this Chromosome must be contained in ArrayList "population"
     */
    public void addChromo(Chromosome ch) {
        // # Modified by Andy 07/04/05: changed to add chromosome
        population.add(ch);
    }
	
	/**
	 * Method to replace chromo at index i in the population of networks
	 * @param i is the index
	 * @param c is the replacing chromosome
	 * @return replaced chromosome
	 */
	public Chromosome replaceChromo(int i, Chromosome c) {
        Chromosome old = getChromo(i);
        population.set(i,c);
		return old;
    }
	 
	 /**
     * Method to return a Chromosome of the Population of Networks
     * (Chromosomes) at the index i
     * @param   i   the index of the Chromosome to be accessed
	 * @return  the i^{th} Chromosome
     */
    public Chromosome getChromo(int i) {
        return (Chromosome) population.get(i);
    }
    
    /**
     * Method to add a set of Chromosomes to this (current) population
     * Note that the set of Chromosomes can be empty
     *
     * @param   set the SetOfChromosomes containing chromosomes that will be
     *              will be added to this population
     * //@post Each element in the set "set" must be added to the current
     * // population
     */
    public void appendPop(SetOfChromosomes set) {
		if(set != null) {
			getCurPop().addAll(set.getCurPop());
		}	// else, ignore appending a null set
    }
    
    /**
     * Obtains the current set of Networks (this population)
     * @return this population
     */
    public ArrayList getCurPop() {
		return population;
    }
    
    /**
     * Determines the size of the population of Networks (Chromosomes)
     * //@post the size of the Population must be greater than or equal to 0
     * @return the size of this populations
     */
    public int getPopSize() {
		return population.size();
    }
	
	/**
	 * Method to normalise the population of chromosomes at the start of evolution
	 */
	public void normalize() {
		// Total amount of fitness for the population
		// Not set to +/- infinity, since it will mess up the calculations
		//int normSum = 0;
	
/*	
		// For each cost function...
		for(int i=0; i<functions.length; i++) {
			// Reset (amount of fitness for the population) for each cost function
			normSum = 0;
			
			// Determine its corresponding total amount of fitness for the population
			for(int j=0; j<getPopSize(); j++) {
				getChromo(j).createFitnessArr(functions.length);
				normSum = normSum + getChromo(j).getFitArrElem(i);
			}
			arrayOfSums[i] = normSum;
		}
*/
		// For all chromosome...
		for(int i=0; i<getPopSize(); i++) {
			// Reset (amount of fitness for the population) for each cost function
			//normSum = 0;
			
			/**
			 * Determine their corresponding total amount of fitness
			 * for the population for each cost function
			 */
			for(int j=0; j<functions.length; j++) {
				arrayOfSums[j] = arrayOfSums[j] + getChromo(i).getFitArrElem(j);
			}
		}
		
		// Assign fitness values for each Chromosomes
		for(int k=0; k<getPopSize(); k++) {
			pythagoras(getChromo(k));
		}
	}
	
	/**
	 * Method to normalise each value of fitness for each cost function
	 * and determine the fitness for this Chromosome using an extended
	 * version of Pythagoras'theorem for a n-dimensional space
	 *
	 * ### This assumes that the cost fuunctions are not correlated to each other 
	 *
	 * @param chromo is the Chromosome that will have its fitness determined
	 */
	public void pythagoras(Chromosome chromo) {
		// sum of the squares of normalised fitness values
		double sumSq = 0.0;
		// fitness value for the ith cost function
		double cfFitness;
		
		for(int i=0; i<chromo.getFitnessArr().length; i++) {
			// Get the fitness value for the ith cost function
			cfFitness = chromo.getFitArrElem(i);
			// Normalsie it with the array of sums
			cfFitness = cfFitness/arrayOfSums[i];
			// Square the normalised fitness value for the ith cost function
			cfFitness = cfFitness * cfFitness;
			// Add it to the sum of squares
			sumSq = sumSq + cfFitness;
		}
		
		// Take the square root of the sum of the squares to get the fitness
		double fitnessChromo = Math.sqrt(sumSq);
		// Set the fitness of the chromo
		chromo.setFitness(fitnessChromo);
	}
    
	/**
	 * Method to assign the array of cost functions used
	 * @param funcArr is the array of cost functions used
	 */
	public void setCostFunctions(Function[] funcArr) {
		functions = funcArr;
		// Create the array of sums of fitness values of cost functions
		arrayOfSums = new double[functions.length];
	}
	
	/**
	 * Method to obtain the array of cost functions used
	 * @return the array of cost functions used
	 */
	public Function[] getCostFunctions() {
		return functions;
	}
	
	
	/**
	 * Method to clone a set of Chromosomes
	 */
	public Object clone() {
		ArrayList clonePopList = new ArrayList();
		SetOfChromosomes cloneSet = new SetOfChromosomes(clonePopList);
		//cloneSet.setCostFunctions(this.getCostFunctions());
		//double[] arraySums = new double[this.arrayOfSums.length];
		
		int numChromos = this.getCurPop().size();
		for(int i=0; i<numChromos; i++) {
			Chromosome temp = this.getChromo(i);
			Chromosome cloneChromo = (Chromosome)temp.clone();
			cloneSet.addChromo(cloneChromo);
		}
		
		return cloneSet;
	}
}
