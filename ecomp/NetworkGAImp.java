/**
 * ecomp package evolves the set (population) of networks (Chromosomes) using
 * Genetic Algorithms to find the optimal balance between pleiotropy and
 * redundancy. This mimics the process of biological evolution using natural
 * selection of the best networks (fittest Chromosomes) and inheritance of
 * network properties (traits).
 */
package ecomp;

//importing packages
import java.util.Random;
import java.util.ArrayList;
//import java.util.*;
import population.*;
import population.graph.*;
import utility.*;

/**
 * This implements the behaviour of the evolutionary computation specified in
 * interface <code>NetworkGA</code>. This will be the basic class used for
 * all evolutionary computation. It's main function is to initialise a
 * population of chromosomes containing network nodes. this population of
 * chromosomes are evolved by using operations of crossover (mating),
 * mutation and cloning (straight copy).<p>
 * 
 * There is only one crossover functin in this implementation, and it is a
 * version of fixed-length two-point crossover, where we will definitely be 
 * swapping 50% of the cells in a chromosome every time we mate.<p>
 *
 * The mutation method is elementary, as it will randomly add or remove nodes
 * for each cell. The number of cells to alter will vary from generation to
 * generation.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.8
 * @since   0.3
 * @acknowledgement Professor Zbigniew Michalewicz - for introducing the 
 *                  method of steady state evolution.
 * @see     Chromosome    
 */

// Class definition...

// ========================================================================

public class NetworkGAImp implements NetworkGA {
    // Declare constants

    // # Modified by Zhiyang Ong - 8 April 2005
    // Height and Width of the GUI's workspace in pixels
    private final int HEIGHT_WORKSPACE = 950; 
    private final int WIDTH_WORKSPACE = 700;
    // # Modified by Andy Lo, 18 Apr 2005
    private final int MAX_EDGE_COST = 
                   (int) Math.sqrt( (WIDTH_WORKSPACE * WIDTH_WORKSPACE) +
                                    (HEIGHT_WORKSPACE * HEIGHT_WORKSPACE) );
    /**
     * Maximum limit for the capacity of server/client Node of processing 
     * data in Mbps
     */
    private final double SERVER_CAPACITY    = 1000;
    private final double CLIENT_CAPACITY    = 16;
    /**
     * These constants will determine the probability of adding or removing 
     * nodes in a mutation operation
     */
    private final double ACCUM_PROB_REMOVE  = 0.40;
    private final double ACCUM_PROB_ADD     = 0.80;
    /**
     * Maximum number of generation required for repair for a Node object
     */
    public  final int    MAX_REPAIR_GEN     = 20;
    /**
     * This is used to offset the base value used to determine the number of
     * chromosome cells to be changed in the mutation method. This is 
     * required because:
     * [Quote Java API: java.lang.Math]
     *      If the first argument is positive zero and the second argument is
     *      less than zero, or the first argument is positive infinity and 
     *      the second argument is greater than zero, then the result is 
     *      positive infinity.
     */
    public final int GEN_OFFSET = 1;

// ----------------------------------------------------------------------
    // Declare instance variables

   /**
     * The Probability of Crossover and Probability of Mutation are variables
     * that can vary between generations in evolution
     * However, in version 1.0 of the "NetSim" software, we shall keep this
     * constant
     */
    // Probability of Mutation for a Chromosome (Network); Pr(Mutation)
    private double prMutation;
    /**
     * Probaility of Crossover for a pair of Chromosomes (Networks);
     * Pr(Crossover)
     */
    private double prCrossover;
    // Number of chromosomes to include in the next population initialisation
    private int numChromInPop;
    // The current ("curGen"th) generation of evolution
    private int curGen = 0;
    /**
     * The current set (population) of Chromosomes (Networks) for
     * this generation
     */
    private SetOfChromosomes curPop;
    // a pseudo random number generator
    private Random rand = new Random();
    // a list to store all nodes created
    // # Modified by Zhiyang Ong
    //  private ArrayList nodeList = new NodeList();
    private ArrayList currNodeList = new ArrayList();
	
	// The ith node's clustering coefficiency
	private double connectivity;
	// top 5 clustering coefficiencies
	private double[] cluscoeff={0.0,0.0,0.0,0.0,0.0};
	/**
	 * Indices of the nodes with the top 5 clustering coefficiencies
	 * in the population
	 */
	private int[] top5connect= new int[5];
	// Pr(symbiosis)-probability of symbiosis occurring for a pair of chromosomes
	private double prSymbiosis=0.0;
	// Perform mutualism if true, else do commensalism
	private boolean mutualism=false;
	
	private int numOfServers=0;
	private int numOfClients=0;

// ----------------------------------------------------------------------

    // Default constructor
    /**
     * Creates an instance of NetworkGAImp. This constructor sets all
     * variables to null or 0, where appropriate
     */
    public NetworkGAImp() {
        prMutation = 0.0;
        prCrossover = 0.0;
        numChromInPop = 0;
        curPop = new SetOfChromosomes();
    }

    // Standard Constructor
    /**
     * Creates an instance of NetworkGAImp with a population initialised and
     * ready for evolution.
     *
     * <b>Modified</b> by Andy Lo 13 Apr 2005: the population size must be
     * set prior to initialisation of population. This requires the size of
     * the population to be set. This initial size needs to be provided in
     * the constructor.
     *
     * @param   pc The probability of crossover for chromosomes in this
     *          evolution process
     * @param   pm The probability of mutation for chromosomes in this
     *          evolution process
     * @param   numServers The number of servers initially in the Network
     * @param   numClients The number of clients initially in the Network
     * @param   size The number of chromosomes intially in the population
     * @throws  PreconditionException If probabilities are NOT between 0 and 1
     * @throws  PreconditionException If numServers or numClients is less
     *          than 0
     * @throws  PreconditionException If size of the population is less than 1
     */
    public NetworkGAImp(double pc, double pm, int numServers, int numClients,
    int size) throws PreconditionException {
        Assertion.pre( (pc >= 0.0)&&(pc <= 1.0)&&(pm >= 0.0)&&(pm <= 1.0),
                "Probabilities passed are pc = " + pc + ", pm = " + pm,
                "Probabilities passed MUST be between 0 and 1");
        Assertion.pre( (numServers >= 0) && (numClients >= 0),
                "There will be " +  numServers +" Servers and " +
                    numClients + " Clients in the network",
                "Number of Servers and Clients MUST BE non-negative");
        // # Modified by Andy Lo 13 Apr 2005: check for positive pop size
        Assertion.pre( size > 0,
                "There will be " + size + " chromosomes in the population",
                "Number of Chromosomes in a population MUST BE positive");
        // # Modified by Zhiyang Ong 30 Mar 2005
        // prMutation = prMutate;
        prMutation = pm;
        // prCrossover = prXover;
        prCrossover = pc;
        /**
         * # Modified by Andy Lo 13 Apr 2005: 
         *  Was:     popSize = numServers + num Clients
         *  Problem: There is no correlation between population size and
         *           and number of servers and clients
         *  Changed: popSize should be the population size desired
         */
        numChromInPop = size;
		
numOfClients=numClients;
numOfServers=numServers;
        /**
         * # Modified by Andy Lo 13 Apr 2005: 
         *  Was:     popSize = numServers + num Clients
         *  Problem: There is no correlation between population size and
         *           and number of servers and clients
         *  Changed: popSize should be the population size desired
         */        
        setCurPop(initPop(numServers,numClients));
    }
    
    // ----------------------------------------------------------------------
    
    // Methods...

    /**
     * Evolves a given population of Chromosomes once. It will be called
     * repeatedly to search for a Network with optimal properties that
     * contribute to Pleiotropy and Redundancy. Each call to this method
     * resembles one generation of evolution.<p>
     *
     * The <code>SetOfChromosomes</code> passed in should already be sorted
     * in descending order of fitness, with the fittest chromosome at the top
     * of the population. This may help easy assignment of probability of
     * selection. Thus, each resulting <code>SetOfChromosomes</code> is
     * sorted by fitness, before it is returned.<p>
     *
     * For a large enough population, <b>elitism</b> is used to retain a pair
     * of chromosomes from the older generation for the next generation.
     * However elitism doesn't provide enough diversity for small populations,
     * so it is not used for small populations.<p>
     *
     * Selection of chromosomes is carried out by the <code>Selection</code>
     * class. Whether a selected pair of chromosomes will crossover and/or
     * mutated is based on the predetermined values <code>prCrossover</code>
     * and <code>prMutation</code>. If neither have occured, the chromosomes
     * are simply <i>cloned</i>.
     *
     * @param   func The array of fitness functions determining the amount of
     *          pleiotropy and redundancy of the network
     * @see     Selection
     */
    // # Modified by Zhiyang Ong 30 Mar 2005
    //   method changed to return void and update pop
    //   param set is not required, since curPop is automatically updated

    public void evolve(Function[] func) {
        int popSize = getPopSize();

        /**
         * get the population size, and ensure the size is an even number,
         * because chromosomes are selected in pairs
         */
        // # Modified by Zhiyang Ong 30 Mar 2005
        //      int newSize = set.getSize();
        //      newSize = (newSize%2 == 1) ? newSize+1 : newSize;
        /**
         * Since the input parameter SetOfChromosomes set has been removed,
         * and we are updating curPop at the end of the method definition,
         * the parameter newSize can be replaced by the instance variable popSize
         */

        if(popSize%2 ==1) {  // if popSize is odd, make it even by adding one
            popSize++;
        }

        /**
         * # Modified by Zhiyang Ong 30 Mar 2005
         *      create a "SetOfChromosomes" to temporarily store the new
         *      generation of chromosomes
         * # Modified by Andy Lo 12 Apr 2005
         *      Different constructor of SetOfChromosomes is used.;
         */ 
        SetOfChromosomes newPop = new SetOfChromosomes();

        /**
         * For a large enough population, a pair of chromosomes are chosen
         * to be retained.
         *
         * # Modified by Zhiyang Ong 30 Mar 2005:
         *      Add the top two Chromosomes, in terms of fitness, of current
         * population to newPop
         */
		// # Modified by Zhiyang Ong - 23 Apr 2005
		// ### A population is considered large enough if it exceeds criticalPopSize
		int criticalPopSize = 20;
        if(popSize >= criticalPopSize) {
            // Retain the top two Chromosomes because of Elitism
            newPop.addChromo(curPop.getChromo(0));
            newPop.addChromo(curPop.getChromo(1));
        }
        
        // # Modified by Zhiyang Ong 30 Mar 2005
        // Selection selection = new Selection(set);   // prepare for selection
        //Debugger.printErr("getPopSize() = " +getPopSize());
        Selection selection = new Selection(curPop);
        

        /**
         * Colonize the rest of the population with chromosomes resulting
         * from the process of mating and mutation.
         */
        // # Modified by Zhiyang Ong 30 Mar 2005
        // while (newPop.size() < newSize) {
        // Method setOfChromosomes.size() does not exist
        // removed popSize since set is no longer an input parameter

        while(newPop.getPopSize() < popSize) {
            // select a pair of chromosomes
            Chromosome[] pair = selection.selectPairChromo();

            /**
             * each chromosome is cloned, since the old generation should not
             * be altered.
             */
            // # Modified by Zhiyang Ong 30 Mar 2005
            // # casting from return Object to Chromosome is required

            pair[0] = (Chromosome)pair[0].clone();
            // # Modified by Zhiyang Ong 30 Mar 2005
            // # casting from return Object to Chromosome is required
            pair[1] = (Chromosome)pair[1].clone();

            /**
             * compare a random generated probability with a predetermined
             * value "prCrossover", if the generated value is less, mate the
             * pair of chromosomes.
             */

            if(rand.nextDouble() < prCrossover)
                pair = crossover(pair[0], pair[1]);

            /**
             * compare a random generated probability with a predetermined
             * value "prMutation", if the generated value is less, mutate a
             * chromosome.
             */

            if(rand.nextDouble() < prMutation) pair[0] = mutate(pair[0]);
            if(rand.nextDouble() < prMutation) pair[1] = mutate(pair[1]);

            newPop.addChromo(pair[0]);    // add results to new generation
            newPop.addChromo(pair[1]);
        }
        
		for(int z=0; z<func.length; z++) {
        	func[z].map(newPop,z);         // calculate appropriate fitness values
        }
		// Normalise the fitness values of the population's chromosomes
		newPop.normalize();
        newPop.sort();              // sort the population

        //Debugger.printErr("newPop.getPopSize() = " + newPop.getPopSize());
                
        // # Modified by Zhiyang Ong 30 Mar 2005
        // curPop is updated immediatedly
        curPop = newPop;

        increGen();

        // # Modified by Zhiyang Ong 30 Mar 2005
        // return newPop;

    }


    /**
     * Evolves a given population of Chromosomes once usining the Steady-State
     * Evolution Algorithm. It will be called repeatedly in order to obtain
     * a Network with optimal properties that contribute to Pleiotropy an
     * Redundancy. Each call to this method resembles one generation of
     * evolution.<p>
     *
     * In steady-state evolution algorithm, 2 chromosomes are selected using
     * tournament selecetion. These two chromosomes are cloned, as we do not 
     * wish to alter the original population. In this algorithm,
     * crossover of 2 chromosomes will be carried out if a pseudo random 
     * number is less than the predetermined values of 
     * <code>prCrossover</code>. At first, only one of the chromosomes may be
     * mutated using similar method. A function will be applied to it. Then 
     * this chromosome is placed back into the population using "closest 
     * fitness" method. Then if <code>replaceTwo</code> is true, the second 
     * chromosome will be mutated and so on.<p>
     *
     * It is assumed that the current population is already sorted with the
     * best chromosome at the top. This will help selecting the chromosome to 
     * be drawn in the graph, simplify insertion sort when replacing the
     * chromosomes, facilitate finding the top "x" chromosomes when using
     * elitism. Elitism is useful in a large population to retain the better
     * solutions, but it may limit diversity for small populations. <p>
     *
     * Implemented by Andy 11/04/05.
     *
     * @param   func The function determining the fitness of the network.
     * @param   replaceTwo Determines, where 1 or 2 chromosomes will be used 
     *          to replace chromosomes in the population, since the result of
     *          selection provide 2 chromosomes
     * @see     SSEASelection
     */
	// to accomodate for more than one function, use array of Functions
    public void steadyStateEvolve(Function[] func, boolean replaceTwo) {
        // declare & instantiate tournament selection
        SSEASelection select = new SSEASelection(curPop);
        
        // select a pair of chromosomes using tournament selection
        Chromosome[] pair = new Chromosome[2];
        pair[0] = select.tournamentSelectFrom(2);
        pair[1] = select.tournamentSelectFrom(2);
        
        // clone them
        pair[0] = (Chromosome)pair[0].clone();
        pair[1] = (Chromosome)pair[1].clone();
        
        /**
         * compare a random generated probability with a predetermined
         * value "prCrossover", if the generated value is less, mate the
         * pair of chromosomes.
         */
        if(rand.nextDouble() < prCrossover)
            pair = crossover(pair[0], pair[1]);
        
		// do symbiosis
		if(rand.nextDouble() < prSymbiosis) {
			pair=symbiosis(pair[0],pair[1]);
		}
		
        /**
         * compare a random generated probability with a predetermined
         * value "prMutation", if the generated value is less, mutate a
         * chromosome.
         */
        if(rand.nextDouble() < prMutation) pair[0] = mutate(pair[0]);
// use for loop to apply each cost function on to the chromosome
pair[0].createFitnessArr(getCurPop().getCostFunctions().length);
		for(int i=0; i<func.length; i++) {
Debugger.debug("$$$$$$$The value of i is: "+i);
Debugger.enableTrace(true);
Debugger.debug("$$$$$$$The value of i is: "+i);
Debugger.debug("");
Debugger.debug("func[i] IS EQUAL TO NULL??? "+(func[i] == null));
Debugger.debug("");
Debugger.debug("$$$$$$$The value of getNumGen() is: "+getNumGen());	// OK
Debugger.debug("");
Debugger.debug("Is pair[0] equal to null? "
	+(pair[0].getFitnessArr()==null));
Debugger.debug("");
Debugger.enableTrace(false);		// Not OK
			func[i].apply(pair[0],i);        // apply functions to it
		}
		
		// Assign the overall fitness value for chromosome pair[0]
		getCurPop().pythagoras(pair[0]);
		
        //func.apply(pair[0]);        // apply function to it
        select.insert(pair[0]);     // replace in population
        
        // redo for second if replaceTwo is true
        if(replaceTwo) {
            if(rand.nextDouble() < prMutation) pair[1] = mutate(pair[1]);
// use for loop to apply each cost function on to the chromosome 
pair[1].createFitnessArr(getCurPop().getCostFunctions().length);
				for(int j=0; j<func.length; j++) {
					func[j].apply(pair[1],j);        // apply functions to it
				}
			// Assign the overall fitness value for chromosome pair[1]
			getCurPop().pythagoras(pair[1]);
			//func.apply(pair[1]);
            select.insert(pair[1]);
        }
		
		//pair=symbiosis(pair[0],pair[1]);
		//select.insert(pair[0]);     // replace in population
		//select.insert(pair[1]);
        
        increGen();
    }


    /**
	 * ### Modified by Zhiyang Ong - 31 July 2005
     * Performs uniform crossover on a pair of Chromosomes.<p>
     *
     * The boundary between elements in a chromosome form the cut-points.<p>
     *
     * Every other cell (odd/even) that is bounded by the cut-points is swapped
     * (crossed over) between the two chromosomes. The result consists of two
     * chromosomes inhereting properties from their parents, whilst reducing
	 * biasness in the mating process.<p>
     *
     * <i>Modified by Andy Lo 15 Apr 2005</i>: added precondition check that
     * the chromosomes must share the same nodeList. This also implies that
     * the chromosomes must be of the same length.
     *
     * @param   ch1 The first <code>Chromosome</code> for mating (crossover)
     * @param   ch2 The other <code>Chromosome</code> for mating. i.e.
     *          <code>ch1</code> is <code>ch2</code>'s partner
     * @return  A pair of Chromosomes resulting from the "mating" process,
     *          marriage, in an array of 2 Chromosomes (Networks)
     * @throws  PreconditionException If the node list for the chromosomes
     *          are not the same.
     * @throws  PreconditionException If the chromosome is of non-positive
     *          length
     * @throws  PostconditionException If the array of Chromosomes returned
     *          is not of length 2.
     */
    public Chromosome[] crossover(Chromosome ch1, Chromosome ch2)
    throws PostconditionException{
        // # Modified by Andy Lo 15 Apr 2005: added precondition check


/*
        Assertion.pre( (ch1.getNodeList()).equals(ch2.getNodeList()),
                "Both chromosomes share the same node list",
                "ERROR!!! Both chromosome SHOULD share the SAME node list");
*/

        Assertion.pre( ch1.getLength() > 0,
                "The chromosomes have length = " + ch1.getLength(),
                "ERROR!!! The chromosomes MUST not be of 0 length" );
	// ### Modified by Zhiyang Ong - 31 July 2005
	/**
	 * ### MISSING EXPLICIT description of assumption!
	 * Normal statement should read Chromosome 1 has length "ch1.getLength(),"
	 * as opposed to mentioning that they have the same length.
	 * An assumption was made that they were the same. This assumption
	 * has not been verified, or explicitly stated.
	 */

        //Debugger.debug(" Crossing: " + ch1 + "\n" + ch2 + "\n");
        // # Modified by Zhiyang Ong 31 Mar 2005
        /**
         * Set crossover points before the end of the shorter Chromosome,
         * if they are not of the same length
         * This prevents IndexOutOfBoundsException(s) from being thrown when
         * 
         */
        // int len = ch1.getLength();        // get the length of the chromosome
        int len;    // length of the shorter chromosome
        if(ch2.getLength() < ch1.getLength()) {
            len = ch2.getLength();
        }else{
            // ch2.getLength() >= ch1.getLength()
            len = ch1.getLength();
        }

		// Determine if every odd or even element will be swapped
		// Indicate if even elements will be swapped
		boolean even = rand.nextBoolean();
		int xPt;	// Starting index for Crossover point
		if(even) {
			// Swap even elements, commence at index 1
			xPt = 1;
		}else{
			// Swap odd elements, commence at index 0
			xPt = 0;
		}
        
		// obtain the contents within each chromosome
        ArrayList cells1 = ch1.getDataArray();
        ArrayList cells2 = ch2.getDataArray();

        //Debugger.debug("Swapping from " + XPt1 + " to " + (XPt2-1));
        // swap the contents between the cut-points for every other element
		int j=0; // 
        for (int i=xPt; i<len; i+=2) {
            Object temp = cells2.get(j);
            cells2.set(j, cells1.get(j));
            cells1.set(j, temp);
        }
        //Debugger.debug(" Results:\n " + ch1 + "\n" + ch2 + "\n");

		// Finishing the methiod body...
        Chromosome[] pair = {ch1, ch2};
        Assertion.post( pair.length == 2,
                        "The result array is of length 2",
                        "The result array is NOT of length 2");
        return pair;
    }

    /**
     * Simulates the process of mutation of a Chromosome. This will modify
     * the contents of the chromosome by adding or removing connected nodes
     * for some randomly selected cells.<p>
     *
     * The number of cells to alter must be limited. It is desirable to allow
     * more changes to the chromosomes at earlier generations of evolution,
     * compared to later stages, where changes should be relatively small.<p>
     *
     * The maximum allowable cells to change is limited by the variable
     * <code>fraction</code>. At present, <code>fraction</code> decreases
     * linearly from 50% to 10% until the 200th generations of evolution.
     * After the 200th generation, the value remains constant at 10%.<p>
     *
     * The above method for calculating the maximum allowable change is
     * rather injudicious and is dependent on the number of generations to
     * evolve. 200 generations is just a number out of speculation. A more
     * informative method will be to vary the maximum allowable change by
     * <b>observing the diversity</b> of results. This, for example, may be
     * measured as the <b>variance of fitness</b>. If the variance remains
     * within <i>similar</i> for many generations, the allowable change
     * should increase to a maximum of, say, 50%, otherwise it should be
     * decreased to, say, 10%.<p>
     *
     * <i>Modified by Andy Lo 15 Apr 2005</i>: added precondition check that
     * the chromosome must of positive length.
     *     
     * @param   ch The <code>Chromosome</code> to be mutated
     * @throws  PreconditionException If the chromosome is of non-positive
     *          length
     * @return  A Chromosome resulting from the "mutation" process
     */
    public Chromosome mutate(Chromosome ch) throws PreconditionException {
        // # Modified by Andy Lo 15 Apr 2005: added precondition check
        Assertion.pre( ch.getLength() > 0,
                "The chromosomes have length = " + ch.getLength(),
                "ERROR!!! The chromosomes MUST have positive length" );
        //Debugger.debug("Mutating:\n" + ch);
        /**
         * factor determining the number of nodes that will be mutated
         *
         * maximum allowable change,
         * fraction = (50 - gens/50)%, if gens < 200
         *          = 10%, otherwise
         * double fraction = (getNumGen() < 200)? (0.5-getNumGen()/5000) : 0.10;
         * ### if the number of generations evolved has exceeded 200,
         * ### we will reduce the amount of Chromosomes that will be changed
         *
         * Other option includes:
         *      fraction = 0.5 * Math.pow(0.2, getNumGen()/gensToEvolve)
         *
         * Modified by Andy 11/04/05:
         *      A new method is used to get a curve with no discontinuity,
         *  and does not require the knowledge of the number of generations
         *  to evolve.
         *
         * # Modified by Andy 15 Apr 2005:
         *      Changed to offset getNumGen() due to the special cases with
         *      Math.pow(double,double). This is required because:
         *      [Quote Java API: java.lang.Math]
         *      If the first argument is positive zero and the second 
         *      argument is less than zero, or the first argument is positive 
         *      infinity and the second argument is greater than zero, then 
         *      the result is positive infinity.
         */  
        double fraction = 0.5 * Math.pow(getNumGen()+GEN_OFFSET,-0.17);
        
        //Debugger.printErr("numGen^-0.17 = "+ Math.pow(getNumGen(),-0.17));
        //Debugger.printErr("numGen = " + getNumGen());
        //Debugger.printErr("fraction = " + fraction);

        // # Modified by Andy 15 Apr 2005: Obtain Node List from chromosome
        ArrayList nodeList = ch.getNodeList();

        // calculate the number of nodes to change
        int numNodes = nodeList.size();
        // the number of cells in the chromosome that will be mutated
        int cellsToChange = rand.nextInt( ((int)(numNodes*fraction)) +1)+ 1;
        //Debugger.printErr("Cells to change = " + cellsToChange);
        
        // temporary storage for indices
        int index, randNodeId;

        for(int i = 0; i < cellsToChange; i++) {
            // randomly select a cell to modify
            // # Modified by Zhiyang Ong 31 Mar 2005
            // variable "len" has not been declared
            // index = rand.nextInt(len);
            index = rand.nextInt(numNodes);

            //Debugger.printErr(" Changing cell #" + index);
            ArrayList cell = ch.getData(index);
            
            // decide whether to remove a node, add a node, or do nothing
            double temp = rand.nextDouble();
            //Debugger.printErr(" Number generated = " + temp);

            if(temp < ACCUM_PROB_REMOVE) {
                /**
                 * # Modified by Andy Lo 17 Apr 2005:
                 *      Avoids the IllegalArgumentException, when the cell is 
                 *      empty. Also, since we cannot remove from this node, 
                 *      redo this iteration.
                 */
                if (cell.size() == 0) { 
                    i--;
                } else {
                    /**
                     * remove a random node from the list, so that an edge
                     * will not be created to connect to this node.
                     */                
                    randNodeId = rand.nextInt(cell.size());
                    cell.remove(randNodeId);
                }
            } else if(temp < ACCUM_PROB_ADD) {
                /**
                 * # Modified by Andy Lo 17 Apr 2005:
                 *      Avoids the Infinite-Loop problem, when the cell 
                 *      contains all the nodes in the graph. i.e. It connects
                 *      to every other node. In this case, since we cannot 
                 *      add another node, redo this iteration.
                 */
                if (cell.size() == (nodeList.size() - 1)) {
                    i--;
                } else {
                    Node n = null;;
                    /**
                     * Add a random node that the cell hasn't already contained.
                     * However, only add the node, if the node selected 
                     * destination is not the source node itself AND if the cell 
                     * doesn't contains the node, 
                     * 
                     * # Modified by Andy Lo 17 Apr 2005:
                     *      reformated the while loop to a do-while loop, so the
                     *      code is more understandable.
                     */
                    do {
                        // select a random node
                        randNodeId = rand.nextInt(numNodes);
                        // Add a randomly selected node
                        n = (Node) nodeList.get(randNodeId);
                    } while (index == randNodeId || cell.contains(n));
                
                    cell.add(n);    
                }   
            } // else do nothing
        }
        //Debugger.debug(" Result:\n" + ch);
        return ch;
    }

    /**
     * Sets up a population of Network Chromosomes for evolution. Due to the
     * structure of a chromosome, all the nodes must be generate before
     * creating the chromosomes. The servers are conventionally created
     * before the clients are created. Each created node is added to a local
     * "nodeList" for easy access.<p>
     *
     * <i>Updated by Andy 03/04/05</i>: At this stage, the ID information for 
     * each node is redundant, as ID is simply the index of the node in 
     * nodeList. However this may help in future, when specific node 
     * information are to be acquired. <p>
     *
     * Once all nodes (Servers & Clients) are created, we setup a population
     * of <code>getPopSize()</code> chromosomes. Each Chromosome in the
     * population corresponds to a network. Each cell in the chromosome
     * contains a record of which other nodes the node is connected to.
     *
     * @param   numServers The number of Servers in the Network
     * @param   numClients The number of Clients in the Network
     * @return  Population of Network <code>Chromosome</code>s as a
     *          <code>SetOfChromosomes</code>
     * @throws  AssertionException If the nodeList is not empty. That is, the
     *          population of networks has already been initialised.
     * @throws  PreconditionException If the number of Clients or Servers in
     *          the Network is less than 0
     */
    public SetOfChromosomes initPop(int numServers, int numClients)
    throws PreconditionException {
        Assertion.pre( (numServers >= 0) && (numClients >= 0),
                        "Number of Servers and Clients are non-negative",
                        "Number of Servers and Clients MUST BE non-negative");
    
        // temporary storage for list of nodes
        ArrayList nodeList = new ArrayList();
        /**
         * create the servers and add them to the "nodeList". The Id of the
         * server nodes starts from 0 upto (numServer-1), since ID is the
         * order which the nodes are created.
         * Modified by Andy 03/04/05:
         *      The label for server should be all uppercase to be consistent.
         */
        for(int i = 0; i < numServers; i++)
            // # Modified by Zhiyang Ong - 31 March 2005
            // nodeList.add(initNode("Server", i));
            nodeList.add(initNode("SERVER"));

        setNodeList(nodeList);
        /**
         * create the clients and add them to the "nodeList". The ID starts
         * from "numServers" to continue from the sequence.
         * Modified by Andy 03/04/05:
         *      The label for client should be all uppercase to be consistent.
         */
        for(int i = numServers; i < numServers+numClients; i++)
            // # Modified by Zhiyang Ong - 31 March 2005
            // nodeList.add(initNode("Client", i));
            nodeList.add(initNode("CLIENT"));

        // temporary container to hold new chromosomes
        ArrayList chromList = new ArrayList();
        for(int i = 0; i < numChromInPop; i++) {
            /**
             * # Modified by Andy Lo 14 Apr 2005: 
             *      initChromo takes in a to intialise each chromosome.
             */
            chromList.add(initChromo(nodeList, numServers, numClients));
        }


// ### Modified by Zhiyang Ong - 31 July 2005
Chromosome curChrom;
int chromoSize=numServers+numClients;
// Allow servers to connect to 80% of the clients
chromoSize = (int)(chromoSize * 0.8);
for(int k=0;k<numChromInPop;k++) {
	curChrom = (Chromosome)chromList.get(k);
	for(int m=0;m<numServers;m++) {
		for(int n=0;n<chromoSize;n++) {
			int index = rand.nextInt(numClients) + numServers;
			Object nodeA = nodeList.get(index);
			ArrayList destinationNodes = curChrom.getData(m);
			if(!destinationNodes.contains(nodeA)) destinationNodes.add(nodeA);
		}
	}
}


        // construct a "SetOfChromosomes" from the list
        return new SetOfChromosomes(chromList);
    }

    /**
     * Initialises a graph node (Client or Server) for the Network. In this
     * version, the properties for this node is generated pseudo randomly.<p>
     * However in later versions, values used may be predefined, and stored
     * in an accessible <i>pool</i>. <p>
     *
     * <i>Updated by Andy Lo 11 Apr 2005</i>: At this stage, the x and y
     * coordinates will be "randomly" selected based on the workspace size of
     * HEIGHT_WORKSPACE x WIDTH_WORKSPACE, and repair generations will be
     * uniformly distributed over the range [0..MAX_REPAIR_GEN]. The 
     * information regarding the capacity of a node is redundant as we are
     * working in relative terms of usage. The nodes will begin running at 0% 
     * efficiency. The probability of failure and the threshold will be set at
     * 0.30 and 0.70 respectively.<p>
     *
     * <i>Upated by Andy Lo 13 Apr 2005</i>: The method should check if the
     * label supplied is null.
     *
     * @param   label Indicates the type of the <code>Node</code>. e.g.
     *          Server or Client.
     * @return  The Node with pseudo-randomly determined properties
     * @throws  PreconditionException If the label is a null object
     * @throws  PreconditionException If the type is neither "SERVER" nor
     *          "CLIENT". (In future, more combination may be explored)
     */
    // # Modified by Zhiyang Ong - 31 March 2005
    // param int nodeId is not used; hence, it is removed
    // public Node initNode(String label, int nodeId)
    public Node initNode(String label) throws PreconditionException {
        // # Modified by Andy Lo - 13 Apr 2005
        Assertion.pre( label != null, "Label to be set is not null",
                                      "Label to be set MUST NOT be null");
        // # Modified by Zhiyang Ong - 31 Mar 2005
        // Assertion.pre( label.equals("Server") || label.equals("Client"),
        // Allow standard comparisons of labels with standard label prefixes.
        Assertion.pre( (label.toUpperCase()).startsWith("SERVER")
            || (label.toUpperCase()).startsWith("CLIENT"),
            "Type label is " + label,
            "Type label must start with \"SERVER\" or \"CLIENT\"");

        /**
         * # Modified by Zhiyang Ong 31/03/2005:
         *      Originally the assertion checks that variables numServers and
         *      numClients needs to be positive. However these variables have
         *      not been  declared as variables of this Class or method.
         */
        /* Assertion.pre( (numServers >= 0) && (numClients >= 0),
                                "nodeIDs are non-negative",
                                "nodeIDs MUST BE non-negative");
        */

        /**
         * variables "numServers" and "numClients" have not been
         * declared as variables of this Class or method
         */
        /**
         * # Modified by Zhiyang Ong 31/03/2005: nodeId is no longer required
         *  Assertion.pre(nodeId >= 0,"nodeIDs are non-negative",
         *       "nodeIDs MUST BE non-negative");
         */
        
        /**
         * Modified by Andy 03/04/05:
         *      Node n = new Node(label, nodeId, prFailure, traffic, true);
         *      The above is no longer a valid constructor, as there is no 
         *      Node class.
         *      At this stage, the x and y coordinates will be "randomly"
         *      selected based on the workspace size of 1000x750, and repair
         *      generations will normally distributed over the range [0..20].
         *      The capacity will be determined by the type of Node. The
         *      server will be assumed to have gigabit capacity, where as
         *      clients only have 16Mbps. The nodes will run at 100% 
         *      efficiency. The probability of failure and the threshold will
                      *      will be set at 0.30 and 0.70 respectively.
         */
        int x   = rand.nextInt(HEIGHT_WORKSPACE);
        int y   = rand.nextInt(WIDTH_WORKSPACE);
        int gen = rand.nextInt(MAX_REPAIR_GEN);
        
        /**
         * Modified by Andy Lo 3 Apr 2005:
         *      The constructor does not need traffic as a parameter. We only
         *      need the capacity of a node. If the node is a server, its
         *      capacity will in orders of 1000Mbps, whereas if the node is a
         *      client, it may send out at most 16Mbps of traffic.
         * Modified by Andy Lo 13 Apr 2005:
         *      This change reflects upon the fact that label is to be 
         *      prefixed with SERVER and CLIENT. This can be lower-case,
         *      upper-case or a mixture.
         */
        double capacity = ( (label.toUpperCase()).startsWith("SERVER") )?
                                        SERVER_CAPACITY : CLIENT_CAPACITY;
        // double traffic = rand.nextDouble() * factor;

        /**
         * generate a pseudo-random probability of failure within the range
         * from 0.00 to 0.50. The probability is capped at 0.5, because using
         * an object, with fail rate of 50% or higher, is consider high risk.
         * Modified by Andy 03/04/05:
         *      Will use 0.3 for the time being for simplicity of testruns.
         */
        double threshold = 0.20;
        double prFailure = rand.nextDouble() * threshold;
        // # Modified by Zhiyang Ong - 8 April 2005
        // Since this node is not connected to any other node,
        // its operating efficiency is 0%
        double[] params = new double[] {capacity, 0.0, prFailure, threshold};
        
        Node n = new NodeImp(label, x, y, gen, params, true);

        return n;
    }

    /**
     * Generates a <code>Chromosome</code> to represent a Network. The number
     * of cells or the length of the <code>Chromosome</code> is the number of
     * nodes in the Network. Each cell is another list of Nodes, to which a
     * corresponding (by element position and nodeID) node is connected.<p>
     *
     * Initially, a node will connect to <b>at least one</b> node, but <b>no
     * more than 10%</b> of the nodes in the network. For a <b>server</b>
     * node, that is nodes with IDs 0 to (<code>numServer</code>-1), it is
     * equally likely to connect to another Server or Client node. However, 
     * for <b>client</b> nodes (with IDs from <code>numServer</code> to 
     * <code>totalSize</code>) it is more desirable to be connected to a
     * server, rather than a client, but sometime, there isn't be a choice.<p>
     *
     * <b>Modified</b> by Andy Lo 14 Apr 2005
     *
     * @param   nodeList The list that contains information regarding server
     *          and client nodes
     * @param   numServers The number of Servers in the Network
     * @param   numClients The number of Clients in the Network
     * @return  The Chromosome that has a determined number of Servers and
     *          Clients with various properties.
     * @throws  PreconditionException If the nodeList is empty. i.e. The graph
     *          will contains no nodes.
     * @throws  PreconditionException If the number of Clients or Servers in 
     *          the Network is less than 0
     * @throws  PreconditionException If the sum of number of Clients and 
     *          Servers in the Network is not equal the number of nodes in 
     *          the node list
     */
    public Chromosome initChromo(ArrayList nodeList, int numServers,
    int numClients) throws PreconditionException{
        // number of cells to create in the chromosome
        int totalSize = nodeList.size();

        /**
         * # Modified by Andy Lo 13 Apr 2005: 
         *      Assertion check for number of nodes in the network
         */
        Assertion.pre( totalSize > 0,
                    "The network contains " + totalSize + " nodes",
                    "The network must contain AT LEAST 1 nodes");
        Assertion.pre( (numServers >= 0) && (numClients >= 0),
                    "Number of Servers and Clients are non-negative",
                    "Number of Servers and Clients MUST BE non-negative");
        Assertion.pre( totalSize == (numServers + numClients),
                "The number of nodes in the nodeList (network) equals the " +
                    "sum of number\n    of servers and clients",
                "The number of nodes in the nodeList (network) and the  " +
                    "sum of number\n    of servers and clients MUST EQUAL");

        // the container for the cells
        ArrayList cells = new ArrayList();
        
        for(int i=0; i < totalSize; i++) {
            /**
             * If we are setting up server nodes, there's an equal chance to
             * connect to any types of nodes. Yet, if we are setting up for
             * client nodes, it will have more chance to connect to a server.
             * So a server will have equal chance of connecting to another
             * server or a client, whereas a client will have more chance of
             * connecting to a server, than to another client.
             */
            double threshold = (i < numServers)? 0.50 : 0.75;
            /**
             * for each cell, add at least 1 but no more than 50% of total
             * nodes to the cell. That is, connect upto 50% of the nodes.
             */
            int nodesToAdd = rand.nextInt(totalSize/5) + 1;
            // container for the cell / adjacency list
            ArrayList cell = new ArrayList();
            for(int j = 0; j < nodesToAdd; j++) {
                // the index or ID of the node to add to the "cell"
                int index;
                // Check if we want to add a server or a client.
                if(rand.nextDouble() < threshold ) {
                    index = rand.nextInt(numServers);
                } else index = rand.nextInt(numClients) + numServers;


// ### Modified by Zhiyang Ong - 31 July 2005
// if we are considering adding destination nodes to servers
/*
if(j < numServers) {
	for(int k=0;k<10;k++) {
		index = rand.nextInt(numClients) + numServers;
		Object nodeA = nodeList.get(index);
		if( (i != index) && !cell.contains(nodeA)) cell.add(nodeA);
	}
}*/
                
                /**
                 * Only need to add a node, which is not already in the list
                 * and if the node to add is not itself.
                 */
                Object node = nodeList.get(index);
                if( (i != index) && !cell.contains(node)) cell.add(node);
            }
            cells.add(cell);    // add the cell to the list of cells
        }
        // construct a new chromosome and return it
        // Modified by Andy 03/04/05: Changed to use new constructor.
        return new Chromosome(cells, nodeList, numServers, numClients);
    }

    /**
     * Sets the probability of mutation for Chromosomes. This assigns the
     * value of Pr(Mutation)
     * @param   prMutate The probability of mutation for a selected
     *          <code>Chromosome</code>
     * @throws  PreconditionException If <code>prMutate</code> is NOT between
     *          0 and 1
     */
    public void setPrMutation(double prMutate) throws PreconditionException {
        Assertion.pre( (prMutate >= 0.0 && prMutate <= 1.0),
                        "New probability passed is " + prMutate,
                        "Probability passed MUST be between 0 and 1");
        this.prMutation = prMutate;
    }

    /**
     * Returns the probability of mutation for Chromosomes. This gives the
     * value of Pr(Mutation).
     * @return  The probability of mutation for this Chromosomes
     * @throws  PostconditionException If returned <code>prMutate</code> is
     *          NOT between 0 and 1
     */
    public double getPrMutation() throws PostconditionException{
        Assertion.post( (prMutation >= 0.0 && prMutation <= 1.0),
                        "prMutation is " + prMutation,
                        "prMutation MUST be between 0 and 1");
        return this.prMutation;
    }


    /**
     * Sets the probability of crossover for Chromosomes. This assigns the
     * value of Pr(Crossover)
     * @param   prXover The probability of crossover for a selected
     *          <code>Chromosome</code>
     * @throws  PreconditionException If <code>prCrossover</code> is NOT
     *          between 0 and 1
     */
    public void setPrCrossover(double prXover)
    throws PreconditionException {
        Assertion.pre( (prXover >= 0.0 && prXover <= 1.0),
                        "New probability passed is " + prXover,
                        "Probability passed MUST be between 0 and 1");
        this.prCrossover = prXover;
    }

    /**
     * Returns the probability of crossover for Chromosomes. This gives the
     * value of Pr(Crossover).
     * @return  The probability of crossover for this Chromosomes
     * @throws  PostconditionException If returned <code>prCrossover</code>
     *          is NOT between 0 and 1
     */
    public double getPrCrossover() throws PostconditionException{
        Assertion.post( (prCrossover >= 0.0 && prCrossover <= 1.0),
                        "prCrossover is " + prCrossover,
                        "prCrossover MUST be between 0 and 1");
        return this.prCrossover;
    }

    /**
     * Returns current generation number.
     * @return  The number of generations evolved
     * @throws  PostconditionException If the number of generations evolved is
     *          less than 0
     */
    public int getNumGen() throws PostconditionException{
        Assertion.post( curGen >= 0, "curGen is " + curGen,
                                     "curGen MUST be non-negative");
        return this.curGen;
    }

    /**
     * Increments the number of generations evolved by 1.
     */
    public void increGen() {
        curGen++;
    }

    /**
     * Returns the current population in this evolution process.
     * @return  The current population in this evolution process.
     */
    public SetOfChromosomes getCurPop(){
        return this.curPop;
    }
    
    /**
     * Sets the current population of Networks in this evolution process.<p>
     * <i>Modified by Andy Lo 14 Apr 2005</i>
     * @param   set The current population in this evolution process.
     * @throws  PreconditionException If the node list for the chromosoems
     *          are not the same.
     */
    public void setCurPop(SetOfChromosomes set) {
        // # Modified by Andy Lo 14 Apr 2005: precondition check.
        int size = set.getPopSize();
        if (size > 0) {
            ArrayList nodes = (set.getChromo(0)).getNodeList();
            for (int i = 1; i < size; i++) {
                if (!nodes.equals( (set.getChromo(i)).getNodeList() )) {
                    throw new PreconditionException("ERROR!!! All " +
                        "chromosome SHOULD refer to the SAME list of nodes");
                }
            }
            Debugger.debug("All chromosomes share the same node list");
            setNodeList(nodes);
            EdgeCostMatrix.populateMatrix(nodes.size(), MAX_EDGE_COST);
        }
        this.curPop = set;
    }

    /**
     * Sets the current reference node list for this evolution process.<p>
     * # Implemented by Andy Lo 14 Apr 2005
     */
    private void setNodeList(ArrayList list) {
        currNodeList = list;
    }

    /**
     * Returns the current reference node list for this evolution process.<p>
     * <i>Implemented by Andy Lo 14 Apr 2005</i>
     */
    public ArrayList getNodeList() {
        return currNodeList;
    }

    /**
     * Returns the number of chromosomes in this population.
     * @return  The size of this population.
     * @throws  PostconditionException If the size of this population is less
     *          than 0.
     */
    public int getPopSize() throws PostconditionException {
        /**
         * # Modified by Andy Lo 13 Apr 2005: to return the actual number of
         * chromosomes in the population, instead of the number of 
         * chromosomes to be included when a population is initialised
         */
        int size = curPop.getPopSize();
        Assertion.post( size >= 0, "popSize is " + size,
                                   "popSize MUST be non-negative");
        return size;
    }

    /**
     * Sets the size of this population generated when <code>initPop</code> is
     * called.<p>
     * <b>Modified</b> by Andy Lo 13 Apr 2005.
     * @param   size The number of chromosome to be intialised when initPop
     *          is called
     * @throws  PreconditionException If the size for a new population is not
     *          Positive.
     */
    public void setPopSize(int size) {
        Assertion.pre( size > 0, "New popSize is " + size,
                                 "New popSize MUST be POSITIVE");
        this.numChromInPop = size;    
    }
	
	/**
	 * Method to model symbiosis for a pair of nodes
	 * Only relationships with non-adversarial effects (mutualism and
	 * commensalism) are modeled.
	 * ch1 and ch2 are the chromosomes involved in this symbiosis
	 */
	public Chromosome[] symbiosis(Chromosome ch1, Chromosome ch2) {
		// # Modified by Andy Lo 15 Apr 2005: added precondition check
        Assertion.pre( (ch1.getNodeList()).equals(ch2.getNodeList()),
			"Both chromosomes share the same node list",
			"ERROR!!! Both chromosome SHOULD share the SAME node list");
        Assertion.pre( ch1.getLength() > 0,
			"The chromosome 1 have length = " + ch1.getLength(),
			"ERROR!!! The chromosome 1 MUST not be of 0 length" );
		Assertion.pre( ch2.getLength() > 0,
			"The chromosome 2 have length = " + ch2.getLength(),
			"ERROR!!! The chromosome 2 MUST not be of 0 length" );

		// obtain the contents within each chromosome
        ArrayList cells1 = ch1.getDataArray();
        ArrayList cells2 = ch2.getDataArray();

        //Debugger.debug("Swapping from " + XPt1 + " to " + (XPt2-1));
        /**
		 * Swap the contents between the 2 chromosomes at the top 5 nodes
		 * with the highest clustering coefficiency
		 */
		if(!mutualism) {
			// model mutualism
			// obtain the adjacency matrix
        	double[][] adjMatrix1 = ch1.getAdjacencyMatrix();
			DegreeSeparation ds= new DegreeSeparation();
        	// convert to a link matrix with only 0, 1, and infinity (+oo)
        	adjMatrix1 = ds.adj2linkMatrix(adjMatrix1);
			DijkstraMatrix dm1 = new DijkstraMatrix();
        	// perform dijsktra on the matrix
			int higherIndex=-1;
        	double[][] resMatrix1 = dm1.dijkstra(adjMatrix1);
			// determine top 5 nodes with the highest clustering coefficiency
        	for (int i=0; i<ch1.getLength(); i++) {
				connectivity = clusterCoeff(adjMatrix1, resMatrix1,i);
				for(int j=0;j<cluscoeff.length;j++) {
					if(connectivity > cluscoeff[j]) {
						higherIndex=j;
					}
				}
				if(higherIndex >= 0) {
					top5connect[higherIndex]=i;
					cluscoeff[higherIndex]=connectivity;
				}
        	}
			// for each of the top 5 nodes with the highest clustering coefficiency
			for (int k=0; k<top5connect.length; k++) {
				// Union its outgoing edges with that of ch2
            	ArrayList temp1 = (ArrayList)cells1.get(k);
				ArrayList temp2 = (ArrayList)cells2.get(k);
				for(int m=0; m<temp1.size(); m++) {
					if(temp2.contains(temp1.get(m))) {
						// do nothing
					}else{
						temp2.add(temp1.get(m));
					}
				}
        	}
		}else{
			// model commensalism
			// obtain the adjacency matrix
        	double[][] adjMatrix2 = ch2.getAdjacencyMatrix();
			DegreeSeparation ds= new DegreeSeparation();
        	// convert to a link matrix with only 0, 1, and infinity (+oo)
        	adjMatrix2 = ds.adj2linkMatrix(adjMatrix2);
			DijkstraMatrix dm2 = new DijkstraMatrix();
        	// perform dijsktra on the matrix
			int higherIndex=-1;
        	double[][] resMatrix2 = dm2.dijkstra(adjMatrix2);
			// determine top 5 nodes with the highest clustering coefficiency
        	for (int i=0; i<ch2.getLength(); i++) {
				connectivity = clusterCoeff(adjMatrix2, resMatrix2,i);
				for(int j=0;j<cluscoeff.length;j++) {
					if(connectivity > cluscoeff[j]) {
						higherIndex=j;
					}
				}
				if(higherIndex >= 0) {
					top5connect[higherIndex]=i;
					cluscoeff[higherIndex]=connectivity;
				}
        	}
			// for each of the top 5 nodes with the highest clustering coefficiency
			for (int k=0; k<top5connect.length; k++) {
				// Union its outgoing edges with that of ch2
            	ArrayList temp1 = (ArrayList)cells1.get(k);
				ArrayList temp2 = (ArrayList)cells2.get(k);
				for(int m=0; m<temp2.size(); m++) {
					if(temp1.contains(temp2.get(m))) {
						// do nothing
					}else{
						temp1.add(temp2.get(m));
					}
				}
        	}
		}
        //Debugger.debug(" Results:\n " + ch1 + "\n" + ch2 + "\n");

        Chromosome[] pair = {ch1, ch2};
        Assertion.post( pair.length == 2,
                        "The result array is of length 2",
                        "The result array is NOT of length 2");
						
		if(mutualism) {
			mutualism=false;
		}else{
			mutualism=true;
		}
        return pair;
	}
	
	/**
	 * determine the clustering coefficiency of the node
	 */
	public double clusterCoeff(double[][] a, double b[][], int i) {
		// the number of connection for a node (direct links)
        int connections;
        // the number of neighbors indirectly or directly connected
        int neighbors;
		// the accumulated sum of cluster coefficient
        double cluster;
        
        // find the number of direct connection from the original matrix
		connections = 0;
		for (int j = 0; j < a[i].length; j++) {
			if (i == j) continue;
			if (a[i][j] != Double.POSITIVE_INFINITY) connections++;
		}
		// cluster coefficient for nodes with degree 1 is undefined
		if (connections < 2) {
			cluster = 0;
		}
		/**
		 * find the number of connected nodes from the matrix result
		 * from dijsktra
		 */
		neighbors = 0;
		for (int j = 0; j < b[i].length; j++) {
			if (i == j) continue;
			if (b[i][j] != Double.POSITIVE_INFINITY) neighbors++;
		}

		cluster = (double) connections /
			((double) neighbors * ((double)neighbors - 1.0) );
		return cluster;
	}
	
	
	/**
	 *
	 */
	public Object clone() {
		NetworkGAImp cloneNetWk = new NetworkGAImp(this.getPrCrossover(),
			this.getPrMutation(), this.numOfServers, this.numOfClients,
			this.getPopSize());
		cloneNetWk.setCurPop((SetOfChromosomes)this.getCurPop().clone());
		return cloneNetWk;
	}
}
