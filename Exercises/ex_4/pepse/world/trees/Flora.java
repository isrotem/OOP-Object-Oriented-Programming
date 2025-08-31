package pepse.world.trees;

import pepse.world.Block;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Responsible for creating trees.
 */
public class Flora {
	private static final float TREE_PROBABILITY = 0.1f;
	private final Random random = new Random();
	private final Function<Float, Float> groundHeightAtX;
	private final int seed;
	private final Consumer<Float> avatarEnergyAdder;
	private final Consumer<Fruit> fruitAdder;
	private final Consumer<Fruit> fruitRemover;
	private final Map<Float, Boolean> treeAtX = new HashMap<>();
	
	/**
	 * A Flora object will determine for any given x whether a tree should
	 * be created at that coordinate, and if so, will create it at the
	 * appropriate height.
	 *
	 * @param groundHeightAtX   Method callback for determining the ground height.
	 * @param seed              Seed for the randomizer that will determine values for
	 *                          aspects of the trees.
	 * @param avatarEnergyAdder Method callback to allow the avatar to gain energy when
	 *                          eating fruit.
	 * @param fruitAdder        Method callback to allow adding fruit to the game.
	 * @param fruitRemover      Method callback to allow removing fruit from the game.
	 */
	public Flora(Function<Float, Float> groundHeightAtX, int seed, Consumer<Float> avatarEnergyAdder,
				 Consumer<Fruit> fruitAdder, Consumer<Fruit> fruitRemover) {
		this.groundHeightAtX = groundHeightAtX;
		this.seed = seed;
		this.random.setSeed(seed);
		this.avatarEnergyAdder = avatarEnergyAdder;
		this.fruitAdder = fruitAdder;
		this.fruitRemover = fruitRemover;
	}
	
	/**
	 * Creates trees in a specified horizontal range.
	 *
	 * @param minX The minimum x-coordinate of the range (inclusive).
	 * @param maxX The maximum x-coordinate of the range (inclusive).
	 * @return A Set of {@link Tree} objects representing the trees in the given range.
	 */
	public Set<Tree> createInRange(int minX, int maxX) {
		Set<Tree> trees = new HashSet<>();
		float newMinX = ((int) Math.floor((double) minX / Block.SIZE)) * Block.SIZE;
		Tree tree;
		for (float x = newMinX; x <= maxX; x += Block.SIZE) {
			if (!this.treeAtX.containsKey(x)) {
				this.treeAtX.put(x, random.nextFloat() <= TREE_PROBABILITY);
			}
			if (this.treeAtX.get(x)) {
				tree = new Tree(x, (int) Math.floor(this.groundHeightAtX.apply(x) / Block.SIZE) * Block.SIZE,
						new Random(Objects.hash(x, this.seed)), this.avatarEnergyAdder,
						this.fruitAdder, this.fruitRemover);
				trees.add(tree);
			}
		}
		return trees;
	}
}
