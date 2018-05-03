package com.trigger.home;

import com.database.DataSource;
import com.interfaces.ITriggerController;
import com.itemOut.home.ItemController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TriggerController implements ITriggerController {

    public static List<Trigger> triggerList= new ArrayList<>();
    private static DataSource dataSource = new DataSource();

    public TriggerController(){
        dataSource.dbGetTriggers(this);
    }

    public boolean createTrigger(int _id, String name, String note, int masterPin, int slavePin, boolean shouldBeState, boolean triggerState){
        if(!triggerList.contains(new Trigger(masterPin, slavePin))){
            Trigger trigger = new Trigger(name, note, masterPin, slavePin, shouldBeState, triggerState);
            trigger.set_id(_id);
            triggerList.add(trigger);
            ItemController.getItem(masterPin).loadTriggerItems();
            return true;
        }
        return false;
    }

    public boolean createTrigger(String name, String note, int masterPin, int slavePin, boolean shouldBeState, boolean triggerState){
        if(!triggerList.contains(new Trigger(masterPin, slavePin))){
            Trigger trigger = new Trigger(name, note, masterPin, slavePin, shouldBeState, triggerState);
            triggerList.add(trigger);
            int _id = dataSource.dbInsertTrigger(name, note, masterPin, slavePin, shouldBeState, triggerState);
            trigger.set_id(_id);
            ItemController.getItem(masterPin).loadTriggerItems();
            return true;
        }
        return false;
    }

    public boolean deleteTrigger(int _id){
        int id = triggerList.indexOf(getTriggerFromId(_id));
        if(id>=0) {
            triggerList.remove(id);
            return dataSource.dbDeleteTrigger(_id);
        }
        return false;
    }

    public boolean updateTrigger(int _id, String name, String note, int masterPin,
                                        int slavePin, boolean shouldBeState, boolean triggerState){
        if(!triggerList.contains(new Trigger(masterPin, slavePin))){
            Trigger trigger = getTriggerFromId(_id);
            trigger.setName(name);
            trigger.setNote(note);
            trigger.setMasterPin(masterPin);
            trigger.setSlavePin(slavePin);
            trigger.setShouldBeState(shouldBeState);
            trigger.setTriggerState(triggerState);
            return dataSource.dbUpdateTrigger(_id, name, note, masterPin, slavePin, shouldBeState, triggerState);
        }
        return  false;
    }

    private Trigger getTriggerFromId(int _id){
        for(Trigger trigger : triggerList){
            if(trigger.get_id() == _id)
                return trigger;
        }
        return null;
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
