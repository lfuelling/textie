
package de.micromata.azubi;

public class Raum4 extends Raum {

  boolean east = false;

  public Raum4(Inventory inventory, int number, Item... items) {
    super(inventory, number, items);
  }

  public void start(boolean withPrompt) {
    east = false;
    Dungeon.getDungeon().printText("Du kommst in einen hell erleuchteten Raum. Ein alter Mann lehnt an der Wand.");
    warten(withPrompt);
  }

  @Override
  public int isFinished() {
    // east
    if (east) {
      return 1;
    }
    else {
        return 0;
    }
  }

  @Override
  public void goEast() {
    ToggleItem schalter;
    if (Dungeon.getDungeon().itemMap.get(Consts.SCHALTER).isToggle() == true) {
      schalter = (ToggleItem) Dungeon.getDungeon().itemMap.get(Consts.SCHALTER);

      if (schalter.getState() == true) {
        east = true;
        Dungeon.getDungeon().printText("Da ist eine Tür. Du öffnest sie und gehst die Steintreppe dahinter hoch.");
      } else {
        Dungeon.getDungeon().printText("Da ist eine Tür. Du versuchst sie zu öffnen, doch es geht nicht.");
      }
    } else {
      Dungeon.getDungeon().printText("Da ist eine Tür. Du versuchst sie zu öffnen, doch es geht nicht.");
    }
  }
}
