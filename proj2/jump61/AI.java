/** This file contains definitions for an OPTIONAL part of your project.  If you
// choose not to do the optional point, you can delete this file from your
// project.

// This file contains a SUGGESTION for the structure of your program.  You
// may change any of it, or add additional files to this directory (package),
// as long as you conform to the project specification.

// Comments that start with "//" are intended to be removed from your
// solutions.
 * */

package jump61;
import java.util.Random;

import static jump61.Side.*;

/** An automated Player.
 *  @author P. N. Hilfinger
 */
class AI extends Player {

    /** A new player of GAME initially COLOR that chooses moves automatically.
     *  SEED provides a random-number seed used for choosing moves.
     */
    AI(Game game, Side color, long seed) {
        super(game, color);
        _random = new Random(seed);
    }

    @Override
    String getMove() {
        Board board = getGame().getBoard();

        assert getSide() == board.whoseMove();
        int choice = searchForMove();
        getGame().reportMove(board.row(choice), board.col(choice));
        return String.format("%d %d", board.row(choice), board.col(choice));
    }

    /** Return a move after searching the game tree to DEPTH>0 moves
     *  from the current position. Assumes the game is not over. */
    private int searchForMove() {
        Board work = new Board(getBoard());
        int value;
        assert getSide() == work.whoseMove();
        _foundMove = -1;
        if (getSide() == RED) {
            value = minMax(work, 3, true, 1,
                    -work.size() * work.size() - 1,
                    work.size() * work.size() + 1);
        } else {
            value = minMax(work, 3, true, -1,
                    -work.size() * work.size() - 1,
                    work.size() * work.size() + 1);
        }
        if (_foundMove == -1) {
            for (int i = 0; i < work.size() * work.size(); i++) {
                if (work.isLegal(getSide(), i)) {
                    _foundMove = i;
                }
            }
        }
        return _foundMove;
    }


    /** Find a move from position BOARD and return its value, recording
     *  the move found in _foundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _foundMove. If the game is over
     *  on BOARD, does not set _foundMove. */
    private int minMax(Board board,
                       int depth, boolean saveMove,
                       int sense, int alpha, int beta) {
        int gg;
        int goal1 = -board.size() * board.size() - 1;
        int goal2 = board.size() * board.size() + 1;
        int l = board.size() * board.size();
        if (depth == 0) {
            gg = board.numOfSide(RED) - board.numOfSide(BLUE);
            return staticEval(board, gg);
        } else {
            if (sense == 1) {
                for (int i = 0; i < l; i++) {
                    if (board.isLegal(RED, i)) {
                        board.addSpot(RED, i);
                        int max = minMax(board, depth - 1,
                                false, -1, alpha, beta);
                        board.undo();
                        if (max > goal1) {
                            goal1 = max;
                            alpha = Math.max(max, alpha);
                            if (alpha >= beta) {
                                if (saveMove) {
                                    _foundMove = i;
                                }
                                return goal1;
                            }
                            if (saveMove) {
                                _foundMove = i;
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < l; i++) {
                    if (board.isLegal(BLUE, i)) {
                        board.addSpot(BLUE, i);
                        int min = minMax(board, depth - 1,
                                false, 1, alpha, beta);
                        board.undo();
                        if (min < goal2) {
                            goal2 = min;
                            beta = Math.min(min, beta);
                            if (alpha >= beta) {
                                if (saveMove) {
                                    _foundMove = i;
                                }
                                return goal2;
                            }
                            if (saveMove) {
                                _foundMove = i;
                            }
                        }
                    }
                }
            }
        }
        if (sense == 1) {
            return goal1;
        }
        return goal2;
    }




    /** Return a heuristic estimate of the value of board position B.
     *  Use WINNINGVALUE to indicate a win for Red and -WINNINGVALUE to
     *  indicate a win for Blue. */
    private int staticEval(Board b, int winningValue) {
        return winningValue;
    }

    /** A random-number generator used for move selection. */
    private Random _random;

    /** Used to convey moves discovered by minMax. */
    private int _foundMove;
}
