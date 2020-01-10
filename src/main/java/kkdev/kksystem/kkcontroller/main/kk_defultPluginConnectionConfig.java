/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main;

import java.util.ArrayList;
import java.util.List;
import kkdev.kksystem.base.classes.display.tools.menumaker.MKMenuItem;
import static kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker.KK_MENUMAKER_SPECIALCMD_SUBMENU;
import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.classes.plugins.PluginConnection;
import static kkdev.kksystem.base.constants.PluginConsts.*;
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

    public static FeatureConfiguration[] GetDefaultFeature(List<kk_DefConfProfileTypes> profile) {
        FeatureConfiguration[] Ret = new FeatureConfiguration[6];

        Ret[FEATURE_VERSION] = new FeatureConfiguration();
        Ret[FEATURE_VERSION].FeatureName = "Version info";
        Ret[FEATURE_VERSION].FeatureUUID = KK_BASE_FEATURES_SYSTEM_VERSIONINFO_UID;
        Ret[FEATURE_VERSION].IsSystemFeature = true;
        Ret[FEATURE_VERSION].DefaultUIContext = KK_BASE_UICONTEXT_DEFAULT;

        Ret[FEATURE_SETTINGS] = new FeatureConfiguration();
        Ret[FEATURE_SETTINGS].FeatureName = "Settings";
        Ret[FEATURE_SETTINGS].FeatureUUID = KK_BASE_FEATURES_SYSTEM_SETTINGS_UID;
        Ret[FEATURE_SETTINGS].IsSystemFeature = true;
        Ret[FEATURE_SETTINGS].DefaultUIContext = KK_BASE_UICONTEXT_DEFAULT;

        PluginConnection NewPC;
        if (profile.contains(kk_DefConfProfileTypes.PRF_ODB_DATAVIEW)) {
            Ret[FEATURE_ODB] = new FeatureConfiguration();
            Ret[FEATURE_ODB].FeatureName = "ODB Diag Displ";
            Ret[FEATURE_ODB].FeatureUUID = KK_BASE_FEATURES_ODB_DIAG_UID;
            Ret[FEATURE_ODB].IsSystemFeature = false;
            Ret[FEATURE_ODB].ShowInSystemMenu = true;
            Ret[FEATURE_ODB].Connections = new ArrayList<>();
            Ret[FEATURE_ODB].DefaultUIContext = KK_BASE_UICONTEXT_DEFAULT;
            // ODB->DataProcessor
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "ODB to Data Display processor";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_ODB2;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_ODB2_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
            NewPC.PinName = new String[2];
            NewPC.PinName[0] = KK_PLUGIN_BASE_ODB2_DATA;
            NewPC.PinName[1] = KK_PLUGIN_BASE_ODB2_COMMAND;
            Ret[FEATURE_ODB].Connections.add(NewPC);
            //DataProcessor->ODB
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "Data Display processor to ODB";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_ODB2;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_ODB2_UUID;
            NewPC.PinName = new String[1];
            NewPC.PinName[0] = KK_PLUGIN_BASE_ODB2_COMMAND;
            Ret[FEATURE_ODB].Connections.add(NewPC);
            //DataProcessor->LED
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "Data Display processor to LED";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
            NewPC.PinName = new String[2];
            NewPC.PinName[0] = KK_PLUGIN_BASE_LED_COMMAND;
            NewPC.PinName[1] = KK_PLUGIN_BASE_LED_DATA;
            Ret[FEATURE_ODB].Connections.add(NewPC);
            //LED->Data Processor
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "LED to DataProcessor";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
            NewPC.PinName = new String[1];
            NewPC.PinName[0] = KK_PLUGIN_BASE_LED_DATA;
            Ret[FEATURE_ODB].Connections.add(NewPC);
            //Controls->Data Processor
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "Controls to DataProcessor";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_HID;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_HID_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
            NewPC.PinName = new String[1];
            NewPC.PinName[0] = KK_PLUGIN_BASE_CONTROL_DATA;
            Ret[FEATURE_ODB].Connections.add(NewPC);
            //Controls->External
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "Controls to ExtConnector";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_HID;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_HID_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
            NewPC.PinName = new String[1];
            NewPC.PinName[0] = KK_PLUGIN_BASE_CONTROL_DATA;
            Ret[FEATURE_ODB].Connections.add(NewPC);
            //DataProcessor->External
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "DataProcessor to ExtConnector";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
            NewPC.PinName = new String[2];
            NewPC.PinName[0] = KK_PLUGIN_BASE_LED_DATA;
            NewPC.PinName[1] = KK_PLUGIN_BASE_LED_COMMAND;
            Ret[FEATURE_ODB].Connections.add(NewPC);
            //DataProcessor->External
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "ExtConnector to DataProcessor";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
            NewPC.PinName = new String[4];
            NewPC.PinName[0] = KK_PLUGIN_BASE_CONTROL_DATA;
            NewPC.PinName[1] = KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA;
            NewPC.PinName[2] = KK_PLUGIN_BASE_LED_DATA;
            NewPC.PinName[3] = KK_PLUGIN_BASE_LED_COMMAND;
            Ret[FEATURE_ODB].Connections.add(NewPC);
            //RSCOMM->Controls Smarthead
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "Smarthead RSCOMM to Controls";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_RSCOMM;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_RSCOMM_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_HID;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_HID_UUID;
            NewPC.PinName = new String[1];
            NewPC.PinName[0] = KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA;
            Ret[FEATURE_ODB].Connections.add(NewPC);
            //LEDDisplay->RSCOMM Smarthead
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "Smarthead Display to RSCOMM";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_RSCOMM;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_RSCOMM_UUID;
            NewPC.PinName = new String[1];
            NewPC.PinName[0] = KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA;
            Ret[FEATURE_ODB].Connections.add(NewPC);
            //LEDDisplay->RSCOMM Smarthead
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "Data Processor to TTS";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_TTS;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_TTS_UUID;
            NewPC.PinName = new String[1];
            NewPC.PinName[0] = KK_PLUGIN_BASE_NOTIFY_DATA;
            Ret[FEATURE_ODB].Connections.add(NewPC);
        }
        if (profile.contains(kk_DefConfProfileTypes.PRF_BLUETOOTH_ANDROID) | profile.contains(kk_DefConfProfileTypes.PRF_BLUETOOTH_RPI)) {
            Ret[FEATURE_BLUETOOTH] = new FeatureConfiguration();
            Ret[FEATURE_BLUETOOTH].FeatureName = "Bluetooth";
            Ret[FEATURE_BLUETOOTH].FeatureUUID = KK_BASE_FEATURES_BLUETOOTH_UID;
            Ret[FEATURE_BLUETOOTH].IsSystemFeature = false;
            Ret[FEATURE_BLUETOOTH].ShowInSystemMenu = true;
            Ret[FEATURE_BLUETOOTH].Connections = new ArrayList<>();
            Ret[FEATURE_BLUETOOTH].DefaultUIContext = KK_BASE_UICONTEXT_DEFAULT;
            //DataProcessor->LED
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "Bluetooth to LED";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
            NewPC.PinName = new String[2];
            NewPC.PinName[0] = KK_PLUGIN_BASE_LED_COMMAND;
            NewPC.PinName[1] = KK_PLUGIN_BASE_LED_DATA;
            Ret[FEATURE_BLUETOOTH].Connections.add(NewPC);
            //LED->Data Processor
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "LED to Bluetooth";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_UUID;
            NewPC.PinName = new String[1];
            NewPC.PinName[0] = KK_PLUGIN_BASE_LED_DATA;
            Ret[FEATURE_BLUETOOTH].Connections.add(NewPC);
            //Controls->Bluetooth
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "Controls to Bluetooth";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_HID;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_HID_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_UUID;
            NewPC.PinName = new String[1];
            NewPC.PinName[0] = KK_PLUGIN_BASE_CONTROL_DATA;
            Ret[FEATURE_BLUETOOTH].Connections.add(NewPC);
            // BT to TTS
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "Bluetooth to TTS";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_TTS;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_TTS_UUID;
            NewPC.PinName = new String[1];
            NewPC.PinName[0] = KK_PLUGIN_BASE_NOTIFY_DATA;
            Ret[FEATURE_BLUETOOTH].Connections.add(NewPC);
        }
        if (profile.contains(kk_DefConfProfileTypes.PRF_ANDROID_EXT_CONNECTOR)) {
            Ret[FEATURE_MOBILEANDROID] = new FeatureConfiguration();
            Ret[FEATURE_MOBILEANDROID].FeatureName = "Android App";
            Ret[FEATURE_MOBILEANDROID].FeatureUUID = KK_BASE_FEATURES_ODB_DIAG_ANDROIDAPP_UID;
            Ret[FEATURE_MOBILEANDROID].IsSystemFeature = false;
            Ret[FEATURE_MOBILEANDROID].ShowInSystemMenu = false;
            Ret[FEATURE_MOBILEANDROID].Connections = new ArrayList<>();
            Ret[FEATURE_MOBILEANDROID].DefaultUIContext = KK_BASE_UICONTEXT_DEFAULT;
            //External->ODB2
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "ExtConnector to ODB2";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_ODB2;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_ODB2_UUID;
            NewPC.PinName = new String[2];
            NewPC.PinName[0] = KK_PLUGIN_BASE_ODB2_DATA;
            NewPC.PinName[1] = KK_PLUGIN_BASE_ODB2_COMMAND;

            Ret[FEATURE_MOBILEANDROID].Connections.add(NewPC);
            //ODB2->External
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "ODB2 to ExtConnector";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_ODB2;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_ODB2_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
            NewPC.PinName = new String[2];
            NewPC.PinName[0] = KK_PLUGIN_BASE_ODB2_DATA;
            NewPC.PinName[1] = KK_PLUGIN_BASE_ODB2_COMMAND;
            Ret[FEATURE_MOBILEANDROID].Connections.add(NewPC);
        }
        if (profile.contains(kk_DefConfProfileTypes.PRF_MEDIACENTER)) {
            // Mediacenter
            //Controls->Mediacenter
            Ret[FEATURE_MEDIACENTER] = new FeatureConfiguration();
            Ret[FEATURE_MEDIACENTER].FeatureName = "Media Center";
            Ret[FEATURE_MEDIACENTER].FeatureUUID = KK_BASE_FEATURES_MEDIAPLAYER_UID;
            Ret[FEATURE_MEDIACENTER].IsSystemFeature = false;
            Ret[FEATURE_MEDIACENTER].ShowInSystemMenu = true;
            Ret[FEATURE_MEDIACENTER].Connections = new ArrayList<>();
            Ret[FEATURE_MEDIACENTER].DefaultUIContext = KK_BASE_UICONTEXT_DEFAULT;

            NewPC = new PluginConnection();
            NewPC.ConnectionName = "Controls to Mediacenter";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_HID;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_HID_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_MEDIACENTER;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_MEDIACENTER_UUID;
            NewPC.PinName = new String[1];
            NewPC.PinName[0] = KK_PLUGIN_BASE_CONTROL_DATA;
            Ret[FEATURE_MEDIACENTER].Connections.add(NewPC);
            //MediaCenter->Leddisplay
            NewPC = new PluginConnection();
            NewPC.ConnectionName = "Mediacenter to Leddisplay";
            NewPC.SourcePluginName = KK_PLUGIN_BASE_PLUGIN_MEDIACENTER;
            NewPC.SourcePluginUID = KK_PLUGIN_BASE_PLUGIN_MEDIACENTER_UUID;
            NewPC.TargetPluginName = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
            NewPC.TargetPluginUID = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
            NewPC.PinName = new String[2];
            NewPC.PinName[0] = KK_PLUGIN_BASE_LED_COMMAND;
            NewPC.PinName[1] = KK_PLUGIN_BASE_LED_DATA;
            Ret[FEATURE_MEDIACENTER].Connections.add(NewPC);
        }
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
