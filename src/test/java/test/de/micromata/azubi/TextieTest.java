package test.de.micromata.azubi;



import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.micromata.azubi.Textie;
import de.micromata.azubi.model.Dungeon;



/**
 * Textie Tester.
 * 
 * @author Lukas F&uuml;lling
 * @version 2.0
 * @since <pre>
 * Sep 25, 2014
 * </pre>
 */
public class TextieTest {
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
	 * Dungeon zurücksetzen.
	 */
	@After
	public void testAfter() {
		//Dungeon.setDungeon(null);
	}

	/* TESTDURCHGÄNGE */

	/**
	 * Speedrun.
	 * 
	 * @since <pre>
	 * Sep 26, 2014
	 * </pre>
	 */
	@Test
	public void testSpeedrun() {
		System.out.println();
		System.out.println();
		System.err.println("-- Speedrun Test --");
		TextieOutputTester out = new TextieInputTester(dungeon).next()
				.nimm("fackel").next().untersuche("inventar").next();
		Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
		out = out.gehe("süd").next().nimm("schwert").next();
		Assert.assertEquals(2, dungeon.getPlayer().getInventory().getSize());
		out.benutze("schwert").next();
		// FIXME !!
		Assert.assertEquals(false, dungeon.getPlayer().isAlive());
	}

	/**
	 * Komischer Test, der Sachen testet, die es nicht geben sollte.
	 * 
	 * @since <pre>
	 * Sep 29, 2014
	 * </pre>
     */
    @Test
    public void testFehler() {
        System.out.println();
        System.out.println();
        System.err.println("-- QS Test --");
        TextieOutputTester out = new TextieInputTester(dungeon).next()
        .nimm("").next();
        Assert.assertEquals(0, dungeon.getPlayer().getInventory().getSize());
        out = out.nimm("lizard").next();
        Assert.assertEquals(0, dungeon.getPlayer().getInventory().getSize());
        out = out.nimm("234hjfkjvn932").next();
        Assert.assertEquals(0, dungeon.getPlayer().getInventory().getSize());
        out = out.nimm("0xD47B34T").next() // Dat Beat :3
        .benutze("hfsejinefsi").next();
        Assert.assertEquals(0, dungeon.getPlayer().getInventory().getSize());
        out = out.untersuche("").next().benutze("").next().untersuche("sdfghjklhgfd").next();
        Assert.assertEquals(0, dungeon.getPlayer().getInventory().getSize());
        out = out.nimm("whiteboard").next().nimm("brecheisen").next(); // Darf nicht sein, da das Ding nicht in Raum 1 ist.
        Assert.assertEquals(0, dungeon.getPlayer().getInventory().getSize());
        out = out.gehe("süd").next().nimm("truhe").next().untersuche("truhe").next();
        Assert.assertEquals(0, dungeon.getPlayer().getInventory().getSize());
        out = out.hilfe().next();
        System.err.println("finished.");
    }


	/**
	* Der "durch die Räume gehen" Test. Das Programm geht einmal in jeden
	Raum
	* und zurück und nimmt die Items mit, die Pflicht sind.
	*
	* @since <pre>
	* Sep 30, 2014
	* </pre>
	*/
	@Test
	public void testDRG() {
	System.out.println();
	System.out.println();
	System.err.println("-- DRG Test --");
    TextieOutputTester out = new TextieInputTester(dungeon).next()
	.untersuche("raum").next()
	.nimm("fackel").next()
	.untersuche("inventar").next();
	Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
	System.out.println("Gehe in Raum 2");
	out = out.gehe("süd").next()
	.untersuche("raum").next();
	Assert.assertEquals(dungeon.findRoomByNumber(2),
	dungeon.getCurrentRoom());
	out = out.nimm("feuerzeug").next();
	System.out.println("Gehe in Raum 1");
	out.gehe("nord").next()
	.untersuche("raum").next();
	Assert.assertEquals(dungeon.findRoomByNumber(1),
	dungeon.getCurrentRoom());
	System.out.println("Gehe in Raum 2");
	out = out.gehe("süd").next()
	.untersuche("raum").next();
	System.out.println("Gehe in Raum 3");
	out.gehe("west").next()
	.benutze("feuerzeug").next()
	.untersuche("raum").next();
	Assert.assertEquals(dungeon.findRoomByNumber(3),
	dungeon.getCurrentRoom());
	out = out.nimm("brecheisen").next();
	System.out.println("Gehe in Raum 2");
	out.gehe("ost").next()
	.untersuche("raum").next();
	Assert.assertEquals(dungeon.findRoomByNumber(2),
	dungeon.getCurrentRoom());
	System.out.println("Gehe in Raum 3");
	out = out.gehe("west").next()
	.benutze("fackel").next()
	.untersuche("raum").next();
	Assert.assertEquals(dungeon.findRoomByNumber(3),
	dungeon.getCurrentRoom());
	System.out.println("Gehe in Raum 4");
	out = out.benutze("falltür").next()
	.untersuche("raum").next();
	Assert.assertEquals(dungeon.findRoomByNumber(4),
	dungeon.getCurrentRoom());
	System.out.println("Gehe in Raum 4");
	out = out.untersuche("raum").next()
	.benutze("schalter").next();
	System.out.println("Gehe in Raum 1");
	out.gehe("ost").next()
	.untersuche("raum").next();
	Assert.assertEquals(dungeon.findRoomByNumber(1),
	dungeon.getCurrentRoom());
	System.out.println("Gehe in Raum 4");
	out = out.gehe("west").next()
	.untersuche("raum").next();
	Assert.assertEquals(dungeon.findRoomByNumber(4),
	dungeon.getCurrentRoom());
	System.err.println("finished.");
	}

	/**
	* Dieser Test löst die Quest (Reim)
	*/
	@Test
	public void testQuest() {
	System.out.println();
	System.out.println();
	System.err.println("-- Questtest --");
    TextieOutputTester out = new TextieInputTester(dungeon).next()
	.nimm("fackel").next()
	.gehe("süd").next();
	Assert.assertEquals(dungeon.findRoomByNumber(2),
	dungeon.getCurrentRoom());
	out = out.untersuche("raum").next();
	Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
	out = out.nimm("feuerzeug").next();
	Assert.assertEquals(2, dungeon.getPlayer().getInventory().getSize());
	out = out.gehe("west").next()
	.benutze("fackel").next()
	.untersuche("raum").next();
	Assert.assertEquals(dungeon.findRoomByNumber(3),
	dungeon.getCurrentRoom());
	out = out.nimm("brecheisen").next();
	Assert.assertEquals(3, dungeon.getPlayer().getInventory().getSize());
	out= out.benutze("falltür").next()
	.untersuche("raum").next();
	Assert.assertEquals(dungeon.findRoomByNumber(4),
	dungeon.getCurrentRoom());
	out = out.rede("alter mann").next()
	.gib("brecheisen").next()
	.untersuche("inventar").next();
	Assert.assertEquals(true,
	dungeon.getPlayer().getInventory().hasItem("Schlüssel"));
	Assert.assertEquals(false,
	dungeon.getCurrentRoom().getInventory().hasItem("Brecheisen"));
	Assert.assertEquals(3, dungeon.getPlayer().getInventory().getSize());
	out = out.benutze("schalter").next()
	.gehe("ost").next()
	.untersuche("raum").next();
	Assert.assertEquals(dungeon.findRoomByNumber(1),
	dungeon.getCurrentRoom());
	System.err.println("finished.");
	}

	/**
	* ItemTest
	*
	* @since <pre>Sep 26, 2014</pre>
	*/
	@Test
	public void testItem() {
	System.out.println();
	System.out.println();
	System.err.println("-- Item Test --");
    TextieOutputTester out = new TextieInputTester(dungeon).next()
	.untersuche("raum").next()
	.nimm("fackel").next()
	.untersuche("fackel").next()
	.benutze("fackel").next()
	.nimm("truhe").next()
	.untersuche("truhe").next()
	.benutze("truhe").next()
	.untersuche("Schalter").next()
	.benutze("Schalter").next()
	.nimm("handtuch").next()
	.untersuche("Handtuch").next()
	.benutze("handtuch").next()
	.untersuche("inventar").next()
	.untersuche("raum").next()
	.vernichte("handtuch").next()
	.untersuche("raum").next()
	.untersuche("inventar").next();
	Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
	System.err.println("\nGehe in Raum 2\n");
	out = out.gehe("süd").next()
	.untersuche("raum").next()
	.nimm("stein").next()
	.untersuche("stein").next()
	.benutze("stein").next()
	.untersuche("raum").next()
	.untersuche("inventar").next()
	.vernichte("stein").next()
	.untersuche("raum").next()
	.untersuche("inventar").next()
	.nimm("feuerzeug").next()
	.untersuche("feuerzeug").next()
	.benutze("feuerzeug").next()
	.untersuche("inventar").next()
	.untersuche("raum").next()
	.nimm("schwert").next()
	.untersuche("schwert").next()
	.vernichte("schwert").next();
	Assert.assertEquals(2, dungeon.getPlayer().getInventory().getSize());
	System.err.println("\nGehe in Raum 3\n");
	out = out.gehe("west").next()
	.untersuche("raum").next()
	.untersuche("inventar").next()
	.benutze("fackel").next()
	.untersuche("raum").next()
	.untersuche("inventar").next()
	.nimm("brecheisen").next()
	.untersuche("brecheisen").next()
	.benutze("brecheisen").next()
	.untersuche("falltür").next()
	.untersuche("whiteboard").next()
	.benutze("whiteboard").next()
	.nimm("quietscheente").next()
	.untersuche("quietscheente").next()
	.benutze("quietscheente").next()
	.vernichte("quietscheente").next();
	Assert.assertEquals(3, dungeon.getPlayer().getInventory().getSize());
	System.err.println("\nGehe in Raum 4\n");
	out = out.benutze("falltür").next()
	.rede("alter mann").next()
	.gib("brecheisen").next();
	Assert.assertEquals(true,
	dungeon.getPlayer().getInventory().hasItem("Schlüssel"));
	Assert.assertEquals(false,
	dungeon.getCurrentRoom().getInventory().hasItem("Brecheisen"));
	Assert.assertEquals(3, dungeon.getPlayer().getInventory().getSize());
	out = out.untersuche("inventar").next()
	.rede("alter mann").next()
	.rede("alter mann").next()
	.untersuche("schalter").next()
	.benutze("schalter").next();
	System.err.println("\nGehe in Raum 1\n");
	out.benutze("schalter").next()
	.gehe("ost").next()
	.untersuche("inventar").next()
	.benutze("schlüssel").next()
	.untersuche("truhe").next()
	.untersuche("raum").next();
	System.err.println("finished.");
	}

	/**
	* Test Map: 1/4/5/6/7
	* Test Quest: Brief übergabe mit den neuen Items
	*/
	@Test
	public void testRaum4Bis7() {
    TextieOutputTester out = new TextieInputTester(dungeon).next();
	Assert.assertEquals(dungeon.findRoomByNumber(1),
	dungeon.getCurrentRoom());
	out = out.nimm("handtuch").next()
	.untersuche("handtuch").next()
	.benutze("handtuch").next();
	Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
	out = out.benutze("schalter").next()
	.gehe("west").next();
	Assert.assertEquals(dungeon.findRoomByNumber(4),
	dungeon.getCurrentRoom());
	out = out.nimm("karte").next()
	.gehe("west").next()
	.untersuche("raum").next()
	.benutze("karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(WEST)--",
	Textie.lastPrintedText);
	Assert.assertEquals(dungeon.findRoomByNumber(5),
	dungeon.getCurrentRoom());
	out = out.rede("junge").next()
	.gib("handtuch").next();
	Assert.assertEquals(true,
	dungeon.getPlayer().getInventory().hasItem("brief"));
	out = out.untersuche("brief").next()
	.benutze("brief").next()
	.gehe("falltür").next();
	Assert.assertEquals(dungeon.findRoomByNumber(6),
	dungeon.getCurrentRoom());
	out = out.benutze("karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(WEST)--[Raum 5]--(FALLTUER)--",
	Textie.lastPrintedText);
	out = out.untersuche("truhe").next()
	.nimm("axt aus truhe").next()
	.untersuche("inventar").next();
	Assert.assertEquals(3, dungeon.getPlayer().getInventory().getSize());
	out = out.benutze("axt").next()
	.gehe("ost").next();
	Assert.assertEquals(dungeon.findRoomByNumber(7),
	dungeon.getCurrentRoom());
	out = out.benutze("karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(WEST)--[Raum 5]--(FALLTUER)--[Raum 6]--(OST)--",Textie.lastPrintedText);
	out = out.untersuche("raum").next()
	.untersuche("inventar").next()
	.rede("frau").next()
	.gib("brief").next()
	.untersuche("seil").next()
	.untersuche("inventar").next()
	.benutze("schalter").next()
	.gehe("süd").next();
	Assert.assertEquals(dungeon.findRoomByNumber(4),
	dungeon.getCurrentRoom());
	out = out.benutze("karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(WEST)--[Raum 5]--(FALLTUER)--[Raum 6]--(OST)--[Raum 7]--(SUED)--",Textie.lastPrintedText);
	out = out.benutze("schalter").next()
	.gehe("ost").next()
	.benutze("karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(WEST)--[Raum 5]--(FALLTUER)--[Raum 6]--(OST)--[Raum 7]--(SUED)--[Raum 4]--(OST)--",Textie.lastPrintedText);
	System.out.println("Ende.");
	}

	/**
	* Karten Test
	* Spieler läuft einmal durch den kompletten Dungeon und benutzt dabei
	Karte.
	*/
	@Test
	public void testKarte() {
    TextieOutputTester out = new TextieInputTester(dungeon).next()
    .nimm("Fackel").next()
	.benutze("schalter").next()
	.gehe("west").next()
	.nimm("karte").next()
	.benutze("karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--", Textie.lastPrintedText);
	out = out.benutze("schalter").next()
	.gehe("ost").next()
	.benutze("Karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--",
	Textie.lastPrintedText);
	out = out.gehe("süd").next()
	.nimm("Feuerzeug").next()
	.benutze("Karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--",
	Textie.lastPrintedText);
	out = out.gehe("west").next()
	.benutze("Fackel").next()
	.benutze("Karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--",
	Textie.lastPrintedText);
	out = out.benutze("Falltür").next()
	.benutze("Karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--",
	Textie.lastPrintedText);
	out = out.benutze("Fackel").next()
	.gehe("ost").next()
	.benutze("Karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--[Raum 4]--(OST)--",
	Textie.lastPrintedText);
	out = out.gehe("west").next()
	.benutze("Karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--[Raum 4]--(OST)--[Raum 1]--(WEST)--",
	Textie.lastPrintedText);
	out = out.gehe("falltür").next()
	.benutze("karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--[Raum 4]--(OST)--[Raum 1]--(WEST)--",
	Textie.lastPrintedText);
	out = out.gehe("west").next()
	.benutze("karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--[Raum 4]--(OST)--[Raum 1]--(WEST)--[Raum 4]--(WEST)--",Textie.lastPrintedText);
	out = out.gehe("falltuer").next()
	.benutze("karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--[Raum 4]--(OST)--[Raum 1]--(WEST)--[Raum 4]--(WEST)--[Raum 5]--(FALLTUER)--",
	Textie.lastPrintedText);
	out = out.nimm("axt aus truhe").next()
	.benutze("axt").next()
	.gehe("ost").next()
	.benutze("karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--[Raum 4]--(OST)--[Raum 1]--(WEST)--[Raum 4]--(WEST)--[Raum 5]--(FALLTUER)--[Raum 6]--(OST)--",
	Textie.lastPrintedText);
	out = out.benutze("schalter").next()
	.gehe("süd").next()
	.benutze("karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--[Raum 4]--(OST)--[Raum 1]--(WEST)--[Raum 4]--(WEST)--[Raum 5]--(FALLTUER)--[Raum 6]--(OST)--[Raum 7]--(SUED)--",
	Textie.lastPrintedText);
	out = out.gehe("ost").next()
	.benutze("karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--[Raum 4]--(OST)--[Raum 1]--(WEST)--[Raum 4]--(WEST)--[Raum 5]--(FALLTUER)--[Raum 6]--(OST)--[Raum 7]--(SUED)--[Raum 4]--(OST)--",
	Textie.lastPrintedText);
	}

	/**
	* Testet die Karte auf Mitschreiben von falschen Richtungen:
	*/
	@Test
	public void testKarteFalsch() {
        TextieOutputTester out = new TextieInputTester(dungeon).next()
	.benutze("schalter").next()
	.gehe("west").next()
	.nimm("karte").next()
	.benutze("karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--", Textie.lastPrintedText);
	out = out.gehe("süd").next()
	.benutze("Karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--", Textie.lastPrintedText);
	out = out.benutze("schalter").next()
	.gehe("ost").next()
	.benutze("Karte").next();
	Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--",
	Textie.lastPrintedText);
	out= out.gehe("nord").next()
	.benutze("Karte").next();
	Assert.assertFalse("[Raum 1]--(WEST)--[Raum 4]--(OST)--(NORD)--".equals(Textie.lastPrintedText));
	Assert.assertTrue("[Raum 1]--(WEST)--[Raum 4]--(OST)--".equals(Textie.lastPrintedText));
	}



}
