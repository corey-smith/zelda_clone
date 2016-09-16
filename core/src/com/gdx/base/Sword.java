package com.gdx.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.player.Weapon;

public class Sword extends Weapon {
	
	public Sword(Creature weaponCreature) {
		super(weaponCreature);
	}

	@Override
	public void attack() {
		this.weaponCreature.setOverrideAnim(attackCreatureUp_anim);
	}

	@Override
	public void initializeTextures() {
		this.animSpeed = 1/20f;
		attackCreatureUp_txtr = new TextureAtlas(Gdx.files.internal("images/player/attacks/link_attack_up_pack"));
		attackCreatureDown_txtr = new TextureAtlas(Gdx.files.internal("images/player/attacks/link_attack_down_pack"));
		attackCreatureLeft_txtr = new TextureAtlas(Gdx.files.internal("images/player/attacks/link_attack_left_pack"));
		attackCreatureRight_txtr = new TextureAtlas(Gdx.files.internal("images/player/attacks/link_attack_right_pack"));
	}
	
}
