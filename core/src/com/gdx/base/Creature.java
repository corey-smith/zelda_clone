package com.gdx.base;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.core.DrawableObjectContainer;
import com.gdx.map.CollidableObject;

public class Creature extends DrawableObject{

	//protected Animation speed - hardcoded
	protected Float animSpeed = 2/15f;
	protected Float movementSpeed;
	//buffer in pixels for preventing collisions' alternate directions from sticking
	protected Integer collisionBuffer;
    
	//creature delta location values
	public float dx;
	public float dy;
	
	//x/y values of map bounds
	protected float mapLeft, mapRight, mapTop, mapBottom;

	//texture atlases for each protected Animation
	protected TextureAtlas walkingLeft_txtr;
	protected TextureAtlas walkingRight_txtr;
	protected TextureAtlas walkingDown_txtr;
	protected TextureAtlas walkingUp_txtr;
	protected TextureAtlas standingLeft_txtr;
	protected TextureAtlas standingRight_txtr;
	protected TextureAtlas standingDown_txtr;
	protected TextureAtlas standingUp_txtr;
	
	//protected Animation classes for each protected Animation
	protected Animation walkingLeft_anim;
	protected Animation walkingRight_anim;
	protected Animation walkingDown_anim;
	protected Animation walkingUp_anim;
	protected Animation standingLeft_anim;
	protected Animation standingRight_anim;
	protected Animation standingDown_anim;
	protected Animation standingUp_anim;
	
	public Creature(float x, float y) {
		super(x, y);
	}
	
	public void loadAnimations() {
		walkingLeft_anim = new Animation(animSpeed, walkingLeft_txtr.getRegions());
		walkingRight_anim = new Animation(animSpeed, walkingRight_txtr.getRegions());
		walkingUp_anim = new Animation(animSpeed, walkingUp_txtr.getRegions());
		walkingDown_anim = new Animation(animSpeed, walkingDown_txtr.getRegions());
		standingLeft_anim = new Animation(animSpeed, standingLeft_txtr.getRegions());
		standingRight_anim = new Animation(animSpeed, standingRight_txtr.getRegions());
		standingUp_anim = new Animation(animSpeed, standingUp_txtr.getRegions());
		standingDown_anim = new Animation(animSpeed, standingDown_txtr.getRegions());
	}
	
	//set default loop values for movement/animation
	public void preLoop() {
		this.dx = 0;
		this.dy = 0;
		if(this.curAnim == walkingLeft_anim || this.curAnim == standingLeft_anim) { 
			this.curAnim = standingLeft_anim;
		} else if(this.curAnim == walkingRight_anim || this.curAnim == standingRight_anim) {
			this.curAnim = standingRight_anim;
		} else if(this.curAnim == walkingUp_anim || this.curAnim == standingUp_anim) {
			this.curAnim = standingUp_anim;
			//default to something
		} else {
			this.curAnim = standingDown_anim;
		}
	}
	
	public void update(ArrayList<CollidableObject> collidableObjects) {
		handleCollidableObjects(collidableObjects);
		//move player
		this.offsetX += this.dx;
		this.offsetY += this.dy;
	}
	
	/**
	 * Method to run through all the positions of the player and collidable objects on the map
	 * to prevent player from walking into collidable objects
	 * @param collidableObjects - arraylist of all collidable objects on the current map
	 */
	public void handleCollidableObjects(ArrayList<CollidableObject> collidableObjects) {
		float newOffsetX = (this.offsetX + this.dx);
		float newOffsetY = (this.offsetY + this.dy);
		//evaluate map bounds first
		if(newOffsetX < this.mapLeft || (newOffsetX + this.width) > this.mapRight) {
			dx = 0;
		}
		if(newOffsetY < this.mapBottom || (newOffsetY + this.height) > this.mapTop) {
			dy = 0;
		}
		
		//don't run through this logic if there's no movement to process
		if(dx != 0 || dy != 0) {
			//loop through collidable tiles and find all collisions
			Iterator<CollidableObject> collidableObjectIter = collidableObjects.iterator();
			while(collidableObjectIter.hasNext()) {
				boolean xColl = false;
				boolean yColl = false;
				CollidableObject curObj = collidableObjectIter.next();
				float playerLeft = this.getLeftBound();
				float playerRight = this.getRightBound();
				float playerTop = this.getTopBound();
				float playerBottom = this.getBottomBound();
				float objectLeft = curObj.getLeftBound();
				float objectRight = curObj.getRightBound();
				float objectTop = curObj.getTopBound();
				float objectBottom = curObj.getBottomBound();
				//new Y is between the tiles X bounds
				if(newOffsetX + this.width > objectLeft && newOffsetX < objectRight) {
					xColl = true;
				}
				//new Y is between the tile's Y bounds
				if(newOffsetY + this.height > objectBottom && newOffsetY < objectTop) {
					yColl = true;
				}
				//new X and Y will be somewhere within the tile's X/Y bounds
				//this if statement indicates there is a collision
				if(xColl && yColl) {
					//collision to the right - if traveling right and x value is to the left of the object's right bound and current Y value is between object's Y bounds
					if(playerRight - collisionBuffer <= objectLeft && (playerTop > objectBottom && playerBottom < objectTop)) dx = 0;
					//collision to the left - if traveling left and x value is to the right of the object's left bound and current Y value is between object's Y bounds
					else if(playerLeft < objectRight + collisionBuffer && (playerTop > objectBottom && playerBottom < objectTop)) dx = 0;
					//collision above - if traveling up and y value is below the object's bottom bound and current X value is between object's X bounds
					if(playerTop - collisionBuffer <= objectBottom && (playerRight > objectLeft && playerLeft < objectRight)) dy = 0;
					//collision below - if traveling down and y value is above the object's top bound and current X value is between object's X bounds
					else if(playerBottom < objectTop + collisionBuffer && (playerRight > objectLeft && playerLeft < objectRight)) dy = 0;
				}
			}
		}
	}
	
	//set map bounds once here on initializing map to use in updating player
	public void setMapBounds(float mapLeft, float mapRight, float mapTop, float mapBottom) {
		this.mapLeft = mapLeft;
		this.mapRight = mapRight;
		this.mapTop = mapTop;
		this.mapBottom = mapBottom;
	}

	//TODO: handle creature collisions here
	@Override
	public void handleCollision(Collision collision, DrawableObjectContainer drawableObjectContainer, DrawableObject collider) {
	
	}
}
