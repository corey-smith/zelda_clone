package com.gdx.anim;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AnimationContainer {

	AnimationType type;
	float animSpeed;
	TextureAtlas leftTxtr;
	TextureAtlas rightTxtr;
	TextureAtlas upTxtr;
	TextureAtlas downTxtr;
	Animation leftAnim;
	Animation rightAnim;
	Animation upAnim;
	Animation downAnim;
	
	public AnimationContainer(AnimationType type, float animSpeed) {
		this.type = type;
		this.animSpeed = animSpeed;
	}
	
	protected void setAnimSpeed(float animSpeed) {
		this.animSpeed = animSpeed;
	}
	
	protected void setLeftTxtr(TextureAtlas leftTxtr) {
		this.leftTxtr = leftTxtr;
		this.leftAnim = new Animation(this.animSpeed, leftTxtr.getRegions());
	}
	
	protected void setRightTxtr(TextureAtlas rightTxtr) {
		this.rightTxtr = rightTxtr;
		this.rightAnim = new Animation(this.animSpeed, rightTxtr.getRegions());
	}
	
	protected void setUpTxtr(TextureAtlas upTxtr) {
		this.upTxtr = upTxtr;
		this.upAnim = new Animation(this.animSpeed, upTxtr.getRegions());
	}
	
	protected void setDownTxtr(TextureAtlas downTxtr) {
		this.downTxtr = downTxtr;
		this.downAnim = new Animation(this.animSpeed, downTxtr.getRegions());
	}
	
	public Animation getLeftAnim() {
		return this.leftAnim;
	}
	
	public Animation getRightAnim() {
		return this.rightAnim;
	}
	
	public Animation getUpAnim() {
		return this.upAnim;
	}
	
	public Animation getDownAnim() {
		return this.downAnim;
	}
	
	public TextureAtlas getLeftTxtr() {
		return this.leftTxtr;
	}
	
	public TextureAtlas getRightTxtr() {
		return this.rightTxtr;
	}
	
	public TextureAtlas getUpTxtr() {
		return this.upTxtr;
	}
	
	public TextureAtlas getDownTxtr() {
		return this.downTxtr;
	}
	
	public float getAnimSpeed() {
		return this.animSpeed;
	}
}
