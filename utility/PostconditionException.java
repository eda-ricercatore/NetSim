/**
 * Utility package contains the tools that are used for software quality
 * assurance, and to help the software developers of this project "NetSim"
 */
package utility;

/**
 * Thrown when a postcondition failed to comply. These may include:
 *  <ul><li> Returning a result outside of a valid range
 *      <li> Not updating instance variable appropriately </ul>
 *
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.1-andy
 * @since           0.3
 * @acknowledgement Craig Eales
 */
public class PostconditionException extends RuntimeException {
    // Default constructor
    /**
     * Constructs an PostconditionException with no detail message.
     */
    public PostconditionException() {
    }
    
    // Standard constructor
    /**
     * Constructs a PostconditionException with the specified detail message. 
     * @param   error The detail message regarding the thrown exception
     */
    public PostconditionException(String error) {
        super(error);
    }
}