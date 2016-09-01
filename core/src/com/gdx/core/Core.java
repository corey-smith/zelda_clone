package com.gdx.core;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.gdx.input.Input;
import com.gdx.input.Interface;
import com.gdx.map.CollidableObject;
import com.gdx.map.Direction;
import com.gdx.map.LinkableObject;
import com.gdx.player.Player;
import com.gdx.map.Map;

public class Core extends ApplicationAdapter {
	float elapsedTime = 0;
	SpriteBatch batch;
	Texture img;
	
	//input and player objects
	Input input;
	Player player;
	
	//camera
    OrthographicCamera camera;
    float cameraMaxX;
    float cameraMinX;
    float cameraMaxY;
    float cameraMinY;
	
    //current interface for listener purposes - game, menu, etc
    Interface curInterface;
	
    //all collidable objects on the map
	ArrayList<CollidableObject> collidableObjects = new ArrayList<CollidableObject>();
	//all linkable objects on the map
	ArrayList<LinkableObject> linkableObjects = new ArrayList<LinkableObject>();
	
	//current map, renderer for maps
    Map curMap;
    TiledMapRenderer tiledMapRenderer;
    
	@Override
	public void create () {
		initializeGame();
        batch = new SpriteBatch();
	}
	
	//initialize these before the game starts
	public void initializeGame() {
		input = new Input();
		//initialize player in the middle of the screen
		player = new Player(input, (Gdx.graphics.getWidth()/2), (Gdx.graphics.getHeight()/2));
		curInterface = Interface.GAME;
		initializeCamera();
		initializeMap("testMap");
	}
	
	//initializes camera
	public void initializeCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
	}
	
	//initializes map
	public void initializeMap(String mapName) {
		TiledMap tmpMap = new TmxMapLoader().load("map/maps/" + mapName + ".tmx");
		curMap = new Map(tmpMap);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(curMap.tiledMap);
        //get collision properties of map
        addCollidableTiles();
        player.setMapBounds(curMap.getLeftBound(), curMap.getRightBound(), curMap.getTopBound(), curMap.getBottomBound());
        //get linkable properties of map
        addLinkableTiles();
        //set camera properties
        cameraMinX = curMap.getLeftBound() + (Gdx.graphics.getWidth()/2);
        cameraMinY = curMap.getBottomBound() + (Gdx.graphics.getHeight()/2);
        cameraMaxX = curMap.getRightBound() - (Gdx.graphics.getWidth()/2);
        cameraMaxY = curMap.getTopBound() - (Gdx.graphics.getHeight()/2);
	}

	//draw, call to main game loop here
	@Override
	public void render () {
		//game logic
		gameLoop();
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//draw the tile map
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        //draw sprites
		batch.begin();
		//elapsed time just goes up, the true param tells it to loop
		elapsedTime += Gdx.graphics.getDeltaTime();
		batch.draw(player.getCurAnim().getKeyFrame(elapsedTime, true), player.getXDrawPos(), player.getYDrawPos());
		batch.end();
	}
	
	//main method for the game loop
	public void gameLoop() {
		player.updatePlayer(collidableObjects, linkableObjects);
		player.setPlayerDrawPositions(curMap.getLeftBound(), curMap.getRightBound(), curMap.getTopBound(), curMap.getBottomBound());
		setCameraPosition();
		handleLinks();
	}
	
	//handle camera panning, basically follow the player
	public void setCameraPosition() {
		//player is not at a bound, evaluate as normal
		if(player.getXDrawPos() == player.getInitXPos()) {
			camera.position.x = player.getXOffset();
		//player is at the left of the map
		} else if(player.getXDrawPos() < player.getInitXPos()) {
			camera.position.x = cameraMinX;
		//player is at the right of the map
		} else if(player.getXDrawPos() > player.getInitXPos()) {
			camera.position.x = cameraMaxX;
		}
		//player is not at a bound, evaluate as normal
		if(player.getYDrawPos() == player.getInitYPos()) {
			camera.position.y = player.getYOffset();
		//player is at the bottom of the map
		} else if(player.getYDrawPos() < player.getInitYPos()) {
			camera.position.y = cameraMinY;
		//player is at the top of the map
		} else if(player.getYDrawPos() > player.getInitYPos()) {
			camera.position.y = cameraMaxY;
		}
	}
	
	//see if player is currently on a link, and pan/reset the map as necessary
	public void handleLinks() {
		if(player.linking) {
			initializeMap(player.toMap);
			Direction linkDir = null;
			//there's probably a better way to do this than below
			if(player.fromLink.indexOf("N") > -1) {
				linkDir = Direction.NORTH;
			} else if(player.fromLink.indexOf("S") > -1) {
				linkDir = Direction.SOUTH;
			} else if(player.fromLink.indexOf("E") > -1) {
				linkDir = Direction.EAST;
			} else if(player.fromLink.indexOf("W") > -1) {
				linkDir = Direction.WEST;
			}
			switch(linkDir) {
				case NORTH : player.offsetY = curMap.getBottomBound(); break;
				case SOUTH : player.offsetY = curMap.getTopBound() - player.getHeight(); break;
				case EAST : player.offsetX = curMap.getLeftBound(); break;
				case WEST : player.offsetX = curMap.getRightBound() - player.getWidth(); break;
			}
			player.linking = false;
		}
	}
	
	/**
	 * find all collidable tiles, add them to collidable tiles arraylist
	 * to handle player collisions
	 * could probably move this to an initialization function for the map
	 */
	public void addCollidableTiles() {
		collidableObjects.clear();
		//get the collidable map layer, find all of the objects on that layer
		MapLayer collidableLayer = curMap.getLayers().get("collidable");
		MapObjects collidableLayerObjects = collidableLayer.getObjects();
		for (RectangleMapObject collidableObject : collidableLayerObjects.getByType(RectangleMapObject.class)) {
			//get location attributes of the collidable object
			float objX = collidableObject.getRectangle().x;
			float objY = collidableObject.getRectangle().y;
			float objWidth = collidableObject.getRectangle().width;
			float objHeight = collidableObject.getRectangle().height;
			MapProperties objProperties = collidableObject.getProperties();
			//initialize object and add to the arraylist
			CollidableObject curObj = new CollidableObject(objX, objY, objWidth, objHeight, objProperties);
			collidableObjects.add(curObj);
		}
	}
	
	/**
	 * find all collidable tiles, add them to collidable tiles arraylist
	 * to handle player collisions
	 * could probably move this to an initialization function for the map
	 */
	public void addLinkableTiles() {
		linkableObjects.clear();
		//get the collidable map layer, find all of the objects on that layer
		MapLayer linkableLayer = curMap.getLayers().get("linkable");
		MapObjects linkableLayerObjects = linkableLayer.getObjects();
		for (RectangleMapObject linkableObject : linkableLayerObjects.getByType(RectangleMapObject.class)) {
			//get location attributes of the collidable object
			float objX = linkableObject.getRectangle().x;
			float objY = linkableObject.getRectangle().y;
			float objWidth = linkableObject.getRectangle().width;
			float objHeight = linkableObject.getRectangle().height;
			MapProperties objProperties = linkableObject.getProperties();
			//initialize object and add to the arraylist
			LinkableObject curObj;
			curObj = new LinkableObject(objX, objY, objWidth, objHeight, objProperties);
			linkableObjects.add(curObj);
		}
	}
}
