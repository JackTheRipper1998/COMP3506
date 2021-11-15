public class QuaternaryHeapsort {

    /**
     * time complexity: O(nlog(n))
     * space complexity: O(1)
     * Sorts the input array, in-place, using a quaternary heap sort.
     *
     * @param input to be sorted (modified in place)
     */
    public static <T extends Comparable<T>> void quaternaryHeapsort(T[] input) {
        // TODO: implement question 1 here
        int size = input.length;  // the size of the input array

        // build a downheap of the given array
        quaternaryDownheap(input, 0, size);

        // sort
        for (int i = size - 1; i > 0; i--) {
            swap(input, 0, i);
            quaternaryDownheap(input, 0, i);
        }

    }

    /**
     * time complexity: O(nlog(n))
     * space complexity: O(1)
     *
     * Performs a downheap from the element in the given position on the given max heap array.
     *
     * A downheap should restore the heap order by swapping downwards as necessary.
     * The array should be modified in place.
     * 
     * You should only consider elements in the input array from index 0 to index (size - 1)
     * as part of the heap (i.e. pretend the input array stops after the inputted size).
     *
     * @param input array representing a quaternary max heap.
     * @param start position in the array to start the downheap from.
     * @param size the size of the heap in the input array, starting from index 0
     */
    public static <T extends Comparable<T>> void quaternaryDownheap(T[] input, int start, int size) {
        // TODO: implement question 1 here
        // build a downheap
        for (int i = size / 4; i >= start; i--) {
            partlyDownheap(input, i, size);
        }
    }

    /**
     * time complexity: O(log(n))
     * space complexity: O(1)
     *
     * heapify an subtree rooted with node start
     *
     * @param input array representing a quaternary max heap.
     * @param start position in the array to start the heapify from.
     * @param size the size of the heap in the input array, starting from index 0
     */
    private static <T extends Comparable<T>> void partlyDownheap(T[] input, int start, int size) {
        int maxIndex = start; // Initialize largest element as root
        int nodeOne = 4 * start + 1; // First child of the root
        int nodeTwo = 4 * start + 2; // Second child of the root
        int nodeThree = 4 * start + 3; // Third child of the root
        int nodeFour = 4 * start + 4; // Forth child of the root

        if (nodeOne < size && input[nodeOne].compareTo(input[maxIndex]) > 0) {
            maxIndex = nodeOne;
        }

        if (nodeTwo < size && input[nodeTwo].compareTo(input[maxIndex]) > 0) {
            maxIndex = nodeTwo;
        }

        if (nodeThree < size && input[nodeThree].compareTo(input[maxIndex]) > 0) {
            maxIndex = nodeThree;
        }

        if (nodeFour < size && input[nodeFour].compareTo(input[maxIndex]) > 0) {
            maxIndex = nodeFour;
        }

        // if there is a child bigger than root swap
        if (maxIndex != start) {
            swap(input, start, maxIndex);

            //check if there are any impacts on subtree
            quaternaryDownheap(input, maxIndex, size);
        }
    }


    /**
     * time complexity: O(1)
     * space complexity: O(1)
     *
     * swap two element's position
     *
     * @param input array representing a quaternary max heap.
     * @param beforePos the original position of the element
     * @param afterPos the position wanted to swap
     */
    private static <T extends Comparable<T>> void swap(T[] input, int beforePos, int afterPos) {
        T temp;

        temp = input[beforePos];
        input[beforePos] = input[afterPos];
        input[afterPos] = temp;
    }

}
