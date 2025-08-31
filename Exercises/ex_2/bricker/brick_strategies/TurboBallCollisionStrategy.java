package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.ImageReader;

import static bricker.main.BrickerGameManager.BALL_STR;
import static bricker.main.BrickerGameManager.PUCK_BALL_STR;

/**
 * Represents a collision strategy that activates a turbo ball effect upon collision.
 * When triggered, the strategy delegates to the {@code BrickerGameManager} to handle the turbo ball logic.
 * Implements the {@code CollisionStrategy} interface.
 * @see CollisionStrategy
 * @see BrickerGameManager
 * @see GameObject
 */
public class TurboBallCollisionStrategy implements CollisionStrategy {

    //Fields
    private BrickerGameManager manager;

    /**
     * Constructs a new TurboBallCollisionStrategy.
     * @param manager The game manager responsible for managing game objects and states.
     */
    public TurboBallCollisionStrategy(BrickerGameManager manager) {
        this.manager = manager;
    }

    /**
     * Handles the collision event by activating the turbo ball effect.
     * The effect is triggered only if the collided object is a regular ball and not a puck ball.
     * Delegates the action to the game manager.
     * @param thisObj The {@code GameObject} that is the source of the collision.
     * @param otherObj The {@code GameObject} that collided with the source.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (otherObj.getTag().equals(BALL_STR) && !otherObj.getTag().equals(PUCK_BALL_STR))
        {

            this.manager.HandleTurboBall();
        }
    }

}