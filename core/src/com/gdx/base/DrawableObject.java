package com.gdx.base;

import com.badlogic.gdx.graphics.g2d.Animation;

public class DrawableObject {

	//current animation of object
	public Animation curAnim;
	
	//initial X/Y locations, this should match absolute location except when on edge of map
	public float initX;
	public float initY;
	//location within the map
	public float offsetX;
	public float offsetY;
	//location to draw object at
	public float drawPosX;
	public float drawPosY;
	//width/height
	public float width;
	public float height;
	
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
	
	public void setDrawPosX(float leftBound, float rightBound) {
		//if offset is < where player was initialized, player is against the left of the map
		float tmpX = Math.min(this.initX, leftBound + this.offsetX);
		//if difference between right bound and cur pos < initX, player is at the right of the screen
		//find the difference between right bound and offsetX to use in finding the difference between initX and player, then add to initX
		if((rightBound - this.offsetX) < this.initX) tmpX = this.initX + (this.initX - (rightBound - this.offsetX));
		
		this.drawPosX = tmpX;
	}
	
	public void setDrawPosY(float topBound, float bottomBound) {
		//if offset is < where player was initialized, player is against the bottom of the map
		float tmpY = Math.min(this.initY, bottomBound + this.offsetY);
		//if difference between top bound and cur pos < initY, player is at the top of the screen
		//find the difference between top bound and offsetY to use in finding the difference between initY and player, then add to initY
		if((topBound - this.offsetY) < this.initY) tmpY = this.initY + (this.initY - (topBound - this.offsetY));
		this.drawPosY = tmpY;
	}
	
	public float getXDrawPos() {
		return drawPosX;
	}
	
	public float getYDrawPos() {
		return drawPosY;
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
	
	//set map bounds once here on initializing map to use in updating player
	public void setMapBounds(float mapLeft, float mapRight, float mapTop, float mapBottom) {
		this.mapLeft = mapLeft;
		this.mapRight = mapRight;
		this.mapTop = mapTop;
		this.mapBottom = mapBottom;
	}
	
}
