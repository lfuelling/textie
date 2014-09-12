package de.micromata.azubi;

public class Item {

	private String untersucheText = "";
	private String benutzeText;
	private String name;


	public Item(String name, String untersucheText, String benutzeText) {
		this.name = name;
		this.untersucheText = untersucheText;
		this.benutzeText = benutzeText;
	}

	public String getName() {

		return this.name;
		// return getObjectName(objectID);
	}


	public void benutzen() {
		System.out.println(benutzeText);
	}

	public void untersuchen() {
		if (untersucheText.equals("") == true) {
			// TODO itemgeschlechter hinzufügen und Text anpassen
			System.out.println("Da ist ein/e " + this.getName() + ".");
		}
		System.out.println(untersucheText);
	}

}
