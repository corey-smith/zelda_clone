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
	
	//x/y values of map bounds
	protected float mapLeft, mapRight, mapTop, mapBottom;
	
	public DrawableObject(float x, float y) {
		this.initX = x;
		this.initY = y;
		this.offsetX = x;
		this.offsetY = y;
	}
	
	public void setCurAnim(Animation curAnim) {
		this.curAnim = curAnim;
	}
	
	//return current animation to draw in Core.java
	public Animation getCurAnim() {
		return curAnim;
	}
	
	//get absolute player x position
	public float getInitXPos() {
		return this.initX;
	}
	
	//get absolute player y position
	public float getInitYPos() {
		return this.initY;
	}
	
	//get player x position within map
	public float getXOffset() {
		return this.offsetX;
	}
	
	//set player x position within map
	public void setXOffset(float offsetX) {
		this.offsetX = offsetX;
	}
	
	//get player x position within map
	public float getYOffset() {
		return this.offsetY;
	}
	
	//set player y position within map
	public void setYOffset(float offsetY) {
		this.offsetY = offsetY;
	}
	
	//get player width
	public float getWidth() {
		return this.width;
	}
	
	//get player height
	public float getHeight() {
		return this.height;
	}
	
	//get player right bound
	public float getRightBound() {
		return this.offsetX + this.width;
	}
	
	//get player right bound
	public float getLeftBound() {
		return this.offsetX;
	}
	
	//get player right bound
	public float getTopBound() {
		return this.offsetY + this.height;
	}
	
	//get player right bound
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
	
	//set map bounds once here on initializing map to use in updating player
	public void setMapBounds(float mapLeft, float mapRight, float mapTop, float mapBottom) {
		this.mapLeft = mapLeft;
		this.mapRight = mapRight;
		this.mapTop = mapTop;
		this.mapBottom = mapBottom;
	}
	
	//handle collision given the object type, for the top-level drawableObject, don't do anything
	public abstract void handleCollision(DrawableObjectContainer drawableObjectContainer, DrawableObject collider);
	
}
