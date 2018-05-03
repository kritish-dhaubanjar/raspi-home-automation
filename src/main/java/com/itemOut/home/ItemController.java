package com.itemOut.home;

import com.database.DataSource;
import com.interfaces.IItemController;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ItemController implements IItemController{

    /** Populated from DB */
    private static List<Item> itemList = new ArrayList<>();
    private static DataSource dataSource = new DataSource();

    public ItemController(){
        dataSource.dbGetItems(this);
    }

    public static List<Item> getItemList() {
        return itemList;
    }

    /** Check Pin Registration*/
    public static boolean registered(int gpioPin){
        return itemList.contains(new Item(gpioPin));
    }

    /** Create New Item */
    public boolean createItem(boolean firstRun, String deviceName, String notes, int gpioPin, int roomId){
        if(!registered(gpioPin)) {
            Item newItem = new Item(deviceName, notes, gpioPin, roomId);
            itemList.add(newItem);
            if(!firstRun) {
                return dataSource.dbInsertItem(gpioPin, deviceName, notes, roomId);
            }
            return true;
        }
        return false;
    }

    /** Read Item*/
    public static Item getItem(int gpioPin){
        int id = itemList.indexOf(new Item(gpioPin));
        return itemList.get(id);
    }

    /** Set State of Item */
    public void setState(int gpioPin, boolean state){
        getItem(gpioPin).setState(state);
        dataSource.dbUpdateItemState(gpioPin, state);
    }

    /** Update Item */
    public boolean updateItem(int oldPin, String deviceName, String notes, int newPin, int roomId){

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
            return dataSource.dbUpdateItem(oldPin, deviceName, notes, newPin, roomId);
        }
        return false;
    }

    /** Delete Item */
    public boolean deleteItem(int gpioPin){
        if(registered(gpioPin)){
            int id = itemList.indexOf(new Item(gpioPin));
            itemList.get(id).releasePin();
            itemList.remove(id);
            return dataSource.dbDeleteItem(gpioPin);
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
                                "Updated: " + item.getUpdated().toString());
        }
        System.out.println("/************************************/");
    }



}
