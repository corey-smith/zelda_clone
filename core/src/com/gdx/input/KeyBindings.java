package com.gdx.input;

import java.util.HashMap;

import com.badlogic.gdx.Input.Keys;

/**
 * class to abstract out game keys from GDX keys
 * this whole class is basically just a HashMap of <GameKey, GDX Key>
 * should only be accessible through the Input class.
 */
class KeyBindings {
	public KeyMap keyMap;

	//initialize
	public KeyBindings() {
		initializeKeyBindings();
	}
	
	//initialize the actual map
	public void initializeKeyBindings() {
		keyMap = initKeyDefaults();
	}
	
	//default key bindings for all interfaces
	public KeyMap initKeyDefaults() {
		KeyMap returnMap = new KeyMap();
		returnMap.put(UserKey.LEFT, Keys.LEFT);
		returnMap.put(UserKey.RIGHT, Keys.RIGHT);
		returnMap.put(UserKey.UP, Keys.UP);
		returnMap.put(UserKey.DOWN, Keys.DOWN);
		returnMap.put(UserKey.ATTACK, Keys.Z);
		return returnMap;
	}
	
	//this is just a HashMap extension to simplify the keyMap used above
	@SuppressWarnings("serial")
	class KeyMap extends HashMap<UserKey, Integer>{
		
	}
}


