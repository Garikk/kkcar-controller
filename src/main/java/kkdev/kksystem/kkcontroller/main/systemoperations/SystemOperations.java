/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.systemoperations;

import kkdev.kksystem.base.classes.base.PinDataFtrCtx;
import kkdev.kksystem.base.classes.base.PinDataSystemOperations;
import kkdev.kksystem.base.classes.base.PluginMessageData;
import kkdev.kksystem.base.classes.notify.PinDataNotifySystemState;
import kkdev.kksystem.base.classes.notify.PluginMessageData_Notify;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.constants.PluginConsts;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PIN_COMMAND;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_UUID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_BROADCAST_UID;
import kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu;
import kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader;
import kkdev.kksystem.kkcontroller.wdconnection.WatchDogService;

/**
 *
 * @author blinov_is
 */
public class SystemOperations {
    public static String CurrentFeatureGlobal;
    
    public static void ProcessSystemPIN(PluginMessage Msg)
    {
        //Redirect all control data to menu module
        if (Msg.pinName.equals(PluginConsts.KK_PLUGIN_BASE_CONTROL_DATA))
        {
            SystemMenu.processCommands(Msg);
        }
        //Redirect all PIN CMD data to KK control
        else if (Msg.pinName.equals(PluginConsts.KK_PLUGIN_BASE_PIN_COMMAND))
        {
            SystemKKControl.ProcessKKCommand((PinDataSystemOperations)Msg.getPinData());
        }
    }
    
    
     //Change active system feature
   public static void internetStateChanged(boolean State)
   {
       
        //
        PinDataNotifySystemState PData = new PinDataNotifySystemState();
        if (State)
            PData.systemState=PinDataNotifySystemState.SystemStateInfo.INERNET_ACTIVE;
        else
            PData.systemState=PinDataNotifySystemState.SystemStateInfo.INERNET_INACTIVE;
        //
        PluginMessage Msg = new PluginMessageData_Notify(PData);
        Msg.pinName = KK_PLUGIN_BASE_PIN_COMMAND;
        Msg.FeatureID=KK_BASE_FEATURES_SYSTEM_BROADCAST_UID;
        //

        PluginLoader.PlEx.ExecuteDirectCommand(KK_BASE_FEATURES_SYSTEM_BROADCAST_UID, Msg);
        //
   }
   
   //Change active system feature
   public static void changeFeature(String FeatureID,String UIContextID)
   {
       
        //
        PinDataFtrCtx PData = new PinDataFtrCtx();
        PData.managementCommand=PinDataFtrCtx.FCManagementCommand.ChangeFeature;
        PData.changeUIContextID=UIContextID;
        PData.changeFeatureID=FeatureID;
        //
        PluginMessageData Msg = new PluginMessageData(PData);
        Msg.pinName = KK_PLUGIN_BASE_PIN_COMMAND;
        Msg.FeatureID=KK_BASE_FEATURES_SYSTEM_BROADCAST_UID;
        Msg.SenderUID=KK_PLUGIN_BASE_PLUGIN_UUID;
        
        //
        PluginLoader.PlEx.ExecuteDirectCommand(KK_BASE_FEATURES_SYSTEM_BROADCAST_UID, Msg);
        //
   }
   
   public static void systemStateChangedAlert()
   {
       if (WatchDogService.getInstance().InternetState)
            internetStateChanged(true);
        else
            internetStateChanged(false);

    }

    public static void sendCurrentFeature() {

        //
        PinDataFtrCtx PData = new PinDataFtrCtx();
        PData.managementCommand = PinDataFtrCtx.FCManagementCommand.CurrentFeature;
        PData.changeFeatureID = CurrentFeatureGlobal;
        //

        PluginMessage Msg = new PluginMessageData(PData);
        Msg.pinName = KK_PLUGIN_BASE_PIN_COMMAND;
        Msg.FeatureID = KK_BASE_FEATURES_SYSTEM_BROADCAST_UID;

        PluginLoader.PlEx.ExecuteDirectCommand(KK_BASE_FEATURES_SYSTEM_BROADCAST_UID, Msg);
    }
}
