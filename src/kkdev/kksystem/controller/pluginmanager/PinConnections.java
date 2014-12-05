/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.pluginmanager;

import java.util.HashMap;
import java.util.List;
import kkdev.kksystem.base.classes.PluginInfo;
import kkdev.kksystem.base.classes.PluginPin;
import kkdev.kksystem.base.interfaces.IKKConnector;
import kkdev.kksystem.base.interfaces.IPluginKKConnector;

/**
 *
 * @author blinov_is
 */
public class PinConnections implements IKKConnector {
    HashMap<String,List<IPluginKKConnector>> PinTransmit;   //KEY - Pin, VALUE - Plugin
    HashMap<String,List<IPluginKKConnector>> PinReceive;
    
    PinConnections(IPluginKKConnector[] Plugins)
    {
        PinReceive=new HashMap<>();
        PinTransmit=new HashMap<>();
        
        for (IPluginKKConnector Pl : Plugins)
        {
            PluginInfo PLInfo=Pl.GetPluginInfo();
            
            for (String Pin:PLInfo.ReceivePins)
            {
                if (!PinReceive.containsKey(Pin))
                    PinReceive.put(Pin, null);
                    
                PinReceive.get(Pin).add(Pl);
                    
            }
            for (String Pin:PLInfo.TransmitPins)
            {
                 if (!PinTransmit.containsKey(Pin))
                    PinTransmit.put(Pin, null);
                    
                PinTransmit.get(Pin).add(Pl);
            }
        }
    }
    

    
    @Override
    public PluginPin ExecutePin(PluginPin PP) {
        return InternalExecutePin(PP);
    }
    //
    private PluginPin InternalExecutePin(PluginPin PP)
    {
        if (!PinReceive.containsKey(PP.PinName))
            System.out.println("Wrong PIN received");
        
        
        PinReceive.get(PP.PinName).stream().forEach((Pl) -> {
            Pl.ExecutePin(PP);
        });
    
        return null;
    }
}

