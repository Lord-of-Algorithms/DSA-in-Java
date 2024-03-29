package graph.traversal.main;

import graph.traversal.Vertex;

import java.util.Objects;

/**
 * Represents a vertex in a graph.
 */
class GraphVertex implements Vertex {

    private final String label;
    private boolean isVisited;

    GraphVertex(String label) {
        this.label = label;
    }

    @Override
    public void visit() {
        isVisited = true;
    }

    @Override
    public boolean isVisited() {
        return isVisited;
    }

    @Override
    public void resetVisitedStatus() {
        isVisited = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphVertex graphVertex = (GraphVertex) o;
        return Objects.equals(label, graphVertex.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public String toString() {
        return label;
    }
}
