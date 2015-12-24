/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroler.wdconnection;

/**
 *
 * @author blinov_is
 */
public class WDSystemState {
    public enum WDStates
            {
        WD_SysState_ACTIVE,             //engine active or extpower
        WD_SysState_IDLE,               //engine stop security system inactive
        WD_SysState_POWEROFF,           //full system shutdown
        WD_SysState_SLEEP,              //security system active, sleep and monitoring state
        WD_SysState_REBOOT,             //reboot system
        WD_SysState_REBOOT_KK,         //reboot controller
        WD_SysState_NEEDRESTORE,       //reboot controller
        WD_SysState_NEEDRESTORE_EMERG  //reboot controller
    }
    
    public WDStates TargetState;
    public WDStates CurrentState;
    
    public int TargetSleepInterval;
    public int CurrentSleepInterval;
}
