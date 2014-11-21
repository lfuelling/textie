package de.micromata.azubi.builder;

import de.micromata.azubi.Utils;
import de.micromata.azubi.model.Item;

/**
 * Created by jsiebert on 30.10.14.
 */
public abstract class BaseItemBuilder implements Builder<Item> {

    private Item item;
    private String benutzeText;
    private String name;
    private String untersucheText;
    private boolean pickable;

    public BaseItemBuilder(){
        this.item = createInstance();
    }

    protected abstract Item createInstance();

    public BaseItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public BaseItemBuilder setBenutzeText(String benutzeText) {
        this.benutzeText = benutzeText;
        return this;
    }

    public BaseItemBuilder setUntersucheText(String untersucheText) {
        this.untersucheText = untersucheText;
        return this;
    }

    public BaseItemBuilder setPickable(boolean pickable){
        this.pickable = pickable;
        return this;
    }

    @Override
    public BaseItemBuilder build() {
        //item.setUid(Utils.nextId());
        item.setName(name);
        item.setUseText(benutzeText);
        item.setExamineText(untersucheText);
        item.setPickable(pickable);
        return this;
    }

    @Override
    public Item get() {
        return item;
    }
}
