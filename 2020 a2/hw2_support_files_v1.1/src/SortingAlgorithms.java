

public class SortingAlgorithms {
    /**
     * Sorts the given array using the selection sort algorithm.
     * This should modify the array in-place.
     *
     * @param input An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void selectionSort(T[] input, boolean reversed) {
        if (reversed) {
            desSelectionSort(input, input.length);
        } else {
            ascSelectionSort(input, input.length);
        }
    }

    /**
     * Sorts ascending the given array using the selection sort algorithm.
     * @param input An array of comparable objects.
     * @param n the length of the given array.
     */
    private static <T extends Comparable> void ascSelectionSort(T[] input, int n) {
        if (n > 1) {
            int maxIndex = 0;

            for (int i = 1; i < n; i++) {
                if (input[i].compareTo(input[maxIndex]) > 0) {
                    maxIndex = i;
                }
            }

            T temp = input[n - 1];
            input[n - 1] =  input[maxIndex];
            input[maxIndex] = temp;

            ascSelectionSort(input, n - 1);
        }
    }

    /**
     * Sorts descending the given array using the selection sort algorithm.
     * @param input input An array of comparable objects.
     * @param n n the length of the given array.
     */
    private static <T extends Comparable> void desSelectionSort(T[] input, int n) {
        if (n > 1) {
            int minIndex = 0;

            for (int i = 1; i < n; i++) {
                if (input[i].compareTo(input[minIndex]) < 0) {
                    minIndex = i;
                }
            }

            //swap
            T temp = input[n - 1];
            input[n - 1] = input[minIndex];
            input[minIndex] = temp;

            desSelectionSort(input, n - 1);
        }
    }

    /**
     * Sorts the given array using the insertion sort algorithm.
     * This should modify the array in-place.
     *
     * @param input An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void insertionSort(T[] input, boolean reversed) {
        int n = input.length;

        if (reversed) {
            //ascending
            for (int i = 1; i < n; i++) {
                T key = input[i];
                int j = i - 1;

                while (j >= 0 && input[j].compareTo(key) < 0) {
                    input[j + 1] = input[j];
                    j = j - 1;
                }

                input[j + 1] = key;
            }
        } else {
            //descending
            for (int i = 1; i < n; i++) {
                T key = input[i];
                int j = i - 1;

                while (j >= 0 && input[j].compareTo(key) > 0) {
                    input[j + 1] = input[j];
                    j = j - 1;
                }

                input[j + 1] = key;
            }
        }
    }
    
    /**
     * Sorts the given array using the merge sort algorithm.
     * This should modify the array in-place.
     * 
     * @param input An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void mergeSort(T[] input, boolean reversed) {
        int l = 0;
        int r = input.length - 1;
        mergeSortStep1(input, l, r, reversed);
    }

    /**
     *
     * Divide: partition input into two halves
     * Recur: recursively sort each half
     * Conquer: merge the two halves
     *
     * @param input An array of comparable objects.
     * @param l start index of the given array
     * @param r end index of the given array
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     */
    private static <T extends Comparable> void mergeSortStep1(T[] input, int l, int r, boolean reversed) {
        if (l < r) {
            //devide
            int m = (l + r) / 2;
            mergeSortStep1(input, l, m, reversed);
            mergeSortStep1(input, m + 1, r, reversed);

            //merge
            if (reversed) {
                mergeDes(input, l, m, r);
            } else {
                mergeAsc(input, l, m, r);
            }
        }
    }

    /**
     * merge each part by ascending
     * @param input An array of comparable objects.
     * @param l start index of the given array
     * @param m divide key
     * @param r end index of the given array
     */
    private static <T extends Comparable> void mergeAsc(T[] input, int l, int m, int r) {
        int n1 = m - l + 1; // size of first half of input
        int n2 = r - m; // size of second half of input
        T[] left;
        left = (T[]) new Comparable[n1];
        T[] right;
        right = (T[]) new Comparable[n2];

        //copy of input[l..m]
        for (int i = 0; i < n1; ++i) {
            left[i] = input[l + i];
        }

        //copy of input[m..r]
        for (int j = 0; j < n2; ++j) {
            right[j] = input[m + 1 + j];
        }

        int i = 0, j = 0, k = l;

        // merge into input
        while (i < n1 && j < n2) {
            if (left[i].compareTo(right[j]) <= 0 ) {
                input[k++] = left[i++];
            } else {
                input[k++] = right[j++];
            }
        }

        // copy rest of L into input
        while (i < n1) {
            input[k++] = left[i++];
        }

        // copy rest of R into input
        while (j < n2) {
            input[k++] = right[j++];
        }
    }

    /**
     * merge each part by descending
     * @param input An array of comparable objects.
     * @param l start index of the given array
     * @param m divide key
     * @param r end index of the given array
     */
    private static <T extends Comparable> void mergeDes(T[] input, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        T[] left;
        left = (T[]) new Comparable[n1];
        T[] right;
        right = (T[]) new Comparable[n2];

        //copy of input[l..m]
        for (int i = 0; i < n1; ++i) {
            left[i] = input[l + i];
        }

        //copy of input[m..r]
        for (int j = 0; j < n2; ++j) {
            right[j] = input[m + 1 + j];
        }

        int i = 0, j = 0, k = l;

        // merge into input
        while (i < n1 && j < n2) {
            if (left[i].compareTo(right[j]) >= 0 ) {
                input[k++] = left[i++];
            } else {
                input[k++] = right[j++];
            }
        }

        // copy rest of L into input
        while (i < n1) {
            input[k++] = left[i++];
        }

        // copy rest of R into input
        while (j < n2) {
            input[k++] = right[j++];
        }
    }

    
    /**
     * Sorts the given array using the quick sort algorithm.
     * This should modify the array in-place.
     * 
     * You should use the value at the middle of the input  array(i.e. floor(n/2)) 
     * as the pivot at each step.
     * 
     * @param input An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void quickSort(T[] input, boolean reversed) {
        int l = 0;
        int r = input.length - 1;

        if (reversed) {
            desQuickSortInPlace(input, l, r);
        } else {
            ascQuickSortInPlace(input, l, r);
        }


    }

/**
 * Sorts the given array ascending using the quick sort algorithm.
 *
 * You should use the value at the middle of the input  array(i.e. floor(n/2))
 * as the pivot at each step.
 *
 * @param l start index of input.
 * @param r end index of input.
 * @param input An array of comparable objects.
 */
    private static <T extends Comparable> void ascQuickSortInPlace(T[] input, int l, int r) {

        int pivotIndex = (l + r) / 2;  // pick mid index as pivot index
        int left = l;
        int right = r;
        T pivot = input[pivotIndex];

        while (left <= right) {
            // scan until reaching value equal or larger than pivot
            while (input[left].compareTo(pivot) < 0) {
                left++;
            }

            // scan until reaching value equal or smaller than pivot
            while (input[right].compareTo(pivot) > 0) {
                right--;
            }

            if (left <= right) { // indices did not strictly cross
                // swap values and shrink range
                T temp = input[left];
                input[left] = input[right];
                input[right] = temp;
                left++;
                right--;
            }
        }

        //quickSort left
        if (l < right) {
            ascQuickSortInPlace(input, l, right);
        }
        //quickSort right
        if (left < r) {
            ascQuickSortInPlace(input, left, r);
        }
    }

/**
 * Sorts the given array descending using the quick sort algorithm.
 *
 * You should use the value at the middle of the input  array(i.e. floor(n/2))
 * as the pivot at each step.
 *
 * @param l start index of input.
 * @param r end index of input.
 * @param input An array of comparable objects.
 */
    private static <T extends Comparable> void desQuickSortInPlace(T[] input, int l, int r) {

        int pivotIndex = (l + r) / 2; // pick mid index as pivot index
        int left = l;
        int right = r;
        T pivot = input[pivotIndex];

        while (left <= right) {
            // scan until reaching value equal or larger than pivot
            while (input[left].compareTo(pivot) > 0) {
                left++;
            }

            // scan until reaching value equal or smaller than pivot
            while (input[right].compareTo(pivot) < 0) {
                right--;
            }

            if (left <= right) { // indices did not strictly cross
                // swap values and shrink range
                T temp = input[left];
                input[left] = input[right];
                input[right] = temp;
                left++;
                right--;
            }
        }

        //quick sort left
        if (l < right) {
            desQuickSortInPlace(input, l, right);
        }

        //quick sort right
        if (left < r) {
            desQuickSortInPlace(input, left, r);
        }
    }

}

