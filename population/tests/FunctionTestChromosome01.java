// importing packages
import java.util.ArrayList;
import java.util.Arrays;
import utility.*;
import population.*; // the class we are testing
import population.graph.*;

/**
 * This test suite will test for the functionality of getAdjancency and
 * getGraph methods within the Chromosome class. This assumes the rest of the
 * Chromosome class is fully functional.
 *
 * The results of the tests will be output to 2 separate files:
 *     -Function01Normal.txt will contain the message if tests are passed
 *     -Function01Error.txt will contain the message for erroneous behaviour
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.7
 * @since   0.3.7
 */
public class FunctionTestChromosome01 {
    // to store info regarding the current test
    private static String testName;

    // Default constructor
    /**
     * Should not instantate a test class.
     */
    public FunctionTestChromosome01() {
        throw new AssertionException("Don't instantiate a test class.");
    }

    /**
     * The main method for this class. 
     */
    public static void main(String[] args) {
        // pipe results to file
        Debugger.pipeResult("FunctionChromosomeNormal01.txt", 
                            "FunctionChromosomeError01.txt");
        Debugger.enableTrace(true); // enable trace printing

        Debugger.debug("\n========================================\n"+
                         "filename: FunctionChromosomeNormal01.txt\n" +
                         "========================================");
        Debugger.debug("Function Test for population.Chromosome:\n");
        
        Debugger.printErr("\n=======================================\n"+
                            "filename: FunctionChromosomeError01.txt\n" +
                            "=======================================");
        Debugger.printErr("Function Test for population.Chromosome:\n");

        Chromosome c = constructChromosome();
        println("");        

        checkAdjacencyMatrix(c);
        println("");

        checkGraph(c);
        println("");

        Debugger.enableTrace(true);

        Debugger.debug("======================================");
        Debugger.debug("Function Test for Chromosome Completed");
        Debugger.debug("======================================");
        Debugger.printErr("======================================");
        Debugger.printErr("Function Test for Chromosome Completed");
        Debugger.printErr("======================================");
    }

    /**
     */
    private static Chromosome constructChromosome() {
        Debugger.debug("Constructing Chromosome with 11 Nodes and 20 Edges");
        
        Debugger.enableTrace(false);
        
        ArrayList nodeList = new ArrayList();
        ArrayList cells    = new ArrayList();
        
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
        
        /**
         * for every node that exist in the network, create a list to store
         * another list of destination nodes.
         */
        for(int i = 0; i < nodeList.size(); i++) cells.add(new ArrayList());

        // add adjacent nodes        
        
        int[][] edges = { { 2, 3}, { 2, 4}, { 4, 2}, { 5, 3}, { 3, 2}, 
                          { 0, 3}, { 2, 0}, { 0, 7}, { 1, 0}, { 0, 1},
                          { 8, 3}, { 9, 3}, {10, 3}, { 4, 3}, { 6, 7},
                          { 1, 7}, { 2, 6}, { 7, 2}, { 5, 2}, { 2, 5} };
        ArrayList cell;
        for(int i = 0; i < edges.length; i++) {
            cell = (ArrayList) cells.get(edges[i][0]);
            cell.add(nodeList.get(edges[i][1]));
        }

        // create cost matrix
        double[][] cost = new double[nodeList.size()][nodeList.size()];
        for (int i = 0; i < cost.length; i++) {
            Arrays.fill(cost[i], Double.POSITIVE_INFINITY);
            cost[i][i] = 0;
        }

        for (int i = 0; i < cost.length; i++) {
            for(int j = 0; j < cost.length; j++)
                cost[i][j] = distBetween( (Node) nodeList.get(i), 
                                          (Node) nodeList.get(j) );
        }
        EdgeCostMatrix.setMatrix(cost);
                
        Debugger.enableTrace(true);
        
        return new Chromosome(cells, nodeList, 3, 8);
    }


    /**
     * Calculates the cartesian distance between 2 nodes using Pythagoras
     * theorem. (Implemented by Andy 01/04/05)
     *
     * @param   a The source node
     * @param   b The destination node
     * @return  the cartesian distance between 2 nodes
     */
    private static double distBetween(Node a, Node b) {
        int[] aCoord = a.getCoordinates();
        int[] bCoord = b.getCoordinates();
        int x = aCoord[0] - bCoord[0];
        int y = aCoord[1] - bCoord[1];
        return Math.sqrt( (x*x) + (y*y) );
    }

    /**
     */
    private static void checkAdjacencyMatrix(Chromosome c) {
        println("=========================");
        println("Checking Adjacency Matrix");
        println("=========================\n");
        println("Obtaining matrix...");
        double[][] matrix = c.getAdjacencyMatrix();
                
        String tab = "\t";
        println(tab + "0" + tab + "1" + tab + "2" + tab + "3" + tab + "4" + 
                tab + "5" + tab + "6" + tab + "7" + tab + "8" + tab + "9" +
                tab + "10");
        
        String line;
        for (int i = 0; i < matrix.length; i++) {
            line = Integer.toString(i);
            for(int j = 0; j < matrix[i].length; j++) {
                line += tab + double2String(matrix[i][j]);
            }
            println(line);
        }
        println("\n========================");
        println("Finished Checking Matrix");
        println("========================\n");
    }

    private static String double2String(double d) {
           
        if (d == Double.POSITIVE_INFINITY || d == 0) return "-";
        
        String num = Double.toString(d);
        
        if (num.length() > 6) {
            num = num.substring(0,6);
            if (num.endsWith(".")) num = num.substring(0,5);
        }
        
        return num;
    }

    /**
     * This will create a graph from a chromsome and list its properties.
     * @param c the chromosome containing infomration regarding the network
     */
    private static void checkGraph(Chromosome c) {
        println("==================================");
        println("Checking getGraph (from chromosome)");
        println("==================================\n");
        println("Constructing graph...");
        Graph g = c.getGraph();
        
        String line;
        
        Debugger.debug("Nodes: " + g.numNodes() + " Edges: " + g.numEdges());

        // Print graph as a list of adjacency lists.
        Debugger.debug("\nAdjacency lists:");
    
        Debugger.enableTrace(false);    // mute
        for ( Node node = g.firstNode(); node != null ;
        node = g.nextNode(node) ) {
            line = "Node " + g.nodeId(node) + "(" + node.getLabel() +
                                                            ") has:\n";
            // Print the node's out edges
            if ( g.outDegree(node) == 0 ) {
            line += "\tNo out edges";
            } else {
                Debugger.enableTrace(false); // mute
                line += "\t" + g.outDegree(node) + " out edge" + 
                                    (g.outDegree(node) > 1 ? "s" : "") + ":";
                for ( Edge ed = g.firstEdgeFrom(node); ed != null ;
                ed = g.nextEdgeFrom(node,ed) ) {
                    line += " (" + g.nodeId(ed.getFromNode()) + "->" +
                                g.nodeId(ed.getToNode()) + ":" + 
                                ed.getLabel() + ")";            
                }
                line += "\n";
            }
                
            //
            // Print the node's in edges
            //
            if ( g.inDegree(node) == 0 ) {
            line += "\tNo in edges";
            } else {
                Debugger.enableTrace(false);    // mute
                line += "\t" + g.inDegree(node) + " in edge"+ 
                                    (g.outDegree(node) > 1 ? "s" : "") +":";
                for ( Edge ed = g.firstEdgeTo(node); ed != null;
                ed = g.nextEdgeTo(node,ed) ) {
                    line += " (" + g.nodeId(ed.getFromNode()) + "->" + 
                                g.nodeId(ed.getToNode()) + ":" + 
                                ed.getLabel() + ")";                
                }
            line += "\n";
            }
            Debugger.enableTrace(true);
            Debugger.debug(line);
            Debugger.enableTrace(false); // mute
        }


        // Print graph as an adjacency matrix.
        println("\nAdjacency matrix:");
        line = "";
        for(int col = 0; col < g.numNodes(); col++ ) {
            line += "\t" + col;
        }
        println(line);

        Node from, to;
        for(int row = 0; row < g.numNodes(); row++ ) {
        line = Integer.toString(row);
            for(int col = 0; col < g.numNodes(); col++ ) {
                from = g.getNode(row);
                to = g.getNode(col);
                    if ( g.hasEdge(from, to) ) {
                        line += "\t" + double2String(g.getEdge(from,to).getCost());
                    } else line += "\t-";               
            }
            println(line);
                Debugger.enableTrace(false); // mute
        }
    
        // Print graph as a list of edges

        println("\nEdge list:");
        for( int edgeId = 0; edgeId < g.numEdges(); edgeId++ ) {
            Edge edge = g.getEdge(edgeId);
            line = "Edge " + edgeId + " from " +
                g.nodeId(edge.getFromNode()) + " to " +
                g.nodeId(edge.getToNode()) + " with label " + edge.getLabel(); 
            println(line);
        }
        println("\n==========================");
        println("Finished Checking getGraph");
        println("==========================\n");    
    }
    
    private static void println(String s) {
        Debugger.enableTrace(true);
        Debugger.debug(s);
        Debugger.enableTrace(false); // mute
    }
}
