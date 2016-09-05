package com.gdx.base;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.core.DrawableObjectContainer;
import com.gdx.player.Player;

public class Item extends DrawableObject {
	
	//these may be better suited at the drawable object level
	protected TextureAtlas txtr;
	protected float animSpeed;
	
	public Item(float x, float y) {
		super(x, y);
		this.setCollidable(true);
	}
	
	protected void setTxtr(TextureAtlas txtr) {
		this.txtr = txtr;
		this.setCurAnim(new Animation(animSpeed, txtr.getRegions()));
	}

	@Override
	public void handleCollision(Collision collision, DrawableObjectContainer drawableObjectContainer, DrawableObject collider) {
		//we don't really care if a creature runs into an object, just if the player does
		if(collider instanceof Player) {
			//remove from map, add to player's inventory
			System.out.println(collision);
			drawableObjectContainer.remove(this);
			Player player = (Player) collider;
			player.getInventory().add(this);
		}
	}

}
