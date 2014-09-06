package de.micromata.azubi;

public abstract class Raum {

	public static final int MAX_SLOTS_ITEMS = 4;
	protected int[] items;
	protected Inventory inventory;
	protected boolean fackelUsed = false;

	public Raum(Inventory inventory, int... items) {
		this.inventory = inventory;
		this.items = items;
	}

	public void listItems() {
		System.out.println("Im Raum befindet sich:");
		for (int i = 0; i < MAX_SLOTS_ITEMS; i++) {
			String objectName = Item.getObjectName(this.items[i]);
			System.out.println("\t" + objectName);
		}
	}

	/**
	 * 
	 * @param objectID
	 * @return Returns -128 when the item is not in the room
	 */
	public int find(int objectID) {
		for (int i = 0; i < MAX_SLOTS_ITEMS; i++) {
			if (this.items[i] == objectID) {
				return i;
			}
		}
		return -128;
	}

	protected void prompt() {
		do {
			String command = IOUtils.readLine("Was willst du tun? ");
			String[] parsed_command = Textie.parseInput(command);
			int object_to_use = 0;
			int count = 0;
			for (int x = 0; x < parsed_command.length; x++) {
				if (parsed_command[x] != null) {
					count++;
				}
			}
			if (count == 2) {
				object_to_use = Item.getObjectID(parsed_command[1]
						.toUpperCase());
			}
			switch (parsed_command[0]) {
			case "hilfe":
				printHelp();
				break;
			case "nimm":
				doNimm(parsed_command, object_to_use);
				break;
			case "benutze":
				doBenutze(object_to_use);
				break;
			case "untersuche":
				doUntersuche(parsed_command, count);
				break;
			case "vernichte":
				doVernichte(parsed_command, object_to_use, count);
				break;

			case "gehe":
				doGehen(parsed_command, count);
				break;

			default:
				System.out.println("Unbekannter Befehl: " + parsed_command[0]);
				break;
			}
		} while (isFinished() == false);
	}

	private void doGehen(String[] parsed_command, int count) {
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

	private void doVernichte(String[] parsed_command, int object_to_use,
			int count) {
		if (count == 2) {
			if (inventory.removeFromInventory(object_to_use)) {
				System.out.println(parsed_command[1] + " vernichtet.");
				return;
			} else {
				System.out
						.println("Entweder das Objekt gibt es nicht, oder es ist nicht im Inventar.");
				return;
			}
		} else {
			System.out.println("Was soll vernichtet werden?");
		}
	}

	private void doUntersuche(String[] parsed_command, int count) {
		if (count == 2) {
			switch (parsed_command[1]) {
			case "raum":
				this.listItems();
				break;

			case "inventar":
				inventory.listInventory();
				break;

			case "fackel":
				if (inventory.isInInventory(Textie.FACKEL)) {
					System.out
							.println("Du betrachtest die Fackel. Wie kann man die wohl anzünden?");
				} else if (this.find(Textie.FACKEL) != -128) {
					System.out.println("Da liegt eine Fackel.");
				} else {
					System.out.println("Hä?");
				}
				break;

			case "handtuch":
				if (inventory.isInInventory(Textie.FACKEL)) {
					System.out
							.println("Du betrachtest das Handtuch. Es sieht sehr flauschig aus.");
				} else if (this.find(Textie.HANDTUCH) != -128) {
					System.out.println("Da liegt ein Handtuch.");
				} else {
					System.out.println("Hä?");
				}
				break;

			case "truhe":
				if (this.find(Textie.TRUHE) != -128) {
					System.out
							.println("Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.");
				} else {
					System.out.println("Hä?");
				}
				break;

			case "schalter":
				if (this.find(Textie.SCHALTER) != -128) {
					System.out
							.println("Da ist ein kleiner Schalter an der Wand.");
				} else {
					System.out.println("Hä?");
				}
				break;

			case "schwert":
				if (inventory.isInInventory(Textie.SCHWERT)) {
					System.out
							.println("Du betrachtest das Schwert. Es sieht sehr scharf aus.");
				} else if (this.find(Textie.SCHWERT) != -128) {
					System.out.println("Da liegt ein Schwert.");
				} else {
					System.out.println("Hä?");
				}
				break;

			case "feuerzeug":
				if (inventory.isInInventory(Textie.FEUERZEUG)) {
					System.out
							.println("Du betrachtest das Feuerzeug. Es wirkt zuverlässig.");
				} else if (this.find(Textie.FEUERZEUG) != -128) {
					System.out.println("Da liegt ein Feuerzeug.");
				} else {
					System.out.println("Hä?");
				}
				break;

			case "schlüssel":
				if (inventory.isInInventory(Textie.SCHLUESSEL)) {
					System.out
							.println("Du betrachtest den Schlüssel. Was kann man damit wohl aufschließen?");
				} else if (this.find(Textie.SCHLUESSEL) != -128) {
					System.out.println("Da liegt ein Schlüssel.");
				} else {
					System.out.println("Hä?");
				}
				break;

			case "stein":
				if (inventory.isInInventory(Textie.STEIN)) {
					System.out
							.println("Du betrachtest den Stein. Er ist kalt.");
				} else if (this.find(Textie.STEIN) != -128) {
					System.out.println("Da liegt ein Stein.");
				} else {
					System.out.println("Hä?");
				}
				break;
			case "quietscheente":
				if (inventory.isInInventory(Textie.QUIETSCHEENTE)) {
					System.out
							.println("Die Ente schaut dich vorwurfsvoll an.");
					break;
				} else if (find(Textie.QUIETSCHEENTE) != -128) {
					System.out.println("Da liegt eine Quietscheente.");
					break;
				} else {
					System.out.println("Da liegt keine Quietscheente.");
					break;
				}

			case "brecheisen":
				if (inventory.isInInventory(Textie.BRECHEISEN)) {
					System.out
							.println("Da ist ein Brecheisen, es ist \"Gordon\" eingeritzt."); 
																					
																					
					break;
				} else {
					System.out.println("Du hast kein Brecheisen.");
					break;
				}

			case "whiteboard":
				System.out.println("Das fasse ich bestimmt nicht an.");
				break;

			case "falltür":
				System.out.println("Da ist eine Falltür");
				break;

			}
		} else {
			System.out.println("Was soll untersucht werden?");
		}
	}

	private void doBenutze(int object_to_use) {
		switch (object_to_use) {
		case Textie.FACKEL:
		case Textie.FEUERZEUG:
			int fackelSlot = inventory.findInInventory(Textie.FACKEL);
			int feuerZeugSlot = inventory
					.findInInventory(Textie.FEUERZEUG);
			if (feuerZeugSlot < 0) {
				System.out.println("Du hast kein Feuerzeug.");
			} else if (fackelSlot < 0) {
				System.out.println("Du hast keine Fackel.");
			} else {
				System.out
						.println("Du zündest deine Fackel mit dem Feuerzeug an.");
				fackelUsed = true;
			}
			break;
		case Textie.HANDTUCH:
			System.out
					.println("Du wischst dir den Angstschweiß von der Stirn.");
			break;
		case Textie.SCHWERT:
			System.out
					.println("Du stichst dir das Schwert zwischen die Rippen und stirbst.");
			inventory.setAlive(false);
			Textie.ende();
			System.exit(0);
		case Textie.SCHLUESSEL:
			System.out
					.println("Hier gibt es nichts um den Schlüssel zu benutzen.");
			break;
		case Textie.STEIN:
			System.out
					.println("Hier gibt es nichts um den Stein zu benutzen.");
			break;
		case Textie.QUIETSCHEENTE:
			if (inventory.findInInventory(Textie.QUIETSCHEENTE) != -128) {
				System.out
						.println("Die Ente schaut dich vorwurfsvoll an und quietscht leise, als du sie zusammendrückst.");
				break;
			} else {
				System.out.println("Du hast keine Quietscheente.");
				break;
			}

		case Textie.BRECHEISEN:
			if (inventory.findInInventory(Textie.BRECHEISEN) != -128) {
				System.out
						.println("Du kratzt dich mit dem Brecheisen am Kopf.");
				break;
			} else {
				System.out.println("Du hast kein Brecheisen.");
				break;
			}

		case Textie.WHITEBOARD:
			System.out.println("Das fasse ich bestimmt nicht an.");
			break;

		case Textie.FALLTUER:
			if (find(Textie.FALLTUER) != -128 && isFinished()) {
				System.out
						.println("Du schlüpfst durch die Falltür in den darunterliegenden Raum.");
			}

			break;
		default:
			System.out.println("Welches Objekt möchtest du benutzen?");
		}
	}

	private void doNimm(String[] parsed_command, int object_to_use) {
		if (inventory.addToInventory(object_to_use, this)) {
			System.out.println(parsed_command[1]
					+ " zum Inventar hinzugefügt.");
		} else {
			System.out
					.println("Entweder das Objekt gibt es nicht, oder dein Inventar ist voll.");
		}
	}

	private void printHelp() {
		System.out.println("Mögliche Befehle:");
		System.out.println("\thilfe -> Zeigt diese Hilfe");
		System.out
				.println("\tnimm [gegenstand] -> Gegenstand zum Inventar hinzufügen");
		System.out
				.println("\tbenutze [gegenstand] -> Gegenstand benutzen");
		System.out
				.println("\tuntersuche [gegenstand/raum/inventar] -> Gegenstand, Raum oder Inventar untersuchen");
		System.out
				.println("\tvernichte [gegenstand] -> Gegenstand aus dem Inventar löschen");
		System.out.println("\tgehe [himmelsrichtung]");
	}

	private void goWall() {
		System.out.println("Du bist gegen die Wand gelaufen.");
	}

	public abstract void start();

	public abstract boolean isFinished();

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
	
	public void removeItem(int objectID){
		if(this.find(objectID) != -128){
			int i = this.find(objectID);
			items[i] = 0;	
		}
	}
}
