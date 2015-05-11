/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.pluginmanager;

import java.util.ArrayList;
import java.util.HashMap;
import kkdev.kksystem.base.classes.PluginConnection;
import kkdev.kksystem.base.classes.PluginConnectionsConfig;
import kkdev.kksystem.base.classes.PluginMessage;
import kkdev.kksystem.base.interfaces.IPluginBaseInterface;
import kkdev.kksystem.base.interfaces.IPluginKKConnector;
import kkdev.kksystem.kkcontroller.main.SettingsManager;

/**
 *
 * @author blinov_is
 */
public class PluginExecute implements IPluginBaseInterface {
     HashMap<String,HashMap<String,ArrayList<IPluginKKConnector>>> Pin;  
     HashMap<String,IPluginKKConnector> ActivePlugins;
    
    public PluginExecute(HashMap<String,IPluginKKConnector> Plugins)
    {
        ActivePlugins=Plugins;
        //
        Pin=new HashMap();
        //
        ArrayList<PluginConnectionsConfig> ConnConfig=SettingsManager.GetPluginConfigurations();
        //
        for (PluginConnectionsConfig PCC:ConnConfig)
        {
            for (PluginConnection PC:PCC.Connections)
            {
                for (String PIN:PC.PinName)
                    RegisterPINTarget(PC.SourcePluginUID,PC.TargetPluginUID,PIN,Plugins.get(PC.TargetPluginUID));
            }
        }
        
    }
    
    private void RegisterPINTarget(String SenderPluginUUID, String TargetPluginPIN,String PIN, IPluginKKConnector PluginConnector)
    {
        //
        if (!Pin.containsKey(SenderPluginUUID))
            Pin.put(SenderPluginUUID,new HashMap());
        //
        if (!Pin.get(SenderPluginUUID).containsKey(PIN))
            Pin.get(SenderPluginUUID).put(PIN, new ArrayList<>());
        //
        Pin.get(SenderPluginUUID).get(PIN).add(PluginConnector);
            
        //
          System.out.println("[DEBUG][PLUGIN INTERCON][REG] " + SenderPluginUUID + " " + TargetPluginPIN + " " + PIN);
    }
    
     public  void InitPlugins()
   {
       for (IPluginKKConnector CONN: ActivePlugins.values())
       {
           CONN.PluginInit(this);
       }
   
   }
   public  void StartPlugins()
   {
       for (IPluginKKConnector CONN: ActivePlugins.values())
       {
           CONN.PluginStart();
       }
   
   }
   public   void StopPlugins()
   {
       ActivePlugins.values().stream().forEach((CONN) -> {
           CONN.PluginStop();
        });
   
   }
    @Override
    public  PluginMessage ExecutePinCommand(PluginMessage PP) {
        System.out.println("[DEBUG][PLUGIN INTERCON] " + PP.PinName + " " + PP.SenderUID);
        return InternalExecutePin(PP);
    }
    //
    private  PluginMessage InternalExecutePin(PluginMessage PP)
    {
        if (!Pin.containsKey(PP.SenderUID))
            System.out.println("Wrong PIN received (not found sender)");
        
        if (!Pin.get(PP.SenderUID).containsKey(PP.PinName))
            System.out.println("Wrong PIN received (Not found Pin)");
        
        
       // Pin.get(PP.SenderUID).get(PP.PinName).stream().forEach((PKK) -> {
       //     PKK.ExecutePin(PP);
       // });
        ArrayList<IPluginKKConnector> Exec=Pin.get(PP.SenderUID).get(PP.PinName);
        for (IPluginKKConnector PKK:Exec)
        {
          PKK.ExecutePin(PP);
        }
    
        return null;
    }

}

