package enigma;

/** Superclass that represents a rotor in the enigma machine.
 *  @author M.J. Wu
 */
class Rotor {
    /** The current setting. */
    protected int _setting;

    /** A rotor named NAME whose permutation is given by PERM. */
    Rotor(String name, Permutation perm) {
        _name = name;
        _permutation = perm;
        _setting = 0;
        _ring = 0;
    }

    /** Return my name. */
    String name() {
        return _name;
    }

    /** Return my alphabet. */
    Alphabet alphabet() {
        return _permutation.alphabet();
    }

    /** Return my permutation. */
    Permutation permutation() {
        return _permutation;
    }

    /** Return the size of my alphabet. */
    int size() {
        return _permutation.size();
    }

    /** Return true iff I have a ratchet and can move. */
    boolean rotates() {
        return false;
    }

    /** Return true iff I reflect. */
    boolean reflecting() {
        return false;
    }

    /** Return my current setting. */
    int setting() {
        return _setting;
    }

    /** Set setting() to POSN.  */
    void set(int posn) {
        _setting = permutation().wrap(posn);
    }

    /** Set setting() to character CPOSN. */
    void set(char cposn) {
        _setting = alphabet().toInt(cposn);
    }


    /** Return the conversion of P (an integer in the range 0..size()-1)
     *  according to my permutation. */
    int convertForward(int p) {
        if (p > 0 || p < size()) {
            int q = p + _setting - _ring;
            int s = _permutation.permute(_permutation.wrap(q));
            return _permutation.wrap(s - _setting + _ring);
        } else {
            throw new IllegalArgumentException("Illegal Input");
        }
    }

    /** Return the conversion of E (an integer in the range 0..size()-1)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        if (e > 0 || e < size()) {
            int f = e + _setting - _ring;
            int t = _permutation.invert(_permutation.wrap(f));
            return _permutation.wrap(t - _setting + _ring);
        } else {
            throw new IllegalArgumentException("Illegal Input");
        }
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        return false;
    }

    /** Set ring to Int POSN. */
    void setRing(int posn) {
        _ring = posn;
    }

    /** Set setRing() to character CPOSN. */
    void setRing(char cposn) {
        _ring = _permutation.alphabet().toInt(cposn);
    }

    /** Advance me one position, if possible. By default, does nothing. */
    void advance() {
    }

    @Override
    public String toString() {
        return "Rotor " + _name;
    }

    /** My name. */
    private final String _name;

    /** The permutation implemented by this rotor in its 0 position. */
    private Permutation _permutation;

    /** The ring setting. */
    private  int _ring;

}
