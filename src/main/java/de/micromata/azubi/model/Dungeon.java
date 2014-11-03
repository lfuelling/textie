package de.micromata.azubi.model;


import de.micromata.azubi.Textie;
import de.micromata.azubi.builder.*;

import java.io.Serializable;
import java.util.*;

/**
 * @author Tung Ngo (t.ngo@micromata.de)
 * @author Lukas F&uuml;lling (l.fuelling@micromata.de)
 * @author Julian Siebert (j.siebert@micromata.de)
 * @see java.io.Serializable
 */
public class Dungeon implements Serializable {
    private static final long serialVersionUID = -7870743513679247263L;
    private ArrayList<Raum> raums = new ArrayList<>();
    private int currentRoomNumber; //Index des aktuellen Raumes in der RaumListe FIXME In Spieler
    private Player player;
    private HashMap <ToggleItem,Door> doorSchalter = new HashMap<>();//FIXME ab in den Raum
    private static Dungeon dungeon;

    private Dungeon() {}


    /**
     * Initializes the game

    public void init() {
        player = new Player("Fremder", true);
        previousRoomNumber = 1;
        initRooms();
        initInventories();
        initHumans();
        initDoors();
        initDoorSchalter();
        player.getItems().setInventorySize(5);

    }
     */

    /**
     * The Dungeon is the world you play in
     *
     * @return The world
     */
    public static Dungeon getDungeon() {
        if (dungeon == null) {
            dungeon = init();
        }
        return dungeon;
    }
    
    /**
     * Wird nur von getDungeon aufgerufen und erzeugt die Dungeon Instanz
     * 
     * @return
     */
    private static Dungeon init() {
    	DungeonBuilder dungeonBuilder = new DungeonBuilder(new Dungeon());
        
    	PlayerBuilder fremder = new PlayerBuilder().addName("Fremder").add(new InventarBuilder().build()).build();
        dungeonBuilder.add(fremder);
        
        RaumBuilder raum1 = new RaumBuilder().addRoomNumber(1).addwillkommensNachricht("Du befindest dich in einem dunklen Raum. Nach einiger Zeit gewöhnen sich deine Augen an die Dunkelheit.")
                .addInventory(new InventarBuilder()
                        .addItem(new ToggleItemBuilder().setState(false).setName("Fackel").setPickable(true).setUntersucheText("Du betrachtest die Fackel. Wie kann man die wohl anzünden?").setBenutzeText("Du zündest deine Fackel mit dem Feuerzeug an.").build())
                        .addItem(new ItemBuilder().setName("Handtuch").setPickable(true).setUntersucheText("Das Handtuch sieht sehr flauschig aus.").setBenutzeText("Du wischst dir den Angstschweiß von der Stirn.").build())
                        .addItem(new StorageItemBuilder().setLockState(true).setInventarBuilder(new InventarBuilder().build()).setName("Truhe").setPickable(false).setUntersucheText("Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.").setBenutzeText("Du kannst die Truhe nicht öffnen.").build())
                        .addItem(new ToggleItemBuilder().setState(false).setName("Schalter").setPickable(false).setUntersucheText("Da ist ein kleiner Schalter an der Wand.").setBenutzeText("Du hörst ein Rumpeln, als du den Schalter drückst.").build())
                        .build())
                .build();

        RaumBuilder raum2 = new RaumBuilder().addRoomNumber(2).addwillkommensNachricht("Du kommst in einen dunklen Raum.")
                .addInventory(new InventarBuilder()
                        .addItem(new ItemBuilder().setName("Stein").setPickable(true).setUntersucheText("Du betrachtest den Stein. Er wirkt kalt.").setBenutzeText("Du wirfst den Stein vor dir auf den Boden und hebst ihn wieder auf. Was ein Spaß.").build())
                        .addItem(new ItemBuilder().setName("Schwert").setPickable(true).setUntersucheText("Du betrachtest das Schwert. Es sieht sehr scharf aus.").setBenutzeText("Du stichst dir das Schwert zwischen die Rippen und stirbst.").build())
                        .addItem(new ItemBuilder().setName("Feuerzeug").setPickable(true).setUntersucheText("Du betrachtest das Feuerzeug. Es wirkt zuverlässig.").setBenutzeText("Du zündest deine Fackel mit dem Feuerzeug an.").build()).build())
                .build();

        RaumBuilder raum3 = new RaumBuilder().addRoomNumber(3).addwillkommensNachricht("Es ist zu dunkel, um etwas zu sehen. Ein seltsamer Geruch liegt in der Luft.")
                .addInventory(new InventarBuilder()
                        .addItem(new ItemBuilder().setName("Falltür").setPickable(false).setUntersucheText("Da ist eine Falltür").setBenutzeText("Du schlüpfst durch die Falltür in den darunterliegenden Raum.").build())
                        .addItem(new ItemBuilder().setName("Whiteboard").setPickable(false).setUntersucheText("Es steht \'FLIEH!\' mit Blut geschrieben darauf.").setBenutzeText("Das fasse ich bestimmt nicht an!").build())
                        .addItem(new ItemBuilder().setName("Brecheisen").setPickable(true).setUntersucheText("Da ist ein Brecheisen, es ist \"Gordon\" eingeritzt.").setBenutzeText("Du kratzt dich mit dem Brecheisen am Kopf").build())
                        .addItem(new ItemBuilder().setName("Quietscheente").setPickable(true).setUntersucheText("Die Ente schaut dich vorwurfsvoll an.").setBenutzeText("Die Ente schaut dich vorwurfsvoll an und quietscht leise, als du sie zusammendrückst.").build())
                        .build())
                .build();

        RaumBuilder raum4 = new RaumBuilder().addRoomNumber(4).addwillkommensNachricht("Du kommst in einen hell erleuchteten Raum. Ein alter Mann lehnt an der Wand.")
                .addHuman(new HumanBuilder().setHumanName("Gordon").setDialog1("Hast du die Truhe gesehen? Ich frage mich, was da wohl drin ist...").setDialog2("...").setQuestDoneText("Sehr gut. Danke dir.").setQuestText("Ich suche ein Brecheisen. Hast du eins?").setQuestItem("Brecheisen").setRewarditem(new ItemBuilder().setName("Schlüssel").setPickable(true).setUntersucheText("Du betrachtest den Schlüssel. Was kann man damit wohl aufschließen?").setBenutzeText("Hier gibt es nichts um den Schlüssel zu benutzen.").build()).build())
                .addInventory(new InventarBuilder()
                        .addItem(new ItemBuilder().setName("Sack").setPickable(true).setUntersucheText("Du betrachtest den Sack. Vielleicht kannst du ihn ja an deinem Rucksack befestigen.").setBenutzeText("Du bindest den Sack an deinen Rucksack.").build())
                        .addItem(new ToggleItemBuilder().setState(false).setName("Schalter").setUntersucheText("Da ist ein kleiner Schalter an der Wand.").setBenutzeText("Du hörst ein Rumpeln, als du den Schalter drückst.").build())
                        .addItem(new KartenBuilder().setName("Karte").setPickable(true).setUntersucheText("Das ist eine Karte, sie zeigt deinen Laufweg.").build()).build())
                .build();

        RaumBuilder raum5 = new RaumBuilder().addRoomNumber(5).addwillkommensNachricht("Du kommst in einen Raum, in dem eine Junge steht.")
                 .addHuman(new HumanBuilder().setHumanName("Junge").setDialog1("Ich suche meine Mutter.").setDialog2("Finde sie!").setQuestDoneText("Danke").setQuestText("Hier ein Brief bring ihn zu einer Frau.").setQuestItem("Handtuch").setRewarditem(new ItemBuilder().setName("Brief").setPickable(true).setBenutzeText("Bringe den Brief zu einer Frau").setUntersucheText("Ein Brief adressiert an eine Frau.").build()).build())
                 .addInventory(new InventarBuilder()
                 .addItem(new ItemBuilder().setName("Falltür").setPickable(false).setUntersucheText("Da ist eine Falltür").setBenutzeText("Du schlüpfst durch die Falltür in den darunterliegenden Raum.").build()).build()).build();

        RaumBuilder raum6 = new RaumBuilder().addRoomNumber(6).addwillkommensNachricht("Du kommst in einen Raum mit einer Truhe").addInventory(new InventarBuilder().addItem(new StorageItemBuilder().setLockState(false).setInventarBuilder(new InventarBuilder().addItem(new ItemBuilder().setName("Axt").setPickable(true).setUntersucheText("Eine scharfe Axt.").setBenutzeText("Du schlägst mit der Axt zu und zerstörst die Holzbarrikade.").build()).build()).setName("Truhe").setPickable(false).setUntersucheText("Ein große Truhe aus Holz.").setBenutzeText("Du öffnest die Truhe.").build()).build()).build();

        RaumBuilder raum7 = new RaumBuilder().addRoomNumber(7).addwillkommensNachricht("Du kommst in einen Raum, eine Frau steht mitten im Raum.")
               .addHuman(new HumanBuilder().setHumanName("Frau").setDialog1("Du hast mein Sohn gesehen ?").setDialog2("Wo ?").setQuestDoneText("Danke, Hier ein Seil für dich.").setQuestItem("Brief").setRewarditem(new ItemBuilder().setName("Seil").setPickable(true).setUntersucheText("Ein stabiles Seil.").setBenutzeText("Du bindest das Seil fest.").build()).build())
                .addInventory(new InventarBuilder().addItem(new ToggleItemBuilder().setState(false).setBenutzeText("Du hörst ein Rumpeln, als du den Schalter drückst.").setName("Schalter").setPickable(false).setUntersucheText("Da ist ein kleiner Schalter an der Wand.").build()).build().build());

        raum1.addDoor(new DoorBuilder().setRichtung(Richtung.SUED).setNextRoom(raum2.get()).setLock(false).build())
                .addDoor(new DoorBuilder().setRichtung(Richtung.WEST).setNextRoom(raum4.get()).setLock(true).build()).build();

        raum2.addDoor(new DoorBuilder().setRichtung(Richtung.WEST).setNextRoom(raum3.get()).setLock(false).build())
                .addDoor(new DoorBuilder().setRichtung(Richtung.NORD).setNextRoom(raum1.get()).setLock(false).build()).build();

        raum3.addDoor(new DoorBuilder().setRichtung(Richtung.FALLTUER).setNextRoom(raum4.get()).build())
                .addDoor(new DoorBuilder().setRichtung(Richtung.OST).setNextRoom(raum2.get()).build()).build();

        raum4.addDoor(new DoorBuilder().setRichtung(Richtung.OST).setNextRoom(raum1.get()).setLock(true).build()).addDoor(new DoorBuilder().setRichtung(Richtung.WEST).setLock(false).setNextRoom(raum5.get()).build()).addDoor(new DoorBuilder().setNextRoom(raum7.get()).setLock(true).setRichtung(Richtung.NORD).build()).build();

        raum5.addDoor(new DoorBuilder().setRichtung(Richtung.FALLTUER).setNextRoom(raum6.get()).setLock(false).build()).addDoor(new DoorBuilder().setLock(false).setNextRoom(raum4.get()).setRichtung(Richtung.OST).build()).build();


        //raum6.addDoor(new DoorBuilder().setRichtung(Richtung.OST).setLock(false).setNextRoom(raum7.get()).build()).build();

        raum7.addDoor(new DoorBuilder().setRichtung(Richtung.SUED).setLock(true).setNextRoom(raum4.get()).build()).addDoor(new DoorBuilder().setNextRoom(raum6.get()).setRichtung(Richtung.WEST).setLock(false).build()).build();
        dungeonBuilder.addRoom(raum1).addRoom(raum2).addRoom(raum3).addRoom(raum4).addRoom(raum5).addRoom(raum6).addRoom(raum7);

        return dungeonBuilder.build().get();
    }
    /*
    RaumBuilder raum6 = new RaumBuilder().addRoomNumber(6).addwillkommensNachricht("Du kommst in einen Raum mit einer Truhe.")
                .addInventory(new InventarBuilder().addItem(new StorageItemBuilder().setLockState(false).setInventarBuilder(new InventarBuilder().addItem(new ItemBuilder().setName("Axt").setPickable(true).setBenutzeText("Du schlägst mit der Axt zu.").setUntersucheText("Eine scharfe Axt.").build()).build()).setName("Truhe").setBenutzeText("Du versuchst die Truhe zu öffnen.").setUntersucheText("Ein große Truhe aus Holz.")).build()).build();
    */



    /**
     * Starts the game.
     *
     * @param withPrompt Set to <code>true</code>, if you want a prompt.
     */
    public void runGame(boolean withPrompt) {
        currentRoomNumber = 1;
        initDoorSchalter();
        getCurrentRaum().start(withPrompt);


        while (player.isAlive()) {
            if (getCurrentRaum().isLeaveRoom() == false) {
                continue;
            } else {
                Raum raum = getNextRoom(currentRoomNumber);
                raum.setLeaveRoom(false);
                raum.start(withPrompt);
            }
        }
        Textie.ende();
    }

    /**
     * Initializes the rooms.
     */
    /*
    public void initRooms() {
        Raum raum;
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
    */
    public void initDoorSchalter() {
        doorSchalter.put((ToggleItem)findRaumByNummer(1).getInventory().findItemByName("Schalter"), findRaumByNummer(1).findDoorByDirection(Richtung.WEST));
        doorSchalter.put((ToggleItem)findRaumByNummer(4).getInventory().findItemByName("Schalter"), findRaumByNummer(4).findDoorByDirection(Richtung.OST));
        doorSchalter.put((ToggleItem)findRaumByNummer(7).getInventory().findItemByName("Schalter"), findRaumByNummer(7).findDoorByDirection(Richtung.SUED));
    }
    /*
    public void initInventories() {
        StorageItem truhe;
        // Raum 1
        Inventory inventory = new Inventory();
        inventory.getItems().add(new ToggleItem(1, Item.FACKEL, "Du betrachtest die Fackel. Wie kann man die wohl anzünden?", "Du zündest deine Fackel mit dem Feuerzeug an.", true, false));
        inventory.getItems().add(new Item(2, Item.HANDTUCH, "Das Handtuch sieht sehr flauschig aus.", "Du wischst dir den Angstschweiß von der Stirn.", true));
        inventory.getItems().add(new ToggleItem(3, Item.SCHALTER, "Da ist ein kleiner Schalter an der Wand.", "Du hörst ein Rumpeln, als du den Schalter drückst.", false, false));
        inventory.getItems().add(new StorageItem(4, Item.TRUHE, "Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.", "Du kannst die Truhe nicht öffnen.", false, true, true));
        findRaumByNummer(1).setInventory(inventory);

        //Raum 2
        inventory = new Inventory();
        inventory.getItems().add(new Item(5, Item.STEIN, "Du betrachtest den Stein. Er wirkt kalt.", "Hier gibt es nichts um den Stein zu benutzen.", true));
        inventory.getItems().add(new Item(6, Item.SCHWERT, "Du betrachtest das Schwert. Es sieht sehr scharf aus.", "Du stichst dir das Schwert zwischen die Rippen und stirbst.", true));
        inventory.getItems().add(new Item(7, Item.FEUERZEUG, "Du betrachtest das Feuerzeug. Es wirkt zuverlässig.", "Du zündest deine Fackel mit dem Feuerzeug an.", true));
        findRaumByNummer(2).setInventory(inventory);

        //Raum 3
        inventory = new Inventory();
        inventory.getItems().add(new Item(8, Item.FALLTÜR, "Da ist eine Falltür", "Du schlüpfst durch die Falltür in den darunterliegenden Raum.", false));
        inventory.getItems().add(new Item(9, Item.WHITEBOARD, "Es steht \'FLIEH!\' mit Blut geschrieben darauf.", "Das fasse ich bestimmt nicht an!", false));
        inventory.getItems().add(new Item(10, Item.BRECHEISEN, "Da ist ein Brecheisen, es ist \"Gordon\" eingeritzt.", "Du kratzt dich mit dem Brecheisen am Kopf", true));
        inventory.getItems().add(new Item(11, Item.QUIETSCHEENTE, "Die Ente schaut dich vorwurfsvoll an.", "Die Ente schaut dich vorwurfsvoll an und quietscht leise, als du sie zusammendrückst.", true));
        findRaumByNummer(3).setInventory(inventory);

        //Raum 4
        inventory = new Inventory();
        inventory.getItems().add(new Karte(20, "Karte", "Das ist eine Karte, sie zeigt deinen Laufweg.", "Benutzetext wird bei benutzung geändert"));
        inventory.getItems().add(new Item(12, Item.SACK, "Du betrachtest den Sack. Vielleicht kannst du ihn ja an deinem Rucksack befestigen.", "Du bindest den Sack an deinen Rucksack.", true));
        inventory.getItems().add(new ToggleItem(21, Item.SCHALTER,"Da ist ein kleiner Schalter an der Wand.", "Du hörst ein Rumpeln, als du den Schalter drückst.", false, false));
        findRaumByNummer(4).setInventory(inventory);

        //Raum 5
        inventory = new Inventory();
        inventory.getItems().add(new Item(13, Item.FALLTÜR, "Da ist eine Falltür", "Du schlüpfst durch die Falltür in den darunterliegenden Raum.", false));
        findRaumByNummer(5).setInventory(inventory);

        //Raum 6
        inventory = new Inventory();
        inventory.getItems().add(new StorageItem(14, Item.TRUHE, "Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.", "Du kannst die Truhe nicht öffnen.", false, true, false));
        findRaumByNummer(6).setInventory(inventory);

        //Raum 7
        inventory = new Inventory();
        inventory.getItems().add(new ToggleItem(15, Item.SCHALTER, "Da ist ein kleiner Schalter an der Wand.", "Du hörst ein Rumpeln, als du den Schalter drückst.", false, false));
        findRaumByNummer(7).setInventory(inventory);

        //Truhe Raum1
        truhe = (StorageItem) findRaumByNummer(1).getInventory().findItemByName("Truhe");
        inventory = new Inventory();
        //inventory.getItems().add();
        //TODO Items einfügen
        //inventory.getItems().add();
        truhe.setInventory(inventory);

        //Truhe Raum 6
        truhe = (StorageItem) findRaumByNummer(6).getInventory().findItemByName("Truhe");
        inventory = new Inventory();
        inventory.getItems().add(new Item(19, "Axt", "Eine scharfe Axt.", "Du schlägst mit der Axt zu.", true));
        truhe.setInventory(inventory);
    }

    public void initDoors() {
        Door door;
        ArrayList<Door> doors;
        // Raum 1
        doors = new ArrayList<>();
        door = new Door(1, Richtung.SUED, 2, false);
        doors.add(door);
        door = new Door(2, Richtung.WEST, 4, true);
        doors.add(door);
        findRaumByNummer(1).setDoors(doors);

        //Raum 2
        doors = new ArrayList<>();
        door = new Door(3, Richtung.NORD, 1, false);
        doors.add(door);
        door = new Door(4, Richtung.WEST, 3, false);
        doors.add(door);
        findRaumByNummer(2).setDoors(doors);

        //Raum 3
        doors = new ArrayList<>();
        door = new Door(5, Richtung.OST, 2, false);
        doors.add(door);
        door = new Door(9, Richtung.FALLTUER, 4, false); //FIXME noch eine normale Tür
        doors.add(door);
        findRaumByNummer(3).setDoors(doors);

        // Raum 4
        doors = new ArrayList<>();
        door = new Door(6, Richtung.NORD, 7, true);
        doors.add(door);
        door = new Door(7, Richtung.OST, 1, true);
        doors.add(door);
        door = new Door(8, Richtung.WEST, 5, false);
        doors.add(door);
        findRaumByNummer(4).setDoors(doors);

        // Raum 5
        doors = new ArrayList<>();
        door = new Door(9, Richtung.OST, 4, false);
        doors.add(door);
        door = new Door(10, Richtung.FALLTUER, 6, false);
        doors.add(door);
        findRaumByNummer(5).setDoors(doors);


        //Raum 6
        doors = new ArrayList<>();
        //Tür erst gesetzt wenn Axt in Raum 6 benutzt
        findRaumByNummer(6).setDoors(doors);


        //Raum 7
        doors = new ArrayList<>();
        door = new Door(12, Richtung.WEST, 6, false);
        doors.add(door);
        door = new Door(13, Richtung.SUED, 4, true);
        doors.add(door);
        findRaumByNummer(7).setDoors(doors);
    }

    private void initHumans() {
        findRaumByNummer(4).setHuman(new Human(
                "Gordon", "Hast du die Truhe gesehen? Ich frage mich, was da wohl drin ist...", "...",
                "Ich suche ein Brecheisen. Hast du eins?", "Sehr gut. Danke dir.",
                new Item(16, Item.SCHLÜSSEL, "Du betrachtest den Schlüssel. Was kann man damit wohl aufschließen?", "Hier gibt es nichts um den Schlüssel zu benutzen.", true), "Brecheisen"));
        findRaumByNummer(5).setHuman(new Human(
                "Junge", "", "",
                "Hast du ein Handtuch ?", "Danke.",

                new Item(17, "Brief", "Ein Brief adressiert an eine Frau.", "Bringe den Brief zu einer Frau.", true),

                "Handtuch"));
        findRaumByNummer(7).setHuman(new Human(
                "Frau", "", "",
                "Hast du einen Brief?", "Danke",

                new Item(18, "Seil", "Ein stabiles Seil.", "Du seilst dich ab.", true), "Brief"));
    }
    */

    /**
     * @return Returns the current room.
     */
    public Raum getCurrentRaum() {
    	
    	if (raums == null || raums.size() <= 0) {
    		return null;
    	}
    	
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

    /**
     * Walking routine.
     *
     * @param richtung The direction you want to go
     * @return Returns the room you'll get into or null if there isn't any room in this direction.
     */
    public Raum getRaum(Richtung richtung) {
        Raum currentRaum = getCurrentRaum();
        Door door = currentRaum.findDoorByDirection(richtung);
        if (door == null) {
            Textie.printText("Diese Richtung gibt es nicht.");

        } else {
            Raum nextRoom = currentRaum.getNextRoom(door);
            if (nextRoom != null) {
                    /*
                    Hier Räume mit deren Nummern aufführen, die eine per Knopf verschlossene Tür haben
                    if(richtung == Richtung.RICHTUNG_IN_DER_DIE_TÜR_LIEGT){
                    checkSchalter(dungeon, richtung);
                    }
                    */
                if (door.isLocked() == true) {
                    currentRaum.setLeaveRoom(false);
                    Textie.printText("Tür verschlossen.");
                } else {
                    Textie.printText("Du öffnest die Tür");
                    currentRoomNumber = nextRoom.getRoomNumber();
                    currentRaum.setLeaveRoom(true);

                    //previousRoomNumber = raums.indexOf(currentRaum);
                    Karte karte;
                    if (dungeon.player.getInventory().findItemByName("Karte") != null) {
                        karte = (Karte) dungeon.player.getInventory().findItemByName("Karte");
                        karte.writeMap(currentRaum.getRoomNumber(), door.getRichtung().toString());
                    } else if (dungeon.findRaumByNummer(4).getInventory().findItemByName("Karte") != null) {
                        karte = (Karte) dungeon.findRaumByNummer(4).getInventory().findItemByName("Karte");
                        karte.writeMap(currentRaum.getRoomNumber(), door.getRichtung().toString());
                    }
                    return getNextRoom(currentRoomNumber);
                }
            } else {
                Textie.printText("Du bist gegen die Wand gelaufen.");
            }
        }
        return null;
    }


    /**
     * Helps you find the room which comes after the current.
     *
     * @param currentRoomNumber The number of the current room.
     * @return Returns the new room.
     * @see Dungeon#findRaumByNummer(int)
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

    /**
     * Sets the world you play in
     * It's for savegamestuff
     *
     * @param dungeon The world object
     */
    public static void setDungeon(Dungeon dungeon) {
        Dungeon.dungeon = dungeon;

    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Raum> getRooms() {
        return raums;
    }

    public Player getPlayer() {
        return player;
    }

    //TODO
    public HashMap<ToggleItem, Door> getDoorSchalter() {
        return doorSchalter;
    }
}
