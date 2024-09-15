/** This file contains a SUGGESTION for the structure of your program.  You
 * may change any of it, or add additional files to this directory (package),
 * as long as you conform to the project specification.

 * Comments that start with "//" are intended to be removed from your
 * solutions.
 * */
package jump61;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Formatter;

import java.util.function.Consumer;

import static jump61.Side.*;
import static jump61.Square.square;

/** Represents the state of a Jump61 game.  Squares are indexed either by
 *  row and column (between 1 and size()), or by square number, numbering
 *  squares by rows, with squares in row 1 numbered from 0 to size()-1, in
 *  row 2 numbered from size() to 2*size() - 1, etc. (i.e., row-major order).
 *
 *  A Board may be given a notifier---a Consumer<Board> whose
 *  .accept method is called whenever the Board's contents are changed.
 *
 *  @author M.J. WU
 */


class Board {

    /** An uninitialized Board.  Only for use by subtypes. */
    protected Board() {
        _notifier = NOP;
    }

    /** An N x N board in initial configuration. */
    Board(int N) {
        _blue = 0;
        _red = 0;
        _notifier = NOP;
        _size = N;
        _Board = new Square[N][N];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                _Board[i][j] = square(WHITE, 1);
            }
        }
        _history = new ArrayList<>();
    }

    /** A board whose initial contents are copied from BOARD0, but whose
     *  undo history is clear, and whose notifier does nothing. */
    Board(Board board0) {
        this(board0.size());
        int k = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                _Board[i][j] = board0.get(k);
                k += 1;
            }
        }
        _history = new ArrayList<>();
        _readonlyBoard = new ConstantBoard(this);
    }

    /** Returns a readonly version of this board. */
    Board readonlyBoard() {
        return _readonlyBoard;
    }

    /** (Re)initialize me to a cleared board with N squares on a side. Clears
     *  the undo history and sets the number of moves to 0. */
    void clear(int N) {
        _size = N;
        _Board = new Square[N][N];

        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                _Board[i][j] = square(WHITE, 1);
            }
        }
        _notifier = NOP;
        _history = new ArrayList<>();
        announce();
    }

    /** Copy the contents of BOARD into me. */
    void copy(Board board) {
        internalCopy(board);
        _history = board._history;
        announce();
    }

    /** Copy the contents of BOARD into me, without modifying my undo
     *  history. Assumes BOARD and I have the same size. */
    private void internalCopy(Board board) {
        assert size() == board.size();
        for (int i = 1; i < board.size() + 1; i++) {
            for (int j = 1; j < board.size() + 1; j++) {
                Square otherBoard = board.get(i, j);
                _Board[i - 1][j - 1] = otherBoard;
            }
        }
        _notifier = board._notifier;

    }

    /** Return the number of rows and of columns of THIS. */
    int size() {
        return _size;
    }

    /** Returns the contents of the square at row R, column C
     *  1 <= R, C <= size (). */
    Square get(int r, int c) {
        return get(sqNum(r, c));
    }

    /** Returns the contents of square #N, numbering squares by rows, with
     *  squares in row 1 number 0 - size()-1, in row 2 numbered
     *  size() - 2*size() - 1, etc. */
    Square get(int n) {
        _red = 0;
        _blue = 0;
        while (exists(n)) {
            if (_Board[row(n) - 1][col(n) - 1].getSide() == RED) {
                _red++;
                return square(RED, _Board[row(n) - 1][col(n) - 1].getSpots());
            } else if (_Board[row(n) - 1][col(n) - 1].getSide() == BLUE) {
                _blue++;
                return square(BLUE, _Board[row(n) - 1][col(n) - 1].getSpots());
            } else {
                return square(WHITE, _Board[row(n) - 1][col(n) - 1].getSpots());
            }
        }
        return null;
    }

    /** Returns the total number of spots on the board. */
    int numPieces() {
        int spot = 0;
        for (int i = 1; i < size() + 1; i++) {
            for (int j = 1; j < size() + 1; j++) {
                spot += get(i, j).getSpots();
            }
        }
        return spot;
    }

    /** Returns the Side of the player who would be next to move.  If the
     *  game is won, this will return the loser (assuming legal position). */
    Side whoseMove() {
        return ((numPieces() + size()) & 1) == 0 ? RED : BLUE;
    }

    /** Return true iff row R and column C denotes a valid square. */
    final boolean exists(int r, int c) {
        return 1 <= r && r <= size() && 1 <= c && c <= size();
    }

    /** Return true iff S is a valid square number. */
    final boolean exists(int s) {
        int N = size();
        return 0 <= s && s < N * N;
    }

    /** Return the row number for square #N. */
    final int row(int n) {
        return n / size() + 1;
    }

    /** Return the column number for square #N. */
    final int col(int n) {
        return n % size() + 1;
    }

    /** Return the square number of row R, column C. */
    final int sqNum(int r, int c) {
        return (c - 1) + (r - 1) * size();
    }

    /** Return a string denoting move (ROW, COL)N. */
    String moveString(int row, int col) {
        return String.format("%d %d", row, col);
    }

    /** Return a string denoting move N. */
    String moveString(int n) {
        return String.format("%d %d", row(n), col(n));
    }

    /** Returns true iff it would currently be legal for PLAYER to add a spot
     to square at row R, column C. */
    boolean isLegal(Side player, int r, int c) {
        return isLegal(player, sqNum(r, c));
    }

    /** Returns true iff it would currently be legal for PLAYER to add a spot
     *  to square #N. */
    boolean isLegal(Side player, int n) {
        return player.equals(get(n).getSide()) || get(n).getSide() == WHITE;
    }

    /** Returns true iff PLAYER is allowed to move at this point. */
    boolean isLegal(Side player) {
        return whoseMove() == player;
    }

    /** Returns the winner of the current position, if the game is over,
     *  and otherwise null. */
    final Side getWinner() {
        if (numOfSide(BLUE) == size() * size()) {
            return BLUE;
        } else if (numOfSide(RED) == size() * size()) {
            return RED;
        }
        return null;
    }

    /** Return the number of squares of given SIDE. */
    int numOfSide(Side side) {
        _red = 0;
        _blue = 0;
        int white = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (_Board[i][j].getSide() == BLUE) {
                    _blue++;
                } else if (_Board[i][j].getSide() == RED) {
                    _red++;
                } else {
                    white++;
                }
            }
        }
        if (side == BLUE) {
            return _blue;
        } else if (side == RED) {
            return _red;
        }
        return white;
    }


    /** Add a spot from PLAYER at row R, column C.  Assumes
     *  isLegal(PLAYER, R, C). */
    void addSpot(Side player, int r, int c) {
        _history.add(new Board(this));
        _player = player;
        if (neighbors(r, c) == _Board[r - 1][c - 1].getSpots()) {
            if (getWinner() == null) {
                _Board[r - 1][c - 1] = square(player, 1);
                jumpHelper(r, c);
            } else {
                return;
            }
        } else {
            _Board[r - 1][c - 1] = square(player,
                    (_Board[r - 1][c - 1].getSpots() + 1));
        }
        announce();
    }

    /** Add a spot from PLAYER at square #N.  Assumes isLegal(PLAYER, N). */
    void addSpot(Side player, int n) {
        addSpot(player, row(n), col(n));
    }

    /** Set the square at row R, column C to NUM spots (0 <= NUM), and give
     *  it color PLAYER if NUM > 0 (otherwise, white). */
    void set(int r, int c, int num, Side player) {
        internalSet(r, c, num, player);
        announce();
    }

    /** Set the square at row R, column C to NUM spots (0 <= NUM), and give
     *  it color PLAYER if NUM > 0 (otherwise, white).  Does not announce
     *  changes. */
    private void internalSet(int r, int c, int num, Side player) {
        internalSet(sqNum(r, c), num, player);
    }

    /** Set the square #N to NUM spots (0 <= NUM), and give it color PLAYER
     *  if NUM > 0 (otherwise, white). Does not announce changes. */
    private void internalSet(int n, int num, Side player) {
        int row = row(n) - 1;
        int col = col(n) - 1;
        Square squares = square(player, num);
        _Board[row][col] = squares;
    }

    /** There are two obvious ways to conduct a game-tree search in the AI.
     //
     // First, you can explore the consequences of a possible move from
     // position A by making a copy of the Board in position A, and then
     // modifying that copy. Since you retain position A, you can return to
     // it to try other moves from that position.
     //
     // Second, you can explore the consequences of a possible move from
     // position A by making that move on your Board and then, when your
     // analysis of the move is complete, undoing the move to return you to
     // position A. This method is more complicated to implement, but has
     // the advantage that it can be considerably faster than making copies
     // of the Board (you will need one copy per move tried, which will very
     // quickly be thrown away).
     */

    /** Undo the effects of one move (that is, one addSpot command).  One
     *  can only undo back to the last point at which the undo history
     *  was cleared, or the construction of this Board. */
    void undo() {
        if (_history.size() > 0) {
            internalCopy(_history.remove(_history.size() - 1));
        }
    }

    /** Record the beginning of a move in the undo history. */
    private void markUndo() {
        _history.clear();
    }

    /** Add DELTASPOTS spots of side PLAYER to row R, column C,
     *  updating counts of numbers of squares of each color. */
    private void simpleAdd(Side player, int r, int c, int deltaSpots) {
        internalSet(r, c, deltaSpots + get(r, c).getSpots(), player);
    }

    /** Add DELTASPOTS spots of color PLAYER to square #N,
     *  updating counts of numbers of squares of each color. */
    private void simpleAdd(Side player, int n, int deltaSpots) {
        internalSet(n, deltaSpots + get(n).getSpots(), player);
    }

    /** Used in jump to keep track of squares needing processing.  Allocated
     *  here to cut down on allocations. */
    private final ArrayDeque<Integer> _workQueue = new ArrayDeque<>();

    /**
     * Jump Helper method 1.
     * @param R
     * @param C
     */
    private void jumpHelper(int R, int C) {
        if (_size >= R + 1) {
            if (R + 1 > 0) {
                jump(sqNum(R + 1, C));
            }
        }
        if (_size >= R - 1) {
            if (R - 1 > 0) {
                jump(sqNum(R - 1, C));
            }
        }
        if (_size >= C + 1) {
            if (C + 1 > 0) {
                jump(sqNum(R, C + 1));
            }
        }
        if (_size >= C - 1) {
            if (C - 1 > 0) {
                jump(sqNum(R, C - 1));
            }
        }

    }
    /** Do all jumping on this board, assuming that initially, S is the only
     *  square that might be over-full. */
    private void jump(int S) {
        int r = row(S);
        int c = col(S);
        if (neighbors(r, c) == _Board[r - 1][c - 1].getSpots()) {
            if (getWinner() == null) {
                _Board[r - 1][c - 1] = square(_player, 1);
                jumpHelper(r, c);
            } else {
                return;
            }
        } else {
            _Board[r - 1][c - 1] = square(_player,
                    (_Board[r - 1][c - 1].getSpots() + 1));
        }

    }


    /** Returns my dumped representation. */
    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("===%n");
        for (int i = 0; i < size(); i++) {
            out.format("   ");
            for (int j = 0; j < size(); j++) {
                out.format(" %1d%s", _Board[i][j].getSpots(),
                        outputHelper(_Board[i][j].getSide()));
            }
            out.format("%n");
        }
        out.format("===%n");
        return out.toString();
    }


    /**
     * This will take in the Side "BLUE", "WHITE", "RED".
     * and return "b", "-", "r" instead
     * @param C
     */
    private String outputHelper(Side C) {
        if (C == RED) {
            return "r";
        } else if (C == BLUE) {
            return "b";
        } else {
            return "-";
        }
    }

    /** Returns an external rendition of me, suitable for human-readable
     *  textual display, with row and column numbers.  This is distinct
     *  from the dumped representation (returned by toString). */
    public String toDisplayString() {
        String[] lines = toString().trim().split("\\R");
        Formatter out = new Formatter();
        for (int i = 1; i + 1 < lines.length; i += 1) {
            out.format("%2d %s%n", i, lines[i].trim());
        }
        out.format("  ");
        for (int i = 1; i <= size(); i += 1) {
            out.format("%3d", i);
        }
        return out.toString();
    }

    /** Returns the number of neighbors of the square at row R, column C. */
    int neighbors(int r, int c) {
        int size = size();
        int n;
        n = 0;
        if (r > 1) {
            n += 1;
        }
        if (c > 1) {
            n += 1;
        }
        if (r < size) {
            n += 1;
        }
        if (c < size) {
            n += 1;
        }
        return n;
    }

    /** Returns the number of neighbors of square #N. */
    int neighbors(int n) {
        return neighbors(row(n), col(n));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Board)) {
            return false;
        } else {
            Board B = (Board) obj;
            if (size() != B.size()) {
                return false;
            }
            for (int i = 0; i < size(); i++) {
                for (int j = 0; j < size(); j++) {
                if (!_Board[i][j].getSide().equals(B._Board[i][j].getSide())) {
                        return false;
                    }
                if (!_Board[i][j].equals(B._Board[i][j])) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    @Override
    public int hashCode() {
        return numPieces();
    }

    /** Set my notifier to NOTIFY. */
    public void setNotifier(Consumer<Board> notify) {
        _notifier = notify;
        announce();
    }

    /** Take any action that has been set for a change in my state. */
    private void announce() {
        _notifier.accept(this);
    }

    /** A notifier that does nothing. */
    private static final Consumer<Board> NOP = (s) -> { };

    /** A read-only version of this Board. */
    private ConstantBoard _readonlyBoard;

    /** Use _notifier.accept(B) to announce changes to this board. */
    private Consumer<Board> _notifier;
    /** Array list contains all the past history. */
    private ArrayList<Board> _history;
    /** the number of red squares on this Board. */
    private int _red;
    /** the number of blue squares on this Board. */
    private int _blue;
    /** the SIZE of the board. */
    private int _size;
    /** The array representation of this Board. */
    private Square[][] _Board;
    /** This player. */
    private Side _player;

}
