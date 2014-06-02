import utility.*;
import population.*;
import population.graph.*;
import java.util.*;

/**
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.4.1
 */
public class ModuleTestResistance {
    // to store info regarding the current test
    private static String testName;
    private static final double oo = Double.POSITIVE_INFINITY;
    
    // Default constructor
    /**
     * One should not instantate a test class
     */
    public ModuleTestResistance() {
        Debugger.printErr("Don't instantiate a test class: " + 
                            "ModuleTestResistance");
    }
    
    /**
     * 
     */
    public static void main(String[] args) {
        // pipe results to file
        Debugger.pipeResult("ResistanceNormal.txt",
                            "ResistanceError.txt");
        Debugger.enableTrace(true);                             

        Debugger.debug("\n==============================\n"+
                         "filename: ResistanceNormal.txt\n" +
                         "==============================");
        Debugger.debug("Module Test for population.Resistance:\n");
        
        Debugger.printErr("\n=============================\n"+
                            "filename: ResistanceError.txt\n" +
                            "=============================");
        Debugger.printErr("Module Test for population.Resistance:\n");
        
        SetOfChromosomes soc = createPop();
        Debugger.debug("");        

        testApplication(soc);
        Debugger.debug("");        

        Debugger.debug("====================================");
        Debugger.debug("Module Test for Resistance Completed");
        Debugger.debug("====================================");
        Debugger.printErr("====================================");
        Debugger.printErr("Module Test for Resistance Completed");
        Debugger.printErr("====================================");
    }


    /**
     * Create predetermined chromosomes for testing functions
     */
    private static SetOfChromosomes createPop() {
        ArrayList nodeList = genNodeList();
        Debugger.debug("Constructing Chromosomes for test Population:\n");
        
        Debugger.debug("Preparing edge cost matrix...");
        Debugger.debug("[cost between 2 nodes is the average of their ids.");          
        // create cost matrix
        int numNodes = nodeList.size();
        //Debugger.printErr("numNodes = "+numNodes);
        double[][] costMatrix = new double[numNodes][numNodes];
        for(int i = 0; i < numNodes; i++)
            for(int j = 0; j < numNodes; j++) 
                if (i != j) costMatrix[i][j] = ((double)i+(double)j)/2.0;

        // set cost matrix
        EdgeCostMatrix.setMatrix(costMatrix);
        // edges for different test cases
        int[][] chrom1Edges = { {0,4}, {4,5}, {5,2},  {2,6},  {6,7}, {7,8},
                                {8,3}, {3,9}, {9,10}, {10,1}, {1,0} };
        int[][] chrom2Edges = { {0,4}, {4,1}, {1,6}, {6,2}, {2,8}, {8,10},
                                {0,5}, {5,3}, {3,7}, {7,9}, {9,10} };
        int[][] chrom3Edges = { {0,4}, {4,1}, {1,7}, {7,10},
                                {0,5}, {5,2}, {2,8}, {8,10},
                                {0,6}, {6,3}, {3,9}, {9,10} };
        int[][] chrom4Edges = { {1,0}, {0,5}, {5,2}, {2,7}, {7,9},  {9,3},
                                {1,4}, {4,6}, {6,2}, {2,8}, {8,10}, {10,3} };

        // Array of edgelists
        int[][][] allEdges = new int[][][]{ chrom1Edges, chrom2Edges,
                                            chrom3Edges, chrom4Edges };

        SetOfChromosomes set = new SetOfChromosomes();

        int numServers = 3, numClients = 8;
        ArrayList cells, cell;
        Chromosome temp;
        
        Debugger.enableTrace(false);
        
        for (int i = 0; i < allEdges.length; i++) {
            cells = new ArrayList();
            /**
             * for every node in the network, create a list to store   
             * destination nodes.
             */
            for(int j = 0; j < nodeList.size(); j++) 
                cells.add(new ArrayList());

            if(allEdges[i] != null) {
                // add adjacent nodes  
                for(int j = 0; j < allEdges[i].length; j++) {
                    cell = (ArrayList) cells.get(allEdges[i][j][0]);
                    cell.add(nodeList.get(allEdges[i][j][1]));
                }
            }                        
            temp = new Chromosome(cells, nodeList, numServers, numClients); 
            set.addChromo(temp);
        }
        Debugger.enableTrace(true);
        return set;
    }

    /**
     * Generate the nodeList which resemble nodes in our "Mock-Up" graph
     */
     private static ArrayList genNodeList() {
        ArrayList nodeList = new ArrayList();

        //Populating Node List
        Debugger.debug("Populate the nodeList with 11 nodes");

        Debugger.enableTrace(false);
        // assume the worst case repair generation is 20 generations
        int repgen = (int)(Math.random() * 20.0);
        /**
         * assume the probability of failure is 30% and the threshold
         * (the maximum allowable probability of failure) is 70%.
         */
        Repairable r = new Repairable(repgen, 0.30, 0.70, true);
        // node is capable of 10 Gbit traffic
        double[] params = {1000000000, 0.0}; 
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
        Debugger.enableTrace(true);
        
        return nodeList;
    }


    /**
     * Checks that if the method map and apply produces the same result
     */
    private static void testApplication(SetOfChromosomes popA) {
        Debugger.debug(testName = "Testing function application");
        Debugger.debug("============================\n");

        // make a copy of the population
        Debugger.debug("\nCopying the population for later use...\n");
        Debugger.enableTrace(false);
        SetOfChromosomes popB = new SetOfChromosomes();
        for (int i = 0; i < popA.getPopSize(); i++) {
            popA.getChromo(i).createFitnessArr(1);
            popB.addChromo( (Chromosome) (popA.getChromo(i)).clone() );
            popB.getChromo(i).createFitnessArr(1);
        }
        
        int arrIndex = 0;

        Function[] res = new Function[2];
        res[0] = new Resistance(true);
        res[1] = new Resistance(false);
        
        for(int j = 0; j < res.length; j++) {
            
            Debugger.enableTrace(true);
            Debugger.debug("Apply the function to each of the chromosomes " +
                                "in the first population...\n");
            
            double[] fitnessArr1 = new double[popA.getPopSize()];
            Debugger.enableTrace(false);
             
            for (int i = 0; i < popA.getPopSize(); i++) {
                res[j].apply( popA.getChromo(i), arrIndex);
                fitnessArr1[i] = (popA.getChromo(i)).getFitArrElem(arrIndex);
            }
    
            Debugger.enableTrace(true);
            Debugger.debug("Map the function onto the second population...\n");
            Debugger.enableTrace(false);
            
            // start mapping the function
            res[j].map(popB, arrIndex);
    
            double[] fitnessArr2 = new double[popB.getPopSize()];
            for (int i = 0; i < popB.getPopSize(); i++) 
                fitnessArr2[i] = (popB.getChromo(i)).getFitArrElem(arrIndex);
            
            Debugger.enableTrace(true);
    
            Debugger.debug("Comparing the results of Apply and Map...\n    " +
                           "[Note: Result should be equal for corressponding " +
                           "pairs of chromosomes.]" );
            compareArrays(fitnessArr1, fitnessArr2);
            
        }
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

//	private static void printMatrix(double[][] matrix) {
//	    String line = "";
//	    
//	    for(int col = 0; col < matrix.length; col++ ) line += "\t" + col;
//        Debugger.debug(line);
//        
//        for (int i = 0; i < matrix.length; i++) {
//            line = Integer.toString(i);
//            for(int j = 0; j < matrix[i].length; j++) 
//                                line += "\t" + double2String(matrix[i][j]);
//            Debugger.debug(line);
//        }
//        Debugger.debug("");
//	}

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
	
