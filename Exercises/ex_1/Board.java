/**
 * Represents a Tic-Tac-Toe board with a customizable size.
 * Provides methods to initialize the board,
 * place marks, and retrieve marks.
 */
public class Board {

    private final int size;
    private Mark[][] actualBoard;
    private static final int BOARD_SIZE = 4;

    /**
     * Creates a board with the default size.
     * Initializes all cells to BLANK.
     */
    public Board() {
        this.size = BOARD_SIZE;
        boardInitialize();
    }

    /**
     * Creates a board with the specified size.
     *
     * @param size the size of the board (number of rows and columns).
     *             Must be greater than zero.
     */
    public Board(int size) {
        this.size = size;
        boardInitialize();
    }

    /**
     * Initializes the board by setting all cells to BLANK.
     */
    private void boardInitialize() {
        this.actualBoard = new Mark[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.actualBoard[i][j] = Mark.BLANK;
            }
        }
    }

    /**
     * Gets the size of the board.
     *
     * @return the size of the board (number of rows or columns).
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Places a mark on the board at the specified position.
     *
     * @param mark the mark to place (X or O).
     * @param row  the row index (0-based).
     * @param col  the column index (0-based).
     * @return true if the mark was placed successfully;
     *         false if the position
     *         is out of bounds or already occupied.
     */
    public boolean putMark(Mark mark, int row, int col) {
        if (row >= 0 && row < this.size && col >= 0 && col < this.size) {
            if (this.actualBoard[row][col].equals(Mark.BLANK)) {
                this.actualBoard[row][col] = mark;
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the mark at the specified position on the board.
     *
     * @param row the row index (0-based).
     * @param col the column index (0-based).
     * @return the mark at the specified position,
     * or BLANK if the position is out of bounds.
     */
    public Mark getMark(int row, int col) {
        if (row < 0 || row >= this.size || col < 0 || col >= this.size) {
            return Mark.BLANK;
        }
        return this.actualBoard[row][col];
    }
}

