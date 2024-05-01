package sorting.quick;

public class QuickSortLomuto {

    /**
     * Public method to start the Quick Sort process using the Lomuto partition scheme.
     *
     * @param array The array to be sorted.
     * @param low   The starting index of the segment of the array to be sorted.
     * @param high  The ending index of the segment of the array to be sorted.
     */
    public static void sort(int[] array, int low, int high) {
        if (low < high) {
            // Partition the array around the pivot
            int pivotIndex = partition(array, low, high);

            // Recursively sort the sub-array before the pivot
            sort(array, low, pivotIndex - 1);
            // Recursively sort the sub-array after the pivot
            sort(array, pivotIndex + 1, high);
        }
    }

    /**
     * Partitions the array using the Lomuto partition scheme and returns the index of the pivot.
     * The last element is chosen as the pivot.
     *
     * @param array The array to be partitioned.
     * @param low   The starting index for the partitioning.
     * @param high  The ending index for the partitioning.
     * @return The index of the pivot element after partitioning.
     */
    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low; // Index of smaller element

        for (int j = low; j < high; j++) {
            // If current element is smaller than the pivot
            if (array[j] < pivot) {
                swap(array, i, j);
                i++;
            }
        }

        swap(array, i, high);
        return i; // Return the partitioning index
    }

    /**
     * Modifies the array by swapping the elements at the specified indices.
     *
     * @param array  The array in which the elements will be swapped.
     * @param index1 The index of the first element to swap.
     * @param index2 The index of the second element to swap.
     */
    private static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}
