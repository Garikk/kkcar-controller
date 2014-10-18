
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
public class kk_defaultconfig {
    public KKSystemConfig GetDefaultSystemConfig()
    {
        KKSystemConfig Ret=new KKSystemConfig();
        
        
        
    
        return Ret;
    }
    
    private PluginInfo GetODBPlugin()
    {
        PluginInfo Ret=new PluginInfo();
            Ret.PluginName="KKODB2Reader";
            Ret.PluginDescription="Basic ELM327 ODB2 Reader plugin";
            Ret.PluginType = PluginConsts.KK_PLUGIN_TYPE.PLUGIN_INPUT;
            
            Ret.PluginVersion=1;
            return Ret;
        
    }
     private PluginInfo GetTextLogPlugin()
    {
        PluginInfo Ret=new PluginInfo();
            Ret.PluginName="KKODB2Reader";
            Ret.PluginDescription="Basic ELM327 ODB2 Reader plugin";
            Ret.PluginType = PluginConsts.KK_PLUGIN_TYPE.PLUGIN_INPUT;
            
            Ret.PluginVersion=1;
            return Ret;
        
    }
     private PluginInfo GetLEDDisplayPlugin()
    {
        PluginInfo Ret=new PluginInfo();
            Ret.PluginName="KKODB2Reader";
            Ret.PluginDescription="Basic ELM327 ODB2 Reader plugin";
            Ret.PluginType = PluginConsts.KK_PLUGIN_TYPE.PLUGIN_INPUT;
            
            Ret.PluginVersion=1;
            return Ret;
        
    }
}
