/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main;

import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import kkdev.kksystem.base.classes.plugins.ControllerConfiguration;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
import kkdev.kksystem.base.constants.SystemConsts;

/**
 *
 * @author blinov_is
 */
public abstract class SettingsManager {
    
    public static ControllerConfiguration MainConfiguration;
    //static FeatureConfiguration[] PluginConfigurations;

  //  public static ArrayList<FeatureConfiguration> GetPluginConfigurations() {
//        ArrayList<FeatureConfiguration> Ret;
//        Ret = new ArrayList<>();
//        Ret.addAll(Arrays.asList(PluginConfigurations));
//        return (Ret);
//    }

    //TODO CHANGE THIS!!!!
    public static String GetSystemDisplayUID()
    {
        return MainConfiguration.SystemDisplay_UID;
    
    }
    
    public static void Init() throws IOException {
        //
        LoadPluginConnections();
        //
        if (MainConfiguration == null) {
            MakeDefaultPluginConf();
            LoadPluginConnections();
        }
        //
        if (MainConfiguration==null )
        {
                   System.out.print("Load error"); 
                   System.exit(0);
        }

    }

  
    private static void LoadPluginConnections() {
        ControllerConfiguration Ret;
        System.out.println("Try load plugins connection config.");
        try {
            File files = new File(SystemConsts.KK_BASE_CONFPATH + "//"+SystemConsts.KK_BASE_SETTINGS_FILE);
            //
            //
            FileReader fr;
            int cnt = 0;
                try {
                    fr = new FileReader(files.getPath());
                    XStream xstream = new XStream(new DomDriver());
                    Ret=(ControllerConfiguration)xstream.fromXML(fr);

                } catch (Exception ex) {
                    System.out.println("Error on load plugin connections config");
                    System.out.println(ex.toString());
                    return;
                }

        } catch (StreamException Ex) {
            System.out.println("error");
            return;
        }
        MainConfiguration = Ret;
        System.out.println("Ok");
        System.out.println("===========================");
    }

    private static void MakeDefaultPluginConf() throws FileNotFoundException, IOException {
        ControllerConfiguration DefConfig;
        DefConfig=new ControllerConfiguration();
        //
        System.out.println("Creating default plugin connections config");
        //
        FeatureConfiguration[] DefConfFeatures = kk_defultPluginConnectionConfig.GetDefaultFeature();
        //
        DefConfig.SystemDisplay_UID=KK_PLUGIN_BASE_PLUGIN_LEDDISPLAY_UUID;
        DefConfig.Features=DefConfFeatures;
        //
        File MainConfPath = new File(SystemConsts.KK_BASE_CONFPATH);
        if (!MainConfPath.exists()) {
            MainConfPath.mkdir();
            
        }
        File ConfPath = new File(SystemConsts.KK_BASE_CONFPATH);
        if (!ConfPath.exists()) {
            ConfPath.mkdir();
            
        }

        //
        XStream xstream = new XStream(new DomDriver());

        // FileOutputStream fileOut = new FileOutputStream();
       
        FileWriter fw;
        fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + "/"+SystemConsts.KK_BASE_SETTINGS_FILE);

        xstream.toXML(DefConfig, fw);

        fw.close();
    }
}
