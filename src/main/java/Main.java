import client.IPiServer;
import client.PiServer;
import com.pi4j.io.gpio.*;

import java.rmi.Naming;

public class Main  extends PinProvider {

    public static void main(String [] args) throws Exception{

//        Registry registry = LocateRegistry.createRegistry(1099);
//        client.PiServer server = new client.PiServer();
//        registry.bind("PISERVER", server);

        IPiServer piServer;
        System.setProperty("java.rmi.server.hostname", "raspberrypi.local");
        PiServer server = new PiServer();
        Naming.rebind("PISERVER", server);
        System.out.println("PISERVER Started");

    }
}
