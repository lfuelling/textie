package test.de.micromata.azubi;

import de.micromata.azubi.Command;
import de.micromata.azubi.Textie;
import de.micromata.azubi.model.Dungeon;
import de.micromata.azubi.model.StorageItem;

public class TextieOutputTester {

	Dungeon dungeon;

	public TextieOutputTester(Dungeon dungeon) {
		// TODO Copy/Clone Dungeon für Snapshots der einzelenen test Schritte.
		this.dungeon = dungeon;
	}
	
	
	/**
	 *  TODO StreamOut der Console auslesen
	 *  
	 * @return
	 */
	public Dungeon getDungeon() {
		return dungeon;
	}

	/**
	 * Lässt den Spieler gehen.
	 * 
	 * @param richtung
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.model.Dungeon#doGehen(de.micromata.azubi.model.Richtung)
	 */
	public TextieInputTester gehe(String richtung) {
		Textie.executeCommand(new String[] { Command.GEHE, richtung },
				new String[] { richtung }, dungeon);
		return new TextieInputTester(this.dungeon);
	}

	/**
	 * Lässt den Spieler ein item nehmen.
	 * 
	 * @param text
	 *            Was soll der Spieler nehmen?
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.model.Dungeon#doNimm(de.micromata.azubi.model.Item)
	 */
	public TextieInputTester nimm(String text) {
		String[] text2 = Textie.parseInput(text);
		if (text.endsWith("aus truhe")) {
			StorageItem truhe = (StorageItem) dungeon
					.getCurrentRaum().getInventory().findItemByName("Truhe");
			dungeon.doTakeFromChest(truhe.getInventory()
					.findItemByName(text2[0]));

		} else {
			Textie.executeCommand(new String[] { Command.NIMM, text },
					new String[] { text }, dungeon);
		}
		return new TextieInputTester(this.dungeon);

	}

	public TextieInputTester benutze(String item) {
		Textie.executeCommand(new String[] { Command.BENUTZE, item },
				new String[] { item }, dungeon);
		return new TextieInputTester(this.dungeon);
	}

	/**
	 * Lässt den Spieler ein Item untersuchen.
	 * 
	 * @param item
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.model.Dungeon#doUntersuche(String[], int)
	 */
	public TextieInputTester untersuche(String item) {
		Textie.executeCommand(new String[] { Command.UNTERSUCHE, item },
				new String[] { item },dungeon);
		return new TextieInputTester(this.dungeon);
	}

	/**
	 * Lässt den Spieler mit einem Menschen reden.
	 * 
	 * @param human
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.model.Dungeon#doReden()
	 */
	public TextieInputTester rede(String human) {
		Textie.executeCommand(new String[] { Command.REDE, human },
				new String[] { human }, dungeon);
		return new TextieInputTester(this.dungeon);
	}

	/**
	 * Lässt den Spieler ein Item übergeben.
	 * 
	 * @param item
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.model.Dungeon#doGeben(String[], int)
	 */
	public TextieInputTester gib(String item) {
		Textie.executeCommand(new String[] { Command.GIB, item },
				new String[] { item }, dungeon);
		return new TextieInputTester(this.dungeon);
	}

	/**
	 * Lässt den Spieler ein Item wegwerfen.
	 * 
	 * @param item
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.model.Dungeon#doVernichte(de.micromata.azubi.model.Item, int)
	 */
	public TextieInputTester vernichte(String item) {
		Textie.executeCommand(new String[] { Command.VERNICHTE, item },
				new String[] { item }, dungeon);
		return new TextieInputTester(this.dungeon);
	}

	/**
	 * Gibt die hilfe aus.
	 * 
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.Textie#printHelp(dungeon)
	 */
	public TextieInputTester hilfe() {
		Textie.executeCommand(new String[] { Command.HILFE }, new String[] {}, dungeon);
		return new TextieInputTester(this.dungeon);
	}
}
