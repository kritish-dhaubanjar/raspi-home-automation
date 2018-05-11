package com.database;

import com.itemOut.home.ItemController;
import com.trigger.home.TriggerController;

import java.sql.*;

public class DataSource {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/homeAutomation";
    private static final String username = "root";
    private static final String password = "toor";
    private Connection connection;
    private Statement statement;
    private PreparedStatement updateItem;
    private PreparedStatement insertItem;
    private PreparedStatement insertTrigger;
    private PreparedStatement updateTrigger;
    private PreparedStatement updateItemState;

    private static final String updateItemQuery = "UPDATE items SET gpioPin = ?, deviceName = ?, notes = ?, roomId = ? " +
            "WHERE gpioPin = ?";
    private static final String insertItemQuery = "INSERT INTO items(gpioPin, deviceName, notes, state, roomId)" +
            " VALUES(?,?,?,?,?)";
    private static final String updateTriggerQuery = "UPDATE triggers SET name=?, note=?, shouldBeState = ?, " +
            "triggerState=?, masterPin=?, slavePin=? WHERE _id=?";
    private static final String insertTriggerQuery = "INSERT INTO triggers(name, note, shouldBeState, triggerState," +
            "masterPin, slavePin) VALUES (?,?,?,?,?,?)";
    private static final String updateItemStateQuery = "UPDATE items set state = ?, updated_at = ? WHERE gpioPin = ?";

    public DataSource(){
        try{
            Connection connection = DriverManager.getConnection(URL, username, password);
            statement = connection.createStatement();
            updateItem = connection.prepareStatement(updateItemQuery);
            insertItem = connection.prepareStatement(insertItemQuery);
            updateTrigger = connection.prepareStatement(updateTriggerQuery);
            insertTrigger = connection.prepareStatement(insertTriggerQuery, Statement.RETURN_GENERATED_KEYS);
            updateItemState = connection.prepareStatement(updateItemStateQuery);
            this.connection = connection;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void dbGetItems(ItemController itemController){
        try {
            statement.execute("UPDATE items SET state = 0 WHERE 1");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM items");

            while(resultSet.next()){
                int gpioPin = resultSet.getInt(1);
                String deviceName = resultSet.getString(2);
                String notes = resultSet.getString(3);
                boolean state = resultSet.getBoolean(4);
                itemController.createItem(true, deviceName, notes, gpioPin, 0);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean dbInsertItem(int gpioPin, String deviceName, String notes, int roomId){
        try{
            insertItem.setInt(1,gpioPin);
            insertItem.setString(2, deviceName);
            insertItem.setString(3, notes);
            insertItem.setBoolean(4,false);
            insertItem.setInt(5, roomId);
            return insertItem.execute();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean dbUpdateItem(int oldPin, String deviceName, String notes, int newPin, int roomId){
        try {
            updateItem.setInt(1, newPin);
            updateItem.setString(2, deviceName);
            updateItem.setString(3, notes);
            updateItem.setInt(4, roomId);
            updateItem.setInt(5, oldPin);
            return updateItem.execute();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean dbDeleteItem(int gpioPin){
        try {
            return statement.execute("DELETE FROM items WHERE gpioPin = " + gpioPin);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean dbUpdateItemState(int gpioPin, boolean state){
        try{
            updateItemState.setBoolean(1,state);
            Timestamp date = new Timestamp(new java.util.Date().getTime());
            updateItemState.setTimestamp(2, date);
            updateItemState.setInt(3,gpioPin);
            return updateItemState.execute();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void dbGetTriggers(TriggerController triggerController){
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM triggers");

            while(resultSet.next()){
                int _id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String note = resultSet.getString(3);
                Boolean shouldBeState = resultSet.getBoolean(4);
                Boolean triggerState = resultSet.getBoolean(5);
                int masterPin = resultSet.getInt(6);
                int slavePin = resultSet.getInt(7);
                triggerController.createTrigger(_id, name, note, masterPin, slavePin,shouldBeState,triggerState);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public int dbInsertTrigger(String name, String note, int masterPin, int slavePin, Boolean shouldBeState, Boolean triggerState){
        try{
            insertTrigger.setString(1, name);
            insertTrigger.setString(2, note);
            insertTrigger.setBoolean(3, shouldBeState);
            insertTrigger.setBoolean(4,triggerState);
            insertTrigger.setInt(5, masterPin);
            insertTrigger.setInt(6, slavePin);
            insertTrigger.executeUpdate();
            ResultSet resultSet = insertTrigger.getGeneratedKeys();
            resultSet.next();
            int _id = resultSet.getInt(1);
            return _id;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public boolean dbUpdateTrigger(int _id, String name, String note, int masterPin,
                                   int slavePin, boolean shouldBeState, boolean triggerState){
        try{
            updateTrigger.setString(1, name);
            updateTrigger.setString(2, note);
            updateTrigger.setBoolean(3, shouldBeState);
            updateTrigger.setBoolean(4,triggerState);
            updateTrigger.setInt(5, masterPin);
            updateTrigger.setInt(6, slavePin);
            updateTrigger.setInt(7, _id);
            return updateTrigger.execute();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean dbDeleteTrigger(int _id){
        try{
            return statement.execute("DELETE FROM triggers WHERE _id = " + _id);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }


    public void close(){
        try {
            if (insertItem !=null)
                insertItem.close();
            if (updateItem != null)
                updateItem.close();
            if(insertTrigger !=null)
                insertTrigger.close();
            if(updateTrigger !=null)
                updateTrigger.close();
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
