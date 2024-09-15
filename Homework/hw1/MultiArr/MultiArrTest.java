import static org.junit.Assert.*;
import org.junit.Test;

public class MultiArrTest {


    @Test
    public void testMaxValue() {
        int[][] arr = {{8,23,1} ,{10,24,7}};
        assertEquals(24, MultiArr.maxValue(arr));
        arr = new int[][] {{1,3,4},{1},{5,6,7,8},{7,9}};
        assertEquals(9, MultiArr.maxValue(arr));
    }

    @Test
    public void testAllRowSums() {
        int[][] arr1 = {{1,3,4},{1},{5,6,7,8},{7,9}};
        int[] arr2 = {8, 1, 26, 16};
        assertArrayEquals(arr2, MultiArr.allRowSums(arr1));
        arr1 = new int[][]{{8,23,1} ,{10,24,7}};
        arr2 = new int[] {32, 41};
        assertArrayEquals(arr2, MultiArr.allRowSums(arr1));
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(MultiArrTest.class));
    }
}
