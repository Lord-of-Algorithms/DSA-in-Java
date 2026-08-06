package hashtable.chaining;

/**
 * The method used to map a key to its home slot.
 *
 * The hash function is independent of how collisions are resolved — either
 * method works equally well with chaining or with open addressing. Both are
 * offered here only to demonstrate the two classic approaches; the
 * open-addressing table fixes the Division method to keep the focus on probing.
 */
public enum HashFunctionType {
    Division,       // h(k) = k mod m
    Multiplication  // h(k) = floor(m * ((k * A) mod 1)), where A = (sqrt(5) - 1) / 2
}
