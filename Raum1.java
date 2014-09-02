import java.io.Console;

public class Raum1 {
  public static void start(int[] inventory) {
    boolean finished = false;
    System.out.println("Du befindest dich in einem dunklen Raum.");
    do{
      Console console = System.console();
      String command = console.readLine("Was willst du tun? ");
      String[] parsed_command = Textie.parseInput(command);
      int object_to_use = 0;
      if(parsed_command[1] != null || parsed_command[1] != ""){
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
          if(Textie.addToInventory(object_to_use)){
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
