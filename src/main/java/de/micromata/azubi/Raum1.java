package de.micromata.azubi;

public class Raum1 extends Raum {
    boolean south = false;

    public Raum1(Inventory inventory, int number, Item... items) {
        super(inventory, number, null, items);
    }

    public void start() {
        south = false;
        System.out.println("Du befindest dich in einem dunklen Raum. Nach einiger Zeit gewöhnen sich deine Augen an die Dunkelheit.");
        Textie.prompt();
    }

    @Override
    public boolean isFinished() {
        if(south) {
            System.out.println("Da ist eine Tür. Du öffnest sie und gehst die Steintreppe dahinter hoch.");
            south = false;
            return true;
        }

        return false;
    }

    @Override
    public void goSouth() {
        // Fackel muss im inventar sein, bevor south = true gesetzt wird
        if(inventory.isInInventory(Textie.itemMap.get(Consts.FACKEL))) {
            south = true;
        } else {
            System.out.println("Da ist eine Tür. Du gehst nicht hinaus, da du das Gefühl hast, noch nicht alles erledigt zu haben.");
        }

    }
}
