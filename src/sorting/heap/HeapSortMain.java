package sorting.heap;

/**
 * Demonstrates the use of the Heap Sort algorithm in both iterative and recursive forms.
 * This class allows for switching between different implementations of Heap Sort
 * based on the provided sorting nature.
 */
public class HeapSortMain {

    /**
     * Defines the nature of the sorting method to be used in the Heap Sort.
     * It supports both iterative and recursive approaches.
     */
    private enum SortNature {
        ITERATIVE,
        RECURSIVE
    }

    /**
     * Sorts an array using either the iterative or recursive Heap Sort method
     * as specified by the {@code sortNature} parameter.
     *
     * @param array      The array to be sorted.
     * @param sortNature The type of sorting method to use, either ITERATIVE or RECURSIVE.
     * @throws IllegalArgumentException if an unsupported sorting nature is provided.
     */
    private static void sort(int[] array, SortNature sortNature) {
        if (array == null || array.length <= 1) {
            return; // Exit the method early if the array is null, empty, or contains only one element, as no sorting is needed.
        }
        switch (sortNature) {
            case ITERATIVE:
                HeapSortIterative.sort(array);
                break;
            case RECURSIVE:
                HeapSortRecursive.sort(array);
                break;
            default:
                throw new IllegalArgumentException("Unsupported sorting nature: " + sortNature);
        }
    }

    /**
     * Main method to demonstrate the Heap Sort algorithm.
     */
    public static void main(String[] args) {
        int[] array = {64, 25, 12, 22, 11, 90, 18};

        System.out.println("Original Array:");
        for (int value : array) {
            System.out.print(value + " ");
        }

        sort(array, SortNature.RECURSIVE);

        System.out.println("\n\nSorted Array:");
        for (int value : array) {
            System.out.print(value + " ");
        }
    }
}
