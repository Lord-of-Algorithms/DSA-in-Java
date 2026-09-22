package btree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Draws a {@link BTree} on the console.
 *
 * <p>The drawing puts every node of a level on one line and joins each node
 * to its children with a bar, so that the shape of the tree — how wide the
 * nodes are and how they divide the keys between them — can be read at a
 * glance:
 * <pre>
 *                      [50]
 *                        |
 *             +---------------------+
 *          [20 35]                [65]
 *             |                     |
 *    +--------+--------+        +-------+
 * [10 15]  [25 30]  [40 45]  [55 60]  [70]
 * </pre>
 *
 * <p>A wide tree does not fit a console, so {@link #print} falls back to
 * {@link #outline}, which spends one line per node and marks the levels by
 * indentation instead:
 * <pre>
 * [50]
 *     [20 35]
 *         [10 15]
 *         [25 30]
 *         [40 45]
 *     [65]
 *         [55 60]
 *         [70]
 * </pre>
 *
 * <p>An empty tree is a single node holding no keys, and both views show it
 * as {@code []}.
 */
public final class BTreePrinter {

    // Columns between two neighbouring subtrees.
    private static final int Gap = 2;

    // The widest drawing print() will show. Beyond it the lines wrap around
    // the console and the picture is worth less than the outline.
    private static final int MaxWidth = 110;

    private static final String Blank = " ";

    private BTreePrinter() {
        // prevent initializing
    }

    /**
     * Prints the tree — drawn if the drawing fits the console, as an outline
     * if it does not — and a blank line after it.
     *
     * @param tree the tree to print
     */
    public static void print(BTree tree) {
        String drawing = draw(tree);
        if (widthOf(drawing) > MaxWidth) {
            System.out.println("(too wide to draw — the outline instead)");
            System.out.print(outline(tree));
        } else {
            System.out.print(drawing);
        }
        System.out.println();
    }

    /**
     * Returns the tree drawn level by level, each node joined to its
     * children by a bar. The drawing is as wide as it needs to be.
     *
     * @param tree the tree to draw
     */
    public static String draw(BTree tree) {
        StringBuilder sb = new StringBuilder();
        for (String line : layout(tree.root()).lines) {
            sb.append(stripEnd(line)).append('\n');
        }
        return sb.toString();
    }

    /**
     * Returns the tree with one node per line, each child indented under its
     * parent. Unlike {@link #draw}, this stays narrow however large the tree
     * grows.
     *
     * @param tree the tree to describe
     */
    public static String outline(BTree tree) {
        StringBuilder sb = new StringBuilder();
        appendOutline(sb, tree.root(), 0);
        return sb.toString();
    }

    private static void appendOutline(StringBuilder sb, BTree.Node node,
                                      int depth) {
        sb.append(Blank.repeat(4 * depth)).append(label(node)).append('\n');
        for (BTree.Node child : node.children) {
            appendOutline(sb, child, depth + 1);
        }
    }

    // A node the way it is drawn: its keys in ascending order, in brackets.
    private static String label(BTree.Node node) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < node.keys.size(); i++) {
            if (i > 0) {
                sb.append(Blank);
            }
            sb.append(node.keys.get(i));
        }
        return sb.append(']').toString();
    }

    /**
     * Lays out the subtree rooted at the node: the children first, side by
     * side, and then the node itself above the middle of them.
     */
    private static Block layout(BTree.Node node) {
        String label = label(node);
        if (node.isLeaf()) {
            return new Block(List.of(label), label.length(),
                    label.length() / 2);
        }

        // The children stand in a row, a gap apart. Each one is drawn from
        // the column its own block starts at, so its root ends up at that
        // column plus the block's centre.
        List<Block> children = new ArrayList<>();
        List<Integer> centers = new ArrayList<>();
        int childrenWidth = 0;
        for (BTree.Node child : node.children) {
            Block block = layout(child);
            if (childrenWidth > 0) {
                childrenWidth += Gap;
            }
            children.add(block);
            centers.add(childrenWidth + block.center);
            childrenWidth += block.width;
        }

        // The label sits above the middle child, or halfway between the two
        // middle ones when the count is even — that keeps it over the middle
        // of the row however wide the children turn out to be. A label wider
        // than that middle would hang off the left edge, so everything below
        // it moves right instead.
        int middle = centers.size() / 2;
        int center = centers.size() % 2 == 1
                ? centers.get(middle)
                : (centers.get(middle - 1) + centers.get(middle)) / 2;
        int shift = Math.max(0, label.length() / 2 - center);
        center += shift;
        int labelStart = center - label.length() / 2;
        int width = Math.max(shift + childrenWidth,
                labelStart + label.length());

        List<String> lines = new ArrayList<>();
        lines.add(Blank.repeat(labelStart) + label);
        lines.add(Blank.repeat(center) + '|');
        lines.add(bar(centers, shift, width));
        lines.addAll(sideBySide(children, shift));
        return new Block(lines, width, center);
    }

    /**
     * Builds the line that joins a node to its children: a bar reaching from
     * the first child to the last, with a {@code +} where each child hangs
     * from it. The node above meets the bar where its own line comes down.
     */
    private static String bar(List<Integer> centers, int shift, int width) {
        int first = centers.get(0) + shift;
        int last = centers.get(centers.size() - 1) + shift;
        char[] line = new char[width];
        Arrays.fill(line, ' ');
        for (int i = first; i <= last; i++) {
            line[i] = '-';
        }
        for (int childCenter : centers) {
            line[childCenter + shift] = '+';
        }
        return new String(line);
    }

    /**
     * Puts the children's blocks next to each other, a gap apart, shifted as
     * far right as the node above them needs. The blocks may differ in
     * height only when the tree is broken, and a short one is then filled
     * out with blank lines.
     */
    private static List<String> sideBySide(List<Block> children, int shift) {
        int height = 0;
        for (Block child : children) {
            height = Math.max(height, child.lines.size());
        }
        List<String> lines = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            StringBuilder line = new StringBuilder(Blank.repeat(shift));
            for (int i = 0; i < children.size(); i++) {
                if (i > 0) {
                    line.append(Blank.repeat(Gap));
                }
                line.append(children.get(i).line(row));
            }
            lines.add(line.toString());
        }
        return lines;
    }

    private static String stripEnd(String line) {
        int end = line.length();
        while (end > 0 && line.charAt(end - 1) == ' ') {
            end--;
        }
        return line.substring(0, end);
    }

    private static int widthOf(String drawing) {
        int width = 0;
        for (String line : drawing.split("\n")) {
            width = Math.max(width, line.length());
        }
        return width;
    }

    /**
     * A subtree as it will be drawn: the lines it occupies, the width they
     * share, and the column its own root is centred on.
     */
    private static final class Block {

        private final List<String> lines;
        private final int width;
        private final int center;

        Block(List<String> lines, int width, int center) {
            this.lines = lines;
            this.width = width;
            this.center = center;
        }

        // The given line, padded to the block's width so that whatever is
        // drawn to the right of it starts at the same column on every line.
        String line(int row) {
            String line = row < lines.size() ? lines.get(row) : "";
            return line + Blank.repeat(width - line.length());
        }
    }
}
