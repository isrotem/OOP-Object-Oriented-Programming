package ascii_art.exceptions;

/**
 * Exception thrown when an operation cannot be executed due to
 * the charset being too small.
 * The {@code SmallCharsetException} extends {@link CustomException}
 * and provides a specific error message indicating the reason
 * for the failure.
 * This exception is used to ensure that operations requiring
 * a minimum charset size
 * fail gracefully when the size requirement is not met.
 * @author Rotem Israeli, Nadav Benjamin
 * @see CustomException
 */
public class SmallCharsetException extends CustomException {
	private static final String MESSAGE =
			"Did not execute. Charset is too small.";

	/**
	 * Constructs a new {@code SmallCharsetException} with a
	 * predefined error message.
	 */
	public SmallCharsetException() {
		super(MESSAGE);
	}
}
