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

/**
 * This interface specifies the mandatory functionality for the Graph ADT,
 * which is the underlying data structure of Networks in NetSim.<p>
 *
 * Uses design by contract style assertions to specify semantic properties
 * for classes implementing this interface. All implementing classes will use
 * the <code>Assertion</code> class in the package <code>utility</code> to
 * perform such checks.<p>
 *
 * Some properties of the Graph are:
 * <ul><li>Nodes and Edges contains descriptive labels.
 *     <li>Nodes within a Graph are identified by unique IDs.
 *     <li>Edges within a Graph are identified either by its unique ID or the
 *          Nodes associated with the Edge.
 *     <li>A given edge is a member of one and only one graph.
 *     <li>There are no relationships between Graphs.</ul>
 *
 * @author          Andy Hao-Wei Lo
 * @author          Zhiyang Ong
 * @version         0.3.6-andy
 * @since           0.2
 * @acknowledgement Matthew Berryman, Wei-li Khoo and Hiep Nguyen, and code
 *                  obtained from Henry Detmold for Programming Techniques,
 *                  Assignment 1, available <a href="http://www.cs.adelaide.edu.au/users/third/pt/assignments/assignment1.tgz">here</a>.
 */
public interface Graph {
    /**
     * Returns the number of nodes in the Graph.
     * @return  number of nodes in the Graph
     * @throws  PostconditionException If returned value is negative (<0)
     */
    public int numNodes() throws PostconditionException;

    /**
     * Returns the number of edges in the Graph.
     * @return  number of edges in the Graph
     * @throws  PostconditionException If returned value is negative (<0)
     */
    public int numEdges() throws PostconditionException;

    /**
     * Queries the Graph to check if a Node exists in this graph.
     * @param   n A node whose existence will be determined
     * @return  a Boolean, <code>true</code> if <code>n</code> exists
     */
    public boolean hasNode(Node n);

    /**
     * Queries the Graph to check if an Edge exists in this graph.
     * @param   e An Edge whose existence will be determined
     * @return  a Boolean, <code>true</code> if <code>e</code> exists
     */
    public boolean hasEdge(Edge e);

    /**
     * Queries the Graph to check if an Edge exists between two nodes of this
     * Graph.
     * @param   from The fromNode of the Edge
     * @param   to The toNode of the Edge
     * @return  a Boolean, <code>true</code> if an Edge exists between
     *          <code>from</code> and <code>to</code>
     * @throws  PreconditionException If the two nodes does not exist on this
     *          Graph
     */
    public boolean hasEdge(Node from, Node to) throws PreconditionException;

    /**
     * Returns the ID of a Node in this Graph.
     * @param   n The Node whose ID is queried for
     * @return  The Node's ID as an integer
     * @throws  PreconditionException If <code>n</code> does not exist on
     *          this Graph or is a Null node
     * @throws  PostconditionException If result is not between 0 and
     *          (numNodes()-1). That is, the result is either negative or
     *          greater than or equals the number of nodes in this graph.
     */
    public int nodeId(Node n) throws PreconditionException,
    PostconditionException;

    /**
     * Returns the ID of an Edge in this Graph.
     * @param   e The Edge whose ID is queried for
     * @return  The Edges's ID as an integer
     * @throws  PreconditionException If <code>e</code> does not exist on
     *          this Graph or is a Null edge
     * @throws  PostconditionException If result is not between 0 and
     *          (numEdges()-1). That is, the result is either negative or
     *          greater than or equals the number of edges in this graph.
     */
    public int edgeId(Edge e) throws PreconditionException,
    PostconditionException;

    /**
     * Retrieves a Node by its unique ID. If the graph has no nodes, or if the
     * ID is out of range, return a Null node.
     * @param   id The unique ID of the Node
     * @return  a node in the Graph with the ID, or a Null node if the graph
     *          has no nodes
     * @throws  PostconditionException If the result is not Null and the
     *          returned node is not a node in the Graph
     * @throws  PostconditionException If the result is Null but a node
     *          corresponding to <code>id</code> exists.
     */
    public Node getNode(int id) throws PostconditionException;
    /**
     * Modified by Andy 27/03/05: 
     *      This method need not to throw a Precondition Exception. All input
     *      params should be handled gracefully.
     */

    /**
     * Returns the number of incoming edges of a specific node
     * @param   n The node whose in degree is queried for
     * @return  the number of edges going into the node
     * @throws  PreconditionException If <code>n</code> does not exist on
     *          this Graph or is a Null node
     * @throws  PostconditionException If the returned value is not between 0
     *          and numEdges(). That is, the value is either negative or
     *          greater than the number of edges in this graph.
     */
    public int inDegree(Node n) throws PreconditionException,
    PostconditionException;

    /**
     * Returns the number of outgoing edges of a specific node
     * @param   n The Node whose out degree is queried for
     * @return  the number of edges from the node as an integer
     * @throws  PreconditionException If <code>n</code> does not exist on 
     *          this Graph or is a Null Node
     * @throws  PostconditionException If the returned value is not between 0
     *          and numEdges(). That is, the value is either negative or
     *          greater than the number of edges in this graph.
     */
    public int outDegree(Node n) throws PreconditionException,
    PostconditionException;

    /**
     * Retrieves an edge by its unique ID. If the Graph has no edges or the ID
     * id invalid, return a Null edge.
     * @param   id The unique ID of the edge
     * @return  an edge in the Graph with the ID or a Null edge if the graph
     *          has no edges or the Id is invalide
     * @throws  PostconditionException If the result is not Null and the
     *          returned edge is not a edge in the Graph
     * @throws  PostconditionException If the result is Null but a edge
     *          corresponding to <code>id</code> exists.     */
    public Edge getEdge(int id) throws PostconditionException;
    /**
     * Modified by Andy 27/03/05: 
     *      This method need not to throw a Precondition Exception. All input
     *      params should be handled gracefully.
     */

    /**
     * Retrieves an edge connecting two specific nodes in this Graph. If the
     * graph has no nodes, return a Null edge.
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
    PostconditionException;
    /**
     * This version returns only one edge, later version may return a list of
     * Edges.
     */


    /**
     * Finds the first node in the Graph in order of addition. If the graph 
     * has no nodes, return a Null node.
     * @return  the first added node in the Graph or a Null node if Graph has
     *          no nodes
     * @throws  PostconditionException If the result is not Null and the
     *          returned node is not a node in the Graph
     * @throws  PostconditionException If the result is Null but the number 
     *          of nodes in the graph is not 0.
     */
    public Node firstNode() throws PostconditionException;

    /**
     * Returns the next node, added after a specific node, in the Graph. If
     * the specific node is the last added node, return Null.
     * @return  the next node or a Null node if there is no next Node
     * @throws  PreconditionException If <code>n</code> does not exist on
     *          this Graph or is a Null node
     * @throws  PostconditionException If the result is not Null and the id
     *          of the returned node is not the id of <code>n</code>
     *          incremented by 1
     * @throws  PostconditionException If the result is Null but
     *          <code>n</code> is not the last added node
     * @throws  PostconditionException If the result is not Null and the
     *          returned node is not a node in the Graph
     */
    public Node nextNode(Node n) throws PreconditionException,
    PostconditionException;

    /**
     * Finds the first edge in the Graph in order of addition. If the graph
     * has no edges, return a Null edge.
     * @return  the first added edge in the Graph or Null if this Graph has
     *          no edges
     * @throws  PostconditionException If the result is not Null and the
     *          returned edge is not an edge in the Graph
     */
    public Edge firstEdge() throws PostconditionException;

    /**
     * Returns the next edge, added after a specific edge, in the Graph. If
     * the specific Edge is the last added Edge, return null.
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
    PostconditionException;

    /**
     * Returns the first added incoming edge for a specific node in the
     * Graph. If the node has no incoming edges (having in degree of 0),
     * return Null.
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
    PostconditionException;

    /**
     * Returns the last added incoming edge for a specific node in the
     * Graph. If the node has no incoming edges (having in degree of 0),
     * return Null.
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
    PostconditionException;

    /**
     * Returns the first added outgoing edge for a specific node in the
     * Graph. If the node has no outgoing edges (having out degree of 0),
     * return Null.
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
    PostconditionException;

    /**
     * Returns the last added outgoing edge for a specific node in the
     * Graph. If the node has no outgoing edges (having out degree of 0),
     * return Null.
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
    PostconditionException;
    
    /**
     * Returns the next earliest added incoming edge, which is added after a
     * specific edge, for a specific node in the Graph. If the specified edge
     * is the last incoming edge of the specified node, return Null.
     * @return  The next earliest added incoming edge for this node, or Null
     *          if the specified Edge is the last incoming edge for the node
     * @throws  PreconditionException If <code>n</code> does not exist on
     *          this Graph or is a Null node
     * @throws  PreconditionException If <code>e</code> does not exist on
     *          this Graph or is a Null edge
     * @throws  PreconditionException If toNode of <code>e</code> is not
     *          <code>n</code>
     * @throws  PostconditionException If the result is Null but
     *          <code>e</code> is not the last incoming edge to
     *          <code>n</code>
     * @throws  PostconditionException If the result is not Null but the ID
     *          of the returned edge is less than the ID of <code>e</code>
     * @throws  PostconditionException If the result is not Null but the
     *          toNode of the returned edge is not <code>n</code>
     * @throws  PostconditionException If the result is not Null but the
     *          returned edge is not an edge in the Graph
     */
    public Edge nextEdgeTo(Node n, Edge e) throws PreconditionException,
    PostconditionException;
    
    /**
     * Returns the next earliest added outgoing Edge, which is added after a
     * specific edge, for a specific node in the Graph. If the specified edge
     * is the last outgoing edge of the specified node, return Null.
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
    PostconditionException;

    /**
     * Adds a node to this Graph. At this stage, the label should be either
     * "SERVER" or "CLIENT". The node should have a set of coordinates
     * assigned. 
     * @param   n A node template the created node is based on.
     * @throws  PostconditionException If the node added is not contained in
     *          the Graph.
     * @throws  PostconditionException If the number of nodes in the Graph
     *          has not been incremented by 1
     */
    public void addNode(Node n) throws PostconditionException;

    /**
     * Creates a node using a node template and adds this node to this Graph.
     * This node should be given a descriptive label, since none were
     * specified in the template. At this stage, the label should be either
     * "SERVER" or "CLIENT". Details such as capacity, operating
     * efficiency, failure rate, and the number of geneartions required for
     * repair are obtained from the template node.
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
/*    public Node addNode(String label, Node n)
    throws PreconditionException, PostconditionException; 
*/

    /**
     * Adds an edge from an edge to this Graph. The edge should already 
     * connect nodes within the graph.<p>
     *
     * @param   e The edge to be added to the graph
     * @throws  PreconditionException If the from and/or to nodes of the edge
     *          does not exist on this Graph
     * @throws  PostconditionException If the intended edge exists in the
     *          Graph
     * @throws  PostconditionException If the number of edges in the Graph
     *          has not been incremented by 1
     */
    public void addEdge(Edge e)
    throws PreconditionException, PostconditionException;

    /**
     * Creates an edge using an edge template and adds this edge to this
     * Graph. The edge should also be created with a descriptive label, and
     * information regarding the fromNode and toNode of this edge.<p>
     *
     * Information, regarding the capacity, efficiency, cost, failure rate
     * and the number of generations required for repair, is obtained from
     * this template edge.<p>
     *
     * Note: The cost maybe the cost of transferring data along this Edge, or
     * simply the Euclidean distance between the nodes this Edge connects.
     * Since edges are directed, the first node is the source node for the
     * edge and the latter is the destination.
     * @return  the newEdge created for reference
     * @param   from The departure node of the edge
     * @param   to The arrival node of the edge
     * @param   label The descriptive label of the edge
     * @param   e An edge template the created edge is based on.
     * @throws  PreconditionException If the two nodes does not exist on this
     *          Graph
     * @throws  PreconditionException If <code>label</code> is Null.
     * @throws  PreconditionException If <code>e</code> is Null.
     * @throws  PostconditionException If edge is not contained in the Graph
     * @throws  PostconditionException If the number of edges in the Graph
     *          has not been incremented by 1
     * @throws  PostconditionException If the out-degree of <code>from</code>
     *          in-degree of <code>to</code> are not incremented by 1
     */
/*    public Edge addEdge(Node from, Node to, String label, Edge e)
    throws PreconditionException, PostconditionException;
*/
}
