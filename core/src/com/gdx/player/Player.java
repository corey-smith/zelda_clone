package com.gdx.player;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.base.Creature;
import com.gdx.input.Input;
import com.gdx.map.CollidableObject;
import com.gdx.map.LinkableObject;

public class Player extends Creature{
	
	//input passed in from Core.java to evaluate input directly from player class
	Input input;
	
	//variables to determine when the player is walking to another map
	public boolean linking = false;
	public String toMap;
	public String fromMap;
	public String toLink;
	public String fromLink;
	
	//set input, x position, y position, offset, initialize
	public Player(Input input, float x, float y) {
		super(x, y);
		this.input = input;
		initializePlayer();
	}
	
	//method to initialize default values for non-changing variables
	public void initializePlayer() {

		movementSpeed = 4f;
		collisionBuffer = (int) (movementSpeed*2);
		
		//load texture atlases
		walkingLeft_txtr = new TextureAtlas(Gdx.files.internal("images/player/player_walk_left_pack"));
		walkingRight_txtr = new TextureAtlas(Gdx.files.internal("images/player/player_walk_right_pack"));
		walkingUp_txtr = new TextureAtlas(Gdx.files.internal("images/player/player_walk_up_pack"));
		walkingDown_txtr = new TextureAtlas(Gdx.files.internal("images/player/player_walk_down_pack"));
		standingLeft_txtr = new TextureAtlas(Gdx.files.internal("images/player/player_stand_left_pack"));
		standingRight_txtr = new TextureAtlas(Gdx.files.internal("images/player/player_stand_right_pack"));
		standingUp_txtr = new TextureAtlas(Gdx.files.internal("images/player/player_stand_up_pack"));
		standingDown_txtr = new TextureAtlas(Gdx.files.internal("images/player/player_stand_down_pack"));
		
		loadAnimations();
		
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
			this.dx = (movementSpeed*-1);
		}
		if(input.rightHeld && !input.leftHeld) {
			this.dx = movementSpeed;
			this.curAnim = walkingRight_anim;
		}
		if(input.upHeld && !input.downHeld) {
			this.dy = movementSpeed;
			this.curAnim = walkingUp_anim;
		}
		if(input.downHeld && !input.upHeld) {
			this.dy = (movementSpeed*-1);
			this.curAnim = walkingDown_anim;
		}
		handleLinks(linkableObjects);
		update(collidableObjects);
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
}
