
package de.micromata.azubi.model;

import de.micromata.azubi.Textie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukas F&uuml;lling (l.fuelling@micromata.de)
 * @author Julian Siebert (j.siebert@micromata.de)
 */
public class Inventory implements Serializable {

    private static final long serialVersionUID = -706607514666174299L;

    public final static int MAX_SLOTS_INVENTORY = 99;

    private List<Item> items = new ArrayList<>();
    private int maxSlots = MAX_SLOTS_INVENTORY;

    /**
     * Sets the size of the items
     *
     * @param slots Number of slots
     */
    public void setInventorySize(int slots) {

        //TODO Aktuelle Anzahl der Items mit der Neuen Anzahl an Slots prüfen
        if (items.size() > slots) {

            List<Item> remove = new ArrayList<>();
            for (int i = slots; i < items.size(); i++) {
                remove.add(items.get(i));
            }
            items.removeAll(remove);
        }
        maxSlots = slots;
    }

    /**
     * Increases the size(slots) of the inventory
     * @param slots Slots to add
     * @return The new size of the inventory
     */
    public int increaseMaxSlots(int slots){
        maxSlots += slots;
        return maxSlots;
    }

    /**
     * @return The size of the items.
     */
    public int getMaxSlots() {

        return maxSlots;
    }


    /**
     * Adds an Item to the items.
     *
     * @param item The item to add.
     */
    public void addItem(Item item) {

        //TODO if(itemsize > slots) do not add
        this.items.add(item);
    }

    /**
     * Removes an item.
     *
     * @param item The item to remove.
     */
    public boolean removeItem(Item item) {

        return this.items.remove(item);
    }

    /**
     * Lists the items.
     */
    public void listItems(Dungeon dungeon) {

        if (this.items.size() > 0) {
            for (Item items : this.items) {
                String objectName = items.getName();
                Textie.printText("\t" + objectName, dungeon);
            }
        } else {
            Textie.printText("Nichts.", dungeon);
        }
    }

    /**
     * Check if an item is in the items.
     *
     * @param itemName The item you search.
     * @return True if the item is in there.
     */
    public boolean hasItem(String itemName) {

        if (findItemByName(itemName) != null) {
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
        slot = this.items.indexOf(items);
        if (slot > -128) {
            return slot;
        }
        return -128;
    }

    public Item findItemByName(String itemName) throws NullPointerException {

        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    /**
     * @param target         Inventory which should get the item
     * @param itemToTransfer Item to transfer
     * @return True if the item was transferred.
     */
    public boolean transferItem(Inventory target, Item itemToTransfer) {
        if(itemToTransfer.isPickable() == false){
            Textie.printText("Du kannst dieses Item nicht aufheben.");
            return false;
        }
        if ((target.getSize() < target.maxSlots) == false) {
            return false;
        }

        if ((target.items.add(itemToTransfer) && this.items.remove(itemToTransfer)) == false) {
            return false;
        }
        return true;
    }

    public Item findItemByUID(int UID) {
        for (Item item : items) {
            if (item.getItemID() == UID) {
                return item;
            }
        }
        return null;
    }

    public int getSize() {
        return items.size();
    }
}
