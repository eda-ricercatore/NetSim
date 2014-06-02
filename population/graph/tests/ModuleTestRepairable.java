// importing packages
import utility.*;
import population.graph.Repairable; // the class we are testing

/**
 * This test suite will test for the functionality of the Repairable class.
 * This will first check if all the assertion made are enforced by providing
 * invalid input parameters. Then it should be checked that the general
 * behaviour of the class is consistent with that in the class descriptions.
 *
 * The results of the tests will be output to 2 separate files:
 *     -RepairableNormal.txt will contain the message if tests are passed
 *     -RepairableError.txt will contain the message for erroneous behaviour
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.3-andy
 * @since   0.3.3
 */
public class ModuleTestRepairable {
    // to store info regarding the current test
    private static String testName;

    // Default constructor
    /**
     * Should not instantate a test class.
     */
    public ModuleTestRepairable() {
    	throw new AssertionException("Don't instantiate a test class.");
    }

    /**
     * The main method for this class. The test suite is divided into 3 main
     * components. The first test will check the functionality of the default
     * and standard constructors. The second will validate the behaviour
     * concerned with failure of a Repairable object. The last test shall
     * determine the correct behaviour of activation of a Repairable object.
     */
    public static void main(String[] args) {
        // pipe results to file
        Debugger.pipeResult("RepairableNormal.txt", "RepairableError.txt");
        Debugger.enableTrace(true); // enable trace printing
        

        /**
         * Modified by Andy 07/04/05: to add header for output files for
         * test results.
         */
        Debugger.debug("=============================\n"+
                       "filename: RepairableNormal.txt\n" +
                       "=============================");
        Debugger.debug("Module Test for Repairable:\n");
        Debugger.printErr("============================\n"+
                          "filename: RepairableError.txt\n" +
                          "============================");
        Debugger.printErr("Module Test for Repairable:\n");

        testConstructors();
        Debugger.debug("");
        
        testFailure();
        Debugger.debug("");

        testActivation();
        Debugger.debug("");

        // Modified by Andy 07/04/05: to emphisise end of test
        Debugger.debug("====================================");
        Debugger.debug("Module Test for Repairable Completed");
        Debugger.debug("====================================");
        Debugger.printErr("====================================");
        Debugger.printErr("Module Test for Repairable Completed");
        Debugger.printErr("====================================");
    }

    /**
     * Tests the constructors of Repairable object. This includes one default
     * constructor and one standard constructor. The standard constructor
     * will be tested with a variety of input parameters to ensure all the
     * precondition are checked for.
     */
    public static void testConstructors() {
        Debugger.debug("Testing Constructors\n====================");
        testName = "Testing default constructor";
        
        Repairable test = new Repairable();
        checkBasicProperties(test);
        Debugger.debug("");

        testName = "Testing standard constructor";
        // test with illegal value for required generations for repair
        Debugger.debug(testName + " with gens = -1");
        try {
            test = new Repairable(-1, 0.0, 1.0, true);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught,since gens = -1\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "gens = -1 violates the precondition");
        }
        Debugger.debug("");

        // test with illegal value for probability of failure
        Debugger.debug(testName + " with gens = 1, prFailure = 1.5");
        try {
            test = new Repairable(1, 1.5, 1.0, true);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught,since prFailure = 1.5\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "prFailure = 1.5 violates the\n    " +
                           "precondition");
        }
        Debugger.debug("");

        // test with illegal value for probability of failure
        Debugger.debug(testName + " with gens = 1, prFailure = -0.5");
        try {
            test = new Repairable(1, -0.5, 1.0, true);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught,since prFailure = -0.5\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "prFailure = -0.5 violates the\n    " +
                           "precondition");
        }
        Debugger.debug("");

        // test with illegal value for threshold for probability of failure
        Debugger.debug(testName + " with gens = 1, prFailure = 0.5, \n" +
                            "    threshold = 1.5");
        try {
            test = new Repairable(1, 0.5, 1.5, true);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught,since threshold = 1.5\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "threshold = 1.5 violates the\n    " +
                           "precondition");
        }
        Debugger.debug("");

        // test with illegal value for threshold for probability of failure
        Debugger.debug(testName + " with gens = 1, prFailure = 0.5, \n" +
                            "    threshold = -0.5");
        try {
            test = new Repairable(1, 0.5, -0.5, true);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught,since threshold = -0.5\n    " +
                                "violates the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                           "threshold = -0.5 violates the\n    " +
                           "precondition");
        }
        Debugger.debug("");

        // test constructor with legal values
        Debugger.debug(testName + " with gens = 5, prFailure = 0.25, \n" +
                            "    threshold = 0.3");
        test = new Repairable(1, 0.25, 0.3, true);
        checkBasicProperties(test);

        Debugger.debug("");
        Debugger.debug("=============================");
        Debugger.debug("Testing Constructors Complete");
        Debugger.debug("=============================");
    }

    /**
     * This simply queries a Repairable object for its basic properties. This
     * assumes that the functions: "getAvgRepGen", "getFailureProb",
     * "getThreshold" and "activate" functions correctly and will check for
     * assertions as indicated in the interface.
     * @param   r The repairable object whose properties will be checked
     */
    private static void checkBasicProperties(Repairable r) {
        Debugger.debug("    Checking basic properties:");
        try {
            int avgRepGen = r.getAvgRepGen();
            double prFailure = r.getFailureProb();
            double threshold = r.getThreshold();
            boolean activated = r.activate();
        } catch (PostconditionException pe) {
            Debugger.printErr(testName + ":\n    This instance Repairable "+
                                "has invalid properties.");
        }
    }

    /**
     * Tests the functions related to failing and deactivating a Repairable
     * object. Firstly, the modifier method "setFailureProb" will be checked
     * for its compliance with assertions. Then the two ways for deactivating
     * the Repairable objects will be tested. When testing deactivation, it
     * is assumed that methods "activate" and "increCounter" function as
     * expected. Finally, the method "tryAndFail" will be tested a few times
     * as required, since we may not always fail a Repairable object.
     */
    public static void testFailure() {
        Debugger.debug("Testing functions associated with Failures and " +
                       "Deactivation");
        Debugger.debug("===============================================" +
                       "============");

        // creating test Repairable object
        testName = "Testing with an instance of Repairable";
        Debugger.debug(testName + " with gens = 5, prFailure = 0.25, \n" +
                            "    threshold = 0.3");
        Repairable test = new Repairable(5, 0.25, 0.3, true);
        Debugger.debug("");

        testName = "Setting failure probability";

        // test setting failure prob with illegal value for probability
        Debugger.debug(testName + " to -1.5");
        try {
            test.setFailureProb(-1.5);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught,since prFailure to be\n    " +
                                "set is -1.5, violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                                "prFailure to be set is -1.5,\n    " +
                                "violating the precondition");
        }
        Debugger.debug("");

        // test setting failure prob with illegal value for probability
        Debugger.debug(testName + " to 1.5");
        try {
            test.setFailureProb(1.5);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught,since prFailure to be\n    " +
                                "set is 1.5, violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                                "prFailure to be set is 1.5,\n    " +
                                "violating the precondition");
        }
        Debugger.debug("");

        // test setting failure prob with legal value for probability
        Debugger.debug(testName + " to 0.1");
        try {
            test.setFailureProb(0.1);
            Debugger.debug(testName + ":\n    Exception not caught as " +
                                "expected, since prFailure to be set is" +
                                "0.1, which\n    is legal");
        } catch (PreconditionException pe) {
            Debugger.printErr("    Exception should not be caughet since " +
                                "prFailure to be set is 0.1,\n    " +
                                "which is legal");
        }

        Debugger.debug("");
        checkBasicProperties(test);

        Debugger.debug("\nThis Repairable Object is now activated: (" +
                        test.activated()+ ")");

        testName = "Deactivating the Repairable object";
        
        // test to deactivate the Repairable object
        Debugger.debug("");
        Debugger.debug(testName + " for its average required generations");
        test.deactivate();
        Debugger.debug("This Repairable Object is now deactivated: (" +
                        !test.activated()+ ")");
        Debugger.debug("\nRepeatly increment the activation counter until " +
                       "this object has passed the\n    required " +
                       "generations for repair");
        // attempt to activate it
        while(!test.activate()) test.increCounter();
        Debugger.debug("This Repairable Object is now activated: (" +
                        test.activated()+ ")");

        Debugger.debug("");
        // test to deactivate the Repairable object for 3 generations
        Debugger.debug(testName + " for 3 generations");
        test.deactivate(3);
        Debugger.debug("This Repairable Object is now deactivated: (" +
                        !test.activated()+ ")");
        Debugger.debug("Repeatly increment the activation counter 3 times " +
                       "and activate this object");
        test.increCounter(); test.increCounter(); test.increCounter();
        Debugger.debug("This Repairable Object is now activated: (" +
                        test.activate()+ ")");

        Debugger.debug("\nTrying and fail a repairable object");
        int count = 0;
        // Try to fail the object. Repeat until successly failing it
        while(!test.tryAndFail()) count++;
        Debugger.debug("This Repairable Object is now deactivated after " +
                       count + " times : (" + !test.activated()+ ")");
        Debugger.debug("\nRepeatly increment the activation counter until " +
                       "this object has passed the\n    required " +
                       "generations for repair");
        // Try to activate it again
        while(!test.activate()) test.increCounter();
        Debugger.debug("This Repairable Object is now activated: (" +
                        test.activated()+ ")");

        Debugger.debug("");
        Debugger.debug("==================================");
        Debugger.debug("Testing Failure Functions Complete");
        Debugger.debug("==================================");
    }

    /**
     * Tests the fuctions related to the threshold for maximum allowable
     * probability of failure of and activating a Repairable object. Firstly,
     * it is checked the setting threshold is funtioning. Then a Repairable
     * object will be failed, and the activation counter will be increaded
     * until the point that reaches it's required generations. However, the 
     * threshold will be changed to disallow activation and allow it to
     * happen again.
     */
    public static void testActivation() {
        Debugger.debug("Testing functions associated with Activation " +
                            "and Thresholds");
        Debugger.debug("===============================================" +
                            "==============");

        testName = "Testing with an instance of Repairable";
        Debugger.debug(testName + " with gens = 5, prFailure = 0.1, \n" +
                            "    threshold = 0.3");
        // creating test Repairable object
        Repairable test = new Repairable(5, 0.1, 0.3, true);

        testName = "Setting failure probability threshold";

        // test setting threshold with illegal value for probability
        Debugger.debug(testName + " to -1.5");
        try {
            test.setThreshold(-1.5);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught,since threshold to be\n    " +
                                "set is -1.5, violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                                "threshold to be set is -1.5,\n    " +
                                "violating the precondition");
        }
        Debugger.debug("");

        // test setting threshold with illegal value for probability
        Debugger.debug(testName + " to 1.5");
        try {
            test.setThreshold(1.5);
            Debugger.printErr(testName + ":\n    Exception should have " +
                                "been caught,since threshold to be\n    " +
                                "set is 1.5, violating the precondition");
        } catch (PreconditionException pe) {
            Debugger.debug("    Exception caught as expected since " +
                                "threshold to be set is 1.5,\n    " +
                                "violating the precondition");
        }
        Debugger.debug("");

        // test setting threshold with legal value for probability
        Debugger.debug(testName + " to 0.2");
        try {
            test.setThreshold(0.2);
            Debugger.debug("    Exception not caught as " +
                                "expected, since threshold to be set is" +
                                "0.2, which\n    is legal");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    Exception should not be caught since " +
                                "threshold to be set is 0.2,\n    " +
                                "which is legal");
        }

        Debugger.debug("");
        checkBasicProperties(test);

        Debugger.debug("\nThis Repairable Object is now activated: (" +
                        test.activated()+ ")");

        testName = "Deactivating the Repairable object";

        Debugger.debug("");
        Debugger.debug(testName + " for its average required generations");
        test.deactivate();  // deactivate the object
        Debugger.debug("This Repairable Object is now deactivated: (" +
                        !test.activated()+ ")");
        // increment the activation counter until reaching repair generations 
        for (int i = 1; i <= 5; i++) {
            Debugger.debug("    Increment the activation counter by 1 "+
                            "using \"increCounter\"");
            test.increCounter();
            test.getActivationCounter();

            Debugger.debug("    The Repairable can be activated: (" +
                            test.canActivate() + ")");
        }

        testName = "Disallow activation";
        Debugger.debug("");
        // try changing the threshold so that the object cannot be activated
        Debugger.debug(testName + " by setting the threshold to 0.05");
        try {
            test.setThreshold(0.05);
            Debugger.debug("    Exception not caught as " +
                                "expected, since threshold to be set is " +
                                "0.05, which\n    is legal");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    Exception should not be " +
                                "caught since threshold to be set is 0.05," +
                                "\n    which is legal");
        }
        test.getFailureProb();
        Debugger.debug("    The Repairable can be activated: (" +
                            test.canActivate() + ")");

        testName = "Allow activation";
        Debugger.debug("");
        // try changing the threshold so that the object can be activated
        Debugger.debug(testName + " by setting the threshold to 0.25");
        try {
            test.setThreshold(0.25);
            Debugger.debug("    Exception not caught as " +
                                "expected, since threshold to be set is " +
                                "0.25, which\n    is legal");
        } catch (PreconditionException pe) {
            Debugger.printErr(testName + ":\n    Exception should not be " +
                                "caught since threshold to be set is 0.25," +
                                "\n    which is legal");
        }
        test.getFailureProb();
        Debugger.debug("    The Repairable can be activated: (" +
                            test.canActivate() + ")");

        Debugger.debug("");
        Debugger.debug("=====================================");
        Debugger.debug("Testing Activation Functions Complete");
        Debugger.debug("=====================================");
    }

}
