package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import static bricker.main.BrickerGameManager.*;

/**
 * Represents a bonus life heart object in the Bricker game.
 * The heart falls from the top of the screen and grants the player an extra life
 * upon colliding with the paddle. It removes itself from the game after being collected.
 * Extends the {@code Heart} class to provide additional bonus life functionality.
 * @see Heart
 */
public class BonusLifeHeart extends Heart {

    // Constants
    private static final int BONUS_LIFE_SPEED = 100;

    // Fields
    private final GameObjectCollection gameObjects;
    private final Counter liveCounter;
    private final Lives livesObject;

    /**
     * Constructs a new bonus life heart object.
     * @param topLeftCorner The position of the heart's top-left corner in the game world.
     * @param dimensions The width and height of the heart.
     * @param renderable The renderable representing the heart's appearance.
     * @param gameObjects The collection of all game objects in the game.
     * @param livesCounter The counter tracking the number of lives.
     * @param livesObject The lives manager responsible for handling life logic.
     */
    public BonusLifeHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                          GameObjectCollection gameObjects, Counter livesCounter,
                          Lives livesObject) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjects = gameObjects;
        this.liveCounter = livesCounter;
        this.livesObject = livesObject;
        setVelocity(Vector2.DOWN.mult(BONUS_LIFE_SPEED));
        setTag(BONUS_LIFE_STR);
    }

    /**
     * Determines whether this heart should collide with another game object.
     * The heart collides only with the paddle and not with any extra paddles.
     * @param other The {@code GameObject} to check collision with.
     * @return {@code true} if the heart should collide with the other object, {@code false} otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return (other.getTag().equals(PADDLE_STR) && !other.getTag().equals(EXTRA_PADDLE_STR));
    }

    /**
     * Handles collision behavior when the heart collides with the paddle.
     * Increments the player's life counter if a bonus life has not already been granted.
     * Removes the heart from the game after the collision.
     * @param other The {@code GameObject} this heart collided with.
     * @param collision Details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (other.getTag().equals(PADDLE_STR)) {
            if (!this.livesObject.getScoredLifeBonus()){
                this.liveCounter.increment();
            }
            this.gameObjects.removeGameObject(this);
        }
    }
}
