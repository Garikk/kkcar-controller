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
import kkdev.kksystem.base.constants.PluginConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_BROADCAST_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
import kkdev.kksystem.base.interfaces.IKKControllerUtils;
import kkdev.kksystem.base.interfaces.IPluginBaseInterface;
import kkdev.kksystem.base.interfaces.IPluginKKConnector;
import kkdev.kksystem.kkcontroller.main.systemoperations.SystemOperations;
import kkdev.kksystem.kkcontroller.main.utils.UtilsManager;
import static kkdev.kksystem.kkcontroller.main.ControllerSettingsManager.mainConfiguration;

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
        for (FeatureConfiguration Feature : mainConfiguration.features) {
            if (Feature.Connections != null) {
                for (PluginConnection PC : Feature.Connections) {
                    for (String PIN : PC.PinName) {
                        if (PIN!=null)  //Skip wrong config
                        {
                            RegisterPINTarget(Feature.FeatureUUID, PC.SourcePluginUID, PC.TargetPluginUID, PIN);
                            //Register multicast feature pin
                            RegisterPINTarget(KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID, PC.SourcePluginUID, PC.TargetPluginUID, PIN);
                            //KKController may send any pin to any plugin in system feature
                            RegisterPINTarget(KK_BASE_FEATURES_SYSTEM_UID, PluginConsts.KK_PLUGIN_BASE_PLUGIN_UUID, PC.TargetPluginUID, PIN);
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
        //System.out.println("[PKK] Reg PIN " +PIN + " " +TargetPluginUID +" " +  ActivePlugins.get(TargetPluginUID));
         if (!Pin.get(FeatureID).get(SenderPluginUUID).get(PIN).contains(ActivePlugins.get(TargetPluginUID)))
                Pin.get(FeatureID).get(SenderPluginUUID).get(PIN).add(ActivePlugins.get(TargetPluginUID));
        //
    }
    
     public  void InitPlugins()
   {
       for (IPluginKKConnector CONN: ActivePlugins.values())
       {
           CONN.pluginInit(this,mainConfiguration.configurationUID);
       }
   
   }
   public  void StartPlugins()
   {
       for (IPluginKKConnector CONN: ActivePlugins.values())
       {
           CONN.pluginStart();
       }
   
   }
   public   void StopPlugins()
   {
       ActivePlugins.values().stream().forEach((CONN) -> {
           CONN.pluginStop();
        });
   }

    @Override
    public  PluginMessage executePinCommand(PluginMessage PP) {
     //       out.println("DBG[BSE] " +PP.FeatureID + " PIN " + PP.PinName + " ");
        return InternalExecutePin(PP);
    }
    //
    
    private  PluginMessage InternalExecutePin(PluginMessage PP)
    {
        if (PP.FeatureID==null)
        {
            out.println("[ERR] Wrong PluginMessage! Not found FeatureID Plugin: " + PP.SenderUID + " Pin: " + PP.pinName);
            return null;
        }
        // 
        SystemBasePINReceiver(PP.cloneMessage());
        //
         if (PP.FeatureID.equals(KK_BASE_FEATURES_SYSTEM_UID) & !PP.SenderUID.equals(PluginConsts.KK_PLUGIN_BASE_PLUGIN_UUID))
             return null;
         
         if (!Pin.containsKey(PP.FeatureID))
         {
            out.println("Wrong PIN received (not found feature) FTR " +PP.FeatureID + " PIN " + PP.pinName);
            return null;
         }
        if (!Pin.get(PP.FeatureID).containsKey(PP.SenderUID))
        {
            out.println("Wrong PIN received (not found sender) FTR " +PP.FeatureID + " PIN " + PP.pinName);
            return null;
        }
        
        if (!Pin.get(PP.FeatureID).get(PP.SenderUID).containsKey(PP.pinName))
        {
            out.println("Wrong PIN received (Not found Pin) FTR " +PP.FeatureID + " PIN " + PP.pinName);
            return null;
        }
        
        
        ArrayList<IPluginKKConnector> Exec=null;
        //
      
        Exec=Pin.get(PP.FeatureID).get(PP.SenderUID).get(PP.pinName);

        InternalExecutePin_Exec(Exec,PP);
        
        return null;
    }
    private void InternalExecutePin_Exec(ArrayList<IPluginKKConnector> Exec, PluginMessage PP)
    {

        for (IPluginKKConnector PKK:Exec)
        {
            
          PKK.executePin(PP.cloneMessage());
        }
    }
    
    @Override
    public PluginMessage _executePinCommandDirect(String PluginUUID, PluginMessage PP) {
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
                PKK.executePin(PP.cloneMessage());
            }
            return null;
        }
        else
        {
            return ActivePlugins.get(TargetUUID).executePin(PP.cloneMessage());
        }
    }

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

    @Override
    public IKKControllerUtils systemUtilities() {
        return UtilsManager.getInstance();
    }
    
    
   
}
