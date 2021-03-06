package com.gdx.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.anim.AnimationContainer;
import com.gdx.anim.GameAnim;
import com.gdx.core.DrawableObjectQueue;

/**
 * Generic object class for anything that can be drawn on screen, should be creatures and items
 */
public abstract class DrawableObject implements Collidable {

	//current animation container of object
	public AnimationContainer curAnimContainer;
	//single animation that overrides the current animation before returning to drawing curAnim
	public GameAnim overrideAnim;
	public AnimationContainer overrideAnimContainer;
	public float elapsedTime;
	
	//initial X/Y locations, this should match absolute location except when on edge of map
	public float initX;
	public float initY;
	//location within the map
	public float offsetX;
	public float offsetY;
	//width/height
	public float width;
	public float height;
	
	public boolean collidable;
	
	/**
	 * DrawableObject Constructor, given x and y coordinates
	 * @param x
	 * @param y
	 */
	public DrawableObject(float x, float y) {
		this.initX = x;
		this.initY = y;
		this.offsetX = x;
		this.offsetY = y;
	}
	
	/**
	 * Figure out which animation to draw and draw to batch
	 * @param batch
	 * @param elapsedTime
	 */
	public void draw(SpriteBatch batch) {
		elapsedTime += Gdx.graphics.getDeltaTime();
		this.setWidth(this.getCurRegion().getRegionWidth());
		this.setHeight(this.getCurRegion().getRegionHeight());
		if(this.overrideAnim != null) {
			batch.draw(this.getCurRegion(), this.getAnimOffsetX(), this.getAnimOffsetY());
			//if this is the last frame, reset to null
			if(overrideAnim.isAnimationFinished(this.elapsedTime)) resetOverrideAnim();
		} else {
			batch.draw(this.getCurRegion(), this.getAnimOffsetX(), this.getAnimOffsetY());
		}
	}
	
	/**
	 * Set current animation container, this is a common place to figure out relevant directions for creatures
	 * This should only be called for creatures
	 * @param curAnimContainer
	 */
	public void setCurAnimContainer(AnimationContainer curAnimContainer) {
		Creature creature = (Creature) this;
		this.setCurAnim(curAnimContainer.getAnimByDirection(creature.getDirection()));
	}
	
	/**
	 * Set current animation
	 * @param curAnim
	 */
	public void setCurAnim(GameAnim curAnim) {
		this.curAnimContainer = curAnim.getAnimContainer();
		this.curAnimContainer.setCurAnim(curAnim);
	}
	
	/**
	 * return current animation to draw in Core.java
	 * @return curAnim
	 */
	public GameAnim getCurAnim() {
		GameAnim returnAnim = null;
		if(this.overrideAnim != null) {
			returnAnim = this.overrideAnimContainer.getCurAnim();
		} else {
			returnAnim = this.curAnimContainer.getCurAnim();
		}
		return returnAnim;
	}
	
	/**
	 * Get current animation region from the animation and the elapsed time
	 * This assumes we're always looping, anywhere we shouldn't be looping should handle it manually
	 * @return - current region
	 */
	public TextureRegion getCurRegion() {
		return this.getCurAnim().getKeyFrame(elapsedTime, true);
	}
	
	/**
	 * Get current animationContainer
	 * @return curAnimContainer
	 */
	public AnimationContainer GetCurAnimConatiner() {
		AnimationContainer returnAnimContainer = null;
		if(this.overrideAnimContainer != null) {
			returnAnimContainer = this.overrideAnimContainer;
		} else {
			returnAnimContainer = this.curAnimContainer;
		}
		return returnAnimContainer;
		
	}
	
	/**
	 * Reset override animation, reset anim time
	 */
	public void resetOverrideAnim() {
		this.elapsedTime = 0;
		this.overrideAnim = null;
		this.overrideAnimContainer = null;
	}
	
	/**
	 * Set override animation container
	 * Override animation is processed once and then the object returns to it's default animation
	 * This is essentially a one-time animation
	 * Note: This currently only works for Creatures
	 * @param overrideAnim
	 */
	public void setOverrideAnimContainer(AnimationContainer overrideAnimContainer) {
		this.elapsedTime = 0;
		Creature creature = (Creature) this;
		this.overrideAnimContainer = overrideAnimContainer;
		this.overrideAnim = overrideAnimContainer.getAnimByDirection(creature.curDirection);
		this.overrideAnimContainer.setCurAnim(this.overrideAnim);
	}
	
	/**
	 * get starting x position
	 * @return initX
	 */
	public float getInitXPos() {
		return this.initX;
	}
	
	/**
	 * get starting y position
	 * @return initY
	 */
	public float getInitYPos() {
		return this.initY;
	}
	
	/**
	 * Get center x position of the drawable object
	 * @return xCenter
	 */
	public float getXCenter() {
		float xCenter = this.getXOffset() + this.width/2;
		return xCenter;
	}
	
	/**
	 * Get center y position of the drawable object
	 * @return yCenter
	 */
	public float getYCenter() {
		float yCenter = this.getYOffset() + this.height/2;
		return yCenter;
	}
	
	/**
	 * get x position within map
	 * @return offsetX
	 */
	public float getXOffset() {
		return this.offsetX;
	}
	
	/**
	 * set x position within map
	 * @param offsetX
	 */
	public void setXOffset(float offsetX) {
		this.offsetX = offsetX;
	}
	
	/**
	 * get y position within map
	 * @return
	 */
	public float getYOffset() {
		return this.offsetY;
	}
	
	/**
	 * set y position within map
	 * @param offsetY
	 */
	public void setYOffset(float offsetY) {
		this.offsetY = offsetY;
	}
	
	/**
	 * Get animation's offset, account for offsets in different animations
	 * @return - the objects X offset plus the X offset of the animation
	 */
	public float getAnimOffsetX() {
		return this.getXOffset() + this.getCurAnim().getOffsetX();
	}
	
	/**
	 * Get animation's offset, account for offsets in different animations
	 * @return - the objects X offset plus the X offset of the animation
	 */
	public float getAnimOffsetY() {
		return this.getYOffset() + this.getCurAnim().getOffsetY();
	}
	
	/**
	 * get width
	 * @return width
	 */
	public float getWidth() {
		return this.width;
	}
	
	/**
	 * get height
	 * @return height
	 */
	public float getHeight() {
		return this.height;
	}
	
	/**
	 * set object width
	 * @param width
	 */
	public void setWidth(float width) {
		this.width = width;
	}
	
	/**
	 * set object height
	 * @param height
	 */
	public void setHeight(float height) {
		this.height = height;
	}
	
	/**
	 * get right bound
	 * @return offsetX + width
	 */
	public float getRightBound() {
		return this.getXOffset() + this.getCurRegion().getRegionWidth();
	}
	
	/**
	 * get left bound
	 * @return offsetX + animation offsetX
	 */
	public float getLeftBound() {
		return this.getAnimOffsetX();
	}
	
	/**
	 * get top bound
	 * @return offsetY + height
	 */
	public float getTopBound() {
		return this.getAnimOffsetY() + this.getCurRegion().getRegionHeight();
	}
	
	/**
	 * get bottom bound
	 * @return offsetY
	 */
	public float getBottomBound() {
		return this.getAnimOffsetY();
	}
	
	/**
	 * set collidable boolean
	 * @param collidable
	 */
	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}
	
	/**
	 * get collidable boolean
	 * @return collidable
	 */
	public boolean isCollidable() {
		return this.collidable;
	}
	
	/**
	 * TODO: Find a better place to put this
	 * Utility method to get distances between the center of this drawable object and the center of another
	 * @param drawableObject
	 * @return returnVal
	 */
	public float getDistanceBetweenObject(DrawableObject drawableObject) {
		Float returnVal = null;
		returnVal = (float) Math.sqrt(Math.pow((drawableObject.getXCenter() - this.getXCenter()),2) + Math.pow((drawableObject.getYCenter() - this.getYCenter()),2));
		return returnVal;
	}
	
	/**
	 * game loop logic
	 */
	public abstract void update();
	
	/**
	 * Handle collisions
	 * Default to nothing, override as needed
	 */
	public void handleCollision(Collision collision, DrawableObjectQueue drawableObjectContainer, DrawableObject collider) {};
}
