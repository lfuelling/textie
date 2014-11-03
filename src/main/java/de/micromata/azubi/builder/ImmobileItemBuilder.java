package de.micromata.azubi.builder;

import de.micromata.azubi.model.Item;
import de.micromata.azubi.model.ImmobileItem;

/**
 * Immobile Item Builder
 * @author Lukas F&uuml;lling (l.fuelling@micromata.de)
 * @version 1.0
 * @see de.micromata.azubi.builder.BaseItemBuilder
 * @see de.micromata.azubi.builder.Builder 
 */
public class ImmobileItemBuilder extends BaseItemBuilder {
  private ImmobileItem immobileItem;
  private InventarBuilder ib;
  private boolean lockState;

  @Override
  protected Item createInstance() {
    immobileItem = new ImmobileItem();
    System.out.println("Das Item ist ein StorageItem");
    return immobileItem;
  }


  public ImmobileItemBuilder setInventarBuilder(InventarBuilder ib) {
    this.ib = ib;
    return this;
  }

  @Override
  public ImmobileItemBuilder build() {
    super.build();
    return this;
  }

  @Override
  public ImmobileItem get() {
    return immobileItem;
  }

}
