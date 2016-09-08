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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import kkdev.kksystem.base.classes.plugins.PluginConnection;
import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.classes.plugins.PluginInfo;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_PLUGINPATH;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_PLUGINS_MANIFEST_CONNECTOR_ATTR;
import static kkdev.kksystem.kkcontroller.main.ControllerSettingsManager.mainConfiguration;
import kkdev.kksystem.base.interfaces.IPluginConnection;

/**
 *
 * @author blinov_is
 */
public abstract class PluginLoader {

    static HashMap<String, IPluginConnection> ActivePlugins;
    public static PluginExecute PlEx;

    public static void InitPlugins() {
        Set<String> ToLoad;
        //Prepare config
        ToLoad = GetRequiredPlugins(mainConfiguration.features);
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

    public static IPluginConnection getPluginByUUID(String UUID)
    {
        return ActivePlugins.get(UUID);
    }
    public static void PreInitAllPlugins()
    {
        ActivePlugins=ConnectPlugins(null,true);
    }
    public static Set<String> GetActivePluginUIDs()
    {
        return ActivePlugins.keySet();
    }
    
    public static void StartPlugins() {
        PlEx.StartPlugins();
    }
     public static void StopPlugins() {
        PlEx.StopPlugins();
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

    private static HashMap<String, IPluginConnection> ConnectPlugins(Set<String> Plugins, boolean ConnectAllPlugins) {
        //
        int Counter = 0;
        HashMap<String, IPluginConnection> Ret = new HashMap<>();
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
                IPluginConnection PluginConnection;
                ConnectorClass = GetPluginConnectorClass(loadFile);
                //
                URLClassLoader CLoader = new URLClassLoader(new URL[]{loadFile.toURI().toURL()});
                //
                PluginConnection = (IPluginConnection) CLoader.loadClass(ConnectorClass).newInstance();
                //
                if ( (!ConnectAllPlugins) && (!Plugins.contains(PluginConnection.getPluginInfo().PluginUUID)) ) {
                    out.println("Config: not in config. skip");
                    continue;
                }
                Ret.put(PluginConnection.getPluginInfo().PluginUUID, PluginConnection);
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

    public static List<PluginInfo> GetActivePluginsInfo()
    {
        List<PluginInfo> Ret;
        Ret=new ArrayList<>();
        //
        for (IPluginConnection PK:ActivePlugins.values())
        {
            Ret.add(PK.getPluginInfo());
        }
        //
        return Ret;
    }
}
