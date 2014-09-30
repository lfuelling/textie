
package de.micromata.azubi;

public class Raum3 extends Raum {
    boolean east = false;

    public Raum3(Inventory inventory, int number, Item... items) {
        // null, denn es gibt in den ersten drei Räumen nix lebendiges (oder doch?!... *grusel*)
        super(inventory, number, items);


    }

    public void start(boolean withPrompt) {
        boolean east = false;
        ToggleItem fackel = (ToggleItem) Dungeon.getDungeon().itemMap.get(Consts.FACKEL);
        if(fackel.getState() == true) {
            printText("Ein Windstoß sorgt dafür, dass die Fackel ausgeht.");
            fackel.setState(false);
        }
        printText("Es ist zu dunkel, um etwas zu sehen. Ein seltsamer Geruch liegt in der Luft.");

        warten(withPrompt);
    }

    @Override
    public int isFinished() {
        // Raum3 durch benutzen der Falltür verlassen
        if(falltuerUsed == true) {
            return 1;
        } else {
            return 0;
        }
    }
    @Override
    public void goEast(){
        System.out.println("Du siehst eine Tür und gehst die Steintreppe dahinter hinab.");
        east = true;
    }
}
