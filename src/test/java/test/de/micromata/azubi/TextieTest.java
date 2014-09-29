package test.de.micromata.azubi;

import de.micromata.azubi.Command;
import de.micromata.azubi.Dungeon;
import org.junit.*;

import static org.junit.Assert.fail;

/**
 * Textie Tester.
 *
 * @author Lukas F&uuml;lling
 * @since <pre>Sep 25, 2014</pre>
 * @version 1.1
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
     * Speedrun
     *
     * @since <pre>Sep 26, 2014</pre>
     */
    @Test
    public void TestA () {
        nimm("fackel");
        Assert.assertEquals(1,dungeon.inventory.getInventory().size());
        gehe("süd");
        nimm("schwert");
        Assert.assertEquals(2,dungeon.inventory.getInventory().size());
        benutze("schwert");
        System.out.print("\n\n");
    }

    /**
     * ItemTest
     *
     * @since <pre>Sep 26, 2014</pre>
     */
    @Test
    public void TestB () {
        untersuche("raum");
        System.out.println("Was willst du tun?");
        nimm("fackel");
        System.out.println("Was willst du tun?");
        untersuche("fackel");
        System.out.println("Was willst du tun?");
        benutze("fackel");
        System.out.println("Was willst du tun?");
        nimm("truhe");
        System.out.println("Was willst du tun?");
        untersuche("truhe");
        System.out.println("Was willst du tun?");
        benutze("truhe");
        System.out.println("Was willst du tun?");
        untersuche("Schalter");
        System.out.println("Was willst du tun?");
        benutze("Schalter");
        System.out.println("Was willst du tun?");
        nimm("handtuch");
        System.out.println("Was willst du tun?");
        untersuche("Handtuch");
        System.out.println("Was willst du tun?");
        benutze("handtuch");
        System.out.println("Was willst du tun?");
        untersuche("inventar");
        System.out.println("Was willst du tun?");
        untersuche("raum");
        System.out.println("Was willst du tun?");
        vernichte("handtuch");
        System.out.println("Was willst du tun?");
        untersuche("inventar");
        System.out.println("Was willst du tun?");
        untersuche("raum");
        System.out.println("Was willst du tun?");
        Assert.assertEquals(1,dungeon.inventory.getInventory().size());
        System.err.println("\nGehe in Raum 2\n");
        System.out.println("Was willst du tun?");
        gehe("süd");
        System.out.println("Was willst du tun?");
        untersuche("raum");
        System.out.println("Was willst du tun?");
        nimm("stein");
        System.out.println("Was willst du tun?");
        untersuche("stein");
        System.out.println("Was willst du tun?");
        benutze("stein");
        System.out.println("Was willst du tun?");
        untersuche("raum");
        System.out.println("Was willst du tun?");
        untersuche("inventar");
        System.out.println("Was willst du tun?");
        vernichte("stein");
        System.out.println("Was willst du tun?");
        untersuche("raum");
        System.out.println("Was willst du tun?");
        untersuche("inventar");
        System.out.println("Was willst du tun?");
        nimm("feuerzeug");
        System.out.println("Was willst du tun?");
        untersuche("feuerzeug");
        System.out.println("Was willst du tun?");
        benutze("feuerzeug");
        System.out.println("Was willst du tun?");
        untersuche("inventar");
        System.out.println("Was willst du tun?");
        untersuche("raum");
        System.out.println("Was willst du tun?");
        nimm("schwert");
        System.out.println("Was willst du tun?");
        untersuche("schwert");
        System.out.println("Was willst du tun?");
        vernichte("schwert");
        System.out.println("Was willst du tun?");
        Assert.assertEquals(2,dungeon.inventory.getInventory().size());
        System.err.println("\nGehe in Raum 3\n");
        gehe("west");
        System.out.println("Was willst du tun?");
        untersuche("raum");
        System.out.println("Was willst du tun?");
        untersuche("inventar");
        System.out.println("Was willst du tun?");
        benutze("fackel");
        System.out.println("Was willst du tun?");
        untersuche("raum");
        System.out.println("Was willst du tun?");
        untersuche("inventar");
        System.out.println("Was willst du tun?");
        nimm("brecheisen");
        System.out.println("Was willst du tun?");
        untersuche("brecheisen");
        System.out.println("Was willst du tun?");
        benutze("brecheisen");
        System.out.println("Was willst du tun?");
        untersuche("falltür");
        System.out.println("Was willst du tun?");
        untersuche("whiteboard");
        System.out.println("Was willst du tun?");
        benutze("whiteboard");
        System.out.println("Was willst du tun?");
        nimm("quietscheente");
        System.out.println("Was willst du tun?");
        untersuche("quietscheente");
        System.out.println("Was willst du tun?");
        benutze("quietscheente");
        System.out.println("Was willst du tun?");
        vernichte("quietscheente");
        System.out.println("Was willst du tun?");
        Assert.assertEquals(3,dungeon.inventory.getInventory().size());
        System.err.println("\nGehe in Raum 4\n");
        benutze("falltür");
        System.out.println("Was willst du tun?");
        rede("alter mann");
        System.out.println("Was willst du tun?");
        gib("brecheisen");
        System.out.println("Was willst du tun?");
        Assert.assertEquals(3,dungeon.inventory.getInventory().size());
        untersuche("inventar");
        System.out.println("Was willst du tun?");
        rede("alter mann");
        System.out.println("Was willst du tun?");
        rede("alter mann");
        System.out.println("Was willst du tun?");
        untersuche("schalter");
        System.out.println("Was willst du tun?");
        benutze("schalter");
        System.out.println("Was willst du tun?");
        System.err.println("\nGehe in Raum 1\n");
        gehe("ost");
        System.out.println("Was willst du tun?");
        untersuche("inventar");
        System.out.println("Was willst du tun?");
        benutze("schlüssel");
        System.out.println("Was willst du tun?");
        untersuche("truhe");
        System.out.println("Was willst du tun?");
        untersuche("raum");
    }

    /* SUBFUNKTIONEN */

    /**
     * Lässt den Spieler gehen.
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
