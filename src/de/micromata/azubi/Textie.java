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

	static Inventory inventory = new Inventory(ALIVE);
	static Raum raum1 = new Raum1(inventory, FACKEL, HANDTUCH, TRUHE, SCHALTER);
	static Raum raum2 = new Raum2(inventory, SCHWERT, FEUERZEUG, SCHLUESSEL,
			STEIN);
	static Raum raum3 = new Raum3(inventory, QUIETSCHEENTE, WHITEBOARD,
			BRECHEISEN, FALLTUER);

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
