package de.micromata.azubi.model;

import de.micromata.azubi.IOUtils;
import de.micromata.azubi.Textie;
import de.micromata.azubi.model.Dungeon;
import de.micromata.azubi.model.Inventory;

import java.io.Serializable;

/**
 * Created by jsiebert on 29.09.14.
 */
public class Player implements Serializable {

    private static final long serialVersionUID = -2306082155444323753L;
    private String name;
    private Inventory inventory;
    private boolean alive;
    private Raum position;






    public void prompt() {
        do {
            String command = IOUtils.readLine("Was willst du tun? ");
            try{
              if (command.equals("")) {
              } else {
                String[] parsed_command = Textie.parseInput(command);

                String[] parsed_args = new String[2];
                if (parsed_command.length == 1 || parsed_command[1] == null) {
                  parsed_args[0] = "nichts";
                } else {
                  parsed_args = Textie.parseInput(parsed_command[1]);
                }
                Textie.executeCommand(parsed_command, parsed_args);
              }
            } catch(NullPointerException){
              Textie.printText("Keine Eingabe.");
            }
        } while (Dungeon.getDungeon().getCurrentRaum().isLeaveRoom() == false);
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    
		// this.inventory = inventory;
    public Inventory getInventory() {
        return inventory;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Raum getPosition() {
        return position;
    }

    public void setPosition(Raum position) {
        this.position = position;
    }
}
