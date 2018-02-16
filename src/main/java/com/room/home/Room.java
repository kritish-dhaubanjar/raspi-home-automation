package com.room.home;

import com.itemOut.home.Item;
import com.itemOut.home.ItemController;

import java.util.Iterator;
import java.util.List;

public class Room {
    private int roomId;
    private String name;

    private List<Item> roomItems;

    public Room(int roomId, String name) {
        this.roomId = roomId;
        this.name = name;
        Iterator<Item> i = ItemController.getItemList().iterator();
        while (i.hasNext()){
            Item item = i.next();
            if(item.getRoomId() == this.roomId){
                roomItems.add(item);
            }
        }
    }

    public List<Item> getRoomItems() {
        return roomItems;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
