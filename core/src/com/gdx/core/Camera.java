package com.gdx.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera extends OrthographicCamera{
	
    float cameraMaxX;
    float cameraMinX;
    float cameraMaxY;
    float cameraMinY;

	public void setCameraMinX(float cameraMinX) {
		this.cameraMinX = cameraMinX;
	}
	
	public void setCameraMaxX(float cameraMaxX) {
		this.cameraMaxX = cameraMaxX;
	}
	
	public void setCameraMinY(float cameraMinY) {
		this.cameraMinY = cameraMinY;
	}
	
	public void setCameraMaxY(float cameraMaxY) {
		this.cameraMaxY = cameraMaxY;
	}
	
	public float getCameraMinX() {
		return this.cameraMinX;
	}
	
	public float getCameraMaxX() {
		return this.cameraMaxX;
	}
	
	public float getCameraMinY() {
		return this.cameraMinY;
	}
	
	public float getCameraMaxY() {
		return this.cameraMaxY;
	}
	
	/**
	 * Get camera X at the left of camera rather than middle
	 * @return
	 */
	public float getCameraPosX() {
		return this.position.x - (Gdx.graphics.getWidth()/2);
	}
	
	/**
	 * Get camera Y at the bottom of camera rather than middle
	 * @return
	 */
	public float getCameraPosY() {
		return this.position.y - (Gdx.graphics.getHeight()/2);
	}

}
