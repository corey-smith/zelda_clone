package com.gdx.item.weapon;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.base.Collision;
import com.gdx.base.DrawableObject;
import com.gdx.core.DrawableObjectQueue;
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
	public void handleCollision(Collision collision, DrawableObjectQueue drawableObjectContainer, DrawableObject collider) {
		//we don't really care if a creature runs into an object, just if the player does
		if(collider instanceof Player) {
			//remove from map, add to player's inventory
			drawableObjectContainer.remove(this);
			Player player = (Player) collider;
			player.getInventory().add(this);
		}
	}

	@Override
	//we don't actually need to do any updates on items, override parent and do nothing
	public void update() {}

}
