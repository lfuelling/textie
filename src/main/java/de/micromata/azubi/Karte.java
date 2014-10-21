package de.micromata.azubi;

import java.util.ArrayList;

/**
 * Created by jsiebert on 30.09.14.
 */
public class Karte extends Item {





        ArrayList<String> raumNummern = new ArrayList<String>();
        ArrayList<String> laufRichtung = new ArrayList<String>();


    public Karte(){

    }


    public Karte(String name, String untersucheText, String benutzeText, boolean pickable) {
        super(name, untersucheText, benutzeText, pickable);
    }

    public void writeMap(int currentRaumNummer, String laufRichtung){
        this.laufRichtung.add("("+laufRichtung+")--");
        this.raumNummern.add("[Raum " + currentRaumNummer + "]--");

    }

    @Override
    public void benutzen() {
        if(laufRichtung.size() != raumNummern.size()) {
            return;
        }

        StringBuffer buf = new StringBuffer();

        for(int i = 0; i<laufRichtung.size(); i++){
            buf.append(raumNummern.get(i)).append(laufRichtung.get(i));
        }

        Textie.printText(buf.toString());
    }
}

