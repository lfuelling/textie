
package de.micromata.azubi;

import de.micromata.azubi.builder.*;
import de.micromata.azubi.model.*;

import java.io.*;

/**
 * @author Lukas F&uuml;lling (l.fuelling@micromata.de)
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

        //Dungeon dungeon = Dungeon.getDungeon();
        Dungeon dungeon = init();
        dungeon.runGame(true);
        System.exit(0);
    }

    private static Dungeon init() {
        PlayerBuilder fremder = new PlayerBuilder().addName("Fremder").add(new InventarBuilder().build()).build();
        DungeonBuilder dungeonBuilder = new DungeonBuilder().add(fremder);
        RaumBuilder raum1 = new RaumBuilder().addRoomNumber(1).addwillkommensNachricht("Du befindest dich in einem dunklen Raum. Nach einiger Zeit gewöhnen sich deine Augen an die Dunkelheit.")
                .addInventory(new InventarBuilder()
                        .addItem(new ToggleItemBuilder().setState(false).setName("Fackel").setPickable(true).setUntersucheText("Du betrachtest die Fackel. Wie kann man die wohl anzünden?").setBenutzeText("Du zündest deine Fackel mit dem Feuerzeug an.").build())
                        .addItem(new ItemBuilder().setName("Handtuch").setPickable(true).setUntersucheText("Das Handtuch sieht sehr flauschig aus.").setBenutzeText("Du wischst dir den Angstschweiß von der Stirn.").build())
                        .addItem(new StorageItemBuilder().setLockState(true).setInventarBuilder(new InventarBuilder().build()).setName("Truhe").setPickable(false).setUntersucheText("Die Truhe ist verschlossen. Es sieht nicht so aus, als könnte man sie aufbrechen.").setBenutzeText("Du kannst die Truhe nicht öffnen.").build())
                        .addItem(new ToggleItemBuilder().setState(false).setName("Schalter").setPickable(false).setUntersucheText("Da ist ein kleiner Schalter an der Wand.").setBenutzeText("Du hörst ein Rumpeln, als du den Schalter drückst.").build())
                        .build())
                .build();

        RaumBuilder raum2 = new RaumBuilder().addRoomNumber(2).addwillkommensNachricht("Du kommst in einen dunklen Raum.")
                .addInventory(new InventarBuilder()
                        .addItem(new ItemBuilder().setName("Stein").setPickable(true).setUntersucheText("Du betrachtest den Stein. Er wirkt kalt.").setBenutzeText("Du wirfst den Stein vor dir auf den Boden und hebst ihn wieder auf. Was ein Spaß.").build())
                        .addItem(new ItemBuilder().setName("Schwert").setPickable(true).setUntersucheText("Du betrachtest das Schwert. Es sieht sehr scharf aus.").setBenutzeText("Du stichst dir das Schwert zwischen die Rippen und stirbst.").build())
                        .addItem(new ItemBuilder().setName("Feuerzeug").setPickable(true).setUntersucheText("Du betrachtest das Feuerzeug. Es wirkt zuverlässig.").setBenutzeText("Du zündest deine Fackel mit dem Feuerzeug an.").build()).build())
                .build();

        RaumBuilder raum3 = new RaumBuilder().addRoomNumber(3).addwillkommensNachricht("Es ist zu dunkel, um etwas zu sehen. Ein seltsamer Geruch liegt in der Luft.")
                .addInventory(new InventarBuilder()
                        .addItem(new ItemBuilder().setName("Falltür").setPickable(false).setUntersucheText("Da ist eine Falltür").setBenutzeText("Du schlüpfst durch die Falltür in den darunterliegenden Raum.").build())
                        .addItem(new ItemBuilder().setName("Whiteboard").setPickable(false).setUntersucheText("Es steht \'FLIEH!\' mit Blut geschrieben darauf.").setBenutzeText("Das fasse ich bestimmt nicht an!").build())
                        .addItem(new ItemBuilder().setName("Brecheisen").setPickable(true).setUntersucheText("Da ist ein Brecheisen, es ist \"Gordon\" eingeritzt.").setBenutzeText("Du kratzt dich mit dem Brecheisen am Kopf").build())
                        .addItem(new ItemBuilder().setName("Quietscheente").setPickable(true).setUntersucheText("Die Ente schaut dich vorwurfsvoll an.").setBenutzeText("Die Ente schaut dich vorwurfsvoll an und quietscht leise, als du sie zusammendrückst.").build())
                        .build())
                .build();

        RaumBuilder raum4 = new RaumBuilder().addRoomNumber(4).addwillkommensNachricht("Du kommst in einen hell erleuchteten Raum. Ein alter Mann lehnt an der Wand.")
                .addHuman(new HumanBuilder().setHumanName("Gordon").setDialog1("Hast du die Truhe gesehen? Ich frage mich, was da wohl drin ist...").setDialog2("...").setQuestDoneText("Sehr gut. Danke dir.").setQuestText("Ich suche ein Brecheisen. Hast du eins?").setQuestItem("Brecheisen").setRewarditem(new ItemBuilder().setName("Schlüssel").setPickable(true).setUntersucheText("Du betrachtest den Schlüssel. Was kann man damit wohl aufschließen?").setBenutzeText("Hier gibt es nichts um den Schlüssel zu benutzen.").build()).build())
                .addInventory(new InventarBuilder()
                        .addItem(new ItemBuilder().setName("Sack").setPickable(true).setUntersucheText("Du betrachtest den Sack. Vielleicht kannst du ihn ja an deinem Rucksack befestigen.").setBenutzeText("Du bindest den Sack an deinen Rucksack.").build())
                        .addItem(new ToggleItemBuilder().setState(false).setName("Schalter").setUntersucheText("Da ist ein kleiner Schalter an der Wand.").setBenutzeText("Du hörst ein Rumpeln, als du den Schalter drückst.").build())
                        .addItem(new KartenBuilder().setName("Karte").setPickable(true).setUntersucheText("Das ist eine Karte, sie zeigt deinen Laufweg.").build()).build())
                .build();

        RaumBuilder raum5 = new RaumBuilder().addRoomNumber(5).addwillkommensNachricht("Du kommst in einen Raum, in dem eine Junge steht.")
                 .addHuman(new HumanBuilder().setHumanName("Junge").setDialog1("Ich suche meine Mutter.").setDialog2("Finde sie!").setQuestDoneText("Danke").setQuestText("Hier ein Brief bring ihn zu einer Frau.").setQuestItem("Handtuch").setRewarditem(new ItemBuilder().setName("Brief").setPickable(true).setBenutzeText("Bringe den Brief zu einer Frau").setUntersucheText("Ein Brief adressiert an eine Frau.").build()).build())
                 .addInventory(new InventarBuilder().build()).build();

        RaumBuilder raum6 = new RaumBuilder().addRoomNumber(6).addwillkommensNachricht("Du kommst in einen Raum mit einer Truhe.")
                .addInventory(new InventarBuilder().addItem(new StorageItemBuilder().setLockState(false).setInventarBuilder(new InventarBuilder().addItem(new ItemBuilder().setName("Axt").setPickable(true).setBenutzeText("Du schlägst mit der Axt zu.").setUntersucheText("Eine scharfe Axt.").build()).build()).setName("Truhe").setBenutzeText("Du versuchst die Truhe zu öffnen.").setUntersucheText("Ein große Truhe aus Holz.")).build()).build();

        RaumBuilder raum7 = new RaumBuilder().addRoomNumber(7).addwillkommensNachricht("Du kommst in einen Raum, eine Frau steht mitten im Raum.")
               .addHuman(new HumanBuilder().setHumanName("Frau").setDialog1("Du hast mein Sohn gesehen ?").setDialog2("Wo ?").setQuestDoneText("Danke, Hier ein Seil für dich.").setQuestItem("Brief").setRewarditem(new ItemBuilder().setName("Seil").setPickable(true).setUntersucheText("Ein stabiles Seil.").setBenutzeText("Du bindest das Seil fest.").build()).build())
                .addInventory(new InventarBuilder().addItem(new ToggleItemBuilder().setState(false).setBenutzeText("Du hörst ein Rumpeln, als du den Schalter drückst.").setName("Schalter").setPickable(false).setUntersucheText("Da ist ein kleiner Schalter an der Wand.").build()).build().build());

        raum1.addDoor(new DoorBuilder().setRichtung(Richtung.SUED).setNextRoom(raum2.get()).setLock(false).build())
                .addDoor(new DoorBuilder().setRichtung(Richtung.WEST).setNextRoom(raum4.get()).setLock(true).build()).build();

        raum2.addDoor(new DoorBuilder().setRichtung(Richtung.WEST).setNextRoom(raum3.get()).setLock(false).build())
                .addDoor(new DoorBuilder().setRichtung(Richtung.NORD).setNextRoom(raum1.get()).setLock(false).build()).build();

        raum3.addDoor(new DoorBuilder().setRichtung(Richtung.FALLTUER).setNextRoom(raum4.get()).build())
                .addDoor(new DoorBuilder().setRichtung(Richtung.OST).setNextRoom(raum2.get()).build()).build();

        raum4.addDoor(new DoorBuilder().setRichtung(Richtung.OST).setNextRoom(raum1.get()).setLock(true).build()).addDoor(new DoorBuilder().setRichtung(Richtung.WEST).setLock(false).setNextRoom(raum5.get()).build()).addDoor(new DoorBuilder().setNextRoom(raum7.get()).setLock(true).setRichtung(Richtung.NORD).build()).build();

        raum5.addDoor(new DoorBuilder().setRichtung(Richtung.FALLTUER).setNextRoom(raum6.get()).setLock(false).build()).addDoor(new DoorBuilder().setLock(false).setNextRoom(raum4.get()).setRichtung(Richtung.OST).build()).build();


        //raum6.addDoor(new DoorBuilder().setRichtung(Richtung.OST).setLock(false).setNextRoom(raum7.get()).build()).build();

        raum7.addDoor(new DoorBuilder().setRichtung(Richtung.SUED).setLock(true).setNextRoom(raum4.get()).build()).addDoor(new DoorBuilder().setNextRoom(raum6.get()).setRichtung(Richtung.WEST).setLock(false).build()).build();
        dungeonBuilder.addRoom(raum1).addRoom(raum2).addRoom(raum3).addRoom(raum4).addRoom(raum5).addRoom(raum6).addRoom(raum7);

        return dungeonBuilder.build().get();
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
     * @see de.micromata.azubi.model.Dungeon#runGame(boolean)
     */
    public static void warten(boolean withPrompt) {
        if (withPrompt == true) {


            Dungeon.getDungeon().getPlayer().prompt();
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
            System.out.println(Dungeon.getDungeon().getCurrentRaum() == null ? text : "[" + Dungeon.getDungeon().getCurrentRaum().getRoomNumber() + "], " + text);
        } else {
            System.out.println(text);
        }

        lastPrintedText = text;
    }

    /**
     * Lets you walk.
     *
     * @param richtung the direction you want to go.
     */
    static void doGehen(Richtung richtung) {
        if (Dungeon.getDungeon().getCurrentRaum().getRoomNumber() == 6 && Dungeon.getDungeon().getCurrentRaum().getNextRoom(Dungeon.getDungeon().getCurrentRaum().findDoorByDirection(richtung)) == null) {
            printText("Der Weg wird durch eine Holzbarrikade versperrt.");
        } else {
            int roomNumber = Dungeon.getDungeon().getCurrentRaum().getRoomNumber();
            Raum nextRoom = Dungeon.getDungeon().getRaum(richtung);
            if (nextRoom != null && Dungeon.getDungeon().findRaumByNummer(roomNumber).isLeaveRoom()) {
                Dungeon.getDungeon().setRoomNumber(nextRoom);
                Dungeon.getDungeon().getCurrentRaum().setLeaveRoom(false);
                Textie.printText(Dungeon.getDungeon().getCurrentRaum().getWillkommensNachricht());
            }
        }


    }

    /**
     * Throws away an Item.
     *
     * @param item  The item to throw away.
     * @param count The size of the parsed_command String[]
     */
    static void doVernichte(Item item, int count) {
        if (count == 2) {
            if (Dungeon.getDungeon().getPlayer().getInventory().transferItem(Dungeon.getDungeon().getCurrentRaum().getInventory(), item)) {
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
     * Lets you inspect an item.
     *
     * @param parsed_command The String[]
     * @param count          THe size of the String[]
     */
    static void doUntersuche(String[] parsed_command, int count) {
        if (count == 2) {
            switch (parsed_command[1].toLowerCase()) {
                case "raum":
                    if (Dungeon.getDungeon().getCurrentRaum().getRoomNumber() == 3) {
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
                    if (Dungeon.getDungeon().getCurrentRaum().getRoomNumber() == 3) {
                        Item item = chooseInventory("Fackel");
                        if (item instanceof ToggleItem) {
                            ToggleItem fackel = (ToggleItem) item;
                            if (fackel.getState() == true) {
                                Textie.printText("In deiner Tasche befindet sich:");
                                Dungeon.getDungeon().getPlayer().getInventory().listItems();
                            } else {
                                printText("Du kannst nichts sehen!");
                            }
                        }
                    } else {
                        Textie.printText("In deiner Tasche befindet sich:");
                        Dungeon.getDungeon().getPlayer().getInventory().listItems();
                    }
                    break;
                case "truhe":
                    if (Dungeon.getDungeon().getCurrentRaum().getInventory().hasItem("Truhe")) {
                        StorageItem truhe = (StorageItem) Dungeon.getDungeon().getCurrentRaum().getInventory().findItemByName("Truhe");
                        if (truhe.getLockState() == true) {
                            printText("Die Truhe ist verschlossen.");
                        } else {
                            truhe.getInventory().listItems();
                        }
                    } else {
                        printText("Hier ist keine Truhe");
                    }
                    break;
                default:
                    if (Dungeon.getDungeon().getCurrentRaum().getRoomNumber() == 3) {
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
            Inventory playerInventory = Dungeon.getDungeon().getPlayer().getInventory();
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
                            if (fackel.getState() == true && Dungeon.getDungeon().getCurrentRaum().getRoomNumber() == 3) {
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
                        if (Dungeon.getDungeon().getCurrentRaum().getRoomNumber() == 6) {
                            //FIXME Dungeon.getDungeon().getCurrentRaum().getDoors().add(new Door(11, Richtung.OST, 7, false));
                            axt.benutzen();
                        } else {
                            Textie.printText("Du fuchtelst mit der Axt wild in der Gegend herum");
                        }
                        break;
                    case "Sack":
                        Item sack = item;
                        sack.benutzen();
                        playerInventory.removeItem(playerInventory.findItemByName("Sack"));
                        playerInventory.setInventorySize(2);
                        break;
                    case "Schalter":

                        ToggleItem schalter = (ToggleItem) item;
                        schalter.benutzen();
                        schalter.toggleState();
                        Dungeon.getDungeon().getDoorSchalter().get(schalter).toogleLock();

                        break;
                    case "Schwert":
                        playerInventory.findItemByName("Schwert").benutzen();
                        ende();
                        break;
                    case "Schlüssel":
                        StorageItem truhe = (StorageItem) raumInventar.findItemByName("Truhe");
                        if (raumInventar.hasItem("Truhe")) {
                            if (truhe.getLockState() == true) {
                                truhe.setLockState(false);
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
                        if (Dungeon.getDungeon().getCurrentRaum().getRoomNumber() == 3) {
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
    static public void doTakeFromChest(Item item) {
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
                if (Dungeon.getDungeon().getCurrentRaum().getInventory().transferItem(Dungeon.getDungeon().getPlayer().getInventory(), item)) {

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
    public static void printHelp() {
        printText("Mögliche Befehle:");
        printText("\thilfe -> Zeigt diese Hilfe");
        printText("\tnimm [gegenstand] -> Gegenstand zum Inventar hinzufügen");
        printText("\tnimm [gegenstand] aus truhe -> Gegenstand aus Truhe zum Inventar hinzufügen");
        printText("\tbenutze [gegenstand] -> Gegenstand benutzen");
        printText("\tuntersuche [gegenstand/raum/inventar] -> Gegenstand, Raum oder Inventar untersuchen");
        printText("\tvernichte [gegenstand] -> Gegenstand aus dem Inventar löschen");
        printText("\tgehe [nord/süd/ost/west] -> In eine Richtung gehen");
        printText("\trede [person] -> Rede mit einer Person");
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
            Dungeon.getDungeon().player.setItems(null);
            Dungeon.setDungeon(loadedDungeon);
            //Dungeon.getDungeon().player.setItems(loadedDungeon.player.getItems());
            printText("Geladen!");
            //printText("Raum: " + dungeon1.currentRaum.getRoomNumber());
            printText("Raum:" + Dungeon.getDungeon().getCurrentRaum().getNumberAsString());
        } else {
            printText("Wer nicht speichert, kann nichts laden");
        }
        */
    }

    /**
     * Best name ever.
     *
     * @param item Item to take.
     * @return Returns true, if you can pick up the item.
     */
    public static boolean addItemFromChestToInventory(Item item) {
        StorageItem dieTruhe = (StorageItem) Dungeon.getDungeon().getCurrentRaum().getInventory().findItemByName("Truhe");
        if (Dungeon.getDungeon().getPlayer().getInventory().getSize() < Dungeon.getDungeon().getPlayer().getInventory().getInventorySize() && dieTruhe.getInventory().hasItem(item.getName())) {
            dieTruhe.getInventory().transferItem(Dungeon.getDungeon().getPlayer().getInventory(), item);
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
        if (Dungeon.getDungeon().getPlayer().getInventory().removeItem(item)) {
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
        if (Dungeon.getDungeon().getPlayer().getInventory().getSize() < Dungeon.getDungeon().getPlayer().getInventory().getInventorySize()) {
            Dungeon.getDungeon().getPlayer().getInventory().addItem(item);
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
        if (Dungeon.getDungeon().getPlayer().getInventory().findItemByName(itemName) != null) {
            item = Dungeon.getDungeon().getPlayer().getInventory().findItemByName(itemName);
        } else if (Dungeon.getDungeon().getCurrentRaum().getInventory().findItemByName(itemName) != null) {
            item = Dungeon.getDungeon().getCurrentRaum().getInventory().findItemByName(itemName);
        }
        return item;
    }
}
