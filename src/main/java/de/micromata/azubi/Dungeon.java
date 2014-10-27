package de.micromata.azubi;

import javax.xml.soap.Text;
import java.io.Serializable;
import java.util.*;

/**
 * Created by tung on 26.09.14.
 */
public class Dungeon implements Serializable{
    private static final long serialVersionUID = -7870743513679247263L;
    public ArrayList<Raum> raums;
    public int currentRoomNumber; //Index des aktuellen Raumes in der RaumListe
    //public Map<String, Item> itemMap = new HashMap<>();
    public Map<String, Human> humanMap;
    public Human currentHuman;
    public Player player;
    Raum raum;
    public int previousRoomNumber; // Index des vorherigen Raumes in der RaumListe
    public StorageItem truhe;

    private static Dungeon dungeon;

    private Dungeon() {
        init();
    }

    public void init() {
        player = new Player("Fremder", true);
        previousRoomNumber = 1;

        initRooms();
        initInventories();
        initHumans();
        initVerbindungen();

        player.getInventory().setInventorySize(5);
        truhe = (StorageItem) findRaumByNummer(1).getInventory().findItemByName("Truhe");
        Inventory inventory = new Inventory();
        //inventory.getInventory().add();
        //TODO Items einfügen
        //inventory.getInventory().add();
        truhe.setInventory(inventory);
    }

    public static Dungeon getDungeon() {
        if (dungeon == null) {
            dungeon = new Dungeon();
        }
        return dungeon;
    }

    public void runGame(boolean withPrompt) {
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
        raums = new ArrayList<>();
        raum = new Raum(1, "Du befindest dich in einem dunklen Raum. Nach einiger Zeit gewöhnen sich deine Augen an die Dunkelheit.");
        raums.add(raum);
        currentRoomNumber = 1;
        raum = new Raum(2, "Du kommst in einen dunklen Raum.");
        raums.add(raum);
        raum = new Raum(3, "Es ist zu dunkel, um etwas zu sehen. Ein seltsamer Geruch liegt in der Luft.");
        raums.add(raum);
        raum = new Raum(4, "Du kommst in einen hell erleuchteten Raum. Ein alter Mann lehnt an der Wand.");
        raums.add(raum);
        raum = new Raum(5, "RAUM 5");
        raums.add(raum);
        raum = new Raum(6, "RAUM 6");
        raums.add(raum);
        raum = new Raum(7, "RAUM 7");
        raums.add(raum);

    }

    public void initVerbindungen(){
        //Raum 1
        Map<Richtung, Raum> verbindungen = new HashMap<>();
        verbindungen.put(Richtung.SUED, raums.get(2));
        verbindungen.put(Richtung.WEST, raums.get(4));
        findRaumByNummer(1).setVerbindungen(verbindungen);

        //Raum 2
        verbindungen = new HashMap<Richtung, Raum>();
        verbindungen.put(Richtung.NORD, raums.get(1));
        verbindungen.put(Richtung.WEST, raums.get(3));
        findRaumByNummer(2).setVerbindungen(verbindungen);

        //Raum 3
        verbindungen = new HashMap<Richtung, Raum>();
        verbindungen.put(Richtung.FALLTUER, raums.get(4));
        verbindungen.put(Richtung.OST, raums.get(2));
        findRaumByNummer(3).setVerbindungen(verbindungen);

        //Raum 4
        verbindungen = new HashMap<Richtung, Raum>();
        verbindungen.put(Richtung.OST, findRaumByNummer(1));
        verbindungen.put(Richtung.WEST,findRaumByNummer(5));
        verbindungen.put(Richtung.NORD, findRaumByNummer(7));
        findRaumByNummer(4).setVerbindungen(verbindungen);

        //Raum 5
        verbindungen = new HashMap<Richtung, Raum>();
        verbindungen.put(Richtung.OST, findRaumByNummer(4));
        verbindungen.put(Richtung.NORD, findRaumByNummer(6));
        findRaumByNummer(5).setVerbindungen(verbindungen);

        //Raum 6
        verbindungen = new HashMap<Richtung, Raum>();
        verbindungen.put(Richtung.OST, findRaumByNummer(7));
        findRaumByNummer(6).setVerbindungen(verbindungen);

        //Raum 7
        verbindungen = new HashMap<Richtung, Raum>();
        verbindungen.put(Richtung.SUED, raums.get(4));
        verbindungen.put(Richtung.WEST, raums.get(6));
        findRaumByNummer(7).setVerbindungen(verbindungen);
    }

    public void initInventories(){
        // Raum 1
        Inventory inventory = new Inventory();
        inventory.getInventory().add(new ToggleItem(Item.FACKEL, "Du betrachtest die Fackel. Wie kann man die wohl anzünden?", "Du zündest deine Fackel mit dem Feuerzeug an.", true, false));
        inventory.getInventory().add(new Item(Item.HANDTUCH, "Das Handtuch sieht sehr flauschig aus.", "Du wischst dir den Angstschweiß von der Stirn.", true));
        inventory.getInventory().add(new ToggleItem(Item.SCHALTER, "Da ist ein kleiner Schalter an der Wand.", "Du hörst ein Rumpeln, als du den Schalter drückst.", false,false));
        inventory.getInventory().add(new StorageItem(Item.TRUHE, "Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.", "Du kannst die Truhe nicht öffnen.", false, true, true, null, null)); //TODO Items einfügen
        findRaumByNummer(1).setInventory(inventory);

        //Raum 2
        inventory = new Inventory();
        inventory.getInventory().add(new Item(Item.STEIN, "Du betrachtest den Stein. Er wirkt kalt.", "Hier gibt es nichts um den Stein zu benutzen.", true));
        inventory.getInventory().add(new Item(Item.SCHWERT, "Du betrachtest das Schwert. Es sieht sehr scharf aus.", "Du stichst dir das Schwert zwischen die Rippen und stirbst.", true));
        inventory.getInventory().add(new Item(Item.FEUERZEUG, "Du betrachtest das Feuerzeug. Es wirkt zuverlässig.", "Du zündest deine Fackel mit dem Feuerzeug an.", true));
        findRaumByNummer(2).setInventory(inventory);

        //Raum 3
        inventory = new Inventory();
        inventory.getInventory().add(new Item(Item.FALLTÜR, "Da ist eine Falltür", "Du schlüpfst durch die Falltür in den darunterliegenden Raum.", false));
        inventory.getInventory().add(new Item(Item.WHITEBOARD, "Es steht \'FLIEH!\' mit Blut geschrieben darauf.", "Das fasse ich bestimmt nicht an!", false));
        inventory.getInventory().add(new Item(Item.BRECHEISEN, "Da ist ein Brecheisen, es ist \"Gordon\" eingeritzt.", "Du kratzt dich mit dem Brecheisen am Kopf", true));
        inventory.getInventory().add(new Item(Item.QUIETSCHEENTE, "Die Ente schaut dich vorwurfsvoll an.", "Die Ente schaut dich vorwurfsvoll an und quietscht leise, als du sie zusammendrückst.", true));
        findRaumByNummer(3).setInventory(inventory);

        //Raum 4
        inventory = new Inventory();
        inventory.getInventory().add(new Karte("Karte", "Das ist eine Karte, sie zeigt deinen Laufweg.", "Benutzetext wird bei benutzung geändert", true));
        inventory.getInventory().add(new Item(Item.SACK, "Du betrachtest den Sack. Vielleicht kannst du ihn ja an deinem Rucksack befestigen.", "Du bindest den Sack an deinen Rucksack.", true));
        inventory.getInventory().add(findRaumByNummer(1).getInventory().findItemByName("Schalter")); // Der SELBE Schalter wie in Raum1
        findRaumByNummer(4).setInventory(inventory);

        //Raum 5
        inventory = new Inventory();
        inventory.getInventory().add(new Item(Item.FALLTÜR, "Da ist eine Falltür", "Du schlüpfst durch die Falltür in den darunterliegenden Raum.", false));
        findRaumByNummer(5).setInventory(inventory);

        //Raum 6
        inventory = new Inventory();
        inventory.getInventory().add(new StorageItem(Item.TRUHE, "Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.", "Du kannst die Truhe nicht öffnen.", false, true, true, null, null));
        findRaumByNummer(6).setInventory(inventory);

        //Raum 7
        inventory = new Inventory();
        findRaumByNummer(7).setInventory(inventory);
    }

    private void initItems() {
        // TODO (Wenn wir den benutzeText der Items benutzen) Raumnummer
        // hinzufügen.
        //itemMap.put(Consts.KARTE, karte);
        //itemMap.put(Consts.FALLTÜR, new Item(Item.FALLTÜR, "Da ist eine Falltür", "Du schlüpfst durch die Falltür in den darunterliegenden Raum.", false));
        //itemMap.put(Consts.WHITEBOARD, new Item(Item.WHITEBOARD, "Es steht \'FLIEH!\' mit Blut geschrieben darauf.", "Das fasse ich bestimmt nicht an!", false));
        //itemMap.put(Consts.SCHALTER, new ToggleItem(Item.SCHALTER, "Da ist ein kleiner Schalter an der Wand.", "Du hörst ein Rumpeln, als du den Schalter drückst.", false,false));
        //itemMap.put(Consts.TRUHE, new StorageItem(Item.TRUHE, "Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.", "Du kannst die Truhe nicht öffnen.", false, true, true, itemMap.get(Consts.STEIN), itemMap.get(Consts.HANDTUCH))); //TODO: fill in actual Items
        //itemMap.put(Consts.STEIN, new Item(Item.STEIN, "Du betrachtest den Stein. Er wirkt kalt.", "Hier gibt es nichts um den Stein zu benutzen.", true));
        //itemMap.put(Consts.SCHLÜSSEL, new Item(Item.SCHLÜSSEL, "Du betrachtest den Schlüssel. Was kann man damit wohl aufschließen?", "Hier gibt es nichts um den Schlüssel zu benutzen.", true));
        //itemMap.put(Consts.FEUERZEUG, new Item(Item.FEUERZEUG, "Du betrachtest das Feuerzeug. Es wirkt zuverlässig.", "Du zündest deine Fackel mit dem Feuerzeug an.", true));
        //itemMap.put(Consts.SCHWERT, new Item(Item.SCHWERT, "Du betrachtest das Schwert. Es sieht sehr scharf aus.", "Du stichst dir das Schwert zwischen die Rippen und stirbst.", true));
        //itemMap.put(Consts.BRECHEISEN, new Item(Item.BRECHEISEN, "Da ist ein Brecheisen, es ist \"Gordon\" eingeritzt.", "Du kratzt dich mit dem Brecheisen am Kopf", true));
        //itemMap.put(Consts.QUIETSCHEENTE, new Item(Item.QUIETSCHEENTE, "Die Ente schaut dich vorwurfsvoll an.", "Die Ente schaut dich vorwurfsvoll an und quietscht leise, als du sie zusammendrückst.", true));
        //itemMap.put(Consts.HANDTUCH, new Item(Item.HANDTUCH, "Das Handtuch sieht sehr flauschig aus.", "Du wischst dir den Angstschweiß von der Stirn.", true));
        //itemMap.put(Consts.FACKEL, new ToggleItem(Item.FACKEL, "Du betrachtest die Fackel. Wie kann man die wohl anzünden?", "Du zündest deine Fackel mit dem Feuerzeug an.", true, false));
        //itemMap.put(Consts.SACK, new Item(Item.SACK, "Du betrachtest den Sack. Vielleicht kannst du ihn ja an deinem Rucksack befestigen.", "Du bindest den Sack an deinen Rucksack.", true));
        //itemMap.put(Consts.KARTE, new Karte("Karte", "Das ist eine Karte, sie zeigt deinen Laufweg.", "Benutzetext wird bei benutzung geändert", true));
    }

    public void setCurrentHuman(Human hts) {
        currentHuman = hts;
    }


    private void initHumans() {
        humanMap = new HashMap<>();
        humanMap.put(Consts.ALTER_MANN, new Human(
                "Gordon", "Hast du die Truhe gesehen? Ich frage mich, was da wohl drin ist...", "...",
                "Ich suche ein Brecheisen. Hast du eins?", "Sehr gut. Danke dir.",
                new Item(Item.SCHLÜSSEL, "Du betrachtest den Schlüssel. Was kann man damit wohl aufschließen?", "Hier gibt es nichts um den Schlüssel zu benutzen.", true),
                findRaumByNummer(3).getInventory().findItemByName("Brecheisen")));
    }

    public Raum getCurrentRaum() {
        for (Raum raum : raums) {
            if (raum.roomNumber == this.currentRoomNumber) {
                return raum;
            }
        }
        return raums.get(0);
    }

    public Raum findRaumByNummer(int raumNummer){
        for (Raum raum : raums) {
            if (raum.roomNumber == raumNummer) {
                return raum;
            }
        }
        return null;
    }




    public void setCurrentRaum(Raum raum) {

    }

    public Raum getRaum(Richtung richtung) {
        Raum currentRaum = getCurrentRaum();
        Raum nextRoom = currentRaum.getNextRoom(richtung);
        if (nextRoom != null) {
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
                            currentRoomNumber = nextRoom.getRoomNumber();
                            currentRaum.setLeaveRoom(true);
                        } else {
                            currentRaum.setLeaveRoom(false);
                            //es findet kein Raumwechsel statt
                        }
                    } else {
                        currentRoomNumber = nextRoom.getRoomNumber();
                        currentRaum.setLeaveRoom(true);
                    }
                    break;
                case 4:
                    if (richtung == Richtung.OST) {
                        if (checkSchalter()) {
                            Textie.printText("Du öffnest die Tür.");
                            currentRoomNumber = nextRoom.getRoomNumber();
                            currentRaum.setLeaveRoom(true);
                        } else {
                            currentRaum.setLeaveRoom(false);
                            //es findet kein Raumwechsel statt
                        }
                    } else {
                        currentRoomNumber = nextRoom.getRoomNumber();
                        raum.setLeaveRoom(true);
                    }
                    break;
                default:
                    currentRoomNumber = nextRoom.getRoomNumber();
                    currentRaum.setLeaveRoom(true);
                    break;
            }

        previousRoomNumber = raums.indexOf(currentRaum);
        Karte karte;
        if (dungeon.player.getInventory().findItemByName("Karte") != null){
            karte = (Karte) dungeon.player.getInventory().findItemByName("Karte");
            karte.writeMap(currentRaum.getNumber(), richtung.toString());
        }
        else if(dungeon.findRaumByNummer(4).getInventory().findItemByName("Karte") != null){
            karte = (Karte) dungeon.findRaumByNummer(4).getInventory().findItemByName("Karte");
            karte.writeMap(currentRaum.getNumber(), richtung.toString());
        }
        if (currentRoomNumber == 4) {
            currentHuman = humanMap.get(Consts.ALTER_MANN);
        }
        return getNextRoom(currentRoomNumber);

        } else {
            Textie.printText("Du bist gegen die Wand gelaufen.");
        }
        return null;
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
        if (Textie.chooseInventory("Schalter").isToggle()) {
            ToggleItem schalter = (ToggleItem) Textie.chooseInventory("Schalter");
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
