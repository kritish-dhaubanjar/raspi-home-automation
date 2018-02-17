package com.itemOut.home;

import com.pi4j.io.gpio.*;
import com.trigger.home.Trigger;

import java.util.ArrayList;
import java.util.List;

public interface IItem {

    static GpioController gpio = GpioFactory.getInstance();

    List<Trigger> itemsTriggerList = new ArrayList<>();
    /**
     * String deviceName;              //db
     * String notes;                   //db
     * int gpioPin;                    //db
     * Pin GPIOPin;
     * GpioPinDigitalOutput output;
     * PinState state;                 //db
     * LocalDateTime created;          //db
     * LocalDateTime updated;          //db
     **/

    void setState(boolean state);

    void loadTriggerItems();

    void setGpioPin(int gpioPin);

    void releasePin();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
