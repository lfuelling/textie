
package de.micromata.azubi;

public class Raum4 extends Raum {

    boolean nord = false;

    public Raum4(Inventory inventory, int number, Human human, Item... items) {
        super(inventory, number, human, items);
        Textie.setCurrentHuman(human);
    }

    public void start(boolean withPrompt) {

        nord = false;

        System.out.println("Du kommst in einen hell erleuchteten Raum. Ein alter Mann lehnt an der Wand.");
        Textie.prompt();
    }

    @Override
    public boolean isFinished() {
        // nord
        if(nord) {
            System.out.println("Da ist eine Tür. Du öffnest sie und gehst die Steintreppe dahinter hoch.");
            nord=false;
            return true;
        }
        return false;
    }

    @Override
    public void goNorth() {
        ToggleItem schalter;
        if(Textie.itemMap.get(Consts.SCHALTER).isToggle() == true) {
            schalter = (ToggleItem) Textie.itemMap.get(Consts.SCHALTER);

            if(schalter.getState() == true) {
                nord = true;
            } else {
                System.out.println("Da ist eine Tür. Du versuchst sie zu öffnen, doch es geht nicht.");
            }
        } else {
            System.out.println("Da ist eine Tür. Du versuchst sie zu öffnen, doch es geht nicht.");
        }
    }
}
