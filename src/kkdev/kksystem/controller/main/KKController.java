/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.main;

import java.io.IOException;
import kkdev.kksystem.controller.pluginmanager.PluginManager;

/**
 *
 * @author blinov_is
 */
public class KKController {
    static SettingsManager SManager;
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
            System.out.println("Settings:");
            SManager=new SettingsManager();
            //
            SManager.Init();
            //
            System.out.println("Plugins:");
            //
            PM=new PluginManager();
            //
            PM.InitPlugins(SManager.SysConf.ConfPlugins);
            //
            System.out.println("System start:");
    }
    
}
