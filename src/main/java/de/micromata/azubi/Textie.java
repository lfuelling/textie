
package de.micromata.azubi;

/*
 *  TEXTIE
 */

public class Textie {
    public static boolean diag = false;
    public static void main(String[] args) {
        Dungeon.getDungeon().runGame(true);
        System.exit(0);

        if(args[0].equals("--diag")){
            diag = true;
        }

    }
}
