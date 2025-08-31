package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a single block in the game world.
 * Blocks are immovable game objects with a fixed size,
 * used as building units for terrains or other structures.
 * Each block has a predefined size and is tagged
 * for easy identification within the game.
 */
public class Block extends GameObject {
	private static final String BLOCK_TAG = "block";
	/**
	 * Size of each block.
	 */
	public static final int SIZE = 30;

	/**
	 * Constructs a new Block instance.
	 *
	 * @param topLeftCorner The position of the top-left corner of
	 *                      the block in world coordinates.
	 * @param renderable    The renderable representing the block.
	 *                      Can include textures or colors.
	 */
	public Block(Vector2 topLeftCorner, Renderable renderable) {
		super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
		physics().preventIntersectionsFromDirection(Vector2.ZERO);
		physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
		this.setTag(BLOCK_TAG);
	}
}
