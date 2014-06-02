// importing packages
import utility.*;
import population.*;
import population.graph.*;
import ecomp.*;
import java.util.Random;

/**
 * This the test suite for the Selection Class of the ecomp package.
 * It determines if the probabilities of selection are determined correctly,
 * Since the methods divert output streams to different files, this test suite
 * may generate several files on the file systems.<p>
 * When running the test suite, please ensure the files traces.txt,
 * normal.txt and error.txt do not exist in the current directory
 *
 * @author  Zhiyang Ong and Andy Hao-Wei Lo
 * @version 0.3.6
 */
public class TestForSelection {
    // instance variable to store info regarding current test
    private static String testName;
    
    // size of the population
    public static int size;
    // Container for the population of Chromosomes
    public static SetOfChromosomes chromos;
    // Create an instance of Selection so that a Chromosome can be selected
    public static Selection sel;
    // Assign Probabilities of Selection to each Chromosome
    public static double[] arr;
    
     // ---------------------------------------------------------------------
     
    // Default constructor
    /**
     * Should not instantate a test class.
     */
    public TestForSelection() {
//      throw new AssertionException("Don't instantiate a test class.");
    }
    
    // --------------------------------------------------------------------
    
    /**
     * The main method for this class, which will execute the two phases of
     * testing
     */
    public static void main(String[] args) {
        Debugger.pipeResult("SelectionNormal.txt", "SelectionError.txt");
        Debugger.enableTrace(true);
        /**
         * Modified by Andy 07/04/05: to add header for output files for
         * test results.
         */
        Debugger.debug("=============================\n"+
                       "filename: SelectionNormal.txt\n" +
                       "=============================");
        Debugger.debug("Unit Test for Selection Class (ecomp package):\n");
        
        Debugger.printErr("============================\n"+
                          "filename: SelectionError.txt\n" +
                          "============================");
        Debugger.printErr("Unit Test for Selection Class (ecomp package):\n");
        
        /**
         * This implementation of selection uses the rank selection method.
         * Since rank selection is computationally intensive and does not
         * scale well for large number of chromosomes. In addition, the
         * probability of the fittest chromosome to be chosen was derived 
         * from an arbitrary function. This function does not provide enough
         * selective pressure, because as the population size increases, the
         * probabilty allocated to the chromosomes in the population are too
         * close.
         *
         * We will be shifting to steady-state evolution, which will use
         * tournament selection, comparing the fitness amongst n chromosomes,
         * and use the "closest fitness" replacement algorithm. 
         */
//        testConstructor();
//        Debugger.debug("");
//        
//        size = 100;
//        chromos = new SetOfChromosomes();
//        for(int i=0; i<size; i++) {
//            chromos.addChromo(new Chromosome());
//        }
//        sel = new Selection(chromos);
//        arr = sel.getPrSelection();
//        
//        testSetPrSelectionCheckCumulative();
//        Debugger.debug("");
//        testGetPrSelection();
//        Debugger.debug("");
//        testGetFirstProb();       
//        Debugger.debug("");
//        testSelectPairChromo();
//        Debugger.debug("");
        
        // Modified by Andy 07/04/05: to emphisise end of test
        Debugger.debug("===================================");
        Debugger.debug("Module Test for Selection Completed");
        Debugger.debug("===================================");
        Debugger.printErr("===================================");
        Debugger.printErr("Module Test for Selection Completed");
        Debugger.printErr("===================================");
    }
    
    // ---------------------------------------------------------------------
    
    /**
     * Tests the constructor of Selection class.
     * @param none
     * @return nothing
     */
    public static void testConstructor() {
        testName = "Testing to instantiate Selection";
        Selection sel = new Selection();
        double[] arr = sel.getPrSelection();
        Assertion.asrt(arr == null,
            "Default Selection constructor works",
            "Default Selection constructor has failed");
        int size = 100;
        SetOfChromosomes chromos = new SetOfChromosomes();
        for(int i=0; i<size; i++) {
            chromos.addChromo(new Chromosome());
        }
        System.out.println("%^&*pop size "+chromos.getPopSize());
        sel = new Selection(chromos);
        arr = sel.getPrSelection();
        Assertion.asrt(arr.length == size,
            "Standard constructor for Selection works",
            "Standard constructor for Selection did not create a double"
            +"array of the correct size to store Pr(selection)(s) for the "
            +"population of chromosomes");
    }
    
    
    /**
     * Tests the method setPrSelection
      * @param none
     * @return nothing
     */
    public static void testSetPrSelectionCheckCumulative() {
        Debugger.debug("Tests the method setPrSelection");
        for(int i=0; i<arr.length; i++) {
            Debugger.debug("Pr(selection,"+i+"): "+arr[i]);
            if(i < (arr.length -1)) {
                Assertion.asrt(arr[i]<arr[i+1],
                    "list of prSelection for the ranked fitness"
                    +" of Chromosomes is in ascending order",
                    "list of prSelection for the ranked fitness"
                    +" of Chromosomes is NOT in ascending order");
            }
            Assertion.asrt((arr[0] != 0) && (arr[arr.length -1] == 1),
                "prSelection for the fittest and least fit Chromosomes"
                +" are assigned correctly",
                "prSelection for the fittest and least fit Chromosomes"
                +" should be assigned to non-zero and one respectively");
        }
    }
     
     /**
     * Tests the method getPrSelection
      * @param none
     * @return nothing
     */
    public static void testGetPrSelection() {
        Debugger.debug("Tests the method getPrSelection");
        Random rand = new Random();
        int j = rand.nextInt(size);
        Assertion.asrt(arr[j] != 0,
            "Pr(selection) for the Chromosomes are assigned correctly",
            "prSelection for the Chromosomes are NOT properly assigned."
            +" There exist a non-zero value for a randomly selected prSelection."
            +" prSelection at i = "+j+" is "+arr[j]);
    }
     
     /**
     * Tests the method getFirstProb
      * @param none
     * @return nothing
     */
    public static void testGetFirstProb() {
        Debugger.debug("Tests the method getFirstProb");
        // Pr(selection) = 0.02915781944436709 for size=100
        Assertion.asrt(arr[0]==0.02915781944436709,
            "Pr(selection) for the fittest Chromosome has been calculated"
            +" correctly","Pr(selection) for the fittest Chromosome has "
            +"been calculated WRONGLY");
    }
     
    /**
     * Tests the method selectPairChromo
     * @param none
     * @return nothing
     */
    public static void testSelectPairChromo() {
        Debugger.debug("Tests the method selectPairChromo");
        Chromosome[] chromoPair = sel.selectPairChromo();
        Assertion.asrt(chromoPair.length == 2,
            "Selected pair of chromosomes has length of 2",
            "Selected PAIR of chromosomes does NOT have a length of 2");
        Assertion.asrt((chromoPair[0].getPrSelection() > 0)
            && (chromoPair[0].getPrSelection() <=1),
            "The first of the selected chromosomes has its fitness properly assigned",
            "The first of the selected chromosomes has its fitness IMPROPERLY assigned");
        Assertion.asrt((chromoPair[1].getPrSelection() > 0)
            && (chromoPair[1].getPrSelection() <=1),
            "The second of the selected chromosomes has its fitness properly assigned",
            "The second of the selected chromosomes has its fitness IMPROPERLY assigned");
    }

}