package de.micromata.azubi.model;

public class ToggleItem extends Item {

    private boolean state;
    private int itemID;
    public ToggleItem() {

    }


    public ToggleItem(int itemID, String name, String untersucheText, String benutzeText, boolean state) {
        super(itemID, name, untersucheText, benutzeText);
        this.state = state;
        this.itemID = itemID;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean stateToSet) {
        state = stateToSet;
    }

    public void toggleState(){
        if(state == false){
            state = true;
        }
        else{
            state = false;
        }
    }
          
}
