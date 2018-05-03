package com.interfaces;

import com.database.DataSource;
import com.trigger.home.Trigger;

import java.util.ArrayList;
import java.util.List;

public interface ITriggerController {

    static List<Trigger> triggerList= new ArrayList<>();
    static DataSource dataSource = new DataSource();

    boolean createTrigger(int _id, String name, String note, int masterPin,
                          int slavePin, boolean shouldBeState, boolean triggerState);

    boolean createTrigger(String name, String note, int masterPin,
                          int slavePin, boolean shouldBeState, boolean triggerState);

    boolean deleteTrigger(int _id);

    boolean updateTrigger(int _id, String name, String note, int masterPin,
                          int slavePin, boolean shouldBeState, boolean triggerState);

}
