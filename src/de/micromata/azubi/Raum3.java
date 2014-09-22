package de.micromata.azubi;

public class Raum3 extends Raum {
	public Raum3(Inventory inventory, int number, Item... items) {
		super(inventory, number, items);
	}

	public void start() {
		System.out.println("Ein Windstoß sorgt dafür, dass die Fackel ausgeht.");
		Item item2 = Textie.itemMap.get("FACKEL");
		if (item2 instanceof ToggleItem) {
			ToggleItem fackel = (ToggleItem) item2;
			fackel.setState(false);
		}
		System.out.println("Es ist zu dunkel, um etwas zu sehen. Ein seltsamer Geruch liegt in der Luft.");
		Textie.prompt();
	}

	@Override
	public boolean isFinished() {
		// Raum3 durch benutzen der Falltür verlassen
		if (falltuerUsed == true) {
			return true;
		} else {
			return false;
		}
	}
}
