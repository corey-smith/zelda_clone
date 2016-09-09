package com.gdx.map;

import com.badlogic.gdx.maps.MapProperties;
import com.gdx.base.Collidable;

/**
 *	An object that can't be walked through by the player
 */
public class CollidableMapComponent extends MapObject implements Collidable {
	
	public CollidableMapComponent(float x, float y, float width, float height, MapProperties properties) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.properties = properties;
	}

	@Override
	public float getXOffset() {
		return this.y;
	}

	@Override
	public float getYOffset() {
		return this.x;
	}
}
