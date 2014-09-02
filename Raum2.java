import java.io.Console;

public class Raum2 {
  public static void start(int[] inventory) {
    int[] umgebung = new int[4];
    umgebung[0] = 5;
    umgebung[1] = 6;
    umgebung[2] = 7;
    umgebung[3] = 8;
    int vorhanden = 3; // Höchster ZÄHLERWERT des umgebung-Arrays

    //    <DEBUG>
    Textie.listInventory();
    //    </DEBUG>

    boolean finished = false;
    System.out.println("Du kommst in einen weiteren dunklen Raum.");
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

      }
    }while(finished == false);
  }
}
