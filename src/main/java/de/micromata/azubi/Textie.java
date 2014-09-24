
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
    static LinkedList<Raum> raumList = new LinkedList<Raum>();
    static ListIterator<Raum> listIterator;

    public static void main(String[] args) {
        Textie.initItems(); // TODO Die Karte braucht die Räume.
        Textie.initHumans(); // Humans benötigen Items
        Textie.initRooms(); // Räume benötigen Humans und Items
        Textie.showIntro();
        Textie.runGame();
    }

    public static void setCurrentHuman(Human hts) {
        currentHuman = hts;
    }

    public static void runGame() {
        currentRaum = raum1;
        while (inventory.isAlive()) {
            if(currentRaum.roomNumber == 1 || currentRaum.roomNumber == 4) {
                raumList.getFirst().start();
                listIterator = raumList.listIterator(1);
            }
            currentRaum = listIterator.next();
            currentRaum.start();
        }
        Textie.ende();
    }

    public static void prompt() {
        do {
            currentRaum.falltuerUsed = false;
            String command = IOUtils.readLine("Was willst du tun? ");
            String[] parsed_command = Textie.parseInput(command);
            String[] parsed_args = Textie.parseInput(parsed_command[1]);
            int count = 0;
            int args = 0;
            for (int x = 0; x < parsed_command.length; x++) {
                if(parsed_command[x] != null) {
                    count++;
                }
            }
            for (int x = 0; x < parsed_args.length; x++) {
                if(parsed_args[x] != null) {
                    args++;
                }
            }
            if(parsed_command.length < 2) {
                if(parsed_command[0].equals("hilfe")){
                    currentRaum.printHelp();
                } else {
                    System.out.println("Unbekannter Befehl oder fehlende Argumente: " + parsed_command[1]);
                }
            } else {
                Item itemToUse = Textie.itemMap.get(parsed_command[1].toUpperCase());
                switch (parsed_command[0]) {
                    case "hilfe":
                        currentRaum.printHelp();
                        break;
                    case "nimm":
                        if(args > 1) { // (ACHTUNG: auch bei "nimm blauen hut" wird mehr als ein Argument erkannt)
                            switch(parsed_args[1].toLowerCase()) {
                                case "aus truhe":
                                    currentRaum.doTakeFromChest(Textie.itemMap.get(parsed_args[0].toUpperCase()));
                                    break;
                                default:
                                    System.out.println("Unbekanntes Item: " + parsed_command[1]);
                                    break;
                            }
                        }
                        else {
                            currentRaum.doNimm(itemToUse);
                        }
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
                        currentHuman.doReden(parsed_command, count);
                        break;
                    case "gib":
                        currentHuman.doGeben(parsed_command, count);
                        break;
                    default:
                        System.out.println("Unbekannter Befehl: " + parsed_command[1]);
                        break;
                }
            }
        } while (currentRaum.isFinished() == false);
    }

    public static void ende() {
        System.out.println("Herzlichen Glückwunsch " + playerName + "!");
        System.out.println("Du bist aus deinem Traum erwacht und siehst, dass du");
        System.out.println("in deinem Bett liegst. Du spürst dein Herz stark und schnell schlagen");
        System.out.println("bist aber froh, dass du aufwachen konntest.");
        System.exit(0);
    }

    public static void showIntro() {
        System.out.println("\n\nWillkommen " + playerName + ".");
        System.out.println("Falls du Hilfe bei der Bedienung brauchst, tippe \'hilfe\' ein.");
        playerName = IOUtils.readLine("\nWie ist dein Name? ");
        if(playerName == null || playerName == "") {
            playerName = "Fremder";
        }
    }

    public static String[] parseInput(String command) {
        String[] result = command.split(" ", 2);
        return result;
    }

    private static void initHumans() {
        humanMap.put(Consts.ALTER_MANN, new Human(
                "Gordon", "Probier' doch mal, die Karte zu benutzen.", "Hast du irgendwo GabeN gesehen? Wir wollten uns treffen...",
                "Ich suche ein Brecheisen. Hast du eins?", "Sehr gut. Danke dir.", itemMap.get(Consts.BRECHEISEN)));
    }

    private static void initItems() {
        // TODO (Wenn wir den benutzeText der Items benutzen) Raumnummer
        // hinzufügen.
        // itemMap.put(Consts.KARTE, new Item("Karte", "Die Karte zeigt an, in welchem Raum man sich befindet.", "Du bist in Raum " +
        // currentRaum.getNumberAsString()));
        itemMap.put(Consts.FALLTÜR, new Item("Falltür", "Da ist eine Falltür", "Du schlüpfst durch die Falltür in den darunterliegenden Raum.", false));
        itemMap.put(Consts.WHITEBOARD, new Item("Whiteboard", "Es steht \'FLIEH!\' mit Blut geschrieben darauf.", "Das fasse ich bestimmt nicht an!", false));
        itemMap.put(Consts.SCHALTER, new ToggleItem(
                "Schalter", "Da ist ein kleiner Schalter an der Wand.", "Du hörst ein Rumpeln, als du den Schalter drückst.", false,
                false));
        itemMap.put(Consts.TRUHE, new StorageItem(
                "Truhe", "Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.", "Du kannst die Truhe nicht öffnen.", false));
        itemMap.put(Consts.STEIN, new Item("Stein", "Du betrachtest den Stein. Er wirkt kalt.", "Hier gibt es nichts um den Stein zu benutzen.", true));
        itemMap.put(Consts.SCHLÜSSEL, new Item(
                "Schlüssel", "Du betrachtest den Schlüssel. Was kann man damit wohl aufschließen?", "Hier gibt es nichts um den Schlüssel zu benutzen.", true));
        itemMap.put(Consts.FEUERZEUG, new Item(
                "Feuerzeug", "Du betrachtest das Feuerzeug. Es wirkt zuverlässig.", "Du zündest deine Fackel mit dem Feuerzeug an.", true));
        itemMap.put(Consts.SCHWERT, new Item(
                "Schwert", "Du betrachtest das Schwert. Es sieht sehr scharf aus.", "Du stichst dir das Schwert zwischen die Rippen und stirbst.", true));
        itemMap.put(Consts.BRECHEISEN, new Item(
                "Brecheisen", "Da ist ein Brecheisen, es ist \"Gordon\" eingeritzt.", "Du kratzt dich mit dem Brecheisen am Kopf", true));
        itemMap.put(Consts.QUIETSCHEENTE, new Item(
                "Quietscheente", "Die Ente schaut dich vorwurfsvoll an.", "Die Ente schaut dich vorwurfsvoll an und quietscht leise, als du sie zusammendrückst.",
                true));
        itemMap.put(Consts.HANDTUCH, new Item("Handtuch", "Das Handtuch sieht sehr flauschig aus.", "Du wischst dir den Angstschweiß von der Stirn.", true));
        itemMap.put(Consts.FACKEL, new ToggleItem(
                "Fackel", "Du betrachtest die Fackel. Wie kann man die wohl anzünden?", "Du zündest deine Fackel mit dem Feuerzeug an.", true, false));
        itemMap.put(Consts.SACK, new Item(
                "Sack", "Du betrachtest den Sack. Vielleicht kannst du ihn ja an deinem Rucksack befestigen.", "Du bindest den Sack an deinen Rucksack.", true));
    }

    public static void initRooms() {
        raum1 = new Raum1(inventory, 1, itemMap.get(Consts.FACKEL), itemMap.get(Consts.HANDTUCH), itemMap.get(Consts.TRUHE), itemMap.get(Consts.SCHALTER));
        raum2 = new Raum2(inventory, 2, itemMap.get(Consts.SCHWERT), itemMap.get(Consts.FEUERZEUG), itemMap.get(Consts.SCHLÜSSEL), itemMap.get(Consts.STEIN));
        raum3 = new Raum3(inventory, 3, itemMap.get(Consts.QUIETSCHEENTE), itemMap.get(Consts.WHITEBOARD), itemMap.get(Consts.BRECHEISEN), itemMap.get(Consts.FALLTÜR));
        raum4 = new Raum4(inventory, 4, humanMap.get(Consts.ALTER_MANN), itemMap.get(Consts.SCHALTER), itemMap.get(Consts.SACK)); // TODO Schlüssel durch die
        // restlichen Items ersetzen
        // (Sack, etc.)
        raumList.addFirst(raum1);
        raumList.add(raum2);
        raumList.add(raum3);
        raumList.add(raum4);
    }

}
