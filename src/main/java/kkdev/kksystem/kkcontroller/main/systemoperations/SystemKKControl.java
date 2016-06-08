/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.systemoperations;

import kkdev.kksystem.base.classes.base.PinBaseCommand;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.kkcontroller.wdconnection.WDSystemState;
import kkdev.kksystem.kkcontroller.wdconnection.WatchDogService;

/**
 *
 * @author blinov_is
 */
public class SystemKKControl {

    
    public static void ProcessKKCommand(PluginMessage PP)
    {
        PinBaseCommand PBK=(PinBaseCommand)PP.PinData;
        
        switch (PBK.baseCommand)
        {
            case SYSTEM_POWEROFF:
                CMD_PowerOffSystem();
                break;
            case SYSTEM_REBOOT_HW:
                CMD_RebootSystem();
                break;
            case SYSTEM_RESTART_KKCONTROLLER:
                CMD_RebootKKController();
                break;
            case SYSTEM_RESTORE_BACKUP:
                break;
            case SYSTEM_RESTORE_EMERGENCY:
                break;
            case GET_CURRENT_FEATURE:
                break;
        }
    }
    
    public static void CMD_PowerOffSystem()
    {
        CMD_Exec_SetPowerOffState();     //Prepare WD for reboot
        CMD_Exec_ShutDownKKController(); //Stop KK and exit
    }
     public static void CMD_RebootKKController()
    {
        CMD_Exec_SetRebootKKState();      //Prepare WD for reboot
        CMD_Exec_ShutDownKKController();  //Stop KK and exit
    }
    public static void CMD_RebootSystem()
    {
        CMD_Exec_SetRebootHWState();
        CMD_Exec_ShutDownKKController();  //Stop KK and exit
    }
    
    private static void CMD_Exec_SetPowerOffState()
    {
            WatchDogService.getInstance().ChangeWDStateCurrent(WDSystemState.WDStates.WD_SysState_POWEROFF);
    }
    private static void CMD_Exec_SetRebootHWState()
    {
            WatchDogService.getInstance().ChangeWDStateCurrent(WDSystemState.WDStates.WD_SysState_REBOOT);
    }
      private static void CMD_Exec_SetRebootKKState()
    {
             WatchDogService.getInstance().ChangeWDStateCurrent(WDSystemState.WDStates.WD_SysState_REBOOT_KK);
    }
    private static void CMD_Exec_ShutDownKKController()
    {
            //nothing
    }
}
