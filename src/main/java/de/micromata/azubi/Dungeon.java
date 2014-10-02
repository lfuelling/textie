package de.micromata.azubi;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import java.util.*;

/**
 * Created by tung on 26.09.14.
 */
public class Dungeon {
  public LinkedList<Raum> raums = new LinkedList<>();
  public Raum currentRaum;
  public static final boolean ALIVE = true;
  public Map<String, Item> itemMap = new HashMap<>();
  public Map<String, Human> humanMap = new HashMap<>();
  public Inventory inventory = new Inventory();
  public Human currentHuman;
  public Player player = new Player(inventory, currentRaum, "Fremder", true);
  String savegame;
  Raum raum;

  private static Dungeon dungeon;

   private Dungeon() {
    }

   public void init(){
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

  public void runGame(boolean withPrompt) {
    itemMap.put(Consts.KARTE, new Karte("Karte", "Das ist eine Karte, sie zeigt deinen Laufweg.", "Benutzetext wird bei benutzung geändert", true));
            raums.add(new Raum(inventory, 4,"falltür", "ost", itemMap.get(Consts.SCHALTER), itemMap.get(Consts.SACK), itemMap.get(Consts.KARTE)) {

                boolean east = false;

                public void start(boolean withPrompt) {
                    east = false;
                    Textie.printText("Du kommst in einen hell erleuchteten Raum. Ein alter Mann lehnt an der Wand.");
                    Textie.warten(withPrompt);
                }

             
            });
    currentRaum = raums.getFirst();
    currentRaum.start(withPrompt);

    while (player.isAlive()) {
      if (currentRaum.isFinished() == 0) {
        continue;
      }
      else if(currentRaum.isFinished() == 1) {
          if (raums.listIterator(currentRaum.roomNumber).hasNext()) {
              currentRaum = raums.listIterator(currentRaum.roomNumber).next();
          } else {
//              listIterator = raums.listIterator(1);
              currentRaum = raums.getFirst();
          }
      }
        else if(currentRaum.isFinished() == -1) {
          if (raums.listIterator(currentRaum.roomNumber - 1).hasPrevious()) {
              currentRaum = raums.listIterator(currentRaum.roomNumber-1).previous();
          } else {
              currentRaum = raums.getLast();
          }
      }
      if (currentRaum.roomNumber == 4) {
        setCurrentHuman(humanMap.get(Consts.ALTER_MANN));
      } else {
        setCurrentHuman(null);
      }
      currentRaum.setFinished(0);
      currentRaum.start(withPrompt);
    }
    Textie.ende();
  }

  public void initRooms() {
    int counter = 1;
    raum = new Raum(inventory, counter ++, "west", "süd", itemMap.get(Consts.FACKEL), itemMap.get(Consts.HANDTUCH), itemMap.get(Consts.TRUHE), itemMap.get(Consts.SCHALTER)) {

        @Override
        public void start(boolean withPrompt) {
            Textie.printText("Du befindest dich in einem dunklen Raum. Nach einiger Zeit gewöhnen sich deine Augen an die Dunkelheit.");
            finished = 0;
            Textie.warten(withPrompt);
        }
    };
    raums.add(raum);
    currentRaum = raums.getFirst();
    raum = new Raum(inventory, counter++, "nord", "west", itemMap.get(Consts.SCHWERT), itemMap.get(Consts.FEUERZEUG), itemMap.get(Consts.STEIN)) {
        boolean west = false;
        boolean north = false;

        public void start(boolean withPrompt) {
            west = false;
            north = false;
            System.out.println("Du kommst in einen dunklen Raum.");
            Textie.warten(withPrompt);
        }
    };
    raums.add(raum);
    raum = new Raum(inventory, counter++, "ost", "falltür", itemMap.get(Consts.QUIETSCHEENTE), itemMap.get(Consts.WHITEBOARD), itemMap.get(Consts.BRECHEISEN), itemMap.get(Consts.FALLTÜR)) {
        boolean east = false;

        public void start(boolean withPrompt) {
            east = false;
            ToggleItem fackel = (ToggleItem) Dungeon.getDungeon().itemMap.get(Consts.FACKEL);
            if(fackel.getState() == true) {
                Textie.printText("Ein Windstoß sorgt dafür, dass die Fackel ausgeht.");
                fackel.setState(false);
            }
            Textie.printText("Es ist zu dunkel, um etwas zu sehen. Ein seltsamer Geruch liegt in der Luft.");

            Textie.warten(withPrompt);
        }
    };
    raums.add(raum);
   /*
   raum = new Raum(inventory, counter, itemMap.get(Consts.SCHALTER), itemMap.get(Consts.SACK), karte) {

        boolean east = false;
        public void start(boolean withPrompt) {
            east = false;
            Dungeon.getDungeon().printText("Du kommst in einen hell erleuchteten Raum. Ein alter Mann lehnt an der Wand.");
            warten(withPrompt);
        }

        @Override
        public int isFinished() {
            // east
            if (east) {
                return 1;
            }
            else {
                return 0;
            }
        }

        @Override
        public void goEast() {
            ToggleItem schalter;
            if (Dungeon.getDungeon().itemMap.get(Consts.SCHALTER).isToggle() == true) {
                schalter = (ToggleItem) Dungeon.getDungeon().itemMap.get(Consts.SCHALTER);

                if (schalter.getState() == true) {
                    east = true;
                    Dungeon.getDungeon().printText("Da ist eine Tür. Du öffnest sie und gehst die Steintreppe dahinter hoch.");
                } else {
                    Dungeon.getDungeon().printText("Da ist eine Tür. Du versuchst sie zu öffnen, doch es geht nicht.");
                }
            } else {
                Dungeon.getDungeon().printText("Da ist eine Tür. Du versuchst sie zu öffnen, doch es geht nicht.");
            }
        }
    };*/
    //raums.add(raum);
  }

  private void initItems() {
    // TODO (Wenn wir den benutzeText der Items benutzen) Raumnummer
    // hinzufügen.
    //itemMap.put(Consts.KARTE, karte);
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

  public void setCurrentHuman(Human hts) {
    currentHuman = hts;
  }


  private void initHumans() {
    humanMap.put(Consts.ALTER_MANN, new Human(
        "Gordon", "Hast du die Truhe gesehen? Ich frage mich, was da wohl drin ist...", "...",
        "Ich suche ein Brecheisen. Hast du eins?", "Sehr gut. Danke dir.", itemMap.get(Consts.SCHLÜSSEL), itemMap.get(Consts.BRECHEISEN)));
  }

    public LinkedList<Raum> getRaums() {
        return raums;
    }

    public void setRaums(LinkedList<Raum> raums) {
        this.raums = raums;
    }

}
