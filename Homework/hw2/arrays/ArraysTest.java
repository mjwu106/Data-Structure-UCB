package arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/** Array utilities Test
 *  @author Mingjun Wu
 *
 */

public class ArraysTest {
    @Test
    public void catenateTest() {
        int[] TestA = {1,2,3,4,5};
        int[] TestB = {6,7,8,9};
        int[] Test_arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals (Test_arr , Arrays.catenate(TestA , TestB));
    }

    @Test
    public void testRemove() {
        int[] testA = {7, 10, 23, 24, 35};
        int[] Test_remove = {7, 10, 35};
        assertArrayEquals(Test_remove, Arrays.remove(testA, 2, 2));
    }

    @Test
    public void testNaturalRuns() {
        int[] testA = {1, 3, 7, 5, 4, 6, 9, 10, 10, 11};
        int[][] testB = Arrays.naturalRuns(testA);
        int[][] testC = {{1, 3, 7}, {5}, {4, 6, 9, 10}, {10, 11}};
        assertTrue(Utils.equals(testB, testC));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
