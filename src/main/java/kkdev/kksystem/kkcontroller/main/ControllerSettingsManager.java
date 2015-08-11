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
import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.classes.plugins.ControllerConfiguration;
import kkdev.kksystem.base.classes.plugins.simple.SettingsManager;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_HID_UUID;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_DEFAULT_CONTROLLER_CONFIG_STAMP_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_DEFAULT_CONTROLLER_CONFIG_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_SETTINGS_FILE_PFX;

/**
 *
 * @author blinov_is
 */
public abstract class ControllerSettingsManager {

    public static ControllerConfiguration MainConfiguration;
    private static SettingsManager Settings;

    //TODO CHANGE THIS!!!!
    public static String GetSystemDisplayUID() {
        return MainConfiguration.SystemDisplay_UID;

    }

    public static void Init() {
        
        String ConfFileUID=GetLastConfUID();
        
        if (ConfFileUID!=null)
            Settings = new SettingsManager(KK_BASE_SETTINGS_FILE_PFX +ConfFileUID+"_"+ ConfFileUID+".json", ControllerConfiguration.class);
        else //config not found
        {
            Settings = new SettingsManager(KK_BASE_SETTINGS_FILE_PFX +KK_BASE_DEFAULT_CONTROLLER_CONFIG_UID+"_"+ KK_BASE_DEFAULT_CONTROLLER_CONFIG_UID+".json", ControllerConfiguration.class);
            SaveLastConfUID(KK_BASE_DEFAULT_CONTROLLER_CONFIG_UID);
        }
        //
        LoadControllerConfiguration();
        //
        if (MainConfiguration == null) {
            System.out.print("Load error");
            System.exit(0);
        }

    }

    private static String GetLastConfUID() {

        File dir = new java.io.File(SystemConsts.KK_BASE_CONFPATH);
        if (!dir.exists()) {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(SystemConsts.KK_BASE_CONFPATH + "//" + SystemConsts.KK_BASE_SETTINGS_LASTCONF_FILE))) {
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

        try (BufferedWriter br = new BufferedWriter(new FileWriter(SystemConsts.KK_BASE_CONFPATH + "//" + SystemConsts.KK_BASE_SETTINGS_LASTCONF_FILE))) {
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
        System.out.println("Load plugin connection config.");

        File dir = new java.io.File(SystemConsts.KK_BASE_CONFPATH);
        if (!dir.exists()) {
            dir.mkdir();
        }

        MainConfiguration = (ControllerConfiguration) Settings.LoadConfig();

        if (MainConfiguration == null) {
            Settings.SaveConfig(MakeDefaultPluginConf());
            MainConfiguration = (ControllerConfiguration) Settings.LoadConfig();
        }

    }

    private static ControllerConfiguration MakeDefaultPluginConf() {
        ControllerConfiguration DefConfig;
        DefConfig = new ControllerConfiguration();
        //
        System.out.println("Creating default plugin connections config");
        //
        FeatureConfiguration[] DefConfFeatures = kk_defultPluginConnectionConfig.GetDefaultFeature();
        //
        DefConfig.SystemDisplay_UID = KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        DefConfig.SystemHID_UID = KK_PLUGIN_BASE_PLUGIN_HID_UUID;

        DefConfig.Features = DefConfFeatures;
        DefConfig.SystemMenuItems = kk_defultPluginConnectionConfig.GetDefaultSystemMenuItems();
        DefConfig.ConfigurationStamp = KK_BASE_DEFAULT_CONTROLLER_CONFIG_STAMP_UID;
        DefConfig.ConfigurationUID = KK_BASE_DEFAULT_CONTROLLER_CONFIG_UID;

        //
        return DefConfig;
    }
}
