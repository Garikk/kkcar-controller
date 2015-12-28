package kkdev.kksystem.kkcontroller.wdconnection;

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
    UDPClient UDC;
    public WDConnection()
    {
        UDC=new UDPClient();
        
        UDC.StartWDUDPClient();
    }
    
}
