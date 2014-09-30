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
        System.err.println("Speedrun Test");
        start();
        nimm("fackel");
        Assert.assertEquals(1, dungeon.inventory.getInventory().size());
        gehe("süd");
        nimm("schwert");
        Assert.assertEquals(2, dungeon.inventory.getInventory().size());
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
        System.err.println("QS Test, hier soll komischer Kram stehen.");
        start();
        untersuche("raum");
        nimm("fackel");
        Assert.assertEquals(1, dungeon.inventory.getInventory().size());
        nimm("");
        Assert.assertEquals(1, dungeon.inventory.getInventory().size());
        nimm("lizard");
        Assert.assertEquals(1, dungeon.inventory.getInventory().size());
        nimm("234hjfkjvn932");
        Assert.assertEquals(1, dungeon.inventory.getInventory().size());
        nimm("0xD47B34T"); // Dat Beat :3
        benutze("hfsejinefsi");
        Assert.assertEquals(1, dungeon.inventory.getInventory().size());
        untersuche("");
        benutze("");
        untersuche("sdfghjklhgfd");
        Assert.assertEquals(1, dungeon.inventory.getInventory().size());
        nimm("whiteboard");
        nimm("brecheisen"); //Darf nicht sein, da das Ding nicht in Raum 1 ist.
        Assert.assertEquals(1, dungeon.inventory.getInventory().size());
        gehe("süd");
        nimm("truhe");
        untersuche("truhe");
        Assert.assertEquals(1, dungeon.inventory.getInventory().size());
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
        System.err.println("DRG Test.");
        start();
        untersuche("raum");
        nimm("fackel");
        Assert.assertEquals(1, dungeon.inventory.getInventory().size());
        System.out.println("Gehe in Raum 2");
        gehe("süd");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(1), dungeon.currentRaum);
        nimm("feuerzeug");
        System.out.println("Gehe in Raum 1");
        gehe("nord");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(0), dungeon.currentRaum);
        System.out.println("Gehe in Raum 2");
        gehe("süd");
        untersuche("raum");
        System.out.println("Gehe in Raum 3");
        gehe("west");
        benutze("feuerzeug");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(2), dungeon.currentRaum);
        nimm("brecheisen");
        System.out.println("Gehe in Raum 2");
        gehe("ost");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(1), dungeon.currentRaum);
        System.out.println("Gehe in Raum 3");
        gehe("west");
        benutze("fackel");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(2), dungeon.currentRaum);
        System.out.println("Gehe in Raum 4");
        benutze("falltür");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(3), dungeon.currentRaum);
        benutze("schalter");
        System.out.println("Gehe in Raum 1");
        gehe("ost");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(0), dungeon.currentRaum);
        System.out.println("Gehe in Raum 4");
        gehe("west");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(3), dungeon.currentRaum);
        System.err.println("finished.");
    }

    @Test
    public void TestE() {
        System.out.println();
        System.out.println();
        System.err.println("Questtest");
        start();
        nimm("fackel");
        gehe("süd");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(1), dungeon.currentRaum);
        Assert.assertEquals(1, dungeon.inventory.getInventory().size());
        nimm("feuerzeug");
        Assert.assertEquals(2, dungeon.inventory.getInventory().size());
        gehe("west");
        benutze("fackel")
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(2), dungeon.currentRaum);
        nimm("brecheisen");
        Assert.assertEquals(3, dungeon.inventory.getInventory().size());
        benutze("falltür");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(3), dungeon.currentRaum);
        rede("alter mann");
        gib("brecheisen");
        untersuche("inventar");
        Assert.assertEquals(true, dungeon.inventory.isInInventory(dungeon.itemMap.get(Consts.SCHLÜSSEL)));
        Assert.assertEquals(false, dungeon.inventory.isInInventory(dungeon.itemMap.get(Consts.BRECHEISEN)));
        Assert.assertEquals(3, dungeon.inventory.getInventory().size());
        benutze("schalter");
        gehe("ost");
        untersuche("raum");
        Assert.assertEquals(dungeon.raums.get(0), dungeon.currentRaum);
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
        System.err.println("Item Test.");
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
        Assert.assertEquals(1, dungeon.inventory.getInventory().size());
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
        Assert.assertEquals(2, dungeon.inventory.getInventory().size());
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
        Assert.assertEquals(3, dungeon.inventory.getInventory().size());
        System.err.println("\nGehe in Raum 4\n");
        benutze("falltür");
        rede("alter mann");
        gib("brecheisen");
        Assert.assertEquals(true, dungeon.inventory.isInInventory(dungeon.itemMap.get(Consts.SCHLÜSSEL)));
        Assert.assertEquals(false, dungeon.inventory.isInInventory(dungeon.itemMap.get(Consts.BRECHEISEN)));
        Assert.assertEquals(3, dungeon.inventory.getInventory().size());
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
        dungeon.executeCommand(new String[]{Command.GEHE, richtung}, new String[]{richtung});
        return this;
    }

    private TextieTest nimm(String text) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dungeon.executeCommand(new String[]{Command.NIMM, text}, new String[]{text});
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
        dungeon.executeCommand(new String[]{Command.BENUTZE, item}, new String[]{item});
        return this;
    }

    private TextieTest untersuche(String item) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dungeon.executeCommand(new String[]{Command.UNTERSUCHE, item}, new String[]{item});
        return this;
    }

    private TextieTest rede(String human) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dungeon.executeCommand(new String[]{Command.REDE, human}, new String[]{human});
        return this;
    }

    private TextieTest gib(String item) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dungeon.executeCommand(new String[]{Command.GIB, item}, new String[]{item});
        return this;
    }

    private TextieTest vernichte(String item) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dungeon.executeCommand(new String[]{Command.VERNICHTE, item}, new String[]{item});
        return this;
    }

    private TextieTest hilfe() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dungeon.executeCommand(new String[]{Command.HILFE}, new String[]{});
        return this;
    }

} 
