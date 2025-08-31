package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A package-private class of the package image.
 *
 * @author Dan Nirel
 */
public class Image {
	
	private final Color[][] pixelArray;
	private final int width;
	private final int height;

	/**
	 * Constructs an {@code Image} by loading pixel data
	 * from the specified file.
	 * @param filename The path to the image file.
	 * @throws IOException If the file cannot be read or
	 * is not a valid image.
	 */
	public Image(String filename) throws IOException {
		BufferedImage im = ImageIO.read(new File(filename));
		width = im.getWidth();
		height = im.getHeight();
		
		
		pixelArray = new Color[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				pixelArray[i][j] = new Color(im.getRGB(j, i));
			}
		}
	}

	/**
	 * Constructs an {@code Image} with the specified pixel data,
	 * width, and height.
	 * @param pixelArray A 2D array of {@link Color} objects
	 *                   representing the pixel colors.
	 * @param width      The width of the image in pixels.
	 * @param height     The height of the image in pixels.
	 */
	public Image(Color[][] pixelArray, int width, int height) {
		this.pixelArray = pixelArray;
		this.width = width;
		this.height = height;
	}

	/**
	 * Gets the width of the image.
	 * @return The width of the image in pixels.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Gets the height of the image.
	 * @return The height of the image in pixels.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Gets the color of the pixel at the specified coordinates.
	 * @param x The x-coordinate of the pixel.
	 * @param y The y-coordinate of the pixel.
	 * @return The {@link Color} of the pixel at the specified coordinates.
	 */
	public Color getPixel(int x, int y) {
		return pixelArray[x][y];
	}

	/**
	 * Saves the current image to a file in JPEG format.
	 * The file is saved with the specified name,
	 * and the ".jpeg" extension is appended.
	 * @param fileName The name of the output file (without extension).
	 */
	public void saveImage(String fileName) {
		// Initialize BufferedImage, assuming Color[][] is already properly populated.
		BufferedImage bufferedImage = new BufferedImage(pixelArray[0].length, pixelArray.length,
				BufferedImage.TYPE_INT_RGB);
		// Set each pixel of the BufferedImage to the color from the Color[][].
		for (int x = 0; x < pixelArray.length; x++) {
			for (int y = 0; y < pixelArray[x].length; y++) {
				bufferedImage.setRGB(y, x, pixelArray[x][y].getRGB());
			}
		}
		File outputfile = new File(fileName + ".jpeg");
		try {
			ImageIO.write(bufferedImage, "jpeg", outputfile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
