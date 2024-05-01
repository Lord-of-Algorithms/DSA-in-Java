package sorting;

public class InsertionSort {

    /**
     * Sorts the provided array using the Insertion Sort algorithm.
     *
     * @param array The array to be sorted.
     */
    public static void insertionSort(int[] array) {
        int n = array.length;
        for (int i = 1; i < n; i++) {
            int temp = array[i];

            // Move elements of array[0..i-1], that are greater than temp,
            // to one position ahead of their current position
            int j = i;
            while (j > 0 && array[j - 1] > temp) {
                array[j] = array[j - 1];
                --j;
            }
            array[j] = temp;
        }
    }

    public static void main(String[] args) {
        int[] array = {64, 25, 12, 22, 11, 90, 18};

        System.out.println("Original Array:");
        for (int value : array) {
            System.out.print(value + " ");
        }

        insertionSort(array);

        System.out.println("\n\nSorted Array:");
        for (int value : array) {
            System.out.print(value + " ");
        }
    }
}
