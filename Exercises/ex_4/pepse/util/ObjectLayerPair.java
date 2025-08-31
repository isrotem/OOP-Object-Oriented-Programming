package pepse.util;

import danogl.GameObject;

/**
 * Utility class for storing game objects and the layer they belong in.
 * This helps us delete and recreate objects in their appropriate
 * layers in the infinite world.
 */
public class ObjectLayerPair {
	private final GameObject gameObject;
	private final int layer;
	
	/**
	 * Each object of this class is essentially a tuple
	 * holding a game object and the layer it belongs in
	 *
	 * @param gameObject Any game object
	 * @param layer      The layer that object should exist in
	 */
	public ObjectLayerPair(GameObject gameObject, int layer) {
		this.gameObject = gameObject;
		this.layer = layer;
	}
	
	/**
	 * Getter for the game object
	 *
	 * @return Game object this pair represents
	 */
	public GameObject getObject() {
		return this.gameObject;
	}
	
	/**
	 * Getter for the layer
	 *
	 * @return Layer the game object in this pair belongs in
	 */
	public int getLayer() {
		return this.layer;
	}
}
