/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.systemmenu;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import kkdev.kksystem.base.classes.controls.PinControlData;
import static kkdev.kksystem.base.classes.controls.PinControlData.DEF_BTN_BACK;
import static kkdev.kksystem.base.classes.controls.PinControlData.DEF_BTN_DOWN;
import static kkdev.kksystem.base.classes.controls.PinControlData.DEF_BTN_ENTER;
import static kkdev.kksystem.base.classes.controls.PinControlData.DEF_BTN_UP;
import static kkdev.kksystem.base.classes.controls.PinControlData.KK_CONTROL_DATA.CONTROL_LONGPRESS;
import static kkdev.kksystem.base.classes.controls.PinControlData.KK_CONTROL_DATA.CONTROL_TRIGGERED;
import kkdev.kksystem.base.classes.display.tools.menumaker.MKMenuItem;
import kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker;
import kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker.IMenuMakerItemSelected;
import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_CONTROL_DATA;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
import kkdev.kksystem.base.interfaces.IPluginBaseInterface;
import static kkdev.kksystem.kkcontroller.main.ControllerSettingsManager.MainConfiguration;
import static kkdev.kksystem.kkcontroller.main.systemmenu.MenuOperations.ExecSysMenuOperation;
import kkdev.kksystem.kkcontroller.main.systemoperations.SystemOperations;

/**
 *
 * @author blinov_is
 */
public abstract class SystemMenu {

    private static MenuMaker SysMenu;
       
    public static final String MNU_CMD_CHANGE_FEATURE = "CHFTR";
    public static final String MNU_CMD_SYSMENU_PFX = "KKSYSCMD";
    public static final String MNU_CMD_SYSMENU_PFX_BRDTOOLS = "TOOLS";
    public static final String MNU_CMD_SYSMENU_PFX_INFO = "INFO";

    public static final String MNU_CMD_BRD_TOOLS_REBOOT = "REBOOT";
    public static final String MNU_CMD_BRD_TOOLS_POWEROFF = "POWEROFF";
    public static final String MNU_CMD_BRD_TOOLS_BOARDINFO = "BOARDINFO";
    public static final String MNU_CMD_BRD_INFO_PLUGINS = "PLUGINS";
    public static final String MNU_CMD_BRD_INFO_VERSION = "VERSION";
    


    public static void InitSystemMenu(IPluginBaseInterface BaseConnector) {
        IMenuMakerItemSelected MenuCallBack = (String ItemCMD) -> {
            ExecMenuFunction(ItemCMD);
        };
        SysMenu = new MenuMaker(KK_BASE_FEATURES_SYSTEM_UID,SystemConsts.KK_BASE_UICONTEXT_DEFAULT, null, BaseConnector, MenuCallBack, MainConfiguration.SystemDisplay_UID);
        //
        //  MenuItem[] MenuItemsToLoad = SettingsManager.MainConfiguration.SystemMenuItems;
        List<MKMenuItem> FeatureItems = new ArrayList<>();
        for (FeatureConfiguration FT : MainConfiguration.Features) {
            if (FT.IsSystemFeature) {
                continue;
            }
            
            if (!FT.ShowInSystemMenu) {
                continue;
            }

            MKMenuItem MI = new MKMenuItem();
            MI.DisplayName = FT.FeatureName;
            //
            MI.ItemCommand = MNU_CMD_CHANGE_FEATURE + " " + FT.FeatureUUID;
            //
            FeatureItems.add(MI);
        }
        for (MKMenuItem MI : MainConfiguration.SystemMenuItems) {
            FeatureItems.add(MI);
        }
        MKMenuItem[] MT = new MKMenuItem[FeatureItems.size()];
        int i = 0;
        for (MKMenuItem M : FeatureItems) {
            MT[i] = M;
            i++;
        }
        SysMenu.AddMenuItems(MT);
        //
    }

    public static void ShowMenu() {
        SysMenu.ShowMenu();

    }

    private static void ExecMenuFunction(String Exec) {
        String[] CMD = Exec.split(" ");
        switch (CMD[0]) {
            case MNU_CMD_CHANGE_FEATURE:
                SystemOperations.ChangeFeature(CMD[1]);
                break;
            case MNU_CMD_SYSMENU_PFX:
                ExecSysMenuOperation(CMD);
                break;
        }

    }

    public static void ProcessCommands(PluginMessage PP) {
        switch (PP.PinName) {
            case (KK_PLUGIN_BASE_CONTROL_DATA):
                ProcessMenuManager(PP);
                break;

        }
    }

    private static void ProcessMenuManager(PluginMessage PP) {
        PinControlData PD = (PinControlData) PP.PinData;
        //

                
        switch (PD.ControlDataType) {
            case CONTROL_LONGPRESS:
                if (PP.FeatureID.equals(KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID)) {
                    if (PD.ControlID.equals(DEF_BTN_BACK)) {
                        ButtonsManager(PD, true);
                    }
                }
                break;
            case CONTROL_TRIGGERED:
                ButtonsManager(PD, false);
                break;
        }
    }

    private static void ButtonsManager(PinControlData PD, boolean GlobalCommand) {
        switch (PD.ControlID) {
            case DEF_BTN_UP:
                SysMenu.MenuSelectUp();
                break;
            case DEF_BTN_DOWN:
                SysMenu.MenuSelectDown();
                break;
            case DEF_BTN_ENTER:
                SysMenu.MenuExec();
                break;
            case DEF_BTN_BACK:
                if (PD.ControlDataType == CONTROL_TRIGGERED) {
                    SysMenu.MenuSelectBack();
                } else if (PD.ControlDataType == CONTROL_LONGPRESS) {
                    ExecMenuFunction(MNU_CMD_CHANGE_FEATURE + " " + KK_BASE_FEATURES_SYSTEM_UID);
                }
                break;

        }

    }
}
