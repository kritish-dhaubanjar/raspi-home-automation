package com.itemOut.home;

public interface IItemController {

    boolean createItem(String deviceName, String notes, int gpioPin, int roomId);

    void setState(int gpioPin, boolean state);

    boolean updateItem(int oldPin, String deviceName, String notes, int newPin, int roomId);

    boolean deleteItem(int gpioPin);
}
