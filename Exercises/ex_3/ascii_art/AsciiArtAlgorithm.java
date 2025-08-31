package ascii_art;

import image.Image;
import image.ImageManipulator;
import image_char_matching.SubImgCharMatcher;

/**
 * The AsciiArtAlgorithm class generates ASCII art from an image.
 * It uses brightness levels of sub-regions of the image
 * to map each region to a corresponding ASCII character.
 * The algorithm supports efficient recalculations by
 * caching brightness values for the last processed resolution.
 * @author Rotem Israeli, Nadav Benjamin
 */
public class AsciiArtAlgorithm {
	private static int lastResolution = 0;
	private static double[][] lastBrightnesses = null;
	private final SubImgCharMatcher charMatcher;
	private final int resolution;
	private final Image image;

	/**
	 * Constructor for the AsciiArtAlgorithm.
	 * @param charMatcher  An instance of SubImgCharMatcher to map
	 *                     brightness levels to ASCII characters.
	 * @param resolution   The resolution of sub-image regions.
	 * @param image        The image to be converted to ASCII art.
	 */
	public AsciiArtAlgorithm(SubImgCharMatcher charMatcher, int resolution, Image image) {
		this.charMatcher = charMatcher;
		this.resolution = resolution;
		this.image = image;
	}

	/**
	 * Generates ASCII art from the provided image.
	 * If the resolution matches the last processed resolution,
	 * cached brightness values are reused for efficiency.
	 * Otherwise, brightness levels are recalculated.
	 * @return A 2D array of characters representing the ASCII art.
	 */
	public char[][] run() {
		double[][] subImgBrightnesses;
		if (lastResolution == this.resolution) {
			subImgBrightnesses = lastBrightnesses;
		} else {
			subImgBrightnesses = ImageManipulator.getSubImageBrightnesses(this.image, this.resolution);
			lastResolution = this.resolution;
			lastBrightnesses = subImgBrightnesses;
		}
		char[][] chars = new char[subImgBrightnesses.length][subImgBrightnesses[0].length];
		for (int i = 0; i < chars.length; i++) {
			for (int j = 0; j < chars[i].length; j++) {
				chars[i][j] = this.charMatcher.getCharByImageBrightness(subImgBrightnesses[i][j]);
			}
		}
		return chars;
	}
}
