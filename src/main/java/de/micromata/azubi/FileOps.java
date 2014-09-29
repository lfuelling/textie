package de.micromata.azubi;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


/**
 * Created by jsiebert on 29.09.14.
 */
public class FileOps {
    FileWriter writer;
    File file;
    private Inventory inventory;
    private Raum currentRaum;
    private boolean questFinished;
    private Human currentHuman;
    private HashMap<String, Item> itemMap;



    //TODO viel, viel zu tun
    public void schreiben(Inventory inventory, Raum currentRaum, boolean questFinished, Human currentHuman, HashMap itemMap) {

        this.inventory = inventory;
        this.currentRaum = currentRaum;
        this.questFinished = questFinished;
        this.currentHuman = currentHuman;
        this.itemMap = itemMap;

   }


}
