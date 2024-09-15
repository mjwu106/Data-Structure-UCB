package enigma;

import java.util.Collection;

import static enigma.EnigmaException.error;


/** Class that represents a complete enigma machine.
 *  @author M.J.Wu
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _numPawls = pawls;
        _allRotors = allRotors;
        _rotors = new Rotor[numRotors];
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _numPawls;
    }

    /** Set rotors to default ring setting. */
    void tenRings() {
        for (int i = 0; i < _rotors.length - 1; i += 1) {
            _rotors[i + 1].setRing(0);
        }
    }

    /** Set ring settings according to SETTING. */
    void tenRings(String setting) {
        if (setting.length() >= _rotors.length) {
            throw error("ring settings error.");
        } else if (setting.length() < _rotors.length - 1) {
            throw error(" ring settings error. ");
        }
        for (int i = 0; i < setting.length(); i += 1) {
            _rotors[i + 1].setRing(setting.charAt(i));
        }
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        boolean state = false;
        for (int i = 0; i < rotors.length; i++) {
            for (Rotor rotor : _allRotors) {
                if (rotor.name().equals(rotors[i])) {
                    if (state) {
                        throw new EnigmaException("No duplicated");

                    } else if (i < _numRotors - _numPawls
                            && rotor.rotates()) {
                        throw error("Can't move.");
                    } else {
                        _rotors[i] = rotor;
                        state = true;
                    }
                }
            }
            if (!state) {
                throw new EnigmaException("Invalid Rotor");
            } else {
                state = false;
            }
        }
        if (!_rotors[0].reflecting()) {
            throw new EnigmaException("The first rotor is a reflector");
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).*/
    void setRotors(String setting) {
        if (setting.length() < numRotors() - 1) {
            throw new EnigmaException("Invalid setting setRotors");
        } else if (!(setting.length() < numRotors())) {
            throw new EnigmaException("Invalid setting setRotors");
        }
        for (int i = 0; i < setting.length(); i++) {
            _rotors[i + 1].set(setting.charAt(i));
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugboard = plugboard;
    }




    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        boolean[] attack = new boolean[_numRotors];
        for (int i = 1; i < _numRotors; i++) {
            if (i == _numRotors - 1) {
                attack[i] = true;
            } else if (_rotors[i].rotates()
                    && _rotors[i + 1].atNotch()) {
                attack[i] = attack[i + 1] = true;
            }
            if (attack[i]) {
                _rotors[i].advance();
            }
        }
        int cR7 = _plugboard.permute(c);
        for (int j = _numRotors - 1; j >= 0; j--) {
            cR7 = _rotors[j].convertForward(cR7);
        }
        for (int k = 1; k < _numRotors; k++) {
            cR7 = _rotors[k].convertBackward(cR7);
        }
        return _plugboard.invert(cR7);
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        int N = msg.length();
        char[] code = new char[N];
        for (int i = 0; i < msg.length(); i++) {
            if ("\t\n ".indexOf(msg.charAt(i)) == -1) {
                code[i] = _alphabet.toChar(convert(
                        _alphabet.toInt(msg.charAt(i))));
            } else {
                code[i] = msg.charAt(i);
            }
        }
        return new String(code);
    }






    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;
    /** number of rotor slots. */
    private int _numRotors;
    /** number pawls (and thus rotating rotors). */
    private int _numPawls;
    /** A List that contains all rotors that are used in the machine. */
    private Rotor[] _rotors;
    /** A List that contains all the rotors. */
    private Collection<Rotor> _allRotors;
    /** this plugboard. */
    private Permutation _plugboard;
}
