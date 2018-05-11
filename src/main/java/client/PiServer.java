package client;

import client.IPiServer;
import com.itemOut.home.ItemController;
import com.refresh.Sender;
import com.trigger.home.TriggerController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PiServer extends UnicastRemoteObject implements IPiServer {

    private static ItemController itemController = new ItemController();
    private static TriggerController triggerController = new TriggerController();


    public PiServer() throws RemoteException{
        super();
    }

    public boolean createItem(boolean firstRun, String deviceName, String notes, int gpioPin, int roomId){
        boolean result = itemController.createItem(firstRun, deviceName, notes, gpioPin, roomId);
        sendRefreshRequest();
        return result;
    }

    public void setState(int gpioPin, boolean state){
        itemController.setState(gpioPin, state);
        sendRefreshRequest();
    }

    public boolean updateItem(int oldPin, String deviceName, String notes, int newPin, int roomId){
        boolean result = itemController.updateItem(oldPin, deviceName, notes, newPin, roomId);
        sendRefreshRequest();
        return result;
    }

    public boolean deleteItem(int gpioPin){
        boolean result = itemController.deleteItem(gpioPin);
        sendRefreshRequest();
        return result;
    }

    public boolean createTrigger(String name, String note, int masterPin,
                                 int slavePin, boolean shouldBeState, boolean triggerState){
        boolean result =  triggerController.createTrigger(name, note, masterPin, slavePin, shouldBeState, triggerState);
        sendRefreshRequest();
        return result;
    }

    public boolean deleteTrigger(int _id){
        boolean result = triggerController.deleteTrigger(_id);
        sendRefreshRequest();
        return result;
    }

    public boolean updateTrigger(int _id, String name, String note, int masterPin,
                                 int slavePin, boolean shouldBeState, boolean triggerState){
        boolean result = triggerController.updateTrigger(_id, name, note, masterPin, slavePin, shouldBeState, triggerState);
        sendRefreshRequest();
        return result;
    }

    public void sendRefreshRequest(){
        new Sender().start();
    }

}
