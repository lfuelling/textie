package de.micromata.azubi;

/**
 * This is a Trapdoor.
 * @version 1.0
 * @author Lukas F&uuml;lling
 *
 */
public class Trapdoor {
    int uid;
    Raum raum1;
    Raum raum2;
    boolean locked;

    /**
     * @see de.micromata.azubi.Door
     * @param UID The unique identifier we can use to define a key or a switch to a certain trapdoor.
     * @param raum1 The room the door is placed in.
     * @param raum2 The room the door leads to. (and where it's also placed in)
     * @param initialLockState Defines if the door is locked at game launch.
     */
    public Trapdoor(int UID, Raum raum1, Raum raum2, boolean initialLockState) {
        this.uid = UID;
        this.raum1 = raum1;
        this.raum2 = raum2;
        this.locked = initialLockState;
    }

    /**
     * Exits the current room and enters the new one if the door is unlocked.
     */
    public void open(){
        if(locked = false){
            raum1.setLeaveRoom(true);
        } else {
            Textie.printText("Diese Tür ist verschlossen.");
        }
    }

    /**
     *
     * @return Returns the door's UID
     */
    public int getUid() {
        return uid;
    }

    /**
     *
     * @param locked Sets the door's lock state
     */
    public void setLocked(boolean locked) {

        this.locked = locked;
    }

    /**
     *
     * @return Returns the door's lock state.
     */
    public boolean isLocked() {

        return locked;
    }

}
