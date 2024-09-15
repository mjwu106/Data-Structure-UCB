package arrays;

/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

import lists.IntList;
import lists.IntListList;

/** Array utilities.
 *  @Mingjun Wu
 */
class Arrays {

    /* C1. */

    /**
     * Returns a new array consisting of the elements of A followed by the
     * the elements of B.
     */
    static int[] catenate(int[] A, int[] B) {
        int LEN = A.length + B.length;
        int[] arr = new int[LEN];
        for (int i = 0; i < A.length; i++) {
            arr[i] = A[i];
            for (int j = 0; j < B.length; j++) {
                arr[A.length + j] = B[j];
            }
        }
        return arr;
    }

    /* C2. */

    /**
     * Returns the array formed by removing LEN items from A,
     * beginning with item #START. If the start + len is out of bounds for our array, you
     * can return null.
     * Example: if A is [0, 1, 2, 3] and start is 1 and len is 2, the
     * result should be [0, 3].
     */
    static int[] remove(int[] A, int start, int len) {
        int[] removed_lst = new int[A.length - len];
        for (int i = 0; i < removed_lst.length; i++) {
            if (i <= start) {
                System.arraycopy(A, 0, removed_lst, 0, start);
                System.arraycopy(A, start + len, removed_lst, start, A.length - len - start);
                /**https://www.tutorialspoint.com/java/lang/system_arraycopy.htm**/
            } else {
                return null;
            }
        }
        return removed_lst;
    }

    /* C3. */

    /**
     * Returns the array of arrays formed by breaking up A into
     * maximal ascending lists, without reordering.
     * For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     * returns the three-element array
     * {{1, 3, 7}, {5}, {4, 6, 9, 10}}.
     */
    static int[][] naturalRuns(int[] A) {
        int i = 1;
        int index = 0;
        int el = 0;
        for (int j = 1; j < A.length; j++) {
            if (A[j - 1] >= A[j]) {
                i++;
            }
        }
        int[][] lst = new int[i][];
        for (int j = 1; j < A.length; j++) {
            if (A[j - 1] >= A[j]) {
                lst[index]=Utils.subarray(A,el,j-el);
                index ++;
                el = j;
            }
        }
        if (lst[index] == null) {
            lst[index] = Utils.subarray(A,el,A.length-el);
        }
        return lst;
    }
}