package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Responsible for creating the sun.
 */
public class Sun {
	private static final float HEIGHT_SCALE = 2f / 3;
	private static final String SUN_TAG = "sun";
	private static final Float FULL_CIRCLE = 360f;
	private static final float HALF = 0.5f;
	private static final int SIZE = 90;
	
	/**
	 * Creates a round sun that rotates in the screen.
	 *
	 * @param windowDimensions Size of the game window.
	 * @param cycleLength      Length of a single day\night cycle.
	 * @return The created sun, which can be added to the game.
	 */
	public static GameObject create(Vector2 windowDimensions, float cycleLength) {
		float baseHeight = windowDimensions.y() * HEIGHT_SCALE;
		Renderable renderable = new OvalRenderable(Color.YELLOW);
		GameObject sun = new GameObject(Vector2.ZERO, Vector2.ONES.mult(SIZE), renderable);
		sun.setCenter(new Vector2(windowDimensions.x() * HALF, baseHeight * HALF));
		sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		sun.setTag(SUN_TAG);
		Vector2 initialSunCenter = sun.getCenter();
		Vector2 cycleCenter = new Vector2(windowDimensions.x() * HALF, baseHeight);
		new Transition<Float>(sun,
				(Float angle) ->
						sun.setCenter(initialSunCenter.subtract(cycleCenter)
											  .rotated(angle)
											  .add(cycleCenter)),
				0f, FULL_CIRCLE, Transition.LINEAR_INTERPOLATOR_FLOAT, cycleLength,
				Transition.TransitionType.TRANSITION_LOOP, null
		);
		return sun;
	}
}
