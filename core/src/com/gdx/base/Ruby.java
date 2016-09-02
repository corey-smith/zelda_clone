package com.gdx.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Ruby extends Item{

	public Ruby(float x, float y) {
		super(x, y);
		TextureAtlas txtr = new TextureAtlas(Gdx.files.internal("images/items/ruby_pack"));
		this.setTxtr(txtr);
	}
	
}
