package de.micromata.azubi;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
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
	static Map<String, Human> humanMap = new HashMap<String, Human>();
	static Inventory inventory = new Inventory(ALIVE);
	static Raum raum1;
	static Raum raum2;
	static Raum raum3;
	static Raum raum4;
	static Raum currentRaum = raum1;
	static Human currentHuman;
	static LinkedList<Raum> raumList= new LinkedList<Raum>();
	static ListIterator<Raum> listIterator;
	

	public static void main(String[] args) {
		Textie.initItems(); //TODO Die Karte braucht die Räume.
		Textie.initHumans(); // Humans benötigen Items
		Textie.initRooms(); // Räume benötigen Humans und Items
		Textie.showIntro();
		Textie.runGame();
	}
	
	public static void setCurrentHuman (Human hts) {
		currentHuman = hts;
	}
	public static void runGame() {
		currentRaum = raum1;
		while(inventory.isAlive()){
			if(currentRaum.roomNumber == 1){
				raumList.getFirst().start();
				listIterator = raumList.listIterator(currentRaum.roomNumber);
			}
			currentRaum = listIterator.next();
			currentRaum.start();
		}
		Textie.ende();
		/*
		currentRaum = raum1;
		raumList.getFirst().start();
		if (inventory.isAlive()) {
			currentRaum = raum2;
			listIterator = raumList.listIterator(currentRaum.roomNumber-1);
			listIterator.next().start();
			if (inventory.isAlive()) {
				currentRaum = raum3;
				listIterator.next().start();
				if (inventory.isAlive()) {
					raum4.start();
					if (inventory.isAlive()) {
						Textie.runGame();
					} else if (inventory.isAlive() == false) {
						Textie.ende();
					}
				} else if (inventory.isAlive() == false) {
					Textie.ende();
				}
			} else if (inventory.isAlive() == false) {
				Textie.ende();
			}
		} else if (inventory.isAlive() == false) {
			Textie.ende();
		}
		*/
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
			case "rede":
				currentRaum.doReden(parsed_command, count);
				break;
			case "gib":
				currentHuman.doGeben(parsed_command, count);
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

	private static void initHumans() {
		humanMap.put("ALTER MANN", new Human("Gordon", "Probier' doch mal, die Karte zu benutzen.", "Hast du irgendwo GabeN gesehen? Wir wollten uns treffen...",
				"Ich suche ein Brecheisen. Hast du eins?", "Sehr gut. Danke dir.", itemMap.get("BRECHEISEN")));
	}

	private static void initItems() {
		// TODO (Wenn wir den benutzeText der Items benutzen) Raumnummer
		// hinzufügen.
		//itemMap.put("KARTE", new Item("Karte", "Die Karte zeigt an, in welchem Raum man sich befindet.", "Du bist in Raum " + currentRaum.getNumberAsString()));
		itemMap.put("FALLTÜR", new Item("Falltür", "Da ist eine Falltür", "Du schlüpfst durch die Falltür in den darunterliegenden Raum.", false));
		itemMap.put("WHITEBOARD", new Item("Whiteboard", "Es steht \'FLIEH!\' mit Blut geschrieben darauf.", "Das fasse ich bestimmt nicht an!", false));
		itemMap.put("SCHALTER", new Item("Schalter", "Da ist ein kleiner Schalter an der Wand.", "Du hörst ein Rumpeln, als du den Schalter drückst. Es geschieht nichts weiter.", false));
		itemMap.put("TRUHE", new Item("Truhe", "Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.", "Du kannst die Truhe nicht öffnen.", false));
		itemMap.put("STEIN", new Item("Stein", "Du betrachtest den Stein. Er wirkt kalt.", "Hier gibt es nichts um den Stein zu benutzen.", true));
		itemMap.put("SCHLÜSSEL", new Item("Schlüssel", "Du betrachtest den Schlüssel. Was kann man damit wohl aufschließen?", "Hier gibt es nichts um den Schlüssel zu benutzen.", true));
		itemMap.put("FEUERZEUG", new Item("Feuerzeug", "Du betrachtest das Feuerzeug. Es wirkt zuverlässig.", "Du zündest deine Fackel mit dem Feuerzeug an.", true));
		itemMap.put("SCHWERT", new Item("Schwert", "Du betrachtest das Schwert. Es sieht sehr scharf aus.", "Du stichst dir das Schwert zwischen die Rippen und stirbst.", true));
		itemMap.put("BRECHEISEN", new Item("Brecheisen", "Da ist ein Brecheisen, es ist \"Gordon\" eingeritzt.", "Du kratzt dich mit dem Brecheisen am Kopf",true ));
		itemMap.put("QUIETSCHEENTE", new Item("Quietscheente", "Die Ente schaut dich vorwurfsvoll an.", "Die Ente schaut dich vorwurfsvoll an und quietscht leise, als du sie zusammendrückst.", true));
		itemMap.put("HANDTUCH", new Item("Handtuch", "Das Handtuch sieht sehr flauschig aus.", "Du wischst dir den Angstschweiß von der Stirn.",true ));
		itemMap.put("FACKEL", new ToggleItem("Fackel", "Du betrachtest die Fackel. Wie kann man die wohl anzünden?", "Du zündest deine Fackel mit dem Feuerzeug an.", true, false));
	}

	public static void initRooms() {
		raum1 = new Raum1(inventory, 1, itemMap.get("FACKEL"), itemMap.get("HANDTUCH"), itemMap.get("TRUHE"), itemMap.get("SCHALTER"));
		raum2 = new Raum2(inventory, 2, itemMap.get("SCHWERT"), itemMap.get("FEUERZEUG"), itemMap.get("SCHLÜSSEL"), itemMap.get("STEIN"));
		raum3 = new Raum3(inventory, 3, itemMap.get("QUIETSCHEENTE"), itemMap.get("WHITEBOARD"), itemMap.get("BRECHEISEN"), itemMap.get("FALLTÜR")/*, itemMap.get("KARTE")*/);
		raum4 = new Raum4(inventory, 4, humanMap.get("ALTER MANN"), itemMap.get("SCHLÜSSEL")); // TODO Schlüssel durch die restlichen Items ersetzen (Sack, etc.)
		raumList.addFirst(raum1);
		raumList.add(raum2);
		raumList.add(raum3);
		raumList.add(raum4);
	}

}
