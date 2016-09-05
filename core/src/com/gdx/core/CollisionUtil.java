package com.gdx.core;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.gdx.base.Collision;
import com.gdx.base.DrawableObject;

/**
 * Utility class to evaluate when collisions occur
 */
public class CollisionUtil {

	/**
	 * Evaluate a collision between two objects and return the direction of the collision
	 * @param drawableObject1
	 * @param drawableObject2
	 * @return collision
	 */
	public static Collision evaluateCollision(Rectangle r1, Rectangle r2) {
		Collision collision = null;
		
		Rectangle intersection = new Rectangle();
		Intersector.intersectRectangles(r1, r2, intersection);
		//if resulting intersection rectangle has a size, a collision has occurred
		if(intersection.getWidth() > 0 && intersection.getHeight() > 0) {
			if(intersection.x > r1.x) collision = Collision.RIGHT;
			if(intersection.y > r1.y) collision = Collision.TOP;
			if(intersection.x + intersection.width < r1.x + r1.width) collision = Collision.LEFT;
			if(intersection.y + intersection.height < r1.y + r1.height) collision = Collision.BOTTOM;
		}
		
		return collision;
	}
	
	/**
	 * Alternate way of accessing the original method using DrawableObjects rather than a Rectangle
	 * @param drawableObject1
	 * @param drawableObject2
	 * @return collision
	 */
	public static Collision evaluateCollision(DrawableObject drawableObject1, DrawableObject drawableObject2) {
		Rectangle r1 = new Rectangle(drawableObject1.getLeftBound(), drawableObject1.getBottomBound(), drawableObject1.getWidth(), drawableObject1.getHeight());
		Rectangle r2 = new Rectangle(drawableObject2.getLeftBound(), drawableObject2.getBottomBound(), drawableObject2.getWidth(), drawableObject2.getHeight());
		return evaluateCollision(r1, r2);
	}
	
}
