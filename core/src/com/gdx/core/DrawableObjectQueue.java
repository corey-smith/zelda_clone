package com.gdx.core;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.map.LinkableObject;
import com.gdx.map.Map;
import com.gdx.base.Collidable;
import com.gdx.base.Creature;
import com.gdx.base.DrawableObject;
import com.gdx.player.Player;

public class DrawableObjectQueue {

	SpriteBatch batch;
	Player player;
	Map map;
	Camera camera;
	ArrayList<DrawableObject> drawableObjects;
	Iterator<DrawableObject> drawableObjectIter;
	ArrayList<Collidable> collidableObjects;
	ArrayList<LinkableObject> linkableObjects;
	
	/**
	 * Queue to hold all of the current drawable objects and coordinate between them
	 * Generally used to just loop through and update/draw all drawable objects
	 * @param batch
	 * @param player
	 * @param curMap
	 * @param camera
	 */
	public DrawableObjectQueue(SpriteBatch batch, Player player, Map curMap, Camera camera, ArrayList<Collidable> collidableObjects, ArrayList<LinkableObject> linkableObjects) {
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
			drawableObject.draw(batch);
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
			if(drawableObject instanceof Player) ((Player) drawableObject).updatePlayer(linkableObjects);
			if(drawableObject instanceof Creature) updateDrawableCreature((Creature) drawableObject);
			drawableObject.update();
		}
	}
	
	/**
	 * Update logic specific to creatures
	 * mainly handling collidable stuff and behaviors
	 */
	private void updateDrawableCreature(Creature creature) {
		for(DrawableObject drawableObject : this.drawableObjects) {
			if(!drawableObject.equals(creature)) {
				creature.handleProximity((Creature) drawableObject);
			}
		}
		if(creature.getBehaviorPattern() != null) creature.getCurBehavior().execute();
		//evaluate collisions with other objects
		for(Collidable collidable : collidableObjects) {
			if(!collidable.equals(creature)) creature.handleCollidableObject(collidable);
		}
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
		if(drawableObject.isCollidable()) collidableObjects.add(drawableObject);
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
