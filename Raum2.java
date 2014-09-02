import java.io.Console;

public class Raum2 {
  public static void start(int[] inventory) {
    umgebung[0] = SCHWERT;
    umgebung[1] = FEUERZEUG;
    umgebung[2] = SCHLUESSEL;
    umgebung[3] = STEIN;
    Textie.listInventory();
    System.out.println("Du kommst in einen weiteren dunklen Raum.");
    do{
      Console console = System.console();
      String command = console.readLine("Was willst du tun? ");
      parsed_command = parseInput(command);
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
