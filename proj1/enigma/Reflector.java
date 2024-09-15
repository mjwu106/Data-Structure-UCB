package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a reflector in the enigma.
 *  @author M.J. Wu
 */
class Reflector extends FixedRotor {

    /** A non-moving rotor named NAME whose permutation at the 0 setting
     * is PERM. */
    Reflector(String name, Permutation perm) {
        super(name, perm);
    }
    @Override
    boolean reflecting() {
        return true;
    }

    @Override
    void set(char cposn) {
        set(alphabet().toInt(cposn));
    }

    @Override
    void set(int posn) {
        if (posn != 0) {
            throw error("there is only room for one");
        }
    }

}
