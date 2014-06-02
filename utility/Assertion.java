/**
 * Utility package contains the tools that are used for software quality
 * assurance, and to help the software developers of this project "NetSim"
 */
package utility;

/**
 * This class provides methods to help users determine if certain runtime
 * conditions are met. An appropriate message will be printed to indicate
 * whether the condition has been satisfied or not.
 *
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.1-andy
 * @since           0.3
 * @acknowledgement Craig Eales
 * @acknowledgement Nikolay Stoimenov 
 */
public class Assertion {
    // Default Constructor
    /** 
     * Creates a new instance of Assertion
     * @throws AssertionException If this class is instantiated
     */
    public Assertion() {
        throw new AssertionException("Do Not Instantiate Assertion Class.");
    }
    
    // --------------------------------------------------------------------
    
    /** 
     * Checks if an asserted condition is satisfied
     * @param   condition An executable assertion statement.
     * @param   msg The message to print to 'System.out' if the assertion is
     *          satisfied.
     * @param   errMsg The message to print to 'System.err' in the exception
     *          if the assertion is not satisfied.
     * @throws  AssertionException If the assertion is not satisfied
     */
    public static void asrt(boolean condition, String msg, String errMsg) 
    throws AssertionException {
        if(condition) Debugger.debug(msg);
        else throw new AssertionException(errMsg);
    }
 
    /** 
     * Checks if a precondition is satisfied
     * @param   condition An executable precondition statement.
     * @param   msg The message to print to 'System.out' if the precondition
     *          is satisfied.
     * @param   errMsg The message to print to 'System.err' in the exception
     *          if the precondition is not satisfied.
     * @throws  PreconditionException If the precondition is not satisfied
     */
    public static void pre(boolean condition, String msg, String errMsg)
    throws PreconditionException {
        if(condition) Debugger.debug(msg);
        else throw new PreconditionException(errMsg);
    }
    
    /** 
     * Checks if a postcondition is satisfied
     * @param   condition An executable postcondition statement.
     * @param   msg The message to print to 'System.out' if the postcondition
     *          is satisfied.
     * @param   errMsg The message to print to 'System.err' in the exception
     *          if the postcondition is not satisfied.
     * @throws  PostconditionException If the postcondition is not satisfied
     */
    public static void post(boolean condition, String msg, String errMsg)
    throws PostconditionException {
        if(condition) Debugger.debug(msg);
        else throw new PostconditionException(errMsg);
    }
}
