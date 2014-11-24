package de.micromata.azubi.model;

import de.micromata.azubi.Textie;

/**
 * Created by jsiebert on 20.11.14.
 */
public class DarkRoom extends Room {

    public DarkRoom(Dungeon dungeon) {
        super(dungeon);
    }

    public DarkRoom(int number, String willkommensNachricht, Dungeon dung) {
        super(number, willkommensNachricht, dung);
    }

    @Override
    public void examine(Dungeon dungeon) {
        Item item = dungeon.getPlayer().getInventory().findItemByName("Fackel");
        if(item == null){
            Textie.printText("Du kannst nichts sehen!");
        }
        else {
            if (item instanceof ToggleItem) {
                ToggleItem fackel = (ToggleItem) item;
                if (fackel.getState() == true) {
                    Textie.printText("Im Raum befindet sich:", dungeon);
                    inventory.listItems(dungeon);
                } else {
                    Textie.printText("Du kannst nichts sehen!", dungeon);
                }
            }
        }
    }
}
