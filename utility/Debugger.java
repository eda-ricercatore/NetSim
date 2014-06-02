/**
 * Utility package contains the tools that are used for software quality
 * assurance, and to help the software developers of this project "NetSim"
 */
package utility;

// Importing packages
import java.io.*;

/**
 * This class is an adaptor for handling all messages intended to be printed
 *  on System's standard output (including error output) streams. In order 
 *  for traces printing to be switched on, boolean "enabled" must be set 
 *  <code>true</code>, otherwise trace printing is ignored.<p>
 * Throughout the project <B>NetSim</B>, when printing trace/error messages,
 *  the methods, <code>debug()</code> and <code>printErr()</code>, shall be
 *  invoked instead of <code>System.out.println()</code> or 
 *  <code>System.err.println()</code>.<p>
 * The trace and error messages to be printed may also be piped to files.
 * 
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.1-andy
 * @since           0.3
 * @acknowledgement Tishampati Dhar 
 */
public class Debugger {
    //declaring instance variable
    /**
     * boolean to determine whether debugging traces will be printed to
     * System.out (and/or System.err. Set to "true" to enable trace printing.
     */
    private static boolean enabled = false;
    
    // Default Constructor
    /** 
     * Creates a new instance of Debugger
     * @throws AssertionException If this class is instantiated
     */
    public Debugger() {
        throw new AssertionException("Do Not Instantiate Debugger Class.");
    }
    
    // --------------------------------------------------------------------
    
    /**
     * Enables or disables debugging trace printing.
     * @param   condition Determines whether debugging traces will be printed
     *          Set to <code>true</code> to enable, <code>FALSE</code> to 
     *          disable.
     */
    public static void enableTrace(boolean condition) {
        enabled = condition;
    }
    
    /**
     * Prints trace statements to <code>System.out</code> if "enabled" is set
     * to <code>true</code>. Otherwise the message is suppressed.
     * @param   msg The message intended to be printed
     */
    public static synchronized void debug(String msg) {
        // trace statement is printed, if enabled is set 'true'
        if(enabled) System.out.println(msg);
    }

    /**
     * Prints error messages to <code>System.err</code> if enabled is set to
     * <code>true</code>. Otherwise the error message is suppressed.
     * @param   errMsg The error message intended to be printed
     */
    public static synchronized void printErr(String errMsg) {
        // error message is printed, if enabled is set 'true'
        if(enabled) System.err.println(errMsg);
    }
    
    /**
     * Redirects <code>System.out</code> and <code>System.err</code> to a log
     * file.<p> Messages intended to be printed in either stream will be 
     * piped to the same file.<p> If an error occured when opening the file, 
     * 'standard' output or error streams will be used.
     * @param   filename The name of the log file
     * @throws  PreconditionException If file name correspond to an existing
     *          file
     */     
    public static void pipeTrace(String filename) {
        File outFile = new File(filename);
        Assertion.pre(!outFile.exists(), 
                        "New file created for storing traces",
                        "Must supply new file name for traces");

        try{
            PrintStream ps = new PrintStream(new FileOutputStream(outFile));
            System.setOut(ps);
            System.setErr(ps);
        } catch(IOException e) {
            printErr("Error opening " + filename + ". " +
                     "Revert to use 'standard' output and error streams.");
        }
    }
    
    /**
     * Redirects <code>System.out</code> and <code>System.err</code> to two
     * separate files. This facilitates printing of testsuite results.<p>
     * Messages intended to be printed in <code>System.out</code> will be 
     * piped to the "normal" file, whereas messages intended to be printed 
     * in <code>System.err</code> will be piped to the "error" file.<p> If an
     * error occured when opening either file, 'standard' output or error 
     * streams will be used.
     * @param   normal The name of the file to store traces from execution
     * @param   error The name of the file to store error messages
     * @throws  PreconditionException If file names supplied are equal
     * @throws  PreconditionException If file names passed correspond to
     *          existing files
     */     
    public static void pipeResult(String normal, String error) 
    throws PreconditionException {
        Assertion.pre(!normal.equals(error), "Different file names supplied",
                        "Cannot pipe results to the same file");
        File normalFile = new File(normal);
        File errorFile = new File(error);
        Assertion.pre(!(normalFile.exists()||errorFile.exists()), 
                        "New files created for storing results",
                        "Must supply new file names for results");
        
        try{
            System.setOut(new PrintStream(new FileOutputStream(normal)));
        } catch(IOException e) {
            printErr("Error opening " + normal + ". " +
                     "Revert to use 'standard' output stream.");
        }
        try{
            System.setErr(new PrintStream(new FileOutputStream(error)));
        } catch(IOException e) {
            printErr("Error opening " + error + ". " +
                     "Revert to use 'standard' error stream.");
        }
    }
}