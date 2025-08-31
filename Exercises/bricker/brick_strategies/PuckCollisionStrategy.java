package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Paddle;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * Represents a collision strategy that spawns puck-style balls upon collision.
 * This strategy interacts with the {@code BrickerGameManager} to add puck balls to the game.
 * Implements the {@code CollisionStrategy} interface.
 * @see CollisionStrategy
 * @see BrickerGameManager
 * @see Ball
 * @see Brick
 * @see Paddle
 */
public class PuckCollisionStrategy implements CollisionStrategy{

    //Fields
    private final BrickerGameManager manager;

    /**
     * Constructs a new PuckCollisionStrategy.
     * @param manager The game manager responsible for managing game objects and states.
     */
    public PuckCollisionStrategy(BrickerGameManager manager) {
        this.manager = manager;
    }

    /**
     * Handles the collision event by adding puck balls to the game.
     * Delegates the action to the game manager.
     * @param thisObj The {@code GameObject} that is the source of the collision.
     * @param otherObj The {@code GameObject} that collided with the source.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        this.manager.puckBallAddition(thisObj);
    }
}
