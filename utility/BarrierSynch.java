/**
 * Utility package contains the tools that are used for software quality
 * assurance, and to help the software developers of this project "NetSim"
 */
package utility;

/**
 * This class is a utility to synchronise multiple Threads. In this case, the
 * synchronising mechanism uses a barrier to pause the supervisor method,
 * whilst the worker threads do their duties. At the end of execution, each
 * thread will call the method iAmDone, and when waitingOn variable counts
 * down to 0, the ready flag will be set, and the supervior method can resume.
 * In order for the threads to call on iAmDone, an appropriate instance of
 * this class should be passed in as a parameter.
 *
 * @author          Andy Hao-Wei Lo
 * @version         0.3.2-andy
 * @version         0.3.2
 * @acknowledgement Adelaide University, Advanced Programming Paradigms Notes, 
 *                  Lecture 16- Java and Threads, available <a href="http://www.cs.adelaide.edu.au/~third/ai/lectures/HTML/GeneticAlgorithms.htm">here</a>.
 *
 */

public class BarrierSynch {
    /**
     * The number of threads to wait for before setting the "ready" flag on.
     * That is, the number of threads to hold the barrier against. 
     * iAmDone(i) decrements this count.
     */
    private int waitingOn;
    /**
     * The flag determining whether execution in the caller can proceed. It is
     * set true, when all the threads have arrived.
     */
    private boolean ready;
   
    // Default Constructor
    /**
     * Creates a new instance of BarrierSynch. DO NOT USE THIS CONSTRUCTOR.
     * The BarrierSynch created using this constructor, does not wait for
     * any threads. That is to say, this barrier will hold nothing.
     */
    public BarrierSynch() {
        waitingOn = -1;     // not waiting on any threads
        ready = true;       // will always be ready to continue
    }    
    
    // Standard Constructor
    /**
     * Creates a new instance of BarrierSynch, with a predetermined number of
     * threads to wait for, or to hold the barrier against. This is done by
     * setting the number of threads waiting to the input parameter.
     * @param   numToWait The number of threads to wait for.
     * @throws  PreconditionException If the number to wait for is negative.
     */
    public BarrierSynch(int numToWait) throws PreconditionException {
        Assertion.pre( (numToWait > 0), 
                       "New barrier waiting on " + numToWait + " threads",
                       "Barrier cannot wait on negative number of threads" );
        waitingOn = numToWait;  // number of threads to wait for
        ready = false;          // not ready to continue
    }

    /**
     * Decrements the number of threads to wait for. This is done 
     * conditionally. If the count is zero, the "ready" flag will be set. If
     * the count is more greater than 0, simply decrement count. The count
     * should never be less than zero, while "ready" is false.
     * @param   id An integer used to identify the threads which checked in
     */
    public synchronized void iAmDone(int id){
        if (waitingOn > 0) waitingOn--; // if waiting on more, decrement count
        
        if (waitingOn == 0){            // no more to wait for
            waitingOn = -1;             // negative implies no more waiting
            ready = true;               // set flag to indicate "ready"
            notifyAll();
        }
    }
  
    /**
     * Indicates if the barrier is ready. That is, check if all the threads
     * have arrived at the barrier.
     */
    public boolean ready() {
        return ready;
    }
    
    /**
     * Waits for all the threads to arrive. This will put the thread to sleep
     * until all the threads have arrived here.
     */
    public void waitForAll() {
        while (!ready) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
    }
}

