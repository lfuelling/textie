package de.micromata.azubi.model;

import de.micromata.azubi.Textie;

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
    private String benutzeText;
    private String name;
    private String untersucheText;
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
   * @param untersucheText Text which is printed when you inspect the item.
   * @param benutzeText Text which is printed when you use the item.
   * @deprecated Use the builder instead
   */
    public Item(int itemID, String name, String untersucheText, String benutzeText) {
        this.itemID = itemID;
        this.name = name;
        this.untersucheText = untersucheText;
        this.benutzeText = benutzeText;
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
   * prints the benutzeText
   */
    public void benutzen() {
        Textie.printText(benutzeText);
    }

  /**
   * prints the untersucheText
   */
    public void untersuchen() {
        if(untersucheText.equals("") == true) {
            // TODO itemgeschlechter hinzufügen und Text anpassen
            System.out.println("Da ist ein/e " + this.getName() + ".");
        }
        Textie.printText(untersucheText);
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
    public boolean isKarte(){
        if(this instanceof Karte){
            return  true;
        }
        return false;
    }

    public String getUntersucheText() {
        return untersucheText;
    }

    public void setUntersucheText(String untersucheText) {
        this.untersucheText = untersucheText;
    }

    public String getBenutzeText() {
        return benutzeText;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }


    public void setBenutzeText(String benutzeText) {
        this.benutzeText = benutzeText;
    }

    public void setPickable(boolean pickable) {
        this.pickable = pickable;
    }
}
