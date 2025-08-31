package pepse.world.trees;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
import java.util.*;
import java.util.function.Consumer;

/**
 * Represents a tree, which may have leaves and fruits.
 */
public class Tree {
	private static final Color BASE_TRUNK_COLOR = new Color(100, 50, 20);
	private static final int MAX_TREE_HEIGHT = 8;
	private static final int MIN_TREE_HEIGHT = 5;
	private static final int LEAVES_WIDTH = 6;
	private static final float LEAF_PERCENTAGE = 0.66f;
	private static final float FRUIT_PERCENTAGE = 0.1f;
	private static final float FRUIT_OFFSET = 5f;
	private final Set<Block> trunk = new HashSet<>();
	private final Set<Leaf> leaves = new HashSet<>();
	private final Set<Fruit> fruit = new HashSet<>();
	private final Random random;
	private final float x;
	private final float groundHeightAtX;
	private final int trunkHeight;
	
	/**
	 * Each tree has a trunk, and some random number of leaves.
	 *
	 * @param x                 X coordinate to create the tree at.
	 * @param groundHeightAtX   Height of the ground at x.
	 * @param random            Randomizer which will decide values for aspects of the tree.
	 * @param avatarEnergyAdder Method callback to allow the avatar to gain energy when
	 *                          eating fruit.
	 * @param fruitAdder        Method callback to allow adding fruit to the game.
	 * @param fruitRemover      Method callback to allow removing fruit from the game.
	 */
	public Tree(float x, float groundHeightAtX, Random random, Consumer<Float> avatarEnergyAdder,
				Consumer<Fruit> fruitAdder, Consumer<Fruit> fruitRemover) {
		this.x = x;
		this.groundHeightAtX = groundHeightAtX;
		this.random = random;
		this.trunkHeight = this.random.nextInt(MIN_TREE_HEIGHT, MAX_TREE_HEIGHT);
		createTrunk();
		createLeaves(avatarEnergyAdder, fruitAdder, fruitRemover);
	}
	
	/**
	 * Getter for the tree's trunk.
	 *
	 * @return Set of {@link Block} objects making up the tree's trunk.
	 */
	public Set<Block> getTrunk() {
		return this.trunk;
	}
	
	/**
	 * Getter for the tree's leaves.
	 *
	 * @return Set of {@link Leaf} objects making up the tree's leaves.
	 */
	public Set<Leaf> getLeaves() {
		return this.leaves;
	}
	
	/**
	 * Getter for the tree's fruits.
	 *
	 * @return Set of {@link Fruit} objects making up the tree's fruits.
	 */
	public Set<Fruit> getFruit() {
		return this.fruit;
	}
	
	private void createTrunk() {
		Block block;
		Renderable renderable;
		for (int i = 0; i < this.trunkHeight; i++) {
			renderable = new RectangleRenderable(ColorSupplier.approximateColor(BASE_TRUNK_COLOR));
			block = new Block(new Vector2(this.x, this.groundHeightAtX - (Block.SIZE * (i + 1))), renderable);
			this.trunk.add(block);
		}
	}
	
	private void createLeaves(Consumer<Float> avatarEnergyAdder, Consumer<Fruit> fruitAdder,
							  Consumer<Fruit> fruitRemover) {
		float midTrunkX = this.x + Block.SIZE * 0.5f;
		Leaf leaf;
		Fruit fruit;
		for (float x = midTrunkX - 0.5f * LEAVES_WIDTH * Block.SIZE;
			 x < midTrunkX + 0.5f * LEAVES_WIDTH * Block.SIZE; x += Block.SIZE) {
			for (float y = this.groundHeightAtX - this.trunkHeight * Block.SIZE -
								   0.5f * LEAVES_WIDTH * Block.SIZE;
				 y < this.groundHeightAtX - this.trunkHeight * Block.SIZE +
							 0.5f * LEAVES_WIDTH * Block.SIZE; y += Block.SIZE) {
				if (this.random.nextFloat() < LEAF_PERCENTAGE) {
					leaf = new Leaf(new Vector2(x, y), this.random.nextFloat());
					this.leaves.add(leaf);
					if (this.random.nextFloat() < FRUIT_PERCENTAGE) {
						fruit = new Fruit(
								leaf.getTopLeftCorner().add(new Vector2(FRUIT_OFFSET, FRUIT_OFFSET)),
								avatarEnergyAdder, fruitAdder, fruitRemover
						);
						this.fruit.add(fruit);
					}
				}
			}
		}
	}
}
