package de.micromata.azubi;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by tung on 26.09.14.
 */
public class Dungeon {
  public LinkedList<Raum> raums = new LinkedList<>();
  public Raum currentRaum;
  public static final boolean ALIVE = true;
  public Map<String, Item> itemMap = new HashMap<>();
  public Map<String, Human> humanMap = new HashMap<>();
  public Inventory inventory = new Inventory(ALIVE);
  public Human currentHuman;
  public Player player = new Player(inventory, currentRaum, "Fremder", true);

    private static Dungeon dungeon;

  private Dungeon() {
    initItems();
    initHumans(); // Humans benötigen Items
    initRooms();
  }

  public static Dungeon getDungeon() {
    if (dungeon == null) {
      dungeon = new Dungeon();
    }
    return dungeon;
  }

    public static void setDungeon(Dungeon dungeon) {
        Dungeon.dungeon = dungeon;
    }

    public void prompt() {
    do {
      currentRaum.falltuerUsed = false;
      String command = IOUtils.readLine("Was willst du tun? ");
      String[] parsed_command =  Dungeon.getDungeon().parseInput(command);
      String[] parsed_args = new String[2];
      if(parsed_command[1] == null) {
        parsed_args[0] = "nichts";
      } else {
        parsed_args =  Dungeon.getDungeon().parseInput(parsed_command[1]);
      }
      executeCommand(parsed_command, parsed_args);
    } while (!currentRaum.isFinished());
  }

  public void runGame(boolean withPrompt) {
    currentRaum = raums.getFirst();
    ListIterator<Raum> listIterator = raums.listIterator(1);
    currentRaum.start(withPrompt);

    while (player.isAlive()) {
      if (currentRaum.isFinished() == false) {
        continue;
      }
      if(listIterator.hasNext()) {
        currentRaum = listIterator.next();
      } else {
        listIterator = raums.listIterator(1);
        currentRaum = raums.getFirst();
      }
      if (currentRaum.roomNumber == 4) {
        setCurrentHuman(humanMap.get(Consts.ALTER_MANN));
      } else {
        setCurrentHuman(null);
      }
      currentRaum.start(withPrompt);
    }
    ende();
  }

  public void executeCommand(String[] parsed_command, String[] parsed_args) {
    if (currentRaum == null) {
      System.err.println("currentRaum nicht da");
      // Kein raum nichts tun
      return;
    }
    int count = 0;
    int args = 0;
    for (String aParsed_command : parsed_command) {
      if(aParsed_command != null) {
        count++;
      }
    }
    for (String parsed_arg : parsed_args) {
      if(parsed_arg != null) {
        args++;
      }
    }
    if(parsed_command.length < 2) {
      if(parsed_command[0].equals(Command.HILFE)) {
        currentRaum.printHelp();
      } else {
        printText("Unbekannter Befehl oder fehlende Argumente: " + parsed_command[0]);
      }
    } else {
      Item itemToUse = itemMap.get(parsed_command[1].toUpperCase());
      switch (parsed_command[0]) {
        case Command.HILFE:
          currentRaum.printHelp();
          break;
        case Command.NIMM:
          if(args > 1) { // (ACHTUNG: auch bei "nimm blauen hut" wird mehr als ein Argument erkannt)
            switch (parsed_args[1].toLowerCase()) {
              case "aus truhe":
                currentRaum.doTakeFromChest(itemMap.get(parsed_args[0].toUpperCase()));
                break;
              default:
                printText("Unbekanntes Item: " + parsed_command[1]);
                break;
            }
          } else {
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
          printText("Unbekannter Befehl: " + parsed_command[0]);
          break;
      }
    }
  }

  public void initRooms() {
    int counter = 1;
    Raum raum = new Raum1(inventory, counter++, itemMap.get(Consts.FACKEL), itemMap.get(Consts.HANDTUCH), itemMap.get(Consts.TRUHE), itemMap.get(Consts.SCHALTER));
    raums.add(raum);
    currentRaum = raum;
    raum = new Raum2(inventory, counter++, itemMap.get(Consts.SCHWERT), itemMap.get(Consts.FEUERZEUG), itemMap.get(Consts.STEIN));
    raums.add(raum);
    raum = new Raum3(inventory, counter++, itemMap.get(Consts.QUIETSCHEENTE), itemMap.get(Consts.WHITEBOARD), itemMap.get(Consts.BRECHEISEN), itemMap.get(Consts.FALLTÜR));
    raums.add(raum);
    raum = new Raum4(inventory, counter, itemMap.get(Consts.SCHALTER), itemMap.get(Consts.SACK));
    raums.add(raum);


  }

  private void initItems() {
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

  public boolean ende() {
    printText("Herzlichen Glückwunsch !");
    printText("Du bist aus deinem Traum erwacht und siehst, dass du");
    printText("in deinem Bett liegst. Du spürst dein Herz stark und schnell schlagen");
    printText("und bist froh, dass du aufgewacht bist.");
    return true;
  }

  public void printText(String text) {
    System.out.println(currentRaum == null ? text : "[" + currentRaum.roomNumber + "], " + text);
  }

  public void setCurrentHuman(Human hts) {
    currentHuman = hts;
  }


  private void initHumans() {
    humanMap.put(Consts.ALTER_MANN, new Human(
        "Gordon", "Hast du die Truhe gesehen? Ich frage mich, was da wohl drin ist...", "...",
        "Ich suche ein Brecheisen. Hast du eins?", "Sehr gut. Danke dir.", itemMap.get(Consts.BRECHEISEN), itemMap.get(Consts.SCHLÜSSEL)));
  }

  public String[] parseInput(String command) {
    return command.split(" ", 2);
  }

}
