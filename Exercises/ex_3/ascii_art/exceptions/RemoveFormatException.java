package ascii_art.exceptions;

/**
 * Exception thrown when an attempt to remove a character fails due to
 * incorrect format.
 * The {@code RemoveFormatException} extends {@link FormatException}
 * and provides a specific error message indicating the reason
 * for the failure.
 * This exception is used to ensure that invalid removal operations are handled gracefully.
 * @author Rotem Israeli, Nadav Benjamin
 * @see FormatException
 */
public class RemoveFormatException extends FormatException {
	private static final String MESSAGE =
			"Did not remove due to incorrect format.";

	/**
	 * Constructs a new {@code RemoveFormatException} with a
	 * predefined error message.
	 */
	public RemoveFormatException() {
		super(MESSAGE);
	}
}
