/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main;

import java.io.IOException;
import static java.lang.System.exit;
import static java.lang.System.out;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
import static kkdev.kksystem.kkcontroller.main.ControllerSettingsManager.Init;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.InitSystemMenu;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.ShowMenu;
import kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader;
import static kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader.InitPlugins;
import static kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader.PlEx;
import static kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader.StartPlugins;
import static kkdev.kksystem.kkcontroller.sysupdate.SystemUpdater.CheckUpdate;
import kkdev.kksystem.kkcontroller.main.utils.UtilsManager;
import kkdev.kksystem.kkcontroller.wdconnection.WatchDogService;
import static java.lang.Thread.sleep;

/**
 *
 * @author blinov_is
 */
public class KKController {

    public static String CONTROLLER_VERSION = "0.9.test";
       
    static PluginLoader PM;
    static boolean Shutdown = false;
    static boolean ServiceMode=false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        for (String A:args)
            if (A.equals("service"))
            {
                ServiceMode=true;
                System.in.close();
                System.out.close();
            }
        //
        out.println("KK System INIT Begin");
        //
        WatchDogService.getInstance().StartWDS();
        //
        InitSystem();
        //
        int i = 0;
        while (!Shutdown) {
            i++;
            sleep(1000);
            if (i == 70) {
                Shutdown=true;
               // WS = WatchDogService.GetWDInfo();
               // Shutdown = (WS.TargetState==WDSystemState.WDStates.WD_SysState_POWEROFF);
            }

        }
        out.println("Stop");
        exit(0);
    }

    private static void InitSystem() throws IOException {
        out.println("================");
        out.println("OS: " + System.getProperty("os.name").toLowerCase());
        out.println("ARCH: " + System.getProperty("os.arch").toLowerCase());

        out.println("================");
        out.println("Settings:");
        //
        Init();
        //
        //Check updates, if "true" - have updates, watchdog make update and start app
        //
        if (CheckUpdate(CONTROLLER_VERSION)) {
            exit(0);
        }
        out.println("================");
        out.println("Base utils:");
        out.println("Collect RS-232 ports:");
        //
        UtilsManager.getInstance().getRS232Scanner().MakeRS232DevList();
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
