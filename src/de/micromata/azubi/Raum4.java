package de.micromata.azubi;

public class Raum4 extends Raum {
	boolean nord = false;

	public Raum4(Inventory inventory, int number, Human human, Item... items) {
		super(inventory, number, human, items);
		Textie.setCurrentHuman(human);
	}

	public void start() {

		nord = false;

		System.out.println("Du kommst in einen hell erleuchteten Raum. Ein alter Mann lehnt an der Wand.");
		Textie.prompt();
	}

	@Override
	public boolean isFinished() {
		// nord
		if (nord && Textie.humanMap.get("ALTER MANN").getQuestState()) { //TODO
			System.out.println("Da ist eine Tür. Du öffnest sie und gehst die Steintreppe dahinter hoch.");
			return true;
		}
		return false;
	}

	@Override
	public void goNorth() {
		if (inventory.isInInventory(Textie.itemMap.get("FACKEL"))) {
			nord = true;
		} else {
			System.out.println("Da ist eine Tür. Du gehst nicht hinaus, da du das Gefühl hast, noch nicht alles erledigt zu haben.");
		}
	}
}
