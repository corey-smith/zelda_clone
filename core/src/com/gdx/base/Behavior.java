package com.gdx.base;

public abstract class Behavior {

	Creature creature;
	
	/**
	 * Abstract class to dictate where a creature should be moving to
	 * @param creature
	 */
	public Behavior(Creature creature) {
		this.creature = creature;
	}
	
	/**
	 * Actual method to dictate what the creature should be doing
	 * called from the update method of Creature
	 */
	abstract void execute();
}
