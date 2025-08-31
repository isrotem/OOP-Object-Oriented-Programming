package image_char_matching;

import ascii_art.exceptions.RoundingFormatException;
import image_char_matching.brightness_rounding_strategies.RoundingStrategy;
import image_char_matching.brightness_rounding_strategies.RoundingStrategyFactory;
import image_char_matching.brightness_rounding_strategies.RoundAbs;

import java.util.*;

/**
 * Handles the matching of characters to image brightness
 * values for ASCII art generation.
 * The {@code SubImgCharMatcher} class maintains a set
 * of characters and maps their brightness
 * values for use in generating ASCII art.
 * It supports adding/removing characters, normalizing
 * brightness values, and applying different rounding strategies.
 * @author Rotem Israeli, Nadav Benjamin
 */
public class SubImgCharMatcher {
	private static final double INITIAL_MIN_BRIGHTNESS = 1.0;
	private static final double INITIAL_MAX_BRIGHTNESS = 0.0;
	private static final int NUM_OF_PIXELS_IN_ARRAY = 256;
	private static final HashMap<Character, Double> CALCULATED_KEY_BRIGHTNESSES = new HashMap<>();
	private final HashSet<Character> chars;
	private TreeMap<Double, Character> normalizedChars;
	private boolean isUpToDate = false;
	private RoundingStrategy roundingStrategy = new RoundAbs();

	/**
	 * Constructs a {@code SubImgCharMatcher} with the
	 * specified character set.
	 * @param charset An array of characters to initialize
	 * the matcher with.
	 */
	public SubImgCharMatcher(char[] charset) {
		this.chars = new HashSet<>();
		for (char c : charset) {
			addChar(c);
		}
	}

	/**
	 * Gets the character that best matches the given brightness value.
	 * @param brightness The brightness value to match.
	 * @return The character corresponding to the brightness value.
	 */
	public char getCharByImageBrightness(double brightness) {
		if (!this.isUpToDate) {
			normalizeGivenCharBrightness();
		}
		return this.roundingStrategy.getCharByImageBrightness(brightness, this.normalizedChars);
	}


	/**
	 * Adds a character to the set and updates brightness mappings.
	 * @param c The character to add.
	 */
	public void addChar(char c) {
		if (!CALCULATED_KEY_BRIGHTNESSES.containsKey(c)) {
			CALCULATED_KEY_BRIGHTNESSES.put(c, calculateCharBrightnessVal(c));
		}
		this.chars.add(c);
		this.isUpToDate = false;
	}


	/**
	 * Removes a character from the set.
	 * @param c The character to remove.
	 */
	public void removeChar(char c) {
		if (this.chars.remove(c)) {
			this.isUpToDate = false;
		}
	}

	/**
	 * Changes the rounding strategy used to match brightness
	 * values to characters.
	 * @param newRoundingStrategy The name of
	 *                            the new rounding strategy ("abs", "up", or "down").
	 * @throws RoundingFormatException If the strategy name is invalid.
	 */
	public void changeRoundingStrategy(String newRoundingStrategy) throws RoundingFormatException {
		this.roundingStrategy = RoundingStrategyFactory.getBrightnessRoundingStrategy(newRoundingStrategy);
	}

	/**
	 * Gets the current set of characters.
	 * @return A set of characters used for matching brightness values.
	 */
	public Set<Character> getCharSet() {
		return this.chars;
	}
	
	private double calculateCharBrightnessVal(char c) {
		boolean[][] blackWhiteImgOfChar = CharConverter.convertToBoolArray(c);
		int numOfWhiteCells = 0;
		for (boolean[] row : blackWhiteImgOfChar) {
			for (boolean cell : row) {
				if (cell) {
					numOfWhiteCells++;
				}
			}
		}
		return (double) numOfWhiteCells / NUM_OF_PIXELS_IN_ARRAY;
	}
	
	private void normalizeGivenCharBrightness() {
		TreeMap<Double, Character> newCharsBrightnessMap = new TreeMap<>();
		double minBrightness = INITIAL_MIN_BRIGHTNESS;
		double maxBrightness = INITIAL_MAX_BRIGHTNESS;
		for (Character chr : this.chars) {
			Double brightness = CALCULATED_KEY_BRIGHTNESSES.get(chr);
			if (brightness < minBrightness) {
				minBrightness = brightness;
			} else if (brightness > maxBrightness) {
				maxBrightness = brightness;
			}
		}
		for (Character chr : this.chars) {
			double newCharBrightness = (CALCULATED_KEY_BRIGHTNESSES.get(chr) - minBrightness) /
											   (maxBrightness - minBrightness);
			if (!newCharsBrightnessMap.containsKey(newCharBrightness) ||
						chr < newCharsBrightnessMap.get(newCharBrightness)) {
				newCharsBrightnessMap.put(newCharBrightness, chr);
			}
		}
		this.normalizedChars = newCharsBrightnessMap;
		this.isUpToDate = true;
	}
}
