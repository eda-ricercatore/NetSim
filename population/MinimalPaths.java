/*
 * population package determines the bahaviour of chromosomes in the
 * population of the network. That is, each chromosome in the population
 * represents a network.
 * The behaviour of chromosmes is determined by modifying the data pertaining
 * to each chromosome
 */
package population;

// Importing packages
import utility.*;

/**
 * Calculates the cost of the minimal spanning tree or the average of the 
 * minimal path costs of a network associated with a chromosome. This will
 * use the dijsktra's minplus algorithm to obtain the minimal path costs, and
 * The result will be calculated depending on the boolean <code>doAvg</code>.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.8
 * @since   0.3.7
 * @see     Chromosome
 * @see     SetOfChromosomes
 * @acknowledgement Matthew Berryman, Wei-li Khoo and Hiep Nguyen
 */
public class MinimalPaths implements Function {
    
    /**
     * if set to true, the average of cost of minimal paths will be
	 * calculated, or else, the total edge cost of the minimal spanning tree 
	 * will be determined
     */
    private boolean doAvg;

    DijkstraMatrix dm = new DijkstraMatrix();

    /**
     * Constructs this function, which is defaulted to find the total edge 
     * cost of the minimal spanning tree
     */
    public MinimalPaths() {
        doAvg = false;
    }

    /**
     * Constructs this function to calculate the average of cost of minimal
	 * paths, or the total edge cost of the minimal spanning tree
     *
     * @param   doAverage   set <code>true</code> to calculated the average
     */    
    public MinimalPaths(boolean doAverage) {
        this.doAvg = doAverage;
    }

    /**
     * Instructs whether the function will calculate the average cost of 
     * minimal paths, or determine the sum of the minimal spanning tree.
     * @param   doAverage   set <code>true</code> to calculated the average
     */
    public void setDoAvg(boolean doAverage) {
        this.doAvg = doAverage;
    }

    /**
     * Applies this function to every Chromosome in a set of chromosomes.
     * @param   pop     the chromosome population.
	 * @param   index   the index of the cost function being used
     */
    public void map(SetOfChromosomes pop, int index) {
        int size = pop.getPopSize();
        Chromosome c;
        // for every chromosome in the population
        for (int i = 0; i < size; i++) {
            c = pop.getChromo(i);   // store the refrence of the chromosome
            apply(c, index);
        }
    }

    /**
     * Computes the cost of the minimal spanning tree or the average of the 
     * costs of all minimal paths of a network.
     *
     * @param   c       the chromosome to which the function is applied
	 * @param   index   the index of the cost function being used
     */
    public void apply(Chromosome c, int index) {
        double result;
        
        // obtain the adjacency matrix
        double[][] matrixAdj = c.getAdjacencyMatrix();
        // perform dijsktra on the matrix
        double[][] matrixRes = dm.dijkstra(matrixAdj);
        
        // if doAvg is set, get the average apth, otherwise find minimal tree
        if (doAvg) result = getAverage(matrixRes);
        else       result = sumUnchanged(matrixAdj, matrixRes);

        // store the result as fitness
        c.insertIntoFitArr(index, result);
    }
    
    private double sumUnchanged(double[][] m1, double[][] m2) {        
        {// Assert check dimensions
            boolean dimensionMatch = true;
            if (m1.length != m2.length) dimensionMatch = false;
            else {
                for (int i = 0; i < m1.length; i++) 
                    if (m1[i].length != m2[i].length) 
                         dimensionMatch = false;
            }
            if(!dimensionMatch) {
                String msg = "Method sumUnchanged: " + 
                             "Matrices dimension mismatch";
                throw new PreconditionException(msg);
            }
        }
        
        double sum = 0.0;
        
        // check contents
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[i].length; j++) {
                /**
                 * if the corresponding elements are equal and are not
                 * "infinity" add the cost to sum
                 */
                if (m1[i][j] == m2[i][j] && 
                m2[i][j] != Double.POSITIVE_INFINITY)
                    sum += m2[i][j];
            }
        }
        
        { 
            double sumM1 = getSum(m1);
            double sumM2 = getSum(m2);
            //Debugger.enableTrace(true);
            //Debugger.printErr("sum = " + sum);
            //Debugger.printErr("sum m1 = " + sumM1);
            //Debugger.printErr("sum m2 = " + sumM2);
            Assertion.asrt( ( sum < sumM1 || approxEqual(sum, sumM1, 8) ) &&
                ( sum < sumM2 || approxEqual(sum, sumM2, 8) ),
                "The result is less than or equal to the sum of costs of " +
                "either matrix",
                "The result SHOULD be less than or equal the sum of costs " +
                "of either matrix" );
            //Debugger.enableTrace(false);
        }
        return sum;
    }
    
    /**
     * Calculates the average degree of separation in the network
     * @param   m   the matrix from which the average will be calculated
     * @return  the average degree of separation of nodes in the network
     */
    private double getAverage(double[][] m) {
        return dm.getAverage(m);
    }    

    /**
     * Perform summation on an adjacency matrix
     * @param   m   the matrix from which the sum will be determined
     * @return  the total cost of edges/minimal paths in the network
     */
    private double getSum(double[][] m) {
        return dm.getSum(m);
    }  
    
     private static boolean approxEqual(double a, double b, int n) {
        double diff = Math.abs(b - a);
        if (diff * Math.pow(10,n) < 1.0) return true;
        else return false;
    }
        
    private static String double2String(double d) {
        if (d == Double.POSITIVE_INFINITY) return "-";        
        String num = Double.toString(d);        
        if (num.length() > 6) {
            num = num.substring(0,6);
            if (num.endsWith(".")) num = num.substring(0,5);
        }        
        return num;
    }
  
}
