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

    public boolean hasItem (Item item) {
        //TODO: returns false until implemented.

        return false;
    }

    public void removeItem (Item item) {
        public boolean removeItem(Item item) {
            if(items.remove(item))
                return true;
            return false;
        }

    }
}
