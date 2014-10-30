package de.micromata.azubi;

/**
 * This defines a simple door.
 *
 * @author Lukas F&uuml;lling
 * @version 1.0
 */
public class Door {
    int uid;
    Richtung richtungRaum1;
    //Richtung richtungRaum2;
    int raumNr;
    boolean locked;

    /**
     * @param UID              The unique identifier we can use to define a key or a switch to a certain door.
     * @param richtung         The orientation the door should have in the room.
     * @param raumNr           The room you come, when you go through
     * @param initialLockState Defines if the door is locked at game launch.
     */
    public Door(int UID, Richtung richtung, int raumNr, boolean initialLockState) {

        this.uid = UID;
        this.richtungRaum1 = richtung;
        this.raumNr = raumNr;
        this.locked = initialLockState;
        //richtungRaum2 = Richtung.getOpposite(richtungRaum1);
    }

    /**
     * Exits the current room and enters the new one if the door is unlocked.
     */
    public void open() {

        if (locked = false) {
            Dungeon.getDungeon().getCurrentRaum().setLeaveRoom(true);
        } else {
            Textie.printText("Diese Tür ist verschlossen.");
        }
    }


    /**
     * @return Returns the door's UID
     */
    public int getUid() {

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

}
