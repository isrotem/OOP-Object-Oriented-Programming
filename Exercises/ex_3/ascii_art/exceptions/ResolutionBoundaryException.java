package ascii_art.exceptions;

/**
 * Exception thrown when an attempt to change resolution fails
 * due to exceeding allowed boundaries.
 * The {@code ResolutionBoundaryException} extends {@link CustomException}
 * and provides a specific error message indicating the reason for the failure.
 * This exception is used to ensure that invalid resolution
 * changes are handled gracefully.
 * @author Rotem Israeli, Nadav Benjamin
 * @see CustomException
 */
public class ResolutionBoundaryException extends CustomException {
	private static final String MESSAGE =
			"Did not change resolution due to exceeding boundaries.";

	/**
	 * Constructs a new {@code ResolutionBoundaryException}
	 * with a predefined error message.
	 */
	public ResolutionBoundaryException() {
		super(MESSAGE);
	}
}
