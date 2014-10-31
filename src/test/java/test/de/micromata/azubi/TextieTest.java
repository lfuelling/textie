package test.de.micromata.azubi;

import java.util.concurrent.ExecutorService;

import de.micromata.azubi.Command;
import de.micromata.azubi.Textie;
import de.micromata.azubi.model.Dungeon;
import de.micromata.azubi.model.StorageItem;

import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
		dungeon = Dungeon.getDungeon();
		Textie.diag = true;
	}

	/**
	 * Dungeon zurücksetzen.
	 */
	@After
	public void testAfter() {
		Dungeon.setDungeon(null);
	}

	/*
	 * BEISPIELTEST
	 * 
	 * @Test public void TestX() { System.out.println(); System.out.println();
	 * System.err.println("Beispieltest"); start();
	 * 
	 * }
	 */

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
		start();
		nimm("fackel");
		untersuche("inventar");
        Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
        gehe("süd");
        nimm("schwert");
        Assert.assertEquals(2, dungeon.getPlayer().getInventory().getSize());
		benutze("schwert");
	}

	/**
	 * Speedrun.
	 * 
	 * @since <pre>
	 * Sep 26, 2014
	 * </pre>
	 */
	@Test
	public void testSave() {
		System.out.println();
		System.out.println();
		System.err.println("-- Save Test --");
		start();
		nimm("fackel");
		untersuche("inventar");
		Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
		gehe("süd");
		nimm("schwert");
		Assert.assertEquals(2, dungeon.getPlayer().getInventory().getSize());
		save();
		benutze("schwert");
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
        start();
        untersuche("raum");
        nimm("fackel");
        untersuche("inventar");
        Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
        nimm("");
        Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
        nimm("lizard");
        Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
        nimm("234hjfkjvn932");
        Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
        nimm("0xD47B34T"); // Dat Beat :3
        benutze("hfsejinefsi");
        Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
        untersuche("");
        benutze("");
        untersuche("sdfghjklhgfd");
        Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
        nimm("whiteboard");
        nimm("brecheisen"); //Darf nicht sein, da das Ding nicht in Raum 1 ist.
        Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
        gehe("süd");
        nimm("truhe");
        untersuche("truhe");
        Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
        hilfe();
        System.err.println("finished.");
    }

	/**
	 * Der "durch die Räume gehen" Test. Das Programm geht einmal in jeden Raum
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
        start();
        untersuche("raum");
        nimm("fackel");
        untersuche("inventar");
        Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
        System.out.println("Gehe in Raum 2");
        gehe("süd");
        untersuche("raum");
        Assert.assertEquals(dungeon.findRaumByNummer(2), dungeon.getCurrentRaum());
        nimm("feuerzeug");
        System.out.println("Gehe in Raum 1");
        gehe("nord");
        untersuche("raum");
        Assert.assertEquals(dungeon.findRaumByNummer(1), dungeon.getCurrentRaum());
        System.out.println("Gehe in Raum 2");
        gehe("süd");
        untersuche("raum");
        System.out.println("Gehe in Raum 3");
        gehe("west");
        benutze("feuerzeug");
        untersuche("raum");
        Assert.assertEquals(dungeon.findRaumByNummer(3), dungeon.getCurrentRaum());
        nimm("brecheisen");
        System.out.println("Gehe in Raum 2");
        gehe("ost");
        untersuche("raum");
        Assert.assertEquals(dungeon.findRaumByNummer(2), dungeon.getCurrentRaum());
        System.out.println("Gehe in Raum 3");
        gehe("west");
        benutze("fackel");
        untersuche("raum");
        Assert.assertEquals(dungeon.findRaumByNummer(3), dungeon.getCurrentRaum());
        System.out.println("Gehe in Raum 4");
        benutze("falltür");
        untersuche("raum");
        Assert.assertEquals(dungeon.findRaumByNummer(4), dungeon.getCurrentRaum());
        System.out.println("Gehe in Raum 4");
        benutze("falltür");
        untersuche("raum");
        benutze("schalter");
        System.out.println("Gehe in Raum 1");
        gehe("ost");
        untersuche("raum");
        Assert.assertEquals(dungeon.findRaumByNummer(1), dungeon.getCurrentRaum());
        System.out.println("Gehe in Raum 4");
        benutze("schalter");
        gehe("west");
        untersuche("raum");
        Assert.assertEquals(dungeon.findRaumByNummer(4), dungeon.getCurrentRaum());
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
        start();
        nimm("fackel");
        gehe("süd");
        Assert.assertEquals(dungeon.findRaumByNummer(2), dungeon.getCurrentRaum());
        untersuche("raum");
        Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
        nimm("feuerzeug");
        Assert.assertEquals(2, dungeon.getPlayer().getInventory().getSize());
        gehe("west");
        benutze("fackel");
        untersuche("raum");
        Assert.assertEquals(dungeon.findRaumByNummer(3), dungeon.getCurrentRaum());
        nimm("brecheisen");
        Assert.assertEquals(3, dungeon.getPlayer().getInventory().getSize());
        benutze("falltür");
        untersuche("raum");
        Assert.assertEquals(dungeon.findRaumByNummer(4), dungeon.getCurrentRaum());
        rede("alter mann");
        gib("brecheisen");
        untersuche("inventar");
        Assert.assertEquals(true, dungeon.getPlayer().getInventory().hasItem("Schlüssel"));
        Assert.assertEquals(false, dungeon.getCurrentRaum().getInventory().hasItem("Brecheisen"));
        Assert.assertEquals(3, dungeon.getPlayer().getInventory().getSize());
        benutze("schalter");
        gehe("ost");
        untersuche("raum");
        Assert.assertEquals(dungeon.findRaumByNummer(1), dungeon.getCurrentRaum());
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
        start();
        untersuche("raum");
        nimm("fackel");
        untersuche("fackel");
        benutze("fackel");
        nimm("truhe");
        untersuche("truhe");
        benutze("truhe");
        untersuche("Schalter");
        benutze("Schalter");
        nimm("handtuch");
        untersuche("Handtuch");
        benutze("handtuch");
        untersuche("inventar");
        untersuche("raum");
        vernichte("handtuch");
        untersuche("raum");
        untersuche("inventar");
        Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
        System.err.println("\nGehe in Raum 2\n");
        gehe("süd");
        untersuche("raum");
        nimm("stein");
        untersuche("stein");
        benutze("stein");
        untersuche("raum");
        untersuche("inventar");
        vernichte("stein");
        untersuche("raum");
        untersuche("inventar");
        nimm("feuerzeug");
        untersuche("feuerzeug");
        benutze("feuerzeug");
        untersuche("inventar");
        untersuche("raum");
        nimm("schwert");
        untersuche("schwert");
        vernichte("schwert");
        Assert.assertEquals(2, dungeon.getPlayer().getInventory().getSize());
        System.err.println("\nGehe in Raum 3\n");
        gehe("west");
        untersuche("raum");
        untersuche("inventar");
        benutze("fackel");
        untersuche("raum");
        untersuche("inventar");
        nimm("brecheisen");
        untersuche("brecheisen");
        benutze("brecheisen");
        untersuche("falltür");
        untersuche("whiteboard");
        benutze("whiteboard");
        nimm("quietscheente");
        untersuche("quietscheente");
        benutze("quietscheente");
        vernichte("quietscheente");
        Assert.assertEquals(3, dungeon.getPlayer().getInventory().getSize());
        System.err.println("\nGehe in Raum 4\n");
        benutze("falltür");
        rede("alter mann");
        gib("brecheisen");
        Assert.assertEquals(true, dungeon.getPlayer().getInventory().hasItem("Schlüssel"));
        Assert.assertEquals(false, dungeon.getCurrentRaum().getInventory().hasItem("Brecheisen"));
        Assert.assertEquals(3, dungeon.getPlayer().getInventory().getSize());
        untersuche("inventar");
        rede("alter mann");
        rede("alter mann");
        untersuche("schalter");
        benutze("schalter");
        System.err.println("\nGehe in Raum 1\n");
        benutze("schalter");
        gehe("ost");
        untersuche("inventar");
        benutze("schlüssel");
        untersuche("truhe");
        untersuche("raum");
        System.err.println("finished.");
    }

  /**
   * Test Map:  1/4/5/6/7
   * Test Quest: Brief übergabe mit den neuen Items
   */
    @Test
    public void testRaum4Bis7() {
        start();
        Assert.assertEquals(dungeon.findRaumByNummer(1), dungeon.getCurrentRaum());
        nimm("handtuch");
        untersuche("handtuch");
        benutze("handtuch");
        Assert.assertEquals(1, dungeon.getPlayer().getInventory().getSize());
        benutze("schalter");
        gehe("west");
        Assert.assertEquals(dungeon.findRaumByNummer(4), dungeon.getCurrentRaum());
        nimm("karte");
        gehe("west");
        untersuche("raum");
        benutze("karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(WEST)--", Textie.lastPrintedText);
		Assert.assertEquals(dungeon.findRaumByNummer(5),
				dungeon.getCurrentRaum());
        rede("junge");
        gib("handtuch");
        Assert.assertEquals(true, dungeon.getPlayer().getInventory().hasItem("brief"));
        untersuche("brief");
        benutze("brief");
        gehe("falltür");
        Assert.assertEquals(dungeon.findRaumByNummer(6), dungeon.getCurrentRaum());
		benutze("karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(WEST)--[Raum 5]--(FALLTUER)--", Textie.lastPrintedText);
        untersuche("truhe");
        nimm("axt aus truhe");
        untersuche("inventar");
        Assert.assertEquals(3, dungeon.getPlayer().getInventory().getSize());
        benutze("axt");
        gehe("ost");
        Assert.assertEquals(dungeon.findRaumByNummer(7), dungeon.getCurrentRaum());
		benutze("karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(WEST)--[Raum 5]--(FALLTUER)--[Raum 6]--(OST)--",Textie.lastPrintedText);
        untersuche("raum");
        untersuche("inventar");
        rede("frau");
        gib("brief");
        untersuche("seil");
        untersuche("inventar");
        benutze("schalter");
        gehe("süd");
        Assert.assertEquals(dungeon.findRaumByNummer(4), dungeon.getCurrentRaum());
        benutze("karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(WEST)--[Raum 5]--(FALLTUER)--[Raum 6]--(OST)--[Raum 7]--(SUED)--",Textie.lastPrintedText);
        benutze("schalter");
        gehe("ost");
        benutze("karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(WEST)--[Raum 5]--(FALLTUER)--[Raum 6]--(OST)--[Raum 7]--(SUED)--[Raum 4]--(OST)--",Textie.lastPrintedText);
        System.out.println("Ende.");
    }

    /**
     * Karten Test
     * Spieler läuft einmal durch den kompletten Dungeon und benutzt dabei Karte.
     */
    @Test
    public void testKarte() {
        start();
        nimm("Fackel");
        benutze("schalter");
        gehe("west");
        nimm("karte");
        benutze("karte");
        Assert.assertEquals("[Raum 1]--(WEST)--", Textie.lastPrintedText);
        benutze("schalter");
        gehe("ost");
        benutze("Karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--", Textie.lastPrintedText);
        gehe("süd");
        nimm("Feuerzeug");
        benutze("Karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--", Textie.lastPrintedText);
        gehe("west");
        benutze("Fackel");
        benutze("Karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--", Textie.lastPrintedText);
        benutze("Falltür");
        benutze("Karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--", Textie.lastPrintedText);
        benutze("Fackel");
        gehe("ost");
        benutze("Karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--[Raum 4]--(OST)--", Textie.lastPrintedText);
        gehe("west");
        benutze("Karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--[Raum 4]--(OST)--[Raum 1]--(WEST)--", Textie.lastPrintedText);
        gehe("falltür");
        benutze("karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--[Raum 4]--(OST)--[Raum 1]--(WEST)--", Textie.lastPrintedText);
        gehe("west");
        benutze("karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--[Raum 4]--(OST)--[Raum 1]--(WEST)--[Raum 4]--(WEST)--",Textie.lastPrintedText);
        gehe("falltuer");
        benutze("karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--[Raum 4]--(OST)--[Raum 1]--(WEST)--[Raum 4]--(WEST)--[Raum 5]--(FALLTUER)--", Textie.lastPrintedText);
        nimm("axt aus truhe");
        benutze("axt");
        gehe("ost");
        benutze("karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--[Raum 4]--(OST)--[Raum 1]--(WEST)--[Raum 4]--(WEST)--[Raum 5]--(FALLTUER)--[Raum 6]--(OST)--", Textie.lastPrintedText);
        benutze("schalter");
        gehe("süd");
        benutze("karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--[Raum 4]--(OST)--[Raum 1]--(WEST)--[Raum 4]--(WEST)--[Raum 5]--(FALLTUER)--[Raum 6]--(OST)--[Raum 7]--(SUED)--", Textie.lastPrintedText);
        gehe("ost");
        benutze("karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--[Raum 1]--(SUED)--[Raum 2]--(WEST)--[Raum 3]--(FALLTUER)--[Raum 4]--(OST)--[Raum 1]--(WEST)--[Raum 4]--(WEST)--[Raum 5]--(FALLTUER)--[Raum 6]--(OST)--[Raum 7]--(SUED)--[Raum 4]--(OST)--", Textie.lastPrintedText);
    }

    /**
     * Testet die Karte auf Mitschreiben von falschen Richtungen:
     */
    @Test
    public void testKarteFalsch() {
        start();
        benutze("schalter");
        gehe("west");
        nimm("karte");
        benutze("karte");
        Assert.assertEquals("[Raum 1]--(WEST)--", Textie.lastPrintedText);
        gehe("süd");
        benutze("Karte");
        Assert.assertEquals("[Raum 1]--(WEST)--", Textie.lastPrintedText);
        benutze("schalter");
        gehe("ost");
        benutze("Karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--", Textie.lastPrintedText);
        gehe("nord");
        benutze("Karte");
        Assert.assertFalse("[Raum 1]--(WEST)--[Raum 4]--(OST)--(NORD)--".equals(Textie.lastPrintedText));
        Assert.assertTrue("[Raum 1]--(WEST)--[Raum 4]--(OST)--".equals(Textie.lastPrintedText));
    }
    /* SUBFUNKTIONEN */

    /**
     * Lässt den Spieler gehen.
     *
     * @param richtung
     * @return gibt den Test weiter.
     * @see de.micromata.azubi.Textie#doGehen(de.micromata.azubi.model.Richtung)
     */

	/**
	 * Lässt den Spieler gehen.
	 * 
	 * @param richtung
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.Textie#doGehen(de.micromata.azubi.Richtung)
	 */
	private TextieTest gehe(String richtung) {
		Textie.executeCommand(new String[] { Command.GEHE, richtung },
				new String[] { richtung });
		Thread.yield();
		return this;
	}

	/**
	 * Lässt den Spieler ein item nehmen.
	 * 
	 * @param text
	 *            Was soll der Spieler nehmen?
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.Textie#doNimm(de.micromata.azubi.Item)
	 */
	private TextieTest nimm(String text) {
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
		Thread.yield();
		return this;

	}

	ExecutorService executor = Executors.newSingleThreadExecutor();

	/**
	 * Startet das SPiel
	 * 
	 * @return gibt den Test weiter
	 */
	private TextieTest start() {

		Runnable runGame = new Runnable() {
			@Override
			public void run() {
				do {
					dungeon.runGame(false);
				} while (!Dungeon.getDungeon().getPlayer().isAlive());
			}
		};
		executor.submit(runGame);
		return this;
	}

	private TextieTest save() {
		Textie.executeCommand(new String[] { "speichern" }, new String[] {});
		Thread.yield();
		return this;
	}
	
	private TextieTest load() {
		Textie.executeCommand(new String[] { "laden" }, new String[] {});
		Thread.yield();
		return this;
	}
	
	private TextieTest benutze(String item) {
		Textie.executeCommand(new String[] { Command.BENUTZE, item },
				new String[] { item });
		Thread.yield();
		return this;
	}

	/**
	 * Lässt den Spieler ein Item untersuchen.
	 * 
	 * @param item
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.Textie#doUntersuche(String[], int)
	 */
	private TextieTest untersuche(String item) {
		Textie.executeCommand(new String[] { Command.UNTERSUCHE, item },
				new String[] { item });
		 Thread.yield();
		return this;
	}

	/**
	 * Lässt den Spieler mit einem Menschen reden.
	 * 
	 * @param human
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.Textie#doReden()
	 */
	private TextieTest rede(String human) {
		Textie.executeCommand(new String[] { Command.REDE, human },
				new String[] { human });
		 Thread.yield();
		return this;
	}

	/**
	 * Lässt den Spieler ein Item übergeben.
	 * 
	 * @param item
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.Textie#doGeben(String[], int)
	 */
	private TextieTest gib(String item) {
		Textie.executeCommand(new String[] { Command.GIB, item },
				new String[] { item });
		 Thread.yield();
		return this;
	}

	/**
	 * Lässt den Spieler ein Item wegwerfen.
	 * 
	 * @param item
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.Textie#doVernichte(de.micromata.azubi.Item, int)
	 */
	private TextieTest vernichte(String item) {
		Textie.executeCommand(new String[] { Command.VERNICHTE, item },
				new String[] { item });
		 Thread.yield();
		return this;
	}

	/**
	 * Gibt die hilfe aus.
	 * 
	 * @return gibt den Test weiter.
	 * @see de.micromata.azubi.Textie#printHelp()
	 */
	private TextieTest hilfe() {
		Textie.executeCommand(new String[] { Command.HILFE }, new String[] {});
		Thread.yield();
		return this;
	}

}
