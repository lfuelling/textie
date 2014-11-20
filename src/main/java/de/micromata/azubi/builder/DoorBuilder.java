package de.micromata.azubi.builder;

import de.micromata.azubi.Utils;
import de.micromata.azubi.model.*;

/**
 * Created by jsiebert on 30.10.14.
 */
public class DoorBuilder implements Builder<Door> {

    private Door door = new Door();
    private Direction direction;
    private int nextRoom;
    private boolean locked;

    public DoorBuilder setDirection(Direction direction){
        this.direction = direction;
        return this;
    }

    public DoorBuilder setRichtungByText(String richtung){
        this.direction = Direction.getByText(richtung);
        return this;
    }

    public DoorBuilder setNextRoom(int connectedRoom){
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
        door.setDirection(direction);
        door.setNextRoom(nextRoom);
        door.setLocked(locked);
        return this;
    }

    @Override
    public Door get() {
        return door;
    }
}
