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
  static int[] umgebung = new int[4];
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

    if (args.length == 1){
      int raumNummer = Integer.parseInt(args[0]);

      switch(raumNummer){
          case 1:
            Raum1.start(inventory);
            break;

          case 2:
            inventory[1] = FACKEL;
            Raum2.start(inventory);
            break;

          case 3:
            inventory[1] = FACKEL;
            inventory[2] = SCHLUESSEL;
            Raum3.start(inventory);
            break;

          default:
            System.out.println("Gebe eine Zahl von 1-3 ein.");
            break;
      }
      return;
    }

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
        return "Kein Objekt";
    }
  }




  public static int getObjectID (String objectName) {
    switch(objectName) {
      case "FACKEL":
        return FACKEL;

      case "HANDTUCH":
        return HANDTUCH;

      case "QUIETSCHEENTE":
        return QUIETSCHEENTE;

      case "BRECHEISEN":
        return BRECHEISEN;

      case "SCHWERT":
        return SCHWERT;

      case "FEUERZEUG":
        return FEUERZEUG;

      case "SCHLÜSSEL":
        return SCHLUESSEL;

      case "STEIN":
        return STEIN;

      default:
        return 0;
    }
  }


 public static boolean addToInventory(int ObjectID, int[] umgebung)
    for (int i = 1; i<6; i++) {
      if (inventory[i] == 0) {
        inventory[i] = objectID;
        int objektInUmgebung = searchObject(objectID, umgebung)
        umgebung[]
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

  public static String[] parseInput(String command) {
    String[] result = command.split(" ", 2);
    return result;
  }
  public static int searchObject(int ObjectID, int umgebung){
    for(i=0; i<4; i++){
      if(umgebung[i]==ObjectID){
        return i;
      }
    }
  }
}
