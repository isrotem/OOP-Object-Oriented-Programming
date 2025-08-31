/**
 * The Game class represents a Tic-Tac-Toe game.
 * It orchestrates the gameplay between two players on a customizable board.
 * The game alternates turns between Player X and Player O until a winner is
 * determined or the board is full, resulting in a draw.
 * Features:
 * - Configurable board size and win streak condition.
 * - Supports rendering the board after each move.
 * - Determines the winner by checking rows, columns, and diagonals for a streak of marks.
 */
public class Game {

    private final Renderer renderer;
    private final Player playerX;
    private final Player playerO;
    private final int size;
    private final Board board;
    private final int winStreak;
    private static final int BOARD_SIZE = 4;
    private static final int WIN_STREAK = 3;

    /**
     * Constructor for a game with default board size and win streak conditions.
     *
     * @param playerX  The player controlling mark X.
     * @param playerO  The player controlling mark O.
     * @param renderer The renderer for displaying the board state.
     */
    public Game(Player playerX, Player playerO, Renderer renderer) {
        this(playerX, playerO, BOARD_SIZE, WIN_STREAK, renderer);
    }

    /**
     * Constructor for a customizable game with a specified board size and win streak.
     *
     * @param playerX   The player controlling mark X.
     * @param playerO   The player controlling mark O.
     * @param size      The size of the board (e.g., size 4 creates a 4x4 board).
     * @param winStreak The number of consecutive marks required to win.
     * @param renderer  The renderer for displaying the board state.
     */
    public Game(Player playerX,Player playerO, int size, int winStreak,Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
        this.size = size;
        board = new Board(size);
        this.winStreak = winStreak;
    }

    /**
     * Returns the win streak required to win the game.
     *
     * @return The win streak length.
     */
    public int getWinStreak() {
        return this.winStreak;
    }

    /**
     * Returns the size of the board.
     *
     * @return The board size.
     */
    public int getBoardSize() {
        return this.size;
    }

    /**
     * Runs the main game loop, alternating between players until the game ends.
     *
     * @return The mark of the winning player (Mark.X or Mark.O),
     * or Mark.BLANK if the game is a draw.
     */
    public Mark run() {
        Player[] players = {playerX, playerO};
        Mark[] marksInBoard = {Mark.X, Mark.O};
        int markedCells = 0;
        int currentPlayerIndex = 0;

        while (!isFullCapacity(markedCells)) {
            Player currentPlayer =  players[currentPlayerIndex];
            Mark currMark = marksInBoard[currentPlayerIndex];
            currentPlayer.playTurn(board, currMark);
            renderer.renderBoard(board);
            if (checkWinnerStreak(currMark)) {
                return currMark;
            }
            currentPlayerIndex = 1 - currentPlayerIndex;
            markedCells++;
        }

        return Mark.BLANK;
    }

    /**
     * Checks if a player has achieved a winning streak on the board.
     *
     * @param mark The mark (X or O) to check for a win.
     * @return true if the mark has a winning streak; false otherwise.
     */
    private boolean checkWinnerStreak(Mark mark) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                    if (checkDirection(row, col, 0, 1, mark) || // Row
                            checkDirection(row, col, 1, 0, mark) || // Column
                            checkDirection(row, col, 1, 1, mark) || // Diagonal
                            checkDirection(row, col, 1, -1, mark)) { // Opposite Diagonal
                        return true;
                    }
            }
        }
        return false;
    }

    /**
     * Validates whether a cell is within the board's boundaries.
     *
     * @param row The row index.
     * @param col The column index.
     * @return true if the cell is out of bounds; false otherwise.
     */
    private boolean checkValidBounds(int row, int col) {
        return (row >= this.size || col >= this.size || col < 0 || row < 0);
    }

    /**
     * Checks if there is a winning streak in a specific direction from a given cell.
     *
     * @param row      The starting row index.
     * @param col      The starting column index.
     * @param rowDelta The row increment per step in the streak.
     * @param colDelta The column increment per step in the streak.
     * @param mark     The mark to check for a streak.
     * @return true if the streak matches the win condition; false otherwise.
     */
    private boolean checkDirection(int row, int col, int rowDelta, int colDelta, Mark mark) {
        int streak = 0;
        for (int i = 0; i < winStreak; i++) {

            int newRow = row + i * rowDelta;
            int newCol = col + i * colDelta;
            if (checkValidBounds(newRow, newCol) || board.getMark(newRow, newCol) != mark) {
                return false;
            }

            streak++;
        }
        return (streak == winStreak);
    }

    /**
     * Checks if the board is completely full.
     *
     * @param markedCells The number of cells currently marked.
     * @return true if the board is full; false otherwise.
     */
    private boolean isFullCapacity(int markedCells) {
        int boardSize = board.getSize();
        int cellsInBoard = boardSize * boardSize;
        return(markedCells == cellsInBoard);
    }
}
