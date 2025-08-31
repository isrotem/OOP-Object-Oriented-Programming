package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Responsible for darkening the game during nighttime.
 */
public class Night {
	private static final String NIGHT_TAG = "night";
	private static final Float MIDNIGHT_OPACITY = 0.7f;
	private static final int CYCLE_FACTOR = 2;
	
	/**
	 * Creates a black rectangle the size of the screen, which
	 * changes opacity over time to simulate night and day.
	 *
	 * @param windowDimensions Size of the game window.
	 * @param cycleLength      Length of a single day\night cycle.
	 * @return The created object, which can be added to the game.
	 */
	public static GameObject create(Vector2 windowDimensions, float cycleLength) {
		GameObject night = new GameObject(
				Vector2.ZERO, windowDimensions,
				new RectangleRenderable(Color.BLACK));
		night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		night.setTag(NIGHT_TAG);
		new Transition<Float>(night, night.renderer()::setOpaqueness, 0f, MIDNIGHT_OPACITY,
				Transition.CUBIC_INTERPOLATOR_FLOAT, cycleLength / CYCLE_FACTOR,
				Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
		return night;
	}
}
