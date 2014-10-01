
package de.micromata.azubi;

public class Raum4 extends Raum {

  boolean nord = false;

  public Raum4() {
    super();
  }

  public Raum4(Inventory inventory, int number, Item... items) {
    super(inventory, number, items);
  }

  public void start(boolean withPrompt) {
    nord = false;
    Dungeon.getDungeon().printText("Du kommst in einen hell erleuchteten Raum. Ein alter Mann lehnt an der Wand.");
    warten(withPrompt);
  }

  @Override
  public boolean isFinished() {
    // nord
    if (nord) {
      Dungeon.getDungeon().printText("Da ist eine Tür. Du öffnest sie und gehst die Steintreppe dahinter hoch.");
      nord = false;
      return true;
    }
    return false;
  }

  @Override
  public void goNorth() {
    ToggleItem schalter;
    if (Dungeon.getDungeon().itemMap.get(Consts.SCHALTER).isToggle() == true) {
      schalter = (ToggleItem) Dungeon.getDungeon().itemMap.get(Consts.SCHALTER);

      if (schalter.getState() == true) {
        nord = true;
      } else {
        Dungeon.getDungeon().printText("Da ist eine Tür. Du versuchst sie zu öffnen, doch es geht nicht.");
      }
    } else {
      Dungeon.getDungeon().printText("Da ist eine Tür. Du versuchst sie zu öffnen, doch es geht nicht.");
    }
  }
}
