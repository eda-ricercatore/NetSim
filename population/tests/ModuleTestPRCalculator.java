import utility.*;
import population.*;
import population.graph.*;
import java.util.*;

/**
 * This class tests the functionality of the PRCalculation. Different graphs
 * will be used to ensure correctness.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.8
 */
public class ModuleTestPRCalculator {
    // to store info regarding the current test
    private static String testName;
    private static final double oo = Double.POSITIVE_INFINITY;
    
    // Default constructor
    /**
     * One should not instantate a test class
     */
    public ModuleTestPRCalculator() {
        Debugger.printErr("Don't instantiate a test class: " + 
                            "ModuleTestPRCalculator");
    }
    
    /**
     * The main method for this class. This will initiate tests to calculate
     * pleiotropy and redundancy for different network topology.
     */
    public static void main(String[] args) {
        // pipe results to file
        Debugger.pipeResult("PRCalculatorNormal.txt",
                            "PRCalculatorError.txt");
        Debugger.enableTrace(true);                             

        Debugger.debug("\n==================================\n"+
                         "filename: PRCalculatorNormal.txt\n" +
                         "==================================");
        Debugger.debug("Module Test for population.PRCalculator:\n");
        
        Debugger.printErr("\n==================================\n"+
                            "filename: PRCalculatorError.txt\n" +
                            "==================================");
        Debugger.printErr("Module Test for population.PRCalculator:\n");
        
        SetOfChromosomes soc = createPop();
        Debugger.debug("");        

        testApplication(soc);
        Debugger.debug("");        

        Debugger.debug("======================================");
        Debugger.debug("Module Test for PRCalculator Completed");
        Debugger.debug("======================================");
        Debugger.printErr("======================================");
        Debugger.printErr("Module Test for PRCalculator Completed");
        Debugger.printErr("======================================");
    }


    private static SetOfChromosomes createPop() {
        SetOfChromosomes soc = new SetOfChromosomes();
        
        Debugger.debug("Constructing Chromosomes");
           
        Debugger.enableTrace(false);
        
        ArrayList nodeList = new ArrayList();
        
        // assume the worst case repair generation is 20 generations
        int repgen = (int)(Math.random() * 20.0);
        /**
         * assume the probability of failure is 30% and the threshold
         * (the maximum allowable probability of failure) is 70%.
         */
        Repairable r = new Repairable(repgen, 0.30, 0.70, true);
        // node is capable of 10 Gbit traffic
        double[] params = {1000000000, 1.0}; 
        Node tmpNode = new NodeImp (params, r);
        
        nodeList.add(new NodeImp("CLIENT", 130, 160, tmpNode));
        nodeList.add(new NodeImp("SERVER", 210, 210, tmpNode));
        nodeList.add(new NodeImp("SERVER", 130,  70, tmpNode));
        nodeList.add(new NodeImp("SERVER",  70, 120, tmpNode));
        nodeList.add(new NodeImp("CLIENT",  60,  30, tmpNode));
        nodeList.add(new NodeImp("CLIENT", 130,  10, tmpNode));
        nodeList.add(new NodeImp("CLIENT", 210,  60, tmpNode));
        nodeList.add(new NodeImp("CLIENT", 200, 120, tmpNode));
        nodeList.add(new NodeImp("CLIENT",  10, 170, tmpNode));
        nodeList.add(new NodeImp("CLIENT",  90, 200, tmpNode));
        nodeList.add(new NodeImp("CLIENT",  10,  90, tmpNode));

        // create cost matrix
        EdgeCostMatrix.populateMatrix(nodeList.size(), 1000);
      
        // edges for different test cases
        int[][][] edges = new int[6][][];
        edges[0] = new int[][]{ { 2, 3}, { 2, 4}, { 4, 2}, { 5, 3}, { 3, 2}, 
                                { 0, 3}, { 2, 0}, { 0, 7}, { 1, 0}, { 0, 1},
                                { 8, 3}, { 9, 3}, {10, 3}, { 4, 3}, { 6, 7},
                                { 1, 7}, { 2, 6}, { 7, 2}, { 5, 2}, { 2, 5} };
        edges[1] = new int[][]{ { 3, 0}, { 3, 4}, { 3, 7} };
        edges[2] = new int[][]{ { 3, 0}, { 3, 4}, { 3, 7}, 
                                { 2, 4}, { 2, 7}, {2, 9} };
        edges[3] = new int[][]{ { 1, 0}, { 2, 0} };
        edges[4] = new int[][]{ { 1, 0}, { 2, 0}, { 3, 0}, 
                                { 1, 4}, { 2, 4}, {3, 4} };
        edges[5] = new int[][]{ };

        ArrayList cells;
        ArrayList cell;
        
        for(int i = 0; i < edges.length; i++) {
            cells = new ArrayList();
            /**
             * for every node in the network, create a list to store   
             * destination nodes.
             */
            for(int j = 0; j < nodeList.size(); j++) 
                cells.add(new ArrayList());

            if(edges[i] != null) {
                // add adjacent nodes  
                for(int j = 0; j < edges[i].length; j++) {
                    cell = (ArrayList) cells.get(edges[i][j][0]);
                    cell.add(nodeList.get(edges[i][j][1]));
                }
            }
            soc.addChromo(new Chromosome(cells, nodeList, 3, 8));
        }
        Debugger.enableTrace(true);
        
        return soc;
    }

    private static void testApplication(SetOfChromosomes popA) {
        Debugger.debug(testName = "Testing function application");
        Debugger.debug("============================\n");
        
        PRCalculator prc = new PRCalculator();
        
        // make a copy of the population
        Debugger.debug("Copying the population for later use...\n");
        Debugger.enableTrace(false);
        SetOfChromosomes popB = new SetOfChromosomes();
        for (int i = 0; i < popA.getPopSize(); i++)
            popB.addChromo( (Chromosome) (popA.getChromo(i)).clone() );
        
        Debugger.enableTrace(true);
        
        Debugger.debug("APPLY the function to each of the chromosomes " +
                            "in the first population...\n");
        Debugger.enableTrace(false);
        
        double[] pleioArr1 = new double[popA.getPopSize()];
        double[] redunArr1 = new double[popA.getPopSize()];
        
        for (int i = 0; i < popA.getPopSize(); i++) {
            prc.apply( popA.getChromo(i) );
            pleioArr1[i] = (popA.getChromo(i)).getPleiotropy();
            redunArr1[i] = (popA.getChromo(i)).getRedundancy();                
        }

        Debugger.enableTrace(true);
        Debugger.debug("Map the function onto the second population...\n");
        Debugger.enableTrace(false);
        
        // start testing the function
        prc.map(popB);

        double[] pleioArr2 = new double[popB.getPopSize()];
        double[] redunArr2 = new double[popB.getPopSize()];
        for (int i = 0; i < popB.getPopSize(); i++) {
            pleioArr2[i] = (popB.getChromo(i)).getPleiotropy();
            redunArr2[i] = (popB.getChromo(i)).getRedundancy();
        }
        
        Debugger.enableTrace(true);
        
        Debugger.debug("Printing Adjacency Matrices of Population");
        for (int i = 0; i < popA.getPopSize(); i++) {
            printMatrix(popA.getChromo(i).getAdjacencyMatrix());
        }


        Debugger.debug("Comparing the results of Apply and Map...\n    " +
                       "[Note: Result should be equal for corressponding " +
                       "pairs of chromosomes.]\n" );
        Debugger.debug("Pleiotropy:");
            compareArrays(pleioArr1, pleioArr2);
        Debugger.debug("Redundancy:");
            compareArrays(redunArr1, redunArr2);
        
        Debugger.debug(testName + " Completed");
        Debugger.debug("======================================");        
    }

    private static void compareArrays(double[] a, double[] b) {
        String line = "\n";
        for (int i = 0; i < a.length; i++) {
            line += "!!! " + i + "\t" + double2String(a[i]);
            if (a[i] != b[i]) line += "\t=X=\t";
            else              line += "\t = \t";
            line += "" + double2String(b[i]) + "\n";
        }
        Debugger.debug(line +"!!! =============\n");
    }

	private static void printMatrix(double[][] matrix) {
	    String line = "";
	    
	    for(int col = 0; col < matrix.length; col++ ) line += "\t" + col;
        Debugger.debug(line);
        
        for (int i = 0; i < matrix.length; i++) {
            line = Integer.toString(i);
            for(int j = 0; j < matrix[i].length; j++) 
                                line += "\t" + double2String(matrix[i][j]);
            Debugger.debug(line);
        }
        Debugger.debug("");
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
	
