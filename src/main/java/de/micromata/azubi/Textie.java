package de.micromata.azubi;


import java.io.*;

import de.micromata.azubi.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Lukas F&uuml;lling (l.fuelling@micromata.de)
 * @author Julian Siebert (j.siebert@micromata.de)
 */
public class Textie implements Serializable {

	public static final String version = "4.5"; // Needs to be the same as in pom.xml
    
    private static final Logger logger = LogManager.getLogger(Textie.class.getName());
    public static boolean diag;
    public static boolean webapp;
    public static String savegame;
    public static String lastPrintedText = "";
    public static void main(String[] args) {
        logger.trace("Textie V." + getVersion() + " geladen.");
        try {
            if (args[0].equals("--diag")) {
                diag = true;
                logger.trace("Diagnosemodus gestartet!");
            } else {
                diag = false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            diag = false;
            logger.trace("Eine ArrayIndexOutOfBoundsException wurde geworfen, wahrscheinlich gibt es keine Argumente.");
        }

        Dungeon dungeon = Dungeon.createDungeon();
        logger.trace("Dungeon erstellt, starte das Spiel.");
        dungeon.runGame();
    }


    /**
     * End of the game.
     *
     * @return Returns true if you're in diag mode.
     */
    public static boolean end(Dungeon dungeon) {
        printText("Herzlichen Glückwunsch !", dungeon);
        printText("Du bist aus deinem Traum erwacht und siehst, dass du", dungeon);
        printText("in deinem Bett liegst. Du spürst dein Herz stark und schnell schlagen", dungeon);
        printText("und bist froh, dass du aufgewacht bist.", dungeon);
        if (diag) {
            printText("Programm wird aufgrund des Diagnosemodus nicht beendet. Bitte Ctrl+C drücken.", dungeon);
        } else if(webapp){
            printText("\n\n\nUm erneut zu Spielen, logge dich erneut ein!");
        }else{
            logger.trace("Spiel beendet.");
            System.exit(0);
        }
        return true;
    }

    /**
     * @param withPrompt Set to <code>true</code>, if you want a prompt.
     * @see de.micromata.azubi.model.Dungeon#runGame()
     */
    public static void wait(Dungeon dungeon, boolean withPrompt) {
        if (withPrompt == true) {
        	logger.trace("Prompt wird gestartet.");
            Textie.prompt(dungeon);
        } else {
            logger.info("Keine Konsole ausgewählt. Testmodus aktiv.");
        }
    }


    /**
     * Executes the commands.
     *
     * @param parsed_command Command split at the first space.
     * @param parsed_args    Arguments of the command split by the first space.
     */
    public static void executeCommand(String[] parsed_command, String[] parsed_args, Dungeon dungeon) {
        if (dungeon.getCurrentRoom() == null) {
            logger.error("currentRaum ist nicht gesetzt!");
            return;
        }
        String Zcommand = "";
        for (String str:parsed_command) {
        	Zcommand = Zcommand + str;
        }
        logger.trace("Befehl: " + Zcommand);
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
                    Textie.doSave(dungeon);
                    break;
                case "laden":
                    Textie.doLoad(dungeon);
                    break;
                default:
                    Textie.printText("Unbekannter Befehl oder fehlende Argumente: "
                            + parsed_command[0], dungeon);
                    
                    logger.warn("Unbekannter Befehl: " + parsed_command[0]);
                    break;
            }
        } else {
            Item itemToUse = chooseInventory(parsed_command[1], dungeon);
            if ("benutze".equalsIgnoreCase(parsed_command[0]) && itemToUse == null) {
                printText("Das Item gibt es nicht!");
                logger.warn("Objekt " + parsed_command[1] + " nicht gefunden." );
            } else {
                switch (parsed_command[0]) {
                    case Command.HILFE:
                        printHelp(dungeon);
                        logger.trace("Hilfe ausgegeben.");
                        break;
                    case Command.NIMM:
                        if (args > 1) { // (ACHTUNG: auch bei "nimm blauen hut" wird mehr als ein Argument erkannt)
                            switch (parsed_args[1].toLowerCase()) {
                                case "aus truhe":
                                    StorageItem truhe = (StorageItem) dungeon.getCurrentRoom().getInventory().findItemByName("Truhe");
                                    if (truhe == null) {
                                        printText("Hier gibt es keine Truhe", dungeon);
                                        logger.trace("Keine Truhe in Raum " + dungeon.getCurrentRoom().getRoomNumber());
                                    } else {
                                        Item item = truhe.getInventory().findItemByName(parsed_args[0]);
                                        if (item == null) {
                                            printText("Item nicht gefunden", dungeon);
                                        } else {
                                            truhe.getInventory().transferItem(dungeon.getPlayer().getInventory(), item);
                                        }
                                    }
                                    break;
                                default:
                                    if (itemToUse == null) {
                                        printText("Du musst ein Item angeben.");
                                    } else {
                                        printText("Unbekanntes Item: " + parsed_command[1], dungeon);
                                        logger.info("Unbekanntes Item: " + parsed_command[1]);
                                        break;
                                    }
                            }
                        } else {
                            Item item = dungeon.getCurrentRoom().getInventory().findItemByName(parsed_command[1]);
                            if(item == null){
                                printText("Unbekanntes Item:" + parsed_command[1], dungeon);
                                logger.info("Unbekanntes Item: " + parsed_command[1]);
                            }else {
                                dungeon.getCurrentRoom().getInventory().transferItem(dungeon.getPlayer().getInventory(), item);
                                Textie.printText(item.getName() + " zum Inventar hinzugefügt.");
                            }
                        }

                        break;
                    case Command.BENUTZE:
                        itemToUse.use(dungeon);
                        break;
                    case Command.UNTERSUCHE:
                        dungeon.doExamine(parsed_command, count);
                        break;
                    case Command.VERNICHTE:
                        dungeon.getPlayer().getInventory().transferItem(dungeon.getCurrentRoom().getInventory(), itemToUse);
                        break;

                    case Command.GEHE:
                        dungeon.getPlayer().doWalk(Direction.getByText(parsed_command[1]), dungeon);
                        break;
                    case Command.REDE:
                        dungeon.getCurrentRoom().getHuman().doTalk(dungeon);
                        break;
                    case Command.GIB:
                        if (dungeon.getCurrentRoom().getHuman() != null) {
                            dungeon.doGive(parsed_command, count);
                        } else {
                            printText("Hier gibt es niemandem, dem du etwas geben könntest", dungeon);
                        }
                        break;
                    default:
                        printText("Unbekannter Befehl: " + parsed_command[0], dungeon);
                        logger.trace("Unbekannter Befehl: " + parsed_command[0]);
                        break;
                }
            }
        }
    }

    public static void prompt(Dungeon dungeon) {
        do {
            String command = IOUtils.readLine("Was willst du tun? ");
            try {
                if (command.equals("")) {
                	Textie.printText("Keine Eingabe.");
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
                logger.fatal(e);
            }
        } while (dungeon.getCurrentRoom().isLeaveRoom() == false);
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
        if(webapp) {
            lastPrintedText = lastPrintedText + "\n" + text;
        } else {
            if(Textie.diag == true && dungeon != null) {
                System.out.println(dungeon.getCurrentRoom() == null ? text : "[" + dungeon.getCurrentRoom().getRoomNumber() + "], " + text);
            } else {
                System.out.println(text);
            }

            lastPrintedText = text;
        }
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
        printText("\tbenutze [gegenstand] -> Gegenstand use", dungeon);
        printText("\tuntersuche [gegenstand/raum/inventar] -> Gegenstand, Raum oder Inventar examine", dungeon);
        printText("\tvernichte [gegenstand] -> Gegenstand aus dem Inventar löschen", dungeon);
        printText("\tgehe [nord/süd/ost/west] -> In eine Richtung gehen", dungeon);
        printText("\trede [person] -> Rede mit einer Person", dungeon);
    }

    /**
     * Saves.
     */
    public static void doSave(Dungeon dungeon) {

        try (
                OutputStream file = new FileOutputStream("savegame.save"); //FIXME Ich hätte ja daraus eine Variable gemacht, allerdings implementiert java.lang.String nicht java.lang.AutoCloseable...
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer);
        ) {
            logger.trace("Versuche Savegame zu schreiben...");
            output.writeObject(dungeon);
            output.close();
            logger.info("Geschrieben und gespeichert als: " + "savegame.save"); 
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error("IOException geworfen: ", ex);
        }
        printText("Gespeichert!", dungeon);
    }

    /**
     * Loads
     */
    public static void doLoad(Dungeon dungeon) {
    	logger.trace("Versuche Savegame zu laden...");
        try (
                InputStream file = new FileInputStream("savegame.save");
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer);
        ) {
        	logger.trace("Lese Savegame...");
            Dungeon loadedDungeon = (Dungeon) input.readObject();
            dungeon = loadedDungeon;

        }  catch (IOException ex) {
            ex.printStackTrace();
            logger.error("IOException geworfen: ", ex);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            logger.error("ClassNotFoundException geworfen: ", e);
        }

        printText("Geladen", dungeon);
        logger.trace("Savegame erfolgreich geladen.");
    }

    /**
     * Get an Item.
     *
     * @param item The item you want.
     * @return Returns true if you took it.
     */
    public static boolean recieveItem(Item item, Inventory inventory) {
        if (inventory.getSize() < inventory.getMaxSlots()) {
            inventory.addItem(item);
            logger.trace(item.getName() + " zum Inventar hinzugefügt.");
            return true;
        } else {
        	logger.trace("Dein Inventar ist voll.");
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
        logger.trace("Suche nach " + itemName + " (chooseInventory)");
        if (dungeon.getPlayer().getInventory().findItemByName(itemName) != null) {
            item = dungeon.getPlayer().getInventory().findItemByName(itemName);
            logger.trace(itemName + " ist im Inventar.");
        } else if (dungeon.getCurrentRoom().getInventory().findItemByName(itemName) != null) {
            item = dungeon.getCurrentRoom().getInventory().findItemByName(itemName);
            logger.trace(itemName + " ist im Raum.");
        }
        if (item == null){
        	logger.error("Item " + itemName + " konnte nicht gefunden werden.");
        }
        return item;
    }

    public static String getVersion() {

        return version;
    }

    public static Logger getLogger() {
        return logger;
    }
}
