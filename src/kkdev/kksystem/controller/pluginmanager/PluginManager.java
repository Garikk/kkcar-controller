/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.pluginmanager;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.*;
import com.thoughtworks.xstream.io.xml.*;
import java.io.BufferedReader;
import kkdev.kksystem.controller.main.KKSystemConfig;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import kkdev.kksystem.base.classes.PluginInfo;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_PLUGINS_CONNECTOR_FILE;
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
        if (ActivePlugins==null)
        {
            return;
        }
            
        System.out.println("Init plugin connections:");
        System.out.println("Load interconnect configuration:");
        
        PluginConnectons=new PinConnections(ActivePlugins);
        InitPlugins();
        System.out.println("Test system:");
   }
    //test
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

    
    private String GetPluginConnectorClass(File FileToCheck) {
        String Ret=null;

        JarFile jarFile = null;
        JarEntry entry = null;
        InputStream IS = null;
        try {
            jarFile = new JarFile(FileToCheck);
            //
            entry = jarFile.getJarEntry(KK_BASE_PLUGINS_CONNECTOR_FILE);
            if (entry == null) {
                System.out.println("Plugin read error (kkconnector file not found)");
                return null;
            }
            //
            IS = jarFile.getInputStream(entry);
        } catch (IOException ex) {
            System.out.println("Plugin info read error: " + ex.getMessage());            
            try {
                jarFile.close();
            } catch(Exception Ex) {}
            return null;
        }
        if (IS==null)
        {
            System.out.println("Plugin info read error: kkconnector file empty?"); 
            return null;
        }
        //
       BufferedReader in = new BufferedReader(new InputStreamReader(IS));
        
        try {
            Ret=in.readLine();
            in.close();
        } catch (IOException ex) {
              System.out.println("Plugin info read error: kkconnector file empty or broken?"); 
              return null;
        }
        //
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
        if (PluginFiles==null)
        {
            System.out.println("No plugins found...exitting");
            return null;
        }
        //
        System.out.println("Plugin files count: " + PluginFiles.length);
        //

        for (File loadFile : PluginFiles) {
            boolean Err = false;
            //Check load only Jar file
            if (!loadFile.getName().endsWith(".jar") | loadFile.isDirectory()) {
                continue;
            }
            System.out.println("--------------------");
            System.out.println("File: " + loadFile.getName());
            
            
           //
            try {
                //
                String ConnectorClass;
                IPluginKKConnector PluginConnection;
                ConnectorClass=GetPluginConnectorClass(loadFile);
                //
                URLClassLoader CLoader = new URLClassLoader(new URL[]{loadFile.toURI().toURL()});
                //
                PluginConnection= (IPluginKKConnector) CLoader.loadClass(ConnectorClass).newInstance();
                //
                if (CheckPlugin(Plugins,PluginConnection.GetPluginInfo())==false)
                {
                    System.out.println("Config: not in config");
                    System.out.println("Skip");
                    continue;
                }
                Ret[Counter]=PluginConnection;
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
