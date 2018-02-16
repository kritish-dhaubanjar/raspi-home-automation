package com.itemOut.home;

import com.pi4j.io.gpio.*;

public interface IItem {

    static GpioController gpio = GpioFactory.getInstance();

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

    void setGpioPin(int gpioPin);

    void releasePin();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
