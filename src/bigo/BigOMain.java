package bigo;

import java.util.Arrays;

/**
 * Runs the five functions of {@link BigOExamples} on the arrays of the Big
 * O topic: 8 values, then the same 8 with 8 more appended.
 */
public class BigOMain {

    private static final int[] VALUES_8 = {31, 18, 44, 23, 52, 37, 26, 58};
    private static final int[] VALUES_16 = {31, 18, 44, 23, 52, 37, 26, 58,
            74, 63, 85, 69, 91, 66, 80, 97};

    public static void main(String[] args) {
        for (int[] values : new int[][]{VALUES_8, VALUES_16}) {
            int n = values.length;
            int target = values[n - 1];
            int[] sorted = values.clone();
            Arrays.sort(sorted);

            System.out.println("=== n = " + n + " ===");
            System.out.println("values:        " + Arrays.toString(values));
            System.out.println("readAt(5)      = "
                    + BigOExamples.readAt(values, 5));
            System.out.println("linearSearch(" + target + ") = "
                    + BigOExamples.linearSearch(values, target));
            System.out.println("sorted:        " + Arrays.toString(sorted));
            System.out.println("binarySearch(" + target + ") = "
                    + BigOExamples.binarySearch(sorted, target));
            System.out.println("hasDuplicate   = "
                    + BigOExamples.hasDuplicate(values));
            System.out.println();
        }
        System.out.println("printCoins(3):");
        BigOExamples.printCoins(3);
    }
}
