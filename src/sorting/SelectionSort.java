package sorting;

public class SelectionSort {

    /**
     * Sorts the provided array using the Selection Sort algorithm.
     *
     * @param array The array to be sorted.
     */
    public static void selectionSort(int[] array) {
        int n = array.length;
        // One by one move the boundary of the unsorted part
        for (int i = 0; i < n - 1; i++) {
            // Assume the first element is the minimum
            int minIndex = i;
            // Check the rest of the array to find the true minimum
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j; // Found a new minimum, remember its index
                }
            }
            // Swap the found minimum element with the first
            // element of the unsorted part
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
    }

    public static void main(String[] args) {
        int[] array = {64, 25, 12, 22, 11, 90, 18};

        System.out.println("Original Array:");
        for (int value : array) {
            System.out.print(value + " ");
        }

        selectionSort(array);

        System.out.println("\n\nSorted Array:");
        for (int value : array) {
            System.out.print(value + " ");
        }
    }
}
