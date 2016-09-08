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
	 * Actual method to dictate what the creature should be doing
	 * called from the update method of Creature
	 */
	abstract void execute();
}
