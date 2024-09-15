/** Multidimensional array 
 *  @author Zoe Plaxco
 */

public class MultiArr {

    /**
     * {{“hello”,"you",”world”} ,{“how”,”are”,”you”}} prints:
     * Rows: 2
     * Columns: 3
     * <p>
     * {{1,3,4},{1},{5,6,7,8},{7,9}} prints:
     * Rows: 4
     * Columns: 4
     */
    public static void printRowAndCol(int[][] arr) {
        int Row = arr.length;
        int Columns = 0;
        int n = Row - 1;
        while (n >= 0) {
            if (arr[n].length > Columns) {
                Columns = arr[n].length;
            }
            n = n - 1;
        }
        System.out.println("Rows: " + Row);
        System.out.println("Columns: " + Columns);

    }

    /**
     * @param arr: 2d array
     * @return maximal value present anywhere in the 2d array
     */
    public static int maxValue(int[][] arr) {
        int Value = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                Value = Math.max(Value, arr[i][j]);
            }
        }
        return Value;
    }

    /**Return an array where each element is the sum of the 
    corresponding row of the 2d array*/
    public static int[] allRowSums(int[][] arr) {
        int [] arr1 = new int[arr.length];
        int n = arr.length -1;
        while (n>=0){
            for (int i = 0; i < arr[n].length; i++) {
                arr1[n] += arr[n][i];
            }
            n= n-1;
        }
        return arr1;
    }
}