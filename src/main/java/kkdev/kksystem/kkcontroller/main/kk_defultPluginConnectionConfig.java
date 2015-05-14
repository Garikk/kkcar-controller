/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main;

import kkdev.kksystem.base.classes.PluginConnectionsConfig;
import kkdev.kksystem.base.classes.PluginConnection;
import kkdev.kksystem.base.constants.PluginConsts;

/**
 *
 * @author blinov_is
 */
public final class kk_defultPluginConnectionConfig {
    public static PluginConnectionsConfig GetDefaultConnectionsConfig()
    {
        PluginConnectionsConfig Ret;
        
        Ret=new PluginConnectionsConfig();
        Ret.ConfigName="Default ODB Viewer configuration";
        Ret.ConfigUUID="0fcac003-638a-4927-9bee-9f82b677f6e1";
        //
        Ret.SystemDisplay_UID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        //
        Ret.Connections=new PluginConnection[4];
        //
        // ODB->DataProcessor
        Ret.Connections[0]=new PluginConnection();
        Ret.Connections[0].ConnectionName="ODB to Data Display processor";
        Ret.Connections[0].SourcePluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_ODB2;
        Ret.Connections[0].SourcePluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_ODB2_UUID;
        Ret.Connections[0].TargetPluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret.Connections[0].TargetPluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret.Connections[0].PinName=new String[2];
        Ret.Connections[0].PinName[0]=PluginConsts.KK_PLUGIN_BASE_ODB2_DATA;
        Ret.Connections[0].PinName[1]=PluginConsts.KK_PLUGIN_BASE_ODB2_RAW;
        //DataProcessor->ODB
        Ret.Connections[1]=new PluginConnection();
        Ret.Connections[1].ConnectionName="Data Display processor to ODB";
        Ret.Connections[1].SourcePluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret.Connections[1].SourcePluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret.Connections[1].TargetPluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_ODB2;
        Ret.Connections[1].TargetPluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_ODB2_UUID;
        Ret.Connections[1].PinName=new String[1];
        Ret.Connections[1].PinName[0]=PluginConsts.KK_PLUGIN_BASE_ODB2_COMMAND;
        //DataProcessor->LED
        Ret.Connections[2]=new PluginConnection();
        Ret.Connections[2].ConnectionName="Data Display processor to LED";
        Ret.Connections[2].SourcePluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret.Connections[2].SourcePluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret.Connections[2].TargetPluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
        Ret.Connections[2].TargetPluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        Ret.Connections[2].PinName=new String[2];
        Ret.Connections[2].PinName[0]=PluginConsts.KK_PLUGIN_BASE_LED_COMMAND;
        Ret.Connections[2].PinName[1]=PluginConsts.KK_PLUGIN_BASE_LED_DATA;
         //LED->Data Processor
        Ret.Connections[3]=new PluginConnection();
        Ret.Connections[3].ConnectionName="LED to DataProcessor";
        Ret.Connections[3].SourcePluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY;
        Ret.Connections[3].SourcePluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        Ret.Connections[3].TargetPluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY;
        Ret.Connections[3].TargetPluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_DATADISPLAY_UUID;
        Ret.Connections[3].PinName=new String[1];
        Ret.Connections[3].PinName[0]=PluginConsts.KK_PLUGIN_BASE_LED_DATA;
        return Ret;
        
           
    }
}
