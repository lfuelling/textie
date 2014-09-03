import java.io.Console;

public class Raum2 {
  public static void start(int[] inventory) {
    int[] umgebung = new int[4];
    umgebung[0] = 5;
    umgebung[1] = 6;
    umgebung[2] = 7;
    umgebung[3] = 8;
    int vorhanden = 4; // Höchster ZÄHLERWERT des umgebung-Arrays +1

    //    <DEBUG>
    Textie.listInventory(inventory);
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
          if(Textie.addToInventory(object_to_use, umgebung, vorhanden)){
            System.out.println(parsed_command[1] + " zum Inventar hinzugefügt.");
          }
          else {
            System.out.println("Entweder das Objekt gibt es nicht, oder dein Inventar ist voll.");
          }
          break;
        case "benutze":
          boolean objektVorhanden = false;
          for(int i=1; i<6; i++){
            if(inventory[i]==object_to_use){
              objektVorhanden = true;
              switch(object_to_use){
                case Textie.FACKEL:
                 boolean feuerzeugVorhanden = false;
                  for(int j=1; j<6; j++){
                    if(inventory[j]==Textie.FEUERZEUG){
                      feuerzeugVorhanden = true;
                      }
                    }
                    if(feuerzeugVorhanden==true){
                      System.out.println("Du zündest deine Fackel mit dem Feuerzeug an.");
                    }
                    else{
                      System.out.println("Du hast kein Feuerzeug.");
                    }
                    break;
                case Textie.HANDTUCH:
                  System.out.println("Du wischst dir den Angstschweiß von der Stirn.");
                  break;
                case Textie.SCHWERT:
                  System.out.println("Du stichst dir das Schwert zwischen die Rippen und stirbst.");
                  Textie.ende();
                  break;
                case Textie.SCHLUESSEL:
                  System.out.println("Hier gibt es nichts um den Schlüssel zu benutzen.");
                  break;
                case Textie.STEIN:
                  System.out.println("Hier gibt es nichts um den Stein zu benutzen.");
                  break;
                case Textie.FEUERZEUG:
                boolean fackelVorhanden = false;
                 for(int k=1; k<6; k++){
                   if(inventory[k]==Textie.FACKEL){
                     fackelVorhanden = true;
                     }
                   }
                   if(fackelVorhanden==true){
                     System.out.println("Du zündest deine Fackel mit dem Feuerzeug an.");
                   }
                   else{
                     System.out.println("Du hast keine Fackel.");
                   }
                   break;

              }
            }
          }
          if(objektVorhanden==false){
            System.out.println("Du hast " + Textie.getObjectName(object_to_use) + " nicht.");
          }
      }
    }while(finished == false);
  }
}
