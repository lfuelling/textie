package de.micromata.azubi.builder;

import de.micromata.azubi.model.Item;
import de.micromata.azubi.model.StorageItem;

/**
 * Created by jsiebert on 31.10.14.
 */
public class StorageItemBuilder extends BaseItemBuilder {

    private StorageItem storageItem;
    private InventarBuilder ib;
    private boolean lockState;

    @Override
    protected Item createInstance() {
        storageItem = new StorageItem();
        return storageItem;
    }


    public StorageItemBuilder setInventarBuilder(InventarBuilder ib) {
        this.ib = ib;
        return this;
    }

    public StorageItemBuilder setLockState(boolean initalLockState) {
        this.lockState = initalLockState;
        return this;
    }

    @Override
    public StorageItemBuilder build() {
        super.build();
        storageItem.setLockState(lockState);
        storageItem.setInventory(ib.get());
        return this;
    }

    @Override
    public StorageItem get() {
        return storageItem;
    }


}

