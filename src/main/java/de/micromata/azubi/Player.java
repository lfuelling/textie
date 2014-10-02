package de.micromata.azubi;

/**
 * Created by jsiebert on 29.09.14.
 */
public class Player {

    public Inventory inventory;
    public Raum currentRaum;

    private String playerName;
    private boolean alive;
    public Player() {

    }

    public Player(Inventory inventory, Raum currentRaum, String playerName, boolean alive) {
        this.inventory = inventory;
        this.currentRaum = currentRaum;
        this.playerName = playerName;
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }



}
