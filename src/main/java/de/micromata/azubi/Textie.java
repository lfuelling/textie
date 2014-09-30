
package de.micromata.azubi;

/*
 *  TEXTIE
 */

public class Textie {
    public static boolean diag;

    public static void main(String[] args) {

        try {
            if(args[0].equals("--diag")){
                diag = true;
            } else {
                diag = false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            diag = false;
        }

        Dungeon.getDungeon().runGame(true);
        System.exit(0);
    }
}
