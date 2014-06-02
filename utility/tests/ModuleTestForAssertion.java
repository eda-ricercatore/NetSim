// importing packages
import utility.*;

/**
 * This test suite will test for the functionality of assertion class.
 * Whenever an assertion is made, the Assertion class should respond
 * appropriately. The results of the tests will be piped to the files:
 * "AssertionNormal.txt" and "AssertionError.txt". The first of the two, will
 * store the positive results, whereas the latter will store the negative 
 * results.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.1-andy
 * @since   0.3.1
 */
public class ModuleTestForAssertion {
    // instance variable to store info regarding current test
    private static String testName;
    
    // Default constructor
    /**
     * Should not instantate a test class.
     */
    public ModuleTestForAssertion() {
    	throw new AssertionException("Don't instantiate a test class.");
    }
    
    /**
     * The main method for this class. There will be four things to tested:
     * <ul><li>the Constructor
     *     <li>asrt method
     *     <li>pre method
     *     <li>post method</ul>
     * These tests will be executed in turn.
     */
    public static void main(String[] args) {
        Debugger.pipeResult("AssertionNormal.txt", "AssertionError.txt");
        Debugger.enableTrace(true);
        
        /**
         * Modified by Andy 09/04/05: to add header for output files for
         * test results.
         */
        Debugger.debug("\n=============================\n"+
                         "filename: AssertionNormal.txt\n" +
                         "=============================");
        Debugger.debug("Module Test for utility.Assertion:\n");
        
        Debugger.printErr("\n============================\n"+
                            "filename: AssertionError.txt\n" +
                            "============================");
        Debugger.printErr("Module Test for utility.Assertion:\n");
        
        testConstructor();
        Debugger.debug("");
        testAsrt();
        Debugger.debug("");
        testPre();
        Debugger.debug("");
        testPost();
        
        Debugger.enableTrace(true);
        // Modified by Andy 09/04/05: to emphasise end of test
        Debugger.debug("===================================");
        Debugger.debug("Module Test for Assertion Completed");
        Debugger.debug("===================================");
        Debugger.printErr("===================================");
        Debugger.printErr("Module Test for Assertion Completed");
        Debugger.printErr("===================================");
    }
    
    /**
     * Tests the constructor of <code>Assertion</code>. <code>Assertion</code>
     * class should not be instantiated, so if instantiated, the constructor
     * will complain with an <code>AssertionException</code>, which we shall 
     * catch.
     * @param   none
     * @return  none
     */
    public static void testConstructor() {
        testName = "Testing to instantiate Assertion";
        try {
            Assertion testDebugger = new Assertion();
            Debugger.printErr(testName + ":\n    Assertion instantiable.");
        } catch (AssertionException ae) {
            Debugger.debug(testName + ":\n    Assertion not instantiable.");
        }
    }
    
    /**
     * Tests the method <code>asrt</code>. This is done by parsing an
     * executable boolean expression, and observing appropriate outputs.
     * @param   none
     * @return  nothing
     */
    public static void testAsrt() {
        testName = "Testing asrt for \"temp > 0\", where temp = 10";
        int temp = 10;
        try {
            Assertion.asrt( (temp > 0), 
                            testName + ":\n    Condition is satisfied",
                            testName + ":\n    Condition not statisfied" );
            Debugger.debug( "    AssertionException not issued as " +
                            "expected, since assertion is satified");
        } catch (AssertionException ae) {
            Debugger.printErr( testName + ":\n    AssertionException " +
                                "should not be thrown, since assertion is " +
                                "satisfied");
        }
        
        testName = "Testing asrt for \"temp > 0\", where temp = -10";
        temp = -10;
        try {
            Assertion.asrt( (temp > 0), 
                            testName + ":\n    Condition is satisfied",
                            testName + ":\n    Condition not statisfied" );
            Debugger.printErr( "    AssertionException should have been " +
                                "thrown, since assertion is not satisfied");
        } catch (AssertionException ae) {
            Debugger.debug(testName + ":\n    AssertionException caught " +
                            "as expected, since assertion is not satified");
        }
    }
    
    /**
     * Tests the method <code>pre</code>. This is done by parsing an
     * executable boolean expression, and observing appropriate outputs.
     * @param   none
     * @return  nothing
     */
    public static void testPre() {
        testName = "Testing pre for \"temp > 0\", where temp = 10";
        int temp = 10;
        try {
            Assertion.pre( (temp > 0), 
                            testName + ":\n    Condition is satisfied",
                            testName + ":\n    Condition not statisfied" );
            Debugger.debug( "    PreconditionException not issued as " +
                            "expected, since precondition is satified");
        } catch (PreconditionException pe) {
            Debugger.printErr( testName + ":\n    PreconditionException " +
                                "should not be thrown, since precondition " +
                                "is satisfied");
        }
        
        testName = "Testing pre for \"temp > 0\", where temp = -10";
        temp = -10;
        try {
            Assertion.pre( (temp > 0), 
                            testName + ":\n    Condition is satisfied",
                            testName + ":\n    Condition not statisfied" );
            Debugger.printErr( "    PreconditionException should have " +
                                "been thrown, since precondition is not " +
                                "satisfied");
        } catch (PreconditionException pe) {
            Debugger.debug(testName + ":\n    PreconditionException " +
                            "caught as expected, since precondition is " + 
                            "not satified");
        }
    }
    
    /**
     * Tests the method <code>post</code>. This is done by parsing an
     * executable boolean expression, and observing appropriate outputs.
     * @param   none
     * @return  nothing
     */
    public static void testPost() {
        testName = "Testing post for \"temp > 0\", where temp = 10";
        int temp = 10;
        try {
            Assertion.post( (temp > 0), 
                            testName + ":\n    Condition is satisfied",
                            testName + ":\n    Condition not statisfied" );
            Debugger.debug( "    PostconditionException not issued as " +
                            "expected, since postcondition is satified");
        } catch (PostconditionException pe) {
            Debugger.printErr( testName + ":\n    PostconditionException " +
                                "should not be thrown, since postcondition " +
                                "is satisfied");
        }
        
        testName = "Testing post for \"temp > 0\", where temp = -10";
        temp = -10;
        try {
            Assertion.post( (temp > 0), 
                            testName + ":\n    Condition is satisfied",
                            testName + ":\n    Condition not statisfied" );
            Debugger.printErr( "    PostconditionException should have " +
                                "been thrown, since postcondition is not " +
                                "satisfied");
        } catch (PostconditionException pe) {
            Debugger.debug(testName + ":\n    PostconditionException " +
                            "caught as expected, since postcondition is " + 
                            "not satified");
        }
    }        
}