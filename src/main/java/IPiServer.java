import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPiServer extends Remote {

        boolean createItem(boolean firstRun, String deviceName, String notes, int gpioPin, int roomId) throws RemoteException;

        void setState(int gpioPin, boolean state) throws RemoteException;

        boolean updateItem(int oldPin, String deviceName, String notes, int newPin, int roomId) throws RemoteException;

        boolean deleteItem(int gpioPin) throws RemoteException;

        boolean createTrigger(String name, String note, int masterPin,
                              int slavePin, boolean shouldBeState, boolean triggerState) throws RemoteException;

        boolean deleteTrigger(int _id) throws RemoteException;

        boolean updateTrigger(int _id, String name, String note, int masterPin,
                              int slavePin, boolean shouldBeState, boolean triggerState) throws RemoteException;


}
