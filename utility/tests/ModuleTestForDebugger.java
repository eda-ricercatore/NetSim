// importing packages
import utility.*;

/**
 * This the test suite for the utility class Debugger. Note, this is not a
 * standard style for testing, since Debugger class contains all the
 * foundamental methods for trace/error message printing.<p> The test
 * sequence is divided into two phases, the first phase demonstrates
 * funtionality of each methods (including the constructor), and the second 
 * phase will focus on the I/O aspects of output files.<p> Since the methods 
 * divert output streams to different files, this test suite may generate 
 * several files on the file systems.<p> When running the test suite, please
 * ensure the files traces.txt, normal.txt and error.txt do not exist in the
 * current directory
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.1-andy
 * @since   0.3.1
 */
public class ModuleTestForDebugger {
    // instance variable to store info regarding current test
    private static String testName;
    
    // Default constructor
    /**
     * Should not instantate a test class.
     */
    public ModuleTestForDebugger() {
    	throw new AssertionException("Don't instantiate a test class.");
    }
    
    /**
     * The main method for this class, which will execute the two phases of
     * testing
     */
    public static void main(String[] args) {
        testPhase1();
        testPhase2();
    }
    
    /**
     * This is the first part of the test suite. Conventionally, a testsuite
     * should have already piped results to relevant files. However, since we
     * are testing for functionality of the tool here, we follow a different
     * route. Firstly, an attempt was made to pipe all output to a single. At
     * the same time, the constructor and simple methods were tested. Then
     * the same tests were executed, where we pipe results to two separate
     * files.
     * @param none
     * @return nothing
     */
    public static void testPhase1() {
        // redirects all output streams to the file "traces.txt"
        Debugger.pipeTrace("DebuggerTraces.txt");
        // enable trace printing
        Debugger.enableTrace(true);
        /**
         * Modified by Andy 10/04/05: to add header for output file for
         * test results.
         */
        Debugger.debug("\n=============================\n"+
                         "filename: DebuggerTraces.txt\n" +
                         "=============================");
        Debugger.debug("debug: Phase 1 testing commenced");
        Debugger.printErr("printErr: Phase 1 testing commenced");
        testConstructor();
        testMethods();
        Debugger.debug("debug: Phase 1 Ends");
        Debugger.printErr("printErr: Phase 1 Ends");
        // Modified by Andy 10/04/05: to emphasise end of test
        Debugger.debug("==============================================");
        Debugger.debug("Phase 1 of Module Test for Assertion Completed");
        Debugger.debug("==============================================");

        /**
         * Redirects System.out to normal.txt and System.err to error.txt.
         * The latter text file highlights the errors of the program.
         */
        Debugger.enableTrace(true);
        Debugger.pipeResult("DebuggerNormal.txt", "DebuggerError.txt");
        /**
         * Modified by Andy 10/04/05: to add header for output files for
         * test results.
         */
        Debugger.debug("\n=============================\n"+
                         "filename: DebuggerNormal.txt\n" +
                         "=============================");
        Debugger.debug("Phase 2 of Module Test for utility.Debugger:\n");
        
        Debugger.printErr("\n============================\n"+
                            "filename: DebuggerError.txt\n" +
                            "============================");
        Debugger.printErr("Phase 2 of Module Test for utility.Debugger:\n");
        
        testConstructor();
        testMethods();
        
        // Modified by Andy 10/04/05: to emphasise end of test
        Debugger.debug("==============================================");
        Debugger.debug("Phase 2 of Module Test for Assertion Completed");
        Debugger.debug("==============================================");
        Debugger.printErr("==============================================");
        Debugger.printErr("Phase 2 of Module Test for Assertion Completed");
        Debugger.printErr("==============================================");
    }
    
    /**
     * Tests the constructor of <code>Debugger</code>. We should not be able
     * to instantiate the <code>Debugger</code> class. Thus a correct result
     * is recorded when we catch an <code>AssertionException</code>.
     * @param none
     * @return nothing
     */
    public static void testConstructor() {
        testName = "Testing to instantiate Debuggers";
        try {
            Debugger testDebugger = new Debugger();
            Debugger.printErr(testName + ":" + " Debugger instantiable.");
        } catch (AssertionException ae) {
            Debugger.debug(testName + ":" + " Debugger not instantiable.");
        }
    }
    
    
    /**
     * Tests the methods when trace is diabled. No messages should be printed
     * if trace printing is disabled. 
     * @param none
     * @return nothing
     */
    public static void testMethods() {
        testName = "Trace Printing Disabled";
        Debugger.debug("Traces turned off, no message shall be " +
                       "printed until re-enabled.");
        Debugger.enableTrace(false);
        Debugger.printErr(testName + ": This should not be printed");
        Debugger.enableTrace(true);
        Debugger.debug("Traces turned on, message printing resumed.");
    }

    /**
     * This is the second part of the test, where abnormal input was tested.
     * This includes, trying to pipe to the same files with the method
     * <code>pipeResult()</code> and trying to pipe to an existing file. <p>
     * Subject to the preconditions, all of the above mentioned cannot occur,
     * thus positive result will be recorded if we catch some
     * <code>PreconditionException</code>
     * @param none
     * @return nothing
     */
    public static void testPhase2() {
        Debugger.debug("debug: Phase 2 testing commenced");
        
        testName = "Piping to same file";
        try {
            Debugger.pipeResult("phase2.txt", "phase2.txt");
            Debugger.printErr(testName + ": permitted");
        } catch (PreconditionException  pe) {
            Debugger.debug(testName + ": forbidden");   
        }
       
        testName = "Overwriting existing file";
        try {
            Debugger.pipeTrace("traces.txt");
            Debugger.printErr(testName + ": permitted");
        } catch (PreconditionException  pe) {
            Debugger.debug(testName + ": forbidden");   
        }
        
        testName = "Overwriting existing files";
        try {
            Debugger.pipeResult("normal.txt", "error.txt");
            Debugger.printErr(testName + ": permitted");
        } catch (PreconditionException  pe) {
            Debugger.debug(testName + ": forbidden");   
        }
        
        Debugger.debug("debug: Phase 2 Ends");
    }
    
}