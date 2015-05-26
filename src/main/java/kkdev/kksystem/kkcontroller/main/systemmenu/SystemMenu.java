/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.systemmenu;

import kkdev.kksystem.base.classes.display.DisplayConstants;
import static kkdev.kksystem.base.classes.display.DisplayConstants.KK_DISPLAY_COMMAND.DISPLAY_KKSYS_PAGE_ACTIVATE;
import kkdev.kksystem.base.classes.display.DisplayConstants.KK_DISPLAY_DATA;
import kkdev.kksystem.base.classes.display.PinLedData;
import kkdev.kksystem.base.classes.plugins.simple.PluginManagerLCD;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
import kkdev.kksystem.kkcontroller.main.SettingsManager;
import kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader;

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
    
    final static String PAGE_MENU_FRAME_P1_VAR="SYSMENU_P1";
    final static String PAGE_MENU_FRAME_P2_VAR="SYSMENU_P2";
    //
    static String[] MenuItems;
    static String[] MenuItemsSYS;
    
    static PluginManagerLCD LCDDisplay;
    
    public static void InitDisplay()
    {
        //
        LCDDisplay=new PluginManagerLCD();
        
        LCDDisplay.BaseConnector=PluginLoader.PlEx;
        //
        MenuItems=new String[4];
        MenuItemsSYS=new String[4];
        //
        MenuItems[0] = "ODB Monitor"; //16
        MenuItems[1] = "System info"; //16
        MenuItems[2] = "Settings"; //16
        MenuItems[3] = "Power off"; //16
        MenuItemsSYS[0] = SystemConsts.KK_BASE_FEATURES_ODB_DIAG_UID; 
        MenuItemsSYS[1] = SystemConsts.KK_BASE_FEATURES_SYSTEM_VERSIONINFO_UID;
        MenuItemsSYS[2] = SystemConsts.KK_BASE_FEATURES_SYSTEM_SETTINGS_UID; 
        MenuItemsSYS[3] = ""; //16
        
        //
        String[] Data_S = new String[4];
        Data_S[0] = PAGE_1;
        Data_S[1] = PAGE_2;
        Data_S[2] = PAGE_VERINFO;
        Data_S[3] = PAGE_SETTINGS;
        
        //
        //Init pages
        LCDDisplay._DISPLAY_SendPluginMessageCommandDirect(SettingsManager.GetSystemDisplayUID(),KK_BASE_FEATURES_SYSTEM_UID,DisplayConstants.KK_DISPLAY_COMMAND.DISPLAY_KKSYS_PAGE_INIT,null,Data_S, null, null);
        // Set page to active
        LCDDisplay._DISPLAY_SendPluginMessageCommandDirect(SettingsManager.GetSystemDisplayUID(), KK_BASE_FEATURES_SYSTEM_UID, DISPLAY_KKSYS_PAGE_ACTIVATE, PAGE_1, null,null, null);
        
    }
    public static void ShowMenu()
    {
        //DISPLAY_SendPluginMessageCommand(DisplayConstants.KK_DISPLAY_DATA.DISPLAY_KKSYS_TEXT_UPDATE_FRAME,PAGE_1, null, null, null);
    }
    
    public static void ChangeSelectDown()
    {
        
    }
    public static void ChangeSelectUp()
    {
        
    }
    
    public static void ChangeCurrentFeature(String FeatureUID)
    {
       PluginLoader.PlEx.ChangeFeature(FeatureUID);
    
    }
    //
    //
    //
    public static void SendMenuState(String Page, String[] FrameKeys,String[] FrameValues)
    {
        PinLedData PData=new PinLedData();
        
        PData.DataType=KK_DISPLAY_DATA.DISPLAY_KKSYS_TEXT_UPDATE_FRAME;
        PData.TargetPage=Page;
        PData.OnFrame_DataKeys=FrameKeys;
        PData.OnFrame_DataValues=FrameValues;
        //
       // DISPLAY_SendPluginMessageData(PData);
        LCDDisplay._DISPLAY_SendPluginMessageDataDirect(SettingsManager.GetSystemDisplayUID(),KK_BASE_FEATURES_SYSTEM_UID,PData);
        //
    }
    /*
    public static void DISPLAY_SendPluginMessageCommand(DisplayConstants.KK_DISPLAY_COMMAND Command, String PageID,String[] DataStr, int[] DataInt, boolean[] DataBool) {
        PluginMessage Msg = new PluginMessage();
        Msg.PinName = KK_PLUGIN_BASE_LED_COMMAND;
        //
        PinLedCommand PData = new PinLedCommand();
        PData.Command = Command;
        PData.BOOL = DataBool;
        PData.INT = DataInt;
        PData.STRING = DataStr;
        PData.PageID=PageID;
        
        PData.FeatureUID=KK_BASE_FEATURES_SYSTEM_UID;

        Msg.PinData = PData;
        //
        PluginLoader.PlEx.ExecuteDirectCommand(SettingsManager.GetSystemDisplayUID(), Msg);
        //
    }
        public static void DISPLAY_SendPluginMessageData(PinLedData PData) {
        PluginMessage Msg = new PluginMessage();
        Msg.PinName = KK_PLUGIN_BASE_LED_DATA;
        //
        PData.FeatureUID=KK_BASE_FEATURES_SYSTEM_UID;
        Msg.PinData = PData;
        //
        PluginLoader.PlEx.ExecuteDirectCommand(SettingsManager.GetSystemDisplayUID(), Msg);
    }
   */
}
