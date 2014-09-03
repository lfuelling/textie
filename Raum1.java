import java.io.Console;

public class Raum1 {
  public static void start(int[] inventory) {
    int[] umgebung = new int[5];
    umgebung[0] = 1; // FACKEL
    umgebung[1] = 2; // HANDTUCH
    umgebung[2] = 9; // TRUHE
    umgebung[3] = 10; // SCHALTER
    int vorhanden = 4; // Höchster ZÄHLERWERT des umgebung-Arrays + 1

    boolean finished = false;
    System.out.println("Du befindest dich in einem dunklen Raum. Nach einiger Zeit gewöhnen sich deine Augen an die Dunkelheit.");
    do{
      Console console = System.console();
      String command = console.readLine("Was willst du tun? ");
      String[] parsed_command = Textie.parseInput(command);
      int object_to_use = 0;
      int count = 0;
      for (int x = 0; x < parsed_command.length; x++) {
        if(parsed_command[x] != null) {
            count++;
        }
      }
      if(count == 2){
        object_to_use = Textie.getObjectID(parsed_command[1].toUpperCase());
      }

      switch(parsed_command[0]){
        case "hilfe":
          System.out.println("Mögliche Befehle:");
          System.out.println("\thilfe -> Zeigt diese Hilfe");
          System.out.println("\tnimm [gegenstand] -> Gegenstand zum Inventar hinzufügen");
          System.out.println("\tbenutze [gegenstand] -> Gegenstand benutzen");
          System.out.println("\tuntersuche [gegenstand/raum/inventar] -> Gegenstand, Raum oder Inventar untersuchen");
          System.out.println("\tvernichte [gegenstand] -> Gegenstand aus dem Inventar löschen");
          break;
        case "nimm":
          if(Textie.addToInventory(object_to_use, umgebung, vorhanden)){
            System.out.println(parsed_command[1] + " zum Inventar hinzugefügt.");
            break;
          }
          else {
            System.out.println("Entweder das Objekt gibt es nicht, oder dein Inventar ist voll.");
            break;
          }
        case "benutze":
          if (count == 2){
            switch(parsed_command[1]){
              case "fackel":
                if(Textie.findInInventory(inventory, 1) != -128 && Textie.findInInventory(inventory, 6) != -128) { // Die 1 steht für die Fackel, die 6 für das Feuerzeug. Siehe "Textie.java/getObjectID"
                  System.out.println("Der Raum ist hell erleuchtet.");
                  break;
                }
                else if(Textie.findInInventory(inventory, 1) != -128) {
                  System.out.println("Du betrachtest die Fackel. Wie kann man die wohl anzünden?");
                  break;
                }
                else {
                  System.out.println("Du hast keine Fackel.");
                  break;
                }

              case "handtuch":
                if(Textie.findInInventory(inventory, 2) != -128) {
                  System.out.println("Du bist trocken. Das Handtuch ist schön flauschig.");
                  break;
                }
                else {
                  System.out.println("Du hast kein Handtuch.");
                  break;
                }

              case "schalter":
                System.out.println("Du hörst ein rumpeln, als du den Schalter betätigst. Nichts geschieht.");
            }
          }
          else {
            System.out.println("Was soll benutzt werden?");
            break;
          }
          break;
        case "untersuche":
          if (count == 2){
            switch(parsed_command[1]){
              case "raum":
                Textie.listRoom(umgebung, vorhanden);
                break;

              case "inventar":
                Textie.listInventory(inventory);
                break;

              case "fackel":
                if(Textie.findInInventory(inventory, 1) != -128) {
                  System.out.println("Du betrachtest die Fackel. Wie kann man die wohl anzünden?");
                }
                else if(Textie.findInRoom(umgebung, 1, vorhanden) != -128) {
                  System.out.println("Da liegt eine Fackel.");
                }
                else {
                  System.out.println("Hä?");
                }
                break;

              case "handtuch":
                if(Textie.findInInventory(inventory, 2) != -128) {
                  System.out.println("Du betrachtest das Handtuch. Es sieht sehr flauschig aus.");
                }
                else if(Textie.findInRoom(umgebung, 2, vorhanden) != -128) {
                  System.out.println("Da liegt ein Handtuch.");
                }
                else {
                  System.out.println("Hä?");
                }
                break;

              case "truhe":
                if(Textie.findInRoom(umgebung, 9, vorhanden) != -128) {
                  System.out.println("Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.");
                }
                else {
                  System.out.println("Hä?");
                }
                break;

              case "schalter":
                if(Textie.findInRoom(umgebung, 10, vorhanden) != -128) {
                  System.out.println("Da ist ein kleiner Schalter an der Wand.");
                }
                else {
                  System.out.println("Hä?");
                }
                break;
            }
          }
          else {
            System.out.println("Was soll untersucht werden?");
          }
          break;
        case "vernichte":
          if (count == 2) {
            if(Textie.removeFromInventory(object_to_use)){
              System.out.println(parsed_command[1] + " vernichtet.");
              break;
            }
            else {
              System.out.println("Entweder das Objekt gibt es nicht, oder es ist nicht im Inventar.");
              break;
            }
          }
          else {
            System.out.println("Was soll vernichtet werden?");
          }
          break;

        case "gehe":
          if (count == 2){
            switch(parsed_command[1]){
              case "nord":
                System.out.println("Du bist gegen die Wand gelaufen.");
                break;
              case "süd":
                if(Textie.findInInventory(inventory, 1) != -128){
                  System.out.println("Da ist eine Tür. Du öffnest sie und gehst die Steintreppe dahinter hoch.");
                  finished = true;
                  break;
                }
                else {
                  System.out.println("Da ist eine Tür. Du gehst nicht hinaus, da du das Gefühl hast, noch nicht alles erledigt zu haben.");
                  break;
                }
              case "ost":
                System.out.println("Du bist gegen die Wand gelaufen.");
                break;
              case "west":
                System.out.println("Du bist gegen die Wand gelaufen.");
                break;
            }
          }
          else{
            System.out.println("Wohin gehen?");
          }
          break;

        default:
          System.out.println("Unbekannter Befehl: " + parsed_command[0]);
          break;
      }
    }while(finished == false);
  }
}
