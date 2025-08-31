package ascii_art.exceptions;

/**
 * A generic custom exception for the ASCII art application.
 * The {@code CustomException} extends {@link Exception} and allows
 * for providing specific error messages.
 * This exception can be used as a base for more specific exceptions
 * within the application.
 * @author Rotem Israeli, Nadav Benjamin
 */
public class CustomException extends Exception {

	/**
	 * Constructs a new {@code CustomException} with the
	 * specified error message.
	 * @param message The detail message explaining the reason
	 *                for the exception.
	 */
	public CustomException(String message) {
		super(message);
	}
}
