package de.micromata.azubi;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
 *  TEXTIE
 */

public class Textie {
	static int[] umgebung = new int[4];
	static String playerName = "Fremder";
	public static final int STATE = 0;
	public static final int DEAD = 1;
	public static final boolean ALIVE = true;

	static Map<String, Item> itemMap = new HashMap<String, Item>();
	static Inventory inventory = new Inventory(ALIVE);
	static List<Raum> raumList = new LinkedList<Raum>();
	static Raum currentRaum;
	static int raumNummer;
	
	public static void main(String[] args) {
		Textie.initItems();
		Textie.initRooms();
		Textie.showIntro();
		Textie.runGame();
	}

	public static void runGame() {
		raumList.get(raumNummer);
		if (inventory.isAlive()) {
			raumList.get(raumNummer).start();
			if (inventory.isAlive()) {
				raumNummer = raumNummer+1;
				raumList.get(raumNummer).start();
				if (inventory.isAlive()) {
					Textie.runGame();
				} else if (inventory.isAlive() == false) {
					System.out.println("Du bist gestorben.");
				}
			} else if (inventory.isAlive() == false) {
				Textie.ende();
			}
		} else if (inventory.isAlive() == false) {
			System.out.println("Du bist gestorben.");
		}
	}
	
	
	
	
	public static void prompt() {
		do {
			currentRaum.falltuerUsed = false;
			String command = IOUtils.readLine("Was willst du tun? ");
			String[] parsed_command = Textie.parseInput(command);
			int count = 0;
			for (int x = 0; x < parsed_command.length; x++) {
				if (parsed_command[x] != null) {
					count++;
				}
			}
			Item itemToUse = Textie.itemMap.get(parsed_command[1].toUpperCase());
			switch (parsed_command[0]) {
			case "hilfe":
				currentRaum.printHelp();
				break;
			case "nimm":
				currentRaum.doNimm(itemToUse);
				break;
			case "benutze":
				currentRaum.doBenutze(itemToUse);
				break;
			case "untersuche":
				currentRaum.doUntersuche(parsed_command, count);
				break;
			case "vernichte":
				currentRaum.doVernichte(itemToUse, count);
				break;

			case "gehe":
				currentRaum.doGehen(parsed_command, count);
				break;

			default:
				System.out.println("Unbekannter Befehl: " + parsed_command[1]);
				break;
			}
		} while (currentRaum.isFinished() == false);
	}
	
	
	
	
	
	
	
	
	

	public static void ende() {
		System.out.println("Herzlichen Glückwunsch " + playerName + "!");
		System.out.println("Du bist aus deinem Traum erwacht und siehst, dass du");
		System.out.println("in deinem Bett liegst. Du spürst dein Herz stark und schnell schlagen");
		System.out.println("bist aber froh, dass du aufwachen konntest.");
		return;
	}

	public static void showIntro() {
		System.out.println("\n\nWillkommen " + playerName + ".");
		System.out.println("Falls du Hilfe bei der Bedienung brauchst, tippe \'hilfe\' ein.");
		playerName = IOUtils.readLine("\nWie ist dein Name? ");
		if (playerName == null || playerName == "") {
			playerName = "Fremder";
		}
	}

	public static String[] parseInput(String command) {
		String[] result = command.split(" ", 2);
		return result;
	}

	private static void initItems() {
		//TODO (Wenn wir den benutzeText der Items benutzen) Raumnummer hinzufügen.
		itemMap.put("KARTE", new Item("Karte", "Die Karte zeigt an, in welchem Raum man sich befindet.", "Du bist in Raum "));
		itemMap.put("FALLTÜR", new Item("Falltür", "Da ist eine Falltür", "Du schlüpfst durch die Falltür in den darunterliegenden Raum."));
		itemMap.put("WHITEBOARD", new Item("Whiteboard", "Es steht \'FLIEH!\' mit Blut geschrieben darauf.", "Das fasse ich bestimmt nicht an!"));
		itemMap.put("SCHALTER", new Item("Schalter", "Da ist ein kleiner Schalter an der Wand.", "Du hörst ein Rumpeln, als du den Schalter drückst. Es geschieht nichts weiter."));
		itemMap.put("TRUHE", new Item("Truhe", "Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.", "Du kannst die Truhe nicht öffnen."));
		itemMap.put("STEIN", new Item("Stein", "Du betrachtest den Stein. Er wirkt kalt.", "Hier gibt es nichts um den Stein zu benutzen."));
		itemMap.put("SCHLÜSSEL", new Item("Schlüssel", "Du betrachtest den Schlüssel. Was kann man damit wohl aufschließen?", "Hier gibt es nichts um den Schlüssel zu benutzen."));
		itemMap.put("FEUERZEUG", new Item("Feuerzeug", "Du betrachtest das Feuerzeug. Es wirkt zuverlässig.", "Du zündest deine Fackel mit dem Feuerzeug an."));
		itemMap.put("SCHWERT", new Item("Schwert", "Du betrachtest das Schwert. Es sieht sehr scharf aus.", "Du stichst dir das Schwert zwischen die Rippen und stirbst."));
		itemMap.put("BRECHEISEN", new Item("Brecheisen", "Da ist ein Brecheisen, es ist \"Gordon\" eingeritzt.", "Du kratzt dich mit dem Brecheisen am Kopf"));
		itemMap.put("QUIETSCHEENTE", new Item("Quietscheente", "Die Ente schaut dich vorwurfsvoll an.", "Die Ente schaut dich vorwurfsvoll an und quietscht leise, als du sie zusammendrückst."));
		itemMap.put("HANDTUCH", new Item("Handtuch", "Das Handtuch sieht sehr flauschig aus.", "Du wischst dir den Angstschweiß von der Stirn."));
		itemMap.put("FACKEL", new ToggleItem("Fackel", "Du betrachtest die Fackel. Wie kann man die wohl anzünden?", "Du zündest deine Fackel mit dem Feuerzeug an.", false));
	}

	public static void initRooms() {
		raumList.add(new Raum1(inventory, 1, itemMap.get("FACKEL"), itemMap.get("HANDTUCH"), itemMap.get("TRUHE"), itemMap.get("SCHALTER")));
		raumList.add(new Raum2(inventory, 2, itemMap.get("SCHWERT"), itemMap.get("FEUERZEUG"), itemMap.get("SCHLÜSSEL"), itemMap.get("STEIN")));
		raumList.add(new Raum3(inventory, 3, itemMap.get("QUIETSCHEENTE"), itemMap.get("WHITEBOARD"), itemMap.get("BRECHEISEN"), itemMap.get("FALLTÜR"), itemMap.get("KARTE")));
		int raumNummer = 0;
		currentRaum = raumList.get(raumNummer);
	}

}
