import java.io.Console;

public class Raum1 {
  public static void start(int[] inventory) {
    int[] umgebung = new int[3];
    umgebung[0] = 1; // FACKEL
    umgebung[1] = 2; // HANDTUCH
    umgebung[2] = 3; // QUIETSCHEENTE
    int vorhanden = 2; // Höchster ZÄHLERWERT des umgebung-Arrays

    boolean finished = false;
    System.out.println("Du befindest dich in einem dunklen Raum.");
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
          }
          else {
            System.out.println("Entweder das Objekt gibt es nicht, oder dein Inventar ist voll.");
          }
        case "benutze":


      }
    }while(finished == false);
  }
}
