package pepse.world.cloud;

import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a cloud in the game. A cloud is
 * made of Blocks, and can generate rain.
 */
public class Cloud {
	private static final Color BASE_CLOUD_COLOR = Color.WHITE;
	private static final int CLOUD_ARRAY_SIZE = 6;
	private static final float CLOUD_TRANSITION_TIME = 17;
	private static final Random RANDOM = new Random();
	private final Set<Block> blocks;
	private final Consumer<Rain> rainAdder;
	private final Consumer<Rain> rainRemover;
	
	/**
	 * Create a cloud. Each block in the cloud has an individual
	 * transition, moving that block across the screen.
	 *
	 * @param topLeftCorner    Coordinates for the top left corner of the cloud.
	 * @param windowDimensions Size of the game window.
	 * @param rainAdder        Method callback to allow created rain to be added to the game.
	 * @param rainRemover      Method callback to allow rain to be removed from the game.
	 */
	public Cloud(Vector2 topLeftCorner, Vector2 windowDimensions, Consumer<Rain> rainAdder,
				 Consumer<Rain> rainRemover) {
		this.rainAdder = rainAdder;
		this.rainRemover = rainRemover;
		List<List<Integer>> cloud = List.of(
				List.of(0, 1, 1, 0, 0, 0),
				List.of(1, 1, 1, 0, 1, 0),
				List.of(1, 1, 1, 1, 1, 1),
				List.of(1, 1, 1, 1, 1, 1),
				List.of(0, 1, 1, 1, 0, 0),
				List.of(0, 0, 0, 0, 0, 0)
		);
		Renderable cloudRenderable =
				new RectangleRenderable(ColorSupplier.approximateMonoColor(BASE_CLOUD_COLOR));
		this.blocks = new HashSet<>();
		Block block;
		for (int y = 0; y < CLOUD_ARRAY_SIZE; y++) {
			for (int x = 0; x < CLOUD_ARRAY_SIZE; x++) {
				if (cloud.get(y).get(x) == 1) {
					block = new Block(topLeftCorner.add(new Vector2(x * Block.SIZE, y * Block.SIZE)),
							cloudRenderable);
					block.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
					new Transition<Vector2>(block, block::setTopLeftCorner,
							topLeftCorner.add(new Vector2(x * Block.SIZE, y * Block.SIZE)),
							block.getTopLeftCorner().add(new Vector2(windowDimensions.x(), 0))
									.subtract(new Vector2(topLeftCorner.x(), 0)),
							Transition.LINEAR_INTERPOLATOR_VECTOR, CLOUD_TRANSITION_TIME,
							Transition.TransitionType.TRANSITION_LOOP, null);
					this.blocks.add(block);
				}
			}
		}
	}
	
	/**
	 * Getter to allow the manager to add all the Blocks in the cloud.
	 *
	 * @return Set of all Blocks making up the cloud.
	 */
	public Set<Block> getBlocks() {
		return blocks;
	}
	
	/**
	 * Whenever this method is called, each block in the cloud will randomly
	 * decide whether to create a raindrop or not.
	 */
	public void createRain() {
		for (Block block : this.blocks) {
			if (RANDOM.nextBoolean()) {
				Rain rain = new Rain(block.getCenter(), this.rainRemover);
				this.rainAdder.accept(rain);
			}
		}
	}
}
