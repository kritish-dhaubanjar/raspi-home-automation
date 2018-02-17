import com.pi4j.io.gpio.*;
import com.trigger.home.TriggerController;
import com.itemOut.home.ItemController;

public class Main  extends PinProvider {

    public static void main(String [] args) throws InterruptedException{
        /**
         * ItemController.registered(0);
         * ItemController.createItem("Fan", "Yasuda's Fan", 0);
         * ItemController.getMaster(0);
         * ItemController.updateItem(0,"TV", "Yasuda", 1);
         * ItemController.deleteItem(0);
         * ItemController.listItem();
         **/

        System.out.println("Hello World");
        ItemController.createItem("GPIO 00", "Pin 11", 0, 0);
        ItemController.createItem("GPIO 01", "Pin 12", 1,0);


        TriggerController.createTrigger("Led", "Led",
                                        0, 1,
                                        true,true);

        ItemController.getItem(0).setState(true);
        ItemController.listItem();
    }
}
