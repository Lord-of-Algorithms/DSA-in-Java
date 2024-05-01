package sorting.heap;

/**
 * Implements the Heap Sort algorithm using an iterative approach.
 */
public class HeapSortIterative {

    /**
     * Sorts an array using the Heap Sort algorithm.
     *
     * @param array The array to be sorted.
     */
    public static void sort(int[] array) {
        int n = array.length;
        // Build a heap from the array
        buildHeap(array, n);
        for (int i = n - 1; i > 0; i--) {
            // Swap the root of the heap with the last element of the unsorted part
            int root = array[0];
            array[0] = array[i];
            array[i] = root;
            // Heapify the root element to maintain the heap property
            heapify(array, 0, i);
        }
    }

    /**
     * Transforms an array into a max heap.
     *
     * @param array The array to transform into a heap.
     * @param size  The number of elements in the array.
     */
    private static void buildHeap(int[] array, int size) {
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapify(array, i, size);
        }
    }

    /**
     * Ensures the heap property for a subtree rooted at the index i.
     *
     * @param array The heap array.
     * @param i     The root index of the subtree.
     * @param size  The size of the heap (or subheap during sorting).
     */
    private static void heapify(int[] array, int i, int size) {
        int examined = array[i];
        while (i < size / 2) { // Only non-leaf nodes need to be heapified
            int largestChildIndex =
                    getLargestChildIndex(array, i, size);
            if (examined >= array[largestChildIndex]) {
                break; // The heap property is satisfied
            } else {
                // Overwrite the current node with the value of its largest child
                array[i] = array[largestChildIndex];
                // Move the index to the largest child to continue the process down the tree
                i = largestChildIndex;
            }
        }
        // Place the original root value at the final position determined by the last iteration
        array[i] = examined;
    }

    /**
     * Returns the index of the largest child of a given node.
     *
     * @param array The heap array.
     * @param index The index of the node.
     * @param size  The size of the heap.
     * @return The index of the largest child.
     */
    private static int getLargestChildIndex(
            int[] array,
            int index,
            int size
    ) {
        int leftIndex = 2 * index + 1;
        int rightIndex = leftIndex + 1;
        if (rightIndex < size &&
                array[leftIndex] <
                        array[rightIndex]) {
            return rightIndex; // Right child is larger
        } else {
            return leftIndex; // Left child is larger or only child
        }
    }
}
