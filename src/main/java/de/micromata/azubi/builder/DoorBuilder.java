package de.micromata.azubi.builder;

import de.micromata.azubi.Utils;
import de.micromata.azubi.model.*;

/**
 * Created by jsiebert on 30.10.14.
 */
public class DoorBuilder implements Builder<Door> {

    private Door door = new Door();
    private Richtung richtung;
    private Raum nextRoom;
    private boolean locked;

    public DoorBuilder setRichtung(Richtung richtung){
        this.richtung = richtung;
        return this;
    }

    public DoorBuilder setNextRoom(Raum connectedRoom){
        this.nextRoom = connectedRoom;
        return this;
    }

    public DoorBuilder setLock(boolean locked){
        this.locked = locked;
        return this;
    }


    @Override
    public DoorBuilder build() {
        door.setUid(Utils.nextId());
        door.setRichtung(richtung);
        door.setNextRoom(nextRoom);
        door.setLocked(locked);
        return this;
    }

    @Override
    public Door get() {
        return door;
    }
}
