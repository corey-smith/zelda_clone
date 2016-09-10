package com.gdx.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.player.Player;

public class Orc extends Creature {
	
	/**
	 * Orc class, ideally each creature won't be hard-coded like this and creatures
	 * get loaded from files so they're easier to scale
	 * @param x
	 * @param y
	 */
	public Orc(float x, float y) {
		super(x, y);
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
	
	//TODO: make this more generic, separate out a default hostile behavior and put all of this in the creature class instead of here
	@Override
	public void handleProximity(Creature otherCreature) {
		if(otherCreature instanceof Player) {
			double playerDistance = getDistanceBetweenObject(otherCreature);
			if(playerDistance <= 75) {
				this.getBehaviorPattern().setCurBehavior(new FollowBehavior(this.getBehaviorPattern(), otherCreature));
			} else if(playerDistance > 150 && this.getCurBehavior() instanceof FollowBehavior) {
				this.setCurBehaviorPattern(this.getDefaultBehaviorPattern());
			}
		}
	}
}
