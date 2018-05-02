package com.trigger.home;

import com.itemOut.home.ItemController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TriggerController {

    public static List<Trigger> triggerList= new ArrayList<>();

    private TriggerController(){}

    public static boolean createTrigger(String name, String note, int masterPin, int slavePin, boolean shouldBeState, boolean triggerState){
        if(!triggerList.contains(new Trigger(masterPin, slavePin))){
            triggerList.add(new Trigger(name, note, masterPin, slavePin, shouldBeState, triggerState));
            ItemController.getItem(masterPin).loadTriggerItems();
            return true;
        }
        return false;
    }

    public static boolean deleteTrigger(Trigger trigger){
        int id = triggerList.indexOf(trigger);
        if(id>=0) {
            triggerList.remove(id);
            return true;
        }
        return false;
    }

    public static boolean updateTrigger(Trigger trigger, String name, String note, int masterPin,
                                        int slavePin, boolean shouldBeState, boolean triggerState){
        if(!triggerList.contains(new Trigger(masterPin, slavePin))){
            trigger.setName(name);
            trigger.setNote(note);
            trigger.setMasterPin(masterPin);
            trigger.setSlavePin(slavePin);
            trigger.setShouldBeState(shouldBeState);
            trigger.setTriggerState(triggerState);
        }
        return  false;
    }


    public static void listTrigger(){
        Iterator<Trigger> i = triggerList.iterator();
        System.out.println("/************************************/");
        while (i.hasNext()){
            Trigger trigger = i.next();
            System.out.println( "Name: " + trigger.getName() + "\n" +
                    "Master: " + trigger.getMasterPin() + "\n" +
                    "Slave: " + trigger.getSlavePin() + "\n" +
                    "MasterShould: " + trigger.isShouldBeState() + "\n" +
                    "SlaveShould: " + trigger.isTriggerState() + "\n");
        }
        System.out.println("/************************************/");
    }

}
