package de.micromata.azubi.model;


import de.micromata.azubi.IOUtils;
import de.micromata.azubi.Textie;
import de.micromata.azubi.builder.*;

import javax.xml.soap.Text;
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
  //  private static Dungeon dungeon;

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
    public static Dungeon createDungeon() {

        return init();
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
        
        RaumBuilder raum1 = new RaumBuilder(dungeonBuilder.get()).addRoomNumber(1).addwillkommensNachricht("Du befindest dich in einem dunklen Raum. Nach einiger Zeit gewöhnen sich deine Augen an die Dunkelheit.")
                .addInventory(new InventarBuilder()
                        .addItem(new ToggleItemBuilder().setState(false).setName("Fackel").setPickable(true).setUntersucheText("Du betrachtest die Fackel. Wie kann man die wohl anzünden?").setBenutzeText("Du zündest deine Fackel mit dem Feuerzeug an.").build())
                        .addItem(new ItemBuilder().setName("Handtuch").setPickable(true).setUntersucheText("Das Handtuch sieht sehr flauschig aus.").setBenutzeText("Du wischst dir den Angstschweiß von der Stirn.").build())
                        .addItem(new StorageItemBuilder().setLockState(true).setInventarBuilder(new InventarBuilder().build()).setName("Truhe").setPickable(false).setUntersucheText("Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.").setBenutzeText("Du kannst die Truhe nicht öffnen.").build())
                        .addItem(new ToggleItemBuilder().setState(false).setName("Schalter").setPickable(false).setUntersucheText("Da ist ein kleiner Schalter an der Wand.").setBenutzeText("Du hörst ein Rumpeln, als du den Schalter drückst.").build())
                        .build())
                .build();

        RaumBuilder raum2 = new RaumBuilder(dungeonBuilder.get()).addRoomNumber(2).addwillkommensNachricht("Du kommst in einen dunklen Raum.")
                .addInventory(new InventarBuilder()
                        .addItem(new ItemBuilder().setName("Stein").setPickable(true).setUntersucheText("Du betrachtest den Stein. Er wirkt kalt.").setBenutzeText("Du wirfst den Stein vor dir auf den Boden und hebst ihn wieder auf. Was ein Spaß.").build())
                        .addItem(new ItemBuilder().setName("Schwert").setPickable(true).setUntersucheText("Du betrachtest das Schwert. Es sieht sehr scharf aus.").setBenutzeText("Du stichst dir das Schwert zwischen die Rippen und stirbst.").build())
                        .addItem(new ItemBuilder().setName("Feuerzeug").setPickable(true).setUntersucheText("Du betrachtest das Feuerzeug. Es wirkt zuverlässig.").setBenutzeText("Du zündest deine Fackel mit dem Feuerzeug an.").build()).build())
                .build();

        RaumBuilder raum3 = new RaumBuilder(dungeonBuilder.get()).addRoomNumber(3).addwillkommensNachricht("Es ist zu dunkel, um etwas zu sehen. Ein seltsamer Geruch liegt in der Luft.")
                .addInventory(new InventarBuilder()
                        .addItem(new ItemBuilder().setName("Falltür").setPickable(false).setUntersucheText("Da ist eine Falltür").setBenutzeText("Du schlüpfst durch die Falltür in den darunterliegenden Raum.").build())
                        .addItem(new ItemBuilder().setName("Whiteboard").setPickable(false).setUntersucheText("Es steht \'FLIEH!\' mit Blut geschrieben darauf.").setBenutzeText("Das fasse ich bestimmt nicht an!").build())
                        .addItem(new ItemBuilder().setName("Brecheisen").setPickable(true).setUntersucheText("Da ist ein Brecheisen, es ist \"Gordon\" eingeritzt.").setBenutzeText("Du kratzt dich mit dem Brecheisen am Kopf").build())
                        .addItem(new ItemBuilder().setName("Quietscheente").setPickable(true).setUntersucheText("Die Ente schaut dich vorwurfsvoll an.").setBenutzeText("Die Ente schaut dich vorwurfsvoll an und quietscht leise, als du sie zusammendrückst.").build())
                        .build())
                .build();

        RaumBuilder raum4 = new RaumBuilder(dungeonBuilder.get()).addRoomNumber(4).addwillkommensNachricht("Du kommst in einen hell erleuchteten Raum. Ein alter Mann lehnt an der Wand.")
                .addHuman(new HumanBuilder().setHumanName("Gordon").setDialog1("Hast du die Truhe gesehen? Ich frage mich, was da wohl drin ist...").setDialog2("...").setQuestDoneText("Sehr gut. Danke dir.").setQuestText("Ich suche ein Brecheisen. Hast du eins?").setQuestItem("Brecheisen").setRewarditem(new ItemBuilder().setName("Schlüssel").setPickable(true).setUntersucheText("Du betrachtest den Schlüssel. Was kann man damit wohl aufschließen?").setBenutzeText("Hier gibt es nichts um den Schlüssel zu benutzen.").build()).build())
                .addInventory(new InventarBuilder()
                        .addItem(new ItemBuilder().setName("Sack").setPickable(true).setUntersucheText("Du betrachtest den Sack. Vielleicht kannst du ihn ja an deinem Rucksack befestigen.").setBenutzeText("Du bindest den Sack an deinen Rucksack.").build())
                        .addItem(new ToggleItemBuilder().setState(false).setPickable(false).setName("Schalter").setUntersucheText("Da ist ein kleiner Schalter an der Wand.").setBenutzeText("Du hörst ein Rumpeln, als du den Schalter drückst.").build())
                        .addItem(new KartenBuilder().setName("Karte").setPickable(true).setUntersucheText("Das ist eine Karte, sie zeigt deinen Laufweg.").build()).build())
                .build();

        RaumBuilder raum5 = new RaumBuilder(dungeonBuilder.get()).addRoomNumber(5).addwillkommensNachricht("Du kommst in einen Raum, in dem eine Junge steht.")
                 .addHuman(new HumanBuilder().setHumanName("Junge").setDialog1("Ich suche meine Mutter.").setDialog2("Finde sie!").setQuestDoneText("Danke").setQuestText("Hier ein Brief bring ihn zu einer Frau.").setQuestItem("Handtuch").setRewarditem(new ItemBuilder().setName("Brief").setPickable(true).setBenutzeText("Bringe den Brief zu einer Frau").setUntersucheText("Ein Brief adressiert an eine Frau.").build()).build())
                 .addInventory(new InventarBuilder()
                 .addItem(new ItemBuilder().setName("Falltür").setPickable(false).setUntersucheText("Da ist eine Falltür").setBenutzeText("Du schlüpfst durch die Falltür in den darunterliegenden Raum.").build()).build()).build();

        RaumBuilder raum6 = new RaumBuilder(dungeonBuilder.get()).addRoomNumber(6).addwillkommensNachricht("Du kommst in einen Raum mit einer Truhe").addInventory(new InventarBuilder().addItem(new StorageItemBuilder().setLockState(false).setInventarBuilder(new InventarBuilder().addItem(new ItemBuilder().setName("Axt").setPickable(true).setUntersucheText("Eine scharfe Axt.").setBenutzeText("Du schlägst mit der Axt zu und zerstörst die Holzbarrikade.").build()).build()).setName("Truhe").setPickable(false).setUntersucheText("Ein große Truhe aus Holz.").setBenutzeText("Du öffnest die Truhe.").build()).build()).build();

        RaumBuilder raum7 = new RaumBuilder(dungeonBuilder.get()).addRoomNumber(7).addwillkommensNachricht("Du kommst in einen Raum, eine Frau steht mitten im Raum.")
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
     */
    public void runGame() {
    	initializeGame();
        getCurrentRaum().start(true);


        while (player.isAlive()) {
            if (getCurrentRaum().isLeaveRoom() == false) {
                continue;
            } else {
                Raum raum = getNextRoom(currentRoomNumber);
                raum.setLeaveRoom(false);
                raum.start(true);
            }
        }
        Textie.ende(this);
    }
    
    
    private boolean firstTestRun = true;
    
    public void runGameTest() {
    	if (firstTestRun == true) {
    		// TODO In der Initialisierung Block verschieben.
    		// Dann kann auch die unterscheidung weg fallen.
    		firstTestRun = false;
    		initializeGame();
    	}
    	
    	if (player.isAlive()) {
            if (getCurrentRaum().isLeaveRoom() == false) {
                return;
            } else {
                Raum raum = getNextRoom(currentRoomNumber);
                raum.setLeaveRoom(false);
                raum.start(false);
                return;
            }
        }
        Textie.ende(this);
    }


	private void initializeGame() {
		currentRoomNumber = 1;
        initDoorSchalter();
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
            Textie.printText("Diese Richtung gibt es nicht.", this);

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
                    Textie.printText("Tür verschlossen.", this);
                } else {
                    Textie.printText("Du öffnest die Tür", this);
                    currentRoomNumber = nextRoom.getRoomNumber();
                    currentRaum.setLeaveRoom(true);

                    //previousRoomNumber = raums.indexOf(currentRaum);
                    Karte karte;
                    if (this.player.getInventory().findItemByName("Karte") != null) {
                        karte = (Karte) this.player.getInventory().findItemByName("Karte");
                        karte.writeMap(currentRaum.getRoomNumber(), door.getRichtung().toString());
                    } else if (this.findRaumByNummer(4).getInventory().findItemByName("Karte") != null) {
                        karte = (Karte) this.findRaumByNummer(4).getInventory().findItemByName("Karte");
                        karte.writeMap(currentRaum.getRoomNumber(), door.getRichtung().toString());
                    }
                    return getNextRoom(currentRoomNumber);
                }
            } else {
                Textie.printText("Du bist gegen die Wand gelaufen.", this);
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
    public boolean checkSchalter() {
        if (Textie.chooseInventory("Schalter", this).isToggle()) {
            ToggleItem schalter = (ToggleItem) Textie.chooseInventory("Schalter", this);
            if (schalter.getState() == false) {
                Textie.printText("Da ist eine Tür, du versuchst sie zu öffnen, doch es geht nicht.", this);
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

    /**
     * Lets you walk.
     *
     * @param richtung the direction you want to go.
     */
    public void doGehen(Richtung richtung) {
        if (this.getCurrentRaum().getRoomNumber() == 6 && this.getCurrentRaum().getNextRoom(this.getCurrentRaum().findDoorByDirection(richtung)) == null) {
            Textie.printText("Der Weg wird durch eine Holzbarrikade versperrt.", this);
        } else {
            int roomNumber = this.getCurrentRaum().getRoomNumber();
            Raum nextRoom = this.getRaum(richtung);
            if (nextRoom != null && this.findRaumByNummer(roomNumber).isLeaveRoom()) {
                this.setRoomNumber(nextRoom);
                this.getCurrentRaum().setLeaveRoom(false);
                Textie.printText(this.getCurrentRaum().getWillkommensNachricht(), this);
            }
        }


    }

    /**
     * Throws away an Item.
     *
     * @param item  The item to throw away.
     * @param count The size of the parsed_command String[]
     */
    public void doVernichte(Item item, int count) {
        if (count == 2) {
            if (this.getPlayer().getInventory().transferItem(this.getCurrentRaum().getInventory(), item)) {
                Textie.printText(item.getName() + " vernichtet.", this);
                return;
            } else {
                Textie.printText("Entweder das Objekt gibt es nicht, oder es ist nicht im Inventar.", this);
                return;
            }
        } else {
            Textie.printText("Was soll vernichtet werden?", this);
        }
    }

    /**
     * Lets you inspect an item.
     *
     * @param parsed_command The String[]
     * @param count          THe size of the String[]
     */
    public void doUntersuche(String[] parsed_command, int count) {
        if (count == 2) {
            switch (parsed_command[1].toLowerCase()) {
                case "raum":
                    if (this.getCurrentRaum().getRoomNumber() == 3) {
                        Item item = Textie.chooseInventory("Fackel", this);
                        if (item instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item;
                            if (fackel.getState() == true) {
                                Textie.printText("Im Raum befindet sich:", this);
                                this.getCurrentRaum().getInventory().listItems(this);
                            } else {
                                Textie.printText("Du kannst nichts sehen!", this);
                            }
                        }
                    } else {
                        Textie.printText("Im Raum befindet sich:", this);
                        this.getCurrentRaum().getInventory().listItems(this);
                    }
                    break;
                case "inventar":
                    if (this.getCurrentRaum().getRoomNumber() == 3) {
                        Item item = Textie.chooseInventory("Fackel", this);
                        if (item instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item;
                            if (fackel.getState() == true) {
                                Textie.printText("In deiner Tasche befindet sich:", this);
                                this.getPlayer().getInventory().listItems(this);
                            } else {
                                Textie.printText("Du kannst nichts sehen!", this);
                            }
                        }
                    } else {
                        Textie.printText("In deiner Tasche befindet sich:", this);
                        this.getPlayer().getInventory().listItems(this);
                    }
                    break;
                case "truhe":
                    if (this.getCurrentRaum().getInventory().hasItem("Truhe")) {
                        StorageItem truhe = (StorageItem) this.getCurrentRaum().getInventory().findItemByName("Truhe");
                        if (truhe.getLockState() == true) {
                            Textie.printText("Die Truhe ist verschlossen.", this);
                        } else {
                            truhe.getInventory().listItems(this);
                        }
                    } else {
                        Textie.printText("Hier ist keine Truhe", this);
                    }
                    break;
                default:
                    if (this.getCurrentRaum().getRoomNumber() == 3) {
                        Item item = this.getCurrentRaum().getInventory().findItemByName("Fackel");
                        if (item instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item;
                            if (fackel.getState() == true) {
                                Item itemUSU = this.getCurrentRaum().getInventory().findItemByName(parsed_command[1]);
                                Item itemUSU1 = Textie.chooseInventory(parsed_command[1], this);
                                if (itemUSU == null && itemUSU1 == null) {
                                    Textie.printText("Das Objekt gibt es nicht.", this);
                                } else {
                                    itemUSU.untersuchen();
                                    itemUSU.untersuchen();
                                }
                            } else {
                                Textie.printText("Du kannst nichts sehen!", this);
                            }
                        }
                    } else {
                        Item itemUSU = this.getCurrentRaum().getInventory().findItemByName(parsed_command[1]);
                        Item itemUSU1 = Textie.chooseInventory(parsed_command[1], this);

                        if (itemUSU == null) {
                            if (itemUSU1 == null) {
                               Textie.printText("Das Objekt gibt es nicht.", this);
                            } else {
                                itemUSU1.untersuchen();
                            }
                        } else {
                            itemUSU.untersuchen();
                        }
                    }
            }
        } else {
           Textie.printText("Was soll untersucht werden?", this);
        }
    }

    /**
     * Lets you use an item.
     *
     * @param item The item to use.
     */
    public void doBenutze(Item item) {
        if (item == null) {

           Textie.printText("Das Item gibt es nicht.", this);

        } else {
            Inventory raumInventar = this.getCurrentRaum().getInventory();
            Inventory playerInventory = this.getPlayer().getInventory();
            if (item.isPickable() == false || playerInventory.hasItem(item.getName())) {
                String itemName = item.getName();
                if (Textie.diag == true) {
                   Textie.printText("Du willst " + itemName + " benutzen", this);
                }
                switch (itemName) {
                    // Fackel und Feuerzeug sind besonders, da sie auch funktionen
                    // aufrufen
                    // und nicht nur einen Text ausgeben. Außerdem sollen diese Items
                    // benutzbar sein, selbst wenn der Raum dunkel ist.
                    case "Fackel":// this.itemMap.get("FACKEL").getName():
                    case "Feuerzeug": // this.itemMap.get("FEUERZEUG").getName():
                        int fackelSlot = playerInventory.findItem(playerInventory.findItemByName("Fackel"));
                        int feuerZeugSlot = playerInventory.findItem(playerInventory.findItemByName("Feuerzeug"));
                        if (feuerZeugSlot < 0) {
                           Textie.printText("Du hast kein Feuerzeug.", this);
                            break;
                        } else if (fackelSlot < 0) {
                           Textie.printText("Du hast keine Fackel.", this);
                            break;
                        } else {
                           Textie.printText("Du zündest deine Fackel mit dem Feuerzeug an.", this);
                            Item item2 = playerInventory.findItemByName("Fackel");
                            if (item2 instanceof ToggleItem) {
                                ToggleItem fackel = (ToggleItem) item2;
                                fackel.setState(true);
                            }
                            break;
                        }
                    case "Falltür":
                        ToggleItem fackel = null;
                        Item item5 = Textie.chooseInventory("Fackel", this);
                        if (item5 instanceof ToggleItem) {
                            fackel = (ToggleItem) item5;
                        }
                        if (fackel != null && fackel.getState() == false && this.getCurrentRaum().getRoomNumber() == 3) {
                           Textie.printText("Du kannst nichts sehen!", this);
                        } else {
                            Item itemToUse = item;
                            if (itemToUse == null) {
                               Textie.printText("Das Objekt gibt es nicht.", this);
                                break;
                            } else {
                                if (raumInventar.hasItem("Falltür")) {
                                   Textie.printText("Du schlüpfst durch die Falltür in den darunterliegenden Raum.", this);
                                    doGehen(Richtung.FALLTUER);
                                    break;
                                }
                            }
                            break;
                        }
                        break;
                    case "Axt":
                        Item axt = item;
                        if (this.getCurrentRaum().getRoomNumber() == 6) {
                            this.getCurrentRaum().getDoors().add(new DoorBuilder().setRichtung(Richtung.OST).setLock(false).setNextRoom(this.findRaumByNummer(7)).build().get());
                            axt.benutzen();
                        } else {
                            Textie.printText("Du fuchtelst mit der Axt wild in der Gegend herum", this);
                        }
                        break;
                    case "Sack":
                        Item sack = item;
                        sack.benutzen();
                        playerInventory.removeItem(playerInventory.findItemByName("Sack"));
                        playerInventory.setInventorySize(2);
                        break;
                    case "Schalter":

                        ToggleItem schalter = (ToggleItem) item;
                        schalter.benutzen();
                        schalter.toggleState();
                        this.getDoorSchalter().get(schalter).toogleLock();

                        break;
                    case "Schwert":
                        playerInventory.findItemByName("Schwert").benutzen();
                        this.getPlayer().setAlive(false);
                        Textie.ende(this);
                        break;
                    case "Schlüssel":
                        StorageItem truhe = (StorageItem) raumInventar.findItemByName("Truhe");
                        if (raumInventar.hasItem("Truhe")) {
                            if (truhe.getLockState() == true) {
                                truhe.setLockState(false);
                               Textie.printText("Du öffnest die Truhe mit dem Schlüssel.", this);
                                break;
                            } else {
                               Textie.printText("Die Truhe ist bereits aufgeschlossen.", this);
                                break;
                            }
                        } else {
                           Textie.printText("Hier gibt es nichts, was man aufschließen könnte.", this);
                            break;
                        }
                    default:
                        if (this.getCurrentRaum().getRoomNumber() == 3) {
                            item5 = playerInventory.findItemByName("Fackel");
                            if (item5 instanceof ToggleItem) {
                                fackel = (ToggleItem) item5;
                                if (fackel.getState() == true) {
                                    Item itemToUse = item;
                                    if (itemToUse == null) {
                                       Textie.printText("Das Objekt gibt es nicht.", this);
                                    } else {
                                        itemToUse.benutzen();
                                    }
                                } else {
                                   Textie.printText("Du kannst nichts sehen!", this);
                                }
                            }
                        } else {
                            Item itemToUse = item;
                            if (itemToUse == null) {
                               Textie.printText("Das Objekt gibt es nicht.", this);
                            } else {
                                itemToUse.benutzen();
                            }
                        }
                }
            } else {
               Textie.printText("Du musst das Item im Inventar haben.", this);
            }
        }
    }

    /**
     * Let's you take stuff from a chest.
     *
     * @param item The item to take.
     */
    public void doTakeFromChest(Item item) {
        if (item.isPickable()) {
            if (addItemFromChestToInventory(item, this)) {

               Textie.printText(item.getName() + " zum Inventar hinzugefügt.", this);
            } else {
               Textie.printText("Entweder das Objekt gibt es nicht, oder dein Inventar ist voll.", this);
            }
        } else {
           Textie.printText("Du kannst dieses Item nicht aufheben.", this);
        }
    }

    /**
     * Pick up an item from the floor.
     *
     * @param item The item to pick up.
     */
    public void doNimm(Item item) {
        if (item == null) {
           Textie.printText("Unbekanntes Item.", this);
        } else {
            if (item.isPickable()) {
                if (this.getCurrentRaum().getInventory().transferItem(this.getPlayer().getInventory(), item)) {

                   Textie.printText(item.getName() + " zum Inventar hinzugefügt.", this);
                } else {
                   Textie.printText("Entweder das Objekt gibt es nicht, oder dein Inventar ist voll.", this);
                }
            } else {
               Textie.printText("Du kannst dieses Item nicht aufheben.", this);
            }
        }
    }

    /**
     * Give someone an item.
     *
     * @param parsed_command The String[]
     * @param count          The size of the String[]
     */
    public void doGeben(String[] parsed_command, int count) {
        if (count == 2) {
            String itemToUse = IOUtils.convertToName(parsed_command[1]);
            //Item itemToUse = chooseInventory(parsed_command[1]);
            if (itemToUse.equals(this.getCurrentRaum().getHuman().getQuestItem())) {
                Item item = Textie.chooseInventory(parsed_command[1], this);
                boolean isRemoved = this.getPlayer().getInventory().removeItem(item);
                if (isRemoved == true) {
                   Textie.printText(this.getCurrentRaum().getHuman().getQuestDoneText(), this);
                    this.getCurrentRaum().getHuman().setQuestDone(true);
                    if (Textie.recieveItem(this.getCurrentRaum().getHuman().getRewarditem(), player.getInventory())) {
                       Textie.printText("Im Gegenzug bekommst du von mir auch etwas. Bitteschön.", this);
                    } else {
                       Textie.printText("Dein Inventar ist leider voll. Komm wieder, wenn du Platz hast.", this);
                        this.getCurrentRaum().getHuman().setGaveItem(true);
                    }
                } else {
                   Textie.printText("Item nicht im Inventar.", this);
                }
            } else {
               Textie.printText("Das brauche ich nicht.", this);
            }
        } else {
           Textie.printText("Zu wenig Argumente", this);
        }
    }

    /**
     * Talk to someone.
     */
    public void doReden() {
        if (this.getCurrentRaum().getHuman().isQuestDone() == true) {
            if (this.getCurrentRaum().getHuman().isGaveItem() == true) {
                if (Textie.recieveItem(this.getCurrentRaum().getHuman().getRewarditem(), player.getInventory())) {
                   Textie.printText("Hier, bitte schön.", this);
                    this.getCurrentRaum().getHuman().setGaveItem(false);
                } else {
                   Textie.printText("Dein Inventar ist leider voll. Komm wieder, wenn du Platz hast.", this);
                    this.getCurrentRaum().getHuman().setGaveItem(true);
                }
            } else {
                int dialogNumber = this.getCurrentRaum().getHuman().getDialogNumber();
                switch (dialogNumber) {
                    case 0:
                       Textie.printText(this.getCurrentRaum().getHuman().getDialog1(), this);
                        dialogNumber = 1;
                        break;
                    case 1:
                       Textie.printText(this.getCurrentRaum().getHuman().getDialog2(), this);
                        dialogNumber = 0;
                        break;
                }
            }
        } else {
           Textie.printText(this.getCurrentRaum().getHuman().getQuestText(), this);
        }
    }






    /**
     * Best name ever.
     *
     * @param item Item to take.
     * @return Returns true, if you can pick up the item.
     */
    private static boolean addItemFromChestToInventory(Item item, Dungeon dungeon) {
        StorageItem dieTruhe = (StorageItem) dungeon.getCurrentRaum().getInventory().findItemByName("Truhe");
        if (dungeon.getPlayer().getInventory().getSize() < dungeon.getPlayer().getInventory().getInventorySize() && dieTruhe.getInventory().hasItem(item.getName())) {
            dieTruhe.getInventory().transferItem(dungeon.getPlayer().getInventory(), item);
            return true;
        } else {
            return false;
        }
    }
}
