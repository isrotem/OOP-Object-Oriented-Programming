package image_char_matching.brightness_rounding_strategies;

import ascii_art.exceptions.RoundingFormatException;

/**
 * Factory class for creating instances of {@link RoundingStrategy}.
 * The {@code RoundingStrategyFactory} provides a static method to obtain
 * a rounding strategy implementation based on a given strategy name.
 * This class ensures centralized control over the creation of different
 * rounding strategy objects.
 * @author Rotem Israeli, Nadav Benjamin
 * @see RoundingStrategy
 * @see RoundAbs
 * @see RoundUp
 * @see RoundDown
 */
public class RoundingStrategyFactory {
	private static final String ABS = "abs";
	private static final String UP = "up";
	private static final String DOWN = "down";
	
	private RoundingStrategyFactory() {
	}


	/**
	 * Returns a {@link RoundingStrategy} instance
	 * based on the given strategy name.
	 * @param strategy The name of the rounding strategy
	 * ("abs", "up", or "down").
	 * @return A {@link RoundingStrategy} instance for
	 * the specified strategy.
	 * @throws RoundingFormatException If the strategy name is invalid.
	 */
	public static RoundingStrategy getBrightnessRoundingStrategy(String strategy)
			throws RoundingFormatException {
		return switch (strategy) {
			case ABS -> new RoundAbs();
			case UP -> new RoundUp();
			case DOWN -> new RoundDown();
			default -> throw new RoundingFormatException();
		};
	}
}
