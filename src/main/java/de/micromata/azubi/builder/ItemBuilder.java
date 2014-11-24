package de.micromata.azubi.builder;

import de.micromata.azubi.model.Item;

/**
 * Created by jsiebert on 31.10.14.
 */
public class ItemBuilder extends BaseItemBuilder{


    @Override
    protected Item createInstance() {
        return new Item();
    }
}
