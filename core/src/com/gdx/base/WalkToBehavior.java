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

	/**
	 * Walk directly towards destination, go to next behavior if the destination is reached
	 */
	@Override
	public void execute() {
		Creature creature = this.getCreature();
		creature.dx = creature.getDeltaValueByCoordinate(creature.getXOffset(), (float) this.point.x);
		creature.dy = creature.getDeltaValueByCoordinate(creature.getYOffset(), (float) this.point.y);
		if(creature.dx == 0 && creature.dy == 0) this.behaviorPattern.next();
	}
}
