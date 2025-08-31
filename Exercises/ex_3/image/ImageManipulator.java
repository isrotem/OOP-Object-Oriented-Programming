package image;

import java.awt.*;


/**
 * Provides utility methods for manipulating images.
 * This class includes methods for padding images
 * to the nearest power-of-two dimensions
 * and calculating brightness values for sub-images
 * based on specified resolution.
 * @author Rotem Israeli, Nadav Benjamin
 * @see Image
 */
public class ImageManipulator {
	
	private static final int BASE_TWO = 2;
	private static final int DIVISION_FACTOR = 2;
	private static final double RED_COMPONENT = 0.2126;
	private static final double GREEN_COMPONENT = 0.7152;
	private static final double BLUE_COMPONENT = 0.0722;
	private static final int MAX_BRIGHTNESS = 255;
	
	private ImageManipulator() {
	}

	/**
	 * Pads an image to the nearest power-of-two dimensions.
	 * Adds white padding to the edges of the image
	 * to ensure both its width and height
	 * are powers of two.
	 * @param image The image to be padded.
	 * @return A new image with dimensions padded
	 * to the nearest power of two.
	 */
	public static Image padImage(Image image) {
		int newWidth = (int) Math.pow(BASE_TWO,
				Math.ceil(Math.log(image.getWidth()) / Math.log(BASE_TWO)));
		int newHeight = (int) Math.pow(BASE_TWO,
				Math.ceil(Math.log(image.getHeight()) / Math.log(BASE_TWO)));
		int widthDifference = newWidth - image.getWidth();
		int heightDifference = newHeight - image.getHeight();
		Color[][] newPixels = new Color[newHeight][newWidth];
		for (int y = 0; y < newHeight; y++) {
			for (int x = 0; x < newWidth; x++) {
				if (x < widthDifference / DIVISION_FACTOR || y < heightDifference / DIVISION_FACTOR
							|| x >= image.getWidth() + widthDifference / DIVISION_FACTOR
							|| y >= image.getHeight() + heightDifference / DIVISION_FACTOR) {
					newPixels[y][x] = Color.WHITE;
				} else {
					newPixels[y][x] =
							image.getPixel(y - heightDifference / DIVISION_FACTOR,
									x - widthDifference / DIVISION_FACTOR);
				}
			}
		}
		return new Image(newPixels, newWidth, newHeight);
	}

	/**
	 * Calculates brightness values for sub-images within
	 * the given resolution.
	 * Divides the image into smaller sub-images based
	 * on the resolution and computes
	 * the average brightness for each sub-image.
	 * @param image The image whose brightness values are to be calculated.
	 * @param resolution The number of divisions along one dimension.
	 * @return A 2D array of brightness values for the sub-images.
	 */
	public static double[][] getSubImageBrightnesses(Image image, int resolution) {
		int subImageWidth = image.getWidth() / resolution;
		double[][] subImageBrightnesses = new double[image.getHeight() / subImageWidth][resolution];
		double brightness;
		Color color;
		for (int y = 0; y < image.getHeight() / subImageWidth; y++) {
			for (int x = 0; x < resolution; x++) {
				brightness = 0;
				for (int pixelY = 0; pixelY < subImageWidth; pixelY++) {
					for (int pixelX = 0; pixelX < subImageWidth; pixelX++) {
						color = image.getPixel(y * subImageWidth + pixelY, x * subImageWidth + pixelX);
						brightness += color.getRed() * RED_COMPONENT
											  + color.getGreen() * GREEN_COMPONENT
											  + color.getBlue() * BLUE_COMPONENT;
					}
				}
				subImageBrightnesses[y][x] = brightness /
													 (Math.pow(subImageWidth, BASE_TWO) * MAX_BRIGHTNESS);
			}
		}
		return subImageBrightnesses;
	}
}
