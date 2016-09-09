package com.gdx.base;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.gdx.core.DrawableObjectContainer;
import com.gdx.map.CollidableObject;

/**
 * Generic object class for anything that can be drawn on screen, should be creatures and items
 */
public abstract class DrawableObject {

	//current animation of object
	public Animation curAnim;
	
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
	 * Set current animation
	 * @param curAnim
	 */
	public void setCurAnim(Animation curAnim) {
		this.curAnim = curAnim;
		this.setWidth(curAnim.getKeyFrame(0).getRegionWidth());
		this.setHeight(curAnim.getKeyFrame(0).getRegionHeight());
	}
	
	/**
	 * return current animation to draw in Core.java
	 * @return curAnim
	 */
	public Animation getCurAnim() {
		return curAnim;
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
		return this.offsetX + this.width;
	}
	
	/**
	 * get left bound
	 * @return offsetX
	 */
	public float getLeftBound() {
		return this.offsetX;
	}
	
	/**
	 * get top bound
	 * @return offsetY + height
	 */
	public float getTopBound() {
		return this.offsetY + this.height;
	}
	
	/**
	 * get bottom bound
	 * @return offsetY
	 */
	public float getBottomBound() {
		return this.offsetY;
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
	 * handle collision given the object type, for the top-level drawableObject, don't do anything
	 * @param collision
	 * @param drawableObjectContainer
	 * @param collider
	 */
	public abstract void handleCollision(Collision collision, DrawableObjectContainer drawableObjectContainer, DrawableObject collider);
	
	/**
	 * game loop logic
	 */
	public abstract void update();
}
