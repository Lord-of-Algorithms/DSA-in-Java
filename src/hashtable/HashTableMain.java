package hashtable;

import hashtable.chaining.ChainingHashTable;
import hashtable.chaining.HashFunctionType;
import hashtable.openaddressing.OpenAddressingHashTable;
import hashtable.openaddressing.ProbeType;

/**
 * Demonstrates both hash table implementations on the same kind of data — a
 * small phone book that maps names (keys) to phone numbers (values):
 *
 *   1. Chaining          — collisions share a slot via a linked list.
 *   2. Open addressing   — collisions are resolved by probing for another slot,
 *                          with tombstones marking removed entries.
 */
public class HashTableMain {

    public static void main(String[] args) {
        demoChaining();
        System.out.println();
        demoOpenAddressing();
    }

    private static void demoChaining() {
        System.out.println("=== Chaining ===");
        ChainingHashTable phoneBook = new ChainingHashTable(5, HashFunctionType.Division);

        // Insert name -> phone number pairs.
        phoneBook.put("Alice", "555-0101");
        phoneBook.put("Bob", "555-0102");
        phoneBook.put("Carol", "555-0103");

        System.out.println("Initial phone book:");
        phoneBook.print();

        // Retrieve a value.
        System.out.println("\nBob's number: " + phoneBook.get("Bob"));

        // Update an existing key.
        phoneBook.put("Bob", "555-9999");
        System.out.println("\nAfter updating Bob's number:");
        phoneBook.print();

        // Remove an entry.
        phoneBook.remove("Carol");
        System.out.println("\nAfter removing Carol:");
        phoneBook.print();

        // Insert more entries to trigger rehashing.
        phoneBook.put("Dave", "555-0104");
        phoneBook.put("Eve", "555-0105");
        phoneBook.put("Frank", "555-0106");
        System.out.println("\nAfter adding more names and triggering rehashing:");
        phoneBook.print();
    }

    private static void demoOpenAddressing() {
        System.out.println("=== Open addressing ===");
        OpenAddressingHashTable phoneBook = new OpenAddressingHashTable(7, ProbeType.Linear);

        // Insert name -> phone number pairs.
        phoneBook.put("Bob", "555-0102");
        phoneBook.put("Rob", "555-0103");
        phoneBook.put("Tam", "555-0104");

        System.out.println("After inserts (linear probing):");
        phoneBook.print();

        // Retrieve a value.
        System.out.println("\nRob's number: " + phoneBook.get("Rob"));

        // Remove a key — leaves a tombstone so probe chains stay intact.
        phoneBook.remove("Rob");
        System.out.println("\nAfter removing Rob:");
        phoneBook.print();

        // Searching for the removed key now fails; keys past the tombstone are still found.
        System.out.println("\nRob's number after removal: " + phoneBook.get("Rob"));
        System.out.println("Tam's number after removal: " + phoneBook.get("Tam"));

        // A new insert can reuse the tombstoned slot.
        phoneBook.put("Max", "555-0105");
        System.out.println("\nAfter inserting Max (may reuse the tombstone):");
        phoneBook.print();

        // The same operations with quadratic probing.
        OpenAddressingHashTable quadBook = new OpenAddressingHashTable(7, ProbeType.Quadratic);
        quadBook.put("Bob", "555-0102");
        quadBook.put("Rob", "555-0103");
        quadBook.put("Vic", "555-0104");
        System.out.println("\nAfter inserts (quadratic probing):");
        quadBook.print();
    }
}
