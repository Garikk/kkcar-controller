/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main;

import java.io.IOException;
import kkdev.kksystem.kkcontroller.pluginmanager.PluginManager;

/**
 *
 * @author blinov_is
 */
public class KKController {
   
    static PluginManager PM;
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
            PluginManager.InitPlugins(SettingsManager.GetPluginConfigurations());
            //
            //
            System.out.println("================");
            System.out.println("System start:");
            PluginManager.StartPlugins();
    }
    
}
