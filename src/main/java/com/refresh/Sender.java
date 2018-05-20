package com.refresh;

import java.net.*;

public class Sender extends Thread {

    private final String message = "REFRESH";
    private final int port = 8085;
    private final String host = "224.0.1.2";

    public Sender(){}

    @Override
    public void run() {
        try{
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName(host);
            byte [] buffer = message.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(datagramPacket);
            socket.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
