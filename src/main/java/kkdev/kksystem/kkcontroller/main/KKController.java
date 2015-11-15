/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main;

import java.io.IOException;
import static java.lang.System.exit;
import static java.lang.System.out;
import static java.lang.Thread.sleep;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
import static kkdev.kksystem.kkcontroller.main.ControllerSettingsManager.Init;
import kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.InitSystemMenu;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.ShowMenu;
import kkdev.kksystem.kkcontroller.main.utils.RS232.RS232Scanner;
import kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader;
import static kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader.InitPlugins;
import static kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader.PlEx;
import static kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader.StartPlugins;
import kkdev.kksystem.kkcontroller.sysupdate.SystemUpdater;
import static kkdev.kksystem.kkcontroller.sysupdate.SystemUpdater.CheckUpdate;

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
        out.println("KK System INIT Begin");
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
        out.println("Stop");
        exit(0);
    }
    
    
    private static void InitSystem() throws IOException
    {
            out.println("================");
            out.println("Settings:");
            //
            Init();
            //
            //Check updates, if "true" - have updates, watchdog make update and start app
            //
            if (CheckUpdate(CONTROLLER_VERSION))
                exit(0);
            out.println("================");
            out.println("Base utils:");
            out.println("Collect RS-232 ports:");
            RS232Scanner.MakeRS232DevList();
            //
            out.println("================");
            out.println("Plugins:");
            //
            InitPlugins();
            //
            out.println("================");
            //
            InitSystemMenu(PlEx);
           PlEx.ChangeFeature(KK_BASE_FEATURES_SYSTEM_UID);
            //
            out.println("================");
            out.println("System start:");
            StartPlugins();
            ShowMenu();
            //
         
    }
    
}
