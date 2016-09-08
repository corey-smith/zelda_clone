package com.gdx.base;

public abstract class BehaviorPattern {

	Creature creature;
	Behavior curBehavior;
	
	/**
	 * Abstract class to dictate where a creature should be moving to
	 * @param creature
	 */
	public BehaviorPattern(Creature creature) {
		this.creature = creature;
	}
	
	/**
	 * Generic execution of the current behavior pattern
	 */
	public abstract void execute();
	
	/**
	 * Generic method to go to next behavior in listed behaviors 
	 */
	public abstract void next();
	
	/**
	 * Get current behavior in pattern
	 * @return curBehavior
	 */
	public Behavior getCurBehavior() {
		return this.curBehavior;
	}
	
	/**
	 * Set current behavior in pattern
	 * @param curBehavior
	 */
	public void setCurBehavior(Behavior curBehavior) {
		this.curBehavior = curBehavior;
	}
	
	/**
	 * Get creature that is employeeing the behavior
	 * @return creture
	 */
	public Creature getCreature() {
		return this.creature;
	}
	
}
