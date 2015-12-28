/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.wdconnection;

/**
 *
 * @author blinov_is
 */
public class WDSystemState {

    public enum WDStates {
        WD_SysState_ACTIVE, //engine active or extpower
        WD_SysState_IDLE, //engine stop security system inactive
        WD_SysState_POWEROFF, //full system shutdown
        WD_SysState_SLEEP, //security system active, sleep and monitoring state
        WD_SysState_REBOOT, //reboot system
        WD_SysState_REBOOT_KK, //reboot controller
        WD_SysState_NEEDRESTORE, //reboot controller
        WD_SysState_NEEDRESTORE_EMERG  //reboot controller
    }

    public WDStates TargetState;
    public WDStates CurrentState;

    public int TargetSleepInterval;
    public int CurrentSleepInterval;

    public byte GetTargetStateB() {
        return (GetByteState(TargetState));
    }

    public byte GetCurrentStateB() {
        return (GetByteState(CurrentState));
    }

    private byte GetByteState(WDStates State) {
        switch (State) {
            case WD_SysState_ACTIVE:
                return 1;
            case WD_SysState_IDLE:
                return 2;
            case WD_SysState_POWEROFF:
                return 3;
            case WD_SysState_SLEEP:
                return 4;
            case WD_SysState_REBOOT:
                return 5;
            case WD_SysState_REBOOT_KK:
                return 6;
            case WD_SysState_NEEDRESTORE:
                return 7;
            case WD_SysState_NEEDRESTORE_EMERG:
                return 8;
            default:
                return 0;
        }
    }
}
