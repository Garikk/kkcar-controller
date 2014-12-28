/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.pluginmanager;

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
        
        //
        Ret.Connections=new PluginConnection[3];
        //
        Ret.Connections[0].ConnectionName="ODB to Text Logger";
        Ret.Connections[0].TargetPluginName=PluginConsts.KK_PLUGIN_BASE_PLUGIN_TEXTLOG;
        Ret.Connections[0].TargetPluginUID=PluginConsts.KK_PLUGIN_BASE_PLUGIN_TEXTLOG_UUID;
        
        return Ret;
    
    
    }
}
