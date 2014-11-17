package de.micromata.azubi.model;

import de.micromata.azubi.IOUtils;
import de.micromata.azubi.Textie;
import de.micromata.azubi.model.Dungeon;
import de.micromata.azubi.model.Inventory;

import java.io.Serializable;

/**
 * Created by jsiebert on 29.09.14.
 */
public class Player implements Serializable {

    private static final long serialVersionUID = -2306082155444323753L;
    private String name;
    private Inventory inventory;
    private boolean alive;
    private Raum position;



    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    
		// this.inventory = inventory;
    public Inventory getInventory() {
        return inventory;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Raum getPosition() {
        return position;
    }

    public void setPosition(Raum position) {
        this.position = position;
    }

    /**
     * Lets you walk.
     *
     * @param richtung the direction you want to go.
     */
    public void doGehen(Richtung richtung, Dungeon dungeon) {
        if (position.getRoomNumber() == 6 && position.getNextRoom(position.findDoorByDirection(richtung)) == null) {
            Textie.printText("Der Weg wird durch eine Holzbarrikade versperrt.");
        } else {
            int roomNumber = position.getRoomNumber();
            Raum nextRoom = dungeon.getRaum(richtung);
            if (nextRoom != null && dungeon.findRaumByNummer(roomNumber).isLeaveRoom() == true) {
                dungeon.setRoomNumber(nextRoom);
                position.setLeaveRoom(false);
                position = nextRoom;
                Textie.printText(dungeon.getCurrentRaum().getWillkommensNachricht());
            }
        }


    }
}
