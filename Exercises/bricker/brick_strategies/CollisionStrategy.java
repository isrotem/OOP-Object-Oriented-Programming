package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Represents a strategy for handling collisions in the Bricker game.
 * Classes implementing this interface define specific behaviors that should
 * occur when a collision is detected between game objects.
 * This design allows for flexible and reusable collision logic.
 */
public interface CollisionStrategy {

    /**
     * Defines the behavior to execute upon a collision.
     * @param thisObj The {@code GameObject} that is the source of the collision.
     * @param otherObj The {@code GameObject} that collided with the source.
     */
    void onCollision(GameObject thisObj, GameObject otherObj);
}
