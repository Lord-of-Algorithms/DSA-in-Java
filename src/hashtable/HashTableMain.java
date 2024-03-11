package hashtable;

public class HashTableMain {
    /**
     * Demonstrates the usage of the HashTable class, including key operations
     * such as insertion, retrieval, removal, and automatic rehashing.
     */
    public static void main(String[] args) {
        // Initialize the hash table with a small capacity of 5 for demonstration purposes.
        // This small initial capacity is chosen to illustrate how rehashing operates
        // when the hash table becomes full. In a practical application, especially for a dataset
        // like the chemical elements with a well-defined and relatively small set of items (118 confirmed elements),
        // an initial capacity between 150 and 200 could be more appropriate.
        // This provides ample space for all elements and minimizes the need for rehashing, enhancing performance.
        HashTable hashTable = new HashTable(5, HashFunctionType.Division);

        // Insert key-value pairs into the hash table
        hashTable.put("Hydrogen", 1.008);
        hashTable.put("Helium", 4.0026);
        hashTable.put("Lithium", 6.94);

        // Print the hash table's contents
        System.out.println("Initial Hash Table:");
        hashTable.print();

        // Retrieve and print a specific value
        Double heliumWeight = hashTable.get("Helium");
        System.out.println("\nAtomic weight of Helium: " + heliumWeight);

        // Update an existing key with a new value and print the hash table
        hashTable.put("Helium", 4.002602);
        System.out.println("\nAfter updating Helium's atomic weight:");
        hashTable.print();

        // Remove an entry and print the hash table
        hashTable.remove("Lithium");
        System.out.println("\nAfter removing Lithium:");
        hashTable.print();

        // Insert more entries to trigger rehashing
        hashTable.put("Beryllium", 9.0122);
        hashTable.put("Boron", 10.81);
        hashTable.put("Carbon", 12.011);
        hashTable.put("Nitrogen", 14.007);
        hashTable.put("Oxygen", 15.999);

        System.out.println("\nAfter adding more elements and triggering rehashing:");
        hashTable.print();
    }
}
