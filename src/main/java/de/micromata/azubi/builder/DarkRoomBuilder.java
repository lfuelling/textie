package de.micromata.azubi.builder;

import de.micromata.azubi.model.DarkRoom;
import de.micromata.azubi.model.Dungeon;
import de.micromata.azubi.model.Room;

/**
 * Created by jsiebert on 20.11.14.
 */
public class DarkRoomBuilder extends BaseRoomBuilder implements Builder<Room> {

    private DarkRoom room;

    public DarkRoomBuilder(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    protected Room createInstance(Dungeon dungeon) {
        this.room = new DarkRoom(dungeon);
        return room;
    }
}

/*
    private DarkRoom room;
    private InventarBuilder ib = new InventarBuilder();
    private String willkommensNachricht;
    private int roomNumber;
    private List<DoorBuilder> dbs = new ArrayList<>();
    private HumanBuilder hb = new HumanBuilder();
    private GhostBuilder gb = new GhostBuilder();

    public DarkRoomBuilder(Dungeon dungeon){
        room = new DarkRoom(dungeon);
    }


    public DarkRoomBuilder addInventory(InventarBuilder ib){
        this.ib = ib;
        return this;
    }

    public DarkRoomBuilder addRoomNumber(int roomNumber){
        this.roomNumber = roomNumber;
        return this;
    }

    public DarkRoomBuilder addDoor(DoorBuilder db){
        dbs.add(db);
        return this;
    }

    public DarkRoomBuilder addHuman(HumanBuilder hb){
        this.hb = hb;
        return this;
    }

    public DarkRoomBuilder addwillkommensNachricht(String willkommensNachricht){
        this.willkommensNachricht = willkommensNachricht;
        return this;
    }

    public DarkRoomBuilder addGhost(GhostBuilder gb){
        this.gb = gb;
        return this;
    }



    @Override
    public DarkRoomBuilder build() {
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
    public DarkRoom get() {
        return room;
    }
 */