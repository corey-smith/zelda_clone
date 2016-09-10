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
		if(this.getCreature().getXOffset() < this.followee.getXOffset()) this.getCreature().dx = this.getCreature().getMovementSpeed();
		if(this.getCreature().getXOffset() > this.followee.getXOffset()) this.getCreature().dx = (this.getCreature().getMovementSpeed() * -1);
		if(this.getCreature().getYOffset() < this.followee.getYOffset()) this.getCreature().dy = this.getCreature().getMovementSpeed();
		if(this.getCreature().getYOffset() > this.followee.getYOffset()) this.getCreature().dy = (this.getCreature().getMovementSpeed() * -1);
	}
	
}
