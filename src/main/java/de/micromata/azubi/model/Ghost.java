package de.micromata.azubi.model;

/**
 * Created by Daniel Brommer on 06.11.14.
 */

        import de.micromata.azubi.Textie;
        import java.io.Serializable;import java.util.ArrayList;
        import java.util.List;

public class Ghost implements Serializable{

    private static final long serialVersionUID = -6334257305676290673L;private String name ="Geist";
    private String dialog ="Geist";
    private int nextRoomNumber;
    private Room position;
    private Dungeon dungeon;

    public void move () {

        if (dungeon.getPlayer().getPosition() == position) {
            Textie.printText(dialog);
        }
        List<String> mobileItems = new ArrayList<>();
        mobileItems.add("Handtuch");
        mobileItems.add("Stein");
        mobileItems.add("Brecheisen");
        mobileItems.add("Quietscheente");
        mobileItems.add("Sack");
        mobileItems.add("Schwert");
        mobileItems.add("Whiteboard");

        int itemIdx = (int) (Math.random() * (mobileItems.size() - 1));
        String itemName = mobileItems.get(itemIdx);

        int numberOfRooms = dungeon.getRooms().size();
        nextRoomNumber = (int) (Math.random() * numberOfRooms);

        Item mobileItem = dungeon.getCurrentRoom().getInventory().findItemByName(itemName);
        if (mobileItem == null) {
            setPosition(dungeon.findRoomByNumber(nextRoomNumber));
        } else {
            dungeon.getCurrentRoom().getInventory().addItem(mobileItem);
            setPosition(dungeon.findRoomByNumber(nextRoomNumber));
            dungeon.getCurrentRoom().getInventory().removeItem(mobileItem);
        }
        System.out.println("geist in raum:"+position);
        System.out.println("Item:"+mobileItem);
    }

    public void setName(String name) {this.name = name;}
    public void setDialog(String dialog) {this.dialog = dialog;}
    public void setPosition(Room position) {
        this.position = position;
    }
}

