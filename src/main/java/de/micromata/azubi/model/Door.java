package de.micromata.azubi.model;

import de.micromata.azubi.Textie;

import java.io.Serializable;

/**
 * This defines a simple door.
 *
 * @author Lukas F&uuml;lling
 * @version 1.0
 */
public class Door implements Serializable{
    private static final long serialVersionUID = 8951820683349398179L;
    private long uid;
    private int nextRoom;
    private boolean locked;
    private Direction direction;
    private Room currentRoom;

    /**
     * @param UID              The unique identifier we can use to define a key or a switch to a certain door.
     * @param richtung         The orientation the door should have in the room.
     * @param raumNr           The room you come, when you go through
     * @param initialLockState Defines if the door is locked at game launch.

    public Door(int UID, Richtung richtung, int raumNr, boolean initialLockState) {

        this.uid = UID;
        this.richtungRaum1 = richtung;
        this.nextRoom = nextRoom;
        this.locked = initialLockState;
        //richtungRaum2 = Richtung.getOpposite(richtungRaum1);
    }
    */
    /**
     * Exits the current room and enters the new one if the door is unlocked.
     */
    public void open() {

        if (locked = false) {
            currentRoom.setLeaveRoom(true);
        } else {
            Textie.printText("Diese Tür ist verschlossen.");
        }
    }


    /**
     * @return Returns the door's UID
     */
    public long getUid() {

        return uid;
    }

    /**
     * @param locked Sets the door's lock state
     */
    public void setLocked(boolean locked) {

        this.locked = locked;
    }

    /**
     * @return Returns the door's lock state.
     */
    public boolean isLocked() {

        return locked;
    }

    public void toogleLock() {
        if (locked == true) {
            locked = false;
        } else {
            locked = true;
        }
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     *
     * @param nextRoom The RoomNumber of the next room
     */
    public void setNextRoom(int nextRoom) {
        this.nextRoom = nextRoom;
    }

    public int getNextRoom() {
        return nextRoom;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
}
