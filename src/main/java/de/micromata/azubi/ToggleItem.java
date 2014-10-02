package de.micromata.azubi;

public class ToggleItem extends Item {

  private boolean state;

  public ToggleItem() {
  }

  public ToggleItem(String name, String untersucheText, String benutzeText, boolean pickable, boolean state) {
    super(name, untersucheText, benutzeText, pickable);
    state = this.state;
  }

  public boolean getState() {
    return state;
  }

  public void setState(boolean stateToSet) {
    state = stateToSet;
  }
}
