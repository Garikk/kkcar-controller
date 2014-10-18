/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.pluginmanager;


import kkdev.kksystem.base.constants.SystemConsts;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 *
 * @author blinov_is
 */
public class PluginChainManager {
    KKSystemConfig SysConf;
    PluginManager PM;
    public void Init()
    {
        //
        LoadConf();
        //
        PM=new PluginManager();
        //
        PM.LoadPlugins(SysConf.ConfPlugins);
    }
    
    
    
    ///
    private void LoadConf()
    {

     try
      {
         FileInputStream fileIn = new FileInputStream(SystemConsts.KK_BASE_CONFPATH);
         ObjectInputStream in = new ObjectInputStream(fileIn);
         SysConf = (KKSystemConfig) in.readObject();
         in.close();
         fileIn.close();
      }catch(IOException i)
      {
         System.out.println("system config load failed!");
          i.printStackTrace();
          System.exit(1);
      }catch(ClassNotFoundException c)
      {
         System.out.println("system config load failed! (File not found)");
         c.printStackTrace();
         System.exit(1);
      }
    
    }
}
