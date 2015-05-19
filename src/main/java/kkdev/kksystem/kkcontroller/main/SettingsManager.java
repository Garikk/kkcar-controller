/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main;

import kkdev.kksystem.base.classes.plugins.PluginConnectionsConfig;
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
import kkdev.kksystem.base.constants.SystemConsts;

/**
 *
 * @author blinov_is
 */
public abstract class SettingsManager {

    static PluginConnectionsConfig[] PluginConfigurations;

    public static ArrayList<PluginConnectionsConfig> GetPluginConfigurations() {
        ArrayList<PluginConnectionsConfig> Ret;
        Ret = new ArrayList<>();
        Ret.addAll(Arrays.asList(PluginConfigurations));
        return (Ret);
    }

    //TODO CHANGE THIS!!!!
    public static String GetSystemDisplayUID()
    {
        return PluginConfigurations[0].SystemDisplay_UID;
    
    }
    
    public static void Init() throws IOException {
        //
        LoadPluginConnections();
        //
        if (PluginConfigurations == null) {
            MakeDefaultPluginConf();
            LoadPluginConnections();
        }
        //
        if (PluginConfigurations==null )
        {
                   System.out.print("Load error"); 
                   System.exit(0);
        }

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

    private static void MakeDefaultPluginConf() throws FileNotFoundException, IOException {
        //
        System.out.println("Creating default plugin connections config");
        //
        PluginConnectionsConfig Defconf = kk_defultPluginConnectionConfig.GetDefaultConnectionsConfig();
        //
        File MainConfPath = new File(SystemConsts.KK_BASE_CONFPATH);
        if (!MainConfPath.exists()) {
            MainConfPath.mkdir();
            
        }
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
