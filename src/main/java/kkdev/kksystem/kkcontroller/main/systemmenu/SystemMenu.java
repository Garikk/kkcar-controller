/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.systemmenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import kkdev.kksystem.base.classes.controls.PinDataControl;
import static kkdev.kksystem.base.classes.controls.PinDataControl.*;
import static kkdev.kksystem.base.classes.controls.PinDataControl.KK_CONTROL_DATA.*;
import kkdev.kksystem.base.classes.display.tools.menumaker.MKMenuItem;
import kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker;
import kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker.IMenuMakerItemSelected;
import static kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker.KK_MENUMAKER_SPECIALCMD_SUBMENU;
import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.classes.plugins.PluginInfo;
import kkdev.kksystem.base.classes.plugins.QuickParameterInfo;
import kkdev.kksystem.base.classes.plugins.simple.managers.PluginManagerDataProcessor;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_CONTROL_DATA;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UICONTEXT_GFX2;
import kkdev.kksystem.base.interfaces.IPluginBaseInterface;
import static kkdev.kksystem.kkcontroller.main.systemmenu.MenuOperations.ExecSysMenuOperation;
import kkdev.kksystem.kkcontroller.main.systemoperations.SystemOperations;
import static kkdev.kksystem.kkcontroller.main.ControllerSettingsManager.mainConfiguration;

/**
 *
 * @author blinov_is
 */
public abstract class SystemMenu {

    private static MenuMaker SysMenu;

    public static final String MNU_CMD_LEAVE = "LEAVE";

    public static final String MNU_CMD_CHANGE_FEATURE = "CHFTR";
    public static final String MNU_CMD_SYSMENU_PFX = "KKSYSCMD";
    public static final String MNU_CMD_SYSMENU_PFX_BRDTOOLS = "TOOLS";
    public static final String MNU_CMD_SYSMENU_PFX_INFO = "INFO";

    public static final String MNU_CMD_BRD_TOOLS_REBOOT = "REBOOT";
    public static final String MNU_CMD_BRD_TOOLS_POWEROFF = "POWEROFF";
    public static final String MNU_CMD_BRD_TOOLS_BOARDINFO = "BOARDINFO";
    public static final String MNU_CMD_BRD_INFO_PLUGINS = "PLUGINS";
    public static final String MNU_CMD_BRD_INFO_VERSION = "VERSION";

    private static IPluginBaseInterface BCE;
    static PluginManagerDataProcessor PManager = new PluginManagerDataProcessor();

    public static void initSystemMenu(IPluginBaseInterface BaseConnector) {
        BCE = BaseConnector;
        PManager.setBaseConnector(BCE);

        class MenuCallBack implements IMenuMakerItemSelected {

            @Override
            public void selectedItem(String ItemCMD) {
                execMenuFunction(ItemCMD);
            }

            @Override
            public void stepBack(String BackCMD) {
                //Not Uses
            }

            @Override
            public void activeMenuElement(String ItemText, String ItemCMD) {
                //not used

            }
        }

        SysMenu = new MenuMaker(BCE.systemUtilities(), KK_BASE_FEATURES_SYSTEM_UID, SystemConsts.KK_BASE_UICONTEXT_DEFAULT, null, BaseConnector, new MenuCallBack(), mainConfiguration.systemDisplay_UID, true);
        //
        //  MenuItem[] MenuItemsToLoad = SettingsManager.mainConfiguration.systemMenuItems;
        List<MKMenuItem> FeatureItems = new ArrayList<>();
        for (FeatureConfiguration FT : mainConfiguration.features) {
            if (FT.IsSystemFeature) {
                continue;
            }
            if (!FT.ShowInSystemMenu) {
                continue;
            }

            MKMenuItem MI = new MKMenuItem();
            MI.displayName = FT.FeatureName;
            //
            MI.itemCommand = MNU_CMD_CHANGE_FEATURE + " " + FT.FeatureUUID + " " + FT.DefaultUIContext;
            //
            FeatureItems.add(MI);
        }
        //
        boolean Ok=false;
        for (MKMenuItem MI:  mainConfiguration.systemMenuItems) {
            for (MKMenuItem MSI : MI.subItems) {
                if (MSI.itemCommand.equals("$KK_PLUGINS_QUICKSETTINGS")) {
                    MSI.subItems = createPluginsQuickSettingsMenu();
                    Ok=true;
                    break;
                }
            }
            if (Ok)
                break;
        }
        //
        FeatureItems.addAll(Arrays.asList(mainConfiguration.systemMenuItems));
        //
        //
        MKMenuItem[] MT = new MKMenuItem[FeatureItems.size()];
        int i = 0;
        for (MKMenuItem M : FeatureItems) {
            MT[i] = M;
            i++;
        }

        SysMenu.addMenuItems(MT);
        //
    }

    public static void showMenu() {
        SysMenu.showMenu();

    }

    private static void execMenuFunction(String Exec) {
        String[] CMD = Exec.split(" ");
        switch (CMD[0]) {
            case MNU_CMD_CHANGE_FEATURE:
                SystemOperations.changeFeature(CMD[1], CMD[2]);
                break;
            case MNU_CMD_SYSMENU_PFX:
                ExecSysMenuOperation(CMD);
                break;
        }

    }

    public static void processCommands(String PinName, PinDataControl PP) {
        switch (PinName) {
            case (KK_PLUGIN_BASE_CONTROL_DATA):
                processMenuManager(PP.featureID, PP);
                break;

        }
    }

    private static void processMenuManager(Set<String> FeatureID, PinDataControl PD) {
        //

        switch (PD.controlDataType) {
            case CONTROL_LONGPRESS:
                buttonsManager(PD, FeatureID.contains(KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID));
                break;
            case CONTROL_TRIGGERED:
                buttonsManager(PD, FeatureID.contains(KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID));
                break;
        }
    }

    private static void buttonsManager(PinDataControl PD, boolean GlobalCommand) {

        for (String btnID : PD.controlID) {
            switch (btnID) {
                case DEF_BTN_UP:
                    SysMenu.processControlCommand(PD.controlID);
                    //SysMenu.menuSelectUp();
                    break;
                case DEF_BTN_DOWN:
                    SysMenu.processControlCommand(PD.controlID);
                    //SysMenu.menuSelectDown();
                    break;
                case DEF_BTN_ENTER:
                    SysMenu.processControlCommand(PD.controlID);
                    //SysMenu.menuExec();
                    break;
                case DEF_BTN_BACK:
                    if (PD.controlDataType == CONTROL_TRIGGERED & ((!GlobalCommand) || PD.featureID.contains(SystemConsts.KK_BASE_FEATURES_SYSTEM_UID))) {
                        SysMenu.menuSelectBack();
                    } else if (PD.controlDataType == CONTROL_LONGPRESS & (GlobalCommand)) {
                        execMenuFunction(MNU_CMD_CHANGE_FEATURE + " " + KK_BASE_FEATURES_SYSTEM_UID + " " + SystemConsts.KK_BASE_UICONTEXT_DEFAULT);
                    }
                    break;
                case "CUSTOM_CHR_CLOCK_M":
                    initClock();
                    break;
            }
        }
    }

    private static void initClock() {
        // PluginManagerDataProcessor PManager = new PluginManagerDataProcessor();
        // PManager.baseConnector = BCE;
        PManager._DISPLAY_ActivatePageDirect(KK_BASE_FEATURES_SYSTEM_UID, KK_BASE_UICONTEXT_GFX2, mainConfiguration.systemDisplay_UID, "CLOCK");

    }
    
    private static MKMenuItem[] createPluginsQuickSettingsMenu()
    {
       MKMenuItem[] Ret;
       Ret=new MKMenuItem[BCE.systemUtilities().PLUGINS_GetLoadedPlugins().size()];
        
       int i=0;
       for (PluginInfo PI:BCE.systemUtilities().PLUGINS_GetLoadedPlugins())
       {
           Ret[i]=new MKMenuItem();
           Ret[i].displayName=PI.PluginName;
           Ret[i].itemCommand=KK_MENUMAKER_SPECIALCMD_SUBMENU;
           
           List<QuickParameterInfo> QPI=BCE.systemUtilities().PLUGINS_GetPluginQuickParameters(PI.PluginUUID);
           
           Ret[i].subItems=new MKMenuItem[QPI.size()];
           
           int ii=0;
           for (QuickParameterInfo Q:QPI)
           {
               Ret[i].subItems[ii].displayName=Q.Name;
               Ret[i].subItems[ii].itemCommand="CHANGE QPR " + PI.PluginUUID + " " + Q.Name;
               
               ii++;
           }
           i++;
       }
    
       return Ret;
    }
}
