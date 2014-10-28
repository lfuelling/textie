
package de.micromata.azubi;

import java.io.*;

/**
 * @author Lukas Fülling (l.fuelling@micromata.de)
 * @author Julian Siebert (j.siebert@micromata.de)
 */
public class Textie implements Serializable {
    private static final long serialVersionUID = -6980176018028225023L;
    public static boolean diag;
    public static String savegame;
    public static String lastPrintedText = "";
    static int dialogNumber = 0;

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

        Dungeon dungeon = Dungeon.getDungeon();
//        dungeon.init();
        dungeon.runGame(true);
        System.exit(0);
    }

    /**
     * End of the game.
     *
     * @return Returns true if you're in diag mode.
     */
    public static boolean ende() {
        printText("Herzlichen Glückwunsch !");
        printText("Du bist aus deinem Traum erwacht und siehst, dass du");
        printText("in deinem Bett liegst. Du spürst dein Herz stark und schnell schlagen");
        printText("und bist froh, dass du aufgewacht bist.");
        if (diag) {
            printText("Programm wird aufgrund des Diagnosemodus nicht beendet. Bitte Ctrl+C drücken.");
        } else {
            System.exit(0);
        }
        return true;
    }

    /**
     * @param withPrompt Set to <code>true</code>, if you want a prompt.
     * @see de.micromata.azubi.Dungeon#runGame(boolean)
     */
    public static void warten(boolean withPrompt) {
        if (withPrompt == true) {
            Dungeon.getDungeon().player.prompt();
        } else {
            printText("Was willst du tun? ");
            Runnable warten = new Runnable() {
                @Override
                public void run() {
                    do {
                    } while (Dungeon.getDungeon().getCurrentRaum().isLeaveRoom() == false);
                }
            };

            Thread thread = new Thread(warten);
            thread.start();
        }
    }


    /**
     * Executes the commands.
     *
     * @param parsed_command Command split at the first space.
     * @param parsed_args    Arguments of the command split by the first space.
     */
    public static void executeCommand(String[] parsed_command, String[] parsed_args) {
        if (Dungeon.getDungeon().getCurrentRaum() == null) {
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

        } else {
            Item itemToUse = chooseInventory(parsed_command[1]);
            switch (parsed_command[0]) {
                case Command.HILFE:
                    printHelp();
                    break;
                case Command.NIMM:
                    if (args > 1) { // (ACHTUNG: auch bei "nimm blauen hut" wird mehr als ein Argument erkannt)
                        switch (parsed_args[1].toLowerCase()) {
                            case "aus truhe":
                                StorageItem truhe = (StorageItem) Dungeon.getDungeon().getCurrentRaum().getInventory().findItemByName("Truhe");
                                if (truhe != null) {
                                    doTakeFromChest(truhe.getInventory().findItemByName(parsed_args[0]));
                                } else {
                                    printText("Hier gibt es keine Truhe");
                                }
                                break;
                            default:
                                printText("Unbekanntes Item: " + parsed_command[1]);
                                break;
                        }
                    } else {
                        doNimm(itemToUse);
                    }
                    break;
                case Command.BENUTZE:
                    doBenutze(itemToUse);
                    break;
                case Command.UNTERSUCHE:
                    doUntersuche(parsed_command, count);
                    break;
                case Command.VERNICHTE:
                    doVernichte(itemToUse, count);
                    break;

                case Command.GEHE:
                    doGehen(Richtung.getByText(parsed_command[1]));
                    break;
                case Command.REDE:
                    doReden();
                    break;
                case Command.GIB:
                    if (Dungeon.getDungeon().getCurrentRaum().getHuman() != null) {
                        doGeben(parsed_command, count);
                    } else {
                        printText("Hier gibt es niemandem, dem du etwas geben könntest");
                    }
                    break;
                default:
                    printText("Unbekannter Befehl: " + parsed_command[0]);
                    break;
            }
        }
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
    public static void printText(String text) {
        if (Textie.diag == true) {
            System.out.println(Dungeon.getDungeon().getCurrentRaum() == null ? text : "[" + Dungeon.getDungeon().getCurrentRaum().roomNumber + "], " + text);
        } else {
            System.out.println(text);
        }

        lastPrintedText = text;
    }

    /**
     * Let's you walk.
     *
     * @param richtung the direction you want to go.
     */
    static void doGehen(Richtung richtung) {
        if(Dungeon.getDungeon().getCurrentRaum().getNumber()==6 && Dungeon.getDungeon().getCurrentRaum().getNextRoom(richtung) == null){
            printText("Der Weg wird durch eine Holzbarrikade versperrt.");
        }
        else {
            Raum raum = Dungeon.getDungeon().getRaum(richtung);
            if (raum != null && Dungeon.getDungeon().raums.get(Dungeon.getDungeon().previousRoomNumber).isLeaveRoom()) {
                Dungeon.getDungeon().setRoomNumber(raum);
                Dungeon.getDungeon().getCurrentRaum().setLeaveRoom(false);
                Textie.printText(Dungeon.getDungeon().getCurrentRaum().getWillkommensNachricht());
            }
        }

    }

    /**
     * Throwas away an Item.
     *
     * @param item  The item to throw away.
     * @param count The size of the parsed_command String[]
     */
    static void doVernichte(Item item, int count) {
        if (count == 2) {
            if (Dungeon.getDungeon().player.getInventory().transferItem(Dungeon.getDungeon().getCurrentRaum().getInventory(), item)) {
                printText(item.getName() + " vernichtet.");
                return;
            } else {
                printText("Entweder das Objekt gibt es nicht, oder es ist nicht im Inventar.");
                return;
            }
        } else {
            printText("Was soll vernichtet werden?");
        }
    }

    /**
     * Let's you inspect an item.
     *
     * @param parsed_command The String[]
     * @param count          THe size of the String[]
     */
    static void doUntersuche(String[] parsed_command, int count) {
        if (count == 2) {
            switch (parsed_command[1].toLowerCase()) {
                case "raum":
                    if (Dungeon.getDungeon().getCurrentRaum().getNumber() == 3) {
                        Item item = chooseInventory("Fackel");
                        if (item instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item;
                            if (fackel.getState() == true) {
                                Textie.printText("Im Raum befindet sich:");
                                Dungeon.getDungeon().getCurrentRaum().getInventory().listItems();
                            } else {
                                printText("Du kannst nichts sehen!");
                            }
                        }
                    } else {
                        Textie.printText("Im Raum befindet sich:");
                        Dungeon.getDungeon().getCurrentRaum().getInventory().listItems();
                    }
                    break;
                case "inventar":
                    if (Dungeon.getDungeon().getCurrentRaum().getNumber() == 3) {
                        Item item = chooseInventory("Fackel");
                        if (item instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item;
                            if (fackel.getState() == true) {
                                Textie.printText("In deiner Tasche befindet sich:");
                                Dungeon.getDungeon().player.getInventory().listItems();
                            } else {
                                printText("Du kannst nichts sehen!");
                            }
                        }
                    } else {
                        Textie.printText("In deiner Tasche befindet sich:");
                        Dungeon.getDungeon().player.getInventory().listItems();
                    }
                    break;
                case "truhe":
                    if (Dungeon.getDungeon().getCurrentRaum().getInventory().hasItem("Truhe")) {
                        StorageItem truhe = (StorageItem) Dungeon.getDungeon().getCurrentRaum().getInventory().findItemByName("Truhe");
                        if (truhe.lockState == true) {
                            printText("Die Truhe ist verschlossen.");
                        } else {
                            truhe.getInventory().listItems();
                        }
                    } else {
                        printText("Hier ist keine Truhe");
                    }
                    break;
                default:
                    if (Dungeon.getDungeon().getCurrentRaum().getNumber() == 3) {
                        Item item = Dungeon.getDungeon().getCurrentRaum().getInventory().findItemByName("Fackel");
                        if (item instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item;
                            if (fackel.getState() == true) {
                                Item itemUSU = Dungeon.getDungeon().getCurrentRaum().getInventory().findItemByName(parsed_command[1]);
                                Item itemUSU1 = chooseInventory(parsed_command[1]);
                                if (itemUSU == null && itemUSU1 == null) {
                                    printText("Das Objekt gibt es nicht.");
                                } else {
                                    itemUSU.untersuchen();
                                    itemUSU.untersuchen();
                                }
                            } else {
                                printText("Du kannst nichts sehen!");
                            }
                        }
                    } else {
                        Item itemUSU = Dungeon.getDungeon().getCurrentRaum().getInventory().findItemByName(parsed_command[1]);
                        Item itemUSU1 = chooseInventory(parsed_command[1]);

                        if (itemUSU == null) {
                            if (itemUSU1 == null) {
                                printText("Das Objekt gibt es nicht.");
                            } else {
                                itemUSU1.untersuchen();
                            }
                        } else {
                            itemUSU.untersuchen();
                        }
                    }
            }
        } else {
            printText("Was soll untersucht werden?");
        }
    }

    /**
     * Lets you use an item.
     *
     * @param item The item to use.
     */
    static void doBenutze(Item item) {
        if (item == null) {

            printText("Das Item gibt es nicht.");

        } else {
            Inventory raumInventar = Dungeon.getDungeon().getCurrentRaum().getInventory();
            Inventory playerInventory = Dungeon.getDungeon().player.getInventory();
            if (item.isPickable() == false || playerInventory.hasItem(item.getName())) {
                String itemName = item.getName();
                if (Textie.diag == true) {
                    printText("Du willst " + itemName + " benutzen");
                }
                switch (itemName) {
                    // Fackel und Feuerzeug sind besonders, da sie auch funktionen
                    // aufrufen
                    // und nicht nur einen Text ausgeben. Außerdem sollen diese Items
                    // benutzbar sein, selbst wenn der Raum dunkel ist.
                    case "Fackel":// Dungeon.getDungeon().itemMap.get("FACKEL").getName():
                    case "Feuerzeug": // Dungeon.getDungeon().itemMap.get("FEUERZEUG").getName():
                        int fackelSlot = playerInventory.findItem(playerInventory.findItemByName("Fackel"));
                        int feuerZeugSlot = playerInventory.findItem(playerInventory.findItemByName("Feuerzeug"));
                        if (feuerZeugSlot < 0) {
                            printText("Du hast kein Feuerzeug.");
                            break;
                        } else if (fackelSlot < 0) {
                            printText("Du hast keine Fackel.");
                            break;
                        } else {
                            printText("Du zündest deine Fackel mit dem Feuerzeug an.");
                            Item item2 = playerInventory.findItemByName("Fackel");
                            if (item2 instanceof ToggleItem) {
                                ToggleItem fackel = (ToggleItem) item2;
                                fackel.setState(true);
                            }
                            break;
                        }
                    case "Falltür":
                        Item item5 = chooseInventory("Fackel");
                        if (item5 instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item5;
                            if (fackel.getState() == true && Dungeon.getDungeon().getCurrentRaum().getNumber() == 3) {
                                Item itemToUse = item;
                                if (itemToUse == null) {
                                    printText("Das Objekt gibt es nicht.");
                                    break;
                                } else {
                                    if (raumInventar.hasItem("Falltür")) {
                                        printText("Du schlüpfst durch die Falltür in den darunterliegenden Raum.");
                                        doGehen(Richtung.FALLTUER);
                                        break;
                                    }
                                }
                            } else {
                                printText("Du kannst nichts sehen!");
                                break;
                            }
                        }
                    case "Axt":
                        Item axt = item;
                        if(Dungeon.getDungeon().getCurrentRaum().getNumber()==6) {
                            Dungeon.getDungeon().getCurrentRaum().verbindungen.put(Richtung.OST, Dungeon.getDungeon().findRaumByNummer(7));
                            axt.benutzen();
                        } else {
                            Textie.printText("Du fuchtelst mit der Axt wild in der Gegend herum");
                        }
                        break;
                    case "Sack":
                        Item sack = item;
                        sack.benutzen();
                        playerInventory.getInventory().remove(playerInventory.findItemByName("Sack"));
                        playerInventory.setInventorySize(2);
                        break;
                    case "Schalter":
                        ToggleItem schalter = (ToggleItem) item;
                        schalter.benutzen();
                        schalter.setState(true);
                        break;
                    case "Schwert":
                        playerInventory.findItemByName("Schwert").benutzen();
                        ende();
                        break;
                    case "Schlüssel":
                        StorageItem truhe = (StorageItem) raumInventar.findItemByName("Truhe");
                        if (raumInventar.hasItem("Truhe")) {
                            if (truhe.lockState == true) {
                                truhe.lockState = false;
                                printText("Du öffnest die Truhe mit dem Schlüssel.");
                                break;
                            } else {
                                printText("Die Truhe ist bereits aufgeschlossen.");
                                break;
                            }
                        } else {
                            printText("Hier gibt es nichts, was man aufschließen könnte.");
                            break;
                        }
                    default:
                        if (Dungeon.getDungeon().getCurrentRaum().getNumber() == 3) {
                            item5 = playerInventory.findItemByName("Fackel");
                            if (item5 instanceof ToggleItem) {
                                ToggleItem fackel = (ToggleItem) item5;
                                if (fackel.getState() == true) {
                                    Item itemToUse = item;
                                    if (itemToUse == null) {
                                        printText("Das Objekt gibt es nicht.");
                                    } else {
                                        itemToUse.benutzen();
                                    }
                                } else {
                                    printText("Du kannst nichts sehen!");
                                }
                            }
                        } else {
                            Item itemToUse = item;
                            if (itemToUse == null) {
                                printText("Das Objekt gibt es nicht.");
                            } else {
                                itemToUse.benutzen();
                            }
                        }
                }
            } else {
                printText("Du musst das Item im Inventar haben.");
            }
        }
    }

    /**
     * Let's you take stuff from a chest.
     *
     * @param item The item to take.
     */
    static void doTakeFromChest(Item item) {
        if (item.isPickable()) {
            if (addItemFromChestToInventory(item)) {

                printText(item.getName() + " zum Inventar hinzugefügt.");
            } else {
                printText("Entweder das Objekt gibt es nicht, oder dein Inventar ist voll.");
            }
        } else {
            printText("Du kannst dieses Item nicht aufheben.");
        }
    }

    /**
     * Pick up an item from the floor.
     *
     * @param item The item to pick up.
     */
    static void doNimm(Item item) {
        if (item == null) {
            printText("Unbekanntes Item.");
        } else {
            if (item.isPickable()) {
                if (Dungeon.getDungeon().getCurrentRaum().getInventory().transferItem(Dungeon.getDungeon().player.getInventory(), item)) {

                    printText(item.getName() + " zum Inventar hinzugefügt.");
                } else {
                    printText("Entweder das Objekt gibt es nicht, oder dein Inventar ist voll.");
                }
            } else {
                printText("Du kannst dieses Item nicht aufheben.");
            }
        }
    }

    /**
     * Prints the help.
     */
    static void printHelp() {
        printText("Mögliche Befehle:");
        printText("\thilfe -> Zeigt diese Hilfe");
        printText("\tnimm [gegenstand] -> Gegenstand zum Inventar hinzufügen");
        printText("\tbenutze [gegenstand] -> Gegenstand benutzen");
        printText("\tuntersuche [gegenstand/raum/inventar] -> Gegenstand, Raum oder Inventar untersuchen");
        printText("\tvernichte [gegenstand] -> Gegenstand aus dem Inventar löschen");
        printText("\tgehe [nord/süd/ost/west] -> In eine Richtung gehen");
    }

    /**
     * Saves.
     */
    public static void doSpeichern() {

        try (
                OutputStream file = new FileOutputStream("savegame.save");
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer);
        ) {
            output.writeObject(Dungeon.getDungeon());
            output.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        printText("Gespeichert!");
    }

    /**
     * Loads
     */
    public static void doLaden() {

        try (
                InputStream file = new FileInputStream("savegame.save");
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer);
        ) {
            //deserialize the List
            Dungeon loadedDungeon = (Dungeon) input.readObject();
            Dungeon.setDungeon(loadedDungeon);

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        printText("Geladen");

        /*savegame = IOUtils.readFromFile();
        if (savegame != null) {
            Dungeon loadedDungeon = new JSONDeserializer<Dungeon>().deserialize(savegame);
            Dungeon.setDungeon(null);
            Dungeon.getDungeon().player.setInventory(null);
            Dungeon.setDungeon(loadedDungeon);
            //Dungeon.getDungeon().player.setInventory(loadedDungeon.player.getInventory());
            printText("Geladen!");
            //printText("Raum: " + dungeon1.currentRaum.getNumber());
            printText("Raum:" + Dungeon.getDungeon().getCurrentRaum().getNumberAsString());
        } else {
            printText("Wer nicht speichert, kann nichts laden");
        }
        */
    }


    //RaumKram
    /*
    public static boolean removeItemInRoom(Item item) {
        if (Dungeon.getDungeon().getCurrentRaum().items.remove(item))
            return true;
        return false;
    }


    public static boolean addItemToRoom(Item item) {
        if (Dungeon.getDungeon().getCurrentRaum().items.add(item)) {
            return true;
        }

        return false;
    }

    public static boolean isInRoom(Item item) {
        return findInRoom(item) >= 0;
    }

    public static int findInRoom(Item item) {
        int i = -128;
        i = Dungeon.getDungeon().getCurrentRaum().items.indexOf(item);
        return i;
    }
    */


    /**
     * Best name ever.
     *
     * @param item Item to take.
     * @return Returns true, if you can pick up the item.
     */
    public static boolean addItemFromChestToInventory(Item item) {
        StorageItem dieTruhe = (StorageItem) Dungeon.getDungeon().getCurrentRaum().getInventory().findItemByName("Truhe");
        if (Dungeon.getDungeon().player.getInventory().getInventory().size() < Dungeon.getDungeon().player.getInventory().getInventorySize() && dieTruhe.getInventory().hasItem(item.getName())) {
            dieTruhe.getInventory().transferItem(Dungeon.getDungeon().player.getInventory(), item);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Give someone an item.
     *
     * @param item Item to give.
     * @return Returns true, if you were able to give that item.
     */
    public static boolean giveItem(Item item) {
        if (Dungeon.getDungeon().player.getInventory().getInventory().remove(item)) {
            return true;
        }
        return false;
    }

    /**
     * Talk to someone.
     */
    static void doReden() {
        if (Dungeon.getDungeon().getCurrentRaum().getHuman().isQuestDone() == true) {
            if (Dungeon.getDungeon().getCurrentRaum().getHuman().isGaveItem() == true) {
                if (recieveItem(Dungeon.getDungeon().getCurrentRaum().getHuman().getRewarditem())) {
                    printText("Hier, bitte schön.");
                    Dungeon.getDungeon().getCurrentRaum().getHuman().setGaveItem(false);
                } else {
                    printText("Dein Inventar ist leider voll. Komm wieder, wenn du Platz hast.");
                    Dungeon.getDungeon().getCurrentRaum().getHuman().setGaveItem(true);
                }
            } else {
                switch (dialogNumber) {
                    case 0:
                        printText(Dungeon.getDungeon().getCurrentRaum().getHuman().getDialog1());
                        dialogNumber = 1;
                        break;
                    case 1:
                        printText(Dungeon.getDungeon().getCurrentRaum().getHuman().getDialog2());
                        dialogNumber = 0;
                        break;
                }
            }
        } else {
            printText(Dungeon.getDungeon().getCurrentRaum().getHuman().getQuestText());
        }
    }

    /**
     * Give someone an item.
     *
     * @param parsed_command The String[]
     * @param count          The size of the String[]
     */
    public static void doGeben(String[] parsed_command, int count) {
        if (count == 2) {
            String itemToUse = IOUtils.convertToName(parsed_command[1]);
            //Item itemToUse = chooseInventory(parsed_command[1]);
            if (itemToUse.equals(Dungeon.getDungeon().getCurrentRaum().getHuman().getQuestItem())) {
                if (giveItem(chooseInventory(parsed_command[1]))) {
                    printText(Dungeon.getDungeon().getCurrentRaum().getHuman().getQuestDoneText());
                    Dungeon.getDungeon().getCurrentRaum().getHuman().setQuestDone(true);
                    if (recieveItem(Dungeon.getDungeon().getCurrentRaum().getHuman().getRewarditem())) {
                        printText("Im Gegenzug bekommst du von mir auch etwas. Bitteschön.");
                    } else {
                        printText("Dein Inventar ist leider voll. Komm wieder, wenn du Platz hast.");
                        Dungeon.getDungeon().getCurrentRaum().getHuman().setGaveItem(true);
                    }
                } else {
                    printText("Item nicht im Inventar.");
                }
            } else {
                printText("Das brauche ich nicht.");
            }
        } else {
            printText("Zu wenig Argumente");
        }
    }

    /**
     * Get an Item.
     *
     * @param item The item you want.
     * @return Returns true if you could take it.
     */
    public static boolean recieveItem(Item item) {
        if (Dungeon.getDungeon().player.getInventory().getInventory().size() < Dungeon.getDungeon().player.getInventory().getInventorySize()) {
            Dungeon.getDungeon().player.getInventory().getInventory().add(item);
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
    public static Item chooseInventory(String itemName) {
        Item item = null;
        if (Dungeon.getDungeon().player.getInventory().findItemByName(itemName) != null) {
            item = Dungeon.getDungeon().player.getInventory().findItemByName(itemName);
        } else if (Dungeon.getDungeon().getCurrentRaum().getInventory().findItemByName(itemName) != null) {
            item = Dungeon.getDungeon().getCurrentRaum().getInventory().findItemByName(itemName);
        }
        return item;
    }
}
