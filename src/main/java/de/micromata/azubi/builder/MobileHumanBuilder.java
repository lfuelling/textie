package de.micromata.azubi.builder;

import de.micromata.azubi.model.MobileHuman;

/**
 * Created by Daniel on 26.11.2014.
 */
public class MobileHumanBuilder implements Builder <MobileHuman> {

    private MobileHuman mobileHuman = new MobileHuman();

    private String name;
    private String dialog;

    public String setName() {return this.name; }
    public String setDialog() {
        return this.dialog;
    }

    @Override
    public MobileHumanBuilder build(){
        mobileHuman.setName(name);
        mobileHuman.setDialog(dialog);
        return this;
    }

    @Override
    public MobileHuman get() {return mobileHuman;}
    }



