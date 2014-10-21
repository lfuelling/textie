package test.de.micromata.azubi;

import de.micromata.azubi.Command;
import de.micromata.azubi.Consts;
import de.micromata.azubi.Dungeon;
import de.micromata.azubi.Textie;
import org.junit.*;

import static org.junit.Assert.fail;

/**
 * Textie Tester.
 *
 * @author Lukas F&uuml;lling
 * @version 1.2
 * @since <pre>Sep 25, 2014</pre>
 */
public class TextieTest {
    private static Dungeon dungeon;

    @Before
    public void testBefore() throws Exception {
        dungeon = Dungeon.getDungeon();
        dungeon.init();
        Textie.diag = true;
    }

    @After
    public void testAfter() {
        dungeon.setDungeon(null);
    }


    /* BEISPIELTEST

    @Test
    public void TestX() {
        System.out.println();
        System.out.println();
        System.err.println("Beispieltest");
        start();

    }

     */

    /* TESTDURCHGÄNGE */

    /**
     * Speedrun.
     *
     * @since <pre>Sep 26, 2014</pre>
     */
    @Test
    public void TestA() {
        System.out.println();
        System.out.println();
        System.err.println("-- Speedrun Test --");
        start();
        nimm("fackel");
        Assert.assertEquals(1, dungeon.player.getInventory().getInventory().size());
        gehe("süd");
        nimm("schwert");
        Assert.assertEquals(2, dungeon.player.getInventory().getInventory().size());
        benutze("schwert");
    }

    /**
     * Komischer Test, der Sachen testet, die es nicht geben sollte.
     *
     * @since <pre>Sep 29, 2014</pre>
     */
    @Test
    public void TestC() {
        System.out.println();
        System.out.println();
        System.err.println("-- QS Test --");
        start();
        untersuche("raum");
        nimm("fackel");
        Assert.assertEquals(1, dungeon.player.getInventory().getInventory().size());
        nimm("");
        Assert.assertEquals(1, dungeon.player.getInventory().getInventory().size());
        nimm("lizard");
        Assert.assertEquals(1, dungeon.player.getInventory().getInventory().size());
        nimm("234hjfkjvn932");
        Assert.assertEquals(1, dungeon.player.getInventory().getInventory().size());
        nimm("0xD47B34T"); // Dat Beat :3
        benutze("hfsejinefsi");
        Assert.assertEquals(1, dungeon.player.getInventory().getInventory().size());
        untersuche("");
        benutze("");
        untersuche("sdfghjklhgfd");
        Assert.assertEquals(1, dungeon.player.getInventory().getInventory().size());
        nimm("whiteboard");
        nimm("brecheisen"); //Darf nicht sein, da das Ding nicht in Raum 1 ist.
        Assert.assertEquals(1, dungeon.player.getInventory().getInventory().size());
        gehe("süd");
        nimm("truhe");
        untersuche("truhe");
        Assert.assertEquals(1, dungeon.player.getInventory().getInventory().size());
        hilfe();
        System.err.println("finished.");
    }

    /**
     * Der "durch die Räume gehen" Test. Das Programm geht einmal in jeden Raum und zurück und nimmt die Items mit, die Pflicht sind.
     *
     * @since <pre>Sep 30, 2014</pre>
     */
    @Test
    public void TestD() {
        System.out.println();
        System.out.println();
        System.err.println("-- DRG Test --");
        start();
        untersuche("raum");
        nimm("fackel");
        Assert.assertEquals(1, dungeon.player.getInventory().getInventory().size());
        System.out.println("Gehe in Raum 2");
        gehe("süd");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(1), dungeon.getCurrentRaum());
        nimm("feuerzeug");
        System.out.println("Gehe in Raum 1");
        gehe("nord");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(0), dungeon.getCurrentRaum());
        System.out.println("Gehe in Raum 2");
        gehe("süd");
        untersuche("raum");
        System.out.println("Gehe in Raum 3");
        gehe("west");
        benutze("feuerzeug");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(2), dungeon.getCurrentRaum());
        nimm("brecheisen");
        System.out.println("Gehe in Raum 2");
        gehe("ost");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(1), dungeon.getCurrentRaum());
        System.out.println("Gehe in Raum 3");
        gehe("west");
        benutze("fackel");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(2), dungeon.getCurrentRaum());
        System.out.println("Gehe in Raum 4");
        benutze("falltür");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(3), dungeon.getCurrentRaum());
        benutze("schalter");
        System.out.println("Gehe in Raum 1");
        gehe("ost");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(0), dungeon.getCurrentRaum());
        System.out.println("Gehe in Raum 4");
        gehe("west");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(3), dungeon.getCurrentRaum());
        System.err.println("finished.");
    }

    @Test
    public void TestE() {
        System.out.println();
        System.out.println();
        System.err.println("-- Questtest --");
        start();
        nimm("fackel");
        gehe("süd");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(1), dungeon.getCurrentRaum());
        Assert.assertEquals(1, dungeon.player.getInventory().getInventory().size());
        nimm("feuerzeug");
        Assert.assertEquals(2, dungeon.player.getInventory().getInventory().size());
        gehe("west");
        benutze("fackel");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(2), dungeon.getCurrentRaum());
        nimm("brecheisen");
        Assert.assertEquals(3, dungeon.player.getInventory().getInventory().size());
        benutze("falltür");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(3), dungeon.getCurrentRaum());
        rede("alter mann");
        gib("brecheisen");
        untersuche("inventar");
        Assert.assertEquals(true, Textie.isInInventory(dungeon.itemMap.get(Consts.SCHLÜSSEL)));
        Assert.assertEquals(false, Textie.isInInventory(dungeon.itemMap.get(Consts.BRECHEISEN)));
        Assert.assertEquals(3, dungeon.player.getInventory().getInventory().size());
        benutze("schalter");
        gehe("ost");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(0), dungeon.getCurrentRaum());
        System.err.println("finished.");
    }

    /**
     * ItemTest
     *
     * @since <pre>Sep 26, 2014</pre>
     */
    @Test
    public void TestB() {
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
        untersuche("inventar");
        untersuche("raum");
        Assert.assertEquals(1, dungeon.player.getInventory().getInventory().size());
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
        Assert.assertEquals(2, dungeon.player.getInventory().getInventory().size());
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
        Assert.assertEquals(3, dungeon.player.getInventory().getInventory().size());
        System.err.println("\nGehe in Raum 4\n");
        benutze("falltür");
        rede("alter mann");
        gib("brecheisen");
        Assert.assertEquals(true, Textie.isInInventory(dungeon.itemMap.get(Consts.SCHLÜSSEL)));
        Assert.assertEquals(false, Textie.isInInventory(dungeon.itemMap.get(Consts.BRECHEISEN)));
        Assert.assertEquals(3, dungeon.player.getInventory().getInventory().size());
        untersuche("inventar");
        rede("alter mann");
        rede("alter mann");
        untersuche("schalter");
        benutze("schalter");
        System.err.println("\nGehe in Raum 1\n");
        gehe("ost");
        untersuche("inventar");
        benutze("schlüssel");
        untersuche("truhe");
        untersuche("raum");
        System.err.println("finished.");
    }

    /*
   * Test Map:  1/4/5/6/7
   * Test Quest: Brief übergabe mit den neuen Items
   */
    @Test
    public void testRaum4Bis7() {
        start();
        Assert.assertEquals(dungeon.raums.get(0), dungeon.getCurrentRaum());
        benutze("schalter");
        gehe("west");
        Assert.assertEquals(dungeon.raums.get(3), dungeon.getCurrentRaum());
        gehe("west");
        Assert.assertEquals(dungeon.raums.get(4), dungeon.getCurrentRaum());
        rede("NPC1"); // richtiger NPC Name muss eingetragen werden
//        Assert.assertEquals(true, Textie.isInInventory(dungeon.itemMap.get(Consts.BRIEF)));
        untersuche("brief");
        benutze("falltür");
        Assert.assertEquals(dungeon.raums.get(5), dungeon.getCurrentRaum());
        untersuche("truhe");
        nimm("axt");
        untersuche("inventar");
        Assert.assertEquals(2, dungeon.player.getInventory().getInventory().size());
        benutze("axt");
        gehe("ost");
        Assert.assertEquals(dungeon.raums.get(6), dungeon.getCurrentRaum());
        rede("NPC2"); //richtiger NPC Name muss eingetragen werden
        untersuche("BehlohnungsItem"); // auch hier BehlohnungsItem ändern
        untersuche("inventar");
        Assert.assertEquals(3, dungeon.player.getInventory().getInventory().size());
        benutze("schalter");
        gehe("süd");
        Assert.assertEquals(dungeon.raums.get(3), dungeon.getCurrentRaum());
        System.out.println("Ende.");
    }
    /* Karten Test
     *Spieler läuft einmal durch den kompletten Dungeon und benutzt dabei Karte.
     */
    @Test
    public void testKarte() {
        start();
        nimm("Fackel");
        benutze("schalter");
        Assert.assertEquals("Du hörst ein Rumpeln, als du den Schalter drückst.", Textie.lastPrintedText);
        gehe("west");
        nimm("karte");
        benutze("karte");
        Assert.assertEquals("[Raum 1]--(WEST)--",Textie.lastPrintedText);
        gehe("ost");
        benutze("Karte");
        Assert.assertEquals("[Raum 1]--(WEST)--[Raum 4]--(OST)--",Textie.lastPrintedText);
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
    }





    /* SUBFUNKTIONEN */

    /**
     * Lässt den Spieler gehen.
     *
     * @param richtung
     * @return gibt den Test weiter.
     */






    private TextieTest gehe(String richtung) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Textie.executeCommand(new String[]{Command.GEHE, richtung}, new String[]{richtung});
        return this;
    }

    private TextieTest nimm(String text) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Textie.executeCommand(new String[]{Command.NIMM, text}, new String[]{text});
        return this;
    }

    private TextieTest start() {

        Runnable runGame = new Runnable() {
            @Override
            public void run() {
                do {
                    dungeon.runGame(false);
                } while (!Dungeon.getDungeon().player.isAlive());
            }
        };

        Thread thread = new Thread(runGame);
        thread.start();

        return this;
    }

    private TextieTest benutze(String item) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Textie.executeCommand(new String[]{Command.BENUTZE, item}, new String[]{item});
        return this;
    }

    private TextieTest untersuche(String item) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Textie.executeCommand(new String[]{Command.UNTERSUCHE, item}, new String[]{item});
        return this;
    }

    private TextieTest rede(String human) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Textie.executeCommand(new String[]{Command.REDE, human}, new String[]{human});
        return this;
    }

    private TextieTest gib(String item) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Textie.executeCommand(new String[]{Command.GIB, item}, new String[]{item});
        return this;
    }

    private TextieTest vernichte(String item) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Textie.executeCommand(new String[]{Command.VERNICHTE, item}, new String[]{item});
        return this;
    }

    private TextieTest hilfe() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Textie.executeCommand(new String[]{Command.HILFE}, new String[]{});
        return this;
    }

} 
