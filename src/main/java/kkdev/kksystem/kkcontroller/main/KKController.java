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
import kkdev.kksystem.kkcontroller.main.utils.RS232.RS232Scanner;
import kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader;
import static kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader.InitPlugins;
import static kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader.PlEx;
import static kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader.StartPlugins;
import static kkdev.kksystem.kkcontroller.sysupdate.SystemUpdater.CheckUpdate;
import static java.lang.Thread.sleep;
import kkdev.kksystem.kkcontroller.wdconnection.WDConnection;
import kkdev.kksystem.kkcontroller.wdconnection.WDSystemState;

/**
 *
 * @author blinov_is
 */
public class KKController {

    public static String CONTROLLER_VERSION = "0.9.test";

    static PluginLoader PM;
    static boolean Shutdown = false;
    static RS232Scanner HW_RS232Scan;
    static boolean ServiceMode=false;
    static WDConnection WatchDogService;
    public static WDSystemState CurrentSystemState;
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
        CurrentSystemState=new WDSystemState();
        
        //WatchDogService.GetWDInfo();
        //
        InitSystem();
        //
        int i = 0;
        WDSystemState WS;
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
        HW_RS232Scan = new RS232Scanner();
        HW_RS232Scan.MakeRS232DevList();
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
