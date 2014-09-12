package de.micromata.azubi;

import java.util.ArrayList;
import java.util.List;

public abstract class Raum {

	// public static final int MAX_SLOTS_ITEMS = 4;
	protected List<Item> items = new ArrayList<Item>();
	protected Inventory inventory;
	protected boolean fackelUsed = false;
	protected boolean falltuerUsed = false;
	protected int roomNumber;

	public Raum(Inventory inventory, int number, Item... items1) {
		this.inventory = inventory;
		roomNumber = number;

		for (Item item : items1) {
			this.items.add(item);
		}
	}

	public void listItems() { // TODO
		// System.out.println("1. Item in List: " +
		// this.items.get(1).getName());
		System.out.println("Im Raum befindet sich:");
		for (Item item : items) {
			if (item == null) {
				System.out.println("\tKein Objekt");
			} else {
				// String objectName = items.get(i).getName();
				System.out.println("\t" + item.getName());
				/*
				 * TODO Kontext zum Raum herstellen... for (Item value :
				 * Textie.itemMap.values()) {
				 * System.out.println(value.getName()); }
				 */
			}
		}

	}

	/**
	 * 
	 * @param objectID
	 * @return Returns -128 when the item is not in the room
	 */
	public int find(Item item) {
		int i = -128;
		i = items.indexOf(item);
		return i;
	}
	
	public  boolean hasItem(Item item){
		if(items.contains(item)){
			return true;
		}
		return false;
	}



	void doGehen(String[] parsed_command, int count) {
		if (count == 2) {
			switch (parsed_command[1]) {
			case "nord":
				goNorth();
				break;
			case "west":
				goWest();
				break;
			case "ost":
				goEast();
				break;
			case "süd":
				goSouth();
				break;
			}
		} else {
			System.out.println("Wohin gehen?");
		}
	}

	void doVernichte(Item item, int count) {
		if (count == 2) {
			if (inventory.removeItem(item)) {
				System.out.println(item.getName() + " vernichtet.");
				return;
			} else {
				System.out.println("Entweder das Objekt gibt es nicht, oder es ist nicht im Inventar.");
				return;
			}
		} else {
			System.out.println("Was soll vernichtet werden?");
		}
	}
	
	public int getNumber(){
		return roomNumber;
	}

	void doUntersuche(String[] parsed_command, int count) {
		if (count == 2) {
			switch (parsed_command[1].toLowerCase()) {
			case "raum":
				if(Textie.currentRaum.equals(Textie.raumList.get(Textie.raumNummer))){
					Item item = Textie.itemMap.get("FACKEL");
					if(item instanceof ToggleItem){
						ToggleItem fackel = (ToggleItem) item;
						if(fackel.getState() == true){
							this.listItems();
						}
						else {
							System.out.println("Du kannst nichts sehen!");
						}	
					}
				}
				else {
					this.listItems();
				}
				break;
			case "inventar":
				inventory.listItems();
				break;
			default:
				Item itemUSU = Textie.itemMap.get(parsed_command[1].toUpperCase());
				if (itemUSU == null) {
					System.out.println("Das Objekt gibt es nicht.");
				} else {
					itemUSU.untersuchen();
				}

			}
		} else {
			System.out.println("Was soll untersucht werden?");
		}
	}

	void doBenutze(Item item) {
		String itemName = item.getName();
		switch (itemName) {
		case "Fackel":// Textie.itemMap.get("FACKEL").getName():
		case "Feuerzeug": // Textie.itemMap.get("FEUERZEUG").getName():
			int fackelSlot = inventory.findItem(Textie.itemMap.get("FACKEL"));
			int feuerZeugSlot = inventory.findItem(Textie.itemMap.get("FEUERZEUG"));
			if (feuerZeugSlot < 0) {
				System.out.println("Du hast kein Feuerzeug.");
				break;
			} else if (fackelSlot < 0) {
				System.out.println("Du hast keine Fackel.");
				break;
			} else {
				System.out.println("Du zündest deine Fackel mit dem Feuerzeug an.");
				Item item2 = Textie.itemMap.get("FACKEL");
				if(item2 instanceof ToggleItem){
					ToggleItem fackel = (ToggleItem) item2;
					fackel.setState(true);
				}
				break;
			}
		case "Handtuch":
			System.out.println("Du wischst dir den Angstschweiß von der Stirn.");
			break;
		case "Schwert":
			System.out.println("Du stichst dir das Schwert zwischen die Rippen und stirbst.");
			inventory.setAlive(false);
			Textie.ende();
			System.exit(0);
		case "Schlüssel":
			System.out.println("Hier gibt es nichts um den Schlüssel zu benutzen.");
			break;
		case "Stein":
			System.out.println("Hier gibt es nichts um den Stein zu benutzen.");
			break;
		case "Quietscheente":
			if (inventory.findItem(Textie.itemMap.get("QUIETSCHEENTE")) != -128) {
				System.out.println("Die Ente schaut dich vorwurfsvoll an und quietscht leise, als du sie zusammendrückst.");
				break;
			} else {
				System.out.println("Du hast keine Quietscheente.");
				break;
			}

		case "Brecheisen":
			if (inventory.findItem(Textie.itemMap.get("BRECHEISEN")) != -128) {
				System.out.println("Du kratzt dich mit dem Brecheisen am Kopf.");
				break;
			} else {
				System.out.println("Du hast kein Brecheisen.");
				break;
			}

		case "Whiteboard":
			System.out.println("Das fasse ich bestimmt nicht an.");
			break;

		case "Falltür":
			if (find(Textie.itemMap.get("FALLTUER")) != -128 && hasEverything()) {
				System.out.println("Du schlüpfst durch die Falltür in den darunterliegenden Raum.");
				falltuerUsed = true;
			} else if (find(Textie.itemMap.get("FALLTUER")) != -128) {
				System.out.println("Da ist eine Falltür. Du hast das Gefühl, nicht alles erledigt zu haben.");
			}
			break;
		case "Schalter":
			if (find(Textie.itemMap.get("SCHALTER")) != -128) {
				System.out.println("Du hörst ein Rumpeln, als du den Schalter drückst. Es geschieht nichts weiter.");
			}
			break;
		case "Karte":
			if (find(Textie.itemMap.get("SCHALTER")) != -128) {
				System.out.println("Du bist in Raum " + Textie.currentRaum.getNumber());
			}
		default:
			System.out.println("Welches Objekt möchtest du benutzen?");
			break;
		}
	}

	void doNimm(Item item) {
		if (inventory.addItem(item)) {
			
			System.out.println(item.getName() + " zum Inventar hinzugefügt.");
		} else {
			System.out.println("Entweder das Objekt gibt es nicht, oder dein Inventar ist voll.");
		}
	}

	void printHelp() {
		System.out.println("Mögliche Befehle:");
		System.out.println("\thilfe -> Zeigt diese Hilfe");
		System.out.println("\tnimm [gegenstand] -> Gegenstand zum Inventar hinzufügen");
		System.out.println("\tbenutze [gegenstand] -> Gegenstand benutzen");
		System.out.println("\tuntersuche [gegenstand/raum/inventar] -> Gegenstand, Raum oder Inventar untersuchen");
		System.out.println("\tvernichte [gegenstand] -> Gegenstand aus dem Inventar löschen");
		System.out.println("\tgehe [nord/süd/ost/west] -> In eine Richtung gehen");
	}

	private void goWall() {
		System.out.println("Du bist gegen die Wand gelaufen.");
	}

	public abstract void start();

	public abstract boolean isFinished();

	public boolean hasEverything() {
		if (inventory.isInInventory(Textie.itemMap.get("Brecheisen")) && inventory.isInInventory(Textie.itemMap.get("Schlüssel"))) {
			return true;
		} else {
			return false;
		}
	}

	public void goWest() {
		goWall();
	}

	public void goEast() {
		goWall();
	}

	public void goNorth() {
		goWall();
	}

	public void goSouth() {
		goWall();

	}
	
	public boolean addItem(Item item){
		if(items.add(item)){
			return true;
		}
		
		return false;
	}

	public boolean removeItem(Item item) {
		if(items.remove(item)) return true;
		return false;
	}
	
}
