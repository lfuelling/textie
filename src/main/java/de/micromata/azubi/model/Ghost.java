package de.micromata.azubi.model;

/**
 * Created by Daniel Brommer on 06.11.14.
 */

        import de.micromata.azubi.Textie;
        import java.util.ArrayList;
        import java.util.List;

public class Ghost {

    private String name ="Geist";
    private String dialog ="Geist";
    private int nextRoomNumber;
    private Raum position;
    private Dungeon dungeon;

    public void move () {

        if (dungeon.getPlayer().getPosition() == position) {
            Textie.printText(dialog);
        }
        List<String> mobileItems = new ArrayList<>();
        mobileItems.add(Item.HANDTUCH);
        mobileItems.add(Item.STEIN);
        mobileItems.add(Item.BRECHEISEN);
        mobileItems.add(Item.QUIETSCHEENTE);
        mobileItems.add(Item.SACK);
        mobileItems.add(Item.SCHWERT);
        mobileItems.add(Item.WHITEBOARD);

        int itemIdx = (int) (Math.random() * (mobileItems.size() - 1));
        String itemName = mobileItems.get(itemIdx);

        int numberOfRooms = dungeon.getRooms().size();
        nextRoomNumber = (int) (Math.random() * numberOfRooms);

        Item mobileItem = dungeon.getCurrentRaum().getInventory().findItemByName(itemName);
        if (mobileItem == null) {
            setPosition(dungeon.findRaumByNummer(nextRoomNumber));
        } else {
            dungeon.getCurrentRaum().getInventory().addItem(mobileItem);
            setPosition(dungeon.findRaumByNummer(nextRoomNumber));
            dungeon.getCurrentRaum().getInventory().removeItem(mobileItem);
        }
        System.out.println("geist in raum:"+position);
        System.out.println("Item:"+mobileItem);
    }

    public void setName(String name) {this.name = name;}
    public void setDialog(String dialog) {this.dialog = dialog;}
    public void setPosition(Raum position) {
        this.position = position;
    }
}

