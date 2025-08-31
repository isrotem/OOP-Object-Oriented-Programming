package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Represents the sky in the game world.
 * This class is responsible for creating a static sky background
 * that spans the entire game window.
 * The sky is rendered in a fixed color and uses
 * camera coordinates for positioning.
 */
public class Sky {
	private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");
	private static final String SKY_TAG = "sky";


	/**
	 * Creates and returns a sky background object.
	 *
	 * @param windowDimensions The dimensions of the game window,
	 *                         determining the size of the sky object.
	 * @return A {@link GameObject} representing the sky,
	 * positioned in the camera's coordinate space.
	 */
	public static GameObject create(Vector2 windowDimensions) {
		GameObject sky = new GameObject(
				Vector2.ZERO, windowDimensions,
				new RectangleRenderable(BASIC_SKY_COLOR));
		sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		sky.setTag(SKY_TAG);
		return sky;
	}
}
