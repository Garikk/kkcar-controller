/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.pluginmanager;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.HashMap;
import kkdev.kksystem.base.classes.plugins.PluginConnection;
import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_BROADCAST_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
import kkdev.kksystem.base.interfaces.IPluginBaseInterface;
import kkdev.kksystem.base.interfaces.IPluginKKConnector;
import static kkdev.kksystem.kkcontroller.main.ControllerSettingsManager.MainConfiguration;
import kkdev.kksystem.kkcontroller.main.systemoperations.SystemOperations;

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
        for (FeatureConfiguration Feature : MainConfiguration.Features) {
            if (Feature.Connections != null) {
                for (PluginConnection PC : Feature.Connections) {
                    for (String PIN : PC.PinName) {
                        if (PIN!=null)  //Skip wrong config
                        {
                            RegisterPINTarget(Feature.FeatureUUID, PC.SourcePluginUID, PC.TargetPluginUID, PIN);
                            RegisterPINTarget(KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID, PC.SourcePluginUID, PC.TargetPluginUID, PIN);
                        }
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
    }
    
     public  void InitPlugins()
   {
       for (IPluginKKConnector CONN: ActivePlugins.values())
       {
           CONN.PluginInit(this,MainConfiguration.ConfigurationUID);
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
       //     out.println("[PLEX][INF] PM " + PP.FeatureID + " " + PP.PinName + " " + PP.SenderUID);
        return InternalExecutePin(PP);
    }
    //
    
    private  PluginMessage InternalExecutePin(PluginMessage PP)
    {
        SystemBasePINReceiver(PP);
        //
         if (PP.FeatureID.equals(KK_BASE_FEATURES_SYSTEM_UID))
             return null;
         
         if (!Pin.containsKey(PP.FeatureID))
         {
            out.println("Wrong PIN received (not found feature) FTR " +PP.FeatureID + " PIN " + PP.PinName);
            return null;
         }
        if (!Pin.get(PP.FeatureID).containsKey(PP.SenderUID))
        {
            out.println("Wrong PIN received (not found sender) FTR " +PP.FeatureID + " PIN " + PP.PinName);
            return null;
        }
        
        if (!Pin.get(PP.FeatureID).get(PP.SenderUID).containsKey(PP.PinName))
        {
            out.println("Wrong PIN received (Not found Pin) FTR " +PP.FeatureID + " PIN " + PP.PinName);
            return null;
        }
        
        
        ArrayList<IPluginKKConnector> Exec=null;
        //
        //out.println("FEA " + PP.FeatureID + " SUI " + PP.SenderUID + " PINN "+PP.PinName);
        Exec=Pin.get(PP.FeatureID).get(PP.SenderUID).get(PP.PinName);

        InternalExecutePin_Exec(Exec,PP);
        
        return null;
    }
    private void InternalExecutePin_Exec(ArrayList<IPluginKKConnector> Exec, PluginMessage PP)
    {
        
        for (IPluginKKConnector PKK:Exec)
        {
              
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
        
        if (TargetUUID.equals(KK_BASE_FEATURES_SYSTEM_BROADCAST_UID) | TargetUUID.equals(KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID))
        {
            for (IPluginKKConnector PKK:ActivePlugins.values())
            {
                PKK.ExecutePin(PP);
            }
            return null;
        }
        else
        {
            return ActivePlugins.get(TargetUUID).ExecutePin(PP);
        }
    }

    //
    //
    //
    //
    //
    private void SystemBasePINReceiver(PluginMessage PP) {
        switch (PP.FeatureID) {
            case (KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID):
                SystemOperations.ProcessSystemPIN(PP);
                break;
            case (KK_BASE_FEATURES_SYSTEM_UID):
                SystemOperations.ProcessSystemPIN(PP);
                break;
        }

    }
    
    
   
}
