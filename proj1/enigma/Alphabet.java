package enigma;


/** An alphabet of encodable characters.  Provides a mapping from characters
 *  to and from indices into the alphabet.
 *  @author M.J.Wu
 */
class Alphabet {
    /** */
    private String _alpha;

    /** A new alphabet containing CHARS. The K-th character has index
     *  K (numbering from 0). No character may be duplicated. */
    Alphabet(String chars) {
        this._alpha = chars;
        if (!checkalphaduplicate()) {
            throw EnigmaException.error("characters duplicate");
        }
    }
    /** Returns boolean function check if duplicate exists in characters. */
    boolean checkalphaduplicate() {
        for (int i = 0; i < _alpha.length(); i++) {
            for (int j = i + 1; j < _alpha.length(); j++) {
                if (_alpha.charAt(i) == _alpha.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** A default alphabet of all upper-case characters. */
    Alphabet() {
        this("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /** Returns the size of the alphabet. */
    int size() {
        return  _alpha.length();
    }

    /** Returns true if CH is in this alphabet. */
    boolean contains(char ch) {
        for (int i = 0; i < _alpha.length(); i++) {
            if (_alpha.charAt(i) == ch) {
                return true;
            }

        }
        return false;
    }


    /** Returns character number INDEX in the alphabet, where
     *  0 <= INDEX < size(). */
    char toChar(int index) {
        return _alpha.charAt(index);
    }

    /** Returns the index of character CH which must be in
     *  the alphabet. This is the inverse of toChar(). */
    int toInt(char ch) {
        return _alpha.indexOf(String.valueOf(ch));
    }

}
