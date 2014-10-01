package de.micromata.azubi;

public class Raum1 extends Raum {
    boolean south = false;


    public Raum1() {
      super();
    }

    public Raum1(Inventory inventory, int number, Item... items) {
        super(inventory, number, items);
    }

    public void start(boolean withPrompt) {
        south = false;
        Dungeon.getDungeon().printText("Du befindest dich in einem dunklen Raum. Nach einiger Zeit gewöhnen sich deine Augen an die Dunkelheit.");
        warten(withPrompt);
    }


  @Override
    public boolean isFinished() {
        if(south) {
           south = false;
           return true;
        }

        return false;
    }

    @Override
    public void goSouth() {
        // Fackel muss im inventar sein, bevor south = true gesetzt wird
        if(inventory.isInInventory(Dungeon.getDungeon().itemMap.get(Consts.FACKEL))) {
            south = true;
            Dungeon.getDungeon().printText("Da ist eine Tür. Du öffnest sie und gehst die Steintreppe dahinter hoch.");
        } else {
            Dungeon.getDungeon().printText("Da ist eine Tür. Du gehst nicht hinaus, da du das Gefühl hast, noch nicht alles erledigt zu haben.");
        }

    }
}
