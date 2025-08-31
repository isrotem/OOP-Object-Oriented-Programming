package ascii_art.exceptions;

/**
 * Exception thrown when an attempt to change the rounding method fails
 * due to an incorrect format.
 * The {@code RoundingFormatException} extends {@link FormatException}
 * and provides a specific error message indicating the reason for the failure.
 * This exception is used to ensure that invalid rounding method
 * formats are handled gracefully.
 * @author Rotem Israeli, Nadav Benjamin
 * @see FormatException
 */
public class RoundingFormatException extends FormatException {
	private static final String MESSAGE =
			"Did not change rounding method due to incorrect format";

	/**
	 * Constructs a new {@code RoundingFormatException} with a predefined
	 * error message.
	 */
	public RoundingFormatException() {
		super(MESSAGE);
	}
}
