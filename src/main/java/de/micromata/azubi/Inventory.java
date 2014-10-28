
package de.micromata.azubi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukas Fülling (l.fuelling@micromata.de)
 * @author Julian Siebert (j.siebert@micromata.de)
 */
public class Inventory implements Serializable {

  private static final long serialVersionUID = -706607514666174299L;

  public Inventory() {

  }

  private static int MAX_SLOTS_INVENTORY = 99;

  private List<Item> inventory = new ArrayList<>();

  /**
   * Sets the size of the inventory
   *
   * @param slots Number of slots
   */
  public void setInventorySize(int slots) {

    MAX_SLOTS_INVENTORY = MAX_SLOTS_INVENTORY + slots;
  }

  /**
   * @return The size of the inventory.
   */
  public int getInventorySize() {

    return MAX_SLOTS_INVENTORY;
  }

  /**
   *
   * @return The inventory.
   */
  public List<Item> getInventory() {

    return inventory;
  }

  /**
   * @deprecated
   * @param inventory Inventory to set.
   */
  public void setInventory(List<Item> inventory) {

    this.inventory = inventory;
  }

  /**
   * Adds an Item to the inventory.
   * @param item The item to add.
   */
  public void addItem(Item item) {

    this.inventory.add(item);
  }

  /**
   * Removes an item.
   * @param item The item to remove.
   */
  public void removeItem(Item item) {

    this.inventory.remove(item);
  }

  /**
   * Lists the items.
   */
  public void listItems() {

    if(this.getInventory().size() > 0) {
      for(Item items : this.getInventory()) {
        String objectName = items.getName();
        Textie.printText("\t" + objectName);
      }
    } else {
      Textie.printText("Nichts.");
    }
  }

  /**
   * Check if an item is in the inventory.
   * @param itemName The item you search.
   * @return True if the item is in there.
   */
  public boolean hasItem(String itemName) {

    if(findItemByName(itemName) != null) {
      return true;
    } else {
      return false;
    }

  }

  /**
   * @param items Das Item, das gefunden werden soll
   * @return slot des items. -128, wenn keins gefunden wurde
   */
  public int findItem(Item items) {

    int slot = -128;
    slot = this.getInventory().indexOf(items);
    if(slot > -128) {
      return slot;
    }
    return -128;
  }

  public Item findItemByName(String itemName) {

    for(Item item : inventory) {
      if(item.getName().equalsIgnoreCase(itemName)) {
        return item;
      }
    }
    return null;
  }

  /**
   *
   * @param target Inventory which should get the item
   * @param itemToTransfer Item to transfer
   * @return True if the item was transferred.
   */
  public boolean transferItem(Inventory target, Item itemToTransfer) {

    if(target.getInventory().add(itemToTransfer) && this.inventory.remove(itemToTransfer)) {
      return true;
    } else {
      return false;
    }
  }

}
