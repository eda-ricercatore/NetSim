// importing packages
import utility.*;
import population.*;
import population.graph.*;
import ecomp.*;
import java.util.*;

/**
 * This is the second part of module tests for NetworkGAImp. This will tests
 * the standard constructor, which will automatically generate a populatoin.
 * Evolutionary algorithm methods will also be tested. This includes the
 * operaters crossover and mutation, and steady-state evolution and classical
 * GA evolution.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.7
 */
public class ModuleTestNetworkGA02 {
    // instance variable to store info regarding current test
    private static String testName;
	
	private static final double PROB_CROSSOVER = 0.70;
    private static final double PROB_MUTATION  = 0.35;
    
    // ---------------------------------------------------------------------
	 
    // Default constructor
    /**
     * Should not instantate a test class.
     */
    public ModuleTestNetworkGA02() {
        Debugger.printErr("Don't instantiate a test class: " + 
                            "ModuleTestNetworkGA02");
    }
	
	// --------------------------------------------------------------------
    
    /**
     * Initiates tests for standard constructor, crossover and mutation, and
     * steady-state evolution and classical GA evolutioin.
     */
	public static void main(String[] args) {
        Debugger.pipeResult("NetworkGAImpNormal02.txt",
                            "NetworkGAImpError02.txt");
        Debugger.enableTrace(true);

        Debugger.debug("==================================\n"+
                       "filename: NetworkGAImpNormal02.txt\n" +
                       "==================================");
        Debugger.debug("Module Test 02 for ecomp.NetworkGAImp:\n");
        
        Debugger.printErr("=================================\n"+
                          "filename: NetworkGAImpError02.txt\n" +
                          "=================================");
        Debugger.printErr("Module Test 02 for ecomp.NetworkGAImp:\n");

        // test standard constructor
        Debugger.debug("");
        NetworkGAImp standardImp = testStandardConstructor();
        Debugger.debug("");
        // test basic with standard instance
        testBasic(standardImp);
        Debugger.debug("");

        // obtain known chromosomes
        Chromosome[] testChromos = createChromos();
        // test crossover with known chromosomes
        testCrossover(testChromos);
        Debugger.debug("");
        // test mutation with known chromosome
        testMutation(testChromos);
        Debugger.debug("");

        // create population with known chromosomes
        SetOfChromosomes testPop = new SetOfChromosomes();
        for(int i = 0; i < testChromos.length; i++)
            testPop.addChromo(testChromos[i]);

        // test steady state evolve with known population
        testSteadyStateEvolve(testPop);
        Debugger.debug("");
        
        // re-create population with known chromosomes
        testPop = new SetOfChromosomes();
        for(int i = 0; i < testChromos.length; i++)
            testPop.addChromo(testChromos[i]);
        // test classical ga evovle with known population
        testClassicalGAEvolve(testPop);
        Debugger.debug("");

        Debugger.debug("========================================");
        Debugger.debug("Module Test for NetworkGAImp02 Completed");
        Debugger.debug("========================================");
        Debugger.printErr("========================================");
        Debugger.printErr("Module Test for NetworkGAImp02 Completed");
        Debugger.printErr("========================================");
    }
    
// ==========================================================================
    
    /**
     * Testing the basic accessor and mutator methods of "NetworkGAImp"
     */
    private static void testBasic(NetworkGAImp nga) {
        Debugger.debug("Testing the basic accessor and mutator methods of " +
                            "NetworkGAImp");
        Debugger.debug("==============================="+
                            "==============================\n");

        double prob;
        
        // ========================================================
        // Testing setting and retrieving probability Crossover
        // ========================================================
        nga.getPrCrossover();

        testName = "Setting probability of crossover";
        
        // test setting probability of crossover with illegal value
        prob = -1.5;
        Debugger.debug(testName + " to " + prob);
        try {
            nga.setPrCrossover(prob);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                        "SHOULD have been caught, since prCrossover to be" +
                        "\n    set is " + prob + ", violating the " +
                        "precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                        "prCrossover to be set is " + prob + ",\n    " +
                        "violating the precondition");
        }
        Debugger.debug("");

        // test setting probability of crossover with illegal value
        prob = 1.5;
        Debugger.debug(testName + " to " + prob);
        try {
            nga.setPrCrossover(prob);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                        "SHOULD have been caught, since prCrossover to be" +
                        "\n    set is " + prob + ", violating the " +
                        "precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                        "prCrossover to be set is " + prob + ",\n    " +
                        "violating the precondition");
        }
        Debugger.debug("");

        // test setting probability of crossover with legal value
        prob = 0.3;
        Debugger.debug(testName + " to " + prob);
        try {
            nga.setPrCrossover(prob);
            Debugger.debug(testName + ":\n    Exception not caught as " +
                        "expected, since prCrossover to be\n    set is " + 
                        prob +", which is legal");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                        "SHOULD NOT have been caught since prCrossover to " +
                        "be set is " + prob + ",\n    which is legal");
        }

        nga.getPrCrossover();
        
        Debugger.debug("");

        // =======================================================
        // Testing setting and retrieving probability Mutation
        // =======================================================
        nga.getPrMutation();

        testName = "Setting probability of mutation";
        
        // test setting probability of mutation with illegal value
        prob = -1.5;
        Debugger.debug(testName + " to " + prob);
        try {
            nga.setPrMutation(prob);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                        "SHOULD have been caught, since prMutation to be" +
                        "\n    set is " + prob + ", violating the " +
                        "precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                        "prMutation to be set is " + prob + ",\n    " +
                        "violating the precondition");
        }
        Debugger.debug("");

        // test setting probability of mutation with illegal value
        prob = 1.5;
        Debugger.debug(testName + " to " + prob);
        try {
            nga.setPrMutation(prob);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                        "SHOULD have been caught, since prMutation to be" +
                        "\n    set is " + prob + ", violating the " +
                        "precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                        "prMutation to be set is " + prob + ",\n    " +
                        "violating the precondition");
        }
        Debugger.debug("");

        // test setting probability of mutation with legal value
        prob = 0.05;
        Debugger.debug(testName + " to " + prob);
        try {
            nga.setPrMutation(prob);
            Debugger.debug("    Exception not caught as " +
                        "expected, since prMutation to be\n    set is " + 
                        prob +", which is legal");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                        "SHOULD NOT have been caught since prMutation to " +
                        "be set is " + prob + ",\n    which is legal");
        }

        nga.getPrMutation();
        Debugger.debug("");
        
        // =================================================================
        // Testing incrementing and retrieving number of generations evolved
        // =================================================================
        Debugger.debug(testName = "Testing incrementing and retrieving " +
                                    "number of generations evolved");
        int gens;
        Debugger.debug("Generations evolved is " + (gens = nga.getNumGen()) +
                            " generations");
        Debugger.debug("Increase the number of generations evolved by 1 " +
                            "by calling increGens()");
        nga.increGen();
        if( nga.getNumGen() == (gens+1)) {
            Debugger.debug("Generations evolved is now " + nga.getNumGen() +   
                            " generations");
        } else {
            Debugger.printErr(testName + ":\n    ERROR!!! Generations " +
                            "evolved WAS NOT incremented by 1 generation, " +
                            "but by " + (nga.getNumGen()-gens) );    
        }

        Debugger.debug("");
        Debugger.debug("Testing to basic accessor and mutator methods of " +
                            "NetworkGAImp Completed");
        Debugger.debug("===================================="+
                            "===================================\n");
    }

    /**
     * Generate the nodeList which resemble nodes in our "Mock-Up" graph
     */
     private static ArrayList genNodeList() {

        ArrayList nodeList = new ArrayList();

        //Populating Node List
        Debugger.debug("Populate the nodeList with 11 nodes");

        Debugger.enableTrace(false);
        // assume the worst case repair generation is 20 generations
        int repgen = (int)(Math.random() * 20.0);
        /**
         * assume the probability of failure is 30% and the threshold
         * (the maximum allowable probability of failure) is 70%.
         */
        Repairable r = new Repairable(repgen, 0.30, 0.70, true);
        // node is capable of 10 Gbit traffic
        double[] params = {1000000000, 1.0}; 
        Node tmpNode = new NodeImp (params, r);
        
        nodeList.add(new NodeImp("CLIENT", 130, 160, tmpNode));
        nodeList.add(new NodeImp("SERVER", 210, 210, tmpNode));
        nodeList.add(new NodeImp("SERVER", 130,  70, tmpNode));
        nodeList.add(new NodeImp("SERVER",  70, 120, tmpNode));
        nodeList.add(new NodeImp("CLIENT",  60,  30, tmpNode));
        nodeList.add(new NodeImp("CLIENT", 130,  10, tmpNode));
        nodeList.add(new NodeImp("CLIENT", 210,  60, tmpNode));
        nodeList.add(new NodeImp("CLIENT", 200, 120, tmpNode));
        nodeList.add(new NodeImp("CLIENT",  10, 170, tmpNode));
        nodeList.add(new NodeImp("CLIENT",  90, 200, tmpNode));
        nodeList.add(new NodeImp("CLIENT",  10,  90, tmpNode));

        Debugger.enableTrace(true);
        return nodeList;
    }

    /**
     * Prints a simple adjacency list of a chromosome
     */
    private static void printChromo(Chromosome c) {
        String line;

        Debugger.enableTrace(false);

        ArrayList nodeList = c.getNodeList();
        ArrayList cells = c.getDataArray();
        ArrayList destinations;

        for (int i = 0; i < cells.size(); i++) {
            line = "Node " + i + " -> ";
            
            destinations = (ArrayList) cells.get(i);
            for (int j = 0; j < destinations.size(); j++) {
                if (j > 0) line += ", ";
                line += nodeList.indexOf(destinations.get(j));
            }
            Debugger.enableTrace(true);
            Debugger.debug(line);
            Debugger.enableTrace(false); // mute
        }
        Debugger.enableTrace(true);
        Debugger.debug("");
    }

// ==========================================================================
    
    /**
     * Testing standard constructor with various input parameter
     */
    private static NetworkGAImp testStandardConstructor() {

        Debugger.debug(testName = "Testing standard constructor");
        Debugger.debug("============================");

        NetworkGAImp nga = null;
        double pc, pm;
        int numServers, numClients, size;

        // test 1
        pc = -1.5; pm = 0.05;
        numServers = 3; numClients = 8; size = 2;

        Debugger.debug(testName + " with pc = " + pc + ", pm = " + pm + 
                        ",\n     numServers = " + numServers + ", " +
                        "numClients = " + numClients + ", size = " + size);
        try {
            nga = new NetworkGAImp(pc, pm, numServers, numClients, size);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                            "SHOULD have been caught, since pc = " + pc +
                            "\n    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                            "pc = " + pc + " violates the precondition");
        }
        Debugger.debug("");

        // test 2
        pc = 1.5; pm = 0.05;
        numServers = 3; numClients = 8; size = 2;

        Debugger.debug(testName + " with pc = " + pc + ", pm = " + pm + 
                        ",\n     numServers = " + numServers + ", " +
                        "numClients = " + numClients + ", size = " + size);
        try {
            nga = new NetworkGAImp(pc, pm, numServers, numClients, size);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                            "SHOULD have been caught, since pc = " + pc +
                            "\n    violates the precondition");            
        }
        catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                            "pc = " + pc + " violates the precondition");
        }
        Debugger.debug("");

        // test 3
        pc = 0.3; pm = -1.5;
        numServers = 3; numClients = 8; size = 2;

        Debugger.debug(testName + " with pc = " + pc + ", pm = " + pm + 
                        ",\n     numServers = " + numServers + ", " +
                        "numClients = " + numClients + ", size = " + size);
        try {
            nga = new NetworkGAImp(pc, pm, numServers, numClients, size);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                            "SHOULD have been caught, since pm = " + pm +
                            "\n    violates the precondition");            
        }
        catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                            "pm = " + pm + " violates the precondition");
        }
        Debugger.debug("");

        // test 4
        pc = 0.3; pm = 1.5;
        numServers = 3; numClients = 8; size = 2;

        Debugger.debug(testName + " with pc = " + pc + ", pm = " + pm + 
                        ",\n     numServers = " + numServers + ", " +
                        "numClients = " + numClients + ", size = " + size);
        try {
            nga = new NetworkGAImp(pc, pm, numServers, numClients, size);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                            "SHOULD have been caught, since pm = " + pm +
                            "\n    violates the precondition");            
        }
        catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                            "pm = " + pm + " violates the precondition");
        }
        Debugger.debug("");

        // test 5
        pc = 0.3; pm = 0.05;
        numServers = -3; numClients = -8; size = 2;

        Debugger.debug(testName + " with pc = " + pc + ", pm = " + pm + 
                        ",\n     numServers = " + numServers + ", " +
                        "numClients = " + numClients + ", size = " + size);
        try {
            nga = new NetworkGAImp(pc, pm, numServers, numClients, size);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD have been caught, since numServers = " + 
                    numServers + " and numClients = " + numClients + 
                    "\n    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                    "numServers = " + numServers + " and numClients = " +
                    numClients + "\n    violates the precondition");
        }
        Debugger.debug("");

        // test 6
        pc = 0.3; pm = 0.05;
        numServers = -3; numClients = 8; size = 2;

        Debugger.debug(testName + " with pc = " + pc + ", pm = " + pm + 
                        ",\n     numServers = " + numServers + ", " +
                        "numClients = " + numClients + ", size = " + size);
        try {
            nga = new NetworkGAImp(pc, pm, numServers, numClients, size);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD have been caught, since numServers = " + 
                    numServers + "\n    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                    "numServers = " + numServers + "\n    violates the " +
                    "precondition");
        }
        Debugger.debug("");

        // test 7
        pc = 0.3; pm = 0.05;
        numServers = 3; numClients = -8; size = 2;

        Debugger.debug(testName + " with pc = " + pc + ", pm = " + pm + 
                        ",\n     numServers = " + numServers + ", " +
                        "numClients = " + numClients + ", size = " + size);
        try {
            nga = new NetworkGAImp(pc, pm, numServers, numClients, size);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD have been caught, since numClients = " + 
                    numClients + "\n    violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                    "numClients = " + numClients + "\n    violates the " +
                    "precondition");
        }
        Debugger.debug("");

        // test 8
        pc = 0.3; pm = 0.05;
        numServers = 3; numClients = 8; size = 0;

        Debugger.debug(testName + " with pc = " + pc + ", pm = " + pm + 
                        ",\n     numServers = " + numServers + ", " +
                        "numClients = " + numClients + ", size = " + size);
        try {
            nga = new NetworkGAImp(pc, pm, numServers, numClients, size);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD have been caught, since size = " + size +
                    "\n    violates the precondition");        
        }
        catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                    "size = " + size + "\n    violates the precondition");
        }
        Debugger.debug("");
        
        // test 9
        pc = 0.3; pm = 0.05;
        numServers = 3; numClients = 8; size = 2;

        Debugger.debug(testName + " with pc = " + pc + ", pm = " + pm + 
                        ",\n     numServers = " + numServers + ", " +
                        "numClients = " + numClients + ", size = " + size);
        try {
            Debugger.enableTrace(false);
            nga = new NetworkGAImp(pc, pm, numServers, numClients, size);
            Debugger.enableTrace(true);
            Debugger.debug("    NetWorkGAImp constructed successfully. " +
                    "(Exception not caught as expected,\n    since " + 
                    "pc = " + pc + ", pm = " + pm + ", numServers = " + 
                    numServers + ", " + "numClients = " + numClients + 
                    ", size = " + size + " satisfying the precondition)");
        } catch (PreconditionException pe) {
            Debugger.enableTrace(true);
            Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                    "SHOULD NOT have been caught, since " + "pc = " + pc +
                    ", pm = " + pm + ", numServers = " + numServers + ", " + 
                    "numClients = " + numClients + ", size = " + size + 
                    " satisfying the precondition)");
        }
        Debugger.debug("");
        
        // print current population size
        if (nga.getPopSize() != size) 
            Debugger.printErr(testName + ":\n    ERROR!!! Population in " +
                                "NetworkGAImp should have size = " + size);

        SetOfChromosomes pop = nga.getCurPop();

        for (int i = 0; i < pop.getPopSize(); i++) {
            Debugger.debug("Printing chromosome #" + i + " from population");
            printChromo(pop.getChromo(i));
            Debugger.debug("");
        }      

        Debugger.debug("");
        Debugger.debug("Testing standard constructor Completed");
        Debugger.debug("======================================");

        return nga;
    }

    /**
     * Create predetermined chromosomes for evolutionary functions
     */
    private static Chromosome[] createChromos() {
        int numServers = 3, numClients = 8;
        Chromosome[] results;
        ArrayList nodeList = genNodeList();
        ArrayList tempData, tempCell;

        // specify the edges in the chromosomes
        int[][] chrom1Edges = { {0,2}, {0,3}, {1,7}, {2,1}, {2,5}, 
                         {2,7}, {3,2}, {3,10}, {4,2}, {4,3}, {6,2}, {6,5},
                         {7,6}, {8,3}, {9,0}, {9,3}, {9,1}, {1,0} };

        int[][] chrom2Edges = { {7,3}, {1,7}, {2,6}, {8,1}, {1,9}, 
                         {1,0}, {3,8}, {4,2}, {7,2}, {3,10}, {3,0}, {3,9},
                         {3,4}, {2,5}, {3,2} };

        int[][] chrom3Edges = { {0,3}, {1,2}, {2,1}, {2,3}, {3,2},
                         {4,2}, {5,2}, {6,2}, {6,7}, {7,1}, {8,3}, {9,3},
                         {10,3}, {3,0} };

        int[][] chrom4Edges = { {0,1}, {1,2}, {2,3}, {2,5}, {3,4},
                         {4,5}, {5,6}, {6,7}, {7,1}, {7,8}, {8,9}, {9,0},
                         {9,1}, {9,10}, {10,3}, {10,4} };        

        int[][] chrom5Edges = { {0,1}, {0,3}, {1,7}, {2,3}, {2,5}, 
                         {2,6}, {3,0}, {3,2}, {3,9}, {4,5}, {4,10}, {5,4},
                         {6,2}, {6,7}, {7,1}, {7,6}, {8,9}, {8,10}, {9,3},
                         {9,8}, {10,4}, {10, 8}, {1,0}, {5,2} };

        int[][] chrom6Edges = { {0,9}, {1,9}, {2,3}, {2,7}, {3,0}, 
                         {3,2}, {4,5}, {5,2}, {5,6}, {6,1}, {7,3}, {8,10},
                         {9,8}, {10,4} };        
        
        // Array of edgelists
        int[][][] allEdges = new int[][][]{ chrom1Edges, chrom2Edges,
                  chrom3Edges, chrom4Edges, chrom5Edges, chrom6Edges };

        Debugger.enableTrace(false);
        
        results = new Chromosome[allEdges.length];
        // for each chromosome
        for (int i = 0; i < allEdges.length; i++) {
            // create a new list for storing adjacency lists
            tempData = new ArrayList();
            // add list for storign adacent nodes
            for (int j = 0; j < nodeList.size(); j++)
                tempData.add(new ArrayList());
            
            // for every pair (a, b) add destination node b under a.
            for (int j = 0; j < allEdges[i].length; j++) {
                tempCell = (ArrayList) tempData.get(allEdges[i][j][0]);
                tempCell.add(nodeList.get(allEdges[i][j][1]));
            }        
            // create a chromosome using created data
            results[i] = new Chromosome(tempData, nodeList, 
                                        numServers, numClients);
        }

        Debugger.enableTrace(true);
        // for (int i = 0; i < results.length; i++) printChromo(results[i]);        
        return results;
    }

    /**
     * Testing crossover on chromosomes that we knew already
     */
    private static void testCrossover(Chromosome[] chromos) {
        Debugger.debug(testName = "Testing Crossover");
        Debugger.debug("=================");

        NetworkGAImp nga = new NetworkGAImp();
  
        Chromosome empty = new Chromosome();
        
        // Testing with chromosomes with different nodeLists
        Debugger.debug(testName + " with chromosomes " +
                                                "with diffrent nodeLists");
        try {
            nga.crossover(chromos[0], empty);
            Debugger.printErr(testName + ":\n    Exception SHOULD have " +
                            "been caught, since the chromosomes use " +
                            "different node lists");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected, since the "+
                "chromosomes use different node lists");            
        }
        Debugger.debug("");
        
        // Testing with chromosomes with different nodeLists
        Debugger.debug(testName + " with chromosomes " +
                                                "with diffrent nodeLists");
        try {
            nga.crossover(empty, chromos[0]);
            Debugger.printErr(testName + ":\n    Exception SHOULD have " +
                            "been caught, since the chromosomes use " +
                            "different node lists");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected, since the "+
                "chromosomes use different node lists");   
        }
        Debugger.debug("");
        
        // Testing with chromosomes with empty nodeLists
        Debugger.debug(testName + " with chromosomes " +
                                            "with same but empty nodeLists");
        try {
            nga.crossover(empty, empty);
            Debugger.printErr(testName + ":\n    Exception SHOULD have " +
                            "been caught, since the chromosomes use " +
                            "empty node lists");            
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected, since the "+
                "chromosomes use empty node lists");   
        }
        Debugger.debug("");
        
        
        // create an empty network chromosome based on test nodeList
        ArrayList tempData = new ArrayList();
        for (int i = 0; i < (chromos[0].getNodeList()).size(); i++)
                tempData.add(new ArrayList());
        
        empty = new Chromosome(tempData,
                                            chromos[0].getNodeList(), 3, 8);
        Debugger.debug("");
        
        // ------------------------------------------------------------------
        
        Debugger.enableTrace(false);
        String[] details = { " with 2 empty chromosomes",
                             " with 1 empty and 1 populated chromosomes",
                             " with 1 populated and 1 empty chromosomes",
                             " with 2 populated chromosomes" };
        Chromosome[][] inputChromos = { {clone(empty), clone(empty) },
                                  { clone(empty),      clone(chromos[1]) },
                                  { clone(chromos[0]), clone(empty)      },
                                  { clone(chromos[0]), clone(chromos[1]) } };
        Debugger.enableTrace(true);
        
        // ------------------------------------------------------------------
        Debugger.debug("Testing with chromosomes with same nodeLists");
        
        for (int i = 0; i < details.length; i++) {
            Debugger.debug(testName + details[i]);
            Debugger.debug("Before cross over");
            Debugger.debug("Chromosome 1:");
            printChromo(inputChromos[i][0]);
            Debugger.debug("Chromosome 2:");
            printChromo(inputChromos[i][1]);
            
            try {
                Debugger.enableTrace(false);
                nga.crossover(inputChromos[i][0], 
                                           inputChromos[i][1]);
                Debugger.enableTrace(true);                                           
                Debugger.debug("    Exception not caught as expected, " +
                        "since the chromosomes share the same node lists");   
            
                Debugger.debug("After cross over");
                Debugger.debug("Chromosome 1:");
                printChromo(inputChromos[i][0]);
                Debugger.debug("Chromosome 2:");
                printChromo(inputChromos[i][1]);
            } catch (PreconditionException pe) {
                Debugger.enableTrace(true);                                           
                Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                        "SHOULD NOT have been caught, since the " +
                        "\n   chromosomes share the same node list");            
            }
        }

        Debugger.debug("Testing Crossover Completed");
        Debugger.debug("===========================");
    }

    /**
     * Method to clone chromosomes
     */
    private static Chromosome clone(Chromosome c) {
        ArrayList originalData = c.getDataArray();
        ArrayList newData = new ArrayList();
        
        for (int i = 0; i < originalData.size(); i++)
            newData.add( ((ArrayList)originalData.get(i)).clone() );
            
        return new Chromosome(newData, c.getNodeList(), 
                              c.getNumServers(), c.getNumClients() );
        
    }

    /**
     * Testing mutation on chromosomes that we knew already
     */
    private static void testMutation(Chromosome[] chromos) {
        Debugger.debug(testName = "Testing Mutation");
        Debugger.debug("================");

        NetworkGAImp nga = new NetworkGAImp();
        
        Chromosome empty = new Chromosome();
        
        // Testing with chromosome with empty nodeList
        Debugger.debug(testName + " with chromosome with empty nodeLists");
        try {
            nga.mutate(empty);
            Debugger.printErr(testName + ":\n    Exception SHOULD have " +
                            "been caught, since the chromosomes use " +
                            "empty node lists");            
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected, since the "+
                "chromosomes use empty node lists");   
        }
        Debugger.debug("");
        
        
        // create an empty network chromosome based on test nodeList
        ArrayList tempData = new ArrayList();
        for (int i = 0; i < (chromos[0].getNodeList()).size(); i++)
                tempData.add(new ArrayList());
        
        empty = new Chromosome(tempData, chromos[0].getNodeList(), 3, 8);
        Debugger.debug("");

        
        // ------------------------------------------------------------------
        
        Debugger.enableTrace(false);
        String[] details = { " with an empty chromosome",
                             " with an empty chromosome",
                             " with a populated chromosome", 
                             " with a populated chromosome" };
        Chromosome[] inputChromos = { clone(empty),      clone(empty),
                                      clone(chromos[0]), clone(chromos[0]) };
        Debugger.enableTrace(true);
        
        // ------------------------------------------------------------------
        
        Debugger.debug("Testing with chromosomes with same nodeLists");
        
        for (int i = 0; i < details.length; i++) {
            Debugger.debug(testName + details[i]);
            Debugger.debug("Before mutation:");
            printChromo(inputChromos[i]);
            
            try {
                Debugger.enableTrace(false);
                nga.mutate(inputChromos[i]);
                Debugger.enableTrace(true);
                Debugger.debug("    Exception not caught as expected, " +
                        "since the node list is not empty");   
            
                Debugger.debug("After mutation");
                printChromo(inputChromos[i]);
            } catch (PreconditionException pe) {
                Debugger.enableTrace(true);
                Debugger.printErr(testName + ":\n    ERROR!!! Exception " +
                        "SHOULD NOT have been caught, since the " +
                        "\n    node list is not empty");            
            }
        }

        Debugger.debug("Testing Mutation Completed");
        Debugger.debug("==========================");
    }

    /**
     * Tests steady-state evolution by evolving a known population many times
     */
    private static void testSteadyStateEvolve(SetOfChromosomes set) {
        Debugger.debug(testName = "Testing steadyStateEvolve");
        Debugger.debug("=========================");

        // initalise a function
        Function func = new DegreeSeparation(true);
        // prepare class for evolution
        NetworkGAImp nga = new NetworkGAImp();
        nga.setPrCrossover(PROB_CROSSOVER);
        nga.setPrMutation(PROB_MUTATION);
        EdgeCostMatrix.reset();
        nga.setCurPop(set);
        
        Debugger.enableTrace(false);
        func.map(set);    // apply dijkstra function to set of chromosomes
        Debugger.enableTrace(true);
        
        Debugger.debug("Before Evolution:");
        printPop(set);

        int numGensToEvolve = 4;
        boolean[] updatePair = new boolean[] {false, true};

        // store fitness in array for comparison
        double[] fitnessArr1 = new double[set.getPopSize()];
        for (int i = 0; i < set.getPopSize(); i++) 
            fitnessArr1[i] = set.getChromo(i).getFitness();

        double[] fitnessArr2;

        // for each generation                
        for (int i = 0; i < numGensToEvolve; i++) {
            Debugger.debug("Evolving with pair = " + updatePair[i%2]);
            Debugger.enableTrace(false);
            
            nga.steadyStateEvolve(func, updatePair[i%2]);
            
            fitnessArr2 = new double[set.getPopSize()];
            for (int j = 0; j < nga.getPopSize(); j++) 
                fitnessArr2[j] = nga.getCurPop().getChromo(j).getFitness();
            Debugger.enableTrace(true);
            Debugger.debug("After Generation: " + i);
            compareArrays(fitnessArr1, fitnessArr2);
            fitnessArr1 = fitnessArr2;
        }

        Debugger.debug("Testing steadyStateEvolve Completed");
        Debugger.debug("===================================");
    }

    /**
     * Prints the fitness of each chromosome in a population
     */
    private static void printPop(SetOfChromosomes set) {
        Debugger.debug("Printing all chromosomes in the population...\n");
        Chromosome chrom;
        for (int i = 0; i < set.getPopSize(); i++) {   
            chrom = set.getChromo(i);
            Debugger.debug("Chromosome " + i + " with fitness = " + 
                                chrom.getFitness());
            printChromo(chrom);
        }
        Debugger.debug("Done.");
    }

    /**
     * Lists two arrays in columns and identifies the differences.
     */
    private static void compareArrays(double[] a, double[] b) {
        String line = "\n";
        for (int i = 0; i < a.length; i++) {
            line += "!!! " + i + "\t" + a[i];
            if (a[i] != b[i]) line += "\t->\t";
            else              line += "\t  \t";
            line += "" + b[i] + "\n";
        }
        Debugger.debug(line +"!!! =============\n");
    }

    /**
     * Tests classical GA evolution by evolving a known population many times
     */
    private static void testClassicalGAEvolve(SetOfChromosomes set) {
        Debugger.debug(testName = "Testing evolve");
        Debugger.debug("==============");

        // initalise a function
        Function func = new DegreeSeparation(true);
        // prepare class for evolution
        NetworkGAImp nga = new NetworkGAImp();
        nga.setPrCrossover(PROB_CROSSOVER);
        nga.setPrMutation(PROB_MUTATION);
        EdgeCostMatrix.reset();
        nga.setCurPop(set);
        
        Debugger.debug("Mapping function onto population...\n");
        Debugger.enableTrace(false);
        func.map(set);    // apply dijkstra function to set of chromosomes
        Debugger.enableTrace(true);
        Debugger.debug("Sorting population...\n");
        set.sort();

        Debugger.debug("Before Evolution:");
        printPop(set);

        int numGensToEvolve = 4;
        boolean[] updatePair = new boolean[] {false, true};

        // store fitness in array for comparison
        double[] fitnessArr1 = new double[set.getPopSize()];
        for (int i = 0; i < set.getPopSize(); i++) 
            fitnessArr1[i] = set.getChromo(i).getFitness();

        double[] fitnessArr2;
        
        // for each generation
        for (int i = 1; i <= numGensToEvolve; i++) {
            Debugger.enableTrace(false);
            nga.evolve(func);
            fitnessArr2 = new double[set.getPopSize()];
            Debugger.enableTrace(false);
            for (int j = 0; j < nga.getPopSize(); j++) 
                fitnessArr2[j] = nga.getCurPop().getChromo(j).getFitness();
            Debugger.enableTrace(true);
            Debugger.debug("After Generation:" + i);
            compareArrays(fitnessArr1, fitnessArr2);
            fitnessArr1 = fitnessArr2;
        }

        Debugger.debug("Testing evolve Completed");
        Debugger.debug("========================");
        
    }
}
