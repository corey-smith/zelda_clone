package com.gdx.anim;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class GameAnim extends Animation {

	AnimationContainer animContainer;
	float offsetX;
	float offsetY;
	
	public GameAnim(float frameDuration, Array<? extends TextureRegion> keyFrames) {
		super(frameDuration, keyFrames);
	}
	
	public GameAnim(float frameDuration, Array<? extends TextureRegion> keyFrames, AnimationContainer animContainer) {
		super(frameDuration, keyFrames);
		this.animContainer = animContainer;
	}
	
	public AnimationContainer getAnimContainer() {
		return this.animContainer;
	}
	
	public float getOffsetX() {
		return this.offsetX;
	}
	
	public float getOffsetY() {
		return this.offsetY;
	}
	
	//TODO: Ideally we shouldn't have to set any of these manually.
	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}
	
	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}
}
