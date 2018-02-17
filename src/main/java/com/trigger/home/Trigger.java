package com.trigger.home;

import com.itemOut.home.Item;
import com.itemOut.home.ItemController;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import java.time.LocalDateTime;
import java.util.Objects;

public class Trigger{

    private String name;
    private String note;
    private Item master;
    private boolean shouldBeState;
    private Item slave;
    private boolean triggerState;
    private LocalDateTime created;
    private LocalDateTime updated;
    private int masterPin;
    private int slavePin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trigger trigger = (Trigger) o;
        return (masterPin == trigger.masterPin && slavePin == trigger.slavePin) ||
                (masterPin == trigger.slavePin && slavePin == trigger.masterPin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(masterPin, slavePin);
    }

    /** Temporary Trigger*/
    public Trigger(int masterPin, int slavePin){
        this.master = master;
        this.slave = slave;
    }

    public Trigger(String name, String note, int masterPin, int slavePin, boolean shouldBeState, boolean triggerState){
        this.name = name;
        this.note = note;
        this.shouldBeState = shouldBeState;
        this.triggerState = triggerState;
        this.created = LocalDateTime.now();
        this.updated = created;
        createMasterSlave(masterPin, slavePin);
    }

    public void createMasterSlave(int masterPin, int slavePin){
        if(ItemController.registered(masterPin) && ItemController.registered(slavePin)){
            this.masterPin = masterPin;
            this.slavePin = slavePin;
            this.master = ItemController.getItem(ItemController.getItemList().indexOf(new Item(masterPin)));
            this.slave = ItemController.getItem(ItemController.getItemList().indexOf(new Item(slavePin)));
        }
        master.getOutput().removeAllListeners();
        setTrigger();
    }

    public void setTrigger() {

        master.getOutput().addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (shouldBeState) {
                    if (event.getState().isHigh())
                        slave.setState(triggerState);
                    System.out.println(slave.getState());
                } else {
                    if (event.getState().isLow())
                        slave.setState(triggerState);
                    System.out.println(slave.getState());
                }
            }
        });
    }

    public void setSlavePin(int slavePin) {
        this.slavePin = slavePin;
        createMasterSlave(masterPin, slavePin);
    }

    public void setMasterPin(int masterPin) {
        this.masterPin = masterPin;
        createMasterSlave(masterPin, slavePin);
    }

    public void setTriggerState(boolean triggerState) {
        this.triggerState = triggerState;
        master.getOutput().removeAllListeners();
        setTrigger();
    }

    public void setShouldBeState(boolean shouldBeState) {
        this.shouldBeState = shouldBeState;
        master.getOutput().removeAllListeners();
        setTrigger();

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

    public Item getMaster() {
        return master;
    }

    public void setMaster(Item master) {
        this.master = master;
    }

    public boolean isShouldBeState() {
        return shouldBeState;
    }

    public Item getSlave() {
        return slave;
    }

    public void setSlave(Item slave) {
        this.slave = slave;
    }

    public boolean isTriggerState() {
        return triggerState;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated() {
        this.updated = LocalDateTime.now();
    }
}
