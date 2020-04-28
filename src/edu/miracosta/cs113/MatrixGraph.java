package edu.miracosta.cs113;

import java.util.*;

public class MatrixGraph extends AbstractGraph {

    // Data fields

    // 2D array holds vertices connected by edges
    private double[][] edges;

    // Constructors

    // create directed graph
    public MatrixGraph(int numV, boolean directed) {
        super(numV, directed);
        // create edges connecting vertices
        if(numV > 0) {
            edges = new double[numV][numV];
        }
        // fill in 2D array with positive infinity
        for(int i = 0; i < numV; i++) {
            for(int j = 0; j < numV; j++) {
                edges[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    /** create undirected graph based on World
     * edges are determined by up, down, left, right adjacent
     * vertices and obstacles in world
     * @param world undirected graph
     */
    public MatrixGraph(World world) {
        // get size of world
        super(world.getRows() * world.getColumns(), false);

        // create edges connecting vertices
        edges = new double[getNumV()][getNumV()];

        // if isObstacle = true, cannot move to index,
        // otherwise index is available to move
        boolean isObstacle;

        // current position, and all surrounding positions
        int currentIndex, upIndex, downIndex, leftIndex, rightIndex;

        // fill in 2D array with positive infinity
        // because edge does not exist yet so its
        // weight = pos. infinity
        for(int i = 0; i <= edges.length-1; i++) {
            for (int j = 0; j <= edges.length-1; j++) {
                edges[i][j] = Double.POSITIVE_INFINITY;
            }
        }

        // traverse through every row in graph
        for(int i = 0; i < world.getRows(); i++) {

            // traverse through every column in graph
            for(int j = 0; j < world.getColumns(); j++) {

                // get current position
                currentIndex = world.getXIndex(i, j);

                // if obstacle is in current location, flag/mark it
                if(world.testForObstacle(currentIndex)) {
                    isObstacle = true;
                } else {
                    isObstacle = false;
                }

                // insert left position/vertex in graph based on
                // current position/location if there is no obstacle
                // creating edge connecting left & current position/location
                // with weight of 1
                if (!isObstacle) {

                    if(j - 1 > -1 && j - 1 < world.getColumns()) {
                        leftIndex = world.getXIndex(i, j - 1);
                        if (!world.testForObstacle(leftIndex)) {

                            if (world.getLocation(i, j - 1) != world.OBSTACLE && !isObstacle) {
                                insert(new Edge(leftIndex, currentIndex, 1.0));
                            }
                        }
                    }
                    // insert right position/vertex in graph based on
                    // current position/location if there is no obstacle
                    // creating edge connecting left & current position/location
                    // with weight of 1
                    if(j + 1 < world.getColumns()) {
                        rightIndex = world.getXIndex(i, j + 1);
                        if (!world.testForObstacle(rightIndex)) {

                            if (world.getLocation(i, j + 1) != world.OBSTACLE && !isObstacle) {
                                insert(new Edge(rightIndex, currentIndex, 1.0));
                            }
                        }
                    }
                    // insert up position/vertex in graph based on
                    // current position/location if there is no obstacle
                    // creating edge connecting left & current position/location
                    // with weight of 1
                    if(i - 1 > -1 && i - 1 < world.getRows()) {
                        upIndex = world.getXIndex(i - 1, j);
                        if (!world.testForObstacle(upIndex)) {

                            if (world.getLocation(i - 1, j) != world.OBSTACLE && !isObstacle) {
                                insert(new Edge(upIndex, currentIndex, 1.0));
                            }
                        }
                    }
                    // insert down position/vertex in graph based on
                    // current position/location if there is no obstacle
                    // creating edge connecting left & current position/location
                    // with weight of 1
                    if(i + 1 < world.getRows()) {
                        downIndex = world.getXIndex(i + 1, j);
                        if (!world.testForObstacle(downIndex)) {

                            if (world.getLocation(i + 1, j) != world.OBSTACLE && !isObstacle) {
                                insert(new Edge(downIndex, currentIndex, 1.0));
                            }
                        }
                    }
                }}
        }
    }

    // Methods

    // create/draw matrix visual
    public void drawGraph() {
        for(int i = 0; i < edges.length; i++) {
            for(int j = 0; j < edges.length; j++) {
                if(edges[i][j] == Double.POSITIVE_INFINITY) {
                    // 0 = infinity
                    System.out.println("0\t");
                } else {
                    System.out.println((int)edges[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }

    // Graph Interface methods b/c extends AbstractGraph

    /** Insert a new edge into the graph.
     * @param edge  The new edge
     */
    @Override
    public void insert(Edge edge) {
        // if dest or source are outOfBounds of array
        if(edge.getDest() >= edges.length || edge.getSource() >= edges.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        // assign weight to created edge
        edges[edge.getSource()][edge.getDest()] = edge.getWeight();
        // if undirected graph, makes weight equal both ways to/from
        // destination and source (symmetrical)
        if(!isDirected()) {
            edges[edge.getDest()][edge.getSource()] = edge.getWeight();
        }
    }

    /** Determine whether an edge exists.
     * @param source    The source vertex
     * @param dest      The destination vertex
     * @return          true if there is an edge from source to dest
     */
    @Override
    public boolean isEdge(int source, int dest) {
        return edges[source][dest] != Double.POSITIVE_INFINITY;
    }

    /** Get the edge between two vertices.
     * @param source    The source vertex
     * @param dest      The destination vertex
     * @return          The Edge between these two vertices
     *                  or an Edge with a weight of
     *                  Double.POSITIVE_INFINITY if there is no edge
     */
    @Override
    public Edge getEdge(int source, int dest) {
        // if dest or source are outOfBounds of array
        if(dest >= edges.length || source >= edges.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return new Edge(source, dest, edges[source][dest]);
    }

    /** Return an iterator to the edges connected
     * to a given vertex.
     * @param source    The source vertex
     * @return          An Iterator<Edge> to the vertices
     *                  connected to source
     */
    @Override
    public Iterator<Edge> edgeIterator(int source) {
        return new MatrixIterator(source);
    }

    // inner iterator class Iter, for array
    // checks adjacent vertices of 1 vertex
    private class MatrixIterator implements Iterator<Edge>, Iterable{
        private int vertex;
        private int lastReturned;

        public MatrixIterator(int source) {
            vertex = source;
            lastReturned = 0;
        }

        @Override
        public boolean hasNext() {
            while(lastReturned < edges.length) {
                if(edges[vertex][lastReturned] != Double.POSITIVE_INFINITY) {
                    return true;
                }
                lastReturned++;
            }
            return false;
        }

        @Override
        public Edge next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned++;
            return new Edge(vertex, lastReturned - 1);
        }

        @Override
        public Iterator<Edge> iterator() {
            return this;
        }
    }

    // get relationships b/w vertices
    public ArrayList<Edge> checkConnections() {
        if(isDirected()) {
            return directedConnections();
        } else {
            return undirectedConnections();
        }
    }

    // returns ArrayList of connections b/w vertices in directed graph
    private ArrayList<Edge> directedConnections() {
        ArrayList<Edge> arrayList = new ArrayList<>();
        for(int i = 0; i < edges.length; i++) {
            for (int j = 0; j < edges.length; j++) {
                if(edges[i][j] > 0) {
                    arrayList.add(new Edge(i, j, edges[i][j]));
                }
            }
        }
        return arrayList;
    }

    // returns ArrayList of connections b/w vertices in undirected graph
    // only needs half b/c undirected is the same b/w vertices
    private ArrayList<Edge> undirectedConnections() {
        ArrayList<Edge> arrayList = new ArrayList<>();
        for(int i = 0; i < edges.length; i++) {
            for(int j = 0; j < i; j++) {
                if (edges[i][j] > 0) {
                    arrayList.add(new Edge(i, j, edges[i][j]));
                }
            }
        }
        return arrayList;
    }

    // get path using dijkstra's algorithm
    public int[] getPath(int start, int dest) {
        int[] pred = new int[getNumV()];
        double[] dist = new double[getNumV()];
        dijkstrasAlgorithm(start, pred, dist);
        return getShortestPath(start, dest, pred);
    }

    /** Dijkstra's Shortest-Path algorithm.
     * @param start The start vertex
     * @param pred  Output array to contain the predecessors
     *              in the shortest path
     * @param dist  Output array to contain the distance
     *              in the shortest path
     */
    public void dijkstrasAlgorithm(int start, int[] pred, double[] dist) {
        int numV = getNumV();
        HashSet<Integer> vMinusS = new HashSet<>(numV);
        // Initialize V-S.
        for(int i = 0; i < numV; i++) {
            if(i != start) {
                vMinusS.add(i);
            }
        }
        // Initializes pred and dist.
        for(int v : vMinusS) {
            pred[v] = start;
            dist[v] = getEdge(start, v).getWeight();
        }
        // Main loop
        while (vMinusS.size() != 0) {
            // Find the value u in V-S with the smallest dist[u].
            double minDist = Double.POSITIVE_INFINITY;
            int u = -1;
            for(int v : vMinusS) {
                if(dist[v] < minDist) {
                    minDist = dist[v];
                    u = v;
                }
            }
            if(u == -1) {
                for(int v : vMinusS) {
                    u = v;
                    break;
                }
            }
            // Remove u from vMinusS.
            vMinusS.remove(u);
            // Update the distances.
            for(int v : vMinusS) {
                if(isEdge(u, v)) {
                    double weight = getEdge(u, v).getWeight();
                    if(dist[u] + weight < dist[v]) {
                        dist[v] = dist[u] + weight;
                        pred[v] = u;
                    }
                }
            }
        }
    }

    /** After Dijkstra's Algorithm is called 1st to get all paths
     * from vertex. To get shortest path, backtrack Dijkstra's
     * algorithm from the end vertex to current vertex
     * @param start Starting vertex
     * @param end   Ending vertex
     * @param pred  Array holding vertices path to end
     * @return      Array with vertices of shortest path to end
     */
    public int[] getShortestPath(int start, int end, int[] pred) {
        Stack<Integer> stack = new Stack<>();
        stack.push(end);
        while(end != start) {
            // push predecessor of end and backtrack path
            stack.push(end);
            end = pred[end];
        }
        int[] array = new int[stack.size()];
        int counter = 0;
        while (!stack.isEmpty()) {
            array[counter] = stack.pop();
            counter++;
        }
        return array;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < edges.length; i++) {
            for(int j = 0; j < edges.length; j++) {
                if(edges[i][j] == Double.POSITIVE_INFINITY) {
                    sb.append("X\t");
                }
                else {
                    sb.append((int)edges[i][j] + "\t");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(edges);
    }
}
