package de.micromata.azubi;

public class Raum1 extends Raum {
	boolean south = false;

	public Raum1(Inventory inventory, int... items) {
		super(inventory, items);
	}

	public void start() {
		south = false;
		System.out
				.println("Du befindest dich in einem dunklen Raum. Nach einiger Zeit gewöhnen sich deine Augen an die Dunkelheit.");
		prompt();
	}

	@Override
	public boolean isFinished() {
		if (south) {
			System.out
					.println("Da ist eine Tür. Du öffnest sie und gehst die Steintreppe dahinter hoch.");
			return true;
		}

		return false;
	}

	@Override
	public void goSouth() {
		// Fackel muss im inventar sein, bevor south = true gesetzt wird
		if (inventory.isInInventory(Textie.FACKEL)) {
			south = true;
		} else {
			System.out
					.println("Da ist eine Tür. Du gehst nicht hinaus, da du das Gefühl hast, noch nicht alles erledigt zu haben.");
		}

	}
}