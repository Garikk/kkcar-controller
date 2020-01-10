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
import kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader;
import static kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader.PlEx;
import kkdev.kksystem.kkcontroller.main.utils.UtilsManager;
import kkdev.kksystem.kkcontroller.main.systemoperations.SystemOperations;
import kkdev.kksystem.base.constants.SystemConsts;
import kkdev.kksystem.kkcontroller.main.systemmenu.kk_defaultUI;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.showMenu;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.initSystemMenu;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import kkdev.kksystem.kkcontroller.main.utils.HWUtility;
import kkdev.kksystem.kkcontroller.sysupdate.SystemUpdater;

/**
 *
 * @author blinov_is
 */
public class KKController {

    public static String CONTROLLER_VERSION = "1.1.0.20190104";

    static PluginLoader PM;
    static boolean Shutdown = false;
    static boolean ServiceMode = false;
    static String SysProxyHost;
    static int SysProxyPort;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> Profiles = new ArrayList();
        for (String A : args) {
            if (A.equals("service")) {
                ServiceMode = true;
                System.in.close();
                System.out.close();
            }
            else if (A.startsWith("proxyHost")) {
                SysProxyHost = A.split("=")[1];
            }
            else if (A.startsWith("proxyPort")) {
                SysProxyPort = Integer.parseInt(A.split("=")[1]);
            }
            else if (A.startsWith("PRF"))
            {
                Profiles.add(A);
            }
        }
        if (Profiles.isEmpty())
        {
            //Add default profiles
            Profiles.add(kk_DefConfProfileTypes.PRF_MEDIACENTER.toString());
            Profiles.add(kk_DefConfProfileTypes.PRF_ODB_DATAVIEW.toString());
            Profiles.add(kk_DefConfProfileTypes.PRF_ANDROID_EXT_CONNECTOR.toString());
            Profiles.add(kk_DefConfProfileTypes.PRF_BLUETOOTH_RPI.toString());
        }

        out.println("KK System INIT Begin");
        

        InitSystem(Profiles);

        while (!Shutdown) {
            if (1 == 1) {
                sleep(1000);
                //if (WatchDogService.getInstance().StateChangeAlert) {
                //    WatchDogService.getInstance().StateChangeAlert = false;
                //    SystemOperations.systemStateChangedAlert();
                // }
            } else {
                Shutdown = true;
            }
        }

        StopSystem();

        out.println("Stop");
        exit(0);
    }

    public static void InitSystem(List<String> Profiles) throws IOException {
        out.println("================");
        out.println("OS: " + System.getProperty("os.name").toLowerCase());
        out.println("ARCH: " + System.getProperty("os.arch").toLowerCase());

        out.println("================");
        out.println("Settings:");
       
        ControllerSettingsManager.init(Profiles);
        //
        //Check updates, if "true" - have updates, watchdog make update and start app
        //
        SystemUpdater.getInstance().setProxyHost(SysProxyHost);
        SystemUpdater.getInstance().setProxyPort(SysProxyPort);
        //if (SystemUpdater.getInstance().checkSystemUpdateOnStart(CONTROLLER_VERSION)) {
        //    exit(0);
        //}
        out.println("================");
        out.println("Base utils:");
        out.println("==");
        out.println("Collect RS-232 ports:");

        ((HWUtility) UtilsManager.getInstance().HWManager()).getRS232Scanner().MakeRS232DevList();
        out.println("==");
        out.println("Make system menu");
        kk_defaultUI.AddDefaultSystemUIPages();
        out.println("================");
        out.println("Plugins:");

        PluginLoader.initPlugins();

        out.println("================");
        out.println("Services:");
        out.println("================");
        //PluginLoader.initPlugins();

        initSystemMenu(PlEx);
        SystemOperations.changeFeature(KK_BASE_FEATURES_SYSTEM_UID, SystemConsts.KK_BASE_UICONTEXT_DEFAULT);

        out.println("================");
        out.println("System start:");
        PluginLoader.startPlugins();
        out.println("================");
        out.println("Load ok");
        showMenu();
    }

    public static void StopSystem() {
        out.println("================");
        out.println("Stop Plugins");
        PluginLoader.stopPlugins();
        out.println("================");
    }

}
