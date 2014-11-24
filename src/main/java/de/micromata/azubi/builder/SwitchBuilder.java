package de.micromata.azubi.builder;
import de.micromata.azubi.model.Item;
import de.micromata.azubi.model.Switch;

import java.util.ArrayList;

/**
 * Created by jsiebert on 20.11.14.
 */
public class SwitchBuilder extends BaseItemBuilder {

    private Switch item;
    private ArrayList<Integer> doorIds = new ArrayList<>();

    @Override
    protected Item createInstance() {
        this.item = new Switch();
        return item;
    }

    public SwitchBuilder addDoor(int doorId){
        doorIds.add(doorId);
        return this;
    }

    @Override
    public SwitchBuilder build() {
        super.build();
        item.setAffectedDoorIds(doorIds);
        return this;
    }

    @Override
    public Switch get() {
        return item;
    }

}
