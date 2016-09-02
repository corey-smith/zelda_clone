package com.gdx.base;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Item extends DrawableObject{
	
	//these may be better suited at the drawable object level
	protected TextureAtlas txtr;
	protected float animSpeed;
	
	public Item(float x, float y) {
		super(x, y);
	}
	
	protected void setTxtr(TextureAtlas txtr) {
		this.txtr = txtr;
		this.curAnim = new Animation(animSpeed, txtr.getRegions());
	}

}
