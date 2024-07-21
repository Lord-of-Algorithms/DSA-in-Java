package lineards.array;

/**
 * Demonstrates the operations on a static array.
 * This class showcases various array manipulations such as insertion at specific positions,
 * deletion, and basic array handling in a fixed-size array structure.
 */
public class StaticArrayMain {

    public static void main(String[] args) {
        // 1. Initialization
        String[] planets = new String[8];  // Allocate space for 8 planets

        // 2. Assigning Values
        planets[0] = "Venus";
        planets[1] = "Pluto";
        planets[2] = "Earth";
        planets[3] = "Jupiter";
        System.out.println("Assigning initial values...");
        printArray(planets);

        // 3. Insertion at the beginning
        // Shift elements to the right to make space for the new element
        for (int i = planets.length - 1; i > 0; i--) {
            planets[i] = planets[i - 1];
        }
        planets[0] = "Mercury";
        System.out.println("Inserting 'Mercury' at the beginning...");
        printArray(planets);

        // 4. Insertion at an arbitrary position
        int position = 4;
        // Shift elements to the right from the specified position
        for (int i = planets.length - 1; i > position; i--) {
            planets[i] = planets[i - 1];
        }
        planets[position] = "Mars";
        System.out.println("Inserting 'Mars' at position " + position + "...");
        printArray(planets);

        // 5. Deletion at an arbitrary position
        position = 2;
        // Shift elements to the left to overwrite the deleted element
        for (int i = position; i < planets.length - 1; i++) {
            planets[i] = planets[i + 1];
        }
        planets[planets.length - 1] = null;  // Clear the last element
        System.out.println("Deleting element at position " + position + "...");
        printArray(planets);
    }

    // Method to print the array contents
    public static void printArray(String[] array) {
        System.out.println("Current state of the array:");
        for (String element : array) {
            System.out.print((element != null ? element : "null") + " ");
        }
        System.out.println("\n");
    }
}
