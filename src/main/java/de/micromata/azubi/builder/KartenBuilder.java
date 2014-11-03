package de.micromata.azubi.builder;

import de.micromata.azubi.model.Item;
import de.micromata.azubi.model.Karte;

import java.util.ArrayList;

/**
 * Created by jsiebert on 31.10.14.
 */
public class KartenBuilder extends BaseItemBuilder {

    private Karte karte;


    @Override
    public KartenBuilder build() {
        super.build();
        return this;
    }

    @Override
    public Karte get() {
        return this.karte;
    }

    @Override
    protected Item createInstance() {
        karte = new Karte();
        return karte;
    }
}
