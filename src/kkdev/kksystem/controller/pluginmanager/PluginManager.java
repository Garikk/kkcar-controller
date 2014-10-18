/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.pluginmanager;

import kkdev.kksystem.base.classes.PluginInfo;

/**
 *
 * @author blinov_is
 */
public class PluginManager {

    public void LoadPlugins(PluginInfo Plugins[])
    {
        System.out.println("Loading plugins.");
        System.out.println("Required plugins count: " + Plugins.length);
        //
        for (PluginInfo P : Plugins)
        {
        //
        System.out.println("Required plugins count: " + Plugins.length);
        }
    }
    
}
