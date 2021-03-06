package com.itemOut.home;

import com.database.DataSource;
import com.pi4j.io.gpio.*;
import com.interfaces.IItem;
import com.trigger.home.Trigger;
import com.trigger.home.TriggerController;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item extends PinProvider implements IItem {

    private static GpioController gpio = GpioFactory.getInstance();
    private List<Trigger> itemsTriggerList = new ArrayList<>();
    private DataSource dataSource = new DataSource();

    private int roomId = 0;                 //db
    private String deviceName;              //db
    private String notes;                   //db
    private int gpioPin;                    //db
    private Pin GPIOPin;
    private GpioPinDigitalOutput output;
    private PinState state;                 //db
    private LocalDateTime updated;          //db

    public Item(int gpioPin){
        this.gpioPin = gpioPin;
    }

    public Item(String deviceName, String notes, int gpioPin, int roomId) {
        this.roomId = roomId;
        this.deviceName = deviceName;
        this.notes = notes;
        this.gpioPin = gpioPin;
        this.GPIOPin = createDigitalPin("RaspberryPi GPIO Provider", gpioPin, String.format("GPIO %02d", gpioPin));
        this.output = gpio.provisionDigitalOutputPin(this.GPIOPin, deviceName, PinState.LOW);
        this.output.setShutdownOptions(true);
        this.state = output.getState();
        this.updated = LocalDateTime.now();
    }

    public boolean setState(boolean state) {
        output.setState(state);
        this.state = output.getState();

        /* Trigger Items with Query */
        for(Trigger trigger: itemsTriggerList){
            if(trigger.isShouldBeState() == state){
                ItemController.getItem(trigger.getSlavePin()).setState(trigger.isTriggerState());
                dataSource.dbUpdateItemState(trigger.getSlavePin(), trigger.isTriggerState());
            }
        }
        setUpdate();
        return true;
    }

    public void loadTriggerItems(){
        itemsTriggerList.clear();
        for(Trigger trigger: TriggerController.triggerList){
            if(!itemsTriggerList.contains(trigger)) {
                if (trigger.getMasterPin() == this.gpioPin) {
                    itemsTriggerList.add(trigger);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return gpioPin == item.gpioPin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gpioPin);
    }

    public static GpioController getGpio() {
        return gpio;
    }

    public void setGpioPin(int gpioPin) {
        this.gpioPin = gpioPin;
        releasePin();
        this.GPIOPin = createDigitalPin("RaspberryPi GPIO Provider", gpioPin, String.format("GPIO %02d", gpioPin));
        this.output = gpio.provisionDigitalOutputPin(GPIOPin, deviceName, PinState.LOW);
        setUpdate();
    }

    public void releasePin(){
        gpio.unprovisionPin(this.output);
    }

    public void listTrigger(){
        System.out.println("/********Item's Trigger List*********/");
        for(Trigger trigger : itemsTriggerList ){
            System.out.println( "Name: " + trigger.getName() + "\n" +
                    "Master: " + trigger.getMasterPin() + "\n" +
                    "Slave: " + trigger.getSlavePin() + "\n" +
                    "MasterShould: " + trigger.isShouldBeState() + "\n" +
                    "SlaveShould: " + trigger.isTriggerState() + "\n");
        }
        System.out.println("/************************************/");
    }

    //Getters and Setters

    public int getRoomId(){
        return this.roomId;
    }

    public void setRoomId(int roomId){
        this.roomId = roomId;
    }

    public static void setGpio(GpioController gpio) {
        Item.gpio = gpio;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        setUpdate();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
        setUpdate();
    }

    public int getGpioPin() {
        return gpioPin;
    }

    public GpioPinDigitalOutput getOutput() {
        return output;
    }

    public void setOutput(GpioPinDigitalOutput output) {
        this.output = output;
        setUpdate();
    }

    public PinState getState() {
        return this.state;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdate() {
        this.updated = LocalDateTime.now();
    }

}
