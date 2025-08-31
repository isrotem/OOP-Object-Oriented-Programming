package image_char_matching.brightness_rounding_strategies;

import java.util.TreeMap;

/**
 * Defines a strategy for rounding image brightness values to characters.
 * The {@code RoundingStrategy} interface provides
 * a method for implementing
 * different strategies to map a given
 * brightness value to a corresponding character
 * based on a predefined mapping of brightness values.
 * This interface allows flexibility in defining various
 * rounding behaviors
 * for brightness-to-character mapping.
 * @author Rotem Israeli, Nadav Benjamin
 */
public interface RoundingStrategy {

	/**
	 * Determines the character that corresponds to a
	 * given brightness value.
	 * The method is implemented to match the
	 * brightness value to the closest
	 * character based on the strategy defined by the implementing class.
	 * @param brightness The brightness value of the image pixel.
	 * @param charBrightnesses A {@link TreeMap}
	 * mapping brightness values to characters.
	 * @return The character that matches the given brightness
	 * value according to the strategy.
	 */
	char getCharByImageBrightness(double brightness, TreeMap<Double, Character> charBrightnesses);
}
