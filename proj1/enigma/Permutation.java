package enigma;


/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author M.J. Wu
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _cycles = cycles;
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        for (int i = 0; i < cycle.length(); i++) {
            char ch = cycle.charAt(i);
            if (!alphabet().contains(ch)) {
                throw new EnigmaException("ERROR");
            } else if (!(_cycles.indexOf(ch) == -1)) {
                throw new EnigmaException("ERROR");
            }
            _cycles = _cycles + ch;
        }
    }



    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        int P = wrap(p);
        return _alphabet.toInt(permute(_alphabet.toChar(P)));
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        int C = wrap(c);
        return wrap(alphabet().toInt(invert(alphabet().toChar(C))));
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        String value = String.valueOf(p);
        int j = _cycles.indexOf(value);
        int k = 0;
        char alp = p;
        if (_cycles.indexOf(value) > 0) {
            if (_cycles.charAt(j +  1) == ')') {
                for (int i = 0; i <= j + 1; i++) {
                    if (_cycles.charAt(i) == '(') {
                        k = i;
                    }
                }
                alp = _cycles.charAt(k + 1);
            } else if (_cycles.indexOf(_cycles.charAt(j + 1)) > 0) {
                alp = _cycles.charAt(j + 1);
            }
        }
        return alp;
    }



    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        String value = String.valueOf(c);
        int j = _cycles.indexOf(value);
        int k = 0;
        char alp = c;

        for (int i = j; i < _cycles.length(); i++) {
            if (i > 0 && _cycles.charAt(i) == ')') {
                k = i;
                break;
            }
        }
        if (_cycles.indexOf(value) > 0) {
            if (_cycles.charAt(j - 1) == '(') {
                alp = _cycles.charAt(k - 1);
            } else if (_cycles.indexOf(_cycles.charAt(j - 1)) > 0) {
                alp = _cycles.charAt(j - 1);
            }
        }
        return alp;
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        for (int i = 0; i < _alphabet.size(); i++) {
            if (permute(i) ==  i) {
                return false;
            }
        }
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;
    /** Alphabet of this permutation. */
    private String _cycles;



}
