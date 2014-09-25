
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
    static Raum currentRaum;
    static Human currentHuman;
    static LinkedList<Raum> raumList = new LinkedList<Raum>();
    static ListIterator<Raum> listIterator;

    public static void main(String[] args) {
        initEngine();
        Textie.showIntro(null);
        Textie.runGame(true);
    }

    public static void initEngine() {
        Textie.initItems(); // TODO Die Karte braucht die Räume.
        Textie.initHumans(); // Humans benötigen Items
        Textie.initRooms(); // Räume benötigen Humans und Items
    }

    public static void setCurrentHuman(Human hts) {
        currentHuman = hts;
    }

    public static void runGame(boolean withPrompt) {
        currentRaum = raumList.getFirst();
        listIterator = raumList.listIterator(1);
        while (inventory.isAlive()) {
            currentRaum.start(withPrompt);
            if (listIterator.hasNext()) {
                currentRaum = listIterator.next();
            } else {
                listIterator = raumList.listIterator(1);
                currentRaum = raumList.getFirst();
            }
        }
        Textie.ende();
    }

    public static void prompt() {
        do {
            currentRaum.falltuerUsed = false;
            String command = IOUtils.readLine("Was willst du tun? ");
            String[] parsed_command = Textie.parseInput(command);
            String[] parsed_args = new String[2];
            if (parsed_command[1] == null) {
                parsed_args[0] = "nichts";
            } else {
                parsed_args = Textie.parseInput(parsed_command[1]);
            }
            executeCommand(parsed_command, parsed_args);
        } while (currentRaum.isFinished() == false);
    }

    public static void executeCommand(String[] parsed_command, String[] parsed_args) {
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
            if(parsed_command[0].equals(Command.HILFE)){
                currentRaum.printHelp();
            } else {
                printText("Unbekannter Befehl oder fehlende Argumente: " + parsed_command[1]);
            }
        } else {
            Item itemToUse = Textie.itemMap.get(parsed_command[1].toUpperCase());
            switch (parsed_command[0]) {
                case Command.HILFE:
                    currentRaum.printHelp();
                    break;
                case Command.NIMM:
                    if(args > 1) { // (ACHTUNG: auch bei "nimm blauen hut" wird mehr als ein Argument erkannt)
                        switch(parsed_args[1].toLowerCase()) {
                            case "aus truhe":
                                currentRaum.doTakeFromChest(Textie.itemMap.get(parsed_args[0].toUpperCase()));
                                break;
                            default:
                                printText("Unbekanntes Item: " + parsed_command[1]);
                                break;
                        }
                    }
                    else {
                        currentRaum.doNimm(itemToUse);
                    }
                    break;
                case Command.BENUTZE:
                    currentRaum.doBenutze(itemToUse);
                    break;
                case Command.UNTERSUCHE:
                    currentRaum.doUntersuche(parsed_command, count);
                    break;
                case Command.VERNICHTE:
                    currentRaum.doVernichte(itemToUse, count);
                    break;

                case Command.GEHE:
                    currentRaum.doGehen(parsed_command, count);
                    break;
                case Command.REDE:
                    currentHuman.doReden();
                    break;
                case Command.GIB:
                    currentHuman.doGeben(parsed_command, count);
                    break;
                default:
                    printText("Unbekannter Befehl: " + parsed_command[1]);
                    break;
            }
        }
    }

    public static void ende() {
        printText("Herzlichen Glückwunsch " + playerName + "!");
        printText("Du bist aus deinem Traum erwacht und siehst, dass du");
        printText("in deinem Bett liegst. Du spürst dein Herz stark und schnell schlagen");
        printText("bist aber froh, dass du aufwachen konntest.");
        System.exit(0);
    }

    public static void showIntro(String text) {
        if (text == null || text == "") {
            printText("\n\nWillkommen " + playerName + ".");
            printText("Falls du Hilfe bei der Bedienung brauchst, tippe \'hilfe\' ein.");
            playerName = IOUtils.readLine("\nWie ist dein Name? ");
            if(playerName == null || playerName == "") {
                playerName = "Fremder";
            }
        } else {
            printText("\n\nWillkommen " + playerName + ".");
            printText("Falls du Hilfe bei der Bedienung brauchst, tippe \'hilfe\' ein.");
        }

    }

    public static String[] parseInput(String command) {
        String[] result = command.split(" ", 2);
        return result;
    }

    private static void initHumans() {
        humanMap.put(Consts.ALTER_MANN, new Human(
                "Gordon", "Hast du die Truhe gesehen? Ich frage mich, was da wohl drin ist...", "Hast du irgendwo GabeN gesehen? Wir wollten uns treffen...",
                "Ich suche ein Brecheisen. Hast du eins?", "Sehr gut. Danke dir.", itemMap.get(Consts.BRECHEISEN), itemMap.get(Consts.SCHLÜSSEL)));
    }

    private static void initItems() {
        // TODO (Wenn wir den benutzeText der Items benutzen) Raumnummer
        // hinzufügen.
        // itemMap.put(Consts.KARTE, new Item("Karte", "Die Karte zeigt an, in welchem Raum man sich befindet.", "Du bist in Raum " +
        // currentRaum.getNumberAsString()));
        itemMap.put(Consts.FALLTÜR, new Item(Item.FALLTÜR, "Da ist eine Falltür", "Du schlüpfst durch die Falltür in den darunterliegenden Raum.", false));
        itemMap.put(Consts.WHITEBOARD, new Item(Item.WHITEBOARD, "Es steht \'FLIEH!\' mit Blut geschrieben darauf.", "Das fasse ich bestimmt nicht an!", false));
        itemMap.put(Consts.SCHALTER, new ToggleItem(
                Item.SCHALTER, "Da ist ein kleiner Schalter an der Wand.", "Du hörst ein Rumpeln, als du den Schalter drückst.", false,
                false));
        itemMap.put(Consts.TRUHE, new StorageItem(
                Item.TRUHE, "Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.", "Du kannst die Truhe nicht öffnen.", false, true, true, itemMap.get(Consts.STEIN), itemMap.get(Consts.HANDTUCH))); //TODO: fill in actual Items
        itemMap.put(Consts.STEIN, new Item(Item.STEIN, "Du betrachtest den Stein. Er wirkt kalt.", "Hier gibt es nichts um den Stein zu benutzen.", true));
        itemMap.put(Consts.SCHLÜSSEL, new Item(
                Item.SCHLÜSSEL, "Du betrachtest den Schlüssel. Was kann man damit wohl aufschließen?", "Hier gibt es nichts um den Schlüssel zu benutzen.", true));
        itemMap.put(Consts.FEUERZEUG, new Item(
                Item.FEUERZEUG, "Du betrachtest das Feuerzeug. Es wirkt zuverlässig.", "Du zündest deine Fackel mit dem Feuerzeug an.", true));
        itemMap.put(Consts.SCHWERT, new Item(
                Item.SCHWERT, "Du betrachtest das Schwert. Es sieht sehr scharf aus.", "Du stichst dir das Schwert zwischen die Rippen und stirbst.", true));
        itemMap.put(Consts.BRECHEISEN, new Item(
                Item.BRECHEISEN, "Da ist ein Brecheisen, es ist \"Gordon\" eingeritzt.", "Du kratzt dich mit dem Brecheisen am Kopf", true));
        itemMap.put(Consts.QUIETSCHEENTE, new Item(
                Item.QUIETSCHEENTE, "Die Ente schaut dich vorwurfsvoll an.", "Die Ente schaut dich vorwurfsvoll an und quietscht leise, als du sie zusammendrückst.",
                true));
        itemMap.put(Consts.HANDTUCH, new Item(Item.HANDTUCH, "Das Handtuch sieht sehr flauschig aus.", "Du wischst dir den Angstschweiß von der Stirn.", true));
        itemMap.put(Consts.FACKEL, new ToggleItem(
                Item.FACKEL, "Du betrachtest die Fackel. Wie kann man die wohl anzünden?", "Du zündest deine Fackel mit dem Feuerzeug an.", true, false));
        itemMap.put(Consts.SACK, new Item(
                Item.SACK, "Du betrachtest den Sack. Vielleicht kannst du ihn ja an deinem Rucksack befestigen.", "Du bindest den Sack an deinen Rucksack.", true));
    }

    public static void initRooms() {
        int counter = 1;
        Raum raum = new Raum1(inventory, counter++, itemMap.get(Consts.FACKEL), itemMap.get(Consts.HANDTUCH), itemMap.get(Consts.TRUHE), itemMap.get(Consts.SCHALTER));
        raumList.add(raum);
        raum = new Raum2(inventory, counter++, itemMap.get(Consts.SCHWERT), itemMap.get(Consts.FEUERZEUG), itemMap.get(Consts.STEIN));
        raumList.add(raum);
        raum = new Raum3(inventory, counter++, itemMap.get(Consts.QUIETSCHEENTE), itemMap.get(Consts.WHITEBOARD), itemMap.get(Consts.BRECHEISEN), itemMap.get(Consts.FALLTÜR));
        raumList.add(raum);
        raum = new Raum4(inventory, counter, humanMap.get(Consts.ALTER_MANN), itemMap.get(Consts.SCHALTER), itemMap.get(Consts.SACK));
        raumList.add(raum);
    }

    public static void printText(String text) {
        System.out.println(Textie.currentRaum == null ? text : "[" + Textie.currentRaum.roomNumber + "], " +  text);
    }

}
