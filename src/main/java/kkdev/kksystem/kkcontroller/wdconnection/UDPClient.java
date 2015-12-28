/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.wdconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kkdev.kksystem.kkcontroller.main.KKController;

/**
 *
 * @author blinov_is
 */
public class UDPClient {

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
                    Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
                }

                
                try {
                    System.out.println("START UDP");
                    while (!Stop) {
                        InetAddress IPAddress = InetAddress.getByName("localhost");
                        byte[] sendData = new byte[1024];
                        byte[] receiveData = new byte[1024];
                        sendData = new byte[4];
                        sendData[0] = 7;
                        sendData[1] = 17;
                        sendData[2] = KKController.CurrentSystemState.GetCurrentStateB();
                        sendData[3] =  KKController.CurrentSystemState.GetTargetStateB();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 39876);
                        clientSocket.send(sendPacket);
                        /*
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        clientSocket.receive(receivePacket);
                        String modifiedSentence = new String(receivePacket.getData());
                        System.out.println("FROM SERVER:" + modifiedSentence);
                        */
                        Thread.sleep(1000);
                    }
                    clientSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("STOP UDP");
            }
        });
        myThready.start();
    }
}
