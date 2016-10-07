/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kkdev.kksystem.base.classes.plugins.PluginConfiguration;
import kkdev.kksystem.base.classes.plugins.PluginInfo;
import kkdev.kksystem.base.interfaces.IPluginUtils;
import kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader;

/**
 *
 * @author blinov_is
 */
public class PluginUtility implements IPluginUtils{

    @Override
    public List<PluginInfo> getPluginsInfo() {
          return PluginLoader.getActivePluginsInfo();
    }

    @Override
    public Map<PluginInfo, PluginConfiguration> getPluginsParameters() {
        HashMap Ret=new HashMap<>();
        
        for (String PID :PluginLoader.getActivePluginUIDs())
        {
            PluginConfiguration PC=PluginLoader.getPluginByUUID(PID).getPluginSettings();
            if (PC!=null)
                Ret.put(PluginLoader.getPluginByUUID(PID).getPluginInfo(), PC);
        }
        return Ret;
    }
    
}
