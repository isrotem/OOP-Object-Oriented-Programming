/**
 * Represents a player in a Tic-Tac-Toe game.
 * Each player is required to implement
 * this interface to define their behavior
 * when taking a turn during the game.
 */
public interface Player {

    /**
     * Executes the player's turn by placing their mark on the board.
     *
     * @param board the game board where the move will be made.
     *              The board object is used to interact with the game's state.
     * @param mark  the mark (X or O) associated with the player making the move.
     *              This indicates which player's turn it is.
     */
    void playTurn(Board board, Mark mark);

}
