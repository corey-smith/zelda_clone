package com.gdx.anim;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.base.Direction;

public class AnimationContainer {

	public AnimationType type;
	float animSpeed;
	GameAnim curAnim;
	TextureAtlas leftTxtr;
	TextureAtlas rightTxtr;
	TextureAtlas upTxtr;
	TextureAtlas downTxtr;
	GameAnim leftAnim;
	GameAnim rightAnim;
	GameAnim upAnim;
	GameAnim downAnim;
	
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
		this.leftAnim = new GameAnim(this.animSpeed, leftTxtr.getRegions(), this);
	}
	
	protected void setRightTxtr(TextureAtlas rightTxtr) {
		this.rightTxtr = rightTxtr;
		this.rightAnim = new GameAnim(this.animSpeed, rightTxtr.getRegions(), this);
	}
	
	protected void setUpTxtr(TextureAtlas upTxtr) {
		this.upTxtr = upTxtr;
		this.upAnim = new GameAnim(this.animSpeed, upTxtr.getRegions(), this);
	}
	
	protected void setDownTxtr(TextureAtlas downTxtr) {
		this.downTxtr = downTxtr;
		this.downAnim = new GameAnim(this.animSpeed, downTxtr.getRegions(), this);
	}
	
	public GameAnim getAnimByDirection(Direction direction) {
		switch(direction) {
			case LEFT : return this.leftAnim;
			case RIGHT : return this.rightAnim;
			case UP : return this.upAnim;
			case DOWN : return this.downAnim;
		}
		return null;
	}
	
	public void setCurAnim(GameAnim anim) {
		this.curAnim = anim;
	}
	
	public GameAnim getCurAnim() {
		return this.curAnim;
	}
	
	public GameAnim getLeftAnim() {
		return this.leftAnim;
	}
	
	public GameAnim getRightAnim() {
		return this.rightAnim;
	}
	
	public GameAnim getUpAnim() {
		return this.upAnim;
	}
	
	public GameAnim getDownAnim() {
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
