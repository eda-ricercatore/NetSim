/*
 * Package containing the necessary tools to provide a
 * Graphical User Interface.
 * #It allows users to modify restricted input parameters for the network
 *  to be optimised.
 * #It graphically indicates the evolution of the network as the "NetSim"
 *  software optimises the network, seeking the optimal trade-off between
 *  pleiotropy and redundancy
 * #It allows users to graphically view the optimised network at the end
 *  of the simulation
 */

package gui;

import population.graph.*;
import population.*;
import ecomp.*;
import utility.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class to create the Thread to run the simulation for "NetSim"
 * @author Zhiyang Ong and Andy Lo
 * @version 0.3.1-zhiyang
 * @acknowledgement C. Xavier, "Programming with Java 2," Scitech Publications
 * (India) Pvt Ltd, Chennai, 2000
 * @acknowledgement Anton van den Hengel, "Artificial Intelligence Lecture
 *  Notes: Genetic Algorithms," The University of Adelaide, Adelaide, 2004
 */

// Class definition...

// ========================================================================

public class SimulationThread extends Thread {
	// Declaration of instance variables
	
	// Indices of the cost functions selected
	boolean[] costFunctions;
	
	/**
	 * User input for the number of clients and servers in the network,
	 * number of generations to be evolved, and the size of the population
	 */
	int[] userInput;
	
	// Index for the number of clients in the telecommunications network
	int numClients=0;
	
	// Index for the number of servers in the telecommunications network
	int numServers=1;
	
	// Index for the number of generations which the genetic algorithm will be run
	int numGen=2;
	
	/**
	 * Index for the number of Chromosomes in the population that the
	 * genetic algorithm is optimising
	 */
	int numChromo = 3;
	
	// Number of pairs of chromosomes in the population
	int numPairChromo;
	
	
	// Probability of mutation for selected Chromosomes
	double prMutation=0.05;
	// Probability of crossover for selected Chromosomes
	double prCrossover=0.3;
	
	
	/**
	 * Indicator of the boolean number of offsprings produced for
	 * steady state evolutionary algorithm
	 */
	boolean replaceTwo=true;
	
	/**
	 * Graph data structure to model the topology of the network
	 * Contains information about the nodes and edges
	 */
	GraphImp graph = new GraphImp();
	
	/**
	 * Data structure to contain information about the nodes, clients and
	 * servers, in the network over a telecommunication connection
	 */
	NodeImp fromNode;
	NodeImp toNode;
	
	/**
	 * Data structure to contain information about the data transfer links
	 * in the network
	 */
	EdgeImp edge;
	
	// Label for Server Nodes
	public static final String SERVER = "SERVER";
	// Label for Client Nodes
	public static final String CLIENT = "CLIENT";
	// Label for Client-Server data links
	public static final String CSLINK = "CLIENT-SERVER";
	// Label for Server-Server data links
	public static final String SSLINK = "SERVER-SERVER";
	
	// Description of PLAY/PAUSE and STOP/DO_NOTHING buttons
	String playButton;
	String stopButton;
	
	// State description of the PLAY/PAUSE button
	public static final String PAUSE = "Pause";
	// State description of the STOP/DO_NOTHING button
	public static final String STOP = "Stop";
	
	/**
	 * Frame for the GUI of NetSim; used to display the evolution
	 * of network topology
	 */
	GuiManager gui;
	
	// Boolean flag to indicate if simulation can start
	boolean start=false;
	
	// Number of selected Chromosomes
	int numSelectedCostFns=0;
	
	// Fittest Chromosome of the population
	Chromosome fittestChromo;
	
	// Separator for the simulation results file to delimit values
	private char separator = ' ';	// Use a character space
	// Name of the output file
	private String outputFile = "simulation";
	
	// Pop-up window to query user for input
	SelectionChoices input;
	
	// Container for cost functions...
/*
	ArrayList andy1;
	ArrayList andy = new ArrayList();
	MinimalPaths func1 = new MinimalPaths(false);
	//andy.set(0,func1);
	andy.add(0,func1);
	andy.add(new MinimalPaths(true));
	andy.add(new TotalEdgeCost());
	andy.add(new DegreeSeparation(true));
	andy.add(new DegreeSeparation(false));
	andy.add(new ServerLoad());
*/
	
    
   // -----------------------------------------------------------------
    
   // Default constructor
   public SimulationThread() {
   		/**
		 * Assume that when SimulationThread is instantiated, the simulation
		 * will commence and that the PLAY/PAUSE and STOP/DO_NOTHING buttons
		 * have been set to PAUSE and STOP respectively
		 * Hence, the simulator is in the PLAY mode
		 */
		//gui = graphics;

		// Get user input...
		// Create pop-up window to query user for input
//		input = new SelectionChoices(this);
		input.show();		// Make the pop-up window visible
		// DO NOT INSTANTIATE THE DEFAULT CONSTRUCTOR OF SimulationThread
/*
		Debugger.printErr("Simulation cannot commence without user's"
			+" interaction with the GUI");
		Debugger.printErr("Operation of NetSim has come to a halt!");
*/
	}
	 
	// Standard Constructor
	public SimulationThread(GuiManager graphics) {
		/**
		 * Assume that when SimulationThread is instantiated, the simulation
		 * will commence and that the PLAY/PAUSE and STOP/DO_NOTHING buttons
		 * have been set to PAUSE and STOP respectively
		 * Hence, the simulator is in the PLAY mode
		 */
		gui = graphics;

		// Get user input...
		// Create pop-up window to query user for input
//		input = new SelectionChoices(this);
		input.show();		// Make the pop-up window visible

	}
    
	// ------------------------------------------------------------------
    
	/**
	 * Method to run this SimulationThread to commence simulation
	 * @return nothing
	 */
	public void run() {
		Assertion.asrt((getPlayButton().equals(PAUSE) && getStopButton().equals(STOP)),
			"Simulation has been properly started up",
			"NetSim cannot commence simulation until user clicks PLAY");
			
		if(gui != null) {
			
			/**
			 * Pseudo code for genetic algorithm taken from AI lecture notes
			 * Evolve M generations of a population of N chromosomes
			 * 
			 * generate random initial population of N chromosomes
			 * while( (M - 1) > 0 ) {
			 * 	while( (N/2) > 0) {
			 *			select a pair of chromosomes for breeding;
			 *			generate 2 offsprings via chromosome mating;
			 *			add offsprings to new generation;
			 *			(N/2) - 1;
			 * 	}
			 *
			 *		replace current population with new population;
			 *		(M - 1) - 1;
			 * }
			 */
			
			// Start of the genetic algorithm
			// Generate random initial population of "numChromo" chromosomes
			
			/**
			 * Decrement gen since number of evolutions is one less than
			 * the number of desired generations
			 * That is, to obtain getNumGen() generations, (gen - 1) evolutions
			 * are required
			 */
			while(getStart()) {
Debugger.debug("Simulation is starting!!!!!!!!!!");
				int gen = getNumGen();
				gen--;

Debugger.enableTrace(true);
Debugger.debug("getNumGen() is: !!!!!!!!!!"+getNumGen());
Debugger.enableTrace(false);
				// Number of evolutions...
				int evolutions=0;
				
				// Provide the means to allow the network to evolve
				NetworkGAImp netwk = new 
					NetworkGAImp(prCrossover,prMutation,
					userInput[numServers],userInput[numClients],
					userInput[numChromo]);
				
				// Get the current set of Population
				SetOfChromosomes set = netwk.getCurPop();


Debugger.enableTrace(true);
Debugger.debug("@@@@@Is set == netwk.getCurPop()?"+(set == netwk.getCurPop()));
Debugger.debug("@@@@@Is set.equals(netwk.getCurPop())?"
	+(set.equals(netwk.getCurPop())));
Debugger.enableTrace(false);

				
				//ArrayList functions = new ArrayList();
				// Cost functions for the simulation
				Function[] functions = new Function[6];
				functions[0]=new MinimalPaths(false);
				functions[1]=new MinimalPaths(true);
				functions[2]=new TotalEdgeCost();
				functions[3]=new DegreeSeparation(true);
				functions[4]=new DegreeSeparation(false);
				functions[5]=new ServerLoad();
				
				// Tool used to measure the pleiotropy and redundancy values
				PRCalculator pr = new PRCalculator();
				
				// Selected cost functions by the user
//
				Function[] functArr = new Function[getNumSelectedCostFns()];
				
				
				// For each chromosome...
				for(int i=0; i<set.getPopSize(); i++) {
					// Create the array of fitness functions to hold
					// the fitness values
Debugger.enableTrace(true);
					set.getChromo(i).createFitnessArr(functArr.length);
Debugger.debug("functArr.length IS THIS: "+functArr.length);
Debugger.debug("getFitnessArr() == null???? "
	+(set.getChromo(i).getFitnessArr()==null));
Debugger.enableTrace(false);
				}


Debugger.enableTrace(true);
Debugger.debug("@@@@@Is set == netwk.getCurPop()?"+(set == netwk.getCurPop()));
Debugger.debug("@@@@@Is set.equals(netwk.getCurPop())?"
	+(set.equals(netwk.getCurPop())));
Debugger.enableTrace(false);
				
				
				int arrIndex=0;
				// Put the selected cost functions into an array...
				// For all functions...
				for(int indice=0; indice<costFunctions.length; indice++) {
					// Is this cost function selected?
					if(costFunctions[indice]) {
						/**
						 * Yes... Determine the fitness of each Chromosome in
						 * the population
						 * Assign fitness value to each Chromosome before
						 * evolving them
						 */
						functArr[arrIndex] = functions[indice];
Debugger.enableTrace(true);
Debugger.debug("");
Debugger.debug("");
//Debugger.debug("#####VALUE OF arrIndex IS: "+arrIndex);
//Debugger.debug("#####VALUE OF indice IS: "+indice);
Debugger.debug("#####IS CURRENT FUNCTION == NULL: "+indice
	+(functArr[arrIndex] == null));
Debugger.debug("Costs functions are: "+costFunctions[0]
			+" "+costFunctions[1]+" "+costFunctions[2]+" "+costFunctions[3]
			+" "+costFunctions[4]+" "+costFunctions[5]);
Debugger.debug("");
Debugger.debug("");
Debugger.enableTrace(false);
//Debugger.enableTrace(false);
						// Determine the fitness of each Chromosome in the population
						functArr[arrIndex].map(set,arrIndex);
						// Add next selected cost function... if any
						arrIndex++;
					}
				}
				
				// pass the selected cost functions to set of chromosomes
				set.setCostFunctions(functArr);
				// Assign Fitness values to them
				// Average fitnesses of the population for each generation
				double[][] avgFit = new double[getNumGen()][functArr.length];
				
				
				// Best fitnesses of the population for each generation
				double[][] bestFit = new double[getNumGen()][functArr.length];
				
				// Normalise fitness values before sorting
				set.normalize();
				
				// Sort the Chromosomes in the population according to their fitness
				set.sort();

Debugger.enableTrace(true);
Debugger.debug("@@@@@Is set == netwk.getCurPop()?"+(set == netwk.getCurPop()));
Debugger.debug("@@@@@Is set.equals(netwk.getCurPop())?"
	+(set.equals(netwk.getCurPop())));
Debugger.enableTrace(false);
				
				
Debugger.enableTrace(true);
Debugger.debug("COMPLETE SORTING THE POPULATION #########");
Debugger.enableTrace(false);				
				/**
				 * Pipe the results into sets of 3+2*(number of cost functions)
				 * columns each line and print them into a text file
				 */
				PrintText.pipeResults(outputFile);
				// Number of columns in the text file
int numColumns = 3 + 2 * getNumSelectedCostFns(); // redundant
			
				// Start evolving...
				while(gen > 0) {
					/**
					 * The selection of chromosomes, and the process of
					 * mating or mutating them is provided by the NetworkGAImp
					 * Class.
					 */

Debugger.enableTrace(true);
Debugger.debug("fitness of first cost fuunction for first chromo: "
	+set.getChromo(0).getFitArrElem(0));
Debugger.debug("Is fitnessArr of 1st Chromo equal to null? "
	+(set.getChromo(0).getFitnessArr()==null));
Debugger.enableTrace(false);

					netwk.steadyStateEvolve(functArr, this.toggleBinOffSprings());
					
					// For each fitness function...
					for(int j=0; j<functArr.length; j++) {
						// Put the fitness of each Chromosome into the array of arrays
						
						// Put the fittest Chromosome of this generation in there
						bestFit[evolutions][j]=set.getChromo(0).getFitArrElem(j);
						
						// Average fitness value of population
						double avg = 0.0;
						
						/**
						 * Determine the total fitness of Chromosomes in
						 * the population
						 */
						for(int k=0; k<set.getPopSize(); k++) {
							// Sum the fitness of each Chromosome in the population
							avg = avg+set.getChromo(k).getFitArrElem(j);
						}
						
						// Get average fitness of Chromosomes in the population
						avg = avg/set.getPopSize();
						// Put the average fitness in there
						avgFit[evolutions][j]=avg;
					}
					
					/**
					 * Determine the pleiotropy and redundancy of the
					 * fittest chromosome
					 */
					pr.apply(set.getChromo(0));
					// Store the fittest Chromosome for drawing purposes
					fittestChromo = set.getChromo(0);
					gui.displayGUI("Pause","Stop",getFittestChromo());
					// Pause for 3 seconds so that the graph can be displayed
//					try {
//						Thread.sleep(3000);
//					}catch(InterruptedException ie) {
//						// Do nothing...
//					}

/*
					outputFile
					separator
					numColumns
*/
					// Print the results into the file...
					// Add a separator between values
					//[evolutions][#cf]
					/**
					 * # Modified by Andy Lo 24 Apr 2005:
					 *      Orignally tried to print results for non 
					 *  selected functions. This causes 
					 *  ArrayIndexOutOfBoundsException. Now it will obtain
					 *  results from the array.
					 */
					String line = "" + evolutions + separator
						+set.getChromo(0).getPleiotropy() + separator
						+set.getChromo(0).getRedundancy();
					for (int i = 0; i < functArr.length; i++) {
						line += separator + avgFit[evolutions][i] + 
						separator + bestFit[evolutions][i];
					}

					PrintText.printResults(line);
					
					/*
					PrintText.printResults(""+evolutions+separator
						+set.getChromo(0).getPleiotropy()+separator
						+set.getChromo(0).getRedundancy()+separator
						+avgFit[evolutions][0]+separator+bestFit[evolutions][0]
						+separator
						+avgFit[evolutions][1]+separator+bestFit[evolutions][1]
						+separator
						+avgFit[evolutions][2]+separator+bestFit[evolutions][2]
						+separator
						+avgFit[evolutions][3]+separator+bestFit[evolutions][3]
						+separator
						+avgFit[evolutions][4]+separator+bestFit[evolutions][4]
						+separator
						+avgFit[evolutions][5]+separator+bestFit[evolutions][5]);
					*/

Debugger.debug("Simulation is at evolution number: "+evolutions);
					// Increment the number of evolutions
					evolutions++;
					// Decrement the number of generations passed
					gen--;
				}	// End of the genetic algorithm
Debugger.enableTrace(true);
Debugger.debug("Simulation is at evolution number: "+evolutions);
Debugger.enableTrace(false);
				
				// End simulation...
				pauseNow();
				
			}	// evolution in play/paused

		}
		// else do nothing... User must initiate simulation
	}
	
	/**
	 * Method to obtain the state of the PLAY/PAUSE BUTTON
	 * @return the state of the PLAY/PAUSE BUTTON
	 */
	public String getPlayButton() {
		return playButton;
	}
	
	/**
	 * Method to obtain the state of the STOP/DO_NOTHING BUTTON
	 * @return the state of the STOP/DO_NOTHING BUTTON
	 */
	public String getStopButton() {
		return stopButton;
	}
	
	/**
	 * Method to obtain the state of the PLAY/PAUSE BUTTON
	 * @param playBut contains the state of the PLAY/PAUSE BUTTON
	 */
	public void setPlayButton(String playBut) {
		playButton = playBut;
	}
	
	/**
	 * Method to obtain the state of the STOP/DO_NOTHING BUTTON
	 * @param stopBut contains the state of the STOP/DO_NOTHING BUTTON
	 */
	public void setStopButton(String stopBut) {
		stopButton = stopBut;
	}
	
	/**
	 * Method to return the graph storing the topology of the network
	 * @return the graph modeling the telecommunications network
	 */
	public GraphImp getGraph() {
		return graph;
	}
	
	/**
	 * Method to get the fittest Chromosome of the population
	 * @param none
	 * @return fittest Chromosome of the population
	 */
	public Chromosome getFittestChromo() {
		return fittestChromo;
	}
	
	/**
	 * Method to obtain the number of chromosomes in the population
	 * @return number of chromosomes
	 */
	public int getNumChromo() {
		return userInput[numChromo];
	}
	
	/**
	 * Method to obtain the number of generations that the genetic
	 * algorithm is evolving over
	 * @return number of generations
	 */
	public int getNumGen() {
		return userInput[numGen];
	}
	
	/**
	 * Method to return the user input
	 * @param none
	 * @return user input
	 */
	public int[] getUserInput() {
		return userInput;
	}
	
	/**
	 * Method to set the user input
	 * @param input is the user's input
	 * @return nothing
	 */
	public void setUserInput(int[] input) {
		userInput = input;
		numPairChromo = userInput[numChromo]/2;
		Debugger.debug("User input is: "+userInput[0]
			+" "+userInput[1]+" "+userInput[2]+" "+userInput[3]);
	}
	
	/**
	 * Method to return the selected cost functions' indices
	 * @param none
	 * @return selected cost functions' indices
	 */
	public boolean[] getCostFunctions() {
		return costFunctions;
	}
	
	/**
	 * Method to set the selected cost functions' indices
	 * @param input is the selected cost functions' indices
	 * @return nothing
	 */
	public void setCostFunctions(boolean[] costs) {
		costFunctions = costs;
		Debugger.debug("Costs functions are: "+costFunctions[0]
			+" "+costFunctions[1]+" "+costFunctions[2]+" "+costFunctions[3]
			+" "+costFunctions[4]+" "+costFunctions[5]);
Debugger.debug("THE VALUE OF getStart() IS: "+getStart());
startNow();
input.dispose();
Debugger.enableTrace(false);
run();
Debugger.debug("THE VALUE OF getStart() HAS BEEN SET TO???: "+getStart());
Debugger.debug("");
Debugger.debug("");
Debugger.debug("");
Debugger.debug("NUMBER OF SELECTED COST FUNCTIONS IS: "+getNumSelectedCostFns());
Debugger.debug("");
Debugger.debug("");
Debugger.debug("");
	}
	
	/**
	 * Method to indicate if the simulation can start
	 * @param none
	 * @return boolean variable indicating if the simulation can start
	 */
	public boolean getStart() {
		return start;
	}
	
	/**
	 * Method to start the simulation
	 * @param none
	 * @return nothing
	 */
	public void startNow() {
		start = true;
	}
	
	/**
	 * Method to pause the simulation
	 * @param none
	 * @return nothing
	 */
	public void pauseNow() {
		start = false;
		gui.displayGUI("Play","Stop",getFittestChromo());
	}
	
	/**
	 * Method to toggle the boolean amount of offsprings to be created
	 * during steady state evolutionary computation
	 * @param none
	 * @return the toggled value for the number of offsprings
	 */
	public boolean toggleBinOffSprings(){
		if(replaceTwo) {
			replaceTwo = false;
		}else{
			replaceTwo = true;
		}
		return replaceTwo;
	}
	
	/**
	 * Method to return the number of selected cost functions
	 * @param none
	 * @return number of selected cost functions
	 */
	public int getNumSelectedCostFns(){
		return numSelectedCostFns;
	}
	
	/**
	 * Method to set the number of selected cost functions
	 * @param number of selected cost functions
	 * @return none
	 */
	public void setNumSelectedCostFns(int num){
		numSelectedCostFns=num;
	}
}
