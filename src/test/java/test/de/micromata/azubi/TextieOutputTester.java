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
	 * @see de.micromata.azubi.Textie#doGehen(de.micromata.azubi.Richtung)
	 */
	public TextieInputTester gehe(String richtung) {
		Textie.executeCommand(new String[] { Command.GEHE, richtung },
				new String[] { richtung });
		return new TextieInputTester(this.dungeon);
	}

	/**
	 * Lässt den Spieler ein item nehmen.
	 * 
	 * @param text
	 *            Was soll der Spieler nehmen?
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.Textie#doNimm(de.micromata.azubi.Item)
	 */
	public TextieInputTester nimm(String text) {
		String[] text2 = Textie.parseInput(text);
		if (text.endsWith("aus truhe")) {
			StorageItem truhe = (StorageItem) Dungeon.getDungeon()
					.getCurrentRaum().getInventory().findItemByName("Truhe");
			Textie.doTakeFromChest(truhe.getInventory()
					.findItemByName(text2[0]));

		} else {
			Textie.executeCommand(new String[] { Command.NIMM, text },
					new String[] { text });
		}
		return new TextieInputTester(this.dungeon);

	}

	public TextieInputTester benutze(String item) {
		Textie.executeCommand(new String[] { Command.BENUTZE, item },
				new String[] { item });
		return new TextieInputTester(this.dungeon);
	}

	/**
	 * Lässt den Spieler ein Item untersuchen.
	 * 
	 * @param item
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.Textie#doUntersuche(String[], int)
	 */
	public TextieInputTester untersuche(String item) {
		Textie.executeCommand(new String[] { Command.UNTERSUCHE, item },
				new String[] { item });
		return new TextieInputTester(this.dungeon);
	}

	/**
	 * Lässt den Spieler mit einem Menschen reden.
	 * 
	 * @param human
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.Textie#doReden()
	 */
	public TextieInputTester rede(String human) {
		Textie.executeCommand(new String[] { Command.REDE, human },
				new String[] { human });
		return new TextieInputTester(this.dungeon);
	}

	/**
	 * Lässt den Spieler ein Item übergeben.
	 * 
	 * @param item
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.Textie#doGeben(String[], int)
	 */
	public TextieInputTester gib(String item) {
		Textie.executeCommand(new String[] { Command.GIB, item },
				new String[] { item });
		return new TextieInputTester(this.dungeon);
	}

	/**
	 * Lässt den Spieler ein Item wegwerfen.
	 * 
	 * @param item
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.Textie#doVernichte(de.micromata.azubi.Item, int)
	 */
	public TextieInputTester vernichte(String item) {
		Textie.executeCommand(new String[] { Command.VERNICHTE, item },
				new String[] { item });
		return new TextieInputTester(this.dungeon);
	}

	/**
	 * Gibt die hilfe aus.
	 * 
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.Textie#printHelp()
	 */
	public TextieInputTester hilfe() {
		Textie.executeCommand(new String[] { Command.HILFE }, new String[] {});
		return new TextieInputTester(this.dungeon);
	}
}
