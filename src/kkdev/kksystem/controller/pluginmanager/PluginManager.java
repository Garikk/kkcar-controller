/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.pluginmanager;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.*;
import com.thoughtworks.xstream.io.xml.*;
import kkdev.kksystem.controller.main.KKSystemConfig;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import kkdev.kksystem.base.classes.PluginInfo;
import kkdev.kksystem.base.classes.PluginPin;
import kkdev.kksystem.base.constants.SystemConsts;
import kkdev.kksystem.base.interfaces.IKKConnector;
import kkdev.kksystem.base.interfaces.IPluginKKConnector;

/**
 *
 * @author blinov_is
 */
public class PluginManager  {

    KKSystemConfig MainConfiguraion;
    IPluginKKConnector[] ActivePlugins;
    PinConnections PluginConnectons;

  
    public void InitPlugins(PluginInfo[] PluginsToLoad) {
        ActivePlugins = ConnectPlugins(PluginsToLoad);
        System.out.println("Init plugin connections:");
        PluginConnectons=new PinConnections(ActivePlugins);
        InitPlugins();
        System.out.println("Test system:");
   }
    //
    private void InitPlugins()
    {
        for (IPluginKKConnector PL : ActivePlugins)
        {
            PL.PluginInit(PluginConnectons);
        }
    }
    private void SelfTest()
    {
        
    }
    
    private boolean CheckPlugin(PluginInfo Plugins[], PluginInfo CheckPlugin) {
        for (PluginInfo Pl : Plugins) {
           if (Pl.PluginName.equals(CheckPlugin.PluginName))
                return true;
        }
        return false;
    }

    
    private PluginInfo GetJarPluginInfo(File FileToCheck) {
        PluginInfo Ret;

        JarFile jarFile = null;
        JarEntry entry = null;
        InputStream IS = null;
        try {
            jarFile = new JarFile(FileToCheck);
            //
            entry = jarFile.getJarEntry("kkinfo.xml");
            if (entry == null) {
                System.out.println("Plugin info read error (kkinfo.xml not found)");
                return null;
            }
            //
            IS = jarFile.getInputStream(entry);
        } catch (IOException ex) {
            System.out.println("Plugin info read error (jar reading)");
            return null;
        }

        if ( IS == null) {
            return null;
        }

        try {
            XStream xstream = new XStream(new DomDriver());
            Ret = (PluginInfo) xstream.fromXML(IS);
        } catch (StreamException Ex) {
             return null;
        }
        return Ret;
    }

    private IPluginKKConnector[] ConnectPlugins(PluginInfo Plugins[]) {
        System.out.println("Required plugins count: " + Plugins.length);
        //
        int Counter = 0;
        IPluginKKConnector[] Ret = new IPluginKKConnector[Plugins.length];
        //
        //
        File folder = new File(SystemConsts.KK_BASE_PLUGINPATH);
        File[] PluginFiles = folder.listFiles();
        //
        System.out.println("Plugin files count: " + PluginFiles.length);
        //

        for (File loadFile : PluginFiles) {
            boolean Err = false;
            PluginInfo PluginLoad;
            //Check load only Jar file
            if (!loadFile.getName().endsWith(".jar") | loadFile.isDirectory()) {
                continue;
            }
            System.out.println("--------------------");
            System.out.println("File: " + loadFile.getName());
            
            PluginInfo Check = GetJarPluginInfo(loadFile);
            if (Check == null) {
                System.out.println("Plugin info: not found");
                System.out.println("Skip");
                continue;
            }
            else
            {
                System.out.println("Plugin info: "+Check.PluginName);
            }
            
            if (CheckPlugin(Plugins,Check)==false)
                {
                    System.out.println("Config: not in config");
                    System.out.println("Skip");
                    continue;
                }
                
            System.out.println("Config: ok");
            //
            try {
                URLClassLoader CLoader = new URLClassLoader(new URL[]{loadFile.toURI().toURL()});
                //
                Ret[Counter] = (IPluginKKConnector) CLoader.loadClass(Check.ConnectorClass).newInstance();
                //
                 System.out.println("Load: ok");
                //
                Counter++;
            } catch (MalformedURLException | InstantiationException | ClassNotFoundException | IllegalAccessException e) {
                System.out.println("Load Error: " + loadFile.getName() + " " + e.toString());
            }
           //

        }
        //
        for (PluginInfo Pl : Plugins) {
            if (!Pl.Enabled) {
                System.out.println("Disabled plugin: " + Pl.PluginName);
            }
        }
        //
        return Ret;
    }

    
}