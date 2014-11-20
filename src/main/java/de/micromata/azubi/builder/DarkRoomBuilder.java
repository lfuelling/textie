package de.micromata.azubi.builder;

import de.micromata.azubi.model.DarkRoom;
import de.micromata.azubi.model.Dungeon;
import de.micromata.azubi.model.Raum;

/**
 * Created by jsiebert on 20.11.14.
 */
public class DarkRoomBuilder extends BaseRaumBuilder implements Builder<Raum> {

    private DarkRoom raum;

    public DarkRoomBuilder(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    protected Raum createInstance(Dungeon dungeon) {
        this.raum = new DarkRoom(dungeon);
        return raum;
    }
}

/*
    private DarkRoom raum;
    private InventarBuilder ib = new InventarBuilder();
    private String willkommensNachricht;
    private int roomNumber;
    private List<DoorBuilder> dbs = new ArrayList<>();
    private HumanBuilder hb = new HumanBuilder();
    private GhostBuilder gb = new GhostBuilder();

    public DarkRoomBuilder(Dungeon dungeon){
        raum = new DarkRoom(dungeon);
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
        raum.setInventory(inv);
        raum.setWillkommensNachricht(willkommensNachricht);
        raum.setRoomNumber(roomNumber);
        for(DoorBuilder db : dbs){
            raum.addDoorToDoors(db.get());
        }
        raum.setHuman(hb.get());
        raum.setGhost(gb.get());
        return this;
    }

    @Override
    public DarkRoom get() {
        return raum;
    }
 */