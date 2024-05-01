package sorting;

public class MergeSort {

    /**
     * Sorts the provided array using the Merge Sort algorithm.
     *
     * @param array The array to be sorted.
     */
    public static void mergeSort(int[] array) {
        if (array == null || array.length <= 1) {
            return; // Exit the method early if the array is null, empty, or contains only one element, as no sorting is needed.
        }
        int[] auxiliaryArray = new int[array.length]; // auxiliary array for merging
        mergeSortRec(array, auxiliaryArray, 0, array.length - 1);
    }

    /**
     * Recursive method that divides the array into halves, sorts them and merges them.
     *
     * @param array    The array to be sorted.
     * @param auxArray Auxiliary array used for merging sorted halves.
     * @param start    The starting index of the segment of the array to be sorted.
     * @param end      The ending index of the segment of the array to be sorted.
     */
    private static void mergeSortRec(
            int[] array,
            int[] auxArray,
            int start,
            int end
    ) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSortRec(array, auxArray, start, mid); // Sort the first half
            mergeSortRec(array, auxArray, mid + 1, end); // Sort the second half
            mergeBothParts(array, auxArray, start, mid + 1, end); // Merge sorted halves
        }
    }

    /**
     * Merges two sorted halves of the array.
     *
     * @param array    The original array containing all elements.
     * @param auxArray Auxiliary array used to help merge elements.
     * @param left     The starting index of the first sorted half.
     * @param right    The starting index of the second sorted half.
     * @param end      The ending index of the second sorted half.
     */
    private static void mergeBothParts(
            int[] array,
            int[] auxArray,
            int left,
            int right,
            int end
    ) {
        int start = left;
        int mid = right - 1;
        int itemsCount = end - left + 1;

        int i = 0;

        // Merge elements and store in auxArray
        while (left <= mid &&
                right <= end) {

            if (array[left] <
                    array[right]) {
                auxArray[i++] = array[left++];
            } else {
                auxArray[i++] = array[right++];
            }
        }

        // Copy remaining elements from the first half
        while (left <= mid) {
            auxArray[i++] = array[left++];
        }

        // Copy remaining elements from the second half
        while (right <= end) {
            auxArray[i++] = array[right++];
        }

        // Copy back the merged elements to the original array
        for (i = 0; i < itemsCount; i++) {
            array[start + i] = auxArray[i];
        }
    }

    /**
     * Main method to demonstrate the Merge Sort algorithm.
     */
    public static void main(String[] args) {
        int[] array = {64, 25, 12, 22, 11, 90, 18};

        System.out.println("Original Array:");
        for (int value : array) {
            System.out.print(value + " ");
        }

        mergeSort(array);

        System.out.println("\n\nSorted Array:");
        for (int value : array) {
            System.out.print(value + " ");
        }
    }
}
