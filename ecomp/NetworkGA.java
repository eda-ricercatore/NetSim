/**
 * ecomp package evolves the set (population) of networks (Chromosomes) using
 * Genetic Algorithms to find the optimal balance between pleiotropy and
 * redundancy. This mimics the process of biological evolution using natural
 * selection of the best networks (fittest Chromosomes) and inheritance of
 * network properties (traits).
 */
package ecomp;

//importing packages
import population.*;
import population.graph.*;
import utility.*;
import java.util.ArrayList;

/**
 * This specifies the behaviour of the evolutionary computation in the 
 * <b>NetSim</b> software. It includes methods for evolving the population 
 * of networks using Genetic Algorithms. <p>
 * The Genetic Algorithms use mating (crossover) and mutation to optimise
 * the network
 *
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.7
 * @since           0.3
 * @acknowledgement Matthew Berryman, Wei-li Khoo and Hiep Nguyen
 */

// Interface definition...

// ========================================================================

public interface NetworkGA {
    // Method specifications...
    
    /**
     * Evolves a population of Chromosomes. In this case, population is
     * abstracted by a <code>set</code> and each <code>Chromosome</code>
     * represents a Network.<p>
     *
     * The first part of evolution includes determining which pair of 
     * Networks in the Set will be mutated and/or mated with each other. For
     * each pair of selected Chromosomes for crossover or individual
     * mutation, the probability of selection for each of these pairs of
     * chromosomes is generated pseudo-randomly.<p>
     *
     * To select a pair of Networks (Chromosomes) for selection, compare
     * the probabilities of selection since the array of predetermined
     * values of probabilities of selection is cumulative. i.e. Select when:
     * <ul>(pseudo random generated value) < (predetermined value)</ul>
     *
     * @param   func The fitness function determining the amount of
     *          pleiotropy and redundancy of the network
     */
    // # Modified by Zhiyang Ong - 30 Mar 2005
    //	public SetOfChromosomes evolve(Function func, SetOfChromosomes set);
    public void evolve(Function[] func);
    
    /**
     * Crosses over a pair of Chromosomes. Two Networks are mated to
     * produce new networks inhereting their properties, such that some 
     * Network properties (traits) of <code>ch1</code> and <code>ch2</code>
     * will be swapped.
     *
     * @param   ch1 The first <code>Chromosome</code> for mating (crossover)
     * @param   ch2 The other <code>Chromosome</code> for mating. i.e.
     *          <code>ch1</code> is <code>ch2</code>'s partner
     * @return  A pair of Chromosomes resulting from the "mating" process,
     *          marriage, in an array of 2 Chromosomes (Networks)
     * @throws  PostconditionException If the array of Chromosomes returned 
     *          is not of length 2.
     */
    public Chromosome[] crossover(Chromosome ch1, Chromosome ch2)
    throws PostconditionException;
    
    /**
     * Mutates a Chromosome. The properties (traits) of this Network will be
     * changed pseudo-randomly.
     * @param   ch The <code>Chromosome</code> to be mutated
     * @return  A Chromosome resulting from the "mutation" process
     */
    public Chromosome mutate(Chromosome ch);
    /**
     * ### Note that the mutation and crossover methods can be implemented
     * ### in different fashions with different arguments
     * ### The above mutation and crossover functions are the default
     * ### functions
     */
    
    /**
     * Initialises a population of Network Chromosomes. That is, for each
     * each Network (Chromosome) in the set (population), it creates
     * a network with the selected number of Clients and Servers.
     * @param   numServers The number of Servers in the Network
     * @param   numClients The number of Clients in the Network
     * @return  Population of Network <code>Chromosome</code>s as a
     *          <code>SetOfChromosomes</code>
     * @throws  PreconditionException If the number of Clients or Servers in 
     *          the Network is less than 0
     */
    public SetOfChromosomes initPop(int numServers, int numClients)
    throws PreconditionException;
    /**
     * The number of Chromosomes is given by getPopSize()
     * Instantiate a SetOfChromosomes of the size getPopSize()
     * for each Chromosome, initialise the Chromosome...
     * call the method initChromos()
     */
    
    /**
     * Initialises a Node of the Graph. This determines the properties for
     * this Node in this Network <code>Chromosome</code>
     * @param   label Indicates the type of the <code>Node</code>. e.g.
     *          Server or Client.
     * @return  The Node with pseudo-randomly determined properties
     * @throws  PreconditionException If the type is neither "Server" nor
     *          "Client". (In v2.0 or later, type only needs to be non-null)
     * @throws  PreconditionException If <code>nodeId</code> is negative
     */
    // # Modified by Zhiyang Ong - 31 March 2005
    //	public Node initNode(String label, int nodeId)
    public Node initNode(String label) throws PreconditionException;
       
    /**
     * Initialises a Chromosome for a population. This determines the
     * properties for each Network <code>Node</code> (Client or Server) in 
     * this network
     * @param   nodeList The list that contains information regarding server
     *          and client nodes
     * @param   numServers The number of Servers in the Network
     * @param   numClients The number of Clients in the Network
     * @return  The Chromosome that has a determined number of Servers and
     *          Clients with various properties.
     * @throws  PreconditionException If the number of Clients or Servers in 
     *          the Network is less than 0
     * @throws  PreconditionException If the sum of number of Clients and 
     *          Servers in the Network is not equal the number of nodes in 
     *          the node list
     */
    public Chromosome initChromo(ArrayList nodeList, int numServers,
    int numClients) throws PreconditionException;
    /**
     * Create all the Servers first before you create any Client
     */

    /**
     * Assigns the probability of mutation for Chromosomes. This sets the
     * value of Pr(Mutation)
     * @param   prMutate The probability of mutation for a selected
     *          <code>Chromosome</codE>
     * @throws  PreconditionException If <code>prMutate</code> is NOT between
     *          0 and 1
     */
    public void setPrMutation(double prMutate) throws PreconditionException;
    
    /**
     * Returns the probability of mutation for Chromosomes. This gives the
     * value of Pr(Mutation).
     * @return  The probability of mutation for this Chromosomes
     * @throws  PostconditionException If returned <code>prMutate</code> is 
     *          NOT between 0 and 1
     */
    public double getPrMutation() throws PostconditionException;
    

    /**
     * Assigns the probability of crossover for Chromosomes. This sets the
     * value of Pr(Crossover)
     * @param   prCrossover The probability of crossover for a selected
     *          <code>Chromosome</codE>
     * @throws  PreconditionException If <code>prCrossover</code> is NOT 
     *          between 0 and 1
     */
    public void setPrCrossover(double prCrossover)
    throws PreconditionException;
    
    /**
     * Returns the probability of crossover for Chromosomes. This gives the
     * value of Pr(Crossover).
     * @return  The probability of crossover for this Chromosomes
     * @throws  PostconditionException If returned <code>prCrossover</code> 
     *          is NOT between 0 and 1
     */
    public double getPrCrossover() throws PostconditionException;
    
    /**
     * Returns the number of generations evolved
     * @return  The number of generations evolved
     * @throws  PostconditionException If the number of generations evolved is
     *          less than 0
     */
    public int getNumGen() throws PostconditionException;
    
    /**
     * Increments the counter for number of generations evolved
     */
    public void increGen();
    
    /**
     * Returns the current set of Networks (population) in this evolution
     * process
     * @return  The current population in this evolution process
     */
    public SetOfChromosomes getCurPop();
    
    /**
     * Sets the current set of Networks (population) in this evolution
     * process
     * @param   set The current population in this evolution process
     */
    public void setCurPop(SetOfChromosomes set);
    
    /**
     * Returns the size of this set of Networks (population)
     * @return  The size of this population
     * @throws  PostconditionException If the size of this population is less
     *          than 0
     */
    public int getPopSize() throws PostconditionException;
    
    /**
     * Sets the size of this set of Networks (population)
     * @param   size The size of this population
     * @throws  PreconditionException If the size of this set of networks is
     *          less than 0
     */
    public void setPopSize(int size);
    
    /**
     * To be implemented in later versions...
     * Method to change the size of the population
     * public SetOfChromosomes changePop(SetOfChromosomes set)
     */    
}
