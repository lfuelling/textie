package de.micromata.azubi;

public class Raum2 extends Raum {
	boolean west = false;

	public Raum2(Inventory inventory, int... items) {
		super(inventory, items);
	}

	public void start() {

		west = false;

		System.out.println("Du kommst in einen weiteren dunklen Raum.");
		this.prompt();
	}

	@Override
	public boolean isFinished() {
		//West wird in goWest gesetzt und ist wahr, wenn Feuerzeug und Schlüssel im Inventar sind
		if (west) {
			System.out
					.println("Da ist eine Tür. Du öffnest sie und gehst die Steintreppe dahinter hoch.");
			return true;
		}
		return false;
	}

	@Override
	public void goWest() {
		if (inventory.isInInventory(Textie.FEUERZEUG)
				&& inventory.isInInventory(Textie.SCHLUESSEL)) {
			west = true;
		} else {
			System.out
					.println("Da ist eine Tür. Du gehst nicht hinaus, da du das Gefühl hast, noch nicht alles erledigt zu haben.");
		}
	}
}