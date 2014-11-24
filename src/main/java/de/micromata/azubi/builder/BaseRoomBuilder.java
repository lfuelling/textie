package de.micromata.azubi.builder;

import de.micromata.azubi.model.Dungeon;
import de.micromata.azubi.model.Inventory;
import de.micromata.azubi.model.Room;
import java.util.*;

/**
 * Created by jsiebert on 30.10.14.
 */
public abstract class BaseRoomBuilder implements Builder<Room> {

    private Room room;
    private InventoryBuilder ib = new InventoryBuilder();
    private String willkommensNachricht;
    private int roomNumber;
    private List<DoorBuilder> dbs = new ArrayList<>();
    private HumanBuilder hb = new HumanBuilder();
    private GhostBuilder gb = new GhostBuilder();

    public BaseRoomBuilder(Dungeon dungeon){
        this.room = createInstance(dungeon);
    }

    protected abstract Room createInstance(Dungeon dungeon);


    public BaseRoomBuilder addInventory(InventoryBuilder ib){
        this.ib = ib;
        return this;
    }

    public BaseRoomBuilder addRoomNumber(int roomNumber){
        this.roomNumber = roomNumber;
        return this;
    }

    public BaseRoomBuilder addDoor(DoorBuilder db){
        dbs.add(db);
        return this;
    }

    public BaseRoomBuilder addHuman(HumanBuilder hb){
        this.hb = hb;
        return this;
    }

    public BaseRoomBuilder addwillkommensNachricht(String willkommensNachricht){
        this.willkommensNachricht = willkommensNachricht;
        return this;
    }

    public BaseRoomBuilder addGhost(GhostBuilder gb){
        this.gb = gb;
        return this;
    }



    @Override
    public BaseRoomBuilder build() {
        Inventory inv = ib.get();
        room.setInventory(inv);
        room.setWelcomeText(willkommensNachricht);
        room.setRoomNumber(roomNumber);
        for(DoorBuilder db : dbs){
            room.addDoorToDoors(db.get());
        }
        room.setHuman(hb.get());
        room.setGhost(gb.get());
        return this;
    }

    @Override
    public Room get() {
        return room;
    }
}
