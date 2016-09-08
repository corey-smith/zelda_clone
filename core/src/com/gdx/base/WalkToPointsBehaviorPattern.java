package com.gdx.base;

import java.awt.Point;
import java.util.ArrayList;

public class WalkToPointsBehaviorPattern extends BehaviorPattern{

	ArrayList<Point> pointList;
	Point curPoint;
	
	/**
	 * Takes an arraylist of points and follows the points continuously
	 * @param creature
	 */
	public WalkToPointsBehaviorPattern(Creature creature, ArrayList<Point> pointList) {
		super(creature);
		this.pointList = pointList;
		this.curPoint = pointList.get(0);
		this.curBehavior = new WalkToBehavior(this, curPoint);
	}
	
	/**
	 * Walk to current point, create new behaviors as needed
	 */
	public void execute() {
		curBehavior.execute();
	}
	
	/**
	 * Set the current point to the next point in the point list 
	 */
	public void next() {
		//find current point
		for(int i = 0; i < pointList.size(); i++) {
			if(curPoint == pointList.get(i)) {
				if(pointList.size() - 1 > i) {
					this.curPoint = pointList.get(i + 1);
				//end of list
				} else {
					this.curPoint = pointList.get(0);
				}
				break;
			}
		}
		//set new behavior given new point
		this.setCurBehavior(new WalkToBehavior(this, this.curPoint));
	}
	
}
