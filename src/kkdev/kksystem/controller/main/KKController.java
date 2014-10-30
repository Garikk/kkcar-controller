/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.main;

import java.io.IOException;
import kkdev.kksystem.controller.pluginmanager.PluginChainManager;
import kkdev.kksystem.controller.pluginmanager.PluginManager;

/**
 *
 * @author blinov_is
 */
public class KKController {
    static PluginChainManager CManager;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        System.out.println("KK System INIT Begin");
        //
        InitChainManager();
        //
    }
    
    
    private static void InitChainManager() throws IOException
    {
            System.out.println("Init Chain Manager:");
            CManager=new PluginChainManager();
            //
            CManager.Init();
            //
            
    }
    
}
