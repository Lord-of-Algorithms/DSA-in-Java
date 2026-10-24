package bigo;

/**
 * One function per growth class of the Big O topic.
 *
 * Big O describes how the number of steps grows with the size of the
 * input, n. Counting how many times a function's loops can run, or how
 * many times it calls itself, gives its class:
 *
 *   * readAt       - no loop, one step:             O(1)
 *   * binarySearch - a loop that halves its range:  O(log n)
 *   * linearSearch - one loop over the n values:    O(n)
 *   * hasDuplicate - a loop inside a loop:          O(n^2)
 *   * printCoins   - every combination of n coins: O(2^n)
 *
 * Each class is given for the worst case.
 */
public final class BigOExamples {

    private BigOExamples() {
    }

    /**
     * O(1): reads the value at the given index in one step, whatever the
     * length of the array.
     */
    public static int readAt(int[] values, int index) {
        return values[index];
    }

    /**
     * O(n): looks at the values one by one from the left. Returns the
     * index of the target, or -1 if it is missing. In the worst case (the
     * target is last or missing) the loop runs n times.
     */
    public static int linearSearch(int[] values, int target) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * O(log n): the values must be sorted in ascending order. Each look at
     * the middle of the remaining range rules out half of it, so the loop
     * runs about log2(n) + 1 times at most. Returns the index of the
     * target, or -1 if it is missing.
     */
    public static int binarySearch(int[] values, int target) {
        int lo = 0;
        int hi = values.length - 1;
        while (lo <= hi) {
            // The left middle when the range has two; this form of
            // (lo + hi) / 2 cannot overflow.
            int mid = lo + (hi - lo) / 2;
            if (values[mid] == target) {
                return mid;
            } else if (values[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return -1;
    }

    /**
     * O(n^2): compares every pair of values, n(n - 1) / 2 comparisons when
     * there is no duplicate. Returns true as soon as two values are equal.
     */
    public static boolean hasDuplicate(int[] values) {
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (values[i] == values[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * O(2^n): prints every way n coins can land, one line each, heads (H) or
     * tails (T). Each coin doubles the number of combinations, so there are
     * 2^n lines. Uses recursion: printAll calls itself.
     */
    public static void printCoins(int n) {
        printAll(new char[n], 0);
    }

    /**
     * Coins before index i are set; tries coin i both ways.
     */
    private static void printAll(char[] coins, int i) {
        if (i == coins.length) {
            System.out.println(new String(coins));
            return;
        }
        coins[i] = 'H';
        printAll(coins, i + 1);
        coins[i] = 'T';
        printAll(coins, i + 1);
    }
}
