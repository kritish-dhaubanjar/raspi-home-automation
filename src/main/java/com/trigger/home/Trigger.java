package com.trigger.home;

import com.itemOut.home.Item;
import com.itemOut.home.ItemController;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import java.time.LocalDateTime;
import java.util.Objects;

public class Trigger{

    private int _id;
    private String name;
    private String note;
    private boolean shouldBeState;
    private boolean triggerState;
    private LocalDateTime updated;
    private int masterPin;
    private int slavePin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trigger trigger = (Trigger) o;
//        return (masterPin == trigger.masterPin && slavePin == trigger.slavePin);
        return (masterPin == trigger.masterPin && slavePin == trigger.slavePin) ||
                (masterPin == trigger.slavePin && slavePin == trigger.masterPin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(masterPin, slavePin);
    }

    /** Temporary Trigger*/
    public Trigger(int masterPin, int slavePin){
        this.masterPin = masterPin;
        this.slavePin = slavePin;
    }

    /** Create a new Trigger*/
    public Trigger(String name, String note, int masterPin, int slavePin, boolean shouldBeState, boolean triggerState){
        this.name = name;
        this.note = note;
        this.shouldBeState = shouldBeState;
        this.triggerState = triggerState;
        this.updated = LocalDateTime.now();
        createMasterSlave(masterPin, slavePin);
    }

    /** Set Master and Slave Items' Pins*/
    /**
     * @param masterPin The master Pin of Trigger
     * @param slavePin The slave Pin of master
     */
    public void createMasterSlave(int masterPin, int slavePin){
        if(ItemController.registered(masterPin) && ItemController.registered(slavePin)){
            this.masterPin = masterPin;
            this.slavePin = slavePin;
        }
    }

    public void setSlavePin(int slavePin) {
        this.slavePin = slavePin;
    }

    public void setMasterPin(int masterPin) {
        this.masterPin = masterPin;
    }

    public void setTriggerState(boolean triggerState) {
        this.triggerState = triggerState;
    }

    public void setShouldBeState(boolean shouldBeState) {
        this.shouldBeState = shouldBeState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMasterPin() {
        return masterPin;
    }

    public int getSlavePin() {
        return slavePin;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isShouldBeState() {
        return shouldBeState;
    }

    public boolean isTriggerState() {
        return triggerState;
    }

    public int get_id() { return _id; }

    public void set_id(int _id) { this._id = _id; }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated() {
        this.updated = LocalDateTime.now();
    }
}
