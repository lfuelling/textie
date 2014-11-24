package test.de.micromata.azubi;

import de.micromata.azubi.model.Dungeon;

public class TextieInputTester {

	Dungeon dungeon;
	
	public TextieInputTester(Dungeon dungeon) {
		this.dungeon = dungeon;
		
	}
	
	public Dungeon getDungeon() {
		return dungeon;
	}
	
	// TODO Create Some Methodes for The Output
		
	
	public TextieOutputTester next() {
		this.dungeon.runGameTest();
		return new TextieOutputTester(dungeon);
	}

}
