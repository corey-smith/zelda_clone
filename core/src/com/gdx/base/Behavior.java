package com.gdx.base;

public abstract class Behavior {

	BehaviorPattern behaviorPattern;
	
	/**
	 * Abstract class to dictate where a creature should be doing
	 * @param creature
	 */
	public Behavior(BehaviorPattern behaviorPattern) {
		this.behaviorPattern = behaviorPattern;
	}
	
	/**
	 * Get behavior pattern holding this behavior
	 * @return
	 */
	public BehaviorPattern getBehaviorPattern() {
		return this.behaviorPattern;
	}
	
	/**
	 * Get creature that is employeeing the behavior
	 * @return
	 */
	public Creature getCreature() {
		return this.behaviorPattern.getCreature();
	}
	
	/**
	 * Actual method to dictate what the creature should be doing
	 * called from the update method of Creature
	 */
	public abstract void execute();
}
