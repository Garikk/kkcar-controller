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
import kkdev.kksystem.base.interfaces.IContextUtils;
import kkdev.kksystem.kkcontroller.main.systemoperations.SystemOperations;
import kkdev.kksystem.kkcontroller.main.utils.RS232.RS232Scanner;
import kkdev.kksystem.kkcontroller.main.utils.display.DisplayPageStorage;
import kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader;
import kkdev.kksystem.base.interfaces.IPluginBaseConnection;
import kkdev.kksystem.base.interfaces.IControllerUtils;
import kkdev.kksystem.base.interfaces.IDisplayUtils;
import kkdev.kksystem.base.interfaces.IHWDevicesUtils;
import kkdev.kksystem.base.interfaces.IPluginUtils;

/**
 *
 * @author blinov_is
 */
public class UtilsManager implements IControllerUtils {

    private static UtilsManager UTMInstance;
    private IPluginUtils PluginManager;
    private IContextUtils ContextFunctions;
    private IHWDevicesUtils HWManager;
    private IDisplayUtils DisplayFunctions;

    public static UtilsManager getInstance() {
        if (UTMInstance == null) {
            UTMInstance = new UtilsManager();
        }

        return UTMInstance;
    }

    private UtilsManager() {
        PluginManager = new PluginUtility();
        ContextFunctions = new ContextUtility();
        HWManager = new HWUtility();
        DisplayFunctions = new DisplayUtility();
    }

    public void execUtilityPin(IPluginBaseConnection connector, PinDataUtils PData) {
        switch (PData.CommandType) {
            case UICONTEXT_GetUIContext:
                break;
        }
    }

    @Override
    public IContextUtils ContextFunctions() {
        return ContextFunctions;
    }

    @Override
    public IDisplayUtils DisplayFunctions() {
        return DisplayFunctions;
    }

    @Override
    public IHWDevicesUtils HWManager() {
        return HWManager;
    }

    @Override
    public IPluginUtils PluginManager() {
        return PluginManager;
    }
}
