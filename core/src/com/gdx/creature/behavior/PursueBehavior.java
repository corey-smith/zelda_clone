package com.gdx.creature.behavior;

import com.gdx.base.Behavior;
import com.gdx.base.BehaviorPattern;
import com.gdx.base.Creature;

public class PursueBehavior extends Behavior {

	//I don't think this is an actual word
	Creature pursuee;
	
	public PursueBehavior(BehaviorPattern behaviorPattern, Creature pursuee) {
		super(behaviorPattern);
		this.pursuee = pursuee;
	}

	/**
	 * Walk towards followee
	 */
	@Override
	public void execute() {
		Creature creature = this.getCreature();
		creature.dx = creature.getDeltaValueByCoordinate(creature.getXOffset(), pursuee.getXOffset());
		creature.dy = creature.getDeltaValueByCoordinate(creature.getYOffset(), pursuee.getYOffset());
	}
}
