package com.gdx.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

/**
 * user input, accessed from either Player.java or Core.java
 * this is just for the actual listeners, keys are abstracted out in KeyBindings.java
 */
public class Input implements InputProcessor{
	KeyBindings keyBindings;
	
	//specific key-value variables to listen for elsewhere
	public boolean leftHeld = false;
	public boolean rightHeld = false;
	public boolean upHeld = false;
	public boolean downHeld = false;
	public boolean attackHeld = false;

	//initialize
	public Input() {
        Gdx.input.setInputProcessor(this);
        keyBindings = new KeyBindings();
	}

	//actual key down listeners
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == keyBindings.keyMap.get(UserKey.LEFT)) {
			leftHeld = true;
		} else if(keycode == keyBindings.keyMap.get(UserKey.RIGHT)) {
			rightHeld = true;
		} else if(keycode == keyBindings.keyMap.get(UserKey.UP)) {
			upHeld = true;
		} else if(keycode == keyBindings.keyMap.get(UserKey.DOWN)) {
			downHeld = true;
		} else if(keycode == keyBindings.keyMap.get(UserKey.ATTACK)) {
			attackHeld = true;
		}
		return false;
	}

	//actual key up listeners
	@Override
	public boolean keyUp(int keycode) {
		if(keycode == keyBindings.keyMap.get(UserKey.LEFT)) {
			leftHeld = false;
		} else if(keycode == keyBindings.keyMap.get(UserKey.RIGHT)) {
			rightHeld = false;
		} else if(keycode == keyBindings.keyMap.get(UserKey.UP)) {
			upHeld = false;
		} else if(keycode == keyBindings.keyMap.get(UserKey.DOWN)) {
			downHeld = false;
		} else if(keycode == keyBindings.keyMap.get(UserKey.ATTACK)) {
			attackHeld = false;
		}
		return false;
	}

	//unused, possibly can be used?
	@Override
	public boolean keyTyped(char character) {
		
		return false;
	}
	
	//not using any of these below
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
