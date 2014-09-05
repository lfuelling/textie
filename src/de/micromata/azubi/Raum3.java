package de.micromata.azubi;

public class Raum3 extends Raum {
	public Raum3(Inventory inventory, int... items) {
		super(inventory, items);
	}

	public void start() {
		System.out
				.println("Ein Windstoß sorgt dafür, dass die Fackel ausgeht.");
		System.out
				.println("Es ist zu dunkel, um etwas zu sehen. Ein seltsamer Geruch liegt in der Luft.");
		prompt();
	}

	@Override
	public boolean isFinished() {
		//Raum3 durch durch benutzen der Falltür verlassen
		return inventory.isInInventory(Textie.BRECHEISEN)
				|| inventory.isInInventory(Textie.SCHLUESSEL);
	}
}
