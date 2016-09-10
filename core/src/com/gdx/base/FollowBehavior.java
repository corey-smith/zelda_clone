package com.gdx.base;

public class FollowBehavior extends Behavior {

	Creature followee;
	
	public FollowBehavior(BehaviorPattern behaviorPattern, Creature followee) {
		super(behaviorPattern);
		this.followee = followee;
	}

	/**
	 * Walk towards followee
	 */
	@Override
	public void execute() {
		Creature creature = this.getCreature();
		creature.dx = creature.getDeltaValueByCoordinate(creature.getXOffset(), followee.getXOffset());
		creature.dy = creature.getDeltaValueByCoordinate(creature.getYOffset(), followee.getYOffset());
	}
}
