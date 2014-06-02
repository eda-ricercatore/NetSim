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
import java.util.ArrayList;
import java.util.List;
//import java.util.*;

/**
 * This class implments the Graph interface and GraphStat interface, which is
 * the interface for statistical functions for <b>NetSim</b>. The graph
 * consists of two main components, namely Nodes and Edges.<p>
 *
 * In this implementation, these nodes and edges are stored in separate lists
 * for easy access. A node is  used to represent an entity, such as servers
 * and clients. An edge is used to connect nodes together, so they behave
 * like links or relations. <p>
 *
 * In the software <b>NetSim</b> the graph is used to represent a static view
 * or a snapshot of a Network with linked Servers and Clients.<p>
 *
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.7-andy
 * @since           0.3
 * @acknowledgement Matthew Berryman, Wei-li Khoo and Hiep Nguyen
 */
public class GraphImp implements Graph, GraphStat {

    /**
     * Indicates the width and height of display in order to determine the
     * position to place the nodes.
     */
    private int maxXWidth, maxYHeight;
    /**
     * # Modified by Andy 08/04/05:
     *      This value signifies the amount, of serving capacity at the 
     * destination nodes, that a source node will occupy.
     * 
     * We will not need to increase edge usages. This is because we are not
     * simulating routing in the telecommunication network. Hence, we can
     * assume that the efficiency of edges is 100%. Thus there is not a point
     * to assign a random number for the edge's efficiency.
     * 
     * Therefore ultilization increment of Edge is 0, since its efficiency
     * cannot exceed 100%.
     */
    private final double NODE_INCREMENT = 0.0025;
    
    /**
     * This matrix marks which location are occupied by nodes on the display.
     * An element is set <code>true</code> if it is occupied by another node.
     */
    public boolean[][] reference;
    /**
     * A pseudo-random number generator, typically used for determining the
     * location of the nodes.
     */
    private Random prng = new Random();
    /**
     * a list to store all the nodes and edges. Chronological order of
     * addition are implicitly maintained by the ArrayList.
     */
    private ArrayList nodeList = new ArrayList(), edgeList = new ArrayList();
    // separate counter for number of server and client nodes in the graph
    int numServer = 0, numClient = 0;


    // Default Constructor
    /**
     * Creates a default instance of GraphImp. The default size of graph
     * display is 640 by 480 pixels.
     */
    public GraphImp() {
        maxXWidth = 640;                    // set width of graph display
        maxYHeight = 480;                   // set height of graph display
        reference = new boolean[640][480];  // initialise reference matrix
    }

    // Standard Constructor
    /**
     * Creates an instance of GraphImp. The users determines the size of the
     * displaying the graph. The size is typically that of the display area
     * of the graphical user interface for <b>NetSim</b>. The sizes are given
     * in number of pixels.
     * @param   maxX The width of graph display
     * @param   maxY The height of graph display
     * @throws  PreconditionException If sizes provided are negative
     */
    public GraphImp(int maxX, int maxY) throws PreconditionException {
        Assertion.pre( (maxX >= 0)&&(maxY >= 0),
                       "The display area is " + maxX + " x " + maxY,
                       "The disaply area must not be negative");
        maxXWidth = maxX;                   // set width of graph display
        maxYHeight = maxY;                  // set height of graph display
        reference = new boolean[maxX][maxY];// initialise reference matrix
    }

    /**
     * implementing the Graph interface
     */

    /**
     * Returns the number of nodes in the Graph. The number of nodes in the
     * Graph is the size of node list, since the node list contains
     * references to all the nodes in the graph.
     * @return  number of nodes in the Graph
     * @throws  PostconditionException If returned value is negative (<0)
     */
    public int numNodes() throws PostconditionException {
        // Checking post-condition
        Assertion.post( nodeList.size() >= 0,
                        "The number of nodes " + nodeList.size(),
                        "The number of nodes must not be negative");
        return nodeList.size();
    }

    /**
     * Returns the number of edges in the Graph. The number of edges in the
     * Graph is the size of the edge list, since the edge list contains
     * references to all the edges in the graph.
     * @return  number of edges in the Graph
     * @throws  PostconditionException If returned value is negative (<0)
     */
    public int numEdges() throws PostconditionException {
        // Checking post-condition
        Assertion.post( edgeList.size() >= 0,
                        "The number of edges " + edgeList.size(),
                        "The number of edges must not be negative");
        return edgeList.size();
    }

    /**
     * Queries the Graph to check if a Node exists in this graph. This will
     * check if this Node is contained in the node list.
     * @param   n A node whose existence will be checked
     * @return  a Boolean, <code>true</code> if <code>n</code> exists
     */
    public boolean hasNode(Node n) { 
        return nodeList.contains(n);
    }

    /**
     * Queries the Graph to check if an Edge exists in this graph. This will
     * check if this Edge is containted in the edge list.
     * @param   e An Edge whose existence will be checked
     * @return  a Boolean, <code>true</code> if <code>e</code> exists
     */
    public boolean hasEdge(Edge e) {
        return edgeList.contains(e);
    }

    /**
     * Queries the Graph to check if an Edge exists between two nodes of this
     * Graph. The result is determined by obtaining the outgoing edge list of
     * <code>from</code>, the originating node, and trying to match the
     * destinations of the outgoing edges with <code>to</code>, the
     * terminating node.
     * @param   from The fromNode of the Edge
     * @param   to The toNode of the Edge
     * @return  a Boolean, <code>true</code> if an Edge exists between
     *          <code>from</code> and <code>to</code>
     * @throws  PreconditionException If the two nodes does not exist on this
     *          Graph
     */
    public boolean hasEdge(Node from, Node to) throws PreconditionException {
        // Checking pre-condition
        Assertion.pre( hasNode(from) && hasNode(to),
                       "The from and to nodes are in the graph",
                       "The from and to nodes must be in the graph");
        // obtaining the outgoing edge list of the "from" node
        List tempList = from.outEdgeList();
        for(int i = 0; i < tempList.size(); i++) {// search through the list
            Edge e = (Edge) tempList.get(i);    
            // if edge's to node matches "to" we found the edge    
            if(e.getToNode() == to) return true;
        }
        return false;
    }

    /**
     * Returns the ID of a Node in this Graph. This is the index of the node
     * object in the node list.
     * @param   n The Node whose ID is queried for
     * @return  The Node's ID as an integer
     * @throws  PreconditionException If <code>n</code> does not exist on
     *          this Graph or is a Null node
     * @throws  PostconditionException If result is not between 0 and
     *          (numNodes()-1). That is, the result is either negative or
     *          greater than or equals the number of nodes in this graph.
     */
    public int nodeId(Node n) throws PreconditionException,
    PostconditionException {
        // Checking pre-condition
        Assertion.pre( hasNode(n), "The node exists in the graph",
                                   "The node must exist in the graph");
        // obtain the index of the node from the node list
        int index = nodeList.indexOf(n);  
        // Checking post-condition
        Assertion.post( index >= 0 && index < numNodes(),
                        "The node's ID is " + index,
                        "The node's ID is out of bounds");
        return index;
    }

    /**
     * Returns the ID of an Edge in this Graph. This is the index of the edge
     * object in the edge list.
     * @param   e The Edge whose ID is queried for
     * @return  The Edges's ID as an integer
     * @throws  PreconditionException If <code>e</code> does not exist on
     *          this Graph or is a Null edge
     * @throws  PostconditionException If result is not between 0 and
     *          (numEdges()-1). That is, the result is either negative or
     *          greater than or equals the number of edges in this graph.
     */
    public int edgeId(Edge e) throws PreconditionException,
    PostconditionException {
        // Checking pre-condition
        Assertion.pre( hasEdge(e), "The edge exists in the graph",
                                   "The edge must exist in the graph");
        // obtain the index of the edge from the edge list
        int index = edgeList.indexOf(e);
        // Checking post-condition
        Assertion.post( index >= 0 && index < numEdges(),
                        "The edge's ID is " + index,
                        "The edge's ID is out of bounds");
        return index;
    }

    /**
     * Retrieves a Node by its unique ID. If the ID is invalid (e.g. out of
     * range), return a Null node.
     * @param   id The unique ID of the Node
     * @return  a node in the Graph with the ID, or a Null node if the graph
     *          has no nodes
     * @throws  PostconditionException If the result is not Null and the
     *          returned node is not a node in the Graph
     * @throws  PostconditionException If the result is Null but a node
     *          corresponding to <code>id</code> exists.
     */
    public Node getNode(int id) throws PostconditionException {
        /**
         * Modified by Andy 27/03/05: 
         *      This method need not to throw a Precondition Exception. All
         *      input params should be handled gracefully.
         */
        // check if ID is in range
        boolean goodID = (id >= 0) && (id < numNodes());
        // if it is, obtain the node, otherwise assign null
        Node n = (goodID)? (Node) nodeList.get(id) : null;
        // Checking post-condition
        if( n != null ) Assertion.post( hasNode(n),
                            "The resulting node exists in the graph",
                            "The resulting node must exist in the graph");
        else Assertion.post( !goodID,
                            "The node does not exist in the graph",
                            "The node does exist but result is null");
        return n;
    }

    /**
     * Returns the number of incoming edges of a specific node. This queries
     * the node for its in-degree.
     * @param   n The node whose in degree is queried for
     * @return  the number of edges going into the node
     * @throws  PreconditionException If <code>n</code> does not exist on
     *          this Graph or is a Null node
     * @throws  PostconditionException If the returned value is not between 0
     *          and numEdges(). That is, the value is either negative or
     *          greater than the number of edges in this graph.
     */
    public int inDegree(Node n) throws PreconditionException,
    PostconditionException {
        // Checking post-condition
        Assertion.pre( hasNode(n), "The node exists in the graph",
                                   "The node must exist in the graph");
        int deg = n.inDegree();     // obtain node's in-degree
        // Checking post-condition
        Assertion.post( deg >= 0, "The in degree of this node is " + deg,
                        "The in degree of this node must not be negative");
        return deg;
    }

    /**
     * Returns the number of outgoing edges of a specific node. This queries
     * the node for its out-degree.
     * @param   n The Node whose out degree is queried for
     * @return  the number of edges from the node as an integer
     * @throws  PreconditionException If <code>n</code> does not exist on
     *          this Graph or is a Null Node
     * @throws  PostconditionException If the returned value is not between 0
     *          and numEdges(). That is, the value is either negative or
     *          greater than the number of edges in this graph.
     */
    public int outDegree(Node n) throws PreconditionException,
    PostconditionException {
        // Checking post-condition
        Assertion.pre( hasNode(n), "The node exists in the graph",
                                   "The node must exist in the graph");
        int deg = n.outDegree();    // obtain node's in-degree

        // Checking post-condition
        Assertion.post( deg >= 0, "The out degree of this node is " + deg,
                        "The out degree of this node must not be negative");
        return deg;
    }

    /**
     * Retrieves an edge by its unique ID. If the ID is invalide (e.g. out of
     * range), return a Null edge.
     * @param   id The unique ID of the edge
     * @return  an edge in the Graph with the ID or a Null edge if the graph
     *          has no edges
     * @throws  PostconditionException If the result is not Null and the
     *          returned edge is not a edge in the Graph
     * @throws  PostconditionException If the result is Null but a edge
     *          corresponding to <code>id</code> exists.     */
    public Edge getEdge(int id) throws PostconditionException {
        /**
         * Modified by Andy 27/03/05: 
         *      This method need not to throw a Precondition Exception. All
         *      input params should be handled gracefully.
         *
         */
        // check if ID is in range
        boolean goodID = (id >= 0) && (id < numEdges());
        // if it is, obtain the node, otherwise assign null
        Edge e = (goodID)? (Edge) edgeList.get(id) : null;
        // Checking post-condition
        if( e != null ) Assertion.post( hasEdge(e),
                            "The resulting edge exists in the graph",
                            "The resulting edge must exist in the graph");
        else  Assertion.post( !hasEdge(e),
                            "The edge does not exist in the graph",
                            "The edge does exist but result is null");
        return e;
    }

    /**
     * Retrieves an edge connecting two specific nodes in this Graph. The
     * Edge is obtained by retrieving the the outgoing edge list of
     * <code>from</code>, the originating node, and traversing the list to
     * match the destinations of the outgoing edges with <code>to</code>, the
     * terminating node. The first matching edge is the Edge we want. If there
     * doesn't exist such edge between the nodes, return a Null edge.
     * @param   from The fromNode of the edge
     * @param   to The toNode of the edge
     * @return  an edge connecting the two nodes in this Graph or a Null edge
     *          if the graph has no nodes
     * @throws  PreconditionException If the two nodes does not exist on this
     *          Graph
     * @throws  PostconditionException If the result is Null but a edge
     *          connecting the two nodes exist.
     * @throws  PostconditionException If the result is not Null and the
     *          fromNode of the returned edge is not <code>from</code> or the
     *          toNode is not <code>to</code>
     * @throws  PostconditionException If the result is not Null and the
     *          returned edge is not an edge in the Graph
     */
    public Edge getEdge(Node from, Node to) throws PreconditionException,
    PostconditionException {
        // Checking pre-condition
        Assertion.pre( hasNode(from) && hasNode(to),
                       "The from and to nodes are in the graph",
                       "The from and to nodes must be in the graph");
        Edge e = null, temp;    // create temporary nodes;
        // retrive the out-going edge list
        List tempList = from.outEdgeList();
        // for every edge, try to match the edge's toNode with "to": 
        for(int i = 0; i < tempList.size(); i++) {
            temp = (Edge) tempList.get(i);
            if(temp.getToNode() == to) {
                e = temp;
                break;
            }
        }
        // Checking post-conditions
        if( e != null ) {
            Assertion.post( hasEdge(e),
                            "The resulting edge exists in the graph",
                            "The resulting edge must exist in the graph");
            Assertion.post( e.getFromNode()==from && e.getToNode()==to,
                            "The edge's nodes matches the query",
                            "The edge's nodes must match the query");
        } else Assertion.post( !hasEdge(e),
                            "The edge does not exist in the graph",
                            "The edge does exist but result is null");
        return e;
    }

    /**
     * Finds the first node in the Graph in order of addition. The node is the
     * first element in the node list. If the graph has no nodes, that is the
     * node list is empty, return a Null node.
     * @return  the first added node in the Graph or a Null node if Graph has
     *          no nodes
     * @throws  PostconditionException If the result is not Null and the
     *          returned node is not a node in the Graph
     * @throws  PostconditionException If the result is Null but the number
     *          of nodes in the graph is not 0.
     */
    public Node firstNode() throws PostconditionException {
        /**
         * Check if the node list is empty. If it is, assign null to "n",
         * otherwise assign the first element in the list to "n".
         */
        Node n = (nodeList.isEmpty())? null : (Node) nodeList.get(0);
        // Checking post-condition
        if (n != null) Assertion.post( hasNode(n),
                            "The resulting node exists in the graph",
                            "The resulting node must exist in the graph");
        else Assertion.post( !hasNode(n),
                            "There is no first node for empty graph",
                            "The graph is not empty but null is returned");
        return n;
    }

    /**
     * Returns the next node, added after a specific node, in the Graph.
     * Firstly, the id of the current node will be obtained. If the ID is that
     * of the last element of the node list, that is the specific node is the
     * last added node, return Null. Otherwise the node with ID of 1 greater
     * than ID of <code>n</code> will be returned. 
     * @return  the next node or a Null node if there is no next Node
     * @throws  PreconditionException If <code>n</code> does not exist on
     *          this Graph or is a Null node
     * @throws  PostconditionException If the result is not Null and the id
     *          of the returned node is not the id of <code>n</code>
     *          incremented by 1
     * @throws  PostconditionException If the result is not Null and the
     *          returned node is not a node in the Graph
     * @throws  PostconditionException If the result is Null but
     *          <code>n</code> is not the last added node
     */
    public Node nextNode(Node n) throws PreconditionException,
    PostconditionException {
        // Checking pre-condition
        Assertion.pre( hasNode(n), "The node exists in the graph",
                                   "The node must exist in the graph");
        // obtain the index of "n" in the node list
        int id = nodeList.indexOf(n); 
        /**
         * if the index is that of the last element, there is no next node.
         * Otherwise the next node is one with ID "id+1"
         */ 
        Node next = (id==(numNodes()-1))? null : (Node)nodeList.get(id+1);

        // Checking post-conditions
        /**
         * Modified by Andy 27/03/05: 
         *      Used to be comparing the param node n with null, whereas it
         *      now compares the "next" node with null.
         */
        if (next != null) {
            Assertion.post( hasNode(next),
                            "The resulting node exists in the graph",
                            "The resulting node must exist in the graph");
            Assertion.post( nodeId(next)==(id+1),
                            "ID of the node is the original ID plus 1",
                            "ID of the node must be the original ID plus 1");
        } else {
            /**
             * Modified by Andy 27/03/05: 
             *      Postcondition changed to check assertion that if null is
             *      returned, the param node must be the last node.
             */
            Assertion.post( id == (numNodes()-1), "There is no next node",
                            "The next node does exist but result is null");
        }
        /**
         * Modified by Andy 27/03/05: 
         *      Used to return the param node n. It now returns the next node
         */
        return next;
    }

    /**
     * Finds the first edge in the Graph in order of addition. The node is the
     * first element in the node list. If the graph has no edges, that is the
     * edge list is empty, return a Null edge.
     * @return  the first added edge in the Graph or Null if this Graph has
     *          no edges
     * @throws  PostconditionException If the result is not Null and the
     *          returned edge is not an edge in the Graph
     */
    public Edge firstEdge() throws PostconditionException {
        /**
         * Check if the edge list is empty. If it is, assign null to "e",
         * otherwise assign the first element in the list to "e".
         */
        Edge e = (edgeList.isEmpty())? null: (Edge) edgeList.get(0);
        // Checking post-condition
        if (e != null) {
            Assertion.post( hasEdge(e),
                            "The resulting edge exists in the graph",
                            "The resulting edge must exist in the graph");
        } else {
            Assertion.post( !hasEdge(e),
                            "There is no first edge for empty graph",
                            "The graph is not empty but null is returned");
        }
        return e;
    }

    /**
     * Returns the next edge, added after a specific edge, in the Graph.
     * Firstly, the id of the current edge will be obtained. If the ID is that
     * of the last element of the edge list, that is, the specific node is the
     * last added edge, return Null. Otherwise the edge with ID of 1 greater
     * than ID of <code>e</code> will be returned..
     * @return  the next edge or a Null edge if there is no next edge
     * @throws  PreconditionException If <code>e</code> does not exist on
     *          this Graph or is a Null edge
     * @throws  PostconditionException If the result is not Null but the id
     *          of the returned edge is not the id of <code>e</code>
     *          incremented by 1
     * @throws  PostconditionException If the result Null but
     *          <code>e</code> is not the last added edge
     * @throws  PostconditionException If the result is not Null but the
     *          returned edge is not a node in the Graph
     */
    public Edge nextEdge(Edge e) throws PreconditionException,
    PostconditionException {
        // Checking pre-condition
        Assertion.pre( hasEdge(e), "The edge exists in the graph",
                                   "The edge must exist in the graph");
        // obtain the index of "e" in the edge list
        int id = edgeList.indexOf(e);
        /**
         * if the index is that of the last element, there is no next edge.
         * Otherwise the next edge is one with ID "id+1"
         */ 
        Edge next = (id==(numEdges()-1))? null : (Edge)edgeList.get(id+1);

        // Checking post-conditions
        /**
         * Modified by Andy 27/03/05: 
         *      Used to be comparing the param edge e with null, whereas it
         *      should be comparing the "next" edge
         */
        if (next != null) {
            Assertion.post( hasEdge(next),
                            "The resulting edge exists in the graph",
                            "The resulting edge must exist in the graph");
            Assertion.post( edgeId(next)==(id+1),
                            "ID of the edge is the original ID plus 1",
                            "ID of the edge must be the original ID plus 1");
        } else {
            /**
             * Modified by Andy 27/03/05: 
             *      Postcondition changed to check assertion that if null is
             *      returned, the param edge must be the last edge.
             */
            Assertion.post( id == (numEdges()-1), "There is no next edge",
                            "The next edge does exist but result is null");
        }
        /**
         * Modified by Andy 27/03/05: 
         *      Used to return the param edge e. It now returns the next edge
         */
        return next;
    }

    /**
     * Returns the first added incoming edge for a specific node in the
     * Graph. This edge is the first edge in the node's incoming edge list. If
     * the node has no incoming edges (having in degree of 0), return Null.
     * @return  The first added incoming edge for the node, or Null if the
     *          node has no incoming edges.
     * @throws  PreconditionException If <code>n</code> does not exist on
     *          this Graph or is a Null node
     * @throws  PostconditionException If the result is Null but the inDegree
     *          of the specified node is not 0
     * @throws  PostconditionException If the result is not Null but the
     *          toNode of the Edge is not <code>n</code>
     * @throws  PostconditionException If the result is not Null but the
     *          returned edge is not an edge in the Graph
     */
    public Edge firstEdgeTo(Node n) throws PreconditionException,
    PostconditionException {
        // Checking pre-condition
        Assertion.pre( hasNode(n), "The node exists in the graph",
                                   "The node must exist in the graph");
        // obtain the incoming edge list of the node
        List tempList = n.inEdgeList();
        /**
         * Check if the edge list is empty. If it is, assign null to "e",
         * otherwise assign the first element in the list to "e".
         */
        Edge e = (tempList.isEmpty())? null : (Edge) tempList.get(0);               
        // Checking post-conditions
        if (e != null) {
            Assertion.post( hasEdge(e),
                            "The resulting edge exists in the graph",
                            "The resulting edge must exist in the graph");
            Assertion.post( e.getToNode() == n,
                        "The edge's to-node matches the supplied node",
                        "The edge's to-node must match the supplied node");
        } else {
            /**
             * Modified by Andy 27/03/05: 
             *      Postcondition changed to check assertion that if null is
             *      returned, the incoming edge list of the node must be
             *      empty. That is, the edge has in degree of 0.
             */
            Assertion.post( tempList.isEmpty(),
                    "No first inward edge for a node with in-degree of 0",
                    "The node's in-degree is not 0 but null is returned");
        }
        return e;
    }

    /**
     * Returns the last added incoming edge for a specific node in the
     * Graph. This edge is the last edge in the node's incoming edge list. If
     * the node has no incoming edges (having in degree of 0), return Null.
     * @return  The last added incoming Edge for the node, or Null if the
     *          node has no incoming edges.
     * @throws  PreconditionException If <code>n</code> does not exist on
     *          this Graph or is a Null node
     * @throws  PostconditionException If the result is Null but the inDegree
     *          of the specified node is not 0
     * @throws  PostconditionException If the result is not Null but the
     *          toNode of the Edge is not <code>n</code>
     * @throws  PostconditionException If the result is not Null but the
     *          returned edge is not an edge in the Graph
     */
    public Edge lastEdgeTo(Node n) throws PreconditionException,
    PostconditionException {
        // Checking pre-condition
        Assertion.pre( hasNode(n), "The node exists in the graph",
                                   "The node must exist in the graph");
        // obtain the incoming edge list of the node
        List tempList = n.inEdgeList();
        // calculate the index of the last element in the list
        int lastIndex = tempList.size()-1;
        /**
         * if the index is not negative, the list is not empty, so assign the
         * edge to "e". Otherwise, assign null to "e".
         */
        Edge e = (lastIndex < 0)? null : (Edge) tempList.get(lastIndex);
        // Checking post-conditions
        if (e != null) {
            Assertion.post( hasEdge(e),
                            "The resulting edge exists in the graph",
                            "The resulting edge must exist in the graph");
            Assertion.post( e.getToNode() == n,
                        "The edge's to-node matches the supplied node",
                        "The edge's to-node must match the supplied node");
        } else {
            /**
             * Modified by Andy 27/03/05: 
             *      Postcondition changed to check assertion that if null is
             *      returned, the incoming edge list of the node must be
             *      empty. That is, the edge has in degree of 0.
             */
            Assertion.post( tempList.isEmpty(),
                    "No last inward edge for a node with in-degree of 0",
                    "The node's in-degree is not 0 but null is returned");
        }
        return e;
    }

    /**
     * Returns the first added outgoing edge for a specific node in the
     * Graph. This edge is the first edge in the node's outgoing edge list. If
     * the node has no outgoing edges (having out degree of 0), return Null.
     * @return  The first added outgoing edge for a node, or Null if the
     *          node has no outgoing edges.
     * @throws  PreconditionException If <code>n</code> does not exist on
     *          this Graph or is a Null Node
     * @throws  PostconditionException If the result is Null but the
     *          outDegree of the specified node is not 0
     * @throws  PostconditionException If the result is not Null but the
     *          fromNode of the Edge is not <code>n</code>
     * @throws  PostconditionException If the result is not Null but the
     *          returned edge is not an edge in the Graph
     */
    public Edge firstEdgeFrom(Node n) throws PreconditionException,
    PostconditionException {
        // Checking pre-condition
        Assertion.pre( hasNode(n), "The node exists in the graph",
                                   "The node must exist in the graph");
        // obtain the outgoing edge list of the node
        List tempList = n.outEdgeList();
        /**
         * Check if the edge list is empty. If it is, assign null to "e",
         * otherwise assign the first element in the list to "e".
         */
        Edge e = (tempList.isEmpty())? null : (Edge) tempList.get(0);        
        // Checking post-conditions
        if (e != null) {
            Assertion.post( hasEdge(e),
                            "The resulting edge exists in the graph",
                            "The resulting edge must exist in the graph");
            Assertion.post( e.getFromNode() == n,
                        "The edge's from-node matches the supplied node",
                        "The edge's from-node must match the supplied node");
        } else {
            /**
             * Modified by Andy 27/03/05: 
             *      Postcondition changed to check assertion that if null is
             *      returned, the outgoing edge list of the node must be
             *      empty. That is, the edge has out degree of 0.
             */
             Assertion.post( tempList.isEmpty(),
                    "No first outward edge for a node with out-degree of 0",
                    "The node's out-degree is not 0 but null is returned");
        }
        return e;
    }

    /**
     * Returns the last added outgoing edge for a specific node in the
     * Graph. This edge is the last edge in the node's outgoing edge list. If
     * the node has no outgoing edges (having out degree of 0), return Null.
     * @return  The last added outgoing edge for a node, or Null if the
     *          node has no outgoing edges.
     * @throws  PreconditionException If <code>n</code> does not exist on
     *          this Graph or is a Null Node
     * @throws  PostconditionException If the result is Null but the
     *          outDegree of the specified node is not 0
     * @throws  PostconditionException If the result is not Null but the
     *          fromNode of the Edge is not <code>n</code>
     * @throws  PostconditionException If the result is not Null but the
     *          returned edge is not an edge in the Graph
     */
    public Edge lastEdgeFrom(Node n) throws PreconditionException,
    PostconditionException {
        // Checking pre-condition
        Assertion.pre( hasNode(n), "The node exists in the graph",
                                   "The node must exist in the graph");
        // obtain the outgoing edge list of the node
        List tempList = n.outEdgeList();
        // calculate the index of the last element in the list
        int lastIndex = tempList.size()-1;
        /**
         * if the index is not negative, the list is not empty, so assign the
         * edge to "e". Otherwise, assign null to "e".
         */
        Edge e = (lastIndex < 0)? null : (Edge) tempList.get(lastIndex);
        // Checking post-conditionss
        if (e != null) {
            Assertion.post( hasEdge(e),
                            "The resulting edge exists in the graph",
                            "The resulting edge must exist in the graph");
            Assertion.post( e.getFromNode() == n,
                        "The edge's from-node matches the supplied node",
                        "The edge's from-node must match the supplied node");
        } else {
            /**
             * Modified by Andy 27/03/05: 
             *      Postcondition changed to check assertion that if null is
             *      returned, the outgoing edge list of the node must be
             *      empty. That is, the edge has out degree of 0.
             */            
            Assertion.post( tempList.isEmpty(),
                    "No last outward edge for a node with out-degree of 0",
                    "The node's out-degree is not 0 but null is returned");
        }
        return e;
    }

    /**
     * Returns the next earliest added incoming edge, which is added after a
     * specific edge, for a specific node in the Graph. Providing this edge
     * exists, it will be in the node's incoming edge list. So the list is
     * first obtained and the index of the edge in the list is checked. If
     * the specified edge is the last incoming edge of the specified node,
     * return Null. Otherwise obtain the edge of index 1 greater than the
     * specified edge.
     * @return  The next earliest added incoming edge for this node, or Null
     *          if the specified Edge is the last incoming edge for the node
     * @throws  PreconditionException If <code>n</code> does not exist on
     *          this Graph or is a Null node
     * @throws  PreconditionException If <code>e</code> does not exist on
     *          this Graph or is a Null edge
     * @throws  PreconditionException If toNode of <code>e</code> is not
     *          <code>n</code>
     * @throws  PostconditionException If the result is Null but
     *          <code>e</code> is not the LAST incoming edge to
     *          <code>n</code>
     * @throws  PostconditionException If the result is not Null but the
     *          returned edge is not an edge in the Graph
     * @throws  PostconditionException If the result is not Null but the
     *          toNode of the returned edge is not <code>n</code>
     * @throws  PostconditionException If the result is not Null but the ID
     *          of the returned edge is less than the ID of <code>e</code>
     */
    public Edge nextEdgeTo(Node n, Edge e) throws PreconditionException,
    PostconditionException {
        // Checking pre-conditions
        Assertion.pre( hasNode(n) && hasEdge(e),
                                "The node and edge exist in the graph",
                                "The node and edge must exist in the graph");
        Assertion.pre( e.getToNode() == n, 
                            "The edge's toNode is the parameter node",
                            "The edge's toNode must be the parameter node");
        // obtain the node's incoming edge list and then index of the edge
        List tempList = n.inEdgeList();
        int id = tempList.indexOf(e);
        /**
         * If the index is not that of the last element, assign the next edge
         * to "next", or else assign null to "next".
         */
        Edge next = (id==(inDegree(n)-1))? null : (Edge)tempList.get(id+1);
        
        // Checking post-conditions
        /**
         * Modified by Andy 27/03/05: 
         *      Used to be comparing the param edge e with null, whereas it
         *      should be comparing the "next" edge
         */
        if (next != null) {
            Assertion.post( hasEdge(next),
                            "The resulting edge exists in the graph",
                            "The resulting edge must exist in the graph");
            Assertion.post( next.getToNode() == n,
                        "The edge's to-node matches the supplied node",
                        "The edge's to-node must match the supplied node");
            Assertion.post( edgeId(next) > edgeId(e),
                    "ID of the edge is higher than the param edge ID",
                    "ID of the edge must be higher than the param edge ID");
        } else {
            /**
             * Modified by Andy 27/03/05: 
             *      Postcondition changed to check assertion that if null is
             *      returned, the param edge must be the last edge to node n.
             */
            Assertion.post( id == (inDegree(n)-1),
                    "No next inward edge for this node",
                    "The next inward edge does exist but null is returned");
        }
        /**
         * Modified by Andy 27/03/05: 
         *      Used to return the param edge e. It now returns the next edge
         */
        return next;
    }

    /**
     * Returns the next earliest added outgoing Edge, which is added after a
     * specific edge, for a specific node in the Graph. Providing this edge
     * exists, it will be in the node's outgoing edge list. So the list is
     * first obtained and the index of the edge in the list is checked. If
     * If the specified edge is the last outgoing edge of the specified node,
     * return Null. Otherwise obtain the edge of index 1 greater than the
     * specified edge.
     * @return  The next earliest added outgoing edge for this node, or Null
     *          if the specified Edge is the last outgoing edge for the node
     * @throws  PreconditionException If <code>n</code> does not exist on
     *          this Graph or is a Null node
     * @throws  PreconditionException If <code>e</code> does not exist on
     *          this Graph or is a Null edge
     * @throws  PreconditionException If fromNode of <code>e</code> is not
     *          <code>n</code>
     * @throws  PostconditionException If the result is Null but
     *          <code>e</code> is not the last outgoing edge to
     *          <code>n</code>
     * @throws  PostconditionException If the result is not Null but the ID
     *          of the returned edge is less than the ID of <code>e</code>
     * @throws  PostconditionException If the result is not Null but the
     *          fromNode of the returned edge is not <code>n</code>
     * @throws  PostconditionException If the result is not Null but the
     *          returned edge is not an edge in the Graph
     */
    public Edge nextEdgeFrom(Node n, Edge e) throws PreconditionException,
    PostconditionException {
        // Checking pre-conditions
        Assertion.pre( hasNode(n) && hasEdge(e),
                                "The node and edge exist in the graph",
                                "The node and edge must exist in the graph");
        Assertion.pre( e.getFromNode() == n, 
                        "The edge's fromNode is the parameter node",
                        "The edge's fromNode must be the parameter node");
        // obtain the node's outgoing edge list and then index of the edge
        List tempList = n.outEdgeList();
        int id = tempList.indexOf(e);
        /**
         * If the index is not that of the last element, assign the next edge
         * to "next", or else assign null to "next".
         */
        Edge next = (id==(outDegree(n)-1))? null : (Edge)tempList.get(id+1);
        
        // Checking post-conditions
        /**
         * Modified by Andy 27/03/05: 
         *      Used to be comparing the param edge e with null, whereas it
         *      should be comparing the "next" edge
         */
        if (next != null) {
            Assertion.post( hasEdge(next),
                            "The resulting edge exists in the graph",
                            "The resulting edge must exist in the graph");
            Assertion.post( next.getFromNode() == n,
                        "The edge's from-node matches the supplied node",
                        "The edge's from-node must match the supplied node");
            Assertion.post( edgeId(next) > edgeId(e),
                    "ID of the edge is higher than the param edge ID",
                    "ID of the edge must be higher than the param edge ID");
        } else {
            /**
             * Modified by Andy 27/03/05: 
             *      Postcondition changed to check assertion that if null is
             *      returned, the param edge must be the last edge from node
             *      n.
             * Modified by Andy 02/04/05: 
             *      Check for out degree instead of in degree
             */
            Assertion.post( id == (outDegree(n)-1),
                    "No next outward edge for this node",
                    "The next outward edge does exist but null is returned");
        }
        /**
         * Modified by Andy 27/03/05: 
         *      Used to return the param edge e. It now returns the next edge
         */        
        return next;
    }

    /**
     * Adds a node to this Graph. At this stage, the label should be either
     * "SERVER" or "CLIENT". The node should have a set of coordinates
     * assigned. However is this position should be free. If not, a new set
     * of coordinate should be generated.
     *
     * @param   n A node template the created node is based on.
     * @throws  PostconditionException If the node added is not contained in
     *          the Graph.
     * @throws  PostconditionException If the number of nodes in the Graph
     *          has not been incremented by 1
     */
    public void addNode (Node n) throws PostconditionException {
        /**
         * Attempt to assign the coordinate for displaying the node. If the
         * intended position is occupied, a new position will be generated.
         */
        int[] coord = n.getCoordinates();
        if (!reference[coord[0]][coord[1]]) {
            reference[coord[0]][coord[1]] = true;
        } else {
            /**
             * Modified by Andy 27/03/05:  
             *      prompts that a new set of coordinates will be assigned
             */
            Debugger.debug("("+ coord[0] +","+ coord[1] +") is already " +
                            "occupied. The node is being relocated...");            
            int x, y;
            do {
                x = prng.nextInt(maxXWidth);
                y = prng.nextInt(maxYHeight);
            } while (reference[x][y]==true);
            Debugger.debug("("+ x +","+ y +") is the new position");
            reference[x][y]=true;
            n.setCoordinates(x,y);
        }
        int numBefore = numNodes(); // get count for postcondition checking
        nodeList.add(n);            // add the node to the graph
        
        // increment appropriate counter
        if ( (n.getLabel().toUpperCase()).startsWith("SERVER") ) numServer++;
        else if ((n.getLabel().toUpperCase()).startsWith("CLIENT")) 
                                                                numClient++;
        // Checking post-conditions
        Assertion.post( hasNode(n), "The new node is now in the Graph",
                                        "The new node is NOT in the Graph");
        Assertion.post( numNodes() == (numBefore+1),
                        "The number of nodes is incremented by 1",
                        "The number of nodes must be incremented by 1");     
    }

    /**
     * Modified by Andy 27/03/05: 
     *      method addNode(String l, Node n) removed, as the functionality is 
     *      redundant to the constructor in NodeImp. The method block below
     *      shall be removed in the next revision.
     */
    /**
     * Creates a node using a node template and adds this node to this Graph.
     * This node should be given a descriptive label, since none were
     * specified in the template. At this stage, the label should be either
     * "SERVER" or "CLIENT". Details such as capacity, operating
     * efficiency, failure rate, and the number of geneartions required for
     * repair are obtained from the template node. <p>
     *
     * A node needs to be assigned a set of coordinates to a free position
     * for display. This position will be determined in a loop checking for
     * the availablility in a reference matrix. Once a position is assigned,
     * that position will be marked as occupied.<p>
     *
     * In order to provide some graph statistics, the type in the label of
     * each node is also checked. Counters for Servers and Clients are 
     * incremented where appropriate.
     *
     * @return  the newNode created for reference
     * @param   label A descriptive label for the node to be created
     * @param   n A node template the created node is based on.
     * @throws  PreconditionException If <code>label</code> is Null.
     * @throws  PreconditionException If <code>n</code> is Null.
     * @throws  PostconditionException If the created/returned node is not
     *          contained in the Graph.
     * @throws  PostconditionException If the number of nodes in the Graph
     *          has not been incremented by 1
     */
/*    public Node addNode (String l, int x, int y, Node n)
    throws PreconditionException, PostconditionException {
        // Checking pre-conditions
        Assertion.pre( label != null, "The label is " + label,
                                      "The label must not be null");
        Assertion.pre( n != null, "The template node is not null",
                                  "The template node must not be null");
*/
        /**
         * Attempt to find a coordinate for displaying the node. This is
         * repeated until finding an empty position.
         */
/*        int x, y;
        do {
            x = prng.nextInt(maxXWidth);
            y = prng.nextInt(maxYHeight);
        } while (reference[x][y]==true);    
        reference[x][y] = true;
        // increment appropriate counter
        if ( (label.toUpperCase()).startsWith("SERVER") ) numServer++;
        else if ( (label.toUpperCase()).startsWith("CLIENT") ) numClient++;

        Node newNode = new NodeImp(label, x, y, n); // create the node
        
        int numBefore = numNodes(); // get count for postcondition checking
        nodeList.add(newNode);      // add the node to the graph
        // Checking post-conditions
        Assertion.post( hasNode(newNode), "The new node is now in the Graph",
                                        "The new node is NOT in the Graph");
        Assertion.post( numNodes() == (numBefore+1),
                        "The number of nodes is incremented by 1",
                        "The number of nodes must be incremented by 1");     
        return newNode; 
    }*/


    /**
     * Adds an edge from an edge to this Graph. The edge should specify the 
     * nodes which it intends to connect within the graph.<p>
     *
     * @param   e The edge to be added to the graph
     * @throws  PreconditionException If the from and/or to nodes of the edge
     *          does not exist on this Graph
     * @throws  PostconditionException If the edge does not exist in the
     *          Graph
     * @throws  PostconditionException If the number of edges in the Graph
     *          has not been incremented by 1
     * @throws  PostconditionException If the out-degree of <code>from</code>
     *          in-degree of <code>to</code> are not incremented by 1
     */
    public void addEdge (Edge e)
    throws PreconditionException, PostconditionException {
        Node from = e.getFromNode();
        Node to = e.getToNode();
        // Checking pre-conditions
        Assertion.pre( hasNode(from) && hasNode(to),
                       "The from and to nodes are in the graph",
                       "The from and to nodes must be in the graph");
        
        /**
         * # Modified by Andy 08/04/05:
         *      Included check for available resource at the destination
         *      node. If there is enough resource, continue to the later
         *      stages. However if this connection cannot be added. We will
         *      simply ignore it.
         */
        if(1.0 - ((NodeImp)to).getEfficiency() < NODE_INCREMENT) {
            Debugger.debug("Destination node cannot accept any more " +
                            "connections, because it has already reached " +
                            "its maximum operating efficiency. No changes " +
                            "to the graph were made.");
            return;
        }
        
        // get relevant counts for postcondition checking
        int outDeg = outDegree(from); 
        int inDeg = inDegree(to);
        int numBefore = numEdges();
        // add the edge into the graph
        edgeList.add(e);
        /**
         * # Modified by Andy 27/03/05: 
         *      This method assumed that from and to nodes of the edge will
         *      have references to the edge in its relevant edgelist. However
         *      a risk of invalid node property may arise with node degrees.
         *      Thus edge should specify the nodes that it should connect,
         *      and this method will make the actual connection.
         */
        from.addOutEdge(e);
        to.addInEdge(e);
        /**
         * # Modified by Andy 22/04/05: 
         *      Increases node usage for servers when initiating or receiving
         * a connection
         */
        if (from.getLabel().startsWith("SERVER"));
            ((NodeImp)from).increUsage(NODE_INCREMENT);
        if (to.getLabel().startsWith("SERVER"));
            ((NodeImp)to).increUsage(NODE_INCREMENT);

        // Checking post-conditions
        Assertion.post( hasEdge(e), "The new edge is now in the Graph",
                                        "The new edge is NOT in the Graph");
        Assertion.post( numEdges() == (numBefore+1),
                        "The number of edges is incremented by 1",
                        "The number of edges must be incremented by 1");             
        /**
         * Modified by Andy 27/03/05: 
         *      ensures the respective in and out degrees of the relevant
         *      node are incremented
         */
        
        Assertion.post( outDegree(from)==(outDeg+1)&&inDegree(to)==(inDeg+1),
                    "The out and in-degree of nodes were incremented by 1",
                    "The out and in-degree of nodes were not incremented");
    }
	 
	 	 
    /**
     * Modified by Andy 27/03/05: 
     *      method removed, as the functionality is redundant to the EdgeImp
     *      constructor
     */
        
    /**
     * Creates an edge from a template edge and adds the created edge to this
     * Graph. The edge should also be given a descriptive label, and some
     * information regarding the fromNode and toNode.<p>
     *
     * Other information regarding the capacity, efficiency, cost, the
     * probability of failure, and the thresh hold for that probability, and
     * the number of generations required for repair, is extracted from this
     * this template edge.<p>
     *
     * @return  the newEdge created for reference
     * @param   from The departure node of the edge
     * @param   to The arrival node of the edge
     * @param   label The descriptive label of the edge
     * @param   e An edge template the created edge is based on.
     * @throws  PreconditionException If the two nodes does not exist on this
     *          Graph
     * @throws  PreconditionException If <code>label</code> is Null.
     * @throws  PreconditionException If <code>e</code> is Null.
     * @throws  PostconditionException If there's an edge connecting the
     *          supposed from and to node in the Graph
     * @throws  PostconditionException If the number of edges in the Graph
     *          has not been incremented by 1
     * @throws  PostconditionException If the out-degree of <code>from</code>
     *          in-degree of <code>to</code> are not incremented by 1
     */
 /*   public Edge addEdge (Node from, Node to, String label, Edge e)
    throws PreconditionException, PostconditionException {
        // Checking pre-conditions
        Assertion.pre( hasNode(from) && hasNode(to),
                       "The from and to nodes are in the graph",
                       "The from and to nodes must be in the graph");
        Assertion.pre( label != null, "The label is " + label,
                                      "The label must not be null");
        Assertion.pre( e != null, "The template edge is not null",
                                  "The template edge must not be null");
        // create the edge
        Edge newEdge = new EdgeImp(from,to,label,e);
        // get relevant counts for postcondition checking
        int outDeg = outDegree(from); 
        int inDeg = inDegree(to);
        int numBefore = numEdges();
        // add the edge into the graph
        edgeList.add(newEdge);
        // connect the from adn to nodes with the edge
        from.addOutEdge(newEdge);
        to.addInEdge(newEdge);
        // Checking post-conditions
        Assertion.post( hasEdge(from,to), "The new edge is now in the Graph",
                                        "The new edge is NOT in the Graph");
        Assertion.post( numEdges() == (numBefore+1),
                        "The number of edges is incremented by 1",
                        "The number of edges must be incremented by 1");     
        Assertion.post( outDegree(from)==(outDeg+1)&&inDegree(to)==(inDeg+1),
                    "The out and in-degree of nodes were incremented by 1",
                    "The out and in-degree of nodes were not incremented");
        return newEdge;
    }*/

    /**
     * implementing the GraphStat interface
     */

    /**
     * Returns the number of Server nodes in the network. 
     * @return  the number of Servers
     * @throws  PostconditionException If the returned number is negative.
     */
    public int numServers() throws PostconditionException {
        Assertion.post( numServer >= 0,
                        "The number of Servers " + numServer,
                        "The number of Servers must not be negative");
        return numServer;
    }

    /**
     * Returns the number of Client nodes in the network.
     * @return  the number of Clients
     * @throws  PostconditionException If the returned number is negative.
     */
    public int numClients() throws PostconditionException {
        Assertion.post( numClient >= 0,
                        "The number of Clients " + numClient,
                        "The number of Clients must not be negative");
        return numClient;
    }

    /**
     * Returns the degree of pleiotropy of a node provided to other Server
     * nodes only. This is defined as the ratio between the number of other
     * Server nodes this node is currently serving (or connected to), and the 
     * total number of Servers that the node can possibly be connected to.
     * The number of servers that this node is currently serving (or
     * connected to) is obtained from the stats array of the node.
     * @return  the degree of pleiotropy due to servers
     * @see     Node
     */
    public double pleioToServer(Node n) {
        int[] numLinks = n.getStats();
        /**
         * Modified by Andy 27/03/05: 
         *      int/int results in int so must cast to double
         */
        double pleio = (double)numLinks[0] / (double)numServer;
        return pleio;
    }

    /**
     * Returns the degree of pleiotropy of a node provided to other Client
     * nodes only. This is defined as the ratio between the number of other
     * Client nodes this node iscurrently  serving (or connected to), and the
     * total number of Clients that the node can possibly be connected to.
     * The number of clients that this node is currently serving (or
     * connected to) is obtained from the stats array of the node.
     * @return  the degree of pleiotropy due to clients
     * @see     Node
     */
    public double pleioToClient(Node n) {
        int[] numLinks = n.getStats();
        /**
         * Modified by Andy 27/03/05: 
         *      int/int results in int so must cast to double
         */
        double pleio = (double)numLinks[2] / (double)numClient;
        return pleio;
    }
    /**
     * Returns the degree of pleiotropy of a node provided to all other
     * nodes. This is defined as the ratio between the number of other nodes
     * this node is currently serving (or connected to), and the total number
     * of other nodes that can possibly be served (or connected to).
     * @return  the degree of pleiotropy due to all nodes
     */
    public double pleioToAll(Node n) {
        /**
         * Modified by Andy 27/03/05: 
         *      int/int results in int so must cast to double
         */
        double pleio = (double)n.outDegree() / (double)numNodes();
        return pleio;
    }

    /**
     * Returns the degree of redundancy of a node provided by other Server
     * nodes only. This is defined as the ratio between the number of other
     * Server nodes currently serving this node (or accepting connection 
     * from), and the total number of Servers that can possibly serve (or 
     * connect to) this node. The number of servers that currently serve (or
     * connect to) this node is obtained from the stats array of the node.
     * @return  the degree of redundancy due to servers
     * @see     Node
     */
    public double redunFromServer(Node n) {
        int[] numLinks = n.getStats();
        /**
         * Modified by Andy 27/03/05: 
         *      int/int results in int so must cast to double
         */
        double redun = (double)numLinks[1] / (double)numServer;
        return redun;
    }

    /**
     * Returns the degree of redundancy of a node provided by other Client
     * nodes only. This is defined as the ratio between the number of other
     * Client nodes currently serving this node (or accepting connection
     * from), and the total number of Clients that can possibly serve (or
     * connect to) this node. The number of clients that currently serve (or
     * connect to) this node is obtained from the stats array of the node.
     * @return  the degree of redundancy due to clients
     * @see     Node
     */
    public double redunFromClient(Node n) {
        int[] numLinks = n.getStats();
        /**
         * Modified by Andy 27/03/05: 
         *      int/int results in int so must cast to double
         */
        double redun = (double)numLinks[3] / (double)numClient;
        return redun;
    }

    /**
     * Returns the degree of redundancy of a node provided by all other
     * nodes. This is defined as the ratio between the number of other nodes
     * that currently serve (or connect to) this node, and the total number
     * of other nodes that can possibly serve (or connect to) this node.
     * @return  the degree of redundancy due to all nodes
     */
    public double redunFromAll(Node n) {
        /**
         * Modified by Andy 27/03/05: 
         *      int/int results in int so must cast to double
         */
        double pleio = (double)n.inDegree() / (double)numNodes();
        return pleio;
    }

    /**
     * Return the clustering factor of this node. This is defined as the
     * ratio between the number of links this node has, and the total number
     * of nodes that can possibly connect to this node.
     * @return  the clustering factor
     * @see     Node
     */
    public double clusterFactor(Node n) {
        int numLinks = sumIntArray(n.getStats());
        /**
         * Modified by Andy 27/03/05: 
         *      int/int results in int so must cast to double
         */
        double clustering = (double)numLinks / (double)numNodes();
        return clustering;
    }

    /**
     * Sums the values in an integer array
     * @param   a An integer array
     * @return  the sum of all values
     */
    private int sumIntArray(int[] a) {
        int sum = 0;
        for(int i = 0; i < a.length; i++) sum += a[i];
        return sum;
    }
}
