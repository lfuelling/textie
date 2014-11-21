package de.micromata.azubi.model;

import java.util.ArrayList;

/**
 * Created by jsiebert on 20.11.14.
 */
public class Switch extends ToggleItem{

    private ArrayList<Integer> affectedDoorIds;

    public ArrayList<Integer> getAffectedDoorIds() {
        return affectedDoorIds;
    }

    public void setAffectedDoorIds(ArrayList<Integer> affectedDoorIds) {

        this.affectedDoorIds = affectedDoorIds;
    }

    public void toggleLock(Dungeon dungeon){
        for(Room room : dungeon.getRooms()){
           for(Door door : room.getDoors()){
               for(int doorId : affectedDoorIds){
                   if(door.getUid() == doorId){
                       door.toogleLock();
                   }
               }
           }
        }

    }
}
