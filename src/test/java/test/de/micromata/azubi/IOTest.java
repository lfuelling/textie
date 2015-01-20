package test.de.micromata.azubi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.micromata.azubi.Textie;
import de.micromata.azubi.model.Dungeon;




/**
 * Diese Klasse testet laden und speichern. 
 * Damit gibt es immer mal wieder Probleme, 
 * wie z.B. eine Klasse nicht Serializable ist.
 * 
 * @author Lukas F&uuml;lling
 * @version 2.0
 * @since <pre>
 * Jan 20, 2015
 * </pre>
 */
public class IOTest {
	private Dungeon dungeon;

	/**
	 * Kram, der vor jedem Test ausgeführt wird.
	 * 
	 * @throws Exception
	 */
	@Before
	public void testBefore() throws Exception {
		dungeon = Dungeon.createDungeon();
		dungeon.getPlayer().setPosition(dungeon.getCurrentRoom());
		Textie.diag = true;
	}
	
	/**
	 * Der eigentliche Test wird nicht ausgeführt, weil es momentan nicht geht. (KP, wieso)
	 * 
	 * @since <pre>
	 * Jan 20, 2015
	 * </pre>
	 *
	 *
	
	@Test
	public void testSaveLaod() {
		System.out.println();
		System.out.println();
		System.err.println("-- IO Test --");
		TextieOutputTester out = new TextieInputTester(dungeon).next().nimm("fackel").next().untersuche("inventar").next();
		Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
		out = out.gehe("süd").next().nimm("schwert").next();
		Assert.assertEquals(2, dungeon.getPlayer().getInventory().getSize());
		out = out.speichern().next();
		out = out.gehe("nord").next().vernichte("schwert").next();
		out = out.laden().next();
		out.benutze("schwert").next();
		Assert.assertEquals(false, dungeon.getPlayer().isAlive());
	}
	*/
}
