package de.micromata.azubi.model;


/*
 * Der Mensch (Human) ist bis jetzt noch ziemlich beschränkt (nur zwei Dialoge)
 * Wir werden das später evtl. erweitern. Vorerst genügt das. Wichtig ist mir nur, dass die Quests funktionieren.
 */

import de.micromata.azubi.Textie;

import java.io.Serializable;

public class Human implements Serializable{

    private static final long serialVersionUID = -1984948626055306501L;

    public Human() {

    }

    // Anzahl der ausgegebenen Dialoge
    private boolean questDone = false;
    private String dialog1;
    private String dialog2;
    private String questText;
    private String questDoneText;
    private String name;
    private boolean gaveItem = false;
    private String questItem;
    private Item rewarditem;
    private int dialogNumber = 0;

    public Human(String name, String dialog1, String dialog2, String questText, String questDoneText, Item rewardItem, String questItemName) {
        this.name = name;
        this.dialog1 = dialog1;
        this.dialog2 = dialog2;
        this.questText = questText;
        this.questDoneText = questDoneText;
        this.questItem = questItemName;
      /*  for(Item questItems : questItem){
            this.questItems.add(questItems);
        }*/
        // this.questItemName = questItem.getName();
        this.rewarditem = rewardItem;
    }

    public String getName() {
        return this.name;
    }

    public boolean isQuestDone() {
        return questDone;
    }

    public void setQuestDone(boolean questDone) {
        this.questDone = questDone;
    }

    public boolean isGaveItem() {
        return gaveItem;
    }

    public void setGaveItem(boolean gaveItem) {
        this.gaveItem = gaveItem;
    }

    public Item getRewarditem() {
        return rewarditem;
    }

    public void setRewarditem(Item rewarditem) {
        this.rewarditem = rewarditem;
    }

    public String getDialog1() {
        return dialog1;
    }

    public void setDialog1(String dialog1) {
        this.dialog1 = dialog1;
    }

    public String getDialog2() {
        return dialog2;
    }

    public void setDialog2(String dialog2) {
        this.dialog2 = dialog2;
    }

    public String getQuestText() {
        return questText;
    }

    public void setQuestText(String questText) {
        this.questText = questText;
    }

    public String getQuestItem() {
        return questItem;
    }

    public void setQuestItem(String questItemName) {
        this.questItem = questItemName;
    }

    public String getQuestDoneText() {
        return questDoneText;
    }

    public void setQuestDoneText(String questDoneText) {
        this.questDoneText = questDoneText;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDialogNumber() {
        return dialogNumber;
    }

    public void setDialogNumber(int dialogNumber) {
        this.dialogNumber = dialogNumber;
    }

    /**
     * Talk to someone.
     */
    public void doTalk(Dungeon dungeon) {
        if (this.isQuestDone() == true) {
            if (this.isGaveItem() == true) {
                if (Textie.recieveItem(this.getRewarditem(), dungeon.getPlayer().getInventory())) {
                    Textie.printText("Hier, bitte schön.");
                    this.setGaveItem(false);
                } else {
                    Textie.printText("Dein Inventar ist leider voll. Komm wieder, wenn du Platz hast.");
                    this.setGaveItem(true);
                }
            } else {
                int dialogNumber = this.getDialogNumber();
                switch (dialogNumber) {
                    case 0:
                        Textie.printText(this.getDialog1());
                        dialogNumber = 1;
                        break;
                    case 1:
                        Textie.printText(this.getDialog2());
                        dialogNumber = 0;
                        break;
                }
            }
        } else {
            Textie.printText(this.getQuestText());
        }
    }
}
