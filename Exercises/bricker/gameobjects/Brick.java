package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.util.ArrayList;

import static bricker.main.BrickerGameManager.BRICK_STR;

/**
 * Represents a brick in the Bricker game.
 * A brick is a static game object that can interact with other objects via collisions.
 * Upon collision, it may invoke specific strategies, decrement the brick counter,
 * and remove itself from the game.
 * Extends the {@code GameObject} class from the DanoGL framework.
 * @see GameObject
 */
public class Brick extends GameObject {

    // Fields
    private final GameObjectCollection gameObjects;
    private final Counter bricksCounter;
    private ArrayList<CollisionStrategy> collisionStrategies;

    /**
     * Constructs a new Brick object.
     * @param coordinates The top-left corner position of the brick in window coordinates (pixels).
     * @param dimensions The width and height of the brick in window coordinates.
     * @param renderable The renderable representing the brick's appearance.
     * @param collisionStrategies A list of strategies to execute upon collision.
     * @param gameObjects The collection of all game objects in the game.
     * @param bricksCounter A counter tracking the number of bricks remaining in the game.
     */
    public Brick(Vector2 coordinates, Vector2 dimensions, Renderable renderable,
                 ArrayList<CollisionStrategy> collisionStrategies,
                 GameObjectCollection gameObjects, Counter bricksCounter) {
        super(coordinates, dimensions, renderable);
        this.collisionStrategies = collisionStrategies;
        this.gameObjects = gameObjects;
        this.bricksCounter = bricksCounter;
        setTag(BRICK_STR);
    }

    /**
     * Handles the collision logic when another object collides with the brick.
     * The brick removes itself from the game, decrements the brick counter,
     * and executes its collision strategies.
     * @param other The {@code GameObject} that collided with the brick.
     * @param collision Details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (this.gameObjects.removeGameObject(this, Layer.STATIC_OBJECTS)){
            this.bricksCounter.decrement();
        }
        for(CollisionStrategy strategy : this.collisionStrategies){
            strategy.onCollision(this, other);
        }
    }
}
