package com.gdx.anim;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AnimationContainer {

	AnimationType type;
	float animSpeed;
	Animation curAnim;
	TextureAtlas leftTxtr;
	TextureAtlas rightTxtr;
	TextureAtlas upTxtr;
	TextureAtlas downTxtr;
	Animation leftAnim;
	Animation rightAnim;
	Animation upAnim;
	Animation downAnim;
	
	/**
	 * Class to contain animations, this is to group animations into their corresponding directions
	 * and to link them to an animation type - this is so I can link animations to thier corresponding actions in-game
	 * pretty much as a way to group common code and to tell if a single-animation is completed and write logic around it depending on the animation type
	 * @param type - animation type enum
	 * @param animSpeed - creature's animation speed
	 */
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
	
	public void setCurAnim(Animation anim) {
		this.curAnim = anim;
	}
	
	public Animation getCurAnim() {
		return this.curAnim;
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
	
	public AnimationType getAnimType() {
		return this.type;
	}
}
