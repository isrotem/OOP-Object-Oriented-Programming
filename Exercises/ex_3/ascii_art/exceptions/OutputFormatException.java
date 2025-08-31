package ascii_art.exceptions;

/**
 * Exception thrown when the output method cannot be changed
 * due to an incorrect format.
 * The {@code OutputFormatException} extends {@link FormatException}
 * and is used to signal issues where the provided output
 * format is invalid or does not meet
 * the expected criteria.
 * This exception ensures that invalid output format changes
 * are handled gracefully.
 * @author Rotem Israeli, Nadav Benjamin
 * @see FormatException
 */
public class OutputFormatException extends FormatException {
	private static final String MESSAGE =
			"Did not change output method due to incorrect format.";

	/**
	 * Constructs a new {@code OutputFormatException}
	 * with a predefined error message.
	 */
	public OutputFormatException() {
		super(MESSAGE);
	}
}
