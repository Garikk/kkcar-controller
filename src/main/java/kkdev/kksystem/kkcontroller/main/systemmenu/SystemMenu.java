/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.systemmenu;

import kkdev.kksystem.base.classes.controls.PinControlData;
import static kkdev.kksystem.base.classes.controls.PinControlData.KK_CONTROL_DATA.CONTROL_LONGPRESS;
import kkdev.kksystem.base.classes.display.menumaker.MenuMaker;
import kkdev.kksystem.base.classes.display.menumaker.MenuMaker.IMenuMakerItemSelected;
import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_CONTROL_COMMAND;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
import kkdev.kksystem.base.interfaces.IPluginBaseInterface;
import kkdev.kksystem.kkcontroller.main.SettingsManager;

/**
 *
 * @author blinov_is
 */
public abstract class SystemMenu  {
    private static MenuMaker SysMenu;
    
    
    public static void InitSystemMenu(IPluginBaseInterface BaseConnector)
    {
        IMenuMakerItemSelected MenuCallBack=(String ItemID) -> {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        };
        SysMenu=new MenuMaker(KK_BASE_FEATURES_SYSTEM_UID, BaseConnector,MenuCallBack,SettingsManager.MainConfiguration.SystemDisplay_UID);
        //
        String[][] ForMenuItems=new String[SettingsManager.MainConfiguration.Features.length][2];
        int f=0;
        for (FeatureConfiguration FT:SettingsManager.MainConfiguration.Features)
        {
            ForMenuItems[f][0]=FT.FeatureName;
            ForMenuItems[f][1]="FS " + FT.FeatureUUID;
            f++;
        }
        SysMenu.AddMenuItems(ForMenuItems);
        //
    }
    
    public static void ShowMenu()
    {
        SysMenu.ShowMenu();
    
    }

  
    public static void ProcessCommands(PluginMessage PP)
    {
        switch (PP.PinName)
        {
            case (KK_PLUGIN_BASE_CONTROL_COMMAND):
                ProcessMenuManager(PP);
                break;
        
        }
    }
    
    private static void ProcessMenuManager(PluginMessage PP)
    {
        PinControlData PD=(PinControlData) PP.PinData;
        //
        switch (PD.DataType)
        {
            case CONTROL_LONGPRESS:
                if (PP.FeatureID.equals(KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID))
                {
                    if (PD.ControlID.equals(PinControlData.DEF_BTN_BACK))
                    {
                        ButtonsManager(PP,true);
                    }
                }
                break;
            case CONTROL_TRIGGERED:
                ButtonsManager(PP,false);
                break;
        }
    }
    private static void ButtonsManager(PluginMessage PP, boolean GlobalCommand)
    {
        
    
    }
}
