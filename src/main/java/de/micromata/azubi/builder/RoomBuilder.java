package de.micromata.azubi.builder;

import de.micromata.azubi.model.Dungeon;
import de.micromata.azubi.model.Room;

/**
 * Created by jsiebert on 20.11.14.
 */
public class RoomBuilder extends BaseRoomBuilder implements Builder<Room> {

    Room room;

    public RoomBuilder(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    protected Room createInstance(Dungeon dungeon) {
        return new Room(dungeon);
    }
}
