package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;
import static bricker.main.BrickerGameManager.PADDLE_STR;

/**
 * Represents the player's paddle in the Bricker game.
 * The paddle moves horizontally in response to user input and is constrained within the screen boundaries.
 * Extends the {@code GameObject} class from the DanoGL framework.
 * @see GameObject
 */
public class Paddle extends GameObject {

    // Constants
    private static final float MOVEMENT_SPEED = 300;

    // Fields
    private final UserInputListener inputListener;
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = 5;
    private Vector2 windowDimensions;

    /**
     * Constructs a new Paddle object.
     * @param vector2 The top-left corner position of the paddle in window coordinates (pixels).
     * @param vector21 The dimensions (width and height) of the paddle in window coordinates.
     * @param renderable The renderable representing the paddle's appearance.
     * @param inputListener The listener for user input controlling the paddle's movement.
     * @param windowDimensions The dimensions of the game window.
     */
    public Paddle(Vector2 vector2, Vector2 vector21, Renderable renderable, UserInputListener inputListener,
                  Vector2 windowDimensions) {
        super(vector2, vector21, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        setTag(PADDLE_STR);
    }

    /**
     * Updates the paddle's position based on user input.
     * Ensures that the paddle stays within the screen boundaries and adjusts its velocity accordingly.
     * @param deltaTime The time elapsed since the last frame (in seconds).
     */
    @Override
    public void update(float deltaTime) {
        float minX = MIN_DISTANCE_FROM_SCREEN_EDGE;
        float maxX = windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x();

        super.update(deltaTime);
        Vector2 movementDir  = Vector2.ZERO;

        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            movementDir = movementDir.add(Vector2.LEFT);
        }

        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            movementDir = movementDir.add(Vector2.RIGHT);
        }

        setVelocity(movementDir.mult(MOVEMENT_SPEED));

        if (getTopLeftCorner().x() < MIN_DISTANCE_FROM_SCREEN_EDGE) {
            setTopLeftCorner(new Vector2(minX, getTopLeftCorner().y()));

        }

        else if (getTopLeftCorner().x() > maxX) {
            setTopLeftCorner(new Vector2(maxX, getTopLeftCorner().y()));
        }
    }
}
