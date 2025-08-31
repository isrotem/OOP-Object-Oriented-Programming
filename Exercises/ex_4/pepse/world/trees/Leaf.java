package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;

/**
 * Represents a leaf on a tree.
 */
public class Leaf extends GameObject {
	private static final Color BASE_LEAF_COLOR = new Color(50, 200, 30);
	private static final String LEAF_TAG = "leaf";
	private static final Float INITIAL_ANGLE = -10f;
	private static final Float FINAL_ANGLE = 10f;
	private static final Vector2 INITIAL_SIZE = new Vector2(Block.SIZE * 0.8f, Block.SIZE);
	private static final Vector2 FINAL_SIZE = new Vector2(Block.SIZE * 1.2f, Block.SIZE);
	private static final float SWING_TRANSITION_TIME = 5;
	private static final float SIZE_TRANSITION_TIME = 3;
	private static final float MAX_WAIT_TIME = 2f;
	
	/**
	 * Each leaf sways in the wind, squishing, stretching, and changing angle.
	 *
	 * @param topLeftCorner Coordinates to create the leaf at.
	 * @param waitTime      Time to wait before creating transitions for the leaf.
	 */
	public Leaf(Vector2 topLeftCorner, float waitTime) {
		super(topLeftCorner, Vector2.ONES.mult(Block.SIZE),
				new RectangleRenderable(ColorSupplier.approximateColor(BASE_LEAF_COLOR)));
		new ScheduledTask(this, waitTime * MAX_WAIT_TIME, false, this::createTransitions);
		this.setTag(LEAF_TAG);
	}
	
	private void createTransitions() {
		new Transition<>(this, this.renderer()::setRenderableAngle, INITIAL_ANGLE,
				FINAL_ANGLE, Transition.CUBIC_INTERPOLATOR_FLOAT, SWING_TRANSITION_TIME,
				Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
		new Transition<>(this, this::setDimensions, INITIAL_SIZE, FINAL_SIZE,
				Transition.CUBIC_INTERPOLATOR_VECTOR, SIZE_TRANSITION_TIME,
				Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
	}
}
