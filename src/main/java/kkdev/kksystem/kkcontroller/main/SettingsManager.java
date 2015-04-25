/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import kkdev.kksystem.base.classes.PluginConnectionsConfig;
import kkdev.kksystem.base.constants.SystemConsts;

/**
 *
 * @author blinov_is
 */
public abstract class SettingsManager {

    static KKSystemConfig SysConfiguration;
    static PluginConnectionsConfig[] PluginConfigurations;

    public static ArrayList<PluginConnectionsConfig> GetPluginConfigurations() {
        ArrayList<PluginConnectionsConfig> Ret;
        Ret = new ArrayList<>();
        Ret.addAll(Arrays.asList(PluginConfigurations));
        return (Ret);
    }

    public static void Init() throws IOException {
        //
        LoadConf();
        //
        if (SysConfiguration == null) {
            MakeDefaultConf();
            LoadConf();
        }
        //
        LoadPluginConnections();
        //
        if (PluginConfigurations == null) {
            MakeDefaultPluginConf();
            LoadPluginConnections();
        }
        //
        if (PluginConfigurations==null || SysConfiguration==null)
        {
                   System.out.print("Load error"); 
                   System.exit(0);
        }

    }

    ///
    private static void LoadConf() {
        System.out.print("Load configuration...");

        try {
            String ConfFilePath = SystemConsts.KK_BASE_CONFPATH + "/" + SystemConsts.KK_BASE_SETTINGS_FILE;
            FileReader fr;

            try {
                fr = new FileReader(ConfFilePath);
            } catch (FileNotFoundException ex) {
                System.out.println("file not found");
                return;
            }

            XStream xstream = new XStream(new DomDriver());
            SysConfiguration = (KKSystemConfig) xstream.fromXML(fr);
            String current;

        } catch (StreamException Ex) {
            System.out.println("error");
            return;
        }
        System.out.println("Ok");
        System.out.println("===========================");
        //

    }

    private static void LoadPluginConnections() {
        PluginConnectionsConfig[] Ret;
        System.out.println("Try load plugins connection config.");
        try {
            File[] files = new File(SystemConsts.KK_BASE_CONFPATH_CONNECTIONS).listFiles();
            //
            if (files == null) {
                return;
            }
            //
            Ret = new PluginConnectionsConfig[files.length];

            FileReader fr;
            int cnt = 0;
            for (File f : files) {
                try {
                    fr = new FileReader(f.getPath());
                    XStream xstream = new XStream(new DomDriver());
                    Ret[cnt] = (PluginConnectionsConfig) xstream.fromXML(fr);
                    System.out.println("Loaded: " + Ret[cnt].ConfigName);
                    cnt++;
                } catch (Exception ex) {
                    System.out.println("Error on load plugin connections config");
                    System.out.println(ex.toString());
                    return;
                }
            }
        } catch (StreamException Ex) {
            System.out.println("error");
            return;
        }
        PluginConfigurations = Ret;
        System.out.println("Ok");
        System.out.println("===========================");
    }

    private static void MakeDefaultConf() throws FileNotFoundException, IOException {
        //
        System.out.println("Creating default conf");
        //
        KKSystemConfig Defconf = kk_defaultconfig.GetDefaultSystemConfig();
        //
        File ConfPath = new File(SystemConsts.KK_BASE_CONFPATH);
        if (!ConfPath.exists()) {
            ConfPath.mkdir();

        }

        //
        XStream xstream = new XStream(new DomDriver());

        // FileOutputStream fileOut = new FileOutputStream();
        FileWriter fw;
        fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + "/" + SystemConsts.KK_BASE_SETTINGS_FILE);

        xstream.toXML(Defconf, fw);

        fw.close();
    }

    private static void MakeDefaultPluginConf() throws FileNotFoundException, IOException {
        //
        System.out.println("Creating default plugin connections config");
        //
        PluginConnectionsConfig Defconf = kk_defultPluginConnectionConfig.GetDefaultConnectionsConfig();
        //
        File ConfPath = new File(SystemConsts.KK_BASE_CONFPATH_CONNECTIONS);
        if (!ConfPath.exists()) {
            ConfPath.mkdir();
        }

        //
        XStream xstream = new XStream(new DomDriver());

        // FileOutputStream fileOut = new FileOutputStream();
        FileWriter fw;
        fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH_CONNECTIONS + "/PluginConnections_" + Defconf.ConfigUUID + ".xml");

        xstream.toXML(Defconf, fw);

        fw.close();
    }
}
