package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/**
 * The suite of all JUnit tests for the Permutation class. For the purposes of
 * this lab (in order to test) this is an abstract class, but in proj1, it will
 * be a concrete class. If you want to copy your tests for proj1, you can make
 * this class concrete by removing the 4 abstract keywords and implementing the
 * 3 abstract methods.
 *
 *  @author M.J.Wu
 */
public class PermutationTest {

    /**
     * For this lab, you must use this to get a new Permutation,
     * the equivalent to:
     * new Permutation(cycles, alphabet)
     * @return a Permutation with cycles as its cycles and alphabet as
     * its alphabet
     * @see Permutation for description of the Permutation conctructor
     */
    Permutation getNewPerm(String cycles, Alphabet alphabet) {
        return new Permutation(cycles, alphabet);

    }

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet(chars)
     * @return an Alphabet with chars as its characters
     * @see Alphabet for description of the Alphabet constructor
     */
    Alphabet getNewAlphabet(String chars) {
        return new Alphabet(chars);
    }

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet()
     * @return a default Alphabet with characters ABCD...Z
     * @see Alphabet for description of the Alphabet constructor
     */
    Alphabet getNewAlphabet() {
        return new Alphabet();
    }

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /** Check that PERM has an ALPHABET whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha,
                           Permutation perm, Alphabet alpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.toInt(c), ei = alpha.toInt(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        Alphabet alpha = getNewAlphabet();
        Permutation perm = getNewPerm("", alpha);
        checkPerm("identity", UPPER_STRING, UPPER_STRING, perm, alpha);
    }
    @Test
    public void testDerangement() {
        Permutation p = getNewPerm("(BACD)", getNewAlphabet("ABCD"));
        assertTrue(p.derangement());
        Permutation p1 = getNewPerm("(ILK) (J)", getNewAlphabet("IJKL"));
        assertFalse(p1.derangement());
        Permutation p2 = getNewPerm("(ILK)", getNewAlphabet("IJKL"));
        assertFalse(p2.derangement());
    }




    @Test
    public void testInvertChar() {
        Permutation p = getNewPerm("(BACD)", getNewAlphabet("ABCD"));
        assertEquals('B', p.invert('A'));
        assertEquals('D', p.invert('B'));
        assertEquals('C', p.invert('D'));
        assertEquals('A', p.invert('C'));
    }



    @Test
    public void testPermuteInt() {
        Permutation p = getNewPerm("(BACD)", getNewAlphabet("ABCD"));
        assertEquals(2, p.permute(0));
        assertEquals(0, p.permute(1));
        assertEquals(1, p.permute(3));
    }
    @Test
    public void testPermuteChar() {
        Permutation p = getNewPerm("(BACD)", getNewAlphabet("ABCD"));
        Permutation q = getNewPerm("(A) (B) (C) (DE)", getNewAlphabet("ABCDE"));
        assertEquals('C', p.permute('A'));
        assertEquals('A', p.permute('B'));
        assertEquals('B', p.permute('D'));
        assertEquals('A', q.permute('A'));
        assertEquals('D', q.permute('E'));
    }
    @Test
    public void testSize() {
        Permutation p = getNewPerm(" (BACD)", getNewAlphabet("ABCD"));
        assertEquals(4, p.size());
    }


}
