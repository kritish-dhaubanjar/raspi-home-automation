package com.interfaces;

import com.database.DataSource;
import com.itemOut.home.Item;
import java.util.ArrayList;
import java.util.List;

public interface IItemController{

    static List<Item> itemList = new ArrayList<>();
    static DataSource dataSource = new DataSource();

    boolean createItem(boolean firstRun, String deviceName, String notes, int gpioPin, int roomId);

    void setState(int gpioPin, boolean state);

    boolean updateItem(int oldPin, String deviceName, String notes, int newPin, int roomId);

    boolean deleteItem(int gpioPin);

}
