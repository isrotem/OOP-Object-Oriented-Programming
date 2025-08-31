package ascii_art.exceptions;

/**
 * Exception thrown when an attempt to change resolution fails
 * due to an incorrect format.
 * The {@code ResolutionFormatException} extends {@link FormatException}
 * and provides a specific error message indicating the reason for the failure.
 * This exception is used to ensure that invalid resolution formats
 * are handled gracefully.
 * @author Rotem Israeli, Nadav Benjamin
 * @see FormatException
 */
public class ResolutionFormatException extends FormatException {
	private static final String MESSAGE =
			"Did not change resolution due to incorrect format.";

	/**
	 * Constructs a new {@code ResolutionFormatException} with a
	 * predefined error message.
	 */
	public ResolutionFormatException() {
		super(MESSAGE);
	}
}
