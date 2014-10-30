package de.micromata.azubi.builder;

import de.micromata.azubi.model.Dungeon;

/**
 * Created by jsiebert on 30.10.14.
 */
public class DungeonBuilder implements Builder<Dungeon>{
    Dungeon dungeon = Dungeon.getDungeon();

    public DungeonBuilder addRoom(RaumBuilder rm) {
        dungeon.getRooms().add(rm.get());
        return this;
    }

    public DungeonBuilder add(PlayerBuilder pb) {
        dungeon.setPlayer(pb.get());
        return this;
    }

    @Override
    public Builder<Dungeon> build() {
        //dungeon.init();
        /*
         public void init() {
         player = new Player("Fremder", true);
         previousRoomNumber = 1;
         initRooms();
         initInventories();
         initHumans();
         initDoors();
         initDoorSchalter();

         }
         */
        return this;
    }

    @Override
    public Dungeon get() {
        return dungeon;
    }
}
