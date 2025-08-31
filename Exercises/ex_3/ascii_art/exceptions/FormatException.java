package ascii_art.exceptions;

/**
 * Exception thrown when a format-related error occurs
 * in the ASCII art application.
 * The {@code FormatException} extends {@link CustomException} and serves
 * as a base class for more specific format-related exceptions.
 * This exception is used to signal issues such as invalid input formats.
 * @author Rotem Israeli, Nadav Benjamin
 * @see CustomException
 */
public class FormatException extends CustomException {

	/**
	 * Constructs a new {@code FormatException} with the
	 * specified error message.
	 * @param message The detail message explaining the reason
	 *                for the exception.
	 */
	public FormatException(String message) {
		super(message);
	}
}
