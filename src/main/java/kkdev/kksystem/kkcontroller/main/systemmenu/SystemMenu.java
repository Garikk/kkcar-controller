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
import kkdev.kksystem.base.classes.notify.NotifyConsts;
import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.classes.plugins.PluginInfo;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.classes.plugins.simple.managers.PluginManagerDataProcessor;
import kkdev.kksystem.base.constants.PluginConsts;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_CONTROL_DATA;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UICONTEXT_GFX2;
import kkdev.kksystem.base.interfaces.IPluginBaseInterface;
import kkdev.kksystem.base.interfaces.IPluginKKConnector;
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

    public static void InitSystemMenu(IPluginBaseInterface BaseConnector) {
        BCE = BaseConnector;
        PManager.baseConnector = BCE;

        class MenuCallBack implements IMenuMakerItemSelected {

            @Override
            public void selectedItem(String ItemCMD) {
                ExecMenuFunction(ItemCMD);
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
        for (MKMenuItem MI : mainConfiguration.systemMenuItems) {
            FeatureItems.add(MI);
        }
        MKMenuItem[] MT = new MKMenuItem[FeatureItems.size()];
        int i = 0;
        for (MKMenuItem M : FeatureItems) {
            MT[i] = M;
            i++;
        }

        SysMenu.addMenuItems(MT);
        //
    }

    public static void ShowMenu() {
        SysMenu.showMenu();

    }

    private static void ExecMenuFunction(String Exec) {
        String[] CMD = Exec.split(" ");
        switch (CMD[0]) {
            case MNU_CMD_CHANGE_FEATURE:
                SystemOperations.ChangeFeature(CMD[1], CMD[2]);
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
        //
        switch (PD.controlDataType) {
            case CONTROL_LONGPRESS:
                ButtonsManager(PD, PP.FeatureID.equals(KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID));
                break;
            case CONTROL_TRIGGERED:
                ButtonsManager(PD, PP.FeatureID.equals(KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID));
                break;
        }
    }

    private static void ButtonsManager(PinControlData PD, boolean GlobalCommand) {
        switch (PD.controlID) {
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
                if (PD.controlDataType == CONTROL_TRIGGERED & (!GlobalCommand)) {
                 SysMenu.menuSelectBack();
                } else if (PD.controlDataType == CONTROL_LONGPRESS & (GlobalCommand)) {
                    ExecMenuFunction(MNU_CMD_CHANGE_FEATURE + " " + KK_BASE_FEATURES_SYSTEM_UID + " " + SystemConsts.KK_BASE_UICONTEXT_DEFAULT);
                }
                break;
            case "CUSTOM_CHR_CLOCK_M":
                InitClock();
                break;

        }

    }

    private static void InitClock() {
        // PluginManagerDataProcessor PManager = new PluginManagerDataProcessor();
        // PManager.baseConnector = BCE;
        PManager._DISPLAY_ActivatePageDirect(KK_BASE_FEATURES_SYSTEM_UID, KK_BASE_UICONTEXT_GFX2, mainConfiguration.systemDisplay_UID, "CLOCK");

    }
}
