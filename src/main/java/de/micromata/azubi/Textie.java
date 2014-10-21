
package de.micromata.azubi;

/*
 *  Das ist der GameMaster
 *  Der Spieler darf entscheiden, was er tun möchte, doch der GameMaster entscheidet, was geschiet.
 */


import java.io.*;

public class Textie implements Serializable{
    private static final long serialVersionUID = -6980176018028225023L;
    public static boolean diag;
    public static String savegame;
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
        dungeon.init();
        dungeon.runGame(true);
        System.exit(0);
    }

    public static boolean ende() {
        printText("Herzlichen Glückwunsch !");
        printText("Du bist aus deinem Traum erwacht und siehst, dass du");
        printText("in deinem Bett liegst. Du spürst dein Herz stark und schnell schlagen");
        printText("und bist froh, dass du aufgewacht bist.");
        if(diag) {
            printText("Programm wird aufgrund des Diagnosemodus nicht beendet. Bitte Ctrl+C drücken.");
        } else {
            System.exit(0);
        }
        return true;
    }

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


    //Befehlsverarbeitung
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
            Item itemToUse = Dungeon.getDungeon().itemMap.get(parsed_command[1].toUpperCase());
            switch (parsed_command[0]) {
                case Command.HILFE:
                    printHelp();
                    break;
                case Command.NIMM:
                    if (args > 1) { // (ACHTUNG: auch bei "nimm blauen hut" wird mehr als ein Argument erkannt)
                        switch (parsed_args[1].toLowerCase()) {
                            case "aus truhe":
                                doTakeFromChest(Dungeon.getDungeon().itemMap.get(parsed_args[0].toUpperCase()));
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
                    if (Dungeon.getDungeon().currentHuman != null) {
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

    public static String[] parseInput(String command) {

        return command.split(" ", 2);
    }

    public static void printText(String text) {
        if (Textie.diag == true) {
            System.out.println(Dungeon.getDungeon().getCurrentRaum() == null ? text : "[" + Dungeon.getDungeon().getCurrentRaum().roomNumber + "], " + text);
        } else {
            System.out.println(text);
        }
    }

    static void doGehen(Richtung richtung) {
        Raum raum = Dungeon.getDungeon().getRaum(richtung);
        if (raum != null && Dungeon.getDungeon().raums.get(Dungeon.getDungeon().previousRoomNumber).isLeaveRoom()) {
            Dungeon.getDungeon().setRoomNumber(raum);
            Dungeon.getDungeon().getCurrentRaum().setLeaveRoom(false);
            Textie.printText(Dungeon.getDungeon().getCurrentRaum().getWillkommensNachricht());
        }

    }


/*

                        printText("Du siehst eine Tür und gehst die Treppe dahinter hinauf.");
                        Karte karte;
                        if (Dungeon.getDungeon().itemMap.get(Consts.KARTE).isKarte() == true) {
                            karte = (Karte) Dungeon.getDungeon().itemMap.get(Consts.KARTE);
                            karte.writeMap(Dungeon.getDungeon().getCurrentRaum().getNumberAsString(), parsed_command[1].toUpperCase());
                        }printText("Du siehst eine Tür und gehst die Treppe dahinter hinab.");
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

    static void doUntersuche(String[] parsed_command, int count) {
        if (count == 2) {
            switch (parsed_command[1].toLowerCase()) {
                case "raum":
                    if (Dungeon.getDungeon().getCurrentRaum().getNumber() == 3) {
                        Item item = Dungeon.getDungeon().itemMap.get(Consts.FACKEL);
                        if (item instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item;
                            if (fackel.getState() == true) {
                                Dungeon.getDungeon().getCurrentRaum().listItems();
                            } else {
                                printText("Du kannst nichts sehen!");
                            }
                        }
                    } else {
                        Dungeon.getDungeon().getCurrentRaum().listItems();
                    }
                    break;
                case "inventar":
                    if (Dungeon.getDungeon().getCurrentRaum().getNumber() == 3) {
                        Item item = Dungeon.getDungeon().itemMap.get(Consts.FACKEL);
                        if (item instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item;
                            if (fackel.getState() == true) {
                                Dungeon.getDungeon().player.getInventory().listItems();
                            } else {
                                printText("Du kannst nichts sehen!");
                            }
                        }
                    } else {
                        Dungeon.getDungeon().player.getInventory().listItems();
                    }
                    break;
                case "truhe":
                    if (Dungeon.getDungeon().getCurrentRaum().getInventory().hasItem("Truhe")){
                        StorageItem truhe = (StorageItem) Dungeon.getDungeon().itemMap.get(Consts.TRUHE);
                        truhe.listItems();
                    } else {
                        printText("Hier ist keine Truhe");
                    }
                    break;
                default:
                    if (Dungeon.getDungeon().getCurrentRaum().getNumber() == 3) {
                        Item item = Dungeon.getDungeon().itemMap.get(Consts.FACKEL);
                        if (item instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item;
                            if (fackel.getState() == true) {
                                Item itemUSU = Dungeon.getDungeon().itemMap.get(parsed_command[1].toUpperCase());
                                if (itemUSU == null) {
                                    printText("Das Objekt gibt es nicht.");
                                } else {
                                    itemUSU.untersuchen();
                                }
                            } else {
                                printText("Du kannst nichts sehen!");
                            }
                        }
                    } else {
                        Item itemUSU = Dungeon.getDungeon().itemMap.get(parsed_command[1].toUpperCase());
                        if (itemUSU == null) {
                            printText("Das Objekt gibt es nicht.");
                        } else {
                            itemUSU.untersuchen();
                        }
                    }
            }
        } else {
            printText("Was soll untersucht werden?");
        }
    }

    static void doBenutze(Item item) {
        if (item == null) {

            printText("Das Item gibt es nicht.");

        } else {
            if (item.isPickable() == false || Dungeon.getDungeon().player.getInventory().hasItem(item.getName())) {
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
                        int fackelSlot = Dungeon.getDungeon().player.getInventory().findItem(Dungeon.getDungeon().player.getInventory().findItemByName("Fackel"));
                        int feuerZeugSlot = Dungeon.getDungeon().player.getInventory().findItem(Dungeon.getDungeon().player.getInventory().findItemByName("Feuerzeug"));
                        if (feuerZeugSlot < 0) {
                            printText("Du hast kein Feuerzeug.");
                            break;
                        } else if (fackelSlot < 0) {
                            printText("Du hast keine Fackel.");
                            break;
                        } else {
                            printText("Du zündest deine Fackel mit dem Feuerzeug an.");
                            Item item2 = Dungeon.getDungeon().itemMap.get("FACKEL");
                            if (item2 instanceof ToggleItem) {
                                ToggleItem fackel = (ToggleItem) item2;
                                fackel.setState(true);
                            }
                            break;
                        }
                    case "Falltür":
                        Item item5 = Dungeon.getDungeon().itemMap.get(Consts.FACKEL);
                        if (item5 instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item5;
                            if (fackel.getState() == true && Dungeon.getDungeon().getCurrentRaum().getNumber() == 3) {
                                Item itemToUse = Dungeon.getDungeon().itemMap.get(itemName.toUpperCase());
                                if (itemToUse == null) {
                                    printText("Das Objekt gibt es nicht.");
                                    break;
                                } else {
                                    if (Dungeon.getDungeon().getCurrentRaum().getInventory().hasItem("Falltür")) {
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
                    case "Sack":
                        Item sack = Dungeon.getDungeon().itemMap.get(itemName.toUpperCase());
                        sack.benutzen();
                        Dungeon.getDungeon().player.getInventory().getInventory().remove(Dungeon.getDungeon().player.getInventory().findItemByName("Sack"));
                        Dungeon.getDungeon().player.getInventory().setInventorySize(2);
                        break;
                    case "Schalter":
                        ToggleItem schalter = (ToggleItem) Dungeon.getDungeon().itemMap.get(itemName.toUpperCase());
                        schalter.benutzen();
                        schalter.setState(true);
                        break;
                    case "Schwert":
                        Dungeon.getDungeon().itemMap.get(Consts.SCHWERT).benutzen();
                        ende();
                        break;
                    case "Schlüssel":
                        StorageItem truhe = (StorageItem) Dungeon.getDungeon().getCurrentRaum().getInventory().findItemByName("Truhe");
                        if (Dungeon.getDungeon().getCurrentRaum().getInventory().hasItem("Truhe")) {
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
                    case "Karte":
                        Karte karte;
                        if (Dungeon.getDungeon().itemMap.get(Consts.KARTE).isKarte() == true) {
                            karte = (Karte) Dungeon.getDungeon().itemMap.get(Consts.KARTE);
                            karte.setBenutzeText(karte.readMap()); //NOTE Muss vor dem default stehen!
                        } else System.err.println("Fehler in der Karte!");
                    default:
                        if (Dungeon.getDungeon().getCurrentRaum().getNumber() == 3) {
                            item5 = Dungeon.getDungeon().itemMap.get(Consts.FACKEL);
                            if (item5 instanceof ToggleItem) {
                                ToggleItem fackel = (ToggleItem) item5;
                                if (fackel.getState() == true) {
                                    Item itemToUse = Dungeon.getDungeon().itemMap.get(itemName.toUpperCase());
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
                            Item itemToUse = Dungeon.getDungeon().itemMap.get(itemName.toUpperCase());
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

    static void printHelp() {
        printText("Mögliche Befehle:");
        printText("\thilfe -> Zeigt diese Hilfe");
        printText("\tnimm [gegenstand] -> Gegenstand zum Inventar hinzufügen");
        printText("\tbenutze [gegenstand] -> Gegenstand benutzen");
        printText("\tuntersuche [gegenstand/raum/inventar] -> Gegenstand, Raum oder Inventar untersuchen");
        printText("\tvernichte [gegenstand] -> Gegenstand aus dem Inventar löschen");
        printText("\tgehe [nord/süd/ost/west] -> In eine Richtung gehen");
    }

    public static void doSpeichern() {

        try (
                OutputStream file = new FileOutputStream("savegame.save");
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer);
        ){
            output.writeObject(Dungeon.getDungeon());
            output.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        printText("Gespeichert!");
    }

    public static void doLaden() {

        try(
                InputStream file = new FileInputStream("savegame.save");
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream (buffer);
        ){
            //deserialize the List
            Dungeon loadedDungeon = (Dungeon)input.readObject();
            Dungeon.setDungeon(loadedDungeon);

        }
        catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        catch (Exception ex){
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


    //InventarKram
    public static boolean addItemFromChestToInventory(Item item) {
        StorageItem dieTruhe = (StorageItem) Dungeon.getDungeon().itemMap.get(Consts.TRUHE);
        if (Dungeon.getDungeon().player.getInventory().getInventory().size() < Dungeon.getDungeon().player.getInventory().getInventorySize() && dieTruhe.hasItem(item)) {
            Dungeon.getDungeon().player.getInventory().getInventory().add(item);
            dieTruhe.removeItem(item);
            return true;
        } else {
            return false;
        }
    }

    public static boolean giveItem(Item item) {
        if (Dungeon.getDungeon().player.getInventory().getInventory().remove(item)) {
            return true;
        }
        return false;
    }

    //HumanKram
    static void doReden() {
        if (Dungeon.getDungeon().currentHuman.isQuestDone() == true) {
            if (Dungeon.getDungeon().currentHuman.isGaveItem() == true) {
                if (recieveItem(Dungeon.getDungeon().currentHuman.getRewarditem())) {
                    printText("Hier, bitte schön.");
                    Dungeon.getDungeon().currentHuman.setGaveItem(false);
                } else {
                    printText("Dein Inventar ist leider voll. Komm wieder, wenn du Platz hast.");
                    Dungeon.getDungeon().currentHuman.setGaveItem(true);
                }
            } else {
                switch (dialogNumber) {
                    case 0:
                        printText(Dungeon.getDungeon().currentHuman.getDialog1());
                        dialogNumber = 1;
                        break;
                    case 1:
                        printText(Dungeon.getDungeon().currentHuman.getDialog2());
                        dialogNumber = 0;
                        break;
                }
            }
        } else {
            printText(Dungeon.getDungeon().currentHuman.getQuestText());
        }
    }

    public static void doGeben(String[] parsed_command, int count) {
        if (count == 2) {
            String itemToUse = IOUtils.convertToName(parsed_command[1]);
            if (itemToUse.equals(Dungeon.getDungeon().currentHuman.getQuestItem().getName())) {
                if (giveItem(Dungeon.getDungeon().itemMap.get(parsed_command[1].toUpperCase()))) {
                    printText(Dungeon.getDungeon().currentHuman.getQuestDoneText());
                    Dungeon.getDungeon().currentHuman.setQuestDone(true);
                    if (recieveItem(Dungeon.getDungeon().currentHuman.getRewarditem())) {
                        printText("Im Gegenzug bekommst du von mir auch etwas. Bitteschön.");
                    } else {
                        printText("Dein Inventar ist leider voll. Komm wieder, wenn du Platz hast.");
                        Dungeon.getDungeon().currentHuman.setGaveItem(true);
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

    public static boolean recieveItem(Item item) {
        if (Dungeon.getDungeon().player.getInventory().getInventory().size() < Dungeon.getDungeon().player.getInventory().getInventorySize()) {
            Dungeon.getDungeon().player.getInventory().getInventory().add(item);
            return true;
        } else {
            return false;
        }
    }
}
