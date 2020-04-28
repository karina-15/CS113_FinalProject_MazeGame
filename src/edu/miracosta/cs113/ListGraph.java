package edu.miracosta.cs113;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/** A ListGraph is an extension of the AbstractGraph abstract class
 * that uses an array of lists to represent the edges.
 */
public class ListGraph extends AbstractGraph {

    // Data fields

    // An array of Lists to contain the edges that
    // originate with each vertex.
    private List<Edge>[] edges;

    // Constructors

    /** Construct a graph with the specified number of
     * vertices and directionality.
     * @param numV      The number of vertices
     * @param directed  The directionality flag
     */
    public ListGraph(int numV, boolean directed) {
        super(numV, directed);
        edges = new List[numV];
        for(int i = 0; i < numV; i++) {
            edges[i] = new LinkedList<Edge>();
        }
    }

    // Methods

    /** Determines whether an edge exists.
     * @param source    The source vertex
     * @param dest      The destination vertex
     * @return          true if there is an edge from
     *                  source to dest
     */
    public boolean isEdge(int source, int dest) {
        return edges[source].contains(new Edge(source, dest));
    }

    /** Insert a new edge into the graph.
     * @param edge  The new edge
     */
    public void insert(Edge edge) {
        edges[edge.getSource()].add(edge);
        if(!isDirected()) {
            edges[edge.getDest()].add(new Edge(edge.getDest(),
                    edge.getSource(),
                    edge.getWeight()));
        }
    }

    /** Returns the iterator to the edges that originate from a
     * given vertices.
     * @param source    The source
     * @return          the iterator to the edges
     */
    public Iterator<Edge> edgeIterator(int source) {
        return edges[source].iterator();
    }

    /** Get the edge between two vertices. If an
     * edge does not exist, an Edge with a weight
     * of Double.POSITIVE_INFINITY is returned.
     * @param source    The source
     * @param dest      The destination
     * @return          the edge b/w these 2 vertices
     */
    public Edge getEdge(int source, int dest) {
        Edge target =
                new Edge(source, dest, Double.POSITIVE_INFINITY);
        for(Edge edge : edges[source]) {
            if(edge.equals(target)) {
                return edge;    // Desired edge found, return it.
            }
        }
        // Assert: All edges for source checked.
        return target;  // Desired edge not found.
    }
}




























