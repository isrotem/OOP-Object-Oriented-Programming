/**
 * The GeniusPlayer class represents a player in a Tic-Tac-Toe game
 * who employs a straightforward but efficient strategy for placing its mark.
 * The GeniusPlayer scans the board sequentially from the top-left corner
 * (row 0, column 0) to the bottom-right corner. It places its mark in the
 * first available (empty) cell and ends its turn immediately.

 * */
 public class GeniusPlayer implements Player {

    /**
     * Default constructor for the GeniusPlayer.
     * Initializes a new instance with no specific configuration.
     */
    public GeniusPlayer() {
    }

    /**
     * Executes the GeniusPlayer's move on the given Tic-Tac-Toe board.
     * Strategy:
     * - Scans the board sequentially from the top-left (row 0, column 0)
     * to the bottom-right.
     * - Places the player's mark in the first available (empty) cell it encounters.
     * - Ends the turn immediately after placing the mark.
     * Assumptions:
     * - The board has at least one empty cell when this method is called.
     *
     * @param board The current state of the Tic-Tac-Toe board.
     * @param mark  The player's mark (e.g., X or O) to be placed on the board.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int boardSize = board.getSize();
        boolean markPlaced = false;
        for (int row = 0; row < boardSize && !markPlaced; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board.putMark(mark, row, col)) {
                    markPlaced = true;
                    break;
                }
            }
        }
    }
}
