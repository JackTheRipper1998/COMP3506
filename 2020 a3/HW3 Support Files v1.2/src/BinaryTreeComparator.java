import java.util.Comparator;

/**
 * A comparator for Binary Trees.
 */
public class BinaryTreeComparator<E extends Comparable<E>> implements Comparator<BinaryTree<E>> {

    /**
     * time complexity: O(n)
     * space complexity: O(1)
     *
     * Compares two binary trees with the given root nodes.
     * <p>
     * Two nodes are compared by their left childs, their values, then their right childs,
     * in that order. A null is less than a non-null, and equal to another null.
     *
     * @param tree1 root of the first binary tree, may be null.
     * @param tree2 root of the second binary tree, may be null.
     * @return -1, 0, +1 if tree1 is less than, equal to, or greater than tree2, respectively.
     */
    @Override
    public int compare(BinaryTree<E> tree1, BinaryTree<E> tree2) {
        // TODO: implement question 3 here
        // check if one of tree root is null
        if (tree1 == null && tree2 == null) {
            return 0;
        } else if (tree1 != null && tree2 == null) {
            return 1;
        } else if (tree1 == null && tree2 != null) {
            return -1;
        } else {
            return partlyCompare(tree1, tree2);
        }

    }

    /**
     * time complexity: O(n)
     * space complexity: O(1)
     *
     * Compares two none null binary trees with the given root nodes.
     *
     * @param tree1 root of the first binary tree, may be null.
     * @param tree2 root of the second binary tree, may be null.
     * @return -1, 0, +1 if tree1 is less than, equal to, or greater than tree2, respectively.
     */
    private int partlyCompare(BinaryTree<E> tree1, BinaryTree<E> tree2) {
        // visit both tree's left nodes until return a number which not equal to 0
        // or return 0
        if (tree1.getLeft() != null && tree2.getLeft() != null) {
            int result = partlyCompare(tree1.getLeft(), tree2.getLeft());
            if (result != 0) {
                return result;
            }
        }

        // compare two nodes if one of the node is null
        if (tree1.getLeft() != null && tree2.getLeft() == null) {
            return 1;
        } else if (tree1.getLeft() == null && tree2.getLeft() != null) {
            return -1;
        }

        // compare two nodes if both nodes are leftMost
        if (tree1.getValue().compareTo(tree2.getValue()) > 0) {
            return 1;
        } else if (tree1.getValue().compareTo(tree2.getValue()) < 0) {
            return -1;
        }

        // visit both tree's right nodes until return a number which not equal to 0

        if (tree1.getRight() != null && tree2.getRight() != null) {
            int result = partlyCompare(tree1.getRight(), tree2.getRight());
            if (result != 0) {
                return result;
            }
        }

        // compare two nodes if one of the node is null
        if (tree1.getRight() != null && tree2.getRight() == null) {
            return 1;
        } else if (tree1.getRight() == null && tree2.getRight() != null) {
            return -1;
        }

        // compare two nodes if both nodes are rightMost
        if (tree1.getValue().compareTo(tree2.getValue()) > 0) {
            return 1;
        } else if (tree1.getValue().compareTo(tree2.getValue()) < 0) {
            return -1;
        }

        // both node are equal
        return 0;
    }
}
