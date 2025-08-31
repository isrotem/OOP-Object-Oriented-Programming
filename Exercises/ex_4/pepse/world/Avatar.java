package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Represents the playable avatar in the game.
 */
public class Avatar extends GameObject {
	private float energy = MAX_ENERGY;
	private Consumer<Float> energyUpdater;
	private final Set<Runnable> jumpListeners = new HashSet<>();
	private static final double IDLE_ANIMATION_SPEED = 0.12;
	private static final double RUN_ANIMATION_SPEED = 0.06;
	private static final double JUMP_ANIMATION_SPEED = 0.1;
	private static final float MOVE_ENERGY = 0.5f;
	private static final float JUMP_ENERGY = 10f;
	private static final float MAX_ENERGY = 100f;
	private static final float VELOCITY_X = 400;
	private static final float VELOCITY_Y = -600;
	private static final float GRAVITY = 600;
	private static final String IDLE_0 = "src/assets/idle_0.png";
	private static final String IDLE_1 = "src/assets/idle_1.png";
	private static final String IDLE_2 = "src/assets/idle_2.png";
	private static final String IDLE_3 = "src/assets/idle_3.png";
	private static final String JUMP_0 = "src/assets/jump_0.png";
	private static final String JUMP_1 = "src/assets/jump_1.png";
	private static final String JUMP_2 = "src/assets/jump_2.png";
	private static final String JUMP_3 = "src/assets/jump_3.png";
	private static final String RUN_0 = "src/assets/run_0.png";
	private static final String RUN_1 = "src/assets/run_1.png";
	private static final String RUN_2 = "src/assets/run_2.png";
	private static final String RUN_3 = "src/assets/run_3.png";
	private static final String RUN_4 = "src/assets/run_4.png";
	private static final String RUN_5 = "src/assets/run_5.png";
	private static final int IDLE_WIDTH = 50;
	private static final int IDLE_HEIGHT = 78;
	private static final int JUMP_WIDTH = 69;
	private static final int JUMP_HEIGHT = 73;
	private static final int RUN_WIDTH = 58;
	private static final int RUN_HEIGHT = 73;
	private final UserInputListener inputListener;
	private final AnimationRenderable idleRenderable;
	private final AnimationRenderable jumpRenderable;
	private final AnimationRenderable runRenderable;
	
	/**
	 * The avatar can be controlled by the player, and has several sprites
	 * that change based on the avatar's current state.
	 *
	 * @param topLeftCorner Coordinates to create the avatar at.
	 * @param inputListener Input listener to receive the user's input.
	 * @param imageReader   Image reader to render the avatar's sprites.
	 */
	public Avatar(Vector2 topLeftCorner, UserInputListener inputListener, ImageReader imageReader) {
		super(topLeftCorner, Vector2.ZERO, null);
		Renderable[] idleClips = new Renderable[] {
				imageReader.readImage(IDLE_0, false),
				imageReader.readImage(IDLE_1, false),
				imageReader.readImage(IDLE_2, false),
				imageReader.readImage(IDLE_3, false),
		};
		Renderable[] jumpClips = new Renderable[] {
				imageReader.readImage(JUMP_0, false),
				imageReader.readImage(JUMP_1, false),
				imageReader.readImage(JUMP_2, false),
				imageReader.readImage(JUMP_3, false)
		};
		Renderable[] runClips = new Renderable[] {
				imageReader.readImage(RUN_0, false),
				imageReader.readImage(RUN_1, false),
				imageReader.readImage(RUN_2, false),
				imageReader.readImage(RUN_3, false),
				imageReader.readImage(RUN_4, false),
				imageReader.readImage(RUN_5, false)
		};
		this.idleRenderable = new AnimationRenderable(idleClips, IDLE_ANIMATION_SPEED);
		this.jumpRenderable = new AnimationRenderable(jumpClips, JUMP_ANIMATION_SPEED);
		this.runRenderable = new AnimationRenderable(runClips, RUN_ANIMATION_SPEED);
		this.renderer().setRenderable(this.idleRenderable);
		this.setDimensions(new Vector2(IDLE_WIDTH, IDLE_HEIGHT));
		this.inputListener = inputListener;
		physics().preventIntersectionsFromDirection(Vector2.ZERO);
		transform().setAccelerationY(GRAVITY);
	}
	
	/**
	 * Should be called once per frame.
	 *
	 * @param deltaTime The time elapsed, in seconds, since the last frame. Can
	 *                  be used to determine a new position/velocity by multiplying
	 *                  this delta with the velocity/acceleration respectively
	 *                  and adding to the position/velocity:
	 *                  velocity += deltaTime*acceleration
	 *                  pos += deltaTime*velocity
	 */
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		float xVel = 0;
		if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) &&
					(this.energy >= MOVE_ENERGY || getVelocity().y() != 0)) {
			xVel -= VELOCITY_X;
		}
		if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) &&
					(this.energy >= MOVE_ENERGY || getVelocity().y() != 0)) {
			xVel += VELOCITY_X;
		}
		transform().setVelocityX(xVel);
		if (xVel != 0) { //moving horizontally
			if (getVelocity().y() == 0) { //on the ground
				this.energy -= MOVE_ENERGY;
			}
			this.renderer().setIsFlippedHorizontally(xVel < 0);
			this.renderer().setRenderable(this.runRenderable);
			this.setDimensions(new Vector2(RUN_WIDTH, RUN_HEIGHT));
		} else if (this.energy <= MAX_ENERGY && getVelocity().y() == 0 && //idle
						   ((!inputListener.isKeyPressed(KeyEvent.VK_LEFT) &&
									 !inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
									|| (inputListener.isKeyPressed(KeyEvent.VK_LEFT)
												&& inputListener.isKeyPressed(KeyEvent.VK_RIGHT)))) {
			this.energy += 1;
			this.renderer().setRenderable(this.idleRenderable);
			this.setDimensions(new Vector2(IDLE_WIDTH, IDLE_HEIGHT));
			if (this.energy > MAX_ENERGY) {
				this.energy = MAX_ENERGY;
			}
		} else if (getVelocity().y() != 0) {
			this.renderer().setRenderable(this.jumpRenderable);
			this.setDimensions(new Vector2(JUMP_WIDTH, JUMP_HEIGHT));
		}
		if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && //jump
					getVelocity().y() == 0 && this.energy >= JUMP_ENERGY) {
			transform().setVelocityY(VELOCITY_Y);
			this.energy -= JUMP_ENERGY;
			this.renderer().setRenderable(this.jumpRenderable);
			this.setDimensions(new Vector2(JUMP_WIDTH, JUMP_HEIGHT));
			for (Runnable listener : jumpListeners) {
				listener.run();
			}
		}
		
		this.energyUpdater.accept(this.energy);
	}
	
	/**
	 * Sets the means by which the avatar displays it's remaining energy.
	 *
	 * @param energyUpdater Method reference that updates the visual display of energy.
	 */
	public void setEnergyUpdater(Consumer<Float> energyUpdater) {
		this.energyUpdater = energyUpdater;
	}
	
	/**
	 * Getter for the avatar's initial maximum energy.
	 *
	 * @return Maximum amount of energy the avatar can have.
	 */
	public float getMaxEnergy() {
		return MAX_ENERGY;
	}
	
	/**
	 * Allows external objects to grant the avatar bonus energy.
	 *
	 * @param energy Amount of energy for the avatar to gain.
	 */
	public void addEnergy(float energy) {
		this.energy += energy;
		if (this.energy > MAX_ENERGY) {
			this.energy = MAX_ENERGY;
		}
		this.energyUpdater.accept(this.energy);
	}
	
	/**
	 * Adds a method to call every time the avatar jumps.
	 *
	 * @param listener Method reference to call on each jump.
	 */
	public void addJumpListener(Runnable listener) {
		this.jumpListeners.add(listener);
	}
}
