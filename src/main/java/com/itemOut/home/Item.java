package com.itemOut.home;

import com.pi4j.io.gpio.*;

import java.time.LocalDateTime;
import java.util.Objects;

public class Item extends PinProvider implements IItem {

    private static GpioController gpio = GpioFactory.getInstance();

    private int roomId = 0;                 //db
    private String deviceName;              //db
    private String notes;                   //db
    private int gpioPin;                    //db
    private Pin GPIOPin;
    private GpioPinDigitalOutput output;
    private PinState state;                 //db
    private LocalDateTime created;          //db
    private LocalDateTime updated;          //db

    /** Create Temporary Instance*/
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
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    public void setState(boolean state) {
        output.setState(state);
        this.state = output.getState();
        setUpdate();
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

    /** Release Pin before delete || update */
    public void releasePin(){
        gpio.unprovisionPin(this.output);
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

    public LocalDateTime getCreated(){
        return created;
    }

    public void setUpdate() {
        this.updated = LocalDateTime.now();
    }

}
