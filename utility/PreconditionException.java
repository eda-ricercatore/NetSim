/**
 * Utility package contains the tools that are used for software quality
 * assurance, and to help the software developers of this project "NetSim"
 */
package utility;

/**
 * Thrown when a precondition failed to comply. These may include:
 *  <ul><li>Receiving a parameter outside of a valid range</ul>
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.1-andy
 * @since           0.3
 * @acknowledgement Craig Eales
 */
public class PreconditionException extends RuntimeException {
    // Default constructor
    /**
     * Constructs an PreconditionException with no detail message.
     */
    public PreconditionException() {
    }
    
    // Standard constructor
    /**
     * Constructs a PreconditionException with the specified detail message. 
     * @param   error The detail message regarding the thrown exception
     */
    public PreconditionException(String error) {
        super(error);
    }
}