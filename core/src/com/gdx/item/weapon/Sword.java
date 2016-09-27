package com.gdx.item.weapon;

import com.gdx.anim.AnimationFactory;
import com.gdx.anim.AnimationType;
import com.gdx.base.Creature;
import com.gdx.base.Direction;
import com.gdx.base.Weapon;

public class Sword extends Weapon {
	
	public Sword(Creature weaponCreature) {
		super(weaponCreature);
	}

	@Override
	public void attack() {
		this.weaponCreature.setOverrideAnimContainer(attackingAnimContainer);
	}

	@Override
	public void initializeTextures() {
		this.animSpeed = 1/20f;
		this.attackingAnimContainer = AnimationFactory.initializeAnimationContainer("images/player/attacks/link_attack_up_pack", AnimationType.ATTACK, this.animSpeed = 1/20f);
		this.attackingAnimContainer.getAnimByDirection(Direction.LEFT).setOffsetX(-32);
		this.attackingAnimContainer.getAnimByDirection(Direction.DOWN).setOffsetX(-32);
		this.attackingAnimContainer.getAnimByDirection(Direction.DOWN).setOffsetY(-32);
	}
	
}
