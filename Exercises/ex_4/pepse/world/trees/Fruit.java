package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;
import java.util.function.Consumer;

/**
 * Represents a fruit that can grow on a tree's leaf.
 */
public class Fruit extends GameObject {
	private static final int FRUIT_SIZE = 20;
	private static final Color FRUIT_COLOR = Color.RED;
	private static final float ENERGY_BONUS = 10f;
	private static final float CYCLE_LENGTH = 30f;
	private final Consumer<Float> avatarEnergyAdder;
	private final Consumer<Fruit> fruitAdder;
	private final Consumer<Fruit> fruitRemover;
	
	/**
	 * Each Fruit will grant the avatar some energy once eaten.
	 *
	 * @param topLeftCorner     Coordinates to create the fruit at.
	 * @param avatarEnergyAdder Method callback to allow the avatar to gain energy when
	 *                          eating fruit.
	 * @param fruitAdder        Method callback to allow adding fruit to the game.
	 * @param fruitRemover      Method callback to allow removing fruit from the game.
	 */
	public Fruit(Vector2 topLeftCorner, Consumer<Float> avatarEnergyAdder,
				 Consumer<Fruit> fruitAdder, Consumer<Fruit> fruitRemover) {
		super(topLeftCorner, new Vector2(FRUIT_SIZE, FRUIT_SIZE),
				new OvalRenderable(ColorSupplier.approximateColor(FRUIT_COLOR)));
		this.avatarEnergyAdder = avatarEnergyAdder;
		this.fruitAdder = fruitAdder;
		this.fruitRemover = fruitRemover;
	}
	
	/**
	 * Called on the first frame of a collision.
	 *
	 * @param other     The GameObject with which a collision occurred.
	 * @param collision Information regarding this collision.
	 *                  A reasonable elastic behavior can be achieved with:
	 *                  setVelocity(getVelocity().flipped(collision.getNormal()));
	 */
	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		super.onCollisionEnter(other, collision);
		this.avatarEnergyAdder.accept(ENERGY_BONUS);
		this.fruitRemover.accept(this);
		new ScheduledTask(other, CYCLE_LENGTH, false, this::addFruit);
	}
	
	private void addFruit() {
		this.fruitAdder.accept(this);
	}
}
