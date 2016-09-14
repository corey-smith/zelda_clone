package com.gdx.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.player.Weapon;

public class Sword extends Weapon {
	
	public Sword(Creature weaponCreature) {
		super(weaponCreature);
	}

	@Override
	public void initialize() {
		attackCreatureUp_txtr = new TextureAtlas(Gdx.files.internal("images/player/attacks/link_attack_up_pack"));
		attackCreatureUp_anim = new Animation(this.weaponCreature.getAnimSpeed(), attackCreatureUp_txtr.getRegions());
	}

	@Override
	public void attack() {
		this.weaponCreature.setCurAnim(attackCreatureUp_anim);
	}
	
}
