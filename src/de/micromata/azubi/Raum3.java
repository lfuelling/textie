package de.micromata.azubi;

public class Raum3 extends Raum {
	public Raum3(Inventory inventory, Item... items) {
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
		//Raum3 durch benutzen der Falltür verlassen
		if(falltuerUsed == true){
			return true;
		}
		else {
			return false;
		}
	}
}
