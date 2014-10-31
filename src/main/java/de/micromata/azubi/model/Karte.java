package de.micromata.azubi.model;

import de.micromata.azubi.Textie;

import java.util.ArrayList;

/**
 * @author Julian Siebert (j.siebert@micromata.de)
 * @author Lukas F&uuml;lling (lf.fuelling@micromata.de)
 * @see Item
 */
public class Karte extends Item {


  ArrayList<String> raumNummern = new ArrayList<String>();
  ArrayList<String> laufRichtung = new ArrayList<String>();

  /**
   *
   * @param name Item Name
   * @param untersucheText Text which is printed when you inspect the map.
   * @param benutzeText Text which is replaced by the map's output.
   */
  /*
  public Karte(int id, String name, String untersucheText, String benutzeText) {

    super(id, name, untersucheText, benutzeText, true);
  }
  */

  /**
   * Add another field to the map's output.
   * @param currentRaumNummer Number of the room.
   * @param laufRichtung Direction
   */
  public void writeMap(int currentRaumNummer, String laufRichtung) {

    this.laufRichtung.add("(" + laufRichtung + ")--");
    this.raumNummern.add("[Raum " + currentRaumNummer + "]--");

  }

  /**
   * Prints out the map's ouput.
   */
  @Override
  public void benutzen() {

    if(laufRichtung.size() != raumNummern.size()) {
      return;
    }

    StringBuffer buf = new StringBuffer();

    for(int i = 0; i < laufRichtung.size(); i++) {
      buf.append(raumNummern.get(i)).append(laufRichtung.get(i));
    }

    Textie.printText(buf.toString());
  }


}

