package sorting;

public class BubbleSort {

    /**
     * Sorts the provided array using the Bubble Sort algorithm.
     *
     * @param array The array to be sorted.
     */
    public static void bubbleSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < array.length - 1 - i; j++) {
                // Compare adjacent elements
                if (array[j] > array[j + 1]) {
                    // Swap if they are in the wrong order
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }
            // If no two elements were swapped, the array is sorted
            if (!swapped) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {64, 34, 25, 12, 22, 11, 90};

        System.out.println("Original Array:");
        for (int value : array) {
            System.out.print(value + " ");
        }

        bubbleSort(array);

        System.out.println("\n\nSorted Array:");
        for (int value : array) {
            System.out.print(value + " ");
        }
    }
}
