package pepse;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

/**
 * Represents an energy display object in the game.
 * The class extends GameObject and is used to show avatar's current energy level
 * as a percentage. The energy display updates dynamically based on the input value.
 * The position and dimensions of the object are defined in the camera's coordinate space.
 */
public class Energy extends GameObject {
	/**
	 * Construct a new GameObject instance.
	 *
	 * @param topLeftCorner Position of the object, in window coordinates (pixels).
	 *                      Note that (0,0) is the top-left corner of the window.
	 * @param dimensions    Width and height in window coordinates.
	 * @param renderable    The renderable representing the object. Can be null, in which case
	 *                      the GameObject will not be rendered.
	 */
	public Energy(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
		super(topLeftCorner, dimensions, renderable);
		this.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
	}
	
	/**
	 * Performs this operation on the given argument.
	 *
	 * @param aFloat the input argument
	 */
	public void update(Float aFloat) {
		this.renderer().setRenderable(new TextRenderable(aFloat.intValue() + "%"));
	}
}
