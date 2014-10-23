
package de.micromata.azubi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements Serializable{

    private static final long serialVersionUID = -706607514666174299L;

    public Inventory(){
    }

    private static int MAX_SLOTS_INVENTORY = 99;

    private List<Item> inventory = new ArrayList<>();

    public void setInventorySize(int slots) {
        MAX_SLOTS_INVENTORY = MAX_SLOTS_INVENTORY + slots;
    }

    public int getInventorySize(){return MAX_SLOTS_INVENTORY;}

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }


    public void addItem(Item item) {
        this.inventory.add(item);
    }

    public void removeItem(Item item){
        this.inventory.remove(item);
    }

    public void listItems() {
        if (this.getInventory().size() > 0) {

            // FIXME inventar ist jetzt auch an räumen. semantisch falsch
            for (Item items : this.getInventory()) {
                String objectName = items.getName();
                Textie.printText("\t" + objectName);
            }
        } else {
            // FIXME inventar ist jetzt auch an räumen. semantisch falsch
            Textie.printText("Nichts.");
        }
    }

    public boolean hasItem(String itemName) {
        if(findItemByName(itemName) != null) {
            return true;
        }
        else{
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
        if (slot > -128) {
            return slot;
        }
        return -128;
    }

    public Item findItemByName(String itemName){
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public boolean transferItem(Inventory target, Item itemToTransfer){
        if(target.getInventory().add(itemToTransfer) && this.inventory.remove(itemToTransfer)) {
            return true;
        }
        else{
            return false;
        }
    }

}
