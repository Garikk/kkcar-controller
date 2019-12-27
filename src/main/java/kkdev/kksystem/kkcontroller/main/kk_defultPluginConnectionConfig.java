/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main;

import kkdev.kksystem.base.classes.display.tools.menumaker.MKMenuItem;
import static kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker.KK_MENUMAKER_SPECIALCMD_SUBMENU;
import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.classes.plugins.PluginConnection;
import static kkdev.kksystem.base.constants.PluginConsts.*;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.*;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.*;


/**
 *
 * @author blinov_is
 */
public final class kk_defultPluginConnectionConfig {

    static int FEATURE_ODB = 0;
    static int FEATURE_VERSION = 1;
    static int FEATURE_SETTINGS = 2;
    static int FEATURE_MOBILEANDROID = 3;
    static int FEATURE_BLUETOOTH = 4;
    static int FEATURE_MEDIACENTER = 5;

    public static FeatureConfiguration[] GetDefaultFeature() {
        FeatureConfiguration[] Ret = new FeatureConfiguration[6];

        Ret[FEATURE_VERSION] = new FeatureConfiguration();
        Ret[FEATURE_VERSION].FeatureName = "Version info";
        Ret[FEATURE_VERSION].FeatureUUID = KK_BASE_FEATURES_SYSTEM_VERSIONINFO_UID;
        Ret[FEATURE_VERSION].IsSystemFeature = true;
        Ret[FEATURE_VERSION].DefaultUIContext=KK_BASE_UICONTEXT_DEFAULT;

        Ret[FEATURE_SETTINGS] = new FeatureConfiguration();
        Ret[FEATURE_SETTINGS].FeatureName = "Settings";
        Ret[FEATURE_SETTINGS].FeatureUUID = KK_BASE_FEATURES_SYSTEM_SETTINGS_UID;
        Ret[FEATURE_SETTINGS].IsSystemFeature = true;
        Ret[FEATURE_SETTINGS].DefaultUIContext=KK_BASE_UICONTEXT_DEFAULT;
      //
        Ret[FEATURE_ODB] = new FeatureConfiguration();
        Ret[FEATURE_ODB].FeatureName = "ODB Diag Displ";
        Ret[FEATURE_ODB].FeatureUUID = KK_BASE_FEATURES_ODB_DIAG_UID;
        Ret[FEATURE_ODB].IsSystemFeature = false;
        Ret[FEATURE_ODB].ShowInSystemMenu = true;
        Ret[FEATURE_ODB].Connections = new PluginConnection[11];
        Ret[FEATURE_ODB].DefaultUIContext=KK_BASE_UICONTEXT_DEFAULT;
//
        Ret[FEATURE_MOBILEANDROID] = new FeatureConfiguration();
        Ret[FEATURE_MOBILEANDROID].FeatureName = "Android App";
        Ret[FEATURE_MOBILEANDROID].FeatureUUID = KK_BASE_FEATURES_ODB_DIAG_ANDROIDAPP_UID;
        Ret[FEATURE_MOBILEANDROID].IsSystemFeature = false;
        Ret[FEATURE_MOBILEANDROID].ShowInSystemMenu = false;
        Ret[FEATURE_MOBILEANDROID].Connections = new PluginConnection[2];
        Ret[FEATURE_MOBILEANDROID].DefaultUIContext=KK_BASE_UICONTEXT_DEFAULT;
//
        Ret[FEATURE_BLUETOOTH] = new FeatureConfiguration();
        Ret[FEATURE_BLUETOOTH].FeatureName = "Bluetooth";
        Ret[FEATURE_BLUETOOTH].FeatureUUID = KK_BASE_FEATURES_BLUETOOTH_UID;
        Ret[FEATURE_BLUETOOTH].IsSystemFeature = false;
        Ret[FEATURE_BLUETOOTH].ShowInSystemMenu = true;
        Ret[FEATURE_BLUETOOTH].Connections = new PluginConnection[4];
        Ret[FEATURE_BLUETOOTH].DefaultUIContext=KK_BASE_UICONTEXT_DEFAULT;
        //
        Ret[FEATURE_MEDIACENTER] = new FeatureConfiguration();
        Ret[FEATURE_MEDIACENTER].FeatureName = "Media Center";
        Ret[FEATURE_MEDIACENTER].FeatureUUID = KK_BASE_FEATURES_MEDIAPLAYER_UID;
        Ret[FEATURE_MEDIACENTER].IsSystemFeature = false;
        Ret[FEATURE_MEDIACENTER].ShowInSystemMenu = true;
        Ret[FEATURE_MEDIACENTER].Connections = new PluginConnection[2];
        Ret[FEATURE_MEDIACENTER].DefaultUIContext=KK_BASE_UICONTEXT_DEFAULT;
        //
        // ODB->DataProcessor
        Ret[FEATURE_ODB].Connections[0] = new PluginConnection();
        Ret[FEATURE_ODB].Connections[0].ConnectionName = "ODB to Data Display processor";
        Ret[FEATURE_ODB].Connections[0].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_ODB2;
        Ret[FEATURE_ODB].Connections[0].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_ODB2_UUID;
        Ret[FEATURE_ODB].Connections[0].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[0].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[0].PinName = new String[2];
        Ret[FEATURE_ODB].Connections[0].PinName[0] = KK_PLUGIN_BASE_ODB2_DATA;
        Ret[FEATURE_ODB].Connections[0].PinName[1] = KK_PLUGIN_BASE_ODB2_COMMAND;
        //DataProcessor->ODB
        Ret[FEATURE_ODB].Connections[1] = new PluginConnection();
        Ret[FEATURE_ODB].Connections[1].ConnectionName = "Data Display processor to ODB";
        Ret[FEATURE_ODB].Connections[1].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[1].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[1].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_ODB2;
        Ret[FEATURE_ODB].Connections[1].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_ODB2_UUID;
        Ret[FEATURE_ODB].Connections[1].PinName = new String[1];
        Ret[FEATURE_ODB].Connections[1].PinName[0] = KK_PLUGIN_BASE_ODB2_COMMAND;
        //DataProcessor->LED
        Ret[FEATURE_ODB].Connections[2] = new PluginConnection();
        Ret[FEATURE_ODB].Connections[2].ConnectionName = "Data Display processor to LED";
        Ret[FEATURE_ODB].Connections[2].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[2].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[2].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
        Ret[FEATURE_ODB].Connections[2].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[2].PinName = new String[2];
        Ret[FEATURE_ODB].Connections[2].PinName[0] = KK_PLUGIN_BASE_LED_COMMAND;
        Ret[FEATURE_ODB].Connections[2].PinName[1] = KK_PLUGIN_BASE_LED_DATA;
        //LED->Data Processor
        Ret[FEATURE_ODB].Connections[3] = new PluginConnection();
        Ret[FEATURE_ODB].Connections[3].ConnectionName = "LED to DataProcessor";
        Ret[FEATURE_ODB].Connections[3].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
        Ret[FEATURE_ODB].Connections[3].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[3].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[3].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[3].PinName = new String[1];
        Ret[FEATURE_ODB].Connections[3].PinName[0] = KK_PLUGIN_BASE_LED_DATA;
        //
        //Controls->Data Processor
        Ret[FEATURE_ODB].Connections[4] = new PluginConnection();
        Ret[FEATURE_ODB].Connections[4].ConnectionName = "Controls to DataProcessor";
        Ret[FEATURE_ODB].Connections[4].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_HID;
        Ret[FEATURE_ODB].Connections[4].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_HID_UUID;
        Ret[FEATURE_ODB].Connections[4].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[4].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[4].PinName = new String[1];
        Ret[FEATURE_ODB].Connections[4].PinName[0] = KK_PLUGIN_BASE_CONTROL_DATA;

        //Controls->External
        Ret[FEATURE_ODB].Connections[5] = new PluginConnection();
        Ret[FEATURE_ODB].Connections[5].ConnectionName = "Controls to ExtConnector";
        Ret[FEATURE_ODB].Connections[5].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_HID;
        Ret[FEATURE_ODB].Connections[5].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_HID_UUID;
        Ret[FEATURE_ODB].Connections[5].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
        Ret[FEATURE_ODB].Connections[5].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
        Ret[FEATURE_ODB].Connections[5].PinName = new String[1];
        Ret[FEATURE_ODB].Connections[5].PinName[0] = KK_PLUGIN_BASE_CONTROL_DATA;
        //DataProcessor->External
        Ret[FEATURE_ODB].Connections[6] = new PluginConnection();
        Ret[FEATURE_ODB].Connections[6].ConnectionName = "DataProcessor to ExtConnector";
        Ret[FEATURE_ODB].Connections[6].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[6].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[6].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
        Ret[FEATURE_ODB].Connections[6].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
        Ret[FEATURE_ODB].Connections[6].PinName = new String[2];
        Ret[FEATURE_ODB].Connections[6].PinName[0] = KK_PLUGIN_BASE_LED_DATA;
        Ret[FEATURE_ODB].Connections[6].PinName[1] = KK_PLUGIN_BASE_LED_COMMAND;

        //DataProcessor->External
        Ret[FEATURE_ODB].Connections[7] = new PluginConnection();
        Ret[FEATURE_ODB].Connections[7].ConnectionName = "ExtConnector to DataProcessor";
        Ret[FEATURE_ODB].Connections[7].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
        Ret[FEATURE_ODB].Connections[7].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
        Ret[FEATURE_ODB].Connections[7].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[7].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[7].PinName = new String[4];
        Ret[FEATURE_ODB].Connections[7].PinName[0] = KK_PLUGIN_BASE_CONTROL_DATA;
        Ret[FEATURE_ODB].Connections[7].PinName[1] = KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA;
        Ret[FEATURE_ODB].Connections[7].PinName[2] = KK_PLUGIN_BASE_LED_DATA;
        Ret[FEATURE_ODB].Connections[7].PinName[3] = KK_PLUGIN_BASE_LED_COMMAND;

         //RSCOMM->Controls Smarthead
        Ret[FEATURE_ODB].Connections[8] = new PluginConnection();
        Ret[FEATURE_ODB].Connections[8].ConnectionName = "Smarthead RSCOMM to Controls";
        Ret[FEATURE_ODB].Connections[8].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_RSCOMM;
        Ret[FEATURE_ODB].Connections[8].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_RSCOMM_UUID;
        Ret[FEATURE_ODB].Connections[8].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_HID;
        Ret[FEATURE_ODB].Connections[8].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_HID_UUID;
        Ret[FEATURE_ODB].Connections[8].PinName = new String[1];
        Ret[FEATURE_ODB].Connections[8].PinName[0] = KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA;
        //LEDDisplay->RSCOMM Smarthead
        Ret[FEATURE_ODB].Connections[9] = new PluginConnection();
        Ret[FEATURE_ODB].Connections[9].ConnectionName = "Smarthead Display to RSCOMM";
        Ret[FEATURE_ODB].Connections[9].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
        Ret[FEATURE_ODB].Connections[9].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[9].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_RSCOMM;
        Ret[FEATURE_ODB].Connections[9].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_RSCOMM_UUID;
        Ret[FEATURE_ODB].Connections[9].PinName = new String[1];
        Ret[FEATURE_ODB].Connections[9].PinName[0] = KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA;
          //LEDDisplay->RSCOMM Smarthead
        Ret[FEATURE_ODB].Connections[10] = new PluginConnection();
        Ret[FEATURE_ODB].Connections[10].ConnectionName = "Data Processor to TTS";
        Ret[FEATURE_ODB].Connections[10].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
        Ret[FEATURE_ODB].Connections[10].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[10].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_TTS;
        Ret[FEATURE_ODB].Connections[10].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_TTS_UUID;
        Ret[FEATURE_ODB].Connections[10].PinName = new String[1];
        Ret[FEATURE_ODB].Connections[10].PinName[0] = KK_PLUGIN_BASE_NOTIFY_DATA;
        //
          

         //DataProcessor->LED
        Ret[FEATURE_BLUETOOTH].Connections[0] = new PluginConnection();
        Ret[FEATURE_BLUETOOTH].Connections[0].ConnectionName = "Bluetooth to LED";
        Ret[FEATURE_BLUETOOTH].Connections[0].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH;
        Ret[FEATURE_BLUETOOTH].Connections[0].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_UUID;
        Ret[FEATURE_BLUETOOTH].Connections[0].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
        Ret[FEATURE_BLUETOOTH].Connections[0].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        Ret[FEATURE_BLUETOOTH].Connections[0].PinName = new String[2];
        Ret[FEATURE_BLUETOOTH].Connections[0].PinName[0] = KK_PLUGIN_BASE_LED_COMMAND;
        Ret[FEATURE_BLUETOOTH].Connections[0].PinName[1] = KK_PLUGIN_BASE_LED_DATA;
        //LED->Data Processor
        Ret[FEATURE_BLUETOOTH].Connections[1] = new PluginConnection();
        Ret[FEATURE_BLUETOOTH].Connections[1].ConnectionName = "LED to Bluetooth";
        Ret[FEATURE_BLUETOOTH].Connections[1].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
        Ret[FEATURE_BLUETOOTH].Connections[1].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        Ret[FEATURE_BLUETOOTH].Connections[1].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH;
        Ret[FEATURE_BLUETOOTH].Connections[1].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_UUID;
        Ret[FEATURE_BLUETOOTH].Connections[1].PinName = new String[1];
        Ret[FEATURE_BLUETOOTH].Connections[1].PinName[0] = KK_PLUGIN_BASE_LED_DATA;
         //Controls->Bluetooth
        Ret[FEATURE_BLUETOOTH].Connections[2] = new PluginConnection();
        Ret[FEATURE_BLUETOOTH].Connections[2].ConnectionName = "Controls to Bluetooth";
        Ret[FEATURE_BLUETOOTH].Connections[2].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_HID;
        Ret[FEATURE_BLUETOOTH].Connections[2].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_HID_UUID;
        Ret[FEATURE_BLUETOOTH].Connections[2].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH;
        Ret[FEATURE_BLUETOOTH].Connections[2].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_UUID;
        Ret[FEATURE_BLUETOOTH].Connections[2].PinName = new String[1];
        Ret[FEATURE_BLUETOOTH].Connections[2].PinName[0] = KK_PLUGIN_BASE_CONTROL_DATA;
        // BT to TTS
        Ret[FEATURE_BLUETOOTH].Connections[3] = new PluginConnection();
        Ret[FEATURE_BLUETOOTH].Connections[3].ConnectionName = "Bluetooth to TTS";
        Ret[FEATURE_BLUETOOTH].Connections[3].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH;
        Ret[FEATURE_BLUETOOTH].Connections[3].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_UUID;
        Ret[FEATURE_BLUETOOTH].Connections[3].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_TTS;
        Ret[FEATURE_BLUETOOTH].Connections[3].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_TTS_UUID;
        Ret[FEATURE_BLUETOOTH].Connections[3].PinName = new String[1];
        Ret[FEATURE_BLUETOOTH].Connections[3].PinName[0] = KK_PLUGIN_BASE_NOTIFY_DATA;
        

         //
        //External->ODB2
        Ret[FEATURE_MOBILEANDROID].Connections[0] = new PluginConnection();
        Ret[FEATURE_MOBILEANDROID].Connections[0].ConnectionName = "ExtConnector to ODB2";
        Ret[FEATURE_MOBILEANDROID].Connections[0].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
        Ret[FEATURE_MOBILEANDROID].Connections[0].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
        Ret[FEATURE_MOBILEANDROID].Connections[0].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_ODB2;
        Ret[FEATURE_MOBILEANDROID].Connections[0].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_ODB2_UUID;
        Ret[FEATURE_MOBILEANDROID].Connections[0].PinName = new String[2];
        Ret[FEATURE_MOBILEANDROID].Connections[0].PinName[0] = KK_PLUGIN_BASE_ODB2_DATA;
        Ret[FEATURE_MOBILEANDROID].Connections[0].PinName[1] = KK_PLUGIN_BASE_ODB2_COMMAND;
        //ODB2->External
        Ret[FEATURE_MOBILEANDROID].Connections[1] = new PluginConnection();
        Ret[FEATURE_MOBILEANDROID].Connections[1].ConnectionName = "ODB2 to ExtConnector";
        Ret[FEATURE_MOBILEANDROID].Connections[1].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_ODB2;
        Ret[FEATURE_MOBILEANDROID].Connections[1].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_ODB2_UUID;
        Ret[FEATURE_MOBILEANDROID].Connections[1].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
        Ret[FEATURE_MOBILEANDROID].Connections[1].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
        Ret[FEATURE_MOBILEANDROID].Connections[1].PinName = new String[2];
        Ret[FEATURE_MOBILEANDROID].Connections[1].PinName[0] = KK_PLUGIN_BASE_ODB2_DATA;
        Ret[FEATURE_MOBILEANDROID].Connections[1].PinName[1] = KK_PLUGIN_BASE_ODB2_COMMAND;
          
        // Mediacenter
        //
        //Controls->Mediacenter
        Ret[FEATURE_MEDIACENTER].Connections[0] = new PluginConnection();
        Ret[FEATURE_MEDIACENTER].Connections[0].ConnectionName = "Controls to Mediacenter";
        Ret[FEATURE_MEDIACENTER].Connections[0].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_HID;
        Ret[FEATURE_MEDIACENTER].Connections[0].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_HID_UUID;
        Ret[FEATURE_MEDIACENTER].Connections[0].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_MEDIACENTER;
        Ret[FEATURE_MEDIACENTER].Connections[0].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_MEDIACENTER_UUID;
        Ret[FEATURE_MEDIACENTER].Connections[0].PinName = new String[1];
        Ret[FEATURE_MEDIACENTER].Connections[0].PinName[0] = KK_PLUGIN_BASE_CONTROL_DATA;
        //MediaCenter->Leddisplay
        Ret[FEATURE_MEDIACENTER].Connections[1] = new PluginConnection();
        Ret[FEATURE_MEDIACENTER].Connections[1].ConnectionName = "Mediacenter to Leddisplay";
        Ret[FEATURE_MEDIACENTER].Connections[1].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_MEDIACENTER;
        Ret[FEATURE_MEDIACENTER].Connections[1].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_MEDIACENTER_UUID;
        Ret[FEATURE_MEDIACENTER].Connections[1].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
        Ret[FEATURE_MEDIACENTER].Connections[1].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        Ret[FEATURE_MEDIACENTER].Connections[1].PinName = new String[2];
        Ret[FEATURE_MEDIACENTER].Connections[1].PinName[0] = KK_PLUGIN_BASE_LED_COMMAND;
        Ret[FEATURE_MEDIACENTER].Connections[1].PinName[1] = KK_PLUGIN_BASE_LED_DATA;
        return Ret;

    }

    public static MKMenuItem[] GetDefaultSystemMenuItems() {
        //We create only system items, feature items creating in SystemMenu module from non-system features
        MKMenuItem[] Ret;

        //
        // Settings 
        //  -test setting1
        //  -test setting2
        //  -test setting3
        //  -test submenu
        //  --test setting1
        //  --test setting2
        // Board tools
        //  -SysInfo        
        //  -reboot
        //  -poweroff
        // KK Info
        //  -Plugins
        //  -Version
        Ret = new MKMenuItem[3]; // 3 main items (see above)
        Ret[0] = new MKMenuItem();
        Ret[0].displayName = "Settings";
        Ret[0].itemCommand = KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[0].subItems = new MKMenuItem[4];
        Ret[0].subItems[0] = new MKMenuItem();
        Ret[0].subItems[0].displayName = "Plugins";
        Ret[0].subItems[0].itemCommand = "$KK_PLUGINS_QUICKSETTINGS";
        Ret[0].subItems[1] = new MKMenuItem();
        Ret[0].subItems[1].displayName = "nothing 1";
        Ret[0].subItems[1].itemCommand = "";
        Ret[0].subItems[2] = new MKMenuItem();
        Ret[0].subItems[2].displayName = "nothing 2";
        Ret[0].subItems[2].itemCommand = "";
        Ret[0].subItems[3] = new MKMenuItem();
        Ret[0].subItems[3].displayName = "sub 1";
        Ret[0].subItems[3].itemCommand = KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[0].subItems[3].subItems = new MKMenuItem[2];
        Ret[0].subItems[3].subItems[0] = new MKMenuItem();
        Ret[0].subItems[3].subItems[0].displayName = "Test 1";
        Ret[0].subItems[3].subItems[0].itemCommand = "";
        Ret[0].subItems[3].subItems[1] = new MKMenuItem();
        Ret[0].subItems[3].subItems[1].displayName = "Test 2";
        Ret[0].subItems[3].subItems[1].itemCommand = "";
        Ret[1] = new MKMenuItem();
        Ret[1].displayName = "Board tools";
        Ret[1].itemCommand = KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[1].subItems = new MKMenuItem[3];
        Ret[1].subItems[0] = new MKMenuItem();
        Ret[1].subItems[0].displayName = "System Info";
        Ret[1].subItems[0].itemCommand = MNU_CMD_SYSMENU_PFX + " " + MNU_CMD_SYSMENU_PFX_BRDTOOLS + " " + MNU_CMD_BRD_TOOLS_BOARDINFO;
        Ret[1].subItems[1] = new MKMenuItem();
        Ret[1].subItems[1].displayName = "Reboot";
        Ret[1].subItems[1].itemCommand = MNU_CMD_SYSMENU_PFX + " " + MNU_CMD_SYSMENU_PFX_BRDTOOLS + " " + MNU_CMD_BRD_TOOLS_REBOOT;
        Ret[1].subItems[2] = new MKMenuItem();
        Ret[1].subItems[2].displayName = "Power Off";
        Ret[1].subItems[2].itemCommand = MNU_CMD_SYSMENU_PFX + " " + MNU_CMD_SYSMENU_PFX_BRDTOOLS + " " + MNU_CMD_BRD_TOOLS_POWEROFF;
        Ret[2] = new MKMenuItem();
        Ret[2].displayName = "KK Info";
        Ret[2].itemCommand = KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[2].subItems = new MKMenuItem[2];
        Ret[2].subItems[0] = new MKMenuItem();
        Ret[2].subItems[0].displayName = "Plugins";
        Ret[2].subItems[0].itemCommand = MNU_CMD_SYSMENU_PFX + " " + MNU_CMD_SYSMENU_PFX_INFO + " " + MNU_CMD_BRD_INFO_PLUGINS;
        Ret[2].subItems[1] = new MKMenuItem();
        Ret[2].subItems[1].displayName = "Version";
        Ret[2].subItems[1].itemCommand = MNU_CMD_SYSMENU_PFX + " " + MNU_CMD_SYSMENU_PFX_INFO + " " + MNU_CMD_BRD_INFO_VERSION;
        return Ret;
    }

}
