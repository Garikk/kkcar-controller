/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.pluginmanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.exit;
import static java.lang.System.out;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import kkdev.kksystem.base.classes.plugins.PluginConnection;
import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_PLUGINPATH;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_PLUGINS_MANIFEST_CONNECTOR_ATTR;
import kkdev.kksystem.base.interfaces.IPluginKKConnector;
import kkdev.kksystem.kkcontroller.main.ControllerSettingsManager;
import static kkdev.kksystem.kkcontroller.main.ControllerSettingsManager.MainConfiguration;

/**
 *
 * @author blinov_is
 */
public abstract class PluginLoader {

    static HashMap<String, IPluginKKConnector> ActivePlugins;
    public static PluginExecute PlEx;

    public static void InitPlugins() {
        Set<String> ToLoad;
        //Prepare config
        ToLoad = GetRequiredPlugins(MainConfiguration.Features);
        //Load plugins
        if (ActivePlugins==null)
            ActivePlugins = ConnectPlugins(ToLoad,false);
        //
        if (ActivePlugins == null) {
            return;
        }
        //
        PlEx = new PluginExecute(ActivePlugins);
        PlEx.InitPlugins();

    }

    public static void PreInitAllPlugins()
    {
        ActivePlugins=ConnectPlugins(null,true);
    }
    public static Set<String> GetPluginUIDs()
    {
        return ActivePlugins.keySet();
    }
    
    public static void StartPlugins() {
        PlEx.StartPlugins();
    }

    public static Set<String> GetRequiredPlugins(FeatureConfiguration[] Features) {
        Set<String> Ret;
        Ret = new HashSet<>();
        
        for (FeatureConfiguration FT : Features) {
            if (FT.Connections != null) {
                for (PluginConnection PCC : FT.Connections) {
                    if (!Ret.contains(PCC.SourcePluginUID)) {
                        Ret.add(PCC.SourcePluginUID);
                    }
                    if (!Ret.contains(PCC.TargetPluginUID)) {
                        Ret.add(PCC.TargetPluginUID);
                    }
                }
            }
            //
        }
        return Ret;
    }

    private static String GetPluginConnectorClass(File FileToCheck) {
        String Ret;

        JarFile jarFile = null;
        InputStream IS = null;
        try {
            jarFile = new JarFile(FileToCheck);
            //
            Manifest MF = jarFile.getManifest();
            Ret = MF.getMainAttributes().getValue(KK_BASE_PLUGINS_MANIFEST_CONNECTOR_ATTR);
            //
            if (Ret == null) {
                out.println("Plugin read error (kkconnector attribute in Manifest)");
                return null;
            }
            //
            jarFile.close();
        } catch (IOException ex) {
            out.println("Plugin info read error: " + ex.getMessage());
            try {
                jarFile.close();
            } catch (Exception Ex) {
            }
            return null;
        }
        //
        return Ret;
    }

    private static HashMap<String, IPluginKKConnector> ConnectPlugins(Set<String> Plugins, boolean ConnectAllPlugins) {
        //
        int Counter = 0;
        HashMap<String, IPluginKKConnector> Ret = new HashMap<>();
        //
        //
        File folder = new File(KK_BASE_PLUGINPATH);
        File[] PluginFiles = folder.listFiles();
        //
        if (PluginFiles == null) {
            out.println("No plugins found...exitting");
            exit(0);
        }
        //
        out.println("Plugin files count: " + PluginFiles.length);
        //

        for (File loadFile : PluginFiles) {
            boolean Err = false;
            //Check load only Jar file
            if (!loadFile.getName().endsWith(".jar") | loadFile.isDirectory()) {
                continue;
            }
            out.println("--------------------");
            out.println("File: " + loadFile.getName());

            //
            try {
                //
                String ConnectorClass;
                IPluginKKConnector PluginConnection;
                ConnectorClass = GetPluginConnectorClass(loadFile);
                //
                URLClassLoader CLoader = new URLClassLoader(new URL[]{loadFile.toURI().toURL()});
                //
                PluginConnection = (IPluginKKConnector) CLoader.loadClass(ConnectorClass).newInstance();
                //
                if ( (!ConnectAllPlugins) && (!Plugins.contains(PluginConnection.GetPluginInfo().PluginUUID)) ) {
                    out.println("Config: not in config. skip");
                    continue;
                }
                Ret.put(PluginConnection.GetPluginInfo().PluginUUID, PluginConnection);
                //
                out.println("Load: ok");
                //
                Counter++;
            } catch (MalformedURLException | InstantiationException | ClassNotFoundException |NullPointerException | IllegalAccessException e) {
                out.println("Load Error: " + loadFile.getName() + " " + e.toString());
            }
            //

        }
        return Ret;
    }

}
