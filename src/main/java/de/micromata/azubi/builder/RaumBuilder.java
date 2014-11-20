package de.micromata.azubi.builder;

import de.micromata.azubi.model.Dungeon;
import de.micromata.azubi.model.Raum;

/**
 * Created by jsiebert on 20.11.14.
 */
public class RaumBuilder extends BaseRaumBuilder implements Builder<Raum> {

    Raum raum;

    public RaumBuilder(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    protected Raum createInstance(Dungeon dungeon) {
        return new Raum(dungeon);
    }
}
