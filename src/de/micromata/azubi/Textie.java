package de.micromata.azubi;

import java.util.HashMap;
import java.util.Map;

/*
 *  TEXTIE
 *
 *  Wenn man einen Raum startet (RaumX.start();), muss dieser Raum ein Array zurück geben.
 *  Dieses Array beinhaltet im ersten Feld, ob das Level beendet wurde, und in den übrigen fünf Feldern sind die ID's der Items enthalten, die man mit sich trägt.
 *  Wenn der Raum abgeschlossen wurde, muss das erste Feld "0" enthalten und wenn der Spieler tot ist, muss dieses Feld 1 enthalten.
 */

public class Textie {
	// static int[] inventory = new int[6];
	static int[] umgebung = new int[4];
	static String playerName = "Fremder";
	public static final int STATE = 0;
	public static final int DEAD = 1;
	public static final boolean ALIVE = true;

	/*
	 * // Gegenstände public static final int FACKEL = 1; public static final
	 * int HANDTUCH = 2; public static final int QUIETSCHEENTE = 3; public
	 * static final int BRECHEISEN = 4; public static final int SCHWERT = 5;
	 * public static final int FEUERZEUG = 6; public static final int SCHLUESSEL
	 * = 7; public static final int STEIN = 8; public static final int TRUHE =
	 * 9; public static final int SCHALTER = 10; public static final int
	 * WHITEBOARD = 11; public static final int FALLTUER = 12; public static
	 * final int KARTE = 13;
	 */

	static Inventory inventory = new Inventory(ALIVE);

	static Raum raum1 = new Raum1(inventory, fackel, handtuch, truhe, schalter);
	static Raum raum2 = new Raum2(inventory, schwert, feuerzeug, schluessel,
			stein);
	static Raum raum3 = new Raum3(inventory, ente, whiteboard, brecheisen,
			falltuer, karte);

	static Map<String, Item> itemMap = new HashMap<String, Item>();

	public static void main(String[] args) {

		/*
		 * 'fackel' = new Item(.... 'fackel')
		 */

		itemMap.put("fackel", fackel);

		Item _fackel = itemMap.get("fackel");

		for (Item value : itemMap.values()) {
			System.out.println(value);
		}

		for (String key : itemMap.keySet()) {
			System.out.println(key);
		}

		if (args.length == 1) {
			int raumNummer = Integer.parseInt(args[0]);

			switch (raumNummer) {
			case 1:
				raum1.start();
				break;

			case 2:
				inventory.setItemForStartHack(fackel);
				raum2.start();
				break;

			case 3:
				inventory.setItemForStartHack(fackel);
				inventory.setItemForStartHack(schluessel);
				raum3.start();
				break;

			default:
				System.out.println("Gebe eine Zahl von 1-3 ein.");
				break;
			}
			return;
		}
		Textie.initItems();
		Textie.showIntro();
		Textie.runGame();
	}

	public static void runGame() {
		raum1.start();
		if (inventory.isAlive()) {
			raum2.start();
			if (inventory.isAlive()) {
				raum3.start();
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

	public static void ende() {
		System.out.println("Herzlichen Glückwunsch " + playerName + "!");
		System.out
				.println("Du bist aus deinem Traum erwacht und siehst, dass du");
		System.out
				.println("in deinem Bett liegst. Du spürst dein Herz stark und schnell schlagen");
		System.out.println("bist aber froh, dass du aufwachen konntest.");
		return;
	}

	public static void showIntro() {
		System.out.println("\n\nWillkommen " + playerName + ".");
		System.out
				.println("Falls du Hilfe bei der Bedienung brauchst, tippe \'hilfe\' ein.");
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
		itemMap.put("Karte", new Item("Karte",
				"Die Karte zeigt an, in welchem Raum man sich befindet.",
				"Du bist in Raum"));
		itemMap.put(
				"Falltür",
				new Item("Falltür", "Da ist eine Falltür",
						"Du schlüpfst durch die Falltür in den darunterliegenden Raum."));
		itemMap.put("Whiteboard", new Item("Whiteboard",
				"Es steht \'FLIEH!\' mit Blut geschrieben darauf.",
				"Das fasse ich bestimmt nicht an!"));
		itemMap.put(
				"Schalter",
				new Item(
						"Schalter",
						"Da ist ein kleiner Schalter an der Wand.",
						"Du hörst ein Rumpeln, als du den Schalter drückst. Es geschieht nichts weiter."));
		itemMap.put(
				"Truhe",
				new Item(
						"Truhe",
						"Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.",
						"Du kannst die Truhe nicht öffnen."));
		itemMap.put("Stein", new Item("Stein",
				"Du betrachtest den Stein. Er wirkt kalt.",
				"Hier gibt es nichts um den Stein zu benutzen."));
		itemMap.put(
				"Schlüssel",
				new Item(
						"Schlüssel",
						"Du betrachtest den Schlüssel. Was kann man damit wohl aufschließen?",
						"Hier gibt es nichts um den Schlüssel zu benutzen."));
		itemMap.put("Feuerzeug", new Item("Feuerzeug",
				"Du betrachtest das Feuerzeug. Es wirkt zuverlässig.",
				"Du zündest deine Fackel mit dem Feuerzeug an."));
		itemMap.put("Schwert", new Item("Schwert",
				"Du betrachtest das Schwert. Es sieht sehr scharf aus.",
				"Du stichst dir das Schwert zwischen die Rippen und stirbst."));
		itemMap.put("Brecheisen", new Item("Brecheisen",
				"Da ist ein Brecheisen, es ist \"Gordon\" eingeritzt.",
				"Du kratzt dich mit dem Brecheisen am Kopf"));
		itemMap.put(
				"Ente",
				new Item(
						"Quietscheente",
						"Die Ente schaut dich vorwurfsvoll an.",
						"Die Ente schaut dich vorwurfsvoll an und quietscht leise, als du sie zusammendrückst."));
		itemMap.put("Handtuch", new Item("Handtuch",
				"Das Handtuch sieht sehr flauschig aus.",
				"Du wischst dir den Angstschweiß von der Stirn."));
		itemMap.put("Fackel", new Item("Fackel",
				"Du betrachtest die Fackel. Wie kann man die wohl anzünden?",
				"Du zündest deine Fackel mit dem Feuerzeug an."));
	}

}
