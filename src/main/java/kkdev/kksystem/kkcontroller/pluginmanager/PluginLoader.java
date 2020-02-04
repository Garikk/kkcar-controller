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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author blinov_is
 */
public final class PluginLoader {
    private static final Logger logger = LogManager.getLogger("CONTROLLER_PLOADER");
    static HashMap<String, IPluginConnection> ActivePlugins;
    public static PluginExecute PlEx;

    public static void initPlugins() {
        Set<String> ToLoad;
        //Prepare config
        ToLoad = getRequiredPlugins(mainConfiguration.features);
        //Load plugins
        if (ActivePlugins == null) {
            ActivePlugins = connectPlugins(ToLoad, false);
        }

        if (ActivePlugins == null) {
            return;
        }

        PlEx = new PluginExecute(ActivePlugins);
        PlEx.InitPlugins();

    }

    public static IPluginConnection getPluginByUUID(String UUID) {
        return ActivePlugins.get(UUID);
    }

    public static void preInitAllPlugins() {
        ActivePlugins = connectPlugins(null, true);
    }

    public static Set<String> getActivePluginUIDs() {
        return ActivePlugins.keySet();
    }

    public static void startPlugins() {
        PlEx.StartPlugins();
    }

    public static void stopPlugins() {
        PlEx.StopPlugins();
    }

    public static Set<String> getRequiredPlugins(FeatureConfiguration[] Features) {
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

        }
        return Ret;
    }

    private static String getPluginConnectorClass(File FileToCheck) {
        String Ret;

        JarFile jarFile = null;
        InputStream IS = null;
        try {
            jarFile = new JarFile(FileToCheck);

            Manifest MF = jarFile.getManifest();
            Ret = MF.getMainAttributes().getValue(KK_BASE_PLUGINS_MANIFEST_CONNECTOR_ATTR);

            if (Ret == null) {
                logger.error("Plugin read error (kkconnector attribute in Manifest)");
                return null;
            }

            jarFile.close();
        } catch (IOException ex) {
            logger.error("Plugin info read error: " + ex.getMessage());
            try {
                jarFile.close();
            } catch (Exception Ex) {
            }
            return null;
        }

        return Ret;
    }

    private static HashMap<String, IPluginConnection> connectPlugins(Set<String> Plugins, boolean ConnectAllPlugins) {

        Integer Counter = 0;
        HashMap Ret = new HashMap<String, IPluginConnection>();

        File folder = new File(KK_BASE_PLUGINPATH);
        File[] PluginFiles = folder.listFiles();

        if (PluginFiles == null) {
            logger.warn("No plugins found...exitting");
            exit(0);
        }

        logger.info("Plugin files count: " + PluginFiles.length);

        for (File loadFile : PluginFiles) {
            Boolean Err = false;
            //Check load only Jar file
            if (!loadFile.getName().endsWith(".jar") | loadFile.isDirectory()) {
                continue;
            }
            logger.info("--------------------");
            logger.info("File: " + loadFile.getName());

            try {

                String ConnectorClass;
                IPluginConnection PluginConnection;
                ConnectorClass = getPluginConnectorClass(loadFile);

                URLClassLoader CLoader = new URLClassLoader(new URL[]{loadFile.toURI().toURL()});

                PluginConnection = (IPluginConnection) CLoader.loadClass(ConnectorClass).newInstance();

                if ((!ConnectAllPlugins) && (!Plugins.contains(PluginConnection.getPluginInfo().PluginUUID))) {
                    logger.info("Config: not in config. skip");
                    continue;
                }
                Ret.put(PluginConnection.getPluginInfo().PluginUUID, PluginConnection);

                logger.info("Load: ok");

                Counter++;
            } catch (MalformedURLException | InstantiationException | ClassNotFoundException | NullPointerException | IllegalAccessException e) {
                logger.error("Load Error: " + loadFile.getName() + " " + e.toString());
            }

        }
        return Ret;
    }

    public static List<PluginInfo> getActivePluginsInfo() {
        List<PluginInfo> Ret;
        Ret = new ArrayList<>();

        for (IPluginConnection PK : ActivePlugins.values()) {
            Ret.add(PK.getPluginInfo());
        }

        return Ret;
    }
}
