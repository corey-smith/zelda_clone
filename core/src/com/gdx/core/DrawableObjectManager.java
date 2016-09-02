package com.gdx.core;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.map.Map;
import com.gdx.base.DrawableObject;
import com.gdx.player.Player;

public class DrawableObjectManager {

	SpriteBatch batch;
	Player player;
	Map map;
	Camera camera;
	ArrayList<DrawableObject> drawableObjects;
	
	public DrawableObjectManager(SpriteBatch batch, Player player, Map curMap, Camera camera) {
		this.batch = batch;
		this.player = player;
		this.map = curMap;
		this.camera = camera;
		drawableObjects = new ArrayList<DrawableObject>();
	}
	
	protected void updateDrawableObjects() {
		for(DrawableObject drawableObject : drawableObjects) {
			drawableObject.drawPosX = Math.min(Math.max(drawableObject.getXOffset() - camera.position.x,drawableObject.getInitXPos()), drawableObject.getInitXPos());
			//drawableObject.drawPosX = Math.min(drawableObject.getXOffset() + (player.getInitXPos() - player.getXOffset()), drawableObject.getInitXPos());
			//drawableObject.drawPosY = Math.min(drawableObject.getYOffset() + (player.getInitYPos() - player.getYOffset()), drawableObject.getInitYPos());
		}
	}
	
	protected void drawDrawableObjects(float elapsedTime) {
		for(DrawableObject drawableObject : drawableObjects) {
			batch.draw(drawableObject.getCurAnim().getKeyFrame(elapsedTime, true), drawableObject.getXDrawPos(), drawableObject.getYDrawPos());
		}
	}
	
	protected void addDrawableObject(DrawableObject drawableObject) {
		this.drawableObjects.add(drawableObject);
	}
	
	protected void removeDrawableObject(DrawableObject drawableObject) {
		this.drawableObjects.remove(drawableObject);
	}
	
}
