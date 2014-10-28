package de.micromata.azubi;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tung Ngo (t.ngo@micromata.de)
 * @author Lukas Fülling (l.fuelling@micromata.de)
 * @author Julian Siebert (j.siebert@micromata.de)
 * @see java.io.Serializable
 */
public class Dungeon implements Serializable {
    private static final long serialVersionUID = -7870743513679247263L;
    public ArrayList<Raum> raums;
    public ArrayList<Door> doors;
    public int currentRoomNumber; //Index des aktuellen Raumes in der RaumListe
    public Player player;
    Raum raum;
    Door door;
    public int previousRoomNumber; // Index des vorherigen Raumes in der RaumListe
    private StorageItem truhe;

    private static Dungeon dungeon;

    private Dungeon() {
        init();
    }


    /**
     * Initializes the game
     */
    public void init() {
        player = new Player("Fremder", true);
        previousRoomNumber = 1;

        initRooms();
        initInventories();
        initHumans();
        initVerbindungen();
        player.getInventory().setInventorySize(5);
    }

    public static Dungeon getDungeon() {
        if (dungeon == null) {
            dungeon = new Dungeon();
        }
        return dungeon;
    }

    /**
     * Starts the game.
     *
     * @param withPrompt Set to <code>true</code>, if you want a prompt.
     */
    public void runGame(boolean withPrompt) {
        currentRoomNumber = 1;
        getCurrentRaum().start(withPrompt);

        while (player.isAlive()) {
            if (getCurrentRaum().isLeaveRoom() == false) {
                continue;
            } else {
                raum = getNextRoom(currentRoomNumber);
                raum.setLeaveRoom(false);
                raum.start(withPrompt);
            }
        }
        Textie.ende();
    }

    /**
     * Initializes the rooms.
     */
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
        raum = new Raum(5, "Du kommst in einen Raum, in dem eine Junge steht.");
        raums.add(raum);
        raum = new Raum(6, "Du kommst in einen Raum mit einer Truhe.");
        raums.add(raum);
        raum = new Raum(7, "Du kommst in einen Raum, eine Frau steht mitten im Raum.");
        raums.add(raum);
    }

    /**
     * Initializes the connections between the rooms.
     */
    public void initVerbindungen() {
        //Raum 1
        Map<Richtung, Raum> verbindungen = new HashMap<>();
        verbindungen.put(Richtung.SUED, findRaumByNummer(2));
        verbindungen.put(Richtung.WEST, findRaumByNummer(4));
        findRaumByNummer(1).setVerbindungen(verbindungen);

        //Raum 2
        verbindungen = new HashMap<Richtung, Raum>();
        verbindungen.put(Richtung.NORD, findRaumByNummer(1));
        verbindungen.put(Richtung.WEST, findRaumByNummer(3));
        findRaumByNummer(2).setVerbindungen(verbindungen);

        //Raum 3
        verbindungen = new HashMap<Richtung, Raum>();
        verbindungen.put(Richtung.FALLTUER, findRaumByNummer(4));
        verbindungen.put(Richtung.OST, findRaumByNummer(2));
        findRaumByNummer(3).setVerbindungen(verbindungen);

        //Raum 4
        verbindungen = new HashMap<Richtung, Raum>();
        verbindungen.put(Richtung.OST, findRaumByNummer(1));
        verbindungen.put(Richtung.WEST, findRaumByNummer(5));
        verbindungen.put(Richtung.NORD, findRaumByNummer(7));
        findRaumByNummer(4).setVerbindungen(verbindungen);

        //Raum 5
        verbindungen = new HashMap<Richtung, Raum>();
        verbindungen.put(Richtung.FALLTUER, findRaumByNummer(6));
        verbindungen.put(Richtung.OST, findRaumByNummer(4));
        findRaumByNummer(5).setVerbindungen(verbindungen);

        //Raum 6
        verbindungen = new HashMap<Richtung, Raum>();
        verbindungen.put(Richtung.OST, findRaumByNummer(7));
        findRaumByNummer(6).setVerbindungen(verbindungen);

        //Raum 7
        verbindungen = new HashMap<Richtung, Raum>();
        verbindungen.put(Richtung.SUED, findRaumByNummer(4));
        verbindungen.put(Richtung.WEST, findRaumByNummer(6));
        findRaumByNummer(7).setVerbindungen(verbindungen);
    }

    /**
     * Initializes the inventories of the rooms.
     */
    public void initInventories() {
        // Raum 1
        Inventory inventory = new Inventory();
        inventory.getInventory().add(new ToggleItem(1, Item.FACKEL, "Du betrachtest die Fackel. Wie kann man die wohl anzünden?", "Du zündest deine Fackel mit dem Feuerzeug an.", true, false));
        inventory.getInventory().add(new Item(2, Item.HANDTUCH, "Das Handtuch sieht sehr flauschig aus.", "Du wischst dir den Angstschweiß von der Stirn.", true));
        inventory.getInventory().add(new ToggleItem(3, Item.SCHALTER, "Da ist ein kleiner Schalter an der Wand.", "Du hörst ein Rumpeln, als du den Schalter drückst.", false, false));
        inventory.getInventory().add(new StorageItem(4, Item.TRUHE, "Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.", "Du kannst die Truhe nicht öffnen.", false, true, true));
        findRaumByNummer(1).setInventory(inventory);

        //Raum 2
        inventory = new Inventory();
        inventory.getInventory().add(new Item(5, Item.STEIN, "Du betrachtest den Stein. Er wirkt kalt.", "Hier gibt es nichts um den Stein zu benutzen.", true));
        inventory.getInventory().add(new Item(6, Item.SCHWERT, "Du betrachtest das Schwert. Es sieht sehr scharf aus.", "Du stichst dir das Schwert zwischen die Rippen und stirbst.", true));
        inventory.getInventory().add(new Item(7, Item.FEUERZEUG, "Du betrachtest das Feuerzeug. Es wirkt zuverlässig.", "Du zündest deine Fackel mit dem Feuerzeug an.", true));
        findRaumByNummer(2).setInventory(inventory);

        //Raum 3
        inventory = new Inventory();
        inventory.getInventory().add(new Item(8, Item.FALLTÜR, "Da ist eine Falltür", "Du schlüpfst durch die Falltür in den darunterliegenden Raum.", false));
        inventory.getInventory().add(new Item(9, Item.WHITEBOARD, "Es steht \'FLIEH!\' mit Blut geschrieben darauf.", "Das fasse ich bestimmt nicht an!", false));
        inventory.getInventory().add(new Item(10, Item.BRECHEISEN, "Da ist ein Brecheisen, es ist \"Gordon\" eingeritzt.", "Du kratzt dich mit dem Brecheisen am Kopf", true));
        inventory.getInventory().add(new Item(11, Item.QUIETSCHEENTE, "Die Ente schaut dich vorwurfsvoll an.", "Die Ente schaut dich vorwurfsvoll an und quietscht leise, als du sie zusammendrückst.", true));
        findRaumByNummer(3).setInventory(inventory);

        //Raum 4
        inventory = new Inventory();
        inventory.getInventory().add(new Karte(20, "Karte", "Das ist eine Karte, sie zeigt deinen Laufweg.", "Benutzetext wird bei benutzung geändert"));
        inventory.getInventory().add(new Item(12, Item.SACK, "Du betrachtest den Sack. Vielleicht kannst du ihn ja an deinem Rucksack befestigen.", "Du bindest den Sack an deinen Rucksack.", true));
        inventory.getInventory().add(findRaumByNummer(1).getInventory().findItemByName("Schalter")); // Der SELBE Schalter wie in Raum1
        findRaumByNummer(4).setInventory(inventory);

        //Raum 5
        inventory = new Inventory();

        inventory.getInventory().add(new Item(13, Item.FALLTÜR, "Da ist eine Falltür", "Du schlüpfst durch die Falltür in den darunterliegenden Raum.", false));
        findRaumByNummer(5).setInventory(inventory);

        //Raum 6
        inventory = new Inventory();
        inventory.getInventory().add(new StorageItem(14, Item.TRUHE, "Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.", "Du kannst die Truhe nicht öffnen.", false, true, true));
        findRaumByNummer(6).setInventory(inventory);

        //Raum 7
        inventory = new Inventory();
        inventory.getInventory().add(new ToggleItem(15, Item.SCHALTER, "Da ist ein kleiner Schalter an der Wand.", "Du hörst ein Rumpeln, als du den Schalter drückst.", false, false));
        findRaumByNummer(7).setInventory(inventory);

        //Truhe Raum1
        truhe = (StorageItem) findRaumByNummer(1).getInventory().findItemByName("Truhe");
        inventory = new Inventory();
        //inventory.getInventory().add();
        //TODO Items einfügen
        //inventory.getInventory().add();
        truhe.setInventory(inventory);

        //Truhe Raum 6
        truhe = (StorageItem) findRaumByNummer(6).getInventory().findItemByName("Truhe");
        inventory = new Inventory();
        inventory.getInventory().add(new Item(19, "Axt", "Eine scharfe Axt.", "Du schlägst mit der Axt zu.", true));
        truhe.setInventory(inventory);
    }
    /**
     * Initializes the doors.
     */
    public void initDoors() {
         doors = new ArrayList<>();
         // Raum 1
         door = new Door(1,Richtung.SUED,1,false);
         doors.add(door);
         door = new Door(2,Richtung.WEST,1,false);
         doors.add(door);

         //Raum 2
         door = new Door(3,Richtung.NORD,2,false);
         doors.add(door);
         door = new Door(4,Richtung.WEST,2,false);
         doors.add(door);

         //Raum 3
         door = new Door(5,Richtung.OST,3,false);
         doors.add(door);

         // Raum 4
         door = new Door(6,Richtung.NORD,4,false);
         doors.add(door);
         door = new Door(7,Richtung.OST,4,false);
         doors.add(door);
         door = new Door(8,Richtung.WEST,4,false);
         doors.add(door);

         // Raum 5
         door = new Door(9,Richtung.OST,5,false);
         doors.add(door);
         door = new Door(10,Richtung.NORD,5,false);
         doors.add(door);

         //Raum 6
         door = new Door(11,Richtung.OST,6,false);
         doors.add(door);

         //Raum 7
         door = new Door(12,Richtung.WEST,7,false);
         doors.add(door);
         door = new Door(13,Richtung.SUED,7,false);
         doors.add(door);
    }
    /**
     * Initializes the humans.
     */
    private void initHumans() {
        findRaumByNummer(4).setHuman(new Human(
                "Gordon", "Hast du die Truhe gesehen? Ich frage mich, was da wohl drin ist...", "...",
                "Ich suche ein Brecheisen. Hast du eins?", "Sehr gut. Danke dir.",
                new Item(16, Item.SCHLÜSSEL, "Du betrachtest den Schlüssel. Was kann man damit wohl aufschließen?", "Hier gibt es nichts um den Schlüssel zu benutzen.", true),
                "Brecheisen"));
        findRaumByNummer(5).setHuman(new Human(
                "Junge", "", "",
                "Hast du ein Handtuch ?", "Danke.",

                new Item(17, "Brief", "Ein Brief adressiert an eine Frau.", "Bringe den Brief zu einer Frau.", true),

                "Handtuch"));
        findRaumByNummer(7).setHuman(new Human(
                "Frau", "", "",
                "Hast du einen Brief?", "Danke",

                new Item(18, "Seil", "Ein stabiles Seil.", "Du seilst dich ab.", true),

                "Brief"));
    }


    /**
     * @return Returns the current room.
     */
    public Raum getCurrentRaum() {
        for (Raum raum : raums) {
            if (raum.roomNumber == this.currentRoomNumber) {
                return raum;
            }
        }
        return raums.get(0);
    }

    /**
     * Helps you finding a room by it's number.
     *
     * @param raumNummer The number of the room you're searching
     * @return Returns the room you're searching.
     */
    public Raum findRaumByNummer(int raumNummer) {
        for (Raum raum : raums) {
            if (raum.roomNumber == raumNummer) {
                return raum;
            }
        }
        return null;
    }


    public void setCurrentRaum(Raum raum) {

    }

    /**
     * Walking routine.
     *
     * @param richtung the direction you're going to.
     * @return Returns the room you'll get into or null if there isn't any room in this direction.
     */
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
                    if (richtung == Richtung.OST || richtung == Richtung.NORD) { // Durch betätigen Schalter in Raum 1 öffnet sich auch Raum 7
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
                case 7:
                    if (richtung == Richtung.SUED) {
                        if (checkSchalter()) {
                            Textie.printText("Du öffnest die Tür.");
                            currentRoomNumber = nextRoom.getRoomNumber();
                            currentRaum.setLeaveRoom(true);
                        } else {
                            currentRaum.setLeaveRoom(false);
                        }
                    } else {
                        currentRoomNumber = nextRoom.getRoomNumber();
                        currentRaum.setLeaveRoom(true);
                    }
                    break;
                default:
                    currentRoomNumber = nextRoom.getRoomNumber();
                    currentRaum.setLeaveRoom(true);
                    break;
            }

            previousRoomNumber = raums.indexOf(currentRaum);
            Karte karte;
            if (dungeon.player.getInventory().findItemByName("Karte") != null) {
                karte = (Karte) dungeon.player.getInventory().findItemByName("Karte");
                karte.writeMap(currentRaum.getNumber(), richtung.toString());
            } else if (dungeon.findRaumByNummer(4).getInventory().findItemByName("Karte") != null) {
                karte = (Karte) dungeon.findRaumByNummer(4).getInventory().findItemByName("Karte");
                karte.writeMap(currentRaum.getNumber(), richtung.toString());
            }
            return getNextRoom(currentRoomNumber);

        } else {
            Textie.printText("Du bist gegen die Wand gelaufen.");
        }
        return null;
    }

    /**
     * Helps you find the room which comes after the current.
     *
     * @param currentRoomNumber The number of the current room.
     * @return Returns the new room.
     * @see de.micromata.azubi.Dungeon#findRaumByNummer(int)
     */
    private Raum getNextRoom(int currentRoomNumber) {
        for (Raum raum : raums) {
            if (raum.roomNumber == currentRoomNumber) {
                return raum;
            }
        }
        return raums.get(0);
    }

    /**
     * @return Returns the state of the switch.
     */
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
