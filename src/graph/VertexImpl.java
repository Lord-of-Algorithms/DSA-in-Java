package graph;

import java.util.Objects;

/**
 * A concrete implementation of the {@link Vertex} interface that uses a string label to uniquely identify
 * each vertex. This class provides methods to support equality checks, hash code generation, and a string
 * representation which are essential for effectively using vertices in various graph-related operations.
 */
public class VertexImpl implements Vertex {

    private final String label;

    /**
     * Constructs a new vertex with the specified label.
     */
    public VertexImpl(String label) {
        if (label == null) {
            throw new IllegalArgumentException("Label cannot be null.");
        }
        this.label = label;
    }

    /**
     * Determines whether the specified object is equal to this vertex.
     * Two vertices are considered equal if they have the same label.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertexImpl that = (VertexImpl) o;
        return Objects.equals(label, that.label);
    }

    /**
     * Returns a hash code value for this vertex.
     */
    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    /**
     * Returns a string representation of this vertex.
     */
    @Override
    public String toString() {
        return label;
    }
}
