package graph;

import java.util.Objects;

/**
 * Represents an edge in a graph, defined by a source vertex,
 * a destination vertex, and a weight.
 */
public class Edge {
    private final Vertex source;
    private final Vertex destination;
    private final int weight;

    public Edge(Vertex source, Vertex destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return weight == edge.weight &&
                Objects.equals(source, edge.source) &&
                Objects.equals(destination, edge.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination, weight);
    }

    @Override
    public String toString() {
        return source + "" + destination + "(" + weight + ")";
    }
}
