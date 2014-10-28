package de.micromata.azubi;

/**
 * This is a Trapdoor.
 *
 * @author Lukas F&uuml;lling
 * @version 1.0
 */
public class Trapdoor {
    int uid;
    Raum raum;
    boolean locked;

    /**
     * @param UID              The unique identifier we can use to define a key or a switch to a certain trapdoor.
     * @param raum             The room the door is placed in.
     * @param initialLockState Defines if the door is locked at game launch.
     * @see de.micromata.azubi.Door
     */
    public Trapdoor(int UID,boolean initialLockState) {

        this.uid = UID;
        this.raum = raum;
        this.locked = initialLockState;
    }

    /**
     * Exits the current room and enters the new one if the door is unlocked.
     */
    public void open() {

        if (locked = false) {
            raum.setLeaveRoom(true);
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

}
