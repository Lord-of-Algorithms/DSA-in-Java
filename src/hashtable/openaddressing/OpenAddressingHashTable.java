package hashtable.openaddressing;

/**
 * A hash table that resolves collisions with open addressing: every entry lives
 * directly in the backing array, and collisions are handled by probing for the
 * next candidate slot. Two probe sequences are supported (linear and quadratic).
 *
 * Deletion uses a tombstone marker so that probe chains passing through a
 * removed slot are not broken. Keys are names (strings) and values are phone
 * numbers (strings).
 */
public class OpenAddressingHashTable {

    // A shared marker flagging a slot whose entry has been removed (a "tombstone").
    private static final Entry DELETED = new Entry(null, null);

    private Entry[] table;
    private int size; // Number of active key-value pairs
    private final ProbeType probeType;

    // Open addressing degrades sharply as the table fills. Keeping the load
    // factor at most 0.5 also guarantees that quadratic probing on a prime-sized
    // table always finds a free slot.
    private static final double MAX_LOAD_FACTOR = 0.5;

    public OpenAddressingHashTable(int capacity, ProbeType probeType) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Initial capacity must be >= 1");
        }
        // A prime capacity spreads the probe sequence across the whole table.
        capacity = isPrime(capacity) ? capacity : nextPrime(capacity);
        table = new Entry[capacity];
        this.probeType = probeType;
    }

    // Computes the home slot for a key using the division method. Any hash
    // function works here; open addressing fixes one so the probe sequence
    // stays the focus (see HashFunctionType in the chaining table).
    private int hash(String key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    // Computes the offset added to the home slot on the i-th probe.
    private int probe(int i) {
        switch (probeType) {
            case Linear:
                return i;      // home, home+1, home+2, ...
            case Quadratic:
                return i * i;  // home, home+1, home+4, home+9, ...
            default:
                throw new IllegalStateException("Unknown Probe Type");
        }
    }

    /**
     * Inserts a new key-value pair, or updates the value if the key already exists.
     *
     * @param key   the key to insert or update
     * @param value the value associated with the key
     */
    public void put(String key, String value) {
        // Resize before the table gets too full.
        if ((double) (size + 1) / table.length > MAX_LOAD_FACTOR) {
            rehash();
        }

        int home = hash(key);
        int firstTombstone = -1;

        for (int i = 0; i < table.length; i++) {
            int index = (home + probe(i)) % table.length;
            Entry entry = table[index];

            if (entry == null) {
                // An empty slot ends the probe chain: the key is not present.
                // Reuse the first tombstone seen along the way, if any.
                int target = (firstTombstone != -1) ? firstTombstone : index;
                table[target] = new Entry(key, value);
                size++;
                return;
            } else if (entry == DELETED) {
                // Remember the first tombstone so the key can be placed there.
                if (firstTombstone == -1) {
                    firstTombstone = index;
                }
            } else if (entry.key.equals(key)) {
                // Key already present — update its value in place.
                entry.value = value;
                return;
            }
            // Otherwise the slot holds a different key: keep probing.
        }
        // Defensive guard — never reached while the invariants hold (prime size,
        // load factor <= 0.5); fails loudly instead of silently dropping the key.
        throw new IllegalStateException("Hash table is full");
    }

    /**
     * Retrieves the value associated with a key.
     *
     * @param key the key whose value is to be retrieved
     * @return the value, or null if the key is not found
     */
    public String get(String key) {
        int home = hash(key);
        for (int i = 0; i < table.length; i++) {
            int index = (home + probe(i)) % table.length;
            Entry entry = table[index];

            if (entry == null) {
                return null; // An empty slot ends the probe chain.
            }
            if (entry != DELETED && entry.key.equals(key)) {
                return entry.value;
            }
            // A tombstone or a different key — keep probing.
        }
        return null;
    }

    /**
     * Removes a key-value pair, leaving a tombstone in its place so that probe
     * chains running through the slot are not broken.
     *
     * @param key the key of the pair to remove
     * @return true if the key was found and removed, false otherwise
     */
    public boolean remove(String key) {
        int home = hash(key);
        for (int i = 0; i < table.length; i++) {
            int index = (home + probe(i)) % table.length;
            Entry entry = table[index];

            if (entry == null) {
                return false; // An empty slot ends the probe chain.
            }
            if (entry != DELETED && entry.key.equals(key)) {
                table[index] = DELETED; // Tombstone, not an empty slot.
                size--;
                return true;
            }
        }
        return false;
    }

    // Returns the number of active key-value pairs in the table.
    public int size() {
        return size;
    }

    // Doubles the capacity (rounded up to a prime) and reinserts every active entry.
    // Tombstones are discarded in the process.
    private void rehash() {
        Entry[] oldTable = table;
        int newCapacity = nextPrime(oldTable.length * 2);
        table = new Entry[newCapacity];
        size = 0;
        for (Entry entry : oldTable) {
            if (entry != null && entry != DELETED) {
                put(entry.key, entry.value);
            }
        }
    }

    // Utility method to find the next prime number greater than or equal to start.
    private int nextPrime(int start) {
        for (int n = start; true; n++) {
            if (isPrime(n)) {
                return n;
            }
        }
    }

    // Utility method to check if a number is prime.
    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prints the backing array slot by slot.
     */
    public void print() {
        for (int i = 0; i < table.length; i++) {
            System.out.print("[" + i + "] ");
            Entry entry = table[i];
            if (entry == null) {
                System.out.println("-");
            } else if (entry == DELETED) {
                System.out.println("(deleted)");
            } else {
                System.out.println(entry.key + " = " + entry.value);
            }
        }
    }

    /**
     * Entry class represents a key-value pair stored directly in the backing array.
     */
    private static class Entry {
        String key;
        String value;

        Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
