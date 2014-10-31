/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.pluginmanager;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import kkdev.kksystem.base.constants.SystemConsts;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import kkdev.kksystem.controller.main.kk_defaultconfig;

/**
 *
 * @author blinov_is
 */
public class SettingsManager {

    KKSystemConfig SysConf;
    PluginManager PM;

    public void Init() throws IOException {
        //
        LoadConf();
        //
        if (SysConf == null) {
            MakeDefaultConf();
            LoadConf();
        }
        //
        PM = new PluginManager();
        PM.LoadPlugins(SysConf.ConfPlugins);
    }

    ///
    private void LoadConf() {
        System.out.print("Try load configuration...");

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
            SysConf = (KKSystemConfig) xstream.fromXML(fr);
        } catch (StreamException Ex) {
            System.out.println("error");
            return;
        }

        System.out.println("Ok");

    }

    private void MakeDefaultConf() throws FileNotFoundException, IOException {
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
}