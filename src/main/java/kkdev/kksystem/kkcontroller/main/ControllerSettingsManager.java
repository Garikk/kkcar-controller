/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.exit;
import static java.lang.System.out;
import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.classes.plugins.ControllerConfiguration;
import kkdev.kksystem.base.classes.plugins.simple.SettingsManager;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_HID_UUID;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_CONFPATH;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_DEFAULT_CONTROLLER_CONFIG_STAMP_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_DEFAULT_CONTROLLER_CONFIG_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_SETTINGS_FILE_PFX;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_SETTINGS_LASTCONF_FILE;
import static kkdev.kksystem.kkcontroller.main.kk_defultPluginConnectionConfig.GetDefaultFeature;
import static kkdev.kksystem.kkcontroller.main.kk_defultPluginConnectionConfig.GetDefaultSystemMenuItems;

/**
 *
 * @author blinov_is
 */
public abstract class ControllerSettingsManager {

    public static ControllerConfiguration mainConfiguration;
    private static SettingsManager settings;

    //TODO CHANGE THIS!!!!
    public static String GetSystemDisplayUID() {
        return mainConfiguration.systemDisplay_UID;

    }

    public static void init() {
        
        String ConfFileUID=getLastConfUID();
        
        if (ConfFileUID!=null)
            settings = new SettingsManager(KK_BASE_SETTINGS_FILE_PFX +ConfFileUID+"_"+ ConfFileUID+".json", ControllerConfiguration.class);
        else //config not found
        {
            settings = new SettingsManager(KK_BASE_SETTINGS_FILE_PFX +KK_BASE_DEFAULT_CONTROLLER_CONFIG_UID+"_"+ KK_BASE_DEFAULT_CONTROLLER_CONFIG_UID+".json", ControllerConfiguration.class);
            SaveLastConfUID(KK_BASE_DEFAULT_CONTROLLER_CONFIG_UID);
        }
        //
        LoadControllerConfiguration();
        //
        if (mainConfiguration == null) {
            out.print("Load error");
            exit(0);
        }

    }

    private static String getLastConfUID() {

        File dir = new java.io.File(KK_BASE_CONFPATH);
        if (!dir.exists()) {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(KK_BASE_CONFPATH + "//" + KK_BASE_SETTINGS_LASTCONF_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                br.close();
               return line;
            }
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
        return null;
    }
     public  static String SaveLastConfUID(String UID) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(KK_BASE_CONFPATH + "//" + KK_BASE_SETTINGS_LASTCONF_FILE))) {
          br.write(UID);
          br.close();
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
        return null;
    }

    private static void LoadControllerConfiguration() {
        ControllerConfiguration Ret;
        out.println("Load plugin connection config.");

        File dir = new java.io.File(KK_BASE_CONFPATH);
        if (!dir.exists()) {
            dir.mkdir();
        }

        mainConfiguration = (ControllerConfiguration) settings.loadConfig();

        if (mainConfiguration == null) {
            settings.saveConfig(MakeDefaultPluginConf());
            mainConfiguration = (ControllerConfiguration) settings.loadConfig();
        }

    }

    private static ControllerConfiguration MakeDefaultPluginConf() {
        ControllerConfiguration DefConfig;
        DefConfig = new ControllerConfiguration();
        //
        out.println("Creating default plugin connections config");
        //
        FeatureConfiguration[] DefConfFeatures = GetDefaultFeature();
        //
        DefConfig.systemDisplay_UID = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        DefConfig.systemHID_UID = KK_PLUGIN_BASE_PLUGIN_HID_UUID;

        DefConfig.features = DefConfFeatures;
        DefConfig.systemMenuItems = GetDefaultSystemMenuItems();
        DefConfig.configurationStamp = KK_BASE_DEFAULT_CONTROLLER_CONFIG_STAMP_UID;
        DefConfig.configurationUID = KK_BASE_DEFAULT_CONTROLLER_CONFIG_UID;

        //
        return DefConfig;
    }
}
