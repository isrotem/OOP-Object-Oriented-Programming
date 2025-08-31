/**
 * Represents a human player in a Tic-Tac-Toe game.
 * The human player interacts with the game by
 * providing input for their moves.
 */
public class HumanPlayer implements Player {

    private static final String INPUT_MSG = "Player %s, type coordinates: ";

    private static final String INVALID_POSITION_MSG =
            "Invalid mark position. Please choose a valid position: ";

    private static final String NOT_EMPTY_MSG =
            "Mark position is already occupied. Please choose a valid position: ";

    private static final int CONVERT_NUM = 10;

    /**
     * Default constructor for the HumanPlayer class.
     */
    public HumanPlayer() {}

    /**
     * Allows the human player to take their turn by
     * selecting a position on the board.
     * Validates the input to ensure it is within
     * bounds and not already occupied.
     *
     * @param board the game board where the player will place their mark.
     * @param mark  the mark (X or O) associated with the player.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        System.out.printf(INPUT_MSG, mark);
        boolean isValidInput = false;
        while (!isValidInput) {
            int input = KeyboardInput.readInt();
            int rowValue = input / CONVERT_NUM;
            int colValue = input % CONVERT_NUM;
            if (isOutOfBounds(board, rowValue, colValue)) {
                System.out.println(INVALID_POSITION_MSG);
            }
            else if (board.getMark(rowValue, colValue) != Mark.BLANK) {
                System.out.println(NOT_EMPTY_MSG);
            }
            else {
                board.putMark(mark, rowValue, colValue);
                isValidInput = true;
            }
        }
    }

    /**
     * Checks if the specified row and column
     * indices are out of the board's bounds.
     *
     * @param board the game board.
     * @param row   the row index to check.
     * @param col   the column index to check.
     * @return true if the position is out of bounds, false otherwise.
     */
    private boolean isOutOfBounds(Board board, int row, int col) {
        return row < 0 || row >= board.getSize() || col < 0 || col >= board.getSize();
    }
}

