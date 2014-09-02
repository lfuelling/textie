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
  static String playerName = "Fremder";
  public static final int STATE = 0;
  public static final int DEAD = 1;

  // Gegenstände
  public static final int FACKEL = 1;
  public static final int HANDTUCH = 2;
  public static final int QUIETSCHEENTE = 3;
  public static final int BRECHEISEN = 4;
  public static final int SCHWERT = 5;
  public static final int FEUERZEUG = 6;
  public static final int SCHLUESSEL = 7;
  public static final int STEIN = 8;

  public static void main (String[] args) {
    Textie.showIntro();
    Textie.runGame();
  }

  public static void runGame () {
    Raum1.start(inventory);
    if (inventory[STATE] != DEAD) {
      Raum2.start(inventory);
        if (inventory[STATE] != DEAD) {
          Raum3.start(inventory);
            if (inventory[STATE] != DEAD) {
              Textie.runGame();
            }
            else if (inventory[STATE] == DEAD) {
              System.out.println("Du bist gestorben.");
            }
        }
        else if (inventory[STATE] == DEAD) {
          Textie.ende();
        }
    }
    else if (inventory[STATE] == DEAD) {
      System.out.println("Du bist gestorben.");
    }
  }

  public static void ende() {
    //TODO
  }

  public static String getObjectName (int id) {
    switch(id) {
      case FACKEL:
        return "Fackel";

      case HANDTUCH:
        return "Handtuch";

      case QUIETSCHEENTE:
        return "Quietscheente";

      case BRECHEISEN:
        return "Brecheisen";

      case SCHWERT:
        return "Schwert";

      case FEUERZEUG:
        return "Feuerzeug";

      case SCHLUESSEL:
        return "Schlüssel";

      case STEIN:
        return "Stein";

      default:
        return "Unbekannt";
    }
  }

  public static boolean addToInventory (int objectID) {
    for (int i = 1; i<6; i++) {
      if (inventory[i] == 0) {
        inventory[i] = objectID;
        return true;
      }
    }
    return false;
  }

  public static boolean removeFromInventory (int objectID) {
    for (int i = 1; i<6; i++) {
      if (inventory[i] == objectID) {
        inventory[i] = 0;
        return true;
      }
    }
    return false;
  }

  public static void listInventory () {
    System.out.println("In deiner Tasche befindet sich:");
    for (int i = 1; i<6; i++) {
      String objectName = getObjectName(inventory[i]);
      System.out.println(objectName);
    }
  }

  public static void showIntro() {
    System.out.println("Willkommen " + playerName + ".");
    System.out.println("Falls du Hilfe bei der Bedienung brauchst, tippe \'hilfe\' ein.");
    Console console = System.console();
    playerName = console.readLine("Wie ist dein Name? ");
    if (playerName == null || playerName == ""){
      playerName = "Fremder";
    }
  }
}
