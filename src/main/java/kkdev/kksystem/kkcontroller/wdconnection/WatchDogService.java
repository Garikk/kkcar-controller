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
public class WatchDogService  {
    WatchDogUDPConnection UDC;
    WDSystemState CurrentSystemState;
    private static WatchDogService WDS;
    
    public static WatchDogService getInstance()
    {
        if (WDS==null)
            WDS=new WatchDogService();
        
        return WDS;
    }

    public void StartWDS()
    {
        if (UDC==null)
        {
            UDC=new WatchDogUDPConnection();
        }
        UDC.StartWDUDPClient();
    }
    public void StopWDS()
    {
        if (UDC!=null)
            UDC.Stop();
    }
    
    public synchronized WDSystemState getCurrentSystemState()
    {
        if (CurrentSystemState==null)
            CurrentSystemState=new WDSystemState();
            
        return CurrentSystemState;
    }
    
}
