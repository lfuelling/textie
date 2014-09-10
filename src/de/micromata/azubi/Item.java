package de.micromata.azubi;

public class Item {
	// Gegenstände
	public static final int FACKEL = 1;
	public static final int HANDTUCH = 2;
	public static final int QUIETSCHEENTE = 3;
	public static final int BRECHEISEN = 4;
	public static final int SCHWERT = 5;
	public static final int FEUERZEUG = 6;
	public static final int SCHLUESSEL = 7;
	public static final int STEIN = 8;
	public static final int TRUHE = 9;
	public static final int SCHALTER = 10;
	public static final int WHITEBOARD = 11;
	public static final int FALLTUER = 12;
	public static final int KARTE = 13;

	private int objectID = 0;
	private String untersucheText = "";
	private String benutzeText;
	private String name;


	public Item(String name, String untersucheText, String benutzeText) {
		this.name = name;
	}

	public String getName() {

		return this.name;
		// return getObjectName(objectID);
	}

	public int getID() {
		return this.objectID;
	}

	public static String getObjectName(int id) {
		switch (id) {
		case FACKEL:
			return "Fackel";

		case HANDTUCH:
			return "Handtuch";

		case QUIETSCHEENTE:
			return "Quietscheente";

		case BRECHEISEN:
			return "Brecheisen";

		case SCHWERT:
			return "Schwert";

		case FEUERZEUG:
			return "Feuerzeug";

		case SCHLUESSEL:
			return "Schlüssel";

		case STEIN:
			return "Stein";

		case TRUHE:
			return "Truhe";

		case SCHALTER:
			return "Schalter";

		case WHITEBOARD:
			return "Whiteboard";

		case FALLTUER:
			return "Falltür";
			
		case KARTE:
			return "Karte";

		default:
			return "Kein Objekt";
		}
	}

	public static int getObjectID(String objectName) {
		switch (objectName) {
		case "FACKEL":
			return FACKEL;

		case "HANDTUCH":
			return HANDTUCH;

		case "QUIETSCHEENTE":
			return QUIETSCHEENTE;

		case "BRECHEISEN":
			return BRECHEISEN;

		case "SCHWERT":
			return SCHWERT;

		case "FEUERZEUG":
			return FEUERZEUG;

		case "SCHLÜSSEL":
			return SCHLUESSEL;

		case "STEIN":
			return STEIN;

		case "TRUHE":
			return TRUHE;

		case "SCHALTER":
			return SCHALTER;

		case "WHITEBOARD":
			return WHITEBOARD;

		case "FALLTÜR":
			return FALLTUER;
			
		case "KARTE":
			return KARTE;

		default:
			return 0;
		}
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
