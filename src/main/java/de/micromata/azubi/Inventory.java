
package de.micromata.azubi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements Serializable{

    private static final long serialVersionUID = -706607514666174299L;

    public Inventory(){
    }

    private static int MAX_SLOTS_INVENTORY = 5;

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

}
