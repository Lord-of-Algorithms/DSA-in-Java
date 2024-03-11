package hashtable;

/**
 * A hash table implementation using chaining with linked lists to resolve collisions.
 * This class provides methods for inserting, retrieving, and removing key-value pairs,
 * where keys are strings and values are double precision numbers.
 */
public class HashTable {

    private HashTableBucket[] bucketArray;
    private int size; // Number of key-value pairs in the hash table
    private HashFunctionType hashFunctionType;
    private static final double A = (Math.sqrt(5) - 1) / 2; // Constant for Multiplication Method

    private static final double MAX_LOAD_FACTOR = 0.75;

    public HashTable(int capacity, HashFunctionType hashFunctionType) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Initial capacity must be >= 1");
        }
        // Adjust capacity to the next prime number if it's not already prime
        capacity = isPrime(capacity) ? capacity : nextPrime(capacity);
        bucketArray = new HashTableBucket[capacity];
        this.hashFunctionType = hashFunctionType;
    }

    // Computes the hash value for a given key
    private int hash(String key) {
        int hashCode = key.hashCode();

        switch (hashFunctionType) {
            case Division:
                return Math.abs(hashCode) % bucketArray.length;

            case Multiplication:
                double fractionalPart = (Math.abs(hashCode) * A) % 1;
                return (int) Math.floor(bucketArray.length * fractionalPart);

            default:
                throw new IllegalStateException("Unknown Hash Function Type");
        }
    }

    /**
     * Inserts or updates a key-value pair in the hash table.
     *
     * @param key   the key to insert or update
     * @param value the value associated with the key
     */
    public void put(String key, double value) {
        // Compute the index for this key using the hash function
        int index = hash(key);
        if (bucketArray[index] == null) {
            bucketArray[index] = new HashTableBucket();
        }

        HashTableBucket bucket = bucketArray[index];
        HashTableEntry existingEntry = bucket.find(key);
        if (existingEntry != null) {
            // Key found, update value
            existingEntry.value = value;
        } else {
            // Check if adding a new entry would exceed the load factor and trigger rehashing if necessary
            if ((double) (size + 1) / bucketArray.length >= MAX_LOAD_FACTOR) {
                rehash();

                // Recompute the index since the bucketArray length has changed after rehashing
                index = hash(key);
                if (bucketArray[index] == null) {
                    bucketArray[index] = new HashTableBucket();
                }
                bucket = bucketArray[index];
            }

            bucket.insertAtBeginning(key, value);
            size++;
        }
    }

    // Doubles the size of the hash table and rehashes all existing entries
    private void rehash() {
        HashTableBucket[] oldTable = bucketArray;
        int newCapacity = nextPrime(oldTable.length * 2); // Find the next prime number greater than double the current length
        bucketArray = new HashTableBucket[newCapacity];
        size = 0;
        for (HashTableBucket bucket : oldTable) {
            if (bucket != null) {
                HashTableEntry current = bucket.head;
                while (current != null) {
                    // Re-add each entry to the new table
                    put(current.key, current.value);
                    current = current.next;
                }
            }
        }
    }

    // Utility method to find the next prime number greater than a given number
    private int nextPrime(int start) {
        for (int n = start; true; n++) {
            if (isPrime(n)) {
                return n;
            }
        }
    }

    // Utility method to check if a number is prime
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
     * Retrieves the value associated with a given key.
     *
     * @param key the key whose value is to be retrieved
     * @return the value associated with the key, or null if the key is not found
     */
    public Double get(String key) {
        int index = hash(key);
        HashTableBucket bucket = bucketArray[index];
        if (bucket == null) {
            return null;
        }
        HashTableEntry entry = bucket.find(key);
        return entry != null ? entry.value : null; // Return value if key found, otherwise null
    }

    /**
     * Removes a key-value pair from the hash table.
     *
     * @param key the key of the pair to be removed
     * @return true if the pair was successfully removed, false if the key was not found
     */
    public boolean remove(String key) {
        int index = hash(key);
        if (bucketArray[index] == null) {
            return false; // No list exists at this index, so the key is not in the table
        }

        HashTableBucket bucket = bucketArray[index];
        if (bucket.delete(key)) {
            size--;
            return true;
        }
        return false;
    }

    // Returns the number of key-value pairs in the hash table
    public int size() {
        return size;
    }

    /**
     * Prints the entire contents of the hash table.
     */
    public void print() {
        for (int i = 0; i < bucketArray.length; i++) {
            System.out.print("[" + i + "]");
            HashTableBucket bucket = bucketArray[i];
            if (bucket != null) {
                bucket.print();
            }
            System.out.println();
        }
    }

    /**
     * HashTableBucket class represents the linked list used in chaining
     */
    private static class HashTableBucket {
        HashTableEntry head; // Head of the linked list

        // Inserts a new entry at the beginning of the list
        void insertAtBeginning(String key, double value) {
            HashTableEntry newEntry = new HashTableEntry(key, value);
            newEntry.next = head;
            head = newEntry;
        }

        /**
         * Deletes an entry with the specified key from the list.
         *
         * @param key The key of the entry to be deleted.
         * @return true if the entry was found and successfully deleted, false otherwise.
         */
        boolean delete(String key) {
            HashTableEntry current = head;
            HashTableEntry prev = null;

            while (current != null) {
                if (current.key.equals(key)) {
                    // Key found, delete the entry
                    if (prev == null) {
                        head = current.next; // Deleting the first entry
                    } else {
                        prev.next = current.next; // Deleting a subsequent entry
                    }
                    return true; // Entry successfully deleted
                }
                prev = current;
                current = current.next;
            }

            return false; // Key not found
        }

        // Finds an entry by key in the list
        HashTableEntry find(String key) {
            HashTableEntry current = head;
            while (current != null) {
                if (current.key.equals(key)) {
                    return current;
                }
                current = current.next;
            }
            return null; // Return null if key not found
        }

        /**
         * Prints all the entries in this hash table bucket.
         */
        void print() {
            HashTableEntry current = head;
            while (current != null) {
                current.print();
                current = current.next;
            }
        }
    }

    /**
     * HashTableEntry class represents a key-value pair in the hash table
     */
    private static class HashTableEntry {
        String key;
        double value;
        HashTableEntry next; // Reference to the next entry (node) in the linked list

        HashTableEntry(String key, double value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Prints the key-value pair represented by this entry.
         */
        void print() {
            System.out.print("->|" + key + ", " + value + "|");
        }
    }
}
