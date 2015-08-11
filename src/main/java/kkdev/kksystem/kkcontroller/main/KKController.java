/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main;

import java.io.IOException;
import static java.lang.Thread.sleep;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
import kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu;
import kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader;
import kkdev.kksystem.kkcontroller.sysupdate.SystemUpdater;

/**
 *
 * @author blinov_is
 */
public class KKController {
    public static String CONTROLLER_VERSION="0.9.test";
    
    static PluginLoader PM;
    static boolean Shutdown=false;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("KK System INIT Begin");
        //
        InitSystem();
        //
        int i=0;
        while (!Shutdown)
        {
            i++;
            sleep(1000);
            if (i==55)
                Shutdown=true;
            
        }
        System.out.println("Stop");
        System.exit(0);
    }
    
    
    private static void InitSystem() throws IOException
    {
            System.out.println("================");
            System.out.println("Settings:");
            //
            ControllerSettingsManager.Init();
            //
            //Check updates, if "true" - have updates, watchdog make update and start app
            //
            if (SystemUpdater.CheckUpdate(CONTROLLER_VERSION))
                System.exit(0);
            
            //
            System.out.println("================");
            System.out.println("Plugins:");
            //
            PluginLoader.InitPlugins();
            //
            System.out.println("================");
            //
            SystemMenu.InitSystemMenu(PM.PlEx);
           PluginLoader.PlEx.ChangeFeature(KK_BASE_FEATURES_SYSTEM_UID);
            //
            System.out.println("================");
            System.out.println("System start:");
            PluginLoader.StartPlugins();
            SystemMenu.ShowMenu();
            //
         
    }
    
}
