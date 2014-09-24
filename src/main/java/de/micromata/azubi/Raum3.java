
package de.micromata.azubi;

public class Raum3 extends Raum {

    public Raum3(Inventory inventory, int number, Item... items) {
        // null, denn es gibt in den ersten drei Räumen nix lebendiges (oder doch?!... *grusel*)
        super(inventory, number, null, items);
    }

    public void start() {
        ToggleItem fackel = (ToggleItem) Textie.itemMap.get(Consts.FACKEL);
        if(fackel.getState() == true) {
            System.out.println("Ein Windstoß sorgt dafür, dass die Fackel ausgeht.");
            fackel.setState(false);
        }
        System.out.println("Es ist zu dunkel, um etwas zu sehen. Ein seltsamer Geruch liegt in der Luft.");
        Textie.prompt();
    }

    @Override
    public boolean isFinished() {
        // Raum3 durch benutzen der Falltür verlassen
        if(falltuerUsed == true) {
            return true;
        } else {
            return false;
        }
    }
}
