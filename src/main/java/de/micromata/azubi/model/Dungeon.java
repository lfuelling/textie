package de.micromata.azubi.model;


import de.micromata.azubi.IOUtils;
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
    private HashMap<ToggleItem, Door> doorSchalter = new HashMap<>();//FIXME ab in den Raum

    private Dungeon() {
    }


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

        PlayerBuilder fremder = new PlayerBuilder().addName("Fremder").add(new InventarBuilder().addSize(5).build()).build();
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

        raum7.addDoor(new DoorBuilder().setRichtung(Richtung.SUED).setLock(true).setNextRoom(raum4.get()).build()).addDoor(new DoorBuilder().setNextRoom(raum6.get()).setRichtung(Richtung.WEST).setLock(false).build()).build();
        dungeonBuilder.addRoom(raum1).addRoom(raum2).addRoom(raum3).addRoom(raum4).addRoom(raum5).addRoom(raum6).addRoom(raum7);

        return dungeonBuilder.build().get();
    }

    /**
     * Starts the game.
     */
    public void runGame() {
        initializeGame();
        player.setPosition(getCurrentRaum());
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
                player.setPosition(raum);
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

    public void initDoorSchalter() {
        doorSchalter.put((ToggleItem) findRaumByNummer(1).getInventory().findItemByName("Schalter"), findRaumByNummer(1).findDoorByDirection(Richtung.WEST));
        doorSchalter.put((ToggleItem) findRaumByNummer(4).getInventory().findItemByName("Schalter"), findRaumByNummer(4).findDoorByDirection(Richtung.OST));
        doorSchalter.put((ToggleItem) findRaumByNummer(7).getInventory().findItemByName("Schalter"), findRaumByNummer(7).findDoorByDirection(Richtung.SUED));
    }

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

    @Override
    public int hashCode() {
        return super.hashCode();
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
            if (nextRoom == null) {
                Textie.printText("Du bist gegen die Wand gelaufen.", this);
            }else{

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
     * Lets you inspect an item.
     *
     * @param parsed_command The String[]
     * @param count          THe size of the String[]
     */
    public void doUntersuche(String[] parsed_command, int count) {
        if (count != 2) {
            Textie.printText("Was soll untersucht werden?", this);
        } else {
            switch (parsed_command[1].toLowerCase()) {
                case "raum":
                    if (this.getCurrentRaum().getRoomNumber() == 3) {
                        Item item = getPlayer().getInventory().findItemByName("Fackel");
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
                        Item item = this.getPlayer().getInventory().findItemByName("Fackel");
                        if (item instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item;
                            if (fackel.getState() == false) {
                                Textie.printText("Du kannst nichts sehen!", this);
                            } else {
                                Textie.printText("In deiner Tasche befindet sich:", this);
                                this.getPlayer().getInventory().listItems(this);
                            }
                        }
                    } else {
                        Textie.printText("In deiner Tasche befindet sich:", this);
                        this.getPlayer().getInventory().listItems(this);
                    }
                    break;
                case "truhe":
                    if (this.getCurrentRaum().getInventory().hasItem("Truhe") == false) {
                        Textie.printText("Hier ist keine Truhe", this);
                    } else {
                        StorageItem truhe = (StorageItem) this.getCurrentRaum().getInventory().findItemByName("Truhe");
                        if (truhe.getLockState() == true) {
                            Textie.printText("Die Truhe ist verschlossen.", this);
                        } else {
                            truhe.getInventory().listItems(this);
                        }
                    }
                    break;
                default:
                    if (this.getCurrentRaum().getRoomNumber() == 3) {
                        Item item = this.getPlayer().getInventory().findItemByName("Fackel");
                        if (item instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item;
                            if (fackel.getState() == false) {
                                Textie.printText("Du kannst nichts sehen!", this);
                            } else {
                                Item itemUSU = Textie.chooseInventory(parsed_command[1], this);
                                if (itemUSU == null) {
                                    Textie.printText("Das Objekt gibt es nicht.", this);
                                } else {
                                    itemUSU.untersuchen();
                                }
                            }
                        }
                    } else {
                        Item itemUSU = Textie.chooseInventory(parsed_command[1], this);
                        if (itemUSU == null) {
                        } else {
                            itemUSU.untersuchen();
                        }
                    }
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
     * Best name ever.
     *
     * @param item Item to take.
     * @return Returns true, if you can pick up the item.
     */
    private static boolean addItemFromChestToInventory(Item item, Dungeon dungeon) {
        StorageItem dieTruhe = (StorageItem) dungeon.getCurrentRaum().getInventory().findItemByName("Truhe");
        if (dungeon.getPlayer().getInventory().getSize() < dungeon.getPlayer().getInventory().getMaxSlots() && dieTruhe.getInventory().hasItem(item.getName())) {
            dieTruhe.getInventory().transferItem(dungeon.getPlayer().getInventory(), item);
            return true;
        } else {
            return false;
        }
    }
}
