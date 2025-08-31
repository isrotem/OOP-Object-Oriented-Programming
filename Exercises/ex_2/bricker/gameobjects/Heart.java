package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import static bricker.main.BrickerGameManager.HEART;

/**
 * Represents a heart object in the Bricker game.
 * A heart serves as a visual indicator or collectible item. It can be rendered in the game world
 * and is tagged with {@code HEART} for easy identification.
 * Extends the {@code GameObject} class from the DanoGL framework.
 * @see GameObject
 */
public class Heart extends GameObject {
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        setTag(HEART);
    }
}
