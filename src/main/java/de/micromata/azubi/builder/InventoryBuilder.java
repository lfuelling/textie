package de.micromata.azubi.builder;

import java.util.ArrayList;
import java.util.List;
import de.micromata.azubi.model.Inventory;

/**
 * Created by jsiebert on 30.10.14.
 */
public class InventoryBuilder implements Builder<Inventory>{
    private int size = Inventory.MAX_SLOTS_INVENTORY;
    private Inventory inventory = new Inventory();
    private List<BaseItemBuilder> ibs = new ArrayList<>();

    public InventoryBuilder addSize(int size){
        this.size = size;
        return this;
    }

    public InventoryBuilder addItem(BaseItemBuilder ib){
        ibs.add(ib);
        return this;
    }



    @Override
    public InventoryBuilder build() {
        inventory.setInventorySize(size);
        for(BaseItemBuilder ib : ibs) {
            inventory.addItem(ib.get());
        }
        return this;
    }

    @Override
    public Inventory get() {
        return inventory;
    }
}
