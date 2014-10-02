package de.micromata.azubi;

import java.util.ArrayList;
import java.util.List;

public abstract class Raum {
  protected List<Item> items = new ArrayList<Item>();
  protected Inventory inventory;
  protected boolean falltuerUsed = false;
  protected int roomNumber;
  protected String eingang;
  protected String ausgang;
  protected int finished = 0;

   public Raum(){
   }

  public Raum(Inventory inventory, int number, String eingang, String ausgang, Item... items1) {
    this.inventory = inventory;
    this.roomNumber = number;
    this.eingang = eingang;
    this.ausgang = ausgang;

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

  public abstract void start(boolean withPrompt);

    /**
     * Methode zum wechseln der Räume
     * @return -1 zum zurück gehen, 0 wenn raum nicht gewechselt werden soll und 1 wenn man weiter gehen möchte
     */
  //public abstract int isFinished();

    public int isFinished(){
        return finished;
    }

  public void setFinished(int finished){
      this.finished = finished;
  }

    public boolean isFalltuerUsed() {
        return falltuerUsed;
    }

    public void setFalltuerUsed(boolean falltuerUsed) {
        this.falltuerUsed = falltuerUsed;
    }

}
