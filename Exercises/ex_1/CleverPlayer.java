import java.util.Random;

/**
 * Represents a "clever" player in a Tic-Tac-Toe game.
 * Wins against a `WhateverPlayer` in at least 55% of games.
 * Loses to a `GeniusPlayer` in at least 55% of games.
 * This player chooses between two strategies
 * (WhateverPlayer or GeniusPlayer)
 * randomly for each turn.
 */
public class CleverPlayer implements Player {
    private static final int RANDOM = 2;
    private final Random random = new Random();

    /**
     * Default constructor for CleverPlayer.
     * Initializes a new instance with no specific configuration.
     */
    public CleverPlayer() {}

    /**
     * Makes a move on the Tic-Tac-Toe board using one of the two strategies:
     * WhateverPlayer or GeniusPlayer. The strategy is chosen randomly for each turn.
     * @param board The current Tic-Tac-Toe board where the move will be made.
     * @param mark  The player's mark (e.g., X or O) to be placed on the board.
     * The strategy selection process is as follows:
     * - If the random number is 0, the WhateverPlayer's strategy is used.
     * - Otherwise, the GeniusPlayer's strategy is applied.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        WhateverPlayer whatever = new WhateverPlayer();
        GeniusPlayer genius = new GeniusPlayer();
        int randomResult = random.nextInt(RANDOM);
        if (randomResult == 0) {
            whatever.playTurn(board, mark);
        } else {
            genius.playTurn(board, mark);
        }
    }
}


