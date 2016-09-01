package com.gdx.base;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * generic creature class for all drawable/collidable game entities
 */
public class Creature {
	
	//texture atlases for each animation
	TextureAtlas walkingLeft_txtr;
	TextureAtlas walkingRight_txtr;
	TextureAtlas walkingDown_txtr;
	TextureAtlas walkingUp_txtr;
	
	//animation classes for each animation
	Animation walkingLeft_anim;
	Animation walkingRight_anim;
	Animation walkingDown_anim;
	Animation walkingUp_anim;
	
	//current animation of the creature
	Animation curAnim;
	
	//creature location
	float x;
	float y;
	//creature delta location values
	float dx;
	float dy;
	//width/height
	float width;
	float height;
	
	public boolean isCollidable;
	
	public Creature() {
		
	}
	
	public void setCurAnim(Animation curAnim) {
		this.curAnim = curAnim;
	}
	
	//return current animation to draw in Core.java
	public Animation getCurAnim() {
		return curAnim;
	}
	
	//get player x position
	public float getXPos() {
		return this.x;
	}
	
	//get player y position
	public float getYPos() {
		return this.y;
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
		return this.x + this.width;
	}
	
	//get player right bound
	public float getLeftBound() {
		return this.x;
	}
	
	//get player right bound
	public float getTopBound() {
		return this.y + this.height;
	}
	
	//get player right bound
	public float getBottomBound() {
		return this.y;
	}
	
	public void setCollidable(boolean isCollidable) {
		this.isCollidable = isCollidable;
	}
	
	public boolean isCollidable() {
		return this.isCollidable;
	}

}
