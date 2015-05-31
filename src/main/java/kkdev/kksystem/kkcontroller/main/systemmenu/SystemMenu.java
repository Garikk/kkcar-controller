/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.systemmenu;

import com.sun.org.apache.xalan.internal.utils.FeatureManager.Feature;
import kkdev.kksystem.base.classes.display.menumaker.MenuMaker;
import kkdev.kksystem.base.classes.display.menumaker.MenuMaker.IMenuMakerItemSelected;
import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.classes.plugins.simple.managers.PluginManagerBase;
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
        SysMenu=new MenuMaker(KK_BASE_FEATURES_SYSTEM_UID, BaseConnector,MenuCallBack);
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
}
