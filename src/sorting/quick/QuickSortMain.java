package sorting.quick;

/**
 * Demonstrates the Quick Sort algorithm using different partition schemes.
 * This class allows switching between Lomuto and Hoare partition schemes
 * to sort an array of integers.
 */
public class QuickSortMain {

    /**
     * Defines the types of partition schemes that can be used for Quick Sort.
     */
    private enum PartitionScheme {
        LOMUTO,
        HOARE
    }

    /**
     * Sorts an array using the specified partition scheme for Quick Sort.
     * The method chooses between Lomuto and Hoare partition schemes based on the input.
     *
     * @param array  The array to be sorted.
     * @param scheme The partition scheme to use for sorting: LOMUTO or HOARE.
     * @throws IllegalStateException if an unsupported partition scheme is provided.
     */
    private static void sort(int[] array, PartitionScheme scheme) {
        if (array == null || array.length <= 1) {
            return; // Exit the method early if the array is null, empty, or contains only one element, as no sorting is needed.
        }
        switch (scheme) {
            case LOMUTO:
                QuickSortLomuto.sort(array, 0, array.length - 1);
                break;
            case HOARE:
                QuickSortHoare.sort(array, 0, array.length - 1);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + scheme);
        }
    }

    /**
     * Main method to demonstrate the Quick Sort algorithm.
     */
    public static void main(String[] args) {
        int[] array = {64, 25, 12, 22, 11, 90, 18};
        System.out.println("Original Array:");
        for (int value : array) {
            System.out.print(value + " ");
        }

        sort(array, PartitionScheme.LOMUTO);

        System.out.println("\n\nSorted Array:");
        for (int value : array) {
            System.out.print(value + " ");
        }
    }
}
