/** This file contains definitions for an OPTIONAL part of your project.  If you
choose not to do the optional point, you can delete this file from your
project.

This file contains a SUGGESTION for the structure of your program.  You
 may change any of it, or add additional files to this directory (package),
 as long as you conform to the project specification.
 *  @author M.J. WU
*/
package jump61;

import ucb.gui2.TopLevel;
import ucb.gui2.LayoutSpec;

import java.util.concurrent.ArrayBlockingQueue;

import static jump61.Side.*;

/** The GUI controller for jump61.  To require minimal change to textual
 *  interface, we adopt the strategy of converting GUI input (mouse clicks)
 *  into textual commands that are sent to the Game object through a
 *  a Writer.  The Game object need never know where its input is coming from.
 *  A Display is an Observer of Games and Boards so that it is notified when
 *  either changes.
 *  @author M.J. Wu
 */
class Display extends TopLevel implements View, CommandSource, Reporter {

    /** A new window with given TITLE displaying GAME, and using COMMANDWRITER
     *  to send commands to the current game. */
    Display(String title) {
        super(title, true);

        addMenuButton("Game->Quit", this::quit);
        addMenuButton("Game->New Game", this::newGame);
        addMenuButton("Size->3 x 3", this::size3);
        addMenuButton("Size->6 x 6", this::size6);
        addMenuButton("Size->9 x 9", this::size9);
        addMenuButton("manual red->Auto red", this::autoR);
        addMenuButton("Auto red-> manual blue", this::manualR);
        addMenuButton("manual blue->Auto blue", this::autoB);
        addMenuButton("Auto blue-> manual blue", this::manualB);
        _boardWidget = new BoardWidget(_commandQueue);
        add(_boardWidget, new LayoutSpec("y", 1, "width", 2));
        display(true);
    }

    /** Response to "Quit" button click. */
    void quit(String dummy) {
        System.exit(0);
    }

    /** Response to "New Game" button click. */
    void newGame(String dummy) {
        _commandQueue.offer("new");
    }

    /** Response to "size change 3 x 3" button click. */
    void size3(String dummy) {
        _commandQueue.offer("size 3");
    }
    /** Response to "size change 6 x 6" button click. */
    void size6(String dummy) {
        _commandQueue.offer("size 6");
    }
    /** Response to "size change 9 x 9" button click. */
    void size9(String dummy) {
        _commandQueue.offer("size 9");
    }
    /** Response to "Switch to Auto RED" button click. */
    void autoR(String dummy) {
        _commandQueue.offer("Switch to Auto RED");
    }
    /** Response to "Switch to Manual RED" button click. */
    void manualR(String dummy) {
        _commandQueue.offer("Switch to Manual RED");
    }
    /** Response to "Switch to Auto BLUE" button click. */
    void autoB(String dummy) {
        _commandQueue.offer("Switch to Auto BLUE");
    }
    /** Response to "Switch to Manual BLUE" button click. */
    void manualB(String dummy) {
        _commandQueue.offer("Switch to Manual BLUE");
    }


    @Override
    public void update(Board board) {
        _boardWidget.update(board);
        pack();
        _boardWidget.repaint();
    }

    @Override
    public String getCommand(String ignored) {
        try {
            return _commandQueue.take();
        } catch (InterruptedException excp) {
            throw new Error("unexpected interrupt");
        }
    }

    @Override
    public void announceWin(Side side) {
        showMessage(String.format("%s wins!", side.toCapitalizedString()),
                    "Game Over", "information");
    }

    @Override
    public void announceMove(int row, int col) {
    }

    @Override
    public void msg(String format, Object... args) {
        showMessage(String.format(format, args), "", "information");
    }

    @Override
    public void err(String format, Object... args) {
        showMessage(String.format(format, args), "Error", "error");
    }

    /** Time interval in msec to wait after a board update. */
    static final long BOARD_UPDATE_INTERVAL = 50;

    /** The widget that displays the actual playing board. */
    private BoardWidget _boardWidget;
    /** Queue for commands going to the controlling Game. */
    private final ArrayBlockingQueue<String> _commandQueue =
        new ArrayBlockingQueue<>(5);
}
