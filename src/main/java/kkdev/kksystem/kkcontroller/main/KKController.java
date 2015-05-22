/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main;

import java.io.IOException;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
import kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu;
import kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader;

/**
 *
 * @author blinov_is
 */
public class KKController {
   
    static PluginLoader PM;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        System.out.println("KK System INIT Begin");
        //
        InitSettingsManager();
        //
    }
    
    
    private static void InitSettingsManager() throws IOException
    {
            System.out.println("================");
            System.out.println("Settings:");
            //
            SettingsManager.Init();
            //
            System.out.println("================");
            System.out.println("Plugins:");
            //
            PluginLoader.InitPlugins(SettingsManager.GetPluginConfigurations());
            //
            System.out.println("================");
            //
            System.out.println("Active feature: System menu");
            SystemMenu.ChangeCurrentFeature(KK_BASE_FEATURES_SYSTEM_UID);
            //
            System.out.println("================");
            System.out.println("System start:");
            PluginLoader.StartPlugins();
            //
            // Init kkcontroller display
            SystemMenu.InitDisplay();
    }
    
}
