package de.micromata.azubi;

/**
 * Created by lfuelling on 24.09.14.
 */
public class StorageItem extends Item {
    public StorageItem(String name, String untersucheText, String benutzeText, boolean pickable) {
        super(name, untersucheText, benutzeText, pickable);
    }

    public boolean hasItem (Item item) {
        //TODO: returns false until implemented.
        return false;
    }

    public void removeItem (Item item) {
        //TODO: Needs implementation.
    }
}
