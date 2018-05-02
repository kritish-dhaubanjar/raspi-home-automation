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

    /**
     * Set the Item's state to passed state, and also Trigger associated items to valid state as defined.
     *
     * @param state Change-to State
     */
    void setState(boolean state);

    /** Load Trigger Items associated with item, masterPin == gpio
     */
    void loadTriggerItems();

    /**
     * Set gpioPin of the Item.
     * @param gpioPin GPIO Pin of Pi.
     */
    void setGpioPin(int gpioPin);

    /** Release Pin before delete || update */
    void releasePin();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
