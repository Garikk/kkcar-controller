/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.pluginmanager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import kkdev.kksystem.base.classes.PluginInfo;
import kkdev.kksystem.base.constants.SystemConsts;
import kkdev.kksystem.base.interfaces.IPluginKKConnector;

/**
 *
 * @author blinov_is
 */
public class PluginManager {

    KKSystemConfig MainConfiguraion;
    
    public PluginManager()
    {
    
    }
    //
    private PluginInfo CheckJarName(PluginInfo Plugins[], String CheckName) {
        for (PluginInfo Pl : Plugins) {
            if (Pl.PluginJarName.toLowerCase() == null ? CheckName.toLowerCase() == null : Pl.PluginJarName.toLowerCase().equals(CheckName.toLowerCase())) {
                return Pl;
            }
        }
        return null;

    }
    public IPluginKKConnector[] LoadPlugins(PluginInfo Plugins[]) {
        System.out.println("Loading plugins.");
        System.out.println("Required plugins count: " + Plugins.length);
        //
        int Counter = 0;
        IPluginKKConnector[] Ret = new IPluginKKConnector[Plugins.length];
        //
        //
        File folder = new File(SystemConsts.KK_BASE_PLUGINPATH);
        File[] PluginFiles = folder.listFiles();

        for (File loadFile : PluginFiles) {
            boolean Err=false;
            PluginInfo PluginLoad;
            //Check load only Jar file
            if (!loadFile.getName().endsWith(".jar") | loadFile.isDirectory()) {
                continue;
            }
            //Check plugin in config
            PluginLoad = CheckJarName(Plugins, loadFile.getName());
            if (PluginLoad == null) {
                System.out.println("Skip: " + loadFile.getName());
                continue;
            }

            //
            try {
                URLClassLoader CLoader = new URLClassLoader(new URL[]{loadFile.toURI().toURL()});
                //
                Ret[Counter] = (IPluginKKConnector) CLoader.loadClass(PluginLoad.ConnectorClass).newInstance();
                //
                System.out.println("Loaded: " + Ret[Counter].GetPluginInfo().PluginName);
                //
                Counter++;
            } catch (MalformedURLException | InstantiationException | ClassNotFoundException | IllegalAccessException e) {
                System.out.println("Load Error: " + loadFile.getName() + " " + e.getMessage());
            }
            //
            
            //
        }

        return null;
    }

}
