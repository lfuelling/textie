package de.micromata.azubi.model;

import de.micromata.azubi.Textie;

/**
 * This defines a simple door.
 *
 * @author Lukas F&uuml;lling
 * @version 1.0
 */
public class Door {
    long uid;
    private Raum nextRoom;
    private boolean locked;
    private Richtung richtung;
    private Raum currentRoom;

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

    public void setRichtung(Richtung richtung) {
        this.richtung = richtung;
    }

    public void setNextRoom(Raum nextRoom) {
        this.nextRoom = nextRoom;
    }

    public Raum getNextRoom() {
        return nextRoom;
    }

    public Richtung getRichtung() {
        return richtung;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setCurrentRoom(Raum currentRoom) {
        this.currentRoom = currentRoom;
    }
}
