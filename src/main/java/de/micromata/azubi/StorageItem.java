package de.micromata.azubi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lfuelling on 24.09.14.
 */
public class StorageItem extends Item {
    protected List<Item> items = new ArrayList<Item>();
    boolean lockable;
    boolean lockState;
    String name;


    public StorageItem(){

    }

    public StorageItem(String name, String untersucheText, String benutzeText, boolean pickable, boolean lockable, boolean initialLockState, Item... items) {
        super(name, untersucheText, benutzeText, pickable);
        for (Item item : items) {
            this.items.add(item);
        }
        this.lockable = lockable;
        this.lockState = initialLockState;
        this.name = name;
    }

    public boolean hasItem(Item item) {
        if(items.contains(item)) {
            return true;
        }
        return false;
    }

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

}
