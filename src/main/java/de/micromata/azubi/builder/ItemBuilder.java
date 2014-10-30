package de.micromata.azubi.builder;

import de.micromata.azubi.model.Item;

/**
 * Created by jsiebert on 30.10.14.
 */
public class ItemBuilder implements Builder<Item> {

    private Item item;
    private String benutzeText;
    private String name;
    private String untersucheText;


    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setBenutzeText(String benutzeText) {
        this.benutzeText = benutzeText;
        return this;
    }

    public ItemBuilder setUntersucheText(String untersucheText) {
        this.untersucheText = untersucheText;
        return this;
    }

    @Override
    public ItemBuilder build() {
        item.setName(name);
        item.setBenutzeText(benutzeText);
        item.setUntersucheText(untersucheText);
        return this;
    }

    @Override
    public Item get() {
        return item;
    }
}
