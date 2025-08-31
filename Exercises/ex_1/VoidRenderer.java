/**
 * The VoidRenderer class implements the Renderer interface but does not render
 * the Tic-Tac-Toe board. It is used for cases where rendering the board is
 * unnecessary, such as during automated testing or silent simulations.
 */
public class VoidRenderer implements Renderer {

    @Override
    public void renderBoard(Board board) {}
}
