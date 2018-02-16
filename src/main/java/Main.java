import com.itemOut.home.Item;
import com.itemOut.home.ItemController;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinProvider;
import com.room.home.Room;

import java.util.*;

public class Main extends PinProvider{

    public static void main(String [] args){
        /**
         * ItemController.registered(0);
         * ItemController.createItem("Fan", "Yasuda's Fan", 0);
         * ItemController.getItem(0);
         * ItemController.updateItem(0,"TV", "Yasuda", 1);
         * ItemController.deleteItem(0);
         * ItemController.listItem();
         **/

        System.out.println("Hello World");

    }
}
