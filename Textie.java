/*
 *  TEXTIE
 *
 *  Wenn man einen Raum startet (RaumX.start();), muss dieser Raum ein Array zurück geben.
 *  Dieses Array beinhaltet im ersten Feld, ob das Level beendet wurde, und in den übrigen fünf Feldern sind die ID's der Items enthalten, die man mit sich trägt.
 *  Wenn der Raum abgeschlossen wurde, muss das erste Feld "0" enthalten und wenn der Spieler tot ist, muss dieses Feld 1 enthalten.
 */

import java.io.Console;

public class Textie {
  static int[] inventory = new int[6];

  public static void main (String[] args) {
    Textie.runGame();
  }

  public static void runGame () {
    Raum1.start(inventory);
    if (inventory[0] == 0) {
      Raum2.start(inventory);
        if (inventory[0] == 0) {
          Raum3.start(inventory);
            if (inventory[0] == 0) {
              Textie.runGame();
            }
            else if (inventory[0] == 1) {
              System.out.println("Du bist gestorben.");
            }
        }
        else if (inventory[0] == 1) {
          Textie.ende();
        }
    }
    else if (inventory[0] == 1) {
      System.out.println("Du bist gestorben.");
    }
  }

  public static void ende() {
    //TODO
  }
}
