package de.micromata.azubi.builder;

import de.micromata.azubi.model.Item;
import de.micromata.azubi.model.Map;

/**
 * Created by jsiebert on 31.10.14.
 */
public class MapBuilder extends BaseItemBuilder {

    private Map map;


    @Override
    public MapBuilder build() {
        super.build();
        return this;
    }

    @Override
    public Map get() {
        return this.map;
    }

    @Override
    protected Item createInstance() {
        map = new Map();
        return map;
    }
}
