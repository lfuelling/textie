package de.micromata.azubi;


/*
 * Der Mensch (Human) ist bis jetzt noch ziemlich beschränkt (nur zwei Dialoge)
 * Wir werden das später evtl. erweitern. Vorerst genügt das. Wichtig ist mir nur, dass die Quests funktionieren.
 */

public class Human {

	// Anzahl der ausgegebenen Dialoge
	int dialogNumber = 0;
	private boolean questDone = false;
	private String dialog1;
	private String dialog2;
	private String questText;
	private String questDoneText;
	private String name;
	Item questItem;
	String questItemName;

	public Human(String name, String dialog1, String dialog2, String questText, String questDoneText, Item questItem) {
		this.name = name;
		this.dialog1 = dialog1;
		this.dialog2 = dialog2;
		this.questText = questText;
		this.questDoneText = questDoneText;
		this.questItem = questItem;
		this.questItemName = questItem.getName();
	}

	public String getName() {
		return this.name;
	}
	
	void doReden(String[] parsed_command, int count) {
		if (questDone == true) {
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
		} else {
			System.out.println(questText);
		}
	}
	
	public void doGeben(String[] parsed_command, int count) {
		if (count == 2) {
			String itemToUse = IOUtils.convertToName(parsed_command[1]);
			if(itemToUse.equals(questItemName)) {
				if(Textie.inventory.giveItem(Textie.itemMap.get(parsed_command[1].toUpperCase()))){
					System.out.println(questDoneText);
					questDone = true;
				}
				else {
					System.out.println("Item nicht im Inventar.");
				}
			}
			else {
				System.out.println("Das brauche ich nicht.");
			}
		}
	}
	
	public boolean getQuestState(){
		return questDone;
	}

}
