package unionfind;

public class UnionFindMain {
    public static void main(String[] args) {
        // Initialize UnionFind with 10 elements
        UnionFind uf = new UnionFind(10);

        // Perform some union operations
        uf.union(0, 1);
        uf.union(2, 3);
        uf.union(4, 5);
        uf.union(6, 7);
        uf.union(7, 8);
        uf.union(0, 9);
        uf.union(5, 9);

        // Output the root of each element to show which set they belong to
        for (int i = 0; i < 10; i++) {
            System.out.println("Element " + i + " is in set: " + uf.find(i));
        }

        // Check if two elements are in the same set
        int setX = uf.find(2);
        int setY = uf.find(3);
        System.out.println("Elements 2 and 3 are in the same set: " + (setX == setY));

        setX = uf.find(0);
        setY = uf.find(8);
        System.out.println("Elements 0 and 8 are in the same set: " + (setX == setY));

        setX = uf.find(1);
        setY = uf.find(6);
        System.out.println("Elements 1 and 6 are in the same set: " + (setX == setY));
    }
}
