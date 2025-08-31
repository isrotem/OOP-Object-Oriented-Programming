/**
 * The Renderer interface defines the contract for rendering a Tic-Tac-Toe board.
 * Classes that implement this interface are responsible for displaying the current
 * state of the game board in various ways, such as in the console or through other outputs.
 */
 public interface Renderer {

    /**
     * Renders the current state of the Tic-Tac-Toe board.
     *
     * @param board The Tic-Tac-Toe board to be rendered.
     *              This includes the current placement of marks (X, O, or BLANK)
     *              in all cells.
     */
    void renderBoard(Board board);
}
