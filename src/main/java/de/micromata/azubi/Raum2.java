package de.micromata.azubi;

public class Raum2 extends Raum {
    boolean west = false;
    boolean north = true;

    public Raum2(Inventory inventory, int number, Item... items) {
        super(inventory, number, items);
    }

    public void start(boolean withPrompt) {
        west = false;
        System.out.println("Du kommst in einen dunklen Raum.");
        warten(withPrompt);
    }

    @Override
    public int isFinished() {
        // West wird in goWest gesetzt und ist wahr, wenn Feuerzeug und
        // Schlüssel im Inventar sind
        if(west) {
            return 1;
        }
        else if(north) {
            return -1;
        }
        else{
            return 0;
        }
    }

    @Override
    public void goWest() {
            west = true;
            System.out.println("Da ist eine Tür. Du öffnest sie und gehst die Steintreppe dahinter hoch.");
    }
    @Override
    public void goNorth(){
        System.out.println("Du siehst eine Tür und gehst die Steintreppe dahinter hinab.");
        north = true;
    }
}
