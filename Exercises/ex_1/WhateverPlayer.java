import java.util.Random;

/**
 * The WhateverPlayer class represents a player in a Tic-Tac-Toe game
 * who employs a random move strategy. This player attempts to place its mark
 * in a randomly selected empty cell on the board.
 * This strategy makes the WhateverPlayer unpredictable
 * but does not ensure optimal moves.
 */
public class WhateverPlayer implements Player{
    private final Random random = new Random();

    /**
     * Default constructor for WhateverPlayer.
     * Initializes a new instance with no specific configuration.
     */
    public WhateverPlayer() {}

    /**
     * Executes the WhateverPlayer's turn by placing a mark in a random empty cell.
     * Strategy:
     * - Continuously generates random row and column
     * indices until an empty cell is found.
     * - Places the player's mark in the first empty cell encountered.
     * - The turn ends immediately after placing the mark.
     * Assumptions:
     * - The board has at least one empty cell when this method is called.
     *
     * @param board The current Tic-Tac-Toe board where the move will be made.
     * @param mark  The player's mark (e.g., X or O) to be placed on the board.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        boolean isPlacedCell = false;
        while(!isPlacedCell) {
            int row = random.nextInt(board.getSize());
            int col = random.nextInt(board.getSize());
            if (board.getMark(row, col) == Mark.BLANK) {
                board.putMark(mark, row, col);
                isPlacedCell = true;
            }
        }
    }
}






