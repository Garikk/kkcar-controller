/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.pluginmanager;


import java.io.File;
import kkdev.kksystem.base.constants.SystemConsts;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.System.out;
import java.util.Map;
import kkdev.kksystem.controller.main.kk_defaultconfig;

/**
 *
 * @author blinov_is
 */
public class PluginChainManager {
    KKSystemConfig SysConf;
    PluginManager PM;
    public void Init() throws IOException
    {
        //
        LoadConf();
        //
        PM=new PluginManager();
        //
       // PM.LoadPlugins(SysConf.ConfPlugins);
        //
        if (SysConf==null)
        {
          MakeDefaultConf();
          LoadConf();
        }
        //
        PM.LoadPlugins(SysConf.ConfPlugins);
    }
    
    ///
    private void LoadConf()
    {
      System.out.println("Loading configuraion");
     try
      {
         FileInputStream fileIn = new FileInputStream(SystemConsts.KK_BASE_CONFPATH + "/" + SystemConsts.KK_BASE_SETTINGS_FILE);
         ObjectInputStream in = new ObjectInputStream(fileIn);
         SysConf = (KKSystemConfig) in.readObject();
         in.close();
         fileIn.close();
      }catch(IOException i)
      {
         System.out.println("system config load failed!");
      }catch(ClassNotFoundException c)
      {
         System.out.println("system config load failed! (File not found)");
      }
    }
    private void MakeDefaultConf() throws FileNotFoundException, IOException
    {
        KKSystemConfig Defconf=kk_defaultconfig.GetDefaultSystemConfig();
        //
        File ConfPath=new File(SystemConsts.KK_BASE_CONFPATH);
        if (!ConfPath.exists())
            ConfPath.mkdir();
                
        //
        FileOutputStream fileOut = new FileOutputStream(SystemConsts.KK_BASE_CONFPATH + "/" + SystemConsts.KK_BASE_SETTINGS_FILE);
        ObjectOutputStream out;
        out = new ObjectOutputStream(fileOut);
        out.writeObject(Defconf);
        out.close();
    }
}
