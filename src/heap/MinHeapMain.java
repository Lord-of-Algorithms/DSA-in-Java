package heap;

/**
 * Demonstrates {@link MinHeap} operations: insert, peekMin, extractMin
 * (which yields the values in ascending order), and delete.
 */
public class MinHeapMain {

    public static void main(String[] args) {
        MinHeap heap = new MinHeap(15);

        System.out.println("=== Insert ===");
        for (int value : new int[]{5, 3, 8, 1, 9, 2, 7}) {
            heap.insert(value);
            System.out.println("insert(" + value + ")   min=" + heap.peekMin()
                    + "   size=" + heap.size());
        }

        System.out.println("\n=== Delete ===");
        // Delete the node at index 2 to show the replacement being sifted.
        System.out.println("delete(index 2)");
        heap.delete(2);
        System.out.println("min=" + heap.peekMin() + "   size=" + heap.size());

        System.out.println("\n=== Extract-min (ascending) ===");
        StringBuilder sb = new StringBuilder();
        while (!heap.isEmpty()) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(heap.extractMin());
        }
        System.out.println(sb);
    }
}
