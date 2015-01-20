package de.micromata.azubi.model;

import java.io.Serializable;

/**
 * Created by Daniel on 26.11.2014.
 */
public class MobileHuman implements Serializable {
    private String name;
    private String dialog;
    private int nextRoomNumber;
    private Room position;
    private Dungeon dungeon;

    // Soll ähnlich wie der Geist auf Bewegen des Spielers sich mit bewegen.
    public void move() {
        nextRoomNumber = (int) (Math.random() *3+1 ); // nur Raum 1 - 3 , wegen dem Rede Befehl.
        setPosition(dungeon.findRoomByNumber(nextRoomNumber));
        if (position == null || dungeon.findRoomByNumber(nextRoomNumber) == this.getPosition()  ) {
            return;
        }
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDialog(String dialog) {
        this.dialog = dialog;
    }
    public void setPosition(Room position) {
        this.position = position;
    }
    public Room getPosition() {
    return this.position;  }
}
