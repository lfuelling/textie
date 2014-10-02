package de.micromata.azubi;

import java.util.ArrayList;

/**
 * Created by jsiebert on 30.09.14.
 */
public class Karte extends Item {




        ArrayList<String> raumNummern = new ArrayList<String>();
        ArrayList<String> laufRichtung = new ArrayList<String>();



    public Karte(String name, String untersucheText, String benutzeText, boolean pickable) {
        super(name, untersucheText, benutzeText, pickable);
    }

    public void writeMap(String currentRaumNummer, String laufRichtung){
        this.laufRichtung.add("("+laufRichtung+")--");
        this.raumNummern.add("[Raum " + currentRaumNummer + "]--");

    }

    public String readMap(){

        if(laufRichtung.size() != raumNummern.size()) {
            return null;
        }

        StringBuffer buf = new StringBuffer();

        for(int i = 0; i<laufRichtung.size(); i++){
            buf.append(raumNummern.get(i)).append(laufRichtung.get(i));
        }

        return buf.toString();
    }


}

