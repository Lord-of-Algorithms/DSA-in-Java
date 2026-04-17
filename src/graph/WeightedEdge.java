package graph;

import java.util.Objects;

/**
 * Represents a weighted directed edge between two vertices.
 */
public class WeightedEdge extends Edge {
    private final int weight;

    public WeightedEdge(Vertex source, Vertex destination, int weight) {
        super(source, destination);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeightedEdge that = (WeightedEdge) o;
        return weight == that.weight &&
                Objects.equals(getSource(), that.getSource()) &&
                Objects.equals(getDestination(), that.getDestination());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getDestination(), weight);
    }

    @Override
    public String toString() {
        return getSource() + "" + getDestination() + "(" + weight + ")";
    }
}
