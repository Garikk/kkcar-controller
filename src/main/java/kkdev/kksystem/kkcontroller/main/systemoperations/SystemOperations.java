/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.systemoperations;

import kkdev.kksystem.base.classes.base.PinBaseCommand;
import static kkdev.kksystem.base.classes.base.PinBaseCommand.BASE_COMMAND_TYPE.CHANGE_FEATURE;
import static kkdev.kksystem.base.classes.base.PinBaseCommand.BASE_COMMAND_TYPE.CURRENT_FEATURE;
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
        if (Msg.PinName.equals(PluginConsts.KK_PLUGIN_BASE_CONTROL_DATA))
        {
            SystemMenu.ProcessCommands(Msg);
        }
        //Redirect all PIN CMD data to KK control
        else if (Msg.PinName.equals(PluginConsts.KK_PLUGIN_BASE_PIN_COMMAND))
        {
            SystemKKControl.ProcessKKCommand(Msg);
        }
    }
    
    
     //Change active system feature
   public static void InternetStateChanged(boolean State)
   {
        PluginMessage Msg = new PluginMessage();
        Msg.PinName = KK_PLUGIN_BASE_PIN_COMMAND;
        //
        PinBaseCommand PData = new PinBaseCommand();
        if (State)
            PData.baseCommand=PinBaseCommand.BASE_COMMAND_TYPE.INTERNET_STATE_ACTIVE;
        else
            PData.baseCommand=PinBaseCommand.BASE_COMMAND_TYPE.INTERNET_STATE_INACTIVE;
        //
        Msg.FeatureID=KK_BASE_FEATURES_SYSTEM_BROADCAST_UID;
        Msg.PinData = PData;
        //

        PluginLoader.PlEx.ExecuteDirectCommand(KK_BASE_FEATURES_SYSTEM_BROADCAST_UID, Msg);
        //
   }
   
   //Change active system feature
   public static void ChangeFeature(String FeatureID,String UIContextID)
   {
        PluginMessage Msg = new PluginMessage();
        Msg.PinName = KK_PLUGIN_BASE_PIN_COMMAND;
        //
        PinBaseCommand PData = new PinBaseCommand();
        PData.baseCommand=CHANGE_FEATURE;
        PData.changeUIContextID=UIContextID;
        PData.changeFeatureID=FeatureID;
        //
        
        Msg.FeatureID=KK_BASE_FEATURES_SYSTEM_BROADCAST_UID;
        Msg.PinData = PData;
        Msg.SenderUID=KK_PLUGIN_BASE_PLUGIN_UUID;
        
        //
        PluginLoader.PlEx.ExecuteDirectCommand(KK_BASE_FEATURES_SYSTEM_BROADCAST_UID, Msg);
        //
   }
   
   public static void SystemStateChangedAlert()
   {
       if (WatchDogService.getInstance().InternetState)
            InternetStateChanged(true);
       else
            InternetStateChanged(false);   
    
   }
   
   public static void SendCurrentFeature()
   {
        PluginMessage Msg = new PluginMessage();
        Msg.PinName = KK_PLUGIN_BASE_PIN_COMMAND;
        //
        PinBaseCommand PData = new PinBaseCommand();
        PData.baseCommand=CURRENT_FEATURE;
        //
        Msg.FeatureID=KK_BASE_FEATURES_SYSTEM_BROADCAST_UID;
        PData.changeFeatureID=CurrentFeatureGlobal;
        Msg.PinData = PData;
     PluginLoader.PlEx.ExecuteDirectCommand(KK_BASE_FEATURES_SYSTEM_BROADCAST_UID, Msg);
   }
}
