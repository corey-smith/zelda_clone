package com.gdx.base;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Orc extends Creature {
	
	/**
	 * Orc class, ideally each creature won't be hard-coded like this and creatures
	 * get loaded from files so they're easier to scale
	 * @param x
	 * @param y
	 */
	public Orc(float x, float y) {
		super(x, y);
		ArrayList<Point> route = new ArrayList<Point>();
		route.add(new Point(400, 400));
		route.add(new Point(500, 400));
		WalkToPointsBehaviorPattern routeBehavior = new WalkToPointsBehaviorPattern(this, route);
		this.setCurBehaviorPattern(routeBehavior);
	}
	
	@Override
	protected void initializeTextures() {
		//load texture atlases
		walkingLeft_txtr = new TextureAtlas(Gdx.files.internal("images/creatures/orc_walk_left.atlas"));
		walkingRight_txtr = new TextureAtlas(Gdx.files.internal("images/creatures/orc_walk_right.atlas"));
		walkingUp_txtr = new TextureAtlas(Gdx.files.internal("images/creatures/orc_walk_up.atlas"));
		walkingDown_txtr = new TextureAtlas(Gdx.files.internal("images/creatures/orc_walk_down.atlas"));
		standingLeft_txtr = new TextureAtlas(Gdx.files.internal("images/creatures/orc_stand_left.atlas"));
		standingRight_txtr = new TextureAtlas(Gdx.files.internal("images/creatures/orc_stand_right.atlas"));
		standingUp_txtr = new TextureAtlas(Gdx.files.internal("images/creatures/orc_stand_up.atlas"));
		standingDown_txtr = new TextureAtlas(Gdx.files.internal("images/creatures/orc_stand_down.atlas"));
		this.loadAnimations();
	}
}
