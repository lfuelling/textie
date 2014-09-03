import java.io.Console;

public class Raum3 {
  public static void start(int[] inventory) {
    int[] umgebung = new int[4];
    umgebung[0] = 3; // ENTE
    umgebung[1] = 4; // BRECHEISEN
    umgebung[2] = 11; // WHITEBOARD
    umgebung[3] = 12; // FALLTÜR
    int vorhanden = 4; // Höchster ZÄHLERWERT des umgebung-Arrays + 1

    boolean finished = false;
    System.out.println("Ein Windstoß sorgt dafür, dass die Fackel ausgeht.");
    System.out.println("Es ist zu dunkel, um etwas zu sehen. Ein seltsamer Geruch liegt in der Luft.");
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
              case "feuerzeug":
                if(Textie.findInInventory(inventory, 1) != -128 && Textie.findInInventory(inventory, 6) != -128) { // Die 1 steht für die Fackel, die 6 für das Feuerzeug. Siehe "Textie.java/getObjectID"
                  System.out.println("Der Raum ist hell erleuchtet.");
                  break;
                }
                else if(Textie.findInInventory(inventory, 6) != -128) {
                  System.out.println("Du betrachtest das Feuerzeug.");
                  break;
                }
                else {
                  System.out.println("Du hast kein Feuerzeug.");
                  break;
                }
              case "quietscheente":
                if(Textie.findInInventory(inventory, 3) != -128) {
                  System.out.println("Die Ente schaut dich vorwurfsvoll an und quietscht leise, als du sie zusammendrückst.");
                  break;
                }
                else {
                  System.out.println("Du hast keine Quietscheente.");
                  break;
                }

              case "brecheisen":
                if(Textie.findInInventory(inventory, 4) != -128) {
                  System.out.println("Du kratzt dich mit dem Brecheisen am Kopf.");
                  break;
                }
                else {
                  System.out.println("Du hast kein Brecheisen.");
                  break;
                }

              case "whiteboard":
                System.out.println("Das fasse ich bestimmt nicht an.");
                break;

              case "falltür":
                if(Textie.findInInventory(inventory, 4) != -128 || Textie.findInInventory(inventory, 7) != -128) {
                  System.out.println("Du schlüpfst durch die Falltür in den darunterliegenden Raum.");
                  break;
                }
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
              case "feuerzeug":
                if(Textie.findInInventory(inventory, 1) != -128 && Textie.findInInventory(inventory, 6) != -128) { // Die 1 steht für die Fackel, die 6 für das Feuerzeug. Siehe "Textie.java/getObjectID"
                  System.out.println("Du schaust auf das Feuerzeug und dann auf die Fackel. Irgendwas muss man da doch machen können.");
                  break;
                }
                else if(Textie.findInInventory(inventory, 6) != -128) {
                  System.out.println("Du betrachtest das Feuerzeug. Es wirkt zuverlässig.");
                  break;
                }
                else {
                  System.out.println("Du hast kein Feuerzeug.");
                  break;
                }
              case "quietscheente":
                if(Textie.findInInventory(inventory, 3) != -128) {
                  System.out.println("Die Ente schaut dich vorwurfsvoll an.");
                  break;
                }
                else if (Textie.findInRoom(umgebung, 1, vorhanden) != -128){
                  System.out.println("Da liegt eine Quietscheente.");
                  break;
                }
                else {
                  System.out.println("Da liegt keine Quietscheente.");
                  break;
                }

              case "brecheisen":
                if(Textie.findInInventory(inventory, 4) != -128) {
                  System.out.println("Du kratzt dich mit dem Brecheisen am Kopf.");
                  break;
                }
                else {
                  System.out.println("Du hast kein Brecheisen.");
                  break;
                }

              case "whiteboard":
                System.out.println("Das fasse ich bestimmt nicht an.");
                break;

              case "falltür":
                if(Textie.findInInventory(inventory, 4) != -128 || Textie.findInInventory(inventory, 7) != -128) {
                  System.out.println("Du schlüpfst durch die Falltür in den darunterliegenden Raum.");
                  finished = true;
                  break;
                }
            }
          }
          else {
            System.out.println("Was soll benutzt werden?");
            break;
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
                System.out.println("Du bist gegen die Wand gelaufen.");
              case "ost":
                System.out.println("Du willst nicht zurück.");
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
