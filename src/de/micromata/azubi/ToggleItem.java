package de.micromata.azubi;

public class ToggleItem extends Item{
	
	private boolean state;

	public ToggleItem(String name, String untersucheText, String benutzeText, boolean state) {
		super(name, untersucheText, benutzeText);
		state = this.state;
	}
	
	public boolean getState () {
		return state;
	}
	
	public void setState (boolean stateToSet) {
		state = stateToSet;
	}

}
