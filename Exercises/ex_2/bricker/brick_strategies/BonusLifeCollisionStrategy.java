package bricker.brick_strategies;

import bricker.gameobjects.BonusLifeHeart;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * Represents a collision strategy that spawns a bonus life heart upon collision.
 * When the strategy is triggered, it delegates to the {@code BrickerGameManager} to add a bonus life heart to the game.
 * Implements the {@code CollisionStrategy} interface.
 * @see CollisionStrategy
 */
public class BonusLifeCollisionStrategy implements CollisionStrategy {

    //Fields
    private final BrickerGameManager manager;

    /**
     * Constructs a new BonusLifeCollisionStrategy.
     * @param manager The game manager responsible for managing bonus life additions.
     */
    public BonusLifeCollisionStrategy(BrickerGameManager manager) {
        this.manager = manager;
    }

    /**
     * Handles the collision by triggering the addition of a bonus life heart.
     * Delegates the action to the game manager.
     * @param thisObj The {@code GameObject} that is the source of the collision.
     * @param otherObj The {@code GameObject} that collided with the source.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        this.manager.bonusLifeAddition(thisObj);
    }
}
