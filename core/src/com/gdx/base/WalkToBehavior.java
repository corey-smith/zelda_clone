package com.gdx.base;

public class WalkToBehavior extends Behavior {

	float x;
	float y;
	
	/**
	 * Walk to destination, given x and y point of destination
	 * @param x
	 * @param y
	 */
	public WalkToBehavior(Creature creature, float x, float y) {
		super(creature);
		this.x = x;
		this.y = y;
	}

	/**
	 * Walk directly towards destination
	 */
	@Override
	void execute() {
		if(this.creature.getXOffset() < this.x) creature.dx = creature.getMovementSpeed();
		else if(this.creature.getXOffset() > this.x) creature.dx = (creature.getMovementSpeed() * -1);
		if(this.creature.getYOffset() < this.y) creature.dy = creature.getMovementSpeed();
		else if(this.creature.getYOffset() > this.y) creature.dy = (creature.getMovementSpeed() * -1);
	}
}
