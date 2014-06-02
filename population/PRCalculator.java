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
import population.graph.Node;
import java.util.*;

/**
 * A function that computes the level of pleiotropy and redundancy.
 * Pleiotropy is defined as [the average number of edges going to a client],
 * and redundancy is defined [the average number of incoming edges from 
 * servers] for clients.
 *
 * @author  Andy Hao-Wei Lo
 * @version 0.3.8
 * @since   0.3.7
 * @see     Chromosome
 * @see     SetOfChromosomes
 * @acknowledgement Matthew Berryman, Wei-li Khoo and Hiep Nguyen
 */
//public class PRCalculator implements Function {
public class PRCalculator {
    
    /**
     * This method takes in a population of chromosomes, and measures the 
     * level of pleiotropy and redundancy for each chromosome within.
     * @param   pop     The chromosome population.
     */
    public void map(SetOfChromosomes pop) {
        int size = pop.getPopSize();
        Chromosome c;
        // for every chromosome in the population
        for (int i = 0; i < size; i++) {
            c = pop.getChromo(i);   // store the refrence of the chromosome
            apply(c);
        }
    }

    /**
     * Calculates the level of pleiotropy and redundancy by assessing an
     * adjacency matrix of a chromosome. The result is stored in the 
     * chromosome by the setPleiotropy and setRedundancy methods
     *
     * @param   c   the chromosome to which the function is applied
     */
    public void apply(Chromosome c) {
        // number of SERVER->CLIENT edges and CLIENT->SERVER edges
        int sumSC = 0;
        int sumCS = 0;
        // obtain adjacency matrix
        double[][] adjMatrix = c.getAdjacencyMatrix();
        // obtain node list
        ArrayList  nodeList  = c.getNodeList();
        // find out which nodes are servers, and mark that in an array
        boolean[]  isServer  = new boolean[nodeList.size()];
        for (int i = 0; i < isServer.length; i++) 
            if (((Node)nodeList.get(i)).getLabel().startsWith("SERVER"))
                isServer[i] = true;
    
// ### Modified by Zhiyang Ong - 31 July 2005
//int numOutgoing = 0;
        // for every node in the node list
        for (int i = 0; i < nodeList.size(); i++) {
            // find for each server node, all its outgoing nodes to clients
            if(isServer[i]) {
                for ( int j = 0; j < nodeList.size(); j++) {
                    if(!isServer[j] && 
                        adjMatrix[i][j] != Double.POSITIVE_INFINITY)
                        sumSC++; // increment count
                }
            }
// This step is redundant as servers' outgoing edges to clients are equivalent
// to clients incoming edges from servers     
//            else {
//                for ( int j = 0; j < nodeList.size(); j++) {
//                    if(isServer[j] &&
//                        adjMatrix[j][i] != Double.POSITIVE_INFINITY)
//                        sumCS++;
//                }
//            }
        }    
        sumCS = sumSC;

// ### Modified by Zhiyang Ong - 31 July 2005
sumSC = 0;
for(int k=0;k<nodeList.size();k++) {
	// Determine the number of outgoing edges of each server
	if(isServer[k]) {
		for(int m=0;m<nodeList.size();m++) {
			if(adjMatrix[k][m] != Double.POSITIVE_INFINITY) {
				// This outgoing edge exist
				sumSC++; // increment count
			}
		}
	}
}
        
        // calculate the pleiotropy and redundancy
        double pleio = (double) sumSC / (double) c.getNumServers();
        double redun = (double) sumCS / (double) c.getNumClients();
        // set the values        
        c.setPleiotropy(pleio);
        c.setRedundancy(redun);
    }
}
