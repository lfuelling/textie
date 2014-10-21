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
    protected Map<Richtung, Integer> verbindungen = new HashMap<Richtung, Integer>();
    protected boolean leaveRoom = false;

    public Raum() {
    }

    public Raum(int number, String willkommensNachricht, Map<Richtung, Integer> verbindungen, Item... items1) {
        this.roomNumber = number;
        this.verbindungen = verbindungen;
        this.willkommensNachricht = willkommensNachricht;

        for (Item item : items1) {
            this.items.add(item);
        }
    }

    public void listItems() {
        // printText("1. Item in List: " +
        // this.items.get(1).getName());
        Textie.printText("Im Raum befindet sich:");
        for (Item item : items) {
            if (item == null) {
                Textie.printText("\tKein Objekt");
            } else {
                // String objectName = items.get(i).getName();
                Textie.printText("\t" + item.getName());
            }
        }

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
            ToggleItem fackel = (ToggleItem) Dungeon.getDungeon().itemMap.get(Consts.FACKEL);
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


    public Integer getRaumNr(Richtung richtung) {
        return verbindungen.get(richtung);
    }


    public boolean isLeaveRoom() {
        return leaveRoom;
    }

    public void setLeaveRoom(boolean leaveRoom) {
        this.leaveRoom = leaveRoom;
    }

    public Map<Richtung, Integer> getVerbindungen() {
        return verbindungen;
    }

    public void setVerbindungen(Map<Richtung, Integer> verbindungen) {
        this.verbindungen = verbindungen;
    }
}
