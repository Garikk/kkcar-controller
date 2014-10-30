package kkdev.kksystem.controller.main;


import kkdev.kksystem.base.classes.PluginInfo;
import kkdev.kksystem.base.constants.PluginConsts;
import kkdev.kksystem.controller.pluginmanager.KKSystemConfig;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sayma_000
 * 
 * by default we found 3
 * 1 - ODB connector
 * 2 - TextLogger
 * 3 - LED Display
 */
public final class kk_defaultconfig {
    public static KKSystemConfig GetDefaultSystemConfig()
    {
        KKSystemConfig Ret=new KKSystemConfig();
        Ret.ConfPlugins=new PluginInfo[3];
        Ret.ConfPlugins[0]=GetODBPlugin();
        Ret.ConfPlugins[1]=GetTextLogPlugin();
        Ret.ConfPlugins[2]=GetLEDDisplayPlugin();
        return Ret;
    }
    
    private static PluginInfo GetODBPlugin()
    {
        PluginInfo Ret=new PluginInfo();
            Ret.PluginName="KKODB2Reader";
            Ret.PluginDescription="Basic ELM327 ODB2 Reader plugin";
            Ret.PluginType = PluginConsts.KK_PLUGIN_TYPE.PLUGIN_INPUT;
            Ret.PluginJarName="kksystem.plugin.odb2.jar";
            Ret.ConnectorClass="kkdev.kksystem.plugins.odb2.elm372.KKPlugin";
            Ret.PluginVersion=1;
            return Ret;
        
    }
     private static PluginInfo GetTextLogPlugin()
    {
        PluginInfo Ret=new PluginInfo();
            Ret.PluginName="KKTextLogger";
            Ret.PluginDescription="Basic Text logger plugin";
            Ret.PluginType = PluginConsts.KK_PLUGIN_TYPE.PLUGIN_OUTPUT;
            Ret.PluginJarName="kksystem.plugin.textlog.jar";
            Ret.ConnectorClass="kkdev.kksystem.plugins.textlog.KKPlugin";
            Ret.PluginVersion=1;
            return Ret;
        
    }
     private static PluginInfo GetLEDDisplayPlugin()
    {
        PluginInfo Ret=new PluginInfo();
            Ret.PluginName="KKLEDDisplay";
            Ret.PluginDescription="Basic LED Display plugin";
            Ret.PluginType = PluginConsts.KK_PLUGIN_TYPE.PLUGIN_OUTPUT;
            Ret.PluginJarName="kksystem.plugin.leddisplay.jar";
            Ret.ConnectorClass="kkdev.kksystem.plugins.leddisplay.KKPlugin";
            Ret.PluginVersion=1;
            return Ret;
        
    }
}
