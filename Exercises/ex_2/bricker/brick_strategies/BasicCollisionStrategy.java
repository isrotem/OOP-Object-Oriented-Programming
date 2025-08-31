package bricker.brick_strategies;

import danogl.GameObject;


/**
 * Represents a basic collision strategy in the Bricker game.
 * This strategy defines a no-op (no operation) behavior upon collision,
 * meaning that no action is taken when a collision occurs.
 * Useful as a default or placeholder strategy in the game.
 * Implements the {@code CollisionStrategy} interface.
 * @see CollisionStrategy
 */
public class BasicCollisionStrategy implements CollisionStrategy {
    /**
     * Constructs a new BasicCollisionStrategy object.
     */
    public BasicCollisionStrategy() {}

    /**
     * Handles the collision event with no specific behavior.
     * This method is intentionally left empty, as this strategy does not perform any actions on collision.
     * @param thisObj The {@code GameObject} that is the source of the collision.
     * @param otherObj The {@code GameObject} that collided with the source.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {}
}
