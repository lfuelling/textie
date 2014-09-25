package de.micromata.azubi;

import java.util.ArrayList;
import java.util.List;

public abstract class Raum {

    // public static final int MAX_SLOTS_ITEMS = 4;
    protected List<Item> items = new ArrayList<Item>();
    protected Inventory inventory;
    protected boolean fackelUsed = false;
    protected boolean falltuerUsed = false;
    protected int roomNumber;

    public Raum(Inventory inventory, int number, Human human, Item... items1) {
        this.inventory = inventory;
        roomNumber = number;

        for (Item item : items1) {
            this.items.add(item);
        }
    }

    public void listItems() {
        // Textie.printText("1. Item in List: " +
        // this.items.get(1).getName());
        Textie.printText("Im Raum befindet sich:");
        for (Item item : items) {
            if(item == null) {
                Textie.printText("\tKein Objekt");
            } else {
                // String objectName = items.get(i).getName();
                Textie.printText("\t" + item.getName());
                /*
				 * TODO Kontext zum Raum herstellen... for (Item value :
				 * Textie.itemMap.values()) {
				 * Textie.printText(value.getName()); }
				 */
            }
        }

    }

    /**
     * @param item
     * @return Returns -128 when the item is not in the room
     */
    public int find(Item item) {
        int i = -128;
        i = items.indexOf(item);
        return i;
    }

    public boolean hasItem(Item item) {
        if(items.contains(item)) {
            return true;
        }
        return false;
    }

    void doGehen(String[] parsed_command, int count) {
        if(count == 2) {
            switch (parsed_command[1]) {
                case "nord":
                    goNorth();
                    break;
                case "west":
                    goWest();
                    break;
                case "ost":
                    goEast();
                    break;
                case "süd":
                    goSouth();
                    break;
            }
        } else {
            Textie.printText("Wohin gehen?");
        }
    }

    void doVernichte(Item item, int count) {
        if(count == 2) {
            if(inventory.removeItem(item)) {
                Textie.printText(item.getName() + " vernichtet.");
                return;
            } else {
                Textie.printText("Entweder das Objekt gibt es nicht, oder es ist nicht im Inventar.");
                return;
            }
        } else {
            Textie.printText("Was soll vernichtet werden?");
        }
    }

    public int getNumber() {
        return roomNumber;
    }

    public String getNumberAsString() {
        String raumNummerString = String.valueOf(roomNumber);
        return raumNummerString;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    void doUntersuche(String[] parsed_command, int count) {
        if(count == 2) {
            switch (parsed_command[1].toLowerCase()) {
                case "raum":
                    if(Textie.currentRaum.roomNumber == 3) {
                        Item item = Textie.itemMap.get(Consts.FACKEL);
                        if(item instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item;
                            if(fackel.getState() == true) {
                                this.listItems();
                            } else {
                                Textie.printText("Du kannst nichts sehen!");
                            }
                        }
                    } else {
                        this.listItems();
                    }
                    break;
                case "inventar":
                    if(Textie.currentRaum.roomNumber == 3) {
                        Item item = Textie.itemMap.get(Consts.FACKEL);
                        if(item instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item;
                            if(fackel.getState() == true) {
                                inventory.listItems();
                            } else {
                                Textie.printText("Du kannst nichts sehen!");
                            }
                        }
                    } else {
                        inventory.listItems();
                    }
                    break;
                case "truhe":
                    if(find(Textie.itemMap.get(Consts.TRUHE)) != -128){
                        StorageItem truhe = (StorageItem) Textie.itemMap.get(Consts.TRUHE);
                        truhe.listItems();
                    } else {
                        Textie.printText("Hier ist keine Truhe");
                    }
                    break;
                default:
                    if(Textie.currentRaum.roomNumber == 3) {
                        Item item = Textie.itemMap.get(Consts.FACKEL);
                        if(item instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item;
                            if(fackel.getState() == true) {
                                Item itemUSU = Textie.itemMap.get(parsed_command[1].toUpperCase());
                                if(itemUSU == null) {
                                    Textie.printText("Das Objekt gibt es nicht.");
                                } else {
                                    itemUSU.untersuchen();
                                }
                            } else {
                                Textie.printText("Du kannst nichts sehen!");
                            }
                        }
                    } else {
                        Item itemUSU = Textie.itemMap.get(parsed_command[1].toUpperCase());
                        if(itemUSU == null) {
                            Textie.printText("Das Objekt gibt es nicht.");
                        } else {
                            itemUSU.untersuchen();
                        }
                    }
            }
        } else {
            Textie.printText("Was soll untersucht werden?");
        }
    }

    void doBenutze(Item item) {
        if(item == null) {

            Textie.printText("Das Item gibt es nicht.");

        } else {
            String itemName = item.getName();
            Textie.printText("Du willst '" + itemName + "' benutzen.");
            switch (itemName) {
                // Fackel und Feuerzeug sind besonders, da sie auch funktionen
                // aufrufen
                // und nicht nur einen Text ausgeben. Außerdem sollen diese Items
                // benutzbar sein, selbst wenn der Raum dunkel ist.
                case "Fackel":// Textie.itemMap.get("FACKEL").getName():
                case "Feuerzeug": // Textie.itemMap.get("FEUERZEUG").getName():
                    int fackelSlot = inventory.findItem(Textie.itemMap.get(Consts.FACKEL));
                    int feuerZeugSlot = inventory.findItem(Textie.itemMap.get(Consts.FEUERZEUG));
                    if(feuerZeugSlot < 0) {
                        Textie.printText("Du hast kein Feuerzeug.");
                        break;
                    } else if(fackelSlot < 0) {
                        Textie.printText("Du hast keine Fackel.");
                        break;
                    } else {
                        Textie.printText("Du zündest deine Fackel mit dem Feuerzeug an.");
                        Item item2 = Textie.itemMap.get("FACKEL");
                        if(item2 instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item2;
                            fackel.setState(true);
                        }
                        break;
                    }
                case "Falltür":
                    if(Textie.currentRaum.getNumber() == 3) {
                        Item item5 = Textie.itemMap.get(Consts.FACKEL);
                        if(item5 instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item5;
                            if(fackel.getState() == true) {
                                Item itemToUse = Textie.itemMap.get(itemName.toUpperCase());
                                if(itemToUse == null) {
                                    Textie.printText("Das Objekt gibt es nicht.");
                                    break;
                                } else {
                                    if(find(Textie.itemMap.get(Consts.FALLTÜR)) != -128) {
                                        Textie.printText("Du schlüpfst durch die Falltür in den darunterliegenden Raum.");
                                        falltuerUsed = true;
                                        break;
                                    }
                                    // else if
                                    // (find(Textie.itemMap.get(Consts.FALLTÜR)) !=
                                    // -128) {
                                    // Textie.printText("Da ist eine Falltür. Du hast das Gefühl, nicht alles erledigt zu haben.");
                                    // break;
                                    // }
                                }
                            } else {
                                Textie.printText("Du kannst nichts sehen!");
                                break;
                            }
                        }
                    } else {
                        Item itemToUse = Textie.itemMap.get(itemName.toUpperCase());
                        if(itemToUse == null) {
                            Textie.printText("Das Objekt gibt es nicht.");
                        } else {
                            if(find(Textie.itemMap.get(Consts.FALLTÜR)) != -128 && hasEverything()) {
                                Textie.printText("Du schlüpfst durch die Falltür in den darunterliegenden Raum.");
                                falltuerUsed = true;
                                break;
                            } else if(find(Textie.itemMap.get(Consts.FALLTÜR)) != -128) {
                                Textie.printText("Da ist eine Falltür. Du hast das Gefühl, nicht alles erledigt zu haben.");
                                break;
                            }
                        }
                    }
                case "Sack":
                    Item sack = Textie.itemMap.get(itemName.toUpperCase());
                    sack.benutzen();
                    inventory.removeItem(Textie.itemMap.get(Consts.SACK));
                    inventory.setInventorySize(2);
                    break;
                case "Schalter":
                    ToggleItem schalter = (ToggleItem) Textie.itemMap.get(itemName.toUpperCase());
                    schalter.benutzen();
                    schalter.setState(true);
                    break;
                case "Schwert":
                    Textie.itemMap.get(Consts.SCHWERT).benutzen();
                    Textie.ende();
                    break;
                case "Schlüssel":
                    StorageItem truhe = (StorageItem) Textie.itemMap.get(Consts.TRUHE);
                    if(Textie.currentRaum.hasItem(Textie.itemMap.get(Consts.TRUHE))) {
                        if(truhe.lockState==true) {
                            truhe.lockState = false;
                            Textie.printText("Du öffnest die Truhe mit dem Schlüssel.");
                            break;
                        } else {
                            Textie.printText("Die Truhe ist bereits aufgeschlossen.");
                            break;
                        }
                    } else {
                        Textie.printText("Hier gibt es nichts, was man aufschließen könnte.");
                        break;
                    }
                default:
                    if(Textie.currentRaum.roomNumber == 3) {
                        Item item5 = Textie.itemMap.get(Consts.FACKEL);
                        if(item5 instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item5;
                            if(fackel.getState() == true) {
                                Item itemToUse = Textie.itemMap.get(itemName.toUpperCase());
                                if(itemToUse == null) {
                                    Textie.printText("Das Objekt gibt es nicht.");
                                } else {
                                    itemToUse.benutzen();
                                }
                            } else {
                                Textie.printText("Du kannst nichts sehen!");
                            }
                        }
                    } else {
                        Item itemToUse = Textie.itemMap.get(itemName.toUpperCase());
                        if(itemToUse == null) {
                            Textie.printText("Das Objekt gibt es nicht.");
                        } else {
                            itemToUse.benutzen();
                        }
                    }
            }
        }
    }

    void doTakeFromChest (Item item){
        if(item.isPickable()) {
            if(inventory.addItemFromChest(item)) {

                Textie.printText(item.getName() + " zum Inventar hinzugefügt.");
            } else {
                Textie.printText("Entweder das Objekt gibt es nicht, oder dein Inventar ist voll.");
            }
        } else {
            Textie.printText("Du kannst dieses Item nicht aufheben.");
        }
    }

    void doNimm(Item item) {
        if(item == null) {
            Textie.printText("Unbekanntes Item.");
        } else {
            if(item.isPickable()) {
                if(inventory.addItem(item)) {

                    Textie.printText(item.getName() + " zum Inventar hinzugefügt.");
                } else {
                    Textie.printText("Entweder das Objekt gibt es nicht, oder dein Inventar ist voll.");
                }
            } else {
                Textie.printText("Du kannst dieses Item nicht aufheben.");
            }
        }
    }

    void printHelp() {
        Textie.printText("Mögliche Befehle:");
        Textie.printText("\thilfe -> Zeigt diese Hilfe");
        Textie.printText("\tnimm [gegenstand] -> Gegenstand zum Inventar hinzufügen");
        Textie.printText("\tbenutze [gegenstand] -> Gegenstand benutzen");
        Textie.printText("\tuntersuche [gegenstand/raum/inventar] -> Gegenstand, Raum oder Inventar untersuchen");
        Textie.printText("\tvernichte [gegenstand] -> Gegenstand aus dem Inventar löschen");
        Textie.printText("\tgehe [nord/süd/ost/west] -> In eine Richtung gehen");
    }

    private void goWall() {
        Textie.printText("Du bist gegen die Wand gelaufen.");
    }

    public abstract void start(boolean withPrompt);

    public abstract boolean isFinished();

    public boolean hasEverything() {
        if(inventory.isInInventory(Textie.itemMap.get(Consts.BRECHEISEN)) && inventory.isInInventory(Textie.itemMap.get(Consts.SCHLÜSSEL))) {
            return true;
        } else {
            return false;
        }
    }

    public void goWest() {
        goWall();
    }

    public void goEast() {
        goWall();
    }

    public void goNorth() {
        goWall();
    }

    public void goSouth() {
        goWall();

    }

    public boolean addItem(Item item) {
        if(items.add(item)) {
            return true;
        }

        return false;
    }

    public boolean removeItem(Item item) {
        if(items.remove(item))
            return true;
        return false;
    }
}
