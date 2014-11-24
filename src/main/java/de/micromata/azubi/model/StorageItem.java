package de.micromata.azubi.model;

import de.micromata.azubi.Textie;

/**
 * @author Lukas F&uuml;lling (l.fuelling@micromata.de)
 * @see Item
 */
public class StorageItem extends Item implements Examineable{
    //boolean lockable;
    boolean lockState;
    int itemID;
    Inventory inventory;

    public StorageItem() {

    }


  /**
   * Initializes a new storageItem
   * @param itemID The item's ID
   * @param name Name of the Item
   * @param untersucheText The inspection text
   * @param benutzeText The use text
   * @param pickable Is it pickable?
   * @param lockable Is it lockable?
   * @param initialLockState What is it initial lock state?
   */

    public StorageItem(int itemID, String name, String untersucheText, String benutzeText, boolean pickable, boolean lockable, boolean initialLockState) {
        super(itemID, name, untersucheText, benutzeText);
        //this.lockable = lockable;
        this.lockState = initialLockState;
        this.itemID = itemID;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean getLockState() {
        return lockState;
    }

    public void setLockState(boolean lockState) {
        this.lockState = lockState;
    }

    @Override
    public void examine(Dungeon dungeon) {
        if (lockState == true) {
            Textie.printText("Die Truhe ist verschlossen.", dungeon);
        } else {
            inventory.listItems(dungeon);
        }
    }
}
