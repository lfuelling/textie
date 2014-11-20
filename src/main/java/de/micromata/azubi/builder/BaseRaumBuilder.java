package de.micromata.azubi.builder;

import de.micromata.azubi.model.Dungeon;
import de.micromata.azubi.model.Inventory;
import de.micromata.azubi.model.Raum;
import java.util.*;

/**
 * Created by jsiebert on 30.10.14.
 */
public abstract class BaseRaumBuilder implements Builder<Raum> {

    private Raum raum;
    private InventarBuilder ib = new InventarBuilder();
    private String willkommensNachricht;
    private int roomNumber;
    private List<DoorBuilder> dbs = new ArrayList<>();
    private HumanBuilder hb = new HumanBuilder();
    private GhostBuilder gb = new GhostBuilder();

    public BaseRaumBuilder(Dungeon dungeon){
        this.raum = createInstance(dungeon);
    }

    protected abstract Raum createInstance(Dungeon dungeon);


    public BaseRaumBuilder addInventory(InventarBuilder ib){
        this.ib = ib;
        return this;
    }

    public BaseRaumBuilder addRoomNumber(int roomNumber){
        this.roomNumber = roomNumber;
        return this;
    }

    public BaseRaumBuilder addDoor(DoorBuilder db){
        dbs.add(db);
        return this;
    }

    public BaseRaumBuilder addHuman(HumanBuilder hb){
        this.hb = hb;
        return this;
    }

    public BaseRaumBuilder addwillkommensNachricht(String willkommensNachricht){
        this.willkommensNachricht = willkommensNachricht;
        return this;
    }

    public BaseRaumBuilder addGhost(GhostBuilder gb){
        this.gb = gb;
        return this;
    }



    @Override
    public BaseRaumBuilder build() {
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
    public Raum get() {
        return raum;
    }
}
