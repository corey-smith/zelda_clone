package com.gdx.base;

import com.gdx.anim.AnimationContainer;
import com.gdx.creature.behavior.PursueBehavior;
import com.gdx.map.Map;
import com.gdx.player.Player;

/**
 * Generic Creature class, essentially any DrawableObject that can move is a creature
 */
public abstract class Creature extends DrawableObject {

	//protected Animation speed - hardcoded
	protected Float animSpeed = 2/15f;
	protected Float movementSpeed;
	
	//distance used by proximate checker, this is used to tell a creature how close the player should be before pursuing
	private float proximateDistance;
	
	//buffer in pixels for preventing collisions' alternate directions from sticking
	protected Integer collisionBuffer;
    
	//creature delta location values
	public float dx;
	public float dy;
	
	//x/y values of map bounds
	protected float mapLeft, mapRight, mapTop, mapBottom;
	
	//current behavior of creature
	public BehaviorPattern behaviorPattern = null;
	public BehaviorPattern defaultBehaviorPattern = null;
	private Behavior hostileBehavior = null;

	protected AnimationContainer standingAnimContainer;
	protected AnimationContainer walkingAnimContainer;
	
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
		this.curAnimContainer = this.standingAnimContainer;
		this.setCurAnim(this.curAnimContainer.getDownAnim());
		setMovementSpeed();
		setProximateDistance();
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
	 * Get animation speed - this may need to go to DrawableObject.java
	 * @return animSpeed
	 */
	public float getAnimSpeed() {
		return this.animSpeed;
	}
	
	/**
	 * Get proximate distance of creature
	 * @return 
	 */
	public float getProximateDistance() {
		return this.proximateDistance;
	}
	
	/**
	 * Set proximate distance
	 * @param movementSpeed
	 */
	protected void setProximateDistance(float proximateDistance) {
		this.proximateDistance = proximateDistance;
	}
	
	/**
	 * Default proximate distance of 100
	 */
	void setProximateDistance() {
		this.setProximateDistance(100f);
	}
	
	/**
	 * Set creature's behavior pattern, sets default behavior pattern if none exists
	 */
	public void setCurBehaviorPattern(BehaviorPattern behaviorPattern) {
		this.behaviorPattern = behaviorPattern;
		this.behaviorPattern.initializeBehavior();
		if(this.defaultBehaviorPattern == null) {
			this.defaultBehaviorPattern = behaviorPattern;
		}
	}
	
	/**
	 * Get creature's behavior pattern
	 * @return curBehavior
	 */
	public BehaviorPattern getBehaviorPattern() {
		return this.behaviorPattern;
	}
	
	/**
	 * Set creature's default behavior pattern
	 * @param defaultBehaviorPattern
	 */
	public void setDefaultBehaviorPattern(BehaviorPattern defaultBehaviorPattern) {
		this.defaultBehaviorPattern = defaultBehaviorPattern;
	}
	
	/**
	 * Get creature's default behavior pattern
	 * @return 
	 */
	public BehaviorPattern getDefaultBehaviorPattern() {
		return this.defaultBehaviorPattern;
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
		//set animation container
		//not moving
		if(this.dx == 0 && this.dy == 0) {
			this.curAnimContainer = this.standingAnimContainer;
		}
		//moving
		else {
			this.curAnimContainer = this.walkingAnimContainer;
		
			this.offsetX += this.dx;
			this.offsetY += this.dy;
			//handle animations
			if(dx < 0) {
				this.setCurAnim(this.curAnimContainer.getLeftAnim());
			}
			else if(dx > 0) {
				this.setCurAnim(this.curAnimContainer.getRightAnim());
			}
			if(dy > 0) {
				this.setCurAnim(this.curAnimContainer.getUpAnim());
			} else if(dy < 0) {
				this.setCurAnim(this.curAnimContainer.getDownAnim());
			}
		}
	}
	
	/**
	 * Get a dx/dy value given a starting coordinate and destination coordinate
	 * This is to handle movement speeds in a common way to prevent overstepping
	 */
	public float getDeltaValueByCoordinate(float startingCoordinate, float endingCoordinate) {
		float diff = (startingCoordinate - endingCoordinate);
		float delta = 0;
		//only move in that direction if the difference in the direction is greater than movement speed to avoid overstepping
		if(Math.abs(diff) > movementSpeed) {
			if(diff < 0) delta = movementSpeed;
			else if(diff > 0) delta = (movementSpeed * -1);
		}
		return delta;
	}
	
	/**
	 * Method to run evaluate collision with other collidable objects and stop creature from walking through them
	 * @param collidableObject - current collidable object being evaluated
	 */
	public void handleCollidableObject(Collidable collidableObject) {
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
			boolean xColl = false;
			boolean yColl = false;
			float creatureLeft = this.getLeftBound();
			float creatureRight = this.getRightBound();
			float creatureTop = this.getTopBound();
			float creatureBottom = this.getBottomBound();
			float objectLeft = collidableObject.getLeftBound();
			float objectRight = collidableObject.getRightBound();
			float objectTop = collidableObject.getTopBound();
			float objectBottom = collidableObject.getBottomBound();
			//new Y is between the tiles X bounds
			if(newOffsetX + this.width > objectLeft && newOffsetX < objectRight) xColl = true;
			//new Y is between the tile's Y bounds
			if(newOffsetY + this.height > objectBottom && newOffsetY < objectTop) yColl = true;
			//new X and Y will be somewhere within the tile's X/Y bounds
			//this if statement indicates there is a collision
			if(xColl && yColl) {
				//collision to the right - if traveling right and x value is to the left of the object's right bound and current Y value is between object's Y bounds
				if(creatureRight - collisionBuffer <= objectLeft && (creatureTop > objectBottom && creatureBottom < objectTop)) this.dx = 0;
				//collision to the left - if traveling left and x value is to the right of the object's left bound and current Y value is between object's Y bounds
				else if(creatureLeft < objectRight + collisionBuffer && (creatureTop > objectBottom && creatureBottom < objectTop)) this.dx = 0;
				//collision above - if traveling up and y value is below the object's bottom bound and current X value is between object's X bounds
				if(creatureTop - collisionBuffer <= objectBottom && (creatureRight > objectLeft && creatureLeft < objectRight)) this.dy = 0;
				//collision below - if traveling down and y value is above the object's top bound and current X value is between object's X bounds
				else if(creatureBottom < objectTop + collisionBuffer && (creatureRight > objectLeft && creatureLeft < objectRight)) this.dy = 0;
			}
		}
	}
	
	/**
	 * Check proximity of other object and handle accordingly
	 * By default see if the proximate creature is the player, and set the behavior to the hostile behavior if it is
	 */
	public void handleProximity(Creature otherCreature) {
		//currently just evaluating against the player, this can be expanded out to other creature-to-creature interactions though
		if(otherCreature instanceof Player) {
			double playerDistance = getDistanceBetweenObject(otherCreature);
			if(playerDistance <= this.proximateDistance) {
				if(this.hostileBehavior== null) this.hostileBehavior = new PursueBehavior(this.getBehaviorPattern(), otherCreature); 
				this.getBehaviorPattern().setCurBehavior(this.hostileBehavior);
			//stop following if the player is too far away, hard-coded to proximate distance * 2
			} else if(playerDistance > (this.proximateDistance*2) && this.getCurBehavior() instanceof PursueBehavior) {
				this.setCurBehaviorPattern(this.getDefaultBehaviorPattern());
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
	
	/**
	 * Handle collision, don't allow creature to walk further in the direction of the collision
	 */
	public void collide(Collision collision) {
		if(collision == Collision.TOP && this.dy > 0) this.dy = 0;
		if(collision == Collision.BOTTOM && this.dy < 0) this.dy = 0;
		if(collision == Collision.LEFT && this.dx < 0) this.dx = 0;
		if(collision == Collision.RIGHT && this.dx > 0) this.dx = 0;
	}
	
	/**
	 * load textures on a per-creature basis
	 */
	protected abstract void initializeTextures();

}
