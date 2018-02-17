import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.trigger.GpioToggleStateTrigger;

import java.util.EventListener;

public class TriggerTest {
    public static void main(String[] args) throws InterruptedException {

        GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalOutput pin0 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00);
        pin0.setShutdownOptions(true);
        GpioPinDigitalOutput pin1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);
        pin0.setShutdownOptions(true);

        Runnable run = new Runnable() {
            @Override
            public void run() {
                while (true)
                    if(pin0.getState().isHigh())
                        pin1.setState(true);
            }
        };


        Thread thread = new Thread(run);
        thread.start();

        Thread.sleep(1000);
        System.out.println("PIN 0" + pin0.getState());
        System.out.println("PIN 1" + pin1.getState());

        pin0.setState(true);
        Thread.sleep(1000);
        System.out.println("PIN 0" + pin0.getState());
        System.out.println("PIN 1" + pin1.getState());

        pin0.setState(false);
        Thread.sleep(1000);
        System.out.println("PIN 0" + pin0.getState());
        System.out.println("PIN 1" + pin1.getState());

    }
}
