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
 * Class to run the simulation for "NetSim" without the GUI
 * @author Zhiyang Ong and Andy Lo
 * @version 0.4.1
 * @acknowledgement C. Xavier, "Programming with Java 2," Scitech Publications
 * (India) Pvt Ltd, Chennai, 2000
 * @acknowledgement Anton van den Hengel, "Artificial Intelligence Lecture
 *  Notes: Genetic Algorithms," The University of Adelaide, Adelaide, 2004
 */

// Class definition...

// ========================================================================

public class Optimizer{ // extends Thread {
	// Declaration of instance variables
	
	/**
	 * ### User input for the number of clients and servers in the network,
	 * ### number of generations to be evolved, and the size of the population
	 */
	private static int[] userInput = {80,5,3000,90};
	
	// Index for the number of clients in the telecommunications network
	private static int numClients=0;
	
	// Index for the number of servers in the telecommunications network
	private static int numServers=1;
	
	// Index for the number of generations which the genetic algorithm will be run
	private static int numGen=2;
	
	/**
	 * Index for the number of Chromosomes in the population that the
	 * genetic algorithm is optimising
	 */
	private static int numChromo = 3;
	
	// Number of pairs of chromosomes in the population
	private static int numPairChromo;
	
	
	// ### Probability of mutation for selected Chromosomes
	private static double prMutation=0.091;
	// ### Probability of crossover for selected Chromosomes
	private static double prCrossover=0.26;
	
	
// ### Number of cost functions
private static int numCostFunctions = 0;
	
	
	/**
	 * Indicator of the boolean number of offsprings produced for
	 * steady state evolutionary algorithm
	 */
	private static boolean replaceTwo=true;
	
	// Fittest Chromosome of the population
	private static Chromosome fittestChromo;
	
	// Separator for the simulation results file to delimit values
	private static String separator = " ";	// Use a character space
	private static char CHARACTER_SPACE = ' ';	// Use a character space
	// ### Name of the output file
	private static String outputFile = "simulation";
    
	// GUI to display the topology of the network
	private static Workspace topo;

	/**
	 * Boolean flag to indicate if user input has been entered
	 * True - if user has not entered input
	 * False - if user has already entered input
	 */
	private static boolean getInput = true;
	
	// Number of selected Chromosomes
	private static int numSelectedCostFns=0;
	
	// Indices of the cost functions selected - default values
	private static int[] costFunctions={1, 0, 0, 1, 0, 0, 0, 0};
	
	/**
	 * User input for the number of clients and servers in the network,
	 * number of generations to be evolved, and the size of the population
	 */
	private static int[] usrip={80, 5, 14, 100};
	
	// Pop-up window to query user for input
	private static SelectionChoices input;
	
   // -----------------------------------------------------------------
    
   // Default constructor
   public Optimizer() {
		// DO NOT INSTANTIATE THE DEFAULT CONSTRUCTOR OF SimulationThread
		Debugger.printErr("Simulation cannot commence without user's"
			+" interaction with the GUI");
		Debugger.printErr("Operation of NetSim has come to a halt!");
	}
    
	// ------------------------------------------------------------------
    
	/**
	 * Method to run this SimulationThread to commence simulation
	 */
	public static void main(String[] args) {
	    long start, finish, timeInit, timeMap, timeEvo;
		
		/**
		 * ### Modified by Zhiyang Ong - 8 Jul 2005
		 * "start" and "finish" indicate the beginning and end of a time
		 * period used to keep track of initializing the population,
		 * evaluating the fitness for each chromosome in the population,
		 * and evolving the population for a number of generations
		 * determined by the user. They are time counters
		 *
		 * timeInit is the time taken to initialize the population
		 *
		 * timeMap is the time taken to evaluate the fitness for each
		 * chromosome in the population
		 *
		 * timeEvo is the time taken to evolve the population for a
		 * number of generations determined by the user.
		 */

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
Debugger.enableTrace(true);
Debugger.debug("");
Debugger.debug("");
Debugger.debug("");
Debugger.debug("Starting NetSim 2005!!!!!!!!!!");
				
// Get user input...
// Create pop-up window to query user for input
input = new SelectionChoices();
input.show();		// Make the pop-up window visible
		// Get the user input
//		while(getUsrInput()) {
//			try{
				
				
//				setUsrInput(false);

/*
			}catch(NumberFormatException nfe) {
				Debugger.debug("Invalid input argument entered");
				Debugger.debug("Enter numclients numservers numgens popsize"
					+" selectedCostFunctions");
			}
*/
//		}

// Keep prompting the user for input until data is entered...
while((input.getUserInput() == null) && (input.getCostFunctions() == null)
	&& (!input.getProceed())) {
}
// Obtain the numerical values of the user's input
usrip=input.getUserInput();
userInput=input.getUserInput();
//costFunctions=input.getCostFunctions();
// Number of selected cost functions
numCostFunctions=0;

// Determine the number of selected cost functions
for(int i=0;i<costFunctions.length;i++) {
	if(costFunctions[i]==1) {
		numCostFunctions++;
	}
}
Debugger.enableTrace(true);
Debugger.debug("");
Debugger.debug("");
Debugger.debug("Selected user input is:");
Debugger.debug("Number of CLIENTS: "+userInput[0]);
Debugger.debug("Number of SERVERS: "+userInput[1]);
Debugger.debug("Number of GENERATIONS: "+userInput[2]);
Debugger.debug("POPULATION SIZE: "+userInput[3]);
Debugger.debug("Number of SELECTED COST FUNCTIONS: "+numCostFunctions);
Debugger.debug("Costs functions are: "+costFunctions[0]
			+" "+costFunctions[1]+" "+costFunctions[2]+" "+costFunctions[3]
			+" "+costFunctions[4]+" "+costFunctions[5]+" "+costFunctions[6]
			+" "+costFunctions[7]);
Debugger.debug("");
Debugger.debug("");
Debugger.enableTrace(false);
		// Determine the number of clients
		//userInput[0] = Integer.parseInt(args[0]);
//		userInput[0]= usrip[0];
		// Determine the number of servers
		//userInput[1] = Integer.parseInt(args[1]);
//		userInput[1]= usrip[1];
		// Determine the number of generations
		//userInput[2] = Integer.parseInt(args[2]);
//		userInput[2]= usrip[2];
		// Determine the size of the population
		//userInput[3] = Integer.parseInt(args[3]);
//		userInput[3]= usrip[3];
		// Get the number of cost functions
/*
		for(int ip=0; ip<costFunctions.length; ip++) {
			if(costFunctions[ip] == 1) {
				numCostFunctions++;
			}
		}
*/

		int gen = getNumGen();
		//gen--;
		// Number of evolutions... == 0
		int evolutions=0;

        Debugger.debug("Pre-processing stage:\n");
        Debugger.debug("Initialising Network...");
        Debugger.enableTrace(false);

		// Counter started to determine the period to initialise the population
        start = System.currentTimeMillis();


// ### Modified by Zhiyang Ong - 31 Jul 2005
// Number of chromosomes in population = number of selected cost functions
userInput[numChromo]=numCostFunctions;
		
		// Provide the means to allow the network to evolve
		NetworkGAImp netwk1 = new 
			NetworkGAImp(prCrossover,prMutation,
			userInput[numServers],userInput[numClients],
			userInput[numChromo]);
				
		// Get the current set of Population
		SetOfChromosomes set1 = netwk1.getCurPop();
		
/*
		NetworkGAImp netwk2 = new 
			NetworkGAImp(prCrossover,prMutation,
			userInput[numServers],userInput[numClients],
			userInput[numChromo]);
*/
NetworkGAImp netwk2 = (NetworkGAImp)netwk1.clone();
				
		// Get the current set of Population
		SetOfChromosomes set2 = netwk2.getCurPop();
		
/*
		NetworkGAImp netwk3 = new 
			NetworkGAImp(prCrossover,prMutation,
			userInput[numServers],userInput[numClients],
			userInput[numChromo]);
*/

NetworkGAImp netwk3 = (NetworkGAImp)netwk1.clone();

		// Get the current set of Population
		SetOfChromosomes set3 = netwk3.getCurPop();

		// Determine the time taken to initialise the population
        finish = System.currentTimeMillis(); 
        timeInit = (finish-start)/1000;
		/**
		 * Initialise time counter to determine period to evaluate
		 * the fitness of every chromosome
		 */
        start = System.currentTimeMillis();
		
/*
Debugger.enableTrace(true);
Debugger.debug("@@@@@Is set == netwk.getCurPop()?"+(set == netwk.getCurPop()));
Debugger.debug("@@@@@Is set.equals(netwk.getCurPop())?"
	+(set.equals(netwk.getCurPop())));
Debugger.enableTrace(false);
*/
        /**
         * # Modified by Andy Lo 30 Apr 2005: 
         *      to include the 7th and 8th cost functions
         */
		// Array of dummy cost functions
		Function[] functions = new Function[8];
		functions[0]=new MinimalPaths(false);
		functions[1]=new MinimalPaths(true);
		functions[2]=new TotalEdgeCost();
		functions[3]=new DegreeSeparation(true);
		functions[4]=new DegreeSeparation(false);
		functions[5]=new ServerLoad();
		functions[6]=new ClusterCoeff();
		functions[7]=new Resistance();
				
		// Cost functions for the simulation
		Function[] functArr = new Function[numCostFunctions];
		// index for the next cost function
		int indexNext=0;
Debugger.enableTrace(true);
Debugger.debug("");
Debugger.debug("");
Debugger.debug("numCostFunctions:"+numCostFunctions);
Debugger.debug("costFunctions.length"+costFunctions.length);
//Debugger.debug("Is this cost function == null"+(functArr[indexNext]==null));
//Debugger.debug("indexNext: "+indexNext);
Debugger.debug("");
Debugger.debug("");
Debugger.enableTrace(false);
		/**
		 * For each selected cost function, add it to the array of cost
		 * functions that will be used to evaluate the fitness of each
		 * chromosome.
		 */
		for(int cfs=0; cfs<costFunctions.length; cfs++) {
			if(costFunctions[cfs] == 1) {
				functArr[indexNext] = functions[cfs];
Debugger.enableTrace(true);
Debugger.debug("");
Debugger.debug("");
Debugger.debug("Selected cost function number: "+cfs);
Debugger.debug("numCostFunctions:"+numCostFunctions);
Debugger.debug("costFunctions.length"+costFunctions.length);
Debugger.debug("number Selected cost functions: "+numCostFunctions);
//Debugger.debug("Is this cost function == null"+(functArr[indexNext]==null));
//Debugger.debug("indexNext: "+indexNext);
Debugger.debug("");
Debugger.debug("");
Debugger.enableTrace(false);
				indexNext++;
			}
		}
		
		netwk2.getCurPop().setCostFunctions(functArr);
		netwk3.getCurPop().setCostFunctions(functArr);
		
		
		//functArr[0]=new MinimalPaths(false);
		//functArr[0]=new MinimalPaths(true);
//		functArr[2]=new TotalEdgeCost();
		//functArr[1]=new DegreeSeparation(true);
		//functArr[3]=new DegreeSeparation(false);
		//functArr[4]=new ServerLoad();
			
		// Tool used to measure the pleiotropy and redundancy values
		PRCalculator pr = new PRCalculator();
			
		// For each chromosome...
		for(int i=0; i<set1.getPopSize(); i++) {
			// Create the array of fitness functions to hold
			// the fitness values
//Debugger.enableTrace(true);


// ### Modified by Zhiyang Ong - 31 July 2005
//			set.getChromo(i).createFitnessArr(functArr.length);
((Chromosome)set1.getChromo(i)).createFitnessArr(1);
double t1 = ((Chromosome)set1.getChromo(i)).getFitArrElem(0);
if((functArr.length != set1.getPopSize()) || (functArr.length != 3)) {
	throw new NullPointerException("Uh oh!");
}
//set2.getChromo(i).createFitnessArr(1);
//set3.getChromo(i).createFitnessArr(1);


//Debugger.debug("functArr.length IS THIS: "+functArr.length);
//Debugger.debug("getFitnessArr() == null???? "
//	+(set.getChromo(i).getFitnessArr()==null));
//Debugger.enableTrace(false);
		}
		
		for(int i=0; i<set2.getPopSize(); i++) {
			((Chromosome)set2.getChromo(i)).createFitnessArr(1);
double t2 = ((Chromosome)set1.getChromo(i)).getFitArrElem(0);
if((functArr.length != set1.getPopSize()) || (functArr.length != 3)) {
	throw new NullPointerException("Uh oh!");
}
		}
		
		for(int i=0; i<set3.getPopSize(); i++) {
			((Chromosome)set3.getChromo(i)).createFitnessArr(1);
double t3 = ((Chromosome)set1.getChromo(i)).getFitArrElem(0);
if((functArr.length != set1.getPopSize()) || (functArr.length != 3)) {
	throw new NullPointerException("Uh oh!");
}
		}

//Debugger.enableTrace(true);
//Debugger.debug("@@@@@Is set == netwk.getCurPop()?"+(set == netwk.getCurPop()));
//Debugger.debug("@@@@@Is set.equals(netwk.getCurPop())?"
//	+(set.equals(netwk.getCurPop())));
//Debugger.enableTrace(false);

		// Put the selected cost functions into an array...
		// For all functions...
		
		
// ### Modified by Zhiyang Ong - 31 July 2005
/*
		for(int indice=0; indice<functArr.length; indice++) {
			// Determine the fitness of each Chromosome in the population
			functArr[indice].map(set,indice);
		}
*/
		for(int indice=0; indice<functArr.length; indice++) {
			// Determine the fitness of each Chromosome in the population
			// For the i(th) chromosome, evaluate the i(th) function
			functArr[indice].apply(set1.getChromo(indice),0);
			functArr[indice].apply(set2.getChromo(indice),0);
			functArr[indice].apply(set3.getChromo(indice),0);
		}		
		
				
		// pass the selected cost functions to set of chromosomes
		set1.setCostFunctions(functArr);
		set2.setCostFunctions(functArr);
		set3.setCostFunctions(functArr);
		// Assign Fitness values to them
		// Average fitnesses of the population for each generation
//		double[][] avgFit = new double[getNumGen()][functArr.length];
int arrSize=3000;
double[][] avgFit1 = new double[arrSize][functArr.length];
double[][] avgFit2 = new double[arrSize][functArr.length];
double[][] avgFit3 = new double[arrSize][functArr.length];	
			
		// Best fitnesses of the population for each generation
//		double[][] bestFit = new double[getNumGen()][functArr.length];
double[][] bestFit1 = new double[arrSize][functArr.length];
double[][] bestFit2 = new double[arrSize][functArr.length];
double[][] bestFit3 = new double[arrSize][functArr.length];
		
		// Normalise fitness values before sorting
// ### Modified by Zhiyang Ong - 31 July 2005
//		set.normalize();
			
		// Sort the Chromosomes in the population according to their fitness
// ### Modified by Zhiyang Ong - 31 July 2005
//		set.sort();
		
		// Determine the period to evaluate the fitness of every chromosome
        finish = System.currentTimeMillis(); 
        timeMap = (finish - start)/1000;

//Debugger.enableTrace(true);
//Debugger.debug("@@@@@Is set == netwk.getCurPop()?"+(set == netwk.getCurPop()));
//Debugger.debug("@@@@@Is set.equals(netwk.getCurPop())?"
//	+(set.equals(netwk.getCurPop())));
//Debugger.enableTrace(false);
				
				
Debugger.enableTrace(true);
// Debugger.debug("COMPLETE SORTING THE POPULATION #########");       
Debugger.debug("\nCommencing Steady State Evolution...");
Debugger.debug("num server: "+userInput[numServers]);
Debugger.debug("num clients: "+userInput[numClients]);
Debugger.debug("num chromo: "+userInput[numChromo]);
Debugger.debug("num gen: "+getNumGen());
Debugger.enableTrace(false);				

		/**
		 * Pipe the results into sets of 3+2*(number of cost functions)
		 * columns each line and print them into a text file
		 */
		PrintText.pipeResults(outputFile);
		
		// Initialse counter to measure period to evolve the population
		start = System.currentTimeMillis(); 
			
		// Start evolving...
		while(gen > 0) {
			/**
			 * The selection of chromosomes, and the process of
			 * mating or mutating them is provided by the NetworkGAImp
			 * Class.
			 */

Debugger.enableTrace(true);
//Debugger.debug("fitness of first cost fuunction for first chromo: "
//	+set.getChromo(0).getFitArrElem(0));
//Debugger.debug("Is fitnessArr of 1st Chromo equal to null? "
//	+(set.getChromo(0).getFitnessArr()==null));
Debugger.debug("Evolution #" + evolutions);
Debugger.enableTrace(false);

			/**
			 * Get a pair of chromosomes in this population to produce offsprings,
			 * mutate, and cooperate
			 */
			netwk1.steadyStateEvolve(functArr, toggleBinOffSprings());
			netwk2.steadyStateEvolve(functArr, toggleBinOffSprings());
			netwk3.steadyStateEvolve(functArr, toggleBinOffSprings());
				
			// For each fitness function...
			for(int j=0; j<functArr.length; j++) {
				// Put the fitness of each Chromosome into the array of arrays
				
				// Put the fittest Chromosome of this generation in there
// ### Modified by Zhiyang Ong - 31 July 2005
// bestFit[evolutions][j]=set.getChromo(0).getFitArrElem(j);
try{
				bestFit1[evolutions][j]=set1.getChromo(j).getFitArrElem(0);
				bestFit2[evolutions][j]=set2.getChromo(j).getFitArrElem(0);
				bestFit3[evolutions][j]=set3.getChromo(j).getFitArrElem(0);
}catch(NullPointerException e){
	if((bestFit1[evolutions][j] == 0) && (j>1)) {
		bestFit1[evolutions][j] = bestFit1[evolutions][j-1];
	}
	if((bestFit2[evolutions][j] == 0) && (j>1)) {
		bestFit2[evolutions][j] = bestFit2[evolutions][j-1];
	}
	if((bestFit3[evolutions][j] == 0) && (j>1)) {
		bestFit3[evolutions][j] = bestFit3[evolutions][j-1];
	}
}
				
				// Average fitness value of population
				double avg1 = 0.0;
				double avg2 = 0.0;
				double avg3 = 0.0;
				
				/**
				 * Determine the total fitness of Chromosomes in
				 * the population
				 */
				for(int k=0; k<set1.getPopSize(); k++) {
					// Sum the fitness of each Chromosome in the population
// ### Modified by Zhiyang Ong - 31 July 2005
// avg = avg+set.getChromo(k).getFitArrElem(j);


try{
					avg1 = avg1+set1.getChromo(k).getFitArrElem(0);
}catch(NullPointerException e){
}
				}
				
				for(int k=0; k<set2.getPopSize(); k++) {
try{
					avg2 = avg2+set2.getChromo(k).getFitArrElem(0);
}catch(NullPointerException e){
}
				}
				
				for(int k=0; k<set3.getPopSize(); k++) {
try{
					avg3 = avg3+set3.getChromo(k).getFitArrElem(0);
}catch(NullPointerException e){
}
				}
				
				
				
				// Get average fitness of Chromosomes in the population
				avg1 = avg1/set1.getPopSize();
				avg2 = avg2/set2.getPopSize();
				avg3 = avg3/set3.getPopSize();
				// Put the average fitness in there
				avgFit1[evolutions][j]=avg1;
				avgFit2[evolutions][j]=avg2;
				avgFit3[evolutions][j]=avg3;
			}
					
			/**
			 * Determine the pleiotropy and redundancy of the
			 * fittest chromosome
			 */
			pr.apply(set1.getChromo(0));
			pr.apply(set2.getChromo(0));
			pr.apply(set3.getChromo(0));
			// Store the fittest Chromosome for drawing purposes
			Chromosome fittestChromo1 = set1.getChromo(0);
			Chromosome fittestChromo2 = set2.getChromo(0);
			Chromosome fittestChromo3 = set3.getChromo(0);

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
				+set1.getChromo(0).getPleiotropy() + separator
				+set1.getChromo(0).getRedundancy();
			for (int i = 0; i < functArr.length; i++) {
				line += separator + avgFit1[evolutions][i] + 
				separator + bestFit1[evolutions][i];
			}

			// Display the results...
			PrintText.printResults(line);
			
			// Display the communication network's topology
/*
			topo = new Workspace(set1.getChromo(0));
			topo.show();
			topo.delayTopology(3);
			topo.dispose();
*/

					
Debugger.debug("Simulation is at evolution number: "+evolutions);
			// Increment the number of evolutions
			evolutions++;
			// Decrement the number of generations passed


// ### Modified by Zhiyang Ong - 31 July 2005
//			gen--;
int prev100= evolutions - 100;
gen = 1;
if((evolutions>100) && (evolutions<(arrSize-2))) {
	if((bestFit1[evolutions][0]!=bestFit1[evolutions][0])
		&& (bestFit2[evolutions][1]!=bestFit2[evolutions][1])
		&& (bestFit3[evolutions][2]!=bestFit3[evolutions][2])) {
		// Nash equilibrium is not reached
		gen = 0;
	}
}else{
	gen = 0;
}


// Swap chromosomes
if((evolutions % 5)==0) {
	Chromosome a = (Chromosome)(set1.getChromo(0)).clone();
	Chromosome b = (Chromosome)(set2.getChromo(0)).clone();
	Chromosome c = (Chromosome)(set3.getChromo(0)).clone();
	set1.replaceChromo(1,b);
	set1.replaceChromo(2,c);
	set2.replaceChromo(0,a);
	set2.replaceChromo(2,c);
	set3.replaceChromo(0,a);
	set3.replaceChromo(1,b);
}


		}	// End of the genetic algorithm
		
		// Get the fittest chromosome...
		Chromosome bestSimChromo = set1.getChromo(0);
		// (numServer)^2 is used, since
//		int[] pleioServer = new int[userInput[1]*userInput[1]];
		int[] pleioServer = new int[userInput[0]*userInput[0]];
		// (numClient)^2 is used, since
//		int[] redunClient = new int[userInput[0]*userInput[0]];
		int[] redunClient = new int[userInput[1]*userInput[1]];
		for(int i=0;i<bestSimChromo.getLength();i++) {
			// Determine the number of outgoing edges for the fittest chromosome
			if(((NodeImp)bestSimChromo.getNodeList().get(i)).getLabel().equals("SERVER")) {
				int p = ((ArrayList)bestSimChromo.getData(i)).size();

//				pleioServer[p]=pleioServer[p]+1;
				pleioServer[p]=p;
			}else{
				// Determine the number of incoming edges for the fittest chromosome
				int r = 0;
				NodeImp curNode = (NodeImp)bestSimChromo.getNodeList().get(i);
				for(int j=0;j<bestSimChromo.getLength();j++) {
					ArrayList curList = bestSimChromo.getData(j);
					if(curList.contains(curNode)) {
						r=r+1;
					}
				}
				redunClient[r]=redunClient[r]+1;
			}
		}
		for(int m=0;m<redunClient.length;m++) {
			if(redunClient[m] != 0) {
				Debugger.enableTrace(true);
				Debugger.debug("num of clients at "+m+" is: "+redunClient[m]);
				Debugger.enableTrace(false);
			}
		}
		for(int n=0;n<pleioServer.length;n++) {
			if(pleioServer[n] != 0) {
				Debugger.enableTrace(true);
				Debugger.debug("num of servers at "+n+" is: "+pleioServer[n]);
				Debugger.enableTrace(false);
			}
		}

		// Determine the period for evolving the network
        finish = System.currentTimeMillis(); 
        timeEvo = (finish - start)/1000;

Debugger.enableTrace(true);
Debugger.debug("Simulation completed at evolution #"+evolutions);
Debugger.debug("===================================\n" +
               "SUMMARY:\n" +
               "Initialising Time   =\t" + timeInit + "\tseconds\n" +
               "Function Map Time   =\t" + timeMap + "\tseconds\n" +
               "Evolution Time      =\t" + timeEvo + "\tseconds\n" +
               "Total Time          =\t" + (timeInit+timeMap+timeEvo) +
                                                             "\tseconds\n" +
               "===================================");    
Debugger.enableTrace(false);
        	
		// End simulation...
//		}
	}
	
	/**
	 * Method to get the fittest Chromosome of the population
	 * @return fittest Chromosome of the population
	 */
	public static Chromosome getFittestChromo() {
		return fittestChromo;
	}
	
	/**
	 * Method to obtain the number of chromosomes in the population
	 * @return number of chromosomes
	 */
	public static int getNumChromo() {
		return userInput[numChromo];
	}
	
	/**
	 * Method to obtain the number of generations that the genetic
	 * algorithm is evolving over
	 * @return number of generations
	 */
	public static int getNumGen() {
		return userInput[numGen];
	}
	
	/**
	 * Method to toggle the boolean amount of offsprings to be created
	 * during steady state evolutionary computation
	 * @return the toggled value for the number of offsprings
	 */
	public static boolean toggleBinOffSprings(){
		if(replaceTwo) {
			replaceTwo = false;
		}else{
			replaceTwo = true;
		}
		return replaceTwo;
	}
	
	public static boolean getUsrInput() {
		return getInput;
	}
	
	public static void setUsrInput(boolean input) {
		getInput = input;
	}
	
	/**
	 * Method to return the number of selected cost functions
	 * @param none
	 * @return number of selected cost functions
	 */
	public static int getNumSelectedCostFns(){
		return numSelectedCostFns;
	}
	
	/**
	 * Method to set the number of selected cost functions
	 * @param number of selected cost functions
	 * @return none
	 */
	public static void setNumSelectedCostFns(int num){
		numSelectedCostFns=num;
	}

	/**
	 * Method to set the selected cost functions' indices
	 * @param input is the selected cost functions' indices
	 * @return nothing
	 */
	public static void setCostFunctions(int[] costs) {
		costFunctions = costs;
		Debugger.debug("Costs functions are: "+costFunctions[0]
			+" "+costFunctions[1]+" "+costFunctions[2]+" "+costFunctions[3]
			+" "+costFunctions[4]+" "+costFunctions[5]+" "+costFunctions[6]
			+" "+costFunctions[7]);
Debugger.debug("THE VALUE OF getUsrInput() IS: "+getUsrInput());
//startNow();
//####input.dispose();
input.dispose();
setUsrInput(true);
Debugger.enableTrace(false);
//run();
Debugger.debug("THE VALUE OF getUsrInput() HAS BEEN SET TO???: "+getUsrInput());
Debugger.debug("");
Debugger.debug("");
Debugger.debug("");
Debugger.debug("NUMBER OF SELECTED COST FUNCTIONS IS: "+getNumSelectedCostFns());
Debugger.debug("");
Debugger.debug("");
Debugger.debug("");
	}

	/**
	 * Method to return the selected cost functions' indices
	 * @param none
	 * @return selected cost functions' indices
	 */
	public static int[] getCostFunctions() {
		return costFunctions;
	}
	
	/**
	 * Method to return the user input
	 * @param none
	 * @return user input
	 */
	public static int[] getUserInput() {
		return usrip;
	}
	
	/**
	 * Method to set the user input
	 * @param input is the user's input
	 * @return nothing
	 */
	public static void setUserInput(int[] input) {
		usrip = input;
		numPairChromo = usrip[numChromo]/2;
		Debugger.debug("User input is: "+usrip[0]
			+" "+usrip[1]+" "+usrip[2]+" "+usrip[3]);
	}
}
