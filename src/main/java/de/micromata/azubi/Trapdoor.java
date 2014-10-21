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

    public int getUid() {
        return uid;
    }

    public void setLocked(boolean locked) {

        this.locked = locked;
    }

    public boolean isLocked() {

        return locked;
    }

}
