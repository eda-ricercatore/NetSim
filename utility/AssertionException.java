/**
 * Utility package contains the tools that are used for software quality
 * assurance, and to help the software developers of this project "NetSim"
 */
package utility;

/**
 * Thrown when an assertion condition failed to comply. These may include:
 *  <ul><li> Instantiating a non-instantiable class
 *      <li> Using an instance variable within an invalid context </ul>
 *
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.1-andy
 * @since           0.3
 * @acknowledgement Craig Eales
 */
public class AssertionException extends RuntimeException {
    // Default constructor
    /**
     * Constructs an AssertionException with no detail message.
     */
    public AssertionException() {
    }
    
    // Standard constructor
    /**
     * Constructs a AssertionException with the specified detail message. 
     * @param   error The detail message regarding the thrown exception
     */
    public AssertionException(String error) {
        super(error);
    }
}