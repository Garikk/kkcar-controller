/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main;

import kkdev.kksystem.base.classes.display.tools.menumaker.MKMenuItem;
import kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker;
import static kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker.KK_MENUMAKER_SPECIALCMD_SUBMENU;
import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.classes.plugins.PluginConnection;
import kkdev.kksystem.base.constants.PluginConsts;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_CONTROL_DATA;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_LED_COMMAND;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_LED_DATA;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_ODB2_COMMAND;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_ODB2_DATA;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_BLUETOOTH;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_UUID;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_HID;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_HID_UUID;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_ODB2;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_ODB2_UUID;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_ODB_DIAG_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_SETTINGS_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_VERSIONINFO_UID;
import kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.MNU_CMD_BRD_INFO_PLUGINS;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.MNU_CMD_BRD_INFO_VERSION;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.MNU_CMD_BRD_TOOLS_BOARDINFO;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.MNU_CMD_BRD_TOOLS_POWEROFF;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.MNU_CMD_BRD_TOOLS_REBOOT;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.MNU_CMD_SYSMENU_PFX;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.MNU_CMD_SYSMENU_PFX_BRDTOOLS;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.MNU_CMD_SYSMENU_PFX_INFO;

/**
 *
 * @author blinov_is
 */
public final class kk_defultPluginConnectionConfig {
    static int FEATURE_ODB=0;
    static int FEATURE_VERSION=1;
    static int FEATURE_SETTINGS=2;
    
    public static FeatureConfiguration[] GetDefaultFeature()
    {
        FeatureConfiguration[] Ret=new FeatureConfiguration[3];

        Ret[FEATURE_VERSION]=new FeatureConfiguration();
        Ret[FEATURE_VERSION].FeatureName="Version info";
        Ret[FEATURE_VERSION].FeatureUUID=KK_BASE_FEATURES_SYSTEM_VERSIONINFO_UID;
        Ret[FEATURE_VERSION].IsSystemFeature=true;

        Ret[FEATURE_SETTINGS]=new FeatureConfiguration();
        Ret[FEATURE_SETTINGS].FeatureName="Settings";
        Ret[FEATURE_SETTINGS].FeatureUUID=KK_BASE_FEATURES_SYSTEM_SETTINGS_UID;
        Ret[FEATURE_SETTINGS].IsSystemFeature=true;
        
        //
        Ret[FEATURE_ODB]=new FeatureConfiguration();
        Ret[FEATURE_ODB].FeatureName="ODB Diag Displ";
        Ret[FEATURE_ODB].FeatureUUID=KK_BASE_FEATURES_ODB_DIAG_UID;
        Ret[FEATURE_ODB].IsSystemFeature=false;
        Ret[FEATURE_ODB].Connections=new PluginConnection[10];
        //
        // ODB->DataProcessor
        Ret[FEATURE_ODB].Connections[0]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[0].ConnectionName="ODB to Data Display processor";
        Ret[FEATURE_ODB].Connections[0].SourcePluginName=KK_PLUGIN_BASE_PLUGIN_ODB2;
        Ret[FEATURE_ODB].Connections[0].SourcePluginUID=KK_PLUGIN_BASE_PLUGIN_ODB2_UUID;
        Ret[FEATURE_ODB].Connections[0].TargetPluginName=KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[0].TargetPluginUID=KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[0].PinName=new String[2];
        Ret[FEATURE_ODB].Connections[0].PinName[0]=KK_PLUGIN_BASE_ODB2_DATA;
        Ret[FEATURE_ODB].Connections[0].PinName[1]=KK_PLUGIN_BASE_ODB2_COMMAND;
        //DataProcessor->ODB
        Ret[FEATURE_ODB].Connections[1]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[1].ConnectionName="Data Display processor to ODB";
        Ret[FEATURE_ODB].Connections[1].SourcePluginName=KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[1].SourcePluginUID=KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[1].TargetPluginName=KK_PLUGIN_BASE_PLUGIN_ODB2;
        Ret[FEATURE_ODB].Connections[1].TargetPluginUID=KK_PLUGIN_BASE_PLUGIN_ODB2_UUID;
        Ret[FEATURE_ODB].Connections[1].PinName=new String[1];
        Ret[FEATURE_ODB].Connections[1].PinName[0]=KK_PLUGIN_BASE_ODB2_COMMAND;
        //DataProcessor->LED
        Ret[FEATURE_ODB].Connections[2]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[2].ConnectionName="Data Display processor to LED";
        Ret[FEATURE_ODB].Connections[2].SourcePluginName=KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[2].SourcePluginUID=KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[2].TargetPluginName=KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
        Ret[FEATURE_ODB].Connections[2].TargetPluginUID=KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[2].PinName=new String[2];
        Ret[FEATURE_ODB].Connections[2].PinName[0]=KK_PLUGIN_BASE_LED_COMMAND;
        Ret[FEATURE_ODB].Connections[2].PinName[1]=KK_PLUGIN_BASE_LED_DATA;
         //LED->Data Processor
        Ret[FEATURE_ODB].Connections[3]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[3].ConnectionName="LED to DataProcessor";
        Ret[FEATURE_ODB].Connections[3].SourcePluginName=KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
        Ret[FEATURE_ODB].Connections[3].SourcePluginUID=KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[3].TargetPluginName=KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[3].TargetPluginUID=KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[3].PinName=new String[1];
        Ret[FEATURE_ODB].Connections[3].PinName[0]=KK_PLUGIN_BASE_LED_DATA;
        //
        //Controls->Data Processor
        Ret[FEATURE_ODB].Connections[4]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[4].ConnectionName="Controls to DataProcessor";
        Ret[FEATURE_ODB].Connections[4].SourcePluginName=KK_PLUGIN_BASE_PLUGIN_HID;
        Ret[FEATURE_ODB].Connections[4].SourcePluginUID=KK_PLUGIN_BASE_PLUGIN_HID_UUID;
        Ret[FEATURE_ODB].Connections[4].TargetPluginName=KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[4].TargetPluginUID=KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[4].PinName=new String[1];
        Ret[FEATURE_ODB].Connections[4].PinName[0]=KK_PLUGIN_BASE_CONTROL_DATA;
       
       //Controls->External
        Ret[FEATURE_ODB].Connections[5]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[5].ConnectionName="Controls to ExtConnector";
        Ret[FEATURE_ODB].Connections[5].SourcePluginName=KK_PLUGIN_BASE_PLUGIN_HID;
        Ret[FEATURE_ODB].Connections[5].SourcePluginUID=KK_PLUGIN_BASE_PLUGIN_HID_UUID;
        Ret[FEATURE_ODB].Connections[5].TargetPluginName=KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
        Ret[FEATURE_ODB].Connections[5].TargetPluginUID=KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
        Ret[FEATURE_ODB].Connections[5].PinName=new String[1];
        Ret[FEATURE_ODB].Connections[5].PinName[0]=KK_PLUGIN_BASE_CONTROL_DATA;
        //DataProcessor->External
        Ret[FEATURE_ODB].Connections[6]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[6].ConnectionName="DataProcessor to ExtConnector";
        Ret[FEATURE_ODB].Connections[6].SourcePluginName=KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[6].SourcePluginUID=KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[6].TargetPluginName=KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
        Ret[FEATURE_ODB].Connections[6].TargetPluginUID=KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
        Ret[FEATURE_ODB].Connections[6].PinName=new String[1];
        Ret[FEATURE_ODB].Connections[6].PinName[0]=KK_PLUGIN_BASE_LED_DATA;
        
          //DataProcessor->External
        Ret[FEATURE_ODB].Connections[7]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[7].ConnectionName="ExtConnector to DataProcessor";
        Ret[FEATURE_ODB].Connections[7].SourcePluginName=KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
        Ret[FEATURE_ODB].Connections[7].SourcePluginUID=KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
        Ret[FEATURE_ODB].Connections[7].TargetPluginName=KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[7].TargetPluginUID=KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[7].PinName=new String[1];
        Ret[FEATURE_ODB].Connections[7].PinName[0]=KK_PLUGIN_BASE_CONTROL_DATA;
        
        //External->Bluetooth
        Ret[FEATURE_ODB].Connections[8]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[8].ConnectionName="ExtConnector to Bluetooth";
        Ret[FEATURE_ODB].Connections[8].SourcePluginName=KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
        Ret[FEATURE_ODB].Connections[8].SourcePluginUID=KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
        Ret[FEATURE_ODB].Connections[8].TargetPluginName=KK_PLUGIN_BASE_PLUGIN_BLUETOOTH;
        Ret[FEATURE_ODB].Connections[8].TargetPluginUID=KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_UUID;
        Ret[FEATURE_ODB].Connections[8].PinName=new String[1];
        Ret[FEATURE_ODB].Connections[8].PinName[0]=KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA;
        //Bluetooth->External
        Ret[FEATURE_ODB].Connections[9]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[9].ConnectionName="Bluetooth to ExtConnector";
        Ret[FEATURE_ODB].Connections[9].SourcePluginName=KK_PLUGIN_BASE_PLUGIN_BLUETOOTH;
        Ret[FEATURE_ODB].Connections[9].SourcePluginUID=KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_UUID;
        Ret[FEATURE_ODB].Connections[9].TargetPluginName=KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
        Ret[FEATURE_ODB].Connections[9].TargetPluginUID=KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
        Ret[FEATURE_ODB].Connections[9].PinName=new String[1];
        Ret[FEATURE_ODB].Connections[9].PinName[0]=KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA;
        return Ret;
        
           
    }
    
    public static MKMenuItem[] GetDefaultSystemMenuItems()
    {
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
        Ret=new MKMenuItem[3]; // 3 main items (see above)
        
        Ret[0]=new MKMenuItem();
        Ret[0].DisplayName="Settings";
        Ret[0].ItemCommand=KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[0].SubItems=new MKMenuItem[4];
        Ret[0].SubItems[0]=new MKMenuItem();
        Ret[0].SubItems[0].DisplayName="Test Prm11";
        Ret[0].SubItems[0].ItemCommand="";
        Ret[0].SubItems[1]=new MKMenuItem();
        Ret[0].SubItems[1].DisplayName="Test Prm12";
        Ret[0].SubItems[1].ItemCommand="";
        Ret[0].SubItems[2]=new MKMenuItem();
        Ret[0].SubItems[2].DisplayName="Test Prm13";
        Ret[0].SubItems[2].ItemCommand="";
        Ret[0].SubItems[3]=new MKMenuItem();
        Ret[0].SubItems[3].DisplayName="test submenu";
        Ret[0].SubItems[3].ItemCommand=KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[0].SubItems[3].SubItems=new MKMenuItem[2];
        Ret[0].SubItems[3].SubItems[0]=new MKMenuItem();
        Ret[0].SubItems[3].SubItems[0].DisplayName="Test Prm21";
        Ret[0].SubItems[3].SubItems[0].ItemCommand="";
        Ret[0].SubItems[3].SubItems[1]=new MKMenuItem();
        Ret[0].SubItems[3].SubItems[1].DisplayName="Test Prm22";
        Ret[0].SubItems[3].SubItems[1].ItemCommand="";
        Ret[1]=new MKMenuItem();
        Ret[1].DisplayName="Board tools";
        Ret[1].ItemCommand=KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[1].SubItems=new MKMenuItem[3];
        Ret[1].SubItems[0]=new MKMenuItem();
        Ret[1].SubItems[0].DisplayName="System Info";
        Ret[1].SubItems[0].ItemCommand=MNU_CMD_SYSMENU_PFX+" "+MNU_CMD_SYSMENU_PFX_BRDTOOLS + " " + MNU_CMD_BRD_TOOLS_BOARDINFO;
        Ret[1].SubItems[1]=new MKMenuItem();
        Ret[1].SubItems[1].DisplayName="Reboot";
        Ret[1].SubItems[1].ItemCommand=MNU_CMD_SYSMENU_PFX+" "+MNU_CMD_SYSMENU_PFX_BRDTOOLS + " " + MNU_CMD_BRD_TOOLS_REBOOT;
        Ret[1].SubItems[2]=new MKMenuItem();
        Ret[1].SubItems[2].DisplayName="Power Off";
        Ret[1].SubItems[2].ItemCommand=MNU_CMD_SYSMENU_PFX+" "+MNU_CMD_SYSMENU_PFX_BRDTOOLS + " " + MNU_CMD_BRD_TOOLS_POWEROFF;
        Ret[2]=new MKMenuItem();
        Ret[2].DisplayName="KK Info";
        Ret[2].ItemCommand=KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[2].SubItems=new MKMenuItem[2];
        Ret[2].SubItems[0]=new MKMenuItem();
        Ret[2].SubItems[0].DisplayName="Plugins";
        Ret[2].SubItems[0].ItemCommand=MNU_CMD_SYSMENU_PFX+" "+MNU_CMD_SYSMENU_PFX_INFO + " " + MNU_CMD_BRD_INFO_PLUGINS;
        Ret[2].SubItems[1]=new MKMenuItem();
        Ret[2].SubItems[1].DisplayName="Version";
        Ret[2].SubItems[1].ItemCommand=MNU_CMD_SYSMENU_PFX+" "+MNU_CMD_SYSMENU_PFX_INFO + " " + MNU_CMD_BRD_INFO_VERSION;

        
                
        return Ret;
    }
    
    
    
}
