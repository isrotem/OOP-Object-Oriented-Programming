package ascii_art.exceptions;

/**
 * Exception thrown when an attempt to add a character fails
 * due to incorrect format.
 * The {@code AddFormatException} extends {@link FormatException}
 * and provides a specific error message indicating
 * the cause of the failure.
 * @author Rotem Israeli, Nadav Benjamin
 * @see FormatException
 */
public class AddFormatException extends FormatException {
	private static final String MESSAGE =
			"Did not add due to incorrect format.";

	/**
	 * Constructs a new {@code AddFormatException} with a predefined
	 * error message.
	 */
	public AddFormatException() {
		super(MESSAGE);
	}
}
