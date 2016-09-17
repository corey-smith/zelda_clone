package com.gdx.anim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AnimationFactory {

	/**
	 * Factory to build out different AnimationContainers depending on animation type
	 * This will set all of the directional animations of an animation container given one path to a texture atlas
	 * @param path - path to one of the directional texture atlases
	 * @param type - AnimationType enum
	 * @param animSpeed - animation speed as a float
	 * @return - built out AnimationContainer
	 */
	public static AnimationContainer initializeAnimationContainer(String path, AnimationType type, float animSpeed) {
		AnimationContainer animContainer = new AnimationContainer(type, animSpeed);
		//this will probably need to be expanded out to handle different animation types other than directional animations
		loadDirectionalAnimations(animContainer, path);
		return animContainer;
	}
	
	/**
	 * Handles loading all directional animations given one directional TextureAtlas
	 * @param animContainer - resulting animation container
	 * @param path - path to texture atlas
	 */
	private static void loadDirectionalAnimations(AnimationContainer animContainer, String path) {
		String replaceStr = "";
		//TODO: How can this be reworked?
		if(path.indexOf("left") > -1) replaceStr = "left";
		if(path.indexOf("right") > -1) replaceStr = "right";
		if(path.indexOf("up") > -1) replaceStr = "up";
		if(path.indexOf("down") > -1) replaceStr = "down";
		String leftPath = path.replace(replaceStr,"left");
		String rightPath = path.replace(replaceStr,"right");
		String upPath = path.replace(replaceStr,"up");
		String downPath = path.replace(replaceStr,"down");
		animContainer.setLeftTxtr(new TextureAtlas(Gdx.files.internal(leftPath)));
		animContainer.setRightTxtr(new TextureAtlas(Gdx.files.internal(rightPath)));
		animContainer.setUpTxtr(new TextureAtlas(Gdx.files.internal(upPath)));
		animContainer.setDownTxtr(new TextureAtlas(Gdx.files.internal(downPath)));
	}
	
}
