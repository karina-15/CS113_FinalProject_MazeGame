package edu.miracosta.cs113;

import java.util.Iterator;
import java.util.Scanner;

/** Abstract base class for graphs. A graph is a set
 * of vertices and a set of edges. Vertices are
 * represented by integers from 0 to n - 1. Edges
 * are ordered pairs of vertices.
 */
public abstract class AbstractGraph implements Graph{

    // Data fields

    // Flag to indicate whether this is a directed graph
    private boolean directed;
    // The number of vertices.
    private int numV;

    // Constructors

    /** Construct a graph with the specified number of vertices
     * and the directed flag. If the directed flag is true,
     * this is a directed graph.
     * @param numV      The number of vertices
     * @param directed  The directed flag
     */
    public AbstractGraph(int numV, boolean directed) {
        this.numV = numV;
        this.directed = directed;
    }

    /** Default constructor
     * Constructs an empty graph with no vertices and is undirected.
     */
    public AbstractGraph() {
        numV = 0;
        directed = false;
    }

    // Methods

    // Accessors

    /** Return the number of vertices.
     * @return  The number of vertices
     */
    @Override
    public int getNumV() {
        return numV;
    }

    /** Return whether this is a directed graph.
     * @return  true if this is a directed graph
     */
    @Override
    public boolean isDirected() {
        return directed;
    }

    // Other methods that override Graph Interface methods
    @Override
    public abstract void insert(Edge edge);

    @Override
    public abstract boolean isEdge(int source, int dest);

    @Override
    public abstract Edge getEdge(int source, int dest);

    @Override
    public abstract Iterator<Edge> edgeIterator(int source);

    // Other methods

    /** Load the edges of a graph from the data in an input file.
     * The file should contain a series of lines, each line
     * with 2 or 3 data values. The 1st is the source,
     * the 2nd is the destination, and the optional 3rd
     * is the weight.
     * @param scan  The Scanner connected to the data file
     */
    public void loadEdgesFromFile(Scanner scan) {
        String line;
        int source, dest;
        double weight;
        scan.nextLine();
        // while end of file has not been reached
        while(scan.hasNextLine()) {
            // read line
            line = scan.nextLine();
            // split line into strings
            String values[] = line.split("\\s+");
            // get 1st string as source
            source = Integer.parseInt(values[0]);
            // get 2nd string as dest
            dest = Integer.parseInt(values[1]);
            // check if weight is provided
            if(values.length == 3) {
                weight = Double.parseDouble(values[2]);
            } else {    // set weight to 1.0 if no weight provided
                weight = 1.0;
            }
            // create Edge object
            Edge edge = new Edge(source, dest, weight);
            // insert edge into graph
            insert(edge);
        }
    }

    /** Factory method to create a graph and load the data from an input
     * file. The 1st line of the input file should contain the number
     * of vertices. The remaining lines should contain the edge data as
     * described under loadEdgesFromFile.
     * @param scan          The Scanner connected to the data file
     * @param isDirected    true if this is a directed graph,
     *                      false otherwise
     * @param type          The string "Matrix" if an adjacency matrix is to be
     *                      created, and the string "List" if an adjacency list
     *                      is to be created
     * @return              Graph
     */
    public static Graph createGraph(Scanner scan,
                                    boolean isDirected,
                                    String type) {
        int numV = scan.nextInt();
        AbstractGraph returnValue = null;
        if(type.equalsIgnoreCase("Matrix")) {
            returnValue = new MatrixGraph(numV, isDirected);
        }
        else if(type.equalsIgnoreCase("List")) {
            returnValue = new ListGraph(numV, isDirected);
        }
        else {
            throw new IllegalArgumentException();
        }
        returnValue.loadEdgesFromFile(scan);
        return returnValue;
    }
}
