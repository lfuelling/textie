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
    Richtung richtungRaum2;
    Raum raum1;
    Raum raum2;
    boolean locked;

    /**
     *
     * @param UID The unique identifier we can use to define a key or a switch to a certain door.
     * @param richtung The orientation the door should have in the room.
     * @param raum1 The room the door is placed in.
     * @param raum2 The room the door leads to. (and where it's also placed in)
     * @param initialLockState Defines if the door is locked at game launch.
     */
    public Door(int UID, Richtung richtung, Raum raum1, Raum raum2, boolean initialLockState) {
        this.uid = UID;
        this.richtungRaum1 = richtung;
        this.raum1 = raum1;
        this.raum2 = raum2;
        this.locked = initialLockState;
        richtungRaum2 = Richtung.getOpposite(richtungRaum1);
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
