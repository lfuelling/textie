package test.de.micromata.azubi;

import de.micromata.azubi.Command;
import de.micromata.azubi.Dungeon;
import org.junit.Test;
import static org.junit.Assert.fail;

/**
 * Textie Tester.
 *
 * @author Lukas F&uuml;lling
 * @since <pre>Sep 25, 2014</pre>
 * @version 1.1
 */
public class TextieTest { 

    Dungeon dungeon;

    @Test
    public void testMain() throws Exception {
        dungeon = Dungeon.getDungeon();
        System.out.println("Starting Speedrun\n");
        start();
        speedrun();
        /*
        System.out.println("Starting Questmode");
        start();
        quest();
        System.out.println("Starting Item Test");
        items();
         */
    }

    /* TESTDURCHGÄNGE */

    private TextieTest speedrun () {
        nimm("fackel");
        gehe("süd");
        nimm("schwert");
        benutze("schwert");
        return this;
    }

    private TextieTest quest () {
        nimm("fackel");
        gehe("süd");
        nimm("feuerzeug");
        gehe("west");
        benutze("fackel");
        nimm("brecheisen");
        benutze("falltür");
        rede("alter mann");
        gib("brecheisen");
        untersuche("inventar");
        if(dungeon.inventory.isInInventory(dungeon.itemMap.get("SCHLÜSSEL"))) {
            return this;
        } else {
            fail("Schlüssel nicht im Inventar");
        }
        return this;
    }

    private TextieTest items () {
        untersuche("raum");
        nimm("fackel");
        untersuche("fackel");
        benutze("fackel");
        nimm("truhe");
        untersuche("truhe");
        benutze("truhe");
        return this;
    }

    /* SUBFUNKTIONEN */

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
          } while (!Dungeon.getDungeon().inventory.isAlive());
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

} 
