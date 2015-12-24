/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroler.wdconnection.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import kkdev.kksystem.kkcontroler.wdconnection.WDSystemState;

/**
 *
 * @author blinov_is
 */
public interface IKKWDConnection extends Remote { 
    public WDSystemState GetWDState() throws RemoteException;
    public WDSystemState SetWDState() throws RemoteException;
    
}
