package de.micromata.azubi.model;

import de.micromata.azubi.Textie;

import java.io.Serializable;

/**
 * Created by jsiebert on 29.09.14.
 */
public class Player implements Serializable {

    private static final long serialVersionUID = -2306082155444323753L;
    private String name;
    private Inventory inventory;
    private boolean alive;
    private Room position;



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

    public Room getPosition() {
        return position;
    }

    public void setPosition(Room position) {
        this.position = position;
    }

    /**
     * Lets you walk.
     *
     * @param direction the direction you want to go.
     */
    public void doWalk(Direction direction, Dungeon dungeon) {
        if (position.getRoomNumber() == 6 && position.getNextRoom(position.findDoorByDirection(direction)) == null) {
            Textie.printText("Der Weg wird durch eine Holzbarrikade versperrt.");
        } else {
            int roomNumber = position.getRoomNumber();
            Room nextRoom = dungeon.getRoom(direction);
            if (nextRoom != null && dungeon.findRoomByNumber(roomNumber).isLeaveRoom() == true) {
                dungeon.setRoomNumber(nextRoom);
                position.setLeaveRoom(false);
                position = nextRoom;
                Textie.printText(dungeon.getCurrentRoom().getWelcomeText());
            }
        }


    }
}
