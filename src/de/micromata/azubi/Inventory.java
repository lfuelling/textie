package de.micromata.azubi;

import java.util.List;

public class Inventory {

	private static final int MAX_SLOTS_INVENTORY = 5;
	private boolean alive;
	private int[] inventory;

	public Inventory(boolean alive) {
		this.alive = alive;
		this.inventory = new int[MAX_SLOTS_INVENTORY];
	}

	public boolean isAlive() {
		return this.alive;
	}

	public boolean addToInventory(int objectID, Raum raum) {
		@SuppressWarnings("rawtypes")
		List items = raum.items;
		int objektInUmgebung = -128;
		for (int i = 0; i < MAX_SLOTS_INVENTORY; i++) {
			if (inventory[i] == 0) {
				inventory[i] = objectID;
				for (int y = 0; y < Raum.MAX_SLOTS_ITEMS; y++) {
					if (((Item) items.get(y)).getID() == objectID) {
						objektInUmgebung = i;
					}
				}
				if (objektInUmgebung != -128) {
					raum.removeItem(objectID);
					return true;
				}
			}
		}
		return false;
	}

	public boolean removeFromInventory(int objectID) {
		for (int i = 0; i < MAX_SLOTS_INVENTORY; i++) {
			if (inventory[i] == objectID) {
				inventory[i] = 0;
				return true;
			}
		}
		return false;
	}

	public void listInventory() {
		System.out.println("In deiner Tasche befindet sich:");
		for (int i = 0; i < MAX_SLOTS_INVENTORY; i++) {
			String objectName = Item.getObjectName(inventory[i]);
			System.out.println("\t" + objectName);
		}
	}

	public int findInInventory(int objectID) {
		for (int i = 0; i < MAX_SLOTS_INVENTORY; i++) {
			if (inventory[i] == objectID) {
				return i;
			}
		}
		return -128;
	}
	
	public boolean isInInventory(int objectID){
		return findInInventory(objectID) != -128;
	}
	
	public void setItemForStartHack(int item, int slot){
		this.inventory[slot] = item;
	}
	
	public void setAlive(boolean par){
		this.alive = par;
	}
	
}
