/**
 * Represents the possible marks in a Tic-Tac-Toe game.
 * A cell on the board can be marked as BLANK, X, or O.
 */
public enum Mark {

    BLANK,X, O;

    /**
     * Converts the mark to its string representation.
     *
     * @return the string representation of the mark
     * ("X" for X, "O" for O, null for BLANK).
     */
    @Override
    public String toString() {
        String convertedMar = null;
        if (this == Mark.X) {
            convertedMar = "X";
        }
        if (this == Mark.O) {
            convertedMar = "O";
        }
        return convertedMar;
    }
}
