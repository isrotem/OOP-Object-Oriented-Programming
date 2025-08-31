package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.*;
import java.util.ArrayList;

import static bricker.main.BrickerGameManager.HEART_SIZE;

/**
 * Represents the lives system in the Bricker game.
 * Manages the display of remaining lives as heart icons and a numeric counter.
 * Updates the number of lives dynamically based on the player's progress.
 * Extends the {@code GameObject} class from the DanoGL framework.
 * @see GameObject
 */
public class Lives extends GameObject {

    // Constants
    private static final int WALL_GAP = 50;
    private static final int TEXT_X = 0;
    private static final int TEXT_SIZE = 40;
    private static final int HEART_SIZE_AND_GAP = 45;

    // Fields
    private final Vector2 windowDimensions;
    private final Renderable heartImage;
    private final Counter newLifeCounter;
    private Counter livesCounter;
    private ArrayList<GameObject> livesArr;
    private GameObjectCollection objectCollection;
    private TextRenderable textLives;
    private int numOfLives;
    private boolean scoredLifeBonus;

    /**
     * Constructs a new Lives object.
     * @param topLeftCorner The position of the lives display's top-left corner in window coordinates (pixels).
     * @param dimensions The dimensions of the lives display in window coordinates.
     * @param renderable The renderable used for heart icons.
     * @param objectCollection The collection of all game objects in the game.
     * @param livesCounter The counter tracking the current number of lives.
     * @param windowDimensions The dimensions of the game window.
     * @param numOfLives The initial number of lives.
     * @param newLifeCounter A counter used to track life changes.
     */
    public Lives(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 GameObjectCollection objectCollection, Counter livesCounter,
                 Vector2 windowDimensions, int numOfLives, Counter newLifeCounter) {
        super(topLeftCorner, dimensions, renderable);
        this.objectCollection = objectCollection;
        this.numOfLives = numOfLives;
        this.livesCounter = livesCounter;
        this.newLifeCounter = newLifeCounter;
        this.windowDimensions = windowDimensions;
        this.textLives = new TextRenderable(Integer.toString(numOfLives));
        this.scoredLifeBonus = false;
        this.heartImage = renderable;
        createLives(this.heartImage);
    }

    /**
     * Returns whether the player has scored a bonus life.
     * @return {@code true} if a bonus life has been scored, {@code false} otherwise.
     */
    public boolean getScoredLifeBonus() {
        return this.scoredLifeBonus;
    }

     //Initializes the heart icons and lives counter display.
    private void createLives(Renderable heartImg) {
        this.livesArr = new ArrayList<GameObject>();
        this.objectCollection.addGameObject(new GameObject(new Vector2(TEXT_X,
                windowDimensions.y() - WALL_GAP), new Vector2(TEXT_SIZE,TEXT_SIZE), textLives), Layer.BACKGROUND);
        textLives.setColor(Color.RED);

        for (int i = 0; i < this.numOfLives; i++) {
            Heart newObj = new Heart(new Vector2(TEXT_SIZE + HEART_SIZE_AND_GAP * i,
                    windowDimensions.y()-WALL_GAP), new Vector2(HEART_SIZE,HEART_SIZE), heartImg);
            livesArr.add(newObj);
            this.objectCollection.addGameObject(newObj, Layer.BACKGROUND);
        }
    }

    /**
     * Updates the lives display based on the player's current lives count.
     * Removes heart icons when lives are lost and adds icons when a bonus life is gained.
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.livesCounter.value() < this.numOfLives) {
            this.numOfLives = this.livesCounter.value();
            textLives.setString(Integer.toString(this.livesCounter.value()));
            this.objectCollection.removeGameObject(this.livesArr.get(this.livesArr.size()-1), Layer.BACKGROUND);
            this.livesArr.remove(this.livesArr.size()-1);
        }

        if ((this.livesCounter.value() > this.numOfLives) && !scoredLifeBonus){
            this.scoredLifeBonus = true;
            this.numOfLives++;
            while (this.livesCounter.value() != this.numOfLives) {
                this.livesCounter.decrement();
            }

            textLives.setString(Integer.toString(this.livesCounter.value()));
            Heart newHeart = new Heart(new Vector2(TEXT_SIZE + HEART_SIZE_AND_GAP * (this.numOfLives - 1),
                    windowDimensions.y()-WALL_GAP),
                                        new Vector2(HEART_SIZE,HEART_SIZE), this.heartImage);
            this.livesArr.add(newHeart);
            this.objectCollection.addGameObject(newHeart, Layer.BACKGROUND);
        }
    }
}
