package de.micromata.azubi;


/*
 * Der Mensch (Human) ist bis jetzt noch ziemlich beschränkt (nur zwei Dialoge)
 * Wir werden das später evtl. erweitern. Vorerst genügt das. Wichtig ist mir nur, dass die Quests funktionieren.
 */

import java.util.ArrayList;
import java.util.HashMap;

public class Human {
    // Anzahl der ausgegebenen Dialoge
    int dialogNumber = 0;
    private boolean questDone = false;
    private String dialog1;
    private String dialog2;
    private String questText;
    private String questDoneText;
    private String name;
    private boolean giveItem = false;
    //ArrayList<Item> questItems;
    Item questItem;
    Item rewarditem;
    // String questItemName;

    public Human(String name, String dialog1, String dialog2, String questText, String questDoneText, Item rewardItem, Item questItem) {
        this.name = name;
        this.dialog1 = dialog1;
        this.dialog2 = dialog2;
        this.questText = questText;
        this.questDoneText = questDoneText;
        this.questItem = questItem;
      /*  for(Item questItems : questItem){
            this.questItems.add(questItems);
        }*/
        // this.questItemName = questItem.getName();
        this.rewarditem = rewardItem;
    }

    public String getName() {
        return this.name;
    }

    void doReden() {
        if(questDone == true) {
            if(giveItem == true) {
                if(Dungeon.getDungeon().inventory.recieveItem(rewarditem)) {
                    System.out.println("Hier, bitte schön.");
                    giveItem = false;
                } else {
                    System.out.println("Dein Inventar ist leider voll. Komm wieder, wenn du Platz hast.");
                    giveItem = true;
                }
            } else {
                switch (dialogNumber) {
                    case 0:
                        System.out.println(dialog1);
                        dialogNumber = 1;
                        break;
                    case 1:
                        System.out.println(dialog2);
                        dialogNumber = 0;
                        break;
                }
            }
        } else {
            System.out.println(questText);
        }
    }

    public void doGeben(String[] parsed_command, int count) {
        if(count == 2) {
            String itemToUse = IOUtils.convertToName(parsed_command[1]);
            if(itemToUse.equals(questItems)) {
                if(Dungeon.getDungeon().inventory.giveItem(Dungeon.getDungeon().itemMap.get(parsed_command[1].toUpperCase()))) {
                    System.out.println(questDoneText);
                    questDone = true;
                    if(Dungeon.getDungeon().inventory.recieveItem(rewarditem)) {
                        System.out.println("Im Gegenzug bekommst du von mir auch etwas. Bitteschön.");
                    } else {
                        System.out.println("Dein Inventar ist leider voll. Komm wieder, wenn du Platz hast.");
                        giveItem = true;
                    }
                } else {
                    System.out.println("Item nicht im Inventar.");
                }
            } else {
                System.out.println("Das brauche ich nicht.");
            }
        }
    }

}
