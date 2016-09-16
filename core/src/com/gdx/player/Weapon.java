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
	protected float animSpeed;
	
	//TODO:Add weaponizable interface that links this class to an Item class so this doesn't have to extend drawableObject, since it really isn't a drawableObject
	/**
	 * Generic weapon class, should hold attack information
	 */
	public Weapon(Creature weaponCreature) {
		this.weaponCreature = weaponCreature;
		//default animation speed to creature's animation speed, this can be overridden at the individual weapon level
		this.animSpeed = weaponCreature.getAnimSpeed();
		initialize();
	}
	
	/**
	 * Initialize textures and animations
	 */
	public void initialize() {
		initializeTextures();
		initializeAnimations();
	}
	
	/**
	 * Load texture files
	 */
	public abstract void initializeTextures();
	
	/**
	 * Load animations given the already, loaded texture files
	 */
	public void initializeAnimations() {
		attackCreatureUp_anim = new Animation(animSpeed, attackCreatureUp_txtr.getRegions());
		attackCreatureDown_anim = new Animation(animSpeed, attackCreatureDown_txtr.getRegions());
		attackCreatureLeft_anim = new Animation(animSpeed, attackCreatureLeft_txtr.getRegions());
		attackCreatureRight_anim = new Animation(animSpeed, attackCreatureRight_txtr.getRegions());
	}
	
	/**
	 * Handle actual attack, including animations
	 */
	public abstract void attack();
	
}
