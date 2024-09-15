import java.util.List;
import java.util.LinkedList;

/** A set of String values.
 *  @author
 */

class ECHashStringSet implements StringSet {
    private LinkedList<String>[] buckets;

    private int _count;

    private int _babyyoda;

    private static double max_load = 5;

    private static double min_load = 0.2;


    @SuppressWarnings("unchecked")
    public ECHashStringSet() {
        _count = 0;
        buckets = (LinkedList<String>[]) new LinkedList[5];
        for (int i = 0; i < max_load; i = i + 1) {
            buckets[i] = new LinkedList<String>();
        }

        // loop through and make sure buckets is actually full of LinkedLists
    }


    @Override
    public void put(String s) {
        _count = _count + 1;
        double threshold = buckets.length * max_load;
        if (s != null && _count > threshold) {
            List order = asList();
            buckets = (LinkedList<String>[]) new LinkedList[buckets.length * 2];
            for (int i = 0; i < buckets.length; i++) {
                buckets[i] = new LinkedList<String>();
                }
                for (Object x : order) {
                    String jedi = (String) x;
                    int j = (jedi.hashCode() & 0x7fffffff) % buckets.length;
                    buckets[j].add(jedi);
                }
            }

            _babyyoda = (s.hashCode() & 0x7fffffff) % buckets.length;
            if (!buckets[_babyyoda].contains(s)) {
                buckets[_babyyoda].add(s);
            }
        }



    @Override
    public boolean contains(String s) {
        int i = (s.hashCode() & 0x7fffffff) % buckets.length;
        return buckets[i].contains(s); // FIXME
    }

    //public int whichBox(String s) {
        // return which bucket i the string s should be in
        // call the default string hashcode, as in s.hashCode()
        // figure out a way to make that default hashcode wrap or fix within
        // the range 0 to
    //}

    @Override
    public List<String> asList() {
        LinkedList<String> order = new LinkedList<String>();
        for(LinkedList<String> n : buckets) {
            order.addAll(n);
            }
        return order;
    }
}
