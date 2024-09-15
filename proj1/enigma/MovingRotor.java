package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author M.J. Wu
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _notches = notches;
    }

    @Override
    void advance() {
        if (setting() == size() - 1) {
            set(0);
        } else {
            this.set(permutation().wrap(this.setting() + 1));
        }
    }

    @Override
    boolean rotates() {
        return true;
    }

    @Override
    boolean atNotch() {
        char cH = permutation().alphabet().toChar(setting());
        for (int i = 0; i < _notches.length(); i++) {
            if (_notches.charAt(i) == cH) {
                return true;
            }
        }
        return false;
    }
    /** Notches of the MovingRotors. */

    private String _notches;


}
