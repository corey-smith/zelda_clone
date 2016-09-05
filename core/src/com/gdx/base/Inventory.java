package com.gdx.base;

import java.util.ArrayList;
import java.util.Iterator;

import com.gdx.player.Player;

public class Inventory {

	Player player;
	ArrayList<Item> items;
	
	/**
	 * Player inventory, this is essentially just a wrapper class for an ArrayList of items
	 * @param player
	 */
	public Inventory(Player player) {
		this.player = player;
		this.items = new ArrayList<Item>();
	}
	
	/**
	 * Add item to inventory given specific item
	 * @param item
	 */
	public void add(Item item) {
		this.items.add(item);
	}
	
	/**
	 * Remove item from inventory given specific item
	 * @param item
	 */
	public void remove(Item item) {
		this.items.remove(item);
	}
	
}
