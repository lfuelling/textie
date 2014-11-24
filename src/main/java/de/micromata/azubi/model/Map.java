package de.micromata.azubi.model;

import de.micromata.azubi.Textie;

import java.util.ArrayList;

/**
 * @author Julian Siebert (j.siebert@micromata.de)
 * @author Lukas F&uuml;lling (lf.fuelling@micromata.de)
 * @see Item
 */
public class Map extends Item {


  ArrayList<String> roomNumbers = new ArrayList<String>();
  ArrayList<String> walkDirection = new ArrayList<String>();

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
   * @param currentRoomNumber Number of the room.
   * @param laufRichtung Direction
   */
  public void writeMap(int currentRoomNumber, String laufRichtung) {

    this.walkDirection.add("(" + laufRichtung + ")--");
    this.roomNumbers.add("[Raum " + currentRoomNumber + "]--");

  }

  /**
   * Prints out the map's ouput.
   */
  public void use() {

    if(walkDirection.size() != roomNumbers.size()) {
      return;
    }

    StringBuffer buf = new StringBuffer();

    for(int i = 0; i < walkDirection.size(); i++) {
      buf.append(roomNumbers.get(i)).append(walkDirection.get(i));
    }

    Textie.printText(buf.toString());
  }


}

