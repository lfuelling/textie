package de.micromata.azubi;

import java.util.ArrayList;
import java.util.List;

public abstract class Raum {
  protected List<Item> items = new ArrayList<Item>();
  protected Inventory inventory;
  protected boolean fackelUsed = false;
  protected boolean falltuerUsed = false;
  protected int roomNumber;

  public Raum(Inventory inventory, int number, Item... items1) {
    this.inventory = inventory;
    roomNumber = number;

    for (Item item : items1) {
      this.items.add(item);
    }
  }

  protected void warten(boolean withPrompt) {
    if (withPrompt == true) {
      Dungeon.getDungeon().prompt();
    } else {
      System.out.println("Was willst du tun? ");
      Runnable warten = new Runnable() {
        @Override
        public void run() {
          do {
          } while (!isFinished());
        }
      };

      Thread thread = new Thread(warten);
      thread.start();
    }
  }

  public void listItems() {
    // printText("1. Item in List: " +
    // this.items.get(1).getName());
    printText("Im Raum befindet sich:");
    for (Item item : items) {
      if (item == null) {
        printText("\tKein Objekt");
      } else {
        // String objectName = items.get(i).getName();
        printText("\t" + item.getName());
                /*
         * TODO Kontext zum Raum herstellen... for (Item value :
				 * Textie.itemMap.values()) {
				 * printText(value.getName()); }
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
    if (items.contains(item)) {
      return true;
    }
    return false;
  }

  void doGehen(String[] parsed_command, int count) {
    if (count == 2) {
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
      printText("Wohin gehen?");
    }
  }

  void doVernichte(Item item, int count) {
    if (count == 2) {
      if (inventory.removeItem(item)) {
        printText(item.getName() + " vernichtet.");
        return;
      } else {
        printText("Entweder das Objekt gibt es nicht, oder es ist nicht im Inventar.");
        return;
      }
    } else {
      printText("Was soll vernichtet werden?");
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
    if (count == 2) {
      switch (parsed_command[1].toLowerCase()) {
        case "raum":
          if (roomNumber == 3) {
            Item item = Dungeon.getDungeon().itemMap.get(Consts.FACKEL);
            if (item instanceof ToggleItem) {
              ToggleItem fackel = (ToggleItem) item;
              if (fackel.getState() == true) {
                this.listItems();
              } else {
                printText("Du kannst nichts sehen!");
              }
            }
          } else {
            this.listItems();
          }
          break;
        case "inventar":
          if (roomNumber == 3) {
            Item item = Dungeon.getDungeon().itemMap.get(Consts.FACKEL);
            if (item instanceof ToggleItem) {
              ToggleItem fackel = (ToggleItem) item;
              if (fackel.getState() == true) {
                inventory.listItems();
              } else {
                printText("Du kannst nichts sehen!");
              }
            }
          } else {
            inventory.listItems();
          }
          break;
        case "truhe":
          if (find(Dungeon.getDungeon().itemMap.get(Consts.TRUHE)) != -128) {
            StorageItem truhe = (StorageItem) Dungeon.getDungeon().itemMap.get(Consts.TRUHE);
            truhe.listItems();
          } else {
            printText("Hier ist keine Truhe");
          }
          break;
        default:
          if (roomNumber == 3) {
            Item item = Dungeon.getDungeon().itemMap.get(Consts.FACKEL);
            if (item instanceof ToggleItem) {
              ToggleItem fackel = (ToggleItem) item;
              if (fackel.getState() == true) {
                Item itemUSU = Dungeon.getDungeon().itemMap.get(parsed_command[1].toUpperCase());
                if (itemUSU == null) {
                  printText("Das Objekt gibt es nicht.");
                } else {
                  itemUSU.untersuchen();
                }
              } else {
                printText("Du kannst nichts sehen!");
              }
            }
          } else {
            Item itemUSU = Dungeon.getDungeon().itemMap.get(parsed_command[1].toUpperCase());
            if (itemUSU == null) {
              printText("Das Objekt gibt es nicht.");
            } else {
              itemUSU.untersuchen();
            }
          }
      }
    } else {
      printText("Was soll untersucht werden?");
    }
  }

  void doBenutze(Item item) {
    if (item == null) {

      printText("Das Item gibt es nicht.");

    } else {
        if(item.isPickable() == false || inventory.isInInventory(item)){
        String itemName = item.getName();
        if(Textie.diag == true){
            printText("Du willst" + itemName + "benutzen");
        }
        switch (itemName) {
            // Fackel und Feuerzeug sind besonders, da sie auch funktionen
            // aufrufen
            // und nicht nur einen Text ausgeben. Außerdem sollen diese Items
            // benutzbar sein, selbst wenn der Raum dunkel ist.
            case "Fackel":// Dungeon.getDungeon().itemMap.get("FACKEL").getName():
            case "Feuerzeug": // Dungeon.getDungeon().itemMap.get("FEUERZEUG").getName():
                int fackelSlot = inventory.findItem(Dungeon.getDungeon().itemMap.get(Consts.FACKEL));
                int feuerZeugSlot = inventory.findItem(Dungeon.getDungeon().itemMap.get(Consts.FEUERZEUG));
                if (feuerZeugSlot < 0) {
                    printText("Du hast kein Feuerzeug.");
                    break;
                } else if (fackelSlot < 0) {
                    printText("Du hast keine Fackel.");
                    break;
                } else {
                    printText("Du zündest deine Fackel mit dem Feuerzeug an.");
                    Item item2 = Dungeon.getDungeon().itemMap.get("FACKEL");
                    if (item2 instanceof ToggleItem) {
                        ToggleItem fackel = (ToggleItem) item2;
                        fackel.setState(true);
                    }
                    break;
                }
            case "Falltür":
                if (Dungeon.getDungeon().currentRaum.getNumber() == 3) {
                    Item item5 = Dungeon.getDungeon().itemMap.get(Consts.FACKEL);
                    if (item5 instanceof ToggleItem) {
                        ToggleItem fackel = (ToggleItem) item5;
                        if (fackel.getState() == true) {
                            Item itemToUse = Dungeon.getDungeon().itemMap.get(itemName.toUpperCase());
                            if (itemToUse == null) {
                                printText("Das Objekt gibt es nicht.");
                                break;
                            } else {
                                if (find(Dungeon.getDungeon().itemMap.get(Consts.FALLTÜR)) != -128) {
                                    printText("Du schlüpfst durch die Falltür in den darunterliegenden Raum.");
                                    falltuerUsed = true;
                                    break;
                                }
                                // else if
                                // (find(Dungeon.getDungeon().itemMap.get(Consts.FALLTÜR)) !=
                                // -128) {
                                // printText("Da ist eine Falltür. Du hast das Gefühl, nicht alles erledigt zu haben.");
                                // break;
                                // }
                            }
                        } else {
                            printText("Du kannst nichts sehen!");
                            break;
                        }
                    }
                } else {
                    Item itemToUse = Dungeon.getDungeon().itemMap.get(itemName.toUpperCase());
                    if (itemToUse == null) {
                        printText("Das Objekt gibt es nicht.");
                    } else {
                        if (find(Dungeon.getDungeon().itemMap.get(Consts.FALLTÜR)) != -128 && hasEverything()) {
                            printText("Du schlüpfst durch die Falltür in den darunterliegenden Raum.");
                            falltuerUsed = true;
                            break;
                        } else if (find(Dungeon.getDungeon().itemMap.get(Consts.FALLTÜR)) != -128) {
                            printText("Da ist eine Falltür. Du hast das Gefühl, nicht alles erledigt zu haben.");
                            break;
                        }
                    }
                }
            case "Sack":
                Item sack = Dungeon.getDungeon().itemMap.get(itemName.toUpperCase());
                sack.benutzen();
                inventory.removeItem(Dungeon.getDungeon().itemMap.get(Consts.SACK));
                inventory.setInventorySize(2);
                break;
            case "Schalter":
                ToggleItem schalter = (ToggleItem) Dungeon.getDungeon().itemMap.get(itemName.toUpperCase());
                schalter.benutzen();
                schalter.setState(true);
                break;
            case "Schwert":
                Dungeon.getDungeon().itemMap.get(Consts.SCHWERT).benutzen();
                Dungeon.getDungeon().ende();
                break;
            case "Schlüssel":
                StorageItem truhe = (StorageItem) Dungeon.getDungeon().itemMap.get(Consts.TRUHE);
                if (Dungeon.getDungeon().currentRaum.hasItem(Dungeon.getDungeon().itemMap.get(Consts.TRUHE))) {
                    if (truhe.lockState == true) {
                        truhe.lockState = false;
                        printText("Du öffnest die Truhe mit dem Schlüssel.");
                        break;
                    } else {
                        printText("Die Truhe ist bereits aufgeschlossen.");
                        break;
                    }
                } else {
                    printText("Hier gibt es nichts, was man aufschließen könnte.");
                    break;
                }
            default:
                if (roomNumber == 3) {
                    Item item5 = Dungeon.getDungeon().itemMap.get(Consts.FACKEL);
                    if (item5 instanceof ToggleItem) {
                        ToggleItem fackel = (ToggleItem) item5;
                        if (fackel.getState() == true) {
                            Item itemToUse = Dungeon.getDungeon().itemMap.get(itemName.toUpperCase());
                            if (itemToUse == null) {
                                printText("Das Objekt gibt es nicht.");
                            } else {
                                itemToUse.benutzen();
                            }
                        } else {
                            printText("Du kannst nichts sehen!");
                        }
                    }
                } else {
                    Item itemToUse = Dungeon.getDungeon().itemMap.get(itemName.toUpperCase());
                    if (itemToUse == null) {
                        printText("Das Objekt gibt es nicht.");
                    } else {
                        itemToUse.benutzen();
                    }
                }
        }
    }
        else{
            System.out.println("Du musst das Item im Inventar haben.");
        }
    }
  }

  void doTakeFromChest(Item item) {
    if (item.isPickable()) {
      if (inventory.addItemFromChest(item)) {

        printText(item.getName() + " zum Inventar hinzugefügt.");
      } else {
        printText("Entweder das Objekt gibt es nicht, oder dein Inventar ist voll.");
      }
    } else {
      printText("Du kannst dieses Item nicht aufheben.");
    }
  }

  void doNimm(Item item) {
    if (item == null) {
      printText("Unbekanntes Item.");
    } else {
      if (item.isPickable()) {
        if (inventory.addItem(item)) {

          printText(item.getName() + " zum Inventar hinzugefügt.");
        } else {
          printText("Entweder das Objekt gibt es nicht, oder dein Inventar ist voll.");
        }
      } else {
        printText("Du kannst dieses Item nicht aufheben.");
      }
    }
  }

  void printHelp() {
    printText("Mögliche Befehle:");
    printText("\thilfe -> Zeigt diese Hilfe");
    printText("\tnimm [gegenstand] -> Gegenstand zum Inventar hinzufügen");
    printText("\tbenutze [gegenstand] -> Gegenstand benutzen");
    printText("\tuntersuche [gegenstand/raum/inventar] -> Gegenstand, Raum oder Inventar untersuchen");
    printText("\tvernichte [gegenstand] -> Gegenstand aus dem Inventar löschen");
    printText("\tgehe [nord/süd/ost/west] -> In eine Richtung gehen");
  }

  private void goWall() {
    printText("Du bist gegen die Wand gelaufen.");
  }

  public abstract void start(boolean withPrompt);

  public abstract boolean isFinished();

  public boolean hasEverything() {
    if (inventory.isInInventory(Dungeon.getDungeon().itemMap.get(Consts.BRECHEISEN)) && inventory.isInInventory(Dungeon.getDungeon().itemMap.get(Consts.SCHLÜSSEL))) {
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
    if (items.add(item)) {
      return true;
    }

    return false;
  }

  public boolean removeItem(Item item) {
    if (items.remove(item))
      return true;
    return false;
  }

  public void printText(String text) {
      if(Textie.diag == true){
          System.out.println("[" + roomNumber + "], " + text);
      }
    System.out.println(text);
  }

}
