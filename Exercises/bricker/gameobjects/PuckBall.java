package bricker.gameobjects;

import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

import static bricker.main.BrickerGameManager.BALL_SPEED;
import static bricker.main.BrickerGameManager.PUCK_BALL_STR;

/**
 * Represents a puck-style ball in the Bricker game.
 * The puck ball behaves similarly to the regular ball but is initialized with a random velocity direction.
 * Extends the {@code Ball} class to reuse collision behavior and appearance logic.
 * @see Ball
 */
public class PuckBall extends Ball {

    /**
     * Constructs a new PuckBall object.
     * @param topLeftCorner The position of the puck ball's top-left corner in window coordinates (pixels).
     * @param dimensions The width and height of the puck ball in window coordinates.
     * @param renderable The renderable representing the puck ball's appearance.
     * @param collisionSound The sound played upon collision.
     * @param rand Random object used to generate a random velocity direction.
     */
    public PuckBall(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                    Sound collisionSound, Random rand) {

        super(topLeftCorner, dimensions, renderable, collisionSound);
        setTag(PUCK_BALL_STR);
        double angle = rand.nextDouble() * Math.PI;
        float velocityX = (float) (Math.cos(angle) * BALL_SPEED);
        float velocityY = (float) (Math.sin(angle) * BALL_SPEED);
        setVelocity(new Vector2(velocityX, velocityY));
    }
}
