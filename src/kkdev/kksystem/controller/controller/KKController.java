/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.controller;

import kkdev.kksystem.controller.pluginmanager.PluginManager;

/**
 *
 * @author blinov_is
 */
public class KKController {
    static PluginManager PManager;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("KK System INIT Begin");
        //
        InitPluginManager();
        //
    }
    
    
    private static void InitPluginManager()
    {
            System.out.println("Init Pluginmanager");
            PManager=new PluginManager();
    }
    
}
