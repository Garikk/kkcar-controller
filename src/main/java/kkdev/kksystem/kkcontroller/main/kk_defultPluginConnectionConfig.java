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
    static int FEATURE_MEDIACENTER = 4;

    public static FeatureConfiguration[] GetDefaultFeature() {
        FeatureConfiguration[] Ret = new FeatureConfiguration[5];

        Ret[FEATURE_VERSION] = new FeatureConfiguration();
        Ret[FEATURE_VERSION].FeatureName = "Version info";
        Ret[FEATURE_VERSION].FeatureUUID = KK_BASE_FEATURES_SYSTEM_VERSIONINFO_UID;
        Ret[FEATURE_VERSION].IsSystemFeature = true;
        Ret[FEATURE_VERSION].DefaultUIContext=SystemConsts.KK_BASE_UICONTEXT_DEFAULT;

        Ret[FEATURE_SETTINGS] = new FeatureConfiguration();
        Ret[FEATURE_SETTINGS].FeatureName = "Settings";
        Ret[FEATURE_SETTINGS].FeatureUUID = KK_BASE_FEATURES_SYSTEM_SETTINGS_UID;
        Ret[FEATURE_SETTINGS].IsSystemFeature = true;
        Ret[FEATURE_SETTINGS].DefaultUIContext=SystemConsts.KK_BASE_UICONTEXT_DEFAULT;

        //
        Ret[FEATURE_ODB] = new FeatureConfiguration();
        Ret[FEATURE_ODB].FeatureName = "ODB Diag Displ";
        Ret[FEATURE_ODB].FeatureUUID = KK_BASE_FEATURES_ODB_DIAG_UID;
        Ret[FEATURE_ODB].IsSystemFeature = false;
        Ret[FEATURE_ODB].ShowInSystemMenu = true;
        Ret[FEATURE_ODB].Connections = new PluginConnection[12];
        Ret[FEATURE_ODB].DefaultUIContext=SystemConsts.KK_BASE_UICONTEXT_DEFAULT;
        //
        Ret[FEATURE_MOBILEANDROID] = new FeatureConfiguration();
        Ret[FEATURE_MOBILEANDROID].FeatureName = "Android App";
        Ret[FEATURE_MOBILEANDROID].FeatureUUID = KK_BASE_FEATURES_ODB_DIAG_ANDROIDAPP_UID;
        Ret[FEATURE_MOBILEANDROID].IsSystemFeature = false;
        Ret[FEATURE_MOBILEANDROID].ShowInSystemMenu = false;
        Ret[FEATURE_MOBILEANDROID].Connections = new PluginConnection[2];
        Ret[FEATURE_MOBILEANDROID].DefaultUIContext=SystemConsts.KK_BASE_UICONTEXT_DEFAULT;
         //
      //  Ret[FEATURE_MEDIACENTER] = new FeatureConfiguration();
       // Ret[FEATURE_MEDIACENTER].FeatureName = "Media center";
       // Ret[FEATURE_MEDIACENTER].FeatureUUID = KK_BASE_FEATURES_ODB_DIAG_ANDROIDAPP_UID;
       // Ret[FEATURE_MEDIACENTER].IsSystemFeature = true;
       // Ret[FEATURE_MEDIACENTER].ShowInSystemMenu = true;
       // Ret[FEATURE_MEDIACENTER].Connections = new PluginConnection[2];
       // Ret[FEATURE_MEDIACENTER].DefaultUIContext=SystemConsts.KK_BASE_UICONTEXT_DEFAULT;
        //
        Ret[FEATURE_BLUETOOTH] = new FeatureConfiguration();
        Ret[FEATURE_BLUETOOTH].FeatureName = "Bluetooth";
        Ret[FEATURE_BLUETOOTH].FeatureUUID = KK_BASE_FEATURES_BLUETOOTH_UID;
        Ret[FEATURE_BLUETOOTH].IsSystemFeature = false;
        Ret[FEATURE_BLUETOOTH].ShowInSystemMenu = true;
        Ret[FEATURE_BLUETOOTH].Connections = new PluginConnection[3];
        Ret[FEATURE_BLUETOOTH].DefaultUIContext=SystemConsts.KK_BASE_UICONTEXT_DEFAULT;
        
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

        //External->Bluetooth
        Ret[FEATURE_ODB].Connections[8] = new PluginConnection();
        Ret[FEATURE_ODB].Connections[8].ConnectionName = "ExtConnector to Bluetooth";
        Ret[FEATURE_ODB].Connections[8].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
        Ret[FEATURE_ODB].Connections[8].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
        Ret[FEATURE_ODB].Connections[8].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH;
        Ret[FEATURE_ODB].Connections[8].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_UUID;
        Ret[FEATURE_ODB].Connections[8].PinName = new String[1];
        Ret[FEATURE_ODB].Connections[8].PinName[0] = KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA;
        //Bluetooth->External
        Ret[FEATURE_ODB].Connections[9] = new PluginConnection();
        Ret[FEATURE_ODB].Connections[9].ConnectionName = "Bluetooth to ExtConnector";
        Ret[FEATURE_ODB].Connections[9].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH;
        Ret[FEATURE_ODB].Connections[9].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_UUID;
        Ret[FEATURE_ODB].Connections[9].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
        Ret[FEATURE_ODB].Connections[9].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
        Ret[FEATURE_ODB].Connections[9].PinName = new String[1];
        Ret[FEATURE_ODB].Connections[9].PinName[0] = KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA;
        //
         //RSCOMM->Controls Smarthead
        Ret[FEATURE_ODB].Connections[10] = new PluginConnection();
        Ret[FEATURE_ODB].Connections[10].ConnectionName = "Smarthead RSCOMM to Controls";
        Ret[FEATURE_ODB].Connections[10].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_RSCOMM;
        Ret[FEATURE_ODB].Connections[10].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_RSCOMM_UUID;
        Ret[FEATURE_ODB].Connections[10].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_HID;
        Ret[FEATURE_ODB].Connections[10].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_HID_UUID;
        Ret[FEATURE_ODB].Connections[10].PinName = new String[1];
        Ret[FEATURE_ODB].Connections[10].PinName[0] = KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA;
        //LEDDisplay->RSCOMM Smarthead
        Ret[FEATURE_ODB].Connections[11] = new PluginConnection();
        Ret[FEATURE_ODB].Connections[11].ConnectionName = "Smarthead Display to RSCOMM";
        Ret[FEATURE_ODB].Connections[11].SourcePluginName = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
        Ret[FEATURE_ODB].Connections[11].SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[11].TargetPluginName = KK_PLUGIN_BASE_PLUGIN_RSCOMM;
        Ret[FEATURE_ODB].Connections[11].TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_RSCOMM_UUID;
        Ret[FEATURE_ODB].Connections[11].PinName = new String[1];
        Ret[FEATURE_ODB].Connections[11].PinName[0] = KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA;
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
        //External->ODB2
        //*
        //[FEATURE_ODB].Connections[10]=new PluginConnection();
        //Ret[FEATURE_ODB].Connections[10].ConnectionName="ExtConnector to ODB2";
        // Ret[FEATURE_ODB].Connections[10].SourcePluginName=KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
        // Ret[FEATURE_ODB].Connections[10].SourcePluginUID=KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
        ///Ret[FEATURE_ODB].Connections[10].TargetPluginName=KK_PLUGIN_BASE_PLUGIN_ODB2;
        //Ret[FEATURE_ODB].Connections[10].TargetPluginUID=KK_PLUGIN_BASE_PLUGIN_ODB2_UUID;
        //Ret[FEATURE_ODB].Connections[10].PinName=new String[2];
        //Ret[FEATURE_ODB].Connections[10].PinName[0]=KK_PLUGIN_BASE_ODB2_DATA;
        //Ret[FEATURE_ODB].Connections[10].PinName[1]=KK_PLUGIN_BASE_ODB2_COMMAND;
        //ODB2->External
        //Ret[FEATURE_ODB].Connections[11]=new PluginConnection();
        //Ret[FEATURE_ODB].Connections[11].ConnectionName="ODB2 to ExtConnector";
        //Ret[FEATURE_ODB].Connections[11].SourcePluginName=KK_PLUGIN_BASE_PLUGIN_ODB2;
        //Ret[FEATURE_ODB].Connections[11].SourcePluginUID=KK_PLUGIN_BASE_PLUGIN_ODB2_UUID;
        //Ret[FEATURE_ODB].Connections[11].TargetPluginName=KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
        //Ret[FEATURE_ODB].Connections[11].TargetPluginUID=KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
        //Ret[FEATURE_ODB].Connections[11].PinName=new String[2];
        //Ret[FEATURE_ODB].Connections[11].PinName[0]=KK_PLUGIN_BASE_ODB2_DATA;
        //Ret[FEATURE_ODB].Connections[11].PinName[1]=KK_PLUGIN_BASE_ODB2_COMMAND;
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
        Ret = new MKMenuItem[4]; // 3 main items (see above)
        Ret[0] = new MKMenuItem();
        //=================TEMPORARY
        Ret[0].DisplayName = "Media Center";
        Ret[0].ItemCommand = KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[0].SubItems = new MKMenuItem[2];
        Ret[0].SubItems[0] = new MKMenuItem();
        Ret[0].SubItems[0].DisplayName = "Internet radio";
        Ret[0].SubItems[0].ItemCommand = KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[0].SubItems[0].SubItems = new MKMenuItem[2];
        Ret[0].SubItems[0].SubItems[0] = new MKMenuItem();
        Ret[0].SubItems[0].SubItems[0].DisplayName = "Record Club";
        Ret[0].SubItems[0].SubItems[0].ItemCommand = KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[0].SubItems[0].SubItems[0].SubItems = new MKMenuItem[1];
        Ret[0].SubItems[0].SubItems[0].SubItems[0] = new MKMenuItem();
        Ret[0].SubItems[0].SubItems[0].SubItems[0].DisplayName = "Playing";
        Ret[0].SubItems[0].SubItems[0].SubItems[0].ItemCommand = KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[0].SubItems[0].SubItems[1] = new MKMenuItem();
        Ret[0].SubItems[0].SubItems[1].DisplayName = "Record Radio";
        Ret[0].SubItems[0].SubItems[1].ItemCommand = KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[0].SubItems[0].SubItems[1].SubItems = new MKMenuItem[1];
        Ret[0].SubItems[0].SubItems[1].SubItems[0] = new MKMenuItem();
        Ret[0].SubItems[0].SubItems[1].SubItems[0].DisplayName = "Playing";
        Ret[0].SubItems[0].SubItems[1].SubItems[0].ItemCommand = KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[0].SubItems[1] = new MKMenuItem();
        Ret[0].SubItems[1].DisplayName = "Media File";
        Ret[0].SubItems[1].ItemCommand = "";
        //=========================
        Ret[1] = new MKMenuItem();
        Ret[1].DisplayName = "Settings";
        Ret[1].ItemCommand = KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[1].SubItems = new MKMenuItem[4];
        Ret[1].SubItems[0] = new MKMenuItem();
        Ret[1].SubItems[0].DisplayName = "Test Prm11";
        Ret[1].SubItems[0].ItemCommand = "";
        Ret[1].SubItems[1] = new MKMenuItem();
        Ret[1].SubItems[1].DisplayName = "Test Prm12";
        Ret[1].SubItems[1].ItemCommand = "";
        Ret[1].SubItems[2] = new MKMenuItem();
        Ret[1].SubItems[2].DisplayName = "Test Prm13";
        Ret[1].SubItems[2].ItemCommand = "";
        Ret[1].SubItems[3] = new MKMenuItem();
        Ret[1].SubItems[3].DisplayName = "test submenu";
        Ret[1].SubItems[3].ItemCommand = KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[1].SubItems[3].SubItems = new MKMenuItem[2];
        Ret[1].SubItems[3].SubItems[0] = new MKMenuItem();
        Ret[1].SubItems[3].SubItems[0].DisplayName = "Test Prm21";
        Ret[1].SubItems[3].SubItems[0].ItemCommand = "";
        Ret[1].SubItems[3].SubItems[1] = new MKMenuItem();
        Ret[1].SubItems[3].SubItems[1].DisplayName = "Test Prm22";
        Ret[1].SubItems[3].SubItems[1].ItemCommand = "";
        Ret[2] = new MKMenuItem();
        Ret[2].DisplayName = "Board tools";
        Ret[2].ItemCommand = KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[2].SubItems = new MKMenuItem[3];
        Ret[2].SubItems[0] = new MKMenuItem();
        Ret[2].SubItems[0].DisplayName = "System Info";
        Ret[2].SubItems[0].ItemCommand = MNU_CMD_SYSMENU_PFX + " " + MNU_CMD_SYSMENU_PFX_BRDTOOLS + " " + MNU_CMD_BRD_TOOLS_BOARDINFO;
        Ret[2].SubItems[1] = new MKMenuItem();
        Ret[2].SubItems[1].DisplayName = "Reboot";
        Ret[2].SubItems[1].ItemCommand = MNU_CMD_SYSMENU_PFX + " " + MNU_CMD_SYSMENU_PFX_BRDTOOLS + " " + MNU_CMD_BRD_TOOLS_REBOOT;
        Ret[2].SubItems[2] = new MKMenuItem();
        Ret[2].SubItems[2].DisplayName = "Power Off";
        Ret[2].SubItems[2].ItemCommand = MNU_CMD_SYSMENU_PFX + " " + MNU_CMD_SYSMENU_PFX_BRDTOOLS + " " + MNU_CMD_BRD_TOOLS_POWEROFF;
        Ret[3] = new MKMenuItem();
        Ret[3].DisplayName = "KK Info";
        Ret[3].ItemCommand = KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[3].SubItems = new MKMenuItem[2];
        Ret[3].SubItems[0] = new MKMenuItem();
        Ret[3].SubItems[0].DisplayName = "Plugins";
        Ret[3].SubItems[0].ItemCommand = MNU_CMD_SYSMENU_PFX + " " + MNU_CMD_SYSMENU_PFX_INFO + " " + MNU_CMD_BRD_INFO_PLUGINS;
        Ret[3].SubItems[1] = new MKMenuItem();
        Ret[3].SubItems[1].DisplayName = "Version";
        Ret[3].SubItems[1].ItemCommand = MNU_CMD_SYSMENU_PFX + " " + MNU_CMD_SYSMENU_PFX_INFO + " " + MNU_CMD_BRD_INFO_VERSION;
        return Ret;
    }

}
