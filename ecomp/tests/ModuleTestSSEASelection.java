// importing packages
import utility.*;
import population.*;
import ecomp.*;
import java.util.ArrayList;

/**
 * Tests the SSEASelection class, which contains methods for selection by
 * tournament selection, and inserting/replacing chromosomes using closest-
 * fitness replacement.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.7
 */
public class ModuleTestSSEASelection {
    // instance variable to store info regarding current test
    private static String testName;
    // Container for the population of Chromosomes
    public static SetOfChromosomes population;
    // Array of predetermined fitness values to be assigned to chromosomes
    public static double[] fitnessArr = new double[] 
                                            { 52.8, 24.2, 96.7, 25.4, 44.0,
                                              43.3, 21.9, 62.9, 38.1, 11.4,
                                               0.1, 76.1, 99.1, 14.1, 99.9,
                                              17.1, 41.2, 21.3, 83.1, 28.3 };
    
    // ---------------------------------------------------------------------
     
    // Default constructor
    /**
     * Should not instantate a test class.
     */
    public ModuleTestSSEASelection() {
        Debugger.printErr("Don't instantiate a test class: " + 
                            "ModuleTestSSEASelection");
    }
    
    // --------------------------------------------------------------------
    
    /**
     * The main method for this class, which will execute the two phases of
     * testing
     */
    public static void main(String[] args) {
        Debugger.pipeResult("SSEASelectionNormal.txt",
                            "SSEASelectionError.txt");
        Debugger.enableTrace(true);
        Debugger.debug("=================================\n"+
                       "filename: SSEASelectionNormal.txt\n" +
                       "=================================");
        Debugger.debug("Module Test for ecomp.SSEASelection:\n");
        
        Debugger.printErr("================================\n"+
                          "filename: SSEASelectionError.txt\n" +
                          "================================");
        Debugger.printErr("Module Test for ecomp.SSEASelection:\n");

        population = createPop();

        testConstructor();
        Debugger.debug("");

        testSelection();
        Debugger.debug("");
        
        testInsert();
        Debugger.debug("");

        Debugger.debug("=======================================");
        Debugger.debug("Module Test for SSEASelection Completed");
        Debugger.debug("=======================================");
        Debugger.printErr("=======================================");
        Debugger.printErr("Module Test for SSEASelection Completed");
        Debugger.printErr("=======================================");
    }
    
    // ---------------------------------------------------------------------
    
    /**
     * Creates a dummy population for testing, using the predetermined values
     * for fitness
     */
    private static SetOfChromosomes createPop() {
        Debugger.debug("Generating Test Population...");
        SetOfChromosomes testPop = new SetOfChromosomes();
        Chromosome temp;
        
        for (int i = 0; i < fitnessArr.length; i++) {
            temp = new Chromosome();
            temp.setFitness(fitnessArr[i]);
            testPop.addChromo(temp);
        }
        
        Debugger.debug("Checking the fitness/cost value of the chromosomes:");
        for (int i = 0; i < testPop.getPopSize(); i++) {
            Debugger.debug("Chromosome " + i + ": " + 
                                (testPop.getChromo(i)).getFitness());
        }
        Debugger.debug("");
        return testPop;
    }  
    
    /**
     * Tests the constructor of Selection class.
     */
    private static void testConstructor() {
        Debugger.debug(testName = "Testing to instantiate SSEASelection");
        SSEASelection sel = new SSEASelection();
        if(sel != null)
            Debugger.debug("    A default instance has been created");
        else
            Debugger.printErr(testName + "\n    !!!ERROR occured " +
                "when instantiating a default instance of SSEASelection");
        
        sel = new SSEASelection(population);
        if(sel != null)
            Debugger.debug("    A standard instance has been created");
        else
            Debugger.printErr(testName + "\n    !!!ERROR occured " +
                "when instantiating a standard instance of SSEASelection");
    }
    
    /**
     * Tests the tournamentSelectFrom method with various input parameters
     */
    private static void testSelection() {
        String basicTestName = "Testing tournamentSelectFrom";
                                
        Debugger.debug(basicTestName + "\n============================");

        Debugger.debug(testName = basicTestName + 
                                " with a default instance of SSEASelection");
        try {
            SSEASelection sel = new SSEASelection();
            sel.tournamentSelectFrom(2);
            Debugger.printErr(testName + ":\n    EXCEPTION SHOULD HAVE " +
                            "BEEN CAUGHT, since the population is empty.");
        } catch (AssertionException ae) {
            Debugger.debug("    Exception caught as expected since the " +
                           "population is empty");
        }
        Debugger.debug("");
        
        
        Debugger.debug(testName = basicTestName +
                                " with an empty population");
        try {
            SetOfChromosomes set = new SetOfChromosomes();
            SSEASelection sel = new SSEASelection(set);
            sel.tournamentSelectFrom(2);
            Debugger.printErr(testName + ":\n    EXCEPTION SHOULD HAVE " +
                            "BEEN CAUGHT, since the population is empty.");
        } catch (AssertionException ae) {
            Debugger.debug("    Exception caught as expected since the " +
                           "population is empty");
        }
        Debugger.debug("");
        
        
        SSEASelection sel = new SSEASelection(population);
        
        Debugger.debug(testName = basicTestName +
                                " with number of candidates n < 1");
        try {
            sel.tournamentSelectFrom(0);
            Debugger.printErr(testName + ":\n    EXCEPTION SHOULD HAVE " +
                            "BEEN CAUGHT, since n < 1 violates the " +
                            "precondition.\n    The selection requires a " +
                            "least 1 candiate. That is n > 0");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected, since n < 1 " +
                            "violates the precondition.\n    The " +
                            "selection requires at least 1 candiate. That " +
                            "is n > 0");
        }
        Debugger.debug("");
        
        
        Debugger.debug(testName = basicTestName +
                                " with number of candidates n = 1");
        try {
            Chromosome c = sel.tournamentSelectFrom(1);
            Debugger.debug("Result chromosome has fitness "+c.getFitness());
            
            Debugger.debug("    Exception not caught as expected, since " +
                            "n = 1 satisfies the precondition.\n    The " +
                            "selection requires at least 1 candiate. That " +
                            "is n > 0");
            
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    EXCEPTION SHOULD NOT HAVE " +
                            "BEEN CAUGHT, since n = 1 satisfies the " +
                            "precondition.\n    The selection requires at " +
                            "least 1 candiate. That is n > 0");
        }
        Debugger.debug("");
        
        
        Debugger.debug(testName = basicTestName +
                                " with number of candidates n = 2");
        try {
            Chromosome c = sel.tournamentSelectFrom(2);
            Debugger.debug("Result chromosome has fitness "+c.getFitness());
            
            Debugger.debug("    Exception not caught as expected, since " +
                            "n = 2 satisfies the precondition.\n    The " +
                            "selection requires at least 1 candiate. That " +
                            "is n > 0");
            
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    EXCEPTION SHOULD NOT HAVE " +
                            "BEEN CAUGHT, since n = 2 satisfies the " +
                            "precondition.\n    The selection requires at " +
                            "least 1 candiate. That is n > 0");
        }
        Debugger.debug("");    
    
    
        Debugger.debug("Completed" + basicTestName + 
                       "\n=====================================");
    }
    
    
    /**
     * Tests the insert method with various chromosomes
     */
    private static void testInsert() {
        
        String basicTestName = "Testing \"insert\"";

        Debugger.debug(basicTestName + "\n================");

        double[] tempFitness = new double[] { 34.8, 6.0, 78.0, 59.9, 15.3 };

        Debugger.debug("Preparing " + tempFitness.length + " chromosomes...");
        Chromosome[] chromArr = new Chromosome[tempFitness.length];
        for (int i = 0; i < tempFitness.length; i++) {
            Debugger.debug("    chrom" + (i+1) + ":");
            chromArr[i] = new Chromosome();    
            chromArr[i].setFitness(tempFitness[i]);
        }

        Chromosome chromToBeReplaced;
        ArrayList chromList = population.getCurPop();
        SSEASelection sel = new SSEASelection(population);
        
        // fitness differences between this chromosome and another  
        double diff;
        // stores the smallest difference
        double minDiff;
        // index of the chromosome contributing to the smallest difference
        int index;
        
        
        for (int j = 0; j < tempFitness.length; j++) {
            Debugger.debug("About to insert chrom" + (j+1) + ":");
        
            minDiff = Double.POSITIVE_INFINITY;
            index = -1;
            // every chromosome for minimum difference
            for (int i = 0; i < population.getPopSize(); i++) {
                // find the differences between "c" and another chromosome
                diff = Math.abs( (population.getChromo(i)).getFitness() -
                                tempFitness[j] ); 
                // if we find a chromosome with lesser difference, update 
                if (diff < minDiff) {
                    minDiff = diff;
                    index = i;
                }
            }        
            Debugger.debug(testName = basicTestName +
                                " with a chromosome of " + tempFitness[j]);
            Debugger.debug("Chromosome #" + index + " in the population " +
                                "will be replaced");
            chromToBeReplaced = population.getChromo(index);
            Debugger.debug("Chromosome chromToBeReplaced has fitness = " +
                                chromToBeReplaced.getFitness());
            Debugger.debug("Inserting chrom" + (j+1) + " (This should " +
                                "replace the above chromosome)");
            sel.insert(chromArr[j]);
            
            if (chromList.contains(chromToBeReplaced)) {
                Debugger.printErr("ERROR!!! chromToBeReplaced should " +
                    "no longer remain in the population");
            } else {
                Debugger.debug("chromToBeReplaced is no longer in the " +
                    "population");
            }

            if (chromList.contains(chromArr[j])) {
                Debugger.debug("chrom" + (j+1) + " is in the population");
                
                if (chromList.get(index) == chromArr[j]) {
                    Debugger.debug("chrom" + (j+1) + " is in position #" +
                        index + " in the population");
                } else {
                    Debugger.printErr("ERROR !!! chrom" + (j+1) + " is in " +
                        "position #" + chromList.indexOf(chromArr[j]) + 
                        "in the population instead of position #" + index);
                }
                
            } else {
                Debugger.printErr("ERROR!!! chrom" + (j+1) + " is not in " +
                    "the population");
            }
            Debugger.debug("");
        }
        
        Debugger.debug("");
        Debugger.debug("Completed" + basicTestName + 
                       "\n==========================");
    }
}
