package pepse.world.daynight;

import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Responsible for creating a halo around the sun.
 */
public class SunHalo {
	private static final int MAX_RGB = 255;
	private static final int TRANSPARENCY_VALUE = 20;
	private static final int HALO_RADIUS = 120;
	private static final String HALO_TAG = "halo";
	
	/**
	 * Creates a halo that will follow the given sun.
	 *
	 * @param sun Sun for the halo to follow.
	 * @return The created halo, which can be added to the game.
	 */
	public static GameObject create(GameObject sun) {
		Color transparentYellow = new Color(MAX_RGB, MAX_RGB, 0, TRANSPARENCY_VALUE);
		Renderable renderable = new OvalRenderable(transparentYellow);
		GameObject sunHalo = new GameObject(Vector2.ZERO, Vector2.ONES.mult(HALO_RADIUS), renderable);
		sunHalo.setCenter(sun.getCenter());
		sunHalo.setCoordinateSpace(sun.getCoordinateSpace());
		sunHalo.setTag(HALO_TAG);
		sunHalo.addComponent(deltaTime -> sunHalo.setCenter(sun.getCenter()));
		return sunHalo;
	}
}
