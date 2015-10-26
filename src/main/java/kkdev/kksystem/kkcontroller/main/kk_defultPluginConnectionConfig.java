/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main;

import kkdev.kksystem.base.classes.display.tools.menumaker.MKMenuItem;
import kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker;
import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.classes.plugins.PluginConnection;
import kkdev.kksystem.base.constants.PluginConsts;
import kkdev.kksystem.base.constants.SystemConsts;
import kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu;

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
        Ret[FEATURE_VERSION].FeatureUUID=SystemConsts.KK_BASE_FEATURES_SYSTEM_VERSIONINFO_UID;
        Ret[FEATURE_VERSION].IsSystemFeature=true;

        Ret[FEATURE_SETTINGS]=new FeatureConfiguration();
        Ret[FEATURE_SETTINGS].FeatureName="Settings";
        Ret[FEATURE_SETTINGS].FeatureUUID=SystemConsts.KK_BASE_FEATURES_SYSTEM_SETTINGS_UID;
        Ret[FEATURE_SETTINGS].IsSystemFeature=true;
        
        //
        Ret[FEATURE_ODB]=new FeatureConfiguration();
        Ret[FEATURE_ODB].FeatureName="ODB Diag Displ";
        Ret[FEATURE_ODB].FeatureUUID=SystemConsts.KK_BASE_FEATURES_ODB_DIAG_UID;
        Ret[FEATURE_ODB].IsSystemFeature=false;
        Ret[FEATURE_ODB].Connections=new PluginConnection[8];
        //
        // ODB->DataProcessor
        Ret[FEATURE_ODB].Connections[0]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[0].ConnectionName="ODB to Data Display processor";
        Ret[FEATURE_ODB].Connections[0].SourcePluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_ODB2;
        Ret[FEATURE_ODB].Connections[0].SourcePluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_ODB2_UUID;
        Ret[FEATURE_ODB].Connections[0].TargetPluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[0].TargetPluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[0].PinName=new String[2];
        Ret[FEATURE_ODB].Connections[0].PinName[0]=PluginConsts.KK_PLUGIN_BASE_ODB2_DATA;
        //DataProcessor->ODB
        Ret[FEATURE_ODB].Connections[1]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[1].ConnectionName="Data Display processor to ODB";
        Ret[FEATURE_ODB].Connections[1].SourcePluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[1].SourcePluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[1].TargetPluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_ODB2;
        Ret[FEATURE_ODB].Connections[1].TargetPluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_ODB2_UUID;
        Ret[FEATURE_ODB].Connections[1].PinName=new String[1];
        Ret[FEATURE_ODB].Connections[1].PinName[0]=PluginConsts.KK_PLUGIN_BASE_ODB2_COMMAND;
        //DataProcessor->LED
        Ret[FEATURE_ODB].Connections[2]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[2].ConnectionName="Data Display processor to LED";
        Ret[FEATURE_ODB].Connections[2].SourcePluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[2].SourcePluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[2].TargetPluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
        Ret[FEATURE_ODB].Connections[2].TargetPluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[2].PinName=new String[2];
        Ret[FEATURE_ODB].Connections[2].PinName[0]=PluginConsts.KK_PLUGIN_BASE_LED_COMMAND;
        Ret[FEATURE_ODB].Connections[2].PinName[1]=PluginConsts.KK_PLUGIN_BASE_LED_DATA;
         //LED->Data Processor
        Ret[FEATURE_ODB].Connections[3]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[3].ConnectionName="LED to DataProcessor";
        Ret[FEATURE_ODB].Connections[3].SourcePluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
        Ret[FEATURE_ODB].Connections[3].SourcePluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[3].TargetPluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[3].TargetPluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[3].PinName=new String[1];
        Ret[FEATURE_ODB].Connections[3].PinName[0]=PluginConsts.KK_PLUGIN_BASE_LED_DATA;
        //
        //Controls->Data Processor
        Ret[FEATURE_ODB].Connections[4]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[4].ConnectionName="Controls to DataProcessor";
        Ret[FEATURE_ODB].Connections[4].SourcePluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_HID;
        Ret[FEATURE_ODB].Connections[4].SourcePluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_HID_UUID;
        Ret[FEATURE_ODB].Connections[4].TargetPluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[4].TargetPluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[4].PinName=new String[1];
        Ret[FEATURE_ODB].Connections[4].PinName[0]=PluginConsts.KK_PLUGIN_BASE_CONTROL_DATA;
       
       //Controls->External
        Ret[FEATURE_ODB].Connections[5]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[5].ConnectionName="Controls to ExtConnector";
        Ret[FEATURE_ODB].Connections[5].SourcePluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_HID;
        Ret[FEATURE_ODB].Connections[5].SourcePluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_HID_UUID;
        Ret[FEATURE_ODB].Connections[5].TargetPluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
        Ret[FEATURE_ODB].Connections[5].TargetPluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
        Ret[FEATURE_ODB].Connections[5].PinName=new String[1];
        Ret[FEATURE_ODB].Connections[5].PinName[0]=PluginConsts.KK_PLUGIN_BASE_CONTROL_DATA;
        //DataProcessor->External
        Ret[FEATURE_ODB].Connections[6]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[6].ConnectionName="DataProcessor to ExtConnector";
        Ret[FEATURE_ODB].Connections[6].SourcePluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[6].SourcePluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[6].TargetPluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
        Ret[FEATURE_ODB].Connections[6].TargetPluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
        Ret[FEATURE_ODB].Connections[6].PinName=new String[1];
        Ret[FEATURE_ODB].Connections[6].PinName[0]=PluginConsts.KK_PLUGIN_BASE_LED_DATA;
        
          //DataProcessor->External
        Ret[FEATURE_ODB].Connections[7]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[7].ConnectionName="ExtConnector to DataProcessor";
        Ret[FEATURE_ODB].Connections[7].SourcePluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR;
        Ret[FEATURE_ODB].Connections[7].SourcePluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_UUID;
        Ret[FEATURE_ODB].Connections[7].TargetPluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[7].TargetPluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[7].PinName=new String[1];
        Ret[FEATURE_ODB].Connections[7].PinName[0]=PluginConsts.KK_PLUGIN_BASE_CONTROL_DATA;
        
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
        Ret[0].ItemCommand=MenuMaker.KK_MENUMAKER_SPECIALCMD_SUBMENU;
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
        Ret[0].SubItems[3].ItemCommand=MenuMaker.KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[0].SubItems[3].SubItems=new MKMenuItem[2];
        Ret[0].SubItems[3].SubItems[0]=new MKMenuItem();
        Ret[0].SubItems[3].SubItems[0].DisplayName="Test Prm21";
        Ret[0].SubItems[3].SubItems[0].ItemCommand="";
        Ret[0].SubItems[3].SubItems[1]=new MKMenuItem();
        Ret[0].SubItems[3].SubItems[1].DisplayName="Test Prm22";
        Ret[0].SubItems[3].SubItems[1].ItemCommand="";
        Ret[1]=new MKMenuItem();
        Ret[1].DisplayName="Board tools";
        Ret[1].ItemCommand=MenuMaker.KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[1].SubItems=new MKMenuItem[3];
        Ret[1].SubItems[0]=new MKMenuItem();
        Ret[1].SubItems[0].DisplayName="System Info";
        Ret[1].SubItems[0].ItemCommand=SystemMenu.MNU_CMD_SYSMENU_PFX+" "+SystemMenu.MNU_CMD_SYSMENU_PFX_BRDTOOLS + " " + SystemMenu.MNU_CMD_BRD_TOOLS_BOARDINFO;
        Ret[1].SubItems[1]=new MKMenuItem();
        Ret[1].SubItems[1].DisplayName="Reboot";
        Ret[1].SubItems[1].ItemCommand=SystemMenu.MNU_CMD_SYSMENU_PFX+" "+SystemMenu.MNU_CMD_SYSMENU_PFX_BRDTOOLS + " " + SystemMenu.MNU_CMD_BRD_TOOLS_REBOOT;
        Ret[1].SubItems[2]=new MKMenuItem();
        Ret[1].SubItems[2].DisplayName="Power Off";
        Ret[1].SubItems[2].ItemCommand=SystemMenu.MNU_CMD_SYSMENU_PFX+" "+SystemMenu.MNU_CMD_SYSMENU_PFX_BRDTOOLS + " " + SystemMenu.MNU_CMD_BRD_TOOLS_POWEROFF;
        Ret[2]=new MKMenuItem();
        Ret[2].DisplayName="KK Info";
        Ret[2].ItemCommand=MenuMaker.KK_MENUMAKER_SPECIALCMD_SUBMENU;
        Ret[2].SubItems=new MKMenuItem[2];
        Ret[2].SubItems[0]=new MKMenuItem();
        Ret[2].SubItems[0].DisplayName="Plugins";
        Ret[2].SubItems[0].ItemCommand=SystemMenu.MNU_CMD_SYSMENU_PFX+" "+SystemMenu.MNU_CMD_SYSMENU_PFX_INFO + " " + SystemMenu.MNU_CMD_BRD_INFO_PLUGINS;
        Ret[2].SubItems[1]=new MKMenuItem();
        Ret[2].SubItems[1].DisplayName="Version";
        Ret[2].SubItems[1].ItemCommand=SystemMenu.MNU_CMD_SYSMENU_PFX+" "+SystemMenu.MNU_CMD_SYSMENU_PFX_INFO + " " + SystemMenu.MNU_CMD_BRD_INFO_VERSION;

        
                
        return Ret;
    }
    
    
    
}
