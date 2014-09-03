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
    //Textie.listInventory(inventory);
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
                  return;
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
                  default:
                    System.out.println("Welches Objekt möchtest du benutzen?");
              }
            }
          }
          if(objektVorhanden==false){
            System.out.println("Du hast " + Textie.getObjectName(object_to_use) + " nicht.");
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

                case "schwert":
                    if(Textie.findInInventory(inventory, 5) != -128) {
                      System.out.println("Du betrachtest das Schwert. Es sieht sehr scharf aus.");
                    }
                    else if(Textie.findInRoom(umgebung, 5, vorhanden) != -128) {
                      System.out.println("Da liegt ein Schwert.");
                    }
                    else {
                      System.out.println("Hä?");
                    }
                    break;

                case "feuerzeug":
                  if(Textie.findInInventory(inventory, 6) != -128) {
                    System.out.println("Du betrachtest das Feuerzeug. Es wirkt zuverlässig.");
                  }
                  else if(Textie.findInRoom(umgebung, 2, vorhanden) != -128) {
                    System.out.println("Da liegt ein Feuerzeug.");
                  }
                  else {
                    System.out.println("Hä?");
                  }
                  break;

                case "schlüssel":
                  if(Textie.findInInventory(inventory, 2) != -128) {
                    System.out.println("Du betrachtest den Schlüssel. Was kann man damit wohl aufschließen?");
                  }
                  else if(Textie.findInRoom(umgebung, 2, vorhanden) != -128) {
                    System.out.println("Da liegt ein Schlüssel.");
                  }
                  else {
                    System.out.println("Hä?");
                  }
                  break;

                case "stein":
                  if(Textie.findInInventory(inventory, 2) != -128) {
                    System.out.println("Du betrachtest den Stein. Er ist kalt.");
                  }
                  else if(Textie.findInRoom(umgebung, 2, vorhanden) != -128) {
                    System.out.println("Da liegt ein Stein.");
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
                case "west":
                  if(Textie.findInInventory(inventory, 1) != -128 && Textie.findInInventory(inventory, 6) != -128 && Textie.findInInventory(inventory, 7) != -128){
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
                case "süd":
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
