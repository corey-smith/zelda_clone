package com.gdx.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.base.Creature;

public abstract class Weapon {

	//Creature owner of weapon
	protected Creature weaponCreature;
	//creature's animation while performing attack, should be single loop of animation frames
	//attack animation, only pertains to attacks that have animations outside of the attackingCreatureAnim
	protected Animation attackAnim;
	protected float attackDamage;
	
	//TextureAtlases, Animations
	//creature's animation while performing attack, should be single loop of animation frames
	protected TextureAtlas attackCreatureLeft_txtr;
	protected TextureAtlas attackCreatureRight_txtr;
	protected TextureAtlas attackCreatureUp_txtr;
	protected TextureAtlas attackCreatureDown_txtr;
	protected Animation attackCreatureLeft_anim;
	protected Animation attackCreatureRight_anim;
	protected Animation attackCreatureUp_anim;
	protected Animation attackCreatureDown_anim;
	
	//TODO:Add weaponizable interface that links this class to an Item class so this doesn't have to extend drawableObject, since it really isn't a drawableObject
	/**
	 * Generic weapon class, should hold attack information
	 */
	public Weapon(Creature weaponCreature) {
		this.weaponCreature = weaponCreature;
		initialize();
	}
	
	/**
	 * Should intitialize animations and damage
	 * @return 
	 */
	public abstract void initialize();
	
	/**
	 * Handle actual attack, including animations
	 */
	public abstract void attack();
	
}
