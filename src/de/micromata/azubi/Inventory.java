
package de.micromata.azubi;

import java.util.ArrayList;
import java.util.List;

public class Inventory
{

	private static final int MAX_SLOTS_INVENTORY = 5;

	private boolean alive;

	// private int[] inventory;
	private List<Item> inventory = new ArrayList<Item>();

	public Inventory(boolean alive)
	{
		this.alive = alive;
		// this.inventory = new int[MAX_SLOTS_INVENTORY];
	}

	public boolean isAlive()
	{
		return this.alive;
	}

	public boolean addItem(Item item)
	{
		if (inventory.size() < MAX_SLOTS_INVENTORY && Textie.currentRaum.hasItem(item)) {
			inventory.add(item);
			Textie.currentRaum.removeItem(item);
			return true;
		} else {
			return false;
		}
	}

	/*
	 * public boolean addToInventory(int objectID, Raum raum) { List <Item>items = raum.items; int objektInUmgebung = -128; for (int i = 0; i <
	 * MAX_SLOTS_INVENTORY; i++) { if (inventory[i] == 0) { inventory[i] = objectID; for (int y = 0; y < items.size(); y++) { if ( items.get(y).getID() ==
	 * objectID) { objektInUmgebung = i; } } if (objektInUmgebung != -128) { raum.removeItem(objectID); return true; } } } return false; }
	 */

	public boolean removeItem(Item item)
	{
		if (inventory.remove(item) && Textie.currentRaum.addItem(item))
			return true;
		return false;
	}

	public void listItems()
	{
		if (inventory.size() > 0) {
			System.out.println("In deiner Tasche befindet sich:");
			for (Item items : inventory) {
				String objectName = items.getName();
				System.out.println("\t" + objectName);
			}
		} else {
			System.out.println("Deine Tasche ist leer.");
		}
	}

	public int findItem(Item items)
	{
		int slot = -128;
		slot = inventory.indexOf(items);
		if (slot > -128) {
			return slot;
		}
		return -128;
	}

	public boolean isInInventory(Item items)
	{
		return findItem(items) >= 0;
	}

	public void setAlive(boolean state)
	{
		this.alive = state;
	}

}
