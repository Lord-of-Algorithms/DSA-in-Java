/*
 *  Copyright 2019 (C) github.com/Lord-of-Algorithms
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package binarytree;

import java.util.*;

enum NumberDigit {
    DoubleDigit(2),
    ThreeDigit(3),
    FourDigit(4);

    int digit;

    NumberDigit(int digit) {
        this.digit = digit;
    }
}

public final class BinaryTreePrinter {

    private final static int MaxLevel = 6;
    private final static String Blank = " ";
    private final static int MinNodeKeyValue = 0;

    private static int maxNodeKeyValue;
    private static int keyNodeValueDigits;

    private BinaryTreePrinter() {
        // prevent initializing
    }

    /**
     * Prints the binary tree
     *
     * @param root The root of the tree
     */
    public static void print(INode root) {
        print(root, NumberDigit.DoubleDigit, MaxLevel);
    }

    /**
     * Prints the binary tree
     *
     * @param root           The root of the tree
     * @param keyNumberDigit The number digit of the key
     * @param maxTreeLevel   The max tree level
     */
    public static void print(INode root, NumberDigit keyNumberDigit, int maxTreeLevel) {
        if (root == null) {
            System.out.println("The tree is empty.");
            return;
        }
        keyNodeValueDigits = keyNumberDigit.digit;
        calculateValidMaxNodeKeyValue(keyNumberDigit);

        int maxLevel = getTreeMaxLevel(root);
        if (maxLevel > maxTreeLevel) {
            throw new RuntimeException(
                    "\n\n\n" +
                            "==========================================" +
                            "\nMax level of the binary tree that can be printed is "
                            + maxTreeLevel + ". Current level is " + maxLevel +
                            ".\n==========================================\n\n\n");
        }
        if (maxLevel == maxTreeLevel) {
            // We reduce the length of edges connecting the nodes. It doesn't change the structure of the tree,
            // but makes the printed tree smaller. We do it only for edge case, when level of tree equals to maxTreeLevel.
            maxLevel -= 1;
        }

        var levelMap = new LinkedHashMap<INode, Integer>(); // position plays a role
        int level = 0;
        fillLevelMap(root, level, levelMap);

        var nodeStartMarginMap = new HashMap<INode, Integer>();
        fillNodeStartMarginMap(root, levelMap, true, 0, false, nodeStartMarginMap, maxLevel - 1);
        System.out.println();
        printBinaryTree(root, maxLevel - 1, nodeStartMarginMap, levelMap);
        System.out.println("\n");
    }

    private static void calculateValidMaxNodeKeyValue(NumberDigit keyNumberDigit) {
        switch (keyNumberDigit) {
            case ThreeDigit:
                maxNodeKeyValue = 999;
                break;
            case FourDigit:
                maxNodeKeyValue = 9999;
                break;
            default:
                maxNodeKeyValue = 99;
        }
    }

    private static int getTreeMaxLevel(INode node) {
        if (node == null)
            return 0;
        return Math.max(getTreeMaxLevel(node.getLeft()), getTreeMaxLevel(node.getRight())) + 1;
    }

    /**
     * Associates each node with its level in the binary tree.
     */
    private static void fillLevelMap(
            INode root,
            int level,
            Map<INode, Integer> levelMap
    ) {
        if (root != null) {

            if (root.getKey() < MinNodeKeyValue || root.getKey() > maxNodeKeyValue) {
                throw new RuntimeException("\n\n\n" +
                        "==========================================" +
                        "\nThe key of the node must be >= "
                        + MinNodeKeyValue + " and <= " + maxNodeKeyValue +
                        "\n==========================================\n\n\n");
            }
            fillLevelMap(root.getLeft(), level + 1, levelMap);
            levelMap.put(root, level);
            fillLevelMap(root.getRight(), level + 1, levelMap);
        }
    }

    /**
     * Calculates startMargin for each node (in pre-order way) and associate this value
     * with this node by using a map. Later these margins will be used
     * for printing.
     *
     * @param startMargin start - means from the left (to not mix with left child. There is no connection between these terms).
     */
    private static void fillNodeStartMarginMap(
            INode root,
            Map<INode, Integer> nodeLevelMap,
            boolean isRoot,
            int startMargin,
            boolean isLeftChild,
            Map<INode, Integer> nodeStartMarginMap,
            int maxLevel) {

        if (root != null) {
            int nodeLevel = nodeLevelMap.get(root);
            // Each level has its own space between nodes
            int spaceBetweenNodes = keyNodeValueDigits * ((int) Math.pow(2, (maxLevel - nodeLevel + 1)) - 1);

            if (isRoot) {
                startMargin = keyNodeValueDigits * ((int) Math.pow(2, (maxLevel - nodeLevel)) - 1);
            } else if (isLeftChild) {
                // for left child the margin is smaller
                startMargin = startMargin - spaceBetweenNodes / keyNodeValueDigits - keyNodeValueDigits / 2;
            } else {
                // for right child the margin is bigger
                startMargin = startMargin + spaceBetweenNodes / keyNodeValueDigits + keyNodeValueDigits / 2;
            }

            nodeStartMarginMap.put(root, startMargin);

            fillNodeStartMarginMap(root.getLeft(), nodeLevelMap, false, startMargin, true, nodeStartMarginMap, maxLevel);
            fillNodeStartMarginMap(root.getRight(), nodeLevelMap, false, startMargin, false, nodeStartMarginMap, maxLevel);
        }
    }

    /**
     * Uses breadth-first traversal to print the nodes
     */
    private static void printBinaryTree(
            INode root,
            int maxLevel,
            Map<INode, Integer> startMarginMap,
            Map<INode, Integer> levelMap
    ) {

        if (root != null) {
            Queue<INode> queue = new LinkedList<>();
            queue.add(root);
            var currentLevel = 0;
            var currentPosition = 0;

            while (!queue.isEmpty()) {
                INode node = queue.remove();

                // Get the level and margin from the maps for given node.
                int startMargin = startMarginMap.get(node);
                var level = levelMap.get(node);

                if (currentLevel != level) {
                    // New level - go to next line
                    System.out.println();

                    var edgeHeight = Math.pow(2, maxLevel - currentLevel) - 1;
                    for (int i = 0; i < edgeHeight; i++) {
                        printEdges(currentLevel, startMarginMap, levelMap, i);
                    }

                    currentLevel = level;
                    currentPosition = 0;
                }

                // Because each node in the level is printed not from 0 position
                // we need to subtract node's startMargin with current position.
                int spacesBetweenNodes = startMargin - currentPosition;
                for (int i = 0; i < spacesBetweenNodes; i++) {
                    System.out.print(Blank);
                    currentPosition++;
                }
                printNode(node);
                // Remember that each number occupies keyNodeValueDigits digits. So
                // we need to increase the current position on this value.
                currentPosition += keyNodeValueDigits;

                if (node.getLeft() != null) {
                    queue.add(node.getLeft());
                }

                if (node.getRight() != null) {
                    queue.add(node.getRight());
                }
            }
        }
    }

    /**
     * Prints the value of the node's key.
     * <p>
     * Here we adjust the "x-position" of the value to get the good looking view.
     */
    private static void printNode(INode node) {
        var key = node.getKey();
        if (keyNodeValueDigits == NumberDigit.DoubleDigit.digit) {
            if (key < 10) {
                System.out.print(Blank + node);
            } else {
                System.out.print(node);
            }
        } else if (keyNodeValueDigits == NumberDigit.ThreeDigit.digit) {
            if (key < 10) {
                System.out.print(Blank + node + Blank);
            } else if (key < 100) {
                System.out.print(node + Blank);
            } else {
                System.out.print(node);
            }
        } else if (keyNodeValueDigits == NumberDigit.FourDigit.digit) {
            if (key < 10) {
                System.out.print(Blank + node + Blank + Blank);
            } else if (key < 100) {
                System.out.print(Blank + node + Blank);
            } else if (key < 1000) {
                System.out.print(node + Blank);
            } else {
                System.out.print(node);
            }
        }
    }

    private static void printEdges(
            int currentLevel,
            Map<INode, Integer> startMarginMap,
            Map<INode, Integer> levelMap,
            int iteration
    ) {
        // Get the list of nodes for each currentLevel
        var levelNodeKeys = new ArrayList<INode>();
        for (Map.Entry<INode, Integer> entry : levelMap.entrySet()) {
            var nodeKey = entry.getKey();
            var level = entry.getValue();
            if (level == currentLevel) {
                levelNodeKeys.add(nodeKey);
            }
        }

        var spaceBetweenSlashAndBackSlash = new StringBuilder();
        // The space between slash and backslash increases on two positions each iteration
        spaceBetweenSlashAndBackSlash.append(Blank.repeat(Math.max(0, 2 * iteration)));

        int currentPosition = 0;
        for (INode node : levelNodeKeys) {
            int startMargin = startMarginMap.get(node);
            int slashPosition = startMargin - currentPosition - iteration + (keyNodeValueDigits / 2 - 1);

            // Starting from the left border we print the blanks
            for (int i = 0; i < slashPosition; i++) {
                System.out.print(Blank);
                currentPosition++;
            }

            // If the node doesn't have a child, we replace the child's place with a blank.
            String slash = node.getLeft() != null ? "/" : Blank;
            var backSlash = node.getRight() != null ? "\\" : Blank;

            System.out.print(slash + spaceBetweenSlashAndBackSlash + backSlash);
            currentPosition += 2 * (iteration + 1);
        }
        System.out.println();
    }
}
