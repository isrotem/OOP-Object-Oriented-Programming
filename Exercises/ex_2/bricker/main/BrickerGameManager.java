package bricker.main;

import bricker.brick_strategies.*;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the main game manager for the Bricker game.
 * This class is responsible for initializing and managing all game objects, mechanics, and interactions.
 * It extends the GameManager class and overrides its methods for game-specific logic.
 * @see GameManager
 */
public class BrickerGameManager extends GameManager {

    // Public constants
    public static final int HEART_SIZE = 40;
    public static final String BALL_STR = "Ball";
    public static final String PUCK_BALL_STR = "PuckBall";
    public static final float BALL_SPEED = 200;
    public static final String BONUS_LIFE_STR = "BonusLifeHeart";
    public static final String EXTRA_PADDLE_STR = "ExtraPaddle";
    public static final String PADDLE_STR = "Paddle";
    public static final String BRICK_STR = "Brick";
    public static final String HEART = "Heart";

    // Private constants

    // Numbers
    private static final float BORDER_WIDTH = 5;
    private static final int GAP_BETWEEN_FRAME_TO_BRICK = 5;
    private static final int GAP_BETWEEN_BRICK_TO_BRICK = 1;
    private static final int BRICKS_NUMBER_IN_ROW = 8;
    private static final int NUMBER_OF_ROWS_OF_BRICKS = 5;
    private static final int BRICK_HEIGHT = 15;
    private static final int NUM_OF_LIVES_INITIAL = 3;
    private static final int RANGE_NORMAL_OR_UNIQUE = 2;
    private static final int MAXIMAL_UNIQUE_STRATEGIES = 3;
    private static final int MAXIMAL_TURBO_COLLISIONS = 6;
    private static final int BALL_SIZE = 50;
    private static final float MIDDLE_FACTOR = 0.5F;
    private static final int HALF = 2;
    private static final int DOWN_GAP = 20;
    private static final int NO_MORE_BRICKS = 0;
    private static final float PADDLE_X = 150;
    private static final float PADDLE_Y = 15;
    private static final float REGULAR_TO_TURBO_FACTOR = 1.4F;
    private static final float TURBO_TO_REGULAR_FACTOR = (float) 5 / 7;
    private static final int SCREEN_X = 700;
    private static final int SCREEN_Y = 500;
    private static final int NUM_OF_SPECIAL_BEHAVIORS = 5;
    private static final int DOUBLE_BEHAVIOR = 4;
    private static final int NUM_OF_PUCK_BALLS = 2;
    private static final float PUCK_BALL_FACTOR = 0.75F;

    // Messages/Strings
    private static final String GAME_OVER_LOSE = "You lose! Game Over!";
    private static final String GAME_OVER_WIN = "You won! Game Over!";
    private static final String EMPTY_STR = "";
    private static final String PLAY_AGAIN_MESSAGE = " Play again?";
    private static final String NAME_OF_GAME = "Bricker Game";
    private static final String REGULAR_BALL_STR = "regular";
    private static final String TURBO_BALL_STR = "turbo";

    // Image paths
    private static final String BALL_IMG_PATH = "assets/ball.png";
    private static final String HEART_IMG_PATH = "assets/heart.png";
    private static final String PUCK_BALL_IMG_PATH = "assets/mockBall.png";
    private static final String TURBO_BALL_IMG_PATH = "assets/redball.png";
    private static final String BLOP_SOUND_PATH = "assets/blop.wav";
    private static final String BRICK_IMG_PATH = "assets/brick.png";
    private static final String BACKGROUND_IMG_PATH = "assets/DARK_BG2_small.jpeg";
    private static final String PADDLE_IMG_PATH = "assets/paddle.png";


    // Fields

    //Objects from the classes we added
    private Ball ball;
    private Paddle paddle;
    private Lives lives;
    private CollisionStrategiesFactory strategiesFactory;

    //Other objects (from DanoGameLab and java libs)
    private Vector2 windowDimensions;
    private ImageReader imageReader;
    private UserInputListener inputListener;
    private WindowController windowController;
    private Counter livesCounter;
    private Counter bricksCounter;
    private Random rand;

    // Images
    private ImageRenderable paddleImage;
    private Renderable ballImage;
    private ImageRenderable puckBallImage;
    private ImageRenderable lifeImage;
    private ImageRenderable turboBallImage;
    private Sound ballCollisionSound;
    private GameObjectCollection gameObjects;

    // Primitive objects
    private boolean turboBallActive;
    private int turboTracker;


    /**
     * Constructor for the Bricker game manager.
     * @param windowTitle Title of the game window.
     * @param windowDimensions Dimensions of the game window.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    /**
     * Initializes the game by setting up images, sounds, game objects, and logic.
     * @param imageReader Utility for reading images.
     * @param soundReader Utility for reading sounds.
     * @param inputListener Utility for handling user input.
     * @param windowController Utility for managing the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener,
                               WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowDimensions = windowController.getWindowDimensions();
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowController.setTargetFramerate(80);
        this.ballCollisionSound = soundReader.readSound(BLOP_SOUND_PATH);
        this.initializeImages();
        initializeOtherFields();
        initializeObjects();

    }


    private void initializeOtherFields() {

        this.turboBallActive = false;
        this.livesCounter = new Counter(NUM_OF_LIVES_INITIAL);
        this.bricksCounter = new Counter(0);
        this.turboTracker = 0;
        this.rand = new Random();

    }

    private void initializeImages() {

        this.paddleImage = this.imageReader.readImage(PADDLE_IMG_PATH, true);
        this.ballImage = this.imageReader.readImage(BALL_IMG_PATH, true);
        this.puckBallImage = this.imageReader.readImage(PUCK_BALL_IMG_PATH, true);
        this.lifeImage = this.imageReader.readImage(HEART_IMG_PATH, true);
        this.turboBallImage = this.imageReader.readImage(TURBO_BALL_IMG_PATH, true);

    }

    private void initializeObjects() {

        this.gameObjects = gameObjects();
        this.strategiesFactory = new CollisionStrategiesFactory();
        createBackground();
        this.lives = new Lives(Vector2.ZERO, this.windowDimensions, lifeImage,
                this.gameObjects, this.livesCounter, this.windowDimensions,
                NUM_OF_LIVES_INITIAL, this.livesCounter);
        createBall();
        createPaddle();
        createWalls();
        createBricks();

    }

    /**
     * Updates the game state for each frame.
     * Called automatically by the game engine.
     * @param deltaTime Time elapsed since the last frame update.
     */
    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);
        this.lives.update(deltaTime);
        checkTurboBall();
        checkSpecialObjects();
        checkWinOrLose();
    }

    private void checkTurboBall() {

        if (turboBallActive &&
                ((ball.getCollisionCounter() - this.turboTracker) == MAXIMAL_TURBO_COLLISIONS)) {
            changeBall(REGULAR_BALL_STR);
            turboBallActive = false;
            this.turboTracker = 0;
        }
    }

    private void checkSpecialObjects() {
        for (GameObject gameObject : this.gameObjects) {
            if ((gameObject.getTag().equals(PUCK_BALL_STR) ||
                    gameObject.getTag().equals(BONUS_LIFE_STR)) &&
                    (gameObject.getCenter().y() > windowDimensions.y())) {
                this.gameObjects.removeGameObject(gameObject);
            }
        }
    }

    private void checkWinOrLose() {
        float ballHeight = ball.getCenter().y();
        String prompt = EMPTY_STR;

        if (ballHeight > windowDimensions.y()) {
            livesCounter.decrement();
            //failsCounter.decrement();
            if (livesCounter.value() == 0) {
                prompt = GAME_OVER_LOSE;
            }
            ball.setCenter(windowDimensions.mult(MIDDLE_FACTOR));
            setBallRandomVelocity();
            paddle.setCenter(new Vector2(windowDimensions.x() / HALF, windowDimensions.y() - DOWN_GAP));
        }
        if (this.bricksCounter.value() == NO_MORE_BRICKS) {
            prompt += GAME_OVER_WIN;
        }

        if (!prompt.isEmpty()) {
            prompt += PLAY_AGAIN_MESSAGE;
            if (windowController.openYesNoDialog(prompt)) {
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }

    private void createBall() {
        ball = new Ball(Vector2.ZERO, new Vector2(BALL_SIZE, BALL_SIZE), ballImage, ballCollisionSound);
        ball.setCenter(windowDimensions.mult(MIDDLE_FACTOR));
        this.gameObjects.addGameObject(ball);
        setBallRandomVelocity();
    }

    private void setBallRandomVelocity() {
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        if (this.rand.nextBoolean()) {
            ballVelX *= -1;
        }
        if (this.rand.nextBoolean()) {
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    private void createBricks() {
        Renderable brickImage = imageReader.readImage(BRICK_IMG_PATH, false);
        float availableWidth = windowDimensions.x() - 2 * GAP_BETWEEN_FRAME_TO_BRICK -
                (BRICKS_NUMBER_IN_ROW - 1) * GAP_BETWEEN_BRICK_TO_BRICK;
        float brickWidth = availableWidth / BRICKS_NUMBER_IN_ROW;

        for (int row = 0; row < NUMBER_OF_ROWS_OF_BRICKS; row++) {
            for (int col = 0; col < BRICKS_NUMBER_IN_ROW; col++) {
                float xPosition = GAP_BETWEEN_FRAME_TO_BRICK + col * (brickWidth + GAP_BETWEEN_BRICK_TO_BRICK);
                float yPosition = GAP_BETWEEN_FRAME_TO_BRICK + row * (BRICK_HEIGHT + GAP_BETWEEN_BRICK_TO_BRICK);
                createSingleBrick(xPosition, yPosition, brickWidth, brickImage);
            }
        }
    }

    private void createSingleBrick(float xPosition, float yPosition, float brickWidth, Renderable brickImage) {
        Brick brick = new Brick(new Vector2(xPosition, yPosition), new Vector2(brickWidth, BRICK_HEIGHT),
                brickImage, chooseCollisionStrategiesArray(), this.gameObjects, this.bricksCounter);
        this.bricksCounter.increment();
        this.gameObjects.addGameObject(brick, Layer.STATIC_OBJECTS);
    }

    private ArrayList<CollisionStrategy> chooseCollisionStrategiesArray() {
        int onlyBasicStrategy = this.rand.nextInt(RANGE_NORMAL_OR_UNIQUE);
        ArrayList<CollisionStrategy> collisionStrategiesArray =
                new ArrayList<>(MAXIMAL_UNIQUE_STRATEGIES);

        if (onlyBasicStrategy == 0) {
            collisionStrategiesArray.add(new BasicCollisionStrategy());
            return collisionStrategiesArray;
        }

        int counterOfAddedStrategies = 0;
        int counterOfLeftToAddStrategies = 1;

        while (counterOfAddedStrategies < MAXIMAL_UNIQUE_STRATEGIES && counterOfLeftToAddStrategies > 0) {

            int randomizedOption = this.rand.nextInt(NUM_OF_SPECIAL_BEHAVIORS);
            CollisionStrategy newStrategy = null;

            if (randomizedOption != DOUBLE_BEHAVIOR) {
                newStrategy = this.strategiesFactory.createCollisionStrategy(randomizedOption, this);
                collisionStrategiesArray.add(newStrategy);
            } else {
                counterOfLeftToAddStrategies++;
            }

            if (newStrategy != null) {
                collisionStrategiesArray.add(newStrategy);
                counterOfAddedStrategies++;
                counterOfLeftToAddStrategies--;
            }
        }

        return collisionStrategiesArray;
    }


    private void createBackground() {
        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(),
                imageReader.readImage(BACKGROUND_IMG_PATH, true));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.gameObjects.addGameObject(background, Layer.BACKGROUND);
    }

    private void createPaddle() {
        paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_X, PADDLE_Y),
                this.paddleImage, inputListener, windowDimensions);
        paddle.setCenter(new Vector2(windowDimensions.x() / HALF, windowDimensions.y() - DOWN_GAP));
        this.gameObjects.addGameObject(paddle);
    }

    private void createWalls() {
        this.gameObjects.addGameObject(new GameObject(new Vector2(windowDimensions.x() - BORDER_WIDTH, 0),
                new Vector2(BORDER_WIDTH, windowDimensions.y()), null));
        this.gameObjects.addGameObject(new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(),
                BORDER_WIDTH), null));
        this.gameObjects.addGameObject(new GameObject(Vector2.ZERO, new Vector2(BORDER_WIDTH, windowDimensions.y()),
                null));

    }

    public void handleExtraPaddleAddition() {
        // Check if there is already ExtraPaddle
        boolean isThereExtraPaddle = false;
        for (GameObject gameObject : this.gameObjects) {
            if (gameObject.getTag().equals(EXTRA_PADDLE_STR)) {
                isThereExtraPaddle = true;
            }
        }

        //If there is not extraPaddle, add
        if (!isThereExtraPaddle) {
            ExtraPaddle extraPaddle = new ExtraPaddle(new Vector2(this.windowDimensions.mult(MIDDLE_FACTOR)),
                    new Vector2(PADDLE_X, PADDLE_Y), this.paddleImage, this.inputListener,
                    this.windowDimensions, this.gameObjects);
            this.gameObjects.addGameObject(extraPaddle);
        }
    }

    /**
     * Spawns puck balls at the position of a game object when the puck collision strategy is activated.
     * @param gameObject The game object that triggers the puck ball spawning.
     */
    public void puckBallAddition(GameObject gameObject) {
        for (int i = 0; i < NUM_OF_PUCK_BALLS; i++) {
            PuckBall puck = new PuckBall(this.getMiddleOfObject(gameObject),
                    new Vector2(this.ball.getDimensions().mult(PUCK_BALL_FACTOR)),
                    this.puckBallImage, this.ballCollisionSound, this.rand);
            this.gameObjects.addGameObject(puck);
        }
    }

    private Vector2 getMiddleOfObject(GameObject gameObject) {
        float x = gameObject.getTopLeftCorner().x() + ((MIDDLE_FACTOR) * gameObject.getDimensions().x());
        float y = gameObject.getTopLeftCorner().y() + ((MIDDLE_FACTOR) * gameObject.getDimensions().y());
        return new Vector2(x, y);
    }

    /**
     * Handles the addition of a bonus life object when the collision strategy triggers it.
     * @param gameObject The game object triggering the bonus life addition.
     */
    public void bonusLifeAddition(GameObject gameObject) {
        BonusLifeHeart heart = new BonusLifeHeart(getMiddleOfObject(gameObject),
                new Vector2(HEART_SIZE, HEART_SIZE), this.lifeImage, this.gameObjects,
                this.livesCounter, this.lives);
        this.gameObjects.addGameObject(heart);
    }

    /**
     * Initiates the turbo ball state when the turbo ball collision strategy is activated.
     */
    public void HandleTurboBall() {
        if (!this.turboBallActive) {
            this.turboBallActive = true;
            changeBall(TURBO_BALL_STR);
            this.turboTracker = this.ball.getCollisionCounter();
        }
    }

    private void changeBall(String state) {
        if (state.equals(TURBO_BALL_STR)) {
            this.ball.setVelocity(this.ball.getVelocity().mult(REGULAR_TO_TURBO_FACTOR));
            this.ball.renderer().setRenderable(this.turboBallImage);
        }
        if (state.equals(REGULAR_BALL_STR)){
            this.ball.setVelocity(this.ball.getVelocity().mult(TURBO_TO_REGULAR_FACTOR));
            this.ball.renderer().setRenderable(this.ballImage);
        }
    }


    /**
     * Entry point to start the Bricker game.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
            new BrickerGameManager(NAME_OF_GAME, new Vector2(SCREEN_X, SCREEN_Y)).run();
    }
}