package de.micromata.azubi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukas F&uuml;lling (l.fuelling@micromata.de)
 * @see de.micromata.azubi.Item
 */
public class StorageItem extends Item {
    protected List<Item> items = new ArrayList<Item>();
    boolean lockable;
    boolean lockState;
    String name;
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
   * @param items What does it contain?
   */
    public StorageItem(int itemID,String name, String untersucheText, String benutzeText, boolean pickable, boolean lockable, boolean initialLockState, Item... items) {
        super(itemID, name, untersucheText, benutzeText, pickable);
        for (Item item : items) {
            this.items.add(item);
        }
        this.lockable = lockable;
        this.lockState = initialLockState;
        this.name = name;
        this.itemID = itemID;
    }

  /**
   *
   * @param item the item you search
   * @return Returns true if the item is in there.
   */
    public boolean hasItem(Item item) {
        if(items.contains(item)) {
            return true;
        }
        return false;
    }

  /**
   *
   * @param item The Item you want to remove
   * @return True if the Item was removed.
   */
    public boolean removeItem(Item item) {
        if(lockable == false || lockState == false) {
            if(items.remove(item)) {
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println(name + " ist verschlossen.");
            return false;
        }
    }

    public void listItems() {
        if(lockable == false || lockState == false) {
            System.out.println("In " + name + " befindet sich:");
            for (Item item : items) {
                if(item == null) {
                    System.out.println("\tKein Objekt");
                } else {
                    System.out.println("\t" + item.getName());
                }
            }
        } else {
            System.out.println(name + " ist verschlossen.");
        }

    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

}
