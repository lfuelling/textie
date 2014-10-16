package de.micromata.azubi;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import java.io.Serializable;
import java.util.*;

/**
 * Created by tung on 26.09.14.
 */
public class Dungeon implements Serializable{
    private static final long serialVersionUID = -7870743513679247263L;
    public ArrayList<Raum> raums = new ArrayList<>();
    public int currentRoomNumber; //Index des aktuellen Raumes in der RaumListe
    public static final boolean ALIVE = true;
    public Map<String, Item> itemMap = new HashMap<>();
    public Map<String, Human> humanMap = new HashMap<>();
    public Human currentHuman;
    public Player player = new Player("Fremder", true);
    Raum raum;
    public int previousRoomNumber = 1; // Index des vorherigen Raumes in der RaumListe

    private static Dungeon dungeon;

    private Dungeon() {
    }

    public void init() {
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

    public void runGame(boolean withPrompt) {
        itemMap.put(Consts.KARTE, new Karte("Karte", "Das ist eine Karte, sie zeigt deinen Laufweg.", "Benutzetext wird bei benutzung geändert", true));
        Map<Richtung, Integer> verbindungen = new HashMap<>();
        verbindungen.put(Richtung.OST, 1);
        raums.add(new Raum(4, "Du kommst in einen hell erleuchteten Raum. Ein alter Mann lehnt an der Wand.", verbindungen, itemMap.get(Consts.SCHALTER), itemMap.get(Consts.SACK), itemMap.get(Consts.KARTE)));
        raums.trimToSize();
        currentRoomNumber = 1;
        getCurrentRaum().start(withPrompt);

        while (player.isAlive()) {
            if (getCurrentRaum().isLeaveRoom() == false) {
                continue;
            } else {
                raum.setLeaveRoom(false);
                raum.start(withPrompt);
            }
        }
        Textie.ende();
    }

    public void initRooms() {
        Map<Richtung, Integer> verbindungen = new HashMap<Richtung, Integer>();
        verbindungen.put(Richtung.SUED, 2);
        verbindungen.put(Richtung.WEST, 4);
        raum = new Raum(1, "Du befindest dich in einem dunklen Raum. Nach einiger Zeit gewöhnen sich deine Augen an die Dunkelheit.", verbindungen, itemMap.get(Consts.FACKEL), itemMap.get(Consts.HANDTUCH), itemMap.get(Consts.TRUHE), itemMap.get(Consts.SCHALTER));
        raums.add(raum);
        currentRoomNumber = 1;
        verbindungen = new HashMap<Richtung, Integer>();
        verbindungen.put(Richtung.NORD, 1);
        verbindungen.put(Richtung.WEST, 3);
        raum = new Raum(2, "Du kommst in einen dunklen Raum.", verbindungen, itemMap.get(Consts.SCHWERT), itemMap.get(Consts.FEUERZEUG), itemMap.get(Consts.STEIN));
        raums.add(raum);
        verbindungen = new HashMap<Richtung, Integer>();
        verbindungen.put(Richtung.FALLTUER, 4);
        verbindungen.put(Richtung.OST, 2);
        raum = new Raum(3, "Es ist zu dunkel, um etwas zu sehen. Ein seltsamer Geruch liegt in der Luft.", verbindungen, itemMap.get(Consts.QUIETSCHEENTE), itemMap.get(Consts.WHITEBOARD), itemMap.get(Consts.BRECHEISEN), itemMap.get(Consts.FALLTÜR));
        raums.add(raum);

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

    public Raum getCurrentRaum() {
        for (Raum raum : raums) {
            if (raum.roomNumber == this.currentRoomNumber) {
                return raum;
            }
        }
        return raums.get(0);
    }

    public void setCurrentRaum(Raum raum) {

    }

    public Raum getRaum(Richtung richtung) {
        Raum currentRaum = getCurrentRaum();
        Integer roomNr = currentRaum.getRaumNr(richtung);
        if (roomNr != null) {
                    /*
                    Hier Räume mit deren Nummern aufführen, die eine per Knopf verschlossene Tür haben
                    if(richtung == Richtung.RICHTUNG_IN_DER_DIE_TÜR_LIEGT){
                    checkSchalter(dungeon, richtung);
                    }
                    */
            switch (dungeon.getCurrentRaum().getNumber()) {
                case 1:
                    if (richtung == Richtung.WEST) {
                        if (checkSchalter()) {
                            Textie.printText("Du öffnest die Tür.");
                            currentRoomNumber = roomNr;
                            currentRaum.setLeaveRoom(true);
                        } else {
                            currentRaum.setLeaveRoom(false);
                            //es findet kein Raumwechsel statt
                        }
                    } else {
                        currentRoomNumber = roomNr;
                        currentRaum.setLeaveRoom(true);
                    }
                    break;
                case 4:
                    if (richtung == Richtung.OST) {
                        if (checkSchalter()) {
                            Textie.printText("Du öffnest die Tür.");
                            currentRoomNumber = roomNr;
                            currentRaum.setLeaveRoom(true);
                        } else {
                            currentRaum.setLeaveRoom(false);
                            //es findet kein Raumwechsel statt
                        }
                    } else {
                        currentRoomNumber = roomNr;
                        raum.setLeaveRoom(true);
                    }
                    break;
                default:
                    currentRoomNumber = roomNr;
                    currentRaum.setLeaveRoom(true);
                    break;
            }
        } else {
            Textie.printText("Du bist gegen die Wand gelaufen.");
        }
        previousRoomNumber = raums.indexOf(currentRaum);
        Karte karte;
        if (Dungeon.getDungeon().itemMap.get(Consts.KARTE).isKarte() == true) {
            karte = (Karte) Dungeon.getDungeon().itemMap.get(Consts.KARTE);
            karte.writeMap(currentRaum.getNumber(), richtung.toString());
        }
        if (currentRoomNumber == 4) {
            currentHuman = humanMap.get(Consts.ALTER_MANN);
        }
        return getNextRoom(currentRoomNumber);


    }

    private Raum getNextRoom(int currentRoomNumber) {
        for (Raum raum : raums) {
            if (raum.roomNumber == currentRoomNumber) {
                return raum;
            }
        }
        return raums.get(0);
    }

    private static boolean checkSchalter() {
        if (dungeon.itemMap.get(Consts.SCHALTER).isToggle()) {
            ToggleItem schalter = (ToggleItem) dungeon.itemMap.get(Consts.SCHALTER);
            if (schalter.getState() == false) {
                Textie.printText("Da ist eine Tür, du versuchst sie zu öffnen, doch es geht nicht.");
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void setRoomNumber(Raum raum) {
        this.currentRoomNumber = raums.indexOf(raum) + 1;
    }

    public ArrayList<Raum> getRaums() {
        return raums;
    }

    public void setRaums(ArrayList<Raum> raums) {
        this.raums = raums;
    }

    public Map<String, Item> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<String, Item> itemMap) {
        this.itemMap = itemMap;
    }

    public Map<String, Human> getHumanMap() {
        return humanMap;
    }

    public void setHumanMap(Map<String, Human> humanMap) {
        this.humanMap = humanMap;
    }

    /*
    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    */
    public static void setDungeon(Dungeon dungeon) {
        Dungeon.dungeon = dungeon;

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
