/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main;

import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.classes.plugins.PluginConnection;
import kkdev.kksystem.base.constants.PluginConsts;
import kkdev.kksystem.base.constants.SystemConsts;

/**
 *
 * @author blinov_is
 */
public final class kk_defultPluginConnectionConfig {
    static int FEATURE_ODB=0;
    static int FEATURE_VERSION=1;
    static int FEATURE_SETTINGS=2;
    static int FEATURE_REBOOT=3;
    static int FEATURE_POWEROFF=4;
    
    public static FeatureConfiguration[] GetDefaultFeature()
    {
        FeatureConfiguration[] Ret=new FeatureConfiguration[5];

        Ret[FEATURE_REBOOT]=new FeatureConfiguration();
        Ret[FEATURE_REBOOT].FeatureName="Reboot";
        Ret[FEATURE_REBOOT].FeatureUUID=SystemConsts.KK_BASE_FEATURES_SYSTEM_REBOOT_UID;
        Ret[FEATURE_REBOOT].IsSystemFeature=true;
        
        Ret[FEATURE_POWEROFF]=new FeatureConfiguration();
        Ret[FEATURE_POWEROFF].FeatureName="Power off";
        Ret[FEATURE_POWEROFF].FeatureUUID=SystemConsts.KK_BASE_FEATURES_SYSTEM_POWEROFF_UID;
        Ret[FEATURE_POWEROFF].IsSystemFeature=true;

        
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
        Ret[FEATURE_ODB].FeatureName="ODB Diag Monitor";
        Ret[FEATURE_ODB].FeatureUUID=SystemConsts.KK_BASE_FEATURES_ODB_DIAG_UID;
        Ret[FEATURE_ODB].IsSystemFeature=false;
        Ret[FEATURE_ODB].Connections=new PluginConnection[4];
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
        Ret[FEATURE_ODB].Connections[0].PinName[1]=PluginConsts.KK_PLUGIN_BASE_ODB2_RAW;
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
        Ret[FEATURE_ODB].Connections[3]=new PluginConnection();
        Ret[FEATURE_ODB].Connections[3].ConnectionName="LED to DataProcessor";
        Ret[FEATURE_ODB].Connections[3].SourcePluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
        Ret[FEATURE_ODB].Connections[3].SourcePluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[3].TargetPluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret[FEATURE_ODB].Connections[3].TargetPluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret[FEATURE_ODB].Connections[3].PinName=new String[1];
        Ret[FEATURE_ODB].Connections[3].PinName[0]=PluginConsts.KK_PLUGIN_BASE_LED_DATA;
        return Ret;
        
           
    }
}
