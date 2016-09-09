package com.gdx.base;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.map.Map;
import com.gdx.core.DrawableObjectContainer;
import com.gdx.map.CollidableObject;

/**
 * Generic Creature class, essentially any DrawableObject that can move is a creature
 */
public abstract class Creature extends DrawableObject{

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
	
	//current behavior of creature
	public BehaviorPattern behaviorPattern = null;

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
	
	/**
	 * Creature constructor, takes the x and y coordinates of creature
	 * @param x
	 * @param y
	 */
	public Creature(float x, float y) {
		super(x, y);
		this.collidable = true;
		initialize();
	}
	
	/**
	 * Initialize creature
	 */
	private void initialize() {
		initializeTextures();
		loadAnimations();
		setCurAnim(standingRight_anim);
		setMovementSpeed();
	}
	
	/**
	 * Generic method to load animations - assumes the different walking/standing textures have been instantiated
	 */
	public void loadAnimations() {
		walkingLeft_anim = new Animation(animSpeed, walkingLeft_txtr.getRegions());
		walkingRight_anim = new Animation(animSpeed, walkingRight_txtr.getRegions());
		walkingUp_anim = new Animation(animSpeed, walkingUp_txtr.getRegions());
		walkingDown_anim = new Animation(animSpeed, walkingDown_txtr.getRegions());
		standingLeft_anim = new Animation(animSpeed, standingLeft_txtr.getRegions());
		standingRight_anim = new Animation(animSpeed, standingRight_txtr.getRegions());
		standingUp_anim = new Animation(animSpeed, standingUp_txtr.getRegions());
		standingDown_anim = new Animation(animSpeed, standingDown_txtr.getRegions());
		//default a starting animation
		this.setCurAnim(standingDown_anim);
	}
	
	/**
	 * Set movement speed
	 * @param movementSpeed
	 */
	protected void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
		collisionBuffer = (int) (movementSpeed*2);
	}
	
	/**
	 * Default movement speed of 1
	 */
	void setMovementSpeed() {
		this.setMovementSpeed(1f);
	}
	
	/**
	 * Get movement speed of creature
	 * @return 
	 */
	public float getMovementSpeed() {
		return this.movementSpeed;
	}
	
	/**
	 * Set creature's behavior pattern
	 */
	public void setCurBehaviorPattern(BehaviorPattern behaviorPattern) {
		this.behaviorPattern = behaviorPattern;
	}
	
	/**
	 * Get creature's behavior pattern
	 * @return curBehavior
	 */
	public BehaviorPattern getBehaviorPattern() {
		return this.behaviorPattern;
	}
	
	/**
	 * Get creature's current behavior
	 * @return curBehavior
	 */
	public Behavior getCurBehavior() {
		Behavior tmpBehavior = null;
		if(this.behaviorPattern != null) tmpBehavior = this.behaviorPattern.getCurBehavior();
		return tmpBehavior;
	}
	
	/**
	 * Main loop logic for updating a creature
	 * executes the current behavior and handles animation and collidables
	 */
	@Override
	public void update() {
		//move creature
		this.offsetX += this.dx;
		this.offsetY += this.dy;
		//handle animations
		if(dx < 0) {
			this.curAnim = walkingLeft_anim;
		}
		else if(dx > 0) {
			this.curAnim = walkingRight_anim;
		}
		if(dy > 0) {
			this.curAnim = walkingUp_anim;
		} else if(dy < 0) {
			this.curAnim = walkingDown_anim;
		}
	}
	
	/**
	 * TODO:Replace this with the new collision util method
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
	
	/**
	 * set map bounds once here on initializing map to use in updating player
	 * @param curMap
	 */
	public void setMapBounds(Map curMap) {
		this.mapLeft = curMap.getLeftBound();
		this.mapRight = curMap.getRightBound();
		this.mapTop = curMap.getTopBound();
		this.mapBottom = curMap.getBottomBound();
	}

	//TODO: handle creature collisions here
	@Override
	public void handleCollision(Collision collision, DrawableObjectContainer drawableObjectContainer, DrawableObject collider) {
	
	}
	
	/**
	 * Handle collision, don't allow creature to walk further in the direction of the collision
	 */
	public void collide(Collision collision) {
		if((collision == Collision.TOP || collision == Collision.TOPLEFT || collision == Collision.TOPRIGHT) && this.dy > 0) this.dy = 0;
		if((collision == Collision.BOTTOM || collision == Collision.BOTTOMLEFT || collision == Collision.BOTTOMRIGHT) && this.dy < 0) this.dy = 0;
		if((collision == Collision.LEFT || collision == Collision.TOPLEFT || collision == Collision.BOTTOMLEFT) && this.dx < 0) this.dx = 0;
		if((collision == Collision.RIGHT || collision == Collision.TOPRIGHT || collision == Collision.BOTTOMRIGHT) && this.dx > 0) this.dx = 0;
	}
	
	/**
	 * load textures on a per-creature basis
	 */
	protected abstract void initializeTextures();
}
