package test.de.micromata.azubi;

import de.micromata.azubi.Command;
import de.micromata.azubi.Dungeon;
import org.junit.*;

import static org.junit.Assert.fail;

/**
 * Textie Tester.
 *
 * @author Lukas F&uuml;lling
 * @version 1.1
 * @since <pre>Sep 25, 2014</pre>
 */
public class TextieTest {
    private static Dungeon dungeon;

    @Before
    public void testBefore() throws Exception {
        dungeon = Dungeon.getDungeon();
        start();
    }

    @After
    public void testAfter() {
        dungeon.setDungeon(null);
    }


    /* TESTDURCHGÄNGE */

    /**
     * Speedrun.
     *
     * @since <pre>Sep 26, 2014</pre>
     */
    @Test
    public void TestA() {
        nimm("fackel");
        Assert.assertEquals(1, dungeon.inventory.getInventory().size());
        gehe("süd");
        nimm("schwert");
        Assert.assertEquals(2, dungeon.inventory.getInventory().size());
        benutze("schwert");
        System.out.print("\n\n");
    }

    /**
     * ItemTest
     *
     * @since <pre>Sep 26, 2014</pre>
     */
    @Test
    public void TestB() {
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

} 
