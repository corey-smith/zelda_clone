package com.gdx.player;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.input.Input;
import com.gdx.map.CollidableObject;
import com.gdx.map.LinkableObject;

public class Player {
	
	//input passed in from Core.java to evaluate input directly from player class
	Input input;
	
	//animation speed - hardcoded
	Float animSpeed = 2/15f;
	public final Integer WALKING_SPEED = 4;
	//buffer in pixels for preventing collisions' alternate directions from sticking
	Integer collisionBuffer = WALKING_SPEED*2;
	
	//texture atlases for each animation
	TextureAtlas walkingLeft_txtr;
	TextureAtlas walkingRight_txtr;
	TextureAtlas walkingDown_txtr;
	TextureAtlas walkingUp_txtr;
	TextureAtlas standingLeft_txtr;
	TextureAtlas standingRight_txtr;
	TextureAtlas standingDown_txtr;
	TextureAtlas standingUp_txtr;
	
	//animation classes for each animation
	Animation walkingLeft_anim;
	Animation walkingRight_anim;
	Animation walkingDown_anim;
	Animation walkingUp_anim;
	Animation standingLeft_anim;
	Animation standingRight_anim;
	Animation standingDown_anim;
	Animation standingUp_anim;
	
	//current animation of the player
	Animation curAnim;
	
	//variables to determine when the player is walking to another map
	public boolean linking = false;
	public String toMap;
	public String fromMap;
	public String toLink;
	public String fromLink;
    
	//player location
	//initial X/Y locations, this should match absolute location except when on edge of map
	public float initX;
	public float initY;
	//location within the map
	public float offsetX;
	public float offsetY;
	//location to draw player at
	public float drawPosX;
	public float drawPosY;
	//player delta location values
	public float dx;
	public float dy;
	//width/height
	float width;
	float height;
	
	//x/y values of map bounds
	float mapLeft, mapRight, mapTop, mapBottom;
	
	//set input, x position, y position, offset, initialize
	public Player(Input input, float x, float y) {
		this.input = input;
		this.initX = x;
		this.initY = y;
		this.offsetX = x;
		this.offsetY = y;
		initializePlayer();
	}
	
	//method to initialize default values for non-changing variables
	public void initializePlayer() {
		//load texture atlases
		walkingLeft_txtr = new TextureAtlas(Gdx.files.internal("images/player/player_walk_left_pack"));
		walkingRight_txtr = new TextureAtlas(Gdx.files.internal("images/player/player_walk_right_pack"));
		walkingUp_txtr = new TextureAtlas(Gdx.files.internal("images/player/player_walk_up_pack"));
		walkingDown_txtr = new TextureAtlas(Gdx.files.internal("images/player/player_walk_down_pack"));
		standingLeft_txtr = new TextureAtlas(Gdx.files.internal("images/player/player_stand_left_pack"));
		standingRight_txtr = new TextureAtlas(Gdx.files.internal("images/player/player_stand_right_pack"));
		standingUp_txtr = new TextureAtlas(Gdx.files.internal("images/player/player_stand_up_pack"));
		standingDown_txtr = new TextureAtlas(Gdx.files.internal("images/player/player_stand_down_pack"));
		
		//load animations
		walkingLeft_anim = new Animation(animSpeed, walkingLeft_txtr.getRegions());
		walkingRight_anim = new Animation(animSpeed, walkingRight_txtr.getRegions());
		walkingUp_anim = new Animation(animSpeed, walkingUp_txtr.getRegions());
		walkingDown_anim = new Animation(animSpeed, walkingDown_txtr.getRegions());
		standingLeft_anim = new Animation(animSpeed, standingLeft_txtr.getRegions());
		standingRight_anim = new Animation(animSpeed, standingRight_txtr.getRegions());
		standingUp_anim = new Animation(animSpeed, standingUp_txtr.getRegions());
		standingDown_anim = new Animation(animSpeed, standingDown_txtr.getRegions());
		
		//set width and height to the first texture or any texture map as these should all be the same size
		this.curAnim = standingRight_anim;
		this.width = standingUp_txtr.findRegion("Link").originalWidth;
		this.height = standingUp_txtr.findRegion("Link").originalHeight;
	}
	
	//player update from main game loop
	public void updatePlayer(ArrayList<CollidableObject> collidableObjects, ArrayList<LinkableObject> linkableObjects) {
		preLoop();
		//check all of the input values
		if(input.leftHeld && !input.rightHeld) {
			this.curAnim = walkingLeft_anim;
			this.dx = (WALKING_SPEED*-1);
		}
		if(input.rightHeld && !input.leftHeld) {
			this.dx = WALKING_SPEED;
			this.curAnim = walkingRight_anim;
		}
		if(input.upHeld && !input.downHeld) {
			this.dy = WALKING_SPEED;
			this.curAnim = walkingUp_anim;
		}
		if(input.downHeld && !input.upHeld) {
			this.dy = (WALKING_SPEED*-1);
			this.curAnim = walkingDown_anim;
		}
		handleCollisions(collidableObjects);
		handleLinks(linkableObjects);
		//move player
		this.offsetX += this.dx;
		this.offsetY += this.dy;
	}
	
	public void setPlayerDrawPositions(float leftBound, float rightBound, float topBound, float bottomBound) {
		this.setDrawPosX(leftBound, rightBound);
		this.setDrawPosY(topBound, bottomBound);
	}
	
	//set default loop values for movement/animation
	public void preLoop() {
		this.dx = 0;
		this.dy = 0;
		if(this.curAnim == walkingLeft_anim || this.curAnim == standingLeft_anim) { 
			this.curAnim = standingLeft_anim;
		} else if(this.curAnim == walkingRight_anim || this.curAnim == standingRight_anim) {
			this.curAnim = standingRight_anim;
		} else if(this.curAnim == walkingUp_anim || this.curAnim == standingUp_anim) {
			this.curAnim = standingUp_anim;
			//default to something
		} else {
			this.curAnim = standingDown_anim;
		}
	}
	
	/**
	 * Method to run through all the positions of the player and collidable objects on the map
	 * to prevent player from walking into collidable objects
	 * @param collidableObjects - arraylist of all collidable objects on the current map
	 */
	public void handleCollisions(ArrayList<CollidableObject> collidableObjects) {
		float newOffsetX = (this.offsetX + this.dx);
		float newOffsetY = (this.offsetY + this.dy);
		//evaluate map bounds first
		if(newOffsetX < this.mapLeft || (newOffsetX + this.width) > this.mapRight) {
			dx = 0;
		}
		if(newOffsetY < this.mapBottom || (newOffsetY + this.height) > this.mapTop) {
			dy = 0;
		}
		
		//don't run through this logic if there's no movement to process
		if(dx != 0 || dy != 0) {
			//loop through collidable tiles and find all collisions
			Iterator<CollidableObject> collidableObjectIter = collidableObjects.iterator();
			while(collidableObjectIter.hasNext()) {
				boolean xColl = false;
				boolean yColl = false;
				CollidableObject curObj = collidableObjectIter.next();
				float playerLeft = this.getLeftBound();
				float playerRight = this.getRightBound();
				float playerTop = this.getTopBound();
				float playerBottom = this.getBottomBound();
				float objectLeft = curObj.getLeftBound();
				float objectRight = curObj.getRightBound();
				float objectTop = curObj.getTopBound();
				float objectBottom = curObj.getBottomBound();
				//new Y is between the tiles X bounds
				if(newOffsetX + this.width > objectLeft && newOffsetX < objectRight) {
					xColl = true;
				}
				//new Y is between the tile's Y bounds
				if(newOffsetY + this.height > objectBottom && newOffsetY < objectTop) {
					yColl = true;
				}
				//new X and Y will be somewhere within the tile's X/Y bounds
				//this if statement indicates there is a collision
				if(xColl && yColl) {
					//collision to the right - if traveling right and x value is to the left of the object's right bound and current Y value is between object's Y bounds
					if(playerRight - collisionBuffer <= objectLeft && (playerTop > objectBottom && playerBottom < objectTop)) dx = 0;
					//collision to the left - if traveling left and x value is to the right of the object's left bound and current Y value is between object's Y bounds
					else if(playerLeft < objectRight + collisionBuffer && (playerTop > objectBottom && playerBottom < objectTop)) dx = 0;
					//collision above - if traveling up and y value is below the object's bottom bound and current X value is between object's X bounds
					if(playerTop - collisionBuffer <= objectBottom && (playerRight > objectLeft && playerLeft < objectRight)) dy = 0;
					//collision below - if traveling down and y value is above the object's top bound and current X value is between object's X bounds
					else if(playerBottom < objectTop + collisionBuffer && (playerRight > objectLeft && playerLeft < objectRight)) dy = 0;
				}
			}
		}
	}
	
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
	
	public void setCurAnim(Animation curAnim) {
		this.curAnim = curAnim;
	}
	
	//return current animation to draw in Core.java
	public Animation getCurAnim() {
		return curAnim;
	}
	
	public void setDrawPosX(float leftBound, float rightBound) {
		//if offset is < where player was initialized, player is against the left of the map
		float tmpX = Math.min(this.initX, leftBound + this.offsetX);
		//if difference between right bound and cur pos < initX, player is at the right of the screen
		//find the difference between right bound and offsetX to use in finding the difference between initX and player, then add to initX
		if((rightBound - this.offsetX) < this.initX) tmpX = this.initX + (this.initX - (rightBound - this.offsetX));
		
		this.drawPosX = tmpX;
	}
	
	public void setDrawPosY(float topBound, float bottomBound) {
		//if offset is < where player was initialized, player is against the bottom of the map
		float tmpY = Math.min(this.initY, bottomBound + this.offsetY);
		//if difference between top bound and cur pos < initY, player is at the top of the screen
		//find the difference between top bound and offsetY to use in finding the difference between initY and player, then add to initY
		if((topBound - this.offsetY) < this.initY) tmpY = this.initY + (this.initY - (topBound - this.offsetY));
		this.drawPosY = tmpY;
	}
	
	public float getXDrawPos() {
		return drawPosX;
	}
	
	public float getYDrawPos() {
		return drawPosY;
	}
	
	//get absolute player x position
	public float getInitXPos() {
		return this.initX;
	}
	
	//get absolute player y position
	public float getInitYPos() {
		return this.initY;
	}
	
	//get player x position within map
	public float getXOffset() {
		return this.offsetX;
	}
	
	//set player x position within map
	public void setXOffset(float offsetX) {
		this.offsetX = offsetX;
	}
	
	//get player x position within map
	public float getYOffset() {
		return this.offsetY;
	}
	
	//set player y position within map
	public void setYOffset(float offsetY) {
		this.offsetY = offsetY;
	}
	
	//get player width
	public float getWidth() {
		return this.width;
	}
	
	//get player height
	public float getHeight() {
		return this.height;
	}
	
	//get player right bound
	public float getRightBound() {
		return this.offsetX + this.width;
	}
	
	//get player right bound
	public float getLeftBound() {
		return this.offsetX;
	}
	
	//get player right bound
	public float getTopBound() {
		return this.offsetY + this.height;
	}
	
	//get player right bound
	public float getBottomBound() {
		return this.offsetY;
	}
	
	//set map bounds once here on initializing map to use in updating player
	public void setMapBounds(float mapLeft, float mapRight, float mapTop, float mapBottom) {
		this.mapLeft = mapLeft;
		this.mapRight = mapRight;
		this.mapTop = mapTop;
		this.mapBottom = mapBottom;
	}
}
