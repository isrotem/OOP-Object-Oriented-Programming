package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the terrain of the game world.
 * This class is responsible for generating terrain blocks
 * within a specified range,
 * simulating natural terrain using Perlin noise
 * for realistic ground height variation.
 */
public class Terrain {
	private static final int FACTOR = 7;
	private final float groundHeightAtX0;
	private final NoiseGenerator noise;
	private static final float TWO_THIRDS = 2f / 3;
	private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
	private static final int TERRAIN_DEPTH = 25;

	/**
	 * Constructs a new Terrain instance.
	 *
	 * @param windowDimensions The dimensions of the game window. Used to calculate
	 *                         the initial ground height relative to the window height.
	 * @param seed             A seed for the noise generator, ensuring consistent
	 *                         terrain generation for the same seed value.
	 */
	public Terrain(Vector2 windowDimensions, int seed) {
		this.groundHeightAtX0 = windowDimensions.y() * TWO_THIRDS;
		this.noise = new NoiseGenerator(seed, (int)this.groundHeightAtX0);
	}

	/**
	 * Calculates the ground height at a given x-coordinate.
	 *
	 * @param x The x-coordinate for which the ground height is calculated.
	 * @return The height of the terrain at the specified x-coordinate.
	 */
	public float getHeightAt(float x) {
		return this.groundHeightAtX0 + (float)this.noise.noise(x, Block.SIZE * FACTOR);
	}

	/**
	 * Creates terrain blocks in a specified horizontal range.
	 *
	 * @param minX The minimum x-coordinate of the range (inclusive).
	 * @param maxX The maximum x-coordinate of the range (inclusive).
	 * @return A list of {@link Block} objects representing the terrain in the given range.
	 */
	public List<Block> createInRange(int minX, int maxX) {
		List<Block> blocks = new ArrayList<>();
		float newMinX = ((int) Math.floor((double) minX / Block.SIZE)) * Block.SIZE;
		Block block;
		Renderable renderable;
		for (float x = newMinX; x <= maxX; x += Block.SIZE) {
			float maxY = ((int) Math.floor(getHeightAt(x) / Block.SIZE)) * Block.SIZE;
			for (int i = 0; i < TERRAIN_DEPTH; i++) {
				renderable = new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
				block = new Block(new Vector2(x, maxY + i * Block.SIZE), renderable);
				blocks.add(block);
			}
		}
		return blocks;
	}
}
