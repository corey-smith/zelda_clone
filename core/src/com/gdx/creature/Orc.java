package com.gdx.creature;

import com.gdx.anim.AnimationFactory;
import com.gdx.anim.AnimationType;
import com.gdx.base.Creature;
import com.gdx.creature.behavior.PursueBehavior;
import com.gdx.player.Player;

public class Orc extends Creature {
	
	/**
	 * Orc class, ideally each creature won't be hard-coded like this and creatures
	 * get loaded from files so they're easier to scale
	 * @param x
	 * @param y
	 */
	public Orc(float x, float y) {
		super(x, y);
	}
	
	@Override
	protected void initializeTextures() {
		//load texture atlases
		standingAnimContainer = AnimationFactory.initializeAnimationContainer("images/creatures/orc_stand_up_pack", AnimationType.STAND, animSpeed);
		walkingAnimContainer = AnimationFactory.initializeAnimationContainer("images/creatures/orc_walk_up_pack", AnimationType.WALK, animSpeed);
	}
	
	//TODO: make this more generic, separate out a default hostile behavior and put all of this in the creature class instead of here
	@Override
	public void handleProximity(Creature otherCreature) {
		if(otherCreature instanceof Player) {
			double playerDistance = getDistanceBetweenObject(otherCreature);
			if(playerDistance <= 75) {
				this.getBehaviorPattern().setCurBehavior(new PursueBehavior(this.getBehaviorPattern(), otherCreature));
			} else if(playerDistance > 150 && this.getCurBehavior() instanceof PursueBehavior) {
				this.setCurBehaviorPattern(this.getDefaultBehaviorPattern());
			}
		}
	}
}
