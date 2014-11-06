package de.micromata.azubi.model;

import de.micromata.azubi.*;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author Lukas F&uuml;lling (l.fuelling@micromata.de)
 * @author Julian Siebert (j.siebert@micromata.de)
 */
public class Raum implements Serializable{
    private static final long serialVersionUID = -2269575363024102428L;
    protected int roomNumber;
    protected String willkommensNachricht;
    //protected Map<Richtung, Raum> verbindungen = new HashMap<>();
    protected boolean leaveRoom = false;
    protected Inventory inventory;
    private Human human;
    private Dungeon dungeon;
    private ArrayList<Door> doors = new ArrayList<>();
    private Ghost ghost;


    public Raum(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

  /**
   *
   * @param number The number of the room
   * @param willkommensNachricht The message you get, when you enter the room.
   */
    public Raum(int number, String willkommensNachricht, Dungeon dung) {
        this(dung);
        this.roomNumber = number;
        this.willkommensNachricht = willkommensNachricht;
    }

  /**
   * @param obj An Object
   * @return True if the object is the same.
   */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

  /**
   * Starts a room.
   * @param withPrompt Set to <code>true</code>, if you want a prompt.
   */
    public void start(boolean withPrompt) {

        if (roomNumber == 3) {
            ToggleItem fackel = (ToggleItem) inventory.findItemByName("Fackel");
            if (fackel != null && fackel.getState() == true) {
                Textie.printText("Ein Windstoß sorgt dafür, dass die Fackel ausgeht.", dungeon);
                fackel.setState(false);
            }
        }
        Textie.printText(willkommensNachricht, dungeon);
        Textie.warten(this.dungeon, withPrompt);
    }


  /**
   *
   * @return The room number
   */
    public int getRoomNumber() {
        return roomNumber;
    }

  /**
   *
   * @return The intro message.
   */
    public String getWillkommensNachricht() {
        return willkommensNachricht;
    }

  /**
   * Get the next room.
   * @param door The door in the current room you want to going trough
   * @return The next room.
   */
    public Raum getNextRoom(Door door) {
        return door.getNextRoom();
    }

  /**
   * Is the room being leaved?
   * @return True if the room is to be leaved.
   */
    public boolean isLeaveRoom() {
        return leaveRoom;
    }

  /**
   *
   * @param leaveRoom you want to go?
   */
    public void setLeaveRoom(boolean leaveRoom) {
        this.leaveRoom = leaveRoom;
    }

    public void setWillkommensNachricht(String willkommensNachricht) {
        this.willkommensNachricht = willkommensNachricht;
    }

    /**
   *
   * @param verbindungen Set some connections.

    public void setVerbindungen(Map<Richtung, Raum> verbindungen) {
        this.verbindungen = verbindungen;
    }
    */

  /**
   *
   * @return The inventory of the room.
   */
    public Inventory getInventory() {
        return inventory;
    }

  /**
   *
   * @param inventory The inventory of the room.
   */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Places a human into the room
     * @param human The Human you want to place in the Room
     */
    public void setHuman(Human human) {
        this.human = human;
    }

    /**
     *
     * @return Returns the Human in the room
     */
    public Human getHuman() {
        return human;
    }

    /**
     *
     * @param richtung The direction you want to go
     * @return The door which is in the direction you asked
     */
    public Door findDoorByDirection(Richtung richtung) {

        for (Door door : this.doors) {
            if (door.getRichtung().equals(richtung)){
                return door;
            }
        }
        return null;
    }

    public void addDoorToDoors(Door door) {
        this.doors.add(door);
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }
    public Door findDoorByUID(int UID){
        for (Door door: this.doors){
            if(door.getUid() == UID){
                return door;
            }
        }
        return null;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setGhost(Ghost ghost) {
        this.ghost = ghost;
    }

}
