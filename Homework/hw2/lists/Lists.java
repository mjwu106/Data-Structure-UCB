package lists;

/* NOTE: The file Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2, Problem #1. */

/** List problem.
 *  @MingjunWu
 */
class Lists {

    /* B. */
    /** Return the list of lists formed by breaking up L into "natural runs":
     *  that is, maximal strictly ascen
     *  ding sublists, in the same order as
     *  the original.  For example, if L is (1, 3, 7, 5, 4, 6, 9, 10, 10, 11),
     *  then result is the four-item list
     *            ((1, 3, 7), (5), (4, 6, 9, 10), (10, 11)).
     *  Destructive: creates no new IntList items, and may modify the
     *  original list pointed to by L. */
    static IntListList naturalRuns(IntList L) {
        IntListList arr = new IntListList();
        IntListList  temp = arr;
        IntList temp2 = L;
        if (L==null) {
            return null;
        }
        if (L.tail == null) {
            arr.head = L;
            return arr;
        }
        for (IntList lst = L.tail; lst != null; lst = lst.tail) {
            arr.head = L;
            if (temp2.head>=lst.head) {
                temp.tail = new IntListList();
                temp = temp.tail;
                temp.head = lst;
                temp2.tail = null;
                temp2 = lst;
            }else{
                temp2 = lst;
            }

        }
        return arr;
    }
}
