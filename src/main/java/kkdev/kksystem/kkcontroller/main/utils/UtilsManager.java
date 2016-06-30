/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.utils;

import java.util.List;
import kkdev.kksystem.base.classes.base.PinDataUtils;
import kkdev.kksystem.base.classes.display.DisplayInfo;
import kkdev.kksystem.base.classes.display.pages.DisplayPage;
import kkdev.kksystem.base.classes.kkcontroller.RS232Device;
import kkdev.kksystem.base.classes.kkcontroller.UIContextInfo;
import kkdev.kksystem.base.classes.plugins.PluginInfo;
import kkdev.kksystem.base.interfaces.IKKControllerUtils;
import kkdev.kksystem.base.interfaces.IPluginBaseInterface;
import kkdev.kksystem.kkcontroller.main.systemoperations.SystemOperations;
import kkdev.kksystem.kkcontroller.main.utils.RS232.RS232Scanner;
import kkdev.kksystem.kkcontroller.main.utils.display.DisplayPageStorage;
import kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader;


/**
 *
 * @author blinov_is
 */
public class UtilsManager implements IKKControllerUtils {
    private static UtilsManager UTMInstance;
    private static DisplayPageStorage DPStor;
    private static UIContextStorage UICtxStor;
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
         DPStor=new DisplayPageStorage();
         UICtxStor=new UIContextStorage();
         
    }
    
    public RS232Scanner getRS232Scanner()
    {
        return RS232ScanUtility;
    }
    //
    //==============================================
    //
    @Override
    public List<RS232Device> HWDEVICES_GetRS232Devices() {
       return RS232ScanUtility.RS232Ports;
    }

    @Override
    public List<PluginInfo> PLUGINS_GetLoadedPlugins() {
        return PluginLoader.GetActivePluginsInfo();
    }

    @Override
    public List<DisplayPage> DISPLAY_GetUIDisplayPages() {
      return DPStor.GetPages();
    }

    @Override
    public void DISPLAY_AddUIDisplayPage(DisplayPage Page) {
       // System.out.println("[KK][UTIL] Add display page " + Page.PageName + " "+ Page.UIContexts);
     DPStor.AddPage(Page);
    }

    @Override
    public DisplayPage DISPLAY_GetUIDisplayPage(String Page) {
      return DPStor.GetPage(Page);
    }

    @Override
    public void UICONTEXT_AddUIContext(String UIContextID) {
        UIContextInfo UIC=  new UIContextInfo(UIContextID);
        UICtxStor.AddContext(UIC);
    }

    @Override
    public UIContextInfo UICONTEXT_GetUIContextInfo(String ContextID) {
        //Fill additional indo
        UIContextInfo Ret;
        Ret=UICtxStor.GetContext(ContextID);
        //
        Ret.ActiveFeature=SystemOperations.getActiveFeatureID(ContextID);
        Ret.ActivePage=SystemOperations.ActivePage;
        //
      return Ret;
    }

    @Override
    public void UICONTEXT_UpdateDisplayInUIContext(String ContextID,DisplayInfo DI) {
       UICtxStor.GetContext(ContextID).UIDisplay=DI;
    }

    
    public void execUtilityPin(IPluginBaseInterface connector, PinDataUtils PData)
    {
          switch (PData.CommandType)
          {
              case UICONTEXT_GetUIContext:
                  break;
          }
              
    
    
    }
}
