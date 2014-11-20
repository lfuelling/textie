package de.micromata.azubi;


import java.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.micromata.azubi.model.*;
import java.util.*;

/**
 * @author Lukas F&uuml;lling (l.fuelling@micromata.de)
 * @author Julian Siebert (j.siebert@micromata.de)
 */
public class Textie implements Serializable {

    private static final long serialVersionUID = -6980176018028225023L;
    public static boolean diag;
    public static String savegame;
    public static String lastPrintedText = "";

    public static void main(String[] args) {

        try {
            if (args[0].equals("--diag")) {
                diag = true;
            } else {
                diag = false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            diag = false;
        }

        Dungeon dungeon = Dungeon.createDungeon();
        dungeon.runGame();
        System.exit(0);
    }


    /**
     * End of the game.
     *
     * @return Returns true if you're in diag mode.
     */
    public static boolean ende(Dungeon dungeon) {
        printText("Herzlichen Glückwunsch !", dungeon);
        printText("Du bist aus deinem Traum erwacht und siehst, dass du", dungeon);
        printText("in deinem Bett liegst. Du spürst dein Herz stark und schnell schlagen", dungeon);
        printText("und bist froh, dass du aufgewacht bist.", dungeon);
        if (diag) {
            printText("Programm wird aufgrund des Diagnosemodus nicht beendet. Bitte Ctrl+C drücken.", dungeon);
        } else {
            System.exit(0);
        }
        return true;
    }

    /**
     * @param withPrompt Set to <code>true</code>, if you want a prompt.
     * @see de.micromata.azubi.model.Dungeon#runGame()
     */
    public static void warten(Dungeon dungeon, boolean withPrompt) {
        if (withPrompt == true) {
            Textie.prompt(dungeon);
        } else {
//            Runnable warten = new Runnable() {
//                @Override
//                public void run() {
//                    do {
//                    } while (Dungeon.getDungeon().getCurrentRaum().isLeaveRoom() == false);
//                }
//            };
//
//            Thread thread = new Thread(warten);
//            thread.start();
        }
    }


    /**
     * Executes the commands.
     *
     * @param parsed_command Command split at the first space.
     * @param parsed_args    Arguments of the command split by the first space.
     */
    public static void executeCommand(String[] parsed_command, String[] parsed_args, Dungeon dungeon) {
        if (dungeon.getCurrentRaum() == null) {
            System.err.println("currentRaum nicht da");
            // Kein raum nichts tun
            return;
        }
        int count = 0;
        int args = 0;
        for (String aParsed_command : parsed_command) {
            if (aParsed_command != null) {
                count++;
            }
        }
        for (String parsed_arg : parsed_args) {
            if (parsed_arg != null) {
                args++;
            }
        }
        if (parsed_command.length < 2) {
            switch (parsed_command[0].toLowerCase()) {
                case "hilfe":
                    Textie.printHelp(dungeon);
                    break;
                case "speichern":
                    Textie.doSpeichern(dungeon);
                    break;
                case "laden":
                    Textie.doLaden(dungeon);
                    break;
                default:
                    Textie.printText("Unbekannter Befehl oder fehlende Argumente: "
                            + parsed_command[0], dungeon);
                    break;
            }
        } else {
            Item itemToUse = chooseInventory(parsed_command[1], dungeon);
            switch (parsed_command[0]) {
                case Command.HILFE:
                    printHelp(dungeon);
                    break;
                case Command.NIMM:
                if(dungeon.getCurrentRaum().getInventory().findItemByName(parsed_command[1]) == null){
                    Textie.printText("Du musst ein Item angeben.");
                }
                else{
                    if (args > 1) { // (ACHTUNG: auch bei "nimm blauen hut" wird mehr als ein Argument erkannt)
                        switch (parsed_args[1].toLowerCase()) {
                            case "aus truhe":
                                StorageItem truhe = (StorageItem) dungeon.getCurrentRaum().getInventory().findItemByName("Truhe");
                                if (truhe != null) {
                                    try {
                                        truhe.getInventory().transferItem(dungeon.getPlayer().getInventory(),
                                                truhe.getInventory().findItemByName(parsed_args[0]));
                                    } catch (NullPointerException e) {
                                        printText("Item nicht gefunden.", dungeon);
                                        break;
                                    }
                                } else {
                                    printText("Hier gibt es keine Truhe", dungeon);
                                }
                                break;
                            default:
                                if (itemToUse == null) {
                                    printText("Du musst ein Item angeben.");
                                } else {
                                    printText("Unbekanntes Item: " + parsed_command[1], dungeon);
                                    break;
                                }
                        }
                    } else {
                        dungeon.getCurrentRaum().getInventory().transferItem(dungeon.getPlayer().getInventory(), dungeon.getCurrentRaum().getInventory().findItemByName(parsed_command[1]));
                    }
            }
                    break;
                case Command.BENUTZE:
                    itemToUse.benutzen(dungeon);
                    break;
                case Command.UNTERSUCHE:
                    dungeon.doUntersuche(parsed_command, count);
                    break;
                case Command.VERNICHTE:
                    dungeon.getPlayer().getInventory().transferItem(dungeon.getCurrentRaum().getInventory(), itemToUse);
                    break;

                case Command.GEHE:
                    dungeon.getPlayer().doGehen(Richtung.getByText(parsed_command[1]), dungeon);
                    break;
                case Command.REDE:
                    dungeon.getCurrentRaum().getHuman().doReden(dungeon);
                    break;
                case Command.GIB:
                    if (dungeon.getCurrentRaum().getHuman() != null) {
                        dungeon.doGeben(parsed_command, count);
                    } else {
                        printText("Hier gibt es niemandem, dem du etwas geben könntest", dungeon);
                    }
                    break;
                default:
                    printText("Unbekannter Befehl: " + parsed_command[0], dungeon);
                    break;
            }
        }
    }

    public static void prompt(Dungeon dungeon) {
        do {
            String command = IOUtils.readLine("Was willst du tun? ");
            try {
                if (command.equals("")) {
                } else {
                    String[] parsed_command = Textie.parseInput(command);

                    String[] parsed_args = new String[2];
                    if (parsed_command.length == 1 || parsed_command[1] == null) {
                        parsed_args[0] = "nichts";
                    } else {
                        parsed_args = Textie.parseInput(parsed_command[1]);
                    }
                    Textie.executeCommand(parsed_command, parsed_args, dungeon);
                }
            } catch (NullPointerException e) {
                Textie.printText("Keine Eingabe.");
                e.printStackTrace();
            }
        } while (dungeon.getCurrentRaum().isLeaveRoom() == false);
    }

    /**
     * Splits the input at the first space.
     *
     * @param command The command you want to execute.
     * @return Returns a string array containing a maximum size of two strings.
     */
    public static String[] parseInput(String command) {

        return command.split(" ", 2);
    }

    /**
     * Prints some text. If diag mode is active, it will print the number of the current room.
     *
     * @param text The text you want to print.
     */
    public static void printText(String text, Dungeon dungeon) {
        if (Textie.diag == true && dungeon != null) {
            System.out.println(dungeon.getCurrentRaum() == null ? text : "[" + dungeon.getCurrentRaum().getRoomNumber() + "], " + text);
        } else {
            System.out.println(text);
        }

        lastPrintedText = text;
    }

    /**
     * Prints some text. If diag mode is active, it will print the number of the current room.
     *
     * @param text The text you want to print.
     */
    public static void printText(String text) {
        Textie.printText(text, null);
    }


    /**
     * Prints the help.
     */
    public static void printHelp(Dungeon dungeon) {
        printText("Mögliche Befehle:", dungeon);
        printText("\thilfe -> Zeigt diese Hilfe", dungeon);
        printText("\tnimm [gegenstand] -> Gegenstand zum Inventar hinzufügen", dungeon);
        printText("\tnimm [gegenstand] aus truhe -> Gegenstand aus Truhe zum Inventar hinzufügen", dungeon);
        printText("\tbenutze [gegenstand] -> Gegenstand benutzen", dungeon);
        printText("\tuntersuche [gegenstand/raum/inventar] -> Gegenstand, Raum oder Inventar untersuchen", dungeon);
        printText("\tvernichte [gegenstand] -> Gegenstand aus dem Inventar löschen", dungeon);
        printText("\tgehe [nord/süd/ost/west] -> In eine Richtung gehen", dungeon);
        printText("\trede [person] -> Rede mit einer Person", dungeon);
    }

    /**
     * Saves.
     */
    public static void doSpeichern(Dungeon dungeon) {

        try (
                OutputStream file = new FileOutputStream("savegame.save");
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer);
        ) {
            output.writeObject(dungeon);
            output.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        printText("Gespeichert!", dungeon);
    }

    /*  Configschreiber

        List<Raum> rooms = dungeon.getRooms();
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("Test.json"), rooms);
        } catch (IOException e) {
            e.printStackTrace();
        }

     */


    /**
     * Loads
     */
    public static void doLaden(Dungeon dungeon) {

        try (
                InputStream file = new FileInputStream("savegame.save");
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer);
        ) {
            //deserialize the List
            Dungeon loadedDungeon = (Dungeon) input.readObject();
            dungeon = loadedDungeon;

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        printText("Geladen", dungeon);
    }

    /**
     * Get an Item.
     *
     * @param item The item you want.
     * @return Returns true if you could take it.
     */
    public static boolean recieveItem(Item item, Inventory inventory) {
        if (inventory.getSize() < inventory.getMaxSlots()) {
            inventory.addItem(item);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Chooses an Inventory.
     *
     * @param itemName the item you search.
     * @return Returns the item.
     */
    public static Item chooseInventory(String itemName, Dungeon dungeon) {
        Item item = null;
        if (dungeon.getPlayer().getInventory().findItemByName(itemName) != null) {
            item = dungeon.getPlayer().getInventory().findItemByName(itemName);
        } else if (dungeon.getCurrentRaum().getInventory().findItemByName(itemName) != null) {
            item = dungeon.getCurrentRaum().getInventory().findItemByName(itemName);
        }
        return item;
    }


}
