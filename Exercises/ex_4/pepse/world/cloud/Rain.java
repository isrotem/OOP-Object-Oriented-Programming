package pepse.world.cloud;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.function.Consumer;

/**
 * Represents a single raindrop.
 */
public class Rain extends GameObject {
	private static final Color RAIN_COLOR = new Color(101, 141, 243);
	private static final float RAIN_SIZE = 10f;
	private static final float RAIN_DROP_TIME = 4f;
	private static final float GRAVITY = 600;
	private final Consumer<Rain> remover;
	
	/**
	 * Creates a raindrop at a given coordinate.
	 * @param center Coordinate to create raindrop at.
	 * @param remover Method callback that will be used to remove raindrop
	 *                from the game after it disappears.
	 */
	public Rain(Vector2 center, Consumer<Rain> remover) {
		super(Vector2.ZERO, new Vector2(RAIN_SIZE, RAIN_SIZE), new OvalRenderable(RAIN_COLOR));
		this.remover = remover;
		this.setCenter(center);
		new Transition<Float>(this, this.renderer()::setOpaqueness, 1f, 0f,
				Transition.LINEAR_INTERPOLATOR_FLOAT, RAIN_DROP_TIME,
				Transition.TransitionType.TRANSITION_ONCE, this::removeRain);
		transform().setAccelerationY(GRAVITY);
		this.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
	}
	
	private void removeRain() {
		this.remover.accept(this);
	}
}
