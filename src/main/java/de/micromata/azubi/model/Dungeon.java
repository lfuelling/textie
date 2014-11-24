package de.micromata.azubi.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser;
import de.micromata.azubi.IOUtils;
import de.micromata.azubi.Textie;
import de.micromata.azubi.builder.*;

import javax.xml.soap.Text;
import java.io.File;
import java.io.IOException;
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
    private ArrayList<Room> rooms = new ArrayList<>();
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
        ObjectMapper mapper = new ObjectMapper();
        mapper.getFactory().enable(JsonParser.Feature.ALLOW_COMMENTS);
        List<LinkedHashMap> roomList = null;
        try {
            roomList = mapper.readValue(new File("config/TextieConf.json"), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DungeonBuilder dungeonBuilder = new DungeonBuilder(new Dungeon());
        PlayerBuilder fremder = new PlayerBuilder().addName("Fremder").add(new InventoryBuilder().addSize(5).build()).build();
        dungeonBuilder.add(fremder);

        for (LinkedHashMap listItem : roomList) {
            BaseRoomBuilder room = null;
            String roomClass = (String) listItem.get("class");
            switch (roomClass) {
                case "de.micromata.azubi.model.Room":
                    room = new RoomBuilder(dungeonBuilder.get());
                    break;
                case "de.micromata.azubi.model.DarkRoom":
                    room = new DarkRoomBuilder(dungeonBuilder.get());
                    break;
            }
            room.addRoomNumber((int) listItem.get("roomNumber")).addwillkommensNachricht((String) listItem.get("welcomeText"));
            LinkedHashMap human = (LinkedHashMap) listItem.get("human");
            if (human == null) {
            } else {
                HumanBuilder hb = new HumanBuilder().setHumanName((String) human.get("name"))
                        .setDialog1((String) human.get("dialog1"))
                        .setDialog2((String) human.get("dialog2"))
                        .setQuestDoneText((String) human.get("questDoneText"))
                        .setQuestText((String) human.get("questText"))
                        .setQuestItem((String) human.get("questItem"));
                BaseItemBuilder rewardItemBuilder = null;
                LinkedHashMap rewardItem = (LinkedHashMap) human.get("rewardItem");
                String rewardItemClass = (String) rewardItem.get("class");
                switch (rewardItemClass) {
                    case "de.micromata.azubi.model.Item":
                        rewardItemBuilder = new ItemBuilder();
                        break;
                    case "de.micromata.azubi.model.ToggleItem":
                        rewardItemBuilder = new ToggleItemBuilder().setState((boolean) rewardItem.get("state"));
                        break;
                    default:
                        System.out.println("Fehler in der Konfiguration. Fehlerhafte Klasse in rewardItem");
                }
                rewardItemBuilder.setName((String) rewardItem.get("name")).setBenutzeText((String) rewardItem.get("useText")).setUntersucheText((String) rewardItem.get("examineText")).build();
                hb.setRewarditem(rewardItemBuilder).build();
                room.addHuman(hb);
            }
            InventoryBuilder ib = new InventoryBuilder();
            List<BaseItemBuilder> ibs = new ArrayList();
            LinkedHashMap inventory = (LinkedHashMap) listItem.get("inventory");
            ArrayList<LinkedHashMap> items = (ArrayList<LinkedHashMap>) inventory.get("items");
            BaseItemBuilder itemBuilder = null;
            for (LinkedHashMap item : items) {
                String itemClass = (String) item.get("class");
                switch (itemClass) {
                    case "de.micromata.azubi.model.Item":
                        itemBuilder = new ItemBuilder();
                        break;
                    case "de.micromata.azubi.model.ToggleItem":
                        itemBuilder = new ToggleItemBuilder().setState((boolean) item.get("state"));
                        break;
                    case "de.micromata.azubi.model.StorageItem":
                        InventoryBuilder chestInvBuilder;
                        itemBuilder = new StorageItemBuilder().setLockState((boolean) item.get("lockState")).setInventoryBuilder(chestInvBuilder = new InventoryBuilder());
                        LinkedHashMap chestInv = (LinkedHashMap) item.get("inventory");
                        ArrayList<LinkedHashMap> chestItems = (ArrayList<LinkedHashMap>) chestInv.get("items");
                        for (LinkedHashMap chestItem : chestItems) {
                            BaseItemBuilder chestItemBuilder = null;
                            String rewardItemClass = (String) chestItem.get("class");
                            switch (rewardItemClass) {
                                case "de.micromata.azubi.model.Item":
                                    chestItemBuilder = new ItemBuilder();
                                    break;
                                case "de.micromata.azubi.model.ToggleItem":
                                    chestItemBuilder = new ToggleItemBuilder().setState((boolean) chestItem.get("state"));
                                    break;
                                default:
                                    System.out.println("Fehler in der Konfiguration. Fehlerhafte Klasse in StorageItem");
                            }
                            chestItemBuilder.setName((String) chestItem.get("name")).setBenutzeText((String) chestItem.get("useText")).setUntersucheText((String) chestItem.get("examineText")).setPickable((boolean) chestItem.get("pickable")).build();
                            chestInvBuilder.addItem(chestItemBuilder);
                        }
                        chestInvBuilder.build();
                        break;
                    case "de.micromata.azubi.model.Karte":
                        itemBuilder = new MapBuilder();
                        break;
                    case "de.micromata.azubi.model.Switch":
                        itemBuilder = new SwitchBuilder();
                        ArrayList<Integer> doorIds = (ArrayList<Integer>) item.get("doorIds");
                        for(int doorId : doorIds){
                            ((SwitchBuilder)itemBuilder).addDoor(doorId);
                        }
                        break;
                    default:
                        System.out.println("Fehler in der Konfiguration. Fehlerhafte Klasse in Item");
                }
                itemBuilder.setName((String) item.get("name"))
                        .setPickable((boolean) item.get("pickable"))
                        .setUntersucheText((String) item.get("examineText"))
                        .setBenutzeText((String) item.get("useText"))
                        .build();
                ibs.add(itemBuilder);
            }
            for (BaseItemBuilder itemBuilder1 : ibs) {
                ib.addItem(itemBuilder1);
            }
            ib.build();
            room.addInventory(ib).build();
            DoorBuilder db;
            List<LinkedHashMap> doorList = (List<LinkedHashMap>) listItem.get("doors");
            for (LinkedHashMap doorItem : doorList) {
                db = new DoorBuilder()
                        .setDoorId((int) doorItem.get("doorId"))
                        .setLock((boolean) doorItem.get("locked"))
                        .setRichtungByText((String) doorItem.get("direction"))
                        .setNextRoom((int) doorItem.get("nextRoom")).build();
                room.addDoor(db);
            }
            room.build();
            dungeonBuilder.addRoom(room);
        }
        return dungeonBuilder.build().get();
    }

    /**
     * Starts the game.
     */
    public void runGame() {
        initializeGame();
        player.setPosition(getCurrentRoom());
        getCurrentRoom().start(true);


        while (player.isAlive()) {
            if (getCurrentRoom().isLeaveRoom() == false) {
                continue;
            } else {
                Room room = getNextRoom(currentRoomNumber);
                room.setLeaveRoom(false);
                room.start(true);
            }
        }
        Textie.end(this);
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
            if (getCurrentRoom().isLeaveRoom() == false) {
                return;
            } else {
                Room room = getNextRoom(currentRoomNumber);
                room.setLeaveRoom(false);
                player.setPosition(room);
                room.start(false);
                return;
            }
        }
        Textie.end(this);
    }


    private void initializeGame() {
        currentRoomNumber = 1;
        initDoorSchalter();
    }

    public void initDoorSchalter() {
        doorSchalter.put((ToggleItem) findRoomByNumber(1).getInventory().findItemByName("Schalter"), findRoomByNumber(1).findDoorByDirection(Direction.WEST));
        doorSchalter.put((ToggleItem) findRoomByNumber(4).getInventory().findItemByName("Schalter"), findRoomByNumber(4).findDoorByDirection(Direction.OST));
        doorSchalter.put((ToggleItem) findRoomByNumber(7).getInventory().findItemByName("Schalter"), findRoomByNumber(7).findDoorByDirection(Direction.SUED));
    }

    /**
     * @return Returns the current room.
     */
    public Room getCurrentRoom() {

        if (rooms == null || rooms.size() <= 0) {
            return null;
        }

        for (Room room : rooms) {
            if (room.roomNumber == this.currentRoomNumber) {
                return room;
            }
        }
        return rooms.get(0);
    }

    /**
     * Helps you finding a room by it's number.
     *
     * @param roomNumber The number of the room you're searching
     * @return Returns the room you're searching.
     */
    public Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.roomNumber == roomNumber) {
                return room;
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
     * @param direction The direction you want to go
     * @return Returns the room you'll get into or null if there isn't any room in this direction.
     */
    public Room getRoom(Direction direction) {
        Room currentRoom = getCurrentRoom();
        Door door = currentRoom.findDoorByDirection(direction);
        if (door == null) {
            Textie.printText("Diese Richtung gibt es nicht.", this);

        } else {
            Room nextRoom = currentRoom.getNextRoom(door);
            if (nextRoom == null) {
                Textie.printText("Du bist gegen die Wand gelaufen.", this);
            } else {

                if (door.isLocked() == true) {
                    currentRoom.setLeaveRoom(false);
                    Textie.printText("Tür verschlossen.", this);
                } else {
                    Textie.printText("Du öffnest die Tür", this);
                    currentRoomNumber = nextRoom.getRoomNumber();
                    currentRoom.setLeaveRoom(true);

                    //previousRoomNumber = raums.indexOf(currentRaum);
                    Map map;
                    if (this.player.getInventory().findItemByName("Karte") != null) {
                        map = (Map) this.player.getInventory().findItemByName("Karte");
                        map.writeMap(currentRoom.getRoomNumber(), door.getDirection().toString());
                    } else if (this.findRoomByNumber(4).getInventory().findItemByName("Karte") != null) {
                        map = (Map) this.findRoomByNumber(4).getInventory().findItemByName("Karte");
                        map.writeMap(currentRoom.getRoomNumber(), door.getDirection().toString());
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
     * @see Dungeon#findRoomByNumber(int)
     */
    private Room getNextRoom(int currentRoomNumber) {
        for (Room room : rooms) {
            if (room.roomNumber == currentRoomNumber) {
                return room;
            }
        }
        return rooms.get(0);
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

    public void setRoomNumber(Room room) {
        this.currentRoomNumber = rooms.indexOf(room) + 1;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Room> getRooms() {
        return rooms;
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
    public void doExamine(String[] parsed_command, int count) {
        if (count != 2) {
            Textie.printText("Was soll untersucht werden?", this);
        } else {
            switch (parsed_command[1].toLowerCase()) {
                case "raum":
                    getCurrentRoom().examine(this);
                    break;
                case "inventar":
                    if (getCurrentRoom() instanceof DarkRoom) {
                        Item item = this.getPlayer().getInventory().findItemByName("Fackel");
                        if (item == null) {
                            Textie.printText("Du kannst nichts sehen!", this);
                        }
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
                    if (this.getCurrentRoom().getInventory().hasItem("Truhe") == false) {
                        Textie.printText("Hier ist keine Truhe", this);
                    } else {
                        StorageItem truhe = (StorageItem) this.getCurrentRoom().getInventory().findItemByName("Truhe");
                        truhe.examine(this);
                    }
                    break;
                default:
                    Item item = Textie.chooseInventory(parsed_command[1], this);
                    if (item == null) {
                        Textie.printText("Das Objekt gibt es nicht.", this);
                    } else {
                        item.examine(item, this);
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
        if (item.isPickable() == true) {
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
    public void doTake(Item item) {
        if (item == null) {
            Textie.printText("Unbekanntes Item.", this);
        } else {
            if (item.isPickable() == true) {
                if (this.getCurrentRoom().getInventory().transferItem(this.getPlayer().getInventory(), item)) {

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
    public void doGive(String[] parsed_command, int count) {
        if (count == 2) {
            String itemToUse = IOUtils.convertToName(parsed_command[1]);
            //Item itemToUse = chooseInventory(parsed_command[1]);
            if (itemToUse.equals(this.getCurrentRoom().getHuman().getQuestItem())) {
                Item item = Textie.chooseInventory(parsed_command[1], this);
                boolean isRemoved = this.getPlayer().getInventory().removeItem(item);
                if (isRemoved == true) {
                    Textie.printText(this.getCurrentRoom().getHuman().getQuestDoneText(), this);
                    this.getCurrentRoom().getHuman().setQuestDone(true);
                    if (Textie.recieveItem(this.getCurrentRoom().getHuman().getRewarditem(), player.getInventory())) {
                        Textie.printText("Im Gegenzug bekommst du von mir auch etwas. Bitteschön.", this);
                    } else {
                        Textie.printText("Dein Inventar ist leider voll. Komm wieder, wenn du Platz hast.", this);
                        this.getCurrentRoom().getHuman().setGaveItem(true);
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
        StorageItem dieTruhe = (StorageItem) dungeon.getCurrentRoom().getInventory().findItemByName("Truhe");
        if (dungeon.getPlayer().getInventory().getSize() < dungeon.getPlayer().getInventory().getMaxSlots() && dieTruhe.getInventory().hasItem(item.getName())) {
            dieTruhe.getInventory().transferItem(dungeon.getPlayer().getInventory(), item);
            return true;
        } else {
            return false;
        }
    }
}
