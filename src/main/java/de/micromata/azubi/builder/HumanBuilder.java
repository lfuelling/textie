package de.micromata.azubi.builder;

import de.micromata.azubi.model.Human;
import de.micromata.azubi.model.Item;

/**
 * Created by jsiebert on 30.10.14.
 */
public class HumanBuilder implements Builder<Human> {

    private Human human = new Human();
    private String dialog1;
    private String dialog2;
    private String questText;
    private String questDoneText;
    private String name;
    private String questItem;
    private BaseItemBuilder rewarditem;

    public HumanBuilder setHumanName(String name){
        this.name = name;
        return this;
    }

    public HumanBuilder setDialog1(String dialog1){
        this.dialog1 = dialog1;
        return this;
    }

    public HumanBuilder setDialog2(String dialog2) {
        this.dialog2 = dialog2;
        return this;
    }

    public HumanBuilder setQuestText(String questText) {
        this.questText = questText;
        return this;
    }

    public HumanBuilder setQuestDoneText(String questDoneText) {
        this.questDoneText = questDoneText;
        return this;
    }


    public HumanBuilder setQuestItem(String questItem) {
        this.questItem = questItem;
        return this;
    }

    public HumanBuilder setRewarditem(BaseItemBuilder rewarditem) {
        this.rewarditem = rewarditem;
        return this;
    }

    @Override
    public HumanBuilder build() {
        human.setName(name);
        human.setDialog1(dialog1);
        human.setDialog2(dialog2);
        human.setQuestText(questText);
        human.setQuestDoneText(questDoneText);
        human.setQuestItem(questItem);
        human.setRewarditem(rewarditem.get());
        return this;
    }

    @Override
    public Human get() {
        return human;
    }
}
