package com.gdx.base;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.gdx.core.DrawableObjectContainer;

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
	
	public DrawableObject(float x, float y) {
		this.initX = x;
		this.initY = y;
		this.offsetX = x;
		this.offsetY = y;
	}
	
	public void setCurAnim(Animation curAnim) {
		this.curAnim = curAnim;
		this.setWidth(curAnim.getKeyFrame(0).getRegionWidth());
		this.setHeight(curAnim.getKeyFrame(0).getRegionHeight());
	}
	
	//return current animation to draw in Core.java
	public Animation getCurAnim() {
		return curAnim;
	}
	
	//get starting x position
	public float getInitXPos() {
		return this.initX;
	}
	
	//get starting y position
	public float getInitYPos() {
		return this.initY;
	}
	
	//get x position within map
	public float getXOffset() {
		return this.offsetX;
	}
	
	//set x position within map
	public void setXOffset(float offsetX) {
		this.offsetX = offsetX;
	}
	
	//get y position within map
	public float getYOffset() {
		return this.offsetY;
	}
	
	//set y position within map
	public void setYOffset(float offsetY) {
		this.offsetY = offsetY;
	}
	
	//get width
	public float getWidth() {
		return this.width;
	}
	
	//get height
	public float getHeight() {
		return this.height;
	}
	
	//set object width
	public void setWidth(float width) {
		this.width = width;
	}
	
	//set object height
	public void setHeight(float height) {
		this.height = height;
	}
	
	//get right bound
	public float getRightBound() {
		return this.offsetX + this.width;
	}
	
	//get left bound
	public float getLeftBound() {
		return this.offsetX;
	}
	
	//get top bound
	public float getTopBound() {
		return this.offsetY + this.height;
	}
	
	//get bottom bound
	public float getBottomBound() {
		return this.offsetY;
	}
	
	//set collidable boolean
	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}
	
	//get collidable boolean
	public boolean isCollidable() {
		return this.collidable;
	}
	
	//handle collision given the object type, for the top-level drawableObject, don't do anything
	public abstract void handleCollision(Collision collision, DrawableObjectContainer drawableObjectContainer, DrawableObject collider);
	
}
