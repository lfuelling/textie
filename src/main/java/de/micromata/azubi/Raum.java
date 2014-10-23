package de.micromata.azubi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Raum implements Serializable{
    private static final long serialVersionUID = -2269575363024102428L;
    protected List<Item> items = new ArrayList<Item>();
    protected boolean falltuerUsed = false;
    protected int roomNumber;
    protected String willkommensNachricht;
    protected Map<Richtung, Raum> verbindungen = new HashMap<>();
    protected boolean leaveRoom = false;
    protected Inventory inventory;

    public Raum() {
    }

    public Raum(int number, String willkommensNachricht) {
        this.roomNumber = number;
        this.willkommensNachricht = willkommensNachricht;
    }

    public boolean hasItem(Item item) {
        if (items.contains(item)) {
            return true;
        }
        return false;
    }

    public int getNumber() {
        return roomNumber;
    }

    public String getNumberAsString() {
        String raumNummerString = String.valueOf(this.roomNumber);
        return raumNummerString;
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void start(boolean withPrompt) {

        if (roomNumber == 3) {
            ToggleItem fackel = (ToggleItem) inventory.findItemByName("Fackel");
            if (fackel.getState() == true) {
                Textie.printText("Ein Windstoß sorgt dafür, dass die Fackel ausgeht.");
                fackel.setState(false);
            }
        }
        Textie.printText(willkommensNachricht);
        Textie.warten(withPrompt);
    }

    public boolean isFalltuerUsed() {
        return falltuerUsed;
    }

    public void setFalltuerUsed(boolean falltuerUsed) {
        this.falltuerUsed = falltuerUsed;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getWillkommensNachricht() {
        return willkommensNachricht;
    }

    public void setWillkommensNachricht(String willkommensNachricht) {
        this.willkommensNachricht = willkommensNachricht;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


    public Raum getNextRoom(Richtung richtung) {
        return verbindungen.get(richtung);
    }


    public boolean isLeaveRoom() {
        return leaveRoom;
    }

    public void setLeaveRoom(boolean leaveRoom) {
        this.leaveRoom = leaveRoom;
    }

    public Map<Richtung, Raum> getVerbindungen() {
        return verbindungen;
    }

    public void setVerbindungen(Map<Richtung, Raum> verbindungen) {
        this.verbindungen = verbindungen;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
