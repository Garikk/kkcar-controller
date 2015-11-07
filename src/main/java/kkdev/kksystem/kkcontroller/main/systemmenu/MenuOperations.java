/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.systemmenu;

import kkdev.kksystem.base.classes.display.tools.infopage.PageMaker;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.MNU_CMD_BRD_INFO_PLUGINS;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.MNU_CMD_BRD_INFO_VERSION;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.MNU_CMD_BRD_TOOLS_BOARDINFO;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.MNU_CMD_BRD_TOOLS_POWEROFF;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.MNU_CMD_BRD_TOOLS_REBOOT;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.MNU_CMD_SYSMENU_PFX_BRDTOOLS;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.MNU_CMD_SYSMENU_PFX_INFO;

/**
 *
 * @author sayma_000
 */
public abstract class MenuOperations {

    public static void ExecSysMenuOperation(String[] Command) {
        switch (Command[0]) {
            case MNU_CMD_SYSMENU_PFX_INFO:
                switch (Command[1]) {
                    case MNU_CMD_BRD_INFO_PLUGINS:
                        break;
                    case MNU_CMD_BRD_INFO_VERSION:
                        break;

                }
                break;
            case MNU_CMD_SYSMENU_PFX_BRDTOOLS:
                switch (Command[1]) {
                    case MNU_CMD_BRD_TOOLS_BOARDINFO:
                        break;
                    case MNU_CMD_BRD_TOOLS_POWEROFF:
                        break;
                    case MNU_CMD_BRD_TOOLS_REBOOT:
                        break;
                }
                break;
        }
    }

    private static void CreateToolsInfoPages()
    { 
       
    
    }
    private static void ExecSysMenuOperationPluginList(String[] Command) {

    }
    
    private static void ExecSysMenuOperationKKTools(String[] Command)
    {
    }
}
