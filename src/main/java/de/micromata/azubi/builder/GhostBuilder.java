package de.micromata.azubi.builder;

/**
 * Created by Daniel Brommer on 06.11.14.
 */
import de.micromata.azubi.model.Ghost;


public class GhostBuilder implements Builder<Ghost> {

    private Ghost ghost = new Ghost();
    String name;
    String dialog;


    public GhostBuilder setName(String name) {
        this.name = name;
        return this;
    }
    public GhostBuilder setDialog(String dialog){
        this.dialog = dialog;
        return this;
    }

    @Override
    public GhostBuilder build(){
        ghost.setName(name);
        ghost.setDialog(dialog);
        return this;
    }

    @Override
    public Ghost get() {return ghost;}
}
