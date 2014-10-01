package de.micromata.azubi;

public class Item {

    public Item(){

    }

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

    private String untersucheText = "";

    public String getBenutzeText() {
        return benutzeText;
    }

    public void setBenutzeText(String benutzeText) {
        this.benutzeText = benutzeText;
    }

    private String benutzeText;
    private String name;
    private boolean pickable;

    public Item(String name, String untersucheText, String benutzeText, boolean pickable) {
        this.name = name;
        this.untersucheText = untersucheText;
        this.benutzeText = benutzeText;
        this.pickable = pickable;
    }

    public boolean isPickable() {
        return this.pickable;
    }

    public String getName() {

        return this.name;
        // return getObjectName(objectID);
    }

    public void benutzen() {
        System.out.println(benutzeText);
    }

    public void untersuchen() {
        if(untersucheText.equals("") == true) {
            // TODO itemgeschlechter hinzufügen und Text anpassen
            System.out.println("Da ist ein/e " + this.getName() + ".");
        }
        System.out.println(untersucheText);
    }

    public boolean isToggle() {
        if(this instanceof ToggleItem) {
            return true;
        }
        return false;
    }

    public boolean isKarte(){
        if(this instanceof Karte){
            return  true;
        }
        return false;
    }


}
