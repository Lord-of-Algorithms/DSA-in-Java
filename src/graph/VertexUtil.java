package graph;

import java.util.Map;

public class VertexUtil {

    private VertexUtil() {
        // Prevents instantiation of utility class.
    }

    // Helper method to find a vertex by its index
    public static Vertex getVertexByIndex(
            Map<Vertex, Integer> vertexIndexMap,
            Integer index
    ) {
        for (Map.Entry<Vertex, Integer> entry : vertexIndexMap.entrySet()) {
            if (index.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
