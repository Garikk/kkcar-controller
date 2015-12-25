package kkdev.kksystem.kkcontroler.wdconnection;

import static java.lang.System.out;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import kkdev.kksystem.kkcontroler.wdconnection.interfaces.IKKWDConnection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author blinov_is
 */
public class WDConnection  {
    

    
    public WDConnection()
    {
        UDPClient.StartWDUDPClient();
    }
    
}
