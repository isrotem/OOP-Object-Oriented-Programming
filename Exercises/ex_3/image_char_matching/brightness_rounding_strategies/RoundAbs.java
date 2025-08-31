package image_char_matching.brightness_rounding_strategies;

import java.util.TreeMap;

/**
 * Implements a rounding strategy for matching image
 * brightness to the closest character.
 * The {@code RoundAbs} class determines the character
 * that best represents
 * the brightness value of an image pixel by
 * comparing the absolute difference
 * between the pixel's brightness and the available brightness keys.
 * @author Rotem Israeli, Nadav Benjamin
 * @see RoundingStrategy
 */
public class RoundAbs implements RoundingStrategy {

	/**
	 * Finds the character that most closely matches the given brightness.
	 * The method compares the absolute difference between
	 * the provided brightness
	 * and the brightness keys in the given {@link TreeMap}.
	 * If two keys are equally
	 * distant, the lower key is preferred.
	 * @param brightness The brightness value of the image pixel.
	 * @param charBrightnesses A {@link TreeMap} mapping
	 * brightness values to characters.
	 * @return The character corresponding to the brightness
	 * closest to the given value.
	 */
	@Override
	public char getCharByImageBrightness(double brightness, TreeMap<Double, Character> charBrightnesses) {
		Double floor = charBrightnesses.floorKey(brightness);
		Double ceiling = charBrightnesses.ceilingKey(brightness);
		if (Math.abs(brightness - floor) <= Math.abs(brightness - ceiling)) {
			return charBrightnesses.get(floor);
		}
		return charBrightnesses.get(ceiling);
	}
}
