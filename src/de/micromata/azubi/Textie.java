package de.micromata.azubi;

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

	// Gegenstände
	public static final int FACKEL = 1;
	public static final int HANDTUCH = 2;
	public static final int QUIETSCHEENTE = 3;
	public static final int BRECHEISEN = 4;
	public static final int SCHWERT = 5;
	public static final int FEUERZEUG = 6;
	public static final int SCHLUESSEL = 7;
	public static final int STEIN = 8;
	public static final int TRUHE = 9;
	public static final int SCHALTER = 10;
	public static final int WHITEBOARD = 11;
	public static final int FALLTUER = 12;
	public static final int KARTE = 13;

	static Item fackel = new Item(FACKEL, "Fackel",
			"Du betrachtest die Fackel. Wie kann man die wohl anzünden?",
			"Du zündest deine Fackel mit dem Feuerzeug an.");
	static Item handtuch = new Item(HANDTUCH, "Handtuch",
			"Das Handtuch sieht sehr flauschig aus.",
			"Du wischst dir den Angstschweiß von der Stirn.");
	static Item ente = new Item(
			QUIETSCHEENTE,
			"Quietscheente",
			"Die Ente schaut dich vorwurfsvoll an.",
			"Die Ente schaut dich vorwurfsvoll an und quietscht leise, als du sie zusammendrückst.");
	static Item brecheisen = new Item(BRECHEISEN, "Brecheisen",
			"Da ist ein Brecheisen, es ist \"Gordon\" eingeritzt.",
			"Du kratzt dich mit dem Brecheisen am Kopf");
	static Item schwert = new Item(SCHWERT, "Schwert",
			"Du betrachtest das Schwert. Es sieht sehr scharf aus.",
			"Du stichst dir das Schwert zwischen die Rippen und stirbst.");
	static Item feuerzeug = new Item(FEUERZEUG, "Feuerzeug",
			"Du betrachtest das Feuerzeug. Es wirkt zuverlässig.",
			"Du zündest deine Fackel mit dem Feuerzeug an.");
	static Item schluessel = new Item(
			SCHLUESSEL,
			"Schlüssel",
			"Du betrachtest den Schlüssel. Was kann man damit wohl aufschließen?",
			"Hier gibt es nichts um den Schlüssel zu benutzen.");
	static Item stein = new Item(STEIN, "Stein",
			"Du betrachtest den Stein. Er wirkt kalt.",
			"Hier gibt es nichts um den Stein zu benutzen.");
	static Item truhe = new Item(
			TRUHE,
			"Truhe",
			"Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.",
			"Du kannst die Truhe nicht öffnen.");
	static Item schalter = new Item(
			SCHALTER,
			"Schalter",
			"Da ist ein kleiner Schalter an der Wand.",
			"Du hörst ein Rumpeln, als du den Schalter drückst. Es geschieht nichts weiter.");
	static Item whiteboard = new Item(WHITEBOARD, "Whiteboard",
			"Es steht \'FLIEH!\' mit Blut geschrieben darauf.",
			"Das fasse ich bestimmt nicht an!");
	static Item falltuer = new Item(FALLTUER, "Falltür", "Da ist eine Falltür",
			"Du schlüpfst durch die Falltür in den darunterliegenden Raum.");
	static Item karte = new Item(KARTE, "Karte",
			"Die Karte zeigt an, in welchem Raum man sich befindet.",
			"Du bist in Raum");

	static Inventory inventory = new Inventory(ALIVE);

	static Raum raum1 = new Raum1(inventory, fackel, handtuch, truhe, schalter);
	static Raum raum2 = new Raum2(inventory, schwert, feuerzeug, schluessel,
			stein);
	static Raum raum3 = new Raum3(inventory, ente, whiteboard, brecheisen,
			falltuer, karte);

	public static void main(String[] args) {

		if (args.length == 1) {
			int raumNummer = Integer.parseInt(args[0]);

			switch (raumNummer) {
			case 1:
				raum1.start();
				break;

			case 2:
				inventory.setItemForStartHack(FACKEL, 0);
				raum2.start();
				break;

			case 3:
				inventory.setItemForStartHack(FACKEL, 0);
				inventory.setItemForStartHack(SCHLUESSEL, 1);
				raum3.start();
				break;

			default:
				System.out.println("Gebe eine Zahl von 1-3 ein.");
				break;
			}
			return;
		}

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

}
