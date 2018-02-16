package com.itemOut.home;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ItemController{
    private static List<Item> itemList = new ArrayList<>();

    private ItemController(){}

    public static List<Item> getItemList() {
        return itemList;
    }

    /** Check Pin Registration*/
    public static boolean registered(int gpioPin){
        return itemList.contains(new Item(gpioPin));
    }

    /** Create New Item */
    public static boolean createItem(String deviceName, String notes, int gpioPin, int roomId){
        if(!registered(gpioPin)) {
            Item newItem = new Item(deviceName, notes, gpioPin, roomId);
            itemList.add(newItem);
            return true;
        }
        return false;
    }

    /** Read Item*/
    public static Item getItem(int gpioPin){
        int id = itemList.indexOf(new Item(gpioPin));
        return itemList.get(id);
    }

    /** Update Item */
    public static boolean updateItem(int oldPin, String deviceName, String notes, int newPin, int roomId){

        if (oldPin!=newPin && registered(newPin))
            return false;

        if(registered(oldPin)){
            int id = itemList.indexOf(new Item(oldPin));
            Item item = itemList.get(id);
            item.setDeviceName(deviceName);
            item.setNotes(notes);
            item.setRoomId(roomId);
            if(oldPin != newPin)
                item.setGpioPin(newPin);
            return true;
        }
        return false;
    }

    /** Delete Item */
    public static boolean deleteItem(int gpioPin){
        if(registered(gpioPin)){
            int id = itemList.indexOf(new Item(gpioPin));
            itemList.get(id).releasePin();
            itemList.remove(id);
            return true;
        }
        return false;
    }

    /** List of Item*/
    public static void listItem(){
        Iterator<Item> i = itemList.iterator();
        System.out.println("/************************************/");
        while (i.hasNext()){
            Item item = i.next();
            System.out.println( "Name: " + item.getDeviceName() + "\n" +
                                "GPIO: " +item.getGpioPin() + "\n" +
                                "State: " +item.getState() + "\n" +
                                "Note: " +item.getNotes() + "\n" +
                                "Updated: " + item.getUpdated().toString() + "\n" +
                                "Created: " + item.getCreated().toString());
        }
        System.out.println("/************************************/");
    }



}
