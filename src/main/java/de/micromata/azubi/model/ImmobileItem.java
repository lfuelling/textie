package de.micromata.azubi.model;

import de.micromata.azubi.model.Item;

/**
 * ImmobileItem -> Non-Pickable
 * @author Lukas F&uuml;lling (l.fuelling@micromata.de)
 * @version 1.0
 * @see de.micromata.azubi.model.Item
 */
public class ImmobileItem extends Item {






  /**
   *
   * @return false
   */
  @Override
  public boolean isPickable() {
    return false;
  }

}
