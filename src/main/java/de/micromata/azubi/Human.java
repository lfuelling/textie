package de.micromata.azubi;


/*
 * Der Mensch (Human) ist bis jetzt noch ziemlich beschränkt (nur zwei Dialoge)
 * Wir werden das später evtl. erweitern. Vorerst genügt das. Wichtig ist mir nur, dass die Quests funktionieren.
 */

public class Human {

    public Human() {

    }

    // Anzahl der ausgegebenen Dialoge
    int dialogNumber = 0;
    private boolean questDone = false;
    private String dialog1;
    private String dialog2;
    private String questText;
    private String questDoneText;
    private String name;
    private boolean gaveItem = false;
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

    public Item getQuestItem() {
        return questItem;
    }

    public void setQuestItem(Item questItem) {
        this.questItem = questItem;
    }

    public String getQuestDoneText() {
        return questDoneText;
    }

    public void setQuestDoneText(String questDoneText) {
        this.questDoneText = questDoneText;
    }
}
