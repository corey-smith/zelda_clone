package com.gdx.core;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.map.Map;
import com.gdx.base.DrawableObject;
import com.gdx.player.Player;

public class DrawableObjectContainer {

	SpriteBatch batch;
	Player player;
	Map map;
	Camera camera;
	ArrayList<DrawableObject> drawableObjects;
	
	/**
	 * Container to hold all of the current drawable objects and coordinate between them
	 * @param batch
	 * @param player
	 * @param curMap
	 * @param camera
	 */
	public DrawableObjectContainer(SpriteBatch batch, Player player, Map curMap, Camera camera) {
		this.batch = batch;
		this.player = player;
		this.map = curMap;
		this.camera = camera;
		drawableObjects = new ArrayList<DrawableObject>();
	}
	
	/**
	 * Handle actual drawing of all present drawable objects
	 * @param elapsedTime
	 */
	protected void drawDrawableObjects(float elapsedTime) {
		for(DrawableObject drawableObject : drawableObjects) {
			batch.draw(drawableObject.getCurAnim().getKeyFrame(elapsedTime, true), drawableObject.getXOffset(), drawableObject.getYOffset());
		}
	}
	
	/**
	 * Handle other logic, currently just evaluating collisions between the player and the objects
	 * This will probably need to be expanded or reworked to handle collisions between creatures
	 */
	protected void updateDrawableObjects() {
		for(DrawableObject drawableObject : drawableObjects) {
			if(drawableObject.isCollidable()) {
				//see if there's a collision occurring with the player and this object
				boolean collision = evaluateCollision(drawableObject, player);
				if(collision) {
					//handle collision at the object level
					drawableObject.handleCollision(this, player);
				}
			}
		}
	}
	
	/**
	 * Evaluate a collision between two objects
	 * @param drawableObject1
	 * @param drawableObject2
	 * @return 
	 */
	private boolean evaluateCollision(DrawableObject drawableObject1, DrawableObject drawableObject2) {
		boolean collision = false;
		
		boolean horizontalCollision = false;
		boolean verticalCollision = false;
		boolean leftCollision = false;
		boolean rightCollision = false;
		boolean topCollision = false;
		boolean bottomCollision = false;
		//collision on drawableObject 1's right side, drawableObject 2's left side
		if(drawableObject1.getRightBound() > drawableObject2.getLeftBound() && drawableObject1.getRightBound() < drawableObject2.getRightBound()) {
			rightCollision = true;
		}
		//collision on drawableObject 1's left side, drawableObject 2's right side
		if(drawableObject1.getLeftBound() > drawableObject2.getLeftBound() && drawableObject1.getLeftBound() < drawableObject2.getRightBound()) {
			leftCollision = true;
		}
		//collision on drawableObject 1's top side, drawableObject 2's bottom side
		if(drawableObject1.getTopBound() > drawableObject2.getBottomBound() && drawableObject1.getTopBound() < drawableObject2.getTopBound()) {
			topCollision = true;
		}
		//collision on drawableObject 1's bottom side, drawableObject 2's top side
		if(drawableObject1.getBottomBound() > drawableObject2.getBottomBound() && drawableObject1.getBottomBound() < drawableObject2.getTopBound()) {
			bottomCollision = true;
		}
		if(rightCollision || leftCollision) horizontalCollision = true;
		if(topCollision || bottomCollision) verticalCollision = true;
		if(horizontalCollision && verticalCollision) {
			collision = true;
		}
		
		return collision;
	}
	
	/**
	 * Add objects to this container
	 * @param drawableObject
	 */
	protected void addDrawableObject(DrawableObject drawableObject) {
		this.drawableObjects.add(drawableObject);
	}
	
	/**
	 * Remove objects from the container
	 * @param drawableObject
	 */
	protected void removeDrawableObject(DrawableObject drawableObject) {
		this.drawableObjects.remove(drawableObject);
	}
	
}
