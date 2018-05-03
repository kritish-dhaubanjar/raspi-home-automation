import com.itemOut.home.ItemController;
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
        return itemController.createItem(firstRun, deviceName, notes, gpioPin, roomId);
    }

    public void setState(int gpioPin, boolean state){
        itemController.setState(gpioPin, state);
    }

    public boolean updateItem(int oldPin, String deviceName, String notes, int newPin, int roomId){
        return itemController.updateItem(oldPin, deviceName, notes, newPin, roomId);
    }

    public boolean deleteItem(int gpioPin){
        return itemController.deleteItem(gpioPin);
    }

    public boolean createTrigger(String name, String note, int masterPin,
                                 int slavePin, boolean shouldBeState, boolean triggerState){
        return triggerController.createTrigger(name, note, masterPin, slavePin, shouldBeState, triggerState);
    }

    public boolean deleteTrigger(int _id){
        return triggerController.deleteTrigger(_id);
    }

    public boolean updateTrigger(int _id, String name, String note, int masterPin,
                                 int slavePin, boolean shouldBeState, boolean triggerState){
        return triggerController.updateTrigger(_id, name, note, masterPin, slavePin, shouldBeState, triggerState);
    }


}
