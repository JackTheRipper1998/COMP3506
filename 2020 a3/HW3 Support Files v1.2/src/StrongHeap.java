public class StrongHeap {
    /**
     * Determines whether the binary tree with the given root node is
     * a "strong binary heap", as described in the assignment task sheet.
     *
     * A strong binary heap is a binary tree which is:
     *  - a complete binary tree, AND
     *  - its values satisfy the strong heap property.
     *
     * @param root root of a binary tree, cannot be null.
     * @return true if the tree is a strong heap, otherwise false.
     */
    public static boolean isStrongHeap(BinaryTree<Integer> root) {
        // TODO: implement question 2
        if (root == null) {
            return true;
        }

        // count total nodes in the binary tree
        int totalNodes = countNodes(root);

        //check tree's property
        if (isComplete(root, 0, totalNodes)) {
            return (isHeapProperty(root) && isStrongHeapProperty(root));
        } else {
            return false;
        }
    }

    /**
     * time complexity: O(n)
     * space complexity: O(1)
     *
     * Count the total number of nodes a binary tree has.
     *
     * @param root root of a binary tree, cannot be null.
     * @return the number of nodes the input binary tree has.
     */
    private static int countNodes(BinaryTree<Integer> root) {
        if (root == null) {
            return (0);
        } else {
            return (1 + countNodes(root.getLeft()) + countNodes(root.getRight()));
        }
    }

    /**
     * time complexity: O(n)
     * space complexity: O(1)
     *
     * Check if a binary tree is complete binary tree.
     *
     * @param root root of a binary tree, cannot be null.
     * @param nodeIndex then index of current node.
     * @param totalNodes the total number of node.
     * @return true if the tree is a complete binary tree, otherwise false.
     */
    private static boolean isComplete(BinaryTree<Integer> root, int nodeIndex, int totalNodes) {
        // An empty tree is a complete tree.
        if (root == null) {
            return true;
        }

        // If current node index larger than total nodes number
        // it is not a complete tree
        if (nodeIndex >= totalNodes) {
            return false;
        }

        int leftIndex = 2 * nodeIndex + 1;
        int rightIndex = 2 * nodeIndex + 2;

        //Recur for left and right subtree
        return (isComplete(root.getLeft(), leftIndex, totalNodes)
                && isComplete(root.getRight(), rightIndex, totalNodes));
    }

    /**
     * time complexity: O(n)
     * space complexity: O(1)
     *
     * Check if a complete binary tree is a heap.
     *
     * @param root root of a binary tree, cannot be null.
     * @return true if is a heap, otherwise false.
     */
    private static boolean isHeapProperty(BinaryTree<Integer> root) {
        //Base case: if current node is leaf then satisfied
        if (root.isLeaf()) {
            return true;
        }

        // Check when right child node is null
        if (root.getRight() == null) {
            return root.getLeft().getValue() < root.getValue();
        } else {
            // Check if both children satisfy heap property
            if (root.getValue() > root.getLeft().getValue()
            && root.getValue() > root.getRight().getValue()) {
                return (isHeapProperty(root.getLeft()) && isHeapProperty(root.getRight()));
            } else {
                return false;
            }
        }
    }

    /**
     * time complexity: O(n)
     * space complexity: O(1)
     *
     * Check if a heap is a strong heap.
     * for nodes x in level 2 or below, we also have x + parent(x) < parent(parent(x)).
     *
     * @param root root of a binary tree, cannot be null.
     * @return true if is a strong heap, otherwise false.
     */
    private static boolean isStrongHeapProperty(BinaryTree<Integer> root) {
        // If root is leaf of only have single child
        // then it is a strong heap
        if (root.isLeaf() || root.getRight() == null) {
            return true;
        }

        // If root do not have grandchildren then it is satisfied
        if (root.getRight().isLeaf() && root.getLeft().isLeaf()) {
            return true;
        }

        // Check when node has grandchildren
        if (!root.getRight().isLeaf() && !root.getLeft().isLeaf()) {
            //check when node's both children are not leaf
            if (!root.getLeft().isLeaf() && root.getRight().getRight() == null) {
                return (root.getValue() > root.getLeft().getValue() + root.getLeft().getRight().getValue() &&
                        root.getValue() > root.getLeft().getValue() + root.getLeft().getLeft().getValue() &&
                        root.getValue() > root.getRight().getValue() + root.getRight().getLeft().getValue());
            } else {
                return (root.getValue() > root.getLeft().getValue() + root.getLeft().getRight().getValue() &&
                        root.getValue() > root.getLeft().getValue() + root.getLeft().getLeft().getValue() &&
                        root.getValue() > root.getRight().getValue() + root.getRight().getLeft().getValue() &&
                        root.getValue() > root.getRight().getValue() + root.getRight().getRight().getValue());
            }
        } else if (root.getRight().isLeaf() && !root.getLeft().isLeaf()) {
            // check when node's right child is leaf
            if (root.getLeft().getRight() == null) {
                return root.getValue() > root.getLeft().getValue() + root.getLeft().getLeft().getValue();
            } else {
                return (root.getValue() > root.getLeft().getValue() + root.getLeft().getRight().getValue() &&
                        root.getValue() > root.getLeft().getValue() + root.getLeft().getLeft().getValue());
            }
        } else {
            // keep check if both child satisfy strong heap property
            return isStrongHeapProperty(root.getLeft()) && isStrongHeapProperty(root.getRight());
        }
    }
}

