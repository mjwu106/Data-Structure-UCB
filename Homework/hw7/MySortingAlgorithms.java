import java.util.Arrays;

/**
 * Note that every sorting algorithm takes in an argument k. The sorting 
 * algorithm should sort the array from index 0 to k. This argument could
 * be useful for some of your sorts.
 *
 * Class containing all the sorting algorithms from 61B to date.
 *
 * You may add any number instance variables and instance methods
 * to your Sorting Algorithm classes.
 *
 * You may also override the empty no-argument constructor, but please
 * only use the no-argument constructor for each of the Sorting
 * Algorithms, as that is what will be used for testing.
 *
 * Feel free to use any resources out there to write each sort,
 * including existing implementations on the web or from DSIJ.
 *
 * All implementations except Counting Sort adopted from Algorithms,
 * a textbook by Kevin Wayne and Bob Sedgewick. Their code does not
 * obey our style conventions.
 */
public class MySortingAlgorithms {

    /**
     * Java's Sorting Algorithm. Java uses Quicksort for ints.
     */
    public static class JavaSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            Arrays.sort(array, 0, k);
        }

        @Override
        public String toString() {
            return "Built-In Sort (uses quicksort for ints)";
        }
    }

    /** Insertion sorts the provided data. */
    public static class InsertionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
            int key;
            int j;
            for (int i = 1; i < k; i++) {
                key = array[i];
                j = i - 1;
                while (j >= 0 && array[j] > key) {
                    array[j + 1] = array[j];
                    j--;
                }
                array[j + 1] = key;
            }
        }

        @Override
        public String toString() {
            return "Insertion Sort";
        }
    }

    /**
     * Selection Sort for small K should be more efficient
     * than for larger K. You do not need to use a heap,
     * though if you want an extra challenge, feel free to
     * implement a heap based selection sort (i.e. heapsort).
     */
    public static class SelectionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
            int minIndex;
            int j;
            int temp;
            for (int i = 0; i < k-1; i++) {
                minIndex = i;
                for (j = i + 1; j < k; j++) {
                    if (array[minIndex] > array[j]) {
                        minIndex = j;
                    }
                }
                temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }

        @Override
        public String toString() {
            return "Selection Sort";
        }
    }

    /** Your mergesort implementation. An iterative merge
      * method is easier to write than a recursive merge method.
      * Note: I'm only talking about the merge operation here,
      * not the entire algorithm, which is easier to do recursively.
      */
    public static class MergeSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
            mergeHelper(array, k);
        }

            /** Helper method for MergeSort. */
            public static int[] mergeHelper(int[] array, int x) {
                int mid;
                int i = 0, j = 0, k = 0;
                int[] left, right;
                if (array.length == 1) {
                    return array;
                }
                mid = Math.floorDiv(x, 2);
                left = new int[mid];
                right = new int[x - mid];
                System.arraycopy(array, 0, left, 0, mid);
                System.arraycopy(array, mid, right, 0, x - mid);
                mergeHelper(left, mid);
                mergeHelper(right, x - mid);
                while (i < left.length && j < right.length) {
                    if (left[i] <= right[j]) {
                        array[k] = left[i];
                        i++;
                    } else {
                        array[k] = right[j];
                        j++;
                    }
                    k++;
                }
                while (i < left.length) {
                    array[k] = left[i];
                    i++;
                    k++;
                }
                while (j < right.length) {
                    array[k] = right[j];
                    j++;
                    k++;
                }
                return array;
        }

        // may want to add additional methods

        @Override
        public String toString() {
            return "Merge Sort";
        }
    }

    /**
     * Your Counting Sort implementation.
     * You should create a count array that is the
     * same size as the value of the max digit in the array.
     */
    public static class CountingSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME: to be implemented
        }

        // may want to add additional methods

        @Override
        public String toString() {
            return "Counting Sort";
        }
    }

    /** Your Heapsort implementation.
     */
    public static class HeapSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "Heap Sort";
        }
    }

    /** Your Quicksort implementation.
     */
    public static class QuickSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "Quicksort";
        }
    }

    /* For radix sorts, treat the integers as strings of x-bit numbers.  For
     * example, if you take x to be 2, then the least significant digit of
     * 25 (= 11001 in binary) would be 1 (01), the next least would be 2 (10)
     * and the third least would be 1.  The rest would be 0.  You can even take
     * x to be 1 and sort one bit at a time.  It might be interesting to see
     * how the times compare for various values of x. */

    /**
     * LSD Sort implementation.
     */
    public static class LSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            // FIXME
            int[] temp = new int [k];
            int[][] b = new int [10][k];
            int[] c = new int [10];
            int digit = 1;
            for (int i = 0; i < k; i++) {
                temp[i] = a[i];
            }
            int max = temp[0];
            for (int i = 1; i < k; i++) {
                if (max < temp[i])
                    max = temp[i];
            }
            for (int i = 0; i < String.valueOf(max).length(); i++) {
                for (int el : temp) {
                    int j = (el / digit) % 10;
                    b[j][c[j]] = el;
                    c[j]++;
                }
                int z = 0;
                for (int x = 0; x < 10; x++) {
                    if (c[x] != 0) {
                        for (int y = 0; y < c[x]; y++) {
                            temp[z] = b[x][y];
                            z++;
                        }
                    }
                    c[x] = 0;
                }
                digit *= 10;
            }
            for (int i = 0; i < k; i++) {
                a[i] = temp[i];
            }
        }

        @Override
        public String toString() {
            return "LSD Sort";
        }
    }

    /**
     * MSD Sort implementation.
     */
    public static class MSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "MSD Sort";
        }
    }

    /** Exchange A[I] and A[J]. */
    private static void swap(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

}
