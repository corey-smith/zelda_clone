package com.gdx.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.player.Weapon;

public class Sword extends Weapon {
	
	public Sword(Creature creature) {
		super(creature);
	}

	@Override
	public void initialize() {
		attackCreatureUp_txtr = new TextureAtlas(Gdx.files.internal("images/player/attacks/link_attack_up_pack"));
	}

	@Override
	public void attack() {
		
	}
	
}
