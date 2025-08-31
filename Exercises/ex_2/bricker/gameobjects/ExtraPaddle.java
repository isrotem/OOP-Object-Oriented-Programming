package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import static bricker.main.BrickerGameManager.EXTRA_PADDLE_STR;

/**
 * Represents an additional paddle in the Bricker game.
 * This paddle behaves like the main paddle but has a limited lifespan,
 * determined by the number of collisions it experiences. Once the maximum
 * collisions are reached, the paddle is removed from the game.
 * Extends the {@code Paddle} class.
 * @see Paddle
 */
public class ExtraPaddle extends Paddle {

    // Constans
    private static final int END_OF_EXTRA_PADDLE = 0;
    private static final int COLLISION_MAX = 4;

    // Fields
    private final GameObjectCollection gameObjects;
    private Counter collisionCounter;

    /**
     * Constructs a new ExtraPaddle object.
     * @param coordinates The top-left corner position of the paddle in window coordinates (pixels).
     * @param dimensions The width and height of the paddle in window coordinates.
     * @param paddleImage The renderable representing the paddle's appearance.
     * @param inputListener Utility to listen to user inputs for paddle control.
     * @param windowDimensions The dimensions of the game window.
     * @param gameObjects The collection of all game objects in the game.
     */
    public ExtraPaddle(Vector2 coordinates, Vector2 dimensions, Renderable paddleImage,
                       UserInputListener inputListener, Vector2 windowDimensions, GameObjectCollection gameObjects) {
        super(coordinates, dimensions, paddleImage, inputListener, windowDimensions);
        this.collisionCounter = new Counter(COLLISION_MAX);
        this.gameObjects = gameObjects;
        setTag(EXTRA_PADDLE_STR);
    }


    /**
     * Handles collision behavior when another object collides with the extra paddle.
     * Decrements the collision counter, and if the counter reaches zero, removes the paddle from the game.
     * @param other The {@code GameObject} that collided with the paddle.
     * @param collision Details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.collisionCounter.decrement();
        if (this.collisionCounter.value() == END_OF_EXTRA_PADDLE){
            this.gameObjects.removeGameObject(this);
        }
    }
}
