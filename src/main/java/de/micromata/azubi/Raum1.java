package de.micromata.azubi;

public class Raum1 extends Raum {
    boolean south = false;
    boolean west = false;

    public Raum1(Inventory inventory, int number, Item... items) {
        super(inventory, number, items);
    }

    /**
     * Startet das Level.
     * @param withPrompt
     */
    public void start(boolean withPrompt) {
        Dungeon.getDungeon().printText("Du befindest dich in einem dunklen Raum. Nach einiger Zeit gewöhnen sich deine Augen an die Dunkelheit.");
        south = false;
        west = false;
        warten(withPrompt);
    }


  @Override
    public int isFinished() {
        if(south) {
           return 1;
        }
      else if(west)
        {
            return -1;
        }
      else {
            return 0;
        }
    }

    @Override
    public void goSouth() {
            south = true;
            Dungeon.getDungeon().printText("Da ist eine Tür. Du öffnest sie und gehst die Steintreppe dahinter hoch.");
    }

    @Override
    public void goWest(){
    ToggleItem schalter;
    if (Dungeon.getDungeon().itemMap.get(Consts.SCHALTER).isToggle() == true) {
        schalter = (ToggleItem) Dungeon.getDungeon().itemMap.get(Consts.SCHALTER);

        if (schalter.getState() == true) {
            Dungeon.getDungeon().printText("Da ist eine Tür. Sie steht offen und du folgst der Steintreppe herunter.");
            west = true;
        } else {
            Dungeon.getDungeon().printText("Da ist eine Tür. Du versuchst sie zu öffnen, doch es geht nicht.");
        }
    } else {
        Dungeon.getDungeon().printText("Da ist eine Tür. Du versuchst sie zu öffnen, doch es geht nicht.");
    }
}


}
