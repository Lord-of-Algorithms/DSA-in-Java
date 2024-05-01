package sorting.quick;

public class QuickSortHoare {

    /**
     * Public method to start the Quick Sort process using the Hoare partition scheme.
     *
     * @param array The array to be sorted.
     * @param low   The starting index of the segment of the array to be sorted.
     * @param high  The ending index of the segment of the array to be sorted.
     */
    public static void sort(int[] array, int low, int high) {
        if (low < high) {
            // Partition the array and obtain the index that divides the partitions
            int partitionIndex = partition(array, low, high);

            // Unlike Lomuto's partition, the Hoare partition
            // might not place the pivot in its final position.
            // Therefore, both recursive calls need to handle segments
            // that potentially include the pivot.
            sort(array, low, partitionIndex);
            sort(array, partitionIndex + 1, high);
        }
    }

    /**
     * Partitions the array using the Hoare partition scheme and returns the index that divides the partitions.
     * This method uses the first element as the pivot.
     *
     * @param array The array to be partitioned.
     * @param low   The starting index for the partitioning.
     * @param high  The ending index for the partitioning.
     * @return The partition index, which is the last index in the lower partition.
     * This index is not necessarily the final position of the pivot.
     */
    private static int partition(int[] array, int low, int high) {
        int pivot = array[low]; // Pivot chosen as the first element
        int i = low - 1;
        int j = high + 1;

        while (true) {
            // Move right while the item is less than the pivot
            do {
                i++;
            } while (array[i] < pivot);

            // Move left while the item is greater than the pivot
            do {
                j--;
            } while (array[j] > pivot);

            if (i >= j) {
                return j; // This is where the partitioning ends, j is the last index in the lower partition
            }

            // Swap elements at i and j
            swap(array, i, j);
        }
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
