/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.systemmenu;

import kkdev.kksystem.base.classes.PluginMessage;
import kkdev.kksystem.base.classes.display.DisplayConstants;
import kkdev.kksystem.base.classes.display.PinLedCommand;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_LED_COMMAND;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_MENU_UID;
import kkdev.kksystem.kkcontroller.main.SettingsManager;
import kkdev.kksystem.kkcontroller.pluginmanager.PluginManager;

/**
 *
 * @author blinov_is
 */
public abstract class SystemMenu {
    static String SelectedItem;
    final static String PAGE_1="SYSMENU_1";
    final static String PAGE_2="SYSMENU_2";
    final static String PAGE_VERINFO="SYSMENU_VI";
    final static String PAGE_SETTINGS="SYSMENU_SETT";    
    //
    String[] MenuItems;
    
    
    
    public static void InitDisplay()
    {
        String[] Data_S = new String[4];
        Data_S[0] = PAGE_1;
        Data_S[1] = PAGE_2;
        Data_S[2] = PAGE_VERINFO;
        Data_S[3] = PAGE_SETTINGS;
        
        //
        //Init pages
        DISPLAY_SendPluginMessageCommand(DisplayConstants.KK_DISPLAY_COMMAND.DISPLAY_KKSYS_PAGE_INIT, Data_S, null, null);
        // Set page to active
        Data_S = new String[1];
        Data_S[0] = PAGE_1;
        //
        DISPLAY_SendPluginMessageCommand(DisplayConstants.KK_DISPLAY_COMMAND.DISPLAY_KKSYS_PAGE_ACTIVATE, Data_S, null, null);
        
    }
    public static void ChangeSelectDown()
    {
        
    }
    public static void ChangeSelectUp()
    {
        
    }
    
    public static void DISPLAY_SendPluginMessageCommand(DisplayConstants.KK_DISPLAY_COMMAND Command, String[] DataStr, int[] DataInt, boolean[] DataBool) {
        PluginMessage Msg = new PluginMessage();
        Msg.PinName = KK_PLUGIN_BASE_LED_COMMAND;
        //
        PinLedCommand PData = new PinLedCommand();
        PData.Command = Command;
        PData.BOOL = DataBool;
        PData.INT = DataInt;
        PData.STRING = DataStr;
        
        PData.FeatureUID=KK_BASE_FEATURES_SYSTEM_MENU_UID;

        Msg.PinData = PData;
        //
        PluginManager.PlEx.ExecuteDirectCommand(SettingsManager.GetSystemDisplayUID(), Msg);
        //
    }
}
