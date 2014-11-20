package de.micromata.azubi.model;

import de.micromata.azubi.Textie;
import de.micromata.azubi.builder.DoorBuilder;

import java.io.Serializable;

public class Item implements Serializable{
    public static final String SCHALTER = "Schalter";
    public static final String TRUHE = "Truhe";
    public static final String SCHLÜSSEL = "Schlüssel";
    public static final String FEUERZEUG = "Feuerzeug";
    public static final String SCHWERT = "Schwert";
    public static final String BRECHEISEN = "Brecheisen";
    public static final String QUIETSCHEENTE = "Quietscheente";
    public static final String FACKEL = "Fackel";
    public static final String SACK = "Sack";
    public static final String FALLTÜR = "Falltür";
    public static final String WHITEBOARD = "Whiteboard";
    public static final String STEIN = "Stein";
    public static final String HANDTUCH = "Handtuch";
    private static final long serialVersionUID = -2308071724210324323L;
    private String useText;
    private String name;
    private String examineText;
    private int itemID;
    private long uid;
    private boolean pickable; // FIXME muss eigentlich wieder raus

    public int getItemID() {
        return itemID;
    }



    public Item(){

    }


  /**
   * @param itemID The item ID
   * @param name Item name
   * @param examineText Text which is printed when you inspect the item.
   * @param useText Text which is printed when you use the item.
   * @deprecated Use the builder instead
   */
    public Item(int itemID, String name, String examineText, String useText) {
        this.itemID = itemID;
        this.name = name;
        this.examineText = examineText;
        this.useText = useText;
    }

  /**
   *
   * @return Returns true if the Item is pickable
   */
    public boolean isPickable() { //FIXME !!
        if(pickable == true){
            return true;
        }else{
            return false;
        }
    }

  /**
   *
   * @return Returns the Item name.
   */
    public String getName() {

        return this.name;
        // return getObjectName(objectID);
    }

  /**
   * prints the examineText
   */
    public void discover() {
        if(examineText.equals("") == true) {
            // TODO itemgeschlechter hinzufügen und Text anpassen
            System.out.println("Da ist ein/e " + this.getName() + ".");
        }
        Textie.printText(examineText);
    }

  /**
   *
   * @return Returns true if the Item is a switch.
   */
    public boolean isToggle() {
        if(this instanceof ToggleItem) {
            return true;
        }
        return false;
    }

  /**
   *
   * @return Returns true if the Item is a map.
   */
    public boolean isMap(){
        if(this instanceof Map){
            return  true;
        }
        return false;
    }

    public String getExamineText() {
        return examineText;
    }

    public void setExamineText(String examineText) {
        this.examineText = examineText;
    }

    public String getUseText() {
        return useText;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }


    public void setUseText(String useText) {
        this.useText = useText;
    }

    public void setPickable(boolean pickable) {
        this.pickable = pickable;
    }

    /**
     * Lets you use an item.
     *
     */
    public void use(Dungeon dungeon) {
        if (this == null) {
            Textie.printText("Das Item gibt es nicht.");
        } else {
            Inventory roomInventory = dungeon.getCurrentRoom().getInventory();
            Inventory playerInventory = dungeon.getPlayer().getInventory();
            if (this.isPickable() == false || playerInventory.hasItem(this.getName())) {
                String itemName = this.getName();
                if (Textie.diag == true) {
                    Textie.printText("Du willst " + itemName + " use");
                }
                switch (itemName) {
                    // Fackel und Feuerzeug sind besonders, da sie auch funktionen
                    // aufrufen
                    // und nicht nur einen Text ausgeben. Außerdem sollen diese Items
                    // benutzbar sein, selbst wenn der Raum dunkel ist.
                    case "Fackel":// this.itemMap.get("FACKEL").getName():
                    case "Feuerzeug": // this.itemMap.get("FEUERZEUG").getName():
                        int fackelSlot = playerInventory.findItem(playerInventory.findItemByName("Fackel"));
                        int feuerZeugSlot = playerInventory.findItem(playerInventory.findItemByName("Feuerzeug"));
                        if (feuerZeugSlot < 0) {
                            Textie.printText("Du hast kein Feuerzeug.");
                            break;
                        } else if (fackelSlot < 0) {
                            Textie.printText("Du hast keine Fackel.");
                            break;
                        } else {
                            Textie.printText("Du zündest deine Fackel mit dem Feuerzeug an.");
                            Item item2 = playerInventory.findItemByName("Fackel");
                            if (item2 instanceof ToggleItem) {
                                ToggleItem fackel = (ToggleItem) item2;
                                fackel.setState(true);
                            }
                            break;
                        }
                    case "Falltür":
                        ToggleItem fackel = null;
                        Item item5 = Textie.chooseInventory("Fackel", dungeon);
                        if (item5 instanceof ToggleItem) {
                            fackel = (ToggleItem) item5;
                        }
                        if (fackel != null && fackel.getState() == false && dungeon.getCurrentRoom().getRoomNumber() == 3) {
                            Textie.printText("Du kannst nichts sehen!");
                        } else {
                            if (this == null) {
                                Textie.printText("Das Objekt gibt es nicht.");
                                break;
                            } else {
                                if (roomInventory.hasItem("Falltür")) {
                                    Textie.printText("Du schlüpfst durch die Falltür in den darunterliegenden Raum.");
                                    dungeon.getPlayer().doWalk(Direction.FALLTUER, dungeon);
                                    break;
                                }
                            }
                            break;
                        }
                        break;
                    case "Axt":
                        if (dungeon.getCurrentRoom().getRoomNumber() != 6) {
                            Textie.printText("Du fuchtelst mit der Axt wild in der Gegend herum");
                        } else {
                            dungeon.getCurrentRoom().getDoors().add(new DoorBuilder().setDirection(Direction.OST).setLock(false).setNextRoom(7).build().get());
                            Textie.printText(useText);
                        }
                        break;
                    case "Sack":
                        Textie.printText(useText);
                        playerInventory.removeItem(playerInventory.findItemByName("Sack"));
                        playerInventory.increaseMaxSlots(2);
                        break;
                    case "Schalter":

                        ToggleItem schalter = (ToggleItem) this;
                        Textie.printText(useText);
                        schalter.toggleState();
                        dungeon.getDoorSchalter().get(schalter).toogleLock();

                        break;
                    case "Schwert":
                        Textie.printText(useText);
                        dungeon.getPlayer().setAlive(false);
                        Textie.end(dungeon);
                        break;
                    case "Schlüssel":
                        StorageItem truhe = (StorageItem) roomInventory.findItemByName("Truhe");
                        if (roomInventory.hasItem("Truhe")) {
                            if (truhe.getLockState() == true) {
                                truhe.setLockState(false);
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
                        if (dungeon.getCurrentRoom().getRoomNumber() == 3) {
                            item5 = playerInventory.findItemByName("Fackel");
                            if (item5 instanceof ToggleItem) {
                                fackel = (ToggleItem) item5;
                                if (fackel.getState() == true) {
                                    if (this == null) {
                                        Textie.printText("Das Objekt gibt es nicht.");
                                    } else {
                                        Textie.printText(useText);
                                    }
                                } else {
                                    Textie.printText("Du kannst nichts sehen!");
                                }
                            }
                        } else {
                            if (this == null) {
                                Textie.printText("Das Objekt gibt es nicht.");
                            } else {
                                Textie.printText(useText);
                            }
                        }
                }
            } else {
                Textie.printText("Du musst das Item im Inventar haben.");
            }
        }
    }
}
