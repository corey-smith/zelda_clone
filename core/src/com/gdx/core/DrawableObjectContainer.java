package com.gdx.core;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.map.CollidableObject;
import com.gdx.map.LinkableObject;
import com.gdx.map.Map;
import com.gdx.base.Collision;
import com.gdx.base.Creature;
import com.gdx.base.DrawableObject;
import com.gdx.player.Player;

public class DrawableObjectContainer {

	SpriteBatch batch;
	Player player;
	Map map;
	Camera camera;
	ArrayList<DrawableObject> drawableObjects;
	Iterator<DrawableObject> drawableObjectIter;
	ArrayList<CollidableObject> collidableObjects;
	ArrayList<LinkableObject> linkableObjects;
	
	/**
	 * Container to hold all of the current drawable objects and coordinate between them
	 * @param batch
	 * @param player
	 * @param curMap
	 * @param camera
	 */
	public DrawableObjectContainer(SpriteBatch batch, Player player, Map curMap, Camera camera, ArrayList<CollidableObject> collidableObjects, ArrayList<LinkableObject> linkableObjects) {
		this.batch = batch;
		this.player = player;
		this.map = curMap;
		this.camera = camera;
		this.collidableObjects = collidableObjects;
		this.linkableObjects = linkableObjects;
		drawableObjects = new ArrayList<DrawableObject>();
	}
	
	/**
	 * Handle actual drawing of all present drawable objects
	 * @param elapsedTime
	 */
	protected void drawDrawableObjects(float elapsedTime) {
		drawableObjectIter = drawableObjects.iterator();
		while(drawableObjectIter.hasNext()) {
			DrawableObject drawableObject = drawableObjectIter.next();
			batch.draw(drawableObject.getCurAnim().getKeyFrame(elapsedTime, true), drawableObject.getXOffset(), drawableObject.getYOffset());
		}
	}
	
	/**
	 * Handle other logic, currently just evaluating collisions between the player and the objects
	 * This will probably need to be expanded or reworked to handle collisions between creatures
	 */
	protected void updateDrawableObjects() {
		drawableObjectIter = drawableObjects.iterator();
		while(drawableObjectIter.hasNext()) {
			DrawableObject drawableObject = drawableObjectIter.next();
			if(drawableObject instanceof Player) ((Player) drawableObject).updatePlayer(collidableObjects, linkableObjects);
			if(drawableObject instanceof Creature) updateDrawableCreature((Creature) drawableObject);
			if(drawableObject.isCollidable()) {
				//evaluate collisions with other objects
				for(DrawableObject otherDrawableObject : drawableObjects) {
					if(otherDrawableObject.isCollidable()) {
						Collision collision = CollisionUtil.evaluateCollision(drawableObject, otherDrawableObject);
						if(collision != null) {
							//handle collision at the object level
							if(drawableObject instanceof Creature) ((Creature) drawableObject).collide(collision);
							drawableObject.handleCollision(collision, this, otherDrawableObject);
						}
					}
				}
			}
			drawableObject.update();
		}
	}
	
	/**
	 * Update logic specific to creatures
	 * mainly handling collidable stuff and behaviors
	 */
	private void updateDrawableCreature(Creature creature) {
		if(creature.getBehaviorPattern() != null) creature.getCurBehavior().execute();
		creature.handleCollidableObjects(collidableObjects);
	}
	
	/**
	 * Add objects to this container
	 * @param drawableObject
	 */
	public void add(DrawableObject drawableObject) {
		this.drawableObjects.add(drawableObject);
		if(drawableObject instanceof Creature) {
			Creature creature = (Creature) drawableObject;
			creature.setMapBounds(map);
		}
	}
	
	/**
	 * Remove objects from the container
	 * @param drawableObject
	 */
	public void remove(DrawableObject drawableObject) {
		this.drawableObjectIter.remove();
		this.drawableObjects.remove(drawableObject);
	}
	
}
