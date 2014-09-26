package test.de.micromata.azubi;

import de.micromata.azubi.Command;
import de.micromata.azubi.Dungeon;
import org.junit.Test;

/** 
* Textie Tester. 
* 
* @author Lukas Fülling
* @since <pre>Sep 25, 2014</pre> 
* @version 1.0 
*/ 
public class TextieTest { 

    Dungeon dungeon;

    @Test
    public void testMain() throws Exception {
        dungeon = Dungeon.getDungeon();
        start().nimm("Fackel").gehe("süd").gehe("west");
    }

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
              //Thread.sleep(1000);
          } while (!Dungeon.getDungeon().inventory.isAlive());
        }
      };

      Thread thread = new Thread(runGame);
      thread.start();

      return this;
    }

} 
