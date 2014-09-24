package de.micromata.azubi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lfuelling on 24.09.14.
 */
public class StorageItem extends Item {
    protected List<Item> items = new ArrayList<Item>();

    public StorageItem(String name, String untersucheText, String benutzeText, boolean pickable, Item... items) {
        super(name, untersucheText, benutzeText, pickable);
        for (Item item : items) {
            this.items.add(item);
        }
    }

    public boolean hasItem(Item item) {
        if(items.contains(item)) {
            return true;
        }
        return false;
    }

    public boolean removeItem(Item item) {
        if(items.remove(item))
            return true;
        return false;
    }

    public void listItems() {
        System.out.println("Im Raum befindet sich:");
        for (Item item : items) {
            if(item == null) {
                System.out.println("\tKein Objekt");
            } else {
                System.out.println("\t" + item.getName());
            }
        }

    }

}
