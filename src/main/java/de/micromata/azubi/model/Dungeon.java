package de.micromata.azubi.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser;
import de.micromata.azubi.IOUtils;
import de.micromata.azubi.Textie;
import de.micromata.azubi.builder.*;

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
        ObjectMapper mapper = new ObjectMapper();
        mapper.getFactory().enable(JsonParser.Feature.ALLOW_COMMENTS);
        List<LinkedHashMap> raumList = null;
        try {
            raumList = mapper.readValue(new File("TextieConf.json"), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DungeonBuilder dungeonBuilder = new DungeonBuilder(new Dungeon());
        PlayerBuilder fremder = new PlayerBuilder().addName("Fremder").add(new InventarBuilder().addSize(5).build()).build();
        dungeonBuilder.add(fremder);

        for (LinkedHashMap listItem : raumList) {
            BaseRaumBuilder raum = null;
            String roomClass = (String) listItem.get("class");
            switch(roomClass){
                case "de.micromata.azubi.model.Raum":
                    raum = new RaumBuilder(dungeonBuilder.get());
                    break;
                case "de.micromata.azubi.model.DarkRoom":
                    raum = new DarkRoomBuilder(dungeonBuilder.get());
                    break;
            }
            raum.addRoomNumber((int) listItem.get("roomNumber")).addwillkommensNachricht((String) listItem.get("willkommensNachricht"));
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
                rewardItemBuilder.setName((String)rewardItem.get("name")).setBenutzeText((String) rewardItem.get("benutzeText")).setUntersucheText((String) rewardItem.get("untersucheText")).build();
                hb.setRewarditem(rewardItemBuilder).build();
                raum.addHuman(hb);
            }
            InventarBuilder ib = new InventarBuilder();
            List<BaseItemBuilder> ibs = new ArrayList();
            LinkedHashMap inventory = (LinkedHashMap) listItem.get("inventory");
            ArrayList<LinkedHashMap> items = (ArrayList<LinkedHashMap>) inventory.get("items");
            BaseItemBuilder itemBuilder = null;
            for(LinkedHashMap item : items){
                String itemClass = (String) item.get("class");
                switch (itemClass) {
                    case "de.micromata.azubi.model.Item":
                        itemBuilder = new ItemBuilder();
                        break;
                    case "de.micromata.azubi.model.ToggleItem":
                        itemBuilder = new ToggleItemBuilder().setState((boolean) item.get("state"));
                        break;
                    case "de.micromata.azubi.model.StorageItem":
                        InventarBuilder chestInvBuilder;
                        itemBuilder = new StorageItemBuilder().setLockState((boolean) item.get("lockState")).setInventarBuilder(chestInvBuilder = new InventarBuilder());
                        LinkedHashMap chestInv = (LinkedHashMap) item.get("inventory");
                        ArrayList<LinkedHashMap> chestItems = (ArrayList<LinkedHashMap>) chestInv.get("items");
                        for(LinkedHashMap chestItem : chestItems){
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
                            chestItemBuilder.setName((String) chestItem.get("name")).setBenutzeText((String) chestItem.get("benutzeText")).setUntersucheText((String) chestItem.get("untersucheText")).build();
                            chestInvBuilder.addItem(chestItemBuilder);
                        }
                        chestInvBuilder.build();
                        break;
                    case "de.micromata.azubi.model.Karte":
                        itemBuilder = new KartenBuilder();
                        break;
                    default:
                        System.out.println("Fehler in der Konfiguration. Fehlerhafte Klasse in Item");
                }
                itemBuilder.setName((String) item.get("name"))
                        .setPickable((boolean) item.get("pickable"))
                        .setUntersucheText((String) item.get("untersucheText"))
                        .setBenutzeText((String) item.get("benutzeText"))
                        .build();
                ibs.add(itemBuilder);
            }
            for(BaseItemBuilder itemBuilder1 : ibs){
               ib.addItem(itemBuilder1);
            }
            ib.build();
            raum.addInventory(ib).build();
            DoorBuilder db;
            List<LinkedHashMap> doorList = (List<LinkedHashMap>) listItem.get("doors");
            for(LinkedHashMap doorItem : doorList){
                db = new DoorBuilder()
                .setLock((boolean) doorItem.get("locked"))
                .setRichtungByText((String) doorItem.get("richtung"))
                .setNextRoom((int) doorItem.get("nextRoom")).build();
                raum.addDoor(db);
            }
            raum.build();
            dungeonBuilder.addRoom(raum);
        }
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
            } else {

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
                        getCurrentRaum().discover();
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
