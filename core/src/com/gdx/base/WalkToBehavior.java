package com.gdx.base;

import java.awt.Point;

public class WalkToBehavior extends Behavior {

	Point point;
	
	/**
	 * Walk to destination, given x and y point of destination
	 * @param x
	 * @param y
	 */
	public WalkToBehavior(BehaviorPattern behaviorPattern, Point point) {
		super(behaviorPattern);
		this.point = point;
	}
	
	public BehaviorPattern getBehaviorPattern() {
		return this.behaviorPattern;
	}
	
	/**
	 * Get creature that is employeeing the behavior
	 * @return
	 */
	private Creature getCreature() {
		return this.behaviorPattern.getCreature();
	}

	/**
	 * Walk directly towards destination, go to next behavior if the destination is reached
	 */
	@Override
	public void execute() {
		boolean xMet = false;
		boolean yMet = false;
		if(this.getCreature().getXOffset() < this.point.x) this.getCreature().dx = this.getCreature().getMovementSpeed();
		else if(this.getCreature().getXOffset() > this.point.x) this.getCreature().dx = (this.getCreature().getMovementSpeed() * -1);
		else xMet = true;
		if(this.getCreature().getYOffset() < this.point.y) this.getCreature().dy = this.getCreature().getMovementSpeed();
		else if(this.getCreature().getYOffset() > this.point.y) this.getCreature().dy = (this.getCreature().getMovementSpeed() * -1);
		else yMet = true;
		if(xMet && yMet) this.behaviorPattern.next();
	}
}
