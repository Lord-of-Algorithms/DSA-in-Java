package graph.traversal.main;

import graph.traversal.algorithms.BfsTraversal;
import graph.traversal.algorithms.DfsRecursiveTraversal;
import graph.traversal.algorithms.DfsTraversal;
import graph.traversal.GraphTraversal;

/**
 * Factory for creating instances of {@link GraphTraversal} based on the specified traversal method.
 */
class GraphTraversalFactory {
    static GraphTraversal createTraversal(TraversalMethod method) {
        switch (method) {
            case DFS:
                return new DfsTraversal();
            case DFS_RECURSIVE:
                return new DfsRecursiveTraversal();
            case BFS:
                return new BfsTraversal();
            default:
                throw new IllegalArgumentException("Unknown traversal method: " + method);
        }
    }
}
