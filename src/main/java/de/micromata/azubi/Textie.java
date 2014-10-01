
package de.micromata.azubi;

/*
 *  TEXTIE
 */

public class Textie {
    public static void main(String[] args) {
      Dungeon dungeon = Dungeon.getDungeon();
      dungeon.init();
      dungeon.runGame(true);
    }


}
