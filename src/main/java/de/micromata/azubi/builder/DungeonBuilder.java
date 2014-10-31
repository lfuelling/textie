package de.micromata.azubi.builder;

import de.micromata.azubi.model.Dungeon;

/**
 * Created by jsiebert on 30.10.14.
 */
import java.util.ArrayList;
import java.util.List;

public class DungeonBuilder implements Builder<Dungeon>{
    Dungeon dungeon;
    PlayerBuilder pb;
    List<RaumBuilder> rbs = new ArrayList<>();

    public DungeonBuilder(Dungeon dungeon) {
    	this.dungeon = dungeon;
    }
    
    public DungeonBuilder addRoom(RaumBuilder rm) {
        rbs.add(rm);
        return this;
    }

    public DungeonBuilder add(PlayerBuilder pb) {
        this.pb = pb;
        return this;
    }

    @Override
    public DungeonBuilder build() {
        for(RaumBuilder rb : rbs){
            dungeon.getRooms().add(rb.get());
        }
        dungeon.setPlayer(pb.get());
        return this;
    }

    @Override
    public Dungeon get() {
        return dungeon;
    }
}
