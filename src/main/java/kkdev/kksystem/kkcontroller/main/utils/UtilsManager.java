/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.utils;

import kkdev.kksystem.kkcontroller.main.utils.RS232.RS232Scanner;

/**
 *
 * @author blinov_is
 */
public class UtilsManager {
    private static UtilsManager UTMInstance;
    
    private RS232Scanner RS232ScanUtility;
    
    public static UtilsManager getInstance()
    {
        if (UTMInstance==null)
            UTMInstance=new UtilsManager();
        
        return UTMInstance;
    }
    
    private UtilsManager()
    {
        RS232ScanUtility=new RS232Scanner();
    }
    
    public RS232Scanner getRS232Scanner()
    {
        return RS232ScanUtility;
    }
        
}
