
package de.micromata.azubi;

/*
 *  Das ist der GameMaster
 *  Der Spieler darf entscheiden, was er tun möchte, doch der GameMaster entscheidet, was geschiet.
 */

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class Textie {
    public static boolean diag;
    public static String savegame;

    public static void main(String[] args) {

        try {
            if(args[0].equals("--diag")){
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
        return true;
    }

    public static void warten(boolean withPrompt) {
        if (withPrompt == true) {
            Dungeon.getDungeon().player.prompt();
        } else {
            System.out.println("Was willst du tun? ");
            Runnable warten = new Runnable() {
                @Override
                public void run() {
                    do {
                    } while (Dungeon.getDungeon().currentRaum.isFinished() == 0);
                }
            };

            Thread thread = new Thread(warten);
            thread.start();
        }
    }



    //Befehlsverarbeitung
    public static void executeCommand(String[] parsed_command, String[] parsed_args) {
        if (Dungeon.getDungeon().currentRaum == null) {
            System.err.println("currentRaum nicht da");
            // Kein raum nichts tun
            return;
        }
        int count = 0;
        int args = 0;
        for (String aParsed_command : parsed_command) {
            if(aParsed_command != null) {
                count++;
            }
        }
        for (String parsed_arg : parsed_args) {
            if(parsed_arg != null) {
                args++;
            }
        }
        if(parsed_command.length < 2) {

        } else {
            Item itemToUse = Dungeon.getDungeon().itemMap.get(parsed_command[1].toUpperCase());
            switch (parsed_command[0]) {
                case Command.HILFE:
                    printHelp();
                    break;
                case Command.NIMM:
                    if(args > 1) { // (ACHTUNG: auch bei "nimm blauen hut" wird mehr als ein Argument erkannt)
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
                    doGehen(parsed_command, count);
                    break;
                case Command.REDE:
                    doReden();
                    break;
                case Command.GIB:
                    if(Dungeon.getDungeon().currentHuman != null) {
                     doGeben(parsed_command, count);
                    }
                    else{
                        System.out.println("Hier gibt es niemandem, dem du etwas geben könntest");
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
        if(Textie.diag == true){
            System.out.println(Dungeon.getDungeon().currentRaum == null ? text : "[" + Dungeon.getDungeon().currentRaum.roomNumber + "], " + text);
        } else {
            System.out.println(text);
        }
    }

    static void doGehen(String[] parsed_command, int count) {
        if (count == 2) {

                if(Dungeon.getDungeon().currentRaum.ausgang.equals(parsed_command[1])){
                    if(Dungeon.getDungeon().currentRaum.getNumber() == 4){
                        if(Dungeon.getDungeon().itemMap.get(Consts.SCHALTER).isToggle()){
                            ToggleItem schalter = (ToggleItem) Dungeon.getDungeon().itemMap.get(Consts.SCHALTER);
                            if(schalter.getState()){
                                Dungeon.getDungeon().currentRaum.setFinished(1);
                                System.out.println("Du siehst eine Tür und gehst die Treppe dahinter hinauf.");
                                Karte karte;
                                if (Dungeon.getDungeon().itemMap.get(Consts.KARTE).isKarte() == true) {
                                    karte = (Karte) Dungeon.getDungeon().itemMap.get(Consts.KARTE);
                                    karte.writeMap(Dungeon.getDungeon().currentRaum.getNumberAsString(), parsed_command[1].toUpperCase());
                                }
                            }
                            else{
                                Dungeon.getDungeon().currentRaum.setFinished(0);
                                System.out.println("Da ist eine Tür, du versuchst sie zu öffnen, doch es geht nicht.");
                            }
                        }

                    }
                    else{
                    Dungeon.getDungeon().currentRaum.setFinished(1);
                        System.out.println("Du siehst eine Tür und gehst die Treppe dahinter hinauf.");
                        Karte karte;
                        if (Dungeon.getDungeon().itemMap.get(Consts.KARTE).isKarte() == true) {
                            karte = (Karte) Dungeon.getDungeon().itemMap.get(Consts.KARTE);
                            karte.writeMap(Dungeon.getDungeon().currentRaum.getNumberAsString(), parsed_command[1].toUpperCase());
                        }
                    }
                }
                else if(Dungeon.getDungeon().currentRaum.eingang.equals(parsed_command[1])){
                    if(Dungeon.getDungeon().currentRaum.getNumber() == 1){
                        if(Dungeon.getDungeon().itemMap.get(Consts.SCHALTER).isToggle()){
                            ToggleItem schalter = (ToggleItem) Dungeon.getDungeon().itemMap.get(Consts.SCHALTER);
                            if(schalter.getState()){
                                Dungeon.getDungeon().currentRaum.setFinished(-1);
                                System.out.println("Du siehst eine Tür und gehst die Treppe dahinter hinab.");
                                Karte karte;
                                if (Dungeon.getDungeon().itemMap.get(Consts.KARTE).isKarte() == true) {
                                    karte = (Karte) Dungeon.getDungeon().itemMap.get(Consts.KARTE);
                                    karte.writeMap(Dungeon.getDungeon().currentRaum.getNumberAsString(), parsed_command[1].toUpperCase());
                                }
                            }
                            else{
                                Dungeon.getDungeon().currentRaum.setFinished(0);
                                System.out.println("Da ist eine Tür, du versuchst sie zu öffnen, doch es geht nicht.");
                            }
                        }

                    }
                    else {
                        Dungeon.getDungeon().currentRaum.setFinished(-1);
                        System.out.println("Du siehst eine Tür und gehst die Treppe dahinter hinab.");
                        Karte karte;
                        if (Dungeon.getDungeon().itemMap.get(Consts.KARTE).isKarte() == true) {
                            karte = (Karte) Dungeon.getDungeon().itemMap.get(Consts.KARTE);
                            karte.writeMap(Dungeon.getDungeon().currentRaum.getNumberAsString(), parsed_command[1].toUpperCase());
                        }
                    }
                }
                else{
                    goWall();
                    Dungeon.getDungeon().currentRaum.setFinished(0);
                }






              /*
               *           switch (parsed_command[1]) {
               *  case "nord":
               *     goNorth();
               *    break;
               * case "west":
               *     goWest();
               *     break;
               * case "ost":
               *     goEast();
               *     break;
               * case "süd":
               *     goSouth();
               *     break;
               *     }
               */

        } else {
            printText("Wohin gehen?");
        }
    }

    static void doVernichte(Item item, int count) {
        if (count == 2) {
            if (removeItemFromInventory(item)) {
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
                    if (Dungeon.getDungeon().currentRaum.getNumber() == 3) {
                        Item item = Dungeon.getDungeon().itemMap.get(Consts.FACKEL);
                        if (item instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item;
                            if (fackel.getState() == true) {
                                Dungeon.getDungeon().currentRaum.listItems();
                            } else {
                                printText("Du kannst nichts sehen!");
                            }
                        }
                    } else {
                        Dungeon.getDungeon().currentRaum.listItems();
                    }
                    break;
                case "inventar":
                    if (Dungeon.getDungeon().currentRaum.getNumber() == 3) {
                        Item item = Dungeon.getDungeon().itemMap.get(Consts.FACKEL);
                        if (item instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item;
                            if (fackel.getState() == true) {
                                listInventory();
                            } else {
                                printText("Du kannst nichts sehen!");
                            }
                        }
                    } else {
                        listInventory();
                    }
                    break;
                case "truhe":
                    if (isInRoom(Dungeon.getDungeon().itemMap.get(Consts.TRUHE))) {
                        StorageItem truhe = (StorageItem) Dungeon.getDungeon().itemMap.get(Consts.TRUHE);
                        truhe.listItems();
                    } else {
                        printText("Hier ist keine Truhe");
                    }
                    break;
                default:
                    if (Dungeon.getDungeon().currentRaum.getNumber() == 3) {
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
            if(item.isPickable() == false || isInInventory(item)){
                String itemName = item.getName();
                if(Textie.diag == true){
                    printText("Du willst " + itemName + " benutzen");
                }
                switch (itemName) {
                    // Fackel und Feuerzeug sind besonders, da sie auch funktionen
                    // aufrufen
                    // und nicht nur einen Text ausgeben. Außerdem sollen diese Items
                    // benutzbar sein, selbst wenn der Raum dunkel ist.
                    case "Fackel":// Dungeon.getDungeon().itemMap.get("FACKEL").getName():
                    case "Feuerzeug": // Dungeon.getDungeon().itemMap.get("FEUERZEUG").getName():
                        int fackelSlot = findInInventory(Dungeon.getDungeon().itemMap.get(Consts.FACKEL));
                        int feuerZeugSlot = findInInventory(Dungeon.getDungeon().itemMap.get(Consts.FEUERZEUG));
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
                                if (fackel.getState() == true && Dungeon.getDungeon().currentRaum.getNumber() == 3) {
                                    Item itemToUse = Dungeon.getDungeon().itemMap.get(itemName.toUpperCase());
                                    if (itemToUse == null) {
                                        printText("Das Objekt gibt es nicht.");
                                        break;
                                    } else {
                                        if (isInRoom(Dungeon.getDungeon().itemMap.get(Consts.FALLTÜR))){
                                            printText("Du schlüpfst durch die Falltür in den darunterliegenden Raum.");
                                            Karte karte;
                                            if (Dungeon.getDungeon().itemMap.get(Consts.KARTE).isKarte() == true) {
                                                karte = (Karte) Dungeon.getDungeon().itemMap.get(Consts.KARTE);
                                                karte.writeMap(Dungeon.getDungeon().currentRaum.getNumberAsString(), "FALLTÜR");
                                            }
                                            Dungeon.getDungeon().currentRaum.setFinished(1);

                                            break;
                                        }
                                        // else if
                                        // (find(Dungeon.getDungeon().itemMap.get(Consts.FALLTÜR)) !=
                                        // -128) {
                                        // printText("Da ist eine Falltür. Du hast das Gefühl, nicht alles erledigt zu haben.");
                                        // break;
                                        // }
                                    }
                                } else {
                                    printText("Du kannst nichts sehen!");
                                    break;
                                }
                            }
                    case "Sack":
                        Item sack = Dungeon.getDungeon().itemMap.get(itemName.toUpperCase());
                        sack.benutzen();
                        removeItemFromInventory(Dungeon.getDungeon().itemMap.get(Consts.SACK));
                        Dungeon.getDungeon().inventory.setInventorySize(2);
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
                        StorageItem truhe = (StorageItem) Dungeon.getDungeon().itemMap.get(Consts.TRUHE);
                        if (Dungeon.getDungeon().currentRaum.hasItem(Dungeon.getDungeon().itemMap.get(Consts.TRUHE))) {
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
                        }
                        else System.err.println("Fehler in der Karte!");
                    default:
                        if (Dungeon.getDungeon().currentRaum.getNumber() == 3) {
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
            }
            else{
                System.out.println("Du musst das Item im Inventar haben.");
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
                if (addItemToInventory(item)) {

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

    static void goWall() {
        printText("Du bist gegen die Wand gelaufen.");
    }

    static void goWest() {
        if(Dungeon.getDungeon().currentRaum.ausgang == "west"){
            Dungeon.getDungeon().currentRaum.setFinished(1);
        }

        goWall();
    }

    static void goEast() {
        goWall();
    }

    static void goNorth() {
        goWall();
    }

    static void goSouth() {
        goWall();

    }

    public static void doSpeichern(){
        JSONSerializer serializer = new JSONSerializer();
        savegame = serializer.include("Human", "Item", "ToggleItem", "Karte", "StorageItem", "Raum", "Inventory", "Player", "raums").serialize(Dungeon.getDungeon());
        IOUtils.writeInFile(savegame);
        System.out.println("Gespeichert!");
    }

    public static void doLaden() {
        savegame = IOUtils.readFromFile();
        if(savegame != null) {
            //Dungeon.setDungeon(new JSONDeserializer<Dungeon>().deserialize(savegame));
            Dungeon dungeon1 = new JSONDeserializer<Dungeon>().deserialize(savegame);
            System.out.println("Geladen!");
            System.out.println("Raum: " + dungeon1.currentRaum.getNumber());
        }
        else{
            System.out.println("Wer nicht speichert, kann nichts laden");
        }
    }



    //RaumKram
    public static boolean removeItemInRoom(Item item) {
        if (Dungeon.getDungeon().currentRaum.items.remove(item))
            return true;
        return false;
    }

    public static boolean addItemToRoom(Item item) {
        if (Dungeon.getDungeon().currentRaum.items.add(item)) {
            return true;
        }

        return false;
    }

    public static boolean isInRoom(Item item){
        return findInRoom(item) >= 0;
    }

    /**
     * @param item
     * @return Returns -128 when the item is not in the room
     */
    public static int findInRoom(Item item) {
        int i = -128;
        i = Dungeon.getDungeon().currentRaum.items.indexOf(item);
        return i;
    }



    //InventarKram
    public static boolean addItemToInventory(Item item) {
        if(Dungeon.getDungeon().inventory.getInventory().size() < Dungeon.getDungeon().inventory.getInventorySize() && Dungeon.getDungeon().currentRaum.hasItem(item)) {
            Dungeon.getDungeon().inventory.getInventory().add(item);
            removeItemInRoom(item);
            return true;
        } else {
            return false;
        }
    }

    public static boolean addItemFromChestToInventory(Item item) {
        StorageItem dieTruhe = (StorageItem) Dungeon.getDungeon().itemMap.get(Consts.TRUHE);
        if(Dungeon.getDungeon().inventory.getInventory().size() < Dungeon.getDungeon().inventory.getInventorySize() && dieTruhe.hasItem(item)){
            Dungeon.getDungeon().inventory.getInventory().add(item);
            dieTruhe.removeItem(item);
            return true;
        } else {
            return false;
        }
    }

    public static boolean removeItemFromInventory(Item item) {
        if(Dungeon.getDungeon().inventory.getInventory().remove(item) && addItemToRoom(item))
            return true;
        return false;
    }

    public static void listInventory() {
        if(Dungeon.getDungeon().inventory.getInventory().size() > 0) {
            System.out.println("In deiner Tasche befindet sich:");
            for (Item items : Dungeon.getDungeon().inventory.getInventory()) {
                String objectName = items.getName();
                System.out.println("\t" + objectName);
            }
        } else {
            System.out.println("Deine Tasche ist leer.");
        }
    }

    public static boolean isInInventory(Item items) {
        return findInInventory(items) >= 0;
    }

    public static int findInInventory(Item items) {
        int slot = -128;
        slot = Dungeon.getDungeon().inventory.getInventory().indexOf(items);
        if(slot > -128) {
            return slot;
        }
        return -128;
    }

    public static boolean giveItem(Item item) {
        if(Dungeon.getDungeon().inventory.getInventory().remove(item)) {
            return true;
        }
        return false;
    }





    //HumanKram
    static  void doReden() {
        if(Dungeon.getDungeon().currentHuman.isQuestDone() == true) {
            if(Dungeon.getDungeon().currentHuman.isGaveItem() == true) {
                if(recieveItem(Dungeon.getDungeon().currentHuman.getRewarditem())) {
                    System.out.println("Hier, bitte schön.");
                    Dungeon.getDungeon().currentHuman.setGaveItem(false);
                } else {
                    System.out.println("Dein Inventar ist leider voll. Komm wieder, wenn du Platz hast.");
                    Dungeon.getDungeon().currentHuman.setGaveItem(true);
                }
            } else {
                int dialogNumber = 0;
                switch (dialogNumber) {
                    case 0:
                        System.out.println(Dungeon.getDungeon().currentHuman.getDialog1());
                        dialogNumber = 1;
                        break;
                    case 1:
                        System.out.println(Dungeon.getDungeon().currentHuman.getDialog2());
                        dialogNumber = 0;
                        break;
                }
            }
        } else {
            System.out.println(Dungeon.getDungeon().currentHuman.getQuestText());
        }
    }

    public static void doGeben(String[] parsed_command, int count) {
        if(count == 2) {
            String itemToUse = IOUtils.convertToName(parsed_command[1]);
            if(itemToUse.equals(Dungeon.getDungeon().currentHuman.getQuestItem().getName())) {
                if(giveItem(Dungeon.getDungeon().itemMap.get(parsed_command[1].toUpperCase()))) {
                    System.out.println(Dungeon.getDungeon().currentHuman.getQuestDoneText());
                    Dungeon.getDungeon().currentHuman.setQuestDone(true);
                    if(recieveItem(Dungeon.getDungeon().currentHuman.getRewarditem())) {
                        System.out.println("Im Gegenzug bekommst du von mir auch etwas. Bitteschön.");
                    } else {
                        System.out.println("Dein Inventar ist leider voll. Komm wieder, wenn du Platz hast.");
                        Dungeon.getDungeon().currentHuman.setGaveItem(true);
                    }
                } else {
                    System.out.println("Item nicht im Inventar.");
                }
            } else {
                System.out.println("Das brauche ich nicht.");
            }
        } else {
            System.out.println("Zu wenig Argumente");
        }
    }

    public static boolean recieveItem(Item item) {
        if(Dungeon.getDungeon().inventory.getInventory().size() < Dungeon.getDungeon().inventory.getInventorySize()) {
            Dungeon.getDungeon().inventory.getInventory().add(item);
            return true;
        } else {
            return false;
        }
    }





}
