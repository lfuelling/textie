package de.micromata.azubi.builder;

import de.micromata.azubi.model.Inventory;
import de.micromata.azubi.model.Player;

/**
 * Created by jsiebert on 30.10.14.
 */
public class PlayerBuilder implements Builder<Player> {

    private Player player = new Player();
    private InventoryBuilder ib;
    private String playerName;

    public PlayerBuilder add(InventoryBuilder ib) {
        this.ib = ib;
        return this;
    }

    public PlayerBuilder addName(String playeName) {
        this.playerName = playeName;
        return this;
    }


    @Override
    public PlayerBuilder build() {
        Inventory inv = ib.get();
        inv.setInventorySize(5);
        player.setInventory(inv);
        player.setAlive(true);
        player.setName(playerName);
        return this;
    }

    @Override
    public Player get() {
        return player;
    }
}
