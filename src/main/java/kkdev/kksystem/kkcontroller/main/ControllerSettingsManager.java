/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main;

import java.io.File;
import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.classes.plugins.ControllerConfiguration;
import kkdev.kksystem.base.classes.plugins.simple.SettingsManager;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_HID_UUID;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_SETTINGS_FILE;

/**
 *
 * @author blinov_is
 */
public abstract class ControllerSettingsManager {
    
    public static ControllerConfiguration MainConfiguration;
   private static SettingsManager Settings;

    //TODO CHANGE THIS!!!!
    public static String GetSystemDisplayUID()
    {
        return MainConfiguration.SystemDisplay_UID;
    
    }

    public static void Init() {
        Settings=new SettingsManager(KK_BASE_SETTINGS_FILE,ControllerConfiguration.class);
        //
        LoadPluginConnections();
        //
        if (MainConfiguration == null) {
            System.out.print("Load error");
            System.exit(0);
        }

    }

    private static void LoadPluginConnections() {
        ControllerConfiguration Ret;
        System.out.println("Load plugin connection config.");

         File dir = new java.io.File(SystemConsts.KK_BASE_CONFPATH);
        if (!dir.exists()) {
            dir.mkdir();
        }
            
        MainConfiguration=(ControllerConfiguration)Settings.LoadConfig();

        if (MainConfiguration == null) {
            Settings.SaveConfig(MakeDefaultPluginConf());
            MainConfiguration=(ControllerConfiguration)Settings.LoadConfig();
        }


    }

    private static ControllerConfiguration MakeDefaultPluginConf() {
        ControllerConfiguration DefConfig;
        DefConfig=new ControllerConfiguration();
        //
        System.out.println("Creating default plugin connections config");
        //
        FeatureConfiguration[] DefConfFeatures = kk_defultPluginConnectionConfig.GetDefaultFeature();
        //
        DefConfig.SystemDisplay_UID=KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        DefConfig.SystemHID_UID=KK_PLUGIN_BASE_PLUGIN_HID_UUID;

        DefConfig.Features=DefConfFeatures;
        DefConfig.SystemMenuItems=kk_defultPluginConnectionConfig.GetDefaultSystemMenuItems();
        DefConfig.ConfigurationStamp="201f7df6-bedc-4942-b295-bd98249e5513";
        DefConfig.ConfigurationUID="88f220f5-a666-4537-a5bc-96aeb620d9fd";
        
                
        //
         return DefConfig;
    }
}
