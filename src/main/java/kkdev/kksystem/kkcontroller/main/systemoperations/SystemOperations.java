/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.systemoperations;

import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.constants.PluginConsts;
import kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu;

/**
 *
 * @author blinov_is
 */
public class SystemOperations {
    
    public static void ProcessSystemPIN(PluginMessage Msg)
    {
        //Redirect all control data to menu module
        if (Msg.PinName.equals(PluginConsts.KK_PLUGIN_BASE_CONTROL_DATA))
        {
            SystemMenu.ProcessCommands(Msg);
        }
        //Redirect all PIN CMD data to KK control
        else if (Msg.PinName.equals(PluginConsts.KK_PLUGIN_BASE_PIN_COMMAND))
        {
            SystemKKControl.ProcessKKCommand(Msg);
        }
    }
    
}
