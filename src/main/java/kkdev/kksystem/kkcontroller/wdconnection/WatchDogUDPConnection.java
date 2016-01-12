/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.wdconnection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author blinov_is
 */
public class WatchDogUDPConnection {
    private final int WD_UDPPORT=39876;
    private boolean Stop;

    public void Stop() {
        Stop = true;
    }

    public void StartWDUDPClient() {
        Thread myThready = new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket clientSocket = null;

                try {
                    clientSocket = new DatagramSocket();
                } catch (SocketException ex) {
                    Logger.getLogger(WatchDogUDPConnection.class.getName()).log(Level.SEVERE, null, ex);
                }

                
                try {
                    while (!Stop) {
                        InetAddress IPAddress = InetAddress.getByName("localhost");
                        byte[] sendData;// = new byte[1024];
                        byte[] receiveData = new byte[4];
                        sendData = new byte[4];
                        sendData[0] = 7;
                        sendData[1] = 17;
                        sendData[2] =  WatchDogService.getInstance().getCurrentSystemState().GetCurrentStateB();
                        sendData[3] =  WatchDogService.getInstance().getCurrentSystemState().GetCurrentStateB();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, WD_UDPPORT);
                        clientSocket.send(sendPacket);
                        
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        clientSocket.receive(receivePacket);
                        String modifiedSentence = new String(receivePacket.getData());
                        
                        Thread.sleep(1000);
                    }
                    clientSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(WatchDogUDPConnection.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(WatchDogUDPConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("STOP UDP");
            }
        });
        myThready.start();
    }
}
