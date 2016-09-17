package com.gdx.base;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.anim.AnimationContainer;

public abstract class Weapon {

	//Creature owner of weapon
	protected Creature weaponCreature;
	//creature's animation while performing attack, should be single loop of animation frames
	//attack animation, only pertains to attacks that have animations outside of the attackingCreatureAnim
	protected Animation attackAnim;
	protected float attackDamage;
	
	//Animation container, creature's animation while performing attack, should be single loop of animation frames
	protected AnimationContainer attackingAnimContainer;
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
	}
	
	/**
	 * Load texture files
	 */
	public abstract void initializeTextures();
	
	/**
	 * Handle actual attack, including animations
	 */
	public abstract void attack();
	
}
