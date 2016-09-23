package com.gdx.player;

import java.util.ArrayList;
import java.util.Iterator;

import com.gdx.anim.AnimationFactory;
import com.gdx.anim.AnimationType;
import com.gdx.base.Collidable;
import com.gdx.base.Creature;
import com.gdx.base.Direction;
import com.gdx.input.Input;
import com.gdx.item.weapon.Sword;
import com.gdx.map.LinkableObject;

public class Player extends Creature {
	
	//input passed in from Core.java to evaluate input directly from player class
	Input input;
	
	//player's inventory, should only have 1
	Inventory inventory;
	
	//variables to determine when the player is walking to another map
	public boolean linking = false;
	public boolean attacking = false;
	public String toMap;
	public String fromMap;
	public String toLink;
	public String fromLink;
	
	/**
	 * Player in game, directly controlled by input, controls camera as well
	 * @param input
	 * @param x
	 * @param y
	 */
	public Player(Input input, float x, float y) {
		super(x, y);
		this.input = input;
		initializePlayer();
	}
	
	/**
	 * Handling creation of player, loading images and initializing variables
	 */
	public void initializePlayer() {
		
		this.setMovementSpeed(5f);
		this.inventory = new Inventory(this);
	}
	
	/**
	 * Load animation containers
	 */
	@Override
	protected void initializeTextures() {
		//load texture atlases
		standingAnimContainer = AnimationFactory.initializeAnimationContainer("images/player/player_stand_up_pack", AnimationType.STAND, animSpeed);
		walkingAnimContainer = AnimationFactory.initializeAnimationContainer("images/player/player_walk_up_pack", AnimationType.WALK, animSpeed);
	}
	
	/**
	 * Main game loop logic for player
	 * @param linkableObjects
	 */
	public void updatePlayer(ArrayList<LinkableObject> linkableObjects) {
		preLoop();
		//check all of the input values
		if(input.leftHeld && !input.rightHeld) {
			this.dx = (movementSpeed*-1);
		}
		if(input.rightHeld && !input.leftHeld) {
			this.dx = movementSpeed;
		}
		if(input.upHeld && !input.downHeld) {
			this.dy = movementSpeed;
		}
		if(input.downHeld && !input.upHeld) {
			this.dy = (movementSpeed*-1);
		}
		if(input.attackHeld) {
			if(!this.attacking) this.attack();
			this.attacking = true;
		} else {
			if(this.GetCurAnimConatiner().getAnimType() != AnimationType.ATTACK) this.attacking = false;
		}
		handleLinks(linkableObjects);
	}
	
	/**
	 * Method to handle attack, should start animation and actual attack itself
	 */
	public void attack() {
		Sword sword = new Sword(this);
		sword.attack();
	}
	
	//TODO: This needs to move to the creature class after AI is implemented for creatures
	/**
	 * Reset animations and delta values before actual looping logic
	 */
	public void preLoop() {
		this.dx = 0;
		this.dy = 0;
		if(this.getCurAnim() == this.curAnimContainer.getLeftAnim()) {
			this.setCurAnim(this.standingAnimContainer.getLeftAnim());
		} else if(this.getCurAnim() == this.curAnimContainer.getRightAnim()) {
			this.setCurAnim(this.standingAnimContainer.getRightAnim());
		} else if(this.getCurAnim() == this.curAnimContainer.getUpAnim()) {
			this.setCurAnim(this.standingAnimContainer.getUpAnim());
			//default to something
		} else {
			this.setCurAnim(this.standingAnimContainer.getDownAnim());
		}
	}
	
	/**
	 * TODO: Rework this to make it less messy
	 * Logic to handle links between maps
	 * @param linkableObjects
	 */
	public void handleLinks(ArrayList<LinkableObject> linkableObjects) {
		Iterator<LinkableObject> linkableObjectIter = linkableObjects.iterator();
		while(linkableObjectIter.hasNext()) {
			boolean xColl = false;
			boolean yColl = false;
			LinkableObject curObj = linkableObjectIter.next();
			//new Y is between the tiles X bounds
			if(this.getRightBound() > curObj.getLeftBound() && this.getLeftBound() < curObj.getRightBound()) {
				xColl = true;
			}
			//new Y is between the tile's Y bounds
			if(this.getTopBound() > curObj.getBottomBound() && this.getBottomBound() < curObj.getTopBound()) {
				yColl = true;
			}
			//new X and Y will be somewhere within the tile's X/Y bounds
			//this if statement indicates there is a collision
			if(xColl && yColl) {
				if(!linking && ((this.fromMap == null && this.toMap == null) || (!this.fromMap.equals(curObj.getToMap()) && !this.fromLink.equals(curObj.getToName())))) {
					this.linking = true;
					this.fromMap = curObj.getFromMap();
					this.fromLink = curObj.getFromName();
					this.toMap= curObj.getToMap();
					this.toLink = curObj.getToName();
				}
			} else {
				this.linking = false;
				this.fromMap = null;
				this.toMap = null;
				this.fromLink = null;
				this.toLink = null;
			}
		}
	}
	
	/**
	 * Get player's inventory
	 * @return Inventory
	 */
	public Inventory getInventory() {
		return this.inventory;
	}

	@Override
	public void handleCollision(ArrayList<Direction> directions, Collidable collidableObject) {
		//do nothing
	}
}
