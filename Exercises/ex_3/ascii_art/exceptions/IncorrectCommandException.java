package ascii_art.exceptions;

/**
 * Exception thrown when a command is invalid or incorrectly formatted.
 * The {@code IncorrectCommandException} extends {@link CustomException}
 * and provides a specific error message indicating the reason for failure.
 * This exception is used to handle cases where user input commands
 * do not match the expected format or are unrecognized.
 * @author Rotem Israeli, Nadav Benjamin
 * @see CustomException
 */
public class IncorrectCommandException extends CustomException {
  private static final String MESSAGE =
          "Did not execute due to incorrect command.";

    /**
     * Constructs a new {@code IncorrectCommandException}
     * with a predefined error message.
     */
  public IncorrectCommandException() {
		super(MESSAGE);
	}
}
