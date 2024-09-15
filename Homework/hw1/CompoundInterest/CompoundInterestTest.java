import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {
    static final double DELTA = 1e-2;

    @Test
    public void testNumYears() {

        assertEquals(0, CompoundInterest.numYears(2021), DELTA);
        assertEquals(1, CompoundInterest.numYears(2022), DELTA);
        assertEquals(100, CompoundInterest.numYears(2121), DELTA);
    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
        assertEquals(12.54, CompoundInterest.futureValue(10, 12, 2023), tolerance);
        assertEquals(100, CompoundInterest.futureValue(100, 20, 2021), tolerance);
        assertEquals(42.19, CompoundInterest.futureValue(100, -25, 2024), tolerance);
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;

    assertEquals(11.8026496 ,CompoundInterest.futureValueReal(10,12,2023,3),tolerance);
    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
        assertEquals(16550, CompoundInterest.totalSavings(5000, 2023,10 ), tolerance);
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        assertEquals(14936.375, CompoundInterest.totalSavingsReal(5000, 2023, 10,5), tolerance);
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
