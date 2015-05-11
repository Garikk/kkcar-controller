/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.pluginmanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import kkdev.kksystem.base.classes.PluginConnection;
import kkdev.kksystem.base.classes.PluginConnectionsConfig;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_PLUGINS_MANIFEST_CONNECTOR_ATTR;
import kkdev.kksystem.base.interfaces.IPluginKKConnector;

/**
 *
 * @author blinov_is
 */
public abstract class PluginManager {

    static HashMap<String, IPluginKKConnector> ActivePlugins;
    static PluginExecute PlEx;

    public static void InitPlugins(ArrayList<PluginConnectionsConfig> ConnectionsConfiguration) {
        ArrayList<String> ToLoad;
        //Prepare config
        ToLoad = CreateLoadPluginsList(ConnectionsConfiguration);
        //Load plugins
        ActivePlugins = ConnectPlugins(ToLoad);
        //
        if (ActivePlugins == null) {
            return;
        }
        //
        PlEx = new PluginExecute(ActivePlugins);
        PlEx.InitPlugins();

    }

    public static void StartPlugins() {
        PlEx.StartPlugins();
    }

    private static ArrayList<String> CreateLoadPluginsList(ArrayList<PluginConnectionsConfig> ConfConfig) {

        ArrayList<String> Ret;
        Ret = new ArrayList<>();
        //
        ConfConfig.stream().forEach((PC) -> {
            for (PluginConnection PCC : PC.Connections) {
                if (!Ret.contains(PCC.SourcePluginUID)) {
                    Ret.add(PCC.SourcePluginUID);
                }
                if (!Ret.contains(PCC.TargetPluginUID)) {
                    Ret.add(PCC.TargetPluginUID);
                }
            }
        });
        //
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
                System.out.println("Plugin read error (kkconnector attribute in Manifest)");
                return null;
            }
            //
            jarFile.close();
        } catch (IOException ex) {
            System.out.println("Plugin info read error: " + ex.getMessage());
            try {
                jarFile.close();
            } catch (Exception Ex) {
            }
            return null;
        }
        //
        return Ret;
    }

    private static HashMap<String, IPluginKKConnector> ConnectPlugins(ArrayList<String> Plugins) {
        System.out.println("Required plugins count: " + Plugins.size());
        //
        int Counter = 0;
        HashMap<String, IPluginKKConnector> Ret = new HashMap<>();
        //
        //
        File folder = new File(SystemConsts.KK_BASE_PLUGINPATH);
        File[] PluginFiles = folder.listFiles();
        //
        if (PluginFiles == null) {
            System.out.println("No plugins found...exitting");
            System.exit(0);
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
                ConnectorClass = GetPluginConnectorClass(loadFile);
                //
                URLClassLoader CLoader = new URLClassLoader(new URL[]{loadFile.toURI().toURL()});
                //
                PluginConnection = (IPluginKKConnector) CLoader.loadClass(ConnectorClass).newInstance();
                //
                if (!Plugins.contains(PluginConnection.GetPluginInfo().PluginUUID)) {
                    System.out.println("Config: not in config. skip");
                    continue;
                }
                Ret.put(PluginConnection.GetPluginInfo().PluginUUID, PluginConnection);
                //
                System.out.println("Load: ok");
                //
                Counter++;
            } catch (MalformedURLException | InstantiationException | ClassNotFoundException | IllegalAccessException e) {
                System.out.println("Load Error: " + loadFile.getName() + " " + e.toString());
            }
            //

        }
        return Ret;
    }

}
