package image_char_matching.brightness_rounding_strategies;

import java.util.TreeMap;

/**
 * Implements a rounding strategy for matching image
 * brightness to the nearest lower character.
 * The {@code RoundDown} class selects the character
 * corresponding to the highest brightness
 * value that is less than or equal to the given brightness.
 * @author Rotem Israeli, Nadav Benjamin
 * @see RoundingStrategy
 */
public class RoundDown implements RoundingStrategy {

	/**
	 * Finds the character corresponding to the largest brightness value
	 * that is less than or equal to the given brightness.
	 * This method uses the {@code floorEntry} of the
	 * provided {@link TreeMap} to efficiently find the closest
	 * matching character for the given brightness.
	 * @param brightness The brightness value of the image pixel.
	 * @param charBrightnesses A {@link TreeMap}
	 * mapping brightness values to characters.
	 * @return The character corresponding to the nearest
	 * lower or equal brightness.
	 */
	@Override
	public char getCharByImageBrightness(double brightness, TreeMap<Double, Character> charBrightnesses) {
		return charBrightnesses.floorEntry(brightness).getValue();
	}
}
