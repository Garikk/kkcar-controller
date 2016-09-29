/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.utils;

import java.util.List;
import kkdev.kksystem.base.classes.plugins.PluginInfo;
import kkdev.kksystem.base.interfaces.IPluginUtils;
import kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader;

/**
 *
 * @author blinov_is
 */
public class PluginUtility implements IPluginUtils{

    @Override
    public List<PluginInfo> GetLoadedPlugins() {
          return PluginLoader.GetActivePluginsInfo();
    }
    
}
