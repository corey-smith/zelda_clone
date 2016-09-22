package com.gdx.anim;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class GameAnim extends Animation {

	AnimationContainer animContainer;
	
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

}
