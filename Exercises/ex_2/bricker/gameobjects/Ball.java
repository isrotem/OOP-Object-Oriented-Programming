package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import static bricker.main.BrickerGameManager.BALL_STR;

/**
 * Represents the ball object in the Bricker game.
 * The ball bounces off surfaces and tracks collision counts.
 * It also plays a sound on each collision.
 * Extends the {@code GameObject} class from the DanoGL framework.
 * @see GameObject
 */
public class Ball extends GameObject {


    // Fields

    private final Sound collisionSound;
    private final Counter collisionCounter;

    /**
     * Constructs a new ball object.
     * @param topLeftCorner The position of the ball's top-left corner in the game world.
     * @param dimensions The width and height of the ball.
     * @param renderable The renderable representing the ball's appearance.
     * @param collisionSound The sound played upon collision.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound){
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCounter = new Counter();
        setTag(BALL_STR);
    }

    /**
     * Called when the ball enters a collision with another object.
     * This method increments the collision counter, flips the ball's velocity based on the collision normal,
     * and plays a collision sound.
     * @param other The {@code GameObject} this ball collided with.
     * @param collision Details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.collisionCounter.increment();
        setVelocity(getVelocity().flipped(collision.getNormal()));
        collisionSound.play();
    }

    /**
     * Returns the number of collisions the ball has experienced.
     * @return The value of the collision counter.
     */
    public int getCollisionCounter(){
        return collisionCounter.value();
    }
}
