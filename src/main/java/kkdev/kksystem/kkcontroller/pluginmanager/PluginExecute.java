/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.pluginmanager;

import java.util.ArrayList;
import java.util.HashMap;
import kkdev.kksystem.base.classes.base.PinBaseCommand;
import kkdev.kksystem.base.classes.plugins.PluginConnection;
import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PIN_COMMAND;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_BROADCAST_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
import kkdev.kksystem.base.interfaces.IPluginBaseInterface;
import kkdev.kksystem.base.interfaces.IPluginKKConnector;
import kkdev.kksystem.kkcontroller.main.SettingsManager;
import kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu;

/**
 *
 * @author blinov_is
 */
public class PluginExecute implements IPluginBaseInterface {
     
    //Pin path: FeatureID,SenderID,PIN,array of connectors
     HashMap<String,HashMap<String,HashMap<String,ArrayList<IPluginKKConnector>>>> Pin;  
     HashMap<String,IPluginKKConnector> ActivePlugins;

    public PluginExecute(HashMap<String, IPluginKKConnector> Plugins) {
        ActivePlugins = Plugins;
        //
        Pin = new HashMap();
        //
        for (FeatureConfiguration Feature : SettingsManager.MainConfiguration.Features) {
            if (Feature.Connections != null) {
                for (PluginConnection PC : Feature.Connections) {
                    for (String PIN : PC.PinName) {
                        RegisterPINTarget(Feature.FeatureUUID, PC.SourcePluginUID, PC.TargetPluginUID, PIN);
                    }
                }
            }
        }

    }

    private void RegisterPINTarget(String FeatureID, String SenderPluginUUID, String TargetPluginUID,String PIN)
    {
        //
        if (!Pin.containsKey(FeatureID))
            Pin.put(FeatureID,new HashMap());
        //
        if (!Pin.get(FeatureID).containsKey(SenderPluginUUID))
            Pin.get(FeatureID).put(SenderPluginUUID,new HashMap());
        //
        if (!Pin.get(FeatureID).get(SenderPluginUUID).containsKey(PIN))
            Pin.get(FeatureID).get(SenderPluginUUID).put(PIN, new ArrayList<>());
        //
        Pin.get(FeatureID).get(SenderPluginUUID).get(PIN).add(ActivePlugins.get(TargetPluginUID));
            
        //
          System.out.println("[DEBUG][PLUGIN INTERCON][REG] " + ActivePlugins.get(SenderPluginUUID) + " " + ActivePlugins.get(TargetPluginUID) + " " + PIN);
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

   //Change active system feature
   public void ChangeFeature(String FeatureID)
   {
        PluginMessage Msg = new PluginMessage();
        Msg.PinName = KK_PLUGIN_BASE_PIN_COMMAND;
        //
        PinBaseCommand PData = new PinBaseCommand();
        PData.BaseCommand=PinBaseCommand.BASE_COMMAND_TYPE.CHANGE_FEATURE;
        //
        Msg.FeatureID=FeatureID;
        Msg.PinData = PData;
        //
        ExecuteDirectCommand(KK_BASE_FEATURES_SYSTEM_BROADCAST_UID, Msg);
        //
   }
   
    @Override
    public  PluginMessage ExecutePinCommand(PluginMessage PP) {
        return InternalExecutePin(PP);
    }
    //
    
    private  PluginMessage InternalExecutePin(PluginMessage PP)
    {
        SystemBasePINReceiver(PP);
                
         if (!Pin.containsKey(PP.FeatureID))
            System.out.println("Wrong PIN received (not found feature)");
        if (!Pin.get(PP.FeatureID).containsKey(PP.SenderUID))
            System.out.println("Wrong PIN received (not found sender)");
        
        if (!Pin.get(PP.FeatureID).get(PP.SenderUID).containsKey(PP.PinName))
            System.out.println("Wrong PIN received (Not found Pin)");
        
        
        ArrayList<IPluginKKConnector> Exec=null;
        //
        if (PP.FeatureID.equals(KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID))
        {
           for (String Ftr:Pin.keySet())
           {
               Exec=Pin.get(Ftr).get(PP.SenderUID).get(PP.PinName);
               InternalExecutePin_Exec(Exec,PP);
           }
        }
        else
        {
            Exec=Pin.get(PP.FeatureID).get(PP.SenderUID).get(PP.PinName);
            InternalExecutePin_Exec(Exec,PP);
        }    
        
        if (Exec==null)
            return null;
        
    
    
        return null;
    }
    private void InternalExecutePin_Exec(ArrayList<IPluginKKConnector> Exec, PluginMessage PP)
    {
        for (IPluginKKConnector PKK:Exec)
        {
          System.out.println("[DEBUG][INTERCON] " + PP.PinName + " >> " + PKK.GetPluginInfo().PluginName);
          PKK.ExecutePin(PP);
        }
    }
    
    @Override
    public PluginMessage _ExecutePinCommandDirect(String PluginUUID, PluginMessage PP) {
       return ExecuteDirectCommand(PluginUUID,PP);
    }
    
    public PluginMessage ExecuteDirectCommand(String TargetUUID,PluginMessage PP)
    {
        if (TargetUUID.equals("")){
            TargetUUID=KK_BASE_FEATURES_SYSTEM_BROADCAST_UID;
        }
        
        SystemBasePINReceiver(PP);
        
        if (!TargetUUID.equals(KK_BASE_FEATURES_SYSTEM_BROADCAST_UID))
        {
            System.out.println("[DEBUG][PLUGIN INTERCON] " + PP.PinName + " >> " + ActivePlugins.get(TargetUUID).GetPluginInfo().PluginName);
            return ActivePlugins.get(TargetUUID).ExecutePin(PP);
        }
        else
            for (IPluginKKConnector PKK:ActivePlugins.values())
            {
                System.out.println("[DEBUG][PLUGIN INTERCON] " + PP.PinName + " >> " + PKK.GetPluginInfo().PluginName);
                return PKK.ExecutePin(PP);
            }
        //
        return null;
    }

    //
    //
    //
    //
    //
    private void SystemBasePINReceiver(PluginMessage PP) {
        switch (PP.FeatureID) {
            case (KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID):
                SystemMenu.ProcessCommands(PP);
                break;
            case (KK_BASE_FEATURES_SYSTEM_UID):
                SystemMenu.ProcessCommands(PP);
                break;
        }

    }
    
    
   
}
