package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.util.ObjectLayerPair;
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.cloud.Cloud;
import pepse.world.cloud.Rain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;
import pepse.world.trees.Fruit;
import pepse.world.trees.Leaf;
import pepse.world.trees.Tree;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

/**
 * Main class, specialized GameManager that runs a single
 * instance of a pepse game.
 */
public class PepseGameManager extends GameManager {
	private static final float CYCLE_LENGTH = 30;
	private static final float AVATAR_INITIAL_X = 0.5f;
	private static final float ENERGY_SIZE = 80;
	private static final int LEAF_LAYER = Layer.STATIC_OBJECTS + 1;
	private static final int FRUIT_LAYER = Layer.STATIC_OBJECTS + 2;
	private static final Vector2 CLOUD_START = new Vector2(-Block.SIZE * 6, Block.SIZE * 3);
	private static final int CLOUD_LAYER = Layer.BACKGROUND + 2;
	private static final int RAIN_LAYER = Layer.BACKGROUND + 1;
	private static final int REGION_WIDTH = 7;
	private final TreeMap<Integer, Set<ObjectLayerPair>> objectsByX = new TreeMap<>();
	private int seed;
	private Avatar avatar;
	private Terrain terrain;
	private Flora flora;
	private int centerRegion;
	private int numOfRegions = 0;
	
	/**
	 * The method will be called once when a GameGUIComponent is created,
	 * and again after every invocation of windowController.resetGame().
	 *
	 * @param imageReader      Contains a single method: readImage, which reads an image from disk.
	 *                         See its documentation for help.
	 * @param soundReader      Contains a single method: readSound, which reads a wav file from
	 *                         disk. See its documentation for help.
	 * @param inputListener    Contains a single method: isKeyPressed, which returns whether
	 *                         a given key is currently pressed by the user or not. See its
	 *                         documentation.
	 * @param windowController Contains an array of helpful, self explanatory methods
	 *                         concerning the window.
	 * @see ImageReader
	 * @see SoundReader
	 * @see UserInputListener
	 * @see WindowController
	 */
	@Override
	public void initializeGame(ImageReader imageReader, SoundReader soundReader,
							   UserInputListener inputListener, WindowController windowController) {
		super.initializeGame(imageReader, soundReader, inputListener, windowController);
		
		this.seed = new Random().nextInt();
		
		GameObject sky = Sky.create(windowController.getWindowDimensions());
		gameObjects().addGameObject(sky, Layer.BACKGROUND);
		
		int minX = -REGION_WIDTH * Block.SIZE;
		int maxX = (int) windowController.getWindowDimensions().x() + REGION_WIDTH * Block.SIZE;
		
		this.terrain = new Terrain(windowController.getWindowDimensions(), this.seed);
		for (int x = minX; x < maxX; x += REGION_WIDTH * Block.SIZE) {
			this.numOfRegions++;
			createNewRegion(x);
		}
		this.centerRegion = minX + (this.numOfRegions * REGION_WIDTH * Block.SIZE) / 2;
		
		GameObject night = Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
		gameObjects().addGameObject(night, Layer.FOREGROUND);
		
		GameObject sun = Sun.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
		this.gameObjects().addGameObject(sun, Layer.BACKGROUND);
		
		GameObject sunHalo = SunHalo.create(sun);
		gameObjects().addGameObject(sunHalo, Layer.BACKGROUND + 1);
		
		this.avatar = new Avatar(Vector2.ZERO, inputListener, imageReader);
		float avatarX = windowController.getWindowDimensions().x() * AVATAR_INITIAL_X;
		Vector2 initialAvatarLocation = new Vector2(avatarX,
				terrain.getHeightAt(avatarX) - this.avatar.getDimensions().y());
		this.avatar.setTopLeftCorner(initialAvatarLocation);
		this.gameObjects().addGameObject(this.avatar);
		setCamera(new Camera(this.avatar,
				windowController.getWindowDimensions().mult(0.5f).subtract(initialAvatarLocation),
				windowController.getWindowDimensions(),
				windowController.getWindowDimensions()));
		
		TextRenderable renderable = new TextRenderable(String.valueOf(this.avatar.getMaxEnergy()));
		Energy energy = new Energy(Vector2.ZERO, Vector2.ONES.mult(ENERGY_SIZE), renderable);
		this.avatar.setEnergyUpdater(energy::update);
		this.gameObjects().addGameObject(energy, Layer.UI);
		
		this.flora = new Flora(terrain::getHeightAt, this.seed, this.avatar::addEnergy,
				this::addFruit, this::removeFruit);
		for (int x = minX; x <= maxX; x += REGION_WIDTH * Block.SIZE) {
			createTreesInRegion(x);
		}
		this.gameObjects().layers().shouldLayersCollide(Layer.DEFAULT, FRUIT_LAYER, true);
		
		Cloud cloud = new Cloud(CLOUD_START, windowController.getWindowDimensions(),
				this::addRain, this::removeRain);
		for (Block cloudBlock : cloud.getBlocks()) {
			this.gameObjects().addGameObject(cloudBlock, CLOUD_LAYER);
		}
		this.avatar.addJumpListener(cloud::createRain);
	}
	
	/**
	 * Called once per frame. Any logic is put here. Rendering, on the other hand,
	 * should only be done within 'render'.
	 * Note that the time that passes between subsequent calls to this method is not constant.
	 *
	 * @param deltaTime The time, in seconds, that passed since the last invocation
	 *                  of this method (i.e., since the last frame). This is useful
	 *                  for either accumulating the total time that passed since some
	 *                  event, or for physics integration (i.e., multiply this by
	 *                  the acceleration to get an estimate of the added velocity or
	 *                  by the velocity to get an estimate of the difference in position).
	 */
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (this.avatar.getCenter().x() < this.centerRegion) {
			for (ObjectLayerPair objectLayerPair : this.objectsByX.remove(this.objectsByX.lastKey())) {
				this.gameObjects().removeGameObject(objectLayerPair.getObject(), objectLayerPair.getLayer());
			}
			int x = this.objectsByX.firstKey() - REGION_WIDTH * Block.SIZE;
			createNewRegion(x);
			createTreesInRegion(x);
			this.centerRegion = this.objectsByX.firstKey() +
										(this.numOfRegions * REGION_WIDTH * Block.SIZE) / 2;
		} else if (this.avatar.getCenter().x() > this.centerRegion + REGION_WIDTH * Block.SIZE) {
			for (ObjectLayerPair objectLayerPair : this.objectsByX.remove(this.objectsByX.firstKey())) {
				this.gameObjects().removeGameObject(objectLayerPair.getObject(), objectLayerPair.getLayer());
			}
			int x = this.objectsByX.lastKey() + REGION_WIDTH * Block.SIZE;
			createNewRegion(x);
			createTreesInRegion(x);
			this.centerRegion = this.objectsByX.firstKey() +
										(this.numOfRegions * REGION_WIDTH * Block.SIZE) / 2;
		}
	}
	
	private void createNewRegion(int x) {
		this.objectsByX.put(x, new HashSet<>());
		for (Block block : terrain.createInRange(x, x + REGION_WIDTH * Block.SIZE)) {
			this.gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
			this.objectsByX.get(x).add(new ObjectLayerPair(block, Layer.STATIC_OBJECTS));
		}
	}
	
	private void createTreesInRegion(int x) {
		for (Tree tree : this.flora.createInRange(x, x + REGION_WIDTH * Block.SIZE)) {
			for (Block trunk : tree.getTrunk()) {
				this.gameObjects().addGameObject(trunk, Layer.STATIC_OBJECTS);
				this.objectsByX.get(x).add(new ObjectLayerPair(trunk, Layer.STATIC_OBJECTS));
			}
			for (Leaf leaf : tree.getLeaves()) {
				this.gameObjects().addGameObject(leaf, LEAF_LAYER);
				this.objectsByX.get(x).add(new ObjectLayerPair(leaf, LEAF_LAYER));
			}
			for (Fruit fruit : tree.getFruit()) {
				this.gameObjects().addGameObject(fruit, FRUIT_LAYER);
				this.objectsByX.get(x).add(new ObjectLayerPair(fruit, FRUIT_LAYER));
			}
		}
	}
	
	private void addFruit(Fruit fruit) {
		this.gameObjects().addGameObject(fruit, FRUIT_LAYER);
	}
	
	private void removeFruit(Fruit fruit) {
		this.gameObjects().removeGameObject(fruit, FRUIT_LAYER);
	}
	
	private void addRain(Rain rain) {
		this.gameObjects().addGameObject(rain, RAIN_LAYER);
	}
	
	private void removeRain(Rain rain) {
		this.gameObjects().removeGameObject(rain, RAIN_LAYER);
	}
	
	/**
	 * Main method. creates and runs a new pepse game.
	 *
	 * @param args Unused in this program
	 */
	public static void main(String[] args) {
		new PepseGameManager().run();
	}
}
