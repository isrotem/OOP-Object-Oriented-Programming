package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * Represents a collision strategy that adds an extra paddle to the game upon collision.
 * This strategy interacts with the {@code BrickerGameManager} to handle the addition of the extra paddle.
 * Implements the {@code CollisionStrategy} interface.
 * @see CollisionStrategy
 * @see BrickerGameManager
 */
public class ExtraPaddleCollisionStrategy implements CollisionStrategy {

    //Fields
    private final BrickerGameManager manager;

    /**
     * Constructs a new ExtraPaddleCollisionStrategy.
     * @param manager The game manager responsible for managing game objects and states.
     */
    public ExtraPaddleCollisionStrategy(BrickerGameManager manager) {
        this.manager = manager;
    }

    /**
     * Handles the collision event by adding an extra paddle to the game.
     * Delegates the action to the game manager.
     * @param thisObj The {@code GameObject} that is the source of the collision.
     * @param otherObj The {@code GameObject} that collided with the source.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        this.manager.handleExtraPaddleAddition();
    }
}
