package edu.miracosta.cs113;

public class Edge {

    // Data fields

    // The destination vertex for an edge.
    private int dest;
    // The source vertex for an edge.
    private int source;
    // The weight.
    private double weight;

    // Constructors

    /** Constructs an Edge from source to dest.
     * Sets the weight to 1.0.
     * @param source    source vertex for an edge
     * @param dest      destination vertex for an edge
     */
    public Edge(int source, int dest) {
        this.source = source;
        this.dest = dest;
        weight = 1.0;
    }

    /** Constructs an Edge from source to dest.
     * Sets the weight to weight param.
     * @param source    source vertex for an edge
     * @param dest      destination vertex for an edge
     * @param weight    double 0 <= weight <= 1
     */
    public Edge(int source, int dest, double weight) {
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }


    // Methods

    // Accessors

    /**
     * @return  the destination vertex
     */
    public int getDest() {
        return dest;
    }

    /**
     * @return  the source vertex
     */
    public int getSource() {
        return source;
    }

    /**
     * @return  the weight
     */
    public double getWeight() {
        return weight;
    }

    // Override Object class methods

    /** Compares 2 edges for equality. Edges are equal if their source
     * and destination vertices are the same. The weight is not considered.
     * @param obj   temp object for comparison
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        } else {
            Edge edge = (Edge) obj;
            return (edge.source == this.source &&
                    edge.dest == this.dest);
        }
    }

    /** The hash code depends only on the source and destination.
     * @return  hash code for an edge
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(dest) + Integer.hashCode(source);
    }

    /**
     * @return  string representation of the edge
     */
    @Override
    public String toString() {
        return String.format("Source: %d\tDest: %d\tWeight: %f\n",
                source, dest, weight);
    }
}
