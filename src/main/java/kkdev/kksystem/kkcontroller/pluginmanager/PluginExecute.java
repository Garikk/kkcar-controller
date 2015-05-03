/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.pluginmanager;

import java.util.ArrayList;
import java.util.HashMap;
import kkdev.kksystem.base.classes.PluginConnection;
import kkdev.kksystem.base.classes.PluginMessage;
import kkdev.kksystem.base.interfaces.IPluginBaseInterface;
import kkdev.kksystem.base.interfaces.IPluginKKConnector;

/**
 *
 * @author blinov_is
 */
public class PluginExecute implements IPluginBaseInterface {
    HashMap<String,HashMap<String,IPluginKKConnector>> Pin;   //KEY - Pin, VALUE - Plugin,PluginConnector
    HashMap<String,ArrayList<String>> PinFilter; //Target, Destination
    HashMap<String,IPluginKKConnector> ActivePlugins;
    
    PluginExecute(HashMap<String,IPluginKKConnector> Plugins, ArrayList<PluginConnection> Connections)
    {
        ActivePlugins=Plugins;
        Pin=new HashMap<>();
        PinFilter =new HashMap<>();
        
        Connections.stream().map((PC) -> {
            for (String PCPin : PC.PinName)
            {
                if (!Pin.containsKey(PCPin))
                {
                    Pin.put(PCPin, new HashMap<>());
                }
                //
                if (!Pin.get(PCPin).containsKey(PC.TargetPluginUID))
                {
                    Pin.get(PCPin).put(PC.TargetPluginUID,ActivePlugins.get(PC.TargetPluginUID));
                }
                //
            }
            return PC;
        }).map((PC) -> {
            // Fill filter array
            if (!PinFilter.containsKey(PC.SourcePluginUID))
            {
                PinFilter.put(PC.SourcePluginUID, new ArrayList<>());
            }
            return PC;
        }).forEach((PC) -> {
            PinFilter.get(PC.SourcePluginUID).add(PC.TargetPluginUID);
        });
    }
     public void InitPlugins()
   {
       for (IPluginKKConnector CONN: ActivePlugins.values())
       {
           CONN.PluginInit(this);
       }
   
   }
   public void StartPlugins()
   {
       for (IPluginKKConnector CONN: ActivePlugins.values())
       {
           CONN.PluginStart();
       }
   
   }
   public void StopPlugins()
   {
       for (IPluginKKConnector CONN: ActivePlugins.values())
       {
           CONN.PluginStop();
       }
   
   }
    @Override
    public PluginMessage ExecutePinCommand(PluginMessage PP) {
        System.out.println("[DEBUG][PLUGIN INTERCON] " + PP.PinName + " " + PP.SenderUID);
        return InternalExecutePin(PP);
    }
    //
    private PluginMessage InternalExecutePin(PluginMessage PP)
    {
        if (!Pin.containsKey(PP.PinName))
            System.out.println("Wrong PIN received");
        
        
        //Pin.get(PP.PinName).stream().forEach((Pl) -> {
//            Pl.ExecutePin(PP);
//        });
        HashMap<String,IPluginKKConnector> PlExec=Pin.get(PP.PinName);
        PlExec.values().stream().forEach((PKK) -> {
            PKK.ExecutePin(PP);
        });
    
        return null;
    }

}

