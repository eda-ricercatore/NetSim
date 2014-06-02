/**
 * This is the underlying data structure for NetSim, abstracting a collection
 * of pairwise connections/relations between pairs of objects. These
 * connection/relations are abstracted by edges, and these objected are
 * represented by nodes.
 * 
 * The Graph to be implemented shall be directed and weighted, such that all
 * edges/relations are non-symmetric and are assigned values, which maybe be
 * costs in monetary or in distance sense.
 */
package population.graph;

// importing packages
import utility.*;
import java.util.Random;

/**
 * This class abstracts a Repairable object. Typically in the software 
 * <b>NetSim</b>, these Repairable objects include network server and
 * clients, and links between them. Each of them has some probability of
 * failing, takes time to repair or replace, and some times it may not be
 * worth while to do so.<p>
 *
 * In this class, the object can be failed by testing its probability against
 * a pseudo-randomly generated number between 0.00 and 1.00 inclusive. If the
 * generate number is less than the probability, the object fails.<p>
 *
 * The time required to repair the object is modelled by repair generations, 
 * or the number of generations of evolution required before the object is
 * restored. There are an average value and a temporary value for repair
 * generations. The temporary value is the average value with some noise,
 * since the time required to repair the object is not definite.<p>
 *
 * A threshold also applies to the Repairable object. It is the maximum
 * allowable value for probability of failure. This threshold is important.
 * For example, if a node has a probability of failure, which is not
 * acceptable (higher than threshold), it may be considered not worthy for
 * repair. The reason may simply be repair costing more than replacement.
 * Since chances that an object can fail may vary from time to time, this
 * condition should be checked frequently.
 *
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.3-andy
 * @since           0.2
 * @acknowledgement Matthew Berryman, Wei-li Khoo and Hiep Nguyen
 */ 
public class Repairable {
    // The number of generations passed since the most recent failure
    private int activationCounter = Integer.MAX_VALUE;
    /**
     * The average number of generations required for repair, and the number
     * of generations required for this occurence of failure.
     */
    private int avgRepGen, tmpRepGen;
    // The probability that this Repairable object can fail
    private double prFailure;
    // The maximum allowable probability of failure
    private double threshold;
    // Determines whether this Repairable object is activated or deactivated
    private boolean activated;
    // A pseudo random number generator for this class
    private Random prng = new Random();
    
    // Default Constructor
    /**
     * Creates a default instances of Repairable. A default Repairable object
     * has the property of a failed non-repairable object. That is, this
     * object will have an 100% fail rate, no failure tolerence and an
     * extremely high repair rate. Thus all its internal values must be
     * manually tuned to be repairable.
     */
    public Repairable() {
        avgRepGen = Integer.MAX_VALUE;
        prFailure = 1.00;
        threshold = 0.00;
        activated = false;
    }

    // Standard Constructor
    /**
     * Creates a instances of Repairable with various defined properties. 
     * Although this object can be constructed to be activated, but this will
     * only occur if the fail rate is lower than the threshold defined.
     * However these values can be modified later on, when necessary.
     * @param   gens The number of generations required to repair this object
     * @param   prFail The probability to failure for this Repairable object
     * @param   thresh The maximum probability to failure acceptable
     * @param   act <code>true</code> if this object is to be switched on
     * @throws  PreconditionException If <code>gens</code> is negative.
     * @throws  PreconditionException If either <code>prFail</code> or
     *          <code>thresh</code> are not in between 0.00 and 1.00.
     */    
    public Repairable(int gens, double prFail, double thresh, boolean act) 
    throws PreconditionException {
       Assertion.pre( (gens >= 0),
                        "Generations required for repair is " + gens,
                        "Generations required for repair must be " +
                        "non-negative");
        Assertion.pre( (prFail>=0)&&(prFail<=1)&&(thresh>=0)&&(thresh<=1),
                        "Probability of failure and Probability threshold " +
                        "are " + prFail + " and " + thresh + " respectively",
                        "Probabilities should be between 0.00 and 1.00");
        avgRepGen = gens;
        prFailure = prFail;
        threshold = thresh;
        activated = canActivate() && act;
    }
    
    /**
     * Checks if this Repairable object is activated.
     * @return <code>true</code> if this object is activated
     */
    public boolean activated() {
        return activated;
    }

    /**
     * Checks if this Repairable object can be activated. This checks if the
     * the <code>activationCounter</code> has reached its goal value, and
     * also check if this object is worthy of being activated.
     * @return  <code>true</code> if this object can be activated
     */
    public boolean canActivate() {
        if((activationCounter < tmpRepGen) || (prFailure > threshold))
            return false;
        else
            return true;
    }

    /**
     * Returns the threshold for this Repairable object. The threshold 
     * indicates the tolerence on probablity to failure imposed on this 
     * object. If an object exceeds this threshold, it is not worthy of being
     * activated.
     * @return  thresh The threshold for this Repairable object
     * @throws  PostconditionException If the returned value is not between 
     *          0.00 and 1.00.
     */    
    public double getThreshold() throws PostconditionException {
        Assertion.post( (threshold>=0.0)&&(threshold<=1.0),
                        "Threshold is " + threshold,
                        "Threshold should be between 0.00 and 1.00");
        return threshold;
    }
    
    /**
     * Sets the threshold for this Repairable object. The threshold indicates
     * the tolerence on probablity to failure imposed on this object. If an
     * object exceeds this threshold, it is not worthy of being activated.
     * @param   thresh The threshold for this Repairable object
     * @throws  PreconditionException If <code>thresh</code> is not between 
     *          0.00 and 1.00.
     */
    public void setThreshold(double thresh) throws PreconditionException {
        Assertion.pre( (thresh>=0.0)&&(thresh<=1.0),
                        "Threshold is " + thresh,
                        "Threshold should be between 0.00 and 1.00");
        threshold = thresh;
    }

    /**
     * Activates this Repairable object. This means that this object has been
     * repaired to be put in use.
     * @return  <code>true</code> if the object is succesfully activated.
     */
    public boolean activate(){ 
        if(canActivate()) {
            activated = true;   // enable this object
            return true;
        }
        else return false;
    }
    
    /**
     * Deactivates this Repairable object for a specified number of
     * generations. This may mean that this object is being repaired.
     * @param   gens The number of generations required to repair this object
     * @throws  PreconditionException If <code>gens</code> is negative.
     */
    public void deactivate(int gens) throws PreconditionException {
        Assertion.pre( (gens >= 0),
                        "Generations required for repair is " + gens,
                        "Generations required for repair must non-negative");
        if(activated) {             // if this object is activated
            tmpRepGen = gens;       // generations required for repair
            activated = false;      // disable this object
            activationCounter = 0;  // reset activation counter
        }   // if this object is already deactivated, leave it alone.
    }

    /**
     * Deactivates this Repairable object. This may mean that this object is
     * being repaired.The number of generations required for the repair is
     * the average number of generations specified earlier.
     */    
    public void deactivate() {
        if(activated) {             // if this object is activated
            tmpRepGen = avgRepGen;  // generations required for repair
            activated = false;      // disable this object
            activationCounter = 0;  // reset activation counter
        }   // if this object is already deactivated, leave it alone.
    }

    /**
     * Tries to fail this Repairable object. Firstly, a test compares the
     * probability to fail against a pseudo random number. If the generated
     * number is less than the probability, the node is deactivated.<p>
     * The length of deactivation period is the total of a defined average 
     * value of repair generations plus a variation from -50% to 250%.
     * @return  <code>true</code> If this object is failed.
     */
    public boolean tryAndFail() {
        // create a pseudo random number and compare
        if (prng.nextDouble() < prFailure) {
            // obtain a noise value for required generations for repair
            int variation = (int) (avgRepGen * (3*prng.nextDouble()-0.5));
            // encorporate that noise in to the average required geneartions
            deactivate(avgRepGen + variation);
            return true;
        }
        return false;
    }
    
    /**
     * Returns the probability of failure for this Repairable object
     * @return  The fail rate of this Repairable object
     * @throws  PostconditionException If returned probability is not between
     *          0.00 and 1.00
     */
    public double getFailureProb() throws PostconditionException { 
        Assertion.post( (prFailure>=0.0)&&(prFailure<=1.0),
            "Probability to failure is " + prFailure,
            "Probability to failure must be between 0.00 and 1.00");        
        return prFailure;
    }   
    
    /**
     * Assigns the probability of failure for this Repairable object
     * @param   prob The probability of failure for this Repairable object    
     * @throws  PreconditionException If returned probability is not between
     *          0.00 and 1.00
     */
    public void setFailureProb(double prob) throws PreconditionException {
        Assertion.pre( (prob>=0.0)&&(prob<=1.0),
            "Probability to failure set is " + prob,
            "Probability to failure set must be between 0.00 and 1.00");
        prFailure = prob;
    }
    
    /**
     * Returns the average number of generations required to repair this
     * Repairable object.
     * @return  the average number of generations required
     * @throws  PostconditionException If returned number is negative.
     */
    public int getAvgRepGen() throws PostconditionException {
        Assertion.post( (avgRepGen >= 0),
                        "Average generations required for repair is " +
                        avgRepGen,
                        "Average generations required for repair must be " +
                        "non-negative");
        return avgRepGen;
    }
    
    /**
     * Returns the number of generations since deactivation for this 
     * Repairable object
     * @return  the number of generations since deactivation
     * @throws  PostconditionException If returned count is negative.
     */
    public int getActivationCounter() throws PostconditionException {
        Assertion.post( (activationCounter >= 0),
                        "Generations elapsed in failure state is " + 
                            activationCounter,
                        "Generations elapsed in failure state is must be " +
                            "non-negative");
        return activationCounter;
    }
    
    /**
     * Increments the number of generations since deactivation by one for 
     * this Repairable object. This is used to indicate that a generation has
     * passed while this Repairable object is deactivated.
     */
    public void increCounter(){
        activationCounter++;
    }
}
