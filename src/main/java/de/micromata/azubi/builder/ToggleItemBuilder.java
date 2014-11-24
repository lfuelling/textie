package de.micromata.azubi.builder;

import de.micromata.azubi.model.Item;
import de.micromata.azubi.model.ToggleItem;

/**
 * Created by jsiebert on 31.10.14.
 */
public class ToggleItemBuilder extends BaseItemBuilder{

    private ToggleItem item;
    private boolean state;

    @Override
    protected Item createInstance() {
        this.item = new ToggleItem();
        return item;
    }


    public ToggleItemBuilder setState(boolean initialState) {
        this.state = initialState;
        return this;
    }

    @Override
    public ToggleItemBuilder build() {
        super.build();
        item.setState(state);
        return this;
    }

    @Override
    public ToggleItem get() {
        return item;
    }
}
