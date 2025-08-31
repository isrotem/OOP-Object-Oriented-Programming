package image_char_matching.brightness_rounding_strategies;

import java.util.TreeMap;

/**
 * A rounding strategy that matches image brightness
 * to the nearest higher character.
 * This strategy selects the smallest brightness value
 * in the map that is greater than
 * or equal to the given brightness.
 * @author Rotem Israeli, Nadav Benjamin
 */
public class RoundUp implements RoundingStrategy {

	/**
	 * Finds the character corresponding to the
	 * nearest higher or equal brightness value.
	 * @param brightness The brightness value of the image pixel.
	 * @param charBrightnesses A {@link TreeMap} mapping
	 * brightness values to characters.
	 * @return The character for the closest higher or equal brightness.
	 */
	@Override
	public char getCharByImageBrightness(double brightness, TreeMap<Double, Character> charBrightnesses) {
		return charBrightnesses.ceilingEntry(brightness).getValue();
	}
}
